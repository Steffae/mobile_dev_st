package ru.mirea.elkinasa.movieproject.data.storage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import ru.mirea.elkinasa.movieproject.domain.models.Movie;

public class SharedPrefMovieStorage implements MovieStorage {

    private static final String SHARED_PREFS_NAME = "shared_prefs_name";
    private static final String KEY_NAME = "movie_name";
    private static final String KEY_ID   = "movie_id";

    private final SharedPreferences sharedPreferences;

    public SharedPrefMovieStorage(Context context) {
        sharedPreferences = context.getSharedPreferences(
                SHARED_PREFS_NAME, Context.MODE_PRIVATE);
    }

    @SuppressLint("CommitPrefEdits")
    @Override
    public boolean save(Movie movie) {
        sharedPreferences.edit()
                .putString(KEY_NAME, movie.getName())
                .putInt(KEY_ID, movie.getId())
                .commit();
        return true;
    }

    @Override
    public Movie get() {
        String name = sharedPreferences.getString(KEY_NAME, "unknown");
        int id = sharedPreferences.getInt(KEY_ID, -1);
        return new Movie(id, name);
    }
}
