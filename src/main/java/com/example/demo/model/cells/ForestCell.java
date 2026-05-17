package com.example.demo.model.cells;

/**
 * Represents a cell within the forest
 * <p>
 * @author Yann Kong IN1 GI1
 * @version 21.0.8
 */


public abstract class ForestCell {

    //Atributes
    protected String name;
    protected int    xPos;
    protected int    yPos;
    protected int    height;

    /**
     * Constructor method
     *
     * @param name String value, name of the cell
     * @param xPos int value, position length-wise in the forest
     * @param yPos int value, position width-wise in the forest
     * @param height int value, the relative height of the forest
     */

    public ForestCell(String name, int xPos, int yPos, int height){
        this.name = name;
        this.xPos = xPos;
        this.yPos = yPos;
        this.height = height;
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

    /**
     * Getter method of the height attribute
     *
     * @return int value, the relative height of the cell within the forest
     */
    public int getHeight(){return this.height;}

    //Abstract Methods

    /**
     * Method to return the character associated to the instance
     *
     * @return char value, the symbol representing the object
     */
    public abstract char getChar();

    //Override Methods

    @Override
    public String toString(){

        String display = "Nom: " + this.name + "\n";
        display += "- " + "x: " + this.xPos + "\n";
        display += "- " + "y: " + this.yPos + "\n";
        display += "- " + "hauteur: " + this.height + "\n";

        return display;
    }
}
