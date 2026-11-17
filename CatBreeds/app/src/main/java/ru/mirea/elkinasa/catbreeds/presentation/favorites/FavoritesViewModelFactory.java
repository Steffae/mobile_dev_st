package ru.mirea.elkinasa.catbreeds.presentation.favorites;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import ru.mirea.elkinasa.data.repository.RepositoryFactory;
import ru.mirea.elkinasa.domain.repository.AuthRepository;
import ru.mirea.elkinasa.domain.repository.FavoriteRepository;

public class FavoritesViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;
    public FavoritesViewModelFactory(Context context) { this.context = context; }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        FavoriteRepository repo = RepositoryFactory.createFavoriteRepository(context);
        AuthRepository authRepo = RepositoryFactory.createAuthRepository(context);
        return (T) new FavoritesViewModel(repo, authRepo);
    }
}