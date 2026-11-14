package ru.mirea.elkinasa.domain.usecases;

import ru.mirea.elkinasa.domain.repository.CatRepository;

public class GetBreedsUseCase {
    private final CatRepository repository;

    public GetBreedsUseCase(CatRepository repository) {
        this.repository = repository;
    }

    public void execute(CatRepository.Callback callback) {
        repository.getAllBreeds(callback);
    }
}