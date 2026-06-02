package com.example.demo.ui.actions;

import com.example.demo.model.Forest;
import com.example.demo.model.Weather.CardinalDirections;
import com.example.demo.model.Weather.Weather;
import com.example.demo.model.Weather.WeatherType;
import com.example.demo.model.Weather.Wind;
import com.example.demo.model.cells.*;
import com.example.demo.simulation.Simulation;
import com.example.demo.ui.ForestDisplay;
import com.example.demo.ui.Menu.NewForestPopUp;
import com.example.demo.ui.editor.Editor;
import com.example.demo.ui.editor.tools.FillCell;
import com.example.demo.ui.editor.tools.IgniteTool;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URI;
import java.util.function.Consumer;

public class UIController {

    private BorderPane root;
    private ForestDisplay forestDisplay;
    private Consumer<Simulation> onSimulationCreated;

    public UIController(BorderPane root, ForestDisplay forestDisplay, Consumer<Simulation> onSimulationCreated) {
        this.root = root;
        this.forestDisplay = forestDisplay;
        this.onSimulationCreated = onSimulationCreated;
    }

    /**
     * Method to Create a new forest grid.
     */
    public void newForestAction() {

        NewForestPopUp dialog = new NewForestPopUp(() -> {

            Weather weather = new Weather(
                    WeatherType.SUNNY,
                    new Wind(CardinalDirections.SOUTH, 100),
                    30.5,
                    50.9,
                    86.25
            );

            Forest forest = new Forest(weather);

            int w = NewForestPopUp.width;
            int h = NewForestPopUp.height;

            ForestCell[][] forestGrid = new ForestCell[h][w];
            forest.setForestGrid(forestGrid);

            for (int i = 0; i < h; i++) {
                for (int j = 0; j < w; j++) {
                    forestGrid[i][j] = new Grass(
                            "Grass",
                            State.ALIVE,
                            i, j, 0,
                            GrassType.MEDIUM
                    );
                }
            }

            Simulation newSimulation = new Simulation(forest);

            onSimulationCreated.accept(newSimulation);
            forestDisplay.setForest(forest);

            StackPane centerStack = (StackPane) root.getCenter();
            centerStack.getChildren().set(0, forestDisplay.createContent());

            Rectangle[][] rects = forestDisplay.getRects();

            for (int i = 0; i < h; i++) {
                for (int j = 0; j < w; j++) {

                    int row = i;
                    int col = j;

                    Rectangle rect = rects[i][j];
                    StackPane tile = (StackPane) rect.getParent();

                    tile.setOnDragDetected(e -> tile.startFullDrag());

                    tile.setOnMouseDragEntered(e -> handleTool(newSimulation, forestGrid, rect, row, col));
                    tile.setOnMouseClicked(e -> handleTool(newSimulation, forestGrid, rect, row, col));
                }
            }
        });

        dialog.open();
    }

    private void handleTool(Simulation sim, ForestCell[][] grid, Rectangle rect, int row, int col) {

        FillCell tool = Editor.getCurrentTool();

        if (tool instanceof IgniteTool igniteTool) {
            try {
                sim.ignitePlant(row, col);
                rect.setFill(grid[row][col].displayColor());
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            return;
        }

        ForestCell newCell = tool.callCell(row, col);
        if (newCell != null) {
            grid[row][col] = newCell;
            rect.setFill(newCell.displayColor());
        }
    }

}