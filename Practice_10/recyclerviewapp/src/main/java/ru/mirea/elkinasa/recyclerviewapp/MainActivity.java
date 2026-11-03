package ru.mirea.elkinasa.recyclerviewapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        // Создаем данные
        List<HistoricalEvent> events = getListData();

        // Устанавливаем адаптер
        recyclerView.setAdapter(new EventAdapter(events));

        // Устанавливаем LayoutManager (вертикальный список)
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
    }

    private List<HistoricalEvent> getListData() {
        List<HistoricalEvent> list = new ArrayList<>();

        list.add(new HistoricalEvent("Крещение Руси", "988 год", android.R.drawable.ic_menu_compass));
        list.add(new HistoricalEvent("Куликовская битва", "1380 год", android.R.drawable.ic_menu_myplaces));
        list.add(new HistoricalEvent("Открытие Америки", "1492 год", android.R.drawable.ic_menu_mapmode));
        list.add(new HistoricalEvent("Основание Санкт-Петербурга", "1703 год", android.R.drawable.ic_menu_agenda));
        list.add(new HistoricalEvent("Бородинское сражение", "1812 год", android.R.drawable.ic_menu_manage));
        list.add(new HistoricalEvent("Первый полет в космос", "1961 год", android.R.drawable.ic_menu_send));
        list.add(new HistoricalEvent("Высадка на Луну", "1969 год", android.R.drawable.ic_menu_view));
        list.add(new HistoricalEvent("Изобретение интернета", "1983 год", android.R.drawable.ic_menu_share));

        return list;
    }
}