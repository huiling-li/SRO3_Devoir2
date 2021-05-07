package com;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

import Controller.ChatServer;
import jakarta.servlet.http.HttpSession;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.net.http.WebSocket;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
@ServerEndpoint("/ws")
 class Websocket extends HttpServlet {
    @OnOpen
    //appelé au moment de connexion
    public void onOpen(Session session, ChatServer.EndpointConfigurator config){

        System.out.println("aaaaaaa");

    }


}
