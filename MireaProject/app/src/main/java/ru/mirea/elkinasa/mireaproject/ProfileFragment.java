package ru.mirea.elkinasa.mireaproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import ru.mirea.elkinasa.mireaproject.R;

public class ProfileFragment extends Fragment {

    private EditText editTextCatName, editTextCatBreed, editTextCatAge, editTextCatColor;
    private TextView textViewSavedProfile;
    private Button buttonSaveProfile;
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "cat_profile";
    private static final String KEY_NAME = "cat_name";
    private static final String KEY_BREED = "cat_breed";
    private static final String KEY_AGE = "cat_age";
    private static final String KEY_COLOR = "cat_color";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextCatName = view.findViewById(R.id.editTextCatName);
        editTextCatBreed = view.findViewById(R.id.editTextCatBreed);
        editTextCatAge = view.findViewById(R.id.editTextCatAge);
        editTextCatColor = view.findViewById(R.id.editTextCatColor);
        buttonSaveProfile = view.findViewById(R.id.buttonSaveProfile);
        textViewSavedProfile = view.findViewById(R.id.textViewSavedProfile);

        sharedPreferences = requireContext().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        // Загрузка сохранённых данных
        loadSavedProfile();

        buttonSaveProfile.setOnClickListener(v -> saveProfile());
    }

    private void loadSavedProfile() {
        String name = sharedPreferences.getString(KEY_NAME, "");
        String breed = sharedPreferences.getString(KEY_BREED, "");
        int age = sharedPreferences.getInt(KEY_AGE, 0);
        String color = sharedPreferences.getString(KEY_COLOR, "");

        if (!name.isEmpty()) {
            editTextCatName.setText(name);
            editTextCatBreed.setText(breed);
            if (age != 0) {
                editTextCatAge.setText(String.valueOf(age));
            }
            editTextCatColor.setText(color);

            textViewSavedProfile.setText("Сохранённый профиль:\nИмя: " + name +
                    "\nПорода: " + breed + "\nВозраст: " + age + " лет\nОкрас: " + color);
        }
    }

    private void saveProfile() {
        String name = editTextCatName.getText().toString().trim();
        String breed = editTextCatBreed.getText().toString().trim();
        String ageStr = editTextCatAge.getText().toString().trim();
        String color = editTextCatColor.getText().toString().trim();

        if (name.isEmpty() || breed.isEmpty() || ageStr.isEmpty() || color.isEmpty()) {
            Toast.makeText(getContext(), "Заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        int age = Integer.parseInt(ageStr);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_BREED, breed);
        editor.putInt(KEY_AGE, age);
        editor.putString(KEY_COLOR, color);
        editor.apply();

        textViewSavedProfile.setText("Сохранённый профиль:\nИмя: " + name +
                "\nПорода: " + breed + "\nВозраст: " + age + " лет\nОкрас: " + color);

        Toast.makeText(getContext(), "Профиль котика сохранён!", Toast.LENGTH_SHORT).show();
    }
}