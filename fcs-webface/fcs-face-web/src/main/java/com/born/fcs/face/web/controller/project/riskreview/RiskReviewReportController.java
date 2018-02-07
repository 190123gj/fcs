package com.born.fcs.face.web.controller.project.riskreview;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.born.fcs.face.web.controller.base.WorkflowBaseController;
import com.born.fcs.face.web.util.PageUtil;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CommonAttachmentTypeEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.FormProjectInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationCreditSchemeInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationLitigationInfo;
import com.born.fcs.pm.ws.info.riskreview.RiskReviewReportInfo;
import com.born.fcs.pm.ws.info.setup.FProjectInfo;
import com.born.fcs.pm.ws.order.riskreview.FRiskReviewOrder;
import com.born.fcs.pm.ws.order.riskreview.FRiskReviewQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.beans.cglib.BeanCopier;

@Controller
@RequestMapping("projectMg/riskreview")
public class RiskReviewReportController extends WorkflowBaseController {
	
	final static String vm_path = "/projectMg/beforeLoanMg/riskReview/";
	
	@RequestMapping("audit.htm")
	public String audit(long formId, HttpServletRequest request, String edit, Model model,
						HttpSession session) {
		
		FormInfo form = formServiceClient.findByFormId(formId);
		if (null != form) {
			model.addAttribute("form", form);// 表单信息
			model.addAttribute("formCode", form.getFormCode());
			initWorkflow(model, form, request.getParameter("taskId"));
		}
		
		RiskReviewReportInfo riskReport = riskReviewReportServiceClient
			.queryRiskReviewByFormId(formId);
		model.addAttribute("riskReport", riskReport);
		
		ProjectInfo project = projectServiceClient.queryByCode(riskReport.getProjectCode(), false);
		model.addAttribute("project", project);
		if (null != project) {
			if (ProjectUtil.isLitigation(project.getBusiType())) {
				FInvestigationLitigationInfo info = investigationServiceClient
					.findFInvestigationLitigationByFormId(formId);
				model.addAttribute("info", info);
			} else {
				FInvestigationCreditSchemeInfo creditScheme = investigationServiceClient
					.findInvestigationCreditSchemeByFormId(formId);
				model.addAttribute("creditScheme", creditScheme);
			}
		}		
		/*RiskReviewReportInfo riskReviewinfo = null;
		FormInfo form = formServiceClient.findByFormId(formId);
		riskReviewinfo = riskReviewReportServiceClient.queryRiskReviewByFormId(formId);
		if (riskReviewinfo == null) {
			FRiskReviewOrder order = new FRiskReviewOrder();
			order.setFormCode(form.getFormCode());//尽职调查和风险审查合并
			order.setFormId(formId);
			FInvestigationInfo info = investigationServiceClient.findInvestigationByFormId(formId);
			BeanCopier.staticCopy(info, order);
			order.setCustomerId(order.getCustomerId());
			order.setId(0);
			order.setCheckIndex(0);
			order.setCheckStatus(1);
			FormBaseResult saveResult = riskReviewReportServiceClient.saveRiskReview(order);
			if (saveResult.isSuccess()) {
				riskReviewinfo = riskReviewReportServiceClient.queryRiskReviewByFormId(formId);
			} else {
				throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "初始风险审查报告错误");
			}
		}
		FProjectInfo projectInfo = projectSetupServiceClient.queryProjectByCode(riskReviewinfo
			.getProjectCode());
		initWorkflow(model, form, form.getTaskId() + "");
		model.addAttribute("riskReviewinfo", riskReviewinfo);
		model.addAttribute("projectInfo", projectInfo);
		model.addAttribute("form", form);// 表单信息
		model.addAttribute("editReportContent", edit);
		
		FInvestigationCreditSchemeInfo creditScheme = investigationServiceClient
			.findInvestigationCreditSchemeByFormId(formId);
		model.addAttribute("creditScheme", creditScheme);*/
		
		//附件
		queryCommonAttachmentData(model, form.getFormId() + "",
			CommonAttachmentTypeEnum.RISK_REVIEW);
		
		return vm_path + "auditReview.vm";
		
	}
	
	/**
	 * 风险审查报告列表
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("riskReviewList.htm")
	public String riskReviewList(FRiskReviewQueryOrder order, Model model) {
		setSessionLocalInfo2Order(order);
		QueryBaseBatchResult<FormProjectInfo> batchResult = riskReviewReportServiceClient
			.queryRiskReview(order);
		model.addAttribute("conditions", order);
		model.addAttribute("page", PageUtil.getCovertPage(batchResult));
		
		return vm_path + "list.vm";
	}
	
	/**
	 * 新增风险审查报告
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("addRiskReview.json")
	@ResponseBody
	public JSONObject addRiskReview(FRiskReviewOrder order, Model model) {
		String tipPrefix = " 新增风险审查报告";
		JSONObject result = new JSONObject();
		try {
			
			// 初始化Form验证信息
			order.setCheckStatus(0);
			order.setCheckIndex(0);
			order.setFormCode(FormCodeEnum.RISK_REVIEW_REPORT);
			
			FProjectInfo projectInfo = projectSetupServiceClient.queryProjectByCode(order
				.getProjectCode());
			BeanCopier.staticCopy(projectInfo, order);
			order.setCustomerId(projectInfo.getCustomerId());
			
			// TODO
			order.setId(0);
			
			FormBaseResult saveResult = riskReviewReportServiceClient.saveRiskReview(order);
			return toJSONResult(saveResult, tipPrefix);
		} catch (Exception e) {
			result = toJSONResult(tipPrefix, e);
			logger.error(tipPrefix, e);
		}
		
		return result;
	}
	
	/**
	 * 查看风险审查报告
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("viewReview.htm")
	public String viewRiskReview(HttpServletRequest request, Model model, long formId) {
		String tipPrefix = "查看风险审查报告";
		try {
			FormInfo form = formServiceClient.findByFormId(formId);
			model.addAttribute("form", form);// 表单信息
			
			RiskReviewReportInfo riskReport = riskReviewReportServiceClient
				.queryRiskReviewByFormId(formId);
			model.addAttribute("riskReport", riskReport);
			
			if (null != riskReport) {
				ProjectInfo project = projectServiceClient.queryByCode(riskReport.getProjectCode(), false);
				model.addAttribute("project", project);
				if (null != project) {
					if (ProjectUtil.isLitigation(project.getBusiType())) {
						FInvestigationLitigationInfo info = investigationServiceClient
								.findFInvestigationLitigationByFormId(formId);
						model.addAttribute("info", info);
					} else {
						FInvestigationCreditSchemeInfo creditScheme = investigationServiceClient
								.findInvestigationCreditSchemeByFormId(formId);
						model.addAttribute("creditScheme", creditScheme);
					}
				}
			}
			
			//附件
			queryCommonAttachmentData(model, form.getFormId() + "",
				CommonAttachmentTypeEnum.RISK_REVIEW);
			
			//审核记录
			setAuditHistory2Page(form, model);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
		}
		
		return vm_path + "viewReview.vm";
	}
	
	/**
	 * 去新增风险审查报告
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("toAddRiskReview.htm")
	public String toAddRiskReview(Model model) {
		return vm_path + "addReview.vm";
	}
	
	/**
	 * 去更新风险审查报告
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("toUpdateRiskReview.htm")
	public String toUpdateRiskReview(Long id, Model model) {
		String tipPrefix = "修改风险审查报告";
		try {
			RiskReviewReportInfo riskReviewinfo = riskReviewReportServiceClient
				.queryRiskReviewById(id);
			
			FProjectInfo projectInfo = projectSetupServiceClient.queryProjectByCode(riskReviewinfo
				.getProjectCode());
			
			model.addAttribute("riskReviewinfo", riskReviewinfo);
			model.addAttribute("projectInfo", projectInfo);
		} catch (Exception e) {
			logger.error(tipPrefix, e);
		}
		
		return vm_path + "modifyReview.vm";
	}
	
	/**
	 * 更新风险审查报告
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping("updateRiskReview.json")
	@ResponseBody
	public JSONObject updateRiskReview(FRiskReviewOrder order, Model model) {
		
		String tipPrefix = "更新风险审查报告";
		JSONObject result = new JSONObject();
		try {
			order.setCheckStatus(0);
			order.setCheckIndex(0);
			
			FcsBaseResult saveResult = riskReviewReportServiceClient
				.updateRiskReviewReportContent(order);
			result = toJSONResult(saveResult, tipPrefix);
		} catch (Exception e) {
			result = toJSONResult(tipPrefix, e);
			logger.error(tipPrefix, e);
		}
		
		return result;
	}
	
	@RequestMapping("edit.htm")
	public String edit(HttpServletRequest request, Model model, long formId) {
		
		FormInfo form = formServiceClient.findByFormId(formId);
		if (null != form) {
			model.addAttribute("form", form);// 表单信息
			model.addAttribute("formCode", form.getFormCode());
			initWorkflow(model, form, request.getParameter("taskId"));
		}
		
		RiskReviewReportInfo riskReport = riskReviewReportServiceClient
			.queryRiskReviewByFormId(formId);
		if (null == riskReport) {
			riskReport = new RiskReviewReportInfo();
		}
		riskReport.setFormId(formId);
		FInvestigationInfo investigation = investigationServiceClient
			.findInvestigationByFormId(formId);
		if (null != investigation) {
			riskReport.setProjectCode(investigation.getProjectCode());
			riskReport.setProjectName(investigation.getProjectName());
			riskReport.setCustomerId(investigation.getCustomerId());
			riskReport.setCustomerName(investigation.getCustomerName());
		}
		model.addAttribute("riskReport", riskReport);
		
		ProjectInfo project = projectServiceClient.queryByCode(riskReport.getProjectCode(), false);
		model.addAttribute("project", project);
		if (null != project) {
			if (ProjectUtil.isLitigation(project.getBusiType())) {
				FInvestigationLitigationInfo info = investigationServiceClient
					.findFInvestigationLitigationByFormId(formId);
				model.addAttribute("info", info);
			} else {
				FInvestigationCreditSchemeInfo creditScheme = investigationServiceClient
					.findInvestigationCreditSchemeByFormId(formId);
				model.addAttribute("creditScheme", creditScheme);
			}
		}
		
		model.addAttribute("isEdit", BooleanEnum.YES.code());
		
		//附件
		queryCommonAttachmentData(model, form.getFormId() + "",
			CommonAttachmentTypeEnum.RISK_REVIEW);
		
		return vm_path + "edit.vm";
		
	}
}
