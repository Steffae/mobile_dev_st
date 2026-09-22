package ru.mirea.elkinasa.catbreeds.domain.usecases;

import ru.mirea.elkinasa.catbreeds.domain.models.User;
import ru.mirea.elkinasa.catbreeds.domain.repository.AuthRepository;

public class LoginUseCase {
    private final AuthRepository repository;

    public LoginUseCase(AuthRepository repository) {
        this.repository = repository;
    }

    public User execute(String email, String password) {
        return repository.login(email, password);
    }
}
