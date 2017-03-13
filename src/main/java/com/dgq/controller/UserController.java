package com.dgq.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dgq.dao.UserRepository;
import com.dgq.pojo.User;

@Controller
@RequestMapping("/user")
public class UserController<E> {
	
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping("/helloSpringBoot")
	@ResponseBody
	public String helloSpringBoot(){
		return "helloSpringBoot";
	}
	
	@RequestMapping("/hello/{name}")
	public String index(@PathVariable String name, Model model){
		model.addAttribute("name", name);
		return "index";
	}
	
	@RequestMapping("/findByName")
	@ResponseBody
	public User findByName(String name){
		
		User user = userRepository.findByName(name);
		
		return user;
	}
	
	@RequestMapping("/findAll")
	@ResponseBody
	public List<User> findAll(){
		
		Iterable<User> iterable = userRepository.findAll();
		List<User> list = new ArrayList<User>();
		
		for (User user : iterable) {
			list.add(user);
		}
		return list;
	}
	
	@RequestMapping("/deleteByName")
	@ResponseBody
	public String deleteByName(long id){
		
		Integer str = userRepository.deleteById(id);
		
		return "ok";
	}
	
	@RequestMapping("/insertUser")
	@ResponseBody
	public User insertUser(String name, int age){
		User user = userRepository.save(new User(name, age));
		return user;
	}
}
