package ru.mirea.elkinasa.data.repository;

import java.util.ArrayList;
import java.util.List;

import ru.mirea.elkinasa.data.storage.network.CatApiResponse;
import ru.mirea.elkinasa.data.storage.network.NetworkApi;
import ru.mirea.elkinasa.domain.models.CatBreed;
import ru.mirea.elkinasa.domain.repository.CatRepository;

public class CatRepositoryImpl implements CatRepository {
    private final NetworkApi networkApi = new NetworkApi();

    @Override
    public void getAllBreeds(Callback callback) {
        networkApi.loadBreeds(new NetworkApi.OnBreedsLoadedListener() {
            @Override
            public void onSuccess(List<CatApiResponse> breeds) {
                List<CatBreed> result = new ArrayList<>();
                for (CatApiResponse dto : breeds) {
                    result.add(mapToDomain(dto));
                }
                callback.onSuccess(result);
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    @Override
    public void getBreedById(String id, Callback callback) {
        networkApi.loadBreeds(new NetworkApi.OnBreedsLoadedListener() {
            @Override
            public void onSuccess(List<CatApiResponse> breeds) {
                List<CatBreed> result = new ArrayList<>();
                for (CatApiResponse dto : breeds) {
                    if (dto.getId().equals(id)) {
                        result.add(mapToDomain(dto));
                        break;
                    }
                }
                callback.onSuccess(result);
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    @Override
    public void searchBreeds(String query, Callback callback) {
        networkApi.loadBreeds(new NetworkApi.OnBreedsLoadedListener() {
            @Override
            public void onSuccess(List<CatApiResponse> breeds) {
                List<CatBreed> result = new ArrayList<>();
                for (CatApiResponse dto : breeds) {
                    // Фильтруем по названию (без учета регистра)
                    if (dto.getName().toLowerCase().contains(query.toLowerCase())) {
                        result.add(mapToDomain(dto));
                    }
                }
                callback.onSuccess(result);
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    private CatBreed mapToDomain(CatApiResponse dto) {
        return new CatBreed(dto.getId(), dto.getName(), dto.getDescription(),
                dto.getOrigin(), dto.getImageUrl());
    }
}