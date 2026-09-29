let questions = [];

// =====================================================
// LOAD CAREER QUESTIONS
// =====================================================

async function loadQuestions() {

    try {

        console.log("Loading career questions...");

        const response =
            await fetch("/api/career-questions/all");

        console.log("Status:", response.status);

        if (!response.ok) {
            throw new Error("Unable to load career questions.");
        }

        questions = await response.json();

        console.log("Questions received:", questions);

        const container =
            document.getElementById("questionContainer");

        if (!container) {
            console.error("questionContainer not found.");
            return;
        }

        container.innerHTML = "";

        if (questions.length === 0) {

            container.innerHTML = `
                <h2 style="text-align:center;">
                    No Career Questions Found
                </h2>
            `;

            return;
        }

        questions.forEach((q, index) => {

            container.innerHTML += `
                <div class="question-card">

                    <h3>
                        Question ${index + 1}
                    </h3>

                    <p>
                        <b>${q.question}</b>
                    </p>

                    <label>
                        <input
                            type="radio"
                            name="q${q.id}"
                            value="A">
                        ${q.option1}
                    </label>

                    <br><br>

                    <label>
                        <input
                            type="radio"
                            name="q${q.id}"
                            value="B">
                        ${q.option2}
                    </label>

                    <br><br>

                    <label>
                        <input
                            type="radio"
                            name="q${q.id}"
                            value="C">
                        ${q.option3}
                    </label>

                    <br><br>

                    <label>
                        <input
                            type="radio"
                            name="q${q.id}"
                            value="D">
                        ${q.option4}
                    </label>

                </div>

                <br>
            `;
        });

        console.log("Questions displayed successfully.");

    } catch (error) {

        console.error(
            "Error loading career questions:",
            error
        );

        const container =
            document.getElementById("questionContainer");

        if (container) {

            container.innerHTML = `
                <h2 style="color:red;text-align:center;">
                    Unable to load career questions.
                </h2>
            `;
        }
    }
}


// =====================================================
// SUBMIT ANSWERS
// =====================================================

async function submitAnswers() {

    const studentId =
        localStorage.getItem("userId");

    console.log("Student ID:", studentId);

    if (!studentId) {

        alert("Please login first.");

        window.location.href = "login.html";

        return;
    }

    if (questions.length === 0) {

        alert("Questions are not loaded yet.");

        return;
    }

    let answeredCount = 0;

    try {

        for (const q of questions) {

            const option =
                document.querySelector(
                    'input[name="q' + q.id + '"]:checked'
                );

            if (option == null) {

                console.log(
                    "Question not answered:",
                    q.id
                );

                continue;
            }

            console.log(
                "Saving answer:",
                q.id,
                option.value
            );

            const response =
                await fetch(
                    "/api/answers/submit",
                    {
                        method: "POST",

                        headers: {
                            "Content-Type":
                                "application/json"
                        },

                        body: JSON.stringify({

                            studentId:
                                Number(studentId),

                            questionId:
                            q.id,

                            selectedOption:
                            option.value
                        })
                    }
                );

            console.log(
                "Save response:",
                response.status
            );

            if (!response.ok) {

                const errorText =
                    await response.text();

                console.error(
                    "Failed to save answer:",
                    errorText
                );

                alert(
                    "Unable to save answer for Question " +
                    (questions.indexOf(q) + 1)
                );

                return;
            }

            answeredCount++;
        }

        console.log(
            "Total answers saved:",
            answeredCount
        );

        if (answeredCount === 0) {

            alert(
                "Please answer at least one question."
            );

            return;
        }

        alert(
            "Assessment submitted successfully!"
        );

        window.location.href =
            "result.html";

    } catch (error) {

        console.error(
            "Submit error:",
            error
        );

        alert(
            "Unable to submit assessment. Please try again."
        );
    }
}


// =====================================================
// LOGOUT
// =====================================================

function logout() {

    localStorage.clear();

    window.location.href = "login.html";
}


// =====================================================
// START
// =====================================================

window.onload = function () {

    loadQuestions();

};