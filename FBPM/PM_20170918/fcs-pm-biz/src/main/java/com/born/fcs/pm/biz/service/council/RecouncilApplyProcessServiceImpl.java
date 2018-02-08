package com.born.fcs.pm.biz.service.council;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.pm.biz.service.base.BaseProcessService;
import com.born.fcs.pm.dal.dataobject.FReCouncilApplyDO;
import com.born.fcs.pm.dal.dataobject.ProjectDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.integration.bpm.result.WorkflowResult;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CompanyNameEnum;
import com.born.fcs.pm.ws.enums.CouncilApplyStatusEnum;
import com.born.fcs.pm.ws.enums.CouncilTypeEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.common.ProjectRelatedUserInfo;
import com.born.fcs.pm.ws.info.council.FReCouncilApplyInfo;
import com.born.fcs.pm.ws.order.bpm.FlowVarField;
import com.born.fcs.pm.ws.order.bpm.WorkflowStartOrder;
import com.born.fcs.pm.ws.order.bpm.enums.FlowVarTypeEnum;
import com.born.fcs.pm.ws.order.common.WorkFlowBeforeProcessOrder;
import com.born.fcs.pm.ws.order.council.CouncilApplyOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.service.common.ProjectRelatedUserService;
import com.born.fcs.pm.ws.service.common.ProjectService;
import com.born.fcs.pm.ws.service.council.CouncilApplyService;
import com.born.fcs.pm.ws.service.council.RecouncilApplyService;
import com.born.fcs.pm.ws.service.investigation.InvestigationService;

/**
 * 项目复议
 *
 *
 * @author wuzj
 *
 */
@Service("recouncilApplyProcessService")
public class RecouncilApplyProcessServiceImpl extends BaseProcessService {
	
	@Autowired
	ProjectService projectService;
	
	@Autowired
	RecouncilApplyService recouncilApplyService;
	
	@Autowired
	ProjectRelatedUserService projectRelatedUserService;
	
	@Autowired
	InvestigationService investigationService;
	
	@Autowired
	CouncilApplyService councilApplyService;
	
	@Override
	public List<FlowVarField> makeWorkFlowVar(FormInfo formInfo) {
		
		FReCouncilApplyInfo apply = recouncilApplyService.queryApplyByFormId(formInfo.getFormId());
		//ProjectInfo project = projectService.queryByCode(apply.getProjectCode(), false);
		
		//风险经理
		ProjectRelatedUserInfo riskManager = projectRelatedUserService.getRiskManager(apply
			.getProjectCode());
		List<FlowVarField> fields = Lists.newArrayList();
		FlowVarField field = new FlowVarField();
		field.setVarName("riskManager");
		field.setVarType(FlowVarTypeEnum.STRING);
		if (riskManager != null) {
			field.setVarVal(String.valueOf(riskManager.getUserId()));
		} else {
			field.setVarVal("0");
		}
		fields.add(field);
		
		//设置B角
		ProjectRelatedUserInfo busiManagerb = projectRelatedUserService.getBusiManagerb(apply
			.getProjectCode());
		FlowVarField managerBID = new FlowVarField();
		managerBID.setVarName("BManager");
		managerBID.setVarType(FlowVarTypeEnum.STRING);
		if (busiManagerb != null) {
			managerBID.setVarVal(busiManagerb.getUserId() + "");
		} else {
			managerBID.setVarVal("0");
		}
		fields.add(managerBID);
		
		//法务专员
		//		ProjectRelatedUserInfo legalManger = projectRelatedUserService.getLegalManager(apply
		//			.getProjectCode());
		//		FlowVarField lawManagerID = new FlowVarField();
		//		lawManagerID.setVarName("LawManagerID");
		//		lawManagerID.setVarType(FlowVarTypeEnum.STRING);
		//		if (legalManger != null) {
		//			lawManagerID.setVarVal(legalManger.getUserId() + "");
		//		} else {
		//			lawManagerID.setVarVal("0");
		//		}
		//		fields.add(lawManagerID);
		
		return fields;
	}
	
	@Override
	public FcsBaseResult startBeforeProcess(WorkFlowBeforeProcessOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			FReCouncilApplyInfo apply = recouncilApplyService.queryApplyByFormId(order
				.getFormInfo().getFormId());
			ProjectInfo project = projectService.queryByCode(apply.getProjectCode(), false);
			
			//自定义待办任务名称
			WorkflowStartOrder startOrder = (WorkflowStartOrder) FcsPmDomainHolder.get()
				.getAttribute("startOrder");
			if (startOrder != null) {
				startOrder.setCustomTaskName(project.getProjectName() + "-复议申请单");
			}
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			logger.error("复议申请启动前置处理出错 ： {}", e);
		}
		return result;
	}
	
	@Override
	public void startAfterProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		try {
			//			FReCouncilApplyInfo apply = recouncilApplyService.queryApplyByFormId(formInfo
			//				.getFormId());
			//			//启动后项目进入复议申请阶段
			//			ChangeProjectStatusOrder order = new ChangeProjectStatusOrder();
			//			//order.setPhase(ProjectPhasesEnum.RE_COUNCIL_PHASES);
			//			order.setPhaseStatus(ProjectPhasesStatusEnum.AUDITING);
			//			order.setProjectCode(apply.getProjectCode());
			//			projectService.changeProjectStatus(order);
		} catch (Exception e) {
			logger.error("复议申请启动后置处理出错 ： {}", e);
		}
	}
	
	@Override
	public void endFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		try {
			
			FReCouncilApplyDO apply = FReCouncilApplyDAO.findByFormId(formInfo.getFormId());
			ProjectDO project = projectDAO.findByProjectCode(apply.getProjectCode());
			//重新进入待上会列表
			CouncilApplyOrder order = new CouncilApplyOrder();
			order.setCouncilCode(CouncilTypeEnum.PROJECT_REVIEW.code());
			order.setCouncilTypeDesc(CouncilTypeEnum.PROJECT_REVIEW.message());
			order.setFormId(formInfo.getFormId());
			order.setProjectCode(apply.getProjectCode());
			order.setProjectName(project.getProjectName());
			order.setCustomerId(project.getCustomerId());
			order.setCustomerName(project.getCustomerName());
			order.setAmount(project.getAmount());
			order.setApplyManId(formInfo.getUserId());
			order.setApplyManName(formInfo.getUserName());
			order.setApplyDeptId(formInfo.getDeptId());
			order.setApplyDeptName(formInfo.getDeptName());
			order.setApplyTime(getSysdate());
			order.setStatus(CouncilApplyStatusEnum.WAIT.code());
			order.setTimeLimit(project.getTimeLimit());
			order.setTimeUnit(project.getTimeUnit());
			order.setCompanyName(CompanyNameEnum.NORMAL);
			order.setMotherCompanyApply(BooleanEnum.NO);
			order.setHasCompanyName(true);
			FcsBaseResult result = councilApplyService.saveCouncilApply(order);
			if (!result.isSuccess()) {
				logger.info("写入上会数据异常(复议)：{}", result);
			} else {
				//项目进入评审阶段
				//				project.setPhases(ProjectPhasesEnum.COUNCIL_PHASES.code());
				//				project.setPhasesStatus(ProjectPhasesStatusEnum.WAITING.code());
				//				project.setStatus(ProjectStatusEnum.NORMAL.code());
				//				projectDAO.update(project);
				logger.info("记录复议上会申请ID：", apply);
				apply.setCouncilBack(BooleanEnum.NO.code());
				apply.setCouncilApplyId(result.getKeyId());
				FReCouncilApplyDAO.update(apply);
			}
			
		} catch (Exception e) {
			logger.error("复议申请流程结束处理出错 ： {}", e);
		}
	}
	
	@Override
	public void manualEndFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		try {
			//			FReCouncilApplyInfo apply = recouncilApplyService.queryApplyByFormId(formInfo
			//				.getFormId());
			//			ProjectDO project = projectDAO.findByProjectCode(apply.getProjectCode());
			//			if (!BooleanEnum.IS.code().equals(project.getIsApproval())) {
			//				ChangeProjectStatusOrder order = new ChangeProjectStatusOrder();
			//				//order.setPhase(ProjectPhasesEnum.RE_COUNCIL_PHASES);
			//				order.setPhaseStatus(ProjectPhasesStatusEnum.NOPASS);
			//				order.setProjectCode(apply.getProjectCode());
			//				order.setStatus(ProjectStatusEnum.FAILED);
			//				projectService.changeProjectStatus(order);
			//			}
		} catch (Exception e) {
			logger.error("复议申请流程手动结束处理出错 ： {}", e);
		}
	}
	
	@Override
	public void deleteAfterProcess(FormInfo formInfo) {
		try {
			FReCouncilApplyInfo apply = recouncilApplyService.queryApplyByFormId(formInfo
				.getFormId());
			//删除后根据时间情况判断是否能再复议
			ProjectDO project = projectDAO.findByProjectCode(apply.getProjectCode());
			if (BooleanEnum.IS.code().equals(project.getIsApproval())) {
				//project.setIsRecouncil(BooleanEnum.IS.code());
				//project.setPhases(ProjectPhasesEnum.RE_COUNCIL_PHASES.code());
				project.setLastRecouncilTime(null);
				projectDAO.update(project);
			} else {
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.MONTH, -6);
				if (project.getApprovalTime().after(calendar.getTime())) {
					project.setIsRecouncil(BooleanEnum.IS.code());
					project.setLastRecouncilTime(null);
					projectDAO.update(project);
				}
				
			}
		} catch (Exception e) {
			logger.error("删除复议申请单处理出错 ： {}", e);
		}
	}
}
