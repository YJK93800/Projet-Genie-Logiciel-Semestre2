package com.example.demo.ui.Menu;

import com.example.demo.model.Forest;
import com.example.demo.model.cells.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

/**
 * Displays the statistics of the simulation
 */
public class Statistics {

    private Forest forest;


    /**
     * Constructor Method
     *
     * @param forest the forest tied to the simulation
     */
    public Statistics(Forest forest) {
        this.forest = forest;
    }

    /**
     * Creates the window containing the statistics
     *
     */
    public void open() {
        Stage stage = new Stage();
        stage.setTitle("Statistics");

        HBox root = new HBox(20);
        root.getStyleClass().add("statistics-root");
        root.setAlignment(Pos.CENTER);

        VBox chart1Box = new VBox();
        chart1Box.getStyleClass().add("statistics-card");
        chart1Box.setAlignment(Pos.CENTER);
        chart1Box.getChildren().add(buildCellTypePieChart());

        VBox chart2Box = new VBox();
        chart2Box.getStyleClass().add("statistics-card");
        chart2Box.setAlignment(Pos.CENTER);
        chart2Box.getChildren().add(buildVegetationStatePieChart());

        root.getChildren().addAll(chart1Box, chart2Box);

        Scene scene = new Scene(root, 1200, 600);


        scene.getStylesheets().add(
                getClass().getResource("/style.css").toExternalForm()
        );

        stage.setScene(scene);
        stage.show();
    }

    /**
     * Creates a pie chart of the repartition of the different type of cells in the grid
     *
     * @return
     */
    private PieChart buildCellTypePieChart() {
        int water = 0, soil = 0, grass = 0;
        Map<String, Integer> treeCount = new HashMap<>();

        ForestCell[][] grid = forest.getForestGrid();

        for (ForestCell[] row : grid) {
            for (ForestCell cell : row) {
                if (cell instanceof Tree tree) {
                    String specie = tree.getSpecie().toString();
                    treeCount.merge(specie, 1, Integer::sum);
                } else if (cell instanceof Grass) {
                    grass++;
                } else if (cell instanceof BodyOfWater) {
                    water++;
                } else if (cell instanceof Soil) {
                    soil++;
                }
            }
        }

        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();

        int total = water + soil + grass + treeCount.values().stream().mapToInt(Integer::intValue).sum();

        if (water > 0) data.add(new PieChart.Data(String.format("Water (%.1f%%)", water * 100.0 / total), water));
        if (soil  > 0) data.add(new PieChart.Data(String.format("Soil (%.1f%%)",  soil  * 100.0 / total), soil));
        if (grass > 0) data.add(new PieChart.Data(String.format("Grass (%.1f%%)", grass * 100.0 / total), grass));

        for (Map.Entry<String, Integer> entry : treeCount.entrySet()) {
            data.add(new PieChart.Data(String.format("%s (%.1f%%)", entry.getKey(), entry.getValue() * 100.0 / total), entry.getValue()));
        }

        PieChart chart = new PieChart(data);
        chart.setTitle("Cell Type Distribution");
        return chart;
    }

    /**
     * Creates the pie chart of the repartition of the different state of each plants
     *
     * @return
     */
    private PieChart buildVegetationStatePieChart() {
        int alive = 0, burning = 0, dead = 0;

        ForestCell[][] grid = forest.getForestGrid();

        for (ForestCell[] row : grid) {
            for (ForestCell cell : row) {
                if (cell instanceof Vegetation veg) {
                    switch (veg.getState()) {
                        case ALIVE:
                            alive += 1;
                            break;
                        case BURNING:
                            burning += 1;
                            break;
                        case DEAD:
                            dead += 1;
                            break;
                    }
                }
            }
        }

        int total = alive + burning + dead;

        ObservableList<PieChart.Data> data = FXCollections.observableArrayList();

        if (alive   > 0) data.add(new PieChart.Data(String.format("Alive (%.1f%%)",   alive   * 100.0 / total), alive));
        if (burning > 0) data.add(new PieChart.Data(String.format("Burning (%.1f%%)", burning * 100.0 / total), burning));
        if (dead    > 0) data.add(new PieChart.Data(String.format("Dead (%.1f%%)",    dead    * 100.0 / total), dead));

        PieChart chart = new PieChart(data);
        chart.setTitle("Vegetation State Distribution");
        return chart;
    }
}