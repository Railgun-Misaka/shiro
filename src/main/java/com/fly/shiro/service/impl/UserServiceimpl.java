package com.fly.shiro.service.impl;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fly.shiro.mapper.UserMapper;
import com.fly.shiro.pojo.User;
import com.fly.shiro.service.UserService;

@Service
public class UserServiceimpl implements UserService {
	
	@Autowired private UserMapper userMapper ;
	
	@Override
	@RequiresAuthentication   //表示当前Subject已经通过login 进行了身份验证；即Subject. isAuthenticated()返回true。
	public List<User> listuser() {
		return userMapper.listuser();
	}

	@Override
	@RequiresAuthentication
	public User getbyid(int uid) {
		return userMapper.getbyid(uid);
	}

	@Override
	@RequiresAuthentication
	public User getbyname(String username) {
		return userMapper.getbyname(username);
	}

	@Override
	@RequiresPermissions("delete")   //需要delete权限才可调用         此类注释只可修饰Controller或Service中的方法,不可修饰Mapper
	public int deleteuser(int uid) {
		return userMapper.deleteuser(uid);
	}

	@Override
	public int adduser(User user) {
		return userMapper.adduser(user);
	}

	@Override
	@RequiresPermissions("update")
	public int update(User user) {
		return userMapper.update(user);
	}

}
