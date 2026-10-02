package ru.mirea.elkinasa.catbreeds.domain.repository;

import ru.mirea.elkinasa.catbreeds.domain.models.RecognitionResult;

public interface BreedRecognizer {
    RecognitionResult recognize(byte[] imageBytes);
}
