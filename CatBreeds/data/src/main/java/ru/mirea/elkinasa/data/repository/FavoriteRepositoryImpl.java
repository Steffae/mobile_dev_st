package ru.mirea.elkinasa.data.repository;

import java.util.ArrayList;
import java.util.List;

import ru.mirea.elkinasa.data.storage.room.FavoriteDao;
import ru.mirea.elkinasa.data.storage.room.FavoriteEntity;
import ru.mirea.elkinasa.domain.models.CatBreed;
import ru.mirea.elkinasa.domain.repository.FavoriteRepository;

public class FavoriteRepositoryImpl implements FavoriteRepository {

    private final FavoriteDao favoriteDao;

    public FavoriteRepositoryImpl(FavoriteDao favoriteDao) {
        this.favoriteDao = favoriteDao;
    }

    @Override
    public boolean addToFavorites(String breedId) {
        FavoriteEntity entity = new FavoriteEntity(
                breedId, breedId, "", System.currentTimeMillis());
        favoriteDao.insert(entity);
        return true;
    }

    @Override
    public boolean removeFromFavorites(String breedId) {
        favoriteDao.deleteById(breedId);
        return true;
    }

    @Override
    public List<CatBreed> getAllFavorites() {
        List<FavoriteEntity> entities = favoriteDao.getAll();
        List<CatBreed> result = new ArrayList<>();
        for (FavoriteEntity e : entities) {
            result.add(new CatBreed(e.breedId, e.breedName, "", "", e.imageUrl));
        }
        return result;
    }

    @Override
    public boolean isFavorite(String breedId) {
        return favoriteDao.isFavorite(breedId);
    }

    @Override
    public List<String> getFavoriteIds() {
        List<FavoriteEntity> entities = favoriteDao.getAll();
        List<String> ids = new ArrayList<>();
        for (FavoriteEntity e : entities) ids.add(e.breedId);
        return ids;
    }
}