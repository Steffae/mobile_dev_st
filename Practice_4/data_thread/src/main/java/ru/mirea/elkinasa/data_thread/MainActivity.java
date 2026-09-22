package ru.mirea.elkinasa.data_thread;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import ru.mirea.elkinasa.data_thread.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // 1. runOnUiThread - выполняется немедленно в UI-потоке, принадлежит Activity
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String msg = "1. runOnUiThread - выполнился немедленно";
                appendToLog(msg);
                Log.d(TAG, msg);
            }
        });

        // 2. post - выполняется немедленно в UI-потоке, принадлежит View
        binding.textViewLog.post(new Runnable() {
            @Override
            public void run() {
                String msg = "2. post - выполнился немедленно";
                appendToLog(msg);
                Log.d(TAG, msg);
            }
        });

        // 3. postDelayed - выполняется с задержкой 3 секунды
        binding.textViewLog.postDelayed(new Runnable() {
            @Override
            public void run() {
                String msg = "3. postDelayed - выполнился через 3 секунды";
                appendToLog(msg);
                Log.d(TAG, msg);
            }
        }, 3000);
    }

    private void appendToLog(String message) {
        String currentText = binding.textViewLog.getText().toString();
        String newText = currentText + "\n" + message;
        binding.textViewLog.setText(newText);
    }
}