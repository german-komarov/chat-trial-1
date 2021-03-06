var stompClient = null;





// function setConnected(connected) {
//     $("#connect").prop("disabled", connected);
//     $("#disconnect").prop("disabled", !connected);
//     if (connected) {
//         $("#conversation").show();
//     }
//     else {
//         $("#conversation").hide();
//     }
//     $("#greetings").html("");
// }

function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        // setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings/'+$("#roomId").val(), function (greeting) {
            showGreeting(JSON.parse(greeting.body).content);
        });
    });
}



function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    // setConnected(false);
    console.log("Disconnected");
}


connect();



function sendName() {
    stompClient.send("/app/hello", {},
        JSON.stringify({'sender': $("#sender").val(),'receiver': $("#receiver").val(),
            'roomId':$("#roomId").val(),'message':$("#message").val()}));
}

function showGreeting(message) {
    $("#greetings").append(message + "</br>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#send" ).click(function() { sendName(); });
});