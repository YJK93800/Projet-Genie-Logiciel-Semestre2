package com.example.demo.ui;

import com.example.demo.simulation.SaveManager;
import com.example.demo.simulation.Simulation;
import com.example.demo.ui.Menu.LoadPopUp;
import com.example.demo.ui.Menu.SavePopUp;
import com.example.demo.ui.actions.UIController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class BottomBarDisplay {

    private Simulation simulation;
    private Runnable refreshUI;
    private UIController controller;

    public BottomBarDisplay(Simulation simulation, Runnable refreshUI, UIController controller) {
        this.simulation = simulation;
        this.refreshUI = refreshUI;
        this.controller = controller;
    }

    public Parent createContent() {

        HBox bottomBar = new HBox();
        bottomBar.getStyleClass().add("bottom-bar");
        bottomBar.setAlignment(Pos.CENTER_RIGHT);
        bottomBar.setSpacing(15);
        bottomBar.setPadding(new Insets(10, 20, 10, 20));

        Button save = new Button("Save");
        save.getStyleClass().add("nav-button");
        save.setOnAction(e -> {
            SavePopUp popup = new SavePopUp(name -> SaveManager.save(this.simulation, name));
            popup.open();
        });

        Button load = new Button("Load");
        load.getStyleClass().add("nav-button");
        load.setOnAction(e -> {
            LoadPopUp popup = new LoadPopUp(name -> {
                Simulation loaded = SaveManager.load(name);
                if (loaded != null) {
                    this.simulation = loaded;
                    this.controller.loadSimulation(loaded);
                    this.refreshUI.run();
                }
            });
            popup.open();
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label turnLabel = new Label("Turns :");
        turnLabel.getStyleClass().add("sub-menu-label");

        TextField turnField = new TextField("1");
        turnField.getStyleClass().add("custom-text-field");
        turnField.setPrefWidth(50);

        Button nextTurn = new Button("=> Next Turn");
        nextTurn.getStyleClass().add("nav-button");
        nextTurn.setOnAction(e -> {
            int n = Integer.parseInt(turnField.getText());
            for (int i = 0; i < n; i++) {
                this.simulation.spreadFireOneTurn();
            }
            this.refreshUI.run();
        });

        bottomBar.getChildren().addAll(save, load, spacer, turnLabel, turnField, nextTurn);

        return bottomBar;
    }

    //setter method

    /**
     * Setter method of the simulation attribute
     *
     * @param simulation the new ongoing simulation
     */

    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

}