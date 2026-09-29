const API_URL = "/api/admin/questions";

let allQuestions = [];

window.onload = function () {
    loadQuestions();
};

// ====================== LOAD QUESTIONS ======================

async function loadQuestions() {

    try {

        const response = await fetch(API_URL + "/all");

        allQuestions = await response.json();

        displayQuestions(allQuestions);

    } catch (error) {

        console.error(error);

        document.getElementById("questionContainer").innerHTML =
            "<h2 style='color:red;text-align:center;'>Unable to load questions.</h2>";

    }

}

// ====================== DISPLAY ======================

function displayQuestions(questions) {

    const container = document.getElementById("questionContainer");

    container.innerHTML = "";

    if (questions.length === 0) {

        container.innerHTML =
            "<h2 style='text-align:center;'>No Questions Found</h2>";

        return;
    }

    questions.forEach((q, index) => {

        container.innerHTML += `

        <div class="feature-card" style="margin-bottom:25px;">

            <h2 style="color:#00E5FF;">
                Question ${index + 1}
            </h2>

            <p style="font-size:18px;">
                <b>${q.question}</b>
            </p>

            <hr>

            <p><b>A :</b> ${q.optionA}</p>

            <p><b>B :</b> ${q.optionB}</p>

            <p><b>C :</b> ${q.optionC}</p>

            <p><b>D :</b> ${q.optionD}</p>

            <br>

            <button
                onclick="editQuestion(${q.id})"
                style="width:48%;margin-right:2%;">

                ✏ Edit

            </button>

            <button
                onclick="deleteQuestion(${q.id})"
                style="width:48%;background:#E53935;">

                🗑 Delete

            </button>

        </div>

        `;

    });

}

// ====================== SEARCH ======================

function searchQuestion() {

    const keyword = document
        .getElementById("searchBox")
        .value
        .toLowerCase();

    const filtered = allQuestions.filter(q =>

        q.question.toLowerCase().includes(keyword)

    );

    displayQuestions(filtered);

}

// ====================== EDIT ======================

function editQuestion(id) {

    alert("Edit Question ID : " + id);

    // Next step we'll create edit-question.html

}

// ====================== DELETE ======================

async function deleteQuestion(id) {

    const ok = confirm("Delete this question?");

    if (!ok) return;

    try {

        const response = await fetch(API_URL + "/delete/" + id, {

            method: "DELETE"

        });

        if (response.ok) {

            alert("Question Deleted Successfully");

            loadQuestions();

        } else {

            alert("Delete API not available yet.");

        }

    } catch (e) {

        alert("Delete API not implemented.");

    }

}

// ====================== LOGOUT ======================

function logout() {

    localStorage.clear();

    window.location.href = "login.html";

}