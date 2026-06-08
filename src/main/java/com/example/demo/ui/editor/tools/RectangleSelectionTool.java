package com.example.demo.ui.editor.tools;

import com.example.demo.model.cells.ForestCell;
import javafx.scene.paint.Color;

/**
 * Represents a marquee/rectangle selection tool for the forest grid.
 */
public class RectangleSelectionTool implements FillCell {
    @Override
    public Color getColor() {
        return Color.TRANSPARENT;
    }

    @Override
    public ForestCell callCell(int row, int col) {
        return null; // Selection tools do not paint cells directly
    }
}