package ru.mirea.elkinasa.internalfilestorage;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import ru.mirea.elkinasa.internalfilestorage.databinding.ActivityMainBinding;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private static final String FILE_NAME = "memorable_date.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonSave.setOnClickListener(v -> saveToFile());
        binding.buttonLoad.setOnClickListener(v -> loadFromFile());
    }

    private void saveToFile() {
        String eventName = binding.editTextEventName.getText().toString().trim();
        String eventDescription = binding.editTextEventDescription.getText().toString().trim();

        if (eventName.isEmpty() || eventDescription.isEmpty()) {
            Toast.makeText(this, "Заполните оба поля", Toast.LENGTH_SHORT).show();
            return;
        }

        // Формируем содержимое файла
        String content = "Событие: " + eventName + "\nОписание: " + eventDescription;

        try (FileOutputStream fos = openFileOutput(FILE_NAME, MODE_PRIVATE)) {
            fos.write(content.getBytes());
            binding.textViewStatus.setText("Статус: файл сохранён");
            Toast.makeText(this, "Данные сохранены в файл", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            binding.textViewStatus.setText("Статус: ошибка сохранения");
            Toast.makeText(this, "Ошибка сохранения", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadFromFile() {
        try (FileInputStream fis = openFileInput(FILE_NAME)) {
            byte[] bytes = new byte[fis.available()];
            fis.read(bytes);
            String content = new String(bytes);

            // Разбираем содержимое файла
            String[] lines = content.split("\n");
            if (lines.length >= 2) {
                String eventName = lines[0].replace("Событие: ", "");
                String eventDescription = lines[1].replace("Описание: ", "");
                binding.editTextEventName.setText(eventName);
                binding.editTextEventDescription.setText(eventDescription);
            }

            binding.textViewStatus.setText("Статус: файл загружен");
            Toast.makeText(this, "Данные загружены из файла", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            binding.textViewStatus.setText("Статус: файл не найден");
            Toast.makeText(this, "Файл не найден. Сначала сохраните данные.", Toast.LENGTH_SHORT).show();
        }
    }
}