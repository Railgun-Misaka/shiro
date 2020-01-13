package com.fly.shiro.util;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;

import com.fly.shiro.pojo.User;

/**
 * md5加密
 * @author Fly
 *
 */
public class EncryptionUtil {
	
	//迭代次数
	private final int time = 2 ;
	
	//加密方式
	private final String algorithmName = "md5";
	
	//盐
	private String salt ;
	
	public User encryption(User user) {
		String password = user.getPassword();
		salt = user.getSalt();
		if(salt == null)
			salt = new SecureRandomNumberGenerator().nextBytes().toString();
		String encodedpassword = new SimpleHash(algorithmName,password,salt,time).toString();
		user.setPassword(encodedpassword);
		user.setSalt(salt);
		return user;
	}
}
