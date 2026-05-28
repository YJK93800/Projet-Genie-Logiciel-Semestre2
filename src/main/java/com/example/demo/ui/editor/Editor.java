package com.example.demo.ui.editor;

import com.example.demo.ui.editor.tools.FillCell;
import javafx.scene.paint.Color;

/**
 * Editing Tool Class
 * <p>
 * @author Yann Kong IN1 GI1
 * @version 21.0.8
 */

public abstract class Editor {

    private static FillCell currentTool;

    /**
     * Getter method of the currentTool attribute
     *
     * @return the current tool being used
     */
    public static FillCell getCurrentTool(){return currentTool;}

    /**
     * Method to get the current color
     * Mostly to avoid a crash during the boot up
     *
     * @return Color value
     */
    public static Color getCurrentColor() {

        if (currentTool == null) {
            return Color.LIGHTGRAY;
        }

        return currentTool.getColor();
    }

    /**
     * Setter method of the currentTool attribute
     *
     * @param tool, new tool being used
     */
    public static void setCurrentTool(FillCell tool) {
        currentTool = tool;
    }
}
