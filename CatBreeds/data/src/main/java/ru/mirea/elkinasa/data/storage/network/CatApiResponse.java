package ru.mirea.elkinasa.data.storage.network;

import com.google.gson.annotations.SerializedName;

public class CatApiResponse {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("origin")
    private String origin;

    @SerializedName("image")
    private Image image;

    // Геттеры
    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getOrigin() { return origin; }
    public String getImageUrl() {
        return image != null ? image.getUrl() : "";
    }

    // Вложенный класс для картинки
    public static class Image {
        @SerializedName("url")
        private String url;
        public String getUrl() { return url; }
    }
}