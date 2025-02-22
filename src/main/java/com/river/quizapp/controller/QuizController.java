package com.river.quizapp.controller;

import com.river.quizapp.dto.QuizAnswerRequestDto;
import com.river.quizapp.dto.QuizQuestionResponseDto;
import com.river.quizapp.dto.QuizCreateDto;
import com.river.quizapp.dto.QuizResponseDto;
import com.river.quizapp.model.Quiz;
import com.river.quizapp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/quizzes")
public class QuizController {

    @Autowired
    QuizService service;


    @GetMapping(path = "/quizzes/{id}")
    public ResponseEntity<QuizResponseDto> getQuiz(
            @PathVariable(value = "id") Integer id) {
        return new ResponseEntity<>(service.getQuiz(id), HttpStatus.OK);
    }

    @PostMapping(path = "/quizzes")
    public ResponseEntity<Void> addQuiz(
            @RequestParam (value = "category") String category,
            @RequestParam (value = "numberOfQuestions") int numberOfQuestions,
            @RequestBody QuizCreateDto createDto
    ) {
        Quiz newQuiz = service.addQuiz(category, numberOfQuestions, createDto);
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-ID", String.valueOf(newQuiz.getId()));
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping(path = "/quizzes/{id}/questions")
    public ResponseEntity<List<QuizQuestionResponseDto>> getQuestions(
            @PathVariable(value = "id") Integer id) {
        return new ResponseEntity<>(service.getQuestions(id), HttpStatus.OK);
    }

    @PostMapping(path = "/quizzes/{id}/answers")
    public ResponseEntity<Integer> submitAnswers(
            @PathVariable(value = "id") Integer id,
            @RequestBody List<QuizAnswerRequestDto> answers
    ) {
        Integer score = service.submitAnswers(id, answers);
        return new ResponseEntity<>(score, HttpStatus.OK);
    }

}
