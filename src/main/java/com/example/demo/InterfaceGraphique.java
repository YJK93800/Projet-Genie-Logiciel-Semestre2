package com.example.demo;

import com.example.demo.model.Forest;
import com.example.demo.model.Weather.CardinalDirections;
import com.example.demo.model.Weather.Weather;
import com.example.demo.model.Weather.WeatherType;
import com.example.demo.model.Weather.Wind;
import com.example.demo.ui.ForestDisplay;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javafx.scene.control.TextArea;
import java.io.IOException;

public class InterfaceGraphique extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        Weather weather = new Weather(WeatherType.SUNNY, new Wind(CardinalDirections.EAST, 100), 30.5, 50.9, 86.25);
        Forest forest = new Forest("forestTest.txt", weather);

        ForestDisplay view = new ForestDisplay(forest);

        Scene scene = new Scene(view.createContent(), 800, 600);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}