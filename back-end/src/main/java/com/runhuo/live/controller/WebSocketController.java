package com.runhuo.live.controller;

import com.alibaba.fastjson2.JSONObject;
import com.runhuo.live.dao.StatusTMapper;
import com.runhuo.live.entity.StatusT;
import com.runhuo.live.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/21 23:14
 */
@Controller
@ServerEndpoint(value = "/websocket/{nameAndId}/{classId}/{classTId}")
@SuppressWarnings("all")
@Slf4j
public class WebSocketController {

    private static RedisService redisService;
    private static StatusTMapper statusTMapper;

    private static ConcurrentHashMap<Long, ConcurrentHashMap<String, Session>> webSocketMap = new ConcurrentHashMap<>();

    private static ConcurrentHashMap<String, Session> classMap = new ConcurrentHashMap<>();
    private final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

    @Autowired
    public void setRedisService(RedisService redisService){
        WebSocketController.redisService=redisService;
    }
    @Autowired
    public void setStatusTMapper(StatusTMapper statusTMapper){
        WebSocketController.statusTMapper=statusTMapper;
    }

    public Object[] getMember(Long classId){
        ConcurrentHashMap<String, Session> stringSessionConcurrentHashMap = webSocketMap.get(classId);
        return stringSessionConcurrentHashMap.keySet().toArray();
    }
    @OnOpen
    public void onOpen(Session session, @PathParam("nameAndId") String nameAndId,
                       @PathParam("classId") Long classId,
                       @PathParam("classTId") Long classTId) {
        ConcurrentHashMap<String, Session> stringSessionConcurrentHashMap;
        if (webSocketMap.containsKey(classId)) {
            stringSessionConcurrentHashMap = webSocketMap.get(classId);
            stringSessionConcurrentHashMap.put(nameAndId, session);
            webSocketMap.remove(classId);
        } else {
            stringSessionConcurrentHashMap = new ConcurrentHashMap<>();
            stringSessionConcurrentHashMap.put(nameAndId, session);
            redisService.set(classId+"classroom",new Date().getTime());
        }
        webSocketMap.put(classId, stringSessionConcurrentHashMap);
        StatusT statusT=new StatusT();
        statusT.setId(classTId);
        statusT.setLogTime(new Timestamp(new Date().getTime()));
        statusT.setLogInfo(nameAndId+"进入了教室");

        statusTMapper.insertStatusLog(statusT);
    }
    @OnClose
    public void onClose(@PathParam("nameAndId") String nameAndId,
                        @PathParam("classId") Long classId,
                        @PathParam("classTId") Long classTId) {
        if (webSocketMap.containsKey(classId)) {
            ConcurrentHashMap<String, Session> stringSessionConcurrentHashMap = webSocketMap.get(classId);
            if (stringSessionConcurrentHashMap.size() == 1) {
                webSocketMap.remove(classId);
                redisService.del(classId+"classroom");
            } else {
                if (stringSessionConcurrentHashMap.containsKey(nameAndId)) {
                    stringSessionConcurrentHashMap.remove(nameAndId);
                    webSocketMap.remove(classId);
                    webSocketMap.put(classId, stringSessionConcurrentHashMap);
                }
            }
        }
        StatusT statusT=new StatusT();
        statusT.setId(classTId);
        statusT.setLogTime(new Timestamp(new Date().getTime()));
        statusT.setLogInfo(nameAndId+"退出了教室");

        statusTMapper.insertStatusLog(statusT);
    }

    @OnMessage
    public void OnMessage(String message, @PathParam("nameAndId") String nameAndId, @PathParam("classId") Long classId) throws EncodeException, IOException {
        if (webSocketMap.containsKey(classId)) {
            ConcurrentHashMap<String, Session> stringSessionConcurrentHashMap = webSocketMap.get(classId);
            HashMap<String, String> messageData = new HashMap<>();
            messageData.put("userId", nameAndId);
            messageData.put("message", message);
            messageData.put("time", sdf.format(new Date()));
            for (Session session : stringSessionConcurrentHashMap.values()) {
                session.getBasicRemote().sendText(JSONObject.toJSONString(messageData));
            }
        }
        log.info("用户{}成功发送消息", nameAndId);
    }

}
