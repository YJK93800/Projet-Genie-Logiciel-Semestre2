package com.example.demo.ui.Menu;

import com.example.demo.model.cells.TreeSpecies;
import com.example.demo.ui.Editor;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import static com.example.demo.ui.EditorMode.*;

/**
 * Menu display of the environmental elements of a forest
 * <p>
 * @author Yann Kong IN1 GI1
 * @version 21.0.8
 */

public class EnvironmentMenuDisplay extends AbstractMenuDisplay {

    public EnvironmentMenuDisplay() {
        super("Environment");
    }

    @Override
    protected void buildSubMenuContent(VBox subMenuContainer) {


        // Water Button
        Button waterButton = new Button("Water");
        waterButton.getStyleClass().add("nav-button");
        waterButton.setOnAction(e ->
                {
                    Editor.switchMode(WATER);
                    System.out.println("Water selected");
                }
        );

        // Soil Button
        Button soilButton = new Button("Soil");
        soilButton.getStyleClass().add("nav-button");
        soilButton.setOnAction(e ->
                {
                    Editor.switchMode(SOIL);
                    System.out.println("Soil selected");
                }
        );


        subMenuContainer.getChildren().addAll(
                waterButton,
                soilButton
        );
    }

    private HBox createSimpleLabel(String text) {

        HBox row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);
        row.setSpacing(10);

        Label label = new Label(text);

        row.getChildren().add(label);

        return row;
    }

    private <T> HBox createDropdownField(String labelText, T[] enumValues, T defaultValue) {

        HBox row = new HBox();

        row.setAlignment(Pos.CENTER_LEFT);
        row.setSpacing(10);

        Label label = new Label(labelText);
        label.setPrefWidth(120);

        ChoiceBox<T> choiceBox = new ChoiceBox<>();

        choiceBox.getItems().addAll(enumValues);
        choiceBox.setValue(defaultValue);

        row.getChildren().addAll(label, choiceBox);

        return row;
    }

}

