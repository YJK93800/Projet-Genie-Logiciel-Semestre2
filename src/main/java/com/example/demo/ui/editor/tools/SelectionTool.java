package com.example.demo.ui.editor.tools;

import com.example.demo.model.cells.ForestCell;
import javafx.scene.paint.Color;

/**
 * Outil utilisé pour sélectionner des cases dans la grille au lieu de les peindre.
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