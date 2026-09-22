package ru.mirea.elkinasa.catbreeds.domain.repository;

import java.util.List;
import ru.mirea.elkinasa.catbreeds.domain.models.CatBreed;

public interface FavoriteRepository {
    boolean addToFavorites(String breedId);
    boolean removeFromFavorites(String breedId);
    List<CatBreed> getAllFavorites();
    boolean isFavorite(String breedId);
}
