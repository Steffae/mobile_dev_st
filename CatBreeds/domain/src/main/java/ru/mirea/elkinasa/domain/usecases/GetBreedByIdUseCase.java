package ru.mirea.elkinasa.domain.usecases;

import ru.mirea.elkinasa.domain.repository.CatRepository;

public class GetBreedByIdUseCase {
    private final CatRepository repository;

    public GetBreedByIdUseCase(CatRepository repository) {
        this.repository = repository;
    }

    public void execute(String id, CatRepository.Callback callback) {
        repository.getBreedById(id, callback);
    }
}