package ru.mirea.elkinasa.data.repository;

import android.content.Context;

import ru.mirea.elkinasa.data.storage.room.AppDatabase;
import ru.mirea.elkinasa.domain.repository.AuthRepository;
import ru.mirea.elkinasa.domain.repository.BreedRecognizer;
import ru.mirea.elkinasa.domain.repository.CatRepository;
import ru.mirea.elkinasa.domain.repository.FavoriteRepository;

public class RepositoryFactory {

    public static CatRepository createCatRepository() {
        return new CatRepositoryImpl();
    }

    public static FavoriteRepository createFavoriteRepository(Context context) {
        return new FavoriteRepositoryImpl(
                AppDatabase.getInstance(context).favoriteDao());
    }

    public static AuthRepository createAuthRepository(Context context) {
        return new AuthRepositoryImpl(context);
    }

    public static BreedRecognizer createBreedRecognizer() {
        return new BreedRecognizerImpl();
    }
}