package ru.mirea.elkinasa.audiorecord;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.io.File;
import java.io.IOException;
import ru.mirea.elkinasa.audiorecord.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private static final int REQUEST_CODE_PERMISSION = 200;
    private static final String TAG = "AudioRecord";
    private boolean isWork = false;
    private String recordFilePath = null;
    private MediaRecorder recorder = null;
    private MediaPlayer player = null;
    private boolean isRecording = false;
    private boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Путь для сохранения аудиофайла
        recordFilePath = (new File(getExternalFilesDir(Environment.DIRECTORY_MUSIC), "audiorecord.3gp")).getAbsolutePath();

        // Проверка разрешений
        int audioRecordPermissionStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        int storagePermissionStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (audioRecordPermissionStatus == PackageManager.PERMISSION_GRANTED &&
                storagePermissionStatus == PackageManager.PERMISSION_GRANTED) {
            isWork = true;
        } else {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, REQUEST_CODE_PERMISSION);
        }

        // Обработчик кнопки записи
        binding.buttonRecord.setOnClickListener(v -> {
            if (!isWork) {
                Toast.makeText(this, "Нет разрешений", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!isRecording) {
                startRecording();
            } else {
                stopRecording();
            }
        });

        // Обработчик кнопки воспроизведения
        binding.buttonPlay.setOnClickListener(v -> {
            if (!isWork) {
                Toast.makeText(this, "Нет разрешений", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!isPlaying) {
                startPlaying();
            } else {
                stopPlaying();
            }
        });
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
            binding.buttonRecord.setText("Остановить запись");
            binding.buttonPlay.setEnabled(false);
            binding.textViewStatus.setText("Статус: идет запись...");
            Log.d(TAG, "Запись начата");
        } catch (IOException e) {
            Log.e(TAG, "prepare() failed");
            e.printStackTrace();
            Toast.makeText(this, "Ошибка записи", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopRecording() {
        if (recorder != null) {
            recorder.stop();
            recorder.release();
            recorder = null;
            isRecording = false;
            binding.buttonRecord.setText("Начать запись");
            binding.buttonPlay.setEnabled(true);
            binding.textViewStatus.setText("Статус: запись завершена");
            Log.d(TAG, "Запись остановлена");
        }
    }

    private void startPlaying() {
        player = new MediaPlayer();
        try {
            player.setDataSource(recordFilePath);
            player.prepare();
            player.start();
            isPlaying = true;
            binding.buttonPlay.setText("Остановить воспроизведение");
            binding.buttonRecord.setEnabled(false);
            binding.textViewStatus.setText("Статус: воспроизведение...");

            player.setOnCompletionListener(mp -> {
                stopPlaying();
            });
        } catch (IOException e) {
            Log.e(TAG, "prepare() failed");
            e.printStackTrace();
            Toast.makeText(this, "Ошибка воспроизведения", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopPlaying() {
        if (player != null) {
            player.stop();
            player.release();
            player = null;
            isPlaying = false;
            binding.buttonPlay.setText("Воспроизвести");
            binding.buttonRecord.setEnabled(true);
            binding.textViewStatus.setText("Статус: воспроизведение остановлено");
            Log.d(TAG, "Воспроизведение остановлено");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Остановка записи или воспроизведения при выходе из приложения
        if (isRecording) {
            stopRecording();
        }
        if (isPlaying) {
            stopPlaying();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            isWork = grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
            if (!isWork) {
                Toast.makeText(this, "Нужны разрешения для записи аудио", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}