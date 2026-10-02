package ru.mirea.elkinasa.catbreeds.data.repository;

import ru.mirea.elkinasa.catbreeds.domain.models.User;
import ru.mirea.elkinasa.catbreeds.domain.repository.AuthRepository;

public class AuthRepositoryImpl implements AuthRepository {

    private User currentUser;

    @Override
    public User login(String email, String password) {
        // Заглушка: любой непустой email+пароль пропускаем
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            return null;
        }
        currentUser = new User("u1", email);
        return currentUser;
    }

    @Override
    public User register(String email, String password) {
        // Заглушка: всегда "успешная" регистрация
        currentUser = new User("u1", email);
        return currentUser;
    }

    @Override
    public void logout() {
        currentUser = null;
    }

    @Override
    public User getCurrentUser() {
        return currentUser;
    }
}
