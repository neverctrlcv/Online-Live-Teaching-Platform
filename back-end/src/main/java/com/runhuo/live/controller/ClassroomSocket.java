package com.runhuo.live.controller;

import com.alibaba.fastjson2.JSONObject;
import com.runhuo.live.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/23 11:06
 */
@Controller
@ServerEndpoint(value = "/classroomSocket/{nameAndId}/{classId}")
@SuppressWarnings("all")
@Slf4j
public class ClassroomSocket {


    private static RedisService redisService;
    private static ConcurrentHashMap<Long, ConcurrentHashMap<String, Session>> webSocketMap = new ConcurrentHashMap<>();

    private static ConcurrentHashMap<String, Session> classMap = new ConcurrentHashMap<>();

    @Autowired
    public void setRedisService(RedisService redisService) {
        ClassroomSocket.redisService = redisService;
    }


    @OnOpen
    public void onOpen(Session session, @PathParam("nameAndId") String nameAndId, @PathParam("classId") Long classId) {
        ConcurrentHashMap<String, Session> stringSessionConcurrentHashMap;
        if (webSocketMap.containsKey(classId)) {
            stringSessionConcurrentHashMap = webSocketMap.get(classId);
            stringSessionConcurrentHashMap.put(nameAndId, session);
            webSocketMap.remove(classId);
        } else {
            stringSessionConcurrentHashMap = new ConcurrentHashMap<>();
            stringSessionConcurrentHashMap.put(nameAndId, session);
        }
        webSocketMap.put(classId, stringSessionConcurrentHashMap);
    }

    @OnClose
    public void onClose(@PathParam("nameAndId") String nameAndId, @PathParam("classId") Long classId) {
        if (webSocketMap.containsKey(classId)) {
            ConcurrentHashMap<String, Session> stringSessionConcurrentHashMap = webSocketMap.get(classId);
            if (stringSessionConcurrentHashMap.size() == 1) {
                webSocketMap.remove(classId);
            } else {
                if (stringSessionConcurrentHashMap.containsKey(nameAndId)) {
                    stringSessionConcurrentHashMap.remove(nameAndId);
                    webSocketMap.remove(classId);
                    webSocketMap.put(classId, stringSessionConcurrentHashMap);
                }
            }
        }
    }

    @OnMessage
    public void OnMessage(String message, @PathParam("nameAndId") String nameAndId, @PathParam("classId") Long classId) throws EncodeException, IOException {
        if (webSocketMap.containsKey(classId)) {
            ConcurrentHashMap<String, Session> stringSessionConcurrentHashMap = webSocketMap.get(classId);
            if (message.length()>1&&!message.equals("1")){
                Session session = stringSessionConcurrentHashMap.get(message);
                session.getBasicRemote().sendText("恭喜你！你已经被抽中起来回答问题了");
            }
        }
    }
}