package ru.mirea.elkinasa.data.storage.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(FavoriteEntity favorite);

    @Query("DELETE FROM favorites WHERE id = :id")
    void deleteById(String id); // Удаляем по составному ID

    // Получаем избранное только конкретного пользователя
    @Query("SELECT * FROM favorites WHERE userId = :userId ORDER BY dateAdded DESC")
    List<FavoriteEntity> getAllForUser(String userId);

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE id = :id)")
    boolean isFavorite(String id);

    @Query("DELETE FROM favorites WHERE userId = :userId")
    void clearForUser(String userId); // Очищаем только для одного пользователя
}