package ru.mirea.elkinasa.data.repository;

import ru.mirea.elkinasa.domain.models.RecognitionResult;
import ru.mirea.elkinasa.domain.repository.BreedRecognizer;

public class BreedRecognizerImpl implements BreedRecognizer {

    @Override
    public RecognitionResult recognize(byte[] imageBytes) {
        // Заглушка: всегда возвращаем "Британская короткошёрстная" с уверенностью 87%
        return new RecognitionResult("Британская короткошёрстная", 0.87f);
    }
}
