const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8090/otp-websocket'
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
