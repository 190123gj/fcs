package com.bornsoft.web.app.filter;

import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bornsoft.utils.tool.AppUtils;
import com.yjf.common.env.Env;
import com.yjf.common.lang.ip.IPUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

public class AccessFilter implements Filter {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public void destroy() {
		
	}
	
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain)
																						throws IOException,
																						ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		//设置请求
		AppUtils.request.set(request);
		
		String url = request.getRequestURI();
		String ip = IPUtil.getIpAddr(request);
		logger.info("请求的ip:{},URI:{}" ,ip, url);
		
		if (Env.isTest()||Env.isLocal()) {
			Enumeration<String> headerNames = request.getHeaderNames();
			while (headerNames.hasMoreElements()) {
				String f = headerNames.nextElement();
				String v = request.getHeader(f);
				logger.info("request.header[ {} ] = {} ", f, v);
			}
		}
		
		StringBuilder stringBuilder = new StringBuilder("请求URL：" + url + "，传入参数：{}");
		Enumeration<String> names = request.getParameterNames();
		int count = 0;
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			if (StringUtil.isNotEmpty(name) && name.toLowerCase().indexOf("password") > -1) {
				continue;
			}
			String[] values = request.getParameterValues(name);
			if (count > 0) {
				stringBuilder.append(",");
			}
			stringBuilder.append(name + "=[");
			for (int i = 0; i < values.length; i++) {
				if (i > 0) {
					stringBuilder.append(",");
				}
				if (values[i].length() <= 50)
					stringBuilder.append(values[i]);
			}
			stringBuilder.append("]");
			count++;
		}
		stringBuilder.append("}");
		logger.info(stringBuilder.toString());
		chain.doFilter(request, response);
		
		if (Env.isTest()||Env.isLocal()) {
			Collection<String> headerNames = response.getHeaderNames();
			for(String head : headerNames){
				String f = head;
				String v = response.getHeader(f);
				logger.info("response.header[ {} ] = {} ", f, v);
			}
		}
	}
	
	@Override
	public void init(FilterConfig config) throws ServletException {

	}
}
