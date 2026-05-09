package com.example.demo.model;

public class Grass extends Vegetation{

    private GrassType height;


    /**
     * Constructor Method
     *
     * @param name String value, name of the grass
     * @param state State enum value, current state of the grass
     * @param xPos int value, x position in the forest
     * @param yPos int value, y position in the forest
     * @param height GrassType enum value, height of the grass
     */
    public Grass(String name, State state, int xPos, int yPos, GrassType height){
        super(name, state, xPos, yPos);
        this.height = height;
    }

    //getter method

    /**
     * Getter method of the height attribute
     *
     * @return GrassType enum value
     */
    public GrassType getHeight(){return this.height;}


    //Override Methods
    @Override
    public String toString(){
        String display = super.toString();
        display += "Type: Herbe" + "\n";
        display += "Taill: " + this.height + "\n";

        return display;
    }
}
