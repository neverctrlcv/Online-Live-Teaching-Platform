package com.runhuo.live.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @product name: IntelliJ IDEA
 * @Author never debug
 * @Date: 2023/5/8 16:13
 */
@SpringBootConfiguration
@EnableWebSocket
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return  new ServerEndpointExporter();
    }
}
