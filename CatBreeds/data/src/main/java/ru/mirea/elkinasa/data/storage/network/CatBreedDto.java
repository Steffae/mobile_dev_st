package ru.mirea.elkinasa.data.storage.network;

public class CatBreedDto {
    public String id;
    public String name;
    public String description;
    public String origin;
    public String imageUrl;

    public CatBreedDto(String id, String name, String description,
                       String origin, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.origin = origin;
        this.imageUrl = imageUrl;
    }
}
