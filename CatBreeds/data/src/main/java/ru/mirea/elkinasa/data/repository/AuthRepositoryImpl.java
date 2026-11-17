package ru.mirea.elkinasa.data.repository;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ru.mirea.elkinasa.data.storage.room.AppDatabase;
import ru.mirea.elkinasa.data.storage.sharedprefs.UserSharedPrefStorage;
import ru.mirea.elkinasa.domain.models.User;
import ru.mirea.elkinasa.domain.repository.AuthRepository;

public class AuthRepositoryImpl implements AuthRepository {

    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private final UserSharedPrefStorage userPrefs;
    private final Context context;

    public AuthRepositoryImpl(Context context) {
        this.context = context.getApplicationContext();
        this.userPrefs = new UserSharedPrefStorage(context);
    }

    @Override
    public void login(String email, String password, AuthCallback callback) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser fbUser = firebaseAuth.getCurrentUser();
                        if (fbUser != null) {
                            userPrefs.saveEmail(fbUser.getEmail());
                            callback.onSuccess(new User(fbUser.getUid(), fbUser.getEmail()));
                        } else {
                            callback.onError("Пользователь не найден");
                        }
                    } else {
                        callback.onError(task.getException() != null
                                ? task.getException().getMessage()
                                : "Ошибка входа");
                    }
                });
    }

    @Override
    public void register(String email, String password, AuthCallback callback) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser fbUser = firebaseAuth.getCurrentUser();
                        if (fbUser != null) {
                            userPrefs.saveEmail(fbUser.getEmail());
                            callback.onSuccess(new User(fbUser.getUid(), fbUser.getEmail()));
                        }
                    } else {
                        callback.onError(task.getException() != null
                                ? task.getException().getMessage()
                                : "Ошибка регистрации");
                    }
                });
    }

    @Override
    public void logout() {
        firebaseAuth.signOut();
        userPrefs.clear();
    }

    @Override
    public User getCurrentUser() {
        FirebaseUser fbUser = firebaseAuth.getCurrentUser();
        if (fbUser == null) return null;
        return new User(fbUser.getUid(), fbUser.getEmail());
    }

    public String getCachedEmail() {
        return userPrefs.getEmail();
    }
}