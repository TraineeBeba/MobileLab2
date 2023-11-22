package com.example.mobilelab2;

public class City {

    private int id;
    private String name;
    private double distance;
    private int population;

    public City(int id, String name, double distance, int population) {
        this.id = id;
        this.name = name;
        this.distance = distance;
        this.population = population;
    }

    public City(String name, double distance, int population) {
        this.name = name;
        this.distance = distance;
        this.population = population;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    @Override
    public String toString() {
        return name + '\n' +
                distance + " km\n" +
                population + " people";
    }
}
