package com.example.demo.ui.Menu;

import com.example.demo.model.Weather.CardinalDirections;
import com.example.demo.model.Weather.WeatherType;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class WeatherMenuDisplay extends AbstractMenuDisplay {

    public WeatherMenuDisplay() {
        super("🌤  Weather");
    }

    @Override
    protected void buildSubMenuContent(VBox subMenuContainer) {
        HBox tempRow = createInputField("Temperature (°C):", "25.0");
        TextField tempField = (TextField) tempRow.getChildren().get(1);
        tempField.setOnAction(e -> System.out.println("Terminal -> Temperature updated: " + tempField.getText() + "°C"));

        HBox humidityRow = createInputField("Humidity (%):", "50.0");
        TextField humidityField = (TextField) humidityRow.getChildren().get(1);
        humidityField.setOnAction(e -> System.out.println("Terminal -> Humidity updated: " + humidityField.getText() + "%"));

        HBox sunRow = createInputField("Sun Intensity:", "86.25");
        TextField sunField = (TextField) sunRow.getChildren().get(1);
        sunField.setOnAction(e -> System.out.println("Terminal -> Sun Intensity updated: " + sunField.getText()));

        HBox windSpeedRow = createInputField("Wind Speed:", "100.0");
        TextField windSpeedField = (TextField) windSpeedRow.getChildren().get(1);
        windSpeedField.setOnAction(e -> System.out.println("Terminal -> Wind Speed updated: " + windSpeedField.getText() + " km/h"));

        HBox typeRow = createDropdownField("Weather Type:", WeatherType.values(), WeatherType.SUNNY);
        @SuppressWarnings("unchecked")
        ChoiceBox<WeatherType> typeChoice = (ChoiceBox<WeatherType>) typeRow.getChildren().get(1);
        typeChoice.setOnAction(e -> System.out.println("Terminal -> Selected weather type: " + typeChoice.getValue()));

        HBox directionRow = createDropdownField("Wind Direction:", CardinalDirections.values(), CardinalDirections.EAST);
        @SuppressWarnings("unchecked")
        ChoiceBox<CardinalDirections> directionChoice = (ChoiceBox<CardinalDirections>) directionRow.getChildren().get(1);
        directionChoice.setOnAction(e -> System.out.println("Terminal -> Selected wind direction: " + directionChoice.getValue()));

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