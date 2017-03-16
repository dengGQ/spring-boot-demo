package com.dgq.rabbitMQ;

import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.rabbitmq.client.Channel;

/**
 * RabbitMQ的配置类
 * @author dgq
 *
	1、@PropertySource("classpath:rabbitmq.properties") : 1、@Value("${rabbitmq.address}")；2、env.getProperty(key)
	2、@ConfigurationProperties(locations = "classpath:rabbitmq.properties", ignoreUnknownFields = false, prefix = "rabbitmq")
 */
@Configuration
@PropertySource("classpath:rabbitmq.properties")
public class RabbitMQConfig {
	/** 消息交换机的名字**/
	public static final String EXCHANGE   = "spring-boot-exchange";
	
	/**队列key1**/
	public static final String ROUTINGKEY1 = "queue-one-key1";

	/**队列key2**/
	public static final String ROUTINGKEY2 = "queue-one-key2";
	
	@Resource
	private Environment env;
	
	/**
	 * 配置连接bean
	 * @return
	 */
	@Bean
	public ConnectionFactory ConnectionFactory(){
		CachingConnectionFactory connFac = new CachingConnectionFactory();
		
		connFac.setAddresses(env.getProperty("rabbitmq.address"));
		connFac.setUsername(env.getProperty("rabbitmq.username"));
		connFac.setPassword(env.getProperty("rabbitmq.password"));
		connFac.setVirtualHost(env.getProperty("rabbitmq.virtualhost"));
		connFac.setPublisherConfirms(true);
		
		return connFac;
	}
	
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
		return new DirectExchange(EXCHANGE, true, false);
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
	@Bean
	public SimpleMessageListenerContainer messageContainer1(){
		SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer(ConnectionFactory());
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
				
				System.out.println(message.getMessageProperties().getDeliveryTag());
				
				Thread.sleep(2000);
				System.out.println("队列1收到消息："+new String(body));
				 //确认消息成功消费
				channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
			}
		});
		
		return listenerContainer;
	}
	
	/**
	 * 接收消息的监听，这个监听会接收消息队列2的消息
	 * 针对消费者配置
	 * @return
	 */
	@Bean
	public SimpleMessageListenerContainer messageContainer2(){
		SimpleMessageListenerContainer listenerContainer = new SimpleMessageListenerContainer(ConnectionFactory());
		listenerContainer.setQueues(queue2(), queue3(), queue4());
		listenerContainer.setExposeListenerChannel(true);
		listenerContainer.setMaxConcurrentConsumers(1);
		listenerContainer.setConcurrentConsumers(1);
		listenerContainer.setAcknowledgeMode(AcknowledgeMode.MANUAL);
		listenerContainer.setMessageListener(new ChannelAwareMessageListener() {
			public void onMessage(Message message, Channel channel) throws Exception {
				
				task(message, channel);	
			
			}
		});
		
		return listenerContainer;
	}
	
	public String task(Message message, Channel channel) throws IOException{
		byte[] body = message.getBody();
		
		System.out.println(message.getMessageProperties().getDeliveryTag());
		
		System.out.println("队列2收到消息："+new String(body));
		
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		
		return null;
	}
	public static String getExchange() {
		return EXCHANGE;
	}

	public static String getRoutingkey1() {
		return ROUTINGKEY1;
	}

	public static String getRoutingkey2() {
		return ROUTINGKEY2;
	}
}
