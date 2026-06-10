package com.example.demo.ui.Menu;

import com.example.demo.simulation.SaveManager;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.function.Consumer;

public class LoadPopUp {

    private Consumer<String> onSelect;

    public LoadPopUp(Consumer<String> onSelect) {
        this.onSelect = onSelect;
    }

    public void open() {

        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Load");

        VBox layout = new VBox(10);
        layout.getChildren().add(new Label("Choose a save"));

        buildList(layout, popup);

        popup.setScene(new Scene(layout, 280, 300));
        popup.showAndWait();
    }

    // Builds the list of saves with a load button and a delete button on each row
    private void buildList(VBox layout, Stage popup) {

        ArrayList<String> saves = SaveManager.getSaveNames();

        if (saves.isEmpty()) {
            layout.getChildren().add(new Label("No save found"));
            return;
        }

        for (String name : saves) {

            Button loadBtn = new Button(name);
            loadBtn.setOnAction(e -> {
                popup.close();
                onSelect.accept(name);
            });
            HBox.setHgrow(loadBtn, Priority.ALWAYS);
            loadBtn.setMaxWidth(Double.MAX_VALUE);

            Button deleteBtn = new Button("X");
            deleteBtn.setOnAction(e -> {
                SaveManager.delete(name);
                // rebuild the list after deleting
                layout.getChildren().clear();
                layout.getChildren().add(new Label("Choose a save"));
                buildList(layout, popup);
            });

            HBox row = new HBox(8, loadBtn, deleteBtn);
            row.setAlignment(Pos.CENTER_LEFT);

            layout.getChildren().add(row);
        }
    }
}