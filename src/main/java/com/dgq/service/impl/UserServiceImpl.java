package com.dgq.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dgq.dao.UserRepository;
import com.dgq.pojo.User;

@Service
public class UserServiceImpl implements UserDetailsService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		List<User> user = userRepository.findByName(username);
		if (user == null) {
			throw new UsernameNotFoundException("用户名不存在");
		}
			
		System.out.println("s:"+username);
        System.out.println("username:"+user.get(0).getUsername()+";password:"+user.get(0).getPassword());
		return user.get(0);
	}

}
