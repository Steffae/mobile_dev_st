package ru.mirea.elkinasa.catbreeds.presentation.login;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import ru.mirea.elkinasa.catbreeds.R;
import ru.mirea.elkinasa.catbreeds.presentation.MainActivity;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private LoginViewModel viewModel;
    private EditText emailInput;
    private EditText passwordInput;
    private TextView statusText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.d(TAG, "LoginActivity created");

        emailInput    = findViewById(R.id.editTextEmail);
        passwordInput = findViewById(R.id.editTextPassword);
        statusText    = findViewById(R.id.textViewStatus);
        Button signInBtn    = findViewById(R.id.buttonSignIn);
        Button createAccBtn = findViewById(R.id.buttonCreateAccount);
        Button signOutBtn   = findViewById(R.id.buttonSignOut);

        // переживёт поворот экрана
        viewModel = new ViewModelProvider(this, new LoginViewModelFactory(this))
                .get(LoginViewModel.class);

        // Подписка на LiveData — UI обновляется автоматически
        viewModel.getStatusText().observe(this, s -> statusText.setText(s));

        viewModel.getCurrentUser().observe(this, user -> {
            if (user != null) {
                Toast.makeText(this, "Привет, " + user.getEmail(), Toast.LENGTH_SHORT).show();
            }
            android.content.Intent intent = new android.content.Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        signInBtn.setOnClickListener(v ->
                viewModel.login(
                        emailInput.getText().toString(),
                        passwordInput.getText().toString()));

        createAccBtn.setOnClickListener(v ->
                viewModel.register(
                        emailInput.getText().toString(),
                        passwordInput.getText().toString()));

        signOutBtn.setOnClickListener(v -> viewModel.logout());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "LoginActivity destroyed");
    }
}