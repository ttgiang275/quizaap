package com.river.quizapp.dto;

import com.river.quizapp.model.Difficulty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionCreateDto {
    @NotNull(message = "Question must be not null")
    private String question;
    private String category;
    private Difficulty difficulty;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String correctAnswer;
}
