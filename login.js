console.log("login.js loaded");

async function login() {

    console.log("Login button clicked");

    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value.trim();

    if (email === "" || password === "") {
        alert("Please enter Email and Password.");
        return;
    }

    try {

        const response = await fetch(
            "http://localhost:8080/api/auth/login",
            {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    email: email,
                    password: password
                })
            }
        );

        const data = await response.json();

        console.log("Server Response:", data);

        if (data.success) {

            // =====================================
            // SAVE LOGIN INFORMATION
            // =====================================

            localStorage.setItem(
                "userId",
                data.userId
            );

            localStorage.setItem(
                "fullName",
                data.fullName
            );

            localStorage.setItem(
                "role",
                data.role
            );

            localStorage.setItem(
                "email",
                email
            );

            console.log("Saved User ID:", data.userId);
            console.log("Saved Role:", data.role);

            alert("Login Successful!");

            // =====================================
            // REDIRECT
            // =====================================

            if (data.role === "ADMIN") {

                window.location.href =
                    "admin-dashboard.html";

            } else {

                window.location.href =
                    "dashboard.html";
            }

        } else {

            alert(
                data.message ||
                "Invalid Email or Password"
            );
        }

    } catch (error) {

        console.error("Login Error:", error);

        alert(
            "Cannot connect to the server. " +
            "Please make sure Spring Boot is running."
        );
    }
}