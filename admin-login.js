async function adminLogin(){

    const email =
        document.getElementById("email").value;

    const password =
        document.getElementById("password").value;

    const response =
        await fetch("/api/auth/login",{

            method:"POST",

            headers:{
                "Content-Type":"application/json"
            },

            body:JSON.stringify({

                email:email,

                password:password

            })

        });

    const data =
        await response.json();

    if(data.success){

        if(data.role !== "ADMIN"){

            alert("Only Admin can login.");

            return;

        }

        localStorage.setItem("adminId",data.userId);

        localStorage.setItem("adminName",data.fullName);

        window.location.href="admin-dashboard.html";

    }

    else{

        alert(data.message);

    }

}