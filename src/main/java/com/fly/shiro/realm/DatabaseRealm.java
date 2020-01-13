package com.fly.shiro.realm;

import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.fly.shiro.mapper.PermissionMapper;
import com.fly.shiro.mapper.RoleMapper;
import com.fly.shiro.mapper.UserMapper;
import com.fly.shiro.pojo.User;

public class DatabaseRealm extends AuthorizingRealm {

	@Autowired private UserMapper userMapper ;
	
	@Autowired private PermissionMapper permissionMapper ;
	
	@Autowired private RoleMapper roleMapper ;
	
	
	/**	
	 *   此方法调用 hasRole,hasPermission的时候才会进行回调.	
	 *  权限信息.(授权): 
	 * 1、如果用户正常退出，缓存自动清空； 
	 * 2、如果用户非正常退出，缓存自动清空；	 
	 * 3、如果我们修改了用户的权限，而用户不退出系统，修改的权限无法立即生效。 （需要手动编程进行实现；放在service进行调用）
	 *  在权限修改后调用realm中的方法，realm已经由spring管理，所以从spring中获取realm实例， 调用clearCached方法；	 
	 * :Authorization 是授权访问控制，用于对用户进行的操作授权，证明该用户是否允许进行当前操作，如访问某个链接，某个资源文件等。
	*/
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		// 能进入到这里，表示账号已经通过验证了
		String username = (String) principalCollection.getPrimaryPrincipal();
		// 通过service获取角色和权限
		int uid = userMapper.getbyname(username).getUid();
		Set<String> roles = roleMapper.rolenames(uid);
		Set<String> permissions = permissionMapper.permissions(uid);

		// 授权对象
		SimpleAuthorizationInfo s = new SimpleAuthorizationInfo();
		// 把通过service获取到的角色和权限放进去
		s.setStringPermissions(permissions);
		s.setRoles(roles);
		return s;
	}

	/**
	 * 1、CAS认证 ,验证用户身份 
	 * 2、将用户基本信息设置到会话中,方便获取
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// 获取账号密码
		UsernamePasswordToken t = (UsernamePasswordToken) token;
		String username = t.getPrincipal().toString();
		// 获取数据库中的密码
		User user = userMapper.getbyname(username);
		if(user == null)
			return null ;  //shiro底层会抛出UnKnowAccountException异常
		String passwordInDB = user.getPassword();
		String salt = user.getSalt();
		// 认证信息里存放账号密码, getName() 是当前Realm的继承方法,通常返回当前类名 :databaseRealm
		// 盐也放进去
		SimpleAuthenticationInfo a = new SimpleAuthenticationInfo(username, passwordInDB, ByteSource.Util.bytes(salt), getName());
		return a;
	}
}
