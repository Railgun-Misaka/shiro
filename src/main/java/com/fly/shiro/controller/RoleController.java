package com.fly.shiro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fly.shiro.pojo.Role;
import com.fly.shiro.service.RoleService;

@RestController
public class RoleController {
	
	@Autowired RoleService roleService ;
	
	@GetMapping("/roles")
	public List<Role> list(){
		
		List<Role> roles = null;
		try {
			roles = roleService.listrole() ;
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return roles ;
	}
	
	@GetMapping("/roles/{rid}")
	public Role getone(@PathVariable("rid") int rid){
		Role role = null ;
		role = roleService.getbyid(rid);
		return role ;
	}
	
	@PostMapping("/roles")
	public String addrole(Role role) {
		try {
			if(roleService.addrole(role) != 1) 
				throw new Exception();
		} catch (Exception e) {
			//e.printStackTrace();
			return "failed";
		}
		return "success";
	}
	
	@DeleteMapping("/roles/{rid}")
	public String deleterole(@PathVariable("rid") int rid) {
		if(roleService.deleterole(rid) != 1)
			try {
				throw new Exception() ;
			} catch (Exception e) {
				//e.printStackTrace();
				return "failed" ;
			}
		return "success";
	}
	
	@PutMapping("/roles/{rid}")
	public String updaterole(@PathVariable("rid") int rid, Role role) {
		if(role.getRid() == 0)
			role.setRid(rid);
		try {
			if(roleService.update(role) != 1)
				throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
			return "failed";
		}
		return "success";
	}
	
	@PostMapping("/sss/{uid}")
	public String user_role(@RequestParam String[] rids) {
		return "";
	}
	
}
