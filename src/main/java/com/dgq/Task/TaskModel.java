package com.dgq.Task;

import java.io.IOException;

import org.springframework.amqp.core.Message;

import com.rabbitmq.client.Channel;


public class TaskModel implements Runnable{
	private Message msg;
	private Channel channel;
	public TaskModel(Message msg, Channel channel){
		this.msg = msg;
		this.channel = channel;
	}
	public void run() {
		try {
			System.out.println(Thread.currentThread().getName());
			System.out.println("-----------开始处理耗时任务！");
			
			Thread.sleep(1000);
			
			System.out.println(new String(msg.getBody()));
			
			System.out.println("--------------处理完成");
			
			channel.basicAck(msg.getMessageProperties().getDeliveryTag(), false);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
