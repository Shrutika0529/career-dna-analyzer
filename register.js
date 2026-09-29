console.log("register.js loaded");

function registerUser() {

    console.log("Register button clicked");

    const fullName = document.getElementById("fullName").value.trim();
    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value.trim();

    const role = "STUDENT";

    if (!fullName || !email || !password) {
        alert("Please fill all fields.");
        return;
    }

    const user = {
        fullName: fullName,
        email: email,
        password: password,
        role: role
    };

    console.log("Sending Data:", user);

    fetch("http://localhost:8080/api/auth/register", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(user)
    })
        .then(async response => {
            console.log("Status:", response.status);

            const data = await response.json();

            if (response.ok && data.success) {
                alert(data.message || "Registration Successful!");
                window.location.href = "index.html"; // Change to login.html if that's your login page
            } else {
                alert(data.message || "Registration Failed!");
            }
        })
        .catch(error => {
            console.error("Error:", error);
            alert("Cannot connect to the server. Make sure Spring Boot is running.");
        });

}