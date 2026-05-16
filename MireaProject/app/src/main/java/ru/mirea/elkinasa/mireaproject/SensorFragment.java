package ru.mirea.elkinasa.mireaproject;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import ru.mirea.elkinasa.mireaproject.R;

public class SensorFragment extends Fragment implements SensorEventListener {

    private TextView textViewLightValue;
    private TextView textViewRecommendation;
    private SensorManager sensorManager;
    private Sensor lightSensor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sensor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textViewLightValue = view.findViewById(R.id.textViewLightValue);
        textViewRecommendation = view.findViewById(R.id.textViewRecommendation);

        sensorManager = (SensorManager) requireContext().getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (lightSensor != null) {
            sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            float lux = event.values[0];
            textViewLightValue.setText(String.format("Освещённость: %.2f люкс", lux));

            // Логическая задача: рекомендация по освещению
            String recommendation;
            if (lux < 50) {
                recommendation = "Рекомендация: слишком темно! Включите свет для комфортного чтения.";
            } else if (lux < 300) {
                recommendation = "Рекомендация: нормальное освещение для работы за компьютером.";
            } else if (lux < 1000) {
                recommendation = "Рекомендация: комфортное освещение для чтения.";
            } else {
                recommendation = "Рекомендация: очень ярко! Возможно, потребуются солнцезащитные очки.";
            }
            textViewRecommendation.setText(recommendation);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Не используется
    }
}