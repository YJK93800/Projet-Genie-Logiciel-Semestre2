package com.example.demo.ui.Menu;

import com.example.demo.ui.editor.Editor;
import com.example.demo.ui.editor.tools.IgniteTool;
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

        Button ignite = new Button("🔥  Ignite Plant");
        ignite.getStyleClass().add("nav-button");
        ignite.setOnAction(e -> {
            Editor.setCurrentTool(new IgniteTool());
            System.out.println("Ignite selected");
        });

        subMenuContainer.getChildren().addAll(
                plantComponent.createMenu(),
                environmentComponent.createMenu(),
                ignite
        );
    }
}