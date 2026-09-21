package ru.mirea.elkinasa.mireaproject;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.layers.ObjectEvent;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.user_location.UserLocationObjectListener;
import com.yandex.mapkit.user_location.UserLocationLayer;
import com.yandex.mapkit.user_location.UserLocationView;
import com.yandex.runtime.image.ImageProvider;
import androidx.appcompat.app.AlertDialog;

public class MapActivity extends AppCompatActivity implements UserLocationObjectListener {

    private MapView mapView;
    private UserLocationLayer userLocationLayer;
    private MapObjectCollection mapObjects;

    private static final int REQUEST_LOCATION_PERMISSION = 100;
    private boolean locationPermissionGranted = false;

    // Координаты заведений
    private final Point[] places = {
            new Point(55.7510, 37.6200),  // Кофейня 1
            new Point(55.7550, 37.6100),  // Кофейня 2
            new Point(55.7600, 37.6250),  // Ресторан
            new Point(55.7450, 37.6300),  // Кинотеатр
            new Point(55.7400, 37.6000)   // Парк
    };

    private final String[] placeNames = {
            "Coffee CAT",
            "Coffee KITTEN",
            "Ресторан Кошачий мир",
            "Кинотеатр Котость",
            "Городской парк с котами"
    };

    private final String[] placeDescriptions = {
            "Уютная кофейня с домашней выпечкой и котами\nОткрыто 9:00-21:00",
            "Кофе с собой, свежая выпечка и коты\nОткрыто 8:00-22:00",
            "Итальянская кухня, пицца, паста и коты\nОткрыто 12:00-23:00",
            "Современные фильмы, 3D залы и коты\nРасписание на сайте",
            "Место для прогулок, велодорожки и коты\nКруглосуточно"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Инициализация MapKit
        MapKitFactory.initialize(this);

        setContentView(R.layout.activity_map);

        mapView = findViewById(R.id.mapView);
        mapObjects = mapView.getMap().getMapObjects();

        // Центр карты - Москва
        mapView.getMap().move(
                new CameraPosition(new Point(55.751574, 37.573856), 14.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null
        );

        // Добавление маркеров заведений
        addPlacesMarkers();

        // Проверка разрешения на геолокацию
        checkLocationPermission();

        // Кнопка "Моё местоположение"
        FloatingActionButton fabMyLocation = findViewById(R.id.fabMyLocation);
        fabMyLocation.setOnClickListener(v -> {
            if (locationPermissionGranted && userLocationLayer != null) {
                // Центрируем карту на пользователе
                mapView.getMap().move(
                        new CameraPosition(mapView.getMap().getCameraPosition().getTarget(), 15.0f, 0.0f, 0.0f),
                        new Animation(Animation.Type.SMOOTH, 0),
                        null
                );
                Toast.makeText(this, "Ваше местоположение", Toast.LENGTH_SHORT).show();
            } else if (!locationPermissionGranted) {
                Toast.makeText(this, "Нет разрешения на геолокацию", Toast.LENGTH_SHORT).show();
                checkLocationPermission();
            } else {
                Toast.makeText(this, "Местоположение не определено", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addPlacesMarkers() {
        for (int i = 0; i < places.length; i++) {
            final int index = i;

            PlacemarkMapObject marker = mapObjects.addPlacemark(
                    places[i],
                    ImageProvider.fromResource(this, android.R.drawable.btn_star_big_on)
            );

            // Открываем диалоговое окно
            marker.addTapListener((mapObject, point) -> {
                showPlaceDialog(placeNames[index], placeDescriptions[index]);
                return true;
            });
        }
    }

    private void showPlaceDialog(String title, String description) {
        // Создаём диалоговое окно
        androidx.appcompat.app.AlertDialog dialog = new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(description)
                .setPositiveButton("Закрыть", (d, which) -> d.dismiss())
                .setIcon(android.R.drawable.btn_star_big_on)
                .create();

        dialog.show();
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
            setupUserLocation();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        }
    }

    private void setupUserLocation() {
        userLocationLayer = MapKitFactory.getInstance().createUserLocationLayer(mapView.getMapWindow());
        userLocationLayer.setObjectListener(this);
        userLocationLayer.setVisible(true);
        userLocationLayer.setHeadingEnabled(true);
    }

    @Override
    public void onObjectAdded(@NonNull UserLocationView userLocationView) {
        userLocationView.getPin().setIcon(
                ImageProvider.fromResource(this, android.R.drawable.ic_menu_mylocation)
        );
        userLocationView.getAccuracyCircle().setFillColor(0x4400FF00);
    }

    @Override
    public void onObjectRemoved(@NonNull UserLocationView userLocationView) {}

    @Override
    public void onObjectUpdated(@NonNull UserLocationView userLocationView, @NonNull ObjectEvent objectEvent) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationPermissionGranted = true;
                setupUserLocation();
            } else {
                Toast.makeText(this, "Разрешение на геолокацию не получено", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();
        MapKitFactory.getInstance().onStart();
    }

    @Override
    protected void onStop() {
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }
}