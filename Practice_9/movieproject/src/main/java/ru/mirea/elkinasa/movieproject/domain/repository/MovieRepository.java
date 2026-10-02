package ru.mirea.elkinasa.movieproject.domain.repository;

import ru.mirea.elkinasa.movieproject.domain.models.Movie;

public interface MovieRepository {
    boolean saveMovie(Movie movie);
    Movie getMovie();
}
