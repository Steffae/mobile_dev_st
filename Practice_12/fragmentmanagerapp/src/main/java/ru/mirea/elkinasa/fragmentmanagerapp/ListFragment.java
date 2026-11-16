package ru.mirea.elkinasa.fragmentmanagerapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

    public interface OnCountrySelectedListener {
        void onCountrySelected(Country country);
    }

    private OnCountrySelectedListener listener;
    private final List<Country> countries = new ArrayList<>();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Пытаемся получить слушателя из хоста (Activity)
        if (context instanceof OnCountrySelectedListener) {
            listener = (OnCountrySelectedListener) context;
        } else {
            throw new RuntimeException(context
                    + " must implement OnCountrySelectedListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        countries.add(new Country("Россия", "Москва", "146 млн",
                "Крупнейшая страна мира по площади."));
        countries.add(new Country("Франция", "Париж", "68 млн",
                "Страна с богатой историей и культурой."));
        countries.add(new Country("Япония", "Токио", "125 млн",
                "Островное государство в Восточной Азии."));
        countries.add(new Country("Германия", "Берлин", "84 млн",
                "Ведущая экономика Европы."));
        countries.add(new Country("Италия", "Рим", "59 млн",
                "Страна с античным наследием."));
        countries.add(new Country("Китай", "Пекин", "1.4 млрд",
                "Крупнейшая страна по населению."));

        ListView listView = view.findViewById(R.id.countryList);
        ArrayAdapter<Country> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                countries);
        listView.setAdapter(adapter);

        // Обработка выбора пункта
        listView.setOnItemClickListener((parent, v, position, id) -> {
            if (listener != null) {
                listener.onCountrySelected(countries.get(position));
            }
        });

        return view;
    }
}