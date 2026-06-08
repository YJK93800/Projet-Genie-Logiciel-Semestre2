package com.example.demo.ui.Menu;

import com.example.demo.ui.editor.Editor;
import com.example.demo.ui.editor.tools.IgniteTool;
import com.example.demo.ui.editor.tools.SelectionTool;
import com.example.demo.ui.editor.tools.RectangleSelectionTool;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class ToolsMenuDisplay extends AbstractMenuDisplay {

    public ToolsMenuDisplay() {
        super("🛠  Tools");
    }

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