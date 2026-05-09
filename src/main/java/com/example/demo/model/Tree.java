package com.example.demo.model;

public class Tree extends Vegetation{

    private TreeSpecies specie;

    //Method

    /**
     * Contructor Method
     *
     * @param name String value, name of the tree
     * @param state State enum value, current state of the tree
     * @param xPos int value, x position of the tree in the forest
     * @param yPos int value, y position of the tree in the forest
     * @param specie TreeSpecies enum value, specie of the tree
     */
    public Tree(String name, State state, int xPos, int yPos, int height, TreeSpecies specie){

        super(name, state, xPos, yPos, height);
        this.specie = specie;

    }

    //Getter method

    /**
     * Getter method of the specie attribute
     *
     * @return TreeSpecies enum value, the specie of the tree
     */

    public TreeSpecies getSpecie(){return this.specie;}


    //Override
    @Override
    public String toString(){
        String display = super.toString();
        display += "Type: Arbre" + "\n";
        display += "Espèce: " + this.specie + "\n";

        return display;
    }
}
