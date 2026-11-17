package ru.mirea.elkinasa.catbreeds.presentation.breeds;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import ru.mirea.elkinasa.data.repository.RepositoryFactory;
import ru.mirea.elkinasa.domain.repository.AuthRepository;
import ru.mirea.elkinasa.domain.repository.CatRepository;
import ru.mirea.elkinasa.domain.repository.FavoriteRepository;

public class BreedsListViewModelFactory implements ViewModelProvider.Factory {

    private final Context context;

    public BreedsListViewModelFactory(Context context) {
        this.context = context.getApplicationContext();
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        CatRepository catRepository = RepositoryFactory.createCatRepository();
        FavoriteRepository favoriteRepository = RepositoryFactory.createFavoriteRepository(context);
        AuthRepository authRepository = RepositoryFactory.createAuthRepository(context);
        return (T) new BreedsListViewModel(catRepository, favoriteRepository, authRepository);
    }
}