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
    public boolean addToFavorites(String userId, CatBreed breed) {
        String id = userId + "_" + breed.getId(); // Составной ключ
        FavoriteEntity entity = new FavoriteEntity(
                id, userId, breed.getId(), breed.getName(), breed.getImageUrl(), System.currentTimeMillis());
        favoriteDao.insert(entity);
        return true;
    }

    @Override
    public boolean removeFromFavorites(String userId, String breedId) {
        String id = userId + "_" + breedId;
        favoriteDao.deleteById(id);
        return true;
    }

    @Override
    public List<CatBreed> getAllFavorites(String userId) {
        List<FavoriteEntity> entities = favoriteDao.getAllForUser(userId);
        List<CatBreed> result = new ArrayList<>();
        for (FavoriteEntity e : entities) {
            result.add(new CatBreed(e.breedId, e.breedName, "", "", e.imageUrl));
        }
        return result;
    }

    @Override
    public boolean isFavorite(String userId, String breedId) {
        String id = userId + "_" + breedId;
        return favoriteDao.isFavorite(id);
    }

    @Override
    public List<String> getFavoriteIds(String userId) {
        List<FavoriteEntity> entities = favoriteDao.getAllForUser(userId);
        List<String> ids = new ArrayList<>();
        for (FavoriteEntity e : entities) ids.add(e.breedId);
        return ids;
    }

    @Override
    public void clearAll() {
    }
}