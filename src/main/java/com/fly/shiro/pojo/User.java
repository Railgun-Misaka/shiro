package com.fly.shiro.pojo;

import java.util.List;

public class User {
	
	private int uid ;
	
	private String username ;
	
	private String password ;
	
	private String salt ;
	
	//非数据库字段
	
	private List<Role> roles ;

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return uid + username ;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	@Override
	public int hashCode() {
		return uid * username.hashCode();
	}
	
	@Override
	public boolean equals(Object object) {
		User user = (User) object ;
		return this.uid == user.getUid() && this.username.equals(user.getUsername());
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}
}
