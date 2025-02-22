package com.river.quizapp.service;

import com.river.quizapp.dto.QuestionCreateDto;
import com.river.quizapp.dto.QuestionUpdateDto;
import com.river.quizapp.model.Question;
import com.river.quizapp.repository.QuestionRepository;
import com.river.quizapp.utils.Optionals;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository repository;

    public List<Question> getQuestions() {
        return repository.findAll();
    }

    public void setup() {

        List<Question> questions = List.of(
                // Java Questions
                new Question(null, "What is JVM in Java?", "Java", "Easy",
                        "Java Virtual Machine", "Java Visual Model", "Java Version Manager", "None of the above", "Java Virtual Machine"),

                new Question(null, "Which keyword is used for inheritance in Java?", "Java", "Medium",
                        "implements", "extends", "inherits", "super", "extends"),

                // JavaScript Questions
                new Question(null, "Which symbol is used for single-line comments in JavaScript?", "JavaScript", "Easy",
                        "//", "/* */", "#", "--", "//"),

                new Question(null, "Which function is used to convert a string to an integer in JavaScript?", "JavaScript", "Medium",
                        "parseInt()", "stringToInt()", "toInteger()", "convert()", "parseInt()"),

                // Python Questions
                new Question(null, "Which keyword is used to define a function in Python?", "Python", "Easy",
                        "func", "define", "def", "function", "def"),

                new Question(null, "What is the output of `print(2 ** 3)` in Python?", "Python", "Medium",
                        "6", "8", "9", "4", "8"),

                // C++ Questions
                new Question(null, "Which operator is used to allocate memory dynamically in C++?", "C++", "Easy",
                        "alloc()", "new", "malloc()", "create()", "new"),

                new Question(null, "What is a destructor in C++?", "C++", "Hard",
                        "A function called when an object is destroyed", "A constructor", "A function for memory allocation", "None of the above", "A function called when an object is destroyed"),

                // .NET Questions
                new Question(null, "Which language is primarily used in .NET?", "DotNet", "Easy",
                        "Java", "C#", "Python", "Ruby", "C#"),

                new Question(null, "What does CLR stand for in .NET?", "DotNet", "Medium",
                        "Common Language Runtime", "Code Logical Repository", "Compiled Language Resource", "None of the above", "Common Language Runtime")
        );

        repository.saveAll(questions);
    }

    public List<Question> findByFilters(String category, String difficulty, String question) {
        List<Question> questions = repository.findByFilters(category, difficulty, question);

        if (questions.isEmpty()) {
            throw new NoSuchElementException("No questions found with the given criteria.");
        }

        return questions;
    }

    public Question getById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Question with id '" + id + "' not found"));
    }

    public List<Question> getByCategory(String category) {
        List<Question> questions = repository.findByCategory(category);

        if (questions == null || questions.isEmpty()) {
            throw new NoSuchElementException("No questions found for category: " + category);
        }

        return questions;
    }


    public Question addQuestion(QuestionCreateDto questionCreateDto) {
        return repository.save(map(questionCreateDto));
    }

    public Question updateQuestion(Integer id, QuestionUpdateDto questionUpdateDto) {
        Question existingQuestion = repository.findById(id).
                orElseThrow(() -> new NoSuchElementException("Question with id '" + id + "' not found"));
        return repository.save(map(questionUpdateDto, existingQuestion));
    }

    private Question map(QuestionCreateDto questionCreateDto) {
        Question question = new Question();
        BeanUtils.copyProperties(questionCreateDto, question);
        return question;
    }

    private Question map(QuestionUpdateDto questionUpdateDto, Question existingQuestion) {
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

}
