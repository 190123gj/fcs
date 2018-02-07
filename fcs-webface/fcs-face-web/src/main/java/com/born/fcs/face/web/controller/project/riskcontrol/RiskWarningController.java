package com.born.fcs.face.web.controller.project.riskcontrol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.web.controller.base.WorkflowBaseController;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.face.web.util.WebUtil;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.PlanTypeEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.fund.ChargeRepayPlanDetailInfo;
import com.born.fcs.pm.ws.info.riskwarning.FRiskWarningCreditInfo;
import com.born.fcs.pm.ws.info.riskwarning.FRiskWarningInfo;
import com.born.fcs.pm.ws.info.riskwarning.RiskWarningInfo;
import com.born.fcs.pm.ws.info.riskwarning.RiskWarningSignalInfo;
import com.born.fcs.pm.ws.order.common.ProjectQueryOrder;
import com.born.fcs.pm.ws.order.fund.ChargeRepayPlanDetailQueryOrder;
import com.born.fcs.pm.ws.order.riskwarning.FRiskWarningCreditOrder;
import com.born.fcs.pm.ws.order.riskwarning.FRiskWarningOrder;
import com.born.fcs.pm.ws.order.riskwarning.RiskWarningQueryOrder;
import com.born.fcs.pm.ws.order.riskwarning.RiskWarningSignalQueryOrder;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.google.common.collect.Lists;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;

/**
 * 
 * 风险预警
 * 
 * 
 * @author lirz
 * 
 * 2016-4-22 下午4:15:47
 */
@Controller
@RequestMapping("projectMg/riskWarning")
public class RiskWarningController extends WorkflowBaseController {
	
	private static final String VM_PATH = "/projectMg/riskControl/riskWarning/";
	
	/**
	 * 风险预警处理表
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("list.htm")
	public String list(HttpServletRequest request, Model model) {
		
		try {
			RiskWarningQueryOrder queryOrder = new RiskWarningQueryOrder();
			WebUtil.setPoPropertyByRequest(queryOrder, request);
			setSessionLocalInfo2Order(queryOrder);
			QueryBaseBatchResult<RiskWarningInfo> batchResult = riskWarningServiceClient
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
			FRiskWarningInfo info = riskWarningServiceClient.findByFormId(formId);
			model.addAttribute("info", info);
			FormInfo form = formServiceClient.findByFormId(formId);
			model.addAttribute("form", form);
			setAuditHistory2Page(form, model);
		} catch (Exception e) {
			logger.error("查询出错");
		}
		
		return VM_PATH + "viewForm.vm";
	}
	
	@RequestMapping("edit.htm")
	public String edit(	@RequestParam(value = "formId", required = false, defaultValue = "0") long formId,
						HttpServletRequest request, Model model) {
		model.addAttribute("formId", formId);
		FRiskWarningInfo info = null;
		if (formId > 0) {
			info = riskWarningServiceClient.findByFormId(formId);
			model.addAttribute("info", info);
			FormInfo form = formServiceClient.findByFormId(formId);
			model.addAttribute("form", form);
		} else {
			//TODO 暂时先用客户名称customerName代替查询
			String projectCode = request.getParameter("projectCode");
			String customerName = request.getParameter("customerName");
			// 添加兼容 工作台过来的项目code用于新增
			
			if (StringUtil.isBlank(customerName) && StringUtil.isNotBlank(projectCode)) {
				ProjectInfo projectInfo = projectServiceClient.queryByCode(projectCode, false);
				if (projectInfo != null) {
					customerName = projectInfo.getCustomerName();
				}
			}
			if (StringUtil.isNotBlank(customerName)) {
				info = new FRiskWarningInfo();
				ProjectQueryOrder projectQueryOrder = new ProjectQueryOrder();
				//TODO 这儿需要用customerId来查询客户的所有项目信息
				long customerId = NumberUtil.parseLong(request.getParameter("customerId"));
				projectQueryOrder.setCustomerId(customerId);
				projectQueryOrder.setCustomerName(customerName);
				projectQueryOrder.setIsApproval(BooleanEnum.IS);
				projectQueryOrder.setPageSize(999);
				QueryBaseBatchResult<ProjectInfo> batchResult = projectServiceClient
					.queryProject(projectQueryOrder);
				if (ListUtil.isNotEmpty(batchResult.getPageList())) {
					List<FRiskWarningCreditInfo> credits = new ArrayList<>();
					for (ProjectInfo project : batchResult.getPageList()) {
						FRiskWarningCreditInfo credit = new FRiskWarningCreditInfo();
						credit.setDeptName(project.getDeptName());
						credit.setProjectCode(project.getProjectCode());
						ChargeRepayPlanDetailQueryOrder planDetailQueryOrder = new ChargeRepayPlanDetailQueryOrder();
						planDetailQueryOrder.setPageSize(1);
						planDetailQueryOrder.setProjectCode(project.getProjectCode());
						planDetailQueryOrder.setPlanType(PlanTypeEnum.REPAY_PLAN.code());
						QueryBaseBatchResult<ChargeRepayPlanDetailInfo> queryBaseBatchResult = chargeRepayPlanServiceClient
							.queryPlanDetail(planDetailQueryOrder);
						if (queryBaseBatchResult.getTotalCount() > 0) {
							credit.setHasRepayPlan(BooleanEnum.YES);
						} else {
							credit.setHasRepayPlan(BooleanEnum.NO);
						}
						JSONObject jsonObject = credit.getJsonObject();
						jsonObject.put("customerId", project.getBusiManagerId());
						credit.setJsonObject(jsonObject);
						credit.setLoanAmount(project.getBalance());
						credit.setIssueDate(project.getStartTime());
						credit.setExpireDate(project.getEndTime());
						
						credits.add(credit);
						info.setCustomerId(customerId);
						info.setCustomerName(project.getCustomerName());
					}
					info.setCredits(credits);
				}
				info.setWarningBillType("风险预警处理表");
				model.addAttribute("info", info);
			}
			//model.addAttribute("warningBillType", );
			/*String projectCode = request.getParameter("projectCode");
			if (StringUtil.isNotBlank(projectCode)) {
				info = new FRiskWarningInfo();
				info.setProjectCode(projectCode);
				ProjectInfo project = projectServiceClient.queryByCode(projectCode, false);
				model.addAttribute("project", project);
				if (null != project) {
					info.setProjectCode(projectCode);
					info.setProjectName(project.getProjectName());
					info.setCustomerId(project.getCustomerId());
					info.setCustomerName(project.getCustomerName());
				}
				model.addAttribute("info", info);
				//TODO 此处还需要带出授信基本情况
			}*/
			
		}
		
		return VM_PATH + "addForm.vm";
	}
	
	@RequestMapping("clearEdit.htm")
	public String clearEdit(@RequestParam(value = "formId", required = true) long formId,
							HttpServletRequest request, Model model) {
		model.addAttribute("formId", formId);
		FRiskWarningInfo info = null;
		if (formId > 0) {
			info = riskWarningServiceClient.findByFormId(formId);
			if ("风险预警处理表".equals(info.getWarningBillType())) {
				model.addAttribute("info", info);
				FormInfo form = formServiceClient.findByFormId(formId);
				form.setFormId(0);
				form.setStatus(FormStatusEnum.DRAFT);
				info.setWarningBillType("解除风险预警表");
				info.setFormId(0);
				info.setWarningId(0);
				model.addAttribute("form", form);
				
				model.addAttribute("oldFormId", formId);
			} else {
				model.addAttribute("info", info);
				FormInfo form = formServiceClient.findByFormId(formId);
				model.addAttribute("form", form);
				FRiskWarningInfo srcInfo = riskWarningServiceClient.findById(info.getSrcWaningId());
				if (srcInfo != null) {
					model.addAttribute("oldFormId", srcInfo.getFormId());
				}
				
			}
			model.addAttribute("isClear", true);
			
		}
		
		return VM_PATH + "clearForm.vm";
	}
	
	@ResponseBody
	@RequestMapping("editSubmit.json")
	public Object editSubmit(HttpServletRequest request, FRiskWarningOrder order) {
		JSONObject json = new JSONObject();
		if (isLoginExpire(json)) {
			return json;
		}
		String relatedProjectCode = "";
		String tipPrefix = "保存风险预警处置信息";
		try {
			long oldFormId = NumberUtil.parseLong(request.getParameter("oldFormId"), 0);
			if (oldFormId > 0) {
				long formId = NumberUtil.parseLong(request.getParameter("formId"), 0);
				if (formId == 0) {
					FRiskWarningInfo fRiskWarningInfo = riskWarningServiceClient
						.findByFormId(oldFormId);
					order = new FRiskWarningOrder();
					BeanCopier.staticCopy(fRiskWarningInfo, order);
					order.setFormCode(FormCodeEnum.RISK_WARNING);
					order.setSrcWaningId(fRiskWarningInfo.getWarningId());
					order.setCheckIndex(0);
					order.setCheckStatus(1);
					//			order.setCheckStatus(0); //没有暂存，直接提交
					setSessionLocalInfo2Order(order);
					order.setWarningBillType("解除风险预警表");
					if (NumberUtil.parseLong(request.getParameter("formId"), 0) == 0) {
						order.setFormId(0L);
						order.setWarningId(0l);
					}
					
					order.setLiftingReason(request.getParameter("liftingReason"));
					FormInfo oldForm = formServiceClient.findByFormId(oldFormId);
					if (oldForm != null) {
						relatedProjectCode = oldForm.getRelatedProjectCode();
					}
					
					if (ListUtil.isNotEmpty(fRiskWarningInfo.getCredits())) {
						List<FRiskWarningCreditOrder> creditOrders = Lists.newArrayList();
						for (FRiskWarningCreditInfo creditInfo : fRiskWarningInfo.getCredits()) {
							FRiskWarningCreditOrder creditOrder = new FRiskWarningCreditOrder();
							BeanCopier.staticCopy(creditInfo, creditOrder);
							creditOrder.setId(0);
							creditOrder.setWarningId(0);
							creditOrder.setDebitInterestStr(creditInfo.getDebitInterest()
								.toString());
							creditOrder.setLoanAmountStr(creditInfo.getLoanAmount().toString());
							creditOrders.add(creditOrder);
						}
						order.setWarningCredits(creditOrders);
					}
				} else {
					FRiskWarningInfo fRiskWarningInfo = riskWarningServiceClient
						.findByFormId(formId);
					order = new FRiskWarningOrder();
					BeanCopier.staticCopy(fRiskWarningInfo, order);
					order.setFormId(fRiskWarningInfo.getFormId());
					order.setSignalLevelStr(fRiskWarningInfo.getSignalLevel().code());
					order.setCustomerName(fRiskWarningInfo.getCustomerName());
					order.setFormCode(FormCodeEnum.RISK_WARNING);
					setSessionLocalInfo2Order(order);
					order.setCheckIndex(0);
					order.setCheckStatus(1);
					order.setLiftingReason(request.getParameter("liftingReason"));
				}
				
			} else {
				order.setFormCode(FormCodeEnum.RISK_WARNING);
				order.setCheckIndex(0);
				//			order.setCheckStatus(0); //没有暂存，直接提交
				setSessionLocalInfo2Order(order);
				
				if (ListUtil.isNotEmpty(order.getWarningCredits())) {
					int i = 0;
					for (FRiskWarningCreditOrder creditOrder : order.getWarningCredits()) {
						JSONObject jsonObject = creditOrder.getJsonObject();
						jsonObject.put("customerId",
							request.getParameter("warningCredits[" + i + "].customerId"));
						jsonObject.put("selectItem", StringUtil.isNotBlank(request
							.getParameter("warningCredits[" + i + "].selectItem")) ? "Y" : "N");
						creditOrder.setHasRepayPlan(BooleanEnum.getByCode(request
							.getParameter("warningCredits[" + i + "].hasRepayPlan")));
						if (creditOrder.getHasRepayPlan() == null) {
							creditOrder.setHasRepayPlan(BooleanEnum.NO);
						}
						if (StringUtil.equals("Y", jsonObject.getString("selectItem"))) {
							if (relatedProjectCode.length() == 0) {
								relatedProjectCode = creditOrder.getProjectCode();
							} else {
								relatedProjectCode += "," + creditOrder.getProjectCode();
							}
						}
						i++;
					}
				}
			}
			order.setRelatedProjectCode(relatedProjectCode);
			FormBaseResult result = riskWarningServiceClient.save(order);
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
				
				setAuditHistory2Page(form, model);
			}
			FRiskWarningInfo info = riskWarningServiceClient.findByFormId(formId);
			model.addAttribute("info", info);
		} catch (Exception e) {
			logger.error("查询出错");
		}
		
		return VM_PATH + "auditForm.vm";
	}
	
	@ResponseBody
	@RequestMapping("querySignal.json")
	public Object querySignal(HttpServletRequest request, Model model,
								RiskWarningSignalQueryOrder queryOrder) {
		
		JSONObject json = new JSONObject();
		try {
			QueryBaseBatchResult<RiskWarningSignalInfo> batchResult1 = riskWarningSignalServiceClient
				.findCompanySpecial();
			QueryBaseBatchResult<RiskWarningSignalInfo> batchResult2 = riskWarningSignalServiceClient
				.findCompanyNomal();
			JSONArray array = new JSONArray();
			//			Object [] objs = new Object[2];
			//			objs[0] = converToArray(batchResult1.getPageList());
			//			objs[1] = converToArray(batchResult2.getPageList());
			array.add(converToArray(batchResult1.getPageList()));
			array.add(converToArray(batchResult2.getPageList()));
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("signals", array);
			
			json.put("success", true);
			json.put("message", "查询成功");
			json.put("data", map);
			return json;
		} catch (Exception e) {
			logger.error("查询出错");
			json = toJSONResult("查询信号", e);
		}
		
		return json;
	}
	
	private JSONArray converToArray(List<RiskWarningSignalInfo> list) {
		JSONArray array = new JSONArray();
		if (ListUtil.isNotEmpty(list)) {
			for (RiskWarningSignalInfo info : list) {
				JSONObject json = new JSONObject();
				json.put("code", "" + info.getId());
				json.put("str", info.getSignalTypeName());
				array.add(json);
			}
		}
		return array;
	}
}
