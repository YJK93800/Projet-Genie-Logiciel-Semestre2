package com.example.demo.model.cells;

import com.example.demo.exceptions.TreeException;
import javafx.scene.paint.Color;

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

        super(name, state, xPos, yPos, height, 2);
        charToType(specie);
        this.flammability = initializeFlammability();

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
     * Setter method for the specie attribute.
     * Updates the species and automatically recalculates flammability.
     *
     * @param specie the new TreeSpecies enum value
     */
    public void setSpecie(TreeSpecies specie) {
        this.specie = specie;
        this.flammability = initializeFlammability();
    }

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

    /**
     * Method to convert a tree's specie into a displayable character
     *
     * @return String value, the character that represents the tree's specie
     */
    public char getChar(){
        char display;
        switch(this.specie){
            case OAK:
                display = 'O';
                break;
            case BIRCH:
                display = 'B';
                break;
            case PINE:
                display = 'P';
                break;
            case ACACIA:
                display = 'A';
                break;
            default:
                throw new TreeException("ERROR 400 : Unknown tree specie");

        }
        return display;
    }

    public double initializeFlammability(){
        return this.specie.getFlammability();
    }


    //Override
    @Override
    public Color displayColor() {

        if (this.state == State.BURNING) {
            return Color.ORANGE;
        }

        if (this.state == State.DEAD) {
            return Color.BLACK;
        }

        return Color.FORESTGREEN;
    }

    @Override
    public String display(){

        switch(this.state){

            case ALIVE:
                return "\uD83C\uDF32";

            case BURNING:
                return "🔥";

            case DEAD:
                return "⬛";

            default:
                return "?";
        }
    }

    @Override
    public String toString(){
        String display = super.toString();
        display += "Type: Arbre" + "\n";
        display += "Espèce: " + this.specie + "\n";

        return display;
    }
}
