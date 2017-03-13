package com.dgq.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages={"com.dgq"}) // 扫描该包路径下的所有spring组件
@EnableJpaRepositories("com.dgq.dao") // JPA扫描该包路径下的Repositorie
@EntityScan("com.dgq.pojo") // 扫描实体类
@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		
		SpringApplication.run(Application.class, args);
	}
}
