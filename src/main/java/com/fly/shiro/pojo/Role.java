package com.fly.shiro.pojo;

import java.util.List;

public class Role {
	
	private int rid ;
	
	private String rolename ;
	
	//非数据库字段
	private List<Permission> permissions;

	public int getRid() {
		return rid;
	}

	public void setRid(int rid) {
		this.rid = rid;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	
	@Override
	public int hashCode() {
		return rid * rolename.hashCode();
	}
	
	@Override
	public boolean equals(Object object) {
		Role r = (Role) object ;
		return this.rid == r.getRid() && this.rolename.equals(r.getRolename());
	}
	@Override
	public String toString() {
		return rid + rolename ;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}
	
}
