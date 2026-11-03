package ru.mirea.elkinasa.domain.repository;

import java.util.List;

import ru.mirea.elkinasa.domain.models.CatBreed;

public interface FavoriteRepository {
    boolean addToFavorites(String breedId);
    boolean removeFromFavorites(String breedId);
    List<CatBreed> getAllFavorites();
    boolean isFavorite(String breedId);
    List<String> getFavoriteIds();
}