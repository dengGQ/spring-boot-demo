package com.dgq.listener;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.dgq.Task.TaskModel;
import com.rabbitmq.client.Channel;

@Component
public class QueueListener {
	
//	private ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(10));
	private ExecutorService executor = Executors.newFixedThreadPool(10);
	
	
	@RabbitListener(queues = {"queue-one1"})
	public void listenerQueue1(Message message, Channel channel) throws IOException{
		System.out.println("------listenerQueue1----------------");
		channel.basicQos(1);
		executor.submit(new TaskModel(message, channel));
		
	}
	
	@RabbitListener(queues = {"queue-one1"})
	public void listener2Queue1(Message message, Channel channel) throws IOException{
		System.out.println("------listener--2--Queue1----------------");
		channel.basicQos(1);
		executor.submit(new TaskModel(message, channel));
	}
	
	@RabbitListener(queues = {"queue-one2", "queue-one3"})
	public void listenerQueue2And3And4(Message message, Channel channel) throws IOException{
		System.out.println("------listenerQueue2And3And4----------------");
		channel.basicQos(1);
		executor.submit(new TaskModel(message, channel));
	}
}
