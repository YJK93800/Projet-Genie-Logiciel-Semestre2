package com.example.demo.model;

public abstract class ForestCell {

    //Atributes
    protected String name;
    protected int    xPos;
    protected int    yPos;

    /**
     * Constructor method
     *
     * @param name String value, name of the cell
     * @param xPos int value, position length-wise in the forest
     * @param yPos int value, position width-wise in the forest
     */

    public ForestCell(String name, int xPos, int yPos){
        this.name = name;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    //Getter Methods

    /**
     * Getter method of the attribute name
     *
     * @return String value, name of the plant
     */

    public String getName(){return this.name;}

    /**
     * Getter method of the xPos attribute
     *
     * @return int value, x position of the plant within the forest
     */

    public int getXPos(){return this.xPos;}

    /**
     * Getter method of the yPos attribute
     *
     * @return int value, the y position of the plant within the forest
     */

    public int getYPos(){return this.yPos;}

    //Override Methods

    @Override
    public String toString(){

        String display = "Nom: " + this.name + "\n";
        display += "- " + "x: " + this.xPos + "\n";
        display += "- " + "y: " + this.yPos + "\n";

        return display;
    }
}
