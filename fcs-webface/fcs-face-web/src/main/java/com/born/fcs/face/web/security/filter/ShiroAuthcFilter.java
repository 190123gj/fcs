package com.born.fcs.face.web.security.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import com.born.fcs.face.web.util.SpringUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.info.common.SysWebAccessTokenInfo;
import com.born.fcs.pm.ws.service.common.SysWebAccessTokenService;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

/**
 * 自定义权限检查
 * @author wuzj
 * 
 */
public class ShiroAuthcFilter extends FormAuthenticationFilter {
	
	private static Logger logger = LoggerFactory.getLogger(ShiroAuthcFilter.class);
	
	private static SysWebAccessTokenService sysWebAccessTokenServiceClient;
	
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response,
										Object mappedValue) {
		
		String accessToken = request.getParameter("accessToken");
		if (StringUtil.isNotBlank(accessToken)) {
			if (sysWebAccessTokenServiceClient == null)
				sysWebAccessTokenServiceClient = SpringUtil
					.getBean("sysWebAccessTokenServiceClient");
			SysWebAccessTokenInfo token = sysWebAccessTokenServiceClient.query(accessToken);
			if (token != null)
				logger.info("accessToken : {}", token);
			request.setAttribute("accessToken", token);
			return token != null;
		}
		
		return super.isAccessAllowed(request, response, mappedValue);
	}
}
