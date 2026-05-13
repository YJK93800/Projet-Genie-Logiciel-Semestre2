package com.example.demo.model.Weather;

/**
 * Represents the weather within the forest
 * <p>
 * @author Yann Kong IN1 GI1
 * @version 21.0.8
 */

public class Weather {

    private WeatherType weatherType;
    private Wind wind;
    private double humidity; // as a percentage
    private double temperature; // in Celsius because Fahrenheit isn't real
    private double sunlightIntensity; // belongs in [0, 100]

    /**
     * Constructor Method
     *
     * @param weatherType WeatherType enum value, represents the current weather type
     * @param wind Wind instance, represents the current wind blowing in the forest
     * @param humidity double value, represents the humidity in the forest
     * @param temperature double value, represents the current temperature in the forest
     * @param sunlightIntensity double value, represents the current sunlight Intensity hitting the forest
     */

    public Weather(WeatherType weatherType, Wind wind, double humidity, double temperature, double sunlightIntensity){
        this.weatherType = weatherType;
        this.wind = wind;
        this.humidity = humidity;
        this.temperature = temperature;
        this.sunlightIntensity = sunlightIntensity;
    }

    // Getter methods

    /**
     * Getter method the weatherType attribute
     *
     * @return self-explanatory
     */
    public WeatherType getWeatherType(){return this.weatherType;}

    /**
     * Getter method of the wind attribute
     *
     * @return self-explanatory
     */
    public Wind getWind(){return this.wind;}

    /**
     * Getter method of the humidity attribute
     *
     * @return self-explanatory
     */
    public double getHumidity(){return this.humidity;}

    /**
     * Getter method of the temperature attribute
     *
     * @return self-explanatory
     */
    public double getTemperature(){return this.temperature;}

    /**
     * Getter method of the sunlightIntensity attribute
     *
     * @return self-explanatory
     */
    public double getSunlightIntensity(){return this.sunlightIntensity;}

    // Setter methods

    /**
     * Setter method of the weatherType attribute
     *
     * @param weatherType new weatherType
     */
    public void setWeatherType(WeatherType weatherType){ this.weatherType = weatherType;}

    /**
     * Setter method of the wind attribute
     *
     * @param wind new wind
     */
    public void setWind(Wind wind){this.wind = wind;}

    /**
     * Setter method of the humidity attribute
     *
     * @param humidity new humidity
     */

    public void setHumidity(double humidity){this.humidity = humidity;}

    /**
     * Setter method of the temperature attribute
     *
     * @param temperature new temperature
     */
    public void setTemperature(double temperature){this.temperature = temperature;}

    /**
     * Setter method of the sunlightIntensity attribute
     *
     * @param sunlightIntensity new sunlightIntensity
     */

    public void setSunlightIntensity(double sunlightIntensity){this.sunlightIntensity = sunlightIntensity;}

}
