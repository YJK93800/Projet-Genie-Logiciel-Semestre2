package com.example.demo.ui;

import com.example.demo.model.cells.ForestCell;
import com.example.demo.model.cells.Vegetation;
import com.example.demo.simulation.Simulation;
import com.example.demo.ui.Menu.Statistics;
import com.example.demo.ui.Menu.ToolsMenuDisplay;
import com.example.demo.ui.Menu.WeatherMenuDisplay;
import com.example.demo.ui.actions.UIController;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import com.example.demo.ui.Menu.NewForestDisplay;

/**
 * Manages all the buttons on the left side of the screen
 */

public class SidebarDisplay {

    private Simulation simulation;
    private BorderPane root;
    private Runnable refreshUI;
    private UIController controller;
    private VBox sidebar;

    /**
     * Constructor method
     *
     * @param simulation simulation to be displayed
     * @param refreshUI the method to refresh the simulation
     * @param root
     * @param controller the controller managing all the interactions directly with the user
     */
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

        ScrollPane scrollPane = new ScrollPane(sidebar);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.getStyleClass().add("sidebar-scroll");

        return scrollPane;
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

        NewForestDisplay newForestComponent = new NewForestDisplay(this.controller);

        Button btnSettings = new Button("⚙  Settings");
        btnSettings.getStyleClass().add("nav-button");
        btnSettings.setOnAction(e -> System.out.println("Open Settings"));

        Button btnStats = new Button("Statistics");
        btnStats.getStyleClass().add("nav-button");
        btnStats.setOnAction(e -> new Statistics(this.simulation.getForest()).open());

        WeatherMenuDisplay weatherComponent = new WeatherMenuDisplay(this.simulation.getForest().getWeather());
        ToolsMenuDisplay toolsComponent = new ToolsMenuDisplay();

        Button randomFire = new Button("🔥  Random Fire");
        randomFire.getStyleClass().add("nav-button");
        randomFire.setOnAction(e -> {
            ForestCell[][] grid = this.simulation.getForest().getForestGrid();
            boolean done = false;
            int tries = 0;
            while (!done && tries < 1000) {
                int i = (int) (Math.random() * grid.length);
                int j = (int) (Math.random() * grid[0].length);
                if (grid[i][j] instanceof Vegetation) {
                    try {
                        this.simulation.ignitePlant(i, j);
                        done = true;
                    } catch (Exception ex) {
                        // cell already burning or dead, retry
                    }
                }
                tries++;
            }
            this.refreshUI.run();
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
                newForestComponent.createMenu(),
                btnSettings,
                btnStats,
                separator(),
                weatherComponent.createMenu(),
                toolsComponent.createMenu(),
                separator(),
                randomFire,
                test,
                tutorial
        );
    }

    /**
     * Creates a thin separator line between button groups
     *
     * @return the thin separator line
     */
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