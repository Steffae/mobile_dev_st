package ru.mirea.elkinasa.domain.usecases;

import ru.mirea.elkinasa.domain.repository.FavoriteRepository;

public class AddToFavoritesUseCase {
    private final FavoriteRepository repository;

    public AddToFavoritesUseCase(FavoriteRepository repository) {
        this.repository = repository;
    }

    public boolean execute(String breedId) {
        return repository.addToFavorites(breedId);
    }
}
