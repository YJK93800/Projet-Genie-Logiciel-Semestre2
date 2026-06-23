package com.example.demo.model.Weather;
/**
 * Enum representing the cardinal directions
 * <p>
 * @author Yann Kong IN1 GI1
 * @version 21.0.8
 */

public enum CardinalDirections {
    NEUTRAL(0,0),
    NORTH(-1,0),
    SOUTH(1,0),
    EAST(0,1),
    WEST(0, -1),
    NORTHEAST(-1,1),
    NORTHWEST(-1,-1),
    SOUTHEAST(1,1),
    SOUTHWEST(1,-1);

    private int xPos;
    private int yPos;

    CardinalDirections(int xPos, int yPos){
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public int getXPos(){return this.xPos;}
    public int getYPos(){return this.yPos;}

    public static CardinalDirections getDirection(int dx, int dy) {
        for (CardinalDirections direction : CardinalDirections.values()) {
            if (direction.getXPos() == dx && direction.getYPos() == dy) {
                return direction;
            }
        }
        return NEUTRAL;
    }

    public static CardinalDirections setRandomDirection(){

        for (CardinalDirections direction : CardinalDirections.values()) {

            if (Math.random() < 0.5){
                return direction;
            }
        }

        return NEUTRAL;
    }

}
