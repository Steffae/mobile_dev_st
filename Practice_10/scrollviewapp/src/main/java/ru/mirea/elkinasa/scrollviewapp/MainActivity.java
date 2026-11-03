package ru.mirea.elkinasa.scrollviewapp;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout wrapper = findViewById(R.id.wrapper);

        long currentValue = 1; // Начинаем с 1

        for (int i = 0; i < 100; i++) {
            // Надуваем (inflate) разметку item
            View view = getLayoutInflater().inflate(R.layout.item, wrapper, false);

            // Находим TextView внутри элемента
            TextView text = view.findViewById(R.id.textView);

            // Устанавливаем текст
            text.setText(String.valueOf(currentValue));

            // Добавляем в контейнер
            wrapper.addView(view);

            // Умножаем на 2 для следующего шага
            currentValue *= 2;
        }
    }
}