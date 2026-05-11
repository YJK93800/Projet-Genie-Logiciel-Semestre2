package com.example.demo.model;

public class Grass extends Vegetation{

    private GrassType type;


    /**
     * Constructor Method
     *
     * @param name String value, name of the grass
     * @param state State enum value, current state of the grass
     * @param xPos int value, x position in the forest
     * @param yPos int value, y position in the forest
     * @param type GrassType enum value, height of the grass
     */
    public Grass(String name, State state, int xPos, int yPos, int height, GrassType type){
        super(name, state, xPos, yPos, height);
        this.type = type;
    }

    //getter method

    /**
     * Getter method of the height attribute
     *
     * @return GrassType enum value
     */
    public GrassType getType(){return this.type;}


    //Override Methods
    @Override
    public String toString(){
        String display = super.toString();
        display += "Type: Herbe" + "\n";
        display += "type: " + this.type + "\n";

        return display;
    }
}
