package com.example.demo.ui.Menu;

import com.example.demo.model.Forest;
import com.example.demo.model.cells.*;
import com.example.demo.simulation.Simulation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Displays the statistics of the simulation
 */
public class Statistics {

    private Simulation simulation;


    /**
     * Constructor Method
     *
     * @param simulation the simulation being displayed
     */
    public Statistics(Simulation simulation) {
        this.simulation = simulation;
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

        VBox chart3Box = new VBox();
        chart3Box.getStyleClass().add("statistics-card");
        chart3Box.setAlignment(Pos.CENTER);
        chart3Box.getChildren().add(buildProgressionLineChart());

        root.getChildren().addAll(chart1Box, chart2Box, chart3Box);

        Scene scene = new Scene(root, 1600, 600);
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

        Forest forest = this.simulation.getForest();

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
        Forest forest = this.simulation.getForest();
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

    /**
     * Creates a line chart diagram of the evolution of the fire
     *
     * @return self-explanatory
     */

    private LineChart<Number, Number> buildProgressionLineChart() {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Turn");
        yAxis.setLabel("Plants");

        LineChart<Number, Number> chart = new LineChart<>(xAxis, yAxis);
        chart.setTitle("Fire Progression");

        XYChart.Series<Number, Number> aliveSeries   = new XYChart.Series<>();
        XYChart.Series<Number, Number> burningSeries = new XYChart.Series<>();
        XYChart.Series<Number, Number> deadSeries    = new XYChart.Series<>();

        aliveSeries.setName("Alive");
        burningSeries.setName("Burning");
        deadSeries.setName("Dead");

        ArrayList<int[]> history = this.simulation.getTurnHistory();
        for (int t = 0; t < history.size(); t++) {
            int[] snapshot = history.get(t);
            aliveSeries.getData().add(new XYChart.Data<>(t + 1, snapshot[0]));
            burningSeries.getData().add(new XYChart.Data<>(t + 1, snapshot[1]));
            deadSeries.getData().add(new XYChart.Data<>(t + 1, snapshot[2]));
        }

        chart.getData().addAll(aliveSeries, burningSeries, deadSeries);
        return chart;
    }
}