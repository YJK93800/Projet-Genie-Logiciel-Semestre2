package com.example.demo.ui.actions;

import com.example.demo.ui.Menu.NewForestPopUp;
import com.example.demo.ui.editor.Editor;
import com.example.demo.ui.editor.tools.FillCell;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Class in charge of the UI actions
 * <p>
 * @author Yann Kong IN1 GI1
 * @version 21.0.8
 */

public class UIController {

    private BorderPane root;

    public UIController(BorderPane root) {
        this.root = root;
    }

    /**
     * Method to Create a temporary grid to fill up
     * Uses the width and height input from the NewForestPopUp
     */

    public void newForestAction() {

        NewForestPopUp dialog = new NewForestPopUp(() -> {

            int w = NewForestPopUp.width;
            int h = NewForestPopUp.height;

            GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);

            for (int i = 0; i < h; i++) {
                for (int j = 0; j < w; j++) {

                    Rectangle rect = new Rectangle(20, 20);
                    rect.setFill(Color.LIGHTGRAY);
                    rect.setStroke(Color.BLACK);
                    StackPane tile = new StackPane(rect);

                    tile.setOnDragDetected(e -> {
                        tile.startFullDrag();
                    });
                    tile.setOnMouseDragEntered(e -> {
                        rect.setFill(Editor.getCurrentColor());
                    });
                    tile.setOnMouseClicked(e ->
                            rect.setFill(Editor.getCurrentColor())
                    );
                    grid.add(tile, j, i);
                }
            }

            StackPane center = new StackPane(grid);
            center.setAlignment(Pos.CENTER);

            root.setCenter(center);
        });

        dialog.open();
    }

}