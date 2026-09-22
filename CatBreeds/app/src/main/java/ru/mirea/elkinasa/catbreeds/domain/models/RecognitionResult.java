package ru.mirea.elkinasa.catbreeds.domain.models;

public class RecognitionResult {
    private final String breedName;
    private final float confidence;

    public RecognitionResult(String breedName, float confidence) {
        this.breedName = breedName;
        this.confidence = confidence;
    }

    public String getBreedName() { return breedName; }
    public float getConfidence() { return confidence; }
}
