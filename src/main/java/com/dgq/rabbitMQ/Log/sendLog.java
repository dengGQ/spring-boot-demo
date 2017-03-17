package com.dgq.rabbitMQ.Log;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class sendLog {
	// 转发器  
    private final static String EXCHANGE_NAME = "ex_log";
    
	public static void main(String[] args) throws IOException {
		
		ConnectionFactory factory = new ConnectionFactory();
	
		factory.setHost("127.0.0.1");
		factory.setPort(5672);
		factory.setUsername("dgq");  
        factory.setPassword("123456");
        
      //  ExecutorService executorService = Executors.newFixedThreadPool(10);
        
        
        Connection connection = factory.newConnection();
        
        Channel channel = connection.createChannel();
        
        //生明一个fanout 类型的 exchange
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        
        String message = new SimpleDateFormat("yyyy-MM-dd").format(new Date())+ " : log something";
        
        //指定消息发送到的转发器
        channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
        
        System.out.println("sen message: " + message);
        //关闭连接
        channel.close();
        connection.close();
	}
}
