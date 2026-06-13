package com.example.demo.ui.Menu;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.geometry.Pos;



/**
 * Pop up Window during the creation of a new Forest
 * <p>
 * @author Yann Kong IN1 GI1
 * @version 21.0.8
 */

public class NewForestPopUp {

    private Runnable onFinish;
    public static int width;
    public static int height;

    public NewForestPopUp(Runnable onFinish) {
        this.onFinish = onFinish;
    }

    /**
     * Method that creates a Pop-up window that inputs the dimensions of the new grid
     */
    public void open() {

        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("New Forest");

        Label widthLabel = new Label("Width");
        widthLabel.getStyleClass().add("popup-label");

        Label heightLabel = new Label("Height");
        heightLabel.getStyleClass().add("popup-label");

        TextField widthField = new TextField();
        widthField.getStyleClass().add("custom-text-field");

        TextField heightField = new TextField();
        heightField.getStyleClass().add("custom-text-field");

        Button createBtn = new Button("Create");
        createBtn.getStyleClass().add("forest-button");
        createBtn.setMaxWidth(Double.MAX_VALUE);

        Label errorLabel = new Label("Wrong type of inputs");
        errorLabel.getStyleClass().add("error-label");
        errorLabel.setVisible(false);

        createBtn.setOnAction(e -> {
            try {
                width = Integer.parseInt(widthField.getText());
                height = Integer.parseInt(heightField.getText());

                popup.close();

                if (onFinish != null) {
                    onFinish.run();
                }
            } catch (NumberFormatException ex) {
                widthField.setStyle("-fx-border-color: red;");
                heightField.setStyle("-fx-border-color: red;");
                errorLabel.setVisible(true);
            }
        });

        VBox layout = new VBox(10,
                widthLabel,
                widthField,
                heightLabel,
                heightField,
                errorLabel,
                createBtn
        );

        layout.getStyleClass().add("popup-container");
        layout.setAlignment(Pos.CENTER_LEFT);

        Scene scene = new Scene(layout, 260, 230);
        String css = getClass().getResource("/style.css").toExternalForm();
        scene.getStylesheets().add(css);

        popup.setScene(scene);
        popup.showAndWait();
    }

}