package ru.mirea.elkinasa.data.storage.network;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface CatApiService {
    @Headers("x-api-key: live_0ZoZTkMi7CWWnZbv9afYQizSnTB0qbRGJ2qme5KV5q6HzLit6yTFXF3GjWAjuLDh")
    @GET("v1/breeds")
    Call<List<CatApiResponse>> getBreeds();
}