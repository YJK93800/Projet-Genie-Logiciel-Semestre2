package com.example.demo.ui;

import com.example.demo.simulation.SaveManager;
import com.example.demo.simulation.Simulation;
import com.example.demo.ui.Menu.LoadPopUp;
import com.example.demo.ui.Menu.SavePopUp;
import com.example.demo.ui.actions.UIController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.geometry.Orientation;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.util.Duration;

/**
 * Bottom bar of the application
 * Contains the save/load, checkpoint/reset, auto play and next turn controls
 * <p>
 * @version 21.0.8
 */

public class BottomBarDisplay {

    private Simulation simulation;
    private Runnable refreshUI;
    private UIController controller;
    private Simulation checkpoint;

    private Timeline timeline;
    private double speed = 1.0;
    private Button playPause;

    /**
     * Constructor Method
     *
     * @param simulation the ongoing simulation
     * @param refreshUI Runnable used to refresh the interface after an action
     * @param controller UIController used to rebuild the grid when a simulation is loaded
     */
    public BottomBarDisplay(Simulation simulation, Runnable refreshUI, UIController controller) {
        this.simulation = simulation;
        this.refreshUI = refreshUI;
        this.controller = controller;
    }

    /**
     * Method that builds the bottom bar with all of its buttons
     *
     * @return Parent value, the bottom bar to be displayed
     */
    public Parent createContent() {

        HBox bottomBar = new HBox();
        bottomBar.getStyleClass().add("bottom-bar");
        bottomBar.setAlignment(Pos.CENTER_LEFT);
        bottomBar.setSpacing(10);
        bottomBar.setPadding(new Insets(10, 20, 10, 20));

        // --- Group 1 : Save / Load ---
        Button save = new Button("Save");
        save.getStyleClass().add("nav-button");
        save.setOnAction(e -> {
            SavePopUp popup = new SavePopUp(name -> SaveManager.save(this.simulation, name));
            popup.open();
        });

        Button load = new Button("Load");
        load.getStyleClass().add("nav-button");
        load.setOnAction(e -> {
            LoadPopUp popup = new LoadPopUp(name -> {
                Simulation loaded = SaveManager.load(name);
                if (loaded != null) {
                    this.simulation = loaded;
                    this.controller.loadSimulation(loaded);
                    this.refreshUI.run();
                }
            });
            popup.open();
        });

        // --- Group 2 : Checkpoint / Reset ---
        Button checkpoint = new Button("Checkpoint");
        checkpoint.getStyleClass().add("nav-button");
        checkpoint.setOnAction(e -> {
            this.checkpoint = SaveManager.deepCopy(this.simulation);
            System.out.println("Checkpoint set");
        });

        Button reset = new Button("Reset");
        reset.getStyleClass().add("nav-button");
        reset.setOnAction(e -> {
            stopAutoPlay();
            if (this.checkpoint != null) {
                Simulation restored = SaveManager.deepCopy(this.checkpoint);
                this.simulation = restored;
                this.controller.loadSimulation(restored);
                this.refreshUI.run();
                System.out.println("Reset to checkpoint");
            } else {
                System.out.println("No checkpoint set");
            }
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // --- Group 3 : playback controls ---
        playPause = new Button("Play");
        playPause.getStyleClass().add("nav-button");
        playPause.setOnAction(e -> {
            if (timeline != null && timeline.getStatus() == Animation.Status.RUNNING) {
                stopAutoPlay();
            } else {
                startAutoPlay();
            }
        });

        ChoiceBox<String> speedChoice = new ChoiceBox<>();
        speedChoice.getItems().addAll("x1", "x2", "x3");
        speedChoice.setValue("x1");
        speedChoice.getStyleClass().add("custom-choice-box");
        speedChoice.setOnAction(e -> {
            String value = speedChoice.getValue();
            setSpeed(Double.parseDouble(value.substring(1)));
        });

        Label turnLabel = new Label("Turns :");
        turnLabel.getStyleClass().add("sub-menu-label");

        TextField turnField = new TextField("1");
        turnField.getStyleClass().add("custom-text-field");
        turnField.setPrefWidth(50);

        Button nextTurn = new Button("=> Next Turn");
        nextTurn.getStyleClass().add("nav-button");
        nextTurn.setOnAction(e -> {
            try {
                int n = Integer.parseInt(turnField.getText());
                for (int i = 0; i < n; i++) {
                    this.simulation.spreadFireOneTurn();
                }
                this.refreshUI.run();
            } catch (NumberFormatException ex) {
                System.out.println("Invalid number of turns");
            }
        });

        bottomBar.getChildren().addAll(
                save, load,
                verticalSeparator(),
                checkpoint, reset,
                spacer,
                playPause, speedChoice,
                verticalSeparator(),
                turnLabel, turnField, nextTurn
        );

        return bottomBar;
    }

    /**
     * Method that creates a vertical separator line between button groups
     *
     * @return Separator value, the vertical line
     */
    private Separator verticalSeparator() {
        Separator sep = new Separator(Orientation.VERTICAL);
        return sep;
    }

    /**
     * Method that starts the auto play at the current speed
     * Uses a Timeline that runs one turn at a regular interval
     * Stops automatically when no plant is burning anymore
     */
    private void startAutoPlay() {
        if (timeline != null) {
            timeline.stop();
        }

        double seconds = 1.0 / speed;

        timeline = new Timeline(new KeyFrame(Duration.seconds(seconds), e -> {
            this.simulation.spreadFireOneTurn();
            this.refreshUI.run();

            if (this.simulation.getBurningPlants().isEmpty()) {
                stopAutoPlay();
            }
        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        playPause.setText("Pause");
    }

    /**
     * Method that stops the auto play
     */
    private void stopAutoPlay() {
        if (timeline != null) {
            timeline.stop();
        }
        playPause.setText("Play");
    }

    /**
     * Method that changes the speed and restarts the timeline if it was running
     *
     * @param newSpeed double value, the new speed multiplier
     */
    private void setSpeed(double newSpeed) {
        this.speed = newSpeed;
        System.out.println("Speed set to x" + (int) newSpeed);

        if (timeline != null && timeline.getStatus() == Animation.Status.RUNNING) {
            startAutoPlay();
        }
    }

    // Setter method

    /**
     * Setter method of the simulation attribute
     *
     * @param simulation the new ongoing simulation
     */
    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

}