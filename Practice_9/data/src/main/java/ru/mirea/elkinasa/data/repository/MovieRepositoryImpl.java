package ru.mirea.elkinasa.data.repository;

import java.time.LocalDate;

import ru.mirea.elkinasa.data.storage.MovieStorage;
import ru.mirea.elkinasa.domain.models.Movie;
import ru.mirea.elkinasa.domain.repository.MovieRepository;

public class MovieRepositoryImpl implements MovieRepository {

    private final MovieStorage movieStorage;

    public MovieRepositoryImpl(MovieStorage movieStorage) {
        this.movieStorage = movieStorage;
    }

    @Override
    public boolean saveMovie(ru.mirea.elkinasa.domain.models.Movie movie) {
        return movieStorage.save(mapToStorage(movie));
    }

    @Override
    public ru.mirea.elkinasa.domain.models.Movie getMovie() {
        ru.mirea.elkinasa.data.storage.models.Movie storageMovie = movieStorage.get();
        return mapToDomain(storageMovie);
    }

    // ===== Мапперы =====

    private ru.mirea.elkinasa.data.storage.models.Movie mapToStorage(
            ru.mirea.elkinasa.domain.models.Movie movie) {
        String name = movie.getName();
        return new ru.mirea.elkinasa.data.storage.models.Movie(
                2, name, LocalDate.now().toString());
    }

    private ru.mirea.elkinasa.domain.models.Movie mapToDomain(
            ru.mirea.elkinasa.data.storage.models.Movie movie) {
        return new ru.mirea.elkinasa.domain.models.Movie(
                movie.getId(), movie.getName());
    }
}
