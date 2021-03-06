/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var canvas = document.getElementById("myCanvas");
var context = canvas.getContext("2d");


canvas.addEventListener("click", defineImage, false);

var checkbox = document.getElementById("check");

checkbox.addEventListener("click", sendBoxState, false);

function getCurrentPos(evt) {
    var rect = canvas.getBoundingClientRect();
    return {
        x: evt.clientX - rect.left,
        y: evt.clientY - rect.top
    };
}

function defineImage(evt) {
    var currentPos = getCurrentPos(evt);

    for (i = 0; i < document.inputForm.color.length; i++) {
        if (document.inputForm.color[i].checked) {
            var color = document.inputForm.color[i];
            break;
        }
    }

    for (i = 0; i < document.inputForm.shape.length; i++) {
        if (document.inputForm.shape[i].checked) {
            var shape = document.inputForm.shape[i];
            break;
        }
    }

    var json = JSON.stringify({
        "shape": shape.value,
        "color": color.value,
        "coords": {
            "x": currentPos.x,
            "y": currentPos.y
        }
    });
    drawImageText(json);
    sendText(json);
    defineImageBinary();
}

function drawImageText(image) {
    console.log("drawImageText");
    var json = JSON.parse(image);
    context.fillStyle = json.color;
    switch (json.shape) {
        case "circle":
            context.beginPath();
            context.arc(json.coords.x, json.coords.y, 5, 0, 2 * Math.PI, false);
            context.fill();
            break;
        case "square":
        default:
            context.fillRect(json.coords.x, json.coords.y, 10, 10);
            break;
    }
}

function showEditNumber(str) {
    $('#numberPeers').html(str);
}

function showAbortNumber(str) {
    $('#numberAborts').html(str);
}

function drawImageBinary(blob) {
    var bytes = new Uint8Array(blob);
//    console.log('drawImageBinary (bytes.length): ' + bytes.length);

    var imageData = context.createImageData(canvas.width, canvas.height);

    for (var i = 8; i < imageData.data.length; i++) {
        imageData.data[i] = bytes[i];
    }
    context.putImageData(imageData, 0, 0);

    var img = document.createElement('img');
    img.height = canvas.height;
    img.width = canvas.width;
    img.src = canvas.toDataURL();
}

function defineImageBinary() {
    var image = context.getImageData(0, 0, canvas.width, canvas.height);
    var buffer = new ArrayBuffer(image.data.length);
    var bytes = new Uint8Array(buffer);
    for (var i = 0; i < bytes.length; i++) {
        bytes[i] = image.data[i];
    }
    sendBinary(buffer);
}

function clearIt() {
    context.clearRect(0, 0, canvas.width, canvas.height);
    defineImageBinary();
}

window.onbeforeunload = function(evt) {
    if (checkbox.checked) {
        sendText("goChecked");
    } else {
        sendText("goNotChecked");
    }
}

function  sendBoxState() {
    if (checkbox.checked) {
        sendText("checked");
    } else {
        sendText("notChecked");
    }
}