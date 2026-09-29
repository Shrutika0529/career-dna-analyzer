let questions = [];

window.onload = function () {

    fetch("http://localhost:8080/api/career/questions")
        .then(response => response.json())
        .then(data => {

            questions = data;

            let container = document.getElementById("questionContainer");

            container.innerHTML = "";

            data.forEach(question => {

                container.innerHTML += `
                    <div style="margin-bottom:20px;">

                        <h3>${question.question}</h3>

                        <input type="radio" name="q${question.id}" value="${question.option1}">
                        ${question.option1}<br>

                        <input type="radio" name="q${question.id}" value="${question.option2}">
                        ${question.option2}<br>

                        <input type="radio" name="q${question.id}" value="${question.option3}">
                        ${question.option3}<br>

                        <input type="radio" name="q${question.id}" value="${question.option4}">
                        ${question.option4}

                    </div>
                `;

            });

        });

};

function submitAnswers() {

    let userId = localStorage.getItem("userId");

    questions.forEach(question => {

        let selected = document.querySelector(
            `input[name="q${question.id}"]:checked`
        );

        if (selected != null) {

            fetch("http://localhost:8080/api/career/answer", {

                method: "POST",

                headers: {
                    "Content-Type": "application/json"
                },

                body: JSON.stringify({

                    userId: userId,

                    questionId: question.id,

                    answer: selected.value

                })

            });

        }

    });

    alert("Career Test Submitted Successfully");

    window.location.href = "result.html";

}