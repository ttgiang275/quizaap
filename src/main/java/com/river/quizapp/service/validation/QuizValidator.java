package com.river.quizapp.service.validation;

import com.river.quizapp.dto.QuizAnswerRequestDto;
import com.river.quizapp.exception.BusinessValidationException;
import com.river.quizapp.model.Quiz;
import com.river.quizapp.utils.BusinessValidator;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QuizValidator implements BusinessValidator<QuizValidationContext> {

    @Override
    public void validate(QuizValidationContext context) {
        Integer id = context.getId();
        List<QuizAnswerRequestDto> answers = context.getAnswers();
        Quiz quiz = context.getQuiz();

        if (quiz.getQuestions().size() != answers.size()) {
            throw new BusinessValidationException("Number of answers does not match number of questions");
        }

       // Validate duplicate question ids in answers list
        for (int i = 0; i < answers.size(); i++) {
            for (int j = i + 1; j < answers.size(); j++) {
                if (answers.get(i).getId().equals(answers.get(j).getId())) {
                    throw new BusinessValidationException("Duplicate question id '" + answers.get(i).getId() + "' found in answers list");
                }
            }
        }
    }
}
