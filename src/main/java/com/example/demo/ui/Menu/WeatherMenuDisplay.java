package com.example.demo.ui.Menu;

import com.example.demo.model.Weather.CardinalDirections;
import com.example.demo.model.Weather.Weather;
import com.example.demo.model.Weather.WeatherType;
import com.example.demo.model.Weather.Wind;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class WeatherMenuDisplay extends AbstractMenuDisplay {

    private Weather weather;

    public WeatherMenuDisplay(Weather weather) {
        super("🌤  Weather");
        this.weather = weather;
    }

    @Override
    protected void buildSubMenuContent(VBox subMenuContainer) {
        HBox tempRow = createInputField("Temperature (°C):", String.valueOf(weather.getTemperature()));
        TextField tempField = (TextField) tempRow.getChildren().get(1);
        tempField.setOnAction(e -> {
            try {
                double val = Double.parseDouble(tempField.getText());
                weather.setTemperature(val);
                System.out.println("Terminal -> Temperature updated: " + val + "°C");
            } catch (NumberFormatException ex) {
                System.out.println("Terminal -> Invalid temperature value");
            }
        });

        HBox humidityRow = createInputField("Humidity (%):", String.valueOf(weather.getHumidity()));
        TextField humidityField = (TextField) humidityRow.getChildren().get(1);
        humidityField.setOnAction(e -> {
            try {
                double val = Double.parseDouble(humidityField.getText());
                weather.setHumidity(val);
                System.out.println("Terminal -> Humidity updated: " + val + "%");
            } catch (NumberFormatException ex) {
                System.out.println("Terminal -> Invalid humidity value");
            }
        });

        HBox sunRow = createInputField("Sun Intensity:", String.valueOf(weather.getSunlightIntensity()));
        TextField sunField = (TextField) sunRow.getChildren().get(1);
        sunField.setOnAction(e -> {
            try {
                double val = Double.parseDouble(sunField.getText());
                weather.setSunlightIntensity(val);
                System.out.println("Terminal -> Sun Intensity updated: " + val);
            } catch (NumberFormatException ex) {
                System.out.println("Terminal -> Invalid sun intensity value");
            }
        });

        HBox windSpeedRow = createInputField("Wind Speed:", String.valueOf(weather.getWind().getWindSpeed()));
        TextField windSpeedField = (TextField) windSpeedRow.getChildren().get(1);
        windSpeedField.setOnAction(e -> {
            try {
                double val = Double.parseDouble(windSpeedField.getText());
                CardinalDirections currentDir = weather.getWind().getWindDirection();
                weather.setWind(new Wind(currentDir, val));
                System.out.println("Terminal -> Wind Speed updated: " + val + " km/h");
            } catch (NumberFormatException ex) {
                System.out.println("Terminal -> Invalid wind speed value");
            }
        });

        HBox typeRow = createDropdownField("Weather Type:", WeatherType.values(), weather.getWeatherType());
        @SuppressWarnings("unchecked")
        ChoiceBox<WeatherType> typeChoice = (ChoiceBox<WeatherType>) typeRow.getChildren().get(1);
        typeChoice.setOnAction(e -> {
            weather.setWeatherType(typeChoice.getValue());
            System.out.println("Terminal -> Selected weather type: " + typeChoice.getValue());
        });

        HBox directionRow = createDropdownField("Wind Direction:", CardinalDirections.values(), weather.getWind().getWindDirection());
        @SuppressWarnings("unchecked")
        ChoiceBox<CardinalDirections> directionChoice = (ChoiceBox<CardinalDirections>) directionRow.getChildren().get(1);
        directionChoice.setOnAction(e -> {
            double currentSpeed = weather.getWind().getWindSpeed();
            weather.setWind(new Wind(directionChoice.getValue(), currentSpeed));
            System.out.println("Terminal -> Selected wind direction: " + directionChoice.getValue());
        });

        subMenuContainer.getChildren().addAll(tempRow, humidityRow, sunRow, windSpeedRow, typeRow, directionRow);
    }

    private <T> HBox createDropdownField(String labelText, T[] enumValues, T defaultValue) {
        HBox row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);
        row.setSpacing(10);
        row.getStyleClass().add("input-row");

        Label label = new Label(labelText);
        label.getStyleClass().add("sub-menu-label");
        label.setPrefWidth(120);

        ChoiceBox<T> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll(enumValues);
        choiceBox.setValue(defaultValue);
        choiceBox.getStyleClass().add("custom-choice-box");
        choiceBox.setPrefWidth(90);

        row.getChildren().addAll(label, choiceBox);
        return row;
    }
}