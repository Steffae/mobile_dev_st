package ru.mirea.elkinasa.domain.repository;

import java.util.List;
import ru.mirea.elkinasa.domain.models.CatBreed;

public interface FavoriteRepository {
    boolean addToFavorites(String userId, CatBreed breed);
    boolean removeFromFavorites(String userId, String breedId);
    List<CatBreed> getAllFavorites(String userId);
    boolean isFavorite(String userId, String breedId);
    List<String> getFavoriteIds(String userId);
    void clearAll();
}