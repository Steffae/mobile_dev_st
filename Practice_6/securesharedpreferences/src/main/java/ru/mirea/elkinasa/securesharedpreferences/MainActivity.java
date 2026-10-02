package ru.mirea.elkinasa.securesharedpreferences;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;
import ru.mirea.elkinasa.securesharedpreferences.databinding.ActivityMainBinding;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private static final String SECURE_PREF_NAME = "poet_secure_prefs";
    private static final String KEY_POET_NAME = "poet_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Загрузка сохранённых данных при запуске
        loadSecureData();

        // Сохранение данных
        binding.buttonSave.setOnClickListener(v -> saveSecureData());
    }

    private void saveSecureData() {
        String poetName = binding.editTextPoetName.getText().toString().trim();

        if (poetName.isEmpty()) {
            Toast.makeText(this, "Введите имя любимого поэта", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            // Создание мастер-ключа
            String mainKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);

            // Создание EncryptedSharedPreferences
            EncryptedSharedPreferences secureSharedPreferences =
                    (EncryptedSharedPreferences) EncryptedSharedPreferences.create(
                            SECURE_PREF_NAME,
                            mainKeyAlias,
                            this,
                            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                    );

            // Сохранение данных (шифрование)
            secureSharedPreferences.edit().putString(KEY_POET_NAME, poetName).apply();

            binding.textViewResult.setText("Сохранённый поэт: " + poetName);
            Toast.makeText(this, "Данные зашифрованы и сохранены", Toast.LENGTH_SHORT).show();

        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Ошибка сохранения", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadSecureData() {
        try {
            String mainKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);

            EncryptedSharedPreferences secureSharedPreferences =
                    (EncryptedSharedPreferences) EncryptedSharedPreferences.create(
                            SECURE_PREF_NAME,
                            mainKeyAlias,
                            this,
                            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
                    );

            // Чтение данных (расшифровка)
            String savedPoetName = secureSharedPreferences.getString(KEY_POET_NAME, "");

            if (!savedPoetName.isEmpty()) {
                binding.editTextPoetName.setText(savedPoetName);
                binding.textViewResult.setText("Сохранённый поэт: " + savedPoetName);
            }

        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
    }
}