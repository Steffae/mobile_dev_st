package ru.mirea.elkinasa.domain.usecases;

import ru.mirea.elkinasa.domain.repository.FavoriteRepository;

public class RemoveFromFavoritesUseCase {
    private final FavoriteRepository repository;

    public RemoveFromFavoritesUseCase(FavoriteRepository repository) {
        this.repository = repository;
    }

    public boolean execute(String userId, String breedId) {
        return repository.removeFromFavorites(userId, breedId);
    }
}
