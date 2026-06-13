package com.example.demo.ui.Menu;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


/**
 * Abstract base class representing a collapsible navigation menu component in the user interface.
 * This class follows a template-like pattern where it handles the visual toggle behavior
 * (expanding/collapsing) of a sub-menu section, while leaving the responsibility of
 * populating the specific sub-menu content to its concrete subclasses.
 */
public abstract class AbstractMenuDisplay {
    /**
     * The display title of the menu item, used as the main label on the toggle button.
     */
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
     * Creates a standardized horizontal layout container containing a structured text label
     * and a pre-formatted numerical/text input field. Useful for keeping UI consistency
     * across different menu forms.
     *
     * @param labelText    the text description to display next to the input field
     * @param defaultValue the initial value to populate within the text input field
     * @return an HBox containing the styled label and text field properly aligned
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