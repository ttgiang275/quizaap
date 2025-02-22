package com.river.quizapp.service;

import com.river.quizapp.dto.QuestionCreateDto;
import com.river.quizapp.dto.QuestionResponseDto;
import com.river.quizapp.dto.QuestionUpdateDto;
import com.river.quizapp.model.Difficulty;
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

    @Autowired
    private QuestionMapping mapping;

    public List<Question> getQuestions() {
        return repository.findAll();
    }

    public void setup() {

        List<Question> questions = List.of(
                // Java Questions
                new Question(null, "What is JVM in Java?", "Java", Difficulty.EASY,
                        "Java Virtual Machine", "Java Visual Model", "Java Version Manager", "None of the above", "Java Virtual Machine"),

                new Question(null, "Which keyword is used for inheritance in Java?", "Java", Difficulty.MEDIUM,
                        "implements", "extends", "inherits", "super", "extends"),

                new Question(null, "What is JVM in Java?", "Java", Difficulty.EASY,
                        "Java Virtual Machine", "Java Visual Model", "Java Version Manager", "None of the above", "Java Virtual Machine"),

                new Question(null, "Which company developed Java?", "Java", Difficulty.EASY,
                        "Sun Microsystems", "Oracle", "Microsoft", "Google", "Sun Microsystems"),

                new Question(null, "What is the default value of an `int` variable in Java?", "Java", Difficulty.MEDIUM,
                        "0", "null", "1", "undefined", "0"),

                new Question(null, "Which keyword is used to define a constant in Java?", "Java", Difficulty.HARD,
                        "final", "const", "static", "var", "final"),

                // JavaScript Questions
                new Question(null, "Which symbol is used for single-line comments in JavaScript?", "JavaScript", Difficulty.EASY,
                        "//", "/* */", "#", "--", "//"),

                new Question(null, "Which function is used to convert a string to an integer in JavaScript?", "JavaScript", Difficulty.MEDIUM,
                        "parseInt()", "stringToInt()", "toInteger()", "convert()", "parseInt()"),
                new Question(null, "What is a closure in JavaScript?", "JavaScript", Difficulty.MEDIUM,
                        "A function inside a function that retains access to its parent’s scope",
                        "A JavaScript design pattern",
                        "A way to declare private variables",
                        "None of the above",
                        "A function inside a function that retains access to its parent’s scope"),

                new Question(null, "What is `===` in JavaScript?", "JavaScript", Difficulty.EASY,
                        "Strict equality operator", "Assignment operator", "Type coercion operator", "None of the above", "Strict equality operator"),

                new Question(null, "Which keyword is used to define an asynchronous function in JavaScript?", "JavaScript", Difficulty.MEDIUM,
                        "async", "await", "promise", "setTimeout", "async"),

                // Python Questions
                new Question(null, "Which keyword is used to define a function in Python?", "Python", Difficulty.EASY,
                        "func", "define", "def", "function", "def"),

                new Question(null, "What is the output of `print(2 ** 3)` in Python?", "Python", Difficulty.MEDIUM,
                        "6", "8", "9", "4", "8"),
                new Question(null, "What is a tuple in Python?", "Python", Difficulty.EASY,
                        "An immutable sequence of elements", "A mutable list", "A function", "A dictionary", "An immutable sequence of elements"),

                new Question(null, "Which Python keyword is used to handle exceptions?", "Python", Difficulty.MEDIUM,
                        "try", "catch", "error", "throw", "try"),
                // C++ Questions
                new Question(null, "Which operator is used to allocate memory dynamically in C++?", "C++", Difficulty.EASY,
                        "alloc()", "new", "malloc()", "create()", "new"),

                new Question(null, "What is a destructor in C++?", "C++", Difficulty.HARD,
                        "A function called when an object is destroyed", "A constructor", "A function for memory allocation", "None of the above", "A function called when an object is destroyed"),
                new Question(null, "What is a pointer in C++?", "C++", Difficulty.MEDIUM,
                        "A variable that stores the address of another variable", "A data type", "A loop structure", "None of the above", "A variable that stores the address of another variable"),

                new Question(null, "What is the purpose of the `virtual` keyword in C++?", "C++", Difficulty.HARD,
                        "To allow method overriding in derived classes", "To define a constant", "To allocate dynamic memory", "None of the above", "To allow method overriding in derived classes"),

                // .NET Questions
                new Question(null, "Which language is primarily used in .NET?", "DotNet", Difficulty.EASY,
                        "Java", "C#", "Python", "Ruby", "C#"),
                new Question(null, "What does CLR stand for in .NET?", "DotNet", Difficulty.MEDIUM,
                        "Common Language Runtime", "Code Logical Repository", "Compiled Language Resource", "None of the above", "Common Language Runtime"),
                new Question(null, "Which framework is used for .NET web development?", "DotNet", Difficulty.EASY,
                        "ASP.NET", "Spring Boot", "Django", "Node.js", "ASP.NET"),
                new Question(null, "What is CLR in .NET?", "DotNet", Difficulty.MEDIUM,
                        "Common Language Runtime", "Compiled Language Registry", "Coded Logic Routine", "None of the above", "Common Language Runtime")
        );

        repository.saveAll(questions);
    }

    public List<QuestionResponseDto> findByFilters(String category, String difficulty, String question) {
        List<Question> questions = repository.findByFilters(category, difficulty, question);

        if (questions.isEmpty()) {
            throw new NoSuchElementException("No questions found with the given criteria.");
        }

        return mapping.map(questions);
    }

    public QuestionResponseDto getById(Integer id) {
        Question question = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Question with id '" + id + "' not found"));

        return mapping.map(question);
    }

    public List<QuestionResponseDto> getByCategory(String category) {
        List<Question> questions = repository.findByCategory(category);

        if (questions == null || questions.isEmpty()) {
            throw new NoSuchElementException("No questions found for category: " + category);
        }

        return mapping.map(questions);
    }


    public Question addQuestion(QuestionCreateDto questionCreateDto) {
        return repository.save(mapping.map(questionCreateDto));
    }

    public Question updateQuestion(Integer id, QuestionUpdateDto questionUpdateDto) {
        Question existingQuestion = repository.findById(id).
                orElseThrow(() -> new NoSuchElementException("Question with id '" + id + "' not found"));
        return repository.save(mapping.map(questionUpdateDto, existingQuestion));
    }

    public void deleteQuestion(Integer id) {
        Question existingQuestion = repository.findById(id).
                orElseThrow(() -> new NoSuchElementException("Question with id '" + id + "' not found"));
        repository.delete(existingQuestion);
    }
}
