package ru.mirea.elkinasa.catbreeds.domain.usecases;

import ru.mirea.elkinasa.catbreeds.domain.repository.FavoriteRepository;

public class RemoveFromFavoritesUseCase {
    private final FavoriteRepository repository;

    public RemoveFromFavoritesUseCase(FavoriteRepository repository) {
        this.repository = repository;
    }

    public boolean execute(String breedId) {
        return repository.removeFromFavorites(breedId);
    }
}
