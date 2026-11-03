package ru.mirea.elkinasa.catbreeds.presentation;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import ru.mirea.elkinasa.catbreeds.R;
import ru.mirea.elkinasa.catbreeds.presentation.breeds.BreedsListViewModel;
import ru.mirea.elkinasa.catbreeds.presentation.breeds.BreedsListViewModelFactory;
import ru.mirea.elkinasa.domain.models.BreedItem;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BreedsListViewModel vm = new ViewModelProvider(
                this, new BreedsListViewModelFactory(this))
                .get(BreedsListViewModel.class);

        vm.getBreedsWithFavorite().observe(this, items -> {
            Log.d(TAG, "Получено пород: " + items.size());
            for (BreedItem item : items) {
                Log.d(TAG, "  → " + item.getBreed().getName()
                        + (item.isFavorite() ? " ❤" : ""));
            }
        });

        // Тест: добавим первую породу в избранное через 2 секунды
        vm.getBreedsWithFavorite().observe(this, items -> {
            if (!items.isEmpty() && !items.get(0).isFavorite()) {
                vm.toggleFavorite(items.get(0).getBreed());
            }
        });
    }
}