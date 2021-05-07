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
    private Hashtable<String, Session> sessions = new Hashtable<>();//stocke tous les sessions

    /**
     * Cette m�thode est d�clench�e � chaque connexion d'un utilisateur.
     */
    @OnOpen
    public void open(Session session, @PathParam("pseudo") String pseudo, EndpointConfig config) {
        this.session = session;
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        this.httpSession = httpSession;
        String username = (String) httpSession.getAttribute("user");
        onlineUsers.put(username,this);
        sendMessage( "Admin >>> Connection established for " + pseudo );
        session.getUserProperties().put( "pseudo", pseudo );
        sessions.put( session.getId(), session );//ajout d'une nouvelle session
        this.room = (Room)httpSession.getAttribute("room");
    }

    /**
     * Cette m�thode est d�clench�e � chaque d�connexion d'un utilisateur.
     */
    @OnClose
    public void close(Session session) {
        String pseudo = (String) session.getUserProperties().get( "pseudo" );
        sessions.remove( session.getId() );//supprime la session
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
        String pseudo = (String) session.getUserProperties().get( "pseudo" );
        String fullMessage = pseudo + " >>> " + message;

        sendMessage( fullMessage );
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
//            // On envoie le message a tout le monde.
//        for( Session session : room.getAllUsers(). ) {//broadcast
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