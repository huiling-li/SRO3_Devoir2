window.addEventListener( "load", function( event ) {

    let ws = new WebSocket( "ws://"+window.location.host );

    let txtHistory = document.getElementById( "history" );
    let txtMessage = document.getElementById( "txtMessage" );
    txtMessage.focus();

    ws.addEventListener( "open", function( evt ) {
        console.log( "Connection established" );
    });

    ws.addEventListener( "message", function( evt ) {
        let message = evt.data;//从后台收到 在前台展示
        console.log( "Receive new message: " + message );
        txtHistory.value += message + "\n";
    });

    ws.addEventListener( "close", function( evt ) {
        console.log( "Connection closed" );
    });


    let btnSend = document.getElementById( "btnSend" );
    btnSend.addEventListener( "click", function( clickEvent ) {
        ws.send( txtMessage.value );//发给后台 资源
        txtMessage.value = "";//发出去就清空
        txtMessage.focus();
    });

    let btnClose = document.getElementById( "btnClose" );
    btnClose.addEventListener( "click", function( clickEvent ) {
        ws.close();
    });

});