const API = "/api/career-mapping";


window.onload = function(){

    loadMappings();

};




// SAVE MAPPING

async function saveMapping(){


    let data = {


        questionId:
            Number(document.getElementById("questionId").value),


        optionSelected:
        document.getElementById("optionSelected").value,


        careerName:
        document.getElementById("careerName").value,


        score:
            Number(document.getElementById("score").value)


    };



    if(!data.questionId ||
        !data.optionSelected ||
        !data.careerName ||
        !data.score){


        alert("Please fill all fields");

        return;

    }



    let response =
        await fetch(API + "/add",{


            method:"POST",


            headers:{


                "Content-Type":"application/json"


            },


            body:JSON.stringify(data)


        });



    if(response.ok){


        alert("Career Mapping Added Successfully 🎯");


        clearForm();


        loadMappings();


    }
    else{


        alert("Error while saving mapping");


    }


}





// LOAD MAPPINGS


async function loadMappings(){


    let response =
        await fetch(API + "/all");


    let mappings =
        await response.json();



    let box =
        document.getElementById("mappingList");



    box.innerHTML="";



    mappings.forEach((map,index)=>{


        box.innerHTML += `


        <div class="feature-card">


            <h3>
            🎯 Mapping ${index+1}
            </h3>


            <p>
            Question ID :
            ${map.questionId}
            </p>


            <p>
            Option :
            ${map.optionSelected}
            </p>


            <p>
            Career :
            ${map.careerName}
            </p>


            <p>
            Score :
            ${map.score}
            </p>


        </div>


        <br>


        `;


    });


}





function clearForm(){


    document.getElementById("questionId").value="";

    document.getElementById("optionSelected").value="";

    document.getElementById("careerName").value="";

    document.getElementById("score").value="";


}





function logout(){


    localStorage.clear();

    window.location.href="login.html";


}