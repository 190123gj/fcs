package com.bornsoft.web.security.realm;

/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.born.fcs.face.integration.bpm.service.result.LoginResult;
import com.born.fcs.face.integration.token.LoginDataToken;
import com.bornsoft.api.service.app.AppLoginServiceImpl;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

/**
 * Shiro登录认证及授权Realm
 * 
 * @Filename ShiroWebServiceRealm.java
 * 
 * @Description
 * 
 * @Version 1.0
 * 
 * @Author lvchen
 * 
 * @Email lvchen@yiji.com
 * 
 * @History <li>Author: lvchen</li> <li>Date: 2013-1-5</li> <li>Version: 1.0</li>
 * <li>Content: create</li>
 * 
 */
public class ShiroWebServiceRealm extends AuthorizingRealm {
	
	private static Logger logger = LoggerFactory.getLogger(ShiroWebServiceRealm.class);
	
	@Autowired
	private AppLoginServiceImpl appLoginService;
	
	/**
	 * 
	 * 登录认证
	 * 
	 * @param authcToken
	 * @return
	 * @throws AuthenticationException
	 * @see org.apache.shiro.realm.AuthenticatingRealm#doGetAuthenticationInfo(org.apache.shiro.authc.AuthenticationToken)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) {
		
		LoginDataToken token = (LoginDataToken) authcToken;
		logger.info("登录用户:{}", token.getPrincipal());
		
		AuthenticationInfo authenticationInfo = null;
		// 验证码 读session
		LoginResult result = null;
		if (token.isValidate()) {
			result = appLoginService.validateLoginInfo(token.getLoginOrder());
		} else {
			result = new LoginResult();
			result.setSuccess(true);
			result.setUserInfo(token.getInfo());
		}
		if (result.isSuccess()) {
			authenticationInfo = new SimpleAuthenticationInfo(result.getUserInfo(),
				String.valueOf(token.getPassword()), result.getUserInfo().getUserName());
			return authenticationInfo;
		} else {
			throw new AuthenticationException(result.getMessage());
		}
		
	}
	
	/**
	 * 
	 * 授权
	 * 
	 * @param principals
	 * @return
	 * @see org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		//添加角色 添加权限
		return info;
	}
	
}