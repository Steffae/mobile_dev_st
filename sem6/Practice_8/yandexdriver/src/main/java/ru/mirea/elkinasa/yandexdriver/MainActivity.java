package ru.mirea.elkinasa.yandexdriver;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.directions.DirectionsFactory;
import com.yandex.mapkit.directions.driving.DrivingOptions;
import com.yandex.mapkit.directions.driving.DrivingRoute;
import com.yandex.mapkit.directions.driving.DrivingRouter;
import com.yandex.mapkit.directions.driving.DrivingSession;
import com.yandex.mapkit.RequestPoint;
import com.yandex.mapkit.RequestPointType;
import com.yandex.mapkit.directions.driving.VehicleOptions;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.runtime.Error;
import com.yandex.runtime.image.ImageProvider;

import java.util.ArrayList;
import java.util.List;

import ru.mirea.elkinasa.yandexdriver.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements DrivingSession.DrivingRouteListener {

    private ActivityMainBinding binding;
    private DrivingRouter drivingRouter;
    private DrivingSession drivingSession;
    private MapObjectCollection mapObjects;

    // Начальная точка маршрута (Москва, Кремль)
    private final Point ROUTE_START_LOCATION = new Point(55.751574, 37.573856);

    // Конечная точка маршрута (любимое заведение - Третьяковская галерея)
    private final Point ROUTE_END_LOCATION = new Point(55.7415, 37.6208);

    // Цвета для различных маршрутов
    private final int[] colors = {0xFF0000FF, 0xFF00FF00, 0xFFFF0000, 0xFFFFA500};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Инициализация MapKit и Directions
        MapKitFactory.initialize(this);
        DirectionsFactory.initialize(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Получение коллекции объектов карты
        mapObjects = binding.mapview.getMap().getMapObjects();

        // Установка камеры на начальную точку
        binding.mapview.getMap().move(
                new CameraPosition(ROUTE_START_LOCATION, 11.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null
        );

        // Добавление маркеров на карту
        addMarkers();

        // Построение маршрута
        submitRequest();
    }

    // Добавление маркера на конечную точку и обработка нажатия
    private void addMarkers() {
        // Создание маркера на конечной точке
        PlacemarkMapObject endMarker = mapObjects.addPlacemark(
                ROUTE_END_LOCATION,
                ImageProvider.fromResource(this, android.R.drawable.ic_menu_info_details)
        );

        // Обработчик нажатия на маркер - отображение информации о заведении
        endMarker.addTapListener((mapObject, point) -> {
            Toast.makeText(MainActivity.this, "Третьяковская галерея\nГлавный музей русского искусства", Toast.LENGTH_LONG).show();
            return false;
        });
    }

    // Отправка запроса на построение маршрута
    private void submitRequest() {
        // Настройка параметров маршрута
        DrivingOptions drivingOptions = new DrivingOptions();
        VehicleOptions vehicleOptions = new VehicleOptions();

        // Установка количества альтернативных маршрутов
        drivingOptions.setRoutesCount(4);

        // Создание списка точек маршрута
        ArrayList<RequestPoint> requestPoints = new ArrayList<>();
        requestPoints.add(new RequestPoint(ROUTE_START_LOCATION, RequestPointType.WAYPOINT, null));
        requestPoints.add(new RequestPoint(ROUTE_END_LOCATION, RequestPointType.WAYPOINT, null));

        // Отправка запроса на сервер
        drivingRouter = DirectionsFactory.getInstance().createDrivingRouter();
        drivingSession = drivingRouter.requestRoutes(requestPoints, drivingOptions, vehicleOptions, this);
    }

    // Обработка полученных маршрутов
    @Override
    public void onDrivingRoutes(@NonNull List<DrivingRoute> list) {
        // Очищаем предыдущие маршруты перед добавлением новых
        mapObjects.clear();

        // Добавляем маркеры заново (так как clear удалил их)
        addMarkers();

        // Отображение каждого маршрута своим цветом
        for (int i = 0; i < list.size(); i++) {
            int color = colors[i];
            mapObjects.addPolyline(list.get(i).getGeometry()).setStrokeColor(color);
        }
    }

    // Обработка ошибки построения маршрута
    @Override
    public void onDrivingRoutesError(@NonNull Error error) {
        Toast.makeText(this, "Ошибка построения маршрута", Toast.LENGTH_SHORT).show();
    }

    // Передача событий жизненного цикла в MapKit
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