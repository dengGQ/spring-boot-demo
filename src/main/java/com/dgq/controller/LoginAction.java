package com.dgq.controller;

import javax.annotation.Resource;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginAction {
	
	@Resource
	private UserDetailsService userService;
	
	@RequestMapping("/")
	public String index(){
		return "index";
	}
	
	@RequestMapping(value = "/loginPage", method = RequestMethod.GET)
	public String login(){
		System.out.println("***************************跳转到登录页面*******************************");
		return "login";
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
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginByUserNameAndPassword(String userName, String password, Model model){
		System.out.println("#####################################" + userName + "#####" + password + "#####" + model.toString());
		
		userService.loadUserByUsername(userName);
		return "index";
	}
}
