package com.example.demo.ui.Menu;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.function.Consumer;

/**
 * Pop up Window used to save a simulation under a chosen name
 * <p>
 * @version 21.0.8
 */

public class SavePopUp {

    private Consumer<String> onConfirm;

    /**
     * Constructor Method
     *
     * @param onConfirm Consumer called with the chosen name when the user confirms
     */
    public SavePopUp(Consumer<String> onConfirm) {
        this.onConfirm = onConfirm;
    }

    /**
     * Method that creates a Pop-up window asking for the name of the save
     */
    public void open() {

        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Save");

        Label nameLabel = new Label("Save name");
        nameLabel.getStyleClass().add("popup-label");

        TextField nameField = new TextField();
        nameField.getStyleClass().add("custom-text-field");

        Button saveBtn = new Button("Save");
        saveBtn.getStyleClass().add("forest-button");
        saveBtn.setMaxWidth(Double.MAX_VALUE);
        saveBtn.setOnAction(e -> {
            String name = nameField.getText();
            if (name != null && !name.isEmpty()) {
                popup.close();
                onConfirm.accept(name);
            }
        });

        VBox layout = new VBox(10,
                nameLabel,
                nameField,
                saveBtn
        );

        layout.getStyleClass().add("popup-container");
        layout.setAlignment(Pos.CENTER_LEFT);

        Scene scene = new Scene(layout, 260, 170);
        String css = getClass().getResource("/style.css").toExternalForm();
        scene.getStylesheets().add(css);

        popup.setScene(scene);
        popup.showAndWait();
    }
}