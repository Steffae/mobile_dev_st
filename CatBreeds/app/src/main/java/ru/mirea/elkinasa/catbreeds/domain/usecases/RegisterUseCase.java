package ru.mirea.elkinasa.catbreeds.domain.usecases;

import ru.mirea.elkinasa.catbreeds.domain.models.User;
import ru.mirea.elkinasa.catbreeds.domain.repository.AuthRepository;

public class RegisterUseCase {
    private final AuthRepository repository;

    public RegisterUseCase(AuthRepository repository) {
        this.repository = repository;
    }

    public User execute(String email, String password) {
        return repository.register(email, password);
    }
}
