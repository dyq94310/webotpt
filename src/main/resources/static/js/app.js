// Function to get the WebSocket URL based on the environment
function getWebSocketURL() {

    if (window.location.hostname === 'localhost' || window.location.hostname === '127.0.0.1') {
        return 'ws://localhost:8090/otp-websocket';
    } else {
        // Get the current domain from the window.location object
        const currentDomain = window.location.hostname;
        return `wss://${currentDomain}/otp-websocket`;
    }
}

// Get the WebSocket URL dynamically
const brokerURL = getWebSocketURL();

// Create the Stomp.js client with the dynamic WebSocket URL
const stompClient = new StompJs.Client({
    brokerURL: brokerURL
});

  
stompClient.onConnect = (frame) => {
    setConnected(true);
    console.log('Connected: ' + frame);
    stompClient.subscribe('/topic/otp', (greeting) => {
        showGreeting(greeting.body);
    });
};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

function setConnected(connected) {
    $("#greetings").html("");
}

function connect() {
    stompClient.activate();
}


function sendName() {
    stompClient.publish({
        destination: "/app/otp",
        body: $("#name").val()
    });
}

function showGreeting(message) {
    $("#greetings").html("<tr><td>" + message + "</td></tr>");
}

window.onload = function () {
    connect();
    $("#name").hide();
};

setInterval(function () {
    $("#name").val("123") ;
    sendName();
}, 2000);
