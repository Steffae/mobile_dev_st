package ru.mirea.elkinasa.domain.usecases;

import ru.mirea.elkinasa.domain.models.RecognitionResult;
import ru.mirea.elkinasa.domain.repository.BreedRecognizer;

public class RecognizeBreedUseCase {
    private final BreedRecognizer recognizer;

    public RecognizeBreedUseCase(BreedRecognizer recognizer) {
        this.recognizer = recognizer;
    }

    public RecognitionResult execute(byte[] imageBytes) {
        return recognizer.recognize(imageBytes);
    }
}
