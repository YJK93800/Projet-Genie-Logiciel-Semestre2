package com.example.demo.ui.editor.tools;

import com.example.demo.model.cells.ForestCell;
import com.example.demo.model.cells.Grass;
import com.example.demo.model.cells.GrassType;
import com.example.demo.model.cells.State;
import javafx.scene.paint.Color;

/**
 * Represents the tool used to paint Grass cells in the forest grid
 * Creates Grass instances and paints their color
 */

public class GrassTool implements FillCell {

    @Override
    public Color getColor() {
        return Color.LIGHTGREEN;
    }

    @Override
    public ForestCell callCell(int row, int col) {
        return new Grass("Grass", State.ALIVE, row, col, 0, GrassType.MEDIUM);
    }
}
