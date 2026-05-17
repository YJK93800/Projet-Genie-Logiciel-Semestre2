package com.example.demo.model.cells;

public abstract class Vegetation extends ForestCell {

    //General Attributes
    protected State state;
    protected double flammability;

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

    /**
     * Getter method of the flammability attribute
     *
     * @return double value, returns the flammability of the plant and ranges from 0 excluded to 1
     */
    public double getFlammability(){return this.flammability;}

    //Abstract Methods

    /**
     * Method that sets the flammability of a plant according to its specifications
     *
     * @return double value, the flammability of the plant
     */
    public abstract double initializeFlammability();


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
