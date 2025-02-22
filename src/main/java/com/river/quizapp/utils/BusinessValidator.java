package com.river.quizapp.utils;

public interface BusinessValidator<T> {
    void validate(T request);
}

