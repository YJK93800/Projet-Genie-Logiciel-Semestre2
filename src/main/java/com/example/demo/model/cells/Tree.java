package com.example.demo.model.cells;

import com.example.demo.exceptions.TreeException;

public class Tree extends Vegetation {

    private TreeSpecies specie;

    //Method

    /**
     * Contructor Method
     *
     * @param name String value, name of the tree
     * @param state State enum value, current state of the tree
     * @param xPos int value, x position of the tree in the forest
     * @param yPos int value, y position of the tree in the forest
     * @param height int value, relative height of the tree in the forest
     * @param specie char value, specie of the tree
     */
    public Tree(String name, State state, int xPos, int yPos, int height, char specie){

        super(name, state, xPos, yPos, height);
        charToType(specie);

    }

    //Getter method

    /**
     * Getter method of the specie attribute
     *
     * @return TreeSpecies enum value, the specie of the tree
     */

    public TreeSpecies getSpecie(){return this.specie;}

    // Methods

    /**
     * Private method to convert a character in a file to the corresponding tree specie
     *
     * @param c char value, the approximated tree specie in the file
     */
    private void charToType(char c){
        switch(c){
            case 'O':
                this.specie = TreeSpecies.OAK;
                break;
            case 'B':
                this.specie = TreeSpecies.BIRCH;
                break;
            case 'P':
                this.specie = TreeSpecies.PINE;
                break;
            case 'A':
                this.specie = TreeSpecies.ACACIA;
                break;
            default:
                throw new TreeException("Unknown Tree specie");
        }
    }


    //Override
    @Override
    public String toString(){
        String display = super.toString();
        display += "Type: Arbre" + "\n";
        display += "Espèce: " + this.specie + "\n";

        return display;
    }
}
