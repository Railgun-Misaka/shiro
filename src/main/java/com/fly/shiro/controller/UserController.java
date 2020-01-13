package com.fly.shiro.controller;

import java.util.List;
import java.util.regex.Pattern;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fly.shiro.pojo.User;
import com.fly.shiro.service.UserService;
import com.fly.shiro.util.EncryptionUtil;

@RestController
public class UserController {
	
	@Autowired private UserService userService;
	
	@Autowired private Subject subject ;
	
	@GetMapping("/users")
	public List<User> list(){
		List<User> users;
		try {
			users = userService.listuser();
		} catch (AuthorizationException e) {
			//e.printStackTrace();
			users = null ;
		}
		return users;
	}
	
	@GetMapping("/users/{id}")
	public User getone(@PathVariable("id") int id){
		return userService.getbyid(id);
	}
	
	@PostMapping("/users")
	public String adduser(User user) {
		try {
			user = new EncryptionUtil().encryption(user); //加密处理
			userService.adduser(user);
		} catch (Exception e) {
			//e.printStackTrace();
			return "failed";
		}
		return "success";
	}
	
	@DeleteMapping("/users/{id}")
	public String deleteuser(@PathVariable("id") int id) throws AuthorizationException {
		try {
			if(userService.deleteuser(id) == 0)
				return "failed";
		} catch (AuthorizationException e) {
			//e.printStackTrace();
			return "noAuth";
		}
		return "success";
	}
	
	@PutMapping("/users/{id}")
	public String updateuser(User user) {
		try {
			user = new EncryptionUtil().encryption(user) ;
			if(userService.update(user) == 0)
				throw new Exception() ;
		} catch (Exception e) {
			//e.printStackTrace();
			return "failed";
		}
		return "success";
	}
	
	@PostMapping("/login")
	public String login(User user) {
		UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
		try {
			subject.login(token);
			return "success";
		} catch (Exception e) {
//			e.printStackTrace();
			return "failed";
		}
	}
	
	@PostMapping("/register")
	public String register(User user) {
		if(user == null)
			return "failed";
		String username = user.getUsername().replace(" ", "");
		String password = user.getPassword().replace(" ", "");
		if(username == null || username == null)
			return "failed";
		
		String pattern = "^[a-z0-9_-]{3,16}$";
		if(!Pattern.matches(pattern, username) || !Pattern.matches(pattern, password))
			return "failed";
		return adduser(user);
	}
	
	@GetMapping("/logout")
	public String logout() {
		if(subject.isAuthenticated())
			subject.logout();
		String html = "<a href='/html/login.html'>点击返回登录界面</a>";
		return html;
	}
}
