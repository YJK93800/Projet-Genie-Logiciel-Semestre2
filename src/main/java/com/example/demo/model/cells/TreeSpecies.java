package com.example.demo.model.cells;

/**
 * All Tree species available
 */
public enum TreeSpecies {
    OAK(0.2),
    BIRCH(0.1),
    ACACIA(0.05),
    PINE(0.5);


    private double flammability;

    /**
     * "Constructor" to set the specic attributes to a specie
     * @param flammability double, the flammability of the specie
     */
    TreeSpecies(double flammability){
        this.flammability = flammability;
    }

    // Getter methods

    /**
     * Getter method of the flammability attribute
     *
     * @return double value, the flammability of the specie
     */
    public double getFlammability(){return this.flammability;}

}
