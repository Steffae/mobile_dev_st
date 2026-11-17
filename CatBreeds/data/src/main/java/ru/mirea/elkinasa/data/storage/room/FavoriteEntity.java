package ru.mirea.elkinasa.data.storage.room;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorites")
public class FavoriteEntity {

    @PrimaryKey
    @NonNull
    public String id; // Уникальный ключ: userId + "_" + breedId

    public String userId; // Кому принадлежит избранное
    public String breedId;
    public String breedName;
    public String imageUrl;
    public long dateAdded;

    public FavoriteEntity(@NonNull String id, String userId, String breedId,
                          String breedName, String imageUrl, long dateAdded) {
        this.id = id;
        this.userId = userId;
        this.breedId = breedId;
        this.breedName = breedName;
        this.imageUrl = imageUrl;
        this.dateAdded = dateAdded;
    }
}