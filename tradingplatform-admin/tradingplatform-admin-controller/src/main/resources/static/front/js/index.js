var socket;

function login() {
    if (socket == null) {
        var id = document.getElementById("id").value;
        console.log(id);
        socket = new WebSocket("ws://10.0.20.31:8188?" + id);
    }
    socket.onmessage = function (event) {
        console.log(event.data);
        var objs = eval('(' + event.data + ')');
        document.getElementById("message").innerHTML = objs.content;
    };
    socket.onopen = function (event) {
        console.log("readyStates", socket.readyState);
    };
    socket.onclose = function (event) {
        document.getElementById("message").innerHTML = "连接已被关闭";
    };
}

function exit() {
    socket.close();
    document.getElementById('message').innerHTML = "退出成功";
}

function sendToSome() {
    var id = document.getElementById("id").value;
    var content = document.getElementById("content").value;
    var to = document.getElementById("to").value;
    var time = new Date();
    time = formatTime(time);

    var msg = {msgType: 'text', spreadType: 'oneToOne', time: time, content: content, from: id, to: to};
    var jsonStr = JSON.stringify(msg);
    socket.send(jsonStr);
}

function sendToAll() {
    var id = document.getElementById("id").value;
    var content = document.getElementById("content").value;
    var to = document.getElementById("to").value;

    var msg = {msgType: 'text', spreadType: 'broadcast', content: content, from: id, to: to};
    var jsonStr = JSON.stringify(msg);
    // console.log(jsonStr);
    socket.send(jsonStr);
}

function formatTime(obj) {
    var time = new Date(obj);
    var y = time.getFullYear();
    var m = time.getMonth() + 1;
    var d = time.getDate();
    var h = time.getHours();
    var mm = time.getMinutes();
    var s = time.getSeconds();
    return y + '-' + add0(m) + '-' + add0(d) + ' ' + add0(h) + ':' + add0(mm) + ':' + add0(s);
}

function add0(m) {
    return m < 10 ? '0' + m : m
}

function fileUpload() {
    var files = document.getElementById('files').files;
    if (!files.length) {
        alert('Please select a file!');
        return;
    }
    var file = files[0];

    var id = document.getElementById("id").value;
    var to = document.getElementById("to").value;
    var time = new Date();
    time = formatTime(time);

    var fileInfo = {
        "fileName": file.name,
        "fileSize": file.size,
    };
    var fileStr = JSON.stringify(fileInfo);

    var msg = {msgType: 'file', spreadType: 'oneToOne', time: time, content: fileStr, from: id, to: to};
    var jsonStr = JSON.stringify(msg);


//  console.log(JSON.stringify(fileInfo));
    socket.send(jsonStr);
    socket.onmessage = function (event) {
        var msg = JSON.parse(event.data);
        if (msg.msgType == "text") {
            document.getElementById("message").innerHTML = msg.content;
        } else {
            if (msg.complete == "true") {
                console.log("文件上传成功!");
            } else {
                readBlob(files, msg.startByte, msg.stopByte);
            }
        }
    };
}

function readBlob(files, opt_startByte, opt_stopByte) {
    if (!files.length) {
        alert('Please select a file!');
        return;
    }
    var file = files[0];
    var start = parseInt(opt_startByte) || 0;
    var stop = parseInt(opt_stopByte) || file.size - 1;

    var reader = new FileReader();
    var blob;
    if (file.slice) {
        blob = file.slice(start, stop);
    } else if (file.webkitSlice) {
        blob = file.webkitSlice(start, stop);
    } else if (file.mozSlice) {
        blob = file.mozSlice(start, stop);
    }


    reader.readAsArrayBuffer(blob);
    reader.onloadend = function (evt) {
        if (evt.target.readyState == FileReader.DONE) { // DONE == 2
            socket.send(reader.result);
        }
    };
}



