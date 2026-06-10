package com.example.demo.simulation;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SaveManager {

    // Saves the simulation instance to a file
    public static void save(Simulation simulation, String fileName) {
        try {
            FileOutputStream fileOut = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(simulation);
            out.close();
            fileOut.close();
            System.out.println("Simulation saved in " + fileName);
        } catch (IOException e) {
            System.out.println("Error while saving: " + e.getMessage());
        }
    }

    // Loads a simulation instance from a file
    public static Simulation load(String fileName) {
        Simulation simulation = null;
        try {
            FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            simulation = (Simulation) in.readObject();
            in.close();
            fileIn.close();
            System.out.println("Simulation loaded from " + fileName);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error while loading: " + e.getMessage());
        }
        return simulation;
    }
}