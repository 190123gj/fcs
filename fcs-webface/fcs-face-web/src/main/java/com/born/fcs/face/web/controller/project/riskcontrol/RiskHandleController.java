package com.born.fcs.face.web.controller.project.riskcontrol;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.web.bean.ProjectBean;
import com.born.fcs.face.web.controller.base.WorkflowBaseController;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.face.web.util.WebUtil;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CommonAttachmentTypeEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.PlanTypeEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;
import com.born.fcs.pm.ws.enums.ProjectRelatedUserTypeEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.council.CouncilApplyRiskHandleInfo;
import com.born.fcs.pm.ws.info.council.FCouncilApplyCreditInfo;
import com.born.fcs.pm.ws.info.council.FCouncilApplyRiskHandleInfo;
import com.born.fcs.pm.ws.info.fund.ChargeRepayPlanDetailInfo;
import com.born.fcs.pm.ws.info.riskwarning.RiskWarningInfo;
import com.born.fcs.pm.ws.order.common.ProjectQueryOrder;
import com.born.fcs.pm.ws.order.council.CouncilApplyRiskHandleQueryOrder;
import com.born.fcs.pm.ws.order.council.FCouncilApplyCreditOrder;
import com.born.fcs.pm.ws.order.council.FCouncilApplyRiskHandleOrder;
import com.born.fcs.pm.ws.order.fund.ChargeRepayPlanDetailQueryOrder;
import com.born.fcs.pm.ws.order.riskwarning.RiskWarningQueryOrder;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;

/**
 * 风险处理上会申报
 * 
 * 
 * @author lirz
 * 
 * 2016-5-9 下午5:45:43
 */
@Controller
@RequestMapping("projectMg/riskHandle")
public class RiskHandleController extends WorkflowBaseController {
	
	private static final String VM_PATH = "/projectMg/riskControl/meetingReport/";
	
	@Override
	protected String[] getDateInputDayNameArray() {
		return new String[] { "repayDate" };
	}
	
	@Override
	protected String[] getMoneyInputNameArray() {
		return new String[] { "creditAmount", "repayAmount" };
	}
	
	/**
	 * 风险预警处理表
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("list.htm")
	public String list(HttpServletRequest request, Model model,
						CouncilApplyRiskHandleQueryOrder queryOrder) {
		
		try {
			setSessionLocalInfo2Order(queryOrder);
			QueryBaseBatchResult<CouncilApplyRiskHandleInfo> batchResult = councilApplyRiskHandleServiceClient
				.queryList(queryOrder);
			model.addAttribute("page", PageUtil.getCovertPage(batchResult));
			model.addAttribute("queryConditions", queryOrder);
		} catch (Exception e) {
			logger.error("查询出错");
		}
		
		return VM_PATH + "list.vm";
	}
	
	@RequestMapping("view.htm")
	public String view(@RequestParam(value = "formId", required = true) long formId,
						HttpServletRequest request, Model model) {
		
		try {
			FCouncilApplyRiskHandleInfo info = councilApplyRiskHandleServiceClient
				.findByFormId(formId);
			model.addAttribute("info", info);
			//附件列表
			queryCommonAttachmentData(model, info.getId() + "",
				CommonAttachmentTypeEnum.RISK_HANDLE);
			
			FormInfo formInfo = formServiceClient.findByFormId(formId);
			model.addAttribute("form", formInfo);
			
			model.addAttribute("riskWarningFid", queryRiskHandFormId(info.getCustomerName()));
			
			//审核记录
			setAuditHistory2Page(formInfo, model);	
			model.addAttribute("HookIsAudit", false);// 审核页面
		} catch (Exception e) {
			logger.error("查询出错");
		}
		
		return VM_PATH + "viewNotInsteadPay.vm";
	}
	
	@RequestMapping("edit.htm")
	public String edit(	@RequestParam(value = "formId", required = false, defaultValue = "0") long formId,
						HttpServletRequest request, Model model) {
		model.addAttribute("formId", formId);
		FCouncilApplyRiskHandleInfo info = null;
		
		if (formId > 0) {
			info = councilApplyRiskHandleServiceClient.findByFormId(formId);
			model.addAttribute("info", info);
			//附件列表
			queryCommonAttachmentData(model, info.getId() + "",
				CommonAttachmentTypeEnum.RISK_HANDLE);
			
			FormInfo form = formServiceClient.findByFormId(formId);
			model.addAttribute("form", form);
		} else {
			String customerName = request.getParameter("customerName");
			ProjectQueryOrder projectQueryOrder = new ProjectQueryOrder();
			long customerId = NumberUtil.parseLong(request.getParameter("customerId"));
			projectQueryOrder.setCustomerId(customerId);
			projectQueryOrder.setCustomerName(customerName);
			projectQueryOrder.setPageSize(999);
			QueryBaseBatchResult<ProjectInfo> batchResult = null;
			if (customerId > 0 || StringUtil.isNotBlank(customerName)) {
				projectQueryOrder.setIsApproval(BooleanEnum.IS);
				batchResult = projectServiceClient.queryProject(projectQueryOrder);
				
			}
			info = new FCouncilApplyRiskHandleInfo();
			info.setIsRepay(request.getParameter("isRepay"));
			if (batchResult != null && ListUtil.isNotEmpty(batchResult.getPageList())) {
				List<FCouncilApplyCreditInfo> credits = new ArrayList<>();
				for (ProjectInfo project : batchResult.getPageList()) {
					
					FCouncilApplyCreditInfo credit = new FCouncilApplyCreditInfo();
					credit.setProjectCode(project.getProjectCode());
					credit.setProjectName(project.getProjectName());
					credit.setDeptName(project.getDeptName());
					credit.setLoanAmount(project.getAmount());
					credit.setCreditAmount(project.getAmount());
					credit.setIssueDate(project.getStartTime());
					credit.setExpireDate(project.getEndTime());
					//					if (StringUtil.equals(project.getProjectCode(), "2016-0200-211-031")) {
					//						logger.info(project.toString());
					//					}
					ProjectBean projectBean = getProjectBean(project);
					if (StringUtil.isBlank(projectBean.getCapitalSubChannelName())) {
						credit.setFundingSources("--");
					} else {
						credit.setFundingSources(projectBean.getCapitalSubChannelName());
					}
					if (StringUtil.isNotBlank(projectBean.getGuaranteeRate())) {
						credit.setGuaranteeRate(projectBean.getGuaranteeRate());
					}
					//credit.setLoanAmount(project.getBalance());
					credit.setBulgariaBalance(project.getBalance());
					ChargeRepayPlanDetailQueryOrder planDetailQueryOrder = new ChargeRepayPlanDetailQueryOrder();
					planDetailQueryOrder.setPageSize(1);
					planDetailQueryOrder.setProjectCode(project.getProjectCode());
					planDetailQueryOrder.setPlanType(PlanTypeEnum.REPAY_PLAN.code());
					QueryBaseBatchResult<ChargeRepayPlanDetailInfo> queryBaseBatchResult = chargeRepayPlanServiceClient
						.queryPlanDetail(planDetailQueryOrder);
					if (queryBaseBatchResult.getTotalCount() > 0) {
						credit.getJsonObject().put("hasRepayPlan", BooleanEnum.YES.code());
					} else {
						credit.getJsonObject().put("hasRepayPlan", BooleanEnum.NO.code());
					}
					credits.add(credit);
					
				}
				info.setCredits(credits);
				ProjectInfo project = batchResult.getPageList().get(0);
				info.setCustomerId(project.getCustomerId());
				info.setCustomerName(project.getCustomerName());
				info.setCompanyName(project.getCustomerName());
				info.setLoanType(project.getBusiTypeName());
				if (null != project.getTimeUnit()) {
					info.setCreditTimeLimit(project.getTimeLimit()
											+ project.getTimeUnit().viewName());
				}
				model.addAttribute("info", info);
			}
		}
		
		//		if (isRepay(request, info)) {
		//			return VM_PATH + "insteadPay.vm";
		//		} else {
		//			return VM_PATH + "notInsteadPay.vm";
		//		}
		return VM_PATH + "notInsteadPay.vm";
	}
	
	/**
	 * 是否是代偿项目
	 * 
	 * @param isRepay
	 * @param info
	 * @return
	 */
	private boolean isRepay(HttpServletRequest request, FCouncilApplyRiskHandleInfo info) {
		if (null != info) {
			return "Y".equals(info.getIsRepay());
		} else {
			return "Y".equals(request.getParameter("isRepay"));
		}
	}
	
	@ResponseBody
	@RequestMapping("editSubmit.json")
	public Object editSubmit(HttpServletRequest request, FCouncilApplyRiskHandleOrder order) {
		JSONObject json = new JSONObject();
		if (isLoginExpire(json)) {
			return json;
		}
		
		String tipPrefix = "保存上会申报";
		try {
			order.setFormCode(FormCodeEnum.COUNCIL_APPLY);
			order.setCheckIndex(0);
			//order.setCheckStatus(1); //没有暂存，直接提交
			setSessionLocalInfo2Order(order);
			String projectCodes = "";
			String projectCodeNames = "";
			if (ListUtil.isNotEmpty(order.getCredits())) {
				int i = 0;
				
				for (FCouncilApplyCreditOrder creditOrder : order.getCredits()) {
					JSONObject jsonObject = creditOrder.getJsonData();
					String projectCode = request.getParameter("credits[" + i + "].selectItem");
					if (StringUtil.isNotBlank(projectCode)) {
						jsonObject.put("selectItem", "Y");
						if (projectCodes.length() == 0) {
							projectCodes = projectCode;
							projectCodeNames = creditOrder.getProjectName();
						} else {
							projectCodes += "," + projectCode;
							projectCodeNames += "," + creditOrder.getProjectName();
						}
					}
					jsonObject.put("hasRepayPlan",
						StringUtil.equals(
							request.getParameter("credits[" + i + "].JsonObject.hasRepayPlan"),
							"YES") ? "YES" : "NO");
					i++;
				}
				order.setProjectCode(projectCodes);
				order.setProjectName(projectCodeNames);
			}
			order.setRelatedProjectCode(projectCodes);
			FormBaseResult result = councilApplyRiskHandleServiceClient
				.saveCouncilApplyRiskHandle(order);
			
			if (result.isSuccess()) {
				long keyId = result.getKeyId();
				//添加附件
				addAttachfile(keyId + "", request, order.getProjectCode(), null,
					CommonAttachmentTypeEnum.RISK_HANDLE);
			}
			
			json = toJSONResult(result, tipPrefix);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
			json = toJSONResult(tipPrefix, e);
		}
		return json;
	}
	
	@RequestMapping("audit.htm")
	public String audit(@RequestParam(value = "formId", required = true) long formId,
						HttpServletRequest request, Model model) {
		try {
			FormInfo form = formServiceClient.findByFormId(formId);
			if (null != form) {
				model.addAttribute("form", form);// 表单信息
				model.addAttribute("formCode", form.getFormCode());
				initWorkflow(model, form, request.getParameter("taskId"));
			}
			FCouncilApplyRiskHandleInfo info = councilApplyRiskHandleServiceClient
				.findByFormId(formId);
			model.addAttribute("info", info);
			model.addAttribute("riskWarningFid", queryRiskHandFormId(info.getCustomerName()));
			
			//附件列表
			queryCommonAttachmentData(model, info.getId() + "",
				CommonAttachmentTypeEnum.RISK_HANDLE);
			
			//			if (isRepay(request, info)) {
			//				return VM_PATH + "auditInsteadPay.vm";
			//			}
			
			
			//审核记录
			setAuditHistory2Page(form, model);	
			model.addAttribute("HookIsAudit", true);// 审核页面
		} catch (Exception e) {
			logger.error("查询出错");
		}
		
		return VM_PATH + "auditNotInsteadPay.vm";
	}
	
	/**
	 * 
	 * 查询项目关联客户的风险预警
	 * 
	 * @param customerName
	 * @return
	 */
	private long queryRiskHandFormId(String customerName) {
		if (StringUtil.isBlank(customerName)) {
			return 0L;
		}
		
		RiskWarningQueryOrder queryOrder = new RiskWarningQueryOrder();
		queryOrder.setCustomerNameFull(customerName);
		queryOrder.setFormStatus(FormStatusEnum.APPROVAL.code());
		queryOrder.setPageNumber(1L);
		queryOrder.setPageSize(1L);
		QueryBaseBatchResult<RiskWarningInfo> batchResult = riskWarningServiceClient
			.queryList(queryOrder);
		
		if (ListUtil.isNotEmpty(batchResult.getPageList())) {
			return batchResult.getPageList().get(0).getFormId();
		}
		return 0L;
	}
	
	@ResponseBody
	@RequestMapping("queryProjects.json")
	public Object queryProjects(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		String tipPrefix = "查询项目信息";
		
		try {
			ProjectQueryOrder queryOrder = new ProjectQueryOrder();
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			
			List<ProjectRelatedUserTypeEnum> relatedRoleList = new ArrayList<>();
			relatedRoleList.add(ProjectRelatedUserTypeEnum.BUSI_MANAGER);
			relatedRoleList.add(ProjectRelatedUserTypeEnum.RISK_MANAGER);
			relatedRoleList.add(ProjectRelatedUserTypeEnum.LEGAL_MANAGER);
			queryOrder.setRelatedRoleList(relatedRoleList);
			
			setSessionLocalInfo2Order(queryOrder);
//			SessionLocal sessionLocal = ShiroSessionUtils.getSessionLocal();
//			if (sessionLocal != null && DataPermissionUtil.isBusiManager()) {
//				queryOrder.setBusiManagerId(sessionLocal.getUserId());
//			}
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
			
			JSONArray dataList = new JSONArray();
			JSONObject data = new JSONObject();
			QueryBaseBatchResult<ProjectInfo> batchResult = null;
			//			if ("Y".equals(request.getParameter("isRepay"))) {
			//				batchResult = councilApplyRiskHandleServiceClient.queryProject(queryOrder);
			//			} else {
			//				
			//			}
			batchResult = councilApplyRiskHandleServiceClient.queryNoRepayProject(queryOrder);
			if (ListUtil.isNotEmpty(batchResult.getPageList())) {
				for (ProjectInfo info : batchResult.getPageList()) {
					JSONObject json = new JSONObject();
					json.put("projectCode", info.getProjectCode());
					json.put("projectName", info.getProjectName());
					json.put("customerId", info.getCustomerId());
					json.put("customerName", info.getCustomerName());
					json.put("amount", info.getAmount().toStandardString());
					json.put("busiType", info.getBusiType());
					json.put("busiTypeName", info.getBusiTypeName());
					dataList.add(json);
				}
			}
			data.put("pageCount", batchResult.getPageCount());
			data.put("pageNumber", batchResult.getPageNumber());
			data.put("pageSize", batchResult.getPageSize());
			data.put("totalCount", batchResult.getTotalCount());
			data.put("pageList", dataList);
			result = toStandardResult(data, tipPrefix);
		} catch (Exception e) {
			result = toStandardResult(null, tipPrefix);
			logger.error("", e);
		}
		return result;
	}
	
}
