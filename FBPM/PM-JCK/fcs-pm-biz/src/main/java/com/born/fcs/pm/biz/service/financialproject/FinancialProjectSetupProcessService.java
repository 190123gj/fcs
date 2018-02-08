package com.born.fcs.pm.biz.service.financialproject;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Maps;

import com.born.fcs.pm.biz.service.base.BaseProcessService;
import com.born.fcs.pm.dal.dataobject.FProjectFinancialDO;
import com.born.fcs.pm.dal.dataobject.FormDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.integration.bpm.result.WorkflowResult;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CouncilApplyStatusEnum;
import com.born.fcs.pm.ws.enums.CouncilTypeEnum;
import com.born.fcs.pm.ws.enums.FinancialProductTermTypeEnum;
import com.born.fcs.pm.ws.enums.FinancialProjectStatusEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectCouncilEnum;
import com.born.fcs.pm.ws.enums.ProjectCouncilStatusEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.financialproject.FProjectFinancialInfo;
import com.born.fcs.pm.ws.order.bpm.WorkflowStartOrder;
import com.born.fcs.pm.ws.order.common.SimpleFormOrder;
import com.born.fcs.pm.ws.order.common.WorkFlowBeforeProcessOrder;
import com.born.fcs.pm.ws.order.council.CouncilApplyOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.service.council.CouncilApplyService;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectService;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectSetupService;

@Service("financialProjectSetupProcessService")
public class FinancialProjectSetupProcessService extends BaseProcessService {
	
	@Autowired
	FinancialProjectSetupService financialProjectSetupService;
	
	@Autowired
	FinancialProjectService financialProjectService;
	
	@Autowired
	CouncilApplyService councilApplyService;
	
	@Override
	public Map<String, String> makeMessageVar(FormInfo formInfo) {
		Map<String, String> vars = Maps.newHashMap();
		FProjectFinancialInfo apply = financialProjectSetupService.queryByFormId(formInfo
			.getFormId());
		vars.put("项目编号", apply.getProjectCode());
		vars.put("产品名称", apply.getProductName());
		String customVar = "";
		if (formInfo.getStatus() == FormStatusEnum.APPROVAL) {
			customVar = "快去购买吧!";
		}
		vars.put("自定义信息", customVar);
		return vars;
	}
	
	@Override
	public FcsBaseResult startBeforeProcess(WorkFlowBeforeProcessOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			FProjectFinancialInfo project = financialProjectSetupService.queryByFormId(order
				.getFormInfo().getFormId());
			
			//自定义待办任务名称
			WorkflowStartOrder startOrder = (WorkflowStartOrder) FcsPmDomainHolder.get()
				.getAttribute("startOrder");
			if (startOrder != null) {
				startOrder.setCustomTaskName(project.getProductName() + "-理财项目立项申请");
			}
			result.setSuccess(true);
			
			FormDO form = (FormDO) FcsPmDomainHolder.get().getAttribute("formDO");
			if (form != null) {
				form.setRelatedProjectCode(project.getProjectCode());
			}
			
			try {
				SimpleFormOrder submitOrder = (SimpleFormOrder) FcsPmDomainHolder.get()
					.getAttribute("submitFormOrder");
				if (submitOrder != null)
					submitOrder.setRelatedProjectCode(project.getProjectCode());
				
			} catch (Exception e) {
				logger.error("获取提交Order出错：{}", e);
			}
			
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			logger.error("理财项目立项流程启动前置处理出错 ： {}", e);
		}
		return result;
	}
	
	//  保存的时候已经设置	
	//	@Override
	//	public void startAfterProcess(FormInfo formInfo, WorkflowResult workflowResult) {
	//		try {
	//			//保存业务经理到相关人员表
	//			ProjectRelatedUserOrder relatedUser = new ProjectRelatedUserOrder();
	//			relatedUser.setProjectCode(formInfo.getRelatedProjectCode());
	//			relatedUser.setUserType(ProjectRelatedUserTypeEnum.BUSI_MANAGER);
	//			relatedUser.setRemark("理财项目业务经理");
	//			relatedUser.setUserId(formInfo.getUserId());
	//			relatedUser.setUserAccount(formInfo.getUserAccount());
	//			relatedUser.setUserName(formInfo.getUserName());
	//			relatedUser.setUserEmail(formInfo.getUserEmail());
	//			relatedUser.setUserMobile(formInfo.getUserMobile());
	//			relatedUser.setDeptId(formInfo.getDeptId());
	//			relatedUser.setDeptCode(formInfo.getDeptCode());
	//			relatedUser.setDeptPath(formInfo.getDeptPath());
	//			relatedUser.setDeptPathName(formInfo.getDeptPathName());
	//			projectRelatedUserService.setRelatedUser(relatedUser);
	//		} catch (Exception e) {
	//			logger.error("理财项目立项流程启动后置处理出错：{}", e);
	//		}
	//	}
	
	@Override
	public void manualEndFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		FProjectFinancialInfo project = financialProjectSetupService.queryByFormId(formInfo
			.getFormId());
		//立项失败 
		financialProjectService.changeStatus(project.getProjectCode(),
			FinancialProjectStatusEnum.FAILED);
	}
	
	@Override
	public FcsBaseResult doNextBeforeProcess(WorkFlowBeforeProcessOrder order) {
		FcsBaseResult result = createResult();
		try {
			final Map<String, Object> customizeMap = order.getCustomizeMap();
			if (customizeMap != null) {
				logger.info("理财产品立项选择上会类型处理开始：{}", customizeMap);
				if ("YES".equals((String) customizeMap.get("chooseCouncil"))) { //选择是否上会及上会类型
					String councilType = (String) customizeMap.get("councilType");
					String isNeedMonthCouncil = (String) customizeMap.get("isNeedMonthCouncil");
					FProjectFinancialDO apply = FProjectFinancialDAO.findByFormId(order
						.getFormInfo().getFormId());
					if (BooleanEnum.NO.equals(isNeedMonthCouncil)) { //需要上母公司会议
						apply.setCouncilType(null);
					} else { //不需要上母公司会议
						apply.setCouncilType(councilType);
					}
					FProjectFinancialDAO.update(apply);
					logger.info("理财产品立项选择上会类型处理结束：{}", customizeMap);
				}
			}
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("选择上会类型出错");
			logger.error("理财产品立项选择上会类型出错 {} {}", order, e);
		}
		return result;
	}
	
	@Override
	public void endFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		
		try {
			FProjectFinancialDO project = FProjectFinancialDAO.findByFormId(formInfo.getFormId());
			logger.info("理财产品立项流程结束处理开始 {}", project);
			//短期理财产品直接可申请资金
			if (FinancialProductTermTypeEnum.SHORT_TERM.code().equals(project.getTermType())) {
				FcsBaseResult cResult = financialProjectService.changeStatus(
					project.getProjectCode(), FinancialProjectStatusEnum.CAPITAL_APPLY_WAITING);
				logger.info("短期理财产品直接修改成待购买状态  {}", cResult);
			} else {//中长期走上会流程
				ProjectCouncilEnum councilType = ProjectCouncilEnum.getByCode(project
					.getCouncilType());
				CouncilApplyOrder order = new CouncilApplyOrder();
				order.setCouncilCode(CouncilTypeEnum.GM_WORKING.code());
				order.setCouncilTypeDesc(CouncilTypeEnum.GM_WORKING.message());
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
				if (councilType != null) { //选择了上母公司会议
					order.setMotherCompanyApply(BooleanEnum.YES);
					if (councilType == ProjectCouncilEnum.SELF_MOTHER_GW) {//上母公司总经理办公会
						order.setMotherCouncilCode(CouncilTypeEnum.GM_WORKING.code());
					} else if (councilType == ProjectCouncilEnum.SELF_GW_MONTHER_PR) {
						order.setMotherCouncilCode(CouncilTypeEnum.PROJECT_REVIEW.code()); //上母公司项目评审会
					} else {
						order.setMotherCompanyApply(BooleanEnum.NO);
						project.setCouncilType(ProjectCouncilEnum.SELF_GW.code());
					}
				} else {
					order.setMotherCompanyApply(BooleanEnum.NO);
					project.setCouncilType(ProjectCouncilEnum.SELF_GW.code());
				}
				FcsBaseResult sResult = councilApplyService.saveCouncilApply(order);
				logger.info("中长期理财产品进入待上会列表 {}", sResult);
				if (sResult.isSuccess()) {
					//记录上会的申请ID
					project.setCouncilStatus(ProjectCouncilStatusEnum.COUNCIL_WAITING.code());
					project.setCouncilApplyId(sResult.getKeyId());
					FProjectFinancialDAO.update(project);
					
					//项目变成上会中
					FcsBaseResult cResult = financialProjectService.changeStatus(
						project.getProjectCode(), FinancialProjectStatusEnum.COUNCIL_WAITING);
					logger.info("中长期理财产品进入上会流程 {} {}", project.getCouncilType(), cResult);
				}
			}
			
		} catch (Exception e) {
			logger.error("理财产品立项流程结束处理出错：{}", e);
		}
	}
}
