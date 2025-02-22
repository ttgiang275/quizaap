package com.river.quizapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "questions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String question;
    private String category;

    @Convert(converter = Difficulty.DifficultyConverter.class)
    private Difficulty difficulty;

    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String correctAnswer;

    @ManyToMany(mappedBy = "questions", fetch = FetchType.LAZY)
//    @JsonManagedReference
    @JsonIgnore
    private List<Quiz> quizzes;

    public Question(Integer id, String question, String category, Difficulty difficulty, String option1, String option2, String option3, String option4, String correctAnswer) {
        this.id = id;
        this.question = question;
        this.category = category;
        this.difficulty = difficulty;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.correctAnswer = correctAnswer;
    }
}
