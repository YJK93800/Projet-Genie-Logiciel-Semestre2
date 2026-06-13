package com.example.demo.ui.Menu;

import com.example.demo.ui.editor.Editor;
import com.example.demo.ui.editor.tools.IgniteTool;
import com.example.demo.ui.editor.tools.SelectionTool;
import com.example.demo.ui.editor.tools.RectangleSelectionTool;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

/**
 * Menu display grouping all the editing tools of the forest
 * Contains the plant menu, the environment menu, the selection tools and the ignite tool
 * <p>
 * @version 21.0.8
 */

public class ToolsMenuDisplay extends AbstractMenuDisplay {

    /**
     * Constructor Method
     */
    public ToolsMenuDisplay() {
        super("🛠  Tools");
    }

    /**
     * Method that fills the Tools submenu with the editing tools
     *
     * @param subMenuContainer VBox value, the container of the submenu
     */
    @Override
    protected void buildSubMenuContent(VBox subMenuContainer) {

        PlantMenuDisplay plantComponent = new PlantMenuDisplay();
        EnvironmentMenuDisplay environmentComponent = new EnvironmentMenuDisplay();

        Button selectBtn = new Button(" Select Cells");
        selectBtn.getStyleClass().add("nav-button");
        selectBtn.setOnAction(e -> {
            Editor.setCurrentTool(new SelectionTool());
            System.out.println("Selection Tool selected");
        });

        Button rectSelectBtn = new Button(" Rectangle Select");
        rectSelectBtn.getStyleClass().add("nav-button");
        rectSelectBtn.setOnAction(e -> {
            Editor.setCurrentTool(new RectangleSelectionTool());
            System.out.println("Rectangle Selection Tool selected");
        });

        Button ignite = new Button(" Ignite Plant");
        ignite.getStyleClass().add("nav-button");
        ignite.setOnAction(e -> {
            Editor.setCurrentTool(new IgniteTool());
            System.out.println("Ignite selected");
        });

        subMenuContainer.getChildren().addAll(
                plantComponent.createMenu(),
                environmentComponent.createMenu(),
                selectBtn,
                rectSelectBtn,
                ignite
        );
    }
}