package com.bornsoft.web.security.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.info.common.SysWebAccessTokenInfo;
import com.born.fcs.pm.ws.service.common.SysWebAccessTokenService;
import com.bornsoft.api.service.app.AppLoginServiceImpl;
import com.bornsoft.api.service.app.SpringUtil;
import com.bornsoft.api.service.app.orders.AppLoginOrder;
import com.bornsoft.api.service.app.result.AppLoginResult;
import com.bornsoft.utils.constants.BornApiConstants;
import com.bornsoft.utils.enums.AppResultCodeEnum;
import com.bornsoft.utils.tool.BornApiRequestUtils;
import com.bornsoft.utils.tool.InstallCommonResultUtil;
import com.yjf.common.lang.ip.IPUtil;
import com.yjf.common.lang.util.ListUtil;
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
	
	@Autowired
	private AppLoginServiceImpl appLoginService;
	
	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response,
										Object mappedValue) {
		String accessToken = request.getParameter("accessToken");
		if (StringUtil.isNotBlank(accessToken)) {
			if (sysWebAccessTokenServiceClient == null) {
				sysWebAccessTokenServiceClient = SpringUtil
					.getBean("sysWebAccessTokenServiceClient");
			}
			if (appLoginService == null) {
				sysWebAccessTokenServiceClient = SpringUtil.getBean("appLoginService");
			}
			SysWebAccessTokenInfo token = sysWebAccessTokenServiceClient.query(accessToken);
			if (token != null)
				logger.info("accessToken : {}", token);
			request.setAttribute("accessToken", token);
			return token != null;
		}
		boolean access = super.isAccessAllowed(request, response, mappedValue);
		HttpServletRequest req = (HttpServletRequest) request;
		if (!access) {
			String e_token = BornApiRequestUtils.getParameter((HttpServletRequest) request,
				BornApiConstants.E_TOKEN);
			if (StringUtils.isNotBlank(e_token)) {
				//创建|获取session
				req.getSession();
				AppLoginOrder loginOrder = new AppLoginOrder();
				loginOrder.setCreateToken(false);
				loginOrder.setDeviceNo(BornApiRequestUtils.getParameter(req,
					BornApiConstants.DEVICE_NO));
				loginOrder.setToken(e_token);
				String userName = StringUtils.defaultIfBlank(BornApiRequestUtils.getParameter(
					(HttpServletRequest) request, BornApiConstants.LOGING_NAME),
					BornApiRequestUtils.getParameter((HttpServletRequest) request,
						BornApiConstants.USER_NAME));
				loginOrder.setUserName(userName);
				loginOrder.setUmDeviceNo(BornApiRequestUtils.getParameter(req, "umDeviceNo"));
				loginOrder.setLoginIp(IPUtil.getIpAddr(req));
				loginOrder.setAgent(req.getHeader("User-Agent"));
				AppLoginResult result = appLoginService.autoLogin(loginOrder);
				access = result.isSuccess();
				if (!access) {
					req.setAttribute(BornApiConstants.ERR_MSG, result.getMessage());
				}
				if (!super.isLoginRequest(request, response) && access) {
					int version = BornApiRequestUtils.getParamterInteger(req,
						BornApiConstants.VERSION, 0);
					if (version > BornApiConstants.MinVersion) {
						long curOrgId = BornApiRequestUtils.getParamterLong(req,
							BornApiConstants.CUR_ORG_ID, -1L);
						if (curOrgId != -1) {
							if (!appLoginService.switchDept(curOrgId)) {
								access = false;
								req.setAttribute(BornApiConstants.ERR_MSG, "请重新选择当前部门");
								req.setAttribute(BornApiConstants.ERR_CODE,
									AppResultCodeEnum.FAILED);
							}
						} else {
							SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
							if (sessionLocal == null || sessionLocal.getUserDetailInfo() == null) {
								req.setAttribute(BornApiConstants.ERR_MSG, "会话失效,请重新登录");
								req.setAttribute(BornApiConstants.ERR_CODE,
									AppResultCodeEnum.FAILED);
								access = false;
								return access;
							}
							
							if (ListUtil.isNotEmpty(sessionLocal.getUserDetailInfo().getOrgList())) {
								access = false;
								req.setAttribute(BornApiConstants.ERR_MSG, "请重新选择当前部门");
								req.setAttribute(BornApiConstants.ERR_CODE,
									AppResultCodeEnum.FAILED);
							}
						}
					}
				}
			}
		}
		return access;
	}
	
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response)
																						throws Exception {
		if (isLoginRequest(request, response)) {
			return true;
		} else {
			HttpServletRequest req = (HttpServletRequest) request;
			Object msg = req.getAttribute(BornApiConstants.ERR_MSG);
			String err = ((msg != null && StringUtils.isNotBlank(msg.toString())) ? msg.toString()
				: "会话失效,请重新登录!");
			Object code = req.getAttribute(BornApiConstants.ERR_CODE);
			AppResultCodeEnum rsCode = (AppResultCodeEnum) (code != null ? code
				: AppResultCodeEnum.NOT_LOGIN);
			JSONObject result = InstallCommonResultUtil.setAppResult(rsCode, err);
			responseJson(response, result);
			return false;
		}
	}
	
	/**
	 * 以JSON格式输出
	 * @param response
	 */
	private void responseJson(ServletResponse response, JSONObject responseJSONObject) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.append(responseJSONObject.toString());
			logger.info("响应：" + responseJSONObject.toString());
		} catch (IOException e) {
			logger.debug("返回json失败", e);
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
	
}
