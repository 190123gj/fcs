package com.bornsoft.api.service.app;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import rop.thirdparty.com.google.common.collect.Lists;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.born.bpm.service.client.user.SysOrg;
import com.born.fcs.face.integration.bpm.service.info.UserInfo;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ApiDigestUtil;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.pm.util.MoneyUtil;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.ws.base.QueryProjectBase;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CustomerTypeEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectRelatedUserTypeEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.born.fcs.pm.ws.info.bpm.Org;
import com.born.fcs.pm.ws.info.bpm.UserDetailInfo;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.order.common.ProjectQueryOrder;
import com.born.fcs.pm.ws.order.common.SimpleFormOrder;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.common.FormService;
import com.born.fcs.pm.ws.service.common.ProjectService;
import com.bornsoft.api.service.BornApiServiceEnum;
import com.bornsoft.api.service.app.util.WebUtil;
import com.bornsoft.integration.jck.config.SystemParamCacheHolder;
import com.bornsoft.utils.constants.ApiSystemParamEnum;
import com.bornsoft.utils.constants.BornApiConstants;
import com.bornsoft.utils.enums.AppResultCodeEnum;
import com.bornsoft.utils.exception.BornApiException;
import com.bornsoft.utils.tool.SingleOrderUtil;
import com.yjf.common.lang.result.ResultBase;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

/**
 * @Description: 进出口基础服务类
 * @author taibai@yiji.com
 * @date 2016-9-26 上午10:41:42
 * @version V1.0
 */
public class JckBaseService {
	
	private static final Logger logger = LoggerFactory.getLogger(JckBaseService.class);
	
	public static final String CODE = "code";
	public static final String MESSAGE = "message";
	
	@Autowired
	protected FormService formServiceClient; //PM 表单客户端
	@Autowired
	protected FormService formServiceFmClient; //FM 资金管理客户端
	@Autowired
	protected FormService formServiceAmClient; //AM 资产管理客户端
	@Autowired
	protected FormService formServiceCrmClient; //CRM 客户管理客户端
	@Autowired
	protected SystemParamCacheHolder systemParamCacheHolder;
	@Autowired
	protected ProjectService projectServiceClient;
	
	public static final String MODULE = "module";
	
	/**
	 * 切换部门
	 * @param orgId
	 * @return
	 */
	public boolean switchDept(long orgId) {
		try {
			logger.info("切换部门-->{}",orgId);
			boolean findedA = false;
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			if (sessionLocal == null || sessionLocal.getUserDetailInfo() == null) {
				throw new BornApiException("会话失效");
			}
			UserDetailInfo userDetailInfo = sessionLocal.getUserDetailInfo();
			if (ListUtil.isNotEmpty(userDetailInfo.getOrgList())) {
				Org primaryOrg = userDetailInfo.getPrimaryOrg();
				for (Org org : userDetailInfo.getOrgList()) {
					if (orgId == org.getId())
						primaryOrg = org;
				}
				userDetailInfo.setPrimaryOrg(primaryOrg);
				for (Org org : userDetailInfo.getOrgList()) {
					org.setPrimary(false);
					if (primaryOrg.getId() == org.getId()){
						findedA = true;
						org.setPrimary(true);
					}
				}
			}
			
			boolean findedB = false;
			//兼容老数据
			UserInfo userInfo = sessionLocal.getUserInfo();
			if (userInfo != null && userInfo.getDeptList() != null) {
				SysOrg primaryOrg = userInfo.getPrimaryOrg();
				for (SysOrg org : userInfo.getDeptList()) {
					if (orgId == org.getOrgId()){
						findedB = true;						
						primaryOrg = org;
					}
				}
				userInfo.setPrimaryOrg(primaryOrg);
			}
			return findedB && findedA;
		} catch (Exception e) {
			logger.error("切换部门失败",e);
			return false;
		}
	}
	
	/**
	 * 踢掉
	 * @param request
	 */
	public void kickOut(HttpServletRequest request){
		try {
			Subject subject = SecurityUtils.getSubject();
			if (null != subject) {
				ShiroSessionUtils.clear();
				subject.logout();
			}
			request.getSession().invalidate();
		} catch (Exception e) {
			logger.error("踢出用户失败",e);
		}
	}
	
	/**
	 * 返回和子系统匹配的表单客户端
	 * @param request
	 * @return
	 */
	public FormService getSystemMatchedFormServiceByModule(ModuleEnum module) {
		//默认PM 项目管理客户端
		FormService formService = formServiceClient;
		if (ModuleEnum.Fund==module) {//资金
			formService = formServiceFmClient;
		} else if (ModuleEnum.Asset == module) {//资产
			formService = formServiceAmClient;
		} else if (ModuleEnum.Report == module) { //报表
		
		} else if (ModuleEnum.Customer == module) { //客户管理
			formService = formServiceCrmClient;
		}
		
		return formService;
	}
	
	/**
	 * 将登陆的信息设置到Order
	 * @param sessionLocal
	 * @param order
	 */
	protected void setSessionLocalInfo2Order(SimpleFormOrder order) {
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal != null) {
			order.setUserId(sessionLocal.getUserId());
			order.setUserAccount(sessionLocal.getUserName());
			order.setUserName(sessionLocal.getRealName());
			order.setUserIp(sessionLocal.getRemoteAddr());

			UserInfo userInfo = sessionLocal.getUserInfo();
			if (userInfo != null) {
				order.setUserMobile(userInfo.getMoblie());
				order.setUserEmail(userInfo.getEmail());
				SysOrg dept = sessionLocal.getUserInfo().getDept();
				if (dept != null) {
					order.setDeptId(dept.getOrgId());
					order.setDeptCode(dept.getCode());
					order.setDeptName(dept.getOrgName());
					order.setDeptPath(dept.getPath());
					order.setDeptPathName(dept.getOrgPathname());
				}
			}
		}
	}

	/**
	 * APP返回结果构建
	 * @param result
	 * @param resultCode
	 * @param message
	 * @return
	 */
	public JSONObject toJSONResult(final JSONObject result, AppResultCodeEnum resultCode, String message) {
		result.put(CODE, resultCode.code());
		result.put(MESSAGE, message);
		setNewToken(result);
		return result;
	}
	/**
	 * APP返回结果构建
	 * @param json
	 * @param baseResult
	 * @param extraTrueMsg
	 * @param extraFalseMsg
	 * @return
	 */
	public JSONObject toJSONResult(JSONObject json, ResultBase baseResult,
			String extraTrueMsg, String extraFalseMsg) {
		if (baseResult.isSuccess()) {
			json.put(CODE, AppResultCodeEnum.SUCCESS.code());
			if (StringUtil.isNotEmpty(extraTrueMsg)) {
				json.put(MESSAGE, extraTrueMsg);
			} else {
				json.put(MESSAGE, baseResult.getMessage());
			}
			if (baseResult instanceof FormBaseResult) {
				FormInfo form = ((FormBaseResult) baseResult).getFormInfo();
				if (form != null)
					json.put("form", form.toJson());
			}
		} else {
			json.put(CODE, AppResultCodeEnum.FAILED.code());
			if (StringUtil.isNotEmpty(extraFalseMsg)) {
				json.put(MESSAGE, extraFalseMsg);
			} else {
				json.put(MESSAGE, baseResult.getMessage());
			}
		}
		setNewToken(json);
		return json;
	}


	public void setNewToken(JSONObject json) {
		SessionLocal local = ShiroSessionUtils.getSessionLocal();
		if(local!=null){
			Object token = local.removeObject(BornApiConstants.N_TOKEN);
			if (token != null && StringUtil.isNotBlank(token.toString())) {
				json.put(BornApiConstants.N_TOKEN, token);
			}
		} 
	}

	public JSONObject toJSONResult(AppResultCodeEnum resultCode, String message) {
		return toJSONResult(new JSONObject(), resultCode, message);
	}

	/**
	 * ResultBase 转换为 JSONObject Result
	 * @param baseResult
	 * @param extraTrueMsg
	 *            格外指定成功消息
	 * @param extraFalseMsg
	 *            格外指定失败消息
	 * @return
	 */
	public JSONObject toJSONResult(ResultBase baseResult,
			String extraTrueMsg, String extraFalseMsg) {
		return toJSONResult(new JSONObject(), baseResult, extraTrueMsg,
				extraFalseMsg);
	}

	/**
	 * ResultBase 转换为 JSONObject Result
	 * 
	 * @param baseResult
	 * @param extraTrueMsg
	 *            格外指定成功消息
	 * @param extraFalseMsg
	 *            格外指定失败消息
	 * @return
	 */
	public JSONObject toJSONResult(ResultBase baseResult) {
		return toJSONResult(new JSONObject(), baseResult,
				baseResult.getMessage(), baseResult.getMessage());
	}
	
	public String getConfigValue(ApiSystemParamEnum paramType) {
		return this.systemParamCacheHolder.getConfig(paramType);
	}
	
	/**
	 * 将登陆的信息设置到Order
	 * 
	 * @param order
	 */
	protected void setSessionLocalInfo2Order(QueryProjectBase order) {
		SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
		if (sessionLocal != null) {
			UserInfo userInfo = sessionLocal.getUserInfo();
			if (hasAllDataPermission()) {// 拥有所有数据权限
				order.setLoginUserId(0);
				order.setDeptIdList(null);
			} else if (hasPrincipalDataPermission() && userInfo != null) { // 拥有所负责的数据权限，按照部门维度查询数据
				order.setLoginUserId(0);
				order.setDeptIdList(userInfo.getDeptIdList());
			} else { // 按照人员维度查询数据
				order.setLoginUserId(sessionLocal.getUserId());
				order.setDeptIdList(null);
			}
		}
	}
	
	/**
	 * 是否拥有所有数据权限
	 * 
	 * @return
	 */
	protected boolean hasAllDataPermission() {
		return DataPermissionUtil.hasAllDataPermission();
	}

	/**
	 * 是拥有所负责数据权限
	 * 
	 * @return
	 */
	protected boolean hasPrincipalDataPermission() {
		return DataPermissionUtil.hasPrincipalDataPermission();
	}
	
	@ResponseBody
	@RequestMapping("queryProjects.json")
	public JSONObject queryProjects(HttpServletRequest request,ProjectQueryOrder queryOrder) {
		JSONObject result = new JSONObject();

		try {
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (StringUtil.equals("YES", request.getParameter("setAuthCondition"))) {
				setSessionLocalInfo2Order(queryOrder);
			}
			
			if (sessionLocal != null && DataPermissionUtil.isBusiManager()) {
				queryOrder.setBusiManagerId(sessionLocal.getUserId());
			}
			
			if (null != request.getParameter("busiManagerId")) {
				queryOrder.setBusiManagerId(NumberUtil.parseLong(request
					.getParameter("busiManagerId")));
			}
			
			if (null != request.getParameter("fromStamp")
				&& "IS".equals(request.getParameter("fromStamp"))) {//用印
				if (DataPermissionUtil.isRiskSecretary()) {//风控委秘书查所有项目
					queryOrder.setBusiManagerId(0);
				}
			}
			//风险处置
			if (StringUtil.equals("YES", request.getParameter("fromRiskCouncil"))) {
				queryOrder.setBusiManagerId(0);
				queryOrder.setDeptIdList(null);
				queryOrder.setLoginUserId(sessionLocal.getUserId());
				List<ProjectRelatedUserTypeEnum> relatedRoleList = Lists.newArrayList();
				relatedRoleList.add(ProjectRelatedUserTypeEnum.RISK_TEAM_MEMBER);
				queryOrder.setRelatedRoleList(relatedRoleList);
			}
			
			queryOrder.setPhases(ProjectPhasesEnum.getByCode(request.getParameter("phases")));
			queryOrder.setStatus(ProjectStatusEnum.getByCode(request.getParameter("status")));
			queryOrder.setPhasesStatus(ProjectPhasesStatusEnum.getByCode(request
				.getParameter("phasesStatus")));
			queryOrder.setCustomerType(CustomerTypeEnum.getByCode(request
				.getParameter("customerType")));
			queryOrder.setHasContract(BooleanEnum.getByCode(request.getParameter("hasContract")));
			queryOrder.setSortCol("p.raw_update_time");
			queryOrder.setSortOrder("DESC");
			String phasesStrList = request.getParameter("phasesList");
			if (StringUtil.isNotBlank(phasesStrList)) {
				List<ProjectPhasesEnum> phasesList = new ArrayList<>();
				String[] phases = phasesStrList.split(",");
				for (String s : phases) {
					ProjectPhasesEnum e = ProjectPhasesEnum.getByCode(s);
					if (null != e) {
						phasesList.add(e);
					}
				}
				queryOrder.setPhasesList(phasesList);
			}
			String phasesStatusStrList = request.getParameter("phasesStatusList");
			if (StringUtil.isNotBlank(phasesStatusStrList)) {
				List<ProjectPhasesStatusEnum> phasesStatusList = new ArrayList<>();
				String[] phases = phasesStatusStrList.split(",");
				for (String s : phases) {
					ProjectPhasesStatusEnum e = ProjectPhasesStatusEnum.getByCode(s);
					if (null != e) {
						phasesStatusList.add(e);
					}
				}
				queryOrder.setPhasesStatusList(phasesStatusList);
			}
			
			String busiTypes = request.getParameter("busiTypes");
			if (StringUtil.isNotBlank(busiTypes)) {
				List<String> busiTypeList = new ArrayList<>();
				String[] types = busiTypes.split(",");
				for (String s : types) {
					if (StringUtil.isNotEmpty(s)) {
						busiTypeList.add(s.trim());
					}
				}
				queryOrder.setBusiTypeList(busiTypeList);
			}
			
			JSONArray dataList = new JSONArray();
			JSONObject data = new JSONObject();
			QueryBaseBatchResult<ProjectInfo> batchResult = projectServiceClient
				.queryProject(queryOrder);
			if (ListUtil.isNotEmpty(batchResult.getPageList())) {
				for (ProjectInfo info : batchResult.getPageList()) {
					JSONObject json = new JSONObject();
					json.put("projectCode", info.getProjectCode());
					json.put("projectName", info.getProjectName());
					json.put("customerId", info.getCustomerId());
					json.put("customerName", info.getCustomerName());
					json.put("busiManagerId", info.getBusiManagerId());
					json.put("busiManagerName", info.getBusiManagerName());
					json.put("deptCode", info.getDeptCode());
					json.put("phases", info.getPhases().message());
					json.put("status", info.getStatus().message());
					json.put("spCode", info.getSpCode());
					json.put("timeLimit", info.getTimeUnit() == null ? "-" : info.getTimeLimit()
																				+ info
																					.getTimeUnit()
																					.viewName());
					json.put("institutionName", info.getIndustryName());
					
					json.put("amount", info.getAmount().toString());
					json.put("amountW",
						NumberUtil.format(MoneyUtil.getMoneyByw(info.getAmount()), "0.00").toString());
					json.put("busiType", info.getBusiType());
					json.put("busiTypeName", info.getBusiTypeName());
					//已代偿金额
					json.put("compAmount",
						info.getCompInterestAmount().add(info.getCompInterestAmount()).toString());
					dataList.add(json);
				}
			}
			data.put("totalPageCount", batchResult.getPageCount());
			data.put("currentPageNo", batchResult.getPageNumber());
			data.put("pageSize", batchResult.getPageSize());
			data.put("totalCount", batchResult.getTotalCount());
			data.put("result", dataList);
			result.put("page", data);
			toJSONResult(result, AppResultCodeEnum.SUCCESS, "");
		} catch (Exception e) {
			logger.error("查询项目列表失败：", e);
			toJSONResult(result, AppResultCodeEnum.FAILED, "查询项目列表失败");
		}
		return result;
	}
	
	@SuppressWarnings("deprecation")
	public String buildBornApiUrl(Map<String,String> paramMap, BornApiServiceEnum service){
		if(paramMap == null){
			paramMap = new HashMap<String, String>();
		}
		paramMap.put("orderNo", SingleOrderUtil.getInstance().createOrderNo());
		paramMap.put("partnerId", systemParamCacheHolder.getConfig(ApiSystemParamEnum.Born_Partner_Id));
		paramMap.put("inputCharset", "utf-8");
		paramMap.put("signType", "MD5"); 
		paramMap.put("service", service.getCode());
		//签名
		ApiDigestUtil.digest(paramMap, systemParamCacheHolder.getConfig(ApiSystemParamEnum.Born_Partner_Key));
		String url = systemParamCacheHolder.getConfig(ApiSystemParamEnum.OWN_API_URL);
		String location = url + "?";
		if (paramMap != null) {
			for (Map.Entry<String, String> entry : paramMap.entrySet()) {
				location += entry.getKey() + "=" + URLEncoder.encode(entry.getValue()) + "&";
			}
		}
		location = location.substring(0, location.length() - 1);
		logger.info("跳转url={}",location);
		return location;
	}
	
	/**
	 * 以JSON格式输出
	 * @param response
	 */
	public static void responseJson(ServletResponse response,JSONObject responseJSONObject) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.append(responseJSONObject.toString());
			logger.info("响应：" + responseJSONObject.toString());
		} catch (IOException e) {
			logger.debug("返回json失败",e);
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
	
	/**
	 * 判断APP接口调用结果
	 * @param result
	 * @return
	 */
	public static boolean isSuccess(JSONObject result){
		return result!=null && AppResultCodeEnum.SUCCESS.code().equals(result.getString(CODE));
	}
}
