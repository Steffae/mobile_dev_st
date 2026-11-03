package ru.mirea.elkinasa.data.storage.room;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorites")
public class FavoriteEntity {

    @PrimaryKey
    @NonNull
    public String breedId;

    public String breedName;
    public String imageUrl;
    public long dateAdded;

    public FavoriteEntity(@NonNull String breedId, String breedName,
                          String imageUrl, long dateAdded) {
        this.breedId = breedId;
        this.breedName = breedName;
        this.imageUrl = imageUrl;
        this.dateAdded = dateAdded;
    }
}
