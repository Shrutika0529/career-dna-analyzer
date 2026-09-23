package com.careerdna.repository;

import com.careerdna.entity.CareerChatKnowledge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CareerChatKnowledgeRepository
        extends JpaRepository<CareerChatKnowledge, Long> {

    List<CareerChatKnowledge>
    findByQuestionContainingIgnoreCase(String question);

    List<CareerChatKnowledge>
    findByCategoryContainingIgnoreCase(String category);
}