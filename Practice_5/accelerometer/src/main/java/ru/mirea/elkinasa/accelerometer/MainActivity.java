package ru.mirea.elkinasa.accelerometer;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import ru.mirea.elkinasa.accelerometer.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private ActivityMainBinding binding;
    private SensorManager sensorManager;
    private Sensor accelerometerSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Получение менеджера датчиков
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // Получение датчика акселерометра
        if (sensorManager != null) {
            accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Регистрация слушателя датчика
        if (accelerometerSensor != null) {
            sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Отмена регистрации для экономии ресурсов
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            // values[0] - ускорение по оси X (боковое, влево/вправо)
            // values[1] - ускорение по оси Y (продольное, вперед/назад)
            // values[2] - ускорение по оси Z (вертикальное, вверх/вниз)
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            binding.textViewX.setText(String.format("X (боковое): %.2f м/с²", x));
            binding.textViewY.setText(String.format("Y (продольное): %.2f м/с²", y));
            binding.textViewZ.setText(String.format("Z (вертикальное): %.2f м/с²", z));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Метод вызывается при изменении точности датчика
    }
}