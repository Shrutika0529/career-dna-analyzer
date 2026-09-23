package com.careerdna.repository;

import com.careerdna.entity.CareerChatHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CareerChatHistoryRepository
        extends JpaRepository<CareerChatHistory, Long> {

    List<CareerChatHistory>
    findByConversationIdOrderByCreatedAtAsc(
            String conversationId
    );

    Optional<CareerChatHistory>
    findTopByConversationIdOrderByCreatedAtDesc(
            String conversationId
    );

    void deleteByConversationId(
            String conversationId
    );
}