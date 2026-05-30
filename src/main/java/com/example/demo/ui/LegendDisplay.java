package com.example.demo.ui;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class LegendDisplay {

    public Parent createContent() {
        StackPane container = new StackPane();
        container.setPickOnBounds(false);

        Button btnLegend = new Button("ℹ Legend");
        btnLegend.getStyleClass().add("legend-button");
        StackPane.setAlignment(btnLegend, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(btnLegend, new Insets(15));

        VBox legendBox = new VBox(8);
        legendBox.getStyleClass().add("legend-box");
        legendBox.setVisible(false);
        legendBox.setMaxSize(220, 160);
        StackPane.setAlignment(legendBox, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(legendBox, new Insets(0, 15, 60, 0));

        Label titleLegend = new Label("Simulation Legend");
        titleLegend.setStyle("-fx-font-weight: bold; -fx-text-fill: #ffffff;");


        legendBox.getChildren().addAll(
                titleLegend,
                createLegendItem(Color.FORESTGREEN, "Tree (Alive)"),
                createLegendItem(Color.LIGHTGREEN, "Grass (Alive)"),
                createLegendItem(Color.ORANGE, "Burning"),
                createLegendItem(Color.BLACK, "Burnt / Ash"),
                createLegendItem(Color.DEEPSKYBLUE, "Water"),
                createLegendItem(Color.BROWN, "Soil")
        );

        btnLegend.setOnAction(e -> legendBox.setVisible(!legendBox.isVisible()));
        container.getChildren().addAll(btnLegend, legendBox);
        return container;
    }


    private HBox createLegendItem(Color color, String text) {
        Rectangle rect = new Rectangle(15, 15);
        rect.setFill(color);
        rect.setStroke(Color.WHITE);

        Label label = new Label(text);
        label.setStyle("-fx-text-fill: #ffffff;");

        HBox item = new HBox(10, rect, label);
        item.setAlignment(Pos.CENTER_LEFT);
        return item;
    }
}