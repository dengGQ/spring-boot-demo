package com.dgq.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dgq.pojo.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	List<User> findByName(String name);
	
	Integer deleteById(long id);

	List<User> findByNameAndAge(String name, int age);
	
	User findByPhone(String phone);
}
