/* =====================================================
   CAREER DNA AI CHATBOT
===================================================== */


/* =====================================================
   TOGGLE CHATBOT
===================================================== */

function toggleChatbot() {

    const chatWindow =
        document.getElementById("chatWindow");

    if (!chatWindow) {

        console.error(
            "chatWindow not found"
        );

        return;
    }


    if (
        chatWindow.style.display === "flex"
    ) {

        chatWindow.style.display = "none";

    } else {

        chatWindow.style.display = "flex";

        const input =
            document.getElementById("message");

        if (input) {

            setTimeout(() => {

                input.focus();

            }, 100);

        }

    }

}


/* =====================================================
   ENTER KEY
===================================================== */

document.addEventListener(
    "DOMContentLoaded",
    function () {

        const input =
            document.getElementById("message");


        if (!input) {

            console.error(
                "Chat input not found"
            );

            return;
        }


        input.addEventListener(
            "keydown",
            function (event) {

                if (
                    event.key === "Enter" &&
                    !event.shiftKey
                ) {

                    event.preventDefault();

                    event.stopPropagation();

                    askCareerAI();

                }

            }
        );

    }
);


/* =====================================================
   QUICK QUESTION
===================================================== */

function quickQuestion(question) {

    const input =
        document.getElementById("message");


    if (!input) {

        return;

    }


    input.value = question;

    input.focus();

    askCareerAI();

}


/* =====================================================
   ASK CAREER AI
===================================================== */

async function askCareerAI() {

    const input =
        document.getElementById("message");

    const messages =
        document.getElementById("chatMessages");


    if (!input || !messages) {

        console.error(
            "Chat elements not found"
        );

        return;

    }


    const question =
        input.value.trim();


    /* Prevent empty question */

    if (!question) {

        return;

    }


    /* Clear input immediately */

    input.value = "";


    /* Show user message */

    addUserMessage(question);


    /* Show typing */

    showTyping();


    try {

        console.log(
            "Sending question:",
            question
        );


        const studentId =
            localStorage.getItem("userId");


        const conversationId =
            localStorage.getItem(
                "careerConversationId"
            );


        const response =
            await fetch(
                "/api/career-chat/ask",
                {

                    method: "POST",

                    headers: {

                        "Content-Type":
                            "application/json"

                    },

                    body:
                        JSON.stringify({

                            question:
                            question,

                            conversationId:
                                conversationId || null,

                            studentId:
                                studentId
                                    ? Number(studentId)
                                    : null

                        })

                }
            );


        console.log(
            "AI response status:",
            response.status
        );


        if (!response.ok) {

            const errorText =
                await response.text();

            console.error(
                "AI server error:",
                errorText
            );

            removeTyping();

            addAIMessage(
                "⚠️ Sorry, I could not connect to the Career AI service."
            );

            return;

        }


        const data =
            await response.json();


        console.log(
            "Career AI response:",
            data
        );


        removeTyping();


        /* Save conversation ID */

        if (
            data.conversationId
        ) {

            localStorage.setItem(
                "careerConversationId",
                data.conversationId
            );

        }


        /* Support different response field names */

        const answer =
            data.answer ||
            data.response ||
            data.message ||
            data.reply;


        if (!answer) {

            addAIMessage(
                "I received your question, but I couldn't generate a response."
            );

            return;

        }


        addAIMessage(answer);


    } catch (error) {

        console.error(
            "Career AI error:",
            error
        );


        removeTyping();


        addAIMessage(
            "⚠️ I'm having trouble connecting to the AI service. Please try again."
        );

    }

}


/* =====================================================
   ADD USER MESSAGE
===================================================== */

function addUserMessage(message) {

    const messages =
        document.getElementById(
            "chatMessages"
        );


    const div =
        document.createElement("div");


    div.className =
        "user-message";


    const bubble =
        document.createElement("div");


    bubble.className =
        "user-bubble";


    bubble.textContent =
        message;


    div.appendChild(bubble);


    messages.appendChild(div);


    scrollChat();

}


/* =====================================================
   ADD AI MESSAGE
===================================================== */

function addAIMessage(message) {

    const messages =
        document.getElementById(
            "chatMessages"
        );


    const div =
        document.createElement("div");


    div.className =
        "ai-message";


    const avatar =
        document.createElement("div");


    avatar.className =
        "message-avatar";


    avatar.textContent =
        "🤖";


    const bubble =
        document.createElement("div");


    bubble.className =
        "message-bubble";


    const name =
        document.createElement("div");


    name.className =
        "message-name";


    name.textContent =
        "Career DNA AI";


    const text =
        document.createElement("div");


    text.className =
        "message-text";


    /*
       Convert new lines to <br>
       but keep text safe.
    */

    text.textContent =
        message;


    bubble.appendChild(name);

    bubble.appendChild(text);


    div.appendChild(avatar);

    div.appendChild(bubble);


    messages.appendChild(div);


    scrollChat();

}


/* =====================================================
   TYPING INDICATOR
===================================================== */

function showTyping() {

    removeTyping();


    const messages =
        document.getElementById(
            "chatMessages"
        );


    const div =
        document.createElement("div");


    div.id =
        "typingIndicator";


    div.className =
        "ai-message";


    div.innerHTML = `

        <div class="message-avatar">
            🤖
        </div>

        <div class="message-bubble">

            <div class="message-name">
                Career DNA AI
            </div>

            <div class="typing-message">

                <span class="typing-dot"></span>

                <span class="typing-dot"></span>

                <span class="typing-dot"></span>

            </div>

        </div>

    `;


    messages.appendChild(div);


    scrollChat();

}


/* =====================================================
   REMOVE TYPING
===================================================== */

function removeTyping() {

    const typing =
        document.getElementById(
            "typingIndicator"
        );


    if (typing) {

        typing.remove();

    }

}


/* =====================================================
   SCROLL CHAT
===================================================== */

function scrollChat() {

    const messages =
        document.getElementById(
            "chatMessages"
        );


    if (!messages) {

        return;

    }


    setTimeout(
        function () {

            messages.scrollTop =
                messages.scrollHeight;

        },
        50
    );

}