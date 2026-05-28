package com.example.demo.ui.editor.tools;

import com.example.demo.model.cells.ForestCell;
import com.example.demo.model.cells.State;
import com.example.demo.model.cells.Tree;
import javafx.scene.paint.Color;


/**
 * Represents the tool used to paint Tree cells in the forest grid
 * Creates tree instances and paints their color
 */


public class TreeTool implements FillCell {

    @Override
    public Color getColor() {
        return Color.GREEN;
    }

    @Override
    public ForestCell callCell(int row, int col) {
        return new Tree("Tree", State.ALIVE, row, col, 0, 'O');
    }
}
