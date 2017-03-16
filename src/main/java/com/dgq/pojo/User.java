package com.dgq.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
	
	@Id  
    @GeneratedValue(strategy = GenerationType.AUTO)  
    private Long id;  
    private String name;  
    private int age;
    private String phone;
    private String address;
    
    public User(){}
    
    public User(String name, int age, String phone, String address){
    	this.name = name;
    	this.age = age;
    	this.phone = phone;
    	this.address = address;
    }
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}  
	
}
