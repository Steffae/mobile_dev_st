package ru.mirea.elkinasa.catbreeds.presentation;

import android.os.Bundle;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import ru.mirea.elkinasa.catbreeds.R;
import ru.mirea.elkinasa.catbreeds.presentation.breeds.BreedsFragment;
import ru.mirea.elkinasa.catbreeds.presentation.favorites.FavoritesFragment;
import ru.mirea.elkinasa.catbreeds.presentation.profile.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottomNavigationView);

        if (savedInstanceState == null) {
            loadFragment(new BreedsFragment());
        }

        bottomNav.setOnItemSelectedListener(item -> {
            Fragment fragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.nav_breeds) {
                fragment = new BreedsFragment();
            } else if (itemId == R.id.nav_favorites) {
                fragment = new FavoritesFragment();
            } else if (itemId == R.id.nav_profile) {
                fragment = new ProfileFragment();
            }

            if (fragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
                return true;
            }
            return false;
        });

        // Обработка системной кнопки "Назад"
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                FragmentManager fm = getSupportFragmentManager();
                // Если есть внутренние переходы - возвращаемся
                if (fm.getBackStackEntryCount() > 0) {
                    fm.popBackStack();
                } else if (bottomNav.getSelectedItemId() != R.id.nav_breeds) {
                    // Если мы не на главной вкладке, возвращаем на главную (Породы)
                    bottomNav.setSelectedItemId(R.id.nav_breeds);
                } else {
                    // Если мы на вкладке Породы и стек пуст - выходим
                    finish();
                }
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        // Для стартового фрагмента добавляем в стек, чтобы при нажатии Назад не было пустоты
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}