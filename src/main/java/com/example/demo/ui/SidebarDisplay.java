package com.example.demo.ui;

import com.example.demo.simulation.Simulation;
import com.example.demo.ui.Menu.ToolsMenuDisplay;
import com.example.demo.ui.Menu.WeatherMenuDisplay;
import com.example.demo.ui.actions.UIController;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class SidebarDisplay {

    private Simulation simulation;
    private BorderPane root;
    private Runnable refreshUI;
    private UIController controller;
    private VBox sidebar;

    public SidebarDisplay(Simulation simulation, Runnable refreshUI, BorderPane root, UIController controller) {
        this.simulation = simulation;
        this.refreshUI = refreshUI;
        this.root = root;
        this.controller = controller;
    }

    public Parent createContent() {
        sidebar = new VBox();
        sidebar.getStyleClass().add("sidebar");
        sidebar.setSpacing(15);
        sidebar.setPrefWidth(240);

        buildSidebarChildren();

        return sidebar;
    }

    public void refresh() {
        if (sidebar != null) {
            sidebar.getChildren().clear();
            buildSidebarChildren();
        }
    }

    private void buildSidebarChildren() {

        Label title = new Label("Forest Simulator");
        title.getStyleClass().add("sidebar-title");

        Button newForest = new Button("+  New");
        newForest.getStyleClass().add("nav-button");
        newForest.setOnAction(e -> controller.newForestAction());

        Button btnSettings = new Button("⚙  Settings");
        btnSettings.getStyleClass().add("nav-button");
        btnSettings.setOnAction(e -> System.out.println("Open Settings"));

        WeatherMenuDisplay weatherComponent = new WeatherMenuDisplay(this.simulation.getForest().getWeather());
        ToolsMenuDisplay toolsComponent = new ToolsMenuDisplay();

        Button nextTurn = new Button("=> Next Turn");
        nextTurn.getStyleClass().add("nav-button");
        nextTurn.setOnAction(e -> {
            this.simulation.spreadFireOneTurn();
            Platform.runLater(() -> this.refreshUI.run());
        });

        Button test = new Button("Test");
        test.getStyleClass().add("nav-button");
        test.setOnAction(e -> System.out.println(this.simulation.getForest()));

        Button tutorial = new Button("Tutorial");
        tutorial.getStyleClass().add("nav-button");
        tutorial.setOnAction(e -> controller.openTutorial());

        sidebar.getChildren().addAll(
                title,
                separator(),
                newForest,
                btnSettings,
                separator(),
                weatherComponent.createMenu(),
                toolsComponent.createMenu(),
                separator(),
                nextTurn,
                test,
                tutorial
        );
    }

    // Creates a thin separator line between button groups
    private Pane separator() {
        Pane line = new Pane();
        line.getStyleClass().add("sidebar-separator");
        line.setMaxWidth(Double.MAX_VALUE);
        return line;
    }

    // Setter method

    /**
     * Setter method for the simulation attribute.
     *
     * @param simulation new simulation that will be used
     */
    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }
}