package ru.mirea.elkinasa.mireaproject;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.concurrent.TimeUnit;

public class WifiWorker extends Worker {

    static final String TAG = "WifiWorker";

    public WifiWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @Override
    public Result doWork() {
        Log.d(TAG, "doWork: Фоновая задача запущена");

        try {
            // Имитация фоновой работы
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return Result.failure();
        }

        Log.d(TAG, "doWork: Фоновая задача успешно завершена");
        return Result.success();
    }
}