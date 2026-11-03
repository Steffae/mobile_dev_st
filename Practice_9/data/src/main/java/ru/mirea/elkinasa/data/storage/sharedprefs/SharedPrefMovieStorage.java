package ru.mirea.elkinasa.data.storage.sharedprefs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import java.time.LocalDate;

import ru.mirea.elkinasa.data.storage.MovieStorage;
import ru.mirea.elkinasa.data.storage.models.Movie;

public class SharedPrefMovieStorage implements MovieStorage {

    private static final String SHARED_PREFS_NAME = "shared_prefs_name";
    private static final String KEY_NAME = "movie_name";
    private static final String KEY_ID   = "movie_id";
    private static final String KEY_DATE = "movie_date";

    private final SharedPreferences sharedPreferences;

    public SharedPrefMovieStorage(Context context) {
        this.sharedPreferences = context.getSharedPreferences(
                SHARED_PREFS_NAME, Context.MODE_PRIVATE);
    }

    @SuppressLint("CommitPrefEdits")
    @Override
    public boolean save(Movie movie) {
        sharedPreferences.edit()
                .putInt(KEY_ID, movie.getId())
                .putString(KEY_NAME, movie.getName())
                .putString(KEY_DATE, movie.getLocalDate())
                .commit();
        return true;
    }

    @Override
    public Movie get() {
        int id = sharedPreferences.getInt(KEY_ID, -1);
        String name = sharedPreferences.getString(KEY_NAME, "unknown");
        String date = sharedPreferences.getString(KEY_DATE, LocalDate.now().toString());
        return new Movie(id, name, date);
    }
}
