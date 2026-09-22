package ru.mirea.elkinasa.looper;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import ru.mirea.elkinasa.looper.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private MyLooper myLooper;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Handler для получения результата из потока MyLooper
        Handler mainThreadHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                String result = msg.getData().getString("result");
                Log.d(TAG, "Получен результат: " + result);
                binding.textViewResult.setText(result);
                Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
            }
        };

        // Создание и запуск потока
        myLooper = new MyLooper(mainThreadHandler);
        myLooper.start();

        binding.buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ageStr = binding.editTextAge.getText().toString();
                String job = binding.editTextJob.getText().toString();

                if (ageStr.isEmpty() || job.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Заполните оба поля", Toast.LENGTH_SHORT).show();
                    return;
                }

                int age = Integer.parseInt(ageStr);

                // Формирование сообщения для отправки в Looper
                Message msg = Message.obtain();
                Bundle bundle = new Bundle();
                bundle.putInt("age", age);
                bundle.putString("job", job);
                msg.setData(bundle);

                // Отправка сообщения в поток MyLooper
                myLooper.mHandler.sendMessage(msg);

                binding.textViewResult.setText("Отправлено... ожидание " + age + " секунд");
                Log.d(TAG, "Сообщение отправлено: возраст=" + age + ", работа=" + job);
            }
        });
    }
}