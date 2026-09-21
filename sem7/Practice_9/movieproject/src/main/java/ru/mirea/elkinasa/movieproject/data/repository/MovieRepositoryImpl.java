package ru.mirea.elkinasa.movieproject.data.repository;

import android.content.Context;
import android.content.SharedPreferences;

import ru.mirea.elkinasa.movieproject.domain.models.Movie;
import ru.mirea.elkinasa.movieproject.domain.repository.MovieRepository;
import ru.mirea.elkinasa.movieproject.presentation.MainActivity;

public class MovieRepositoryImpl implements MovieRepository {

    private static final String PREFS_NAME = "movie_prefs";
    private static final String KEY_NAME   = "favorite_movie_name";
    private static final String KEY_ID     = "favorite_movie_id";

    private final SharedPreferences prefs;

    // ← Context передаётся сюда, в слой data
    public MovieRepositoryImpl(Context context) {
        this.prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public boolean saveMovie(Movie movie) {
        if (movie.getName() == null || movie.getName().isEmpty()) {
            return false;
        }
        prefs.edit()
                .putInt(KEY_ID, movie.getId())
                .putString(KEY_NAME, movie.getName())
                .apply();
        return true;
    }

    @Override
    public Movie getMovie() {
        int id = prefs.getInt(KEY_ID, 1);
        String name = prefs.getString(KEY_NAME, "Game of Thrones");
        return new Movie(id, name);
    }
}
