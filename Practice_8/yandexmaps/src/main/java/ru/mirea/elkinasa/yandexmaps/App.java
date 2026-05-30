package ru.mirea.elkinasa.yandexmaps;

import android.app.Application;

import com.yandex.mapkit.MapKitFactory;

public class App extends Application {

    private final String MAPKIT_API_KEY = "cb6f8269-fc29-4fae-9b5d-eadb670a87b2";

    @Override
    public void onCreate() {
        super.onCreate();
        MapKitFactory.setApiKey(MAPKIT_API_KEY);
    }
}
