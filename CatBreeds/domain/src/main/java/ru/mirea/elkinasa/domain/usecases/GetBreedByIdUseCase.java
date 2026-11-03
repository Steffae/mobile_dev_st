package ru.mirea.elkinasa.domain.usecases;

import ru.mirea.elkinasa.domain.models.CatBreed;
import ru.mirea.elkinasa.domain.repository.CatRepository;

public class GetBreedByIdUseCase {
    private final CatRepository repository;

    public GetBreedByIdUseCase(CatRepository repository) {
        this.repository = repository;
    }

    public CatBreed execute(String id) {
        return repository.getBreedById(id);
    }
}
