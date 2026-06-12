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
import com.example.demo.ui.editor.tools.SelectionTool;
import com.example.demo.ui.editor.tools.RectangleSelectionTool;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.MouseEvent;

import java.awt.Desktop;
import java.io.File;
import java.net.URL;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.HashSet;
import javafx.scene.paint.Color;
import java.util.function.Consumer;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles all the UI Interactions with the user and the simulation
 * Manages cell editing and their respective tools
 */

public class UIController {

    private BorderPane root;
    private ForestDisplay forestDisplay;
    private Consumer<Simulation> onSimulationCreated;
    private Set<String> selectedCells = new HashSet<>();
    private Set<String> baselineSelection = new HashSet<>();
    private int startRow = -1;
    private int startCol = -1;

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
                    tile.setOnMousePressed(e -> handleCellPressed(e, newSimulation, forestGrid, rect, row, col));
                    tile.setOnMouseDragEntered(e -> handleCellDragged(e, newSimulation, forestGrid, rect, row, col));
                }
            }
        });

        dialog.open();
    }

    /**
     * Method to load a forest grid from an already loaded simulation.
     *
     * @param loadedSimulation the simulation loaded from a save file
     */
    public void loadSimulation(Simulation loadedSimulation) {

        Forest forest = loadedSimulation.getForest();
        ForestCell[][] forestGrid = forest.getForestGrid();

        int h = forestGrid.length;
        int w = forestGrid[0].length;

        onSimulationCreated.accept(loadedSimulation);
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
                tile.setOnMousePressed(e -> handleCellPressed(e, loadedSimulation, forestGrid, rect, row, col));
                tile.setOnMouseDragEntered(e -> handleCellDragged(e, loadedSimulation, forestGrid, rect, row, col));
            }
        }
    }

    /**
     * Handles the initial mouse click/press interaction on a grid cell.
     */
    private void handleCellPressed(MouseEvent e, Simulation sim, ForestCell[][] grid, Rectangle rect, int row, int col) {
        FillCell tool = Editor.getCurrentTool();
        if (tool == null) return;

        if (tool instanceof SelectionTool) {
            if (!e.isControlDown()) {
                clearSelection();
            }
            toggleSelection(row, col, rect, grid, sim);
        }
        else if (tool instanceof RectangleSelectionTool) {
            startRow = row;
            startCol = col;
            if (!e.isControlDown()) {
                clearSelection();
            }
            baselineSelection = new HashSet<>(selectedCells);
            addCellToSelection(row, col, rect, grid, sim);
        }
        else {
            handleStandardTool(tool, sim, grid, rect, row, col);
        }
    }

    /**
     * Handles contextual drag behaviors (additive select, rectangle draw, or drawing cells).
     */
    private void handleCellDragged(MouseEvent e, Simulation sim, ForestCell[][] grid, Rectangle rect, int row, int col) {
        FillCell tool = Editor.getCurrentTool();
        if (tool == null) return;

        if (tool instanceof SelectionTool) {
            addCellToSelection(row, col, rect, grid, sim);
        }
        else if (tool instanceof RectangleSelectionTool) {
            resetToBaseline();
            int minR = Math.min(startRow, row);
            int maxR = Math.max(startRow, row);
            int minC = Math.min(startCol, col);
            int maxC = Math.max(startCol, col);

            Rectangle[][] rects = forestDisplay.getRects();
            for (int r = minR; r <= maxR; r++) {
                for (int c = minC; c <= maxC; c++) {
                    addCellToSelection(r, c, rects[r][c], grid, sim);
                }
            }
        }
        else {
            handleStandardTool(tool, sim, grid, rect, row, col);
        }
    }


    private void handleStandardTool(FillCell tool, Simulation sim, ForestCell[][] grid, Rectangle rect, int row, int col) {
        if (tool instanceof IgniteTool) {
            try {
                sim.ignitePlant(row, col);
                rect.setFill(grid[row][col].displayColor());
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            ForestCell newCell = tool.callCell(row, col);
            if (newCell != null) {
                if (grid[row][col] instanceof Vegetation oldPlant) {
                    sim.getBurningPlants().remove(oldPlant);
                    sim.getAlivePlants().remove(oldPlant);
                }
                grid[row][col] = newCell;
                if (newCell instanceof Vegetation newPlant && newPlant.getState() == State.ALIVE) {
                    sim.getAlivePlants().add(newPlant);
                }
                rect.setFill(newCell.displayColor());
            }
        }
    }






    private void toggleSelection(int row, int col, Rectangle rect, ForestCell[][] grid, Simulation sim) {
        String key = row + "," + col;
        if (selectedCells.contains(key)) {
            selectedCells.remove(key);
            rect.getStyleClass().remove("cell-selected");
            rect.setStroke(Color.BLACK);
        } else {
            selectedCells.add(key);
            rect.getStyleClass().add("cell-selected");
        }
        updatePropertiesPanel(grid, sim);
    }

    private void addCellToSelection(int row, int col, Rectangle rect, ForestCell[][] grid, Simulation sim) {
        String key = row + "," + col;
        if (!selectedCells.contains(key)) {
            selectedCells.add(key);
            rect.getStyleClass().add("cell-selected");
            updatePropertiesPanel(grid, sim);
        }
    }

    private void resetToBaseline() {
        Rectangle[][] rects = forestDisplay.getRects();
        for (String key : selectedCells) {
            if (!baselineSelection.contains(key)) {
                String[] parts = key.split(",");
                int r = Integer.parseInt(parts[0]);
                int c = Integer.parseInt(parts[1]);
                rects[r][c].getStyleClass().remove("cell-selected");
                rects[r][c].setStroke(Color.BLACK);
            }
        }
        selectedCells.clear();
        selectedCells.addAll(baselineSelection);
    }

    private void clearSelection() {
        Rectangle[][] rects = forestDisplay.getRects();
        for (String key : selectedCells) {
            String[] parts = key.split(",");
            int r = Integer.parseInt(parts[0]);
            int c = Integer.parseInt(parts[1]);
            rects[r][c].getStyleClass().remove("cell-selected");
            rects[r][c].setStroke(Color.BLACK);
        }
        selectedCells.clear();
        baselineSelection.clear();
        root.setRight(null);
    }

    private void updatePropertiesPanel(ForestCell[][] grid, Simulation sim) {
        if (selectedCells.isEmpty()) {
            root.setRight(null);
            return;
        }

        VBox panel = new VBox(12);
        panel.getStyleClass().add("sidebar");
        panel.setPadding(new Insets(15));
        panel.setPrefWidth(230);

        Label title = new Label("Edit Selection (" + selectedCells.size() + ")");
        title.getStyleClass().add("sidebar-title");
        panel.getChildren().add(title);

        Pane sep1 = new Pane();
        sep1.getStyleClass().add("sidebar-separator");
        panel.getChildren().add(sep1);

        Class<?> commonClass = null;
        List<ForestCell> selectedCellObjects = new ArrayList<>();
        for (String key : selectedCells) {
            String[] parts = key.split(",");
            ForestCell cell = grid[Integer.parseInt(parts[0])][Integer.parseInt(parts[1])];
            selectedCellObjects.add(cell);
            if (commonClass == null) commonClass = cell.getClass();
            else if (commonClass != cell.getClass()) {
                commonClass = (cell instanceof Vegetation && Vegetation.class.isAssignableFrom(commonClass)) ? Vegetation.class : ForestCell.class;
            }
        }

        boolean allTrees = selectedCellObjects.stream().allMatch(c -> c instanceof Tree);
        boolean allGrass = selectedCellObjects.stream().allMatch(c -> c instanceof Grass);

        String currentHeightStr = "Mixed";
        if (!selectedCellObjects.isEmpty()) {
            int firstHeight = selectedCellObjects.get(0).getHeight();
            if (selectedCellObjects.stream().allMatch(c -> c.getHeight() == firstHeight)) {
                currentHeightStr = String.valueOf(firstHeight);
            }
        }

        String currentStateStr = "Mixed";
        if (Vegetation.class.isAssignableFrom(commonClass) && !selectedCellObjects.isEmpty()) {
            State firstState = ((Vegetation) selectedCellObjects.get(0)).getState();
            if (selectedCellObjects.stream().allMatch(c -> ((Vegetation) c).getState() == firstState)) {
                currentStateStr = firstState.toString();
            }
        }

        String currentSpeciesStr = "Mixed";
        if (allTrees && !selectedCellObjects.isEmpty()) {
            TreeSpecies firstSpecie = ((Tree) selectedCellObjects.get(0)).getSpecie();
            if (selectedCellObjects.stream().allMatch(c -> ((Tree) c).getSpecie() == firstSpecie)) {
                currentSpeciesStr = firstSpecie.toString();
            }
        }

        String currentGrassTypeStr = "Mixed";
        if (allGrass && !selectedCellObjects.isEmpty()) {
            GrassType firstType = ((Grass) selectedCellObjects.get(0)).getType();
            if (selectedCellObjects.stream().allMatch(c -> ((Grass) c).getType() == firstType)) {
                currentGrassTypeStr = firstType.toString();
            }
        }

        VBox infoBox = new VBox(6);
        Label infoTitle = new Label("Current Attributes:");
        infoTitle.getStyleClass().add("properties-label");
        infoBox.getChildren().add(infoTitle);
        Label hLabel = new Label("• Height: " + currentHeightStr);
        hLabel.getStyleClass().add("properties-info");
        infoBox.getChildren().add(hLabel);

        if (Vegetation.class.isAssignableFrom(commonClass)) {
            Label sLabel = new Label("• State: " + currentStateStr);
            sLabel.getStyleClass().add("properties-info");
            infoBox.getChildren().add(sLabel);
        }
        if (allTrees) {
            Label spLabel = new Label("• Species: " + currentSpeciesStr);
            spLabel.getStyleClass().add("properties-info");
            infoBox.getChildren().add(spLabel);
        }
        if (allGrass) {
            Label gLabel = new Label("• Grass Type: " + currentGrassTypeStr);
            gLabel.getStyleClass().add("properties-info");
            infoBox.getChildren().add(gLabel);
        }
        panel.getChildren().add(infoBox);

        Pane sepInfo = new Pane();
        sepInfo.getStyleClass().add("sidebar-separator");
        panel.getChildren().add(sepInfo);



        Label transformLabel = new Label("Convert Cell Type:");
        transformLabel.getStyleClass().add("properties-label");

        ChoiceBox<String> typeBox = new ChoiceBox<>();
        typeBox.getItems().addAll("Grass", "Tree", "Water", "Soil");
        typeBox.setValue("Grass");
        typeBox.getStyleClass().add("custom-choice-box");

        Button convertBtn = new Button("Apply Transformation");
        convertBtn.getStyleClass().add("forest-button");
        convertBtn.setOnAction(e -> {
            String targetType = typeBox.getValue();
            if (targetType != null) {
                for (String key : selectedCells) {
                    String[] parts = key.split(",");
                    int r = Integer.parseInt(parts[0]);
                    int c = Integer.parseInt(parts[1]);

                    ForestCell newCell = switch (targetType) {
                        case "Grass" -> new Grass("Grass", State.ALIVE, r, c, 0, GrassType.MEDIUM);
                        case "Tree" -> new Tree("Tree", State.ALIVE, r, c, 0, 'O');
                        case "Water" -> new BodyOfWater("Water", r, c, 0);
                        case "Soil" -> new Soil("Soil", r, c, 0);
                        default -> null;
                    };

                    if (newCell != null) {
                        if (grid[r][c] instanceof Vegetation oldPlant) {
                            sim.getBurningPlants().remove(oldPlant);
                            sim.getAlivePlants().remove(oldPlant);
                        }
                        grid[r][c] = newCell;
                        if (newCell instanceof Vegetation newPlant && newPlant.getState() == State.ALIVE) {
                            sim.getAlivePlants().add(newPlant);
                        }
                        forestDisplay.getRects()[r][c].setFill(newCell.displayColor());
                    }
                }
                updatePropertiesPanel(grid, sim);
            }
        });
        panel.getChildren().addAll(transformLabel, typeBox, convertBtn);

        Pane sepHeight = new Pane();
        sepHeight.getStyleClass().add("sidebar-separator");
        panel.getChildren().add(sepHeight);

        Label heightLabel = new Label("Modify Height:");
        heightLabel.getStyleClass().add("properties-label");

        javafx.scene.control.TextField heightField = new javafx.scene.control.TextField();
        heightField.getStyleClass().add("custom-text-field");
        if (!currentHeightStr.equals("Mixed")) {
            heightField.setText(currentHeightStr);
        }

        Button heightBtn = new Button("Apply Height");
        heightBtn.getStyleClass().add("forest-button");
        heightBtn.setOnAction(e -> {
            try {
                int newHeight = Integer.parseInt(heightField.getText());
                for (ForestCell cell : selectedCellObjects) {
                    cell.setHeight(newHeight);
                }
                updatePropertiesPanel(grid, sim);
            } catch (NumberFormatException ex) {
                System.out.println("Invalid height format.");
            }
        });
        panel.getChildren().addAll(heightLabel, heightField, heightBtn);



        if (Vegetation.class.isAssignableFrom(commonClass)) {
            Pane sep2 = new Pane();
            sep2.getStyleClass().add("sidebar-separator");
            panel.getChildren().addAll(sep2);

            Label stateLabel = new Label("Modify Plant State:");
            stateLabel.getStyleClass().add("properties-label");

            ChoiceBox<State> stateBox = new ChoiceBox<>();
            stateBox.getItems().addAll(State.values());
            stateBox.getStyleClass().add("custom-choice-box");

            if (!currentStateStr.equals("Mixed")) {
                stateBox.setValue(State.valueOf(currentStateStr));
            }

            State firstState = ((Vegetation) selectedCellObjects.get(0)).getState();
            if (selectedCellObjects.stream().allMatch(c -> ((Vegetation) c).getState() == firstState)) {
                stateBox.setValue(firstState);
            }

            Button stateBtn = new Button("Apply State");
            stateBtn.getStyleClass().add("forest-button");
            stateBtn.setOnAction(e -> {
                State newState = stateBox.getValue();
                if (newState != null) {
                    for (String key : selectedCells) {
                        String[] parts = key.split(",");
                        int r = Integer.parseInt(parts[0]);
                        int c = Integer.parseInt(parts[1]);
                        if (grid[r][c] instanceof Vegetation veg) {
                            if (newState == State.BURNING) {
                                try {
                                    sim.ignitePlant(r, c);
                                } catch (Exception ex) {
                                    System.out.println(ex.getMessage());
                                }
                            } else {
                                veg.setState(newState);
                            }
                            forestDisplay.getRects()[r][c].setFill(veg.displayColor());
                        }
                    }
                    updatePropertiesPanel(grid, sim);
                }
            });
            panel.getChildren().addAll(stateLabel, stateBox, stateBtn);
        }

        if (allTrees) {
            Pane sepTree = new Pane();
            sepTree.getStyleClass().add("sidebar-separator");
            panel.getChildren().add(sepTree);

            Label treeTypeLabel = new Label("Modify Tree Species:");
            treeTypeLabel.getStyleClass().add("properties-label");

            ChoiceBox<TreeSpecies> treeTypeBox = new ChoiceBox<>();
            treeTypeBox.getItems().addAll(TreeSpecies.values());
            treeTypeBox.getStyleClass().add("custom-choice-box");
            if (!currentSpeciesStr.equals("Mixed")) {
                treeTypeBox.setValue(TreeSpecies.valueOf(currentSpeciesStr));
            }

            Button treeTypeBtn = new Button("Apply Species");
            treeTypeBtn.getStyleClass().add("forest-button");
            treeTypeBtn.setOnAction(e -> {
                TreeSpecies selectedSpecie = treeTypeBox.getValue();
                if (selectedSpecie != null) {
                    for (ForestCell cell : selectedCellObjects) {
                        if (cell instanceof Tree tree) {
                            tree.setSpecie(selectedSpecie);
                        }
                    }
                    for (String key : selectedCells) {
                        String[] parts = key.split(",");
                        int r = Integer.parseInt(parts[0]);
                        int c = Integer.parseInt(parts[1]);
                        forestDisplay.getRects()[r][c].setFill(grid[r][c].displayColor());
                    }
                    updatePropertiesPanel(grid, sim);
                }
            });
            panel.getChildren().addAll(treeTypeLabel, treeTypeBox, treeTypeBtn);
        }

        if (allGrass) {
            Pane sepGrass = new Pane();
            sepGrass.getStyleClass().add("sidebar-separator");
            panel.getChildren().add(sepGrass);

            Label grassTypeLabel = new Label("Modify Grass Type:");
            grassTypeLabel.getStyleClass().add("properties-label");

            ChoiceBox<GrassType> grassTypeBox = new ChoiceBox<>();
            grassTypeBox.getItems().addAll(GrassType.values());
            grassTypeBox.getStyleClass().add("custom-choice-box");
            if (!currentGrassTypeStr.equals("Mixed")) {
                grassTypeBox.setValue(GrassType.valueOf(currentGrassTypeStr));
            }

            Button grassTypeBtn = new Button("Apply Grass Type");
            grassTypeBtn.getStyleClass().add("forest-button");
            grassTypeBtn.setOnAction(e -> {
                GrassType selectedGrassType = grassTypeBox.getValue();
                if (selectedGrassType != null) {
                    for (ForestCell cell : selectedCellObjects) {
                        if (cell instanceof Grass grass) {
                            grass.setType(selectedGrassType);
                        }
                    }
                    for (String key : selectedCells) {
                        String[] parts = key.split(",");
                        int r = Integer.parseInt(parts[0]);
                        int c = Integer.parseInt(parts[1]);
                        forestDisplay.getRects()[r][c].setFill(grid[r][c].displayColor());
                    }
                    updatePropertiesPanel(grid, sim);
                }
            });
            panel.getChildren().addAll(grassTypeLabel, grassTypeBox, grassTypeBtn);
        }


        Pane spacer = new Pane(); spacer.setPrefHeight(15);
        Button clearBtn = new Button("Clear Selection");
        clearBtn.getStyleClass().add("forest-button");
        clearBtn.setOnAction(e -> clearSelection());
        panel.getChildren().addAll(spacer, clearBtn);

        ScrollPane scrollPanel = new ScrollPane(panel);
        scrollPanel.setFitToWidth(true);
        scrollPanel.getStyleClass().add("sidebar-scroll");

        root.setRight(scrollPanel);
    }




    /**
     * Method to open the tutorial video
     */
    public void openTutorial() {

        CompletableFuture.runAsync(() -> {
            try {
                URL videoUrl = getClass().getResource("/videos/video.mp4");

                if (videoUrl == null) {
                    System.out.println("Tutorial video not found.");
                    return;
                }

                File video = new File(videoUrl.toURI());

                if (!Desktop.isDesktopSupported()) {
                    System.out.println("Desktop not supported");
                    return;
                }

                Desktop.getDesktop().open(video);

            } catch (Exception e) {
                System.err.println("Unable to open tutorial video.");
            }
        });
    }





}