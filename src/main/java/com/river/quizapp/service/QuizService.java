package com.river.quizapp.service;

import com.river.quizapp.dto.QuizAnswerRequestDto;
import com.river.quizapp.dto.QuizCreateDto;
import com.river.quizapp.dto.QuizQuestionResponseDto;
import com.river.quizapp.dto.QuizResponseDto;
import com.river.quizapp.exception.BusinessValidationException;
import com.river.quizapp.model.Question;
import com.river.quizapp.model.Quiz;
import com.river.quizapp.repository.QuestionRepository;
import com.river.quizapp.repository.QuizRepository;
import com.river.quizapp.service.validation.QuizValidationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

@Service
public class QuizService {

    @Autowired
    QuizRepository repository;

    @Autowired
    QuizMapping mapping;

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    BusinessValidationService validationService;

    public Quiz addQuiz(String category, int numberOfQuestions, QuizCreateDto createDto) {
        List<Question> questions = questionRepository.findRandomQuestionsByCategory(category, numberOfQuestions);
        return repository.save(mapping.map(createDto, questions));
    }

    public QuizResponseDto getQuiz(Integer id) {
        Quiz quiz = repository.findById(id).orElseThrow(handleNotFoundException(id));
        return mapping.map(quiz);
    }

    public List<QuizQuestionResponseDto> getQuestions(Integer id) {
        Quiz quiz = repository.findById(id).orElseThrow(handleNotFoundException(id));
        return mapping.map(quiz.getQuestions());
    }

    public Integer submitAnswers(Integer id, List<QuizAnswerRequestDto> answers) {
        // Validation
        Quiz quiz = repository.findById(id).orElseThrow(handleNotFoundException(id));
        QuizValidationContext context = new QuizValidationContext(id, answers, quiz);
        validationService.validate(context);

        // Calculate score
        AtomicInteger score = new AtomicInteger(0);
        for (QuizAnswerRequestDto answerRequestDto : answers) {
            Question question = quiz.getQuestions()
                    .stream()
                    .filter(q -> q.getId().equals(answerRequestDto.getId()))
                    .findFirst()
                    .orElseThrow(() -> new BusinessValidationException("Question with id '" + answerRequestDto.getId() + "' not found in the quiz"));
            String answer = answerRequestDto.getAnswer();
            String correctAnswer = question.getCorrectAnswer();
            if (answer.equals(correctAnswer)) {
                score.incrementAndGet();
            }
        }

        return score.get();
    }

    private Supplier<NoSuchElementException> handleNotFoundException(Integer id) {
        return () -> new NoSuchElementException("Quiz with id '" + id + "' not found");
    }
}
