package ru.mirea.elkinasa.data.storage.network;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkApi {
    private static final String BASE_URL = "https://api.thecatapi.com/";
    private final CatApiService apiService;

    public NetworkApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(CatApiService.class);
    }

    // Callback для возврата результата в слой data
    public interface OnBreedsLoadedListener {
        void onSuccess(List<CatApiResponse> breeds);
        void onFailure(Throwable t);
    }

    public void loadBreeds(OnBreedsLoadedListener listener) {
        apiService.getBreeds().enqueue(new Callback<List<CatApiResponse>>() {
            @Override
            public void onResponse(Call<List<CatApiResponse>> call, Response<List<CatApiResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listener.onSuccess(response.body());
                } else {
                    listener.onFailure(new Exception("Ошибка сервера: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<CatApiResponse>> call, Throwable t) {
                listener.onFailure(t);
            }
        });
    }
}