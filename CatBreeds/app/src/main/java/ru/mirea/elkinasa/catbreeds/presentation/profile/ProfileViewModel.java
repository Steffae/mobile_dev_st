package ru.mirea.elkinasa.catbreeds.presentation.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import ru.mirea.elkinasa.domain.repository.AuthRepository;
import ru.mirea.elkinasa.domain.repository.FavoriteRepository;

public class ProfileViewModel extends ViewModel {
    private final AuthRepository authRepository;
    private final FavoriteRepository favoriteRepository;
    private final MutableLiveData<String> email = new MutableLiveData<>();

    public ProfileViewModel(AuthRepository authRepository, FavoriteRepository favoriteRepository) { // <--- ИЗМЕНЕНО
        this.authRepository = authRepository;
        this.favoriteRepository = favoriteRepository;
        if (authRepository.getCurrentUser() != null) {
            email.setValue(authRepository.getCurrentUser().getEmail());
        } else {
            email.setValue("Гость");
        }
    }

    public LiveData<String> getEmail() {
        return email;
    }

    public void logout() {
        authRepository.logout();
        email.setValue("Гость");
    }
}