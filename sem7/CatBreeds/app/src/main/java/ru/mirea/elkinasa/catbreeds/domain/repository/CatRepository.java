package ru.mirea.elkinasa.catbreeds.domain.repository;

import java.util.List;
import ru.mirea.elkinasa.catbreeds.domain.models.CatBreed;

public interface CatRepository {
    List<CatBreed> getAllBreeds();
    CatBreed getBreedById(String id);
    List<CatBreed> searchBreeds(String query);
}
