window.onload = function () {
    loadQuestions();
};

function saveQuestion() {

    fetch("http://localhost:8080/api/questions/add", {

        method: "POST",

        headers: {
            "Content-Type": "application/json"
        },

        body: JSON.stringify({

            question: document.getElementById("question").value,

            optionA: document.getElementById("option1").value,
            optionB: document.getElementById("option2").value,
            optionC: document.getElementById("option3").value,
            optionD: document.getElementById("option4").value

        })

    })

        .then(response => response.json())

        .then(data => {

            alert("Question Added Successfully");

            document.getElementById("question").value = "";
            document.getElementById("option1").value = "";
            document.getElementById("option2").value = "";
            document.getElementById("option3").value = "";
            document.getElementById("option4").value = "";

            loadQuestions();

        })

        .catch(error => {

            console.log(error);
            alert("Error Saving Question");

        });

}

function loadQuestions() {

    fetch("http://localhost:8080/api/questions/all")

        .then(response => response.json())

        .then(data => {

            let output = "";

            data.forEach(function(q){

                output += `
            <hr>

            <h3>${q.question}</h3>

            <p>A. ${q.optionA}</p>
            <p>B. ${q.optionB}</p>
            <p>C. ${q.optionC}</p>
            <p>D. ${q.optionD}</p>
            `;

            });

            document.getElementById("questions").innerHTML = output;

        });

}