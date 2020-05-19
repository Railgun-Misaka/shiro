package com.fly.shiro.service.impl;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fly.shiro.mapper.RoleMapper;
import com.fly.shiro.pojo.Role;
import com.fly.shiro.service.RoleService;

@Service
public class RoleServiceimpl implements RoleService {
	
	@Autowired RoleMapper roleMapper ;
	
	@Override
	//@RequiresPermissions("update")
	public List<Role> listrole() {
		return roleMapper.listrole();
	}

	@Override
	@RequiresPermissions("update")
	public Role getbyid(int rid) {
		return roleMapper.getbyid(rid);
	}

	@Override
	@RequiresPermissions("update")
	public Role getbyname(String rolename) {
		return roleMapper.getbyname(rolename);
	}

	@Override
	@RequiresRoles("administrator")
	public int deleterole(int rid) {
		return roleMapper.deleterole(rid);
	}

	@Override
	@RequiresRoles("administrator")
	public int addrole(Role role) {
		return roleMapper.addrole(role);
	}

	@Override
	@RequiresRoles("administrator")
	public int update(Role role) {
		return roleMapper.update(role);
	}

}
