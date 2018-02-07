package com.born.fcs.face.integration.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.born.fcs.am.ws.service.assesscompany.AssessCompanyApplyService;
import com.born.fcs.am.ws.service.assesscompany.AssetsAssessCompanyService;
import com.born.fcs.am.ws.service.pledgeasset.PledgeAssetService;
import com.born.fcs.am.ws.service.pledgeimage.PledgeImageCustomService;
import com.born.fcs.am.ws.service.pledgenetwork.PledgeNetworkCustomService;
import com.born.fcs.am.ws.service.pledgetext.PledgeTextCustomService;
import com.born.fcs.am.ws.service.pledgetype.PledgeTypeService;
import com.born.fcs.am.ws.service.pledgetypeattribute.PledgeTypeAttributeService;
import com.born.fcs.am.ws.service.transfer.AssetsTransferApplicationService;
import com.born.fcs.am.ws.service.transferee.AssetsTransfereeApplicationService;
import com.born.fcs.crm.ws.service.ChannalContractService;
import com.born.fcs.fm.ws.service.innerLoan.FormInnerLoanService;
import com.born.fcs.fm.ws.service.payment.BankCategoryService;
import com.born.fcs.fm.ws.service.payment.BudgetService;
import com.born.fcs.fm.ws.service.payment.CostCategoryService;
import com.born.fcs.fm.ws.service.payment.ExpenseApplicationService;
import com.born.fcs.fm.ws.service.payment.FormTransferService;
import com.born.fcs.fm.ws.service.payment.LabourCapitalService;
import com.born.fcs.fm.ws.service.payment.PaymentApplyService;
import com.born.fcs.fm.ws.service.payment.TravelExpenseService;
import com.born.fcs.fm.ws.service.report.ReportFormAnalysisService;
import com.born.fcs.fm.ws.service.subject.SysSubjectMessageService;
import com.born.fcs.pm.biz.service.common.DateSeqService;
import com.born.fcs.pm.biz.service.common.SiteMessageService;
import com.born.fcs.pm.biz.service.common.SysParameterService;
import com.born.fcs.pm.ws.service.assist.InternalOpinionExchangeService;
import com.born.fcs.pm.ws.service.basicmaintain.AssessCompanyService;
import com.born.fcs.pm.ws.service.basicmaintain.DecisionInstitutionService;
import com.born.fcs.pm.ws.service.basicmaintain.DecisionMemberService;
import com.born.fcs.pm.ws.service.basicmaintain.FinancialProductService;
import com.born.fcs.pm.ws.service.brokerbusiness.BrokerBusinessService;
import com.born.fcs.pm.ws.service.capitalappropriationapply.FCapitalAppropriationApplyService;
import com.born.fcs.pm.ws.service.check.AfterwardsCheckService;
import com.born.fcs.pm.ws.service.common.BasicDataService;
import com.born.fcs.pm.ws.service.common.CommonAttachmentService;
import com.born.fcs.pm.ws.service.common.FormMessageTempleteService;
import com.born.fcs.pm.ws.service.common.FormRelatedUserService;
import com.born.fcs.pm.ws.service.common.FormService;
import com.born.fcs.pm.ws.service.common.ProjectChannelRelationService;
import com.born.fcs.pm.ws.service.common.ProjectExtendService;
import com.born.fcs.pm.ws.service.common.ProjectImportService;
import com.born.fcs.pm.ws.service.common.ProjectRelatedUserService;
import com.born.fcs.pm.ws.service.common.ProjectService;
import com.born.fcs.pm.ws.service.common.SubsystemDockProjectService;
import com.born.fcs.pm.ws.service.common.SysDbBackupService;
import com.born.fcs.pm.ws.service.contract.ContractTemplateService;
import com.born.fcs.pm.ws.service.contract.DbFieldService;
import com.born.fcs.pm.ws.service.contract.DbTableService;
import com.born.fcs.pm.ws.service.contract.FContractTemplateService;
import com.born.fcs.pm.ws.service.contract.ProjectContractExtraValueService;
import com.born.fcs.pm.ws.service.contract.ProjectContractService;
import com.born.fcs.pm.ws.service.council.CouncilApplyRiskHandleService;
import com.born.fcs.pm.ws.service.council.CouncilApplyService;
import com.born.fcs.pm.ws.service.council.CouncilProjectService;
import com.born.fcs.pm.ws.service.council.CouncilProjectVoteService;
import com.born.fcs.pm.ws.service.council.CouncilService;
import com.born.fcs.pm.ws.service.council.CouncilSummaryService;
import com.born.fcs.pm.ws.service.council.CouncilTypeService;
import com.born.fcs.pm.ws.service.council.RecouncilApplyService;
import com.born.fcs.pm.ws.service.councilRisk.CouncilRiskService;
import com.born.fcs.pm.ws.service.counterguarantee.CounterGuaranteeService;
import com.born.fcs.pm.ws.service.creditrefrerenceapply.CreditRefrerenceApplyService;
import com.born.fcs.pm.ws.service.expireproject.ExpireProjectService;
import com.born.fcs.pm.ws.service.file.FileService;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectRedeemService;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectService;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectSetupService;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectTransferService;
import com.born.fcs.pm.ws.service.forCrm.CrmUseService;
import com.born.fcs.pm.ws.service.formchange.FormChangeApplyService;
import com.born.fcs.pm.ws.service.fund.ChargeNotificationFeeService;
import com.born.fcs.pm.ws.service.fund.ChargeNotificationService;
import com.born.fcs.pm.ws.service.fund.ChargeRepayPlanService;
import com.born.fcs.pm.ws.service.fund.LoanUseApplyService;
import com.born.fcs.pm.ws.service.fund.RefundApplicationService;
import com.born.fcs.pm.ws.service.investigation.AssetReviewService;
import com.born.fcs.pm.ws.service.investigation.InvestigationService;
import com.born.fcs.pm.ws.service.notice.ConsentIssueNoticeService;
import com.born.fcs.pm.ws.service.projectRiskLog.ProjectRiskLogService;
import com.born.fcs.pm.ws.service.projectRiskReport.ProjectRiskReportService;
import com.born.fcs.pm.ws.service.projectcreditcondition.ProjectCreditConditionService;
import com.born.fcs.pm.ws.service.projectissueinformation.ProjectBondReinsuranceInformationService;
import com.born.fcs.pm.ws.service.projectissueinformation.ProjectIssueInformationService;
import com.born.fcs.pm.ws.service.recovery.ProjectRecoveryService;
import com.born.fcs.pm.ws.service.riskHandleTeam.RiskHandleTeamService;
import com.born.fcs.pm.ws.service.risklevel.RiskLevelService;
import com.born.fcs.pm.ws.service.riskreview.RiskReviewReportService;
import com.born.fcs.pm.ws.service.riskwarning.RiskWarningService;
import com.born.fcs.pm.ws.service.riskwarning.RiskWarningSignalService;
import com.born.fcs.pm.ws.service.setup.ProjectSetupService;
import com.born.fcs.pm.ws.service.shortmessage.ShortMessageService;
import com.born.fcs.pm.ws.service.sms.SMSService;
import com.born.fcs.pm.ws.service.specialpaper.SpecialPaperService;
import com.born.fcs.pm.ws.service.stampapply.StampApplyService;
import com.born.fcs.pm.ws.service.summary.AfterwardsProjectSummaryService;
import com.born.fcs.pm.ws.service.user.UserExtraMessageService;
import com.born.fcs.rm.ws.service.accountbalance.AccountBalanceService;
import com.born.fcs.rm.ws.service.report.ReportService;
import com.born.fcs.rm.ws.service.report.inner.ProjectInfoService;
import com.born.fcs.rm.ws.service.submission.SubmissionService;
import com.bornsoft.facade.api.apix.ApixCreditInvestigationFacade;
import com.bornsoft.facade.api.kingdee.KingdeeTogetheFacade;
import com.bornsoft.facade.api.risk.RiskSystemFacade;

public class ClientAutowiredBaseService extends ClientBaseSevice {
	
	/** 项目管理部分 start */
	@Autowired
	protected BasicDataService basicDataWebService;
	@Autowired
	protected DateSeqService dateSeqWebService;
	@Autowired
	protected SysParameterService sysParameterService;
	@Autowired
	protected ProjectSetupService projectSetupWebService;
	@Autowired
	protected FinancialProjectSetupService financialProjectSetupWebService;
	@Autowired
	protected FinancialProjectService financialProjectWebService;
	@Autowired
	protected FinancialProjectTransferService financialProjectTransferWebService;
	@Autowired
	protected FinancialProjectRedeemService financialProjectRedeemWebService;
	@Autowired
	protected ProjectService projectWebService;
	@Autowired
	protected FinancialProductService financialProductWebService;
	@Autowired
	protected AssessCompanyService assessCompanyWebService;
	@Autowired
	protected FormService formPmWebService;
	@Autowired
	protected FormService formFmWebService;
	@Autowired
	protected FormService formAmWebService;
	@Autowired
	protected FormService formCrmWebService;
	@Autowired
	protected InvestigationService investigationWebService;
	@Autowired
	protected AssetReviewService assetReviewWebService;
	@Autowired
	protected RiskReviewReportService riskReviewReportWebService;
	@Autowired
	protected CouncilApplyService councilApplyWebService;
	@Autowired
	protected CouncilTypeService councilTypeService;
	@Autowired
	protected CouncilProjectVoteService councilProjectVoteWebService;
	@Autowired
	protected CouncilProjectService councilProjectWebService;
	@Autowired
	protected CouncilSummaryService councilSummaryWebService;
	@Autowired
	protected DecisionInstitutionService decisionInstitutionService;
	@Autowired
	protected DecisionMemberService decisionMemberService;
	@Autowired
	protected CommonAttachmentService commonAttachmentWebService;
	@Autowired
	protected CreditRefrerenceApplyService creditRefrerenceApplyWebService;
	@Autowired
	protected LoanUseApplyService loanUseApplyWebService;
	@Autowired
	protected ChargeNotificationService chargeNotificationWebService;
	@Autowired
	protected ChargeNotificationFeeService chargeNotificationFeeWebService;
	@Autowired
	protected StampApplyService stampApplyWebService;
	@Autowired
	protected ExpireProjectService expireProjectWebService;
	@Autowired
	protected SMSService sMSWebService;
	@Autowired
	protected ContractTemplateService contractTemplateWebService;
	@Autowired
	protected FContractTemplateService fContractTemplateWebService;
	@Autowired
	protected ProjectContractExtraValueService projectContractExtraValueWebService;
	@Autowired
	protected RiskWarningService riskWarningWebService;
	@Autowired
	protected RiskWarningSignalService riskWarningSignalWebService;
	@Autowired
	protected AfterwardsCheckService afterwardsCheckWebService;
	@Autowired
	protected ProjectContractService projectContractWebService;
	@Autowired
	protected DbTableService dbTableWebService;
	@Autowired
	protected DbFieldService dbFieldWebService;
	@Autowired
	protected ProjectRelatedUserService projectRelatedUserWebService;
	@Autowired
	protected FormRelatedUserService formRelatedUserWebService;
	@Autowired
	protected ProjectCreditConditionService projectCreditConditionWebService;
	@Autowired
	protected ProjectIssueInformationService projectIssueInformationWebService;
	@Autowired
	protected ProjectBondReinsuranceInformationService projectBondReinsuranceInformationWebService;
	@Autowired
	protected CouncilApplyRiskHandleService councilApplyRiskHandleWebService;
	@Autowired
	protected CounterGuaranteeService counterGuaranteeWebService;
	@Autowired
	protected RiskLevelService riskLevelWebService;
	
	@Autowired
	protected CouncilService councilWebService;
	@Autowired
	protected FCapitalAppropriationApplyService fCapitalAppropriationApplyWebService;
	@Autowired
	protected FileService fileWebService;
	
	@Autowired
	protected SiteMessageService siteMessageWebService;
	
	@Autowired
	protected RecouncilApplyService recouncilApplyWebService;
	@Autowired
	protected AfterwardsProjectSummaryService afterwardsProjectSummaryWebService;
	@Autowired
	protected ConsentIssueNoticeService consentIssueNoticeWebService;
	@Autowired
	protected SpecialPaperService specialPaperWebService;
	
	@Autowired
	protected FormMessageTempleteService formMessageTempleteWebService;
	@Autowired
	protected FormChangeApplyService formChangeApplyWebService;
	@Autowired
	protected UserExtraMessageService userExtraMessageService;
	@Autowired
	protected RefundApplicationService refundApplicationWebService;
	
	@Autowired
	protected SysDbBackupService sysDbBackupWebService;
	
	@Autowired
	protected ProjectExtendService projectExtendWebService;
	
	@Autowired
	protected ChargeRepayPlanService chargeRepayPlanWebService;
	
	@Autowired
	protected InternalOpinionExchangeService internalOpinionExchangeWebService;
	
	// 追偿
	@Autowired
	protected ProjectRecoveryService projectRecoveryWebService;
	
	//经纪业务
	@Autowired
	protected BrokerBusinessService brokerBusinessWebService;
	
	//子系统对接项目信息
	@Autowired
	protected SubsystemDockProjectService subsystemDockProjectWebService;
	
	@Autowired
	protected ProjectChannelRelationService projectChannelRelationWebService;
	/** 项目管理部分 end */
	
	/** 资产管理部分 start */
	@Autowired
	protected PledgeAssetService pledgeAssetWebService;
	@Autowired
	protected PledgeTypeService pledgeTypeWebService;
	@Autowired
	protected PledgeTextCustomService pledgeTextCustomWebService;
	@Autowired
	protected PledgeImageCustomService pledgeImageCustomWebService;
	@Autowired
	protected PledgeNetworkCustomService pledgeNetworkCustomWebService;
	//资产属性
	@Autowired
	protected PledgeTypeAttributeService pledgeTypeAttributeWebService;
	//资产转让
	@Autowired
	protected AssetsTransferApplicationService assetsTransferApplicationWebService;
	//资产受让
	@Autowired
	protected AssetsAssessCompanyService assetsAssessCompanyWebService;
	//评估公司
	@Autowired
	protected AssetsTransfereeApplicationService assetsTransfereeApplicationWebService;
	@Autowired
	protected AssessCompanyApplyService assessCompanyApplyWebService;
	@Autowired
	protected ProjectImportService projectImportWebService;
	/** 资产管理部分 end */
	
	/** 报表系统 start */
	@Autowired
	protected SubmissionService submissionWebService;
	@Autowired
	protected AccountBalanceService accountBalanceWebService;
	@Autowired
	protected ReportService reportWebService;
	@Autowired
	protected ProjectInfoService projectInfoWebService;
	//	@Autowired
	//	protected BaseReportService baseReportWebService;
	/** 报表系统 end */
	
	/** 资金管理 start */
	//差旅费报销单Service
	@Autowired
	protected TravelExpenseService travelExpenseService;
	@Autowired
	protected FormTransferService formTransferService;
	@Autowired
	protected CostCategoryService costCategoryService;
	@Autowired
	protected BankCategoryService bankCategoryService;
	@Autowired
	protected BudgetService budgetService;
	@Autowired
	protected ExpenseApplicationService expenseApplicationService;
	@Autowired
	protected LabourCapitalService labourCapitalService;
	@Autowired
	protected FormInnerLoanService formInnerLoanWebService;
	@Autowired
	protected PaymentApplyService paymentApplyWebService;
	@Autowired
	protected SysSubjectMessageService sysSubjectMessageWebService;
	
	@Autowired
	protected RiskHandleTeamService riskHandleTeamService;
	
	@Autowired
	protected CouncilRiskService councilRiskService;
	
	@Autowired
	protected ProjectRiskLogService projectRiskLogService;
	
	@Autowired
	protected ProjectRiskReportService projectRiskReportService;
	@Autowired
	protected CrmUseService crmUseService;
	@Autowired
	protected ChannalContractService channalContractService;
	@Autowired
	protected ReportFormAnalysisService reportFormAnalysisWebService;
	
	/** 资金管理 end */
	
	//风险
	@Autowired
	protected RiskSystemFacade riskSystemFacade;
	@Autowired
	protected ApixCreditInvestigationFacade apixCreditInvestigationFacade;
	@Autowired
	protected KingdeeTogetheFacade kingdeeTogetheFacade;
	//短信发送
	@Autowired
	protected ShortMessageService shortMessageWebService;
	
}
