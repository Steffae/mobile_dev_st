package ru.mirea.elkinasa.fragmentapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;
import java.util.List;

public class BlankFragment extends Fragment {

    private static final String ARG_MY_NUMBER = "my_number_student";

    private final List<Workout> workouts = new ArrayList<>();

    public static BlankFragment newInstance(int studentNumber) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_MY_NUMBER, studentNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(BlankFragment.class.getSimpleName(), "onCreateView");
        View view = inflater.inflate(R.layout.fragment_blank, container, false);

        // Проверка получения номера из Bundle
        if (getArguments() != null) {
            int number = getArguments().getInt(ARG_MY_NUMBER, -1);
            Log.d("BlankFragment", "Номер студента: " + number);
        }

        LinearLayout listContainer = view.findViewById(R.id.listContainer);

        workouts.add(new Workout("Погладить кота",
                "5 по спине\n10 по голове\n15 по животику"));
        workouts.add(new Workout("Покормить кота",
                "1 раз с утра\n3 раза днём\n1 раз вечером\n100 вкусняшек"));
        workouts.add(new Workout("Помыть кота",
                "В следующем году\nЗавтра\nНикогда"));

        for (Workout w : workouts) {
            View item = inflater.inflate(R.layout.list_item_workout, listContainer, false);
            TextView title = item.findViewById(R.id.workoutTitle);
            TextView desc = item.findViewById(R.id.workoutDescription);
            CheckBox done = item.findViewById(R.id.workoutDone);

            title.setText(w.getTitle());
            desc.setText(w.getDescription());
            done.setChecked(w.isDone());

            done.setOnCheckedChangeListener((cb, checked) -> {
                w.setDone(checked);
                Toast.makeText(getContext(),
                        w.getTitle() + (checked ? " — выполнено" : " — не выполнено"),
                        Toast.LENGTH_SHORT).show();
            });

            listContainer.addView(item);
        }

        return view;
    }
}