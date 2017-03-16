package com.dgq.rabbitMQ.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.QueueingConsumer.Delivery;

public class ReceiveLogsToFile {
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
        
        String queueName = channel.queueDeclare().getQueue();
        
        channel.queueBind(queueName, EXCHANGE_NAME, "");
        
        System.out.println(" Waiting for messages------------write file");
        
        QueueingConsumer consumer = new QueueingConsumer(channel);
        
        channel.basicConsume(queueName, true, consumer);
        
        while(true){
        	Delivery delivery = consumer.nextDelivery();
        	String message = new String(delivery.getBody());
        	System.out.println("write: "+message);
        	print2File(message);
        }
	}
	
	public static void print2File(String msg) throws Exception{
		String dir = ReceiveLogsToFile.class.getClassLoader().getResource("").getPath();
		String filename = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		File file = new File(dir, filename+".txt");
		System.out.println(dir);
		FileOutputStream fos = new FileOutputStream(file, true);
		fos.write((msg+"\r\n").getBytes());
		fos.flush();
		fos.close();
	}
}
