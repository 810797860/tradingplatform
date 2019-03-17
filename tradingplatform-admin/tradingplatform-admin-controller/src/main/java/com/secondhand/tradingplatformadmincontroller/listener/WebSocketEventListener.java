package com.secondhand.tradingplatformadmincontroller.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

/**
 * webSocket事件监听器
 *
 * @author 81079
 */
@Component
public class WebSocketEventListener {

    /**
     * 连接webSocket时调用该方法
     *
     * @param event
     */
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {

    }

    /**
     * 断开连接时调用该方法
     *
     * @param event
     */
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {

    }
}
