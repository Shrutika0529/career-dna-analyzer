package com.careerdna.repository;

import com.careerdna.entity.CareerDNA;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CareerDNARepository
        extends JpaRepository<CareerDNA, Long> {

    Optional<CareerDNA> findByUserId(Long userId);
}