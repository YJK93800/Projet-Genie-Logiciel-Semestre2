package com.example.demo.model;

abstract class Vegetation {

    //General Attributes
    protected String name;
    protected State  state;
    protected int    xPos;
    protected int    yPos;

    // Methods

    /**
     * Constructor Method
     *
     * @param name String value, name of the plant
     * @param xPos int value, position  length-wise of the plant within the forest
     * @param yPos int value, position width-wise of the plant within the forest
     */

    public Vegetation(String name, State state, int xPos, int yPos){
        this.name = name;
        this.state = state;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    //Getter Method

    /**
     * Getter method of the attribute name
     *
     * @return String value, name of the plant
     */

    public String getName(){return this.name;}

    /**
     * Getter method of the attribute state
     *
     * @return State value, current state of the plant
     */

    public State getState(){return this.state;}

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
        display += "- " + "Status: " + this.state + "\n";
        display += "- " + "x: " + this.xPos + "\n";
        display += "- " + "y: " + this.yPos + "\n";

        return display;
    }




}
