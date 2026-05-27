package com.example.demo.ui.actions;

import com.example.demo.ui.Editor;
import javafx.scene.paint.Color;


public abstract class FillCell {

    public static Color color = Color.LIGHTGRAY;


    /**
     * Static Method to return the color that will fill the cell
     *
     * @return self-explanatory
     */
    public static Color getColor(){

        switch(Editor.getCurrentMode()){
            case TREE:
                color = Color.GREEN;
                break;
            case GRASS:
                color = Color.LIGHTGREEN;
                break;
            case WATER:
                color = Color.BLUE;
                break;
            case SOIL:
                color = Color.BROWN;
                break;
            default :
                color = Color.LIGHTGRAY;
                break;
        }

        return color;

    }


}
