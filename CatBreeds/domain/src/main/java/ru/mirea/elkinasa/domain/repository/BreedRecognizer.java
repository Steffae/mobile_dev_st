package ru.mirea.elkinasa.domain.repository;

import ru.mirea.elkinasa.domain.models.RecognitionResult;

public interface BreedRecognizer {
    RecognitionResult recognize(byte[] imageBytes);
}
