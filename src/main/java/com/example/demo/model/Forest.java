package com.example.demo.model;

import com.example.demo.exceptions.ForestException;
import com.example.demo.model.cells.*;
import com.example.demo.model.Weather.Weather;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.io.IOException;
import java.util.List;

/**
 * Represents the forest
 * <p>
 * @author Yann Kong IN1 GI1
 * @version 21.0.8
 */

public class Forest {

    ForestCell[][] forestGrid;
    Weather weather;


    /**
     * Constructor Method
     */
    public Forest(String file, Weather weather){
        this.forestGrid = loadForestGridLayout(file);
        this.weather = weather;

    }

    // Getter methods

    /**
     * Getter method for the forestGrid attribute
     *
     * @return self-explanatory
     */
    public ForestCell[][] getForestGrid() {return this.forestGrid;}

    /**
     * Getter method for the weather attribute
     *
     * @return self-explanatory
     */
    public Weather getWeather(){return this.weather;}

    // Methods

    /**
     * Private Method to load a forest from a txt file
     *
     * @param file String value, name of the txt file
     * @return ForestCell[][] value, the forest grid that will be used
     */

    private ForestCell[][] loadForestGridLayout(String file){
        Path currentDirectory = Paths.get("").toAbsolutePath();
        Path gridToLoad = currentDirectory.resolve("assets").resolve("textFiles").resolve(file);

        ForestCell[][] forestGrid;



        try{
            List<String> lines = Files.readAllLines(gridToLoad);
            if (lines.isEmpty()) throw new ForestException("File is Empty");
            int row = lines.size();
            int col = maxColSize(lines);

            forestGrid = new ForestCell[row][col];

            for (int i = 0; i < row; i++){
                String line = lines.get(i);

                for (int j = 0; j < col; j++){

                    if (line.charAt(j) == 'W') forestGrid[i][j] = new BodyOfWater("Eau", i, j, 0);
                    else if (line.charAt(j) == 'G') forestGrid[i][j] = new Grass("Herbe", State.ALIVE, i, j, 0, GrassType.SMALL);
                    else if (line.charAt(j) == 'S') forestGrid[i][j] = new Soil("Terre", i, j, 0);
                    else forestGrid[i][j] = new Tree("Arbre", State.ALIVE, i, j, 0, line.charAt(j));

                }
            }


        } catch (IOException e){
            System.err.println("Error, could not find level file from the given path: " + gridToLoad.toString());
            return null;
        }
        return forestGrid;
    }

    /**
     * Method to find the maximum col size of the file
     *
     * @param lines List<String> value, contains every line of the level file
     * @return self-explanatory
     */
    private int maxColSize(List<String> lines){
        int max = lines.get(0).length();
        int row = lines.size();
        for (int i = 1 ; i < row ; i++){
            if (lines.get(i).length() > max) max = lines.get(i).length();
        }
        return max;
    }

}
