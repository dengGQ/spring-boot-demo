package com.dgq.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.dgq.Task.SystemWebSocketHandler;
import com.dgq.Task.WebSocketHandshakeInterceptor;

public class WebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer{

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(systemWebSocketHandler(), "/").addInterceptors(new WebSocketHandshakeInterceptor());
		
		registry.addHandler(systemWebSocketHandler(), "/").addInterceptors(new WebSocketHandshakeInterceptor()).withSockJS();
		
	}
	
	
	@Bean
	public SystemWebSocketHandler systemWebSocketHandler(){
		return new SystemWebSocketHandler();
	}
}
