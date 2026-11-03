package ru.mirea.elkinasa.data.storage;

import ru.mirea.elkinasa.data.storage.models.Movie;

public interface MovieStorage {
    public Movie get();
    public boolean save(Movie movie);
}
