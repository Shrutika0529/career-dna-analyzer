package com.careerdna.service;

import com.careerdna.dto.CareerChatResponse;
import com.careerdna.entity.CareerChatHistory;
import com.careerdna.entity.CareerChatKnowledge;
import com.careerdna.repository.CareerChatHistoryRepository;
import com.careerdna.repository.CareerChatKnowledgeRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CareerChatService {

    private final CareerChatKnowledgeRepository repository;
    private final CareerChatHistoryRepository historyRepository;


    // =====================================================
    // CONSTRUCTOR
    // =====================================================

    public CareerChatService(
            CareerChatKnowledgeRepository repository,
            CareerChatHistoryRepository historyRepository) {

        this.repository = repository;
        this.historyRepository = historyRepository;
    }


    // =====================================================
    // MAIN METHOD
    // =====================================================

    public CareerChatResponse askQuestion(
            String question,
            String conversationId) {

        if (question == null ||
                question.trim().isEmpty()) {

            return new CareerChatResponse(
                    question,
                    "Please enter a question."
            );
        }


        String originalQuestion =
                question.trim();

        String userQuestion =
                cleanText(originalQuestion);


        // =================================================
        // RESOLVE CURRENT / PREVIOUS BRANCH
        // =================================================

        String conversationBranch =
                resolveBranch(
                        originalQuestion,
                        conversationId
                );


        System.out.println(
                "Conversation Branch: " +
                        conversationBranch
        );


        // =================================================
        // GET ALL KNOWLEDGE
        // =================================================

        List<CareerChatKnowledge> knowledgeList =
                repository.findAll();


        CareerChatKnowledge bestMatch = null;

        int bestScore = 0;


        // =================================================
        // FIND BEST MATCH
        // =================================================

        for (CareerChatKnowledge knowledge :
                knowledgeList) {

            int score =
                    calculateScore(
                            userQuestion,
                            knowledge
                    );


            // Give extra priority to the
            // current conversation branch.

            String knowledgeBranch =
                    safe(
                            knowledge.getBranch()
                    );


            if (!conversationBranch.equals("GENERAL") &&
                    knowledgeBranch.equalsIgnoreCase(
                            conversationBranch
                    )) {

                score += 20;
            }


            System.out.println(
                    "Question: " +
                            knowledge.getQuestion() +
                            " | Branch: " +
                            knowledgeBranch +
                            " | Score: " +
                            score
            );


            if (score > bestScore) {

                bestScore = score;

                bestMatch = knowledge;
            }
        }


        // =================================================
        // MATCH FOUND
        // =================================================

        if (bestMatch != null &&
                bestScore >= 3) {

            System.out.println(
                    "BEST MATCH: " +
                            bestMatch.getQuestion() +
                            " | BRANCH: " +
                            bestMatch.getBranch() +
                            " | SCORE: " +
                            bestScore
            );


            String answer =
                    bestMatch.getAnswer();


            String answerBranch =
                    safe(
                            bestMatch.getBranch()
                    );


            if (answerBranch.trim().isEmpty()) {

                answerBranch =
                        conversationBranch;
            }


            saveChatHistory(
                    conversationId,
                    originalQuestion,
                    answer,
                    answerBranch
            );


            return new CareerChatResponse(
                    originalQuestion,
                    answer
            );
        }


        // =================================================
        // SMART FALLBACK
        // =================================================

        String fallback =
                generateSmartFallback(
                        userQuestion,
                        conversationBranch
                );


        saveChatHistory(
                conversationId,
                originalQuestion,
                fallback,
                conversationBranch
        );


        return new CareerChatResponse(
                originalQuestion,
                fallback
        );
    }


    // =====================================================
    // SAVE CHAT HISTORY
    // =====================================================

    private void saveChatHistory(
            String conversationId,
            String question,
            String answer,
            String branch) {

        if (conversationId == null ||
                conversationId.trim().isEmpty()) {

            return;
        }


        CareerChatHistory history =
                new CareerChatHistory();


        history.setConversationId(
                conversationId
        );


        history.setStudentMessage(
                question
        );


        history.setAiResponse(
                answer
        );


        history.setBranch(
                branch
        );


        history.setCreatedAt(
                LocalDateTime.now()
        );


        historyRepository.save(history);
    }


    // =====================================================
    // RESOLVE BRANCH
    // =====================================================

    private String resolveBranch(
            String question,
            String conversationId) {

        String currentBranch =
                detectBranch(
                        cleanText(question)
                );


        // If current question contains
        // a branch, use it.

        if (!currentBranch.equals("GENERAL")) {

            return currentBranch;
        }


        // Otherwise use previous
        // conversation branch.

        return getConversationBranch(
                conversationId
        );
    }


    // =====================================================
    // GET BRANCH FROM CONVERSATION
    // =====================================================

    private String getConversationBranch(
            String conversationId) {

        if (conversationId == null ||
                conversationId.trim().isEmpty()) {

            return "GENERAL";
        }


        List<CareerChatHistory> history =
                historyRepository
                        .findByConversationIdOrderByCreatedAtAsc(
                                conversationId
                        );


        if (history == null ||
                history.isEmpty()) {

            return "GENERAL";
        }


        for (int i = history.size() - 1;
             i >= 0;
             i--) {

            CareerChatHistory chat =
                    history.get(i);


            String branch =
                    chat.getBranch();


            if (branch != null &&
                    !branch.trim().isEmpty() &&
                    !branch.equalsIgnoreCase("GENERAL")) {

                return branch;
            }
        }


        return "GENERAL";
    }


    // =====================================================
    // DETECT BRANCH
    // =====================================================

    private String detectBranch(
            String question) {


        // =================================================
        // E&TC FIRST
        // =================================================

        if (containsAny(
                question,
                "e&tc",
                "e and tc",
                "entc",
                "e tc",
                "electronics and telecommunication",
                "electronics telecommunication"
        )) {

            return "E&TC";
        }


        // =================================================
        // ECE
        // Electronics and Computer Engineering
        // =================================================

        if (containsAny(
                question,
                "ece",
                "electronics and computer engineering",
                "electronics computer engineering"
        )) {

            return "ECE";
        }


        // =================================================
        // CSE
        // =================================================

        if (containsAny(
                question,
                "cse",
                "computer science",
                "computer science engineering"
        )) {

            return "CSE";
        }


        // =================================================
        // EE
        // =================================================

        if (containsAny(
                question,
                "electrical engineering",
                "electrical",
                "ee"
        )) {

            return "EE";
        }


        return "GENERAL";
    }


    // =====================================================
    // SCORE CALCULATION
    // =====================================================

    private int calculateScore(
            String userQuestion,
            CareerChatKnowledge knowledge) {

        int score = 0;


        String storedQuestion =
                cleanText(
                        knowledge.getQuestion()
                );


        String keywords =
                cleanText(
                        knowledge.getKeywords()
                );


        String category =
                cleanText(
                        knowledge.getCategory()
                );


        String branch =
                cleanText(
                        knowledge.getBranch()
                );


        Set<String> userWords =
                getImportantWords(
                        userQuestion
                );


        Set<String> storedWords =
                getImportantWords(
                        storedQuestion
                );


        // =================================================
        // QUESTION WORD MATCH
        // =================================================

        for (String word : userWords) {

            if (storedWords.contains(word)) {

                score += 3;
            }
        }


        // =================================================
        // KEYWORD MATCH
        // =================================================

        for (String word : userWords) {

            if (keywords.contains(word)) {

                score += 5;
            }
        }


        // =================================================
        // CATEGORY MATCH
        // =================================================

        for (String word : userWords) {

            if (category.contains(word)) {

                score += 2;
            }
        }


        // =================================================
        // BRANCH MATCH
        // =================================================

        for (String word : userWords) {

            if (branch.contains(word)) {

                score += 10;
            }
        }


        // =================================================
        // INTENT
        // =================================================

        score += getIntentScore(
                userQuestion,
                knowledge
        );


        // =================================================
        // TECHNOLOGY
        // =================================================

        score += getTechnologyScore(
                userQuestion,
                knowledge
        );


        return score;
    }


    // =====================================================
    // INTENT SCORE
    // =====================================================

    private int getIntentScore(
            String question,
            CareerChatKnowledge knowledge) {

        int score = 0;


        String category =
                cleanText(
                        knowledge.getCategory()
                );


        if (containsAny(
                question,
                "placement",
                "placements",
                "campus",
                "campus placement",
                "placed",
                "getting placed"
        )) {

            if (category.contains(
                    "placement")) {

                score += 10;
            }
        }


        if (containsAny(
                question,
                "internship",
                "intern",
                "internships"
        )) {

            if (category.contains(
                    "internship")) {

                score += 10;
            }
        }


        if (containsAny(
                question,
                "career",
                "career path",
                "future",
                "profession"
        )) {

            if (category.contains(
                    "career")) {

                score += 10;
            }
        }


        if (containsAny(
                question,
                "dsa",
                "algorithm",
                "algorithms",
                "data structure",
                "data structures",
                "coding"
        )) {

            if (category.contains(
                    "dsa")) {

                score += 10;
            }
        }


        if (containsAny(
                question,
                "ai",
                "ml",
                "machine learning",
                "artificial intelligence",
                "deep learning"
        )) {

            if (category.contains(
                    "ai")) {

                score += 10;
            }
        }


        if (containsAny(
                question,
                "resume",
                "cv",
                "curriculum vitae"
        )) {

            if (category.contains(
                    "resume")) {

                score += 10;
            }
        }


        if (containsAny(
                question,
                "government",
                "govt",
                "government job",
                "government jobs",
                "competitive exam"
        )) {

            if (category.contains(
                    "government")) {

                score += 10;
            }
        }


        return score;
    }


    // =====================================================
    // TECHNOLOGY SCORE
    // =====================================================

    private int getTechnologyScore(
            String question,
            CareerChatKnowledge knowledge) {

        int score = 0;


        String stored =
                (
                        safe(knowledge.getQuestion())
                                + " "
                                + safe(knowledge.getKeywords())
                                + " "
                                + safe(knowledge.getCategory())
                ).toLowerCase();


        String[] technologies = {

                "java",
                "python",
                "javascript",
                "react",
                "angular",
                "spring boot",
                "node",
                "sql",
                "mysql",
                "html",
                "css",
                "c++",
                "c",
                "aws",
                "azure",
                "docker",
                "kubernetes",
                "tensorflow",
                "pytorch"

        };


        for (String technology :
                technologies) {

            if (question.contains(
                    technology
            )) {

                if (stored.contains(
                        technology
                )) {

                    score += 8;
                }
            }
        }


        return score;
    }


    // =====================================================
    // SMART FALLBACK
    // =====================================================

    private String generateSmartFallback(
            String question,
            String branch) {


        // =================================================
        // WHAT SHOULD I LEARN?
        // =================================================

        if (containsAny(
                question,
                "what should i learn",
                "what should i study",
                "which skills",
                "skills should i learn",
                "what skills",
                "what do i learn"
        )) {


            if (branch.equals("ECE")) {

                return
                        "Since you are an ECE student " +
                                "(Electronics and Computer Engineering), " +
                                "you can prepare for both electronics and " +
                                "software opportunities.\n\n" +

                                "For software:\n" +
                                "• C/C++ or Java/Python\n" +
                                "• DSA\n" +
                                "• OOP\n" +
                                "• SQL and DBMS\n" +
                                "• Web development\n\n" +

                                "For electronics:\n" +
                                "• Digital Electronics\n" +
                                "• Microprocessors\n" +
                                "• Embedded Systems\n" +
                                "• IoT\n" +
                                "• Computer Architecture\n" +
                                "• Basic VLSI concepts.";
            }


            if (branch.equals("E&TC")) {

                return
                        "Since you are an E&TC student " +
                                "(Electronics and Telecommunication), " +
                                "you can explore electronics, embedded, " +
                                "IoT, communication and software careers.\n\n" +

                                "Focus on:\n" +
                                "• C/C++\n" +
                                "• Embedded Systems\n" +
                                "• Microcontrollers\n" +
                                "• IoT\n" +
                                "• Digital Electronics\n" +
                                "• Communication Systems\n" +
                                "• Basic DSA.";
            }


            if (branch.equals("EE")) {

                return
                        "Since you are an Electrical Engineering " +
                                "student, you can prepare for both core " +
                                "electrical and software opportunities.\n\n" +

                                "Focus on:\n" +
                                "• Electrical Machines\n" +
                                "• Power Systems\n" +
                                "• Control Systems\n" +
                                "• Power Electronics\n" +
                                "• MATLAB\n" +
                                "• Embedded Systems\n" +
                                "• Programming and DSA.";
            }


            if (branch.equals("CSE")) {

                return
                        "Since you are a CSE student, focus mainly " +
                                "on software and technology skills.\n\n" +

                                "Focus on:\n" +
                                "• Java/Python/C++\n" +
                                "• DSA\n" +
                                "• OOP\n" +
                                "• DBMS and SQL\n" +
                                "• Operating Systems\n" +
                                "• Computer Networks\n" +
                                "• Web development\n" +
                                "• Git and GitHub.";
            }
        }


        // =================================================
        // BRANCH + INTERNSHIP
        // =================================================

        if (containsAny(
                question,
                "internship",
                "internships",
                "intern"
        )) {


            if (branch.equals("ECE")) {

                return
                        "For ECE internships, consider roles in " +
                                "Embedded Systems, IoT, VLSI, electronics " +
                                "design, firmware and software development.\n\n" +

                                "Build at least one practical project " +
                                "before applying.";
            }


            if (branch.equals("E&TC")) {

                return
                        "For E&TC internships, consider Embedded " +
                                "Systems, IoT, telecommunications, networking, " +
                                "electronics and software development roles.";
            }


            if (branch.equals("EE")) {

                return
                        "For EE internships, consider Power Systems, " +
                                "Electrical Design, Automation, Control Systems, " +
                                "Power Electronics, Embedded Systems and IoT.";
            }


            if (branch.equals("CSE")) {

                return
                        "For CSE internships, consider Software " +
                                "Development, Web Development, Java, Python, " +
                                "Data Science, AI/ML, Cloud and Testing roles.";
            }
        }


        // =================================================
        // THIRD YEAR + PLACEMENT
        // =================================================

        if (
                containsAny(
                        question,
                        "third year",
                        "3rd year",
                        "third-year"
                )
                        &&
                        containsAny(
                                question,
                                "placement",
                                "job",
                                "placed"
                        )
        ) {

            return
                    "Since you are in your third year, " +
                            "this is a good time to start serious " +
                            "placement preparation.\n\n" +

                            "Focus on:\n" +
                            "1. Data Structures and Algorithms\n" +
                            "2. One strong programming language\n" +
                            "3. OOP concepts\n" +
                            "4. DBMS and SQL\n" +
                            "5. Operating Systems\n" +
                            "6. Computer Networks\n" +
                            "7. Aptitude and reasoning\n" +
                            "8. 2 or 3 strong projects\n" +
                            "9. Git and GitHub\n" +
                            "10. Technical and HR interview practice.";
        }


        // =================================================
        // PLACEMENT
        // =================================================

        if (containsAny(
                question,
                "placement",
                "placements",
                "campus",
                "placed"
        )) {

            if (branch.equals("ECE")) {

                return
                        "For ECE placements, you can prepare " +
                                "for both software and electronics roles.\n\n" +
                                "Software: Programming, DSA, OOP, SQL and DBMS.\n" +
                                "Core: Digital Electronics, Embedded Systems, " +
                                "IoT, Microprocessors and basic VLSI.";
            }


            if (branch.equals("EE")) {

                return
                        "For EE placements, prepare core subjects " +
                                "such as Power Systems, Electrical Machines, " +
                                "Control Systems and Power Electronics. " +
                                "For software roles, also prepare programming, " +
                                "DSA, OOP and SQL.";
            }


            if (branch.equals("E&TC")) {

                return
                        "For E&TC placements, prepare Embedded Systems, " +
                                "Digital Electronics, Communication Systems, " +
                                "IoT and programming. DSA and aptitude are also " +
                                "useful for software placements.";
            }


            return
                    "For placements, focus on DSA, programming, " +
                            "OOP, DBMS, SQL, Operating Systems, Computer " +
                            "Networks, aptitude, communication and projects.";
        }


        // =================================================
        // DSA
        // =================================================

        if (containsAny(
                question,
                "dsa",
                "algorithm",
                "data structure",
                "coding"
        )) {

            return
                    "For DSA, start with arrays and strings. " +
                            "Then learn linked lists, stacks, queues, " +
                            "hashing, trees, graphs, recursion, greedy " +
                            "algorithms and dynamic programming.";
        }


        // =================================================
        // JAVA
        // =================================================

        if (containsAny(
                question,
                "java",
                "spring boot"
        )) {

            return
                    "For Java development, learn Java fundamentals, " +
                            "OOP, Collections, Exception Handling, " +
                            "Multithreading, Java 8+ features, JDBC and " +
                            "then Spring Boot and REST APIs.";
        }


        // =================================================
        // PYTHON
        // =================================================

        if (containsAny(
                question,
                "python"
        )) {

            return
                    "Start Python with variables, conditions, loops, " +
                            "functions, lists, dictionaries, OOP, exceptions " +
                            "and file handling. For AI/ML, continue with " +
                            "NumPy, Pandas, statistics and machine learning.";
        }


        // =================================================
        // AI / ML
        // =================================================

        if (containsAny(
                question,
                "ai",
                "ml",
                "machine learning",
                "artificial intelligence"
        )) {

            return
                    "For AI/ML, start with Python, mathematics, " +
                            "statistics, NumPy and Pandas. Then learn " +
                            "machine learning algorithms, model evaluation, " +
                            "scikit-learn and deep learning.";
        }


        // =================================================
        // GOVERNMENT
        // =================================================

        if (containsAny(
                question,
                "government job",
                "government jobs",
                "govt job",
                "government exam"
        )) {

            return
                    "Government career opportunities depend on " +
                            "your qualification and the specific recruitment. " +
                            "Check the latest official notification for " +
                            "eligibility, syllabus and application dates.";
        }


        // =================================================
        // DEFAULT
        // =================================================

        return
                "I don't have a specific answer for that yet.\n\n" +
                        "You can ask me about:\n" +
                        "• Career paths\n" +
                        "• Placement preparation\n" +
                        "• Internships\n" +
                        "• Java\n" +
                        "• Python\n" +
                        "• DSA\n" +
                        "• AI/ML\n" +
                        "• Software development\n" +
                        "• Government jobs\n" +
                        "• Resume and projects";
    }


    // =====================================================
    // IMPORTANT WORDS
    // =====================================================

    private Set<String> getImportantWords(
            String text) {

        Set<String> words =
                new HashSet<>();


        String[] tokens =
                text.split("\\s+");


        Set<String> stopWords =
                Set.of(
                        "the",
                        "is",
                        "am",
                        "are",
                        "i",
                        "a",
                        "an",
                        "and",
                        "or",
                        "to",
                        "for",
                        "of",
                        "in",
                        "on",
                        "what",
                        "how",
                        "can",
                        "do",
                        "should",
                        "my",
                        "me",
                        "tell",
                        "about",
                        "which",
                        "why",
                        "where",
                        "when"
                );


        for (String token :
                tokens) {

            String word =
                    token
                            .replaceAll(
                                    "[^a-zA-Z0-9]",
                                    ""
                            )
                            .toLowerCase();


            if (word.length() >= 3 &&
                    !stopWords.contains(word)) {

                words.add(word);
            }
        }


        return words;
    }


    // =====================================================
    // CLEAN TEXT
    // =====================================================

    private String cleanText(
            String text) {

        if (text == null) {

            return "";
        }


        return text
                .toLowerCase()
                .replaceAll(
                        "[^a-zA-Z0-9 ]",
                        " "
                )
                .replaceAll(
                        "\\s+",
                        " "
                )
                .trim();
    }


    // =====================================================
    // SAFE
    // =====================================================

    private String safe(
            String text) {

        return text == null
                ? ""
                : text;
    }


    // =====================================================
    // CONTAINS ANY
    // =====================================================

    private boolean containsAny(
            String question,
            String... words) {

        for (String word :
                words) {

            if (question.contains(
                    word.toLowerCase()
            )) {

                return true;
            }
        }


        return false;
    }
}