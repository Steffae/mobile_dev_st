package ru.mirea.elkinasa.catbreeds.presentation.breeds;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ru.mirea.elkinasa.catbreeds.R;

public class BreedsListActivity extends AppCompatActivity {

    private static final String TAG = "BreedsListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breeds_list);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        BreedsAdapter adapter = new BreedsAdapter();

        // Настраиваем RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Инициализируем ViewModel
        BreedsListViewModel viewModel = new ViewModelProvider(
                this, new BreedsListViewModelFactory(this))
                .get(BreedsListViewModel.class);

        // Подписываемся на LiveData (данные из сети + БД)
        viewModel.getBreedsWithFavorite().observe(this, items -> {
            Log.d(TAG, "Обновлен список, элементов: " + items.size());
            adapter.setItems(items); // Передаем данные в адаптер
        });

        // Обработка нажатия на сердечко (через ViewModel)
        adapter.setListener(item -> {
            viewModel.toggleFavorite(item.getBreed());
        });
    }
}