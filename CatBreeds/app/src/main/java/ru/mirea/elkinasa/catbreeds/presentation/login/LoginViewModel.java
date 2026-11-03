package ru.mirea.elkinasa.catbreeds.presentation.login;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import ru.mirea.elkinasa.domain.models.User;
import ru.mirea.elkinasa.domain.repository.AuthRepository;
import ru.mirea.elkinasa.domain.usecases.LoginUseCase;
import ru.mirea.elkinasa.domain.usecases.RegisterUseCase;

public class LoginViewModel extends ViewModel {

    private static final String TAG = "LoginViewModel";

    private final AuthRepository authRepository;

    private final MutableLiveData<String> statusText = new MutableLiveData<>();
    private final MutableLiveData<User> currentUser = new MutableLiveData<>();

    public LoginViewModel(AuthRepository authRepository) {
        this.authRepository = authRepository;
        Log.d(TAG, "LoginViewModel created");
    }

    public LiveData<String> getStatusText() {
        return statusText;
    }

    public LiveData<User> getCurrentUser() {
        return currentUser;
    }

    public void login(String email, String password) {
        new LoginUseCase(authRepository).execute(email, password,
                new AuthRepository.AuthCallback() {
                    @Override
                    public void onSuccess(User user) {
                        currentUser.postValue(user);
                        statusText.postValue("Signed In: " + user.getEmail());
                    }

                    @Override
                    public void onError(String message) {
                        statusText.postValue("Ошибка входа: " + message);
                    }
                });
    }

    public void register(String email, String password) {
        new RegisterUseCase(authRepository).execute(email, password,
                new AuthRepository.AuthCallback() {
                    @Override
                    public void onSuccess(User user) {
                        currentUser.postValue(user);
                        statusText.postValue("Registered: " + user.getEmail());
                    }

                    @Override
                    public void onError(String message) {
                        statusText.postValue("Ошибка регистрации: " + message);
                    }
                });
    }

    public void logout() {
        authRepository.logout();
        currentUser.postValue(null);
        statusText.postValue("Signed Out");
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d(TAG, "LoginViewModel cleared");
    }
}