/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.biz.service.base;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.born.fcs.pm.dal.daointerface.*;
import com.born.fcs.pm.daointerface.BusiDAO;
import com.born.fcs.pm.daointerface.ExtraDAO;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

/**
 * 
 * @Filename BaseAutowiredDAOService
 * 
 * @Description
 * 
 * @Version 1.0
 * 
 * @Author qichunhai
 * 
 * @Email qchunhai@yiji.com
 * 
 * @History <li>Author: qichunhai</li> <li>Date: 2014-4-8</li> <li>Version: 1.0</li>
 * <li>Content: create</li>
 * 
 */
public class BaseAutowiredDAOService {
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	protected ExtraDAO extraDAO;
	
	@Autowired
	protected BusiDAO busiDAO;
	
	@Autowired
	protected FormDAO formDAO;
	
	@Autowired
	protected ProjectDAO projectDAO;
	
	@Autowired
	protected FinancialProductDAO financialProductDAO;
	
	@Autowired
	protected BusiTypeDAO busiTypeDAO;
	
	// 项目立项 start
	
	@Autowired
	protected FProjectDAO FProjectDAO;
	@Autowired
	protected FProjectGuaranteeEntrustedDAO FProjectGuaranteeEntrustedDAO;
	@Autowired
	protected FProjectUnderwritingDAO FProjectUnderwritingDAO;
	@Autowired
	protected FProjectLgLitigationDAO FProjectLgLitigationDAO;
	@Autowired
	protected FProjectFinancialDAO FProjectFinancialDAO;
	@Autowired
	protected ProjectFinancialTradeTansferDAO projectFinancialTradeTansferDAO;
	@Autowired
	protected ProjectFinancialTradeRedeemDAO projectFinancialTradeRedeemDAO;
	@Autowired
	protected ProjectFinancialWithdrawingDAO projectFinancialWithdrawingDAO;
	@Autowired
	protected ProjectFinancialDAO projectFinancialDAO;
	@Autowired
	protected FProjectFinancialRedeemApplyDAO FProjectFinancialRedeemApplyDAO;
	@Autowired
	protected FProjectFinancialTansferApplyDAO FProjectFinancialTansferApplyDAO;
	
	@Autowired
	protected FProjectExternalGuaranteeDAO FProjectExternalGuaranteeDAO;
	
	@Autowired
	protected FProjectEquityStructureDAO FProjectEquityStructureDAO;
	
	@Autowired
	protected FProjectCustomerBaseInfoDAO FProjectCustomerBaseInfoDAO;
	
	@Autowired
	protected FProjectCounterGuaranteePledgeDAO FProjectCounterGuaranteePledgeDAO;
	
	@Autowired
	protected FProjectCounterGuaranteeGuarantorDAO FProjectCounterGuaranteeGuarantorDAO;
	
	@Autowired
	protected FProjectBankLoanDAO FProjectBankLoanDAO;
	// 项目立项 end
	
	// 风险审查报告 begin
	@Autowired
	protected FRiskReviewReportDAO FRiskReviewReportDAO;
	
	// 风险审查报告end
	
	// 上会 begin
	
	@Autowired
	protected CouncilApplyDAO councilApplyDAO;
	
	@Autowired
	protected CouncilDAO councilDAO;
	
	@Autowired
	protected CouncilJudgeDAO councilJudgeDAO;
	
	@Autowired
	protected CouncilParticipantDAO councilParticipantDAO;
	
	@Autowired
	protected CouncilProjectDAO councilProjectDAO;
	
	@Autowired
	protected CouncilProjectVoteDAO councilProjectVoteDAO;
	
	@Autowired
	protected CouncilTypeDAO councilTypeDAO;
	// 上会end
	
	// 会议纪要
	@Autowired
	protected FCouncilSummaryDAO FCouncilSummaryDAO;
	
	@Autowired
	protected FCouncilSummaryProjectBondDAO FCouncilSummaryProjectBondDAO;
	
	@Autowired
	protected FCouncilSummaryProjectChargeWayDAO FCouncilSummaryProjectChargeWayDAO;
	
	@Autowired
	protected FCouncilSummaryProjectDAO FCouncilSummaryProjectDAO;
	
	@Autowired
	protected FCouncilSummaryProjectGuaranteeDAO FCouncilSummaryProjectGuaranteeDAO;
	
	@Autowired
	protected FCouncilSummaryProjectEntrustedDAO FCouncilSummaryProjectEntrustedDAO;
	
	@Autowired
	protected FCouncilSummaryProjectGuarantorDAO FCouncilSummaryProjectGuarantorDAO;
	
	@Autowired
	protected FCouncilSummaryProjectProcessControlDAO FCouncilSummaryProjectProcessControlDAO;
	
	@Autowired
	protected FCouncilSummaryProjectLgLitigationDAO FCouncilSummaryProjectLgLitigationDAO;
	
	@Autowired
	protected FCouncilSummaryProjectPledgeDAO FCouncilSummaryProjectPledgeDAO;
	@Autowired
	protected FCouncilSummaryProjectPledgeAssetDAO FCouncilSummaryProjectPledgeAssetDAO;
	
	@Autowired
	protected FCouncilSummaryProjectRepayWayDAO FCouncilSummaryProjectRepayWayDAO;
	
	@Autowired
	protected FCouncilSummaryProjectUnderwritingDAO FCouncilSummaryProjectUnderwritingDAO;
	
	@Autowired
	protected FCouncilSummaryRiskHandleDAO FCouncilSummaryRiskHandleDAO;
	// 会议纪要end
	
	// 评估公司
	@Autowired
	protected AssessCompanyDAO assessCompanyDAO;
	
	// 决策机构维护 begin
	@Autowired
	protected DecisionInstitutionDAO decisionInstitutionDAO;
	@Autowired
	protected DecisionMemberDAO decisionMemberDAO;
	// 决策机构维护 end
	
	// 调查报告 begin
	@Autowired
	protected FInvestigationDAO FInvestigationDAO;
	@Autowired
	protected FInvestigationPersonDAO FInvestigationPersonDAO;
	@Autowired
	protected FInvestigationCreditSchemeDAO FInvestigationCreditSchemeDAO;
	@Autowired
	protected FInvestigationCreditSchemePledgeDAO FInvestigationCreditSchemePledgeDAO;
	@Autowired
	protected FInvestigationCreditSchemePledgeAssetDAO FInvestigationCreditSchemePledgeAssetDAO;
	@Autowired
	protected FInvestigationCreditSchemeGuarantorDAO FInvestigationCreditSchemeGuarantorDAO;
	@Autowired
	protected FInvestigationMainlyReviewDAO FInvestigationMainlyReviewDAO;
	@Autowired
	protected FInvestigationMainlyReviewCertificateDAO FInvestigationMainlyReviewCertificateDAO;
	@Autowired
	protected FInvestigationMainlyReviewBankInfoDAO FInvestigationMainlyReviewBankInfoDAO;
	@Autowired
	protected FInvestigationMainlyReviewStockholderDAO FInvestigationMainlyReviewStockholderDAO;
	@Autowired
	protected FInvestigationMainlyReviewAssetAndLiabilityDAO FInvestigationMainlyReviewAssetAndLiabilityDAO;
	@Autowired
	protected FInvestigationMainlyReviewRelatedCompanyDAO FInvestigationMainlyReviewRelatedCompanyDAO;
	@Autowired
	protected FInvestigationMainlyReviewCreditStatusDAO FInvestigationMainlyReviewCreditStatusDAO;
	@Autowired
	protected FInvestigationMainlyReviewExternalGuaranteeDAO FInvestigationMainlyReviewExternalGuaranteeDAO;
	@Autowired
	protected FInvestigationMainlyReviewCreditInfoDAO FInvestigationMainlyReviewCreditInfoDAO;
	@Autowired
	protected FInvestigationMabilityReviewDAO FInvestigationMabilityReviewDAO;
	@Autowired
	protected FInvestigationMabilityReviewLeadingTeamDAO FInvestigationMabilityReviewLeadingTeamDAO;
	@Autowired
	protected FInvestigationMabilityReviewLeadingTeamResumeDAO FInvestigationMabilityReviewLeadingTeamResumeDAO;
	@Autowired
	protected FInvestigationOpabilityReviewDAO FInvestigationOpabilityReviewDAO;
	@Autowired
	protected FInvestigationOpabilityReviewUpdownStreamDAO FInvestigationOpabilityReviewUpdownStreamDAO;
	@Autowired
	protected FInvestigationOpabilityReviewProductStructureDAO FInvestigationOpabilityReviewProductStructureDAO;
	@Autowired
	protected FInvestigationMajorEventDAO FInvestigationMajorEventDAO;
	@Autowired
	protected FInvestigationProjectStatusDAO FInvestigationProjectStatusDAO;
	@Autowired
	protected FInvestigationProjectStatusFundDAO FInvestigationProjectStatusFundDAO;
	@Autowired
	protected FInvestigationRiskDAO FInvestigationRiskDAO;
	@Autowired
	protected FInvestigationCsRationalityReviewDAO FInvestigationCsRationalityReviewDAO;
	@Autowired
	protected FInvestigationCsRationalityReviewFinancialKpiDAO FInvestigationCsRationalityReviewFinancialKpiDAO;
	@Autowired
	protected FInvestigationCsRationalityReviewGuarantorAbilityDAO FInvestigationCsRationalityReviewGuarantorAbilityDAO;
	@Autowired
	protected FInvestigationFinancialReviewDAO FInvestigationFinancialReviewDAO;
	@Autowired
	protected FInvestigationFinancialReviewKpiDAO FInvestigationFinancialReviewKpiDAO;
	@Autowired
	protected FInvestigationCreditSchemeProcessControlDAO FInvestigationCreditSchemeProcessControlDAO;
	@Autowired
	protected FInvestigationLitigationDAO FInvestigationLitigationDAO;
	@Autowired
	protected FInvestigationUnderwritingDAO FInvestigationUnderwritingDAO;
	@Autowired
	protected FInvestigationAssetReviewDAO FInvestigationAssetReviewDAO;
	// 调查报告 end
	// 征信管理
	@Autowired
	protected FCreditRefrerenceApplyDAO fCreditRefrerenceApplyDAO;
	
	// 承销/发债类项目发行信息
	@Autowired
	protected ProjectIssueInformationDAO projectIssueInformationDAO;
	
	// 项目授信条件 start
	@Autowired
	protected ProjectCreditConditionDAO projectCreditConditionDAO;
	
	@Autowired
	protected FCreditConditionConfirmItemDAO FCreditConditionConfirmItemDAO;
	
	@Autowired
	protected FCreditConditionConfirmDAO FCreditConditionConfirmDAO;
	@Autowired
	protected ProjectCreditMarginDAO projectCreditMarginDAO;
	// 项目授信条件 end
	// 放用款申请 start
	@Autowired
	protected FLoanUseApplyDAO FLoanUseApplyDAO;
	
	@Autowired
	protected FLoanUseApplyFeeDAO FLoanUseApplyFeeDAO;
	
	@Autowired
	protected FLoanUseApplyReceiptDAO FLoanUseApplyReceiptDAO;
	
	// 放用款申请 end
	
	@Autowired
	protected FChargeNotificationDAO fChargeNotificationDAO;
	
	@Autowired
	protected FChargeNotificationFeeDAO fChargeNotificationFeeDAO;
	
	// 用印管理
	@Autowired
	protected FStampApplyDAO fStampApplyDAO;
	@Autowired
	protected FStampApplyFileDAO fStampApplyFileDAO;
	
	@Autowired
	protected FAfterwardsCheckCollectionDAO FAfterwardsCheckCollectionDAO;
	@Autowired
	protected FAfterwardsCheckDAO FAfterwardsCheckDAO;
	
	@Autowired
	protected FAfterwardsCheckLoanDAO FAfterwardsCheckLoanDAO;
	//保后项目汇总
	@Autowired
	protected AfterwardsProjectSummaryDAO afterwardsProjectSummaryDAO;
	
	// 过期逾期项目
	@Autowired
	protected ExpireProjectDAO expireProjectDAO;
	@Autowired
	protected ExpireProjectNoticeTemplateDAO expireProjectNoticeTemplateDAO;
	@Autowired
	protected MessageAlertDAO messageAlertDAO;
	
	// 风险预警项目
	@Autowired
	protected FRiskWarningDAO FRiskWarningDAO;
	@Autowired
	protected FRiskWarningCreditDAO FRiskWarningCreditDAO;
	// 风险预警信号
	@Autowired
	protected RiskWarningSignalDAO riskWarningSignalDAO;
	
	// 上会申报
	@Autowired
	protected FCouncilApplyRiskHandleDAO FCouncilApplyRiskHandleDAO;
	@Autowired
	protected FCouncilApplyCreditDAO FCouncilApplyCreditDAO;
	
	@Autowired
	protected FCouncilApplyCreditCompensationDAO fCouncilApplyCreditCompensationDAO;
	
	// 合同模板管理
	@Autowired
	protected ContractTemplateDAO contractTemplateDAO;
	
	// 项目合同集
	@Autowired
	protected FProjectContractDAO fProjectContractDAO;
	@Autowired
	protected FProjectContractItemDAO fProjectContractItemDAO;
	
	// 附件上传相关
	@Autowired
	protected CommonAttachmentDAO commonAttachmentDAO;
	
	@Autowired
	protected RelatedUserDAO relatedUserDAO;
	
	@Autowired
	protected ProjectRelatedUserDAO projectRelatedUserDAO;
	
	@Autowired
	protected FormRelatedUserDAO formRelatedUserDAO;
	
	@Autowired
	protected DbTableDAO dbTableDAO;
	
	@Autowired
	protected DbFieldDAO dbFieldDAO;
	
	@Autowired
	protected FFileDAO fileDAO;
	
	@Autowired
	protected FFileListDAO fileListDAO;
	
	@Autowired
	protected FFileInputDAO fileInputDAO;
	
	@Autowired
	protected FFileOutputDAO fileOutputDAO;
	
	@Autowired
	protected FFileBorrowDAO fileBorrowDAO;
	
	@Autowired
	protected FFileInputListDAO fileInputListDAO;
	
	@Autowired
	protected FFileListStatusDAO fileListStatusDAO;
	
	@Autowired
	protected FCounterGuaranteeReleaseDAO FCounterGuaranteeReleaseDAO;
	
	@Autowired
	protected FRiskLevelDAO FRiskLevelDAO;
	@Autowired
	protected FRiskLevelDetailDAO FRiskLevelDetailDAO;
	@Autowired
	protected FRiskLevelScoreTemplateDAO FRiskLevelScoreTemplateDAO;
	// 资金划付申请 start
	@Autowired
	protected FCapitalAppropriationApplyDAO FCapitalAppropriationApplyDAO;
	@Autowired
	protected FCapitalAppropriationApplyFeeDAO FCapitalAppropriationApplyFeeDAO;
	@Autowired
	protected FCapitalAppropriationApplyPayeeDAO FCapitalAppropriationApplyPayeeDAO;
	@Autowired
	protected FCapitalAppropriationApplyReceiptDAO FCapitalAppropriationApplyReceiptDAO;
	// 资金划付申请 end
	
	// 合同额外信息维护
	@Autowired
	protected FProjectContractExtraValueDAO fProjectContractExtraValueDAO;
	@Autowired
	protected FProjectContractExtraValueModifyDAO fProjectContractExtraValueModifyDAO;
	
	@Autowired
	protected FProjectContractCheckDAO fProjectContractCheckDAO;
	
	//站内信
	@Autowired
	protected MessageInfoDAO messageInfoDAO;
	@Autowired
	protected MessageReceivedDAO messageReceivedDAO;
	//复议
	@Autowired
	protected FReCouncilApplyDAO FReCouncilApplyDAO;
	
	@Autowired
	protected ReleaseProjectDAO releaseProjectDAO;
	@Autowired
	protected FAfterwardsCheckReportContentDAO FAfterwardsCheckReportContentDAO;
	@Autowired
	protected FAfterwardsCheckReportItemDAO FAfterwardsCheckReportItemDAO;
	@Autowired
	protected FAfterwardsCheckReportFinancialDAO FAfterwardsCheckReportFinancialDAO;
	@Autowired
	protected FAfterwardsCheckReportProjectDAO FAfterwardsCheckReportProjectDAO;
	@Autowired
	protected FAfterwardsCheckReportIncomeDAO FAfterwardsCheckReportIncomeDAO;
	@Autowired
	protected FAfterwardsCheckReportCapitalDAO FAfterwardsCheckReportCapitalDAO;
	@Autowired
	protected FAfterwardsCheckBaseDAO FAfterwardsCheckBaseDAO;
	@Autowired
	protected FAfterwardsCheckCommonDAO FAfterwardsCheckCommonDAO;
	@Autowired
	protected FAfterwardsCheckReportContentExtendDAO FAfterwardsCheckReportContentExtendDAO;
	@Autowired
	protected FAfterwardsCheckLitigationDAO FAfterwardsCheckLitigationDAO;
	
	//发行通知书
	@Autowired
	protected ConsentIssueNoticeDAO consentIssueNoticeDAO;
	
	@Autowired
	protected FormMessageTempleteDAO formMessageTempleteDAO;
	
	// 特殊纸
	@Autowired
	protected SpecialPaperNoDAO specialPaperNoDAO;
	@Autowired
	protected SpecialPaperProvideDeptDAO specialPaperProvideDeptDAO;
	@Autowired
	protected SpecialPaperProvideProjectDAO specialPaperProvideProjectDAO;
	@Autowired
	protected SpecialPaperInvalidDAO specialPaperInvalidDAO;
	// 退费申请 start
	@Autowired
	protected FRefundApplicationDAO FRefundApplicationDAO;
	@Autowired
	protected FRefundApplicationFeeDAO FRefundApplicationFeeDAO;
	// 退费申请 end
	
	@Autowired
	protected FormChangeApplyDAO formChangeApplyDAO;
	@Autowired
	protected FormChangeRecordDAO formChangeRecordDAO;
	
	// 追偿开始
	@Autowired
	protected ProjectRecoveryDAO projectRecoveryDAO;
	@Autowired
	protected ProjectRecoveryNoticeLetterDAO projectRecoveryNoticeLetterDAO;
	@Autowired
	protected ProjectRecoveryDebtorReorganizationAmountDetailDAO projectRecoveryDebtorReorganizationAmountDetailDAO;
	@Autowired
	protected ProjectRecoveryDebtorReorganizationDAO projectRecoveryDebtorReorganizationDAO;
	@Autowired
	protected ProjectRecoveryDebtorReorganizationDebtsCouncilDAO projectRecoveryDebtorReorganizationDebtsCouncilDAO;
	@Autowired
	protected ProjectRecoveryDebtorReorganizationPledgeDAO projectRecoveryDebtorReorganizationPledgeDAO;
	@Autowired
	protected ProjectRecoveryLitigationAdjournedSecondDAO projectRecoveryLitigationAdjournedSecondDAO;
	@Autowired
	protected ProjectRecoveryLitigationAdjournedProcedureDAO projectRecoveryLitigationAdjournedProcedureDAO;
	@Autowired
	protected ProjectRecoveryLitigationBeforePreservationDAO projectRecoveryLitigationBeforePreservationDAO;
	@Autowired
	protected ProjectRecoveryLitigationBeforePreservationPrecautionDAO projectRecoveryLitigationBeforePreservationPrecautionDAO;
	@Autowired
	protected ProjectRecoveryLitigationBeforeTrailDAO projectRecoveryLitigationBeforeTrailDAO;
	@Autowired
	protected ProjectRecoveryLitigationCertificateDAO projectRecoveryLitigationCertificateDAO;
	
	@Autowired
	protected ProjectRecoveryLitigationExecuteDAO projectRecoveryLitigationExecuteDAO;
	@Autowired
	protected ProjectRecoveryLitigationExecuteGyrationDAO projectRecoveryLitigationExecuteGyrationDAO;
	@Autowired
	protected ProjectRecoveryLitigationExecuteStuffDAO projectRecoveryLitigationExecuteStuffDAO;
	@Autowired
	protected ProjectRecoveryLitigationOpeningDAO projectRecoveryLitigationOpeningDAO;
	@Autowired
	protected ProjectRecoveryLitigationPlaceOnFileDAO projectRecoveryLitigationPlaceOnFileDAO;
	
	@Autowired
	protected ProjectRecoveryLitigationPreservationDAO projectRecoveryLitigationPreservationDAO;
	@Autowired
	protected ProjectRecoveryLitigationRefereeDAO projectRecoveryLitigationRefereeDAO;
	@Autowired
	protected ProjectRecoveryLitigationSecondAppealDAO projectRecoveryLitigationSecondAppealDAO;
	@Autowired
	protected ProjectRecoveryLitigationSpecialProcedureDAO projectRecoveryLitigationSpecialProcedureDAO;
	
	// 追偿end 
	
	@Autowired
	protected UserExtraMessageDAO userExtraMessageDAO;
	@Autowired
	protected FormChangeRecordDetailDAO formChangeRecordDetailDAO;
	@Autowired
	protected SysDbBackupConfigDAO sysDbBackupConfigDAO;
	@Autowired
	protected SysDbBackupLogDAO sysDbBackupLogDAO;
	@Autowired
	protected ProjectExtendInfoDAO projectExtendInfoDAO;
	@Autowired
	protected ChargeRepayPlanDAO chargeRepayPlanDAO;
	@Autowired
	protected ChargeRepayPlanDetailDAO chargeRepayPlanDetailDAO;
	@Autowired
	protected FInternalOpinionExchangeDAO FInternalOpinionExchangeDAO;
	@Autowired
	protected FInternalOpinionExchangeUserDAO FInternalOpinionExchangeUserDAO;
	@Autowired
	protected FBrokerBusinessDAO FBrokerBusinessDAO;
	@Autowired
	protected FManagerbChangeApplyDAO FManagerbChangeApplyDAO;
	
	//子系统对接项目信息
	@Autowired
	protected SubsystemDockProjectDAO subsystemDockProjectDAO;
	
	//报表 start
	@Autowired
	protected ReportExpectEventDAO reportExpectEventDAO;
	
	@Autowired
	protected ProjectRiskHandleTeamDAO projectRiskHandleTeamDAO;
	
	@Autowired
	protected CouncilRiskDAO councilRiskDAO;
	
	@Autowired
	protected CouncilRiskSummaryDAO councilRiskSummaryDAO;
	
	@Autowired
	protected ProjectRiskLogDAO projectRiskLogDAO;
	
	@Autowired
	protected ViewChannelProjectAllPhasDAO viewChannelProjectAllPhasDAO;
	
	@Autowired
	protected ProjectRiskReportDAO projectRiskReportDAO;
	
	@Autowired
	protected ProjectRiskReportCompDetailDAO projectRiskReportCompDetailDAO;
	
	@Autowired
	protected ViewProjectIndirectCustomerDAO viewProjectIndirectCustomerDAO;
	
	//报表 end
	
	/**
	 * @return Date
	 */
	protected Date getSysdate() {
		try {
			Date sysDate = extraDAO.getSysdate();
			logger.info("系统时间：sysDate=" + sysDate);
			return sysDate;
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage(), e);
		}
		return new Date();
	}
	
}
