package ru.mirea.elkinasa.catbreeds.domain.usecases;

import java.util.List;
import ru.mirea.elkinasa.catbreeds.domain.models.CatBreed;
import ru.mirea.elkinasa.catbreeds.domain.repository.FavoriteRepository;

public class GetFavoritesUseCase {
    private final FavoriteRepository repository;

    public GetFavoritesUseCase(FavoriteRepository repository) {
        this.repository = repository;
    }

    public List<CatBreed> execute() {
        return repository.getAllFavorites();
    }
}
