package com.born.fcs.pm.biz.service.check;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.pm.biz.service.base.BaseProcessService;
import com.born.fcs.pm.dal.dataobject.FAfterwardsCheckDO;
import com.born.fcs.pm.dal.dataobject.FAfterwardsCheckLitigationDO;
import com.born.fcs.pm.dal.dataobject.ProjectDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.integration.bpm.result.WorkflowResult;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CaseStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectRelatedUserInfo;
import com.born.fcs.pm.ws.order.bpm.FlowVarField;
import com.born.fcs.pm.ws.order.bpm.WorkflowStartOrder;
import com.born.fcs.pm.ws.order.bpm.enums.FlowVarTypeEnum;
import com.born.fcs.pm.ws.order.common.ChangeProjectStatusOrder;
import com.born.fcs.pm.ws.order.common.WorkFlowBeforeProcessOrder;
import com.born.fcs.pm.ws.order.expireproject.MessageAlertOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.common.ProjectResult;
import com.born.fcs.pm.ws.service.expireproject.MessageAlertService;

/**
 * 
 * 保后检查报告流程处理
 * 
 * @author lirz
 * 
 * 2016-7-7 下午2:38:01
 */
@Service("afterwardsCheckProcessService")
public class AfterwardsCheckProcessServiceImpl extends BaseProcessService {
	
	@Autowired
	private MessageAlertService messageAlertService;
	
	@Override
	public List<FlowVarField> makeWorkFlowVar(FormInfo formInfo) {
		List<FlowVarField> fields = Lists.newArrayList();
		FAfterwardsCheckLitigationDO litigation = FAfterwardsCheckLitigationDAO
			.findByFormId(formInfo.getFormId());
		if (null != litigation) {
			FlowVarField lawManagerID = new FlowVarField();
			lawManagerID.setVarName("LawManagerID");
			lawManagerID.setVarType(FlowVarTypeEnum.STRING);
			lawManagerID.setVarVal("0");
			fields.add(lawManagerID);
			//法务
			ProjectRelatedUserInfo legalManger = projectRelatedUserService
				.getLegalManager(litigation.getProjectCode());
			if (null != legalManger) {
				lawManagerID.setVarVal(legalManger.getUserId() + "");
			}
		}
		
		return fields;
	}
	
	@Override
	public FcsBaseResult startBeforeProcess(WorkFlowBeforeProcessOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		FAfterwardsCheckDO checkDO = FAfterwardsCheckDAO.findByFormId(order.getFormInfo()
			.getFormId());
		
		//自定义待办任务名称
		WorkflowStartOrder startOrder = (WorkflowStartOrder) FcsPmDomainHolder.get().getAttribute(
			"startOrder");
		if (startOrder != null) {
			startOrder.setCustomTaskName(checkDO.getProjectName() + "-保后检查报告审核");
		}
		result.setSuccess(true);
		return result;
	}
	
	@Override
	public void startAfterProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		// 启动流程后业务处理
		FAfterwardsCheckDO checkDO = FAfterwardsCheckDAO.findByFormId(formInfo.getFormId());
		ChangeProjectStatusOrder changeOrder = new ChangeProjectStatusOrder();
		changeOrder.setPhase(ProjectPhasesEnum.AFTERWARDS_PHASES);
		changeOrder.setPhaseStatus(ProjectPhasesStatusEnum.AUDITING);
		changeOrder.setProjectCode(checkDO.getProjectCode());
		changeOrder.setStatus(ProjectStatusEnum.NORMAL);
		ProjectResult presult = projectService.changeProjectStatus(changeOrder);
		if (!presult.isSuccess()) {
			logger.error("更新项目状态出错(提交保后检查报告)：" + presult.getMessage());
		}
	}
	
	@Override
	public void endFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		long formId = formInfo.getFormId();
		FAfterwardsCheckLitigationDO litigation = FAfterwardsCheckLitigationDAO
			.findByFormId(formId);
		if (null != litigation) {
			CaseStatusEnum caseStatus = CaseStatusEnum.getByCode(litigation.getCaseStatus());
			if (caseStatus == CaseStatusEnum.WIN || caseStatus == CaseStatusEnum.WITHDRAW) {
				//胜诉/客户撤诉 项目结束
				ProjectDO project = projectDAO.findByProjectCode(litigation.getProjectCode());
				//项目进入尽职调查阶段
				project.setPhases(ProjectPhasesEnum.FINISH_PHASES.code());
				project.setStatus(ProjectStatusEnum.NORMAL.code());
				project.setPhasesStatus(ProjectPhasesStatusEnum.WAITING.code());
				project.setEndTime(getSysdate());
				projectDAO.update(project);
			}
		}
		
		//首次风险等级评定与保后同步
		FAfterwardsCheckDO checkDO = FAfterwardsCheckDAO.findByFormId(formInfo.getFormId());
		ProjectDO projectDO = projectDAO.findByProjectCode(checkDO.getProjectCode());
		if (!ProjectPhasesEnum.FINISH_PHASES.code().equals(projectDO.getPhases())) {
			MessageAlertOrder order = new MessageAlertOrder();
			order.setProjectCode(checkDO.getProjectCode());
			order.setCustomerName(projectDO.getCustomerName());
			order.setAlertPhrase("RISK_LEVEL");
			messageAlertService.add(order);
		}
		
		FAfterwardsCheckDO FAfterwardsCheck = new FAfterwardsCheckDO();
		FAfterwardsCheck.setProjectCode(checkDO.getProjectCode());
		FAfterwardsCheckDAO.updateIsLasetByProjectCode(FAfterwardsCheck);
		
		checkDO.setIsLastest(BooleanEnum.YES.code());
		FAfterwardsCheckDAO.update(checkDO);
	}
	
}
