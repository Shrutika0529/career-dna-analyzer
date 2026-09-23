package com.careerdna.service;

import com.careerdna.entity.Roadmap;
import com.careerdna.repository.RoadmapRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RoadmapService {

    private final RoadmapRepository roadmapRepository;


    // =====================================================
    // CONSTRUCTOR
    // =====================================================

    public RoadmapService(
            RoadmapRepository roadmapRepository) {

        this.roadmapRepository = roadmapRepository;
    }


    // =====================================================
    // GENERATE ROADMAP
    // =====================================================

    public Roadmap generateRoadmap(
            Long studentId,
            String careerName) {

        if (studentId == null) {

            throw new RuntimeException(
                    "Student ID is required."
            );
        }


        if (careerName == null ||
                careerName.trim().isEmpty()) {

            throw new RuntimeException(
                    "Career name is required."
            );
        }


        String career =
                careerName
                        .trim()
                        .toLowerCase();


        Roadmap roadmap =
                new Roadmap();


        roadmap.setStudentId(
                studentId
        );


        roadmap.setCareerName(
                careerName
        );


        roadmap.setDuration(
                "6 Months"
        );


        roadmap.setRoadmapData(
                buildRoadmap(career)
        );


        roadmap.setCreatedAt(
                LocalDateTime.now()
        );


        return roadmapRepository.save(
                roadmap
        );
    }


    // =====================================================
    // BUILD ROADMAP
    // =====================================================

    private String buildRoadmap(
            String career) {


        // =================================================
        // SOFTWARE DEVELOPER
        // =================================================

        if (containsAny(
                career,
                "software",
                "software developer",
                "software engineer",
                "developer"
        )) {

            return
                    "MONTH 1:\n" +
                            "• Java / Python fundamentals\n" +
                            "• OOP concepts\n" +
                            "• Git and GitHub\n\n" +

                            "MONTH 2:\n" +
                            "• Data Structures\n" +
                            "• Arrays\n" +
                            "• Strings\n" +
                            "• Linked Lists\n" +
                            "• Stack and Queue\n\n" +

                            "MONTH 3:\n" +
                            "• DBMS\n" +
                            "• SQL\n" +
                            "• MySQL\n" +
                            "• REST APIs\n\n" +

                            "MONTH 4:\n" +
                            "• Spring Boot / Django / Node.js\n" +
                            "• Backend development\n" +
                            "• Authentication\n\n" +

                            "MONTH 5:\n" +
                            "• Build 2 strong projects\n" +
                            "• Resume preparation\n" +
                            "• GitHub portfolio\n\n" +

                            "MONTH 6:\n" +
                            "• DSA practice\n" +
                            "• Aptitude\n" +
                            "• Technical interviews\n" +
                            "• HR interviews";
        }


        // =================================================
        // AI / ML
        // =================================================

        if (containsAny(
                career,
                "ai",
                "artificial intelligence",
                "machine learning",
                "ml",
                "data scientist"
        )) {

            return
                    "MONTH 1:\n" +
                            "• Python fundamentals\n" +
                            "• NumPy\n" +
                            "• Pandas\n\n" +

                            "MONTH 2:\n" +
                            "• Statistics\n" +
                            "• Probability\n" +
                            "• Linear Algebra basics\n\n" +

                            "MONTH 3:\n" +
                            "• Machine Learning fundamentals\n" +
                            "• Regression\n" +
                            "• Classification\n" +
                            "• Clustering\n\n" +

                            "MONTH 4:\n" +
                            "• Scikit-learn\n" +
                            "• Model evaluation\n" +
                            "• Feature engineering\n\n" +

                            "MONTH 5:\n" +
                            "• Deep Learning\n" +
                            "• Neural Networks\n" +
                            "• TensorFlow / PyTorch\n\n" +

                            "MONTH 6:\n" +
                            "• Build AI/ML projects\n" +
                            "• Resume\n" +
                            "• GitHub\n" +
                            "• Interview preparation";
        }


        // =================================================
        // EMBEDDED SYSTEMS
        // =================================================

        if (containsAny(
                career,
                "embedded",
                "embedded systems",
                "firmware"
        )) {

            return
                    "MONTH 1:\n" +
                            "• C programming\n" +
                            "• Pointers\n" +
                            "• Structures\n" +
                            "• Memory management\n\n" +

                            "MONTH 2:\n" +
                            "• Microcontrollers\n" +
                            "• Arduino\n" +
                            "• ESP32\n" +
                            "• GPIO\n\n" +

                            "MONTH 3:\n" +
                            "• Embedded C\n" +
                            "• UART\n" +
                            "• SPI\n" +
                            "• I2C\n\n" +

                            "MONTH 4:\n" +
                            "• Sensors\n" +
                            "• Actuators\n" +
                            "• Communication protocols\n\n" +

                            "MONTH 5:\n" +
                            "• IoT project\n" +
                            "• Embedded project\n\n" +

                            "MONTH 6:\n" +
                            "• Resume\n" +
                            "• GitHub\n" +
                            "• Embedded interview preparation";
        }


        // =================================================
        // IOT
        // =================================================

        if (containsAny(
                career,
                "iot",
                "internet of things"
        )) {

            return
                    "MONTH 1:\n" +
                            "• C / C++ basics\n" +
                            "• Electronics fundamentals\n\n" +

                            "MONTH 2:\n" +
                            "• Arduino\n" +
                            "• ESP32\n" +
                            "• Sensors\n\n" +

                            "MONTH 3:\n" +
                            "• MQTT\n" +
                            "• HTTP\n" +
                            "• REST APIs\n\n" +

                            "MONTH 4:\n" +
                            "• Cloud basics\n" +
                            "• AWS / Azure\n" +
                            "• IoT dashboards\n\n" +

                            "MONTH 5:\n" +
                            "• Build IoT projects\n\n" +

                            "MONTH 6:\n" +
                            "• Deployment\n" +
                            "• Resume\n" +
                            "• Interview preparation";
        }


        // =================================================
        // WEB DEVELOPMENT
        // =================================================

        if (containsAny(
                career,
                "web",
                "web developer",
                "frontend",
                "backend",
                "full stack"
        )) {

            return
                    "MONTH 1:\n" +
                            "• HTML\n" +
                            "• CSS\n" +
                            "• JavaScript\n\n" +

                            "MONTH 2:\n" +
                            "• JavaScript advanced concepts\n" +
                            "• DOM\n" +
                            "• APIs\n\n" +

                            "MONTH 3:\n" +
                            "• React / Angular\n" +
                            "• Components\n" +
                            "• Routing\n\n" +

                            "MONTH 4:\n" +
                            "• Backend development\n" +
                            "• Java Spring Boot / Node.js\n" +
                            "• REST APIs\n\n" +

                            "MONTH 5:\n" +
                            "• Database\n" +
                            "• Authentication\n" +
                            "• Full-stack project\n\n" +

                            "MONTH 6:\n" +
                            "• Deploy project\n" +
                            "• Resume\n" +
                            "• Interview preparation";
        }


        // =================================================
        // ELECTRICAL
        // =================================================

        if (containsAny(
                career,
                "electrical",
                "electrical engineer",
                "power systems"
        )) {

            return
                    "MONTH 1:\n" +
                            "• Electrical fundamentals\n" +
                            "• Circuit analysis\n\n" +

                            "MONTH 2:\n" +
                            "• Electrical Machines\n" +
                            "• Transformers\n" +
                            "• Motors\n\n" +

                            "MONTH 3:\n" +
                            "• Power Systems\n" +
                            "• Generation\n" +
                            "• Transmission\n\n" +

                            "MONTH 4:\n" +
                            "• Control Systems\n" +
                            "• Power Electronics\n\n" +

                            "MONTH 5:\n" +
                            "• MATLAB\n" +
                            "• Automation\n\n" +

                            "MONTH 6:\n" +
                            "• Core projects\n" +
                            "• Resume\n" +
                            "• Technical interview preparation";
        }


        // =================================================
        // DEFAULT ROADMAP
        // =================================================

        return
                "MONTH 1:\n" +
                        "• Programming fundamentals\n" +
                        "• Communication skills\n\n" +

                        "MONTH 2:\n" +
                        "• DSA fundamentals\n" +
                        "• Problem solving\n\n" +

                        "MONTH 3:\n" +
                        "• DBMS\n" +
                        "• SQL\n" +
                        "• OOP\n\n" +

                        "MONTH 4:\n" +
                        "• Career-specific skills\n" +
                        "• Practical projects\n\n" +

                        "MONTH 5:\n" +
                        "• Resume\n" +
                        "• GitHub\n" +
                        "• Internship applications\n\n" +

                        "MONTH 6:\n" +
                        "• Placement preparation\n" +
                        "• Aptitude\n" +
                        "• Technical interviews\n" +
                        "• HR interviews";
    }


    // =====================================================
    // GET STUDENT ROADMAPS
    // =====================================================

    public List<Roadmap> getStudentRoadmaps(
            Long studentId) {

        return roadmapRepository
                .findByStudentId(studentId);
    }


    // =====================================================
    // GET LATEST ROADMAP
    // =====================================================

    public Roadmap getLatestRoadmap(
            Long studentId) {

        return roadmapRepository
                .findTopByStudentIdOrderByCreatedAtDesc(
                        studentId
                )
                .orElse(null);
    }


    // =====================================================
    // DELETE ROADMAP
    // =====================================================

    public void deleteRoadmap(
            Long roadmapId) {

        roadmapRepository.deleteById(
                roadmapId
        );
    }


    // =====================================================
    // HELPER
    // =====================================================

    private boolean containsAny(
            String text,
            String... values) {

        for (String value : values) {

            if (text.contains(
                    value.toLowerCase()
            )) {

                return true;
            }
        }

        return false;
    }
}