package com.example.demo.ui;

import com.example.demo.simulation.Simulation;
import com.example.demo.ui.Menu.WeatherMenuDisplay;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import java.util.function.Consumer;

public class SidebarDisplay {

    private Simulation simulation;
    private Runnable refreshUI;

    public SidebarDisplay(Simulation simulation, Runnable refreshUI){
        this.simulation = simulation;
        this.refreshUI = refreshUI;
    }

    public Parent createContent() {
        VBox sidebar = new VBox();
        sidebar.getStyleClass().add("sidebar");
        sidebar.setSpacing(15);
        sidebar.setPrefWidth(240);

        Button btnSettings = new Button("⚙  Settings");
        btnSettings.getStyleClass().add("nav-button");
        btnSettings.setOnAction(e -> System.out.println("Open Settings"));

        WeatherMenuDisplay weatherComponent = new WeatherMenuDisplay();

        Button btnPlant = new Button("🌱  Plant Type");
        btnPlant.getStyleClass().add("nav-button");
        btnPlant.setOnAction(e -> System.out.println("Action: Change vegetation type"));

        Button btnCellState = new Button("🔥  Cell State");
        btnCellState.getStyleClass().add("nav-button");
        btnCellState.setOnAction(e -> System.out.println("Action: Modify fire state"));

        Button nextTurn = new Button("=> Next Turn");
        nextTurn.getStyleClass().add("nav-button");
        nextTurn.setOnAction(e -> {
            this.simulation.spreadFireOneTurn();
            this.refreshUI.run();
        });

        sidebar.getChildren().addAll(
                btnSettings,
                weatherComponent.createMenu(),
                btnPlant,
                btnCellState,
                nextTurn
        );

        return sidebar;
    }
}