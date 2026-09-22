package ru.mirea.elkinasa.catbreeds.domain.repository;

import ru.mirea.elkinasa.catbreeds.domain.models.User;

public interface AuthRepository {
    User login(String email, String password);
    User register(String email, String password);
    void logout();
    User getCurrentUser();
}
