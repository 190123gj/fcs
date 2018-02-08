package com.born.fcs.pm.biz.service.financialproject;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;
import rop.thirdparty.com.google.common.collect.Maps;

import com.born.fcs.pm.biz.service.base.BaseProcessService;
import com.born.fcs.pm.dal.dataobject.FProjectFinancialDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.integration.bpm.result.WorkflowResult;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CouncilApplyStatusEnum;
import com.born.fcs.pm.ws.enums.CouncilTypeEnum;
import com.born.fcs.pm.ws.enums.ProjectCouncilEnum;
import com.born.fcs.pm.ws.enums.ProjectCouncilStatusEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectRelatedUserInfo;
import com.born.fcs.pm.ws.info.financialproject.FProjectFinancialInfo;
import com.born.fcs.pm.ws.info.financialproject.FProjectFinancialReviewInfo;
import com.born.fcs.pm.ws.order.bpm.FlowVarField;
import com.born.fcs.pm.ws.order.bpm.WorkflowStartOrder;
import com.born.fcs.pm.ws.order.bpm.enums.FlowVarTypeEnum;
import com.born.fcs.pm.ws.order.common.WorkFlowBeforeProcessOrder;
import com.born.fcs.pm.ws.order.council.CouncilApplyOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.service.council.CouncilApplyService;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectReviewService;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectSetupService;

@Service("financialProjectReviewProcessService")
public class FinancialProjectReviewProcessServiceImpl extends BaseProcessService {
	
	@Autowired
	FinancialProjectReviewService financialProjectReviewService;
	
	@Autowired
	FinancialProjectSetupService financialProjectSetupService;
	
	@Autowired
	CouncilApplyService councilApplyService;
	
	@Override
	public List<FlowVarField> makeWorkFlowVar(FormInfo formInfo) {
		
		List<FlowVarField> fields = Lists.newArrayList();
		try {
			FProjectFinancialReviewInfo review = financialProjectReviewService
				.queryByFormId(formInfo.getFormId());
			//			FProjectFinancialInfo project = financialProjectSetupService
			//				.queryByProjectCode(review.getProjectCode());
			ProjectRelatedUserInfo riskManager = projectRelatedUserService.getRiskManager(review
				.getProjectCode());
			//风险经理
			FlowVarField rm = new FlowVarField();
			rm.setVarName("RiskManagerID");
			rm.setVarType(FlowVarTypeEnum.STRING);
			rm.setVarVal(riskManager == null ? "0" : riskManager.getUserId() + "");
			fields.add(rm);
			
		} catch (Exception e) {
			logger.error("设置流程参数出错：{}", e);
		}
		return fields;
	}
	
	@Override
	public Map<String, String> makeMessageVar(FormInfo formInfo) {
		Map<String, String> vars = Maps.newHashMap();
		
		FProjectFinancialReviewInfo Review = financialProjectReviewService.queryByFormId(formInfo
			.getFormId());
		
		FProjectFinancialInfo project = financialProjectSetupService.queryByProjectCode(Review
			.getProjectCode());
		vars.put("项目编号", project.getProjectCode());
		vars.put("产品名称", project.getProductName());
		//		String customVar = "";
		//		if (formInfo.getStatus() == FormStatusEnum.APPROVAL) {
		//			customVar = "快去赎回吧!";
		//		}
		//		vars.put("自定义信息", customVar);
		return vars;
	}
	
	@Override
	public FcsBaseResult startBeforeProcess(WorkFlowBeforeProcessOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			FProjectFinancialReviewInfo review = financialProjectReviewService.queryByFormId(order
				.getFormInfo().getFormId());
			if (review != null) {
				FProjectFinancialInfo project = financialProjectSetupService
					.queryByProjectCode(review.getProjectCode());
				
				//自定义待办任务名称
				WorkflowStartOrder startOrder = (WorkflowStartOrder) FcsPmDomainHolder.get()
					.getAttribute("startOrder");
				if (startOrder != null) {
					startOrder.setCustomTaskName(project.getProductName() + "-理财项目送审申请");
				}
			}
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			logger.error("理财项目送审流程启动前置处理出错 ： {}", e);
		}
		return result;
	}
	
	@Override
	public void endFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		FProjectFinancialReviewInfo review = financialProjectReviewService.queryByFormId(formInfo
			.getFormId());
		if (review != null) {
			FProjectFinancialDO project = FProjectFinancialDAO.findByProjectCode(review
				.getProjectCode());
			if (project != null) {
				ProjectCouncilEnum councilType = ProjectCouncilEnum.getByCode(project
					.getCouncilType());
				logger.info("理财产品送审上会处理开始 （总部）{} {} ", project.getProjectCode(), councilType);
				CouncilApplyOrder order = new CouncilApplyOrder();
				order.setProjectCode(project.getProjectCode());
				order.setProjectName(project.getProductName());
				order.setCustomerId(0);
				order.setCustomerName("-");
				order.setApplyManId(project.getCreateUserId());
				order.setApplyManName(project.getCreateUserName());
				order.setApplyDeptId(project.getDeptId());
				order.setApplyDeptName(project.getDeptName());
				order.setApplyTime(getSysdate());
				order.setStatus(CouncilApplyStatusEnum.WAIT.code());
				if (councilType == ProjectCouncilEnum.SELF_MOTHER_GW) {//总经理办公会+母公司办公会
					order.setMotherCompanyApply(BooleanEnum.YES);
					order.setCouncilCode(CouncilTypeEnum.GM_WORKING.code());
					order.setCouncilTypeDesc(CouncilTypeEnum.GM_WORKING.message());
					order.setMotherCouncilCode(CouncilTypeEnum.GM_WORKING.code());
				} else if (councilType == ProjectCouncilEnum.SELF_GW_MONTHER_PR) { //总经理办公会+母公司项目评审会
					order.setMotherCompanyApply(BooleanEnum.YES);
					order.setCouncilCode(CouncilTypeEnum.GM_WORKING.code());
					order.setCouncilTypeDesc(CouncilTypeEnum.GM_WORKING.message());
					order.setMotherCouncilCode(CouncilTypeEnum.PROJECT_REVIEW.code());
				} else if (councilType == ProjectCouncilEnum.SELF_PR) { //项目评审会
					order.setMotherCompanyApply(BooleanEnum.NO);
					order.setCouncilCode(CouncilTypeEnum.PROJECT_REVIEW.code());
					order.setCouncilTypeDesc(CouncilTypeEnum.PROJECT_REVIEW.message());
				} else {//总经理办公会
					order.setMotherCompanyApply(BooleanEnum.NO);
					order.setCouncilCode(CouncilTypeEnum.GM_WORKING.code());
					order.setCouncilTypeDesc(CouncilTypeEnum.GM_WORKING.message());
				}
				FcsBaseResult sResult = councilApplyService.saveCouncilApply(order);
				logger.info("理财项目送审进入待上会列表（总部）  {} , {}", project.getProjectCode(), sResult);
				if (sResult.isSuccess()) {
					//记录上会的申请ID
					project.setCouncilStatus(ProjectCouncilStatusEnum.COUNCIL_WAITING.code());
					project.setCouncilApplyId(sResult.getKeyId());
					FProjectFinancialDAO.update(project);
				}
			}
		}
	}
}
