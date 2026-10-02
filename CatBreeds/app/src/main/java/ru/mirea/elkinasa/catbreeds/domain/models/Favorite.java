package ru.mirea.elkinasa.catbreeds.domain.models;

public class Favorite {
    private final String id;
    private final String userId;
    private final String breedId;

    public Favorite(String id, String userId, String breedId) {
        this.id = id;
        this.userId = userId;
        this.breedId = breedId;
    }

    public String getId() { return id; }
    public String getUserId() { return userId; }
    public String getBreedId() { return breedId; }
}