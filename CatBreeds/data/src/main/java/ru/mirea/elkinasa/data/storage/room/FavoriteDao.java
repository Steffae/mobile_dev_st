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

    @Query("DELETE FROM favorites WHERE breedId = :breedId")
    void deleteById(String breedId);

    @Query("SELECT * FROM favorites ORDER BY dateAdded DESC")
    List<FavoriteEntity> getAll();

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE breedId = :breedId)")
    boolean isFavorite(String breedId);
}
