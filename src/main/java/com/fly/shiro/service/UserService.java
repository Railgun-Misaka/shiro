package com.fly.shiro.service;

import java.util.List;

import com.fly.shiro.pojo.User;

public interface UserService {
	
	public List<User> listuser();
	
	public User getbyid(int uid) ;
	
	public User getbyname(String username);
	
	public int deleteuser(int uid) ;
	
	public int adduser(User user) ;
	
	public int update(User user) ;
}
