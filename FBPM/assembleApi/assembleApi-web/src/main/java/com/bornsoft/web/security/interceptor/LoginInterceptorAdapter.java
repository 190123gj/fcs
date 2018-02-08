package com.bornsoft.web.security.interceptor;

/**
 * www.yiji.com Inc.
 * Copyright (c) 2012 All Rights Reserved.
 */

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.integration.bpm.service.PermissionService;
import com.bornsoft.api.service.app.JckBaseService;
import com.bornsoft.utils.enums.AppResultCodeEnum;
import com.bornsoft.web.security.permission.PermissionUtilLocal;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

public class LoginInterceptorAdapter implements HandlerInterceptor,InitializingBean {
	
	@Autowired
	private PermissionService permissionStaticService;
	
	String ignoreUrlStr = "";
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 正则${:}
	 */
	protected static Pattern pattern = Pattern
		.compile("\\$\\{[a-zA-Z0-9.]{1,}:[a-zA-Z0-9.]{1,}\\}");
	
	/**
	 * @param request
	 * @param response
	 * @param handler
	 * @return
	 * @throws Exception
	 * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
								Object handler) throws Exception {
		
		String uri = request.getServletPath();
		if ("/projectMg/contract/excelMessage.htm".equals(uri)
			|| "/projectMg/contract/excelShow.htm".equals(uri)) {
			return true;
		}
		if (PermissionUtilLocal.checkPermission(uri)) {
			return true;
		} else {
			JSONObject json = new JSONObject();
			json.put(JckBaseService.CODE, AppResultCodeEnum.FAILED.code());
			json.put(JckBaseService.MESSAGE, "未授权的资源" );
			printHttpResponse(response, json);
			return false;
		}
	
	}
	
	
	/**
	 * @param request
	 * @param response
	 * @param handler
	 * @param modelAndView
	 * @throws Exception
	 * @see org.springframework.web.servlet.HandlerInterceptor#postHandle(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, java.lang.Object,
	 * org.springframework.web.servlet.ModelAndView)
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
							Object handler, ModelAndView modelAndView) throws Exception {
		try {

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * @param request
	 * @param response
	 * @param handler
	 * @param ex
	 * @throws Exception
	 * @see org.springframework.web.servlet.HandlerInterceptor#afterCompletion(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, java.lang.Object,
	 * java.lang.Exception)
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
								Object handler, Exception ex) throws Exception {
		
	}
	
	public void setIgnoreUrlStr(String ignoreUrlStr) {
		this.ignoreUrlStr = ignoreUrlStr;
	}
	
	private void printHttpResponse(HttpServletResponse response,
			JSONObject json) {
		JckBaseService.responseJson(response, json);
	}


	@Override
	public void afterPropertiesSet() throws Exception {
		PermissionUtilLocal.init(permissionStaticService);
	}
}
