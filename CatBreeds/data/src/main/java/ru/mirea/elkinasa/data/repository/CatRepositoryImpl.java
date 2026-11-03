package ru.mirea.elkinasa.data.repository;

import java.util.ArrayList;
import java.util.List;

import ru.mirea.elkinasa.data.storage.network.CatBreedDto;
import ru.mirea.elkinasa.data.storage.network.NetworkApi;
import ru.mirea.elkinasa.domain.models.CatBreed;
import ru.mirea.elkinasa.domain.repository.CatRepository;

public class CatRepositoryImpl implements CatRepository {

    private final NetworkApi networkApi = new NetworkApi();

    @Override
    public List<CatBreed> getAllBreeds() {
        return mapList(networkApi.getMockBreeds());
    }

    @Override
    public CatBreed getBreedById(String id) {
        for (CatBreedDto dto : networkApi.getMockBreeds()) {
            if (dto.id.equals(id)) return mapToDomain(dto);
        }
        return null;
    }

    @Override
    public List<CatBreed> searchBreeds(String query) {
        List<CatBreed> result = new ArrayList<>();
        for (CatBreedDto dto : networkApi.getMockBreeds()) {
            if (dto.name.toLowerCase().contains(query.toLowerCase())) {
                result.add(mapToDomain(dto));
            }
        }
        return result;
    }

    // ===== Маппинг =====

    private List<CatBreed> mapList(List<CatBreedDto> dtos) {
        List<CatBreed> result = new ArrayList<>();
        for (CatBreedDto dto : dtos) result.add(mapToDomain(dto));
        return result;
    }

    private CatBreed mapToDomain(CatBreedDto dto) {
        return new CatBreed(dto.id, dto.name, dto.description,
                dto.origin, dto.imageUrl);
    }
}