package ru.mirea.elkinasa.fragmentmanagerapp;

public class Country {
    private final String name;
    private final String capital;
    private final String population;
    private final String description;

    public Country(String name, String capital, String population, String description) {
        this.name = name;
        this.capital = capital;
        this.population = population;
        this.description = description;
    }

    public String getName() { return name; }
    public String getCapital() { return capital; }
    public String getPopulation() { return population; }
    public String getDescription() { return description; }

    @Override
    public String toString() { return name; }
}