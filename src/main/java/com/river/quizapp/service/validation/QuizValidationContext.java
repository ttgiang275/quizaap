package com.river.quizapp.service.validation;

import com.river.quizapp.dto.QuizAnswerRequestDto;
import com.river.quizapp.model.Quiz;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizValidationContext {
    private Integer id;
    private List<QuizAnswerRequestDto> answers;
    private Quiz quiz;
}
