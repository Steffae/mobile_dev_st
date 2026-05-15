package ru.mirea.elkinasa.looper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

public class MyLooper extends Thread {

    public Handler mHandler;
    private Handler mainHandler;
    private static final String TAG = "MyLooper";

    public MyLooper(Handler mainHandler) {
        this.mainHandler = mainHandler;
    }

    @Override
    public void run() {
        Log.d(TAG, "Запуск Looper.prepare()");
        Looper.prepare(); // создание цикла

        mHandler = new Handler(Looper.myLooper()) { // получение и отправка данных
            @Override
            public void handleMessage(Message msg) {
                Bundle bundle = msg.getData();
                int age = bundle.getInt("age", 0);
                String job = bundle.getString("job", "");

                Log.d(TAG, "Получено сообщение: возраст = " + age + ", работа = " + job);

                // Имитация задержки = количество лет
                try {
                    Thread.sleep(age * 1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                String result = "Обработка завершена! Возраст: " + age + ", Работа: " + job;
                Log.d(TAG, result);

                // Отправка результата обратно в главный поток
                Message responseMsg = Message.obtain();
                Bundle responseBundle = new Bundle();
                responseBundle.putString("result", result);
                responseMsg.setData(responseBundle);
                mainHandler.sendMessage(responseMsg);
            }
        };

        Log.d(TAG, "Запуск Looper.loop()");
        Looper.loop(); // запуск цикла
        Log.d(TAG, "Looper завершил работу");
    }
}