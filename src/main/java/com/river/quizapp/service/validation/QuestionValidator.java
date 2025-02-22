package com.river.quizapp.service.validation;

import com.river.quizapp.utils.BusinessValidator;
import org.springframework.stereotype.Component;

@Component
public class QuestionValidator implements BusinessValidator<QuestionValidationContext> {
    @Override
    public void validate(QuestionValidationContext request) {

    }
}
