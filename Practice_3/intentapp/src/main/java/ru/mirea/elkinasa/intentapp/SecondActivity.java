package ru.mirea.elkinasa.intentapp;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        tvResult = findViewById(R.id.tvResult);

        // Получаем переданное время
        Intent intent = getIntent();
        String time = intent.getStringExtra("time_key");

        // Номер по списку в группе
        int myNumber = 8;
        int square = myNumber * myNumber;

        String resultText = "КВАДРАТ ЗНАЧЕНИЯ МОЕГО НОМЕРА ПО СПИСКУ В ГРУППЕ СОСТАВЛЯЕТ " + square + ", а текущее время " + time;
        tvResult.setText(resultText);
    }
}