package ru.mirea.elkinasa.osmmaps;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import ru.mirea.elkinasa.osmmaps.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MapView mapView;
    private MyLocationNewOverlay locationNewOverlay;
    private CompassOverlay compassOverlay;
    private ScaleBarOverlay scaleBarOverlay;

    private static final int REQUEST_PERMISSIONS_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Загрузка конфигурации osmdroid
        Configuration.getInstance().load(
                getApplicationContext(),
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
        );

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mapView = binding.mapView;

        // Настройка карты
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setZoomRounding(true);
        mapView.setMultiTouchControls(true);

        // Установка начального зума
        mapView.getController().setZoom(15.0);

        // Компас
        compassOverlay = new CompassOverlay(this, new InternalCompassOrientationProvider(this), mapView);
        compassOverlay.enableCompass();
        mapView.getOverlays().add(compassOverlay);

        // Метрическая шкала масштаба
        final DisplayMetrics dm = getResources().getDisplayMetrics();
        scaleBarOverlay = new ScaleBarOverlay(mapView);
        scaleBarOverlay.setCentred(true);
        scaleBarOverlay.setScaleBarOffset(dm.widthPixels / 2, 10);
        mapView.getOverlays().add(scaleBarOverlay);

        // Добавление маркеров
        addMarkers();

        // Проверка разрешений и определение местоположения
        checkPermissions();
    }

    private void addMarkers() {
        // Маркер 1: Красная площадь
        Marker marker1 = new Marker(mapView);
        marker1.setPosition(new GeoPoint(55.7537, 37.6213));
        marker1.setTitle("Красная площадь");
        marker1.setSubDescription("Главная площадь Москвы");
        marker1.setOnMarkerClickListener((marker, mapView) -> {
            Toast.makeText(MainActivity.this, "Красная площадь - сердце Москвы", Toast.LENGTH_SHORT).show();
            return true;
        });
        mapView.getOverlays().add(marker1);

        // Маркер 2: Большой театр
        Marker marker2 = new Marker(mapView);
        marker2.setPosition(new GeoPoint(55.7601, 37.6187));
        marker2.setTitle("Большой театр");
        marker2.setSubDescription("Знаменитый театр оперы и балета");
        marker2.setOnMarkerClickListener((marker, mapView) -> {
            Toast.makeText(MainActivity.this, "Большой театр - символ русского искусства", Toast.LENGTH_SHORT).show();
            return true;
        });
        mapView.getOverlays().add(marker2);

        // Маркер 3: МИРЭА
        Marker marker3 = new Marker(mapView);
        marker3.setPosition(new GeoPoint(55.7943, 37.7014));
        marker3.setTitle("РТУ МИРЭА");
        marker3.setSubDescription("Корпус на ул. Стромынка, 20");
        marker3.setOnMarkerClickListener((marker, mapView) -> {
            Toast.makeText(MainActivity.this, "Российский Технологический Университет МИРЭА", Toast.LENGTH_SHORT).show();
            return true;
        });
        mapView.getOverlays().add(marker3);
    }

    private void checkPermissions() {
        // Проверяем наличие разрешения на точное местоположение
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            // Разрешение уже есть
            setupLocation();
        } else {
            // Запрашиваем разрешение
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS_CODE);
        }
    }

    private void setupLocation() {
        // Определение местоположения пользователя
        locationNewOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(this), mapView);
        locationNewOverlay.enableMyLocation();      // Включаем отображение местоположения
        locationNewOverlay.enableFollowLocation();  // Включаем следование за местоположением
        locationNewOverlay.runOnFirstFix(() -> runOnUiThread(() -> {
            // При первом определении координат центрируем карту на пользователе
            if (locationNewOverlay.getMyLocation() != null) {
                mapView.getController().animateTo(locationNewOverlay.getMyLocation());
                Toast.makeText(MainActivity.this, "Ваше местоположение определено", Toast.LENGTH_SHORT).show();
            }
        }));
        mapView.getOverlays().add(locationNewOverlay);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Разрешение получено
                setupLocation();
            } else {
                // Разрешение не получено
                Toast.makeText(this, "Разрешение на геолокацию не получено", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Configuration.getInstance().load(
                getApplicationContext(),
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
        );
        if (mapView != null) {
            mapView.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Configuration.getInstance().save(
                getApplicationContext(),
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
        );
        if (mapView != null) {
            mapView.onPause();
        }
    }
}