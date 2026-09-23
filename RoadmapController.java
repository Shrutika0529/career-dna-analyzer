package com.careerdna.controller;

import com.careerdna.entity.Roadmap;
import com.careerdna.service.RoadmapService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roadmap")
@CrossOrigin
public class RoadmapController {

    private final RoadmapService roadmapService;


    // =====================================================
    // CONSTRUCTOR
    // =====================================================

    public RoadmapController(
            RoadmapService roadmapService) {

        this.roadmapService = roadmapService;
    }


    // =====================================================
    // GENERATE ROADMAP
    // =====================================================

    @PostMapping("/generate")
    public ResponseEntity<Roadmap> generateRoadmap(
            @RequestParam Long studentId,
            @RequestParam String careerName) {

        Roadmap roadmap =
                roadmapService.generateRoadmap(
                        studentId,
                        careerName
                );

        return ResponseEntity.ok(roadmap);
    }


    // =====================================================
    // GET ALL ROADMAPS FOR STUDENT
    // =====================================================

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Roadmap>> getStudentRoadmaps(
            @PathVariable Long studentId) {

        List<Roadmap> roadmaps =
                roadmapService.getStudentRoadmaps(
                        studentId
                );

        return ResponseEntity.ok(roadmaps);
    }


    // =====================================================
    // GET LATEST ROADMAP
    // =====================================================

    @GetMapping("/latest/{studentId}")
    public ResponseEntity<?> getLatestRoadmap(
            @PathVariable Long studentId) {

        Roadmap roadmap =
                roadmapService.getLatestRoadmap(
                        studentId
                );

        if (roadmap == null) {

            return ResponseEntity
                    .notFound()
                    .build();
        }

        return ResponseEntity.ok(roadmap);
    }


    // =====================================================
    // DELETE ROADMAP
    // =====================================================

    @DeleteMapping("/{roadmapId}")
    public ResponseEntity<String> deleteRoadmap(
            @PathVariable Long roadmapId) {

        roadmapService.deleteRoadmap(
                roadmapId
        );

        return ResponseEntity.ok(
                "Roadmap deleted successfully."
        );
    }
}