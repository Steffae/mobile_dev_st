package ru.mirea.elkinasa.yandexmaps;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.layers.ObjectEvent;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.user_location.UserLocationObjectListener;
import com.yandex.mapkit.user_location.UserLocationView;
import com.yandex.runtime.image.ImageProvider;

import ru.mirea.elkinasa.yandexmaps.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements UserLocationObjectListener {

    private ActivityMainBinding binding;
    private static final int LOCATION_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MapKitFactory.initialize(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Настройка карты (начальная позиция - Москва)
        binding.mapview.getMap().move(
                new CameraPosition(new Point(55.751574, 37.573856), 11.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null
        );

        // Проверка разрешений
        checkLocationPermission();
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_CODE);
        } else {
            setupUserLocation();
        }
    }

    private void setupUserLocation() {
        com.yandex.mapkit.user_location.UserLocationLayer userLocationLayer =
                MapKitFactory.getInstance().createUserLocationLayer(binding.mapview.getMapWindow());
        userLocationLayer.setObjectListener(this);
        userLocationLayer.setVisible(true);
        userLocationLayer.setHeadingEnabled(true);
    }

    @Override
    public void onObjectAdded(@NonNull UserLocationView userLocationView) {
        // Установка иконки при определении местоположения
        userLocationView.getPin().setIcon(
                ImageProvider.fromResource(this, android.R.drawable.ic_menu_mylocation)
        );
        // Добавление круга точности
        userLocationView.getAccuracyCircle().setFillColor(0x4400FF00);
    }

    @Override
    public void onObjectRemoved(@NonNull UserLocationView userLocationView) {
        // Метод из интерфейса, обязателен
    }

    @Override
    public void onObjectUpdated(@NonNull UserLocationView userLocationView, @NonNull ObjectEvent objectEvent) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setupUserLocation();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        binding.mapview.onStart();
        MapKitFactory.getInstance().onStart();
    }

    @Override
    protected void onStop() {
        binding.mapview.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }
}