package ru.mirea.elkinasa.catbreeds.domain.usecases;

import ru.mirea.elkinasa.catbreeds.domain.models.CatBreed;
import ru.mirea.elkinasa.catbreeds.domain.repository.CatRepository;

public class GetBreedByIdUseCase {
    private final CatRepository repository;

    public GetBreedByIdUseCase(CatRepository repository) {
        this.repository = repository;
    }

    public CatBreed execute(String id) {
        return repository.getBreedById(id);
    }
}
