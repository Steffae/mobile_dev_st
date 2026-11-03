package ru.mirea.elkinasa.movieproject.presentation;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import ru.mirea.elkinasa.movieproject.R;
import ru.mirea.elkinasa.domain.models.Movie;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private MainViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "MainActivity created");

        EditText text = findViewById(R.id.editTextMovie);
        TextView textView = findViewById(R.id.textViewMovie);

        vm = new ViewModelProvider(this, new ViewModelFactory(this))
                .get(MainViewModel.class);

        vm.getStatusText().observe(this, s -> textView.setText(s));

        findViewById(R.id.buttonSaveMovie).setOnClickListener(v ->
                vm.saveMovie(new Movie(2, text.getText().toString())));

        findViewById(R.id.buttonGetMovie).setOnClickListener(v ->
                vm.loadMovie());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}