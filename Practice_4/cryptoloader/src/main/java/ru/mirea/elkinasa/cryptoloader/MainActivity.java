package ru.mirea.elkinasa.cryptoloader;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import ru.mirea.elkinasa.cryptoloader.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private ActivityMainBinding binding;
    private static final String TAG = "MainActivity";
    private final int LOADER_ID = 1234;
    private SecretKey secretKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Генерация ключа шифрования
        secretKey = generateKey();

        binding.buttonEncrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputText = binding.editTextInput.getText().toString();

                if (inputText.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Введите фразу", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    // Шифрование текста
                    byte[] encryptedText = encryptMsg(inputText, secretKey);

                    // Отправка данных в Loader
                    Bundle bundle = new Bundle();
                    bundle.putByteArray(MyLoader.ARG_WORD, encryptedText);
                    bundle.putByteArray("key", secretKey.getEncoded());

                    binding.textViewStatus.setText("Статус: шифрование выполнено, отправка в Loader...");

                    LoaderManager.getInstance(MainActivity.this).initLoader(LOADER_ID, bundle, MainActivity.this);

                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Ошибка шифрования", Toast.LENGTH_SHORT).show();
                    binding.textViewStatus.setText("Статус: ошибка шифрования");
                }
            }
        });
    }

    // Генерация ключа
    private SecretKey generateKey() {
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            sr.setSeed("any data used as random seed".getBytes());
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(256, sr);
            return new SecretKeySpec(kg.generateKey().getEncoded(), "AES");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    // Шифрование
    private byte[] encryptMsg(String message, SecretKey secret) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secret);
            return cipher.doFinal(message.getBytes());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Дешифрование
    private String decryptMsg(byte[] cipherText, SecretKey secret) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secret);
            return new String(cipher.doFinal(cipherText));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        Toast.makeText(this, "onCreateLoader: " + id, Toast.LENGTH_SHORT).show();
        binding.textViewStatus.setText("Статус: Loader создан, дешифрование...");
        return new MyLoader(this, args);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        Toast.makeText(this, "Дешифрованная фраза: " + data, Toast.LENGTH_LONG).show();
        binding.textViewStatus.setText("Статус: дешифровано → " + data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
        // Сброс загрузчика
    }
}