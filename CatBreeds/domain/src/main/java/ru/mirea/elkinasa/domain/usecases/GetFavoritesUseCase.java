package ru.mirea.elkinasa.domain.usecases;

import java.util.List;
import ru.mirea.elkinasa.domain.models.CatBreed;
import ru.mirea.elkinasa.domain.repository.FavoriteRepository;

public class GetFavoritesUseCase {
    private final FavoriteRepository repository;

    public GetFavoritesUseCase(FavoriteRepository repository) {
        this.repository = repository;
    }

    public List<CatBreed> execute() {
        return repository.getAllFavorites();
    }
}
