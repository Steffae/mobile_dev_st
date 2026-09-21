package ru.mirea.elkinasa.workmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.os.Bundle;
import android.view.View;

import ru.mirea.elkinasa.workmanager.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonStartWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBackgroundTask();
            }
        });
    }

    private void startBackgroundTask() {
        binding.textViewStatus.setText("Статус: задача запущена...");

        // Установка условий (наличие интернета)
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .build();

        // Создание WorkRequest с условиями
        WorkRequest uploadWorkRequest = new OneTimeWorkRequest.Builder(UploadWorker.class)
                .setConstraints(constraints)
                .build();

        // Запуск задачи
        WorkManager.getInstance(this).enqueue(uploadWorkRequest);

        binding.textViewStatus.setText("Статус: задача в очереди. Ожидание условий (Wi-Fi)");
    }
}