package com.example.demo;

import com.example.demo.model.Forest;
import com.example.demo.model.Weather.*;
import com.example.demo.simulation.Simulation;
import com.example.demo.ui.BottomBarDisplay;
import com.example.demo.ui.ForestDisplay;
import com.example.demo.ui.SidebarDisplay;
import com.example.demo.ui.actions.UIController;
import com.example.demo.ui.LegendDisplay;
import com.example.demo.ui.StatsDisplay;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainInterface extends Application {

    @Override
    public void start(Stage stage) {

        Weather weather = new Weather(
                WeatherType.SUNNY,
                new Wind(CardinalDirections.SOUTH, 100),
                30.5,
                50.9,
                86.25
        );

        Forest forest = new Forest("forestTest.txt", weather);
        Simulation simulation = new Simulation(forest);
        simulation.ignitePlant(0, 0);



        BorderPane root = new BorderPane();
        ForestDisplay view = new ForestDisplay(simulation.getForest());


        final SidebarDisplay[] sidebarHolder = new SidebarDisplay[1];
        final BottomBarDisplay[] bottomBarHolder = new BottomBarDisplay[1];

        StatsDisplay statsDisplay = new StatsDisplay(simulation);
        LegendDisplay legendDisplay = new LegendDisplay();


        UIController controller = new UIController(root, view, sim -> {
            sidebarHolder[0].setSimulation(sim);
            sidebarHolder[0].refresh();
            statsDisplay.setSimulation(sim);
            statsDisplay.refresh();
            bottomBarHolder[0].setSimulation(sim);
        });

        SidebarDisplay sidebar = new SidebarDisplay(
                simulation,
                () -> {
                    view.update();
                    statsDisplay.refresh();

                },
                root,
                controller
        );

        sidebarHolder[0] = sidebar;

        BottomBarDisplay bottomBar = new BottomBarDisplay(simulation, () -> {
            view.update();
            statsDisplay.refresh();

        });

        bottomBarHolder[0] = bottomBar;

        StackPane centerStack = new StackPane();
        centerStack.getStyleClass().add("center-area");
        centerStack.getChildren().add(view.createContent());
        centerStack.getChildren().add(legendDisplay.createContent());



        root.setTop(statsDisplay.createContent());
        root.setLeft(sidebar.createContent());
        root.setCenter(centerStack);
        root.setBottom(bottomBar.createContent());

        Scene scene = new Scene(root, 1050, 700);

        String css = getClass().getResource("/style.css").toExternalForm();
        scene.getStylesheets().add(css);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}