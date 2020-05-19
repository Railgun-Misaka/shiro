package com.fly.shiro.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.fly.shiro.pojo.User;

@Mapper
public interface UserMapper {
	
	/**
	 * list
	 * @return
	 */
	@Select("SELECT * FROM user_")
	@Results({
		@Result(property = "uid", column = "uid"),
		@Result(property = "roles", javaType = List.class, column = "uid",  many = @Many(select = "com.fly.shiro.mapper.RoleMapper.listbyuser"))
	}) 
	public List<User> listuser() ;
	
	/**
	 * getbyid
	 * @param uid
	 * @return
	 */
	@Select("SELECT * FROM user_ WHERE uid = #{uid}")
	@Results({
		@Result(property = "uid", column = "uid"),
		@Result(property = "roles", javaType = List.class, column = "uid",  many = @Many(select = "com.fly.shiro.mapper.RoleMapper.listbyuser"))
	}) 
	public User getbyid(int uid) ;
	
	/**
	 * getbyname
	 * @param username
	 * @return
	 */
	@Select("SELECT * FROM user_ WHERE username = #{username}")
	@Results({
		@Result(property = "uid", column = "uid"),
		@Result(property = "roles", javaType = List.class, column = "uid",  many = @Many(select = "com.fly.shiro.mapper.RoleMapper.listbyuser"))
	}) 
	public User getbyname(String username);
	
	
	@Delete("DELETE FROM user_ WHERE uid = #{uid}")
	public int deleteuser(int uid) ;
	
	@Insert("INSERT INTO user_ VALUES (null, #{username}, #{password}, #{salt})")
	public int adduser(User user) ;
	
	@Update("UPDATE user_ SET password = #{password}, salt = #{salt} WHERE uid = #{uid}")
	public int update(User user) ;
}
