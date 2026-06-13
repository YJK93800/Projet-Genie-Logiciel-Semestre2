package com.example.demo.ui.editor.tools;

import com.example.demo.model.cells.ForestCell;
import javafx.scene.paint.Color;

/**
 * Tool used to select cells in the grid instead of painting them
 */

public class SelectionTool implements FillCell {

    @Override
    public Color getColor() {
        return Color.TRANSPARENT;
    }

    @Override
    public ForestCell callCell(int row, int col) {
        return null;
    }
}