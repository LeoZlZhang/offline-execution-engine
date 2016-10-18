var stompClient = null;
var myModal = $('#modal_execute_output');
var modalCloseButton = $('#model_close');
var modalBody = $("#modal_execute_output_body");


function connect(topic) {
    var socket = new SockJS('/ee/execute-engine-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/' + topic, function (message) {
            printOutput(message.body);
        });
    });
}


function printOutput(message) {
    if (message === 'EOF') {
        modalCloseButton.show();
    } else {
        modalBody.append("<p>" + message + "</p>");
        modalBody[0].scrollTop = modalBody[0].scrollHeight;
    }
}

function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
        modalBody.empty();
    }
}

myModal.on('ee.execute', function (e, testingTopic) {
    modalCloseButton.hide();
    connect(testingTopic);
});


myModal.on('hidden.bs.modal', function () {
    disconnect();
});

