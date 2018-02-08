package com.born.fcs.pm.biz.service.financialproject;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Maps;

import com.born.fcs.pm.biz.service.base.BaseProcessService;
import com.born.fcs.pm.dal.dataobject.FProjectFinancialTansferApplyDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.integration.bpm.result.WorkflowResult;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CouncilApplyStatusEnum;
import com.born.fcs.pm.ws.enums.CouncilTypeEnum;
import com.born.fcs.pm.ws.enums.FinancialProjectStatusEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectCouncilEnum;
import com.born.fcs.pm.ws.enums.ProjectCouncilStatusEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.financialproject.FProjectFinancialTansferApplyInfo;
import com.born.fcs.pm.ws.info.financialproject.ProjectFinancialInfo;
import com.born.fcs.pm.ws.order.bpm.WorkflowStartOrder;
import com.born.fcs.pm.ws.order.common.WorkFlowBeforeProcessOrder;
import com.born.fcs.pm.ws.order.council.CouncilApplyOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.service.council.CouncilApplyService;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectService;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectTransferService;
import com.yjf.common.lang.util.StringUtil;

@Service("financialProjectTransferProcessService")
public class FinancialProjectTransferProcessServiceImpl extends BaseProcessService {
	
	@Autowired
	FinancialProjectTransferService financialProjectTransferService;
	
	@Autowired
	FinancialProjectService financialProjectService;
	
	@Autowired
	CouncilApplyService councilApplyService;
	
	@Override
	public Map<String, String> makeMessageVar(FormInfo formInfo) {
		Map<String, String> vars = Maps.newHashMap();
		FProjectFinancialTansferApplyInfo apply = financialProjectTransferService
			.queryApplyByFormId(formInfo.getFormId());
		ProjectFinancialInfo project = financialProjectService.queryByCode(apply.getProjectCode());
		vars.put("项目编号", project.getProjectCode());
		vars.put("产品名称", project.getProductName());
		String customVar = "";
		if (formInfo.getStatus() == FormStatusEnum.APPROVAL) {
			customVar = "快去转让吧!";
		}
		vars.put("自定义信息", customVar);
		return vars;
	}
	
	@Override
	public FcsBaseResult startBeforeProcess(WorkFlowBeforeProcessOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			FProjectFinancialTansferApplyInfo apply = financialProjectTransferService
				.queryApplyByFormId(order.getFormInfo().getFormId());
			
			ProjectFinancialInfo project = financialProjectService.queryByCode(apply
				.getProjectCode());
			
			//自定义待办任务名称
			WorkflowStartOrder startOrder = (WorkflowStartOrder) FcsPmDomainHolder.get()
				.getAttribute("startOrder");
			if (startOrder != null) {
				startOrder.setCustomTaskName(project.getProductName() + "-理财项目转让申请");
			}
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			logger.error("理财项目转让流程启动前置处理出错 ： {}", e);
		}
		return result;
	}
	
	@Override
	public void startAfterProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		FProjectFinancialTansferApplyInfo apply = financialProjectTransferService
			.queryApplyByFormId(formInfo.getFormId());
		financialProjectService.changeStatus(apply.getProjectCode(),
			FinancialProjectStatusEnum.TRANSFER_AUDITING);
	}
	
	@Override
	public FcsBaseResult doNextBeforeProcess(final WorkFlowBeforeProcessOrder order) {
		FcsBaseResult result = createResult();
		try {
			
			final Map<String, Object> customizeMap = order.getCustomizeMap();
			if (customizeMap != null) {
				logger.info("理财产品转让选择上会类型处理开始：{}", customizeMap);
				if ("YES".equals((String) customizeMap.get("chooseCouncil"))) { //选择是否上会及上会类型
					String councilType = (String) customizeMap.get("councilType");
					String isNeedCouncil = (String) customizeMap.get("isNeedCouncil");
					FProjectFinancialTansferApplyDO apply = FProjectFinancialTansferApplyDAO
						.findByFormId(order.getFormInfo().getFormId());
					if (BooleanEnum.NO.code().equals(isNeedCouncil)) {//不需要上会
						apply.setCouncilType(null);
					} else { //需要上会
						apply.setCouncilType(councilType);
					}
					FProjectFinancialTansferApplyDAO.update(apply);
					logger.info("理财产品转让选择上会类型处理结束：{}", order);
				}
			}
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("选择上会类型出错");
			logger.error("理财产品转让选择上会类型出错 {} {}", order, e);
		}
		return result;
	}
	
	@Override
	public void endFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		try {
			
			FProjectFinancialTansferApplyDO apply = FProjectFinancialTansferApplyDAO
				.findByFormId(formInfo.getFormId());
			
			ProjectFinancialInfo project = financialProjectService.queryByCode(apply
				.getProjectCode());
			logger.info("理财产品转让审核完成处理开始：{}", apply);
			//不需要上会
			if (StringUtil.isBlank(apply.getCouncilType())) {
				FcsBaseResult result = financialProjectService.changeStatus(apply.getProjectCode(),
					FinancialProjectStatusEnum.TRANSFER_APPROVAL);
				logger.info("理财产品转让无需转让修改为装让通过状态 {}", result);
			} else {
				CouncilApplyOrder councilApplyOrder = new CouncilApplyOrder();
				councilApplyOrder.setProjectCode(project.getProjectCode());
				councilApplyOrder.setProjectName(project.getProductName());
				councilApplyOrder.setStatus(CouncilApplyStatusEnum.WAIT.code());
				councilApplyOrder.setApplyDeptId(formInfo.getDeptId());
				councilApplyOrder.setCustomerId(0);
				councilApplyOrder.setCustomerName("-");
				councilApplyOrder.setApplyDeptName(formInfo.getDeptName());
				councilApplyOrder.setApplyManId(formInfo.getUserId());
				councilApplyOrder.setApplyManName(formInfo.getUserName());
				councilApplyOrder.setFormId(formInfo.getFormId());
				councilApplyOrder.setApplyTime(getSysdate());
				if (ProjectCouncilEnum.SELF_MOTHER_GW.code().equals(apply.getCouncilType())) {
					//上本公司和母公司总经理办公会
					councilApplyOrder.setCouncilCode(CouncilTypeEnum.GM_WORKING.code());
					councilApplyOrder.setCouncilTypeDesc(CouncilTypeEnum.GM_WORKING.message());
					councilApplyOrder.setMotherCompanyApply(BooleanEnum.YES);
					councilApplyOrder.setMotherCouncilCode(CouncilTypeEnum.GM_WORKING.code());
					//				} else if (FinancialProjectCouncilEnum.SELF_GW_MONTHER_PR.code().equals(
					//					apply.getCouncilType())) {
					//					//上本公司总经理办公会和母公司项目评审会  2016-08-18 上母公司项目评审会不合理 
					//					councilApplyOrder.setCouncilCode(CouncilTypeEnum.GM_WORKING.code());
					//					councilApplyOrder.setMotherCompanyApply(BooleanEnum.YES);
					//					councilApplyOrder.setMotherCouncilCode(CouncilTypeEnum.PROJECT_REVIEW.code());
				} else {
					//只上本公司总经理办公会
					councilApplyOrder.setCouncilCode(CouncilTypeEnum.GM_WORKING.code());
					councilApplyOrder.setCouncilTypeDesc(CouncilTypeEnum.GM_WORKING.message());
					councilApplyOrder.setMotherCompanyApply(BooleanEnum.NO);
				}
				logger.info("理财产品转让插入待上会列表  {} ", councilApplyOrder);
				//插入待上会列表
				FcsBaseResult result = councilApplyService.saveCouncilApply(councilApplyOrder);
				if (result.isSuccess()) {
					//修改上会申请时的applyId
					apply.setCouncilApplyId(result.getKeyId());
					//状态修改会上会中
					apply.setCouncilStatus(ProjectCouncilStatusEnum.COUNCIL_WAITING.code());
					FProjectFinancialTansferApplyDAO.update(apply);
					
					//理财项目修改为转让上会中
					FcsBaseResult sResult = financialProjectService
						.changeStatus(apply.getProjectCode(),
							FinancialProjectStatusEnum.TRANSFER_COUNCIL_WAITING);
					logger.info("修改理财项目状态为转让上会中 {} ", sResult);
				}
			}
			
		} catch (Exception e) {
			logger.error("理财产品转让审核完成处理出错:{}", e);
		}
		
	}
	
	@Override
	public void manualEndFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		FProjectFinancialTansferApplyInfo apply = financialProjectTransferService
			.queryApplyByFormId(formInfo.getFormId());
		financialProjectService.changeStatus(apply.getProjectCode(),
			FinancialProjectStatusEnum.TRANSFER_DENY);
	}
}
