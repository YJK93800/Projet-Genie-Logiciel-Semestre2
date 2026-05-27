package com.example.demo;

import com.example.demo.model.Forest;
import com.example.demo.model.Weather.CardinalDirections;
import com.example.demo.model.Weather.Weather;
import com.example.demo.model.Weather.WeatherType;
import com.example.demo.model.Weather.Wind;
import com.example.demo.simulation.Simulation;
import com.example.demo.ui.ForestDisplay;
import com.example.demo.ui.SidebarDisplay;
import com.example.demo.ui.actions.UIController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;

public class MainInterface extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        Weather weather = new Weather(WeatherType.SUNNY, new Wind(CardinalDirections.NEUTRAL, 100), 30.5, 50.9, 86.25);
        Forest forest = new Forest("forestTest.txt", weather);
        Simulation simulation = new Simulation(forest);
        simulation.ignitePlant(0,0);

        ForestDisplay view = new ForestDisplay(simulation.getForest());
        BorderPane root = new BorderPane();
        UIController controller = new UIController(root);

        SidebarDisplay sidebar = new SidebarDisplay(simulation, () -> {
            view.update();
        }, root, controller);

        root.setLeft(sidebar.createContent());
        root.setCenter(view.createContent());

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