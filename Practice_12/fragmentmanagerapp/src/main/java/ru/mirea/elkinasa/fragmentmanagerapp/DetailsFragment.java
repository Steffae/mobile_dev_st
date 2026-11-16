package ru.mirea.elkinasa.fragmentmanagerapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DetailsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    /**
     * Обновляет содержимое экрана деталей.
     */
    public void updateDetails(Country country) {
        View view = getView();
        if (view == null) return;

        TextView name = view.findViewById(R.id.tvName);
        TextView capital = view.findViewById(R.id.tvCapital);
        TextView population = view.findViewById(R.id.tvPopulation);
        TextView description = view.findViewById(R.id.tvDescription);

        name.setText(country.getName());
        capital.setText("Столица: " + country.getCapital());
        population.setText("Население: " + country.getPopulation());
        description.setText(country.getDescription());
    }
}