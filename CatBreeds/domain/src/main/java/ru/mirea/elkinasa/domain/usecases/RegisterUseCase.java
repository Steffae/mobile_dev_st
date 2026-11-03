package ru.mirea.elkinasa.domain.usecases;

import ru.mirea.elkinasa.domain.repository.AuthRepository;

public class RegisterUseCase {

    private final AuthRepository repository;

    public RegisterUseCase(AuthRepository repository) {
        this.repository = repository;
    }

    public void execute(String email, String password,
                        AuthRepository.AuthCallback callback) {
        repository.register(email, password, callback);
    }
}