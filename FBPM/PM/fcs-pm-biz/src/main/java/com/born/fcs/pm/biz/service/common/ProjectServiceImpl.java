package com.born.fcs.pm.biz.service.common;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import rop.thirdparty.com.google.common.collect.Lists;
import rop.thirdparty.com.google.common.collect.Maps;

import com.born.fcs.fm.ws.enums.ForecastFeeTypeEnum;
import com.born.fcs.fm.ws.enums.ForecastTypeEnum;
import com.born.fcs.fm.ws.enums.FundDirectionEnum;
import com.born.fcs.fm.ws.order.forecast.ForecastAccountOrder;
import com.born.fcs.fm.ws.service.forecast.ForecastService;
import com.born.fcs.pm.biz.convert.DoConvert;
import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.exception.FcsPmBizException;
import com.born.fcs.pm.biz.job.AsynchronousTaskJob;
import com.born.fcs.pm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.pm.biz.service.council.CouncilSummaryProcessServiceImpl;
import com.born.fcs.pm.dal.dataobject.CommonAttachmentDO;
import com.born.fcs.pm.dal.dataobject.CouncilApplyDO;
import com.born.fcs.pm.dal.dataobject.CouncilProjectDO;
import com.born.fcs.pm.dal.dataobject.FChargeNotificationDO;
import com.born.fcs.pm.dal.dataobject.FCouncilSummaryProjectDO;
import com.born.fcs.pm.dal.dataobject.FCouncilSummaryRiskHandleDO;
import com.born.fcs.pm.dal.dataobject.FormDO;
import com.born.fcs.pm.dal.dataobject.ProjectChannelRelationDO;
import com.born.fcs.pm.dal.dataobject.ProjectCreditConditionDO;
import com.born.fcs.pm.dal.dataobject.ProjectDO;
import com.born.fcs.pm.dal.dataobject.ProjectIssueInformationDO;
import com.born.fcs.pm.dal.dataobject.ProjectRelatedUserDO;
import com.born.fcs.pm.dal.dataobject.ReleaseProjectDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.domain.exception.FcsPmDomainException;
import com.born.fcs.pm.integration.bpm.BpmUserQueryService;
import com.born.fcs.pm.integration.common.PropertyConfigService;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.ChannelRelationEnum;
import com.born.fcs.pm.ws.enums.ChargePhaseEnum;
import com.born.fcs.pm.ws.enums.ChargeTypeEnum;
import com.born.fcs.pm.ws.enums.CommonAttachmentTypeEnum;
import com.born.fcs.pm.ws.enums.CompareEnum;
import com.born.fcs.pm.ws.enums.ContractStatusEnum;
import com.born.fcs.pm.ws.enums.CouncilApplyStatusEnum;
import com.born.fcs.pm.ws.enums.CreditCheckStatusEnum;
import com.born.fcs.pm.ws.enums.CustomerTypeEnum;
import com.born.fcs.pm.ws.enums.ExeStatusEnum;
import com.born.fcs.pm.ws.enums.FeeTypeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.LoanUseApplyTypeEnum;
import com.born.fcs.pm.ws.enums.OneVoteResultEnum;
import com.born.fcs.pm.ws.enums.PaymentMenthodEnum;
import com.born.fcs.pm.ws.enums.ProjectContractTypeEnum;
import com.born.fcs.pm.ws.enums.ProjectCouncilCompereMessageEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectRecoveryStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectVoteResultEnum;
import com.born.fcs.pm.ws.enums.ReleaseProjectStatusEnum;
import com.born.fcs.pm.ws.enums.ReleaseStatusEnum;
import com.born.fcs.pm.ws.enums.SellStatusEnum;
import com.born.fcs.pm.ws.enums.SysDateSeqNameEnum;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.born.fcs.pm.ws.enums.SystemEnum;
import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.FormRelatedUserInfo;
import com.born.fcs.pm.ws.info.common.ProjectApprovaInfo;
import com.born.fcs.pm.ws.info.common.ProjectBondDetailInfo;
import com.born.fcs.pm.ws.info.common.ProjectChannelRelationInfo;
import com.born.fcs.pm.ws.info.common.ProjectEntrustedDetailInfo;
import com.born.fcs.pm.ws.info.common.ProjectGuaranteeDetailInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.common.ProjectLgLitigationDetailInfo;
import com.born.fcs.pm.ws.info.common.ProjectRelatedUserInfo;
import com.born.fcs.pm.ws.info.common.ProjectSimpleDetailInfo;
import com.born.fcs.pm.ws.info.common.ProjectUnderwritingDetailInfo;
import com.born.fcs.pm.ws.info.contract.ProjectContractResultInfo;
import com.born.fcs.pm.ws.info.council.CouncilProjectVoteResultInfo;
import com.born.fcs.pm.ws.info.council.CouncilSummaryRiskHandleInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectBondInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectEntrustedInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectGuaranteeInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectLgLitigationInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectUnderwritingInfo;
import com.born.fcs.pm.ws.info.counterguarantee.ReleaseApplyInfo;
import com.born.fcs.pm.ws.info.financeaffirm.ProjectChargePayInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationCreditSchemeInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationLitigationInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationUnderwritingInfo;
import com.born.fcs.pm.ws.info.fund.FChargeNotificationFeeInfo;
import com.born.fcs.pm.ws.info.fund.FChargeNotificationResultInfo;
import com.born.fcs.pm.ws.info.projectcreditcondition.FCreditConditionConfirmInfo;
import com.born.fcs.pm.ws.info.projectcreditcondition.ProjectCreditConditionInfo;
import com.born.fcs.pm.ws.info.projectissueinformation.ProjectIssueInformationInfo;
import com.born.fcs.pm.ws.info.revovery.ProjectRecoveryListInfo;
import com.born.fcs.pm.ws.order.common.ChangeProjectStatusOrder;
import com.born.fcs.pm.ws.order.common.FormRelatedUserQueryOrder;
import com.born.fcs.pm.ws.order.common.MessageOrder;
import com.born.fcs.pm.ws.order.common.ProjectApprovalQueryOrder;
import com.born.fcs.pm.ws.order.common.ProjectOrder;
import com.born.fcs.pm.ws.order.common.ProjectQueryOrder;
import com.born.fcs.pm.ws.order.common.SendMailOrder;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.order.common.TransferProjectOrder;
import com.born.fcs.pm.ws.order.contract.ProjectContractQueryOrder;
import com.born.fcs.pm.ws.order.council.CouncilSummaryRiskHandleQueryOrder;
import com.born.fcs.pm.ws.order.council.CouncilVoteProjectQueryOrder;
import com.born.fcs.pm.ws.order.counterguarantee.CounterGuaranteeQueryOrder;
import com.born.fcs.pm.ws.order.financeaffirm.ProjectChargePayQueryOrder;
import com.born.fcs.pm.ws.order.fund.FChargeNotificationQueryOrder;
import com.born.fcs.pm.ws.order.fund.LoanUseApplyFormInfo;
import com.born.fcs.pm.ws.order.fund.LoanUseApplyQueryOrder;
import com.born.fcs.pm.ws.order.project.ProjectRedoOrder;
import com.born.fcs.pm.ws.order.project.UpdateProjectBaseOrder;
import com.born.fcs.pm.ws.order.projectcreditcondition.FCreditConditionConfirmQueryOrder;
import com.born.fcs.pm.ws.order.recovery.ProjectRecoveryQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.common.ProjectBatchResult;
import com.born.fcs.pm.ws.result.common.ProjectCandoResult;
import com.born.fcs.pm.ws.result.common.ProjectResult;
import com.born.fcs.pm.ws.service.common.FormRelatedUserService;
import com.born.fcs.pm.ws.service.common.FormService;
import com.born.fcs.pm.ws.service.common.ProjectChannelRelationService;
import com.born.fcs.pm.ws.service.common.ProjectRelatedUserService;
import com.born.fcs.pm.ws.service.common.ProjectService;
import com.born.fcs.pm.ws.service.common.ProjectTransferService;
import com.born.fcs.pm.ws.service.contract.ProjectContractService;
import com.born.fcs.pm.ws.service.council.CouncilProjectService;
import com.born.fcs.pm.ws.service.council.CouncilSummaryService;
import com.born.fcs.pm.ws.service.counterguarantee.CounterGuaranteeService;
import com.born.fcs.pm.ws.service.expireproject.ExpireProjectService;
import com.born.fcs.pm.ws.service.financeaffirm.FinanceAffirmService;
import com.born.fcs.pm.ws.service.fund.ChargeNotificationService;
import com.born.fcs.pm.ws.service.fund.LoanUseApplyService;
import com.born.fcs.pm.ws.service.investigation.InvestigationService;
import com.born.fcs.pm.ws.service.projectcreditcondition.ProjectCreditConditionService;
import com.born.fcs.pm.ws.service.projectissueinformation.ProjectIssueInformationService;
import com.born.fcs.pm.ws.service.recovery.ProjectRecoveryService;
import com.born.fcs.pm.ws.service.report.PmReportService;
import com.born.fcs.pm.ws.service.report.field.DataTypeEnum;
import com.born.fcs.pm.ws.service.report.field.FcsField;
import com.born.fcs.pm.ws.service.report.order.PmReportDOQueryOrder;
import com.born.fcs.pm.ws.service.report.result.DataListItem;
import com.born.fcs.pm.ws.service.sms.SMSService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("projectService")
public class ProjectServiceImpl extends BaseAutowiredDomainService implements ProjectService,
																	AsynchronousService {
	
	@Autowired
	private CouncilSummaryService councilSummaryService;
	@Autowired
	private ExpireProjectService expireProjectService;
	@Autowired
	private InvestigationService investigationService;
	@Autowired
	private ProjectRelatedUserService projectRelatedUserService;
	@Autowired
	private FormRelatedUserService formRelatedUserService;
	@Autowired
	private SiteMessageService siteMessageService;
	@Autowired
	private MailService mailService;
	@Autowired
	private SMSService sMSService;
	@Autowired
	private LoanUseApplyService loanUseApplyService;
	@Autowired
	PropertyConfigService propertyConfigService;
	@Autowired
	FormService formService;
	@Autowired
	CouncilProjectService councilProjectService;
	@Autowired
	ProjectIssueInformationService projectIssueInformationService;
	@Autowired
	ForecastService forecastServiceClient;
	@Autowired
	AsynchronousTaskJob asynchronousTaskJob;
	@Autowired
	ChargeNotificationService chargeNotificationService;
	@Autowired
	ProjectContractService projectContractService;
	@Autowired
	ProjectCreditConditionService projectCreditConditionService;
	@Autowired
	CounterGuaranteeService counterGuaranteeService;
	@Autowired
	FinanceAffirmService fFinanceAffirmService;
	@Autowired
	ProjectRecoveryService projectRecoveryService;
	@Autowired
	PmReportService pmReportService;
	@Autowired
	ProjectChannelRelationService projectChannelRelationService;
	@Autowired
	BpmUserQueryService bpmUserQueryService;
	@Autowired
	ProjectTransferService projectTransferService;
	
	@Autowired
	CouncilSummaryProcessServiceImpl councilSummaryProcessService;
	
	@Override
	public QueryBaseBatchResult<ProjectInfo> queryProject(ProjectQueryOrder order) {
		QueryBaseBatchResult<ProjectInfo> batchResult = new QueryBaseBatchResult<ProjectInfo>();
		
		try {
			ProjectDO projectDO = new ProjectDO();
			BeanCopier.staticCopy(order, projectDO);
			
			List<String> busiTypeList = null;
			List<String> phases = Lists.newArrayList();
			List<String> phasesStatus = Lists.newArrayList();
			List<String> status = Lists.newArrayList();
			String hasLoanAmount = null; //是否还有放款金额
			String hasUseAmount = null; //是否还有用款金额
			String hasBothAmount = null; //是否有放款或者用款金额
			String hasContract = null; //是否签订合同
			String hasChargeAmount = null; //是否已收费
			String hasCompensatoryAmount = null; //是否有代偿金额
			String hasIssueAmount = null; //发债承销是否已发行金额
			String dockFormType = null; //子系统 对应表单类型
			String isReleasing = null; //是否解保中
			String hasCourtRuling = null; //是否上传法院裁定书
			for (ProjectPhasesEnum s : order.getPhasesList()) {
				phases.add(s.getCode());
			}
			for (ProjectPhasesStatusEnum s : order.getPhasesStatusList()) {
				phasesStatus.add(s.getCode());
			}
			for (ProjectStatusEnum s : order.getStatusList()) {
				status.add(s.getCode());
			}
			
			busiTypeList = order.getBusiTypeList();
			
			//是否还有放款金额
			if (order.getHasLoanAmount() != null) {
				hasLoanAmount = order.getHasLoanAmount().code();
			}
			//是否还有用款金额
			if (order.getHasUseAmount() != null) {
				hasUseAmount = order.getHasUseAmount().code();
			}
			//是否还有用款金额
			if (order.getHasBothAmount() != null) {
				hasBothAmount = order.getHasBothAmount().code();
			}
			
			//是否有收费金额
			if (order.getHasChargeAmount() != null) {
				hasChargeAmount = order.getHasChargeAmount().code();
			}
			//是否有代偿金额
			if (order.getHasCompensatoryAmount() != null) {
				hasCompensatoryAmount = order.getHasCompensatoryAmount().code();
			}
			//承销发债是否已发行
			if (order.getHasIssueAmount() != null) {
				hasIssueAmount = order.getHasIssueAmount().code();
			}
			
			//是否签订合同
			if (order.getHasContract() != null) {
				hasContract = order.getHasContract().code();
			}
			//诉保项目是否上传法院裁定书
			if (order.getHasCourtRuling() != null) {
				hasCourtRuling = order.getHasCourtRuling().code();
			}
			//子系统 对应表单
			if (order.getDockFormType() != null) {
				dockFormType = order.getDockFormType();
			}
			//是否解保中
			if (order.getIsReleasing() != null) {
				isReleasing = order.getIsReleasing();
			}
			//是否可复议
			if (order.getIsRecouncil() != null) {
				projectDO.setIsRecouncil(order.getIsRecouncil().code());
			}
			
			//是否批复
			if (order.getIsApproval() != null) {
				projectDO.setIsApproval(order.getIsApproval().code());
			}
			
			//是否批复作废
			if (order.getIsApprovalDel() != null) {
				projectDO.setIsApprovalDel(order.getIsApprovalDel().getCode());
			}
			
			//项目阶段
			if (order.getPhases() != null) {
				projectDO.setPhases(order.getPhases().code());
			}
			//项目状态
			if (order.getStatus() != null) {
				projectDO.setStatus(order.getStatus().code());
			}
			//项目阶段的状态
			if (order.getPhasesStatus() != null) {
				projectDO.setPhasesStatus(order.getPhasesStatus().code());
			}
			
			if (order.getIsContinue() != null) {
				projectDO.setIsContinue(order.getIsContinue().code());
			}
			if (order.getCustomerType() != null) {
				projectDO.setCustomerType(order.getCustomerType().code());
			}
			long totalCount = projectDAO.findByConditionCount(projectDO,
				order.getProjectCodeOrName(), order.getLoginUserId(), hasLoanAmount, hasUseAmount,
				hasBothAmount, hasChargeAmount, hasIssueAmount, hasCompensatoryAmount,
				dockFormType, isReleasing, busiTypeList, order.getDeptIdList(), phases,
				phasesStatus, status, order.getApprovalTimeStart(), order.getApprovalTimeEnd(),
				hasContract, order.getContractTimeStart(), order.getContractTimeEnd(),
				order.getRecoverySelecterId(), order.getRelatedRoleList(), hasCourtRuling,
				order.getIsHisProject(), order.getRiskManager());
			PageComponent component = new PageComponent(order, totalCount);
			
			String sortCol = "CASE WHEN p.phases = 'SET_UP_PHASES' THEN 1 WHEN p.phases = 'INVESTIGATING_PHASES' THEN 2 WHEN p.phases = 'COUNCIL_PHASES' THEN 3 WHEN p.phases = 'CONTRACT_PHASES' THEN 4 WHEN p.phases IN ('LOAN_USE_PHASES', 'FUND_RAISING_PHASES') THEN 5 WHEN p.phases IN ('AFTERWARDS_PHASES', 'RECOVERY_PHASES') THEN 6 ELSE 7 END";
			if (StringUtil.equalsIgnoreCase(order.getSortCol(), "p.phases")
				|| StringUtil.equalsIgnoreCase(order.getSortCol(), "phases")) {
				order.setSortCol(sortCol);
			}
			
			List<ProjectInfo> pageList = new ArrayList<ProjectInfo>();
			if (totalCount > 0) {
				
				String legalDeptCode = sysParameterService
					.getSysParameterValue(SysParamEnum.SYS_PARAM_FW_DEPT_CODE.code());
				
				List<ProjectDO> list = projectDAO.findByCondition(projectDO,
					order.getProjectCodeOrName(), order.getLoginUserId(), hasLoanAmount,
					hasUseAmount, hasBothAmount, hasChargeAmount, hasIssueAmount,
					hasCompensatoryAmount, dockFormType, isReleasing, busiTypeList,
					order.getDeptIdList(), phases, phasesStatus, status,
					order.getApprovalTimeStart(), order.getApprovalTimeEnd(), hasContract,
					order.getContractTimeStart(), order.getContractTimeEnd(),
					order.getRecoverySelecterId(), order.getRelatedRoleList(), hasCourtRuling,
					order.getIsHisProject(), order.getRiskManager(), order.getSortCol(),
					order.getSortOrder(), component.getFirstRecord(), component.getPageSize());
				for (ProjectDO item : list) {
					ProjectInfo project = DoConvert.convertProjectDO2Info(item);
					if (StringUtil.equals(project.getDeptCode(), legalDeptCode)) {
						project.setBelong2LegalDept(true);
					}
					pageList.add(project);
				}
			}
			batchResult.setSuccess(true);
			batchResult.setPageList(pageList);
			batchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询项目信息失败" + e.getMessage(), e);
			batchResult.setSuccess(false);
			batchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return batchResult;
		
	}
	
	@Override
	public QueryBaseBatchResult<ProjectSimpleDetailInfo> queryProjectSimpleDetail(	ProjectQueryOrder order) {
		QueryBaseBatchResult<ProjectSimpleDetailInfo> batchResult = new QueryBaseBatchResult<ProjectSimpleDetailInfo>();
		
		try {
			ProjectDO projectDO = new ProjectDO();
			BeanCopier.staticCopy(order, projectDO);
			
			List<String> busiTypeList = null;
			List<String> phases = Lists.newArrayList();
			List<String> phasesStatus = Lists.newArrayList();
			List<String> status = Lists.newArrayList();
			String hasLoanAmount = null; //是否还有放款金额
			String hasUseAmount = null; //是否还有用款金额
			String hasBothAmount = null; //是否有放款或者用款金额
			String hasContract = null; //是否签订合同
			String hasChargeAmount = null; //是否收费
			String hasIssueAmount = null; //发债承销是否有发行金额
			String hasCompensatoryAmount = null; //是否有代偿金额
			String dockFormType = null; //子系统 对应表单类型
			String isReleasing = null; //是否解保中
			String hasCourtRuling = null; //是否上传法院裁定书
			for (ProjectPhasesEnum s : order.getPhasesList()) {
				phases.add(s.getCode());
			}
			for (ProjectPhasesStatusEnum s : order.getPhasesStatusList()) {
				phasesStatus.add(s.getCode());
			}
			for (ProjectStatusEnum s : order.getStatusList()) {
				status.add(s.getCode());
			}
			
			busiTypeList = order.getBusiTypeList();
			
			//是否还有放款金额
			if (order.getHasLoanAmount() != null) {
				hasLoanAmount = order.getHasLoanAmount().code();
			}
			//是否还有用款金额
			if (order.getHasUseAmount() != null) {
				hasUseAmount = order.getHasUseAmount().code();
			}
			//是否还有用款金额
			if (order.getHasBothAmount() != null) {
				hasBothAmount = order.getHasBothAmount().code();
			}
			
			//是否有收费金额
			if (order.getHasChargeAmount() != null) {
				hasChargeAmount = order.getHasChargeAmount().code();
			}
			//是否有代偿金额
			if (order.getHasCompensatoryAmount() != null) {
				hasCompensatoryAmount = order.getHasCompensatoryAmount().code();
			}
			//承销发债是否已发行
			if (order.getHasIssueAmount() != null) {
				hasIssueAmount = order.getHasIssueAmount().code();
			}
			
			//是否签订合同
			if (order.getHasContract() != null) {
				hasContract = order.getHasContract().code();
			}
			//是否上传法院裁定书
			if (order.getHasCourtRuling() != null) {
				hasCourtRuling = order.getHasCourtRuling().code();
			}
			//子系统 对应表单
			if (order.getDockFormType() != null) {
				dockFormType = order.getDockFormType();
			}
			//是否解保中
			if (order.getIsReleasing() != null) {
				isReleasing = order.getIsReleasing();
			}
			//是否可复议
			if (order.getIsRecouncil() != null) {
				projectDO.setIsRecouncil(order.getIsRecouncil().code());
			}
			
			//是否批复
			if (order.getIsApproval() != null) {
				projectDO.setIsApproval(order.getIsApproval().code());
			}
			
			//项目阶段
			if (order.getPhases() != null) {
				projectDO.setPhases(order.getPhases().code());
			}
			
			//项目状态
			if (order.getStatus() != null) {
				projectDO.setStatus(order.getStatus().code());
			}
			
			//项目阶段的状态
			if (order.getPhasesStatus() != null) {
				projectDO.setPhasesStatus(order.getPhasesStatus().code());
			}
			
			if (order.getIsContinue() != null) {
				projectDO.setIsContinue(order.getIsContinue().code());
			}
			if (order.getCustomerType() != null) {
				projectDO.setCustomerType(order.getCustomerType().code());
			}
			long totalCount = projectDAO.findByConditionCount(projectDO,
				order.getProjectCodeOrName(), order.getLoginUserId(), hasLoanAmount, hasUseAmount,
				hasBothAmount, hasChargeAmount, hasIssueAmount, hasCompensatoryAmount,
				dockFormType, isReleasing, busiTypeList, order.getDeptIdList(), phases,
				phasesStatus, status, order.getApprovalTimeStart(), order.getApprovalTimeEnd(),
				hasContract, order.getContractTimeStart(), order.getContractTimeEnd(),
				order.getRecoverySelecterId(), order.getRelatedRoleList(), hasCourtRuling,
				order.getIsHisProject(), order.getRiskManager());
			PageComponent component = new PageComponent(order, totalCount);
			
			List<ProjectSimpleDetailInfo> pageList = new ArrayList<ProjectSimpleDetailInfo>();
			
			String sortCol = "CASE WHEN p.phases = 'SET_UP_PHASES' THEN 1 WHEN p.phases = 'INVESTIGATING_PHASES' THEN 2 WHEN p.phases = 'COUNCIL_PHASES' THEN 3 WHEN p.phases = 'CONTRACT_PHASES' THEN 4 WHEN p.phases IN ('LOAN_USE_PHASES', 'FUND_RAISING_PHASES') THEN 5 WHEN p.phases IN ('AFTERWARDS_PHASES', 'RECOVERY_PHASES') THEN 6 ELSE 7 END";
			if (StringUtil.equalsIgnoreCase(order.getSortCol(), "p.phases")
				|| StringUtil.equalsIgnoreCase(order.getSortCol(), "phases")) {
				order.setSortCol(sortCol);
			}
			
			if (totalCount > 0) {
				
				String legalDeptCode = sysParameterService
					.getSysParameterValue(SysParamEnum.SYS_PARAM_FW_DEPT_CODE.code());
				
				List<ProjectDO> list = projectDAO.findByCondition(projectDO,
					order.getProjectCodeOrName(), order.getLoginUserId(), hasLoanAmount,
					hasUseAmount, hasBothAmount, hasChargeAmount, hasIssueAmount,
					hasCompensatoryAmount, dockFormType, isReleasing, busiTypeList,
					order.getDeptIdList(), phases, phasesStatus, status,
					order.getApprovalTimeStart(), order.getApprovalTimeEnd(), hasContract,
					order.getContractTimeStart(), order.getContractTimeEnd(),
					order.getRecoverySelecterId(), order.getRelatedRoleList(), hasCourtRuling,
					order.getIsHisProject(), order.getRiskManager(), order.getSortCol(),
					order.getSortOrder(), component.getFirstRecord(), component.getPageSize());
				
				for (ProjectDO item : list) {
					ProjectInfo project = DoConvert.convertProjectDO2Info(item);
					if (StringUtil.equals(project.getDeptCode(), legalDeptCode)) {
						project.setBelong2LegalDept(true);
					}
					pageList.add(getSimpleDetailInfo(project));
				}
			}
			batchResult.setSuccess(true);
			batchResult.setPageList(pageList);
			batchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询项目信息失败" + e.getMessage(), e);
			batchResult.setSuccess(false);
			batchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return batchResult;
	}
	
	@Override
	public QueryBaseBatchResult<ProjectApprovaInfo> queryProjectApproval(	ProjectApprovalQueryOrder order) {
		QueryBaseBatchResult<ProjectApprovaInfo> batchResult = new QueryBaseBatchResult<ProjectApprovaInfo>();
		
		try {
			FCouncilSummaryProjectDO projectDO = new FCouncilSummaryProjectDO();
			BeanCopier.staticCopy(order, projectDO);
			
			if (StringUtil.isNotBlank(order.getStartTimeStr())) {
				order.setStartTime(DateUtil.string2DateTimeByAutoZero(order.getStartTimeStr()));
			}
			
			if (StringUtil.isNotBlank(order.getEndTimeStr())) {
				order.setEndTime(DateUtil.string2DateTimeBy23(order.getEndTimeStr()));
			}
			
			if (order.getIsDel() != null) {
				projectDO.setIsDel(order.getIsDel().code());
			} else {
				projectDO.setIsDel(null);
			}
			
			//			projectDO.setOneVoteDown(BooleanEnum.NO.code());
			//			projectDO.setVoteResult(ProjectVoteResultEnum.END_PASS.code());
			
			long totalCount = FCouncilSummaryProjectDAO.findByConditionCount(projectDO, order
				.getLoginUserId(), order.getDeptIdList(), order.getIsApproval() == null ? null
				: order.getIsApproval().code(), BooleanEnum.IS.code(), order.getStartTime(), order
				.getEndTime());
			PageComponent component = new PageComponent(order, totalCount);
			
			List<FCouncilSummaryProjectDO> list = FCouncilSummaryProjectDAO.findByCondition(
				projectDO, order.getLoginUserId(), order.getDeptIdList(),
				order.getIsApproval() == null ? null : order.getIsApproval().code(),
				BooleanEnum.IS.code(), order.getStartTime(), order.getEndTime(),
				component.getFirstRecord(), component.getPageSize(), order.getSortCol(),
				order.getSortOrder());
			
			List<ProjectApprovaInfo> pageList = new ArrayList<ProjectApprovaInfo>(list.size());
			
			for (FCouncilSummaryProjectDO item : list) {
				ProjectApprovaInfo info = new ProjectApprovaInfo();
				BeanCopier.staticCopy(item, info);
				info.setCustomerType(CustomerTypeEnum.getByCode(item.getCustomerType()));
				info.setTimeUnit(TimeUnitEnum.getByCode(item.getTimeUnit()));
				info.setIsDel(BooleanEnum.getByCode(item.getIsDel()));
				info.setOneVoteDown(OneVoteResultEnum.getByCode(item.getOneVoteDown()));
				info.setVoteResult(ProjectVoteResultEnum.getByCode(item.getVoteResult()));
				pageList.add(info);
			}
			
			batchResult.setSuccess(true);
			batchResult.setPageList(pageList);
			batchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询项目批复失败" + e.getMessage(), e);
			batchResult.setSuccess(false);
			batchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return batchResult;
	}
	
	@Override
	public ProjectInfo queryByCode(String projectCode, boolean queryDetail) {
		ProjectInfo project = DoConvert.convertProjectDO2Info(projectDAO
			.findByProjectCode(projectCode));
		
		if (project != null) {
			String legalDeptCode = sysParameterService
				.getSysParameterValue(SysParamEnum.SYS_PARAM_FW_DEPT_CODE.code());
			if (StringUtil.equals(project.getDeptCode(), legalDeptCode)) {
				project.setBelong2LegalDept(true);
			}
		}
		if (queryDetail && project != null) {
			//根据业务类型查询会议纪要中信息
			if (ProjectUtil.isBond(project.getBusiType())) { //发债
				FCouncilSummaryProjectBondInfo detailInfo = project.getSpId() > 0 ? councilSummaryService
					.queryBondProjectCsBySpId(project.getSpId(), false) : councilSummaryService
					.queryBondProjectCsByProjectCode(projectCode, false);
				if (detailInfo != null) {
					ProjectBondDetailInfo bondDetailInfo = new ProjectBondDetailInfo();
					BeanCopier.staticCopy(project, bondDetailInfo);
					BeanCopier.staticCopy(detailInfo, bondDetailInfo);
					project.setBondDetailInfo(bondDetailInfo);
				}
			} else if (ProjectUtil.isEntrusted(project.getBusiType())) { //委贷
				FCouncilSummaryProjectEntrustedInfo detailInfo = project.getSpId() > 0 ? councilSummaryService
					.queryEntrustedProjectCsBySpId(project.getSpId(), false)
					: councilSummaryService
						.queryEntrustedProjectCsByProjectCode(projectCode, false);
				if (detailInfo != null) {
					ProjectEntrustedDetailInfo entrustedDetailInfo = new ProjectEntrustedDetailInfo();
					BeanCopier.staticCopy(project, entrustedDetailInfo);
					BeanCopier.staticCopy(detailInfo, entrustedDetailInfo);
					project.setEntrustedDetailInfo(entrustedDetailInfo);
				}
			} else if (ProjectUtil.isUnderwriting(project.getBusiType())) { //承销
				FCouncilSummaryProjectUnderwritingInfo detailInfo = project.getSpId() > 0 ? councilSummaryService
					.queryUnderwritingProjectCsBySpId(project.getSpId(), false)
					: councilSummaryService.queryUnderwritingProjectCsByProjectCode(projectCode,
						false);
				if (detailInfo != null) {
					ProjectUnderwritingDetailInfo underwritingDetailInfo = new ProjectUnderwritingDetailInfo();
					BeanCopier.staticCopy(project, underwritingDetailInfo);
					BeanCopier.staticCopy(detailInfo, underwritingDetailInfo);
					project.setUnderwritingDetailInfo(underwritingDetailInfo);
				}
			} else if (ProjectUtil.isLitigation(project.getBusiType())) { //诉讼担保
				FCouncilSummaryProjectLgLitigationInfo detailInfo = project.getSpId() > 0 ? councilSummaryService
					.queryLgLitigationProjectCsBySpId(project.getSpId(), false)
					: councilSummaryService.queryLgLitigationProjectCsByProjectCode(projectCode,
						false);
				if (detailInfo != null) {
					ProjectLgLitigationDetailInfo lgLitigationDetailInfo = new ProjectLgLitigationDetailInfo();
					BeanCopier.staticCopy(project, lgLitigationDetailInfo);
					BeanCopier.staticCopy(detailInfo, lgLitigationDetailInfo);
					project.setLgLitigationDetailInfo(lgLitigationDetailInfo);
				}
			} else { //担保
				FCouncilSummaryProjectGuaranteeInfo detailInfo = project.getSpId() > 0 ? councilSummaryService
					.queryGuaranteeProjectCsBySpId(project.getSpId(), false)
					: councilSummaryService
						.queryGuaranteeProjectCsByProjectCode(projectCode, false);
				if (detailInfo != null) {
					ProjectGuaranteeDetailInfo guaranteeDetailInfo = new ProjectGuaranteeDetailInfo();
					BeanCopier.staticCopy(project, guaranteeDetailInfo);
					BeanCopier.staticCopy(detailInfo, guaranteeDetailInfo);
					project.setGuaranteeDetailInfo(guaranteeDetailInfo);
				}
			}
		}
		return project;
	}
	
	/**
	 * 获取简单的详细信息
	 * @param info
	 * @return
	 */
	@Override
	public ProjectSimpleDetailInfo getSimpleDetailInfo(ProjectInfo info) {
		ProjectSimpleDetailInfo simpleDetailInfo = null;
		try {
			
			if (info != null) {
				String projectCode = info.getProjectCode();
				simpleDetailInfo = new ProjectSimpleDetailInfo();
				BeanCopier.staticCopy(info, simpleDetailInfo);
				
				//根据业务类型查询会议纪要中信息
				if (ProjectUtil.isBond(info.getBusiType())) { //发债
					FCouncilSummaryProjectBondInfo detailInfo = info.getSpId() > 0 ? councilSummaryService
						.queryBondProjectCsBySpId(info.getSpId(), false) : councilSummaryService
						.queryBondProjectCsByProjectCode(projectCode, false);
					
					if (detailInfo != null
						&& detailInfo.getVoteResult() == ProjectVoteResultEnum.END_PASS) {
						simpleDetailInfo.setInstitutionId(detailInfo.getCapitalChannelId());
						simpleDetailInfo.setInstitutionName(detailInfo.getCapitalChannelName());
						simpleDetailInfo.setInstitutionTypeName("资金渠道");
						simpleDetailInfo.setChargeFee(detailInfo.getGuaranteeFee());
						simpleDetailInfo.setChargeType(detailInfo.getGuaranteeFeeType());
						simpleDetailInfo.setChargeName("担保费");
						simpleDetailInfo.setInterestRate(detailInfo.getInterestRate());
						simpleDetailInfo.setInterrestFloat(detailInfo.getInterestRateFloat());
						simpleDetailInfo.setChargePhase(detailInfo.getChargePhase());
					} else { //查询尽职调查中的数据
						long invFormId = investigationService
							.queryInvestigationFormIdByProjectCode(projectCode);
						if (invFormId > 0) {
							FInvestigationCreditSchemeInfo schemeInfo = investigationService
								.findInvestigationCreditSchemeByFormId(invFormId);
							if (schemeInfo != null) {
								simpleDetailInfo.setInstitutionId(schemeInfo.getCapitalChannelId());
								simpleDetailInfo.setInstitutionName(schemeInfo
									.getCapitalChannelName());
								simpleDetailInfo.setInstitutionTypeName("资金渠道");
								simpleDetailInfo.setChargeFee(schemeInfo.getChargeRate());
								simpleDetailInfo.setChargeType(ChargeTypeEnum.PERCENT);
								simpleDetailInfo.setChargeName("费率");
								simpleDetailInfo.setInterestRate(schemeInfo.getChargeRate());
								simpleDetailInfo.setInterrestFloat(CompareEnum.EQUAL);
								simpleDetailInfo.setChargePhase(schemeInfo.getChargePhase());
							}
						}
					}
				} else if (ProjectUtil.isEntrusted(info.getBusiType())) { //委贷
					FCouncilSummaryProjectEntrustedInfo detailInfo = info.getSpId() > 0 ? councilSummaryService
						.queryEntrustedProjectCsBySpId(info.getSpId(), false)
						: councilSummaryService.queryEntrustedProjectCsByProjectCode(projectCode,
							false);
					//未通过、一票否决、批复作废取尽职调查里面的数据
					if (detailInfo != null
						&& detailInfo.getVoteResult() == ProjectVoteResultEnum.END_PASS) {
						simpleDetailInfo.setInstitutionId(detailInfo.getCapitalChannelId());
						simpleDetailInfo.setInstitutionName(detailInfo.getCapitalChannelName());
						simpleDetailInfo.setInstitutionTypeName("资金渠道");
						simpleDetailInfo.setChargeFee(detailInfo.getInterestRate());
						simpleDetailInfo.setChargeType(ChargeTypeEnum.PERCENT);
						simpleDetailInfo.setChargeName("利率");
						simpleDetailInfo.setInterestRate(detailInfo.getInterestRate());
						simpleDetailInfo.setInterrestFloat(CompareEnum.EQUAL);
						simpleDetailInfo.setChargePhase(detailInfo.getChargePhase());
					} else { //查询尽职调查中的数据
						long invFormId = investigationService
							.queryInvestigationFormIdByProjectCode(projectCode);
						if (invFormId > 0) {
							FInvestigationCreditSchemeInfo schemeInfo = investigationService
								.findInvestigationCreditSchemeByFormId(invFormId);
							if (schemeInfo != null) {
								simpleDetailInfo.setInstitutionId(schemeInfo.getCapitalChannelId());
								simpleDetailInfo.setInstitutionName(schemeInfo
									.getCapitalChannelName());
								simpleDetailInfo.setInstitutionTypeName("资金渠道");
								simpleDetailInfo.setChargeFee(schemeInfo.getChargeRate());
								simpleDetailInfo.setChargeType(ChargeTypeEnum.PERCENT);
								simpleDetailInfo.setChargeName("费率");
								simpleDetailInfo.setInterestRate(schemeInfo.getChargeRate());
								simpleDetailInfo.setInterrestFloat(CompareEnum.EQUAL);
								simpleDetailInfo.setChargePhase(schemeInfo.getChargePhase());
							}
						}
					}
				} else if (ProjectUtil.isUnderwriting(info.getBusiType())) { //承销
					FCouncilSummaryProjectUnderwritingInfo detailInfo = info.getSpId() > 0 ? councilSummaryService
						.queryUnderwritingProjectCsBySpId(info.getSpId(), false)
						: councilSummaryService.queryUnderwritingProjectCsByProjectCode(
							projectCode, false);
					//未通过、一票否决、批复作废取尽职调查里面的数据
					if (detailInfo != null
						&& detailInfo.getVoteResult() == ProjectVoteResultEnum.END_PASS) {
						simpleDetailInfo.setInstitutionId(detailInfo.getBourseId());
						simpleDetailInfo.setInstitutionName(detailInfo.getBourseName());
						simpleDetailInfo.setInstitutionTypeName("交易所");
						simpleDetailInfo.setChargeFee(detailInfo.getUnderwritingFee());
						simpleDetailInfo.setChargeType(detailInfo.getUnderwritingFeeType());
						simpleDetailInfo.setChargeName("承销费");
						simpleDetailInfo.setInterestRate(detailInfo.getReleaseRate());
						simpleDetailInfo.setInterrestFloat(CompareEnum.EQUAL);
						simpleDetailInfo.setChargePhase(detailInfo.getChargePhase());
					} else { //从尽职调查中查询详细信息
						long invFormId = investigationService
							.queryInvestigationFormIdByProjectCode(projectCode);
						if (invFormId > 0) {
							FInvestigationUnderwritingInfo uInfo = investigationService
								.findFInvestigationUnderwritingByFormId(invFormId);
							if (uInfo != null) {
								simpleDetailInfo.setInstitutionId(uInfo.getExchangeId());
								simpleDetailInfo.setInstitutionName(uInfo.getExchangeName());
								simpleDetailInfo.setInstitutionTypeName("交易所");
								simpleDetailInfo.setChargeFee(uInfo.getUnderwritingRate());
								simpleDetailInfo.setChargeType(ChargeTypeEnum.PERCENT);
								simpleDetailInfo.setChargeName("承销费");
								simpleDetailInfo.setInterestRate(uInfo.getIssueRate());
								simpleDetailInfo.setInterrestFloat(CompareEnum.EQUAL);
								simpleDetailInfo.setChargePhase(ChargePhaseEnum.BEFORE);
							}
						}
					}
				} else if (ProjectUtil.isLitigation(info.getBusiType())) { //诉讼担保
					FCouncilSummaryProjectLgLitigationInfo detailInfo = info.getSpId() > 0 ? councilSummaryService
						.queryLgLitigationProjectCsBySpId(info.getSpId(), false)
						: councilSummaryService.queryLgLitigationProjectCsByProjectCode(
							projectCode, false);
					//未通过取尽职调查里面的数据
					if (detailInfo != null
						&& detailInfo.getVoteResult() == ProjectVoteResultEnum.END_PASS) {
						simpleDetailInfo.setInstitutionId(detailInfo.getCoInstitutionId());
						simpleDetailInfo.setInstitutionName(detailInfo.getCoInstitutionName());
						simpleDetailInfo.setInstitutionTypeName("合作机构");
						simpleDetailInfo.setChargeFee(detailInfo.getGuaranteeFee());
						simpleDetailInfo.setChargeType(detailInfo.getGuaranteeFeeType());
						simpleDetailInfo.setChargeName("担保费");
						simpleDetailInfo.setInterestRate(0);
						simpleDetailInfo.setInterrestFloat(CompareEnum.EQUAL);
						simpleDetailInfo.setChargePhase(detailInfo.getChargePhase());
					} else { //从尽职调查中查询详细信息
						long invFormId = investigationService
							.queryInvestigationFormIdByProjectCode(projectCode);
						if (invFormId > 0) {
							FInvestigationLitigationInfo lgInfo = investigationService
								.findFInvestigationLitigationByFormId(invFormId);
							if (lgInfo != null) {
								simpleDetailInfo.setInstitutionId(lgInfo.getCoInstitutionId());
								simpleDetailInfo.setInstitutionName(lgInfo.getCoInstitutionName());
								simpleDetailInfo.setInstitutionTypeName("合作机构");
								simpleDetailInfo.setChargeFee(lgInfo.getGuaranteeFee());
								simpleDetailInfo.setChargeType(lgInfo.getGuaranteeType());
								simpleDetailInfo.setChargeName("担保费用");
								//simpleDetailInfo.setInterestRate(lgInfo.getGuaranteeFee());
								simpleDetailInfo.setInterrestFloat(CompareEnum.EQUAL);
								simpleDetailInfo.setChargePhase(ChargePhaseEnum.BEFORE);
							}
						}
					}
					
				} else { //担保
					FCouncilSummaryProjectGuaranteeInfo detailInfo = info.getSpId() > 0 ? councilSummaryService
						.queryGuaranteeProjectCsBySpId(info.getSpId(), false)
						: councilSummaryService.queryGuaranteeProjectCsByProjectCode(projectCode,
							false);
					//未通过、取尽职调查里面的数据
					if (detailInfo != null
						&& detailInfo.getVoteResult() == ProjectVoteResultEnum.END_PASS) {
						simpleDetailInfo.setInstitutionId(detailInfo.getCapitalChannelId());
						simpleDetailInfo.setInstitutionName(detailInfo.getCapitalChannelName());
						simpleDetailInfo.setInstitutionTypeName("资金渠道");
						simpleDetailInfo.setChargeFee(detailInfo.getGuaranteeFee());
						simpleDetailInfo.setChargeType(detailInfo.getGuaranteeFeeType());
						simpleDetailInfo.setChargeName("担保费");
						simpleDetailInfo.setInterestRate(0);
						simpleDetailInfo.setInterrestFloat(CompareEnum.EQUAL);
						simpleDetailInfo.setChargePhase(detailInfo.getChargePhase());
					} else { //查询尽职调查中的数据
						long invFormId = investigationService
							.queryInvestigationFormIdByProjectCode(projectCode);
						if (invFormId > 0) {
							FInvestigationCreditSchemeInfo schemeInfo = investigationService
								.findInvestigationCreditSchemeByFormId(invFormId);
							if (schemeInfo != null) {
								simpleDetailInfo.setInstitutionId(schemeInfo.getCapitalChannelId());
								simpleDetailInfo.setInstitutionName(schemeInfo
									.getCapitalChannelName());
								simpleDetailInfo.setInstitutionTypeName("资金渠道");
								simpleDetailInfo.setChargeFee(schemeInfo.getChargeRate());
								simpleDetailInfo.setChargeType(ChargeTypeEnum.PERCENT);
								simpleDetailInfo.setChargeName("费率");
								simpleDetailInfo.setInterestRate(schemeInfo.getChargeRate());
								simpleDetailInfo.setInterrestFloat(CompareEnum.EQUAL);
								simpleDetailInfo.setChargePhase(schemeInfo.getChargePhase());
							}
						}
					}
				}
				
				if (info.isBankFinancing()) { //银行融资担保
					//查询所有资金渠道
					List<ProjectChannelRelationInfo> capitalChannels = projectChannelRelationService
						.queryCapitalChannel(projectCode);
					if (ListUtil.isNotEmpty(capitalChannels)) {
						String institutionIds = "";
						String institutionNames = "";
						for (ProjectChannelRelationInfo capitalChannel : capitalChannels) {
							if ("".equals(institutionIds)) {
								institutionIds += capitalChannel.getChannelId();
								institutionNames = capitalChannel.getChannelName();
							} else {
								institutionIds += "," + capitalChannel.getChannelId();
								institutionNames += "，" + capitalChannel.getChannelName();
							}
						}
						simpleDetailInfo.setInstitutionIds(institutionIds);
						simpleDetailInfo.setInstitutionNames(institutionNames);
					}
				} else {
					simpleDetailInfo.setInstitutionIds(simpleDetailInfo.getInstitutionId() + "");
					simpleDetailInfo.setInstitutionNames(simpleDetailInfo.getInstitutionName());
				}
				
			}
		} catch (Exception e) {
			logger.error("查询项目详细信息出错", e);
		}
		return simpleDetailInfo;
	}
	
	@Override
	public ProjectSimpleDetailInfo querySimpleDetailInfo(String projectCode) {
		ProjectInfo info = queryByCode(projectCode, false);
		return getSimpleDetailInfo(info);
	}
	
	@Override
	public ProjectResult saveProject(final ProjectOrder order) {
		return (ProjectResult) commonProcess(order, "保存项目信息", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				ProjectDO project = projectDAO.findByProjectCode(order.getProjectCode());
				boolean isUpdate = false;
				if (project == null) {//新增
					project = new ProjectDO();
					BeanCopier.staticCopy(order, project);
				} else {//修改
					isUpdate = true;
					long projectId = project.getProjectId();
					BeanCopier.staticCopy(order, project);
					project.setProjectId(projectId);
				}
				if (isUpdate) {
					projectDAO.update(project);
				} else {
					project.setProjectId(projectDAO.insert(project));
				}
				ProjectResult result = (ProjectResult) FcsPmDomainHolder.get().getAttribute(
					"result");
				result.setProject(DoConvert.convertProjectDO2Info(project));
				return null;
			}
		}, null, null);
	}
	
	@Override
	public ProjectResult changeProjectStatus(final ChangeProjectStatusOrder order) {
		ProjectResult result = createResult();
		try {
			
			logger.info("修改项目状态 ：{}", order);
			ProjectDO project = projectDAO.findByProjectCode(order.getProjectCode());
			if (project == null) {
				throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "项目不存在");
			}
			boolean hasChange = false;
			if (order.getStatus() != null && !order.getStatus().code().equals(project.getStatus())) { //项目状态
				project.setStatus(order.getStatus().code());
				hasChange = true;
			}
			if (order.getPhases() != null && !order.getPhases().code().equals(project.getPhases())) { //项目阶段
				project.setPhases(order.getPhases().code());
				hasChange = true;
			}
			if (order.getPhaseStatus() != null
				&& !order.getPhaseStatus().code().equals(project.getPhasesStatus())) { //阶段对应状态
				project.setPhasesStatus(order.getPhaseStatus().code());
				hasChange = true;
			}
			
			if (hasChange) {
				projectDAO.update(project);
			}
			result.setProject(DoConvert.convertProjectDO2Info(project));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("修改项目状态出错");
			logger.error("修改项目状态出错:{}", e);
		}
		
		return result;
	}
	
	@Override
	protected ProjectResult createResult() {
		return new ProjectResult();
	}
	
	@Override
	public int updateIsContinueByProjectCode(String projectCode) {
		ProjectDO project = projectDAO.findByProjectCode(projectCode);
		project.setIsContinue(BooleanEnum.IS.code());
		List<ProjectIssueInformationDO> listProjectIssueInformationDO = projectIssueInformationDAO
			.findByProjectCode(projectCode);
		Date lastIssueDate = null;
		for (ProjectIssueInformationDO projectIssueInformationDO : listProjectIssueInformationDO) {
			projectIssueInformationDO.setStatus("SELL_FINISH");//手动停止发售时，更新状态
			projectIssueInformationDAO.update(projectIssueInformationDO);
			if (null == lastIssueDate) {
				lastIssueDate = projectIssueInformationDO.getExpireTime();
			} else {
				if (projectIssueInformationDO.getExpireTime().after(lastIssueDate)) {
					lastIssueDate = projectIssueInformationDO.getExpireTime();
				}
			}
		}
		//承销项目做了发售信息维护后，并且选择不再继续发售，则项目已完成，能做的操作就是到期的发送通知和资料归档
		if (ProjectUtil.isUnderwriting(project.getBusiType())) {
			//			project.setPhases(ProjectPhasesEnum.FINISH_PHASES.code());
			project.setPhasesStatus(ProjectPhasesStatusEnum.APPROVAL.code());
			project.setStatus(ProjectStatusEnum.SELL_FINISH.code());
		}
		return projectDAO.update(project);
	}
	
	/**
	 * 通知
	 * @param project
	 * @param isPause
	 */
	private void notifyPauseOrRestart(final ProjectDO project, final boolean isPause) {
		
		taskExecutor.execute(new Runnable() {
			
			@Override
			public void run() {
				//风险经理
				ProjectRelatedUserInfo riskManager = projectRelatedUserService
					.getRiskManager(project.getProjectCode());
				//业务部总监
				List<SimpleUserInfo> busiDirector = projectRelatedUserService
					.getBusiDirector(project.getProjectCode());
				
				List<SimpleUserInfo> notifyUserList = Lists.newArrayList();
				if (riskManager != null) {
					SimpleUserInfo user = new SimpleUserInfo();
					user.setUserId(riskManager.getUserId());
					user.setUserName(riskManager.getUserName());
					user.setUserAccount(riskManager.getUserAccount());
					user.setEmail(riskManager.getUserEmail());
					user.setMobile(riskManager.getUserMobile());
					notifyUserList.add(user);
				}
				if (busiDirector != null)
					notifyUserList.addAll(busiDirector);
				
				//发送消息
				if (ListUtil.isNotEmpty(notifyUserList)) {
					
					String tip = isPause ? "暂缓" : "重启";
					
					String messageContent = "项目被" + tip + "，项目编号：" + project.getProjectCode()
											+ "，客户名称：" + project.getCustomerName();
					String messageTitle = "项目" + tip + "提醒";
					
					List<String> mailAddress = Lists.newArrayList();
					List<String> mobiles = Lists.newArrayList();
					
					for (SimpleUserInfo user : notifyUserList) {
						if (StringUtil.isEmail(user.getEmail())) {
							mailAddress.add(user.getEmail());
						}
						if (StringUtil.isMobile(user.getMobile())) {
							mobiles.add(user.getMobile());
						}
					}
					
					//发送站内信
					SimpleUserInfo[] sendUsers = notifyUserList
						.toArray(new SimpleUserInfo[notifyUserList.size()]);
					MessageOrder siteMessageOrder = MessageOrder.newSystemMessageOrder();
					siteMessageOrder.setMessageSubject(messageTitle);
					siteMessageOrder.setMessageTitle(messageTitle);
					siteMessageOrder.setMessageContent(messageContent);
					siteMessageOrder.setSendUsers(sendUsers);
					siteMessageService.addMessageInfo(siteMessageOrder);
					
					//发送email
					if (ListUtil.isNotEmpty(mailAddress)) {
						SendMailOrder mailOrder = new SendMailOrder();
						mailOrder.setContent(messageContent);
						mailOrder.setSendTo(mailAddress);
						mailOrder.setSubject(messageTitle);
						mailService.sendHtmlEmail(mailOrder);
					}
					
					//发送短信
					if (ListUtil.isNotEmpty(mobiles)) {
						//				for (String mobile : mobiles) {
						//					sMSService.sendSMS(mobile, messageContent, null);
						//				}
					}
				}
			}
		});
	}
	
	/**
	 * 项目暂缓
	 * @param projectCode
	 * @return
	 * @see com.born.fcs.pm.ws.service.common.ProjectService#pauseProject(java.lang.String)
	 */
	@Override
	public FcsBaseResult pauseProject(final String projectCode) {
		FcsBaseResult result = null;
		try {
			result = transactionTemplate.execute(new TransactionCallback<FcsBaseResult>() {
				
				@Override
				public FcsBaseResult doInTransaction(TransactionStatus status) {
					FcsBaseResult result = createResult();
					try {
						
						ProjectDO project = projectDAO.findByProjectCode(projectCode);
						if (project == null) {
							throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
								"项目不存在");
						}
						
						//正常状态的项目才能暂缓
						if (!ProjectStatusEnum.NORMAL.code().equals(project.getStatus())) {
							throw ExceptionFactory.newFcsException(
								FcsResultEnum.DO_ACTION_STATUS_ERROR, "当前状态不能暂缓");
						}
						
						// 查询正在执行任务的相关人员
						FormRelatedUserQueryOrder order = new FormRelatedUserQueryOrder();
						order.setProjectCode(projectCode);
						List<String> exeStatusList = Lists.newArrayList();
						exeStatusList.add(ExeStatusEnum.WAITING.code());
						exeStatusList.add(ExeStatusEnum.IN_PROGRESS.code());
						order.setExeStatusList(exeStatusList);
						QueryBaseBatchResult<FormRelatedUserInfo> exeTaskUsers = formRelatedUserService
							.queryPage(order);
						
						if (ListUtil.isNotEmpty(exeTaskUsers.getPageList())) {//当前还有执行中的任务
						
							Map<Long, Integer> exeForm = Maps.newHashMap();
							
							for (FormRelatedUserInfo exeUser : exeTaskUsers.getPageList()) {
								Integer count = exeForm.get(exeUser.getFormId());
								if (count == null || count == 0) {
									exeForm.put(exeUser.getFormId(), 1);
								} else {
									exeForm.put(exeUser.getFormId(), count++);
								}
							}
							
							//所有在执行中的表单
							String exeFormName = "";
							for (Long formId : exeForm.keySet()) {
								FormDO form = formDAO.findByFormId(formId);
								if (form != null) {
									exeFormName += "[ " + form.getFormName() + " ] * "
													+ exeForm.get(formId) + "，";
								}
							}
							if (StringUtil.isNotEmpty(exeFormName)) {
								exeFormName = exeFormName.substring(0, exeFormName.length() - 1);
							}
							
							throw ExceptionFactory.newFcsException(
								FcsResultEnum.DO_ACTION_STATUS_ERROR, "表单审核中 : " + exeFormName);
						}
						
						//查询上会记录
						List<CouncilProjectDO> councilProjects = councilProjectDAO
							.findByCouncilProjectCode(projectCode);
						
						if (ListUtil.isNotEmpty(councilProjects)) {
							
							//只取最新的一条数据看
							CouncilProjectDO cp = councilProjects.get(0);
							
							//被选入会议中或者投票中的会议
							if (ProjectVoteResultEnum.IN_VOTE.code().equals(
								cp.getProjectVoteResult())
								|| ProjectVoteResultEnum.NOT_BEGIN.code().equals(
									cp.getProjectVoteResult())) {
								throw ExceptionFactory.newFcsException(
									FcsResultEnum.DO_ACTION_STATUS_ERROR, "会议中的项目不能暂缓");
							} else if (ProjectVoteResultEnum.END_NOPASS.code().equals(
								cp.getProjectVoteResult())) {
								throw ExceptionFactory.newFcsException(
									FcsResultEnum.DO_ACTION_STATUS_ERROR, "项目未通过投票，无须暂缓。");
							} else {
								//查看会议纪要是否已经填了
								FCouncilSummaryInfo summary = councilSummaryService
									.queryCouncilSummaryByCouncilId(cp.getCouncilId());
								if (summary == null) {
									throw ExceptionFactory
										.newFcsException(FcsResultEnum.DO_ACTION_STATUS_ERROR,
											"等待填写项目评审会议纪要的项目不能暂缓");
								} else {
									FormInfo summaryForm = formService.findByFormId(summary
										.getFormId());
									if (summaryForm.getStatus() != FormStatusEnum.APPROVAL
										&& summaryForm.getStatus() != FormStatusEnum.DENY) {
										throw ExceptionFactory.newFcsException(
											FcsResultEnum.DO_ACTION_STATUS_ERROR,
											"项目评审会议纪要审核尚未完成，项目不能暂缓");
									}
								}
							}
						}
						
						//业务类型
						String busiType = project.getBusiType();
						
						//承销、诉保签订合同后不能暂缓
						if ((ProjectUtil.isUnderwriting(busiType) || ProjectUtil
							.isLitigation(busiType))
							&& StringUtil.isNotBlank(project.getContractNo())) {
							throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
								"项目已签订合同，不能暂缓");
						} else if (ProjectUtil.isBond(busiType)) {//发债类，有发售信息后不能暂缓
							if (project.getAccumulatedIssueAmount().greaterThan(ZERO_MONEY)) {
								throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
									"已有发售信息，不能暂缓");
							}
						}
						
						LoanUseApplyQueryOrder laQueryOrder = new LoanUseApplyQueryOrder();
						laQueryOrder.setProjectCode(projectCode);
						laQueryOrder.setPageSize(999);
						laQueryOrder.setFormStatus(FormStatusEnum.APPROVAL.code());
						//放用款后不能暂缓
						QueryBaseBatchResult<LoanUseApplyFormInfo> laResult = loanUseApplyService
							.searchApplyForm(laQueryOrder);
						if (laResult != null && laResult.getTotalCount() > 0) {
							throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
								"项目已放用款，不能暂缓");
						}
						
						//更新项目状态
						project.setStatus(ProjectStatusEnum.PAUSE.code());
						projectDAO.update(project);
						
						//看看待上会列表中是否有数据,有就改成暂缓状态
						CouncilApplyDO applyDO = new CouncilApplyDO();
						applyDO.setProjectCode(projectCode);
						applyDO.setStatus(CouncilApplyStatusEnum.WAIT.code());
						List<CouncilApplyDO> councilApply = councilApplyDAO.findByCondition(
							applyDO, 0, 100, null, null);
						if (ListUtil.isNotEmpty(councilApply)) {
							for (CouncilApplyDO apply : councilApply) {
								apply.setStatus(CouncilApplyStatusEnum.PAUSE.code());
								councilApplyDAO.update(apply);
							}
						}
						
						//通知相关人员 
						notifyPauseOrRestart(project, true);
						
						result.setSuccess(true);
						result.setMessage("项目暂缓成功");
					} catch (FcsPmBizException eex) {
						setYrdException(status, result, eex, eex.getErrorMsg());
					} catch (FcsPmDomainException e) {
						setFcsPmDomainException(status, result, e, e.getErrorMsg());
					} catch (Exception e) {
						setDbException(status, result, e);
					} catch (Throwable e) {
						setDbException(status, result, e);
					}
					return result;
				}
			});
			
		} catch (Exception e) {
			logger.error("项目暂缓出错：{}", e);
		}
		return result;
	}
	
	@Override
	public FcsBaseResult restartProject(final String projectCode) {
		FcsBaseResult result = null;
		try {
			result = transactionTemplate.execute(new TransactionCallback<FcsBaseResult>() {
				
				@Override
				public FcsBaseResult doInTransaction(TransactionStatus status) {
					FcsBaseResult result = createResult();
					try {
						
						ProjectDO project = projectDAO.findByProjectCode(projectCode);
						if (project == null) {
							throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
								"项目不存在");
						}
						
						if (!ProjectStatusEnum.PAUSE.code().equals(project.getStatus())) {
							throw ExceptionFactory.newFcsException(
								FcsResultEnum.DO_ACTION_STATUS_ERROR, "当前状态不能重启");
						}
						
						//更新项目状态
						project.setStatus(ProjectStatusEnum.NORMAL.code());
						projectDAO.update(project);
						
						//看看待上会列表中是否有数据,有就重新改成WAIT
						CouncilApplyDO applyDO = new CouncilApplyDO();
						applyDO.setProjectCode(projectCode);
						applyDO.setStatus(CouncilApplyStatusEnum.PAUSE.code());
						List<CouncilApplyDO> councilApply = councilApplyDAO.findByCondition(
							applyDO, 0, 100, null, null);
						if (ListUtil.isNotEmpty(councilApply)) {
							for (CouncilApplyDO apply : councilApply) {
								apply.setStatus(CouncilApplyStatusEnum.WAIT.code());
								councilApplyDAO.update(apply);
							}
						}
						
						//通知相关人员
						notifyPauseOrRestart(project, false);
						
						result.setSuccess(true);
						result.setMessage("项目重启成功");
					} catch (FcsPmBizException eex) {
						setYrdException(status, result, eex, eex.getErrorMsg());
					} catch (FcsPmDomainException e) {
						setFcsPmDomainException(status, result, e, e.getErrorMsg());
					} catch (Exception e) {
						setDbException(status, result, e);
					} catch (Throwable e) {
						setDbException(status, result, e);
					}
					return result;
				}
			});
			
		} catch (Exception e) {
			logger.error("项目重启出错：{}", e);
		}
		return result;
	}
	
	@Override
	public FcsBaseResult transferProject(TransferProjectOrder order) {
		return projectTransferService.transferProject(order);
	}
	
	//	/**
	//	 * 项目移交后设置新的部门
	//	 * @param project
	//	 */
	//	private void setProjectNewDept(ProjectDO project) {
	//		try {
	//			logger.info("查询用户部门信息：{}", project.getBusiManagerAccount());
	//			UserDetailsServiceProxy serviceProxy = new UserDetailsServiceProxy(
	//				propertyConfigService.getBmpServiceUserDetailsService());
	//			SysOrg org = serviceProxy.loadPrimaryOrgByUsername(project.getBusiManagerAccount());
	//			if (org != null) {
	//				project.setDeptId(org.getOrgId());
	//				project.setDeptCode(org.getCode());
	//				project.setDeptName(org.getOrgName());
	//				project.setDeptPath(org.getPath());
	//				project.setDeptPathName(org.getOrgPathname());
	//			}
	//			logger.info("查询用户部门信息完成  {} ： {}", project.getBusiManagerAccount(), org);
	//		} catch (Exception e) {
	//			logger.error("查询用户部门信息出错 : {}", e);
	//		}
	//	}
	
	@Override
	public ProjectBatchResult getMainCountMessage() {
		ProjectBatchResult result = new ProjectBatchResult();
		List<ProjectInfo> projectInfos = new ArrayList<ProjectInfo>();
		// 20161122 单独抓取 不再按照priject的难维护的方式抓取
		HashMap<String, FcsField> fieldMap = new HashMap<String, FcsField>();
		fieldMap.put("count", new FcsField("count", "数目", null, DataTypeEnum.STRING));
		fieldMap.put("phases", new FcsField("phases", "类型", null, DataTypeEnum.STRING));
		String sql = "SELECT COUNT(*) AS  count , phases FROM project   " + " GROUP BY phases ";
		PmReportDOQueryOrder monthOrder = new PmReportDOQueryOrder();
		monthOrder.setSql(sql);
		monthOrder.setLimitStart(0);
		monthOrder.setPageSize(0);
		monthOrder.setFieldMap(fieldMap);
		List<DataListItem> items = pmReportService.doQuery(monthOrder);
		if (items != null && ListUtil.isNotEmpty(items)) {
			
			for (DataListItem item : items) {
				HashMap<String, Object> itemMap = item.getMap();
				String count = (String) itemMap.get("count");
				String phases = (String) itemMap.get("phases");
				
				if (StringUtil.isBlank(phases)) {
					continue;
				}
				ProjectPhasesEnum phasesEnum = ProjectPhasesEnum.getByCode(phases);
				if (phasesEnum == null) {
					continue;
				}
				ProjectInfo info = new ProjectInfo();
				info.setPhases(phasesEnum);
				info.setCount(Integer.valueOf(count));
				projectInfos.add(info);
			}
		}
		
		//		List<ProjectDO> projectDOs = projectDAO.findCountGroupByPhases();
		//		if (projectDOs != null) {
		//			for (ProjectDO infoDO : projectDOs) {
		//				if (infoDO == null || StringUtil.isBlank(infoDO.getPhases())) {
		//					continue;
		//				}
		//				ProjectPhasesEnum phasesEnum = ProjectPhasesEnum.getByCode(infoDO.getPhases());
		//				if (phasesEnum == null) {
		//					continue;
		//				}
		//				ProjectInfo info = new ProjectInfo();
		//				info.setPhases(phasesEnum);
		//				info.setCount(infoDO.getTimeLimit());
		//				projectInfos.add(info);
		//			}
		//		}
		result.setProjectInfos(projectInfos);
		result.setSuccess(true);
		return result;
	}
	
	@Override
	public FcsBaseResult updateByProjectCode(final UpdateProjectBaseOrder order) {
		return commonProcess(order, "修改项目信息", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				ProjectDO project = projectDAO.findByProjectCodeForUpdate(order.getProjectCode());
				if (null == project) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "项目不存在");
				}
				
				BeanCopier.staticCopy(order, project);
				projectDAO.update(project);
				
				return null;
			}
		}, null, null);
	}
	
	@Override
	public ProjectCandoResult getCando(String projectCode) {
		return getCandoByProject(queryByCode(projectCode, false));
	}
	
	/***
	 * 检查项目是否被退回（过会前准确,过会后存在复议被退回的）
	 * @param projectCode
	 * @return
	 */
	private boolean checkCouncilBack(String projectCode) {
		//查看是否被退回
		boolean hasBack = false;
		//最新的会议
		CouncilProjectVoteResultInfo newest = null;
		CouncilVoteProjectQueryOrder voteProjectQueryOrder = new CouncilVoteProjectQueryOrder();
		voteProjectQueryOrder.setProjectCode(projectCode);
		QueryBaseBatchResult<CouncilProjectVoteResultInfo> voteResult = councilProjectService
			.queryProjectVoteResult(voteProjectQueryOrder);
		if (voteResult.isSuccess()) {
			for (CouncilProjectVoteResultInfo vote : voteResult.getPageList()) {
				if (newest == null || vote.getId() > newest.getId())
					newest = vote;
			}
		}
		if (newest != null
			&& newest.getCompereMessage() == ProjectCouncilCompereMessageEnum.INVESTIGATING_PHASES) {
			hasBack = true;
		}
		return hasBack;
	}
	
	@Override
	public ProjectCandoResult getCandoByProject(ProjectInfo project) {
		ProjectCandoResult result = new ProjectCandoResult();
		try {
			if (project == null) {
				result.setSuccess(false);
				result.setMessage("项目不存在");
				result.forbiddenAll();
				return result;
			}
			ProjectPhasesEnum phase = project.getPhases(); //阶段
			String projectCode = project.getProjectCode(); //项目编号
			String busiType = project.getBusiType(); //业务类型
			boolean isMainContractSign = StringUtil.isNotBlank(project.getContractNo()); //是否上传主合同
			boolean isRedoProject = project.getIsRedoProject() == BooleanEnum.IS;
			result.setSuccess(true);
			result.setProject(project);
			
			//项目已暂缓
			if (project.getStatus() == ProjectStatusEnum.PAUSE) {
				result.setMessage("该项目已暂缓");
				return result;
			} else if (project.getStatus() == ProjectStatusEnum.FAILED) {
				result.forbiddenAll();
			} else if (project.getPhases() == ProjectPhasesEnum.FINISH_PHASES
						|| project.getStatus() == ProjectStatusEnum.FINISH) { //项目已完成
				result.setMessage("该项目已完成");
				result.forbiddenAll();
				result.setChargeNotice(1);//项目完成可收费
				return result;
			}
			
			//除了承销以外，风险上会、风险处理长亮
			if (!ProjectUtil.isUnderwriting(busiType)) {
				result.setRiskHandle(1);
				result.setRiskReport(1);
			}
			
			//立项阶段trans
			if (phase == ProjectPhasesEnum.SET_UP_PHASES) {
				result.setSetup(1);
				result.setMessage("当前项目正在立项");
				return result;
			}
			
			//尽职调查阶段
			if (phase == ProjectPhasesEnum.INVESTIGATING_PHASES) {
				result.setSetup(3);
				result.setInvestigation(1);
				result.setMessage("当前项目进入到尽调阶段，可做的操作：尽职调查报告");
				
				//查看是否复议
				if (project.getLastRecouncilTime() != null) {
					result.setInvestigation(2);
					result.setMessage("当前项目进入到尽调阶段 [复]，可做的操作：编辑尽职调查报告重新申请上会");
					return result;
				}
				//查看是否被退回
				if (checkCouncilBack(projectCode)) {
					result.setInvestigation(1);
					result.setMessage("当前项目会议评审被退回，可做操作：编辑尽职调查报告重新申请上会");
				}
				return result;
			}
			
			//评审阶段
			else if (phase == ProjectPhasesEnum.COUNCIL_PHASES) {
				result.setSetup(3);
				result.setInvestigation(3);
				result.setCouncil(1);
				result.setMessage("当前项目进入到评审阶段，需要根据评审的结果进行下一步操作");
				//查询评审结果
				if (project.getApprovalTime() != null && project.getSpId() > 0) {//有评审结果了
					FCouncilSummaryProjectInfo summary = councilSummaryService
						.queryProjectCsBySpId(project.getSpId());
					if (summary.getVoteResult() == ProjectVoteResultEnum.END_NOPASS
						|| summary.getOneVoteDown() != OneVoteResultEnum.PASS) {
						if (project.getLastRecouncilTime() == null) {
							result.setMessage("当前项目会议评审未通过，可通过项目列表的[ 复议 ]按钮对该项目发起复议");
						} else if (project.getApprovalTime().after(project.getLastRecouncilTime())) {//通过时间在复议时间之后结果才对
							result.setMessage("当前项目复议未通过");
							result.forbiddenAll();
						}
					} else if (summary.getVoteResult() == ProjectVoteResultEnum.END_QUIT) {
						result.setMessage("当前项目本次不议等待重新上会");
					} else {
						result.setSetup(3);
						result.setInvestigation(3);
						result.setCouncil(3);
						result.setContract(1);
						result.setContract_apply(1);
						result.setMessage("当前项目会议评审已通过，可做的操作：合同申请");
					}
				}
				
				//查看是否被退回
				if (checkCouncilBack(projectCode)) {
					result.setCouncil(0);
					result.setInvestigation(1);
					result.setMessage("当前项目会议评审被退回，可做操作：编辑尽职调查报告重新申请上会");
				}
				
				return result;
			}
			
			//合同阶段
			else if (phase == ProjectPhasesEnum.CONTRACT_PHASES
						&& project.getIsRedoProject() != BooleanEnum.IS) {
				result.setSetup(3);
				result.setInvestigation(3);
				result.setCouncil(3);
				
				result.setMessage("当前项目会议评审已通过，可做的操作：合同申请");
				if (isMainContractSign) { //主合同回传
					//担保委贷回传后阶段还在合同阶段、承销发债回传后进入资金募集阶段、诉保直接进入保后阶段
					if (ProjectUtil.isGuarantee(busiType) || ProjectUtil.isEntrusted(busiType)) {
						//放用款可做
						int[] loanUseCando = getLoanUseChargeLetterCando(project);
						result.setLoanUse(loanUseCando[0]);
						result.setLoanUse_apply(loanUseCando[1]);
						result.setLoanUse_receipt(loanUseCando[2]);
						result.setChargeNotice(loanUseCando[3]);
						result.setLetter(loanUseCando[4]);
						//授信落实可做
						result.setCreditConfirm(getCreditConfirmCando(project));
						result.setMessage("该项目的主合同已回传，可做操作：落实授信条件、费用收取通知、放用款申请");
					}
				}
				
				int[] contractCando = getContractCando(project);
				result.setContract(contractCando[0]);
				result.setContract_apply(contractCando[1]);
				result.setContract_stamp(contractCando[2]);
				result.setContract_receipt(contractCando[3]);
				
				return result;
				
			} else {
				
				//立项 尽调 评审已完成
				result.setSetup(3);
				result.setInvestigation(3);
				result.setCouncil(3);
				if (project.getIsRedoProject() == BooleanEnum.IS) {
					result.setSetup(0);
					result.setInvestigation(0);
					result.setCouncil(0);
				}
				
				//合同可做
				int[] contractCando = getContractCando(project);
				result.setContract(contractCando[0]);
				result.setContract_apply(contractCando[1]);
				result.setContract_stamp(contractCando[2]);
				result.setContract_receipt(contractCando[3]);
				//放用款可做
				int[] loanUseCando = getLoanUseChargeLetterCando(project);
				result.setLoanUse(loanUseCando[0]);
				result.setLoanUse_apply(loanUseCando[1]);
				result.setLoanUse_receipt(loanUseCando[2]);
				result.setChargeNotice(loanUseCando[3]);
				result.setLetter(loanUseCando[4]);
				//授信落实可做
				result.setCreditConfirm(getCreditConfirmCando(project));
				//解保可做
				result.setRelease(getReleaseCando(project));
				
				if (ProjectUtil.isGuarantee(busiType) || ProjectUtil.isEntrusted(busiType)) {//担保委贷
					//					if (isMainContractSign) {
					//						result.setChargeNotice(1);
					//						result.setMessage("该项目的主合同已回传，可做操作：落实授信条件、费用收取通知、放用款申请");
					//					}
					
					if (project.getLoanedAmount().greaterThan(ZERO_MONEY)) { //有回执
						//result.setChargeNotice(1);
						result.setRiskLevel(1);
						result.setAfterCheck(1);
						result
							.setMessage("当前项目有已完成的放用款，可做的操作：落实授信条件、费用收取通知、放用款申请、保后检查报告、项目风险等级评定、解保申请");
					}
				} else if (ProjectUtil.isUnderwriting(busiType)) { //承销
				
					if (isMainContractSign) { //主合同回传
						result.setSellConfirm(1);
						//result.setChargeNotice(1);
						result.setMessage("该项目的主合同已回传，可做操作：合同申请、发售信息维护、费用收取通知");
						
						//发售完成
						List<ProjectIssueInformationInfo> sellInfos = projectIssueInformationService
							.findProjectIssueInformationByProjectCode(projectCode);
						
						if (ListUtil.isNotEmpty(sellInfos)) {
							boolean sellFinish = false;
							for (ProjectIssueInformationInfo sellInfo : sellInfos) {
								if (sellInfo.getStatus() == SellStatusEnum.SELL_FINISH) {
									sellFinish = true;
									break;
								}
							}
							if (sellFinish) {
								result.setSellConfirm(3);
								result.setMessage("该项目发售已完成，可做操作：合同申请、费用收取通知");
							} else {
								result.setSellConfirm(2);
							}
						}
						//到期
						if (StringUtil.equals(project.getProjectOverTime(), BooleanEnum.YES.code())) {
							result.setExpireNotice(1);
							result.setMessage("该项目即将到期，可做操作：到期通知");
						}
					}
				} else if (ProjectUtil.isLitigation(busiType)) { //诉保
					if (isMainContractSign) {
						//result.setChargeNotice(1);
						result.setAfterCheck(1);
						result.setMessage("当前项目主合同已回传，可做操作：合同申请、费用收取通知、保后检查报告、解保申请");
					}
				} else if (ProjectUtil.isBond(busiType)) { //发债
				
					if (isMainContractSign || isRedoProject) { //合同回传
						//result.setChargeNotice(1);
						result.setSellConfirm(1);
						result.setMessage("该项目的主合同已回传，可做操作：落实授信条件、费用收取通知、发售信息维护");
						
						//募集到资金后
						if (project.getAccumulatedIssueAmount().greaterThan(ZERO_MONEY)) {
							result.setMessage("该项目已有募集到的资金，可做操作：放用款申请");
						}
						
						//有回执后
						if (project.getLoanedAmount().greaterThan(ZERO_MONEY)) {
							result.setAfterCheck(1);
							result.setRiskLevel(1);
							result
								.setMessage("当前项目有已完成的放用款，可做的操作：落实授信条件、费用收取通知、发售信息维护、放用款申请、保后检查报告、项目风险等级评定、解保申请");
						}
						
						//发售完成
						List<ProjectIssueInformationInfo> sellInfos = projectIssueInformationService
							.findProjectIssueInformationByProjectCode(projectCode);
						if (ListUtil.isNotEmpty(sellInfos)) {
							boolean sellFinish = false;
							for (ProjectIssueInformationInfo sellInfo : sellInfos) {
								if (sellInfo.getStatus() == SellStatusEnum.SELL_FINISH) {
									sellFinish = true;
									break;
								}
							}
							if (sellFinish) {
								result.setSellConfirm(3);
							} else {
								result.setSellConfirm(2);
							}
						}
					}
				}
				
				//代偿条线
				int[] compLine = getCompLineCando(project);
				result.setCapital(compLine[0]);
				result.setRecovery(compLine[1]);
			}
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("获取项目可操作项出错");
			logger.error("获取项目可操作项出错", e);
		}
		return result;
	}
	
	/***
	 * 获取合同可做<br>
	 * idnex0 : contract<br>
	 * index1 : contract_apply<br>
	 * index2 : contract_stamp<br>
	 * index3 : contract_receipt
	 * @param project
	 * @return
	 */
	private int[] getContractCando(ProjectInfo project) {
		int[] cando = new int[] { 0, 0, 0, 0 };
		try {
			
			int contr = 0;
			int apply = 0;
			int stamp = 0;
			int receipt = 0;
			
			ProjectPhasesEnum phases = project.getPhases();
			ProjectStatusEnum status = project.getStatus();
			if ((ProjectPhasesEnum.CONTRACT_PHASES == phases
					|| ProjectPhasesEnum.LOAN_USE_PHASES == phases
					|| ProjectPhasesEnum.FUND_RAISING_PHASES == phases
					|| ProjectPhasesEnum.AFTERWARDS_PHASES == phases || ProjectPhasesEnum.RECOVERY_PHASES == phases)
				&& (ProjectStatusEnum.NORMAL == status || ProjectStatusEnum.RECOVERY == status
					|| ProjectStatusEnum.TRANSFERRED == status
					|| ProjectStatusEnum.SELL_FINISH == status
					|| ProjectStatusEnum.EXPIRE == status || ProjectStatusEnum.OVERDUE == status)) {
				contr = 1;
				apply = 1;
			}
			
			//查询已确认的合同
			ProjectContractQueryOrder contractOrder = new ProjectContractQueryOrder();
			contractOrder.setPageSize(200);
			contractOrder.setProjectCode(project.getProjectCode());
			
			QueryBaseBatchResult<ProjectContractResultInfo> batchResult = projectContractService
				.query(contractOrder);
			
			int applyCount = 0;
			int stampCount = 0;
			int receiptCount = 0;
			if (batchResult.getTotalCount() > 0) {
				for (ProjectContractResultInfo contract : batchResult.getPageList()) {
					if (ContractStatusEnum.CONFIRMED == contract.getContractStatus()) { //已经确认可申请用印
						if (BooleanEnum.IS.code().equals(contract.getIsCanStampApply())
							&& !"0".equals(contract.getCnt())) {
							stamp = 1;
						}
						applyCount++;
					} else if (ContractStatusEnum.SEAL == contract.getContractStatus()) { //已用印可回执
						receipt = 1;
						applyCount++;
						stampCount++;
					} else if (ContractStatusEnum.RETURN == contract.getContractStatus()) {//已回传
						applyCount++;
						stampCount++;
						receiptCount++;
					} else if (ContractStatusEnum.CHECKED == contract.getContractStatus()
								|| ContractStatusEnum.SEALING == contract.getContractStatus()
								|| ContractStatusEnum.INVALID == contract.getContractStatus()) {
						applyCount++;
						if (ContractStatusEnum.INVALID == contract.getContractStatus()) {
							stampCount++;
						}
					}
				}
			}
			//申请数大于0表示可重复做了
			if (applyCount > 0 && apply > 0) {
				contr = 2;
				apply = 2;
			}
			//用印数量大于0表示可重复做了
			if (stampCount > 0 && stamp > 0) {
				stamp = 2;
			}
			//回执数量大于0表示可重复做了
			if (receiptCount > 0 && receipt > 0) {
				receipt = 2;
			}
			
			cando[0] = contr;
			cando[1] = apply;
			cando[2] = stamp;
			cando[3] = receipt;
			
		} catch (Exception e) {
			logger.error("获取合同可做信息出错：{}", e);
		}
		return cando;
	}
	
	/***
	 * 获取放用款，收费通知，函、通知书可做<br>
	 * idnex0 : loanUse<br>
	 * index1 : loanUse_apply<br>
	 * index2 : loanUse_receipt<br>
	 * index3 : chargeNotice<br>
	 * index4 : letter<br>
	 * @param project
	 * @return
	 */
	private int[] getLoanUseChargeLetterCando(ProjectInfo project) {
		ProjectSimpleDetailInfo simpleDetailInfo = getSimpleDetailInfo(project);
		int[] cando = new int[] { 0, 0, 0, 0, 0 };
		try {
			boolean isRedoProject = project.getIsRedoProject() == BooleanEnum.IS;
			if (StringUtil.isNotEmpty(project.getContractNo()) || isRedoProject) {
				
				//满足必须要有一个通过的授信落实单子才能放用款
				boolean creditCondition = false;
				List<ProjectCreditConditionDO> conditions = projectCreditConditionDAO
					.findByProjectCode(project.getProjectCode());
				FCreditConditionConfirmQueryOrder cOrder = new FCreditConditionConfirmQueryOrder();
				cOrder.setProjectCode(project.getProjectCode());
				cOrder.setFormStatus(FormStatusEnum.APPROVAL.code());
				QueryBaseBatchResult<FCreditConditionConfirmInfo> confirms = projectCreditConditionService
					.query(cOrder);
				
				//重新授信的项目不需要授信条件落实前提条件
				if (isRedoProject || conditions == null || conditions.size() == 0
					|| confirms.getTotalCount() > 0) {
					creditCondition = true;
				}
				
				//查看收费通知和函、通知书
				if (creditCondition || isRedoProject) {
					
					cando[3] = 1;
					
					//历史收费情况
					List<FChargeNotificationDO> chargeList = fChargeNotificationDAO
						.findByProjectCode(project.getProjectCode());
					boolean hasCharge = false;
					for (FChargeNotificationDO charge : chargeList) {
						if (!StringUtil.equals(charge.getStatus(), "DRAFT")
							&& !StringUtil.equals(charge.getStatus(), "DELETED")) {
							hasCharge = true;
							break;
						}
					}
					if (hasCharge)
						cando[3] = 2;
					
					cando[4] = 1;
					//查询函、通知书
					ProjectContractQueryOrder letterOrder = new ProjectContractQueryOrder();
					letterOrder.setProjectCode(project.getProjectCode());
					letterOrder.setApplyType(ProjectContractTypeEnum.PROJECT_LETTER);
					QueryBaseBatchResult<ProjectContractResultInfo> letterList = projectContractService
						.query(letterOrder);
					boolean hasLetter = false;
					if (letterList != null && letterList.getTotalCount() > 0) {
						for (ProjectContractResultInfo letter : letterList.getPageList()) {
							if (letter.getFormStatus() != FormStatusEnum.DRAFT
								&& letter.getFormStatus() != FormStatusEnum.DELETED) {
								hasLetter = true;
								break;
							}
						}
					}
					if (hasLetter)
						cando[4] = 2;
				}
				
				//承销和诉保没有放用款
				if (ProjectUtil.isUnderwriting(project.getBusiType())
					|| ProjectUtil.isLitigation(project.getBusiType())) {
					
				} else {
					//收费条件
					boolean chargeCondition = false;
					//先收
					if (simpleDetailInfo != null
						&& simpleDetailInfo.getChargePhase() == ChargePhaseEnum.BEFORE) {
						//查看收费情况
						FChargeNotificationQueryOrder qOrder = new FChargeNotificationQueryOrder();
						qOrder.setFormStatus(FormStatusEnum.APPROVAL.code());
						qOrder.setProjectCode(project.getProjectCode());
						qOrder.setQueryFeeList(true);
						QueryBaseBatchResult<FChargeNotificationResultInfo> chargeList = chargeNotificationService
							.queryChargeNotificationList(qOrder);
						//保费收取情况
						Money guaranteeFee = Money.zero();
						Money chargeMoney = Money.zero();
						for (FChargeNotificationResultInfo feeInfo : chargeList.getPageList()) {
							if (ListUtil.isNotEmpty(feeInfo.getFeeList())) {
								for (FChargeNotificationFeeInfo fee : feeInfo.getFeeList()) {
									chargeMoney.addTo(fee.getChargeAmount());
									if (fee.getFeeType() == FeeTypeEnum.GUARANTEE_FEE) {
										guaranteeFee.addTo(fee.getChargeAmount());
									}
								}
							}
						}
						//担保类需要先收担保费
						if (!ProjectUtil.isEntrusted(project.getBusiType())) {
							if (guaranteeFee.greaterThan(ZERO_MONEY)) {
								chargeCondition = true;
							}
						} else if (chargeMoney.greaterThan(ZERO_MONEY)) {
							chargeCondition = true;
						}
					} else {
						chargeCondition = true;
					}
					
					//重新授信的项目不需要收费前提条件
					if (isRedoProject)
						chargeCondition = true;
					
					//最大可放用款金额
					Money maxAmount = project.getContractAmount();
					if (isRedoProject) {
						maxAmount = project.getAmount();
					}
					
					boolean loanAmountCondition = false;
					//放款金额情况 
					//最高授信额-> 已解保金额+最大可放款金额 > 已放款金额
					//非最高授信 -> 最大可放款金额 > 已放款金额
					if (project.getIsMaximumAmount() == BooleanEnum.IS) {
						if (maxAmount.add(project.getReleasedAmount())
							.subtract(project.getLoanedAmount()).greaterThan(ZERO_MONEY)) {
							loanAmountCondition = true;
						}
					} else {
						if (maxAmount.greaterThan(project.getLoanedAmount())) {
							loanAmountCondition = true;
						}
					}
					
					//用款情况
					//发债：已发售金额>已用款金额
					//其他：已放款金额>已经用款金额
					boolean useAmountCondition = false;
					boolean isBond = ProjectUtil.isBond(project.getBusiType());
					if (isBond
						&& project.getLoanedAmount().greaterThan(
							project.getAccumulatedIssueAmount())) {
						if (project.getAccumulatedIssueAmount()
							.greaterThan(project.getUsedAmount()))
							useAmountCondition = true;
					} else if (!isBond || project.getAccumulatedIssueAmount().getCent() > 0
								&& project.getLoanedAmount().greaterThan(project.getUsedAmount())) {
						useAmountCondition = true;
					}
					
					//重新授信项目 只要放款金额大于=用款金额就可以用款
					if (isRedoProject
						&& !project.getUsedAmount().greaterThan(project.getLoanedAmount())) {
						useAmountCondition = true;
					}
					
					//同时满足条件可做
					if (creditCondition
						&& (loanAmountCondition || (useAmountCondition && chargeCondition))) {
						cando[0] = 1;
						cando[1] = 1;
					}
					
					//已经做过申请
					LoanUseApplyQueryOrder queryOrder = new LoanUseApplyQueryOrder();
					queryOrder.setProjectCode(project.getProjectCode());
					queryOrder.setFormStatus(FormStatusEnum.APPROVAL.code());
					queryOrder.setPageSize(200);
					QueryBaseBatchResult<LoanUseApplyFormInfo> loanUseResult = loanUseApplyService
						.searchApplyForm(queryOrder);
					boolean receipt = false;
					int receiptCount = 0;
					if (loanUseResult.getTotalCount() > 0) {
						cando[0] = 1;
						cando[1] = 2;
						
						//已经做过放用款回执
						if (project.getLoanedAmount().greaterThan(ZERO_MONEY)) {
							cando[0] = 2;
						}
						for (LoanUseApplyFormInfo info : loanUseResult.getPageList()) {
							if (info.getReceiptId() == 0
								&& info.getApplyType() != LoanUseApplyTypeEnum.USE) {
								receipt = true;
							} else {
								receiptCount++;
							}
						}
					}
					
					if (receipt) {
						cando[2] = 1;
						if (receiptCount > 0)
							cando[2] = 2;
					}
					
					//放用款完成   合同金额已放款、用款完成
					if (!isRedoProject
						&& project.getIsMaximumAmount() != BooleanEnum.IS
						&& project.getLoanedAmount().greaterThan(ZERO_MONEY)
						&& project.getLoanedAmount().getCent() >= project.getContractAmount()
							.getCent()
						&& project.getUsedAmount().getCent() >= project.getContractAmount()
							.getCent()) {
						cando[0] = 3;
						cando[1] = 3;
					}
					
				}
			}
		} catch (Exception e) {
			logger.error("获取放用款可做信息出错：{}", e);
		}
		return cando;
	}
	
	/**
	 * 获取授信落实可做状态 <br>
	 * index0 => creditConfirm
	 * @param project
	 * @return
	 */
	public int getCreditConfirmCando(ProjectInfo project) {
		int creditConfirm = 0;
		try {
			boolean isRedoProject = project.getIsRedoProject() == BooleanEnum.IS;
			if (StringUtil.isNotBlank(project.getContractNo()) || isRedoProject) { //主合同回传后才能落实
			
				//查询项目是否有可授信落实条件
				List<ProjectCreditConditionInfo> credits = projectCreditConditionService
					.findProjectCreditConditionByProjectCode(project.getProjectCode());
				//所有授信落实条件
				int allCount = credits == null ? 0 : credits.size();
				if (allCount > 0) {
					int canConfirmCount = 0; //所有能落实数量
					int confirmCount = 0; //已确认数量
					int applyCount = 0; //申请中数量
					for (ProjectCreditConditionInfo credit : credits) {
						if (credit.getIsConfirm() == BooleanEnum.IS) {
							confirmCount++;
						} else if ((credit.getStatus() == CreditCheckStatusEnum.NOT_APPLY || credit
							.getStatus() == CreditCheckStatusEnum.CHECK_PASS)
									&& StringUtil.isNotBlank(credit.getContractNo())) { //能确认的
							canConfirmCount++;
						} else if (credit.getStatus() != CreditCheckStatusEnum.NOT_APPLY
									&& credit.getStatus() != CreditCheckStatusEnum.CHECK_PASS) {
							applyCount++;
						}
					}
					
					if (canConfirmCount > 0 || applyCount > 0) { //可确认的
						if (confirmCount > 0) {
							creditConfirm = 2;
						} else {
							creditConfirm = 1;
						}
					} else if (allCount > confirmCount) { //还有没确认的、但是又没有可以确认的
						//do nothing
					} else { //已经确认完成
						creditConfirm = 3;
					}
				}
			}
		} catch (Exception e) {
			logger.error("获取授信落实可做状态出错：{}", e);
		}
		return creditConfirm;
	}
	
	/**
	 * 获取解保做状态 <br>
	 * index0 => release
	 * @param project
	 * @return
	 */
	private int getReleaseCando(ProjectInfo project) {
		int release = 0;
		try {
			//承销不可做解保
			if (project.isUnderwriting())
				return release;
			
			boolean cando = false;
			boolean isFinish = false;
			ReleaseProjectDO releaseProject = releaseProjectDAO.findByProjectCode(project
				.getProjectCode());
			if (releaseProject != null) {
				if (StringUtil.equals(ReleaseProjectStatusEnum.AVAILABLE.code(),
					releaseProject.getStatus())
					|| StringUtil.equals(ReleaseProjectStatusEnum.RELEASING.code(),
						releaseProject.getStatus())) {
					cando = true;
				} else if (StringUtil.equals(ReleaseProjectStatusEnum.FINISHED.code(),
					releaseProject.getStatus())) {
					isFinish = true;
				}
			}
			//解保项目中没有就看有没有可解保的授信条件
			//查询项目是否有可授信落实条件
			List<ProjectCreditConditionInfo> credits = projectCreditConditionService
				.findProjectCreditConditionByProjectCode(project.getProjectCode());
			if (credits != null) {
				//有落实且未解保的授信条件则可做解保
				for (ProjectCreditConditionInfo credit : credits) {
					if (credit.getIsConfirm() == BooleanEnum.IS
						&& (credit.getReleaseStatus() == ReleaseStatusEnum.WAITING || credit
							.getReleaseStatus() == ReleaseStatusEnum.RELEASING)) {
						cando = true;
						break;
					}
				}
			}
			
			//看看做过解保没有
			CounterGuaranteeQueryOrder qOrder = new CounterGuaranteeQueryOrder();
			qOrder.setProjectCode(project.getProjectCode());
			qOrder.setFormStatus(FormStatusEnum.APPROVAL);
			QueryBaseBatchResult<ReleaseApplyInfo> doneRealse = counterGuaranteeService
				.queryList(qOrder);
			boolean hasRealse = doneRealse != null && doneRealse.isSuccess()
								&& doneRealse.getTotalCount() > 0;
			
			if (cando)
				release = 1;
			if (cando && hasRealse)
				release = 2;
			if (isFinish && !cando)
				release = 3;
		} catch (Exception e) {
			logger.error("获取解保可做状态出错：{}", e);
		}
		return release;
	}
	
	/**
	 * 获取项目代偿条线可做状态<br>
	 * index0 => capital <br>
	 * index1 => recovery
	 * @param project
	 * @return
	 */
	private int[] getCompLineCando(ProjectInfo project) {
		int[] comp = new int[] { 0, 0 };
		try {
			//查看风险处置方案是否有代偿方案
			boolean hasComp = false;
			CouncilSummaryRiskHandleQueryOrder rOrder = new CouncilSummaryRiskHandleQueryOrder();
			rOrder.setProjectCode(project.getProjectCode());
			rOrder.setPageSize(200);
			QueryBaseBatchResult<CouncilSummaryRiskHandleInfo> riskHandleResult = councilSummaryService
				.queryApprovalRiskHandleCs(rOrder);
			if (riskHandleResult != null && riskHandleResult.getTotalCount() > 0) {
				for (CouncilSummaryRiskHandleInfo riskHandle : riskHandleResult.getPageList()) {
					if (riskHandle.getIsComp() == BooleanEnum.IS) {
						hasComp = true;
						break;
					}
				}
			}
			//有代偿
			if (hasComp) {
				comp[0] = 1; //可做划付了
				ProjectChargePayQueryOrder qOrder = new ProjectChargePayQueryOrder();
				qOrder.setAffirmFormType("CAPITAL_APPROPROATION_APPLY");
				qOrder.setProjectCode(project.getProjectCode());
				List<String> feeTypeList = Lists.newArrayList();
				feeTypeList.add(PaymentMenthodEnum.COMPENSATORY_PRINCIPAL.code());
				feeTypeList.add(PaymentMenthodEnum.COMPENSATORY_INTEREST.code());
				feeTypeList.add(PaymentMenthodEnum.COMPENSATORY_PENALTY.code());
				feeTypeList.add(PaymentMenthodEnum.COMPENSATORY_LIQUIDATED_DAMAGES.code());
				feeTypeList.add(PaymentMenthodEnum.COMPENSATORY_OTHER.code());
				qOrder.setFeeTypeList(feeTypeList);
				QueryBaseBatchResult<ProjectChargePayInfo> compResult = fFinanceAffirmService
					.queryProjectChargePayDetail(qOrder);
				if (compResult != null && compResult.getTotalCount() > 0) {
					comp[0] = 2; //已经划付了
					comp[1] = 1; //可以追偿了
					//看看追偿是不是完了
					ProjectRecoveryQueryOrder cOrder = new ProjectRecoveryQueryOrder();
					cOrder.setProjectCode(project.getProjectCode());
					QueryBaseBatchResult<ProjectRecoveryListInfo> recoverList = projectRecoveryService
						.queryRecovery(cOrder);
					if (recoverList != null && recoverList.getTotalCount() > 0) {
						for (ProjectRecoveryListInfo recovery : recoverList.getPageList()) {
							if (recovery.getRecoveryStatus() == ProjectRecoveryStatusEnum.CLOSED) {
								//追偿完了
								comp[0] = 3;
								comp[1] = 3;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("获取项目代偿条线可做状态出错：{}", e);
		}
		return comp;
	}
	
	/**
	 * 异步执行同步、防止在其他线程中事务未提交导致查询处理的数据不是最新的
	 * @param projectCode
	 * @return
	 * @see com.born.fcs.pm.ws.service.common.ProjectService#syncForecastDeposit(java.lang.String)
	 */
	@Override
	public FcsBaseResult syncForecastDeposit(String projectCode) {
		FcsBaseResult result = createResult();
		if (StringUtil.isNotEmpty(projectCode)) {
			asynchronousTaskJob.addAsynchronousService(this, new String[] { projectCode });
		}
		result.setSuccess(true);
		return result;
	}
	
	@Override
	public FcsBaseResult redoProject(final ProjectRedoOrder order) {
		return commonProcess(order, "项目重新授信", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				
				Date now = FcsPmDomainHolder.get().getSysDate();
				
				FCouncilSummaryRiskHandleDO riskHandle = FCouncilSummaryRiskHandleDAO
					.findById(order.getHandleId());
				if (riskHandle == null) {
					throw ExceptionFactory
						.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "风险处置方案未找到");
				}
				if (!StringUtil.equals(riskHandle.getIsRedo(), BooleanEnum.IS.code())) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.DO_ACTION_STATUS_ERROR,
						"非重新授信风险处置方案");
				}
				if (StringUtil.isNotBlank(riskHandle.getRedoProjectCode())) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.DO_ACTION_STATUS_ERROR,
						"已重新授信");
				}
				//查询原项目
				ProjectDO sourceProject = projectDAO.findByProjectCode(riskHandle.getProjectCode());
				if (sourceProject == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "源项目未找到");
				}
				
				//标记成风险项目
				sourceProject.setIsRedo(BooleanEnum.IS.code());
				projectDAO.update(sourceProject);
				
				//授信信息
				FCouncilSummaryProjectDO sProject = FCouncilSummaryProjectDAO
					.findByRiskHandleId(riskHandle.getHandleId());
				
				ProjectDO newProject = new ProjectDO();
				
				if (sProject != null) {
					newProject.setSpId(sProject.getSpId());
					newProject.setSpCode(sProject.getSpCode());
				}
				
				newProject.setApprovalTime(now);
				newProject.setRawAddTime(now);
				newProject.setBelongToNc(sourceProject.getBelongToNc());
				newProject.setBusiManagerId(sourceProject.getBusiManagerId());
				newProject.setBusiManagerName(sourceProject.getBusiManagerName());
				newProject.setBusiManagerAccount(sourceProject.getBusiManagerAccount());
				newProject.setBusiManagerbId(sourceProject.getBusiManagerbId());
				newProject.setBusiManagerbName(sourceProject.getBusiManagerbName());
				newProject.setBusiManagerbAccount(sourceProject.getBusiManagerbAccount());
				newProject.setCustomerId(sourceProject.getCustomerId());
				newProject.setCustomerName(sourceProject.getCustomerName());
				newProject.setCustomerType(sourceProject.getCustomerType());
				newProject.setDeptId(sourceProject.getDeptId());
				newProject.setDeptCode(sourceProject.getDeptCode());
				newProject.setDeptName(sourceProject.getDeptName());
				newProject.setDeptPath(sourceProject.getDeptPath());
				newProject.setDeptPathName(sourceProject.getDeptPathName());
				newProject.setIndustryCode(sourceProject.getIndustryCode());
				newProject.setIndustryName(sourceProject.getIndustryName());
				newProject.setIsMaximumAmount(sourceProject.getIsMaximumAmount());
				newProject.setIsCourtRuling(BooleanEnum.NO.code());
				newProject.setIsApproval(BooleanEnum.IS.code());
				newProject.setIsApprovalDel(BooleanEnum.NO.code());
				newProject.setIsRecouncil(BooleanEnum.NO.code());
				newProject.setIsContinue(BooleanEnum.NO.code());
				newProject.setLoanPurpose(sourceProject.getLoanPurpose());
				newProject.setStatus(ProjectStatusEnum.NORMAL.code());
				newProject.setPhases(ProjectPhasesEnum.AFTERWARDS_PHASES.code());
				newProject.setPhasesStatus(ProjectPhasesStatusEnum.APPROVAL.code());
				
				newProject.setAmount(riskHandle.getRedoAmount());
				newProject.setBusiType(riskHandle.getRedoBusiType());
				newProject.setBusiTypeName(riskHandle.getRedoBusiTypeName());
				newProject.setTimeLimit(riskHandle.getRedoTimeLimit());
				newProject.setTimeUnit(riskHandle.getRedoTimeUnit());
				
				//衍生项目标记
				newProject.setIsRedoProject(BooleanEnum.IS.code());
				newProject.setProjectName(riskHandle.getRedoProjectName());
				newProject.setRedoFrom(sourceProject.getProjectCode());
				newProject.setRedoRiskHandleId(riskHandle.getHandleId());
				//重新生成项目编号
				newProject.setProjectCode(genProjectCode(newProject.getBusiType(),
					newProject.getDeptCode()));
				riskHandle.setRedoProjectCode(newProject.getProjectCode());
				
				projectDAO.insert(newProject);
				FCouncilSummaryRiskHandleDAO.update(riskHandle);
				
				if (sProject != null) {
					//丰富授信信息
					BeanCopier.staticCopy(newProject, sProject);
					sProject.setApprovalTime(now);
					FCouncilSummaryProjectDAO.update(sProject);
					//同步授信条件
					councilSummaryProcessService.syncCreditCondition(sProject.getSpId(), newProject);
					//更新反担保附件项目编号
					CommonAttachmentDO queryDO = new CommonAttachmentDO();
					queryDO.setBizNo("spId_" + sProject.getSpId());
					queryDO.setModuleType(CommonAttachmentTypeEnum.COUNTER_GUARANTEE.code());
					List<CommonAttachmentDO> attachs = commonAttachmentDAO
						.findByBizNoModuleType(queryDO);
					if (ListUtil.isNotEmpty(attachs)) {
						for (CommonAttachmentDO attach : attachs) {
							attach.setProjectCode(newProject.getProjectCode());
							commonAttachmentDAO.update(attach);
						}
					}
				}
				
				//项目相关成员
				ProjectRelatedUserDO qdo = new ProjectRelatedUserDO();
				qdo.setIsCurrent(BooleanEnum.IS.code());
				qdo.setIsDel(BooleanEnum.NO.code());
				qdo.setProjectCode(sourceProject.getProjectCode());
				List<ProjectRelatedUserDO> relatedUserList = projectRelatedUserDAO.findByCondition(
					qdo, null, null, null, 0, 999);
				if (ListUtil.isNotEmpty(relatedUserList)) {
					String remark = "衍生自项目" + sourceProject.getProjectCode();
					for (ProjectRelatedUserDO relatedUser : relatedUserList) {
						relatedUser.setRelatedId(0);
						relatedUser.setProjectCode(newProject.getProjectCode());
						relatedUser.setRemark(remark);
						relatedUser.setRawAddTime(now);
						projectRelatedUserDAO.insert(relatedUser);
					}
				}
				
				//复制资金渠道
				List<ProjectChannelRelationInfo> cChannels = projectChannelRelationService
					.queryCapitalChannel(sourceProject.getProjectCode());
				if (ListUtil.isNotEmpty(cChannels)) {
					for (ProjectChannelRelationInfo c : cChannels) {
						ProjectChannelRelationDO relationDO = new ProjectChannelRelationDO();
						relationDO.setBusiType(newProject.getBusiType());
						relationDO.setBusiTypeName(newProject.getBusiTypeName());
						relationDO.setPhases(ProjectPhasesEnum.COUNCIL_PHASES.code());
						relationDO.setProjectCode(newProject.getProjectCode());
						relationDO.setChannelRelation(ChannelRelationEnum.CAPITAL_CHANNEL.code());
						relationDO.setRawAddTime(now);
						relationDO.setLatest(BooleanEnum.YES.code());
						relationDO.setChannelId(c.getChannelId());
						relationDO.setChannelType(c.getChannelType());
						relationDO.setChannelName(c.getChannelName());
						relationDO.setChannelCode(c.getChannelCode());
						relationDO.setSubChannelName(c.getSubChannelName());
						projectChannelRelationDAO.insert(relationDO);
					}
				}
				
				ProjectChannelRelationInfo pChanngel = projectChannelRelationService
					.queryProjectChannel(sourceProject.getProjectCode());
				if (pChanngel != null) {
					ProjectChannelRelationDO relationDO = new ProjectChannelRelationDO();
					relationDO.setBusiType(newProject.getBusiType());
					relationDO.setBusiTypeName(newProject.getBusiTypeName());
					relationDO.setPhases(ProjectPhasesEnum.INVESTIGATING_PHASES.code());
					relationDO.setProjectCode(newProject.getProjectCode());
					relationDO.setChannelRelation(ChannelRelationEnum.PROJECT_CHANNEL.code());
					relationDO.setRawAddTime(now);
					relationDO.setLatest(BooleanEnum.YES.code());
					relationDO.setChannelId(pChanngel.getChannelId());
					relationDO.setChannelType(pChanngel.getChannelType());
					relationDO.setChannelName(pChanngel.getChannelName());
					relationDO.setChannelCode(pChanngel.getChannelCode());
					relationDO.setSubChannelName(pChanngel.getSubChannelName());
					projectChannelRelationDAO.insert(relationDO);
				}
				
				return null;
			}
		}, null, null);
	}
	
	/**
	 * 生成项目编号
	 * @param busiType
	 * @return
	 */
	private synchronized String genProjectCode(String busiType, String deptCode) {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		deptCode = (deptCode == null ? "" : deptCode);
		busiType = (busiType == null ? "" : busiType);
		String projectCode = year + "-" + StringUtil.leftPad(deptCode, 3, "0") + "-"
								+ StringUtil.leftPad(busiType, 3, "0");
		String seqName = SysDateSeqNameEnum.PROJECT_CODE_SEQ.code() + projectCode;
		long seq = dateSeqService.getNextSeqNumberWithoutCache(seqName, false);
		projectCode += "-" + StringUtil.leftPad(String.valueOf(seq), 3, "0");
		return projectCode;
	}
	
	@Override
	public void execute(Object[] objects) {
		String projectCode = (String) objects[0];
		try {
			ProjectInfo project = queryByCode(projectCode, false);
			if (project != null && project.getEndTime() != null
				&& project.getCustomerDepositAmount().greaterThan(ZERO_MONEY)) {
				ForecastAccountOrder forecastAccountOrder = new ForecastAccountOrder();
				forecastAccountOrder.setForecastStartTime(project.getEndTime());
				forecastAccountOrder.setAmount(project.getCustomerDepositAmount());
				forecastAccountOrder.setForecastMemo("退还客户保证金（" + project.getProjectCode() + "）");
				forecastAccountOrder.setForecastType(ForecastTypeEnum.OUT_THKHBZJ);
				forecastAccountOrder.setFundDirection(FundDirectionEnum.OUT);
				forecastAccountOrder.setOrderNo(project.getProjectCode() + "_"
												+ ForecastFeeTypeEnum.REFOUND_DEPOSIT.code());
				forecastAccountOrder.setSystemForm(SystemEnum.PM);
				forecastAccountOrder.setUsedDeptId(String.valueOf(project.getDeptId()));
				forecastAccountOrder.setUsedDeptName(project.getDeptName());
				forecastAccountOrder.setFeeType(ForecastFeeTypeEnum.REFOUND_DEPOSIT);
				forecastAccountOrder.setProjectCode(project.getProjectCode());
				forecastAccountOrder.setCustomerId(project.getCustomerId());
				forecastAccountOrder.setCustomerName(project.getCustomerName());
				logger.info("退还客户保证金流出预测 , projectCode：{}, forecastAccountOrder：{} ",
					project.getProjectCode(), forecastAccountOrder);
				forecastServiceClient.add(forecastAccountOrder);
			}
		} catch (Exception e) {
			logger.error("同步退还客户保证金流出预测数据到资金系统出错：project {} {}", projectCode, e);
		}
	}
}
