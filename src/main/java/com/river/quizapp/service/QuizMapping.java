package com.river.quizapp.service;

import com.river.quizapp.dto.QuizQuestionResponseDto;
import com.river.quizapp.dto.QuizCreateDto;
import com.river.quizapp.dto.QuizResponseDto;
import com.river.quizapp.model.Question;
import com.river.quizapp.model.Quiz;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QuizMapping {

    public Quiz map(QuizCreateDto createDto, List<Question> questions) {
        Quiz quiz = new Quiz();
        BeanUtils.copyProperties(createDto, quiz);
        quiz.setQuestions(questions);
        return quiz;
    }

    public QuizResponseDto map(Quiz quiz) {
        QuizResponseDto responseDto = new QuizResponseDto();
        BeanUtils.copyProperties(quiz, responseDto);
        return responseDto;
    }

    public List<QuizQuestionResponseDto> map(List<Question> questions) {
        if (questions == null || questions.isEmpty()) return null;
        return questions.stream()
                .map((question) -> {
                    QuizQuestionResponseDto quizQuestionResponseDto = new QuizQuestionResponseDto();
                    BeanUtils.copyProperties(question, quizQuestionResponseDto);
                    return quizQuestionResponseDto;
                })
                .toList();
    }
}
