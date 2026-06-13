package com.example.demo.simulation;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Handles the saving and loading of the simulations
 */

public class SaveManager {

    private static final String SAVE_FOLDER = "saves";

    /**
     * Saves the simulation instance in the saves folder under the given name
     *
     * @param simulation the simulation to be saved
     * @param name the name of the file
     */
    public static void save(Simulation simulation, String name) {
        File folder = new File(SAVE_FOLDER);
        if (!folder.exists()) {
            folder.mkdir();
        }

        String path = SAVE_FOLDER + "/" + name + ".ser";

        try {
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(simulation);
            out.close();
            fileOut.close();
            System.out.println("Simulation saved in " + path);
        } catch (IOException e) {
            System.out.println("Error while saving: " + e.getMessage());
        }
    }

    /**
     *  Loads a simulation from the saves folder using the given name
     *
     * @param name , the name of the file
     * @return the simulation that will be displayed
     */
    public static Simulation load(String name) {
        Simulation simulation = null;
        String path = SAVE_FOLDER + "/" + name + ".ser";

        try {
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            simulation = (Simulation) in.readObject();
            in.close();
            fileIn.close();
            System.out.println("Simulation loaded from " + path);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error while loading: " + e.getMessage());
        }
        return simulation;
    }

    /**
     * Returns the list of existing save names (without the .ser extension)
     *
     * @return a list containing the names of the files
     */
    public static ArrayList<String> getSaveNames() {
        ArrayList<String> names = new ArrayList<>();
        File folder = new File(SAVE_FOLDER);

        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    String fileName = file.getName();
                    if (fileName.endsWith(".ser")) {
                        names.add(fileName.substring(0, fileName.length() - 4));
                    }
                }
            }
        }
        return names;
    }

    /**
     * Deletes the save with the given name
     *
     * @param name name of the file to be deleted
     */
    public static void delete(String name) {
        File file = new File(SAVE_FOLDER + "/" + name + ".ser");
        if (file.exists()) {
            file.delete();
            System.out.println("Save deleted: " + name);
        }
    }

    /**
     * Makes a deep copy of a simulation in memory (used for the checkpoint)
     *
     * @param simulation the simulation to be copied
     * @return a copy of the simulation at a specific moment
     */
    public static Simulation deepCopy(Simulation simulation) {
        Simulation copy = null;
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(simulation);
            out.close();

            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            ObjectInputStream in = new ObjectInputStream(byteIn);
            copy = (Simulation) in.readObject();
            in.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error while copying: " + e.getMessage());
        }
        return copy;
    }
}