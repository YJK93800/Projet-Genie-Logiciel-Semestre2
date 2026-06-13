package com.example.demo.ui.Menu;

import com.example.demo.ui.actions.UIController;
import com.example.demo.ui.editor.Editor;
import com.example.demo.ui.editor.tools.SoilTool;
import com.example.demo.ui.editor.tools.WaterTool;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class NewForestDisplay extends AbstractMenuDisplay{

    private UIController controller;

    public NewForestDisplay(UIController controller) {
        super("Create Forest");
        this.controller = controller;
    }

    /**
     * Creates the folding menu for the New Forest section
     *
     * @param subMenuContainer
     */
    @Override
    protected void buildSubMenuContent(VBox subMenuContainer) {


        // Button to create the forest Manually
        Button btnManual = new Button("Manual Creation");
        btnManual.getStyleClass().add("nav-button");
        btnManual.setOnAction(e ->
                {
                    controller.newForestAction();
                    System.out.println("Manually Creating the Forest");
                }
        );

        // Button to import a picture to create the forest
        Button btnImage = new Button("Import picture");
        btnImage.getStyleClass().add("nav-button");
        btnImage.setOnAction(e ->
                {
                    controller.newForestPictureAction();
                    System.out.println("Importing Picture");
                }
        );


        subMenuContainer.getChildren().addAll(
                btnManual,
                btnImage
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
