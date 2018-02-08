package com.born.fcs.pm.biz.service.council;

import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.pm.biz.service.base.BaseProcessService;
import com.born.fcs.pm.dal.dataobject.ProjectDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.integration.bpm.result.WorkflowResult;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.common.ProjectRelatedUserInfo;
import com.born.fcs.pm.ws.info.council.FReCouncilApplyInfo;
import com.born.fcs.pm.ws.order.bpm.FlowVarField;
import com.born.fcs.pm.ws.order.bpm.WorkflowStartOrder;
import com.born.fcs.pm.ws.order.bpm.enums.FlowVarTypeEnum;
import com.born.fcs.pm.ws.order.common.ChangeProjectStatusOrder;
import com.born.fcs.pm.ws.order.common.WorkFlowBeforeProcessOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.service.common.ProjectRelatedUserService;
import com.born.fcs.pm.ws.service.common.ProjectService;
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
	
	@Override
	public List<FlowVarField> makeWorkFlowVar(FormInfo formInfo) {
		FReCouncilApplyInfo apply = recouncilApplyService.queryApplyByFormId(formInfo.getFormId());
		ProjectRelatedUserInfo riskManager = projectRelatedUserService.getRiskManager(apply
			.getProjectCode());
		List<FlowVarField> list = Lists.newArrayList();
		FlowVarField field = new FlowVarField();
		field.setVarName("riskManager");
		field.setVarType(FlowVarTypeEnum.STRING);
		if (riskManager != null) {
			field.setVarVal(String.valueOf(riskManager.getUserId()));
		} else {
			field.setVarVal("0");
		}
		list.add(field);
		return list;
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
			FReCouncilApplyInfo apply = recouncilApplyService.queryApplyByFormId(formInfo
				.getFormId());
			//启动后项目进入复议申请阶段
			ChangeProjectStatusOrder order = new ChangeProjectStatusOrder();
			order.setPhase(ProjectPhasesEnum.RE_COUNCIL_PHASES);
			order.setPhaseStatus(ProjectPhasesStatusEnum.AUDITING);
			order.setProjectCode(apply.getProjectCode());
			projectService.changeProjectStatus(order);
		} catch (Exception e) {
			logger.error("复议申请启动后置处理出错 ： {}", e);
		}
	}
	
	@Override
	public void endFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		try {
			final FReCouncilApplyInfo apply = recouncilApplyService.queryApplyByFormId(formInfo
				.getFormId());
			//复议申请通过后项目重新进入尽职调查阶段
			//			ChangeProjectStatusOrder order = new ChangeProjectStatusOrder();
			//			order.setPhase(ProjectPhasesEnum.INVESTIGATING_PHASES);
			//			order.setPhaseStatus(ProjectPhasesStatusEnum.WAITING);
			//			order.setProjectCode(apply.getProjectCode());
			//			order.setStatus(ProjectStatusEnum.NORMAL);
			//			projectService.changeProjectStatus(order);
			
			// 复制尽职调查表
			investigationService.copyInvestigation(apply.getProjectCode());
		} catch (Exception e) {
			logger.error("复议申请流程结束处理出错 ： {}", e);
		}
	}
	
	@Override
	public void manualEndFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		try {
			FReCouncilApplyInfo apply = recouncilApplyService.queryApplyByFormId(formInfo
				.getFormId());
			//复议申请通过后项目重新进入尽职调查阶段
			ChangeProjectStatusOrder order = new ChangeProjectStatusOrder();
			order.setPhase(ProjectPhasesEnum.RE_COUNCIL_PHASES);
			order.setPhaseStatus(ProjectPhasesStatusEnum.NOPASS);
			order.setProjectCode(apply.getProjectCode());
			order.setStatus(ProjectStatusEnum.FAILED);
			projectService.changeProjectStatus(order);
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
				project.setIsRecouncil(BooleanEnum.IS.code());
				project.setPhases(ProjectPhasesEnum.RE_COUNCIL_PHASES.code());
				project.setPhasesStatus(ProjectPhasesStatusEnum.WAITING.code());
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
