package com.dgq.Task;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

public class SystemWebSocketHandler implements WebSocketHandler{
	
	private static final Logger logger;
	private static final ArrayList<WebSocketSession> users;
	
	static{
		logger = LoggerFactory.getLogger(SystemWebSocketHandler.class);
		users = new ArrayList<WebSocketSession>();
	}
	
	/**
	 * 连接建立后
	 */
	@Override
	public void afterConnectionEstablished(WebSocketSession session)
			throws Exception {
		logger.debug("connect to the websocket success....................");
		users.add(session);
	}
	
	/**
	 * 连接关闭后
	 */
	@Override
	public void afterConnectionClosed(WebSocketSession session,
			CloseStatus closeStatus) throws Exception {
		logger.debug("websocket chat connection closed............");
		users.remove(session);
	}
	
	/**
	 * 抛出异常时处理
	 */
	@Override
	public void handleTransportError(WebSocketSession session,
			Throwable exception) throws Exception {
		if(session.isOpen()){
			session.close();
		}
		
		logger.debug("websocket chat connectin closed...........");
		users.remove(session);
	}
	
	/**
	 * 接收消息
	 */
	@Override
	public void handleMessage(WebSocketSession session,
			WebSocketMessage<?> message) throws Exception {
		logger.debug("");
		
	}

	@Override
	public boolean supportsPartialMessages() {
		// TODO Auto-generated method stub
		return false;
	}

}
