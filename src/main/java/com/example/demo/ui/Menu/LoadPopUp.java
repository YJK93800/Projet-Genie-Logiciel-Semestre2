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

/**
 * Pop up Window used to load or delete an existing save
 * <p>
 * @version 21.0.8
 */

public class LoadPopUp {

    private Consumer<String> onSelect;

    /**
     * Constructor Method
     *
     * @param onSelect Consumer called with the chosen save name when the user selects one
     */
    public LoadPopUp(Consumer<String> onSelect) {
        this.onSelect = onSelect;
    }

    /**
     * Method that creates a Pop-up window showing the list of existing saves
     */
    public void open() {

        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Load");

        VBox layout = new VBox(10);
        layout.getStyleClass().add("popup-container");
        layout.setAlignment(Pos.CENTER_LEFT);

        Label titleLabel = new Label("Choose a save");
        titleLabel.getStyleClass().add("popup-label");
        layout.getChildren().add(titleLabel);

        buildList(layout, popup);

        Scene scene = new Scene(layout, 280, 320);
        String css = getClass().getResource("/style.css").toExternalForm();
        scene.getStylesheets().add(css);

        popup.setScene(scene);
        popup.showAndWait();
    }

    /**
     * Method that builds the list of saves with a load button and a delete button on each row
     *
     * @param layout VBox value, the container in which the rows are added
     * @param popup Stage value, the pop up window, closed when a save is loaded
     */
    private void buildList(VBox layout, Stage popup) {

        ArrayList<String> saves = SaveManager.getSaveNames();

        if (saves.isEmpty()) {
            Label empty = new Label("No save found");
            empty.getStyleClass().add("popup-label");
            layout.getChildren().add(empty);
            return;
        }

        for (String name : saves) {

            Button loadBtn = new Button(name);
            loadBtn.getStyleClass().add("forest-button");
            loadBtn.setMaxWidth(Double.MAX_VALUE);
            HBox.setHgrow(loadBtn, Priority.ALWAYS);
            loadBtn.setOnAction(e -> {
                popup.close();
                onSelect.accept(name);
            });

            Button deleteBtn = new Button("X");
            deleteBtn.getStyleClass().add("forest-button");
            deleteBtn.setOnAction(e -> {
                SaveManager.delete(name);
                layout.getChildren().clear();
                Label titleLabel = new Label("Choose a save");
                titleLabel.getStyleClass().add("popup-label");
                layout.getChildren().add(titleLabel);
                buildList(layout, popup);
            });

            HBox row = new HBox(8, loadBtn, deleteBtn);
            row.setAlignment(Pos.CENTER_LEFT);

            layout.getChildren().add(row);
        }
    }
}