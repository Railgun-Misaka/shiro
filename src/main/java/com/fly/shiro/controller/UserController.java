package com.fly.shiro.controller;

import java.util.List;
import java.util.regex.Pattern;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fly.shiro.pojo.Result;
import com.fly.shiro.pojo.StatusCode;
import com.fly.shiro.pojo.User;
import com.fly.shiro.service.UserService;
import com.fly.shiro.util.EncryptionUtil;

@RestController
@CrossOrigin //允许跨域请求
public class UserController {
	
	@Autowired private UserService userService;
	
	@Autowired private Subject subject ;
	
	@Autowired SessionManager sessionManager ;
	
	@GetMapping("/users")
	public List<User> list(){
		List<User> users = null ;
		try {
			users = userService.listuser();
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return users;
	}
	
	@GetMapping("/users/{uid}")
	public User getone(@PathVariable("uid") int id){
		User user = null ;
		try {
			user = userService.getbyid(id) ;
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return user ;
	}
	
	@PostMapping("/users")
	public String adduser(User user) {
		try {
			user = new EncryptionUtil().encryption(user); //加密处理
			if(userService.adduser(user) != 1)
				throw new Exception() ;
		} catch (Exception e) {
			//e.printStackTrace();
			return "failed";
		}
		return "success";
	}
	
	@DeleteMapping("/users/{uid}")
	public String deleteuser(@PathVariable("uid") int uid) throws AuthorizationException {
		try {
			if(userService.deleteuser(uid) != 1)
				return "failed";
		} catch (AuthorizationException e) {
			//e.printStackTrace();
			return "noAuth";
		}
		return "success";
	}
	
	@PutMapping("/users/{uid}")
	public String updateuser(@PathVariable("uid") int uid, User user) {
		
		if(user.getUid() == 0)
			user.setUid(uid);
			
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
		
		String username = user.getUsername() ;
		String password = user.getPassword() ;
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		try {
			subject.login(token);
			subject.getSession().setAttribute("user", userService.getbyname(username));
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
	
	/**
	 * 是否登录过
	 * @param redirecturl
	 * @return
	 */
	@GetMapping("/isAuthenticated")
	public String isAuthenticated(String redirecturl) {
		if(redirecturl == null)
			redirecturl = "/html/login.html";
		if(subject.isAuthenticated()){
			return htmlbody(redirecturl + "?token=" + subject.getSession().getId());  //为子服务颁发token
		}
		return htmlbody(redirecturl) ;
	}
	
	private String htmlbody(String url) {
		String html = "<script>location='" + url + "'</script>" ;
		return html;
	}
	
	/**
	 * token认证
	 * @param token
	 * @return
	 */
	@GetMapping("/tokenAuthenticated")
	public Result tokenAuthenticated(String token) {
		
		SessionKey key = new DefaultSessionKey(token);
		try {
			Session session = sessionManager.getSession(key) ;
			if(session == null)
				throw new Exception();
			return new Result(true, StatusCode.OK, null, (User) session.getAttribute("user"));
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, StatusCode.ERROR);
		}
	}
}
