package com.example.demo.ui;

import com.example.demo.simulation.Simulation;
import com.example.demo.ui.Menu.EnvironmentMenuDisplay;
import com.example.demo.ui.Menu.NewForestPopUp;
import com.example.demo.ui.Menu.PlantMenuDisplay;
import com.example.demo.ui.Menu.WeatherMenuDisplay;
import com.example.demo.ui.actions.UIController;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import java.util.function.Consumer;

public class SidebarDisplay {

    private Simulation simulation;
    private BorderPane root;
    private Runnable refreshUI;
    private UIController controller;

    public SidebarDisplay(Simulation simulation, Runnable refreshUI, BorderPane root, UIController controller){
        this.simulation = simulation;
        this.refreshUI = refreshUI;
        this.root = root;
        this.controller = controller;
    }

    public Parent createContent() {
        VBox sidebar = new VBox();
        sidebar.getStyleClass().add("sidebar");
        sidebar.setSpacing(15);
        sidebar.setPrefWidth(240);


        // Button to create a new Forest
        Button NewForest = new Button("+  New");
        NewForest.getStyleClass().add("nav-button");
        NewForest.setOnAction(e -> {
            controller.newForestAction();
        });

        Button btnSettings = new Button("⚙  Settings");
        btnSettings.getStyleClass().add("nav-button");
        btnSettings.setOnAction(e -> System.out.println("Open Settings"));

        WeatherMenuDisplay weatherComponent = new WeatherMenuDisplay(simulation.getForest().getWeather());
        PlantMenuDisplay plantComponent = new PlantMenuDisplay();
        EnvironmentMenuDisplay environmentComponent = new EnvironmentMenuDisplay();

        Button btnCellState = new Button("🔥  Cell State");
        btnCellState.getStyleClass().add("nav-button");
        btnCellState.setOnAction(e -> System.out.println("Action: Modify fire state"));

        sidebar.getChildren().addAll(
                NewForest,
                btnSettings,
                weatherComponent.createMenu(),
                plantComponent.createMenu(),
                environmentComponent.createMenu(),
                btnCellState
        );

        return sidebar;
    }
}