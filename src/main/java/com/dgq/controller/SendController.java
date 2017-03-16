package com.dgq.controller;

import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dgq.rabbitMQ.RabbitMQConfig;

/**
 * 测试RabbitMQ发送消息的Controller
 * @author dgq
 *
 */
@RestController
public class SendController implements RabbitTemplate.ConfirmCallback{
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	/**
	 * empty constructor
	 */
	public SendController(){
	}
	
	/*public SendController(RabbitTemplate rabbitTemplate){
		this.rabbitTemplate = rabbitTemplate;
		this.rabbitTemplate.setConfirmCallback(this);
	}*/
	
	/**
	 * 向消息队列1中发送消息
	 * @param msg
	 * @return
	 */
	@RequestMapping("/send1")
	public String send1(String msg){
		//设置消息消息回调
		rabbitTemplate.setConfirmCallback(this);
		
		String uuid = UUID.randomUUID().toString();
		CorrelationData correlationId = new CorrelationData(uuid);
		
		rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTINGKEY1, msg, correlationId);
		
		return null;
	
	}
	/**
	 * 向消息队列2中发送消息
	 * @param msg
	 * @return
	 */
	@RequestMapping("/send2")
	public String send2(String msg){
		//设置消息消息回调
		rabbitTemplate.setConfirmCallback(this);
		
		String uuid = UUID.randomUUID().toString();
		
		CorrelationData correlationId = new CorrelationData(uuid);
	
		rabbitTemplate.convertAndSend("logs", "", msg, correlationId);
		
		
		return null;
	}
	
	/**
	 * 消息的回调，实现至RabbitTemplate.ConfirmCallback接口
	 * 注意消息的回调只能代表消息成功发送到RabbitMQ服务器，不能代表消息成功处理和接收
	 */
	public void confirm(CorrelationData correlationData, boolean ack,
			String cause) {
		System.out.println("回调ID: " + correlationData);
			
		if(ack){
			System.out.println("消费消息成功");
		}else{
			System.out.println("消费消息失败: " + cause +"\n重新发送");
		}		
	}
	
}
