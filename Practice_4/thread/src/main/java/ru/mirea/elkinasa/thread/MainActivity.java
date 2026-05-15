package ru.mirea.elkinasa.thread;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import java.util.Arrays;
import ru.mirea.elkinasa.thread.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        TextView infoTextView = binding.textViewInfo;
        Thread mainThread = Thread.currentThread();
        infoTextView.setText("Имя текущего потока: " + mainThread.getName());
        // Меняем имя и выводим в текстовом поле
        mainThread.setName("МОЙ НОМЕР ГРУППЫ: 8, НОМЕР ПО СПИСКУ: 8, МОЙ ЛЮБИМЫЙ ФИЛЬМ: Человек-паук");
        infoTextView.append("\n Новое имя потока: " + mainThread.getName());
        Log.d(MainActivity.class.getSimpleName(), "Stack: " + Arrays.toString(mainThread.getStackTrace()));

        Log.d(MainActivity.class.getSimpleName(), "Group: " + mainThread.getThreadGroup());

        binding.buttonMirea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    public void run() {
                        int numberThread = counter++;
                        Log.d("ThreadProject", String.format("Запущен поток № %d студентом группы № %s номер по списку № %d ", numberThread, "БСБО-08-23", -1));
                        long endTime = System.currentTimeMillis() + 20 * 1000;
                        while (System.currentTimeMillis() < endTime) {
                            synchronized (this) {
                                try {
                                    wait(endTime - System.currentTimeMillis());
                                    Log.d(MainActivity.class.getSimpleName(), "Endtime: " + endTime);
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }
                            Log.d("ThreadProject", "Выполнен поток № " + numberThread);
                        }
                    }
                }).start();
            }
        });

        // расчет среднего количества пар
        binding.buttonCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pairsStr = binding.editTextTotalPairs.getText().toString();
                String daysStr = binding.editTextStudyDays.getText().toString();

                if (pairsStr.isEmpty() || daysStr.isEmpty()) {
                    binding.textViewResult.setText("Заполните оба поля");
                    return;
                }

                int totalPairs = Integer.parseInt(pairsStr);
                int studyDays = Integer.parseInt(daysStr);

                if (studyDays <= 0) {
                    binding.textViewResult.setText("Количество дней должно быть больше 0");
                    return;
                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // Вычисление среднего
                        double avgPairs = (double) totalPairs / studyDays;
                        final String result = "Среднее количество пар в день: " + String.format("%.2f", avgPairs);

                        Log.d("ThreadProject", "Результат расчета: " + result);

                        // Возвращаем результат в UI-поток
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.textViewResult.setText(result);
                            }
                        });
                    }
                }).start();

                binding.textViewResult.setText("Вычисление...");
            }
        });
    }
}