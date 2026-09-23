package com.careerdna.repository;

import com.careerdna.entity.StudentAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentAnswerRepository
        extends JpaRepository<StudentAnswer, Long> {

    List<StudentAnswer> findByStudentId(Long studentId);
}