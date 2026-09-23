package com.careerdna.service;

import com.careerdna.entity.Career;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import com.careerdna.repository.CareerRepository;
@Service
public class CareerService {

    @Autowired
    private CareerRepository repository;

    public Career addCareer(Career career) {
        return repository.save(career);
    }

    public List<Career> getAllCareers() {
        return repository.findAll();
    }

    public Career updateCareer(Long id, Career career) {

        Career c = repository.findById(id).orElseThrow();

        c.setCareerName(career.getCareerName());
        c.setDescription(career.getDescription());
        c.setSkills(career.getSkills());
        c.setCourses(career.getCourses());
        c.setSalary(career.getSalary());

        return repository.save(c);
    }

    public void deleteCareer(Long id) {
        repository.deleteById(id);
    }
}