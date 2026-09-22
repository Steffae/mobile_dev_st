package ru.mirea.elkinasa.movieproject.data.storage;

import ru.mirea.elkinasa.movieproject.domain.models.Movie;

public interface MovieStorage {
    Movie get();
    boolean save(Movie movie);
}
