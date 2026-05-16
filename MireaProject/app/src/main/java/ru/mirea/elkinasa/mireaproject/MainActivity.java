package ru.mirea.elkinasa.mireaproject;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavController navController;
    private AppBarConfiguration appBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        // Инициализация инструмента Navigation
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,
                R.id.nav_webview,
                R.id.nav_worker,
                R.id.nav_sensor,
                R.id.nav_camera_profile,
                R.id.nav_audio,
                R.id.nav_cat_profile,
                R.id.nav_cat_notes)
                .setOpenableLayout(drawerLayout)
                .build();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        // Проверяем, открыта ли шторка меню
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            // Если шторка открыта - закрываем её
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            // Если шторка закрыта - пытаемся вернуться назад по навигации
            if (!navController.navigateUp()) {
                // Если не можем вернуться назад - выходим из приложения
                Toast.makeText(this, "До свидания! Возвращайтесь к котикам!", Toast.LENGTH_SHORT).show();
                super.onBackPressed();
            }
        }
    }
}