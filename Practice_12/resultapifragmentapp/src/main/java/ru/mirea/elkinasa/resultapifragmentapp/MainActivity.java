package ru.mirea.elkinasa.resultapifragmentapp;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements FragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction tx = fm.beginTransaction();
            tx.replace(R.id.fragment_container, new DataFragment());
            tx.commit();
        }
    }

    // Реализация метода из интерфейса FragmentListener
    @Override
    public void sendResult(String message) {
        Log.d(MainActivity.class.getSimpleName(), "message: " + message);
    }
}