package com.example.demo.model;

public abstract class Vegetation extends ForestCell {

    //General Attributes
    protected State  state;

    // Methods

    /**
     * Constructor Method
     *
     * @param name String value, name of the plant
     * @param xPos int value, position  length-wise of the plant within the forest
     * @param yPos int value, position width-wise of the plant within the forest
     */

    public Vegetation(String name, State state, int xPos, int yPos){
        super(name, xPos, yPos);
        this.state = state;
    }

    //Getter Method


    /**
     * Getter method of the attribute state
     *
     * @return State value, current state of the plant
     */

    public State getState(){return this.state;}


    //Override Methods

    @Override
    public String toString(){

        String display = super.toString();
        display += "- " + "Status: " + this.state + "\n";

        return display;
    }




}
