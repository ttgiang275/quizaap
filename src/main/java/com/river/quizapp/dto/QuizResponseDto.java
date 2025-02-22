package com.river.quizapp.dto;

import com.river.quizapp.model.Question;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizResponseDto {
    private Integer id;
    private String title;
    private List<Question> questions;
}
