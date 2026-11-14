package ru.mirea.elkinasa.domain.usecases;

import ru.mirea.elkinasa.domain.repository.CatRepository;

public class SearchBreedsUseCase {
    private final CatRepository repository;

    public SearchBreedsUseCase(CatRepository repository) {
        this.repository = repository;
    }

    public void execute(String query, CatRepository.Callback callback) {
        repository.searchBreeds(query, callback);
    }
}