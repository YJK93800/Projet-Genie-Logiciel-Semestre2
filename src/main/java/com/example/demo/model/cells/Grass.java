package com.example.demo.model.cells;

import javafx.scene.paint.Color;

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

    /**
     * Setter method for the grass type attribute.
     *
     * @param type the new GrassType enum value
     */
    public void setType(GrassType type) {
        this.type = type;
    }

    public double initializeFlammability(){
        return type.getFlammability();
    }


    //Override Methods
    @Override
    public Color displayColor() {

        if (this.state == State.BURNING) {
            return Color.ORANGE;
        }

        if (this.state == State.DEAD) {
            return Color.BLACK;
        }

        return Color.LIGHTGREEN;
    }

    @Override
    public String display(){

        switch(this.state){

            case ALIVE:
                return "🌿";

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
        display += "Type: Herbe" + "\n";
        display += "type: " + this.type + "\n";

        return display;
    }
}
