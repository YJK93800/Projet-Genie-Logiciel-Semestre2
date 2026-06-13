package com.example.demo.ui;

import com.example.demo.simulation.Simulation;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * Handles the general statistics of the simulation
 */

public class StatsDisplay {

    private Simulation simulation;
    private Label lblTurn;
    private Label lblAlive;
    private Label lblBurning;

    /**
     * Constructor Method
     * @param simulation the current simulation being displayed
     */
    public StatsDisplay(Simulation simulation) {
        this.simulation = simulation;
    }

    /**
     * Setter method for the simulation attribute
     *
     * @param simulation the current simulation being played
     */
    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    /**
     * Creates the box containing all the general statistics
     *
     * @return Parent instance containing all the elements beings displayed
     */

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

    /**
     * Refreshes the current generals stats to the actual ones
     */
    public void refresh() {
        if (lblTurn != null) {
            lblTurn.setText("Turn: " + simulation.getTurn());
            lblAlive.setText("Alive Plants: " + simulation.getAlivePlants().size());
            lblBurning.setText("Burning Plants: " + simulation.getBurningPlants().size());
        }
    }
}