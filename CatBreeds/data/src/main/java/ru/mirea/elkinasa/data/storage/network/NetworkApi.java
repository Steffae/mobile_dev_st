package ru.mirea.elkinasa.data.storage.network;

import java.util.ArrayList;
import java.util.List;

public class NetworkApi {

    public List<CatBreedDto> getMockBreeds() {
        List<CatBreedDto> list = new ArrayList<>();
        list.add(new CatBreedDto("abys", "Абиссинская",
                "Активная и умная порода", "Эфиопия",
                "https://cdn2.thecatapi.com/images/0XYvRd7oD.jpg"));
        list.add(new CatBreedDto("beng", "Бенгальская",
                "Похожа на леопарда", "США",
                "https://cdn2.thecatapi.com/images/O3btzLlsO.png"));
        list.add(new CatBreedDto("brit", "Британская короткошёрстная",
                "Спокойная и пушистая", "Великобритания",
                "https://cdn2.thecatapi.com/images/7isgggqKu.jpg"));
        return list;
    }
}
