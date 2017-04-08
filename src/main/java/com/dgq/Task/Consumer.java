package com.dgq.Task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dgq.config.RabbitMQConfig;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

/**
 * 消费者
 * @author dgq
 *
 */
@Configuration
public class Consumer {
	
	private ExecutorService executor = Executors.newFixedThreadPool(10);
	
	/**
	 * 配置消费交换机
	 * 针对消费者配置  
        FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念  
        HeadersExchange ：通过添加属性key-value匹配  
        DirectExchange:按照routingkey分发到指定队列  
        TopicExchange:多关键字匹配 
	 * @return
	 */
	@Bean
	public DirectExchange defaultExchange(){
		return new DirectExchange(RabbitMQConfig.EXCHANGE_NAME, true, false);
	}
	
	@Bean
	public FanoutExchange getFanoutExchange(){
		return new FanoutExchange("logs", true, false);
	}
	
	/**
	 * 配置消息队列 1
	 * 针对消费者
	 * @return
	 */
	@Bean
	public Queue queue1(){
		return new Queue("queue-one1", true); //true 表示队列持久
	}
	/**
	 * 将消息队列1与交换机绑定
	 * 针对消费者配置
	 * @return
	 */
	@Bean
	public Binding binding1(){
		return BindingBuilder.bind(queue1()).to(defaultExchange()).with(RabbitMQConfig.ROUTINGKEY1);
	}
	
	/**
	 * 配置消息队列2
	 * 针对消费者
	 * @return
	 */
	@Bean
	public Queue queue2(){
		return new Queue("queue-one2", true);
	}
	@Bean
	public Queue queue3(){
		return new Queue("queue-one3", true);
	}
	@Bean
	public Queue queue4(){
		return new Queue("queue-one4", true);
	}
	/**
	 * 将消息队列2与交换机绑定
	 * 针对消费者配置
	 * @return
	 */
	@Bean
	public Binding binding2(){
		return BindingBuilder.bind(queue2()).to(getFanoutExchange());
	}
	@Bean
	public Binding binding3(){
		return BindingBuilder.bind(queue3()).to(getFanoutExchange());
	}
	@Bean
	public Binding binding4(){
		return BindingBuilder.bind(queue4()).to(getFanoutExchange());
	}
	
	
	/**
	 * 接收消息的监听，这个监听会接收消息队列1的消息
	 * 针对消费者配置
	 * @return
	 */
	/*@Bean
	public SimpleMessageListenerContainer messageContainer1(ConnectionFactory connectionFactory){
		SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer(connectionFactory);
		//设置监听消息队列
		listenerContainer.setQueues(queue1());
		//设置是否暴露监听管道
		listenerContainer.setExposeListenerChannel(true);
		//设置对大并发消费者数
		listenerContainer.setMaxConcurrentConsumers(1); 
		listenerContainer.setConcurrentConsumers(1);
		//设置确认模式为手工确认
		listenerContainer.setAcknowledgeMode(AcknowledgeMode.MANUAL); 
		listenerContainer.setMessageListener(new ChannelAwareMessageListener() {
			
			public void onMessage(Message message, Channel channel) throws Exception {
				
				byte[] body = message.getBody();
				
				channel.basicQos(1);
				
				System.out.println("队列1收到消息："+new String(body));
				 //确认消息成功消费
				channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
			}
		});
		
		return listenerContainer;
	}*/
	
	/**
	 * 接收消息的监听，监听queue2、queue3、queue4三个队列
	 * 针对消费者配置
	 * @return
	 */
	/*@Bean
	public SimpleMessageListenerContainer messageContainer2(ConnectionFactory connectionFactory){
		//创建消息监听容器
		SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer(connectionFactory);
		//设置监听容器监听的队列
		listenerContainer.setQueues(queue2(), queue3(), queue4());
		
		listenerContainer.setExposeListenerChannel(true);
		listenerContainer.setMaxConcurrentConsumers(1);
		//设置并发消费者数
		listenerContainer.setConcurrentConsumers(1);
		// 设置手动回执，默认自动，自动的话：如果消息发出后不管消费者是否消费了rabbitMQ都会删除该条消息，手动：rabbitMQ会等待回执之后才会删除消息
		listenerContainer.setAcknowledgeMode(AcknowledgeMode.MANUAL);
		
		listenerContainer.setMessageListener(new ChannelAwareMessageListener() {
			public void onMessage(Message message, Channel channel) throws Exception {
				channel.basicQos(1);

				executor.submit(new TaskModel(message, channel));
			}
		});
		
		return listenerContainer;
	}*/
}
