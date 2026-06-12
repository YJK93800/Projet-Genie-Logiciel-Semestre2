package com.example.demo.model.cells;

/**
 * General lengths of grass
 */
public enum GrassType {
    SMALL(0.5),
    MEDIUM(0.8),
    TALL(0.9);

    private double flammability;

    /**
     * "Constructor" Method
     *
     * @param flammability double value, the flammability of the length-type of the grass
     */
    GrassType(double flammability){
        this.flammability = flammability;
    }

    // Getter methods

    /**
     * Getter method of the flammability attribute
     *
     * @return double value, the flammability of the length-type of the grass
     */
    public double getFlammability(){return this.flammability;}
}
