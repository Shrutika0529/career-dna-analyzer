async function loadResult() {

    const studentId =
        localStorage.getItem("userId");


    // ==========================================
    // CHECK LOGIN
    // ==========================================

    if (!studentId) {

        alert("Please login first.");

        window.location.href =
            "login.html";

        return;
    }


    try {

        console.log(
            "Loading Career DNA for student:",
            studentId
        );


        // ==========================================
        // CALL SPRING BOOT API
        // ==========================================

        const response =
            await fetch(
                "/api/career-dna/" + studentId
            );


        console.log(
            "Response status:",
            response.status
        );


        if (!response.ok) {

            const errorText =
                await response.text();

            console.error(
                "Server Error:",
                errorText
            );

            alert(
                "Unable to load career result."
            );

            return;
        }


        const data =
            await response.json();


        console.log(
            "Career DNA Result:",
            data
        );


        // ==========================================
        // RECOMMENDED CAREER
        // ==========================================

        const careerName =
            document.getElementById(
                "careerName"
            );


        if (careerName) {

            careerName.textContent =
                data.recommendedCareer ||
                "Career Not Available";

        }


        // ==========================================
        // OVERALL SCORE
        // ==========================================

        const overallScore =
            data.overallScore ?? 0;


        const overallElement =
            document.getElementById(
                "overallScore"
            );


        if (overallElement) {

            overallElement.textContent =
                overallScore;

        }


        // ==========================================
        // CAREER MATCH SCORE
        // ==========================================

        const careerScore =
            document.getElementById(
                "careerScore"
            );


        if (careerScore) {

            careerScore.textContent =
                overallScore;

        }


        // ==========================================
        // INDUSTRY READINESS
        // ==========================================

        const readiness =
            data.industryReadiness ?? 0;


        const readinessElement =
            document.getElementById(
                "industryReadiness"
            );


        if (readinessElement) {

            readinessElement.textContent =
                readiness;

        }


        // ==========================================
        // NUMBER OF CAREERS
        // ==========================================

        const scores =
            data.careerScores || {};


        const careerCount =
            document.getElementById(
                "careerCount"
            );


        if (careerCount) {

            careerCount.textContent =
                Object.keys(scores).length;

        }


        // ==========================================
        // AI INSIGHT
        // ==========================================

        const insight =
            document.getElementById(
                "careerInsight"
            );


        if (insight) {

            insight.innerHTML =

                "Based on your assessment, our AI " +
                "identified <strong>" +
                (data.recommendedCareer ||
                    "your strongest career") +
                "</strong> as your leading career " +
                "pathway. Your overall profile achieved " +
                "<strong>" +
                overallScore +
                "%</strong> and your industry readiness " +
                "is currently <strong>" +
                readiness +
                "%</strong>. Continue developing the " +
                "skills associated with your recommended " +
                "career to strengthen your professional profile.";

        }


        // ==========================================
        // CAREER SCORE BARS
        // ==========================================

        const careerContainer =
            document.getElementById(
                "careerScores"
            );


        if (careerContainer) {

            careerContainer.innerHTML = "";


            const sortedCareers =
                Object.entries(scores)
                    .sort(
                        (a, b) =>
                            b[1] - a[1]
                    );


            sortedCareers.forEach(
                ([career, score]) => {


                    const row =
                        document.createElement(
                            "div"
                        );


                    row.className =
                        "career-score-item";


                    row.innerHTML = `

<strong>
${career}
</strong>

<div class="progress">

    <div
        class="progress-bar"
        style="width: 0%"
    ></div>

</div>

<span>
                            ${score}%
                        </span>

    `;


                    careerContainer.appendChild(
                        row
                    );


                    // Animate progress bar

                    setTimeout(() => {

                        const bar =
                            row.querySelector(
                                ".progress-bar"
                            );


                        if (bar) {

                            bar.style.width =
                                Math.min(
                                    score,
                                    100
                                ) + "%";

                        }

                    }, 100);

                }
            );

        }


        console.log(
            "AI Career Report loaded successfully."
        );


    } catch (error) {

        console.error(
            "Error loading result:",
            error
        );


        alert(
            "Unable to connect to the server. " +
            "Please make sure Spring Boot is running."
        );

    }

}



// ==========================================
// PAGE LOAD
// ==========================================

window.onload = function () {

    loadResult();

};



// ==========================================
// DASHBOARD
// ==========================================

function goDashboard() {

    window.location.href =
        "dashboard.html";

}



// ==========================================
// RETAKE ASSESSMENT
// ==========================================

function retakeTest() {

    window.location.href =
        "student-assessment.html";

}



// ==========================================
// LOGOUT
// ==========================================

function logout() {

    localStorage.clear();

    window.location.href =
        "login.html";

}

