package com.bornsoft.web.app.main;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import rop.thirdparty.com.google.common.collect.Lists;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.born.bpm.service.client.user.SysUser;
import com.born.fcs.face.integration.bpm.service.WorkflowEngineWebClient;
import com.born.fcs.face.integration.bpm.service.info.BpmFinishTaskInfo;
import com.born.fcs.face.integration.bpm.service.order.TaskSearchOrder;
import com.born.fcs.face.integration.pm.service.app.AppAboutConfServiceClient;
import com.born.fcs.face.integration.session.SessionLocal;
import com.born.fcs.face.integration.util.ApiDigestUtil;
import com.born.fcs.face.integration.util.ShiroSessionUtils;
import com.born.fcs.fm.ws.enums.CostCategoryStatusEnum;
import com.born.fcs.fm.ws.info.payment.CostCategoryInfo;
import com.born.fcs.fm.ws.info.payment.FormExpenseApplicationDetailInfo;
import com.born.fcs.fm.ws.info.payment.FormExpenseApplicationInfo;
import com.born.fcs.fm.ws.info.payment.FormTravelExpenseInfo;
import com.born.fcs.fm.ws.order.payment.CostCategoryQueryOrder;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.ws.app.AppAboutConfInfo;
import com.born.fcs.pm.ws.enums.OneVoteResultEnum;
import com.born.fcs.pm.ws.enums.ProjectVoteResultEnum;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectInfo;
import com.born.fcs.pm.ws.order.bpm.WorkflowTaskInfo;
import com.born.fcs.pm.ws.order.common.FormQueryOrder;
import com.born.fcs.pm.ws.order.council.OneVoteDownOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.common.ProjectBatchResult;
import com.bornsoft.api.service.BornApiServiceEnum;
import com.bornsoft.api.service.app.DataPermissionUtil;
import com.bornsoft.api.service.app.JckFormService;
import com.bornsoft.integration.jck.config.SystemParamCacheHolder;
import com.bornsoft.utils.constants.ApiSystemParamEnum;
import com.bornsoft.utils.constants.BornApiConstants;
import com.bornsoft.utils.enums.AppResultCodeEnum;
import com.bornsoft.utils.exception.BornApiException;
import com.bornsoft.utils.tool.DateUtils;
import com.bornsoft.utils.tool.GsonUtil;
import com.bornsoft.utils.tool.ReflectUtils;
import com.bornsoft.utils.tool.SingleOrderUtil;
import com.bornsoft.web.app.base.WorkflowBaseController;
import com.bornsoft.web.app.main.AppMenuEnum.Menu;
import com.bornsoft.web.app.main.UserMainPageController.ViewType.APPViewEnum;
import com.bornsoft.web.app.project.council.CouncilController;
import com.bornsoft.web.app.system.SiteMessageController;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;

@Controller
@RequestMapping("userHome")
public class UserMainPageController extends WorkflowBaseController {
	
	@Autowired
	WorkflowEngineWebClient workflowEngineWebClient;
	@Autowired
	protected AppAboutConfServiceClient appAboutConfServiceClient;
	
	/******** url-view映射 ********/
	private static Map<String, ViewType> viewMap = new HashMap<>();
	/** 非主流用法,谔谔 **/
	@Autowired
	private CouncilController councilController;
	@Autowired
	private SiteMessageController siteMessageController;
	
	@Autowired
	private SystemParamCacheHolder	systemParamCacheHolder;
	
	@ResponseBody
	@RequestMapping("mainMsgCount.json")
	public JSONObject mainMsgCount(HttpServletRequest request, HttpServletResponse response,
									Model model) {
		JSONObject result = new JSONObject();
		try {
			JSONObject council = councilController.councilCount(true);
			if (StringUtil.equals(council.getString(CODE), AppResultCodeEnum.SUCCESS.code())) {
				result.put(AppMenuEnum.MeetingList.code(), council.getInteger(TOTAL));
			} else {
				throw new BornApiException("获取会议列表数量失败");
			}
			JSONObject task = taskCount(request, true);
			if (StringUtil.equals(task.getString(CODE), AppResultCodeEnum.SUCCESS.code())) {
				result.put(AppMenuEnum.ToDo.code(), task.getIntValue("wait"));
				result.put(AppMenuEnum.ToOnPc.code(), task.getIntValue("pc"));
			} else {
				throw new BornApiException("获取待办事项数量失败");
			}
			JSONObject message = siteMessageController.ajaxLoadUnReadData();
			if (StringUtil.equals(message.getString(CODE), AppResultCodeEnum.SUCCESS.code())) {
				result.put(AppMenuEnum.MessageList.code(), message.getIntValue(TOTAL));
			} else {
				throw new BornApiException("获取消息数量失败");
			}
			//跳转工作台
			/*int version = getVersion(request);
			if(version>=2){
				String ownUrl = systemParamCacheHolder.getConfig(ApiSystemParamEnum.OWN_API_URL);
				if(StringUtil.isBlank(ownUrl)){
					throw new BornApiException("未配置API访问地址");
				}else{
					ownUrl = ownUrl.substring(0, ownUrl.indexOf("gateway.html"));
					toWorkStation(request, response);
					result.put("workStation", ownUrl +"userHome/toWorkStation.html");
				}
			}else{
				String faceUrl = systemParamCacheHolder.getConfig(ApiSystemParamEnum.FACE_URL);
				if(StringUtil.isBlank(faceUrl)){
					throw new BornApiException("未配置face访问地址");
				}else{
					toWorkStation(request, response);
					result.put("workStation", faceUrl +"/userHome/mainIndex.htm?accessToken=" + getAccessToken());
				}
			}*/
			String faceUrl = systemParamCacheHolder.getConfig(ApiSystemParamEnum.FACE_URL);
			if (StringUtil.isBlank(faceUrl)) {
				throw new BornApiException("未配置face访问地址");
			} else {
				toWorkStation(request, response);
				result.put("workStation", faceUrl + "/userHome/mainIndex.htm?accessToken="
											+ getAccessToken());
			}
			
			try {
				//跳转风险监控
				String riskUrl = toRisk(getLoginName());
				result.put("riskUrl", riskUrl);
			} catch (Exception e) {
				logger.error("", e);
			}
			toJSONResult(result, AppResultCodeEnum.SUCCESS, "获取成功");
		} catch (Exception e) {
			logger.error("获取消息条数失败： ", e);
			toJSONResult(result, AppResultCodeEnum.FAILED, "获取消息条数失败");
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("createToken.json")
	public JSONObject createToken(HttpServletRequest request, HttpServletResponse response,
									Model model) {
		JSONObject result = new JSONObject();
		try {
			result.put("accessToken", getAccessToken());
			toJSONResult(result, AppResultCodeEnum.SUCCESS, "获取成功");
		} catch (Exception e) {
			logger.error("获取菜单列表失败： ", e);
			toJSONResult(result, AppResultCodeEnum.FAILED, "获取登陆令牌失败");
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("getMenus.json")
	public JSONObject getMenuList(HttpServletRequest request, HttpServletResponse response,
									Model model) {
		JSONObject result = new JSONObject();
		try {
			List<Menu> menuList = AppMenuEnum.getOwnMenus();
			JSONObject menus = new JSONObject();
			for (Menu m : menuList) {
				menus.put(m.getCode(), m.isAllowed() ? 1 : 0);
			}
			result.put("menus", menus);
			toJSONResult(result, AppResultCodeEnum.SUCCESS, "获取成功");
		} catch (Exception e) {
			logger.error("获取菜单列表失败： ", e);
			toJSONResult(result, AppResultCodeEnum.FAILED, "获取菜单列表失败");
		}
		return result;
	}
	
	static {
		//跳转至PC端
		viewMap.put("/projectMg/riskLevel/riskEdit.htm", ViewType.PC);
		viewMap.put("/projectMg/riskreview/edit.htm", ViewType.PC);
		viewMap.put("/projectMg/file/inputApply.htm", ViewType.PC);
		//		viewMap.put("/projectMg/fCapitalAppropriationApply/audit.htm", ViewType.PC);
		//		viewMap.put("/projectMg/chargeNotification/audit.htm", ViewType.PC);
		viewMap.put("/assetMg/assessCompanyApply/audit/chooseAssessCompany.htm", ViewType.PC);
		viewMap.put("/projectMg/afterwardsCheck/lawManagerAudit.htm", ViewType.PC);
		//资金划拨【财务应付岗确认】
		viewMap.put("/projectMg/fCapitalAppropriationApply/audit/financeAffirm.htm", ViewType.PC);
		//收费通知【财务部确认实收金额】
		viewMap.put("/projectMg/chargeNotification/affirmAudit.htm", ViewType.PC);
		//资金
		viewMap.put("/fundMg/travelExpense/audit/canModify.htm", ViewType.PC);
		viewMap.put("/fundMg/expenseApplication/audit/canModify.htm", ViewType.PC);
		//内部借款
		//		viewMap.put("/fundMg/innerLoan/innerLoanModify.htm", ViewType.PC);
		
		//		viewMap.put("/fundMg/travelExpense/audit.htm", ViewType.PC);
		//		viewMap.put("/fundMg/expenseApplication/audit.htm", ViewType.PC);
		//		viewMap.put("/fundMg/transfer/audit.htm", ViewType.PC);
		viewMap.put("/projectMg/financialProject/setUp/audit/chooseCouncil.htm", ViewType.PC);
		
		//指定处理人
		viewMap.put("/projectMg/setUp/audit/riskManager.htm", ViewType.POINT_RISK_MANAGE);
		viewMap.put("/projectMg/setUp/audit/busiManagerb.htm", ViewType.POINT_B);
		
		viewMap.put("/projectMg/setUp/audit/legalManager.htm", ViewType.POINT_LEGAL_SETUP_SS);
		
		viewMap.put("/projectMg/meetingMg/summary/audit/president.htm", ViewType.POINT_ROUTE_3);
		viewMap.put("/projectMg/brokerBusiness/audit/chooseLegalMan.htm",
			ViewType.POINT_LEGAL_SETUP_BROKER_BUSINESS);
		
		//签报相关
		viewMap.put("/projectMg/formChangeApply/audit/selManager.htm", ViewType.PC);
		viewMap.put("/projectMg/formChangeApply/audit.htm", ViewType.PC);
		viewMap.put("/projectMg/formChangeApply/audit/selPath.htm", ViewType.PC);
		viewMap.put("/projectMg/formChangeApply/edit.htm", ViewType.PC);
		
		//担保函
		viewMap.put("/projectMg/contract/editContract.htm", ViewType.PC);
		viewMap.put("/projectMg/contract/audit.htm", ViewType.PC);
		viewMap.put("/projectMg/contract/auditChooseLegalManager.htm", ViewType.PC);
		
		//文书/函/通知书审批
		viewMap.put("/projectMg/contract/audit/selPath.htm", ViewType.PC);
		
	}
	
	public static enum DataType {
		Project("project", "项目信息"),
		TravelExpense("travelExpense", "差旅费报销安"),
		ExpenseApply("expenseApply", "费用支付申请单"), ;
		private String code;
		private String message;
		
		private DataType(String code, String message) {
			this.code = code;
			this.message = message;
		}
		
		public String getCode() {
			return code;
		}
		
		public void setCode(String code) {
			this.code = code;
		}
		
		public String getMessage() {
			return message;
		}
		
		public void setMessage(String message) {
			this.message = message;
		}
		
	}
	
	public static enum ViewType {
		//通用C
		COMMON("1", "通用界面", APPViewEnum.COMMON),
		//指定P
		POINT_B("2", "指定B角", APPViewEnum.POINT),
		/****** 法务部负责人指定法务专员【合同申请】 **********/
		POINT_LEGAL_CONTRACT("3", "法务人员", APPViewEnum.POINT),
		/****** 法务部负责人指定法务专员【诉讼保函立项】 **********/
		POINT_LEGAL_SETUP_SS("31", "法务人员", APPViewEnum.POINT),
		/****** 法务部负责人指定法务专员【经纪业务流程】 **********/
		POINT_LEGAL_SETUP_BROKER_BUSINESS("32", "法务人员", APPViewEnum.POINT),
		//FIXME 这个怎么处理
		POINT_LEGAL_JINGBAN("33", "审批或者指派经办人", APPViewEnum.POINT),
		
		POINT_LEGAL_MANAGERE("4", "法务经理", APPViewEnum.POINT),
		POINT_RISK_MANAGE("5", "指派风险经理", APPViewEnum.POINT),
		
		POINT_ROUTE_3("10", "风控主任审核", APPViewEnum.POINT_ROUTE_3),
		
		//PC
		PC("6", "跳转至PC完成", APPViewEnum.PC),
		
		//指定执行路径R
		POINT_ROUTE_1("7", "指定执行路径：结束、相关领导审批、部门负责人审批或者指派经办人", APPViewEnum.POINT_ROUTE_1),
		POINT_ROUTE_2("8", "指定执行路径：法务部负责人审批、客户经理确认", APPViewEnum.POINT_ROUTE_2),
		ASSIGN("9", "转交待办", APPViewEnum.POINT),
		
		;
		/** 历史遗留，客户端不关心，只管回传，可以理解为对某种具体页面的唯一标志 */
		private String code;
		private String message;
		private APPViewEnum type;
		
		private ViewType(String code, String message, APPViewEnum type) {
			this.code = code;
			this.message = message;
			this.type = type;
		}
		
		public APPViewEnum getType() {
			return type;
		}
		
		public void setType(APPViewEnum type) {
			this.type = type;
		}
		
		public String getMessage() {
			return message;
		}
		
		public String getCode() {
			return code;
		}
		
		public void setCode(String code) {
			this.code = code;
		}
		
		public void setMessage(String message) {
			this.message = message;
		}
		
		/**
		 * 根据code获取对应的页面
		 * @param url
		 * @return
		 */
		public static ViewType getViewByCode(String code) {
			for (ViewType view : values()) {
				if (view.code.equals(code)) {
					return view;
				}
			}
			return null;
		}
		
		public static enum APPViewEnum {
			COMMON("1", "通用"),
			POINT("2", "指派人"),
			POINT_ROUTE_1("7", "指定执行路径：结束、相关领导审批、部门负责人审批或者指派经办人"),
			POINT_ROUTE_2("8", "指定执行路径：法务部负责人审批、客户经理确认"),
			POINT_ROUTE_3("10", "风控委主任审批"),
			PC("6", "跳转至PC"), ;
			private String code;
			private String message;
			
			private APPViewEnum(String code, String message) {
				this.code = code;
				this.message = message;
			}
			
			public String getCode() {
				return code;
			}
			
			public void setCode(String code) {
				this.code = code;
			}
			
			public String getMessage() {
				return message;
			}
			
			public void setMessage(String message) {
				this.message = message;
			}
			
		}
		
	}
	
	/**
	 * 处理任务
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("taskDistributor.json")
	public JSONObject taskDistributor(HttpServletRequest request, HttpServletResponse response) {
		JSONObject result = null;
		
		String faceUrl = sysParameterServiceClient.getSysParameterValue(ApiSystemParamEnum.FACE_URL
			.code());
		if (StringUtil.isBlank(faceUrl)) {
			throw new BornApiException("未配置face访问地址");
		}
		String taskId = request.getParameter("taskId");
		String processDefinitionId = request.getParameter("processDefinitionId");
		String processInstanceId = request.getParameter("processInstanceId");
		
		Map<String, Object> resultMap = new HashMap<>();
		FormQueryOrder formQueryOrder = new FormQueryOrder();
		formQueryOrder.setActDefId(request.getParameter("processDefinitionId"));
		formQueryOrder
			.setActInstId(NumberUtil.parseLong(request.getParameter("processInstanceId")));
		String module = "projectMg";
		String taskUrl = request.getParameter("taskUrl");
		if (StringUtil.isNotEmpty(taskUrl)) {
			String[] stringSplit = taskUrl.split("/");
			if (stringSplit.length > 0)
				module = stringSplit[1];
		}
		resultMap.put(JckFormService.MODULE, module);
		QueryBaseBatchResult<FormInfo> batchResult = getSystemMatchedFormServiceByModule(module)
			.queryPage(formQueryOrder);
		if (ListUtil.isNotEmpty(batchResult.getPageList())) {
			FormInfo formInfo = batchResult.getPageList().get(0);
			//界面类型
			ViewType view = getViewType(taskUrl, getVersion(request), null);
			
			resultMap.put("view", view.type.code);
			resultMap.put("viewCode", view.code);
			if (view != ViewType.PC) {
				//按钮初始化[可能会更改view]
				initWorkflow(resultMap, formInfo, taskId);
			}
			//签报数据
			if (ViewType.POINT_ROUTE_1.code.equals(resultMap.get("viewCode"))) {
				//公司领导们
				String ldDeptCode = sysParameterServiceClient
					.getSysParameterValue(SysParamEnum.COMPANY_LEADER_DEPT_CODE.code());
				if (StringUtil.isNotBlank(ldDeptCode)) {
					List<SysUser> leaders = bpmUserQueryService.findUserByDeptCode(ldDeptCode);
					resultMap.put("leaders", leaders);
				}
				//会签部门
				String signDepts = sysParameterServiceClient
					.getSysParameterValue(SysParamEnum.FORM_CHANGE_SIGN_DEPARTMENT.code());
				if (StringUtil.isNotEmpty(signDepts)) {
					signDepts = signDepts.replaceAll("\r", "").replaceAll("\n", "");
					String[] depts = signDepts.split(";");
					if (depts != null && depts.length > 0) {
						List<JSONObject> deptList = Lists.newArrayList();
						for (String dept : depts) {
							if (StringUtil.isBlank(dept))
								continue;
							String[] code_name = dept.split("_");
							if (code_name != null && code_name.length == 2) {
								JSONObject deptObj = new JSONObject();
								deptObj.put("deptCode", code_name[0].trim());
								deptObj.put("deptName", code_name[1]);
								deptList.add(deptObj);
							}
						}
						resultMap.put("deptList", deptList);
					}
				}
				
				String leaderTaskNodeId = sysParameterServiceClient
					.getSysParameterValue(SysParamEnum.FORM_CHANGE_LEADER_TASK_NODE_ID.code());
				resultMap.put("leaderTaskNodeId", leaderTaskNodeId);
				
				String deptTaskNodeId = sysParameterServiceClient
					.getSysParameterValue(SysParamEnum.FORM_CHANGE_SIGN_DEPARTMENT_TASK_NODE_ID
						.code());
				resultMap.put("deptTaskNodeId", deptTaskNodeId);
			}
			FCouncilSummaryInfo summaryInfo = null;
			Map<String, FCouncilSummaryProjectInfo> councilSummaryInfoProjectMap = new HashMap<String, FCouncilSummaryProjectInfo>();
			if (ViewType.POINT_ROUTE_3.code.equals(resultMap.get("viewCode"))) {
				summaryInfo = councilSummaryServiceClient.queryCouncilSummaryByFormId(formInfo
					.getFormId());
				resultMap.put("summaryId", summaryInfo.getSummaryId());
				resultMap.put("summaryInfo", summaryInfo);
				List<FCouncilSummaryProjectInfo> councilSummaryProjectInfos = councilSummaryServiceClient
					.queryProjectCsBySummaryId(summaryInfo.getSummaryId());
				for (FCouncilSummaryProjectInfo summaryProjectInfo : councilSummaryProjectInfos) {
					councilSummaryInfoProjectMap.put(summaryProjectInfo.getProjectCode(),
						summaryProjectInfo);
				}
			}
			//项目信息
			List<JSONObject> fprojectList = new ArrayList<>();
			String relatedProjectCode = formInfo.getRelatedProjectCode();
			if (StringUtil.isNotBlank(relatedProjectCode)) {
				String projectCode[] = relatedProjectCode.split(",");
				for (String p : projectCode) {
					if (StringUtil.isNotBlank(p)) {
						ProjectInfo fproject = projectServiceClient.queryByCode(p, false);
						//创建PC访问token
						String token = getAccessToken();
						if (fproject != null) {
							JSONObject project = new JSONObject();
							project.put("projectId", fproject.getProjectId());
							project.put("projectCode", fproject.getProjectCode());
							if (summaryInfo != null && councilSummaryInfoProjectMap.size() > 0) {
								FCouncilSummaryProjectInfo summaryProjectInfo = councilSummaryInfoProjectMap
									.get(fproject.getProjectCode());
								if (summaryProjectInfo != null) {
									project.put("spId", summaryProjectInfo.getSpId());
									project.put("voteResult", summaryProjectInfo.getVoteResult()
										.code());
									if (summaryProjectInfo.getOneVoteDown() != null) {
										project.put("oneVoteDown", summaryProjectInfo
											.getOneVoteDown().code());
									}
									
								}
								
							}
							project.put("projectName", fproject.getProjectName());
							project.put("customerName", fproject.getCustomerName());
							project.put("timeLimit", fproject.getTimeLimit());
							project.put("timeUnit", fproject.getTimeUnit() == null ? "" : fproject
								.getTimeUnit().code());
							project.put("busiTypeName", fproject.getBusiTypeName());
							project.put("amount", fproject.getAmount() == null ? "" : fproject
								.getAmount().toStandardString());
							String tmpUrl = faceUrl
											+ "/projectMg/viewProjectAllMessage.htm?projectCode="
											+ fproject.getProjectCode() + "&accessToken=" + token;
							project.put("detailUrl", tmpUrl);
							fprojectList.add(project);
						}
					}
				}
				
			}
			
			resultMap.put("projectList", fprojectList);
			resultMap.put("form", formInfo);
			
			//创建PC访问token
			
			String token = getAccessToken();
			String tmpUrl = faceUrl + "/userHome/taskDistributor.htm?taskId=" + taskId
							+ "&processInstanceId=" + processInstanceId + "&processDefinitionId="
							+ processDefinitionId + "&taskUrl=" + taskUrl + "&accessToken=" + token;
			resultMap.put("url", tmpUrl);
			//普通界面特殊类型处理
			handleSpecialCommon(getVersion(request), formInfo.getFormId(), taskUrl, resultMap);
			result = toJSONResult((JSONObject) GsonUtil.toJSONObject(resultMap),
				AppResultCodeEnum.SUCCESS, "");
			
		} else {
			logger.error("taskDistributor.htm调用结果", batchResult);
			result = toJSONResult(AppResultCodeEnum.FAILED, batchResult.getMessage());
		}
		logger.info("审核初始化,result={}", result);
		return result;
	}
	
	/**
	 * 最后的特殊处理
	 * @param formId
	 * @param taskUrl
	 * @param resultMap
	 */
	private void handleSpecialCommon(long version, long formId, String taskUrl,
										final Map<String, Object> resultMap) {
		
		JSONObject fundInfo = null;
		resultMap.put("dataType", DataType.Project.code);
		
		if (taskUrl.contains("/fundMg/travelExpense")) {
			//查询差旅费报销单
			FormTravelExpenseInfo info = travelExpenseServiceClient.queryByFormId(formId);
			if (info != null) {
				//差旅费报销单
				resultMap.put("dataType", DataType.TravelExpense.code);
				fundInfo = new JSONObject();
				fundInfo.put("deptName", info.getFormDeptName());
				fundInfo.put("agent", info.getFormUserName());
				fundInfo.put("applicationTime",
					DateUtils.toString(info.getApplicationTime(), DateUtils.PATTERN1));
				fundInfo.put("amount", info.getAmount().toStandardString());
				fundInfo.put("reasons", info.getReasons());
				resultMap.put("dataInfo", fundInfo);
			}
		} else if (taskUrl.contains("/fundMg/expenseApplication")) {
			//查询费用支付申请单
			FormExpenseApplicationInfo info = expenseApplicationServiceClient.queryByFormId(formId);
			if (info != null) {
				//费用支付申请单
				resultMap.put("dataType", DataType.ExpenseApply.code);
				fundInfo = new JSONObject();
				fundInfo.put("deptName", info.getFormDeptName());
				fundInfo.put("agent", info.getFormUserName());
				fundInfo.put("applicationTime",
					DateUtils.toString(info.getApplicationTime(), DateUtils.PATTERN1));
				JSONArray detailList = new JSONArray();
				fundInfo.put("detailList", detailList);
				fundInfo.put("amount", info.getAmount().toStandardString());
				fundInfo.put("reasons", info.getReimburseReason());
				
				List<CostCategoryInfo> costList = queryCostTypeList();
				if (info.getDetailList() != null && costList != null) {
					for (FormExpenseApplicationDetailInfo detail : info.getDetailList()) {
						JSONObject detailInfo = new JSONObject();
						detailList.add(detailInfo);
						detailInfo.put("feeType", getCostName(detail.getExpenseType(), costList));
						detailInfo.put("amount", detail.getAmount().toStandardString());
						detailInfo.put("deptName", detail.getDeptName());
					}
				}
				resultMap.put("dataInfo", fundInfo);
			}
			
		}
		if (version >= 8 && fundInfo != null) {
			resultMap.remove("projectList");
		}
		
	}
	
	private String getCostName(String expenseType, List<CostCategoryInfo> costList) {
		for (CostCategoryInfo info : costList) {
			if (StringUtils.equals(String.valueOf(info.getCategoryId()), expenseType)) {
				return info.getName();
			}
		}
		return "";
	}
	
	/**
	 * 
	 * @return
	 */
	private List<CostCategoryInfo> queryCostTypeList() {
		CostCategoryQueryOrder order = new CostCategoryQueryOrder();
		order.setStatusList(new ArrayList<CostCategoryStatusEnum>());
		order.getStatusList().add(CostCategoryStatusEnum.NORMAL);
		order.setPageSize(10000);
		QueryBaseBatchResult<CostCategoryInfo> batchResult = costCategoryServiceClient
			.queryPage(order);
		return batchResult.getPageList();
	}
	
	/**
	 * 获取界面类型
	 * @param url
	 * @param info
	 * @return
	 */
	private ViewType getViewType(String url, int version, WorkflowTaskInfo nodeInfo) {
		ViewType view = null;
		String tmp = null;
		if (StringUtil.isNotBlank(url)) {
			tmp = url.toLowerCase();
			int i = tmp.lastIndexOf("/");
			if (i > 0) {
				tmp = tmp.substring(i, tmp.length());
			}
			
		}
		if (StringUtil.isBlank(tmp) || StringUtil.contains(tmp.toLowerCase(), "edit")
			|| tmp.endsWith("modify.htm")) {
			view = ViewType.PC;
		} else if (!StringUtil.contains(url, "projectMg") && version < BornApiConstants.MinVersion) {
			view = ViewType.PC;
		} else {
			view = viewMap.get(url);
			if (view == null) {
				view = ViewType.COMMON;
			}
		}
		if (nodeInfo != null && StringUtil.contains(nodeInfo.getName(), "发起人提交")) {
			view = ViewType.PC;
		}
		if (view == ViewType.POINT_LEGAL_JINGBAN) {
			view = ViewType.PC;
		}
		String  pcAuditUrls = systemParamCacheHolder.getConfig(ApiSystemParamEnum.PC_AUDIT_URLS);
		if(StringUtils.isNotBlank(pcAuditUrls)){
			if(Arrays.asList(pcAuditUrls.split(";")).contains(url)){
				view = ViewType.PC;
			}
		}
		return view;
	}
	
	@ResponseBody
	@RequestMapping("taskCount.json")
	public JSONObject taskCount(HttpServletRequest request, boolean justTotal) {
		JSONObject result = new JSONObject();
		try {
			if (!justTotal) {
				int done = taskList(new TaskSearchOrder(), QueryType.DONE.code, true, request)
					.getJSONObject("page").getInteger("totalCount");
				result.put("done", done);
			}
			JSONObject pcPage = taskList(new TaskSearchOrder(), QueryType.PC.code, true, request)
				.getJSONObject("page");
			int pc = pcPage.getInteger("totalCount");
			result.put("pc", pc);
			int wait = pcPage.getIntValue("oldTotalCount") - pc;
			result.put("wait", wait);
			
			result.put(TOTAL, wait + pc);
			toJSONResult(result, AppResultCodeEnum.SUCCESS, "");
		} catch (Exception e) {
			logger.error("统计个数失败:", e);
			toJSONResult(result, AppResultCodeEnum.FAILED, "查询任务个数失败");
		}
		return result;
	}
	
	private static enum QueryType {
		DONE("done", "已办"),
		WAIT("wait", "待办"),
		PC("pc", "PC待办");
		String code;
		
		private QueryType(String code, String msg) {
			this.code = code;
		}
	}
	
	@ResponseBody
	@RequestMapping("taskList.json")
	public JSONObject taskList(TaskSearchOrder taskSearchOrder, String queryType,
								Boolean justCount, HttpServletRequest request) {
		logger.info("查询待办任务，入参={}", ReflectUtils.toString(taskSearchOrder));
		JSONObject result = null;
		try {
			if (taskSearchOrder == null)
				taskSearchOrder = new TaskSearchOrder();
			
			if (QueryType.DONE.code.equals(queryType)) {
				taskSearchOrder.setSubject(taskSearchOrder.getBillNo());
				taskSearchOrder.setBillNo("");
				taskSearchOrder.setUserId(ShiroSessionUtils.getSessionLocal().getUserId());
				QueryBaseBatchResult<BpmFinishTaskInfo> bpmFinishTask = workflowEngineWebClient
					.getFinishTask(taskSearchOrder);
				result = toJSONResult(AppResultCodeEnum.SUCCESS, "查询成功");
				JSONObject page = makePage(bpmFinishTask);
				if (justCount != Boolean.TRUE) {
					JSONArray dataList = new JSONArray();
					page.put("result", dataList);
					for (BpmFinishTaskInfo info : bpmFinishTask.getPageList()) {
						JSONObject entity = new JSONObject();
						dataList.add(entity);
						entity.put("subject",
							StringUtil.defaultIfBlank(info.getSubjectView(), info.getSubject()));
						entity.put("processName", info.getProcessName());
						entity.put("createTime",
							DateUtils.toString(info.getCreatetime(), DateUtils.PATTERN2));
						entity.put("createDateStr",
							DateUtils.toString(info.getCreatetime(), DateUtils.PATTERN2));
						entity.put("creator", info.getCreator());
					}
				}
				result.put("page", page);
			} else if (StringUtil.isBlank(queryType) || StringUtil.equals(queryType, "其他")
						|| QueryType.WAIT.code.equals(queryType)) {
				//郁闷啊,内存里面分页
				int pageNo = (int) (taskSearchOrder.getPageNumber() > 0 ? taskSearchOrder
					.getPageNumber() : 1);
				int pageSize = (int) (taskSearchOrder.getPageSize() > 0 ? taskSearchOrder
					.getPageSize() : 10);
				
				taskSearchOrder.setUserName(ShiroSessionUtils.getSessionLocal().getUserName());
				taskSearchOrder.setPageNumber(1);
				taskSearchOrder.setPageSize(999);
				QueryBaseBatchResult<WorkflowTaskInfo> baseBatchResult = workflowEngineWebClient
					.getTasksByAccount(taskSearchOrder);
				//筛选非PC待办事项,时间复杂度！！？
				List<WorkflowTaskInfo> tmpList = new ArrayList<WorkflowTaskInfo>();
				for (WorkflowTaskInfo info : baseBatchResult.getPageList()) {
					ViewType viewType = getViewType(info.getTaskUrl(), getVersion(request), info);
					if (viewType == null || (viewType != null && viewType.type != APPViewEnum.PC)) {
						tmpList.add(info);
					}
				}
				result = createPage(pageNo, pageSize, tmpList, justCount);
				result.getJSONObject("page").put("oldTotalCount", baseBatchResult.getTotalCount());
			} else if (QueryType.PC.code.equals(queryType)) {
				//郁闷啊,内存里面分页
				int pageNo = (int) (taskSearchOrder.getPageNumber() > 0 ? taskSearchOrder
					.getPageNumber() : 1);
				int pageSize = (int) (taskSearchOrder.getPageSize() > 0 ? taskSearchOrder
					.getPageSize() : 10);
				
				taskSearchOrder.setUserName(ShiroSessionUtils.getSessionLocal().getUserName());
				taskSearchOrder.setPageNumber(1);
				taskSearchOrder.setPageSize(999);
				QueryBaseBatchResult<WorkflowTaskInfo> baseBatchResult = workflowEngineWebClient
					.getTasksByAccount(taskSearchOrder);
				//筛选PC待办事项,时间复杂度！！？
				List<WorkflowTaskInfo> tmpList = new ArrayList<WorkflowTaskInfo>();
				for (WorkflowTaskInfo info : baseBatchResult.getPageList()) {
					ViewType viewType = getViewType(info.getTaskUrl(), getVersion(request), info);
					if (viewType != null && viewType.type == APPViewEnum.PC) {
						tmpList.add(info);
					}
				}
				result = createPage(pageNo, pageSize, tmpList, justCount);
				result.getJSONObject("page").put("oldTotalCount", baseBatchResult.getTotalCount());
			}
			if (result == null) {
				result = toJSONResult(AppResultCodeEnum.SUCCESS, "");
			}
			result.put("isRiskSecretary", isRiskSecretary());
			
		} catch (Exception e) {
			logger.error("查询待办任务失败： ", e);
			result = toJSONResult(AppResultCodeEnum.FAILED, "查询");
		}
		logger.info("查询待办任务,出参={}", result);
		return result;
	}
	
	@ResponseBody
	@RequestMapping("queryChartData.json")
	public JSONObject queryChartData(String chartType) {
		JSONObject result = new JSONObject();
		try {
			JSONObject tmp = null;
			
			// 管理人员 公司领导 展示图表查询
			if (DataPermissionUtil.isSystemAdministrator() || DataPermissionUtil.isCompanyLeader()) {
				//项目总览
				JSONObject data = new JSONObject();
				if (StringUtil.isBlank(chartType) || "project".equals(chartType)) {
					JSONArray customArr = new JSONArray();
					data.put("project", customArr);
					ProjectBatchResult qResult = projectServiceClient.getMainCountMessage();
					if (qResult.isExecuted() && qResult.isSuccess()) {
						if (qResult.getProjectInfos() != null) {
							for (ProjectInfo info : qResult.getProjectInfos()) {
								tmp = new JSONObject();
								tmp.put("name", info.getPhases() != null ? info.getPhases()
									.message() : "--");
								tmp.put("count", info.getCount());
								customArr.add(tmp);
							}
						}
					}
				}
				//客户总览
				if (StringUtil.isBlank(chartType) || "custom".equals(chartType)) {
					JSONArray customArr = new JSONArray();
					data.put("custom", customArr);
					FcsBaseResult deptCustomerResult = customerServiceClient
						.statisticsCustomerDept();
					if (deptCustomerResult != null && deptCustomerResult.isSuccess()
						&& deptCustomerResult.getMessage() != null) {
						JSONObject json = JSONObject.parseObject(deptCustomerResult.getMessage());
						for (String k : json.keySet()) {
							tmp = new JSONObject();
							tmp.put("name", k);
							tmp.put("count", json.getString(k));
							customArr.add(tmp);
							
						}
					}
				}
				result.put("data", data);
				toJSONResult(result, AppResultCodeEnum.SUCCESS, "");
			} else {
				toJSONResult(result, AppResultCodeEnum.FAILED, "无权限");
			}
			
		} catch (Exception e) {
			logger.error("查询失败： ", e);
			result.clear();
			result = toJSONResult(AppResultCodeEnum.FAILED, e.getMessage());
		}
		return result;
	}
	
	@ResponseBody
	@RequestMapping("checkUpdate.json")
	public JSONObject checkUpdate(HttpServletRequest request, HttpServletResponse response) {
		logger.info("app检查版本更新..");
		JSONObject result = new JSONObject();
		try {
			AppAboutConfInfo appConfigInfo = appAboutConfServiceClient.get();
			if (appConfigInfo != null) {
				result.put("forceUpdateCode", appConfigInfo.getAndroidForceUpdate());
				result.put("optionalUpdateCode", appConfigInfo.getAndroidOptionUpdate());
				result.put("updateInfo", appConfigInfo.getAndroidChangeLog());
				result.put("updateUrl", appConfigInfo.getAndroidPath());
				result.put("updateUrl2", "http://www.cguarantee.com:8208/uploadfile/apk/jck.apk");
				toJSONResult(result, AppResultCodeEnum.SUCCESS, "");
			} else {
				toJSONResult(result, AppResultCodeEnum.FAILED, "未发现新版本");
			}
		} catch (Exception e) {
			logger.error("检查更新失败失败: ", e);
			toJSONResult(result, AppResultCodeEnum.FAILED, "获取更新信息失败");
		}
		logger.info("app检查版本更新出参={}", result);
		return result;
	}
	
	@RequestMapping("aboutUs")
	public ModelAndView aboutUs(HttpServletRequest request, HttpServletResponse response) {
		logger.info("app关于我们..");
		ModelAndView view = null;
		try {
			AppAboutConfInfo appConfigInfo = appAboutConfServiceClient.get();
			view = new ModelAndView("/aboutUs.vm");
			if (appConfigInfo != null) {
				view.addObject("CONTENT", appConfigInfo.getContent());
				view.addObject("ANDROID_URL", appConfigInfo.getAndroidPath());
				view.addObject("IOS_URL", appConfigInfo.getIosPath());
				
				view.addObject("ANDROID_VERSION", appConfigInfo.getAndroidVersion());
				view.addObject("IOS_VERSION", appConfigInfo.getIosVersion());
				//当前版本
				view.addObject("CUR_VERSION", request.getParameter("appVersion"));
				view.addObject("showBtn", request.getParameter("showBtn"));
			}
		} catch (Exception e) {
			logger.error("关于我们获取版本信息失败: ", e);
			view = new ModelAndView("/common_failure.vm");
		}
		logger.info("app关于我们..{}", view.getModelMap());
		return view;
	}
	
	@RequestMapping("toWorkStation")
	public ModelAndView toWorkStation(HttpServletRequest request, HttpServletResponse response) {
		logger.info("跳转至PC工作台..");
		ModelAndView view = null;
		try {
			String faceUrl = systemParamCacheHolder.getConfig(ApiSystemParamEnum.FACE_URL);
			if (StringUtil.isBlank(faceUrl)) {
				throw new BornApiException("未配置face访问地址");
			}
			RedirectView redirectView = new RedirectView(
				faceUrl + "/userHome/mainIndex.htm?accessToken=" + getAccessToken());
			redirectView.setEncodingScheme(request.getCharacterEncoding());
			view = new ModelAndView(redirectView, new HashMap<String, String>());
		} catch (Exception e) {
			logger.error("跳转至PC工作台失败: ", e);
			view = new ModelAndView("/common_failure.vm");
			view.addObject("description", e.getMessage());
		}
		return view;
	}
	
	@RequestMapping("toRisk")
	public ModelAndView toRisk(HttpServletRequest request, HttpServletResponse response) {
		logger.info("跳转至风险监控..");
		ModelAndView view = null;
		try {
			String riskUrl = toRisk(StringUtil.defaultIfBlank(request.getParameter("loginName"),
				"none"));
			if (StringUtil.isBlank(riskUrl)) {
				throw new BornApiException("未配置risk访问地址");
			} else {
				RedirectView redirectView = new RedirectView(riskUrl);
				redirectView.setEncodingScheme(request.getCharacterEncoding());
				view = new ModelAndView(redirectView, new HashMap<String, String>());
			}
		} catch (Exception e) {
			logger.error("跳转至风险监控失败: ", e);
			view = new ModelAndView("/common_failure.vm");
			view.addObject("description", "跳转风险监控失败：" + e.getMessage());
		}
		return view;
	}
	
	/**
	 * 初始化Map
	 * @return
	 */
	private Map<String, String> instanceMap() {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("orderNo", SingleOrderUtil.getInstance().createOrderNo());
		paramMap.put("partnerId", "3");
		paramMap.put("inputCharset", "utf-8");
		paramMap.put("signType", "MD5");
		return paramMap;
	}
	
	/**
	 * 跳转网页式请求 仅供于测试
	 * @param paramMap
	 */
	@SuppressWarnings("deprecation")
	private String toRisk(String loginName) {
		if (StringUtil.isBlank(loginName)) {
			throw new BornApiException("会话失效,请重新登陆");
		}
		Map<String, String> paramMap = instanceMap();
		//添加请求参数、
		paramMap.put("service", BornApiServiceEnum.LOGIN_RISK_SYSTEM.getCode());
		paramMap.put("operator", loginName);
		/** 放入签名 **/
		String url = systemParamCacheHolder.getConfig(ApiSystemParamEnum.OWN_API_URL);
		ApiDigestUtil.digest(paramMap, "be7c713d08194fac983036924586be09");
		String location = url + "?";
		if (paramMap != null) {
			for (Map.Entry<String, String> entry : paramMap.entrySet()) {
				location += entry.getKey() + "=" + URLEncoder.encode(entry.getValue()) + "&";
			}
		}
		location = location.substring(0, location.length() - 1);
		logger.info("跳转url={}", location);
		return location;
	}
	
	private JSONObject createPage(int pageNo, int pageSize, List<WorkflowTaskInfo> tmpList,
									Boolean justCount) {
		int startIndex = (pageNo - 1) * pageSize;
		JSONObject page = new JSONObject();
		page.put("totalCount", tmpList.size());
		page.put("totalPageCount", 1 + (tmpList.size() / pageSize));
		page.put("currentPageNo", pageNo);
		
		if (justCount != Boolean.TRUE) {
			JSONArray dataList = new JSONArray();
			page.put("result", dataList);
			
			for (int len = 0, i = startIndex; i < tmpList.size() && len < pageSize; i++, len++) {
				WorkflowTaskInfo info = tmpList.get(i);
				JSONObject entity = new JSONObject();
				entity.put("id", info.getId());
				entity.put("processInstanceId", info.getProcessInstanceId());
				entity.put("processDefinitionId", info.getProcessDefinitionId());
				entity.put("subject",
					StringUtil.defaultIfBlank(info.getSubject(), info.getSubjectView()));
				entity.put("createTime",
					DateUtils.toString(info.getCreateTime(), DateUtils.PATTERN2));
				entity.put("processName", info.getName());
				entity.put("creator", info.getCreator());
				entity.put("createDateStr", info.getCreateDateStr());
				entity.put("taskUrl", info.getTaskUrl());
				dataList.add(entity);
			}
		}
		JSONObject result = toJSONResult(AppResultCodeEnum.SUCCESS, "查询成功");
		result.put("page", page);
		return result;
	}
	
	@RequestMapping("checkOneVote.json")
	@ResponseBody
	public JSONObject checkOneVote(HttpServletRequest request) {
		JSONObject json = new JSONObject();
		long summaryId = NumberUtil.parseLong(request.getParameter("summaryId"));
		if (summaryId > 0) {
			//尽调附件管理 (项目编号：2017-0300-011-058)
			List<FCouncilSummaryProjectInfo> projects = councilSummaryServiceClient
				.queryProjectCsBySummaryId(summaryId);
			String message = null;
			if (ListUtil.isNotEmpty(projects)) {
				for (FCouncilSummaryProjectInfo project : projects) {
					if (project.getVoteResult() == ProjectVoteResultEnum.END_PASS
						&& project.getOneVoteDown() == null) {
						if (message == null) {
							message = project.getProjectName() + "(项目编号："
										+ project.getProjectCode() + ")";
						} else {
							message += "、" + project.getProjectName() + "(项目编号："
										+ project.getProjectCode() + ")";
						}
					}
				}
				if (message != null) {
					message += "，尚未发表意见";
					json = toJSONResult(json, AppResultCodeEnum.FAILED, message);
				} else {
					json = toJSONResult(json, AppResultCodeEnum.SUCCESS, "处理成功");
				}
			} else {
				json = toJSONResult(json, AppResultCodeEnum.FAILED, "检查是否发表意见出错[未查询到会议纪要项目]");
			}
		} else {
			json = toJSONResult(json, AppResultCodeEnum.FAILED, "检查是否发表意见出错[会议纪要ID未知]");
		}
		return json;
	}
	
	@RequestMapping("oneVoteDown.json")
	@ResponseBody
	public JSONObject oneVoteDown(OneVoteDownOrder order, HttpServletRequest request, Model model) {
		JSONObject jsonObject = new JSONObject();
		try {
			
			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
			
			if (sessionLocal == null) {
				jsonObject.put("success", false);
				jsonObject.put("code", false);
				jsonObject.put("message", "您未登陆或登陆已失效");
				return jsonObject;
			}
			order.setOneVoteResult(OneVoteResultEnum.getByCode(request
				.getParameter("oneVoteResult")));
			setSessionLocalInfo2Order(order);
			
			FcsBaseResult result = councilSummaryServiceClient.oneVoteDown(order);
			
			jsonObject = toJSONResult(jsonObject, result, "处理成功", null);
			jsonObject.put("tabId", order.getSpId());
		} catch (Exception e) {
			toJSONResult(jsonObject, AppResultCodeEnum.FAILED, "投票失败");
			logger.error("一票权执行出错", e);
		}
		
		return jsonObject;
	}
}
