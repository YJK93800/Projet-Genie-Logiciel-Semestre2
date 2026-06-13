package com.example.demo.ui;

import com.example.demo.model.Forest;
import com.example.demo.model.cells.*;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Class that is used to display the forest
 * Displays the forest using colored rectangles with a fixed size of 20px by 20px
 */

public class ForestDisplay {

    private Forest forest;
    private GridPane grid;
    private Rectangle[][] rects;

    public ForestDisplay(Forest forest) {
        this.forest = forest;
    }

    public Parent createContent() {
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        buildGrid();

        StackPane gridWrapper = new StackPane(grid);

        ScrollPane scrollPane = new ScrollPane(gridWrapper);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.getStyleClass().add("forest-scroll");

        return scrollPane;
    }


    private void buildGrid() {
        grid.getChildren().clear();

        ForestCell[][] forestGrid = forest.getForestGrid();
        int rows = forestGrid.length;
        int cols = forestGrid[0].length;

        rects = new Rectangle[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                ForestCell cell = forestGrid[i][j];

                Rectangle rect = new Rectangle(20, 20);
                rect.setFill(cell == null ? Color.LIGHTGRAY : cell.displayColor());
                rect.setStroke(Color.BLACK);

                rects[i][j] = rect;

                StackPane tile = new StackPane(rect);
                grid.add(tile, j, i);
            }
        }
    }


    public void update() {
        ForestCell[][] forestGrid = forest.getForestGrid();

        if (rects == null
                || rects.length != forestGrid.length
                || rects[0].length != forestGrid[0].length) {
            buildGrid();
            return;
        }

        for (int i = 0; i < forestGrid.length; i++) {
            for (int j = 0; j < forestGrid[0].length; j++) {
                ForestCell cell = forestGrid[i][j];
                Color color;
                if (cell == null) {
                    color = Color.LIGHTGRAY;
                } else {
                    color = cell.displayColor();
                }
                rects[i][j].setFill(color);
            }
        }
    }

    // Setter Method

    /**
     * Setter method for the forest attribute.
     * Reconstruct the grid if called after createContent()
     *
     * @param forest new forest to draw
     */
    public void setForest(Forest forest) {
        this.forest = forest;
        if (grid != null) {
            buildGrid();
        }
    }

    // Getter methods

    /**
     * Getter method of the rects attribute
     *
     * @return Rectangle[][] value, contains every rectangles
     */
    public Rectangle[][] getRects() { return rects; }
}
