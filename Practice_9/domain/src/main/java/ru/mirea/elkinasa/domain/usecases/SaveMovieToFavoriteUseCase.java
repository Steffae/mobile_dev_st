package ru.mirea.elkinasa.domain.usecases;

import ru.mirea.elkinasa.domain.models.Movie;
import ru.mirea.elkinasa.domain.repository.MovieRepository;

public class SaveMovieToFavoriteUseCase {
    private MovieRepository movieRepository;

    public SaveMovieToFavoriteUseCase(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public boolean execute(Movie movie) {
        return movieRepository.saveMovie(movie);
    }
}
