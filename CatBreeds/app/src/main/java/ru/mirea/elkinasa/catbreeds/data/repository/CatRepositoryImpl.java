package ru.mirea.elkinasa.catbreeds.data.repository;

import java.util.ArrayList;
import java.util.List;
import ru.mirea.elkinasa.catbreeds.domain.models.CatBreed;
import ru.mirea.elkinasa.catbreeds.domain.repository.CatRepository;

public class CatRepositoryImpl implements CatRepository {

    private final List<CatBreed> testBreeds = new ArrayList<>();

    public CatRepositoryImpl() {
        testBreeds.add(new CatBreed("abys", "Абиссинская",
                "Активная и умная порода", "Эфиопия",
                "https://cdn2.thecatapi.com/images/0XYvRd7oD.jpg"));
        testBreeds.add(new CatBreed("beng", "Бенгальская",
                "Похожа на леопарда", "США",
                "https://cdn2.thecatapi.com/images/O3btzLlsO.png"));
        testBreeds.add(new CatBreed("brit", "Британская короткошёрстная",
                "Спокойная и пушистая", "Великобритания",
                "https://cdn2.thecatapi.com/images/7isgggqKu.jpg"));
    }

    @Override
    public List<CatBreed> getAllBreeds() {
        return testBreeds;
    }

    @Override
    public CatBreed getBreedById(String id) {
        for (CatBreed b : testBreeds) {
            if (b.getId().equals(id)) return b;
        }
        return null;
    }

    @Override
    public List<CatBreed> searchBreeds(String query) {
        List<CatBreed> result = new ArrayList<>();
        for (CatBreed b : testBreeds) {
            if (b.getName().toLowerCase().contains(query.toLowerCase())) {
                result.add(b);
            }
        }
        return result;
    }
}
