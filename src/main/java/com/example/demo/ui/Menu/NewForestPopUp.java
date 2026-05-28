package com.example.demo.ui.Menu;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


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

        TextField widthField = new TextField();
        TextField heightField = new TextField();

        Button createBtn = new Button("Create");

        createBtn.setOnAction(e -> {

            width = Integer.parseInt(widthField.getText());
            height = Integer.parseInt(heightField.getText());

            popup.close();

            if (onFinish != null) {
                onFinish.run();
            }
        });

        VBox layout = new VBox(10,
                new Label("Width"),
                widthField,
                new Label("Height"),
                heightField,
                createBtn
        );

        popup.setScene(new Scene(layout, 250, 200));
        popup.showAndWait();
    }

}