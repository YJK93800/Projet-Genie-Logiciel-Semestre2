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

    public Vegetation(String name, State state, int xPos, int yPos, int height){
        super(name, xPos, yPos, height);
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

    @Override
    public boolean equals(Object o){
        if (o instanceof Vegetation){
            Vegetation v = (Vegetation) o;

            return this.xPos == v.getXPos() &&
                   this.yPos == v.getYPos() &&
                   this.name.equals(v.getName());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + xPos;
        result = 31 * result + yPos;
        return result;
    }

}
