package com.born.fcs.pm.biz.service.counterguarantee;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.pm.biz.service.base.BaseProcessService;
import com.born.fcs.pm.dal.daointerface.GuaranteeApplyCounterDAO;
import com.born.fcs.pm.dal.dataobject.FCounterGuaranteeReleaseDO;
import com.born.fcs.pm.dal.dataobject.GuaranteeApplyCounterDO;
import com.born.fcs.pm.dal.dataobject.ProjectDO;
import com.born.fcs.pm.dal.dataobject.ProjectIssueInformationDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.integration.bpm.result.WorkflowResult;
import com.born.fcs.pm.integration.bpm.service.client.user.SysUser;
import com.born.fcs.pm.util.CommonUtil;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CreditCheckStatusEnum;
import com.born.fcs.pm.ws.enums.FeeTypeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.PaymentMenthodEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.born.fcs.pm.ws.enums.ReleaseProjectStatusEnum;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.info.capitalappropriationapply.FCapitalAppropriationApplyInfo;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectRelatedUserInfo;
import com.born.fcs.pm.ws.info.counterguarantee.FCounterGuaranteeReleaseInfo;
import com.born.fcs.pm.ws.info.projectcreditcondition.ProjectCreditConditionInfo;
import com.born.fcs.pm.ws.order.bpm.FlowVarField;
import com.born.fcs.pm.ws.order.bpm.WorkflowStartOrder;
import com.born.fcs.pm.ws.order.bpm.enums.FlowVarTypeEnum;
import com.born.fcs.pm.ws.order.capitalappropriationapply.FCapitalAppropriationApplyQueryOrder;
import com.born.fcs.pm.ws.order.common.MessageOrder;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.order.common.WorkFlowBeforeProcessOrder;
import com.born.fcs.pm.ws.order.counterguarantee.CreditConditionReleaseOrder;
import com.born.fcs.pm.ws.order.projectcreditcondition.ProjectCreditConditionQueryOrder;
import com.born.fcs.pm.ws.order.release.UpdateReleaseStatusOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.capitalappropriationapply.FCapitalAppropriationApplyService;
import com.born.fcs.pm.ws.service.counterguarantee.CounterGuaranteeService;
import com.born.fcs.pm.ws.service.fund.ChargeNotificationService;
import com.born.fcs.pm.ws.service.projectcreditcondition.ProjectCreditConditionService;
import com.born.fcs.pm.ws.service.releaseproject.ReleaseProjectService;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;

/**
 * 到期解保流程处理
 * 
 * @author lirz
 * 
 * 2016-5-12 下午7:22:13
 */
@Service("counterGuaranteeProcessService")
public class CounterGuaranteeProcessServiceImpl extends BaseProcessService {
	
	@Autowired
	private CounterGuaranteeService counterGuaranteeService;
	@Autowired
	private ProjectCreditConditionService projectCreditConditionService;
	@Autowired
	private GuaranteeApplyCounterDAO guaranteeApplyCounterDAO;
	@Autowired
	private ReleaseProjectService releaseProjectService;
	@Autowired
	private ChargeNotificationService chargeNotificationService;
	@Autowired
	protected FCapitalAppropriationApplyService fCapitalAppropriationApplyService;
	
	@Override
	public List<FlowVarField> makeWorkFlowVar(FormInfo formInfo) {
		List<FlowVarField> fields = Lists.newArrayList();
		FCounterGuaranteeReleaseInfo info = counterGuaranteeService.findByFormId(formInfo
			.getFormId());
		if (null == info) {
			return fields;
		}
		
		if (ProjectUtil.isLitigation(info.getBusiType())) {
			//法务经理
			ProjectRelatedUserInfo legalManager = projectRelatedUserService.getLegalManager(info
				.getProjectCode());
			if (null != legalManager) {
				FlowVarField riskManagerId = new FlowVarField();
				riskManagerId.setVarName("LawManagerID");
				riskManagerId.setVarType(FlowVarTypeEnum.STRING);
				riskManagerId.setVarVal(legalManager.getUserId() + "");
				fields.add(riskManagerId);
			} else {
				logger.info("解保申请，没有找到法务经理：" + info.getProjectCode());
			}
		} else {
			//风险经理ID
			ProjectRelatedUserInfo riskManager = projectRelatedUserService.getRiskManager(info
				.getProjectCode());
			if (null != riskManager) {
				FlowVarField riskManagerId = new FlowVarField();
				riskManagerId.setVarName("RiskManagerID");
				riskManagerId.setVarType(FlowVarTypeEnum.STRING);
				riskManagerId.setVarVal(riskManager.getUserId() + "");
				fields.add(riskManagerId);
			} else {
				logger.info("解保申请，没有找到风险经理：" + info.getProjectCode());
			}
		}
		
		return fields;
	}
	
	@Override
	public FcsBaseResult startBeforeProcess(WorkFlowBeforeProcessOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		FCounterGuaranteeReleaseDO release = FCounterGuaranteeReleaseDAO.findByFormId(order
			.getFormInfo().getFormId());
		
		//自定义待办任务名称
		WorkflowStartOrder startOrder = (WorkflowStartOrder) FcsPmDomainHolder.get().getAttribute(
			"startOrder");
		if (startOrder != null) {
			startOrder.setCustomTaskName(release.getProjectName() + "-解保申请送审");
		}
		
		result.setSuccess(true);
		return result;
	}
	
	@Override
	public void startAfterProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		// 启动流程后业务处理
		FCounterGuaranteeReleaseDO release = FCounterGuaranteeReleaseDAO.findByFormId(formInfo
			.getFormId());
		Money releasingAmount = counterGuaranteeService.queryReleasingAmount(release
			.getProjectCode());
		ProjectDO project = projectDAO.findByProjectCodeForUpdate(release.getProjectCode());
		//更新解保中的金额
		project.setReleasingAmount(releasingAmount);
		projectDAO.update(project);
		
		if (ProjectUtil.isLitigation(project.getBusiType())) {
			UpdateReleaseStatusOrder updateReleaseOrder = new UpdateReleaseStatusOrder();
			updateReleaseOrder.setProjectCode(project.getProjectCode());
			updateReleaseOrder.setStatus(ReleaseProjectStatusEnum.RELEASING.code());
			releaseProjectService.update(updateReleaseOrder);
		}
	}
	
	@Override
	public void endFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		//审核通过后,将已解保金额更新到project表中
		FCounterGuaranteeReleaseDO release = FCounterGuaranteeReleaseDAO.findByFormId(formInfo
			.getFormId());
		ProjectDO project = projectDAO.findByProjectCodeForUpdate(release.getProjectCode());
		Money releasedAmount = counterGuaranteeService
			.queryReleasedAmount(release.getProjectCode());
		project.setReleasedAmount(releasedAmount);
		Money releasingAmount = counterGuaranteeService.queryReleasingAmount(release
			.getProjectCode());
		project.setReleasingAmount(releasingAmount);
		
		if (ProjectUtil.isLitigation(project.getBusiType())) {
			//诉保类
			UpdateReleaseStatusOrder updateReleaseOrder = new UpdateReleaseStatusOrder();
			updateReleaseOrder.setProjectCode(project.getProjectCode());
			updateReleaseOrder.setStatus(ReleaseProjectStatusEnum.FINISHED.code());
			releaseProjectService.update(updateReleaseOrder);
			
			project.setPhases(ProjectPhasesEnum.FINISH_PHASES.code());
			project.setStatus(ProjectStatusEnum.FINISH.code());
			//诉保解保审核通过，写入endTime授信结束时间
			project.setEndTime(getSysdate());
		} else if (ProjectUtil.isBond(project.getBusiType())) {
			//发债类
			if (StringUtil.equals(BooleanEnum.IS.code(), project.getIsContinue())) {
				//手动结束
				if (!project.getReleasableAmount().equals(ZERO_MONEY)
					&& project.getReleasableAmount().equals(project.getReleasedAmount())) {
					project.setPhases(ProjectPhasesEnum.FINISH_PHASES.code());
					project.setStatus(ProjectStatusEnum.FINISH.code());
				}
			} else {
				ProjectIssueInformationDO issue = projectIssueInformationDAO.findByProjectCode(
					project.getProjectCode()).get(0);
				if (issue.getAmount().equals(project.getReleasedAmount())) {
					project.setPhases(ProjectPhasesEnum.FINISH_PHASES.code());
					project.setStatus(ProjectStatusEnum.FINISH.code());
				}
			}
		} else if (!BooleanEnum.IS.code().equals(project.getIsMaximumAmount())) {
			//非最高授信，全部解保，则为完成
			if (project.getAmount().equals(project.getReleasedAmount())) {
				project.setPhases(ProjectPhasesEnum.FINISH_PHASES.code());
				project.setStatus(ProjectStatusEnum.FINISH.code());
			}
		}
		
		// ------------------------------------------------解保后发消息--------------------------------------------------
		//存入保证金是：收费通知单中的客户保证金(存入保证金总和)(减去)资金划付申请单中退还客户保证金的总和
		//存入保证金
		Money guaranteeDeposit = chargeNotificationService.queryChargeTotalAmount(
			project.getProjectCode(), FeeTypeEnum.GUARANTEE_DEPOSIT).getOther();
		//资金划付申请单中退还客户保证金
		FCapitalAppropriationApplyQueryOrder queryOrder = new FCapitalAppropriationApplyQueryOrder();
		queryOrder.setProjectCode(project.getProjectCode());
		queryOrder.setFormStatus(FormStatusEnum.APPROVAL.code());
		List<String> appropriateReasonList = new ArrayList<>();
		appropriateReasonList.add(PaymentMenthodEnum.CUSTOMER_DEPOSIT_REFUND.code());
		queryOrder.setAppropriateReasonList(appropriateReasonList);
		QueryBaseBatchResult<FCapitalAppropriationApplyInfo> batchResult1 = fCapitalAppropriationApplyService
			.query(queryOrder);
		List<FCapitalAppropriationApplyInfo> listApplyInfo = batchResult1.getPageList();
		Money customerDeposit = Money.zero();//保证金划回
		for (FCapitalAppropriationApplyInfo fCapitalAppropriationApplyInfo : listApplyInfo) {
			customerDeposit = customerDeposit.add(fCapitalAppropriationApplyInfo
				.getAppropriateAmount());
		}
		Money m1 = guaranteeDeposit.subtract(customerDeposit);
		
		//存出保证金：资金划付申请单中的存出保证金(减去)收费通知单的存出保证金划回
		//资金划付申请单中的存出保证金
		queryOrder = new FCapitalAppropriationApplyQueryOrder();
		queryOrder.setProjectCode(project.getProjectCode());
		queryOrder.setFormStatus(FormStatusEnum.APPROVAL.code());
		appropriateReasonList = new ArrayList<>();
		appropriateReasonList.add(PaymentMenthodEnum.DEPOSIT_PAID.code());
		queryOrder.setAppropriateReasonList(appropriateReasonList);
		batchResult1 = fCapitalAppropriationApplyService
			.query(queryOrder);
		listApplyInfo = batchResult1.getPageList();
		customerDeposit = Money.zero();//保证金划回
		for (FCapitalAppropriationApplyInfo fCapitalAppropriationApplyInfo : listApplyInfo) {
			customerDeposit = customerDeposit.add(fCapitalAppropriationApplyInfo
				.getAppropriateAmount());
		}
		//收费通知单的存出保证金划回
		guaranteeDeposit = chargeNotificationService.queryChargeTotalAmount(
			project.getProjectCode(), FeeTypeEnum.REFUNDABLE_DEPOSITS_DRAW_BACK).getOther();
		Money m2 = customerDeposit.subtract(guaranteeDeposit);
		
		if (m1.greaterThan(ZERO_MONEY) || m2.greaterThan(ZERO_MONEY)) {
			List<SysUser> roleUsers = new ArrayList<>();
			String roleName = sysParameterService
				.getSysParameterValue(SysParamEnum.SYS_PARAM_CWYSG_ROLE_NAME.code());
			//财务应收岗
			List<SysUser> roleUsers1 = bpmUserQueryService.findUserByRoleAlias(roleName);
			if (ListUtil.isNotEmpty(roleUsers1)) {
				roleUsers.addAll(roleUsers1);
			}
			roleName = sysParameterService
				.getSysParameterValue(SysParamEnum.SYS_PARAM_CWYFG_ROLE_NAME.code());
			//财务应付岗
			roleUsers1 = bpmUserQueryService.findUserByRoleAlias(roleName);
			if (ListUtil.isNotEmpty(roleUsers1)) {
				roleUsers.addAll(roleUsers1);
			}
			
			SimpleUserInfo[] users = new SimpleUserInfo[roleUsers.size()];
			int i = 0;
			for (SysUser sendUser : roleUsers) {
				SimpleUserInfo userInfo = new SimpleUserInfo();
				userInfo.setUserId(sendUser.getUserId());
				userInfo.setUserName(sendUser.getFullname());
				userInfo.setUserAccount(sendUser.getAccount());
				userInfo.setEmail(sendUser.getEmail());
				userInfo.setMobile(sendUser.getMobile());
				users[i++] = userInfo;
			}
			
			MessageOrder messageOrder = MessageOrder.newSystemMessageOrder();
			StringBuilder sb = new StringBuilder();
			sb.append("项目编号：").append(project.getProjectCode()).append("，客户名称：")
				.append(project.getCustomerName()).append("解保申请单审核已通过，该项目有客户存入保证金")
				.append(m1.toStandardString()).append("元，存出保证金")
				.append(m2.toStandardString()).append("元。");
			messageOrder.setMessageContent(sb.toString());
			messageOrder.setSendUsers(users);
			siteMessageService.addMessageInfo(messageOrder);
			
			//需要给业务经理发消息
			ProjectRelatedUserInfo busiManager = projectRelatedUserService.getBusiManager(project
				.getProjectCode());
			if (null != busiManager) {
				SimpleUserInfo[] users2 = new SimpleUserInfo[1];
				users2[0] = busiManager.toSimpleUserInfo();
				MessageOrder messageOrder2 = MessageOrder.newSystemMessageOrder();
				messageOrder2.setMessageContent(sb.toString());
				messageOrder2.setSendUsers(users2);
				siteMessageService.addMessageInfo(messageOrder2);
			}
		}
		// ------------------------------------------------解保后发消息--------------------------------------------------
		
		if (!ProjectUtil.isLitigation(project.getBusiType())) {
			//业务经理
			ProjectRelatedUserInfo busiManager = projectRelatedUserService.getBusiManager(project
				.getProjectCode());
			if (null != busiManager) {
				//项目名称项目编码，该项目的解保申请单审核通过，是否需要权利凭证出库？点击处理；
				StringBuilder sb = new StringBuilder();
				String url = CommonUtil.getRedirectUrl("/projectMg/file/detailFileList.htm");
				sb.append(project.getProjectName()).append("(").append(project.getProjectCode())
				.append(")").append("，该项目的解保申请单审核通过，是否需要权利凭证出库？").append("<a href='").append(url)
				.append("'>点击处理</a>");
				SimpleUserInfo[] users = new SimpleUserInfo[1];
				users[0] = busiManager.toSimpleUserInfo();
				MessageOrder messageOrder = MessageOrder.newSystemMessageOrder();
				messageOrder.setMessageContent(sb.toString());
				messageOrder.setSendUsers(users);
				siteMessageService.addMessageInfo(messageOrder);
			}
		}
		
		projectDAO.update(project);
		
	}
	
	@Override
	public void manualEndFlowProcess(FormInfo formInfo, WorkflowResult workflowResult) {
		// 手动结束流程后业务处理
		FCounterGuaranteeReleaseDO release = FCounterGuaranteeReleaseDAO.findByFormId(formInfo
			.getFormId());
		ProjectCreditConditionQueryOrder queryOrder = new ProjectCreditConditionQueryOrder();
		queryOrder.setStatus(CreditCheckStatusEnum.CHECK_PASS.code());
		queryOrder.setProjectCode(release.getProjectCode());
		queryOrder.setPageNumber(1L);
		queryOrder.setPageSize(999L);
		QueryBaseBatchResult<ProjectCreditConditionInfo> batchResult = projectCreditConditionService
			.queryCreditCondition(queryOrder);
		if (ListUtil.isNotEmpty(batchResult.getPageList())) {
			//取消选择的反担保措施
			for (ProjectCreditConditionInfo info : batchResult.getPageList()) {
				if (info.getReleaseId() == release.getId()) {
					//保存审核不通过的历史记录
					GuaranteeApplyCounterDO doObj = new GuaranteeApplyCounterDO();
					doObj.setFormId(formInfo.getFormId());
					doObj.setProjectCode(release.getProjectCode());
					doObj.setItemDesc(info.getItemDesc());
					doObj.setReleaseId(info.getId());
					doObj.setReleaseGist(info.getReleaseGist());
					doObj.setReleaseReason(info.getReleaseReason());
					guaranteeApplyCounterDAO.insert(doObj);
				}
				CreditConditionReleaseOrder order = new CreditConditionReleaseOrder();
				order.setId(info.getId());
				order.setReleaseId(release.getId());
				projectCreditConditionService.saveCreditConditionRelease(order);
			}
		}
		//审核不通过,更新解保中的金额到project中
		Money releasingAmount = counterGuaranteeService.queryReleasingAmount(release
			.getProjectCode());
		ProjectDO project = projectDAO.findByProjectCodeForUpdate(release.getProjectCode());
		//更新解保中的金额
		project.setReleasingAmount(releasingAmount);
		projectDAO.update(project);
		
		if (ProjectUtil.isLitigation(project.getBusiType())) {
			//被中止后，可以重新申请
			UpdateReleaseStatusOrder updateReleaseOrder = new UpdateReleaseStatusOrder();
			updateReleaseOrder.setProjectCode(project.getProjectCode());
			updateReleaseOrder.setStatus(ReleaseProjectStatusEnum.AVAILABLE.code());
			releaseProjectService.update(updateReleaseOrder);
		}
	}
}
