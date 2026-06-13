package com.example.demo.ui.editor.tools;

import com.example.demo.model.cells.ForestCell;
import javafx.scene.paint.Color;

/**
 * Tool used to select cells in the grid in a rectangle shape instead of painting them
 */

public class RectangleSelectionTool implements FillCell {
    @Override
    public Color getColor() {
        return Color.TRANSPARENT;
    }

    @Override
    public ForestCell callCell(int row, int col) {
        return null;
    }
}