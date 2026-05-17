package com.example.demo;

import com.example.demo.model.Forest;
import com.example.demo.model.Weather.Weather;
import com.example.demo.model.Weather.WeatherType;
import com.example.demo.model.Weather.Wind;
import com.example.demo.model.Weather.CardinalDirections;

public class TempMain {
    public static void main(String[] args) {

        Weather weather = new Weather(WeatherType.SUNNY, new Wind(CardinalDirections.EAST, 100), 30.5, 50.9, 86.25);

        Forest forest = new Forest("forestTest.txt", weather);

        System.out.println(forest);
    }

}
