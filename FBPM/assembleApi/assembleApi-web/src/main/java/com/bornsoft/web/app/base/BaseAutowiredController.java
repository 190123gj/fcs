package com.bornsoft.web.app.base;

import java.util.Map;

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
import com.born.fcs.face.integration.crm.service.channal.ChannalClient;
import com.born.fcs.face.integration.crm.service.customer.CompanyCustomerClient;
import com.born.fcs.face.integration.crm.service.customer.CustomerServiceClient;
import com.born.fcs.face.integration.crm.service.customer.PersonalCompanyClient;
import com.born.fcs.face.integration.crm.service.customer.PersonalCustomerClient;
import com.born.fcs.face.integration.crm.service.evalue.EvaluatingBaseSetClient;
import com.born.fcs.face.integration.crm.service.evalue.EvaluatingFinancialSetClient;
import com.born.fcs.face.integration.crm.service.evalue.EvaluetingClient;
import com.born.fcs.face.integration.fm.service.innerLoan.FormInnerLoanServiceClient;
import com.born.fcs.face.integration.fm.service.payment.CostCategoryServiceClient;
import com.born.fcs.face.integration.fm.service.payment.ExpenseApplicationServiceClient;
import com.born.fcs.face.integration.fm.service.payment.TravelExpenseServiceClient;
import com.born.fcs.face.integration.fm.service.subject.SysSubjectMessageServiceClient;
import com.born.fcs.face.integration.pm.service.common.CommonAttachmentServiceClient;
import com.born.fcs.face.integration.pm.service.common.SysParameterServiceClient;
import com.born.fcs.face.integration.pm.service.fund.ChargeNotificationServiceClient;
import com.born.fcs.face.integration.pm.service.investigation.InvestigationServiceClient;
import com.born.fcs.face.integration.pm.service.risklevel.RiskLevelServiceClient;
import com.born.fcs.face.integration.risk.service.VerifyMessageSaveClient;
import com.born.fcs.face.integration.service.BasicDataCacheService;
import com.born.fcs.face.integration.service.PropertyConfigService;
import com.born.fcs.fm.ws.service.bank.BankMessageService;
import com.born.fcs.fm.ws.service.common.ReceiptPaymentFormService;
import com.born.fcs.fm.ws.service.payment.PaymentApplyService;
import com.born.fcs.fm.ws.service.receipt.ReceiptApplyService;
import com.born.fcs.pm.biz.service.common.SiteMessageService;
import com.born.fcs.pm.ws.service.assist.InternalOpinionExchangeService;
import com.born.fcs.pm.ws.service.basicmaintain.AssessCompanyService;
import com.born.fcs.pm.ws.service.basicmaintain.DecisionInstitutionService;
import com.born.fcs.pm.ws.service.basicmaintain.DecisionMemberService;
import com.born.fcs.pm.ws.service.brokerbusiness.BrokerBusinessService;
import com.born.fcs.pm.ws.service.capitalappropriationapply.FCapitalAppropriationApplyService;
import com.born.fcs.pm.ws.service.check.AfterwardsCheckService;
import com.born.fcs.pm.ws.service.common.BasicDataService;
import com.born.fcs.pm.ws.service.common.FormMessageTempleteService;
import com.born.fcs.pm.ws.service.common.FormRelatedUserService;
import com.born.fcs.pm.ws.service.common.FormService;
import com.born.fcs.pm.ws.service.common.ProjectExtendService;
import com.born.fcs.pm.ws.service.common.ProjectRelatedUserService;
import com.born.fcs.pm.ws.service.common.ProjectService;
import com.born.fcs.pm.ws.service.common.SysDbBackupService;
import com.born.fcs.pm.ws.service.common.SysWebAccessTokenService;
import com.born.fcs.pm.ws.service.contract.ContractTemplateService;
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
import com.born.fcs.pm.ws.service.counterguarantee.CounterGuaranteeService;
import com.born.fcs.pm.ws.service.creditrefrerenceapply.CreditRefrerenceApplyService;
import com.born.fcs.pm.ws.service.expireproject.ExpireProjectService;
import com.born.fcs.pm.ws.service.file.FileService;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectRedeemService;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectService;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectSetupService;
import com.born.fcs.pm.ws.service.financialproject.FinancialProjectTransferService;
import com.born.fcs.pm.ws.service.formchange.FormChangeApplyService;
import com.born.fcs.pm.ws.service.fund.ChargeRepayPlanService;
import com.born.fcs.pm.ws.service.fund.LoanUseApplyService;
import com.born.fcs.pm.ws.service.fund.RefundApplicationService;
import com.born.fcs.pm.ws.service.notice.ConsentIssueNoticeService;
import com.born.fcs.pm.ws.service.projectcreditcondition.ProjectCreditConditionService;
import com.born.fcs.pm.ws.service.projectissueinformation.ProjectIssueInformationService;
import com.born.fcs.pm.ws.service.recovery.ProjectRecoveryService;
import com.born.fcs.pm.ws.service.riskreview.RiskReviewReportService;
import com.born.fcs.pm.ws.service.riskwarning.RiskWarningService;
import com.born.fcs.pm.ws.service.riskwarning.RiskWarningSignalService;
import com.born.fcs.pm.ws.service.setup.ProjectSetupService;
import com.born.fcs.pm.ws.service.sms.SMSService;
import com.born.fcs.pm.ws.service.specialpaper.SpecialPaperService;
import com.born.fcs.pm.ws.service.stampapply.StampApplyService;
import com.born.fcs.pm.ws.service.summary.AfterwardsProjectSummaryService;
import com.born.fcs.pm.ws.service.user.UserExtraMessageService;
import com.bornsoft.api.service.BornApiServiceEnum;
import com.bornsoft.api.service.app.JckFormService;
import com.bornsoft.facade.api.risk.RiskSystemFacade;
import com.bornsoft.integration.jck.config.SystemParamCacheHolder;
import com.bornsoft.utils.constants.ApiSystemParamEnum;

public class BaseAutowiredController {
	
	@Autowired
	protected VerifyMessageSaveClient verifyMessageSaveClient;
	
	@Autowired
	protected SysWebAccessTokenService sysWebAccessTokenServiceClient;
	
	@Autowired
	protected BasicDataCacheService basicDataCacheService;
	
	@Autowired
	protected CommonAttachmentServiceClient commonAttachmentServiceClient;
	
	@Autowired
	protected PropertyConfigService propertyConfigService;
	
	@Autowired
	WorkflowEngineWebClient workflowEngineWebClient;
	
	@Autowired
	protected SysParameterServiceClient sysParameterServiceClient;
	
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
	protected CustomerServiceClient customerServiceClient;
	
	@Autowired
	protected ProjectIssueInformationService projectIssueInformationServiceClient;
	
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
	protected ContractTemplateService contractTemplateServiceClient;
	
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
	// 追偿
	@Autowired
	protected ProjectRecoveryService projectRecoveryServiceClient;
	
	//经纪业务
	@Autowired
	protected BrokerBusinessService brokerBusinessServiceClient;
	
	//报表系统end
	
	//资金管理start
	@Autowired
	protected TravelExpenseServiceClient travelExpenseServiceClient;
	@Autowired
	protected CostCategoryServiceClient costCategoryServiceClient;
	@Autowired
	protected ExpenseApplicationServiceClient expenseApplicationServiceClient;
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
	//资金管理end
	
	@Autowired
	protected JckFormService  	jckFormService;
	@Autowired
	protected RiskSystemFacade riskSystemFacadeApi;
	@Autowired
	protected SystemParamCacheHolder systemParamCacheHolder;
	
	public String getConfigValue(ApiSystemParamEnum paramType){
		return this.sysParameterServiceClient.getSysParameterValue(paramType.code());
	}
	
	/**
	 * 构建本系统跳转链接
	 * @param paramMap
	 * @param service
	 * @return
	 */
	protected String buildBornApiUrl(Map<String,String> paramMap, BornApiServiceEnum service){
		return jckFormService.buildBornApiUrl(paramMap, service);
	}
}
