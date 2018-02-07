package com.born.fcs.face.web.controller.base;

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
import com.born.fcs.face.integration.bpm.service.BpmUserQueryService;
import com.born.fcs.face.integration.bpm.service.MobileManager;
import com.born.fcs.face.integration.bpm.service.WorkflowEngineWebClient;
import com.born.fcs.face.integration.crm.service.busyRegion.BusyRegionClient;
import com.born.fcs.face.integration.crm.service.change.ChangeSaveClient;
import com.born.fcs.face.integration.crm.service.channal.ChannalClient;
import com.born.fcs.face.integration.crm.service.channal.ChannalContractClient;
import com.born.fcs.face.integration.crm.service.customer.CompanyCustomerClient;
import com.born.fcs.face.integration.crm.service.customer.CustomerServiceClient;
import com.born.fcs.face.integration.crm.service.customer.PersonalCompanyClient;
import com.born.fcs.face.integration.crm.service.customer.PersonalCustomerClient;
import com.born.fcs.face.integration.crm.service.evalue.EvaluatingBaseSetClient;
import com.born.fcs.face.integration.crm.service.evalue.EvaluatingFinancialSetClient;
import com.born.fcs.face.integration.crm.service.evalue.EvaluetingClient;
import com.born.fcs.face.integration.fm.service.forecast.ForecastServiceClient;
import com.born.fcs.face.integration.fm.service.innerLoan.FormInnerLoanServiceClient;
import com.born.fcs.face.integration.fm.service.payment.BankCategoryServiceClient;
import com.born.fcs.face.integration.fm.service.payment.BudgetServiceClient;
import com.born.fcs.face.integration.fm.service.payment.CostCategoryServiceClient;
import com.born.fcs.face.integration.fm.service.payment.ExpenseApplicationServiceClient;
import com.born.fcs.face.integration.fm.service.payment.FormTransferServiceClient;
import com.born.fcs.face.integration.fm.service.payment.LabourCapitalServiceClient;
import com.born.fcs.face.integration.fm.service.payment.TravelExpenseServiceClient;
import com.born.fcs.face.integration.fm.service.subject.SysSubjectMessageServiceClient;
import com.born.fcs.face.integration.pm.service.basicmaintain.FinancialProductServiceClient;
import com.born.fcs.face.integration.pm.service.common.CommonAttachmentServiceClient;
import com.born.fcs.face.integration.pm.service.common.SysParameterServiceClient;
import com.born.fcs.face.integration.pm.service.contract.ContractTemplateServiceClient;
import com.born.fcs.face.integration.pm.service.contract.FContractTemplateServiceClient;
import com.born.fcs.face.integration.pm.service.fund.ChargeNotificationFeeServiceClient;
import com.born.fcs.face.integration.pm.service.fund.ChargeNotificationServiceClient;
import com.born.fcs.face.integration.pm.service.investigation.AssetReviewServiceClient;
import com.born.fcs.face.integration.pm.service.investigation.InvestigationServiceClient;
import com.born.fcs.face.integration.pm.service.report.ChannelReportClient;
import com.born.fcs.face.integration.pm.service.report.PmReportServiceClient;
import com.born.fcs.face.integration.pm.service.risklevel.RiskLevelServiceClient;
import com.born.fcs.face.integration.risk.service.ApixCreditInvestigationFacadeClient;
import com.born.fcs.face.integration.risk.service.RiskPageService;
import com.born.fcs.face.integration.risk.service.RiskSystemFacadeClient;
import com.born.fcs.face.integration.risk.service.VerifyMessageSaveClient;
import com.born.fcs.face.integration.rm.service.report.ProjectInfoServiceClient;
import com.born.fcs.face.integration.rm.service.report.ReportServiceClient;
import com.born.fcs.face.integration.rm.service.submission.AccountBalanceServiceClient;
import com.born.fcs.face.integration.rm.service.submission.SubmissionServiceClient;
import com.born.fcs.face.integration.service.BasicDataCacheService;
import com.born.fcs.face.integration.service.PropertyConfigService;
import com.born.fcs.face.integration.service.SysClearWebCacheService;
import com.born.fcs.fm.ws.service.bank.BankMessageService;
import com.born.fcs.fm.ws.service.common.ReceiptPaymentFormService;
import com.born.fcs.fm.ws.service.incomeconfirm.IncomeConfirmService;
import com.born.fcs.fm.ws.service.payment.PaymentApplyService;
import com.born.fcs.fm.ws.service.receipt.ReceiptApplyService;
import com.born.fcs.fm.ws.service.report.ReportFormAnalysisService;
import com.born.fcs.pm.biz.service.common.OperationJournalService;
import com.born.fcs.pm.biz.service.common.SiteMessageService;
import com.born.fcs.pm.ws.service.assist.InternalOpinionExchangeService;
import com.born.fcs.pm.ws.service.basicmaintain.AssessCompanyService;
import com.born.fcs.pm.ws.service.basicmaintain.DecisionInstitutionService;
import com.born.fcs.pm.ws.service.basicmaintain.DecisionMemberService;
import com.born.fcs.pm.ws.service.basicmaintain.SysDataDictionaryService;
import com.born.fcs.pm.ws.service.brokerbusiness.BrokerBusinessService;
import com.born.fcs.pm.ws.service.capitalappropriationapply.FCapitalAppropriationApplyService;
import com.born.fcs.pm.ws.service.check.AfterwardsCheckService;
import com.born.fcs.pm.ws.service.common.BasicDataService;
import com.born.fcs.pm.ws.service.common.FormMessageTempleteService;
import com.born.fcs.pm.ws.service.common.FormRelatedUserService;
import com.born.fcs.pm.ws.service.common.FormService;
import com.born.fcs.pm.ws.service.common.ProjectChannelRelationService;
import com.born.fcs.pm.ws.service.common.ProjectExtendService;
import com.born.fcs.pm.ws.service.common.ProjectRelatedUserService;
import com.born.fcs.pm.ws.service.common.ProjectService;
import com.born.fcs.pm.ws.service.common.ProjectTransferService;
import com.born.fcs.pm.ws.service.common.SysDbBackupService;
import com.born.fcs.pm.ws.service.contract.DbFieldService;
import com.born.fcs.pm.ws.service.contract.DbTableService;
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
import com.born.fcs.pm.ws.service.financeaffirm.FinanceAffirmService;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectRedeemService;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectService;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectSetupService;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectTransferService;
import com.born.fcs.pm.ws.service.forCrm.CrmUseService;
import com.born.fcs.pm.ws.service.formchange.FormChangeApplyService;
import com.born.fcs.pm.ws.service.fund.ChargeRepayPlanService;
import com.born.fcs.pm.ws.service.fund.LoanUseApplyService;
import com.born.fcs.pm.ws.service.fund.RefundApplicationService;
import com.born.fcs.pm.ws.service.managerbchange.ManagerbChangeService;
import com.born.fcs.pm.ws.service.notice.ConsentIssueNoticeService;
import com.born.fcs.pm.ws.service.projectRiskLog.ProjectRiskLogService;
import com.born.fcs.pm.ws.service.projectRiskReport.ProjectRiskReportService;
import com.born.fcs.pm.ws.service.projectcreditcondition.ProjectCreditConditionService;
import com.born.fcs.pm.ws.service.projectissueinformation.ProjectBondReinsuranceInformationService;
import com.born.fcs.pm.ws.service.projectissueinformation.ProjectIssueInformationService;
import com.born.fcs.pm.ws.service.recovery.ProjectRecoveryService;
import com.born.fcs.pm.ws.service.riskHandleTeam.RiskHandleTeamService;
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
import com.born.fcs.pm.ws.service.virtualproject.VirtualProjectService;

public class BaseAutowiredController {
	
	@Autowired
	WorkflowEngineWebClient workflowEngineWebClient;
	
	@Autowired
	protected SysParameterServiceClient sysParameterServiceClient;
	
	@Autowired
	protected SysClearWebCacheService sysClearCacheServiceClient;
	@Autowired
	protected OperationJournalService operationJournalServiceClient;
	@Autowired
	protected RiskReviewReportService riskReviewReportServiceClient;
	
	@Autowired
	protected AssessCompanyService assessCompanyServiceClient;
	
	@Autowired
	protected ProjectSetupService projectSetupServiceClient;
	
	@Autowired
	protected FinancialProjectSetupService financialProjectSetupServiceClient;
	
	@Autowired
	protected FinancialProjectService financialProjectServiceClient;
	
	@Autowired
	protected FinancialProjectTransferService financialProjectTransferServiceClient;
	
	@Autowired
	protected FinancialProjectRedeemService financialProjectRedeemServiceClient;
	
	@Autowired
	protected BasicDataService basicDataServiceClient;
	
	@Autowired
	protected FormService formServiceClient; //PM 表单客户端
	@Autowired
	protected FormService formServiceFmClient; //FM 资金管理客户端
	@Autowired
	protected FormService formServiceAmClient; //AM 资产管理客户端
	@Autowired
	protected FormService formServiceCrmClient; //CRM 客户管理客户端
	@Autowired
	protected InvestigationServiceClient investigationServiceClient;
	@Autowired
	protected AssetReviewServiceClient assetReviewServiceClient;
	
	@Autowired
	protected BasicDataCacheService basicDataCacheService;
	
	@Autowired
	protected CouncilApplyService councilApplyServiceClient;
	
	@Autowired
	protected CouncilService councilServiceClient;
	
	@Autowired
	protected CouncilSummaryService councilSummaryServiceClient;
	
	@Autowired
	protected CouncilProjectService councilProjectServiceClient;
	
	@Autowired
	protected CouncilProjectVoteService councilProjectVoteServiceClient;
	
	@Autowired
	protected CouncilTypeService councilTypeServiceClient;
	
	@Autowired
	protected DecisionInstitutionService decisionInstitutionServiceClient;
	
	@Autowired
	protected DecisionMemberService decisionMemberServiceClient;
	
	@Autowired
	protected CreditRefrerenceApplyService creditRefrerenceApplyServiceClient;
	
	@Autowired
	protected StampApplyService stampApplyServiceClient;
	
	@Autowired
	protected BpmUserQueryService bpmUserQueryService;
	@Autowired
	protected ProjectService projectServiceClient;
	
	@Autowired
	protected ProjectIssueInformationService projectIssueInformationServiceClient;
	
	@Autowired
	protected ProjectBondReinsuranceInformationService projectBondReinsuranceInformationServiceClient;
	
	@Autowired
	protected ProjectCreditConditionService projectCreditConditionServiceClient;
	
	@Autowired
	protected ExpireProjectService expireProjectServiceClient;
	
	@Autowired
	protected RiskWarningService riskWarningServiceClient;
	
	@Autowired
	protected CouncilApplyRiskHandleService councilApplyRiskHandleServiceClient;
	
	@Autowired
	protected RiskWarningSignalService riskWarningSignalServiceClient;
	
	@Autowired
	protected LoanUseApplyService loanUseApplyServiceClient;
	
	@Autowired
	protected SMSService sMSServiceClient;
	
	@Autowired
	protected FinancialProductServiceClient financialProductServiceClient;
	
	@Autowired
	protected ContractTemplateServiceClient contractTemplateServiceClient;
	@Autowired
	protected FContractTemplateServiceClient fContractTemplateServiceClient;
	@Autowired
	protected CommonAttachmentServiceClient commonAttachmentServiceClient;
	
	@Autowired
	protected AfterwardsCheckService afterwardsCheckServiceClient;
	
	@Autowired
	protected ProjectContractService projectContractServiceClient;
	
	@Autowired
	protected DbTableService dbTableServiceClient;
	
	@Autowired
	protected DbFieldService dbFieldServiceClient;
	
	@Autowired
	protected ProjectRelatedUserService projectRelatedUserServiceClient;
	
	@Autowired
	protected FormRelatedUserService formRelatedUserServiceClient;
	
	@Autowired
	protected CounterGuaranteeService counterGuaranteeServiceClient;
	
	@Autowired
	protected RiskLevelServiceClient riskLevelServiceClient;
	
	@Autowired
	protected ChargeNotificationServiceClient chargeNotificationServiceClient;
	
	@Autowired
	protected ChargeNotificationFeeServiceClient chargeNotificationFeeServiceClient;
	
	@Autowired
	protected FCapitalAppropriationApplyService fCapitalAppropriationApplyServiceClient;
	
	@Autowired
	protected FileService fileServiceClient;
	
	@Autowired
	protected SiteMessageService siteMessageServiceClient;
	
	@Autowired
	protected RecouncilApplyService recouncilApplyServiceClient;
	
	@Autowired
	protected AfterwardsProjectSummaryService afterwardsProjectSummaryServiceClient;
	@Autowired
	protected ConsentIssueNoticeService consentIssueNoticeServiceClient;
	@Autowired
	protected SpecialPaperService specialPaperServiceClient;
	@Autowired
	protected FormMessageTempleteService formMessageTempleteServiceClient;
	@Autowired
	protected FormChangeApplyService formChangeApplyServiceClient;
	
	@Autowired
	protected PropertyConfigService propertyConfigService;
	
	@Autowired
	protected MobileManager mobileManager;
	
	@Autowired
	protected UserExtraMessageService userExtraMessageServiceClient;
	@Autowired
	protected RefundApplicationService refundApplicationServiceClient;
	
	@Autowired
	protected SysDbBackupService sysDbBackupServiceClient;
	
	@Autowired
	protected ProjectExtendService projectExtendServiceClient;
	
	@Autowired
	protected ChargeRepayPlanService chargeRepayPlanServiceClient;
	
	@Autowired
	protected InternalOpinionExchangeService internalOpinionExchangeServiceClient;
	
	@Autowired
	protected FinanceAffirmService financeAffirmServiceClient;
	
	@Autowired
	protected ProjectChannelRelationService projectChannelRelationServiceClient;
	
	@Autowired
	protected ProjectTransferService projectTransferServiceClient;
	
	@Autowired
	protected VirtualProjectService virtualProjectServiceClient;
	
	//资产
	@Autowired
	protected PledgeTypeService pledgeTypeServiceClient;
	@Autowired
	protected PledgeTextCustomService pledgeTextCustomServiceClient;
	@Autowired
	protected PledgeImageCustomService pledgeImageCustomServiceClient;
	@Autowired
	protected PledgeNetworkCustomService pledgeNetworkCustomServiceClient;
	@Autowired
	protected PledgeAssetService pledgeAssetServiceClient;
	@Autowired
	protected PledgeTypeAttributeService pledgeTypeAttributeServiceClient;
	@Autowired
	protected AssetsTransferApplicationService assetsTransferApplicationServiceClient;
	@Autowired
	protected AssetsTransfereeApplicationService assetsTransfereeApplicationServiceClient;
	@Autowired
	protected AssetsAssessCompanyService assetsAssessCompanyServiceClient;
	@Autowired
	protected AssessCompanyApplyService assessCompanyApplyServiceClient;
	//客户管理
	@Autowired
	protected CompanyCustomerClient companyCustomerClient;
	@Autowired
	protected PersonalCustomerClient personalCustomerClient;
	@Autowired
	protected PersonalCompanyClient personalCompanyClient;
	@Autowired
	protected ChannalClient channalClient;
	@Autowired
	protected EvaluatingBaseSetClient evaluatingBaseSetClient;
	@Autowired
	protected EvaluatingFinancialSetClient evaluatingFinancialSetClient;
	@Autowired
	protected EvaluetingClient evaluetingClient;
	@Autowired
	protected CustomerServiceClient customerServiceClient;
	@Autowired
	protected ChangeSaveClient changeSaveClient;
	
	// 追偿
	@Autowired
	protected ProjectRecoveryService projectRecoveryServiceClient;
	
	//经纪业务
	@Autowired
	protected BrokerBusinessService brokerBusinessServiceClient;
	//业务经理B角更换
	@Autowired
	protected ManagerbChangeService managerbChangeServiceClient;
	
	//报表系统start
	@Autowired
	protected SubmissionServiceClient submissionServiceClient;
	@Autowired
	protected AccountBalanceServiceClient accountBalanceServiceClient;
	@Autowired
	protected ReportServiceClient reportServiceClient;
	@Autowired
	protected ProjectInfoServiceClient projectInfoServiceClient;
	//	@Autowired
	//	protected BaseReportServiceClient baseReportServiceClient;
	//报表系统end
	
	//资金管理start
	@Autowired
	protected TravelExpenseServiceClient travelExpenseServiceClient;
	@Autowired
	protected FormTransferServiceClient formTransferServiceClient;
	@Autowired
	protected CostCategoryServiceClient costCategoryServiceClient;
	@Autowired
	protected BankCategoryServiceClient bankCategoryServiceClient;
	@Autowired
	protected BudgetServiceClient budgetServiceClient;
	@Autowired
	protected ExpenseApplicationServiceClient expenseApplicationServiceClient;
	@Autowired
	protected LabourCapitalServiceClient labourCapitalServiceClient;
	@Autowired
	protected FormInnerLoanServiceClient formInnerLoanServiceClient;
	@Autowired
	protected SysSubjectMessageServiceClient sysSubjectMessageServiceClient;
	@Autowired
	protected BankMessageService bankMessageServiceClient;
	@Autowired
	protected ReceiptApplyService receiptApplyServiceClient;
	@Autowired
	protected PaymentApplyService paymentApplyServiceClient;
	@Autowired
	protected ReceiptPaymentFormService receiptPaymentFormServiceClient;
	
	@Autowired
	protected RiskHandleTeamService riskHandleTeamServiceClient;
	
	@Autowired
	protected CouncilRiskService councilRiskServiceClient;
	
	@Autowired
	protected ProjectRiskLogService projectRiskLogClient;
	
	@Autowired
	protected ProjectRiskReportService projectRiskReportClient;
	@Autowired
	protected CrmUseService crmUseServiceClient;
	
	@Autowired
	protected ReportFormAnalysisService reportFormAnalysisServiceClient;
	
	@Autowired
	protected IncomeConfirmService incomeConfirmServiceClient;
	//资金管理end
	
	//风险页面跳转接口
	@Autowired
	protected RiskPageService riskPageService;
	//风险检测
	@Autowired
	protected ApixCreditInvestigationFacadeClient apixCreditInvestigationFacadeClient;
	@Autowired
	protected RiskSystemFacadeClient riskSystemFacadeClient;
	@Autowired
	protected VerifyMessageSaveClient verifyMessageSaveClient;
	@Autowired
	protected ForecastServiceClient forecastServiceClient;
	//报表
	@Autowired
	protected PmReportServiceClient pmReportServiceClient;
	//渠道合同
	@Autowired
	protected ChannalContractClient channalContractClient;
	//业务区域
	@Autowired
	protected BusyRegionClient busyRegionClient;
	@Autowired
	protected ChannelReportClient channelReportClient;
	//短信发送
	@Autowired
	protected ShortMessageService shortMessageServiceClient;
	//数据字典
	@Autowired
	protected SysDataDictionaryService sysDataDictionaryServiceClient;
}
