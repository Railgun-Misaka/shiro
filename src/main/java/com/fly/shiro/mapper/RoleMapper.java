package com.fly.shiro.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Many;

import com.fly.shiro.pojo.Role;

@Mapper
public interface RoleMapper {
	
	/**
	 * list
	 * @return
	 */
	@Select("SELECT * FROM role_")
	@Results({
		@Result(property = "rid", column = "rid"),
		@Result(property = "permissions", javaType = List.class, column = "rid",  many = @Many(select = "com.fly.shiro.mapper.PermissionMapper.listbyrole"))
	}) 
	public List<Role> listrole() ;
	
	/**
	 * getbyid
	 * @param id
	 * @return
	 */
	@Select("SELECT * FROM role_ WHERE rid = #{rid}")
	@Results({
		@Result(property = "rid", column = "rid"),
		@Result(property = "permissions", javaType = List.class, column = "rid",  many = @Many(select = "com.fly.shiro.mapper.PermissionMapper.listbyrole"))
	}) 
	public Role getbyid(int rid) ;
	
	/**
	 * getbyname
	 * @param rolename
	 * @return
	 */
	@Select("SELECT * FROM role_ WHERE rolename = #{rolename}")
	@Results({
		@Result(property = "rid", column = "rid"),
		@Result(property = "permissions", javaType = List.class, column = "rid",  many = @Many(select = "com.fly.shiro.mapper.PermissionMapper.listbyrole"))
	}) 
	public Role getbyname(String rolename);
	
	/**
	 * listbyuser
	 * @param uid
	 * @return
	 */
	@Select("SELECT * FROM role_ r LEFT JOIN user_role ur ON r.rid = ur.rid WHERE ur.rid = #{rid}")
	@Results({
		@Result(property = "rid", column = "rid"),
		@Result(property = "permissions", javaType = List.class, column = "rid",  many = @Many(select = "com.fly.shiro.mapper.PermissionMapper.listbyrole"))
	}) 
	public List<Role> listbyuser(int uid) ;
	/**
	 * 通过用户名获取角色名
	 * @param uid
	 * @return
	 */
	@Select("SELECT rolename FROM role_ r LEFT JOIN user_role ur ON r.rid = ur.rid WHERE ur.rid = #{rid}")
	public Set<String> rolenames(int uid) ;
	
	@Delete("DELETE FROM role_ WHERE rid = #{rid}")
	public int deleterole(int rid) ;
	
	@Insert("INSERT INTO role_ VALUES (null, #{rolename})")
	public int addrole(Role role) ;
	
	@Update("UPDATE role_ SET rolename = #{rolename} WHERE rid = #{rid}")
	public int update(Role role) ;
}
