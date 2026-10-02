package ru.mirea.elkinasa.catbreeds.domain.usecases;

import ru.mirea.elkinasa.catbreeds.domain.repository.FavoriteRepository;

public class AddToFavoritesUseCase {
    private final FavoriteRepository repository;

    public AddToFavoritesUseCase(FavoriteRepository repository) {
        this.repository = repository;
    }

    public boolean execute(String breedId) {
        return repository.addToFavorites(breedId);
    }
}
