package com.example.demo.model.cells;

public class Grass extends Vegetation {

    private GrassType type;


    /**
     * Constructor Method
     *
     * @param name String value, name of the grass
     * @param state State enum value, current state of the grass
     * @param xPos int value, x position in the forest
     * @param yPos int value, y position in the forest
     * @param height int value, relative height of the grass in the forest
     * @param type GrassType enum value, height of the grass
     */
    public Grass(String name, State state, int xPos, int yPos, int height, GrassType type){
        super(name, state, xPos, yPos, height, 1);
        this.type = type;
        this.flammability = initializeFlammability();
    }

    //getter method

    /**
     * Getter method of the height attribute
     *
     * @return GrassType enum value
     */
    public GrassType getType(){return this.type;}

    public char getChar(){
        return 'G';
    }

    //Methods

    public double initializeFlammability(){
        return 0.8;
    }


    //Override Methods
    @Override
    public String toString(){
        String display = super.toString();
        display += "Type: Herbe" + "\n";
        display += "type: " + this.type + "\n";

        return display;
    }
}
