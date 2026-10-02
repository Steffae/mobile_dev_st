package ru.mirea.elkinasa.toastapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private EditText editTextInput;
    private static final String STUDENT_NUMBER = "8";
    private static final String GROUP = "БСБО-08-23";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        editTextInput = findViewById(R.id.editTextInput);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void onClickCountSymbols(View view) {
        // получаем текст из поля ввода
        String inputText = editTextInput.getText().toString();

        // подсчитываем кол-во символов
        int symbolCount = inputText.length();

        // сообщение
        String message = "СТУДЕНТ № " + STUDENT_NUMBER + " ГРУППА " + GROUP +
                " Количество символов - " + symbolCount;

        // выводи сообщение
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}