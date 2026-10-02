package ru.mirea.elkinasa.lesson5;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.widget.SimpleAdapter;
import ru.mirea.elkinasa.lesson5.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Получение менеджера датчиков
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // Получение списка всех датчиков
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);

        // Подготовка данных для отображения в списке
        ArrayList<HashMap<String, Object>> arrayList = new ArrayList<>();

        for (int i = 0; i < sensors.size(); i++) {
            HashMap<String, Object> sensorTypeList = new HashMap<>();
            sensorTypeList.put("Name", sensors.get(i).getName());
            sensorTypeList.put("Vendor", sensors.get(i).getVendor());
            sensorTypeList.put("Power", sensors.get(i).getPower() + " mA");
            sensorTypeList.put("Version", sensors.get(i).getVersion());
            arrayList.add(sensorTypeList);
        }

        // создаем адаптер и устанавливаем тип адаптера - отображение двух полей
        SimpleAdapter adapter = new SimpleAdapter(
                this,
                arrayList,
                android.R.layout.simple_list_item_2,
                new String[]{"Name", "Vendor"},
                new int[]{android.R.id.text1, android.R.id.text2}
        );

        binding.listViewSensors.setAdapter(adapter);
    }
}