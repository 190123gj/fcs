package com.bornsoft.web.app.login;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.born.bpm.service.client.user.SysUser;
import com.born.bpm.service.client.user.UserDetailsServiceProxy;
import com.born.fcs.face.integration.bpm.service.PasswordManagerService;
import com.born.fcs.face.integration.bpm.service.order.UpdatePasswordOrder;
import com.born.fcs.face.integration.bpm.service.result.LoginResult;
import com.born.fcs.face.integration.enums.FunctionalModulesEnum;
import com.born.fcs.face.integration.enums.UserStateEnum;
import com.born.fcs.face.integration.session.SessionConstant;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.session.SessionMobileSend;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.pm.util.MD5Util;
import com.born.fcs.pm.ws.enums.CheckStatusEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.bpm.Org;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.bornsoft.api.service.app.AppLoginServiceImpl;
import com.bornsoft.api.service.app.orders.AppLoginOrder;
import com.bornsoft.api.service.app.result.AppLoginResult;
import com.bornsoft.utils.enums.AppResultCodeEnum;
import com.bornsoft.utils.exception.BornApiException;
import com.bornsoft.utils.tool.AesUtil;
import com.bornsoft.utils.tool.AppUtils;
import com.bornsoft.utils.tool.ReflectUtils;
import com.bornsoft.utils.tool.ValidateParamUtil;
import com.bornsoft.web.app.base.BaseController;
import com.yjf.common.env.Env;
import com.yjf.common.lang.ip.IPUtil;
import com.yjf.common.lang.result.Result;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;

@Controller
@RequestMapping
public class LoginController extends BaseController {
	
	@Autowired
	private PasswordManagerService passwordManagerService;
	
	final static String vm_path = "/platform/login/";
	
	@Autowired
	private AppLoginServiceImpl appLoginService;
	@Autowired
	RequestMappingHandlerMapping requestMappingHandlerMapping;
	
	@ResponseBody
	@RequestMapping(value = "api/allUrl.json")
	public Object test(HttpServletRequest request, HttpServletResponse response) {
		
		logger.info("获取链接,version={}", AppUtils.getAppVersion());
		JSONObject result = new JSONObject();
		try {
			Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping
				.getHandlerMethods();
			JSONArray data = new JSONArray();
			for (RequestMappingInfo info : map.keySet()) {
				System.out.println(info.getPatternsCondition().toString() + ","
									+ map.get(info).getBean().toString());
				for (String u : info.getPatternsCondition().getPatterns()) {
					data.add(u);
				}
			}
			toJSONResult(result, AppResultCodeEnum.SUCCESS, "");
			result.put("dataList", data);
			result.put("count", data.size());
		} catch (Exception e) {
			logger.error("获取链接失败: ", e);
			toJSONResult(result, AppResultCodeEnum.FAILED, "failed");
		}
		logger.info("获取链接={}", result);
		return result;
	}
	
	@ResponseBody
	@RequestMapping("login.json")
	public JSONObject login(HttpServletRequest request, HttpServletResponse response, Model model,
							AppLoginOrder loginOrder, String deviceNo) {
		logger.info("app登录，入参={}", ReflectUtils.toString(loginOrder));
		JSONObject result = new JSONObject();
		try {
			LoginResult loginResult = null;
			//参数校验
			validateOrder(loginOrder);
			loginOrder.setLoginIp(IPUtil.getIpAddr(request));
			loginOrder.setAgent(request.getHeader("User-Agent"));
			//根据密码或者token登录
			if (StringUtils.isNotBlank(loginOrder.getPassword())) {
				//密码登录
				loginResult = appLoginService.login(loginOrder);
			} else if (StringUtils.isNotBlank(loginOrder.getToken())) {
				//token自动登录
				loginResult = appLoginService.autoLogin(loginOrder);
			} else {
				throw new BornApiException("请输入登录凭证");
			}
			if (loginResult.isSuccess()) {
				toJSONResult(result, AppResultCodeEnum.SUCCESS, "登录成功");
				SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
				JSONArray orgList = new JSONArray();
				result.put("orgList", orgList);
				result.put("hasDept", false);
				if (sessionLocal != null && sessionLocal.getUserDetailInfo() != null
					&& ListUtil.isNotEmpty(sessionLocal.getUserDetailInfo().getOrgList())) {
					result.put("hasDept", true);
					Org curOrg = getLocalSession().getUserDetailInfo().getPrimaryOrg();
					for (Org org : sessionLocal.getUserDetailInfo().getOrgList()) {
						JSONObject e = new JSONObject();
						orgList.add(e);
						e.put("orgId", org.getId());
						e.put("name", org.getName());
						if (curOrg != null && curOrg.getId() == org.getId()) {
							e.put("isCur", true);
						} else {
							e.put("isCur", false);
						}
					}
				} else {
					//版本号检查
					int version = getVersion(request);
					if (version <= 4) {
						throw new BornApiException("当前版本暂不支持该用户" + loginOrder.getUserName() + "登录");
					}
				}
				result.put("userId", ShiroSessionUtils.getSessionLocal().getUserId());
			} else if (loginResult.getFcsResultEnum() == FcsResultEnum.HAVE_NOT_DATA) {
				toJSONResult(result, AppResultCodeEnum.FAILED, "用户不存在");
			} else if (loginResult.getFcsResultEnum() == FcsResultEnum.USER_DISABLE) {
				toJSONResult(result, AppResultCodeEnum.FAILED, "该用户不可用");
			} else if (FcsResultEnum.FUNCTION_NOT_OPEN == loginResult.getFcsResultEnum()) {
				UserDetailsServiceProxy serviceProxy = new UserDetailsServiceProxy(
					propertyConfigService.getBmpServiceUserDetailsService());
				SysUser userInfo = serviceProxy.loadUserByUsername(loginOrder.getUserName());
				request.getSession().setAttribute(SessionConstant.SESSSION_KEY_FIRST_ACTIVE_USER,
					userInfo);
				request.getSession().setAttribute(SessionConstant.SESSSION_KEY_FIRST_ACTIVE_STATUS,
					CheckStatusEnum.NOT_APPLY);
				toJSONResult(result, AppResultCodeEnum.SUCCESS, "用户未激活");
			} else {
				toJSONResult(result, AppResultCodeEnum.FAILED, StringUtils.defaultString(
					loginResult.getMessage(), loginResult.getFcsResultEnum().message()));
			}
			result.put("userStatus", loginResult.getFcsResultEnum().code());
		} catch (Exception e) {
			logger.error("登录失败: ", e);
			toJSONResult(result, AppResultCodeEnum.FAILED, e.getMessage());
		}
		logger.info("app登录出参={}", result);
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value = "logout.json")
	public Object logout(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
		
		logger.info("app退出登录");
		Map<String, String> resultMap = new HashMap<>();
		try {
			appLoginService.loginOut(request, response);
			resultMap.put(CODE, AppResultCodeEnum.SUCCESS.code());
			resultMap.put(MESSAGE, "退出成功");
		} catch (Exception e) {
			logger.error("退出登录失败: ", e);
			resultMap.put(CODE, AppResultCodeEnum.FAILED.code());
			resultMap.put(MESSAGE, "退出登录失败:" + e.getMessage());
		}
		logger.info("app退出登录出参={}", resultMap);
		return resultMap;
	}
	
	/**
	 * 发送手机验证码
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("sendMobileCode.json")
	public Object sendMobileValidateCode(String userName, String mobile,
											HttpServletRequest request,
											HttpServletResponse response, ModelMap model)
																							throws IOException {
		logger.info("发送手机验证码,userName={}", userName);
		Map<String, String> resultMap = new HashMap<>();
		try {
			if (StringUtil.isBlank(userName)) {
				throw new BornApiException("用户名为空");
			}
			SysUser userInfo = null;
			try {
				UserDetailsServiceProxy serviceProxy = new UserDetailsServiceProxy(
					propertyConfigService.getBmpServiceUserDetailsService());
				userInfo = serviceProxy.loadUserByUsername(userName);
			} catch (Exception e) {
				throw new BornApiException("未找到用户");
			}
			if (userInfo == null) {
				throw new BornApiException("用户不存在");
			} else if (StringUtil.notEquals(userName, userInfo.getAccount())) {
				throw new BornApiException("发送验证码的账户与校验手机的账户不一致，请刷新页面重新操作!");
			}
			//调用发送短信服务
			String mobileNumber = StringUtils.defaultIfBlank(userInfo.getMobile(), mobile);
			Result result = mobileManager.sendMobileValidateCode(mobileNumber,
				FunctionalModulesEnum.ACCOUNT_ACTIVATION);
			if (result.isSuccess()) {
				resultMap.put(CODE, AppResultCodeEnum.SUCCESS.code());
				resultMap.put(MESSAGE, "发送成功");
				request.getSession().setAttribute(SessionConstant.SESSSION_KEY_FIRST_ACTIVE_STATUS,
					CheckStatusEnum.APPLYING);
				if (!Env.isOnline()) {
					Subject subject = SecurityUtils.getSubject();
					SessionMobileSend sessionMobileSend = (SessionMobileSend) subject.getSession()
						.getAttribute(SessionConstant.SESSION_VALIDATE_CODE);
					resultMap.put("smsCode", sessionMobileSend.getCode());
				}
			} else {
				resultMap.put(CODE, AppResultCodeEnum.FAILED.code());
				resultMap.put(MESSAGE, "发送失败");
			}
			
		} catch (Exception e) {
			logger.error("发送验证码失败：", e);
			resultMap.put(CODE, AppResultCodeEnum.FAILED.code());
			resultMap.put(MESSAGE, "发送失败: " + e.getMessage());
		}
		logger.info("发送手机验证码出参={}", resultMap);
		return resultMap;
	}
	
	/**
	 * 验证用户名和手机是否匹配，并验证改用户是否已激活
	 * @param userName
	 * @param mobile
	 * @return
	 */
	@ResponseBody
	@RequestMapping("checkUserAndMobile.json")
	public Object checkUserAndMobile(String userName, String mobile, HttpServletRequest request) {
		
		logger.info("app校验用户名和手机是否匹配，userName={},mobile={}", userName, mobile);
		Map<String, String> resultMap = new HashMap<>();
		try {
			if (StringUtils.isBlank(userName)) {
				throw new BornApiException("userName 为空");
			}
			if (StringUtils.isBlank(mobile)) {
				throw new BornApiException("mobile 为空");
			}
			
			UserDetailsServiceProxy serviceProxy = new UserDetailsServiceProxy(
				propertyConfigService.getBmpServiceUserDetailsService());
			SysUser userInfo = serviceProxy.loadUserByUsername(userName);
			if (StringUtil.isBlank(mobile) || StringUtil.notEquals(mobile, userInfo.getMobile())) {
				throw new BornApiException("您输入的手机号码和系统数据不匹配，请重新输入或联系管理员!");
			}
			if (UserStateEnum.INACTIVE.code().equals(userInfo.getUserStatus())) {
				throw new BornApiException("该账户不存在或未处于待激活状态!");
			}
			request.getSession().setAttribute(SessionConstant.SESSSION_KEY_FIRST_ACTIVE_USER,
				userInfo);
			CheckStatusEnum checkStatus = (CheckStatusEnum) request.getSession().getAttribute(
				SessionConstant.SESSSION_KEY_FIRST_ACTIVE_STATUS);
			if (checkStatus == null) {
				request.getSession().setAttribute(SessionConstant.SESSSION_KEY_FIRST_ACTIVE_STATUS,
					CheckStatusEnum.NOT_APPLY);
			}
			resultMap.put(CODE, AppResultCodeEnum.SUCCESS.code());
			resultMap.put(MESSAGE, "校验成功");
			
		} catch (Exception e) {
			logger.error("验证用户名、手机号是否匹配失败： ", e);
			resultMap.put(CODE, AppResultCodeEnum.FAILED.code());
			resultMap.put(MESSAGE, "校验失败： " + e.getMessage());
		}
		logger.info("app校验用户名和手机是否匹配，出参={}", resultMap);
		return resultMap;
	}
	
	/**
	 * 用户激活
	 * @param password
	 * @param smsCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("activeUser.json")
	public JSONObject activeUser(String userName, String password, String smsCode,
									HttpServletRequest request) {
		logger.info("app用户激活，userName={}", userName);
		JSONObject result = new JSONObject();
		String nPwd = null;
		try {
			//校验参数
			if (StringUtil.isBlank(userName)) {
				throw new BornApiException("用户名为空");
			}
			if (StringUtil.isBlank(password)) {
				throw new BornApiException("密码为空");
			} else {
				nPwd = decryptAes(password);
			}
			SysUser user = (SysUser) request.getSession().getAttribute(
				SessionConstant.SESSSION_KEY_FIRST_ACTIVE_USER);
			if (user == null) {
				throw new BornApiException("验证信息已过期，请刷新页面后重试!");
			}
			
			if (StringUtil.notEquals(userName, user.getAccount())) {
				throw new BornApiException("发送验证码的账户与校验手机的账户不一致，请刷新页面重新操作!");
			}
			// 判断验证码是否已校验通过
			CheckStatusEnum checkStatus = (CheckStatusEnum) request.getSession().getAttribute(
				SessionConstant.SESSSION_KEY_FIRST_ACTIVE_STATUS);
			if (CheckStatusEnum.CHECK_PASS != checkStatus) {
				throw new BornApiException("请先获取或校验验证码!");
			}
			
			//调用修改密码服务
			UserDetailsServiceProxy serviceProxy = new UserDetailsServiceProxy(
				propertyConfigService.getBmpServiceUserDetailsService());
			
			String str = serviceProxy.updatePwd(user.getAccount(), nPwd);
			logger.info("修改结果: " + str);
			// 忘记密码时无需登录
			AppLoginOrder loginOrder = new AppLoginOrder();
			loginOrder.setUserName(user.getAccount());
			loginOrder.setLoginIp(IPUtil.getIpAddr(request));
			loginOrder.setAgent(request.getHeader("User-Agent"));
			loginOrder.setPassword(password);
			loginOrder.setDeviceNo(request.getParameter("deviceNo"));
			loginOrder.setUmDeviceNo(request.getParameter("umDeviceNo;"));
			AppLoginResult loginResult = appLoginService.login(loginOrder);
			
			if (!loginResult.isSuccess()
				&& FcsResultEnum.FUNCTION_NOT_OPEN == loginResult.getFcsResultEnum()) {
				String key = "6IG0MVB5QOKRH4XPLE3FW8AJCZ79NTDUSY12";
				String actPwd = user.getAccount() + "active" + key;
				actPwd = MD5Util.getMD5_32(actPwd);
				String loginRes = serviceProxy.login(user.getAccount(), actPwd,
					loginOrder.getLoginIp(), loginOrder.getAgent());
				logger.info("登录结果: ", loginRes);
				result.put("n_token", loginResult.getToken());
				result.put(CODE, AppResultCodeEnum.SUCCESS.code());
				result.put(MESSAGE, "激活成功");
				toJSONResult(result, AppResultCodeEnum.SUCCESS, "激活成功");
				mobileManager.clearValidateCode();
			} else {
				toJSONResult(result, AppResultCodeEnum.FAILED, "激活失败");
			}
		} catch (Exception e) {
			logger.error("激活失败：", e);
			toJSONResult(result, AppResultCodeEnum.FAILED, "激活失败:" + e.getMessage());
		}
		logger.info("app激活出参={}", result);
		return result;
	}
	
	/**
	 * 忘记密码
	 * @param password
	 * @param checkKey
	 * @return
	 */
	@ResponseBody
	@RequestMapping("forgetPwd.json")
	public Object forgetPwd(String userName, String password, String checkKey,
							HttpServletRequest request) {
		logger.info("app找回密码");
		Map<String, String> resultMap = new HashMap<>();
		try {
			//校验参数
			if (StringUtil.isBlank(userName)) {
				throw new BornApiException("用户名为空");
			}
			if (StringUtil.isBlank(password)) {
				throw new BornApiException("密码为空");
			} else {
				password = decryptAes(password);
			}
			
			UserDetailsServiceProxy serviceProxy = new UserDetailsServiceProxy(
				propertyConfigService.getBmpServiceUserDetailsService());
			SysUser user = serviceProxy.loadUserByUsername(userName);
			if (user == null) {
				throw new BornApiException("用户不存在");
			}
			if (StringUtil.notEquals(userName, user.getAccount())) {
				throw new BornApiException("发送验证码的账户与校验手机的账户不一致，请刷新页面重新操作!");
			}
			// 判断验证码是否已校验通过
			CheckStatusEnum checkStatus = (CheckStatusEnum) request.getSession().getAttribute(
				SessionConstant.SESSSION_KEY_FIRST_ACTIVE_STATUS);
			if (CheckStatusEnum.CHECK_PASS != checkStatus) {
				throw new BornApiException("请先获取或校验验证码!");
			}
			
			//调用修改密码服务
			String str = serviceProxy.updatePwd(userName, password);
			logger.info("修改结果: " + str);
			resultMap.put(CODE, AppResultCodeEnum.SUCCESS.code());
			resultMap.put(MESSAGE, "密码找回成功");
		} catch (Exception e) {
			logger.error("密码找回失败：", e);
			resultMap.put(CODE, AppResultCodeEnum.FAILED.code());
			resultMap.put(MESSAGE, ("密码找回失败：") + e.getMessage());
		}
		logger.info("app找回密码出参={}", resultMap);
		return resultMap;
	}
	
	@ResponseBody
	@RequestMapping("modifyPassword.json")
	public Object modifyPassword(String oldPassword, String newPassword,
									HttpServletRequest request, HttpServletResponse response,
									Model model) {
		logger.info("app修改密码,入参={}");
		Map<String, String> resultMap = new HashMap<>();
		
		try {
			//密码解密
			oldPassword = decryptAes(oldPassword);
			newPassword = decryptAes(newPassword);
			//执行修改
			if (StringUtil.equals(oldPassword, ShiroSessionUtils.getSessionLocal().getPassword())) {
				UpdatePasswordOrder passwordOrder = new UpdatePasswordOrder();
				passwordOrder.setNewPassword(newPassword);
				passwordOrder.setUserName(ShiroSessionUtils.getSessionLocal().getUserName());
				FcsBaseResult result = passwordManagerService.updateUserPassword(passwordOrder);
				if (result.isSuccess()) {
					resultMap.put(CODE, AppResultCodeEnum.SUCCESS.code());
					resultMap.put(MESSAGE, "修改成功");
				} else {
					resultMap.put(CODE, AppResultCodeEnum.FAILED.code());
					resultMap.put(MESSAGE, result.getMessage());
				}
			} else {
				resultMap.put(CODE, AppResultCodeEnum.FAILED.code());
				resultMap.put(MESSAGE, "原密码输入不正确");
			}
			
		} catch (Exception e) {
			logger.error("密码修改失败：", e);
			resultMap.put(CODE, AppResultCodeEnum.FAILED.code());
			resultMap.put(MESSAGE, ("修改失败：") + e.getMessage());
		}
		logger.info("app修改密码出参={}", resultMap);
		return resultMap;
		
	}
	
	/**
	 * 校验手机验证码
	 * 
	 * @param request
	 * @param response
	 * @param modelMap
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("validateCode.json")
	public Object validateCode(String smsCode, HttpServletRequest request,
								HttpServletResponse response, ModelMap model) throws IOException {
		logger.info("校验手机验证码,入参={}", smsCode);
		Map<String, String> resultMap = new HashMap<>();
		try {
			CheckStatusEnum checkStatus = (CheckStatusEnum) request.getSession().getAttribute(
				SessionConstant.SESSSION_KEY_FIRST_ACTIVE_STATUS);
			if (CheckStatusEnum.APPLYING == checkStatus
				|| CheckStatusEnum.CHECK_NOT_PASS == checkStatus
				|| CheckStatusEnum.CHECK_PASS == checkStatus) {
				
				FcsBaseResult fcsBaseResult = mobileManager.equalValidateCode(smsCode,
					FunctionalModulesEnum.ACCOUNT_ACTIVATION, false);
				if (fcsBaseResult.isSuccess()) {
					request.getSession().setAttribute(
						SessionConstant.SESSSION_KEY_FIRST_ACTIVE_STATUS,
						CheckStatusEnum.CHECK_PASS);
					resultMap.put(CODE, AppResultCodeEnum.SUCCESS.code());
					resultMap.put(MESSAGE, "校验成功");
				} else {
					request.getSession().setAttribute(
						SessionConstant.SESSSION_KEY_FIRST_ACTIVE_STATUS,
						CheckStatusEnum.CHECK_NOT_PASS);
					resultMap.put(CODE, AppResultCodeEnum.FAILED.code());
					resultMap.put(MESSAGE, fcsBaseResult.getMessage());
				}
				
			} else {
				resultMap.put(CODE, AppResultCodeEnum.FAILED.code());
				resultMap.put(MESSAGE, "请先获取校验码");
				logger.error("未发起激活用户手机验证申请");
				request.getSession().setAttribute(SessionConstant.SESSSION_KEY_FIRST_ACTIVE_STATUS,
					CheckStatusEnum.NOT_APPLY);
			}
		} catch (Exception e) {
			logger.info("校验手机验证码失败: ", e);
			resultMap.put(CODE, AppResultCodeEnum.FAILED.code());
			resultMap.put(MESSAGE, "校验失败");
		}
		logger.info("app退出登录出参={}", resultMap);
		return resultMap;
	}
	
	private void validateOrder(AppLoginOrder loginOrder) {
		ValidateParamUtil.hasTextV2(loginOrder.getUserName(), "userName");
		ValidateParamUtil.hasTextV2(loginOrder.getDeviceNo(), "deviceNo");
	}
	
	public static void main(String[] args) {
		System.out.println(AesUtil.decrypt("M1K5YkFtGMSwvJG7glqFDg==", "0123456789abcdef"));
		
	}
}
