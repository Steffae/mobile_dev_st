package ru.mirea.elkinasa.mireaproject;

import android.app.Application;
import com.yandex.mapkit.MapKitFactory;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MapKitFactory.setApiKey("37bc9cf1-179e-49a2-b571-bd86252f4ada");
    }
}