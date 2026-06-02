package com.example.demo.ui;

import com.example.demo.simulation.Simulation;
import com.example.demo.ui.Menu.EnvironmentMenuDisplay;
import com.example.demo.ui.Menu.PlantMenuDisplay;
import com.example.demo.ui.Menu.WeatherMenuDisplay;
import com.example.demo.ui.actions.UIController;
import com.example.demo.ui.editor.Editor;
import com.example.demo.ui.editor.tools.IgniteTool;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.layout.StackPane;

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
        PlantMenuDisplay plantComponent = new PlantMenuDisplay();
        EnvironmentMenuDisplay environmentComponent = new EnvironmentMenuDisplay();

        Button btnCellState = new Button("🔥  Ignite Plant");
        btnCellState.getStyleClass().add("nav-button");
        btnCellState.setOnAction(e -> {
            Editor.setCurrentTool(new IgniteTool());
            System.out.println("Ignite selected");
        });

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
                plantComponent.createMenu(),
                environmentComponent.createMenu(),
                separator(),
                btnCellState,
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