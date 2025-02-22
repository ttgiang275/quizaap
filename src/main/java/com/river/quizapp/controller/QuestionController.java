package com.river.quizapp.controller;

import com.river.quizapp.dto.QuestionCreateDto;
import com.river.quizapp.dto.QuestionResponseDto;
import com.river.quizapp.dto.QuestionUpdateDto;
import com.river.quizapp.model.Question;
import com.river.quizapp.service.QuestionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping(path = "/questions")
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
    public List<QuestionResponseDto> getQuestions(@RequestParam(value = "category", required = false) String category,
                                                  @RequestParam(value = "difficulty", required = false) String difficulty,
                                                  @RequestParam(value = "question", required = false) String question) {
        return service.findByFilters(category, difficulty, question);
    }

    @GetMapping(path = "/questions/{id}")
    public ResponseEntity<QuestionResponseDto> getById(@PathVariable(value = "id") Integer id) {
        return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
    }

    @GetMapping(path = "/questions/category/{category}")
    public ResponseEntity<List<QuestionResponseDto>> getByCategory(@PathVariable(value = "category") String category) {
        return new ResponseEntity<>(service.getByCategory(category), HttpStatus.OK);
    }

    @PostMapping(path = "/questions")
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<Void> addQuestion(@Valid @RequestBody QuestionCreateDto questionCreateDto) {
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

    @DeleteMapping(path = "/questions/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteQuestion(@PathVariable(value = "id") Integer id) {
        service.deleteQuestion(id);
    }

}
