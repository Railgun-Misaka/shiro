package com.fly.shiro.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.fly.shiro.pojo.Permission;

@Mapper
public interface PermissionMapper {
	
	@Select("SELECT * FROM permission_")
	public List<Permission> listpermission() ;
	
	@Select("SELECT * FROM permission_ WHERE pid = #{id}")
	public Permission getbyid(int id) ;
	
	@Select("SELECT * FROM permission_ WHERE permission = #{permission}")
	public Permission getbyname(String permission);
	
	@Delete("DELETE FROM permission_ WHERE pid = #{id}")
	public int deletepermission(int id) ;
	
	@Insert("INSERT INTO permission_ VALUES (null, #{permission})")
	public int addpermission(Permission permission) ;
	
	@Update("UPDATE permission_ SET permission = #{permission} WHERE pid = #{id}")
	public int update(Permission permission) ;
	
	@Select("SELECT * FROM permission_ p LEFT JOIN role_permission rp ON rp.pid = p.pid WHERE rp.rid = #{rid}")
	public List<Permission> listbyrole(int rid) ;
	
	/**
	 * 通过用户名获取权限名称
	 * @param uid
	 * @return
	 */
	@Select("SELECT permission FROM permission_ p "
			+ "LEFT JOIN role_permission rp ON rp.pid = p.pid "
			+ "LEFT JOIN user_role ur ON ur.rid = rp.rid "
			+ "WHERE ur.uid = #{uid}")
	public Set<String> permissions(int uid) ;
}
