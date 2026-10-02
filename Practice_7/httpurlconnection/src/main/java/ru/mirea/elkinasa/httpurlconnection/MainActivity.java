package ru.mirea.elkinasa.httpurlconnection;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView textViewIP;
    private TextView textViewCity;
    private TextView textViewRegion;
    private TextView textViewCountry;
    private TextView textViewWeather;
    private TextView textViewTemp;
    private TextView textViewWind;
    private TextView textViewStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewIP = findViewById(R.id.textViewIP);
        textViewCity = findViewById(R.id.textViewCity);
        textViewRegion = findViewById(R.id.textViewRegion);
        textViewCountry = findViewById(R.id.textViewCountry);
        textViewWeather = findViewById(R.id.textViewWeather);
        textViewTemp = findViewById(R.id.textViewTemp);
        textViewWind = findViewById(R.id.textViewWind);
        textViewStatus = findViewById(R.id.textViewStatus);

        // Проверка подключения к интернету
        findViewById(R.id.buttonGetInfo).setOnClickListener(v -> {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                new DownloadPageTask().execute("https://ipinfo.io/json"); // запуск нового потока
            } else {
                Toast.makeText(MainActivity.this, "Нет интернета", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class DownloadPageTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            textViewStatus.setText("Статус: загрузка данных...");
        }

        @Override
        protected String doInBackground(String... urls) {
            String result = downloadIpInfo(urls[0]);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d(TAG, "Response: " + result);
            parseAndDisplay(result);
        }

        // Метод для загрузки данных с сервера
        private String downloadIpInfo(String address) {
            InputStream inputStream = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(address);
                connection = (HttpURLConnection) url.openConnection(); // создаётся экземпляр класса «HttpURLConnection»
                connection.setConnectTimeout(10000);
                connection.setReadTimeout(10000);
                connection.setRequestMethod("GET");
                connection.setDoInput(true);

                int responseCode = connection.getResponseCode(); // 200 ok
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    throw new IOException("HTTP error code: " + responseCode);
                }

                inputStream = connection.getInputStream();
                ByteArrayOutputStream bos = new ByteArrayOutputStream(); // способы чтения потоков данных
                int read = 0;
                byte[] buffer = new byte[1024];
                while ((read = inputStream.read(buffer)) != -1) {
                    bos.write(buffer, 0, read);
                }
                return bos.toString();
            } catch (IOException e) {
                e.printStackTrace();
                return "Ошибка: " + e.getMessage();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        // Парсинг JSON и получение погоды
        private void parseAndDisplay(String jsonResponse) {
            try {
                JSONObject jsonObject = new JSONObject(jsonResponse);
                String ip = jsonObject.getString("ip");
                String city = jsonObject.getString("city");
                String region = jsonObject.getString("region");
                String country = jsonObject.getString("country");
                String loc = jsonObject.getString("loc"); // "55.7558,37.6176"

                double lat = 55.7558;  // Москва по умолчанию
                double lon = 37.6176;

                if (!loc.isEmpty()) {
                    String[] parts = loc.split(",");
                    lat = Double.parseDouble(parts[0]);
                    lon = Double.parseDouble(parts[1]);
                }

                textViewIP.setText("IP-адрес: " + ip);
                textViewCity.setText("Город: " + city);
                textViewRegion.setText("Регион: " + region);
                textViewCountry.setText("Страна: " + country);
                textViewStatus.setText("Статус: получение погоды...");

                // Запрос погоды по координатам
                new GetWeatherTask().execute(lat, lon);

            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "Ошибка парсинга: " + e.getMessage());
                textViewStatus.setText("Статус: ошибка парсинга - " + e.getMessage());
            }
        }
    }

    // Класс для получения погоды
    private class GetWeatherTask extends AsyncTask<Double, Void, String> {

        private double lat;
        private double lon;

        @Override
        protected String doInBackground(Double... params) {
            lat = params[0];
            lon = params[1];
            return downloadWeatherInfo();
        }

        private String downloadWeatherInfo() {
            InputStream inputStream = null;
            HttpURLConnection connection = null;
            try {
                String urlString = String.format(Locale.US,
                        "https://api.open-meteo.com/v1/forecast?latitude=%.4f&longitude=%.4f&current_weather=true",
                        lat, lon);
                URL url = new URL(urlString);
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(10000);
                connection.setReadTimeout(10000);
                connection.setRequestMethod("GET");
                connection.setDoInput(true);

                int responseCode = connection.getResponseCode();
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    throw new IOException("HTTP error code: " + responseCode);
                }

                inputStream = connection.getInputStream();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                int read = 0;
                byte[] buffer = new byte[1024];
                while ((read = inputStream.read(buffer)) != -1) {
                    bos.write(buffer, 0, read);
                }
                return bos.toString();
            } catch (IOException e) {
                e.printStackTrace();
                return "Ошибка: " + e.getMessage();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        // из ответа формируется JSON объект и извлекается значение
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                JSONObject json = new JSONObject(result);
                JSONObject currentWeather = json.optJSONObject("current_weather");
                if (currentWeather != null) {
                    double temperature = currentWeather.optDouble("temperature", 0);
                    double windSpeed = currentWeather.optDouble("windspeed", 0);
                    int weatherCode = currentWeather.optInt("weathercode", 0);

                    textViewTemp.setText("Температура: " + temperature + "°C");
                    textViewWind.setText("Скорость ветра: " + windSpeed + " км/ч");
                    textViewWeather.setText("Погода: " + getWeatherDescription(weatherCode));
                    textViewStatus.setText("Статус: данные получены");
                } else {
                    textViewWeather.setText("Погода: данные не найдены");
                    textViewStatus.setText("Статус: ошибка получения погоды");
                }
            } catch (Exception e) {
                e.printStackTrace();
                textViewWeather.setText("Погода: ошибка");
                textViewStatus.setText("Статус: ошибка");
            }
        }

        private String getWeatherDescription(int code) {
            // Описание кодов погоды по методичке open-meteo
            switch (code) {
                case 0: return "Ясно";
                case 1: case 2: case 3: return "Переменная облачность";
                case 45: case 48: return "Туман";
                case 51: case 53: case 55: return "Морось";
                case 61: case 63: case 65: return "Дождь";
                case 71: case 73: case 75: return "Снег";
                case 80: case 81: case 82: return "Ливень";
                default: return "Облачно";
            }
        }
    }
}