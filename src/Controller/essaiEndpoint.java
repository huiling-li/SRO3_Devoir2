package Controller;

import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/lll")
public class essaiEndpoint {
    @OnOpen
    public void onOpen(Session session, ChatServer.EndpointConfigurator config){
        System.out.println("hahahaha");
    }
}

