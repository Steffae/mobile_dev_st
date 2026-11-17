package ru.mirea.elkinasa.catbreeds.presentation.breeds;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import ru.mirea.elkinasa.domain.models.CatBreed;
import ru.mirea.elkinasa.domain.models.User;
import ru.mirea.elkinasa.domain.repository.AuthRepository;
import ru.mirea.elkinasa.domain.repository.FavoriteRepository;

public class BreedDetailsViewModel extends ViewModel {

    private final FavoriteRepository favoriteRepository;
    private final AuthRepository authRepository;
    private final MutableLiveData<Boolean> isFavorite = new MutableLiveData<>();
    private final MutableLiveData<Boolean> showLoginMessage = new MutableLiveData<>();

    private CatBreed breed;

    public BreedDetailsViewModel(FavoriteRepository favoriteRepository, AuthRepository authRepository) {
        this.favoriteRepository = favoriteRepository;
        this.authRepository = authRepository;
    }

    public void init(CatBreed breed) {
        if (this.breed != null) return;
        this.breed = breed;
        checkFavoriteStatus();
    }

    public LiveData<Boolean> getIsFavorite() {
        return isFavorite;
    }

    public LiveData<Boolean> getShowLoginMessage() {
        return showLoginMessage;
    }

    public void onLoginMessageShown() {
        showLoginMessage.setValue(false);
    }

    private void checkFavoriteStatus() {
        User user = authRepository.getCurrentUser();
        String userId = user != null ? user.getId() : "guest";
        isFavorite.setValue(favoriteRepository.isFavorite(userId, breed.getId()));
    }

    public void toggleFavorite() {
        User user = authRepository.getCurrentUser();
        if (user == null) {
            showLoginMessage.setValue(true);
            return;
        }

        String userId = user.getId();
        Boolean currentStatus = isFavorite.getValue();
        if (currentStatus != null && currentStatus) {
            favoriteRepository.removeFromFavorites(userId, breed.getId());
            isFavorite.setValue(false);
        } else {
            favoriteRepository.addToFavorites(userId, breed);
            isFavorite.setValue(true);
        }
    }
}