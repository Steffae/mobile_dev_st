package ru.mirea.elkinasa.catbreeds.domain.usecases;

import java.util.List;
import ru.mirea.elkinasa.catbreeds.domain.models.CatBreed;
import ru.mirea.elkinasa.catbreeds.domain.repository.CatRepository;

public class SearchBreedsUseCase {
    private final CatRepository repository;

    public SearchBreedsUseCase(CatRepository repository) {
        this.repository = repository;
    }

    public List<CatBreed> execute(String query) {
        return repository.searchBreeds(query);
    }
}
