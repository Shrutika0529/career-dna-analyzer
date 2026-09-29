window.onload = function () {

    fetch("http://localhost:8080/api/admin/students")
        .then(response => response.json())
        .then(data => {

            console.log("Students Data:", data);

            alert(JSON.stringify(data));

            let table = document.getElementById("studentTable");

            table.innerHTML = "";

            data.forEach(function(student){

                console.log(student);

                table.innerHTML += `
                    <tr>
                        <td>${student.id}</td>
                        <td>${student.fullName}</td>
                        <td>${student.email}</td>
                        <td>${student.role}</td>
                    </tr>
                `;
            });

        })
        .catch(error => {

            console.error(error);

            alert(error);

        });

}