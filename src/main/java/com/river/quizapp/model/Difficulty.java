package com.river.quizapp.model;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.Getter;

@Getter
public enum Difficulty {
    EASY("Easy"),
    MEDIUM("Medium"),
    HARD("Hard");

    private final String value;

    Difficulty(String value) {
        this.value = value;
    }

    public static Difficulty fromValue(String value) {
        for (Difficulty d : values()) {
            if (d.value.equalsIgnoreCase(value)) {
                return d;
            }
        }
        throw new IllegalArgumentException("Invalid difficulty: " + value + ". Allowed values: EASY, MEDIUM, HARD");
    }

    /**
     * JPA Attribute Converter (Embedded inside the Enum)
     */
    @Converter(autoApply = true) // Applies to all Difficulty fields automatically
    public static class DifficultyConverter implements AttributeConverter<Difficulty, String> {

        @Override
        public String convertToDatabaseColumn(Difficulty difficulty) {
            return (difficulty == null) ? null : difficulty.getValue();
        }

        @Override
        public Difficulty convertToEntityAttribute(String value) {
            return (value == null) ? null : Difficulty.fromValue(value);
        }
    }
}

