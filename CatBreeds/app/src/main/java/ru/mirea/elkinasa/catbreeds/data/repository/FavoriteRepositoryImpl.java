package ru.mirea.elkinasa.catbreeds.data.repository;

import java.util.ArrayList;
import java.util.List;
import ru.mirea.elkinasa.catbreeds.domain.models.CatBreed;
import ru.mirea.elkinasa.catbreeds.domain.repository.FavoriteRepository;

public class FavoriteRepositoryImpl implements FavoriteRepository {

    private final List<String> favoriteIds = new ArrayList<>();
    private final CatRepositoryImpl catRepo = new CatRepositoryImpl();

    @Override
    public boolean addToFavorites(String breedId) {
        if (!favoriteIds.contains(breedId)) {
            favoriteIds.add(breedId);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeFromFavorites(String breedId) {
        return favoriteIds.remove(breedId);
    }

    @Override
    public List<CatBreed> getAllFavorites() {
        List<CatBreed> result = new ArrayList<>();
        for (String id : favoriteIds) {
            CatBreed b = catRepo.getBreedById(id);
            if (b != null) result.add(b);
        }
        return result;
    }

    @Override
    public boolean isFavorite(String breedId) {
        return favoriteIds.contains(breedId);
    }
}
