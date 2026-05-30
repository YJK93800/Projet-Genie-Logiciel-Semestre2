package com.example.demo.ui;

import com.example.demo.simulation.Simulation;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class StatsDisplay {

    private Simulation simulation;
    private Label lblTurn;
    private Label lblAlive;
    private Label lblBurning;

    public StatsDisplay(Simulation simulation) {
        this.simulation = simulation;
    }

    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    public Parent createContent() {
        HBox statsPanel = new HBox(25);
        statsPanel.getStyleClass().add("stats-panel");
        statsPanel.setAlignment(Pos.CENTER_RIGHT);
        statsPanel.setPadding(new Insets(12, 24, 12, 24));

        lblTurn = new Label("Turn: " + simulation.getTurn());
        lblAlive = new Label("Alive Plants: " + simulation.getAlivePlants().size());
        lblBurning = new Label("Burning Plants: " + simulation.getBurningPlants().size());

        lblTurn.getStyleClass().add("stats-label");
        lblAlive.getStyleClass().add("stats-label");
        lblBurning.getStyleClass().add("stats-label");

        statsPanel.getChildren().addAll(lblTurn, lblAlive, lblBurning);
        return statsPanel;
    }

    public void refresh() {
        if (lblTurn != null) {
            lblTurn.setText("Turn: " + simulation.getTurn());
            lblAlive.setText("Alive Plants: " + simulation.getAlivePlants().size());
            lblBurning.setText("Burning Plants: " + simulation.getBurningPlants().size());
        }
    }
}