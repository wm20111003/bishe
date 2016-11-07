package com.centfor.frame.shiro;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.centfor.frame.util.GlobalStatic;
import com.centfor.system.entity.User;
import com.centfor.system.service.IUserRoleMenuService;

//认证数据库存储
@Component("shiroDbRealm")
public class ShiroDbRealm extends AuthorizingRealm {
	public Logger logger = LoggerFactory.getLogger(getClass());
	@Resource
	IUserRoleMenuService userRoleMenuService;

	@Resource
	private CacheManager shiroCacheManager;

	public static final String HASH_ALGORITHM = "MD5";
	public static final int HASH_INTERATIONS = 1;
	private static final int SALT_SIZE = 8;

	public ShiroDbRealm() {
		// 认证
		// super.setAuthenticationCacheName(GlobalStatic.authenticationCacheName);
		super.setAuthenticationCachingEnabled(false);
		// 授权
		super.setAuthorizationCacheName(GlobalStatic.authorizationCacheName);
		super.setName(GlobalStatic.authorizingRealmName);
	}

	// 授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principalCollection) {

		// 因为非正常退出，即没有显式调用 SecurityUtils.getSubject().logout()
		// (可能是关闭浏览器，或超时)，但此时缓存依旧存在(principals)，所以会自己跑到授权方法里。
		if (!SecurityUtils.getSubject().isAuthenticated()) {
			doClearCache(principalCollection);
			SecurityUtils.getSubject().logout();
			return null;
		}

		ShiroUser shiroUser = (ShiroUser) principalCollection
				.getPrimaryPrincipal();
		// String userId = (String)
		// principalCollection.fromRealm(getName()).iterator().next();
		String userId = shiroUser.getId();
		if (StringUtils.isBlank(userId)) {
			return null;
		}
		// 添加角色及权限信息
		SimpleAuthorizationInfo sazi = new SimpleAuthorizationInfo();
		try {
			sazi.addRoles(userRoleMenuService.getRolesAsString(userId));
			sazi.addStringPermissions(userRoleMenuService
					.getPermissionsAsString(userId));
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}

		return sazi;
	}

	// 认证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		/*
		 * String pwd = new String(upToken.getPassword()); if
		 * (StringUtils.isNotBlank(pwd)) { pwd = DigestUtils.md5Hex(pwd); }
		 */
		// 调用业务方法
		User user = null;
		String userName = upToken.getUsername();
		try {
			user = userRoleMenuService.findLoginUser(userName, null);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw  new AuthenticationException(e);
		}

		if (user != null) {
			// 要放在作用域中的东西，请在这里进行操作
			// SecurityUtils.getSubject().getSession().setAttribute("c_user",
			// user);
			// byte[] salt = EncodeUtils.decodeHex(user.getSalt());

			//Session session = SecurityUtils.getSubject().getSession(false);
			AuthenticationInfo authinfo = new SimpleAuthenticationInfo(
					new ShiroUser(user), user.getPassword(), getName());
			// Cache<Object, Object> cache =
			// shiroCacheManager.getCache(GlobalStatic.authenticationCacheName);
			// cache.put(GlobalStatic.authenticationCacheName+"-"+userName,
			// session.getId());
			return authinfo;
		}
		// 认证没有通过
		return null;
	}

	/**
	 * 设定Password校验的Hash算法与迭代次数.
	 */
	@PostConstruct
	public void initCredentialsMatcher() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(
				HASH_ALGORITHM);
		matcher.setHashIterations(HASH_INTERATIONS);
		setCredentialsMatcher(matcher);
	}
}
