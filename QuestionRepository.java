package com.careerdna.repository;

import com.careerdna.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}