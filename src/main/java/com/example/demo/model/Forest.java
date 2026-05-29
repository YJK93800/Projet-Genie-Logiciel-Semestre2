package com.example.demo.model;

import com.example.demo.exceptions.ForestException;
import com.example.demo.model.cells.*;
import com.example.demo.model.Weather.Weather;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.io.IOException;
import java.util.List;

import java.awt.image.BufferedImage;

import static java.lang.Math.min;

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

    /**
     * Second Constructor Method
     */

    public Forest(Weather weather){
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

    // Setter methods

    public void setForestGrid(ForestCell[][] forestGrid){this.forestGrid = forestGrid;}

    // Methods

    /**
     * Private Method to load a forest from a txt file
     *
     * @param file String value, name of the txt file
     * @return ForestCell[][] value, the forest grid that will be used
     */

    private ForestCell[][] loadForestGridLayout(String file){
        Path currentDirectory = Paths.get("").toAbsolutePath();
        Path gridToLoad = currentDirectory.resolve("src").resolve("main").resolve("resources").resolve("textFiles").resolve(file);

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

    /**
     * Method to divide an image into a grid and get the average RGB color distribution per cell
     *
     * @param image BufferedImage, source image
     * @return int[rows][cols][3] containing the average RGB color distribution for each cell of the grid created
     */
    public static int[][][] gridColors(BufferedImage image) {

        int imgWidth = image.getWidth();
        int imgHeight = image.getHeight();

        int cellWidth = 10;
        int cellHeight = 10;

        int gridCol = imgWidth/cellWidth + 1;
        int gridRow = imgHeight/cellHeight + 1;

        int[][][] result = new int[gridRow][gridCol][3];

        for (int row = 0; row < gridRow; row++) {
            for (int col = 0; col < gridCol; col++) {

                long sumR = 0;
                long sumG = 0;
                long sumB = 0;
                int countPixel = 0;

                int xStart = col * cellWidth;
                int yStart = row * cellHeight;
                int xEnd = (col + 1 == gridCol) ? imgWidth : xStart + cellWidth;
                int yEnd = (row + 1 == gridRow) ? imgHeight : yStart + cellHeight;

                for (int y = yStart; y < yEnd; y++) {
                    for (int x = xStart; x < xEnd; x++) {

                        int rgb = image.getRGB(x, y);

                        int r = ((rgb >> 16) & 0xFF);
                        int g = ((rgb >> 8) & 0xFF);
                        int b = (rgb & 0xFF);

                        sumR += r;
                        sumG += g;
                        sumB += b;
                        countPixel++;
                    }
                }

                result[row][col][0] = (int) (sumR / countPixel);
                result[row][col][1] = (int) (sumG / countPixel);
                result[row][col][2] = (int) (sumB / countPixel);
            }
        }

        return result;
    }

    /**
     * Method to calculate the squared distance from an RGB distribution to another
     *
     * @param r1 int, red from first distribution
     * @param g1 int, green from first distribution
     * @param b1 int, blue from first distribution
     * @param r2 int, red from second distribution
     * @param g2 int, green from second distribution
     * @param b2 int, blue from second distribution
     * @return int, squared distance between first and second RGB distribution
     */
    private static int colorDistanceSquared(int r1, int g1, int b1, int r2, int g2, int b2) {

        int dr = r1 - r2;
        int dg = g1 - g2;
        int db = b1 - b2;

        return dr*dr + dg*dg + db*db;
    }

    /**
     * Method to convert a grid of RGB distributions to a forest grid
     *
     * @param colorGrid int[][][] a grid containing RGB distributions
     * @return ForestCell[][], the forest grid corresponding best to the colors
     */
    public static ForestCell[][] convertColorGrid(int[][][] colorGrid) {

        int gridRow = colorGrid.length;
        int gridCol = colorGrid[0].length;

        ForestCell[][] forestGrid = new ForestCell[gridRow][gridCol];

        for (int i = 0; i < gridRow; i++) {
            for (int j = 0; j < gridCol; j++) {

                int r = colorGrid[i][j][0];
                int g = colorGrid[i][j][1];
                int b = colorGrid[i][j][2];

                int treeDist = colorDistanceSquared(r, g, b, 20, 90, 20);
                int grassDist = colorDistanceSquared(r, g, b, 120, 220, 120);
                int waterDist = colorDistanceSquared(r, g, b, 40, 100, 220);
                int soilDist = colorDistanceSquared(r, g, b, 120, 80, 40);

                int minDist = min(treeDist, min(grassDist, min(waterDist, soilDist)));

                if (minDist == treeDist) forestGrid[i][j] = new Tree("Arbre", State.ALIVE, i, j, 0, T);
                else if (minDist == grassDist) forestGrid[i][j] = new Grass("Herbe", State.ALIVE, i, j, 0, GrassType.SMALL);
                else if (minDist == waterDist) forestGrid[i][j] = new BodyOfWater("Eau", i, j, 0);
                else if (minDist == soilDist) forestGrid[i][j] = new Soil("Terre", i, j, 0);
            }
        }

        return forestGrid;
    }


    //Override Methods

    @Override
    public String toString(){
        String display = "\n";
        int row = this.forestGrid.length;
        int col = this.forestGrid[0].length;

        for (int i = 0; i < row; i++){
            for(int j = 0; j< col; j++){
                display += this.forestGrid[i][j].display() + " ";
            }
            display += "\n";
        }


        return display;
    }

}
