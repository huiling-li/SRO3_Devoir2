package com.websocket.websocketdemo.ws;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.websocket.websocketdemo.interceptor.UserInterceptor;
import com.websocket.websocketdemo.pojo.Message;
import com.websocket.websocketdemo.utils.MessageUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
@Slf4j
@ServerEndpoint(value = "/chat",configurator = GetHttpSessionConfigurator.class)
@Component
public class ChatEndPoint {
    //用线程安全的map来保存当前用户
    private static Map<String,ChatEndPoint> onLineUsers = new ConcurrentHashMap<>();
    //声明一个session对象，通过该对象可以发送消息给指定用户，不能设置为静态，每个ChatEndPoint有一个session才能区分.(websocket的session)
    private Session session;
    //保存当前登录浏览器的用户
    private HttpSession httpSession;


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
