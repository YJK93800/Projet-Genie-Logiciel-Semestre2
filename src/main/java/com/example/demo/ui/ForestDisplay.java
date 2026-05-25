package com.example.demo.ui;

import com.example.demo.model.Forest;
import com.example.demo.model.cells.*;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ForestDisplay {

    private Forest forest;

    public ForestDisplay(Forest forest) {
        this.forest = forest;
    }

    public Parent createContent() {

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);

        ForestCell[][] forestGrid = forest.getForestGrid();

        int rows = forestGrid.length;
        int cols = forestGrid[0].length;

        for (int i = 0; i < rows; i++) {

            for (int j = 0; j < cols; j++) {

                ForestCell cell = forestGrid[i][j];

                Rectangle rect = new Rectangle(30, 30);

                // Couleur selon le type
                if (cell instanceof Tree) {
                    rect.setFill(Color.rgb(34, 139, 34));
                }

                else if (cell instanceof Grass) {
                    rect.setFill(Color.rgb(124, 252, 0));
                }

                else if (cell instanceof BodyOfWater) {
                    rect.setFill(Color.rgb(30, 144, 255));
                }

                else if (cell instanceof Soil) {
                    rect.setFill(Color.rgb(139, 69, 19));
                }

                // Bordure
                rect.setStroke(Color.BLACK);

                StackPane tile = new StackPane(rect);

                grid.add(tile, j, i);
            }
        }

        return grid;
    }
}