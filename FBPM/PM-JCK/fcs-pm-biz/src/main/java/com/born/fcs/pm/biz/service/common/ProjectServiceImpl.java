package com.born.fcs.pm.biz.service.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import rop.thirdparty.com.google.common.collect.Lists;
import rop.thirdparty.com.google.common.collect.Maps;

import com.born.fcs.pm.biz.convert.DoConvert;
import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.exception.FcsPmBizException;
import com.born.fcs.pm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.pm.dal.dataobject.CouncilApplyDO;
import com.born.fcs.pm.dal.dataobject.CouncilProjectDO;
import com.born.fcs.pm.dal.dataobject.FCouncilSummaryProjectDO;
import com.born.fcs.pm.dal.dataobject.FormDO;
import com.born.fcs.pm.dal.dataobject.ProjectDO;
import com.born.fcs.pm.dal.dataobject.ProjectIssueInformationDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.domain.exception.FcsPmDomainException;
import com.born.fcs.pm.integration.bpm.service.client.user.SysOrg;
import com.born.fcs.pm.integration.bpm.service.client.user.UserDetailsServiceProxy;
import com.born.fcs.pm.integration.common.PropertyConfigService;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.ChargePhaseEnum;
import com.born.fcs.pm.ws.enums.ChargeTypeEnum;
import com.born.fcs.pm.ws.enums.CompareEnum;
import com.born.fcs.pm.ws.enums.CouncilApplyStatusEnum;
import com.born.fcs.pm.ws.enums.CustomerTypeEnum;
import com.born.fcs.pm.ws.enums.ExeStatusEnum;
import com.born.fcs.pm.ws.enums.FormRelatedUserTypeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectCouncilCompereMessageEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectRelatedUserTypeEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectVoteResultEnum;
import com.born.fcs.pm.ws.enums.SellStatusEnum;
import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.FormRelatedUserInfo;
import com.born.fcs.pm.ws.info.common.ProjectApprovaInfo;
import com.born.fcs.pm.ws.info.common.ProjectBondDetailInfo;
import com.born.fcs.pm.ws.info.common.ProjectEntrustedDetailInfo;
import com.born.fcs.pm.ws.info.common.ProjectGuaranteeDetailInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.common.ProjectLgLitigationDetailInfo;
import com.born.fcs.pm.ws.info.common.ProjectRelatedUserInfo;
import com.born.fcs.pm.ws.info.common.ProjectSimpleDetailInfo;
import com.born.fcs.pm.ws.info.common.ProjectUnderwritingDetailInfo;
import com.born.fcs.pm.ws.info.council.CouncilProjectVoteResultInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectBondInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectEntrustedInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectGuaranteeInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectLgLitigationInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectUnderwritingInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationCreditSchemeInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationLitigationInfo;
import com.born.fcs.pm.ws.info.finvestigation.FInvestigationUnderwritingInfo;
import com.born.fcs.pm.ws.info.projectissueinformation.ProjectIssueInformationInfo;
import com.born.fcs.pm.ws.order.common.ChangeProjectStatusOrder;
import com.born.fcs.pm.ws.order.common.FormRelatedUserQueryOrder;
import com.born.fcs.pm.ws.order.common.MessageOrder;
import com.born.fcs.pm.ws.order.common.ProjectApprovalQueryOrder;
import com.born.fcs.pm.ws.order.common.ProjectOrder;
import com.born.fcs.pm.ws.order.common.ProjectQueryOrder;
import com.born.fcs.pm.ws.order.common.ProjectRelatedUserOrder;
import com.born.fcs.pm.ws.order.common.SendMailOrder;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.order.common.TransferProjectOrder;
import com.born.fcs.pm.ws.order.council.CouncilVoteProjectQueryOrder;
import com.born.fcs.pm.ws.order.fund.LoanUseApplyFormInfo;
import com.born.fcs.pm.ws.order.fund.LoanUseApplyQueryOrder;
import com.born.fcs.pm.ws.order.project.UpdateProjectBaseOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.common.ProjectBatchResult;
import com.born.fcs.pm.ws.result.common.ProjectCandoResult;
import com.born.fcs.pm.ws.result.common.ProjectResult;
import com.born.fcs.pm.ws.service.common.FormRelatedUserService;
import com.born.fcs.pm.ws.service.common.FormService;
import com.born.fcs.pm.ws.service.common.ProjectRelatedUserService;
import com.born.fcs.pm.ws.service.common.ProjectService;
import com.born.fcs.pm.ws.service.council.CouncilProjectService;
import com.born.fcs.pm.ws.service.council.CouncilSummaryService;
import com.born.fcs.pm.ws.service.expireproject.ExpireProjectService;
import com.born.fcs.pm.ws.service.fund.LoanUseApplyService;
import com.born.fcs.pm.ws.service.investigation.InvestigationService;
import com.born.fcs.pm.ws.service.projectissueinformation.ProjectIssueInformationService;
import com.born.fcs.pm.ws.service.sms.SMSService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("projectService")
public class ProjectServiceImpl extends BaseAutowiredDomainService implements ProjectService {
	
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
				order.getRecoverySelecterId(), order.getRelatedRoleList());
			PageComponent component = new PageComponent(order, totalCount);
			
			List<ProjectDO> list = projectDAO.findByCondition(projectDO,
				order.getProjectCodeOrName(), order.getLoginUserId(), hasLoanAmount, hasUseAmount,
				hasBothAmount, hasChargeAmount, hasIssueAmount, hasCompensatoryAmount,
				dockFormType, isReleasing, busiTypeList, order.getDeptIdList(), phases,
				phasesStatus, status, order.getApprovalTimeStart(), order.getApprovalTimeEnd(),
				hasContract, order.getContractTimeStart(), order.getContractTimeEnd(),
				order.getRecoverySelecterId(), order.getRelatedRoleList(), order.getSortCol(),
				order.getSortOrder(), component.getFirstRecord(), component.getPageSize());
			
			List<ProjectInfo> pageList = new ArrayList<ProjectInfo>(list.size());
			for (ProjectDO item : list) {
				pageList.add(DoConvert.convertProjectDO2Info(item));
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
				order.getRecoverySelecterId(), order.getRelatedRoleList());
			PageComponent component = new PageComponent(order, totalCount);
			
			List<ProjectDO> list = projectDAO.findByCondition(projectDO,
				order.getProjectCodeOrName(), order.getLoginUserId(), hasLoanAmount, hasUseAmount,
				hasBothAmount, hasChargeAmount, hasIssueAmount, hasCompensatoryAmount,
				dockFormType, isReleasing, busiTypeList, order.getDeptIdList(), phases,
				phasesStatus, status, order.getApprovalTimeStart(), order.getApprovalTimeEnd(),
				hasContract, order.getContractTimeStart(), order.getContractTimeEnd(),
				order.getRecoverySelecterId(), order.getRelatedRoleList(), order.getSortCol(),
				order.getSortOrder(), component.getFirstRecord(), component.getPageSize());
			
			List<ProjectSimpleDetailInfo> pageList = new ArrayList<ProjectSimpleDetailInfo>(
				list.size());
			
			for (ProjectDO item : list) {
				ProjectInfo project = DoConvert.convertProjectDO2Info(item);
				pageList.add(getSimpleDetailInfo(project));
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
				info.setOneVoteDown(BooleanEnum.getByCode(item.getOneVoteDown()));
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
		if (queryDetail && project != null) {
			//根据业务类型查询会议纪要中信息
			if (ProjectUtil.isBond(project.getBusiType())) { //发债
				FCouncilSummaryProjectBondInfo detailInfo = councilSummaryService
					.queryBondProjectCsByProjectCode(projectCode, false);
				if (detailInfo != null) {
					ProjectBondDetailInfo bondDetailInfo = new ProjectBondDetailInfo();
					BeanCopier.staticCopy(project, bondDetailInfo);
					BeanCopier.staticCopy(detailInfo, bondDetailInfo);
					project.setBondDetailInfo(bondDetailInfo);
				}
			} else if (ProjectUtil.isEntrusted(project.getBusiType())) { //委贷
				FCouncilSummaryProjectEntrustedInfo detailInfo = councilSummaryService
					.queryEntrustedProjectCsByProjectCode(projectCode, false);
				if (detailInfo != null) {
					ProjectEntrustedDetailInfo entrustedDetailInfo = new ProjectEntrustedDetailInfo();
					BeanCopier.staticCopy(project, entrustedDetailInfo);
					BeanCopier.staticCopy(detailInfo, entrustedDetailInfo);
					project.setEntrustedDetailInfo(entrustedDetailInfo);
				}
			} else if (ProjectUtil.isUnderwriting(project.getBusiType())) { //承销
				FCouncilSummaryProjectUnderwritingInfo detailInfo = councilSummaryService
					.queryUnderwritingProjectCsByProjectCode(projectCode, false);
				if (detailInfo != null) {
					ProjectUnderwritingDetailInfo underwritingDetailInfo = new ProjectUnderwritingDetailInfo();
					BeanCopier.staticCopy(project, underwritingDetailInfo);
					BeanCopier.staticCopy(detailInfo, underwritingDetailInfo);
					project.setUnderwritingDetailInfo(underwritingDetailInfo);
				}
			} else if (ProjectUtil.isLitigation(project.getBusiType())) { //诉讼担保
				FCouncilSummaryProjectLgLitigationInfo detailInfo = councilSummaryService
					.queryLgLitigationProjectCsByProjectCode(projectCode, false);
				if (detailInfo != null) {
					ProjectLgLitigationDetailInfo lgLitigationDetailInfo = new ProjectLgLitigationDetailInfo();
					BeanCopier.staticCopy(project, lgLitigationDetailInfo);
					BeanCopier.staticCopy(detailInfo, lgLitigationDetailInfo);
					project.setLgLitigationDetailInfo(lgLitigationDetailInfo);
				}
			} else { //担保
				FCouncilSummaryProjectGuaranteeInfo detailInfo = councilSummaryService
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
					FCouncilSummaryProjectBondInfo detailInfo = councilSummaryService
						.queryBondProjectCsByProjectCode(projectCode, false);
					
					//未通过、一票否决、批复作废取尽职调查里面的数据
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
					FCouncilSummaryProjectEntrustedInfo detailInfo = councilSummaryService
						.queryEntrustedProjectCsByProjectCode(projectCode, false);
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
					FCouncilSummaryProjectUnderwritingInfo detailInfo = councilSummaryService
						.queryUnderwritingProjectCsByProjectCode(projectCode, false);
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
					FCouncilSummaryProjectLgLitigationInfo detailInfo = councilSummaryService
						.queryLgLitigationProjectCsByProjectCode(projectCode, false);
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
					FCouncilSummaryProjectGuaranteeInfo detailInfo = councilSummaryService
						.queryGuaranteeProjectCsByProjectCode(projectCode, false);
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
	public FcsBaseResult transferProject(final TransferProjectOrder order) {
		return commonProcess(order, "项目移交", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				Date now = FcsPmDomainHolder.get().getSysDate();
				
				String projectCode = order.getProjectCode();
				ProjectDO project = projectDAO.findByProjectCode(projectCode);
				if (project == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.DO_ACTION_STATUS_ERROR,
						"项目不存在");
				}
				
				SimpleUserInfo user = null;
				if (order.getUserType() == ProjectRelatedUserTypeEnum.BUSI_MANAGER) {
					ProjectRelatedUserInfo relatedUser = projectRelatedUserService
						.getBusiManager(projectCode);
					if (relatedUser != null)
						user = relatedUser.toSimpleUserInfo();
				} else {
					ProjectRelatedUserInfo relatedUser = projectRelatedUserService
						.getRiskManager(projectCode);
					if (relatedUser != null)
						user = relatedUser.toSimpleUserInfo();
				}
				
				if (user == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.DO_ACTION_STATUS_ERROR,
						order.getUserType().message() + "未找到");
				}
				
				if (user.getUserId() == order.getUserId()) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.DO_ACTION_STATUS_ERROR,
						"当前" + order.getUserType().message() + "未改变");
				}
				
				FormRelatedUserQueryOrder qOrder = new FormRelatedUserQueryOrder();
				qOrder.setProjectCode(order.getProjectCode());
				qOrder.setExeStatus(ExeStatusEnum.WAITING);
				qOrder.setUserId(user.getUserId());
				//查询正在执行的任务
				QueryBaseBatchResult<FormRelatedUserInfo> exeTaskUsers = formRelatedUserService
					.queryPage(qOrder);
				
				//等待人审核的表单
				String waitAuditForm = "";
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
					for (Long formId : exeForm.keySet()) {
						FormDO form = formDAO.findByFormId(formId);
						if (form != null) {
							waitAuditForm += "[ " + form.getFormName() + " ] * "
												+ exeForm.get(formId) + "，";
						}
					}
					if (StringUtil.isNotEmpty(waitAuditForm)) {
						waitAuditForm = "等待当前" + order.getUserType().message() + "处理的表单："
										+ waitAuditForm.substring(0, waitAuditForm.length() - 1);
					}
					
				}
				
				//查询提交的任务(正在审核中)
				qOrder.setExeStatus(ExeStatusEnum.IN_PROGRESS);
				qOrder.setUserType(FormRelatedUserTypeEnum.FLOW_SUBMIT_MAN);
				exeTaskUsers = formRelatedUserService.queryPage(qOrder);
				String submitedForm = "";
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
					for (Long formId : exeForm.keySet()) {
						FormDO form = formDAO.findByFormId(formId);
						if (form != null) {
							submitedForm += "[ " + form.getFormName() + " ] * "
											+ exeForm.get(formId) + "，";
						}
					}
					if (StringUtil.isNotEmpty(submitedForm)) {
						submitedForm = "当前" + order.getUserType().message() + "提交审核中的表单："
										+ submitedForm.substring(0, submitedForm.length() - 1);
					}
				}
				
				//有提交审核中的表单、有待处理的表单
				if (StringUtil.isNotBlank(waitAuditForm) || StringUtil.isNotBlank(submitedForm)) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.DO_ACTION_STATUS_ERROR,
						waitAuditForm + "  " + submitedForm);
				}
				
				//移交项目
				ProjectRelatedUserOrder relatedUser = new ProjectRelatedUserOrder();
				relatedUser.setUserId(order.getAcceptUserId());
				relatedUser.setUserName(order.getAcceptUserName());
				relatedUser.setUserAccount(order.getAcceptUserAccount());
				relatedUser.setDelOrinigal(true);
				relatedUser.setProjectCode(projectCode);
				relatedUser.setTransferTime(now);
				relatedUser.setUserType(order.getUserType());
				FcsBaseResult result = projectRelatedUserService.setRelatedUser(relatedUser);
				
				if (!result.isSuccess()) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.DO_ACTION_STATUS_ERROR,
						"设置新的" + order.getUserType().message() + "出错");
				}
				
				//修改项目业务经理
				if (order.getUserType() == ProjectRelatedUserTypeEnum.BUSI_MANAGER) {
					
					project.setBusiManagerId(order.getAcceptUserId());
					project.setBusiManagerName(order.getAcceptUserName());
					project.setBusiManagerAccount(order.getAcceptUserAccount());
					
					//设置到新的部门
					setProjectNewDept(project);
					
					//更新项目信息
					projectDAO.update(project);
				}
				return null;
			}
		}, null, null);
	}
	
	/**
	 * 项目移交后设置新的部门
	 * @param project
	 */
	private void setProjectNewDept(ProjectDO project) {
		try {
			logger.info("查询用户部门信息：{}", project.getBusiManagerAccount());
			UserDetailsServiceProxy serviceProxy = new UserDetailsServiceProxy(
				propertyConfigService.getBmpServiceUserDetailsService());
			SysOrg org = serviceProxy.loadPrimaryOrgByUsername(project.getBusiManagerAccount());
			if (org != null) {
				project.setDeptId(org.getOrgId());
				project.setDeptCode(org.getCode());
				project.setDeptName(org.getOrgName());
				project.setDeptPath(org.getPath());
				project.setDeptPathName(org.getOrgPathname());
			}
			logger.info("查询用户部门信息完成  {} ： {}", project.getBusiManagerAccount(), org);
		} catch (Exception e) {
			logger.error("查询用户部门信息出错 : {}", e);
		}
	}
	
	@Override
	public ProjectBatchResult getMainCountMessage() {
		ProjectBatchResult result = new ProjectBatchResult();
		List<ProjectDO> projectDOs = projectDAO.findCountGroupByPhases();
		List<ProjectInfo> projectInfos = new ArrayList<ProjectInfo>();
		if (projectDOs != null) {
			for (ProjectDO infoDO : projectDOs) {
				if (infoDO == null || StringUtil.isBlank(infoDO.getPhases())) {
					continue;
				}
				ProjectPhasesEnum phasesEnum = ProjectPhasesEnum.getByCode(infoDO.getPhases());
				if (phasesEnum == null) {
					continue;
				}
				ProjectInfo info = new ProjectInfo();
				info.setPhases(phasesEnum);
				info.setCount(infoDO.getTimeLimit());
				projectInfos.add(info);
			}
		}
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
			result.setSuccess(true);
			result.setProject(project);
			
			//项目已暂缓
			if (project.getStatus() == ProjectStatusEnum.PAUSE) {
				result.setMessage("该项目已暂缓");
				//				result.setCrediInfo(false);
				//				result.setFileInput(false);
				//				result.setPaper(false);
				//				result.setLetter(false);
				return result;
			} else if (project.getPhases() == ProjectPhasesEnum.FINISH_PHASES
						|| project.getStatus() == ProjectStatusEnum.FINISH) { //项目已完成
				result.setMessage("该项目已完成");
				result.forbiddenAll();
				return result;
			}
			
			//除了承销以外，风险上会、风险处理长亮
			if (!ProjectUtil.isUnderwriting(busiType)) {
				result.setRiskHandle(true);
				result.setRiskReport(true);
			}
			
			//立项阶段
			if (phase == ProjectPhasesEnum.SET_UP_PHASES) {
				result.setSetup(true);
				result.setMessage("当前项目正在立项");
				return result;
			}
			
			//尽职调查阶段
			if (phase == ProjectPhasesEnum.INVESTIGATING_PHASES) {
				result.setInvestigation(true);
				result.setMessage("当前项目进入到尽调阶段，可做的操作：尽职调查报告");
				//查看是否复议
				if (project.getLastRecouncilTime() != null) {
					result.setMessage("当前项目进入到尽调阶段 [复]，可做的操作：编辑尽职调查报告重新申请上会");
					return result;
				}
				//查看是否被退回
				boolean hasBack = false;
				CouncilVoteProjectQueryOrder voteProjectQueryOrder = new CouncilVoteProjectQueryOrder();
				voteProjectQueryOrder.setProjectCode(projectCode);
				QueryBaseBatchResult<CouncilProjectVoteResultInfo> voteResult = councilProjectService
					.queryProjectVoteResult(voteProjectQueryOrder);
				if (voteResult.isSuccess()) {
					for (CouncilProjectVoteResultInfo vote : voteResult.getPageList()) {
						if (vote.getCompereMessage() == ProjectCouncilCompereMessageEnum.INVESTIGATING_PHASES) {
							hasBack = true;
						}
					}
				}
				if (hasBack) {
					result.setMessage("当前项目会议评审被退回，可做操作：编辑尽职调查报告重新申请上会");
				}
				return result;
			}
			
			//评审阶段
			else if (phase == ProjectPhasesEnum.COUNCIL_PHASES) {
				result.setCouncil(true);
				result.setMessage("当前项目进入到评审阶段，需要根据评审的结果进行下一步操作");
				//查询评审结果
				if (project.getApprovalTime() != null && project.getSpId() > 0) {//有评审结果了
					FCouncilSummaryProjectInfo summary = councilSummaryService
						.queryProjectCsBySpId(project.getSpId());
					if (summary.getVoteResult() == ProjectVoteResultEnum.END_NOPASS
						|| summary.getOneVoteDown() == BooleanEnum.YES) {
						if (project.getLastRecouncilTime() == null) {
							result.setMessage("当前项目会议评审未通过，可通过项目列表的[ 复议 ]按钮对该项目发起复议");
						} else if (project.getApprovalTime().after(project.getLastRecouncilTime())) {//通过时间在复议时间之后结果才对
							result.setMessage("当前项目复议未通过");
							result.forbiddenAll();
						}
					} else {
						result.setMessage("当前项目会议评审已通过，可做的操作：合同申请");
					}
				}
				return result;
			}
			
			//合同阶段
			else if (phase == ProjectPhasesEnum.CONTRACT_PHASES) {
				result.setContract(true);
				result.setMessage("当前项目会议评审已通过，可做的操作：合同申请");
				if (isMainContractSign) { //主合同回传
					//担保委贷回传后阶段还在合同阶段、承销发债回传后进入资金募集阶段、诉保直接进入保后阶段
					if (ProjectUtil.isGuarantee(busiType) || ProjectUtil.isEntrusted(busiType)) {
						result.setCreditConfirm(true);
						result.setChargeNotice(true);
						result.setLoanUse(true);
						result.setMessage("该项目的主合同已回传，可做操作：落实授信条件、费用收取通知、放用款申请");
					}
				}
				return result;
				
			} else {
				
				if (ProjectUtil.isGuarantee(busiType) || ProjectUtil.isEntrusted(busiType)) {//担保委贷
				
					if (isMainContractSign) {
						result.setLoanUse(true);
						result.setContract(true);
						result.setCreditConfirm(true);
						result.setChargeNotice(true);
						result.setMessage("该项目的主合同已回传，可做操作：落实授信条件、费用收取通知、放用款申请");
					}
					
					if (project.getLoanedAmount().greaterThan(ZERO_MONEY)) { //有回执
						result.setContract(true);
						result.setCreditConfirm(true);
						result.setChargeNotice(true);
						result.setLoanUse(true);
						result.setRiskLevel(true);
						result.setAfterCheck(true);
						result.setRelease(true);
						result
							.setMessage("当前项目有已完成的放用款，可做的操作：落实授信条件、费用收取通知、放用款申请、保后检查报告、项目风险等级评定、解保申请");
					}
				} else if (ProjectUtil.isUnderwriting(busiType)) { //承销
				
					if (isMainContractSign) { //主合同回传
						result.setContract(true);
						result.setSellConfirm(true);
						result.setChargeNotice(true);
						result.setMessage("该项目的主合同已回传，可做操作：合同申请、发售信息维护、费用收取通知");
						
						//发售完成
						List<ProjectIssueInformationInfo> sellInfos = projectIssueInformationService
							.findProjectIssueInformationByProjectCode(projectCode);
						if (ListUtil.isNotEmpty(sellInfos)) {
							ProjectIssueInformationInfo sellInfo = sellInfos.get(0);
							if (sellInfo.getStatus() == SellStatusEnum.SELL_FINISH) {
								result.setSellConfirm(false);
								result.setMessage("该项目发售已完成，可做操作：合同申请、费用收取通知");
							}
						}
						//到期
						if (StringUtil.equals(project.getProjectOverTime(), BooleanEnum.YES.code())) {
							result.setExpireNotice(true);
							result.setMessage("该项目即将到期，可做操作：到期通知");
						}
					}
				} else if (ProjectUtil.isLitigation(busiType)) { //诉保
					if (isMainContractSign) {
						result.setContract(true);
						result.setChargeNotice(true);
						result.setAfterCheck(true);
						result.setRelease(true);
						result.setMessage("当前项目主合同已回传，可做操作：合同申请、费用收取通知、保后检查报告、解保申请");
					}
				} else if (ProjectUtil.isBond(busiType)) { //发债
				
					if (isMainContractSign) { //合同回传
						result.setContract(true);
						result.setCreditConfirm(true);
						result.setChargeNotice(true);
						result.setSellConfirm(true);
						result.setMessage("该项目的主合同已回传，可做操作：落实授信条件、费用收取通知、发售信息维护");
						
						//募集到资金后
						if (project.getAccumulatedIssueAmount().greaterThan(ZERO_MONEY)) {
							result.setLoanUse(true);
							result.setMessage("该项目已有募集到的资金，可做操作：放用款申请");
						}
						
						//有回执后
						if (project.getLoanedAmount().greaterThan(ZERO_MONEY)) {
							result.setAfterCheck(true);
							result.setRiskLevel(true);
							result.setRelease(true);
							result
								.setMessage("当前项目有已完成的放用款，可做的操作：落实授信条件、费用收取通知、发售信息维护、放用款申请、保后检查报告、项目风险等级评定、解保申请");
						}
						
						//发售完成
						List<ProjectIssueInformationInfo> sellInfos = projectIssueInformationService
							.findProjectIssueInformationByProjectCode(projectCode);
						if (ListUtil.isNotEmpty(sellInfos)) {
							ProjectIssueInformationInfo sellInfo = sellInfos.get(0);
							if (sellInfo.getStatus() == SellStatusEnum.SELL_FINISH) {
								result.setSellConfirm(false);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("获取项目可操作项出错");
			logger.error("获取项目可操作项出错", e);
		}
		return result;
	}
}
