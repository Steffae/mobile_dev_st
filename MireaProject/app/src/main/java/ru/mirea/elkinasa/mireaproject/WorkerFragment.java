package ru.mirea.elkinasa.mireaproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import ru.mirea.elkinasa.mireaproject.R;
import ru.mirea.elkinasa.mireaproject.WifiWorker;

public class WorkerFragment extends Fragment {

    private TextView textViewStatus;
    private Button buttonStartTask;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_worker, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textViewStatus = view.findViewById(R.id.textViewStatus);
        buttonStartTask = view.findViewById(R.id.buttonStartTask);

        buttonStartTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBackgroundTask();
            }
        });
    }

    private void startBackgroundTask() {
        textViewStatus.setText("Статус: задача запущена... ожидание Wi-Fi");

        // Условие: Wi-Fi
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED) // Wi-Fi
                .build();

        WorkRequest workRequest = new OneTimeWorkRequest.Builder(WifiWorker.class)
                .setConstraints(constraints)
                .build();

        WorkManager.getInstance(requireContext()).enqueue(workRequest);

        textViewStatus.setText("Статус: задача в очереди. Выполнится при подключении к Wi-Fi");
    }
}