package ru.mirea.elkinasa.dialogadvanced;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class MyProgressDialogFragment extends DialogFragment {

    private ProgressDialog progressDialog;
    private Handler handler = new Handler();
    private int progressStatus = 0;

    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Загрузка");
        progressDialog.setMessage("Пожалуйста, подождите...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
        progressDialog.show();

        // Имитация процесса загрузки
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progressStatus < 100) {
                    progressStatus++;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.setProgress(progressStatus);
                        }
                    });
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // По завершении закрываем диалог
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        dismiss();
                        ((MainActivity) getActivity()).onProgressComplete();
                    }
                });
            }
        }).start();

        return progressDialog;
    }
}