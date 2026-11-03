package ru.mirea.elkinasa.movieproject.presentation;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.mirea.elkinasa.domain.models.Movie;
import ru.mirea.elkinasa.domain.repository.MovieRepository;
import ru.mirea.elkinasa.domain.usecases.GetFavoriteFilmUseCase;
import ru.mirea.elkinasa.domain.usecases.SaveMovieToFavoriteUseCase;

public class MainViewModel extends ViewModel {

    private static final String TAG = "MainViewModel";

    private final MovieRepository movieRepository;

    // LiveData для статуса (save result / movie name)
    private final MutableLiveData<String> statusText = new MutableLiveData<>();

    public MainViewModel(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
        Log.d(TAG, "MainViewModel created");
    }

    public LiveData<String> getStatusText() {
        return statusText;
    }

    public void saveMovie(Movie movie) {
        Boolean result = new SaveMovieToFavoriteUseCase(movieRepository).execute(movie);
        statusText.setValue(String.format("Save result %s", result));
    }

    public void loadMovie() {
        Movie movie = new GetFavoriteFilmUseCase(movieRepository).execute();
        statusText.setValue(String.format("Save result %s", movie.getName()));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d(TAG, "MainViewModel cleared");
    }
}