package ru.mirea.elkinasa.catbreeds.domain.usecases;

import ru.mirea.elkinasa.catbreeds.domain.models.RecognitionResult;
import ru.mirea.elkinasa.catbreeds.domain.repository.BreedRecognizer;

public class RecognizeBreedUseCase {
    private final BreedRecognizer recognizer;

    public RecognizeBreedUseCase(BreedRecognizer recognizer) {
        this.recognizer = recognizer;
    }

    public RecognitionResult execute(byte[] imageBytes) {
        return recognizer.recognize(imageBytes);
    }
}
