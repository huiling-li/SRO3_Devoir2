package com;

import Controller.ChatServer;
import jakarta.servlet.http.HttpSession;

import javax.servlet.http.HttpServlet;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/chat")
public class ChatEndpoint extends HttpServlet{

    //stocker les objects pour chaque user
    private static Map<String,ChatEndpoint> onLineUsers = new ConcurrentHashMap<>();
    //session: pour envoyer les messages à une personne indiquée
    private Session session;
    //Httpsession: danslaquelle on a stocké username
    private HttpSession httpSession;


    @OnOpen
    //appelé au moment de connexion
    public void onOpen(Session session, ChatServer.EndpointConfigurator config){
        //récupération d'object de session et httpsession
        System.out.println("aaaaaaa");
        this.session = session;
        //HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());

        this.httpSession = httpSession;

    }

    @OnOpen
    public void onOpen(Session session, EndpointConfig config){
        this.session = session;
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        this.httpSession = httpSession;
        //ré
        String username = (String) httpSession.getAttribute("user");
        log.info("user en ligne："+username);
        onLineUsers.put(username,this);
        String message = MessageUtils.getMessage(true,null,getNames());
        broadcastAllUsers(message);
    }
    //récupération d'usersnames
    private Set<String> getNames(){
        return onLineUsers.keySet();
    }
    //envoyer les messages
    private void broadcastAllUsers(String message){
        try{
            Set<String> names = onLineUsers.keySet();
            for(String name : names){
                ChatEndPoint chatEndPoint = onLineUsers.get(name);
                chatEndPoint.session.getBasicRemote().sendText(message);//envoyer les messages
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //envoyer les messages entre les users
    @OnMessage
    public void onMessage(String message,Session session){
        try{
            ObjectMapper mapper = new ObjectMapper();
            Message mess = mapper.readValue(message,Message.class);
            //le user destiné
            String toName = mess.getToName();
            //récupérer les messages
            String data = mess.getMessage();
            String username = (String) httpSession.getAttribute("user");
            log.info(username + "à"+toName+"les messages：" + data);
            String resultMessage = MessageUtils.getMessage(false,username,data);
            if(StringUtils.hasLength(toName)) {
                onLineUsers.get(toName).session.getBasicRemote().sendText(resultMessage);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose(Session session){
        String username = (String) httpSession.getAttribute("user");
        log.info("pas en line："+ username);
        if (username != null){
            onLineUsers.remove(username);
            UserInterceptor.onLineUsers.remove(username);
        }
        httpSession.removeAttribute("user");
//        Map a = UserInterceptor.onLineUsers;
//        System.out.println(a);
        MessageUtils.getMessage(true,null,getNames());
    }
}

