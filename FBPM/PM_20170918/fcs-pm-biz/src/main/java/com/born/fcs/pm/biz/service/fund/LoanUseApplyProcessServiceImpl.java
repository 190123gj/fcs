package com.born.fcs.pm.biz.service.fund;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseProcessService;
import com.born.fcs.pm.dal.dataobject.ProjectDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.integration.bpm.result.WorkflowResult;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.ws.enums.ChargePhaseEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.LoanUseApplyTypeEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesStatusEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.common.ProjectRelatedUserInfo;
import com.born.fcs.pm.ws.info.common.ProjectSimpleDetailInfo;
import com.born.fcs.pm.ws.info.fund.FLoanUseApplyInfo;
import com.born.fcs.pm.ws.order.bpm.FlowVarField;
import com.born.fcs.pm.ws.order.bpm.WorkflowStartOrder;
import com.born.fcs.pm.ws.order.bpm.enums.FlowVarTypeEnum;
import com.born.fcs.pm.ws.order.common.ChangeProjectStatusOrder;
import com.born.fcs.pm.ws.order.common.WorkFlowBeforeProcessOrder;
import com.born.fcs.pm.ws.order.expireproject.MessageAlertOrder;
import com.born.fcs.pm.ws.order.fund.LoanUseApplyFormInfo;
import com.born.fcs.pm.ws.order.fund.LoanUseApplyQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.common.ProjectRelatedUserService;
import com.born.fcs.pm.ws.service.common.ProjectService;
import com.born.fcs.pm.ws.service.expireproject.MessageAlertService;
import com.born.fcs.pm.ws.service.fund.LoanUseApplyService;
import com.yjf.common.lang.util.ListUtil;

@Service("loanUseApplyProcessService")
public class LoanUseApplyProcessServiceImpl extends BaseProcessService {
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private LoanUseApplyService loanUseApplyService;
	
	@Autowired
	private ProjectRelatedUserService projectRelatedUserService;
	
	@Autowired
	private MessageAlertService messageAlertService;
	
	@Override
	public FcsBaseResult startBeforeProcess(WorkFlowBeforeProcessOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		try {
			FLoanUseApplyInfo apply = loanUseApplyService.queryByFormId(order.getFormInfo()
				.getFormId());
			
			ProjectInfo project = projectService.queryByCode(apply.getProjectCode(), false);
			
			//自定义待办任务名称
			WorkflowStartOrder startOrder = (WorkflowStartOrder) FcsPmDomainHolder.get()
				.getAttribute("startOrder");
			if (startOrder != null) {
				startOrder.setCustomTaskName(project.getProjectName() + "-放用款申请单");
			}
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
			logger.error("放用款申请流程启动前置处理出错 ： {}", e);
		}
		return result;
	}
	
	@Override
	public void startAfterProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		try {
			
			//查询所有已经提交的放用款单子
			LoanUseApplyQueryOrder qOrder = new LoanUseApplyQueryOrder();
			qOrder.setProjectCode(formInfo.getRelatedProjectCode());
			List<String> statusList = Lists.newArrayList();
			statusList.add(FormStatusEnum.SUBMIT.code());
			statusList.add(FormStatusEnum.AUDITING.code());
			statusList.add(FormStatusEnum.CANCEL.code());
			statusList.add(FormStatusEnum.BACK.code());
			statusList.add(FormStatusEnum.DENY.code());
			statusList.add(FormStatusEnum.APPROVAL.code());
			qOrder.setPageSize(999);
			QueryBaseBatchResult<LoanUseApplyFormInfo> allForm = loanUseApplyService
				.searchApplyForm(qOrder);
			
			//是否存在  已经上传了的回执的单子
			int applyCount = 0;
			if (ListUtil.isNotEmpty(allForm.getPageList())) {
				applyCount = allForm.getPageList().size();
			}
			
			if (applyCount == 1 && formInfo.getFormId() == allForm.getPageList().get(0).getFormId()) { //第一次放用款进入放用款阶段
				ChangeProjectStatusOrder cOrder = new ChangeProjectStatusOrder();
				cOrder.setPhases(ProjectPhasesEnum.LOAN_USE_PHASES);
				cOrder.setPhaseStatus(ProjectPhasesStatusEnum.APPROVAL);
				cOrder.setProjectCode(formInfo.getRelatedProjectCode());
				projectService.changeProjectStatus(cOrder);
			}
			
		} catch (Exception e) {
			logger.error("放用款流程启动后处理出错：{}", e);
		}
	}
	
	@Override
	public List<FlowVarField> makeWorkFlowVar(FormInfo formInfo) {
		
		FLoanUseApplyInfo apply = loanUseApplyService.queryByFormId(formInfo.getFormId());
		
		List<FlowVarField> fields = Lists.newArrayList();
		if (apply != null) {
			
			//查询风险经理
			ProjectRelatedUserInfo riskManager = projectRelatedUserService.getRiskManager(apply
				.getProjectCode());
			
			if (riskManager == null) {
				throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "风险经理不存在");
			}
			
			//风险经理
			FlowVarField flowVarField = new FlowVarField();
			flowVarField.setVarName("riskManager");
			flowVarField.setVarType(FlowVarTypeEnum.STRING);
			flowVarField.setVarVal(String.valueOf(riskManager.getUserId()));
			fields.add(flowVarField);
			
			//判断先收后扣
			ProjectSimpleDetailInfo project = projectService.querySimpleDetailInfo(apply
				.getProjectCode());
			if (project != null) {
				//先收或者后扣
				FlowVarField chargePhase = new FlowVarField();
				chargePhase.setVarName("xianShou");
				chargePhase.setVarType(FlowVarTypeEnum.STRING);
				String xianShou = "N";
				if (project.getChargePhase() == ChargePhaseEnum.BEFORE) {
					xianShou = "Y";
				}
				chargePhase.setVarVal(xianShou);
				fields.add(chargePhase);
			}
		}
		
		return fields;
	}
	
	//同步执行
	@Override
	public void endFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		//申请单
		FLoanUseApplyInfo apply = loanUseApplyService.queryByFormId(formInfo.getFormId());
		ProjectDO projectDO = projectDAO.findByProjectCodeForUpdate(apply.getProjectCode());
		if (apply.getApplyType() == LoanUseApplyTypeEnum.USE
			|| apply.getApplyType() == LoanUseApplyTypeEnum.BOTH) {
			synchronized (this) {
				//加锁 修改项目放用款金额
				projectDO.getUsedAmount().addTo(apply.getAmount());
				projectDAO.update(projectDO);
			}
		}
		if (!ProjectPhasesEnum.FINISH_PHASES.code().equals(projectDO.getPhases())
			&& !ProjectUtil.isUnderwriting(projectDO.getBusiType())
			&& !ProjectUtil.isLitigation(projectDO.getBusiType())) {
			if (apply.getApplyType() == LoanUseApplyTypeEnum.LOAN
				|| apply.getApplyType() == LoanUseApplyTypeEnum.BOTH) {
				MessageAlertOrder order = new MessageAlertOrder();
				order.setProjectCode(apply.getProjectCode());
				order.setCustomerName(projectDO.getCustomerName());
				order.setAlertPhrase(ProjectPhasesEnum.AFTERWARDS_PHASES.code());
				messageAlertService.add(order);
			}
		}
	}
}
