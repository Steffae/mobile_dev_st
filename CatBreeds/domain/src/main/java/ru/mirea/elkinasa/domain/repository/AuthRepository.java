package ru.mirea.elkinasa.domain.repository;

import ru.mirea.elkinasa.domain.models.User;

public interface AuthRepository {
    void login(String email, String password, AuthCallback callback);
    void register(String email, String password, AuthCallback callback);
    void logout();
    User getCurrentUser();

    interface AuthCallback {
        void onSuccess(User user);
        void onError(String message);
    }
}