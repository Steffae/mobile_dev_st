package ru.mirea.elkinasa.catbreeds.presentation.login;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import ru.mirea.elkinasa.data.repository.RepositoryFactory;
import ru.mirea.elkinasa.domain.repository.AuthRepository;

public class LoginViewModelFactory implements ViewModelProvider.Factory {

    private final Context context;

    public LoginViewModelFactory(Context context) {
        this.context = context.getApplicationContext();
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        AuthRepository authRepository = RepositoryFactory.createAuthRepository(context);
        return (T) new LoginViewModel(authRepository);
    }
}