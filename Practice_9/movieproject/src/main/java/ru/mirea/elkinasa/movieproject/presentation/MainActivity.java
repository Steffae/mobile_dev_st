package ru.mirea.elkinasa.movieproject.presentation;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import ru.mirea.elkinasa.movieproject.R;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ru.mirea.elkinasa.movieproject.data.repository.MovieRepositoryImpl;
import ru.mirea.elkinasa.movieproject.domain.models.Movie;
import ru.mirea.elkinasa.movieproject.domain.repository.MovieRepository;
import ru.mirea.elkinasa.movieproject.domain.usecases.GetFavoriteFilmUseCase;
import ru.mirea.elkinasa.movieproject.domain.usecases.SaveMovieToFavoriteUseCase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editTextMovie = findViewById(R.id.editTextMovie);
        TextView textViewMovie = findViewById(R.id.textViewMovie);
        Button buttonGetMovie  = findViewById(R.id.buttonGetMovie);
        Button buttonSaveMovie = findViewById(R.id.buttonSaveMovie);

        // Внедрение зависимости — реализация репозитория передаётся в domain-слой
        MovieRepository movieRepository = new MovieRepositoryImpl(this);

        buttonSaveMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean result = new SaveMovieToFavoriteUseCase(movieRepository)
                        .execute(new Movie(2, editTextMovie.getText().toString()));
                textViewMovie.setText(String.format("Save result %s", result));
            }
        });

        buttonGetMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Movie movie = new GetFavoriteFilmUseCase(movieRepository).execute();
                textViewMovie.setText(String.format("Save result %s", movie.getName()));
            }
        });
    }
}