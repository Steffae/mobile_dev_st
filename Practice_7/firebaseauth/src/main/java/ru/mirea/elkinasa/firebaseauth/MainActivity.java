package ru.mirea.elkinasa.firebaseauth;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "EmailPassword";
    private EditText fieldEmail;
    private EditText fieldPassword;
    private Button buttonSignIn;
    private Button buttonCreateAccount;
    private Button buttonVerifyEmail;
    private Button buttonSignOut;
    private TextView statusTextView;
    private TextView detailTextView;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация UI элементов
        fieldEmail = findViewById(R.id.fieldEmail);
        fieldPassword = findViewById(R.id.fieldPassword);
        buttonSignIn = findViewById(R.id.buttonSignIn);
        buttonCreateAccount = findViewById(R.id.buttonCreateAccount);
        buttonVerifyEmail = findViewById(R.id.buttonVerifyEmail);
        buttonSignOut = findViewById(R.id.buttonSignOut);
        statusTextView = findViewById(R.id.statusTextView);
        detailTextView = findViewById(R.id.detailTextView);

        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

        // Обработчики кнопок
        buttonSignIn.setOnClickListener(v -> signIn(fieldEmail.getText().toString(), fieldPassword.getText().toString()));

        buttonCreateAccount.setOnClickListener(v -> createAccount(fieldEmail.getText().toString(), fieldPassword.getText().toString()));

        buttonVerifyEmail.setOnClickListener(v -> sendEmailVerification());

        buttonSignOut.setOnClickListener(v -> signOut());
    }

    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    // [END on_start_check_user]

    // Метод создания аккаунта
    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
        // [END create_user_with_email]
    }

    // Метод входа в аккаунт
    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
                            statusTextView.setText(R.string.auth_failed);
                        }
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }

    // Метод отправки письма для верификации
    private void sendEmailVerification() {
        // Disable button
        buttonVerifyEmail.setEnabled(false);

        // Send verification email
        // [START send_email_verification]
        final FirebaseUser user = mAuth.getCurrentUser();
        Objects.requireNonNull(user).sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        // Re-enable button
                        buttonVerifyEmail.setEnabled(true);

                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this,
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(MainActivity.this,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // [END_EXCLUDE]
                    }
                });
        // [END send_email_verification]
    }

    // Метод выхода из аккаунта
    private void signOut() {
        mAuth.signOut();
        updateUI(null);
    }

    // Валидация полей
    private boolean validateForm() {
        String email = fieldEmail.getText().toString();
        String password = fieldPassword.getText().toString();

        if (email.isEmpty()) {
            fieldEmail.setError("Required");
            return false;
        }

        if (password.isEmpty()) {
            fieldPassword.setError("Required");
            return false;
        }

        return true;
    }

    // Обновление UI в зависимости от состояния входа
    private void updateUI(FirebaseUser user) {
        if (user != null) {
            statusTextView.setText(getString(R.string.emailpassword_status_fmt,
                    user.getEmail(), user.isEmailVerified()));
            detailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));

            // Прячем кнопки входа/регистрации
            buttonSignIn.setVisibility(View.GONE);
            buttonCreateAccount.setVisibility(View.GONE);
            fieldEmail.setVisibility(View.GONE);
            fieldPassword.setVisibility(View.GONE);

            // Показываем кнопки верификации и выхода
            buttonVerifyEmail.setVisibility(View.VISIBLE);
            buttonSignOut.setVisibility(View.VISIBLE);

            buttonVerifyEmail.setEnabled(!user.isEmailVerified());
        } else {
            statusTextView.setText(R.string.signed_out);
            detailTextView.setText(null);

            // Показываем кнопки входа/регистрации
            buttonSignIn.setVisibility(View.VISIBLE);
            buttonCreateAccount.setVisibility(View.VISIBLE);
            fieldEmail.setVisibility(View.VISIBLE);
            fieldPassword.setVisibility(View.VISIBLE);

            // Прячем кнопки верификации и выхода
            buttonVerifyEmail.setVisibility(View.GONE);
            buttonSignOut.setVisibility(View.GONE);
        }
    }
}