package com.born.fcs.pm.biz.service.financialproject;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Maps;

import com.born.fcs.pm.biz.service.base.BaseProcessService;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.integration.bpm.result.WorkflowResult;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.financialproject.FProjectFinancialRedeemApplyInfo;
import com.born.fcs.pm.ws.info.financialproject.ProjectFinancialInfo;
import com.born.fcs.pm.ws.order.bpm.WorkflowStartOrder;
import com.born.fcs.pm.ws.order.common.WorkFlowBeforeProcessOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectRedeemService;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectService;

@Service("financialProjectRedeemProcessService")
public class FinancialProjectRedeemProcessServiceImpl extends BaseProcessService {
	
	@Autowired
	FinancialProjectRedeemService financialProjectRedeemService;
	
	@Autowired
	FinancialProjectService financialProjectService;
	
	@Override
	public Map<String, String> makeMessageVar(FormInfo formInfo) {
		Map<String, String> vars = Maps.newHashMap();
		FProjectFinancialRedeemApplyInfo apply = financialProjectRedeemService
			.queryApplyByFormId(formInfo.getFormId());
		ProjectFinancialInfo project = financialProjectService.queryByCode(apply.getProjectCode());
		vars.put("项目编号", project.getProjectCode());
		vars.put("产品名称", project.getProductName());
		String customVar = "";
		if (formInfo.getStatus() == FormStatusEnum.APPROVAL) {
			customVar = "快去赎回吧!";
		}
		vars.put("自定义信息", customVar);
		return vars;
	}
	
	@Override
	public FcsBaseResult startBeforeProcess(WorkFlowBeforeProcessOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			FProjectFinancialRedeemApplyInfo apply = financialProjectRedeemService
				.queryApplyByFormId(order.getFormInfo().getFormId());
			
			ProjectFinancialInfo project = financialProjectService.queryByCode(apply
				.getProjectCode());
			
			//自定义待办任务名称
			WorkflowStartOrder startOrder = (WorkflowStartOrder) FcsPmDomainHolder.get()
				.getAttribute("startOrder");
			if (startOrder != null) {
				startOrder.setCustomTaskName(project.getProductName() + "-理财项目赎回申请");
			}
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			logger.error("理财项目赎回流程启动前置处理出错 ： {}", e);
		}
		return result;
	}
	
	@Override
	public void startAfterProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		//		FProjectFinancialRedeemApplyInfo apply = financialProjectRedeemService
		//			.queryApplyByFormId(formInfo.getFormId());
		//		financialProjectService.changeStatus(apply.getProjectCode(),
		//			FinancialProjectStatusEnum.REDEEM_AUDITING);
	}
	
	@Override
	public void endFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		//		FProjectFinancialRedeemApplyInfo apply = financialProjectRedeemService
		//			.queryApplyByFormId(formInfo.getFormId());
		//		financialProjectService.changeStatus(apply.getProjectCode(),
		//			FinancialProjectStatusEnum.REDEEM_APPROVAL);
	}
	
	@Override
	public void manualEndFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		//		FProjectFinancialRedeemApplyInfo apply = financialProjectRedeemService
		//			.queryApplyByFormId(formInfo.getFormId());
		//		financialProjectService.changeStatus(apply.getProjectCode(),
		//			FinancialProjectStatusEnum.REDEEM_DENY);
	}
}
