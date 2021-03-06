/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var wsUri
if (document.location.protocol === "https:") {
    wsUri = "wss://" + document.location.host + "/EdcodisRT/whiteboardendpoint";
} else {
    wsUri = "ws://" + document.location.host + "/EdcodisRT/whiteboardendpoint";
}

var websocket = new WebSocket(wsUri);

websocket.onerror = function(evt) {
    onError(evt)
};

function onError(evt) {
    writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data);
}

//// For testing purposes
//var output = document.getElementById("output");
//websocket.onopen = function(evt) { onOpen(evt) };
//
//function writeToScreen(message) {
//    output.innerHTML += message + "<br>";
//}
//
//function onOpen() {
//    writeToScreen("Connected to " + wsUri);
//}
//// End test functions

websocket.onmessage = function(evt) {
    onMessage(evt);
};

function sendText(json) {
    console.log("sending text: " + json);
    websocket.send(json);
    defineImageBinary();
}

function onMessage(evt) {
    console.log("received: " + evt.data);
    if (typeof evt.data === "string") {
        var json = JSON.parse(evt.data);
        showEditNumber(json.edit);
        showAbortNumber(json.abort);
        if(json.edit===0){
            clearIt();
        }
    } else {
        drawImageBinary(evt.data);
    }
}

websocket.binaryType = "arraybuffer";
function sendBinary(bytes) {
    console.log("sending binary: " + Object.prototype.toString.call(bytes));
    websocket.send(bytes);
}
