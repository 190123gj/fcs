package com.born.fcs.pm.biz.service.financialproject;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;
import rop.thirdparty.com.google.common.collect.Maps;

import com.born.fcs.pm.biz.service.base.BaseProcessService;
import com.born.fcs.pm.dal.dataobject.FProjectFinancialTansferApplyDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.integration.bpm.result.WorkflowResult;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CouncilApplyStatusEnum;
import com.born.fcs.pm.ws.enums.CouncilTypeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectCouncilEnum;
import com.born.fcs.pm.ws.enums.ProjectCouncilStatusEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectRelatedUserInfo;
import com.born.fcs.pm.ws.info.financialproject.FProjectFinancialTansferApplyInfo;
import com.born.fcs.pm.ws.info.financialproject.ProjectFinancialInfo;
import com.born.fcs.pm.ws.order.bpm.FlowVarField;
import com.born.fcs.pm.ws.order.bpm.WorkflowStartOrder;
import com.born.fcs.pm.ws.order.bpm.enums.FlowVarTypeEnum;
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
	public List<FlowVarField> makeWorkFlowVar(FormInfo formInfo) {
		
		List<FlowVarField> fields = Lists.newArrayList();
		try {
			FProjectFinancialTansferApplyInfo apply = financialProjectTransferService
				.queryApplyByFormId(formInfo.getFormId());
			//			FProjectFinancialInfo project = financialProjectSetupService
			//				.queryByProjectCode(review.getProjectCode());
			ProjectRelatedUserInfo riskManager = projectRelatedUserService.getRiskManager(apply
				.getProjectCode());
			//风险经理
			FlowVarField rm = new FlowVarField();
			rm.setVarName("RiskManagerID");
			rm.setVarType(FlowVarTypeEnum.STRING);
			rm.setVarVal(riskManager == null ? "0" : riskManager.getUserId() + "");
			fields.add(rm);
			//上会类型
			FlowVarField ct = new FlowVarField();
			ct.setVarName("councilType");
			ct.setVarType(FlowVarTypeEnum.STRING);
			ct.setVarVal(apply.getCouncilType() == null ? "无" : apply.getCouncilType().code() + "");
			fields.add(ct);
			
		} catch (Exception e) {
			logger.error("设置流程参数出错：{}", e);
		}
		return fields;
	}
	
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
			
			if (StringUtil.isBlank(apply.getCouncilType())) {//不需要上会
				//同步到资金预测
				financialProjectTransferService.syncForecast(apply.getApplyId());
			} else {//需要上会
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
				} else if (ProjectCouncilEnum.SELF_GW_MONTHER_PR.code().equals(//总经理办公会+母公司项目评审会
					apply.getCouncilType())) {
					councilApplyOrder.setMotherCompanyApply(BooleanEnum.YES);
					councilApplyOrder.setCouncilCode(CouncilTypeEnum.GM_WORKING.code());
					councilApplyOrder.setCouncilTypeDesc(CouncilTypeEnum.GM_WORKING.message());
					councilApplyOrder.setMotherCouncilCode(CouncilTypeEnum.PROJECT_REVIEW.code());
				} else if (ProjectCouncilEnum.SELF_PR.code().equals(////项目评审会
					apply.getCouncilType())) {
					councilApplyOrder.setMotherCompanyApply(BooleanEnum.NO);
					councilApplyOrder.setCouncilCode(CouncilTypeEnum.PROJECT_REVIEW.code());
					councilApplyOrder.setCouncilTypeDesc(CouncilTypeEnum.PROJECT_REVIEW.message());
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
				}
			}
			
		} catch (Exception e) {
			logger.error("理财产品转让审核完成处理出错:{}", e);
		}
		
	}
}
