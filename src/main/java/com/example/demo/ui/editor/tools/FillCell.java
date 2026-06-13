package com.example.demo.ui.editor.tools;

import com.example.demo.model.cells.ForestCell;
import javafx.scene.paint.Color;

/**
 * Represents a painting tool used in the forest editor. <br/>
 * Each tool defines: <br/>
 * - the visual color used for preview
 * - the type of ForestCell it creates when painting
 */
public interface FillCell {

    /**
     * Method to return the color associated with the tool
     * @return Color value
     */
    Color getColor();

    /**
     * Method to return an instance associated with the tool
     *
     * @param row int value, position length-wise
     * @param col int value, position width-wise
     * @return an instance of the tool
     */

    ForestCell callCell(int row, int col);
}
