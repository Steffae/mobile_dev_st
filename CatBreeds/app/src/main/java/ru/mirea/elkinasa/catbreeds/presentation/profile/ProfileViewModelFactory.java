package ru.mirea.elkinasa.catbreeds.presentation.profile;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import ru.mirea.elkinasa.data.repository.RepositoryFactory;
import ru.mirea.elkinasa.domain.repository.AuthRepository;
import ru.mirea.elkinasa.domain.repository.FavoriteRepository;

public class ProfileViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;

    public ProfileViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        AuthRepository authRepo = RepositoryFactory.createAuthRepository(context);
        FavoriteRepository favRepo = RepositoryFactory.createFavoriteRepository(context);
        return (T) new ProfileViewModel(authRepo, favRepo);
    }
}