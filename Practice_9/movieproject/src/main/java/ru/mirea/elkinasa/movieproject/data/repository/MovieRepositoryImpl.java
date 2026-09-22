package ru.mirea.elkinasa.movieproject.data.repository;

import ru.mirea.fio.lesson9.data.storage.MovieStorage;
import ru.mirea.fio.lesson9.domain.models.Movie;
import ru.mirea.fio.lesson9.domain.repository.MovieRepository;

public class MovieRepositoryImpl implements MovieRepository {

    private final MovieStorage movieStorage;

    public MovieRepositoryImpl(MovieStorage movieStorage) {
        this.movieStorage = movieStorage;
    }

    @Override
    public boolean saveMovie(Movie movie) {
        return movieStorage.save(movie);
    }

    @Override
    public Movie getMovie() {
        return movieStorage.get();
    }
}
