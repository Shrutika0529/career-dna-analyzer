document.getElementById("adminName").innerHTML =

    localStorage.getItem("adminName");

function logout(){

    localStorage.clear();

    window.location.href="admin-login.html";

}