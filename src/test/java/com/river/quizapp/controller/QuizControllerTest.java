package com.river.quizapp.controller;

import com.river.quizapp.dto.QuizQuestionResponseDto;
import com.river.quizapp.dto.QuizResponseDto;
import com.river.quizapp.model.Quiz;
import com.river.quizapp.service.QuizService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // ✅ Enables Mockito
public class QuizControllerTest {

    @InjectMocks  // ✅ Injects mocks into the controller
    private QuizController quizController;

    @Mock  // ✅ Mocks the service
    private QuizService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // ✅ Initializes mocks
    }

    @Test
    public void testGetQuiz() {
        // Arrange
        Integer id = 1;
        QuizResponseDto expectedResponse = new QuizResponseDto();
        when(service.getQuiz(id)).thenReturn(expectedResponse); // ✅ Mocks service call

        // Act
        ResponseEntity<QuizResponseDto> response = quizController.getQuiz(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void testGetQuestions_Success() {
        // Arrange
        Integer id = 1;
        List<QuizQuestionResponseDto> expectedResponse = new ArrayList<>();
        expectedResponse.add(
                new QuizQuestionResponseDto(1, "Question 1", "Option 1", "Option 2", "Option 3", "Option 4")
        );
        when(service.getQuestions(id)).thenReturn(expectedResponse); // ✅ Mocks service call

        // Act
        ResponseEntity<List<QuizQuestionResponseDto>> response = quizController.getQuestions(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedResponse, response.getBody());
    }

    @Test
    public void testAddQuiz() {
        // Arrange
        String category = "Category";
        int numberOfQuestions = 5;
        Quiz expectedResponse = new Quiz();
        when(service.addQuiz(category, numberOfQuestions, null)).thenReturn(expectedResponse); // ✅ Mocks service call

        // Act
        ResponseEntity<Void> response = quizController.addQuiz(category, numberOfQuestions, null);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

}
