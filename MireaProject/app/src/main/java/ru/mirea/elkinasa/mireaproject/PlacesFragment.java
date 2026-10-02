package ru.mirea.elkinasa.mireaproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ru.mirea.elkinasa.mireaproject.MapActivity;
import ru.mirea.elkinasa.mireaproject.R;

public class PlacesFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_places, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnOpenMap = view.findViewById(R.id.btnOpenMap);
        btnOpenMap.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), MapActivity.class);
            startActivity(intent);
        });
    }
}