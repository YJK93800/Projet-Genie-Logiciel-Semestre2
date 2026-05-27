package com.example.demo.ui;

/**
 * Editing Tool Class
 * <p>
 * @author Yann Kong IN1 GI1
 * @version 21.0.8
 */

public abstract class Editor {

    private static EditorMode currentMode = EditorMode.NONE;

    public static void switchMode(EditorMode mode) {
        currentMode = mode;
    }

    public static EditorMode getCurrentMode(){return currentMode;}
}
