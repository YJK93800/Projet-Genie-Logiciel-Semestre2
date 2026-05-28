package com.example.demo.ui.editor.tools;

import com.example.demo.model.cells.ForestCell;
import com.example.demo.model.cells.Soil;
import javafx.scene.paint.Color;

/**
 * Represents the tool used to paint Soil cells in the forest grid
 * Creates Soil instances and paints their color
 */

public class SoilTool implements FillCell {

    @Override
    public Color getColor() {
        return Color.BROWN;
    }

    @Override
    public ForestCell callCell(int row, int col) {
        return new Soil("Soil", row, col, 0);
    }
}