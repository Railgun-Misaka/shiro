package com.fly.shiro.pojo;

public class Permission {
	
	private int pid ;
	
	private String permission ;

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}
	
	@Override
	public int hashCode() {
		return pid * permission.hashCode();
	}
	
	@Override
	public boolean equals(Object object) {
		Permission p = (Permission) object ;
		return this.pid == p.getPid() && this.permission.equals(p.getPermission());
	}
	@Override
	public String toString() {
		return pid + permission ;
	}
}
