package com.imooc.sell.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Author DateBro
 * @Date 2020/12/23 21:36
 */
@Component
@ServerEndpoint("/webSocket")
@Slf4j
public class WebSocketService {

    private Session session;

    private static CopyOnWriteArraySet<WebSocketService> socketSet = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        socketSet.add(this);
        log.info("【websocket消息】有新的连接，连接总数 = {}", socketSet.size());
    }

    @OnClose
    public void onClose() {
        socketSet.remove(this);
        log.info("【websocket消息】断开连接，连接总数 = {}", socketSet.size());
    }

    @OnMessage
    public void onMessage(String message) {
        log.info("【websocket消息】收到客户端发来的消息，message：{}", message);
    }

    public void sendMessage(String message) {
        log.info("进入websocket的sendMessage方法");
        for (WebSocketService webSocketService : socketSet) {
            log.info("【websocket消息】广播消息，message = {}", message);
            try {
                webSocketService.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                log.error("【websocket消息】广播消息出错，message = {}", e.getMessage());
            }
        }
    }
}
