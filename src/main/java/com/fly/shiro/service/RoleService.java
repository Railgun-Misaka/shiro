package com.fly.shiro.service;

import java.util.List;

import com.fly.shiro.pojo.Role;

public interface RoleService {
	
	public List<Role> listrole();
	
	public Role getbyid(int rid) ;
	
	public Role getbyname(String rolename);
	
	public int deleterole(int rid) ;
	
	public int addrole(Role role) ;
	
	public int update(Role role) ;
}
