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
    private int turn = 0;
    private HashSet<Vegetation> burningPlants = new HashSet<>();
    private HashSet<Vegetation> alivePlants = new HashSet<>();
    private ArrayList<int[]> turnHistory = new ArrayList<>();

    /**
     * Constructor method
     *
     * @param forest to be used for the simulation
     */
    public Simulation(Forest forest){
        this.forest = forest;
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
     * Getter method of the turn attribute
     *
     * @return self-explanatory
     */
    public int getTurn(){return this.turn;}

    /**
     * Getter method of the burningPlants attribute
     *
     * @return self-explanatory
     */
    public HashSet<Vegetation> getBurningPlants(){return this.burningPlants;}

    /**
     * Getter method of the alivePlants attribute
     *
     * @return self-explanatory
     */
    public HashSet<Vegetation> getAlivePlants() { return this.alivePlants; }

    /**
     * Getter method of the turnHistory attribute
     *
     * @return list of int[] where each entry is {alive, burning, dead} for a given turn
     */
    public ArrayList<int[]> getTurnHistory() { return this.turnHistory; }

    // Methods

    /**
     * Method to determine a weather modifier necessary to calculate the fire spread probability
     *
     * @return double value
     */
    private double initializeWeatherModifier() {
        Weather w = this.forest.getWeather();

        double temp     = w.getTemperature() / 50.0;
        double sun      = w.getSunlightIntensity() / 100.0;
        double humidity = w.getHumidity() / 100.0;

        double modifier = (0.5 + temp * 0.8 + sun * 0.5) * Math.pow(1.0 - humidity, 2.0);

        return Math.max(0.0, Math.min(modifier, 3.0));
    }

    /**
     * Initializes the set of alive plants by scanning the entire forest grid.
     * Clears any existing data in the alive plants set and adds all vegetation
     * cells that currently have an ALIVE state.
     */
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
     * Records the current state counts (alive, burning, dead) into the turn history.
     */
    private void recordTurnSnapshot() {
        int alive = alivePlants.size();
        int burning = burningPlants.size();
        int dead = 0;

        ForestCell[][] grid = this.forest.getForestGrid();

        for (ForestCell[] row : grid) {
            for (ForestCell cell : row) {
                if (cell instanceof Vegetation veg && veg.getState() == State.DEAD) {
                    dead++;
                }
            }
        }

        turnHistory.add(new int[]{alive, burning, dead});
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
                System.out.println("Simulation stopped.");
                break;
            }
            else {
                System.out.println("Unknown command. Use p or q.");
            }
        }

        System.out.println("Simulation done : no more plants burning.");
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
        recordTurnSnapshot();
    }

    /**
     * Auxiliary method for fire spreading methods
     *
     * @param source the origin of the fire targeting the plant
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

        double base = 0.7;
        double flammability = (source.getFlammability() + target.getFlammability()) / 2.0;

        double probability = base * flammability * initializeWeatherModifier();

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

        int dHeight = target.getHeight() - source.getHeight();
        double heightFactor;
        if (dHeight > 0)      heightFactor = 1.3;
        else if (dHeight < 0) heightFactor = 0.05;
        else                  heightFactor = 1.0;
        probability *= heightFactor;

        double distanceFactor;
        if (dist > 1.0) distanceFactor = 0.7;
        else            distanceFactor = 1.0;
        probability *= distanceFactor;

        return Math.max(0.0, Math.min(probability, 1.0));
    }
}