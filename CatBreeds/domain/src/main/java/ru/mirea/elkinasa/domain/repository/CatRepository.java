package ru.mirea.elkinasa.domain.repository;

import java.util.List;
import ru.mirea.elkinasa.domain.models.CatBreed;

public interface CatRepository {
    void getAllBreeds(Callback callback);

    void getBreedById(String id, Callback callback);

    void searchBreeds(String query, Callback callback);

    interface Callback {
        void onSuccess(List<CatBreed> breeds);
        void onError(String message);
    }
}