package com.dgq.config;

import javax.annotation.Resource;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
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
	public static final String EXCHANGE_NAME   = "spring-boot-exchange";
	
	/**队列key1**/
	public static final String ROUTINGKEY1 = "queue-one-key1";

	/**队列key2**/
	public static final String ROUTINGKEY2 = "queue-one-key2";
	
	@Resource
	private Environment env;
	
	/**
	 * rabbitMQ connectionFactory 配置, 注册为bean
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
	 * rabbitListenerContainerFactory 配置, 不注册此bean，spring会使用默认的listenerContainer
	 * @param connectionFactory
	 * @return
	 */
	@Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory listenerFac = new SimpleRabbitListenerContainerFactory();
        listenerFac.setConnectionFactory(connectionFactory);
       // listenerFac.setMessageConverter(new Jackson2JsonMessageConverter());
        listenerFac.setAcknowledgeMode(AcknowledgeMode.MANUAL);
       
        return listenerFac;
    }
}
