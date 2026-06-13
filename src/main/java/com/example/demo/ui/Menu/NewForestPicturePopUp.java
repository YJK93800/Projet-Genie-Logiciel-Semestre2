package com.example.demo.ui.Menu;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.geometry.Pos;

import javafx.stage.FileChooser;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

/**
 * Pop up Window during the creation of a new Forest from a picture
 * <p>
 * @author Adem Gluntz IN1 GI1
 * @version 21.0.8
 */

public class NewForestPicturePopUp {
    private Runnable onFinish;
    public static BufferedImage selectedImage;

    public NewForestPicturePopUp(Runnable onFinish) {
        this.onFinish = onFinish;
    }

    /**
     * Method that creates a Pop-up window that inputs a picture
     */
    public void open() {

        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("New Forest");

        Label imageLabel = new Label("Current image");
        imageLabel.getStyleClass().add("popup-label");

        TextField imagePathField = new TextField();
        imagePathField.getStyleClass().add("custom-text-field");
        imagePathField.setEditable(false);

        Button browseBtn = new Button("Browse");
        browseBtn.getStyleClass().add("forest-button");

        final BufferedImage[] tempImage = new BufferedImage[1];

        browseBtn.setOnAction(e -> {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Select an image");
            chooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter(
                            "Images",
                            "*.png", "*.jpg", "*.jpeg", "*.bmp", "*.webp"
                    )
            );

            File file = chooser.showOpenDialog(popup);

            if (file != null) {
                try {
                    tempImage[0] = ImageIO.read(file);
                    imagePathField.setText(file.getName());
                } catch (IOException ex) {
                    ex.printStackTrace();
                    return;
                }

            }
        });

        Button createBtn = new Button("Create");
        createBtn.getStyleClass().add("forest-button");
        createBtn.setMaxWidth(Double.MAX_VALUE);

        createBtn.setOnAction(e -> {

            if (tempImage[0] == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(null);
                alert.setContentText("Please select an image.");
                alert.showAndWait();
                return;
            }

            selectedImage = tempImage[0];

            popup.close();

            if (onFinish != null) {
                onFinish.run();
            }
        });

        VBox layout = new VBox(10,
                imageLabel,
                imagePathField,
                browseBtn,
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
