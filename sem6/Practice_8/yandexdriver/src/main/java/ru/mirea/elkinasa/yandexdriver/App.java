package ru.mirea.elkinasa.yandexdriver;

import android.app.Application;
import com.yandex.mapkit.MapKitFactory;

public class App extends Application {
    private final String MAPKIT_API_KEY = "cec3dba7-c84a-4ac0-b5df-40d0396eee5e";

    @Override
    public void onCreate() {
        super.onCreate();
        MapKitFactory.setApiKey(MAPKIT_API_KEY);
    }
}
