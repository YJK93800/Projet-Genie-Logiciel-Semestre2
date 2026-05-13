package com.example.demo.model.Weather;

/**
 * Represents the Wind's characteristics within the forest
 * <p>
 * @author Yann Kong IN1 GI1
 * @version 21.0.8
 */

public class Wind {

    private CardinalDirections windDirection;
    private double windSpeed;   // in km/h


    /**
     * Constructor Method
     *
     * @param windDirection CardinalDirections enum value, direction of the wind
     * @param windSpeed double value, speed of the wind
     */
    public Wind(CardinalDirections windDirection, double windSpeed){
        this.windDirection = windDirection;
        this.windSpeed = windSpeed;
    }

    // Getter Methods

    /**
     * Getter method of the windDirection attribute
     *
     * @return CardinalDirections enum value, direction of the wind
     */
    public CardinalDirections getWindDirection(){return this.windDirection;}

    /**
     * Getter method of the windSpeed attribute
     *
     * @return double value, speed of the wind
     */
    public double getWindSpeed(){return this.windSpeed;}
}
