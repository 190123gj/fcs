package com.born.fcs.rm.ws.base.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

public class RequestLogFilter implements Filter {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public void destroy() {
		
	}
	
	
	private static final String[] urls = {};
	
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain chain)
																						throws IOException,
																						ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		String uri = request.getServletPath();
		
		for (String s : urls) {
			if (uri.startsWith(s)) {
				chain.doFilter(request, response);
				return;
			}
		}
		
		StringBuilder stringBuilder = new StringBuilder("http请求URL：" + uri + "，传入参数：{");
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
	}
	
	@Override
	public void init(FilterConfig config) throws ServletException {
		
	}
}
