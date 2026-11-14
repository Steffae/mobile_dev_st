package ru.mirea.elkinasa.retrofitapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    public static final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
        Call<List<Todo>> call = apiService.getTodos();

        call.enqueue(new Callback<List<Todo>>() {
            @Override
            public void onResponse(Call<List<Todo>> call, Response<List<Todo>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Todo> todos = response.body();

                    // Передаем слушатель в адаптер
                    TodoAdapter todoAdapter = new TodoAdapter(MainActivity.this, todos, (todo, isChecked) -> {
                        todo.setCompleted(isChecked); // Обновляем локально

                        // Отправляем PUT запрос на сервер
                        Call<Todo> updateCall = apiService.updateTodo(todo.getId(), todo);
                        updateCall.enqueue(new Callback<Todo>() {
                            @Override
                            public void onResponse(Call<Todo> call, Response<Todo> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "Обновлено на сервере (ID: " + todo.getId() + ")", Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.e(TAG, "Ошибка обновления: " + response.code());
                                }
                            }

                            @Override
                            public void onFailure(Call<Todo> call, Throwable t) {
                                Log.e(TAG, "onFailure: " + t.getMessage());
                            }
                        });
                    });

                    recyclerView.setAdapter(todoAdapter);
                } else {
                    Log.e(TAG, "onResponse: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Todo>> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }
}