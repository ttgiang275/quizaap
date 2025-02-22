package com.river.quizapp.controller;

import com.river.quizapp.dto.QuestionCreateDto;
import com.river.quizapp.dto.QuestionUpdateDto;
import com.river.quizapp.model.Question;
import com.river.quizapp.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping(path = "/api")
public class QuestionController {

    @Autowired
    private QuestionService service;

    @GetMapping(path = "/setup")
    @ResponseStatus(value = HttpStatus.OK)
    public String setup() {
        service.setup();
        return "Setup Completed";
    }

    @GetMapping(path = "/questions")
    @ResponseStatus(value = HttpStatus.OK)
    public List<Question> getQuestions(@RequestParam(value = "category", required = false) String category,
                                       @RequestParam(value = "difficulty", required = false) String difficulty,
                                       @RequestParam(value = "question", required = false) String question) {
        return service.findByFilters(category, difficulty, question);
    }

    @GetMapping(path = "/questions/{id}")
    public Question getById(@PathVariable(value = "id") Integer id) {
        return service.getById(id);
    }

    @GetMapping(path = "/questions/category/{category}")
    public List<Question> getByCategory(@PathVariable(value = "category") String category) {
        return service.getByCategory(category);
    }

    @PostMapping(path = "/questions")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<Void> addQuestion(@RequestBody QuestionCreateDto questionCreateDto) {
        Question newQuestion = service.addQuestion(questionCreateDto);
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-ID", String.valueOf(newQuestion.getId()));
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PatchMapping(path = "/questions/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public Question updateQuestion(@PathVariable(value = "id") Integer id,
                                   @RequestBody QuestionUpdateDto questionUpdateDto) {
        return service.updateQuestion(id, questionUpdateDto);
    }

}
