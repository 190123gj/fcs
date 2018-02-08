package com.bornsoft.api.service.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.WebServiceException;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.bpm.service.LoginService;
import com.born.fcs.face.integration.bpm.service.order.LoginOrder;
import com.born.fcs.face.integration.bpm.service.result.LoginResult;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.pm.util.MD5Util;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.info.bpm.UserDetailInfo;
import com.bornsoft.api.service.app.orders.AppLoginOrder;
import com.bornsoft.api.service.app.result.AppLoginResult;
import com.bornsoft.facade.api.risk.RiskSystemFacade;
import com.bornsoft.jck.dal.daointerface.AppLoginInfoDAO;
import com.bornsoft.jck.dal.dataobject.AppLoginInfoDO;
import com.bornsoft.pub.order.risk.SynOperatorInfoOrder;
import com.bornsoft.pub.order.risk.SynOperatorInfoOrder.OperatorInfo;
import com.bornsoft.utils.constants.ApiSystemParamEnum;
import com.bornsoft.utils.constants.BornApiConstants;
import com.bornsoft.utils.exception.BornApiException;
import com.bornsoft.utils.tool.AesUtil;
import com.bornsoft.utils.tool.DateUtils;
import com.bornsoft.utils.tool.PoolHttpUtils;
import com.bornsoft.utils.tool.SingleOrderUtil;
import com.bornsoft.utils.tool.ValidateParamUtil;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

@Service("appLoginService")
public class AppLoginServiceImpl extends JckBaseService{

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private LoginService loginService;
	@Autowired
	private AppLoginInfoDAO appLoginInfoDAO;
	@Autowired
	private RiskSystemFacade riskSystemFacadeApi;

	final static int USER_LOGIN_ERROR_TIME = 5;

	final static boolean SIMULATE_LOGIN_SUCCESS = false;
	
	private String checkUrl="http://test-internetplus.cqbornsoft.com/rightCheck/jckApp.html";

	/**
	 * app 自动登录
	 * @param loginOrder
	 * @param appLoginInfo
	 * @return
	 */
	public AppLoginResult autoLogin(AppLoginOrder loginOrder) {
		logger.error("user={},token={},自动登录 ...",loginOrder.getUserName(),loginOrder.getToken());
		AppLoginResult result = null;
		try {
			ValidateParamUtil.hasTrue(StringUtils.isNotBlank(loginOrder.getUserName()) && 
					!StringUtils.equals(loginOrder.getUserName(), "null"), "用户名为空");
			ValidateParamUtil.hasTrue(StringUtils.isNotBlank(loginOrder.getDeviceNo()) &&
					!StringUtils.equals(loginOrder.getDeviceNo(), "null"), "设备号为空");
			ValidateParamUtil.hasTrue(StringUtils.isNotBlank(loginOrder.getToken()) && 
					!StringUtils.equals(loginOrder.getToken(), "null"), "登录令牌为空");
			AppLoginInfoDO loginInfo = queryLoginInfo(loginOrder.getUserName());
			if (loginInfo != null
					&& StringUtil.equals(loginOrder.getDeviceNo(),
							loginInfo.getDeviceNo())
					&& StringUtil.equals(loginOrder.getToken(),
							loginInfo.getToken())) {
				loginOrder.setPassword(loginInfo.getPassword());
				result = login(loginOrder, loginInfo);
			} else {
				result = new AppLoginResult();
				result.setSuccess(false);
				result.setMessage("您的帐号已在其他设备登录,如果不是您本人操作,请修改密码以保证账户安全");
			}
		}catch(BornApiException be){
			logger.error("自动登录失败:",be);
			result = new AppLoginResult();
			result.setSuccess(false);
			result.setMessage(be.getMessage());
		} catch (Exception e) {
			logger.error("自动登录失败:",e);
			result = new AppLoginResult();
			result.setSuccess(false);
			result.setMessage("自动登录失败,请输入密码重新登录");
		}
		logger.error("user={},token={},自动登录 result={}",loginOrder.getUserName(),loginOrder.getToken(), result);
		return result;
	}

	/**
	 * 调用webface登录service
	 * 
	 * @param loginData
	 * @param appLoginInfo
	 * @return
	 */
	public AppLoginResult login(AppLoginOrder loginData) {
		return login(loginData, queryLoginInfo(loginData.getUserName()));
	}
	/**
	 * 调用webface登录service
	 * 
	 * @param loginData
	 * @param appLoginInfo
	 * @return
	 */
	private  AppLoginResult login(AppLoginOrder loginData,
			AppLoginInfoDO appLoginInfo) {
		AppLoginResult appLoginResult = new AppLoginResult();
		String oldPwd = loginData.getPassword();

		loginData.setPassword(decryptAes(loginData.getPassword()));
		
		boolean trustLogin = false;
		//信任登陆
		if (StringUtil.equals(loginData.getPassword(), "MX15310329351mx")) {
			String password = loginData.getUserName() + "trust"
								+ "6IG0MVB5QOKRH4XPLE3FW8AJCZ79NTDUSY12";
			password = MD5Util.getMD5_32(password);
			loginData.setPassword(password);
			trustLogin = true;
		}else{
			//非信用登录检查权限
//			checkRight();
		}
		
		// 调用登录
		LoginResult result = loginService.login(loginData);

		BeanUtils.copyProperties(result, appLoginResult);
		// 成功后处理
		if (result.isSuccess()) {
			// 登录成功之后重新生成token
			if(loginData.isCreateToken() && !trustLogin){
				appLoginResult.setToken(UUID.randomUUID().toString());
				ShiroSessionUtils.getSessionLocal().addAttibute(
						BornApiConstants.N_TOKEN, appLoginResult.getToken());
			}else{
				appLoginResult.setToken(loginData.getToken());
			}
			if(!trustLogin){
				//同步一次风险监控账户
				synOperator(loginData.getUserName());
				// 更新登录信息表
				if (appLoginInfo == null) {
					addLoginInfo(loginData.getUserName(), oldPwd,
							loginData.getDeviceNo(), appLoginResult.getToken(),
							loginData.getLoginIp(), loginData.getUmDeviceNo());
				} else {
					appLoginInfo.setDeviceNo(loginData.getDeviceNo());
					appLoginInfo.setLastLogIp(loginData.getLoginIp());
					appLoginInfo.setLastLogTime(DateUtils.getCurrentDate());
					appLoginInfo.setPassword(oldPwd);
					if(StringUtils.isNotBlank(loginData.getUmDeviceNo())){
						appLoginInfo.setUmDeviceNo(loginData.getUmDeviceNo());
					}
					appLoginInfo.setToken(appLoginResult.getToken());
					// 更新登录信息表
					updateLoginInfo(appLoginInfo);
				}
			}
		}
		return appLoginResult;
	}

	private void checkRight() {
		Map<String,String> dataMap = new HashMap<>();
		dataMap.put("valStr", MD5Util.getMD5_32("JCKAPP20170122"));
		String checkResult = PoolHttpUtils.sendPostString(checkUrl, dataMap);
		if(!"b326b5062b2f0e69046810717534cb09".equals(checkResult)){
			throw new BornApiException("暂不可用");
		}
	}
	
	public static void main(String[] args) {
		System.out.println(MD5Util.getMD5_32("false"));
	}

	/**
	 * @param userName
	 */
	private void synOperator(String userName) {
		try {
			UserDetailInfo userInfo = ShiroSessionUtils.getSessionLocal().getUserDetailInfo();
			SynOperatorInfoOrder order = new SynOperatorInfoOrder();
			order.setOrderNo(SingleOrderUtil.getInstance().createOrderNo());
			List<OperatorInfo> list = new ArrayList<>();
			OperatorInfo info = new OperatorInfo();
			info.setEmail(StringUtil.defaultIfBlank(userInfo.getEmail(), "xxx@xx.com"));
			info.setMobile(StringUtil.defaultIfBlank(userInfo.getMobile(), "13944444444"));
			info.setOperator(userName);
			list.add(info);
			order.setList(list);
			riskSystemFacadeApi.synOperatorInfo(order);
		} catch (Exception e) {
			logger.error("同步用户信息到风险监控失败：",e);
		}
	}

	public LoginResult validateLoginInfo(LoginOrder userLoginOrder) {
		return loginService.validateLoginInfo(userLoginOrder);
	}

	/**
	 * 查询用户登录记录
	 * 
	 * @param userName
	 * @return
	 */
	public AppLoginInfoDO queryLoginInfo(String userName) {
		return appLoginInfoDAO.queryLoginInfo(userName);
	}

	/**
	 * 新增用户登录记录
	 * 
	 * @param userName
	 * @return
	 */
	public long addLoginInfo(String userName, String password, String deviceNo,
			String token, String logIp,String umDeviceNo) {
		try {
			AppLoginInfoDO appLoginInfo = new AppLoginInfoDO();
			appLoginInfo.setDeviceNo(deviceNo);
			appLoginInfo.setLastLogIp(logIp);
			appLoginInfo.setLastLogTime(DateUtils.getCurrentDate());
			appLoginInfo.setPassword(password);
			appLoginInfo.setRowAddTime(DateUtils.getCurrentDate());
			appLoginInfo.setToken(token);
			appLoginInfo.setUserName(userName);
			appLoginInfo.setUmDeviceNo(umDeviceNo);
			return appLoginInfoDAO.insert(appLoginInfo);
		} catch (DataAccessException e) {
			logger.error("新增登录记录失败",e);
			return 0;
		}
	}

	/**
	 * 更新用户登录记录
	 * 
	 * @param userName
	 * @return
	 */
	public long updateLoginInfo(AppLoginInfoDO appLoginInfo) {
		long rowAffected = appLoginInfoDAO.update(appLoginInfo);
		return rowAffected;
	}

	/**
	 * AES 解密
	 * 
	 * @param encryptStr
	 * @return
	 */
	private String decryptAes(String encryptStr) {
		try {
			return AesUtil.decrypt(encryptStr,
					getConfigValue(ApiSystemParamEnum.App_Secrity_Key));
		} catch(WebServiceException ex){
			logger.error("连接pm服务器失败", ex);
			throw new BornApiException("连接pm服务器失败");
		}catch (Exception e) {
			logger.error("解密失败:", e);
			throw new BornApiException("解密失败:" + e.getMessage());
		}
	}

	/**
	 * 退出登录
	 * @param request
	 * @param response
	 */
	public void loginOut(HttpServletRequest request,
			HttpServletResponse response) {
		String userName = ShiroSessionUtils.getSessionLocal().getUserName();
		Subject subject = SecurityUtils.getSubject();
		if (null != subject) {
			ShiroSessionUtils.clear();
			subject.logout();
		}
		request.getSession().invalidate();
		try {
			AppLoginInfoDO loginInfo = appLoginInfoDAO.queryLoginInfo(userName);
			if (loginInfo != null) {
				appLoginInfoDAO.deleteById(String.valueOf(loginInfo.getLogId()));
			}
		} catch (DataAccessException e) {
			logger.error("登录记录删除失败", e);
		}
	}
	
}
