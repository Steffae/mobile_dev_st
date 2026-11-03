package ru.mirea.elkinasa.domain.repository;

import ru.mirea.elkinasa.domain.models.Movie;

public interface MovieRepository {
    boolean saveMovie(Movie movie);
    Movie getMovie();
}
