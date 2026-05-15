package ru.mirea.elkinasa.cryptoloader;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class MyLoader extends AsyncTaskLoader<String> {

    public static final String ARG_WORD = "cryptText";
    private static final String ARG_KEY = "key";
    private static final String TAG = "MyLoader";

    private Bundle bundle;

    public MyLoader(@NonNull Context context, @Nullable Bundle args) {
        super(context);
        this.bundle = args;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        Log.d(TAG, "onStartLoading");
        forceLoad();
    }

    @Nullable
    @Override
    public String loadInBackground() {
        Log.d(TAG, "loadInBackground - начало работы");

        try {
            byte[] cryptText = bundle.getByteArray(ARG_WORD);
            byte[] keyBytes = bundle.getByteArray(ARG_KEY);

            if (cryptText == null || keyBytes == null) {
                Log.e(TAG, "Данные не получены");
                return "Ошибка: данные не получены";
            }

            // Восстановление ключа
            SecretKey originalKey = new SecretKeySpec(keyBytes, 0, keyBytes.length, "AES");

            // Дешифрование
            String decryptedText = decryptMsg(cryptText, originalKey);

            Log.d(TAG, "loadInBackground - дешифровано: " + decryptedText);
            return decryptedText;

        } catch (Exception e) {
            Log.e(TAG, "Ошибка в loadInBackground", e);
            return "Ошибка дешифрования: " + e.getMessage();
        }
    }

    private String decryptMsg(byte[] cipherText, SecretKey secret) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secret);
            return new String(cipher.doFinal(cipherText));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}