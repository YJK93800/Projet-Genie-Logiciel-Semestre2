package com.example.demo.model.cells;

import javafx.scene.paint.Color;

/**
 * Represents a body of water, river, lake, etc..
 * <p>
 * @author Yann Kong IN1 GI1
 * @version 21.0.8
 */


public class BodyOfWater extends ForestCell{

    public BodyOfWater(String name, int xPos, int yPos, int height){
        super(name, xPos, yPos, height);
    }

    public char getChar(){
        return 'W';
    }

    //Override Method

    @Override
    public Color displayColor() {
        return Color.DEEPSKYBLUE;
    }

    @Override
    public String display(){
        return "🌊";
    }

}
