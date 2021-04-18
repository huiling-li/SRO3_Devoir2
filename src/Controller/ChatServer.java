package Controller;
//package src.Model;

import Model.Room;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;


@ServerEndpoint(value = "/chatserver" ,
        configurator=ChatServer.EndpointConfigurator.class)
public class ChatServer {

    private static Map<String,ChatServer> onlineUsers = new ConcurrentHashMap<>();
    private Session session;
    private HttpSession httpSession;
    private static ChatServer singleton = new ChatServer();
    private Room room;

    private ChatServer() {
    }

    /**
     * Acquisition de notre unique instance ChatServer
     */
    public static ChatServer getInstance() {
        return ChatServer.singleton;
    }

    /**
     * On maintient toutes les sessions utilisateurs dans une collection.
     */
    private Hashtable<String, Session> sessions = new Hashtable<>();//直接把所有session存起来

    /**
     * Cette m�thode est d�clench�e � chaque connexion d'un utilisateur.
     */
    @OnOpen
    public void open(Session session, @PathParam("pseudo") String pseudo, EndpointConfig config) {//有什么区别
        this.session = session;
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        this.httpSession = httpSession;
        String username = (String) httpSession.getAttribute("user");
        onlineUsers.put(username,this);
        sendMessage( "Admin >>> Connection established for " + pseudo );
        session.getUserProperties().put( "pseudo", pseudo );
        sessions.put( session.getId(), session );//加新会话
        this.room = (Room)httpSession.getAttribute("room");//点链接时传过来 每次都更新
    }

    /**
     * Cette m�thode est d�clench�e � chaque d�connexion d'un utilisateur.
     */
    @OnClose
    public void close(Session session) {//这个会话知道的
        String pseudo = (String) session.getUserProperties().get( "pseudo" );
        sessions.remove( session.getId() );//移除会话
        sendMessage( "Admin >>> Connection closed for " + pseudo );
    }

    /**
     * Cette m�thode est d�clench�e en cas d'erreur de communication.
     */
    @OnError
    public void onError(Throwable error) {
        System.out.println( "Error: " + error.getMessage() );
    }

    /**
     * Cette m�thode est d�clench�e � chaque r�ception d'un message utilisateur.
     */
    @OnMessage
    public void handleMessage(String message, Session session) {
        String pseudo = (String) session.getUserProperties().get( "pseudo" );//你是谁
        String fullMessage = pseudo + " >>> " + message;

        sendMessage( fullMessage );//发送数据 加到对话框
    }

    /**
     * Une m�thode priv�e, sp�cifique � notre exemple.
     * Elle permet l'envoie d'un message aux participants de la discussion.
     */
    private void sendMessage( String fullMessage ) {
        // Affichage sur la console du server Web.
        System.out.println( fullMessage );
        for( Model.User user : room.getAllUsers().values()){
            ChatServer chatServer = onlineUsers.get(user.getLogin());
            try {
                chatServer.session.getBasicRemote().sendText(fullMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//            // On envoie le message � tout le monde.
//        for( Session session : room.getAllUsers(). ) {//遍历 广播
//            try {
//                session.getBasicRemote().sendText( fullMessage );
//            } catch( Exception exception ) {
//                System.out.println( "ERROR: cannot send message to " + session.getId() );
//            }
//        }
    }

    /**
     * Permet de ne pas avoir une instance diff�rente par client.
     * ChatServer est donc g�rer en "singleton" et le configurateur utilise ce singleton.
     */
    public static class EndpointConfigurator extends ServerEndpointConfig.Configurator {
        @Override
        @SuppressWarnings("unchecked")
        public <T> T getEndpointInstance(Class<T> endpointClass) {
            return (T) ChatServer.getInstance();
        }
    }
}