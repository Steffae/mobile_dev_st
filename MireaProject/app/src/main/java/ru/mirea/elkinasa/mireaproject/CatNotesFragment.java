package ru.mirea.elkinasa.mireaproject;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import ru.mirea.elkinasa.mireaproject.R;

public class CatNotesFragment extends Fragment {

    private EditText editTextFileName, editTextNote;
    private boolean isWork = false;

    private final ActivityResultLauncher<String[]> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), permissions -> {
                boolean allGranted = true;
                for (Boolean granted : permissions.values()) {
                    if (!granted) {
                        allGranted = false;
                        break;
                    }
                }
                isWork = allGranted;
                if (!isWork) {
                    Toast.makeText(getContext(), "Нет разрешений для работы с файлами", Toast.LENGTH_SHORT).show();
                }
            });

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cat_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextFileName = view.findViewById(R.id.editTextFileName);
        editTextNote = view.findViewById(R.id.editTextNote);

        view.findViewById(R.id.buttonSaveNote).setOnClickListener(v -> saveNote());
        view.findViewById(R.id.buttonLoadNote).setOnClickListener(v -> loadNote());

        checkPermissions();
    }

    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Android 13+ - разрешения не требуются для работы в своей папке
            isWork = true;
        } else {
            int storagePermission = ContextCompat.checkSelfPermission(requireContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (storagePermission == PackageManager.PERMISSION_GRANTED) {
                isWork = true;
            } else {
                requestPermissionLauncher.launch(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE});
            }
        }
    }

    private void saveNote() {
        if (!isWork) {
            Toast.makeText(getContext(), "Нет разрешений", Toast.LENGTH_SHORT).show();
            return;
        }

        String fileName = editTextFileName.getText().toString().trim();
        String note = editTextNote.getText().toString().trim();

        if (fileName.isEmpty() || note.isEmpty()) {
            Toast.makeText(getContext(), "Заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
            if (!path.exists()) {
                path.mkdirs();
            }
            File file = new File(path, fileName + "_cat_note.txt");

            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter writer = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            writer.write(note);
            writer.close();

            Toast.makeText(getContext(), "Заметка сохранена в Documents/", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Ошибка: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void loadNote() {
        if (!isWork) {
            Toast.makeText(getContext(), "Нет разрешений", Toast.LENGTH_SHORT).show();
            return;
        }

        String fileName = editTextFileName.getText().toString().trim();

        if (fileName.isEmpty()) {
            Toast.makeText(getContext(), "Введите имя файла", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
            File file = new File(path, fileName + "_cat_note.txt");

            if (!file.exists()) {
                Toast.makeText(getContext(), "Файл не найден", Toast.LENGTH_SHORT).show();
                return;
            }

            FileInputStream fis = new FileInputStream(file);
            InputStreamReader reader = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(reader);

            StringBuilder content = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line).append("\n");
            }
            reader.close();

            editTextNote.setText(content.toString());
            Toast.makeText(getContext(), "Заметка загружена!", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Ошибка: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}