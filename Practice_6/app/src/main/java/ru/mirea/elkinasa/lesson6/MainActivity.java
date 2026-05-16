package ru.mirea.elkinasa.lesson6;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import ru.mirea.elkinasa.lesson6.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "mirea_settings";
    private static final String KEY_GROUP = "GROUP";
    private static final String KEY_NUMBER = "NUMBER";
    private static final String KEY_MOVIE = "MOVIE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Получение экземпляра SharedPreferences
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        // Загрузка сохраненных данных
        loadSavedData();

        // Сохранение данных при нажатии на кнопку
        binding.buttonSave.setOnClickListener(v -> saveData());
    }

    private void loadSavedData() {
        String group = sharedPreferences.getString(KEY_GROUP, "");
        int number = sharedPreferences.getInt(KEY_NUMBER, 0);
        String movie = sharedPreferences.getString(KEY_MOVIE, "");

        binding.editTextGroup.setText(group);
        if (number != 0) {
            binding.editTextNumber.setText(String.valueOf(number));
        }
        binding.editTextMovie.setText(movie);

        if (!group.isEmpty() || number != 0 || !movie.isEmpty()) {
            binding.textViewStatus.setText("Статус: данные загружены из памяти");
        }
    }

    private void saveData() {
        String group = binding.editTextGroup.getText().toString().trim();
        String numberStr = binding.editTextNumber.getText().toString().trim();
        String movie = binding.editTextMovie.getText().toString().trim();

        if (group.isEmpty() || numberStr.isEmpty() || movie.isEmpty()) {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        int number = Integer.parseInt(numberStr);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_GROUP, group);
        editor.putInt(KEY_NUMBER, number);
        editor.putString(KEY_MOVIE, movie);
        editor.apply();

        binding.textViewStatus.setText("Статус: данные сохранены!");
        Toast.makeText(this, "Данные сохранены", Toast.LENGTH_SHORT).show();
    }
}