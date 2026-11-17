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
import ru.mirea.elkinasa.domain.models.User;
import ru.mirea.elkinasa.domain.repository.AuthRepository;
import ru.mirea.elkinasa.domain.repository.CatRepository;
import ru.mirea.elkinasa.domain.repository.FavoriteRepository;

public class BreedsListViewModel extends ViewModel {
    private static final String TAG = "BreedsListViewModel";

    private final CatRepository catRepository;
    private final FavoriteRepository favoriteRepository;
    private final AuthRepository authRepository;

    private final MutableLiveData<List<CatBreed>> breedsLive = new MutableLiveData<>();
    private final MutableLiveData<List<String>> favoritesLive = new MutableLiveData<>();
    private final MediatorLiveData<List<BreedItem>> result = new MediatorLiveData<>();
    private final MutableLiveData<Boolean> showLoginMessage = new MutableLiveData<>();

    public BreedsListViewModel(CatRepository catRepository, FavoriteRepository favoriteRepository, AuthRepository authRepository) {
        this.catRepository = catRepository;
        this.favoriteRepository = favoriteRepository;
        this.authRepository = authRepository;

        // Получаем ID текущего пользователя
        String userId = getCurrentUserId();
        favoritesLive.setValue(favoriteRepository.getFavoriteIds(userId));

        catRepository.getAllBreeds(new CatRepository.Callback() {
            @Override
            public void onSuccess(List<CatBreed> breeds) {
                breedsLive.postValue(breeds);
            }

            @Override
            public void onError(String message) {
                Log.e(TAG, "Ошибка сети: " + message);
            }
        });

        result.addSource(breedsLive, breeds -> rebuild());
        result.addSource(favoritesLive, ids -> rebuild());
    }

    private String getCurrentUserId() {
        User user = authRepository.getCurrentUser();
        return user != null ? user.getId() : "guest";
    }

    public LiveData<List<BreedItem>> getBreedsWithFavorite() {
        return result;
    }

    public LiveData<Boolean> getShowLoginMessage() {
        return showLoginMessage;
    }

    public void onLoginMessageShown() {
        showLoginMessage.setValue(false);
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
        User user = authRepository.getCurrentUser();
        if (user == null) {
            showLoginMessage.setValue(true);
            return;
        }

        String userId = user.getId();
        List<String> ids = favoritesLive.getValue();
        if (ids == null) ids = new ArrayList<>();
        Set<String> favSet = new HashSet<>(ids);

        if (favSet.contains(breed.getId())) {
            favoriteRepository.removeFromFavorites(userId, breed.getId());
            favSet.remove(breed.getId());
        } else {
            favoriteRepository.addToFavorites(userId, breed);
            favSet.add(breed.getId());
        }
        favoritesLive.setValue(new ArrayList<>(favSet));
    }

    public void refreshFavorites() {
        User user = authRepository.getCurrentUser();
        String userId = user != null ? user.getId() : "guest";
        favoritesLive.setValue(favoriteRepository.getFavoriteIds(userId));
    }
}