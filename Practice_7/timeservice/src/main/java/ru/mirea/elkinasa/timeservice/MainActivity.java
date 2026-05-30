package ru.mirea.elkinasa.timeservice;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView textViewDate, textViewTime, textViewRaw, textViewStatus;
    private final String host = "time.nist.gov";
    private final int port = 13;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewDate = findViewById(R.id.textViewDate);
        textViewTime = findViewById(R.id.textViewTime);
        textViewRaw = findViewById(R.id.textViewRaw);
        textViewStatus = findViewById(R.id.textViewStatus);

        findViewById(R.id.buttonGetTime).setOnClickListener(v -> new GetTimeTask().execute());
    }

    private class GetTimeTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            textViewStatus.setText("Статус: подключение к серверу...");
        }

        @Override
        protected String doInBackground(Void... params) {
            String timeResult = "";
            try {
                Socket socket = new Socket(host, port);
                BufferedReader reader = SocketUtils.getReader(socket);
                reader.readLine(); // игнорируем первую строку
                timeResult = reader.readLine(); // считываем вторую строку
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
                timeResult = "ERROR: " + e.getMessage();
            }
            return timeResult;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            textViewRaw.setText("Raw: " + result);
            textViewStatus.setText("Статус: данные получены");

            if (result != null && !result.startsWith("ERROR")) {
                parseAndDisplayTime(result);
            } else if (result != null && result.startsWith("ERROR")) {
                Toast.makeText(MainActivity.this, "Ошибка подключения", Toast.LENGTH_SHORT).show();
                textViewStatus.setText("Статус: ошибка подключения");
            }
        }

        private void parseAndDisplayTime(String rawData) {
            try {
                String[] parts = rawData.split(" ");

                String dateStr = null;
                String timeStr = null;

                for (int i = 0; i < parts.length; i++) {
                    if (parts[i].matches("\\d{2}-\\d{2}-\\d{2}")) {
                        dateStr = parts[i];
                    }
                    if (parts[i].matches("\\d{2}:\\d{2}:\\d{2}")) {
                        timeStr = parts[i];
                    }
                }

                if (dateStr != null && timeStr != null) {
                    // Формат от сервера: YY-MM-DD
                    String[] dateParts = dateStr.split("-");
                    int yearServer = Integer.parseInt(dateParts[0]); // 26
                    int month = Integer.parseInt(dateParts[1]);      // 05
                    int day = Integer.parseInt(dateParts[2]);        // 29

                    // Преобразуем год: 26 -> 2026
                    int year = 2000 + yearServer;

                    // Парсим время (UTC)
                    String[] timeParts = timeStr.split(":");
                    int hour = Integer.parseInt(timeParts[0]);
                    int minute = Integer.parseInt(timeParts[1]);
                    int second = Integer.parseInt(timeParts[2]);

                    // Прибавляем 3 часа (UTC+3)
                    hour += 3;

                    // Корректировка при переполнении часов
                    if (hour >= 24) {
                        hour -= 24;
                        day += 1;
                    }

                    textViewDate.setText(String.format("Дата: %02d.%02d.%04d", day, month, year));
                    textViewTime.setText(String.format("Время: %02d:%02d:%02d (МСК)", hour, minute, second));
                } else {
                    textViewDate.setText("Дата: не распознана");
                    textViewTime.setText("Время: не распознано");
                }
            } catch (Exception e) {
                e.printStackTrace();
                textViewDate.setText("Дата: ошибка парсинга");
                textViewTime.setText("Время: ошибка парсинга");
            }
        }
    }
}