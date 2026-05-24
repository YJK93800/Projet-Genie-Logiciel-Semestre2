package com.example.demo.ui.Menu;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public abstract class AbstractMenuDisplay {

    private final String menuTitle;

    /**
     * Constructor requiring the menu title (e.g., "Weather", "Forest").
     */
    public AbstractMenuDisplay(String menuTitle) {
        this.menuTitle = menuTitle;
    }

    /**
     * Final method that builds the component structure.
     */
    public final Parent createMenu() {
        VBox menuContainer = new VBox(5);

        Button btnToggle = new Button("> " + menuTitle);
        btnToggle.getStyleClass().add("nav-button");

        VBox subMenu = new VBox(10);
        subMenu.getStyleClass().add("sub-menu");
        subMenu.setVisible(false);
        subMenu.setManaged(false);

        btnToggle.setOnAction(e -> {
            boolean isExpanded = subMenu.isVisible();
            if (isExpanded) {
                subMenu.setVisible(false);
                subMenu.setManaged(false);
                btnToggle.setText("> " + menuTitle);
            } else {
                subMenu.setVisible(true);
                subMenu.setManaged(true);
                btnToggle.setText("v " + menuTitle);
            }
        });

        buildSubMenuContent(subMenu);

        menuContainer.getChildren().addAll(btnToggle, subMenu);
        return menuContainer;
    }

    /**
     * Abstract method implemented by child classes to populate the submenu container.
     */
    protected abstract void buildSubMenuContent(VBox subMenuContainer);

    /**
     * Shared utility method to easily create aligned input fields.
     */
    protected HBox createInputField(String labelText, String defaultValue) {
        HBox row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);
        row.setSpacing(10);
        row.getStyleClass().add("input-row");

        Label label = new Label(labelText);
        label.getStyleClass().add("sub-menu-label");
        label.setPrefWidth(120);

        TextField textField = new TextField(defaultValue);
        textField.getStyleClass().add("custom-text-field");
        textField.setPrefWidth(70);

        row.getChildren().addAll(label, textField);
        return row;
    }


}