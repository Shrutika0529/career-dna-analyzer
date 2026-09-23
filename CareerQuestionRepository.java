package com.careerdna.repository;

import com.careerdna.entity.CareerQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CareerQuestionRepository extends JpaRepository<CareerQuestion, Long> {

}