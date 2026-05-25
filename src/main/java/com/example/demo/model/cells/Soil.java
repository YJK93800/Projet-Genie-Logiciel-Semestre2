package com.example.demo.model.cells;

import javafx.scene.paint.Color;

/**
 * Represents the soil, a dirt path within the forest
 * <p>
 * @author Yann Kong IN1 GI1
 * @version 21.0.8
 */


public class Soil extends ForestCell{

    public Soil(String name, int xPos, int yPos, int height){
        super(name, xPos, yPos, height);
    }

    public char getChar(){
        return 'S';
    }

    // Override
    @Override
    public Color displayColor() {
        return Color.SADDLEBROWN;
    }

    @Override
    public String display(){
        return "🟫";
    }

}
