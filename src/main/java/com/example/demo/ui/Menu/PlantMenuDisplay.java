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

public class PlantMenuDisplay extends AbstractMenuDisplay {

    public PlantMenuDisplay() {
        super("🌱 Plant Type");
    }

    @Override
    protected void buildSubMenuContent(VBox subMenuContainer) {


        // Grass Button
        Button grassButton = new Button("Grass");
        grassButton.getStyleClass().add("nav-button");
        grassButton.setOnAction(e ->
                {
                    Editor.switchMode(GRASS);
                    System.out.println("Grass selected");
                }
        );

        // Tree Button
        Button TreeButton = new Button("Tree");
        TreeButton.getStyleClass().add("nav-button");
        TreeButton.setOnAction(e ->
                {
                    Editor.switchMode(TREE);
                    System.out.println("Tree selected");
                }
        );

        // Tree Choice
        ChoiceBox<TreeSpecies> treeChoice = new ChoiceBox<>();
        treeChoice.getItems().addAll(TreeSpecies.values());
        treeChoice.setValue(TreeSpecies.OAK);
        treeChoice.getStyleClass().add("custom-choice-box");
        treeChoice.setOnAction(e ->
                System.out.println(treeChoice.getValue())
        );

        HBox treeRow = new HBox();
        treeRow.setSpacing(10);
        treeRow.setAlignment(Pos.CENTER_LEFT);

        Label treeLabel = new Label("Tree:");
        treeLabel.getStyleClass().add("sub-menu-label");

        treeRow.getChildren().addAll(treeLabel, treeChoice);

        subMenuContainer.getChildren().addAll(
                grassButton,
                TreeButton,
                treeRow
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