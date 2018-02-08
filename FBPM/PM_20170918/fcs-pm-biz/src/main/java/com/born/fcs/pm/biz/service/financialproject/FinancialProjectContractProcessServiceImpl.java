package com.born.fcs.pm.biz.service.financialproject;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;
import rop.thirdparty.com.google.common.collect.Maps;

import com.born.fcs.pm.biz.service.base.BaseProcessService;
import com.born.fcs.pm.dal.dataobject.FProjectFinancialContractDO;
import com.born.fcs.pm.dal.dataobject.FormDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.integration.bpm.result.WorkflowResult;
import com.born.fcs.pm.util.NumberUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.ContractStatusEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectCouncilEnum;
import com.born.fcs.pm.ws.enums.ProjectRelatedUserTypeEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectRelatedUserInfo;
import com.born.fcs.pm.ws.info.financialproject.FProjectFinancialContractInfo;
import com.born.fcs.pm.ws.info.financialproject.FProjectFinancialInfo;
import com.born.fcs.pm.ws.order.bpm.FlowVarField;
import com.born.fcs.pm.ws.order.bpm.WorkflowStartOrder;
import com.born.fcs.pm.ws.order.bpm.enums.FlowVarTypeEnum;
import com.born.fcs.pm.ws.order.common.ProjectRelatedUserOrder;
import com.born.fcs.pm.ws.order.common.WorkFlowBeforeProcessOrder;
import com.born.fcs.pm.ws.order.financialproject.ProjectFinancialContractStatusOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectContractService;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectSetupService;

@Service("financialProjectContractProcessService")
public class FinancialProjectContractProcessServiceImpl extends BaseProcessService {
	
	@Autowired
	FinancialProjectContractService financialProjectContractService;
	
	@Autowired
	FinancialProjectSetupService financialProjectSetupService;
	
	@Override
	public List<FlowVarField> makeWorkFlowVar(FormInfo formInfo) {
		
		List<FlowVarField> fields = Lists.newArrayList();
		try {
			FProjectFinancialContractInfo contract = financialProjectContractService
				.queryByFormId(formInfo.getFormId());
			
			FProjectFinancialInfo project = financialProjectSetupService
				.queryByProjectCode(contract.getProjectCode());
			
			//是否上会
			FlowVarField whether = new FlowVarField();
			whether.setVarName("Whether");
			whether.setVarType(FlowVarTypeEnum.STRING);
			whether.setVarVal(project.getCouncilType() == ProjectCouncilEnum.SELF_PR ? "1" : "0");
			fields.add(whether);
			
			ProjectRelatedUserInfo legalManager = projectRelatedUserService.getLegalManager(project
				.getProjectCode());
			
			//法务经理
			FlowVarField lm = new FlowVarField();
			lm.setVarName("legalCounsel");
			lm.setVarType(FlowVarTypeEnum.STRING);
			if (legalManager != null) {
				lm.setVarVal(legalManager.getUserId() + "");
			} else {
				Object legalManagerId = FcsPmDomainHolder.get().getAttribute("legalManagerId");
				if (legalManagerId != null) {
					lm.setVarVal(String.valueOf(legalManagerId));
				} else {
					lm.setVarVal("0");
				}
			}
			
			fields.add(lm);
			
			//风险
			ProjectRelatedUserInfo riskManager = projectRelatedUserService.getRiskManager(project
				.getProjectCode());
			FlowVarField rm = new FlowVarField();
			rm.setVarName("RiskManagerID");
			rm.setVarType(FlowVarTypeEnum.STRING);
			rm.setVarVal(riskManager == null ? "0" : riskManager.getUserId() + "");
			fields.add(rm);
			
			//是否法务驳回了
			FlowVarField legalBack = new FlowVarField();
			legalBack.setVarName("Reject");
			legalBack.setVarType(FlowVarTypeEnum.STRING);
			if (StringUtil.equals("法务驳回标记", formInfo.getRemark())) {
				legalBack.setVarVal("1");
			} else {
				legalBack.setVarVal("0");
			}
			fields.add(legalBack);
			
		} catch (Exception e) {
			logger.error("设置流程参数出错：{}", e);
		}
		return fields;
	}
	
	@Override
	public Map<String, String> makeMessageVar(FormInfo formInfo) {
		Map<String, String> vars = Maps.newHashMap();
		
		FProjectFinancialContractInfo contract = financialProjectContractService
			.queryByFormId(formInfo.getFormId());
		
		FProjectFinancialInfo project = financialProjectSetupService.queryByProjectCode(contract
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
			
			FProjectFinancialContractInfo contract = financialProjectContractService
				.queryByFormId(order.getFormInfo().getFormId());
			
			FProjectFinancialInfo project = financialProjectSetupService
				.queryByProjectCode(contract.getProjectCode());
			
			//自定义待办任务名称
			WorkflowStartOrder startOrder = (WorkflowStartOrder) FcsPmDomainHolder.get()
				.getAttribute("startOrder");
			if (startOrder != null) {
				startOrder.setCustomTaskName(project.getProductName() + "-理财项目合同申请");
			}
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			logger.error("理财项目合同流程启动前置处理出错 ： {}", e);
		}
		return result;
	}
	
	@Override
	public FcsBaseResult doNextBeforeProcess(WorkFlowBeforeProcessOrder order) {
		FcsBaseResult result = createResult();
		try {
			final Map<String, Object> customizeMap = order.getCustomizeMap();
			if (customizeMap != null) {
				//选择法务经理
				String selectLegalManager = (String) customizeMap.get("chooseLegalManager");
				if (BooleanEnum.YES.code().equals(selectLegalManager)) {
					long legalManagerId = NumberUtil.parseLong((String) customizeMap
						.get("legalManagerId"));
					String legalManagerAccount = (String) customizeMap.get("legalManagerAccount");
					String legalManagerName = (String) customizeMap.get("legalManagerName");
					
					FProjectFinancialContractDO contract = FProjectFinancialContractDAO
						.findByFormId(order.getFormInfo().getFormId());
					//保存风险到相关人员表
					ProjectRelatedUserOrder relatedUser = new ProjectRelatedUserOrder();
					relatedUser.setProjectCode(contract.getProjectCode());
					relatedUser.setUserType(ProjectRelatedUserTypeEnum.LEGAL_MANAGER);
					relatedUser.setRemark("合同审核,选择法务经理");
					relatedUser.setUserId(legalManagerId);
					relatedUser.setUserAccount(legalManagerAccount);
					relatedUser.setUserName(legalManagerName);
					projectRelatedUserService.setRelatedUser(relatedUser);
					
					FcsPmDomainHolder.get().addAttribute("legalManagerId", legalManagerId);
				}
			}
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("选择法务经理出错");
			logger.error("理财项目合同选择法务经理出错 {} {}", order, e);
		}
		return result;
	}
	
	@Override
	public void doNextAfterProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		try {
			final Map<String, Object> customizeMap = workflowResult.getCustomizeMap();
			if (customizeMap != null) {
				String legalAudit = (String) customizeMap.get("legalAudit");
				if (StringUtil.equals(legalAudit, "YES")
					&& formInfo.getStatus() == FormStatusEnum.BACK) {
					FormDO form = formDAO.findByFormId(formInfo.getFormId());
					if (form != null) {
						form.setRemark("法务驳回标记");
						formDAO.update(form);
					}
				}
			}
		} catch (Exception e) {
			logger.error("理财项目合同审核后置处理出错 {} {}", e);
		}
	}
	
	@Override
	public void startAfterProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		ProjectFinancialContractStatusOrder order = new ProjectFinancialContractStatusOrder();
		order.setContractStatus(ContractStatusEnum.AUDITING);
		order.setFormId(formInfo.getFormId());
		financialProjectContractService.changeStatus(order);
	}
	
	@Override
	public void endFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		ProjectFinancialContractStatusOrder order = new ProjectFinancialContractStatusOrder();
		order.setContractStatus(ContractStatusEnum.APPROVAL);
		order.setFormId(formInfo.getFormId());
		financialProjectContractService.changeStatus(order);
	}
	
	@Override
	public void manualEndFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		ProjectFinancialContractStatusOrder order = new ProjectFinancialContractStatusOrder();
		order.setContractStatus(ContractStatusEnum.DENY);
		order.setFormId(formInfo.getFormId());
		financialProjectContractService.changeStatus(order);
	}
	
	@Override
	public void selfEndProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		ProjectFinancialContractStatusOrder order = new ProjectFinancialContractStatusOrder();
		order.setContractStatus(ContractStatusEnum.END);
		order.setFormId(formInfo.getFormId());
		financialProjectContractService.changeStatus(order);
	}
	
	@Override
	public void deleteAfterProcess(FormInfo formInfo) {
		ProjectFinancialContractStatusOrder order = new ProjectFinancialContractStatusOrder();
		order.setContractStatus(ContractStatusEnum.DELETED);
		order.setFormId(formInfo.getFormId());
		financialProjectContractService.changeStatus(order);
	}
}
