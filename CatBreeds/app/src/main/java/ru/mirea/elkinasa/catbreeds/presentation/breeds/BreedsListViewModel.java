package ru.mirea.elkinasa.catbreeds.presentation.breeds;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ru.mirea.elkinasa.domain.models.BreedItem;
import ru.mirea.elkinasa.domain.models.CatBreed;
import ru.mirea.elkinasa.domain.repository.CatRepository;
import ru.mirea.elkinasa.domain.repository.FavoriteRepository;
import ru.mirea.elkinasa.domain.usecases.AddToFavoritesUseCase;
import ru.mirea.elkinasa.domain.usecases.RemoveFromFavoritesUseCase;

public class BreedsListViewModel extends ViewModel {

    private static final String TAG = "BreedsListViewModel";

    private final CatRepository catRepository;
    private final FavoriteRepository favoriteRepository;

    // LiveData создаём тут
    private final MutableLiveData<List<CatBreed>> breedsLive = new MutableLiveData<>();
    private final MutableLiveData<List<String>> favoritesLive = new MutableLiveData<>();

    // MediatorLiveData
    private final MediatorLiveData<List<BreedItem>> result = new MediatorLiveData<>();

    public BreedsListViewModel(CatRepository catRepository,
                               FavoriteRepository favoriteRepository) {
        this.catRepository = catRepository;
        this.favoriteRepository = favoriteRepository;
        Log.d(TAG, "BreedsListViewModel created");

        // Загружаем начальные значения
        breedsLive.setValue(catRepository.getAllBreeds());
        favoritesLive.setValue(favoriteRepository.getFavoriteIds());

        // Слушаем оба источника
        result.addSource(breedsLive, breeds -> rebuild());
        result.addSource(favoritesLive, ids -> rebuild());
    }

    public LiveData<List<BreedItem>> getBreedsWithFavorite() {
        return result;
    }

    private void rebuild() {
        List<CatBreed> breeds = breedsLive.getValue();
        List<String> ids = favoritesLive.getValue();
        if (breeds == null || ids == null) return;

        Set<String> favSet = new HashSet<>(ids);
        List<BreedItem> items = new ArrayList<>();
        for (CatBreed b : breeds) {
            items.add(new BreedItem(b, favSet.contains(b.getId())));
        }
        result.setValue(items);
    }

    public void toggleFavorite(CatBreed breed) {
        List<String> ids = favoritesLive.getValue();
        if (ids == null) ids = new ArrayList<>();
        Set<String> favSet = new HashSet<>(ids);

        if (favSet.contains(breed.getId())) {
            new RemoveFromFavoritesUseCase(favoriteRepository).execute(breed.getId());
            favSet.remove(breed.getId());
        } else {
            new AddToFavoritesUseCase(favoriteRepository).execute(breed.getId());
            favSet.add(breed.getId());
        }

        favoritesLive.setValue(new ArrayList<>(favSet));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d(TAG, "BreedsListViewModel cleared");
    }
}