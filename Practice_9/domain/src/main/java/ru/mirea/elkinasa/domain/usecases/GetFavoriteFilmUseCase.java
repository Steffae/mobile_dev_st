package ru.mirea.elkinasa.domain.usecases;

import ru.mirea.elkinasa.domain.models.Movie;
import ru.mirea.elkinasa.domain.repository.MovieRepository;

public class GetFavoriteFilmUseCase {
    private MovieRepository movieRepository;

    public GetFavoriteFilmUseCase(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Movie execute() {
        return movieRepository.getMovie();
    }
}
