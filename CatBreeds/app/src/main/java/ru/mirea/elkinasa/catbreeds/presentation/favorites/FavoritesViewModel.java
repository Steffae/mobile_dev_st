package ru.mirea.elkinasa.catbreeds.presentation.favorites;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.List;
import ru.mirea.elkinasa.domain.models.BreedItem;
import ru.mirea.elkinasa.domain.models.CatBreed;
import ru.mirea.elkinasa.domain.models.User;
import ru.mirea.elkinasa.domain.repository.AuthRepository;
import ru.mirea.elkinasa.domain.repository.FavoriteRepository;

public class FavoritesViewModel extends ViewModel {
    private final FavoriteRepository repository;
    private final AuthRepository authRepository;
    private final MutableLiveData<List<BreedItem>> items = new MutableLiveData<>();

    public FavoritesViewModel(FavoriteRepository repository, AuthRepository authRepository) {
        this.repository = repository;
        this.authRepository = authRepository;
        load();
    }

    public LiveData<List<BreedItem>> getFavorites() {
        return items;
    }

    private String getCurrentUserId() {
        User user = authRepository.getCurrentUser();
        return user != null ? user.getId() : "guest";
    }

    // Метод для обновления списка из базы данных
    public void refresh() {
        load();
    }

    public void removeFavorite(String breedId) {
        repository.removeFromFavorites(getCurrentUserId(), breedId);
        load(); // Перезагружаем список после удаления
    }

    private void load() {
        List<CatBreed> favBreeds = repository.getAllFavorites(getCurrentUserId());
        List<BreedItem> result = new ArrayList<>();
        for (CatBreed b : favBreeds) {
            result.add(new BreedItem(b, true)); // В избранном всегда true
        }
        items.setValue(result);
    }
}