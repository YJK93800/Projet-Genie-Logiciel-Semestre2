package com.example.demo.simulation;

import com.example.demo.exceptions.SimulationException;
import com.example.demo.model.Forest;
import com.example.demo.model.Weather.CardinalDirections;
import com.example.demo.model.Weather.Weather;
import com.example.demo.model.Weather.Wind;
import com.example.demo.model.cells.ForestCell;
import com.example.demo.model.cells.State;
import com.example.demo.model.cells.Vegetation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.io.Serializable;

import static com.example.demo.model.Weather.CardinalDirections.NEUTRAL;
import static com.example.demo.model.cells.State.BURNING;

/**
 * Represents the ongoing simulation of the wildfire
 * <p>
 * @author Yann Kong IN1 GI1
 * @version 21.0.8
 */

public class Simulation implements Serializable {

    private Forest forest;
    private static int nbFire = 0;
    private double weatherModifier;
    private int turn = 0;
    private HashSet<Vegetation> burningPlants = new HashSet<>();
    private HashSet<Vegetation> alivePlants = new HashSet<>();

    /**
     * Constructor method
     *
     * @param forest to be used for the simulation
     */
    public Simulation(Forest forest){
        this.forest = forest;
        this.weatherModifier = initializeWeatherModifier();
        initializeAlivePlants();
    }

    // Getter methods

    /**
     * Getter method of the forest attribute
     *
     * @return self-explanatory
     */
    public Forest getForest(){return this.forest;}

    /**
     *  Getter method of the nbFire class attribute
     *
     * @return self-explanatory
     */
    public static int getNbFire(){return nbFire;}

    /**
     * Getter method of the weatherModifier
     *
     * @return self-explanatory
     */
    public double weatherModifier(){return this.weatherModifier;}

    /**
     * Getter method of the turn attribute
     *
     * @return self-explanatory
     */

    public int getTurn(){return this.turn;}

    /**
     * Getter method of the burningPlants attribute
     * @return self-explanatory
     */
    public HashSet<Vegetation> getBurningPlants(){return this.burningPlants;}

    public HashSet<Vegetation> getAlivePlants() { return this.alivePlants; }

    // Methods

    /**
     * Method to determine a weather modifier necessary to calculate the fire spread probability
     *
     * @return double value
     */
    private double initializeWeatherModifier() {
        Weather w = this.forest.getWeather();

        double temp = w.getTemperature() / 40.0;
        double sun = w.getSunlightIntensity() / 100.0;
        double humidity = w.getHumidity() / 100.0;

        double modifier = 1.0 + temp * 1.0 + sun * 0.8;
        modifier *= (1.0 - 0.4 * humidity);

        return Math.max(0.3, Math.min(modifier, 3.0));
    }



    private void initializeAlivePlants() {
        this.alivePlants.clear();
        ForestCell[][] grid = this.forest.getForestGrid();
        if (grid == null) return;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] instanceof Vegetation) {
                    Vegetation veg = (Vegetation) grid[i][j];
                    if (veg.getState() == State.ALIVE) {
                        this.alivePlants.add(veg);
                    }
                }
            }
        }
    }

    /**
     * Method to set a plant on fire
     *
     * @param i int value, the width position
     * @param j int value, the length position
     */

    public void ignitePlant(int i, int j){
        ForestCell[][] grid = this.forest.getForestGrid();

        if (grid[i][j] instanceof Vegetation){
            Vegetation plant = (Vegetation) grid[i][j];
            plant.setState(BURNING);
            burningPlants.add(plant);
            alivePlants.remove(plant);
            return;
        }

        throw new SimulationException(
                "ERROR 100 : Cannot Ignite cell at position :" + i + "," + j
        );
    }

    /**
     * Method to run the simulation
     *
     * @param i width-coordinate of the starting cell
     * @param j length-coordinate of the starting cell
     */
    public void run(int i, int j){
        ignitePlant(i,j);
        Scanner scanner = new Scanner(System.in);

        System.out.println("Simulation running.");
        System.out.println("Press p to run one turn.");
        System.out.println("Press q to exit.");

        System.out.println(this.forest);

        while (!burningPlants.isEmpty()) {
            System.out.print("> ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("p")) {
                spreadFireOneTurn();
                System.out.println(this.forest);
                System.out.println("Turn: " + this.turn);
            }
            else if (input.equalsIgnoreCase("q")) {
                System.out.println("Simulation arrêtée.");
                break;
            }
            else {
                System.out.println("Commande inconnue. Utilise p ou q.");
            }
        }

        System.out.println("Simulation terminée : plus aucune plante ne brûle.");
    }

    /**
     * Spreads fire by one turn without wind
     * Burning plants try to ignite their four direct neighbors
     */

    public void spreadFireOneTurn() {

        ArrayList<Vegetation> plantsToIgnite = new ArrayList<>();

        Iterator<Vegetation> iterator = burningPlants.iterator();

        while (iterator.hasNext()) {
            Vegetation plant = iterator.next();

            int i = plant.getXPos();
            int j = plant.getYPos();

            tryIgniteNeighbor(plant, plantsToIgnite, i - 1, j); // top cell
            tryIgniteNeighbor(plant, plantsToIgnite, i + 1, j); // bottom cell
            tryIgniteNeighbor(plant, plantsToIgnite, i, j - 1); // left cell
            tryIgniteNeighbor(plant, plantsToIgnite, i, j + 1); // right cell

            plant.burn();

            if (plant.getState() != State.BURNING) {
                iterator.remove();
            }
        }

        for (Vegetation plant : plantsToIgnite) {
            plant.setState(State.BURNING);
            burningPlants.add(plant);
            alivePlants.remove(plant);
        }
        this.turn += 1;
    }

    /**
     * Auxiliary method for fire spreading methods
     *
     * @param source the origin of the fire targetting the plant
     * @param plantsToIgnite array List, contains the cells to be set on fire
     * @param i int value, the width position
     * @param j int value, the length position
     */

    private void tryIgniteNeighbor(Vegetation source, ArrayList<Vegetation> plantsToIgnite, int i, int j) {

        ForestCell[][] grid = this.forest.getForestGrid();

        int row = grid.length;
        int col = grid[0].length;

        if (i < 0 || i >= row || j < 0 || j >= col) {
            return;
        }

        if (grid[i][j] instanceof Vegetation) {
            Vegetation plant = (Vegetation) grid[i][j];

            if (plant.getState() == State.ALIVE) {
                double probability = spreadingProbability(source, plant, i, j);
                probability = Math.max(0.0, Math.min(1.0, probability));

                if (Math.random() < probability && !plantsToIgnite.contains(plant)) {
                    plantsToIgnite.add(plant);
                }
            }
        }
    }

    /**
     * Method to set the probability for a fire to spread to a nearby plant
     *
     * @param source the origin of the current fire
     * @param i width-coordinate of the targeted plant
     * @param j length-coordinate of the targeted plant
     * @return the probability to ignite the targeted plant on fire
     */
    private double spreadingProbability(Vegetation source, Vegetation target, int i, int j) {
        Weather weather = this.forest.getWeather();
        Wind wind = weather.getWind();

        int dxCell = i - source.getXPos();
        int dyCell = j - source.getYPos();

        double dist = Math.sqrt(dxCell * dxCell + dyCell * dyCell);
        if (dist == 0) return 0.0;

        double dx = dxCell / dist;
        double dy = dyCell / dist;

        double base = 0.45;
        double flammability = (source.getFlammability() + target.getFlammability()) / 2.0;

        double probability = base * flammability * this.weatherModifier;

        if (wind.getWindDirection() != NEUTRAL) {
            double wx = wind.getWindDirection().getXPos();
            double wy = wind.getWindDirection().getYPos();

            double norm = Math.sqrt(wx * wx + wy * wy);
            if (norm != 0) { wx /= norm; wy /= norm; }

            double dot = dx * wx + dy * wy;
            double windStrength = Math.min(wind.getWindSpeed() / 120.0, 1.0);

            double windFactor = 1.0 + dot * windStrength * 1.2;
            probability *= windFactor;
        }

        return Math.max(0.0, Math.min(probability, 1.0));
    }



}
