package com.dgq.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@ComponentScan(basePackages={"com.dgq"}) // 扫描该包路径下的所有spring组件
@EnableJpaRepositories("com.dgq.dao") // JPA扫描该包路径下的Repositorie
@EntityScan("com.dgq.pojo") // 扫描实体类
@SpringBootApplication
@Controller
public class Application {
	public static void main(String[] args) {
		
		SpringApplication.run(Application.class, args);
	}
	
	@RequestMapping("/")
	public String index(){
		return "index";
	}
	
	/**
	 * 跳转到首页面
	 * @param name
	 * @param model
	 * @return
	 */
	@RequestMapping("/hello/{name}")
	public String index(@PathVariable String name, Model model){
		model.addAttribute("name", name);
		return "index";
	}
}
