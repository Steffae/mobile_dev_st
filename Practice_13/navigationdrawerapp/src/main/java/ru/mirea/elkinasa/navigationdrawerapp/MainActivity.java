package ru.mirea.elkinasa.navigationdrawerapp;

import android.os.Bundle;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import ru.mirea.elkinasa.navigationdrawerapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Устанавливаем Toolbar
        setSupportActionBar(binding.appBarMain.toolbar);

        // Конфигурация AppBar и Drawer
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_info, R.id.nav_profile)
                .setOpenableLayout(binding.drawerLayout)
                .build();

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host);
        NavController navController = navHostFragment.getNavController();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        // кнопка назад
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Если шторка открыта — закрываем её и не выходим из приложения
                if (binding.drawerLayout.isDrawerOpen(binding.navView)) {
                    binding.drawerLayout.closeDrawer(binding.navView);
                } else {
                    // Иначе выполняем стандартное поведение
                    setEnabled(false);
                    getOnBackPressedDispatcher().onBackPressed();
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = ((NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host)).getNavController();
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}