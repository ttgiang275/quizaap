package com.river.quizapp.dto;

import com.river.quizapp.model.Question;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizCreateDto {
    private String title;
}
