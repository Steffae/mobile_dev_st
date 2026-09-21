package ru.mirea.elkinasa.dialogadvanced;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        tvResult = findViewById(R.id.tvResult);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Метод для вызова диалога выбора времени
    public void onClickShowTimeDialog(View view) {
        MyTimeDialogFragment timeDialog = new MyTimeDialogFragment();
        timeDialog.show(getSupportFragmentManager(), "time_dialog");
    }

    // Метод для вызова диалога выбора даты
    public void onClickShowDateDialog(View view) {
        MyDateDialogFragment dateDialog = new MyDateDialogFragment();
        dateDialog.show(getSupportFragmentManager(), "date_dialog");
    }

    // Метод для вызова диалога прогресса
    public void onClickShowProgressDialog(View view) {
        MyProgressDialogFragment progressDialog = new MyProgressDialogFragment();
        progressDialog.show(getSupportFragmentManager(), "progress_dialog");
    }

    // Обработка выбранного времени и snackbar
    public void onTimeSet(int hourOfDay, int minute) {
        String time = String.format("Выбрано время: %02d:%02d", hourOfDay, minute);
        tvResult.setText(time);

        Snackbar.make(findViewById(android.R.id.content), time, Snackbar.LENGTH_LONG).show();
    }

    // Обработка выбранной даты
    public void onDateSet(int year, int month, int dayOfMonth) {
        String date = String.format("Выбрана дата: %02d.%02d.%d", dayOfMonth, month + 1, year);
        tvResult.setText(date);
        Toast.makeText(this, date, Toast.LENGTH_SHORT).show();
    }

    // Обработка завершения прогресса
    public void onProgressComplete() {
        tvResult.setText("Загрузка завершена!");
        Toast.makeText(this, "Загрузка завершена!", Toast.LENGTH_SHORT).show();
    }
}