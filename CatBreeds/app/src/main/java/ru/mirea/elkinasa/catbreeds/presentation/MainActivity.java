package ru.mirea.elkinasa.catbreeds.presentation;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import ru.mirea.elkinasa.catbreeds.R;
import ru.mirea.elkinasa.catbreeds.data.repository.AuthRepositoryImpl;
import ru.mirea.elkinasa.catbreeds.data.repository.BreedRecognizerImpl;
import ru.mirea.elkinasa.catbreeds.data.repository.CatRepositoryImpl;
import ru.mirea.elkinasa.catbreeds.data.repository.FavoriteRepositoryImpl;
import ru.mirea.elkinasa.catbreeds.domain.models.CatBreed;
import ru.mirea.elkinasa.catbreeds.domain.models.RecognitionResult;
import ru.mirea.elkinasa.catbreeds.domain.models.User;
import ru.mirea.elkinasa.catbreeds.domain.repository.AuthRepository;
import ru.mirea.elkinasa.catbreeds.domain.repository.BreedRecognizer;
import ru.mirea.elkinasa.catbreeds.domain.repository.CatRepository;
import ru.mirea.elkinasa.catbreeds.domain.repository.FavoriteRepository;
import ru.mirea.elkinasa.catbreeds.domain.usecases.AddToFavoritesUseCase;
import ru.mirea.elkinasa.catbreeds.domain.usecases.GetBreedsUseCase;
import ru.mirea.elkinasa.catbreeds.domain.usecases.GetFavoritesUseCase;
import ru.mirea.elkinasa.catbreeds.domain.usecases.LoginUseCase;
import ru.mirea.elkinasa.catbreeds.domain.usecases.RecognizeBreedUseCase;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "CatBreeds";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Внедрение зависимостей (пока вручную)
        CatRepository catRepo = new CatRepositoryImpl();
        FavoriteRepository favRepo = new FavoriteRepositoryImpl();
        AuthRepository authRepo = new AuthRepositoryImpl();
        BreedRecognizer recognizer = new BreedRecognizerImpl();

        // 1. Получить список пород
        List<CatBreed> breeds = new GetBreedsUseCase(catRepo).execute();
        Log.d(TAG, "Пород в списке: " + breeds.size());
        for (CatBreed b : breeds) Log.d(TAG, "  → " + b.getName());

        // 2. Авторизация
        User user = new LoginUseCase(authRepo).execute("test@test.ru", "123456");
        Log.d(TAG, "Пользователь: " + (user != null ? user.getEmail() : "null"));

        // 3. Добавить первую породу в избранное
        boolean added = new AddToFavoritesUseCase(favRepo).execute(breeds.get(0).getId());
        Log.d(TAG, "Добавлено в избранное: " + added);

        // 4. Получить избранное
        List<CatBreed> favorites = new GetFavoritesUseCase(favRepo).execute();
        Log.d(TAG, "Избранных пород: " + favorites.size());

        // 5. Распознавание
        RecognitionResult result = new RecognizeBreedUseCase(recognizer)
                .execute(new byte[]{1, 2, 3});
        Log.d(TAG, "Распознано: " + result.getBreedName()
                + " (" + (result.getConfidence() * 100) + "%)");
    }
}