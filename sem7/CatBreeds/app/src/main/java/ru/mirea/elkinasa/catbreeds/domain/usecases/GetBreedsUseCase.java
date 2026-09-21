package ru.mirea.elkinasa.catbreeds.domain.usecases;

import java.util.List;
import ru.mirea.elkinasa.catbreeds.domain.models.CatBreed;
import ru.mirea.elkinasa.catbreeds.domain.repository.CatRepository;

public class GetBreedsUseCase {
    private final CatRepository repository;

    public GetBreedsUseCase(CatRepository repository) {
        this.repository = repository;
    }

    public List<CatBreed> execute() {
        return repository.getAllBreeds();
    }
}
