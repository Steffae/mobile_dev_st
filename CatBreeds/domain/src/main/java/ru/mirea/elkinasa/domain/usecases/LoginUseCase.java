package ru.mirea.elkinasa.domain.usecases;

import ru.mirea.elkinasa.domain.repository.AuthRepository;

public class LoginUseCase {
    private final AuthRepository repository;

    public LoginUseCase(AuthRepository repository) {
        this.repository = repository;
    }

    public void execute(String email, String password,
                        AuthRepository.AuthCallback callback) {
        repository.login(email, password, callback);
    }
}