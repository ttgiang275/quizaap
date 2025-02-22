package com.river.quizapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionUpdateDto {

    private Optional<String> question;
    private Optional<String> category;
    private Optional<String> difficulty;
    private Optional<String> option1;
    private Optional<String> option2;
    private Optional<String> option3;
    private Optional<String> option4;
    private Optional<String> correctAnswer;

}
