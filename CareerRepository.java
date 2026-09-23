package com.careerdna.repository;

import com.careerdna.entity.Career;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CareerRepository extends JpaRepository<Career, Long> {

}