package ru.mirea.elkinasa.mireaproject;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.IOException;

import ru.mirea.elkinasa.mireaproject.R;

public class AudioFragment extends Fragment {

    private Button buttonRecord;
    private Button buttonPlay;
    private TextView textViewStatus;

    private boolean isWork = false;
    private String recordFilePath = null;
    private MediaRecorder recorder = null;
    private MediaPlayer player = null;
    private boolean isRecording = false;
    private boolean isPlaying = false;

    // Лаунчер для запроса разрешений
    private final ActivityResultLauncher<String[]> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), permissions -> {
                boolean allGranted = true;
                for (Boolean granted : permissions.values()) {
                    if (!granted) {
                        allGranted = false;
                        break;
                    }
                }
                isWork = allGranted;
                if (!isWork) {
                    Toast.makeText(getContext(), "Нет разрешений для записи аудио", Toast.LENGTH_SHORT).show();
                }
            });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_audio, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buttonRecord = view.findViewById(R.id.buttonRecord);
        buttonPlay = view.findViewById(R.id.buttonPlay);
        textViewStatus = view.findViewById(R.id.textViewStatus);

        // Путь для сохранения аудиофайла
        recordFilePath = (new File(requireContext().getExternalFilesDir(Environment.DIRECTORY_MUSIC), "audiorecord.3gp")).getAbsolutePath();

        // Проверка и запрос разрешений
        checkAndRequestPermissions();

        // Обработчик кнопки записи
        buttonRecord.setOnClickListener(v -> {
            if (!isWork) {
                Toast.makeText(getContext(), "Нет разрешений", Toast.LENGTH_SHORT).show();
                checkAndRequestPermissions();
                return;
            }

            if (!isRecording) {
                startRecording();
            } else {
                stopRecording();
            }
        });

        // Обработчик кнопки воспроизведения
        buttonPlay.setOnClickListener(v -> {
            if (!isWork) {
                Toast.makeText(getContext(), "Нет разрешений", Toast.LENGTH_SHORT).show();
                checkAndRequestPermissions();
                return;
            }

            if (!isPlaying) {
                startPlaying();
            } else {
                stopPlaying();
            }
        });
    }

    private void checkAndRequestPermissions() {
        String[] permissions;

        permissions = new String[]{Manifest.permission.RECORD_AUDIO};

        boolean allGranted = true;
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(requireContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                allGranted = false;
                break;
            }
        }

        if (allGranted) {
            isWork = true;
        } else {
            requestPermissionLauncher.launch(permissions);
        }
    }

    private void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(recordFilePath);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
            recorder.start();
            isRecording = true;
            buttonRecord.setText("Остановить запись");
            buttonPlay.setEnabled(false);
            textViewStatus.setText("Статус: идет запись...");
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Ошибка записи", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopRecording() {
        if (recorder != null) {
            recorder.stop();
            recorder.release();
            recorder = null;
            isRecording = false;
            buttonRecord.setText("Начать запись");
            buttonPlay.setEnabled(true);
            textViewStatus.setText("Статус: запись завершена");
        }
    }

    private void startPlaying() {
        player = new MediaPlayer();
        try {
            player.setDataSource(recordFilePath);
            player.prepare();
            player.start();
            isPlaying = true;
            buttonPlay.setText("Остановить воспроизведение");
            buttonRecord.setEnabled(false);
            textViewStatus.setText("Статус: воспроизведение...");

            player.setOnCompletionListener(mp -> stopPlaying());
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Ошибка воспроизведения", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopPlaying() {
        if (player != null) {
            player.stop();
            player.release();
            player = null;
            isPlaying = false;
            buttonPlay.setText("Воспроизвести");
            buttonRecord.setEnabled(true);
            textViewStatus.setText("Статус: воспроизведение остановлено");
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (isRecording) {
            stopRecording();
        }
        if (isPlaying) {
            stopPlaying();
        }
    }
}