package ru.mirea.elkinasa.mireaproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private EditText fieldEmail, fieldPassword;
    private Button buttonSignIn, buttonCreateAccount;
    private TextView statusTextView;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fieldEmail = findViewById(R.id.fieldEmail);
        fieldPassword = findViewById(R.id.fieldPassword);
        buttonSignIn = findViewById(R.id.buttonSignIn);
        buttonCreateAccount = findViewById(R.id.buttonCreateAccount);
        statusTextView = findViewById(R.id.statusTextView);

        mAuth = FirebaseAuth.getInstance();

        // Проверяем, не вошёл ли уже пользователь
        if (mAuth.getCurrentUser() != null) {
            startMainActivity();
        }

        buttonSignIn.setOnClickListener(v -> signIn());
        buttonCreateAccount.setOnClickListener(v -> createAccount());
    }

    private void signIn() {
        String email = fieldEmail.getText().toString().trim();
        String password = fieldPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        startMainActivity();
                    } else {
                        statusTextView.setText("Ошибка входа: " + task.getException().getMessage());
                    }
                });
    }

    private void createAccount() {
        String email = fieldEmail.getText().toString().trim();
        String password = fieldPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Аккаунт создан! Теперь войдите.", Toast.LENGTH_SHORT).show();
                    } else {
                        statusTextView.setText("Ошибка: " + task.getException().getMessage());
                    }
                });
    }

    private void startMainActivity() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}