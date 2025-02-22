package com.river.quizapp.service;

import com.river.quizapp.utils.BusinessValidator;
import org.springframework.stereotype.Service;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BusinessValidationService {

    private final List<BusinessValidator<?>> validators;

    public BusinessValidationService(List<BusinessValidator<?>> validators) {
        this.validators = validators;
    }

    @SuppressWarnings("unchecked")
    public <T> void validate(T request) {
        validators.stream()
                .filter(validator -> validator.getClass()
                        .getGenericInterfaces()[0].getTypeName()
                        .contains(request.getClass().getSimpleName()))
                .forEach(validator -> ((BusinessValidator<T>) validator).validate(request));
    }
}

