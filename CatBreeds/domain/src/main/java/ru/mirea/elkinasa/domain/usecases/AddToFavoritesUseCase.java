package ru.mirea.elkinasa.domain.usecases;

import ru.mirea.elkinasa.domain.models.CatBreed;
import ru.mirea.elkinasa.domain.repository.FavoriteRepository;

public class AddToFavoritesUseCase {
    private final FavoriteRepository repository;

    public AddToFavoritesUseCase(FavoriteRepository repository) {
        this.repository = repository;
    }

    public boolean execute(String userId, CatBreed breed) {
        return repository.addToFavorites(userId, breed);
    }
}