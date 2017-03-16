package com.dgq.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
		return "helloSpringBoot-----";
	}
	
	/**
	 * 根据姓名查询用户
	 * @param name
	 * @return
	 */
	@RequestMapping("/findByName")
	@ResponseBody
	public List<User> findByName(String name){
		
		List<User> user = userRepository.findByName(name);
		
		return user;
	}
	
	@RequestMapping("/findAll")
	@ResponseBody
	public List<User> findAll(){
		List<User> list = userRepository.findAll();
		
		return list;
	}
	
	@RequestMapping("/deleteById")
	@ResponseBody
	public String deleteById(long id){
		
		userRepository.deleteById(id);
		
		return "ok";
	}
	
	/**
	 * 插入一条数据
	 * @param name
	 * @param age
	 * @return
	 */
	@RequestMapping("/insertUser")
	@ResponseBody
	public User insertUser(String name, int age, String phone, String address){
		User user = userRepository.save(new User(name, age, phone, address));
		return user;
	}
	
	@RequestMapping("/findByNameAndAge")
	@ResponseBody
	public List<User> findByNameAndAge(String name, int age){
		
		List<User> list = userRepository.findByNameAndAge(name, age);
	
		return list;
	}
	
	@RequestMapping("/findByPhone")
	@ResponseBody
	public User findByPhone(String phone){
		User user = userRepository.findByPhone(phone);
		return user;
	}
}
