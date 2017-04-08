package com.dgq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;

@SuppressWarnings("deprecation")
@ComponentScan(basePackages={"com.dgq"}) // 扫描该包路径下的所有spring组件
@EnableJpaRepositories("com.dgq.dao") // JPA扫描该包路径下的Repositorie
@EntityScan("com.dgq.pojo")
@SpringBootApplication
@Controller
public class Application {
	public static void main(String[] args) {
		
		SpringApplication.run(Application.class, args);
	}
}
