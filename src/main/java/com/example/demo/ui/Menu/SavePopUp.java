package com.example.demo.ui.Menu;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.function.Consumer;

public class SavePopUp {

    private Consumer<String> onConfirm;

    public SavePopUp(Consumer<String> onConfirm) {
        this.onConfirm = onConfirm;
    }

    public void open() {

        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Save");

        TextField nameField = new TextField();

        Button saveBtn = new Button("Save");
        saveBtn.setOnAction(e -> {
            String name = nameField.getText();
            if (name != null && !name.isEmpty()) {
                popup.close();
                onConfirm.accept(name);
            }
        });

        VBox layout = new VBox(10,
                new Label("Save name"),
                nameField,
                saveBtn
        );

        popup.setScene(new Scene(layout, 250, 150));
        popup.showAndWait();
    }
}