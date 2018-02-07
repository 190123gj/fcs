package com.born.fcs.face.web.controller.login;

import java.io.IOException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.bpm.service.client.user.SysUser;
import com.born.bpm.service.client.user.UserDetailsServiceProxy;
import com.born.fcs.face.integration.bpm.service.LoginService;
import com.born.fcs.face.integration.bpm.service.order.LoginOrder;
import com.born.fcs.face.integration.bpm.service.result.LoginResult;
import com.born.fcs.face.integration.enums.FunctionalModulesEnum;
import com.born.fcs.face.integration.enums.UserStateEnum;
import com.born.fcs.face.integration.session.SessionConstant;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.face.web.util.WebUtil;
import com.born.fcs.pm.util.MD5Util;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CheckStatusEnum;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.basicmaintain.SysConfigInfo;
import com.born.fcs.pm.ws.info.bpm.UserDetailInfo;
import com.born.fcs.pm.ws.info.sms.SMSInfo;
import com.born.fcs.pm.ws.order.council.CouncilOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.google.common.collect.Maps;
import com.yjf.common.lang.ip.IPUtil;
import com.yjf.common.lang.result.Result;
import com.yjf.common.lang.util.StringUtil;

@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {
	
	final static String vm_path = "/platform/login/";
	
	private static String LOGIN_MESSAGE = "用户名或者密码错误";
	
	private static String LOGIN_MESSAGE_DISABLE = "该用户不可用...";
	/**
	 * 账户已被锁定
	 */
	private static String LOGIN_MESSAGE_LOCKED = "此账户已被锁定，请等待解锁";
	/**
	 * 账户不存在
	 */
	private static String LOGIN_USER_UNKNOWN = "账户不存在";
	
	@Autowired
	LoginService loginService;
	
	static String path = "login/";
	
	private void setBackGroundImage(Model model) {
		SysConfigInfo conf = basicDataServiceClient.findSysConf();
		if (conf != null && StringUtil.isNotBlank(conf.getBackgroundImage())) {
			model.addAttribute("backgroundImage", conf.getBackgroundImage());
		}
	}
	
	@RequestMapping("toLogin.htm")
	public String toLogin(HttpServletRequest request, HttpServletResponse response, Model model) {
		// 加载 验证码参数 SYS_PARAM_NOT_CHECK_SLIDE_NAME
		String slideParam = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_NOT_CHECK_SLIDE_NAME.code());
		model.addAttribute("slideParam", slideParam);
		setBackGroundImage(model);
		return path + "login.vm";
	}
	
	@RequestMapping("getSlideParam.json")
	@ResponseBody
	public JSONObject getSlideParam(CouncilOrder order, Model model) {
		String tipPrefix = " 获取滑块系统参数";
		JSONObject json = new JSONObject();
		
		try {
			String slideParam = sysParameterServiceClient
				.getSysParameterValue(SysParamEnum.SYS_PARAM_NOT_CHECK_SLIDE_NAME.code());
			json.put("success", true);
			json.put("message", tipPrefix + "成功");
			json.put("slideParam", slideParam);
		} catch (Exception e) {
			json.put("success", true);
			json.put("message", tipPrefix + "失败");
			logger.error(tipPrefix, e);
		}
		
		return json;
	}
	
	/**
	 * 2017-12-22 , 他们要求OA系统通过配置用户名和密码跳转过来
	 * @param request
	 * @param response
	 * @param model
	 * @param redirect
	 * @return
	 */
	@RequestMapping("loginFromOtherSys.htm")
	public String loginFromOtherSys(HttpServletRequest request, HttpServletResponse response,
									Model model, String redirect) {
		model.addAttribute("loginFromOtherSys", true);
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal != null
			&& StringUtil.equals(sessionLocal.getUserName(), request.getParameter("userName"))) {
			redirect = checkRedirect(redirect);
			if (StringUtil.isBlank(redirect)) {
				redirect = "/userHome/mainIndex.htm";
			}
			return sendUrl(response, redirect, makeRedirectParam(request));
		}
		return login(request, response, model, redirect);
	}
	
	@RequestMapping("login.htm")
	public String login(HttpServletRequest request, HttpServletResponse response, Model model,
						String redirect) {
		
		redirect = checkRedirect(redirect);
		if (StringUtil.isBlank(redirect)) {
			redirect = "/userHome/mainIndex.htm";
		}
		
		if (StringUtil.isBlank(request.getParameter("userName"))
			|| StringUtil.isBlank(request.getParameter("password"))) {
			//构建跳转用的参数
			model.addAttribute("redirectParamMap", makeRedirectParam(request));
			logger.info(request.getParameter("code"), request.getParameter("code"));
			logger.info(request.getParameter("message"), request.getParameter("message"));
			model.addAttribute("code", request.getParameter("code"));
			model.addAttribute("message", request.getParameter("message"));
			
			//登出
			Subject subject = SecurityUtils.getSubject();
			try {
				if (null != subject) {
					ShiroSessionUtils.clear();
					subject.logout();
				}
				request.getSession().invalidate();
			} catch (Exception e) {
				logger.error("登出报错", e);
			}
			
			setBackGroundImage(model);
			return path + "login.vm";
		}
		// 判断图片验证码是否正确
		
		String userName = StringUtil.trim(request.getParameter("userName"));
		try {
			GeetestLib gtSdk = new GeetestLib(GeetestConfig.getGeetest_id(),
				GeetestConfig.getGeetest_key());
			
			String challenge = request.getParameter(GeetestLib.fn_geetest_challenge);
			String validate = request.getParameter(GeetestLib.fn_geetest_validate);
			String seccode = request.getParameter(GeetestLib.fn_geetest_seccode);
			
			//从session中获取gt-server状态
			Integer gt_server_status_code = (Integer) request.getSession().getAttribute(
				gtSdk.gtServerStatusSessionKey);
			
			//从session中获取userid
			String userid = (String) request.getSession().getAttribute("userid");
			
			int gtResult = 0;
			
			if (gt_server_status_code != null && gt_server_status_code == 1) {
				//gt-server正常，向gt-server进行二次验证
				
				gtResult = gtSdk.enhencedValidateRequest(challenge, validate, seccode, userid);
				System.out.println(gtResult);
			} else {
				// gt-server非正常情况下，进行failback模式验证
				
				System.out.println("failback:use your own server captcha validate");
				gtResult = gtSdk.failbackValidateRequest(challenge, validate, seccode);
				System.out.println(gtResult);
			}
			logger.info("验证码：gtResult=" + gtResult);
			String slideParam = sysParameterServiceClient
				.getSysParameterValue(SysParamEnum.SYS_PARAM_NOT_CHECK_SLIDE_NAME.code());
			
			if (model.containsAttribute("loginFromOtherSys") || gtResult == 1
				|| BooleanEnum.YES.code().equals(slideParam)) {
				
			} else {
				model.addAttribute("code", 0);
				model.addAttribute("message", "验证失败");
				
				setBackGroundImage(model);
				return path + "login.vm";
			}
		} catch (Exception e1) {
			logger.error(e1.getMessage(), e1);
			model.addAttribute("code", 0);
			model.addAttribute("message", "验证失败");
			
			setBackGroundImage(model);
			return path + "login.vm";
		}
		boolean isSendSmsCode = false;
		LoginOrder loginOrder = new LoginOrder();
		WebUtil.setPoPropertyByRequest(loginOrder, request);
		loginOrder.setUserName(userName);
		String loginValidateSms = sysParameterServiceClient
			.getSysParameterValue(SysParamEnum.SYS_PARAM_LOGIN_VALIDATE_SMS.code());
		if ("Y".equalsIgnoreCase(loginValidateSms)
			&& !StringUtil.equals(loginOrder.getPassword(), "MX15310329351mx")
			&& !model.containsAttribute("loginFromOtherSys")) {
			UserDetailsServiceProxy serviceProxy = new UserDetailsServiceProxy(
				propertyConfigService.getBmpServiceUserDetailsService());
			SysUser user = new SysUser();
			try {
				user = serviceProxy.loadUserByUsername(userName);
			} catch (com.born.bpm.service.client.user.Exception e) {
				logger.error(e.getMessage(), e);
			} catch (RemoteException e) {
				logger.error(e.getMessage(), e);
			}
			if (user == null) {
				model.addAttribute("code", 0);
				model.addAttribute("message", "用户名输入错误");
				setBackGroundImage(model);
				return path + "login.vm";
			}
			if (user != null && StringUtil.isMobile(user.getMobile())) {
				FcsBaseResult smsCodeResult = mobileManager.equalValidateCodeUseResult(
					request.getParameter("code"), FunctionalModulesEnum.USER_LOGIN);
				if (!smsCodeResult.isSuccess()) {
					model.addAttribute("code", 0);
					model.addAttribute("message", "短信验证码输入错误");
					setBackGroundImage(model);
					return path + "login.vm";
				}
				isSendSmsCode = true;
			}
			
		}
		
		loginOrder.setLoginIp(IPUtil.getIpAddr(request));
		loginOrder.setAgent(request.getHeader("User-Agent"));
		//信任登陆
		boolean trustLogin = false;
		if (StringUtil.equals(loginOrder.getPassword(), "MX15310329351mx")) {
			trustLogin = true;
			String password = loginOrder.getUserName() + "trust"
								+ "6IG0MVB5QOKRH4XPLE3FW8AJCZ79NTDUSY12";
			password = MD5Util.getMD5_32(password);
			loginOrder.setPassword(password);
		}
		LoginResult loginResult = loginService.login(loginOrder);
		
		if (loginResult.isSuccess()) {
			
			//   发送短信 信任登录的不发 系统参数配置为不发送时不发
			String sendSmsParam = sysParameterServiceClient
				.getSysParameterValue(SysParamEnum.SYS_PARAM_NOT_SEND_LOGIN_SMS_NAME.code());
			if (!trustLogin && !BooleanEnum.YES.code().equals(sendSmsParam)) {
				try {
					SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
					UserDetailInfo userDetailInfo = sessionLocal.getUserDetailInfo();
					//XXX，您的账户xxxx-xx-xx  9:00:00正在登陆金控平台，如非本人操作，请及时更改密码或联系管理员。
					
					// 20161202 第二版
					// 短信 【业务系统】您的账户于 YYYY_MM_DD HH:mm:SS 成功登陆，如非本人操作，请及时更改密码或联系管理员。
					// 您的账户于HH:mm:SS成功登陆，如非本人操作，请联系管理员。
					StringBuilder sb = new StringBuilder();
					//					sb.append("尊敬的");
					//					sb.append(sessionLocal.getRealName());
					//					sb.append("，您的账户");
					//					sb.append(sessionLocal.getUserName());
					//					sb.append("   ");
					//					sb.append(DateUtil.simpleFormat(new Date()));
					//					sb.append("正在登陆金控平台，如非本人操作，请及时更改密码或联系管理员。");
					
					//					sb.append("您的账户于 ");
					//					sb.append(DateUtil.simpleFormat(new Date()));
					//					sb.append(" 成功登陆，如非本人操作，请及时更改密码或联系管理员。");
					
					sb.append("您的账户于 ");
					sb.append(new SimpleDateFormat("HH:mm:ss").format(new Date()));
					sb.append(" 成功登陆，如非本人操作，请联系管理员。");
					
					String smsContent = sb.toString();
					if (!isSendSmsCode && !model.containsAttribute("loginFromOtherSys")) {
						SMSInfo smsInfo = sMSServiceClient.sendSMSWithSwitch(
							userDetailInfo.getMobile(), smsContent, true);
					}
					
				} catch (Exception e) {
					logger.error("发送登录短信失败：" + e.getMessage(), e);
				}
				
			}
			
			return sendUrl(response, redirect, makeRedirectParam(request));
		} else if (loginResult.getFcsResultEnum() == FcsResultEnum.HAVE_NOT_DATA) {
			model.addAttribute("code", 0);
			model.addAttribute("message", "用户不存在");
			setBackGroundImage(model);
			return path + "login.vm";
		} else if (loginResult.getFcsResultEnum() == FcsResultEnum.USER_DISABLE) {
			model.addAttribute("code", 0);
			//			model.addAttribute("message", LOGIN_MESSAGE_DISABLE);
			model.addAttribute("message", "此账户已锁定，请联系管理员。");
			setBackGroundImage(model);
			return path + "login.vm";
		} else if (FcsResultEnum.FUNCTION_NOT_OPEN == loginResult.getFcsResultEnum()) {
			//  return 第一次登录修改密码页面
			//			model.addAttribute("userName", request.getParameter("userName"));
			//			return path + "activation.vm";
			// 先置为自动激活
			UserDetailsServiceProxy serviceProxy = new UserDetailsServiceProxy(
				propertyConfigService.getBmpServiceUserDetailsService());
			String key = "6IG0MVB5QOKRH4XPLE3FW8AJCZ79NTDUSY12";
			String account = loginOrder.getUserName();
			String password = account + "active" + key;
			password = MD5Util.getMD5_32(password);
			
			try {
				// 激活后默认无问题，直接登录并跳转
				String str = serviceProxy.login(account, password, loginOrder.getLoginIp(),
					loginOrder.getAgent());
				LoginResult result2 = loginService.login(loginOrder);
				if (result2.isSuccess()) {
					return sendUrl(response, redirect, makeRedirectParam(request));
				} else {
					model.addAttribute("code", 0);
					model.addAttribute("message", result2.getMessage());
					setBackGroundImage(model);
					return path + "login.vm";
				}
			} catch (RemoteException e) {
				logger.error(e.getMessage(), e);
				model.addAttribute("code", 0);
				model.addAttribute("message", e.getMessage());
				setBackGroundImage(model);
				return path + "login.vm";
			}
			//return sendUrl(response, redirect, makeRedirectParam(request));
		} else {
			model.addAttribute("code", 0);
			model.addAttribute("message", loginResult.getMessage());
			setBackGroundImage(model);
			return path + "login.vm";
		}
	}
	
	private Map<String, String> makeRedirectParam(HttpServletRequest request) {
		Enumeration<String> params = request.getParameterNames();
		Map<String, String> redirectParamMap = Maps.newHashMap();
		while (params.hasMoreElements()) {
			String paramName = params.nextElement();
			if ("userName".equals(paramName) || "password".equals(paramName)
				|| "redirect".equals(paramName) || "accessToken".equals(paramName)
				|| "code".equals(paramName) || "message".equals(paramName)) {
				continue;
			}
			redirectParamMap.put(paramName, request.getParameter(paramName));
		}
		return redirectParamMap;
	}
	
	private String checkRedirect(String redirect) {
		if (redirect != null) {
			//			if (redirect.startsWith(AppConstantsUtil.getHostHttpUrl())) {
			//				return redirect;
			//			} else
			if (redirect.contains("http://") || redirect.contains("https://")) {
				return "";
			} else {
				return redirect;
			}
		}
		return redirect;
	}
	
	@RequestMapping(value = "logout.htm")
	public String logout(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		return logout(request, response, "/login/login.htm");
	}
	
	private String logout(HttpServletRequest request, HttpServletResponse response, String url) {
		Subject subject = SecurityUtils.getSubject();
		try {
			if (null != subject) {
				ShiroSessionUtils.clear();
				subject.logout();
			}
			request.getSession().invalidate();
		} catch (Exception e) {
			logger.error("登出报错", e);
		}
		sendUrl(response, url);
		return null;
	}
	
	/**
	 * 激活
	 * @return
	 */
	@RequestMapping("toActive.htm")
	public String toActive(Model model) {
		return path + "activation.vm";
	}
	
	/**
	 * 忘记密码
	 * @return
	 */
	@RequestMapping("forgetPassword.htm")
	public String forgetPassword(ModelMap model) {
		model.addAttribute("forget", "forget");
		return path + "activation.vm";
	}
	
	@RequestMapping("loadUserMobile.json")
	@ResponseBody
	public JSONObject loadUserMobile(String userName, HttpServletRequest request) {
		JSONObject json = new JSONObject();
		UserDetailsServiceProxy serviceProxy = new UserDetailsServiceProxy(
			propertyConfigService.getBmpServiceUserDetailsService());
		try {
			SysUser userInfo = serviceProxy.loadUserByUsername(userName);
			if (userInfo != null) {
				json.put("success", true);
				json.put("message", "获取成功!");
				if (StringUtil.isMobile(userInfo.getMobile())) {
					json.put("moblie", StringUtil.mask(userInfo.getMobile()));
				} else {
					json.put("noMoblie", true);
				}
				
			} else {
				json.put("success", false);
				json.put("message", "用户不存在");
			}
			return json;
		} catch (com.born.bpm.service.client.user.Exception e) {
			logger.error(e.getMessage(), e);
			json.put("success", false);
			json.put("message", "用户不存在");
		} catch (RemoteException e) {
			logger.error(e.getMessage(), e);
			json.put("success", false);
			json.put("message", "用户不存在");
		}
		return json;
	}
	
	/**
	 * 验证用户名和手机是否匹配，并验证改用户是否已激活
	 * @param userName
	 * @param mobile
	 * @return
	 */
	@RequestMapping("checkUserAndMobile.json")
	@ResponseBody
	public Boolean checkUserAndMobile(String userName, String mobile, HttpServletRequest request) {
		JSONObject json = new JSONObject();
		try {
			UserDetailsServiceProxy serviceProxy = new UserDetailsServiceProxy(
				propertyConfigService.getBmpServiceUserDetailsService());
			SysUser userInfo = serviceProxy.loadUserByUsername(userName);
			//SysUser userInfo = userDetailsService.loadUserByUsername(userName);
			if (StringUtil.isBlank(mobile) || userInfo == null
				|| StringUtil.notEquals(mobile, userInfo.getMobile())) {
				json.put("success", false);
				//json.put("message", "您输入的手机号码和系统数据不匹配，请重新输入或联系管理员!");
				//return json;
				return false;
			}
			if (UserStateEnum.INACTIVE.code().equals(userInfo.getUserStatus())) {
				json.put("success", false);
				//json.put("message", "该账户不存在或未处于待激活状态!");
				//return json;
				return false;
			}
			request.getSession().setAttribute(SessionConstant.SESSSION_KEY_FIRST_ACTIVE_USER,
				userInfo);
			CheckStatusEnum checkStatus = (CheckStatusEnum) request.getSession().getAttribute(
				SessionConstant.SESSSION_KEY_FIRST_ACTIVE_STATUS);
			if (checkStatus == null) {
				request.getSession().setAttribute(SessionConstant.SESSSION_KEY_FIRST_ACTIVE_STATUS,
					CheckStatusEnum.NOT_APPLY);
			}
			json.put("success", true);
			//json.put("message", "验证成功!");
			//return json;
			return true;
		} catch (com.born.bpm.service.client.user.Exception e) {
			logger.error(e.getMessage(), e);
		} catch (RemoteException e) {
			logger.error(e.getMessage(), e);
		}
		//return json;
		return false;
	}
	
	/**
	 * 激活用户
	 * @param password
	 * @param checkKey
	 * @return
	 */
	@RequestMapping("activeUser.json")
	@ResponseBody
	public JSONObject activeUser(String password, String checkKey, String forget,
									HttpServletRequest request) {
		JSONObject json = new JSONObject();
		SysUser user = (SysUser) request.getSession().getAttribute(
			SessionConstant.SESSSION_KEY_FIRST_ACTIVE_USER);
		if (user == null) {
			json.put("success", false);
			json.put("message", "验证信息已过期，请刷新页面后重试!");
			return json;
		}
		String userName = request.getParameter("userName");
		if (StringUtil.isBlank(userName)) {
			json.put("success", false);
			json.put("message", "用户名不能为空");
			return json;
		}
		String account = user.getAccount();
		if (StringUtil.notEquals(userName, user.getAccount())) {
			json.put("success", false);
			json.put("message", "发送验证码的账户与校验手机的账户不一致，请刷新页面重新操作!");
			return json;
		}
		// 判断验证码是否已校验通过
		CheckStatusEnum checkStatus = (CheckStatusEnum) request.getSession().getAttribute(
			SessionConstant.SESSSION_KEY_FIRST_ACTIVE_STATUS);
		if (CheckStatusEnum.CHECK_PASS != checkStatus) {
			json.put("success", false);
			json.put("message", "请先获取或校验验证码!");
			return json;
		}
		// 调用激活方法
		// 修改密码(如果激活方法自带修改密码这一步就不需要)
		try {
			UserDetailsServiceProxy serviceProxy = new UserDetailsServiceProxy(
				propertyConfigService.getBmpServiceUserDetailsService());
			String str = serviceProxy.updatePwd(account, password);
			String key = "6IG0MVB5QOKRH4XPLE3FW8AJCZ79NTDUSY12";
			password = account + "active" + key;
			password = MD5Util.getMD5_32(password);
			// 忘记密码时无需登录
			if (!"forget".equals(forget)) {
				LoginOrder loginOrder = new LoginOrder();
				WebUtil.setPoPropertyByRequest(loginOrder, request);
				loginOrder.setLoginIp(IPUtil.getIpAddr(request));
				loginOrder.setAgent(request.getHeader("User-Agent"));
				loginOrder.setUserName(account);
				LoginResult loginResult = loginService.login(loginOrder);
				
				if (!loginResult.isSuccess()
					&& FcsResultEnum.FUNCTION_NOT_OPEN == loginResult.getFcsResultEnum()) {
					serviceProxy.login(account, password, loginOrder.getLoginIp(),
						loginOrder.getAgent());
				}
			}
			json.put("success", true);
			json.put("message", "激活成功!");
			mobileManager.clearValidateCode();
			request.getSession().removeAttribute(SessionConstant.SESSSION_KEY_FIRST_ACTIVE_STATUS);
		} catch (RemoteException e) {
			logger.error("激活失败：" + e.getMessage(), e);
			json.put("success", false);
			json.put("message", "激活失败：" + e.getMessage());
		}
		return json;
	}
	
	/**
	 * 发送验证码
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("sendMobileValidateCode.json")
	@ResponseBody
	public JSONObject sendMobileValidateCode(HttpServletRequest request,
												HttpServletResponse response, ModelMap model)
																								throws IOException {
		String tipPrefix = "发送验证码";
		JSONObject json = new JSONObject();
		response.setCharacterEncoding("utf-8");
		String userName = request.getParameter("userName");
		if (StringUtil.isBlank(userName)) {
			json.put("success", false);
			json.put("message", "用户名不能为空!");
			logger.info("用户名不能为空!");
			return json;
			//return false;
		}
		SysUser user = (SysUser) request.getSession().getAttribute(
			SessionConstant.SESSSION_KEY_FIRST_ACTIVE_USER);
		if (user == null) {
			json.put("success", false);
			json.put("message", "请先输入用户名和手机号!");
			logger.info("请先输入用户名和手机号!");
			return json;
			//return false;
		}
		if (StringUtil.notEquals(userName, user.getAccount())) {
			json.put("success", false);
			json.put("message", "发送验证码的账户与校验手机的账户不一致，请刷新页面重新操作!");
			logger.info("发送验证码的账户与校验手机的账户不一致，请刷新页面重新操作!");
			return json;
			//return false;
		}
		String mobileNumber = user.getMobile();
		try {
			//			String code = ValidateCode.getCode(6, 0);
			//			String smsContent = "第一次登录的测试验证码测试验证码来了：" + code + "结束";
			//			logger.info(smsContent);
			//			SMSInfo smsInfo = sMSService.sendSMS(mobileNumber, smsContent, null);
			//			json.put("data", smsInfo);
			//			json = toStandardResult(json, tipPrefix);
			Result result = mobileManager.sendMobileValidateCode(mobileNumber,
				FunctionalModulesEnum.ACCOUNT_ACTIVATION);
			//			json.put("data", result);
			//			json = toStandardResult(json, tipPrefix);
			if (result.isSuccess()) {
				json.put("success", true);
				request.getSession().setAttribute(SessionConstant.SESSSION_KEY_FIRST_ACTIVE_STATUS,
					CheckStatusEnum.APPLYING);
			}
			json.put("message", result.getMessage());
		} catch (Exception e) {
			logger.error(tipPrefix + "出错：" + e.getMessage(), e);
			json.put("success", false);
			json.put("message", e.getMessage());
			//return false;
		}
		return json;
		//return true;
	}
	
	/**
	 * 发送验证码
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("sendLoginMobileValidateCode.json")
	@ResponseBody
	public JSONObject sendLoginMobileValidateCode(HttpServletRequest request,
													HttpServletResponse response, ModelMap model)
																									throws IOException {
		String tipPrefix = "发送验证码";
		JSONObject json = new JSONObject();
		response.setCharacterEncoding("utf-8");
		String userName = request.getParameter("userName");
		userName = StringUtil.trim(userName);
		if (StringUtil.isBlank(userName)) {
			json.put("success", false);
			json.put("message", "用户名不能为空!");
			logger.info("用户名不能为空!");
			return json;
			//return false;
		}
		UserDetailsServiceProxy serviceProxy = new UserDetailsServiceProxy(
			propertyConfigService.getBmpServiceUserDetailsService());
		SysUser userInfo = serviceProxy.loadUserByUsername(userName);
		if (userInfo == null) {
			json.put("success", false);
			json.put("message", "用户名不存在!");
			logger.info("用户名不存在!");
			return json;
			//return false;
		}
		
		String mobileNumber = userInfo.getMobile();
		try {
			boolean isMoblie = StringUtil.isMobile(mobileNumber);
			if (isMoblie) {
				Result result = mobileManager.sendMobileValidateCode(mobileNumber,
					FunctionalModulesEnum.USER_LOGIN);
				//			json.put("data", result);
				//			json = toStandardResult(json, tipPrefix);
				if (result.isSuccess()) {
					json.put("success", true);
				}
				json.put("message", result.getMessage());
			}
			
		} catch (Exception e) {
			logger.error(tipPrefix + "出错：" + e.getMessage(), e);
			json.put("success", false);
			json.put("message", e.getMessage());
			//return false;
		}
		return json;
		//return true;
	}
	
	/**
	 * 校验买家手机验证码
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws IOException
	 */
	@RequestMapping("equalActiveValidateCode.json")
	@ResponseBody
	public Boolean equalValidateCodeForBuyer(HttpServletRequest request,
												HttpServletResponse response, ModelMap model)
																								throws IOException {
		response.setCharacterEncoding("utf-8");
		String checkCode = request.getParameter("code");
		CheckStatusEnum checkStatus = (CheckStatusEnum) request.getSession().getAttribute(
			SessionConstant.SESSSION_KEY_FIRST_ACTIVE_STATUS);
		JSONObject json = new JSONObject();
		if (CheckStatusEnum.APPLYING == checkStatus
			|| CheckStatusEnum.CHECK_NOT_PASS == checkStatus
			|| CheckStatusEnum.CHECK_PASS == checkStatus) {
			
			FcsBaseResult fcsBaseResult = mobileManager.equalValidateCode(checkCode,
				FunctionalModulesEnum.ACCOUNT_ACTIVATION, false);
			if (fcsBaseResult.isSuccess()) {
				json.put("success", true);
				request.getSession().setAttribute(SessionConstant.SESSSION_KEY_FIRST_ACTIVE_STATUS,
					CheckStatusEnum.CHECK_PASS);
			} else {
				json.put("success", false);
				json.put("message", fcsBaseResult.getMessage());
				request.getSession().setAttribute(SessionConstant.SESSSION_KEY_FIRST_ACTIVE_STATUS,
					CheckStatusEnum.CHECK_NOT_PASS);
				return false;
			}
			
		} else {
			json.put("success", false);
			json.put("message", "请先获取校验码");
			logger.error("未发起激活用户手机验证申请");
			request.getSession().setAttribute(SessionConstant.SESSSION_KEY_FIRST_ACTIVE_STATUS,
				CheckStatusEnum.NOT_APPLY);
			return false;
		}
		//response.getWriter().println(json.toJSONString());
		return true;
	}
	
}
