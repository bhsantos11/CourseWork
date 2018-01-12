const socket = new WebSocket("ws://localhost:500");
socket.addEventListener('open', function (event) {
    socket.send('Hello Server!');
});
