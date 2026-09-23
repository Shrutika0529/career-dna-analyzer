package com.careerdna.repository;

import com.careerdna.entity.CareerResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CareerResultRepository
        extends JpaRepository<CareerResult, Long> {

    Optional<CareerResult> findByUserId(Long userId);
}