package ru.mirea.elkinasa.domain.repository;

import java.util.List;

import ru.mirea.elkinasa.domain.models.CatBreed;

public interface CatRepository {
    List<CatBreed> getAllBreeds();
    CatBreed getBreedById(String id);
    List<CatBreed> searchBreeds(String query);
}