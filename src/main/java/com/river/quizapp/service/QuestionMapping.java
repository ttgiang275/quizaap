package com.river.quizapp.service;

import com.river.quizapp.dto.QuestionCreateDto;
import com.river.quizapp.dto.QuestionResponseDto;
import com.river.quizapp.dto.QuestionUpdateDto;
import com.river.quizapp.model.Question;
import com.river.quizapp.utils.Optionals;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QuestionMapping {
    public List<QuestionResponseDto> map(List<Question> questions) {
        return questions.stream()
                .map(question -> new QuestionResponseDto(question.getId(),
                        question.getQuestion(),
                        question.getCategory(),
                        question.getDifficulty(),
                        question.getOption1(),
                        question.getOption2(),
                        question.getOption3(),
                        question.getOption4(),
                        question.getCorrectAnswer()))
                .toList();
    }

    public Question map(QuestionCreateDto questionCreateDto) {
        Question question = new Question();
        BeanUtils.copyProperties(questionCreateDto, question);
        return question;
    }

    public Question map(QuestionUpdateDto questionUpdateDto, Question existingQuestion) {
        Optionals.ifDefined(questionUpdateDto.getQuestion(), existingQuestion::setQuestion);
        Optionals.ifDefined(questionUpdateDto.getCategory(), existingQuestion::setCategory);
        Optionals.ifDefined(questionUpdateDto.getDifficulty(), existingQuestion::setDifficulty);
        Optionals.ifDefined(questionUpdateDto.getOption1(), existingQuestion::setOption1);
        Optionals.ifDefined(questionUpdateDto.getOption2(), existingQuestion::setOption2);
        Optionals.ifDefined(questionUpdateDto.getOption3(), existingQuestion::setOption3);
        Optionals.ifDefined(questionUpdateDto.getOption4(), existingQuestion::setOption4);
        Optionals.ifDefined(questionUpdateDto.getCorrectAnswer(), existingQuestion::setCorrectAnswer);
        return existingQuestion;
    }

    public QuestionResponseDto map(Question question) {
        QuestionResponseDto questionResponseDto = new QuestionResponseDto();
        BeanUtils.copyProperties(question, questionResponseDto);
        return questionResponseDto;
    }
}
