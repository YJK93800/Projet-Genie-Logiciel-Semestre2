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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

import java.util.function.Consumer;

/**
 * Class in charge of the UI actions
 *
 * @author Yann Kong IN1 GI1
 * @version 21.0.8
 */
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
     * Uses the width and height input from the NewForestPopUp.
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

            // Fill default grid
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

                    tile.setOnMouseDragEntered(e -> {
                        FillCell tool = Editor.getCurrentTool();

                        if (tool instanceof IgniteTool) {
                            try {
                                newSimulation.ignitePlant(row, col);
                                rect.setFill(forestGrid[row][col].displayColor());
                            } catch (Exception ex) {
                                System.out.println(ex.getMessage());
                            }
                            return;
                        }

                        ForestCell newCell = tool.callCell(row, col);
                        if (newCell != null) {
                            forestGrid[row][col] = newCell;
                            rect.setFill(newCell.displayColor());
                        }
                    });

                    tile.setOnMouseClicked(e -> {
                        FillCell tool = Editor.getCurrentTool();

                        if (tool instanceof IgniteTool) {
                            try {
                                newSimulation.ignitePlant(row, col);
                                rect.setFill(forestGrid[row][col].displayColor());
                            } catch (Exception ex) {
                                System.out.println(ex.getMessage());
                            }
                            return;
                        }

                        ForestCell newCell = tool.callCell(row, col);
                        if (newCell != null) {
                            forestGrid[row][col] = newCell;
                            rect.setFill(newCell.displayColor());
                        }
                    });
                }
            }
        });

        dialog.open();
    }
}