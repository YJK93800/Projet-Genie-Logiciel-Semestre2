package com.example.demo.ui;

import com.example.demo.model.Forest;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ForestDisplay {

    private Forest forest;

    public ForestDisplay(Forest forest) {
        this.forest = forest;
    }

    public Parent createContent() {

        Label forestLabel = new Label(forest.toString());

        Button button = new Button("Afficher / Cacher");

        button.setOnAction(e ->
                forestLabel.setVisible(!forestLabel.isVisible())
        );

        VBox layout = new VBox(20, forestLabel, button);

        layout.setAlignment(Pos.CENTER);

        return layout;
    }
}