package com.river.quizapp.repository;

import com.river.quizapp.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuestionRepository extends JpaRepository<Question, Integer> {

    List<Question> findByCategory(String category);

    @Query("SELECT q FROM Question q " +
            "WHERE (:category IS NULL OR q.category = :category) " +
            "AND (:difficulty IS NULL OR q.difficulty = :difficulty) " +
            "AND (:question IS NULL OR LOWER(q.question) LIKE LOWER(CONCAT('%', :question, '%')))")
    List<Question> findByFilters(
            @Param("category") String category,
            @Param("difficulty") String difficulty,
            @Param("question") String question
    );

}
