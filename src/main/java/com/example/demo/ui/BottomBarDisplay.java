package com.example.demo.ui;

import com.example.demo.simulation.Simulation;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class BottomBarDisplay {

    private Simulation simulation;
    private Runnable refreshUI;

    public BottomBarDisplay(Simulation simulation, Runnable refreshUI) {
        this.simulation = simulation;
        this.refreshUI = refreshUI;
    }

    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    public Parent createContent() {

        HBox bottomBar = new HBox();
        bottomBar.getStyleClass().add("bottom-bar");
        bottomBar.setAlignment(Pos.CENTER_RIGHT);
        bottomBar.setSpacing(15);
        bottomBar.setPadding(new Insets(10, 20, 10, 20));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button nextTurn = new Button("=> Next Turn");
        nextTurn.getStyleClass().add("nav-button");
        nextTurn.setOnAction(e -> {
            this.simulation.spreadFireOneTurn();
            this.refreshUI.run();
        });

        bottomBar.getChildren().addAll(spacer, nextTurn);

        return bottomBar;
    }
}