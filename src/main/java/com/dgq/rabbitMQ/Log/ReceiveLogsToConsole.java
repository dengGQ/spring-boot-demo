package com.dgq.rabbitMQ.Log;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class ReceiveLogsToConsole {
	 private final static String EXCHANGE_NAME = "ex_log";    
	    public static void main(String[] args) throws Exception {  
	    	ConnectionFactory factory = new ConnectionFactory();
			
			factory.setHost("127.0.0.1");
			factory.setPort(5672);
			factory.setUsername("dgq");  
	        factory.setPassword("123456");
	        
	        Connection connection = factory.newConnection();
	        
	        Channel channel = connection.createChannel();
			
	        channel.exchangeDeclare(EXCHANGE_NAME, "fanout"); 
	    
	        // 创建一个非持久的、唯一的且自动删除的队列且队列名称由服务器随机产生一般情况这个名称与amq.gen-JzTY20BRgKO-HjmUJj0wLg 类似。  
	        String queueName = channel.queueDeclare().getQueue();    
	        // 为转发器指定队列，设置binding    
	        channel.queueBind(queueName, EXCHANGE_NAME, "");    
	    
	        System.out.println(" Waiting for messages------------print console");    
	    
	        QueueingConsumer consumer = new QueueingConsumer(channel);    
	        // 指定接收者，第二个参数为自动应答，无需手动应答    
	        channel.basicConsume(queueName, true, consumer);    
	    
	        while (true)    
	        {    
	            QueueingConsumer.Delivery delivery = consumer.nextDelivery();    
	            String message = new String(delivery.getBody());    
	            System.out.println("Received: " + message + "");    
	    
	        }    
	    }  
}
