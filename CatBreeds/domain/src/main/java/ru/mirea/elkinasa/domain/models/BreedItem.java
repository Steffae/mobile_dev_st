package ru.mirea.elkinasa.domain.models;

public class BreedItem {

    private final CatBreed breed;
    private final boolean favorite;

    public BreedItem(CatBreed breed, boolean favorite) {
        this.breed = breed;
        this.favorite = favorite;
    }

    public CatBreed getBreed() {
        return breed;
    }

    public boolean isFavorite() {
        return favorite;
    }
}