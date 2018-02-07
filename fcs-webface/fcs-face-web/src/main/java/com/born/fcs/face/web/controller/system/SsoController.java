package com.born.fcs.face.web.controller.system;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import rop.thirdparty.com.google.common.collect.Lists;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.integration.bpm.service.LoginService;
import com.born.fcs.face.integration.bpm.service.WorkflowEngineWebClient;
import com.born.fcs.face.integration.bpm.service.order.LoginOrder;
import com.born.fcs.face.integration.bpm.service.order.TaskSearchOrder;
import com.born.fcs.face.integration.bpm.service.result.LoginResult;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.face.web.controller.base.BaseController;
import com.born.fcs.face.web.util.DataPermissionUtil;
import com.born.fcs.face.web.util.ViewShowUtil;
import com.born.fcs.face.web.util.WebUtil;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.MD5Util;
import com.born.fcs.pm.ws.enums.CompanyNameEnum;
import com.born.fcs.pm.ws.enums.CouncilStatusEnum;
import com.born.fcs.pm.ws.enums.CouncilTypeEnum;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.bpm.UserDetailInfo;
import com.born.fcs.pm.ws.info.council.CouncilInfo;
import com.born.fcs.pm.ws.info.user.UserExtraMessageInfo;
import com.born.fcs.pm.ws.order.bpm.WorkflowTaskInfo;
import com.born.fcs.pm.ws.order.council.CouncilQueryOrder;
import com.born.fcs.pm.ws.order.user.UserExtraMessageQueryOrder;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.ip.IPUtil;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;

@Controller
@RequestMapping("/sso")
public class SsoController extends BaseController {
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	WorkflowEngineWebClient workflowEngineWebClient;
	
	/**
	 * 返回登陆页面
	 * @param message
	 * @param model
	 * @return
	 */
	private String error(String message, Model model) {
		model.addAttribute("code", 0);
		model.addAttribute("message", message);
		return "forward:/login/toLogin.htm";
	}
	
	/**
	 * sso验证
	 * @param request
	 * @return
	 */
	private SsoCheckResult ssoCheck(HttpServletRequest request) {
		
		SsoCheckResult checkResult = new SsoCheckResult();
		
		//接入系统用户名
		String uname = request.getParameter("uname");
		//签名
		String sign = request.getParameter("sign");
		//时间戳
		String ts = request.getParameter("ts");
		//来源系统
		String from = request.getParameter("from");
		
		if (StringUtil.isBlank(uname) || StringUtil.isBlank(ts) || StringUtil.isBlank(sign)
			|| StringUtil.isBlank(from)) {
			checkResult.setSuccess(false);
			checkResult.setMessage("请求参数不完整");
		}
		//get secretKey
		String secrectKey = null;
		if (StringUtil.equalsIgnoreCase("OA", from)) { //OA
			secrectKey = sysParameterServiceClient
				.getSysParameterValue(SysParamEnum.SYS_PARAM_OA_ACCESS_SECRET.code());
		} else {
			checkResult.setSuccess(false);
			checkResult.setMessage("未知来源 " + from);
			return checkResult;
		}
		
		if (StringUtil.isBlank(secrectKey)) {
			checkResult.setSuccess(false);
			checkResult.setMessage("尚未配置密钥 [" + from + "]");
			return checkResult;
		}
		
		if ((new Date().getTime() - Long.parseLong(ts)) / (1000 * 60) > 1) {
			checkResult.setSuccess(false);
			checkResult.setMessage("签名已过期");
			return checkResult;
		}
		
		String localSign = MD5Util.getMD5_32(uname + secrectKey + ts);
		
		if (!StringUtil.equalsIgnoreCase(localSign, sign)) {
			checkResult.setSuccess(false);
			checkResult.setMessage("签名错误");
			return checkResult;
		}
		
		UserExtraMessageQueryOrder qOrder = new UserExtraMessageQueryOrder();
		qOrder.setPageSize(1);
		if (StringUtil.equalsIgnoreCase("OA", from)) { //OA
			qOrder.setOaSystemId(uname);
		} else {
			qOrder.setUserAccount(uname);
		}
		QueryBaseBatchResult<UserExtraMessageInfo> userInfo = userExtraMessageServiceClient
			.queryUserExtraMessage(qOrder);
		if (userInfo == null || userInfo.getTotalCount() == 0) {
			checkResult.setSuccess(false);
			checkResult.setMessage("用户[" + uname + "]不存在");
			return checkResult;
		}
		
		checkResult.setSuccess(true);
		checkResult.setMessage("验证成功");
		UserExtraMessageInfo localUser = userInfo.getPageList().get(0);
		checkResult.setLocalUser(localUser);
		
		return checkResult;
	}
	
	@RequestMapping("gateway.htm")
	public String login(HttpServletRequest request, HttpServletResponse response, Model model,
						String redirect) {
		try {
			
			redirect = checkRedirect(redirect);
			
			if (StringUtil.isBlank(redirect)) {
				redirect = "/userHome/mainIndex.htm";
			}
			
			SsoCheckResult checkResult = ssoCheck(request);
			
			logger.info("/sso/gateway.htm checkResult {} ", checkResult);
			
			if (!checkResult.isSuccess()) {
				return error(checkResult.getMessage(), model);
			}
			
			UserExtraMessageInfo localUser = checkResult.getLocalUser();
			
			//已经登陆的情况
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			if (sessionLocal != null
				&& StringUtil.equals(sessionLocal.getUserName(), localUser.getUserAccount())) {
				return sendUrl(response, redirect, null);
			}
			
			LoginOrder loginOrder = new LoginOrder();
			WebUtil.setPoPropertyByRequest(loginOrder, request);
			loginOrder.setUserName(localUser.getUserAccount());
			loginOrder.setLoginIp(IPUtil.getIpAddr(request));
			loginOrder.setAgent(request.getHeader("User-Agent"));
			
			//信任登陆
			String password = loginOrder.getUserName() + "trust"
								+ "6IG0MVB5QOKRH4XPLE3FW8AJCZ79NTDUSY12";
			password = MD5Util.getMD5_32(password);
			loginOrder.setPassword(password);
			
			LoginResult loginResult = loginService.login(loginOrder);
			
			if (loginResult.isSuccess()) {
				// 发送短信 信任登录的不发 系统参数配置为不发送时不发
				//			String sendSmsParam = sysParameterServiceClient
				//				.getSysParameterValue(SysParamEnum.SYS_PARAM_NOT_SEND_LOGIN_SMS_NAME.code());
				//			if (!BooleanEnum.YES.code().equals(sendSmsParam)) {
				//				try {
				//					SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
				//					UserDetailInfo userDetailInfo = sessionLocal.getUserDetailInfo();
				//					StringBuilder sb = new StringBuilder();
				//					sb.append("您的账户于 ");
				//					sb.append(new SimpleDateFormat("HH:mm:ss").format(new Date()));
				//					sb.append(" 成功登陆，如非本人操作，请联系管理员。");
				//					String smsContent = sb.toString();
				//					sMSServiceClient
				//						.sendSMSWithSwitch(userDetailInfo.getMobile(), smsContent, true);
				//				} catch (Exception e) {
				//					logger.error("发送登录短信失败：" + e.getMessage(), e);
				//				}
				//			}
				return sendUrl(response, redirect, null);
			} else if (loginResult.getFcsResultEnum() == FcsResultEnum.HAVE_NOT_DATA) {
				return error("用户不存在", model);
			} else if (loginResult.getFcsResultEnum() == FcsResultEnum.USER_DISABLE) {
				return error("此账户已锁定，请联系管理员。", model);
			} else if (loginResult.getFcsResultEnum() == FcsResultEnum.FUNCTION_NOT_OPEN) {
				return error("此账户尚未激活", model);
			} else {
				return error(loginResult.getMessage(), model);
			}
		} catch (Exception e) {
			logger.error("单点登录出错 {}", e);
			return error("单点登录出错", model);
		}
	}
	
	@RequestMapping("task.json")
	@ResponseBody
	public JSONObject task(HttpServletRequest request, HttpServletResponse response, Model model) {
		JSONObject result = new JSONObject();
		try {
			SsoCheckResult checkResult = ssoCheck(request);
			
			logger.info("/sso/task.json checkResult {} ", checkResult);
			
			if (!checkResult.isSuccess()) {
				result.put("success", "F");
				result.put("message", checkResult.getMessage());
				return result;
			}
			
			//查询
			UserExtraMessageInfo localUser = checkResult.getLocalUser();
			
			TaskSearchOrder taskSearchOrder = new TaskSearchOrder();
			taskSearchOrder.setUserName(localUser.getUserAccount());
			taskSearchOrder.setPageSize(1000);
			QueryBaseBatchResult<WorkflowTaskInfo> batchResult = workflowEngineWebClient
				.getTasksByAccount(taskSearchOrder);
			
			//业务代办
			JSONArray busiList = new JSONArray();
			//资金代办
			JSONArray fundList = new JSONArray();
			
			if (batchResult != null && ListUtil.isNotEmpty(batchResult.getPageList())) {
				for (WorkflowTaskInfo task : batchResult.getPageList()) {
					String taskUrl = task.getTaskUrl();
					String taskName = task.getSubjectView();
					if (taskUrl == null) {
						if (taskName.indexOf("差旅费报销") != -1 || taskName.indexOf("费用支付") != -1
							|| taskName.indexOf("劳资及税金") != -1) {
							fundList.add(convertTask2Json(task));
						} else {
							busiList.add(convertTask2Json(task));
						}
					} else {
						if (taskUrl.startsWith("/fundMg/expenseApplication")
							|| taskUrl.startsWith("/fundMg/travelExpense")
							|| taskUrl.startsWith("/fundMg/labourCapital")) {
							fundList.add(convertTask2Json(task));
						} else {
							busiList.add(convertTask2Json(task));
						}
					}
				}
			}
			
			result.put("success", "T");
			result.put("message", "查询成功");
			result.put("busiList", busiList);
			result.put("fundList", fundList);
			
		} catch (Exception e) {
			result.put("success", "F");
			result.put("message", "查询代办任务出错");
			result.put("busiList", new JSONArray());
			result.put("fundList", new JSONArray());
			logger.error("查询代办任务出错 {}", e);
		}
		return result;
	}
	
	@RequestMapping("council.json")
	@ResponseBody
	public JSONObject council(HttpServletRequest request, HttpServletResponse response, Model model) {
		JSONObject result = new JSONObject();
		try {
			SsoCheckResult checkResult = ssoCheck(request);
			
			logger.info("/sso/council.json checkResult {} ", checkResult);
			
			if (!checkResult.isSuccess()) {
				result.put("success", "F");
				result.put("message", checkResult.getMessage());
				return result;
			}
			
			//查询
			UserExtraMessageInfo localUser = checkResult.getLocalUser();
			
			UserDetailInfo userDetail = bpmUserQueryService.findUserDetailByUserId(localUser
				.getUserId());
			
			CouncilQueryOrder queryOrder = new CouncilQueryOrder();
			List<String> statuss = Lists.newArrayList();
			statuss.add(CouncilStatusEnum.NOT_BEGIN.code());
			statuss.add(CouncilStatusEnum.IN_PROGRESS.code());
			queryOrder.setStatuss(statuss);
			queryOrder.setPageSize(1000);
			
			List<String> councilTypeCodes = new ArrayList<>();
			List<String> companyNames = new ArrayList<String>();
			
			boolean isRiskSecretary = DataPermissionUtil.isRiskSecretary(userDetail);
			boolean isManagerSecretary = DataPermissionUtil.isManagerSecretary(userDetail);
			boolean isManagerSecretaryXH = DataPermissionUtil.isManagerSecretaryXH(userDetail);
			
			if (!DataPermissionUtil.isSystemAdministrator(userDetail)) {
				
				if (isRiskSecretary) {
					councilTypeCodes.add(CouncilTypeEnum.PROJECT_REVIEW.code());
					councilTypeCodes.add(CouncilTypeEnum.RISK_HANDLE.code());
					companyNames.add(CompanyNameEnum.NORMAL.code());
				}
				
				if (isManagerSecretary) {
					councilTypeCodes.add(CouncilTypeEnum.GM_WORKING.code());
					companyNames.add(CompanyNameEnum.NORMAL.code());
				}
				if (isManagerSecretaryXH) {
					councilTypeCodes.add(CouncilTypeEnum.GM_WORKING.code());
					companyNames.add(CompanyNameEnum.XINHUI.code());
				}
				queryOrder.setCompanyNames(companyNames);
				// 非风险经理/非总经理秘书 只能看与自己相关的项目
				if (!isRiskSecretary && !isManagerSecretary && !isManagerSecretaryXH) {
					queryOrder.setRelatveId(localUser.getUserId());
				}
				queryOrder.setCouncilTypeCodes(councilTypeCodes);
			}
			
			QueryBaseBatchResult<CouncilInfo> batchResult = councilServiceClient
				.queryCouncil(queryOrder);
			
			JSONArray councilList = new JSONArray();
			if (batchResult != null && ListUtil.isNotEmpty(batchResult.getPageList())) {
				ViewShowUtil viewShowUtil = new ViewShowUtil();
				for (CouncilInfo council : batchResult.getPageList()) {
					JSONObject json = new JSONObject();
					json.put("councilCode", council.getCouncilCode());
					json.put("councilType", council.getCouncilTypeCode().message());
					json.put("startTime", DateUtil.simpleFormat(council.getStartTime()));
					json.put("item", council.getProjectsName());
					json.put("status", council.getStatus().message());
					String councilUrl = "/projectMg/meetingMg/showCouncil.htm?councilId="
										+ council.getCouncilId();
					json.put("councilUrl", URLEncoder.encode(councilUrl, "UTF-8"));
					
					//只有评审会和风险处置会有投票
					if (council.getCouncilTypeCode() == CouncilTypeEnum.PROJECT_REVIEW
						|| council.getCouncilTypeCode() == CouncilTypeEnum.RISK_HANDLE) {
						
						String voteUrl = "/projectMg/meetingMg/allCouncilProjectList.htm?councilId="
											+ council.getCouncilId();
						
						if (isRiskSecretary
							|| viewShowUtil.findJusgeIn(council.getJudges(), userDetail.getId())) {
							//自身是评委才展示评委表决,风控委也可以看 评委表决
							if (council.getStatus() == CouncilStatusEnum.NOT_BEGIN) {
								json.put("voteUrl", URLEncoder.encode(voteUrl, "UTF-8"));
							} else if (!viewShowUtil.checkVoteAllDown(council.getProjects())) { //会议中的未投票完成的可以表决
								json.put("voteUrl", URLEncoder.encode(voteUrl, "UTF-8"));
							} else {
								json.put("voteUrl", "");
							}
						} else {
							json.put("voteUrl", "");
						}
						
					} else {
						json.put("voteUrl", "");
					}
					councilList.add(json);
				}
			}
			
			result.put("success", "T");
			result.put("message", "查询成功");
			result.put("councilList", councilList);
			
		} catch (Exception e) {
			result.put("success", "F");
			result.put("message", "查询会议出错");
			result.put("councilList", new JSONArray());
			logger.error("查询会议出错 {}", e);
		}
		return result;
	}
	
	private JSONObject convertTask2Json(WorkflowTaskInfo task) throws UnsupportedEncodingException {
		JSONObject json = new JSONObject();
		json.put("taskName",
			StringUtil.isBlank(task.getSubjectView()) ? task.getSubject() : task.getSubjectView());
		json.put("creator", task.getCreator());
		json.put("taskNode", task.getName());
		json.put("createTime", DateUtil.simpleFormat(task.getCreateTime()));
		json.put("durationTime", DateUtil.getDurationTime(task.getCreateTime()));
		String accessUrl = "/userHome/taskDistributor.htm?taskId=" + task.getId()
							+ "&processInstanceId=" + task.getProcessInstanceId()
							+ "&processDefinitionId=" + task.getProcessDefinitionId() + "&taskUrl="
							+ task.getTaskUrl();
		json.put("accessUrl", URLEncoder.encode(accessUrl, "UTF-8"));
		return json;
	}
	
	private String checkRedirect(String redirect) {
		if (redirect != null) {
			if (redirect.contains("http://") || redirect.contains("https://")) {
				return "";
			} else {
				return redirect;
			}
		}
		return redirect;
	}
	
	/**
	 * SSO验证结果
	 *
	 * @author wuzj
	 */
	class SsoCheckResult {
		
		boolean success;
		
		String message;
		
		UserExtraMessageInfo localUser;
		
		public boolean isSuccess() {
			return this.success;
		}
		
		public void setSuccess(boolean success) {
			this.success = success;
		}
		
		public String getMessage() {
			return this.message;
		}
		
		public void setMessage(String message) {
			this.message = message;
		}
		
		public UserExtraMessageInfo getLocalUser() {
			return this.localUser;
		}
		
		public void setLocalUser(UserExtraMessageInfo localUser) {
			this.localUser = localUser;
		}
		
		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
		}
	}
}
