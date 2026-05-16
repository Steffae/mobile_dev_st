package ru.mirea.elkinasa.notebook;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import ru.mirea.elkinasa.notebook.databinding.ActivityMainBinding;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private static final int REQUEST_CODE_PERMISSION = 100;
    private boolean isWork = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Проверка разрешений
        checkPermissions();

        binding.buttonSave.setOnClickListener(v -> {
            if (isWork) {
                saveToFile();
            } else {
                Toast.makeText(this, "Нет разрешений", Toast.LENGTH_SHORT).show();
                checkPermissions();
            }
        });

        binding.buttonLoad.setOnClickListener(v -> {
            if (isWork) {
                loadFromFile();
            } else {
                Toast.makeText(this, "Нет разрешений", Toast.LENGTH_SHORT).show();
                checkPermissions();
            }
        });
    }

    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android 13+ - не требуется WRITE_EXTERNAL_STORAGE
            isWork = true;
        } else {
            int storagePermissionStatus = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (storagePermissionStatus == PackageManager.PERMISSION_GRANTED) {
                isWork = true;
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_PERMISSION);
            }
        }
    }

    private void saveToFile() {
        String fileName = binding.editTextFileName.getText().toString().trim();
        String quote = binding.editTextQuote.getText().toString().trim();

        if (fileName.isEmpty() || quote.isEmpty()) {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // Путь к публичной директории Documents
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
            File file = new File(path, fileName + ".txt");

            FileOutputStream fileOutputStream = new FileOutputStream(file);
            OutputStreamWriter writer = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);
            writer.write(quote);
            writer.close();

            binding.textViewStatus.setText("Статус: файл сохранён в Documents/" + fileName + ".txt");
            Toast.makeText(this, "Файл сохранён", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
            binding.textViewStatus.setText("Статус: ошибка сохранения");
            Toast.makeText(this, "Ошибка: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void loadFromFile() {
        String fileName = binding.editTextFileName.getText().toString().trim();

        if (fileName.isEmpty()) {
            Toast.makeText(this, "Введите название файла", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
            File file = new File(path, fileName + ".txt");

            if (!file.exists()) {
                Toast.makeText(this, "Файл не найден", Toast.LENGTH_SHORT).show();
                binding.textViewStatus.setText("Статус: файл не найден");
                return;
            }

            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(inputStreamReader);

            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
            reader.close();

            binding.editTextQuote.setText(content.toString());
            binding.textViewStatus.setText("Статус: файл загружен из Documents/" + fileName + ".txt");
            Toast.makeText(this, "Файл загружен", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
            binding.textViewStatus.setText("Статус: ошибка загрузки");
            Toast.makeText(this, "Ошибка: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            isWork = grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
            if (!isWork) {
                Toast.makeText(this, "Разрешение не получено", Toast.LENGTH_SHORT).show();
            }
        }
    }
}