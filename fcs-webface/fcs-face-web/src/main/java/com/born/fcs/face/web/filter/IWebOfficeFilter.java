package com.born.fcs.face.web.filter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class IWebOfficeFilter implements Filter {
	
	private String[] iWebofficeHandleURL;

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) req;
		String reqPath = getServletPath(httpServletRequest);
		for (String tempUrl : iWebofficeHandleURL) {
			if (tempUrl.equals(reqPath)) {
				InputStream in = req.getInputStream();
//				String tempSavePath = System.getProperty("java.io.tmpdir") + File.separator + UUID.randomUUID();
				byte[] buf = new byte[32 * 1024];
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				for (int len; (len = in.read(buf)) != -1;) {
					out.write(buf, 0, len);
				}
				out.close();
				req.setAttribute("iwebofficeParam", out.toByteArray());
			}
		}
		filterChain.doFilter(req, resp);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		String initParamValue = config.getInitParameter("iwebofficeURL");
		iWebofficeHandleURL = initParamValue.split(",");
	}
	
	public static String getServletPath(HttpServletRequest request){
		String servletPath = request.getServletPath();
		if (servletPath == null || "".equals(servletPath)) {
			servletPath = request.getRequestURI();
			int index = servletPath.indexOf('/', 2);
			if (index != -1) servletPath = servletPath.substring(index);
		}
		return servletPath;
	}

}
