package com.example.demo.ui.editor.tools;

import com.example.demo.model.cells.ForestCell;
import com.example.demo.model.cells.BodyOfWater;
import javafx.scene.paint.Color;

/**
 * Represents the tool used to paint Water cells in the forest grid
 * Creates Water instances and paints their color
 */

public class WaterTool implements FillCell {

    @Override
    public Color getColor() {
        return Color.DEEPSKYBLUE;
    }

    @Override
    public ForestCell callCell(int row, int col) {
        return new BodyOfWater("Water", row, col, 0);
    }
}