package com.careerdna.controller;

import com.careerdna.entity.Career;
import com.careerdna.service.CareerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/career")
@CrossOrigin("*")
public class CareerController {

    @Autowired
    private CareerService service;

    @PostMapping("/add")
    public Career addCareer(@RequestBody Career career) {
        return service.addCareer(career);
    }

    @GetMapping("/all")
    public List<Career> getAll() {
        return service.getAllCareers();
    }

    @PutMapping("/update/{id}")
    public Career update(@PathVariable Long id,
                         @RequestBody Career career) {
        return service.updateCareer(id, career);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.deleteCareer(id);
        return "Career Deleted Successfully";
    }
}