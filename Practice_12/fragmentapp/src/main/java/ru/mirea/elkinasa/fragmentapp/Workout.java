package ru.mirea.elkinasa.fragmentapp;

public class Workout {
    private final String title;
    private final String description;
    private boolean done;

    public Workout(String title, String description) {
        this.title = title;
        this.description = description;
        this.done = false;
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public boolean isDone() { return done; }
    public void setDone(boolean done) { this.done = done; }
}