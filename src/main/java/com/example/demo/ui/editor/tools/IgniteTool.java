package com.example.demo.ui.editor.tools;

import com.example.demo.model.cells.ForestCell;
import javafx.scene.paint.Color;


/**
 * Represents the tool used to Ignite cells in the forest grid
 * Set a Vegetation Cell to a BURNING State
 */

public class IgniteTool implements FillCell {

    @Override
    public Color getColor() {
        return Color.ORANGE;
    }

    @Override
    public ForestCell callCell(int row, int col) {
        return null;
    }

}