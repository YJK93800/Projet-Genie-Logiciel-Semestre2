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
    private GridPane grid;

    public ForestDisplay(Forest forest) {
        this.forest = forest;
    }

    public Parent createContent() {

        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);

        draw();

        return grid;
    }
    private void draw() {

        grid.getChildren().clear();

        ForestCell[][] forestGrid = forest.getForestGrid();
        int rows = forestGrid.length;
        int cols = forestGrid[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {

                ForestCell cell = forestGrid[i][j];

                Rectangle rect = new Rectangle(30, 30);
                rect.setFill(cell.displayColor());
                rect.setStroke(Color.BLACK);

                StackPane tile = new StackPane(rect);
                grid.add(tile, j, i);
            }
        }
    }
    public void update() {
        draw();
    }


}