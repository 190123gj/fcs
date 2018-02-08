package com.born.fcs.pm.ws.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * 表单流程映射
 * 
 * @author wuzj
 */
public enum FormCodeEnum {
	
	DEFAULT("DEFAULT", "", "", "", "", "", "", "通用"),
	
	//项目管理 start
	SET_UP_GUARANTEE_ENTRUSTED("SET_UP_GUARANTEE_ENTRUSTED", "ProjectApproval",
								"projectSetupProcessService", "/projectMg/setUp/view.htm",
								"/projectMg/setUp/edit.htm", "/projectMg/setUp/print.htm",
								"011101", "担保/委贷类项目立项"),
	
	SET_UP_UNDERWRITING("SET_UP_UNDERWRITING", "ConsignmentSalesApproval",
						"projectSetupProcessService", "/projectMg/setUp/view.htm",
						"/projectMg/setUp/edit.htm", "/projectMg/setUp/print.htm", "010", "承销类项目立项"),
	
	SET_UP_LITIGATION_PERSIONAL("SET_UP_LITIGATION_PERSIONAL", "DBProjectApproval",
								"projectSetupProcessService", "/projectMg/setUp/view.htm",
								"/projectMg/setUp/edit.htm", "/projectMg/setUp/print.htm", "0110",
								"诉讼保函业务立项-个人"),
	
	SET_UP_LITIGATION_ENTERPRISE("SET_UP_LITIGATION_ENTERPRISE", "DBProjectApproval",
									"projectSetupProcessService", "/projectMg/setUp/view.htm",
									"/projectMg/setUp/edit.htm", "/projectMg/setUp/print.htm",
									"01110", "诉讼保函业务立项-企业"),
	
	SET_UP_FINANCING("SET_UP_FINANCING", "FinancingAudit", "financialProjectSetupProcessService",
						"/projectMg/financialProject/setUp/view.htm",
						"/projectMg/financialProject/setUp/edit.htm", "", "1", "理财项目立项申请"),
	
	FINANCING_TRANSFER("FINANCING_TRANSFER", "FinancingTransferAudit",
						"financialProjectTransferProcessService",
						"/projectMg/financialProject/transfer/view.htm",
						"/projectMg/financialProject/transfer/edit.htm", "", "1", "理财项目转让"),
	
	FINANCING_REDEEM("FINANCING_REDEEM", "FinancingRedeemAudit",
						"financialProjectRedeemProcessService",
						"/projectMg/financialProject/redeem/view.htm",
						"/projectMg/financialProject/redeem/edit.htm", "", "1", "理财项目赎回"),
	
	FINANCING_REVIEW("FINANCING_REVIEW", "FinancialReviewAudit",
						"financialProjectReviewProcessService",
						"/projectMg/financialProject/review/view.htm",
						"/projectMg/financialProject/review/edit.htm", "", "1", "理财项目送审"),
	
	FINANCING_CONTRACT("FINANCING_CONTRACT", "FinancialContractAudit",
						"financialProjectContractProcessService",
						"/projectMg/financialProject/contract/view.htm",
						"/projectMg/financialProject/contract/edit.htm", "", "1", "理财项目合同"),
	
	RISK_REVIEW_REPORT("RISK_REVIEW_REPORT", "", "", "/projectMg/riskreview/viewReview.htm",
						"/projectMg/riskreview/edit.htm", "", "0", "风险审查报告"),
	
	INVESTIGATION_BASE("INVESTIGATION_BASE", "ProjectInspect", "investigationProcessService",
						"/projectMg/investigation/view.htm",
						"/projectMg/investigation/editDeclare.htm", "", "0000000100", "担保委贷尽职调查报告"),
	
	INVESTIGATION_LITIGATION("INVESTIGATION_LITIGATION", "DBProjectInspect",
								"investigationProcessService", "/projectMg/investigation/view.htm",
								"/projectMg/investigation/editDeclare.htm", "", "10", "诉讼保函尽职调查报告"),
	
	INVESTIGATION_UNDERWRITING("INVESTIGATION_UNDERWRITING", "ExamineAndApprove",
								"investigationProcessService", "/projectMg/investigation/view.htm",
								"/projectMg/investigation/editDeclare.htm", "", "10", "承销项目尽职调查报告"),
	
	RISK_WARNING("RISK_WARNING", "ProjectWarning", "riskWarningProcessService",
					"/projectMg/riskWarning/view.htm", "/projectMg/riskWarning/edit.htm", "", "0",
					"风险预警"),
	
	COUNCIL_APPLY("COUNCIL_APPLY", "RiskDisposeApply", "riskHandleProcessService",
					"/projectMg/riskHandle/view.htm", "/projectMg/riskHandle/edit.htm", "", "0",
					"项目上会申报"),
	
	COUNTER_GUARANTEE("COUNTER_GUARANTEE", "ExpireRefundAndRescission",
						"counterGuaranteeProcessService", "/projectMg/counterGuarantee/view.htm",
						"/projectMg/counterGuarantee/edit.htm", "", "0", "解保申请"),
	
	COUNTER_GUARANTEE_LITIGATION("COUNTER_GUARANTEE_LITIGATION", "SSExpireRefundAndRescission",
									"counterGuaranteeProcessService",
									"/projectMg/counterGuarantee/view.htm",
									"/projectMg/counterGuarantee/edit.htm", "", "0", "解保申请-诉保"),
	
	RISK_LEVEL("RISK_LEVEL", "SXProjectRiskClassifyAudit", "riskLevelProcessService",
				"/projectMg/riskLevel/view.htm", "/projectMg/riskLevel/edit.htm", "", "0",
				"项目风险等级评级"),
	
	COUNCIL_SUMMARY_PROJECT_REVIEW("COUNCIL_SUMMARY_PROJECT_REVIEW", "MeetingNoteReview",
									"councilSummaryProcessService",
									"/projectMg/meetingMg/summary/view.htm",
									"/projectMg/meetingMg/summary/edit.htm", "", "0", "项目评审会-会议纪要"),
	
	COUNCIL_SUMMARY_RISK_HANDLE("COUNCIL_SUMMARY_RISK_HANDLE", "MeetingNoteReview",
								"councilSummaryProcessService",
								"/projectMg/meetingMg/summary/view.htm",
								"/projectMg/meetingMg/summary/edit.htm", "", "0", "风险处置会-会议纪要"),
	
	STAMP_APPLY("STAMP_APPLY", "SealApply", "stampApplyProcessService",
				"/projectMg/stampapply/viewAudit.htm", "/projectMg/stampapply/editStampApply.htm",
				"/projectMg/stampapply/print.htm", "0", "用印申请"),
	STAMP_BASIC_DATA_APPLY("STAMP_BASIC_DATA_APPLY", "InitialValueSealApply",
							"stampBasicDataApplyProcessService",
							"/projectMg/stampapply/basicDataView.htm",
							"/projectMg/stampapply/editBasicDataStampApply.htm",
							"/projectMg/stampapply/basicDataPrint.htm", "0", "基础资料用印申请"),
	
	LOAN_USE_APPLY("LOAN_USE_APPLY", "GrantAudit", "loanUseApplyProcessService",
					"/projectMg/loanUseApply/view.htm", "/projectMg/loanUseApply/edit.htm", "",
					"0", "放用款申请"),
	
	CHARGE_NOTIFICATION("CHARGE_NOTIFICATION", "CollectFee", "chargeNotificationProcessService",
						"/projectMg/chargeNotification/viewAudit.htm",
						"/projectMg/chargeNotification/editChargeNotification.htm",
						"/projectMg/chargeNotification/print.htm", "0", "收款通知"),
	
	AFTERWARDS_CHECK_GUARANTEE("AFTERWARDS_CHECK_GUARANTEE", "SSDBGuaranteedManage",
								"afterwardsCheckProcessService",
								"/projectMg/afterwardsCheck/view.htm",
								"/projectMg/afterwardsCheck/edit.htm", "", "100", "担保委贷保后检查报告"),
	AFTERWARDS_CHECK_LITIGATION("AFTERWARDS_CHECK_LITIGATION", "GuaranteedManage",
								"afterwardsCheckProcessService",
								"/projectMg/afterwardsCheck/viewLitigation.htm",
								"/projectMg/afterwardsCheck/editLitigation.htm", "", "0",
								"诉讼保函保后检查报告"),
	
	PROJECT_CREDIT_CONDITION_CONFIRM(
										"PROJECT_CREDIT_CONDITION_CONFIRM",
										"SXProjectConditionConfirm",
										"projectCreditConditionProcessService",
										"/projectMg/projectCreditCondition/viewProjectCreditCondition.htm",
										"/projectMg/projectCreditCondition/editProjectCredit.htm",
										"/projectMg/projectCreditCondition/print.htm", "0",
										"授信条件落实情况"),
	AFTERWARDS_PROJECT_SUMMARY("AFTERWARDS_PROJECT_SUMMARY", "afterwardsSummarizingAudit",
								"afterwardsProjectSummaryProcessService",
								"/projectMg/afterwardsSummary/viewSummary.htm",
								"/projectMg/afterwardsSummary/editSummary.htm",
								"/projectMg/afterwardsSummary/print.htm", "0", "保后汇总表"),
	PROJECT_CONTRACT("PROJECT_CONTRACT", "ContractAudit", "projectContractProcessService",
						"/projectMg/contract/viewAudit.htm",
						"/projectMg/contract/editContract.htm", "/projectMg/contract/print.htm",
						"0", "项目合同集"),
	PROJECT_CONTRACT_INVALID("PROJECT_CONTRACT_INVALID", "ZFContractAudit",
								"projectContractInvalidProcessService",
								"/projectMg/contract/viewAuditInvalid.htm",
								"/projectMg/contract/addProjectContractItemInvlid.htm", "", "0",
								"合同作废"),
	
	CREDIT_REFRERENCE_APPLY("CREDIT_REFRERENCE_APPLY", "CreditInquiry",
							"creditRefrerenceApplyProcessService",
							"/projectMg/creditRefrerenceApply/viewAudit.htm",
							"/projectMg/creditRefrerenceApply/editCredit.htm",
							"/projectMg/creditRefrerenceApply/print.htm", "0", "征信查询申请"),
	
	AFTERWARDS_CHECK_COMMON("AFTERWARDS_CHECK_COMMON", "", "", "", "", "", "100", "通用版保后检查报告"),
	
	AFTERWARDS_CHECK_REAL_ESTATE("AFTERWARDS_CHECK_REAL_ESTATE", "", "", "", "", "", "100",
									"房地产开发版保后检查报告"),
	
	AFTERWARDS_CHECK_CONSTRUCTION("AFTERWARDS_CHECK_CONSTRUCTION", "", "", "", "", "", "100",
									"建筑行业版保后检查报告"),
	
	AFTERWARDS_CHECK_MANUFACTURING("AFTERWARDS_CHECK_MANUFACTURING", "", "", "", "", "", "100",
									"生产制造业版保后检查报告"),
	
	AFTERWARDS_CHECK_LOAN("AFTERWARDS_CHECK_LOAN", "", "", "", "100", "", "", "小贷公司版保后检查报告"),
	
	AFTERWARDS_CHECK_CITY("AFTERWARDS_CHECK_CITY", "", "", "", "100", "", "", "城市开发版保后检查报告"),
	
	AFTERWARDS_CHECK_FIXED_ASSETS("AFTERWARDS_CHECK_FIXED_ASSETS", "", "", "", "", "", "100",
									"固定资产授信版保后检查报告"),
	
	REPLACE_STAMP_APPLY("REPLACE_STAMP_APPLY", "FileReplaceSealApply",
						"replaceStampProcessService", "/projectMg/stampapply/viewAudit.htm",
						"/projectMg/stampapply/editStampApply.htm",
						"/projectMg/stampapply/print.htm", "0", "用印申请替换"),
	
	FCAPITAL_APPROPRIATION_APPLY(
									"FCAPITAL_APPROPRIATION_APPLY",
									"FundTransfer",
									"fCapitalAppropriationApplyProcessService",
									"/projectMg/fCapitalAppropriationApply/viewCapitalAppropriationApply.htm",
									"/projectMg/fCapitalAppropriationApply/editCapitalAppropriationApply.htm",
									"/projectMg/fCapitalAppropriationApply/print.htm", "0", "资金划付"),
	
	FCAPITAL_APPROPRIATION_APPLY_COMP(
										"FCAPITAL_APPROPRIATION_APPLY_COMP",
										"CompensatoryDeductFrozenAudit",
										"fCapitalAppropriationApplyProcessService",
										"/projectMg/fCapitalAppropriationApply/viewCapitalAppropriationApply.htm",
										"/projectMg/fCapitalAppropriationApply/editCapitalAppropriationApply.htm",
										"/projectMg/fCapitalAppropriationApply/print.htm", "0",
										"被扣划冻结"),
	
	FILE_INPUT_APPLY("FILE_INPUT_APPLY", "ArchivesArchive", "fileInputApplyProcessService",
						"/projectMg/file/inputView.htm", "/projectMg/file/inputApply.htm",
						"/projectMg/file/printInput.htm", "0", "档案入库申请"),
	FILE_OUTPUT_APPLY("FILE_OUTPUT_APPLY", "ProofOutbound", "fileOutputApplyProcessService",
						"/projectMg/file/outputView.htm", "/projectMg/file/outputEdit.htm",
						"/projectMg/file/printOutput.htm", "0", "档案出库申请"),
	FILE_OUTPUT_EXTENSION("FILE_OUTPUT_EXTENSION", "RightVoucherExtension",
							"fileOutputExtensionApplyProcessService",
							"/projectMg/file/extensionView.htm",
							"/projectMg/file/extensionEdit.htm",
							"/projectMg/file/printExtension.htm", "0", "档案出库展期申请"),
	FILE_VIEW_APPLY("FILE_VIEW_APPLY", "EfileView", "fileViewApplyProcessService",
					"/projectMg/file/outputView.htm", "/projectMg/file/outputEdit.htm",
					"/projectMg/file/printOutput.htm", "0", "档案查阅申请"),
	FILE_BORROW_APPLY("FILE_BORROW_APPLY", "ArchivesRetrival", "fileBorrowApplyProcessService",
						"/projectMg/file/borrowView.htm", "/projectMg/file/borrowEdit.htm",
						"/projectMg/file/printBorrow.htm", "0", "档案借阅申请"),
	FILE_RETURN_APPLY("FILE_RETURN_APPLY", "ArchivesReturn", "fileReturnApplyProcessService",
						"/projectMg/file/borrowView.htm", "/projectMg/file/borrowEdit.htm",
						"/projectMg/file/printBorrow.htm", "0", "档案归还申请"),
	PROJET_RECOUNCIL_APPLY("PROJET_RECOUNCIL_APPLY", "ProjectReexamination",
							"recouncilApplyProcessService", "/projectMg/recouncil/view.htm",
							"/projectMg/recouncil/edit.htm", "", "0", "项目复议申请"),
	REFUND_APPLICATION("REFUND_APPLICATION", "RefundRequest", "refundApplicationProcessService",
						"/projectMg/refundApplication/view.htm",
						"/projectMg/refundApplication/edit.htm",
						"/projectMg/refundApplication/print.htm", "0", "退费申请"),
	
	FORM_CHANGE_APPLY("FORM_CHANGE_APPLY", "ProjectRequestAndReport",
						"formChangeApplyProcessService", "/projectMg/formChangeApply/view.htm",
						"/projectMg/formChangeApply/edit.htm", "", "1", "签报申请"),
	
	BROKER_BUSINESS("BROKER_BUSINESS", "XHProjectRequestAndReport", "brokerBusinessProcessService",
					"/projectMg/brokerBusiness/view.htm", "/projectMg/brokerBusiness/edit.htm", "",
					"1", "经纪业务"),
	
	INTERNAL_OPINION_EXCHANGE_REPORT_SEEK_OP("INTERNAL_OPINION_EXCHANGE_REPORT_SEEK_OP",
												"IdeasExchange",
												"internalOpinionExchangeProcessService",
												"/projectMg/internalOpinionExchange/view.htm",
												"/projectMg/internalOpinionExchange/edit.htm",
												"/projectMg/internalOpinionExchange/print.htm",
												"1", "《合规检查报告》征求意见"),
	
	INTERNAL_OPINION_EXCHANGE_WP_CONFIRM("INTERNAL_OPINION_EXCHANGE_WP_CONFIRM",
											"ConfirmationSheetAudit",
											"internalOpinionExchangeProcessService",
											"/projectMg/internalOpinionExchange/view.htm",
											"/projectMg/internalOpinionExchange/edit.htm",
											"/projectMg/internalOpinionExchange/print.htm", "1",
											"《合规检查工作底稿》确认"),
	
	INTERNAL_OPINION_EXCHANGE_REPORT_DELI("INTERNAL_OPINION_EXCHANGE_REPORT_DELI",
											"SheetSendAudit",
											"internalOpinionExchangeProcessService",
											"/projectMg/internalOpinionExchange/view.htm",
											"/projectMg/internalOpinionExchange/edit.htm",
											"/projectMg/internalOpinionExchange/print.htm", "1",
											"《合规检查报告》送达"),
	
	PROJECT_SUMMARY("PROJECT_SUMMARY", "ProjectSummaryAudit", "projectExtendProcessService",
					"/projectMg/summary/view.htm", "/projectMg/summary/edit.htm", "", "1", "项目小结"),
	
	PROJECT_WRIT("PROJECT_WRIT", "wssc", "projectWritProcessService",
					"/projectMg/contract/viewAudit.htm", "/projectMg/contract/editContract.htm",
					"/projectMg/contract/print.htm", "0", "项目文书集"),
	
	PROJECT_LETTER("PROJECT_LETTER", "hhtzssp", "projectLetterProcessService",
					"/projectMg/contract/viewAudit.htm", "/projectMg/contract/editContract.htm",
					"/projectMg/contract/print.htm", "0", "项目函、通知书集"),
	
	PROJECT_DB_LETTER("PROJECT_DB_LETTER", "BondBackLetterAudit", "projectDBLetterProcessService",
						"/projectMg/contract/viewAudit.htm",
						"/projectMg/contract/editContract.htm", "/projectMg/contract/print.htm",
						"0", "担保函出具/保证合同签订"),
	
	MANAGERB_CHANGE_APPLY("MANAGERB_CHANGE_APPLY", "ChangeRoleB", "managerbChangeProcessService",
							"/projectMg/managerbchange/view.htm",
							"/projectMg/managerbchange/edit.htm",
							"/projectMg/managerbchange/print.htm", "1", "业务经理B角变更申请"),
	CONTRACT_TEMPLATE("CONTRACT_TEMPLATE", "ContractEditAudit", "contractTemplateProcessService",
						"/projectMg/contract/auditTemplate.htm",
						"/projectMg/contract/modifyTemplate.htm",
						"/projectMg/contract/printTemplate.htm", "0", "合同模板"),
	
	PROJECT_TRANSFER("PROJECT_TRANSFER", "ProjectHandOverAudit", "projectTransferProcessService",
						"/projectMg/transfer/view.htm", "/projectMg/transfer/edit.htm",
						"/projectMg/transfer/print.htm", "0", "项目移交"),
	
	//项目管理end
	
	//资产管理 start				
	ASSETS_TRANSFER_APPLICATION("ASSETS_TRANSFER_APPLICATION", "AssetTransfer",
								"assetsTransferApplicationProcessService",
								"/assetMg/transfer/view.htm", "/assetMg/transfer/edit.htm",
								"/assetMg/transfer/print.htm", "0", "资产转让"),
	
	ASSETS_TRANSFEREE_APPLICATION("ASSETS_TRANSFEREE_APPLICATION", "AssetSR",
									"assetsTransfereeApplicationProcessService",
									"/assetMg/transferee/view.htm", "/assetMg/transferee/edit.htm",
									"/assetMg/transferee/print.htm", "0", "资产受让"),
	ASSETS_COMPANY_APPLY("ASSETS_COMPANY_APPLY", "ChangeEvaluateCompany",
							"assessCompanyApplyProcessService",
							"/assetMg/assessCompanyApply/view.htm",
							"/assetMg/assessCompanyApply/edit.htm",
							"/assetMg/assessCompanyApply/print.htm", "0", "评估公司申请单"),
	ASSETS_COMPANY_CHANGE("ASSETS_COMPANY_CHANGE", "CEvaluateCompany",
							"assessCompanyChangeProcessService",
							"/assetMg/assessCompanyApply/viewChange.htm",
							"/assetMg/assessCompanyApply/editChange.htm",
							"/assetMg/assessCompanyApply/print.htm", "0", "评估公司更换"),
	//资产管理 end						
	
	//资金管理 start
	FORM_INNER_LOAN("FORM_INNER_LOAN", "InternalLoanAudit", "formInnerLoanProcessService",
					"/fundMg/innerLoan/innerLoanMessage.htm",
					"/fundMg/innerLoan/innerLoanModify.htm", "/fundMg/innerLoan/print.htm", "0",
					"内部借款单"),
	
	FM_RECEIPT_APPLY("FM_RECEIPT_APPLY", "ReceiptBillsAudit", "receiptApplyProcessService",
						"/fundMg/receipt/apply/view.htm", "/fundMg/receipt/apply/edit.htm",
						"/fundMg/receipt/apply/print.htm", "0", "收款单"),
	
	FM_PAYMENT_APPLY("FM_PAYMENT_APPLY", "PaymentBillsAudit", "paymentApplyProcessService",
						"/fundMg/payment/apply/view.htm", "/fundMg/payment/apply/edit.htm",
						"/fundMg/payment/apply/print.htm", "0", "付款单"),
	
	TRAVEL_EXPENSE_APPLY("TRAVEL_EXPENSE_APPLY", "TravelExpenseReimbursing",
							"travelExpenseProcessService", "/fundMg/travelExpense/view.htm",
							"/fundMg/travelExpense/travelAdd.htm",
							"/fundMg/travelExpense/print.htm", "0", "差旅费报销单"),
	
	EVALUETING_CUSTOMER("EVALUETING_CUSTOMER", "CustomerRating", "evaluetingProcessService",
						"/customerMg/evaluting/view.htm", "/customerMg/evaluting/edit.htm",
						"/fundMg/travelExpense/print.htm", "0", "客户配评级审核"),
	
	EXPENSE_APPLICATION("EXPENSE_APPLICATION", "FeePaymentBillsAudit",
						"expenseApplyProcessService", "/fundMg/expenseApplication/view.htm",
						"/fundMg/expenseApplication/edit.htm",
						"/fundMg/expenseApplication/print.htm", "0", "费用支付申请单"),
	EXPENSE_APPLICATION_NLIMIT("EXPENSE_APPLICATION_NLIMIT", "NLimitTypeFeePaymentAudit",
								"expenseApplyProcessService",
								"/fundMg/expenseApplication/view.htm",
								"/fundMg/expenseApplication/edit.htm",
								"/fundMg/expenseApplication/print.htm", "0", "费用支付(无金额规则类)审核"),
	EXPENSE_APPLICATION_DF("EXPENSE_APPLICATION_DF", "DFPaymentBillsAudit",
							"expenseApplyProcessService", "/fundMg/expenseApplication/view.htm",
							"/fundMg/expenseApplication/edit.htm",
							"/fundMg/expenseApplication/print.htm", "0", "费用支付(党费)审批"),
	EXPENSE_APPLICATION_TF("EXPENSE_APPLICATION_TF", "TFPaymentBillsAudit",
							"expenseApplyProcessService", "/fundMg/expenseApplication/view.htm",
							"/fundMg/expenseApplication/edit.htm",
							"/fundMg/expenseApplication/print.htm", "0", "费用支付(团委费)审批"),
	EXPENSE_APPLICATION_LZ("EXPENSE_APPLICATION_LZ", "LZFeePaymentAudit",
							"expenseApplyProcessService", "/fundMg/expenseApplication/view.htm",
							"/fundMg/expenseApplication/edit.htm",
							"/fundMg/expenseApplication/print.htm", "0", "费用支付(劳资类)审核"),
	EXPENSE_APPLICATION_LIMIT("EXPENSE_APPLICATION_LIMIT", "LimitFeePaymentBillsAudit",
								"expenseApplyProcessService",
								"/fundMg/expenseApplication/view.htm",
								"/fundMg/expenseApplication/edit.htm",
								"/fundMg/expenseApplication/print.htm", "0", "费用支付(金额规则类)审批"),
	EXPENSE_APPLICATION_FZJG("EXPENSE_APPLICATION_FZJG", "FilialeFeePaymentBillsAudit",
								"expenseApplyProcessService",
								"/fundMg/expenseApplication/view.htm",
								"/fundMg/expenseApplication/edit.htm",
								"/fundMg/expenseApplication/print.htm", "0", "费用支付(分支机构)审批"),
	EXPENSE_APPLICATION_JE_XH("EXPENSE_APPLICATION_JE_XH", "XHPaymentBillsAudit",
								"expenseApplyProcessService",
								"/fundMg/expenseApplication/view.htm",
								"/fundMg/expenseApplication/edit.htm",
								"/fundMg/expenseApplication/print.htm", "0", "费用支付(信惠)审批"),
	EXPENSE_APPLICATION_LZ_XH("EXPENSE_APPLICATION_LZ_XH", "XHLZPaymentBillsAudit",
								"expenseApplyProcessService",
								"/fundMg/expenseApplication/view.htm",
								"/fundMg/expenseApplication/edit.htm",
								"/fundMg/expenseApplication/print.htm", "0", "费用支付(信惠劳资及税金)审批"),
	EXPENSE_APPLICATION_REFUND("EXPENSE_APPLICATION_REFUND", "RefundFeePaymentBillsAudit",
								"expenseApplyProcessService",
								"/fundMg/expenseApplication/view.htm",
								"/fundMg/expenseApplication/edit.htm",
								"/fundMg/expenseApplication/print.htm", "0", "费用支付(还款类)审批"),
	EXPENSE_APPLICATION_LOAN("EXPENSE_APPLICATION_LOAN", "LoanFeePaymentBillsAudit",
								"expenseApplyProcessService",
								"/fundMg/expenseApplication/view.htm",
								"/fundMg/expenseApplication/edit.htm",
								"/fundMg/expenseApplication/print.htm", "0", "费用支付(借款类)审批流程"),
	EXPENSE_APPLICATION_CPREPAY("EXPENSE_APPLICATION_CPREPAY", "CPrepayFeePaymentBillsAudit",
								"expenseApplyProcessService",
								"/fundMg/expenseApplication/view.htm",
								"/fundMg/expenseApplication/edit.htm",
								"/fundMg/expenseApplication/print.htm", "0", "费用支付(冲预付款类)审批"),
	EXPENSE_APPLICATION_PREPAY("EXPENSE_APPLICATION_PREPAY", "PrepayFeePaymentBillsAudit",
								"expenseApplyProcessService",
								"/fundMg/expenseApplication/view.htm",
								"/fundMg/expenseApplication/edit.htm",
								"/fundMg/expenseApplication/print.htm", "0", "费用支付(预付款类)审批"),
	
	EXPENSE_APPLICATION_UNION_FUNDS("EXPENSE_APPLICATION_UNION_FUNDS", "UnionFundsPaymentBillsAudit",
								"expenseApplyProcessService",
								"/fundMg/expenseApplication/view.htm",
								"/fundMg/expenseApplication/edit.htm",
								"/fundMg/expenseApplication/print.htm", "0", "费用支付(工会经费类)审批"),
	
	LABOUR_CAPITAL_QT("LABOUR_CAPITAL_QT", "ElsePaymentAudit", "labourCapitalProcessService",
						"/fundMg/labourCapital/view.htm", "/fundMg/labourCapital/edit.htm",
						"/fundMg/labourCapital/print.htm", "0", "劳资及税金(其他)审批"),
	LABOUR_CAPITAL_LZ("LABOUR_CAPITAL_LZ", "LZPaymentAudit", "labourCapitalProcessService",
						"/fundMg/labourCapital/view.htm", "/fundMg/labourCapital/edit.htm",
						"/fundMg/labourCapital/print.htm", "0", "劳资及税金(劳资类)审批"),
	LABOUR_CAPITAL_SJ("LABOUR_CAPITAL_SJ", "SJPaymentAudit", "labourCapitalProcessService",
						"/fundMg/labourCapital/view.htm", "/fundMg/labourCapital/edit.htm",
						"/fundMg/labourCapital/print.htm", "0", "劳资及税金(税金类)审批"),
	
	TRANSFER_APPLICATION("TRANSFER_APPLICATION", "TransferBillsAudit",
							"formTransferProcessService", "/fundMg/transfer/view.htm",
							"/fundMg/transfer/edit.htm", "/fundMg/transfer/print.htm", "0", "转账申请单"),
	//资金管理 end
	CHANNAL_CONTRACT("CHANNAL_CONTRACT", "qdContractAudit", "channalContractProcessService",
						"/customerMg/channalContract/view.htm",
						"/customerMg/channalContract/edit.htm", "/fundMg/travelExpense/print.htm",
						"0", "渠道合同"), ;
	
	/** 表单code */
	private final String code;
	
	/**
	 * 对应流程编号
	 */
	private final String flowCode;
	
	/**
	 * 流程业务处理的相关serviceName
	 */
	private final String processServiceName;
	
	/** 枚举描述 */
	private final String message;
	
	/**
	 * 默认的验证状态
	 */
	private final String defaultCheckStatus;
	
	/**
	 * 表单查看Url
	 */
	private final String viewUrl;
	
	/**
	 * 编辑页面
	 */
	private final String editUrl;
	
	/**
	 * 打印页面
	 */
	private final String printUrl;
	
	/**
	 * 构造一个<code>FormCodeEnum</code>枚举对象
	 * 
	 * @param code
	 * @param message
	 */
	private FormCodeEnum(String code, String flowCode, String processServiceName, String viewUrl,
							String editUrl, String printUrl, String defaultCheckStatus,
							String message) {
		this.code = code;
		this.flowCode = flowCode;
		this.processServiceName = processServiceName;
		this.defaultCheckStatus = defaultCheckStatus;
		this.viewUrl = viewUrl;
		this.editUrl = editUrl;
		this.printUrl = printUrl;
		this.message = message;
	}
	
	/**
	 * @return Returns the code.
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * @return Returns the code.
	 */
	public String getFlowCode() {
		return flowCode;
	}
	
	/**
	 * @return Returns the processServiceName.
	 */
	public String getProcessServiceName() {
		return processServiceName;
	}
	
	/**
	 * @return Returns the message.
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * @return Returns the viewUrl.
	 */
	public String getViewUrl() {
		return viewUrl;
	}
	
	/**
	 * @return Returns the editUrl.
	 */
	public String getEditUrl() {
		return editUrl;
	}
	
	/**
	 * @return Returns the printUrl.
	 */
	public String getPrintUrl() {
		return printUrl;
	}
	
	/**
	 * @return Returns the code.
	 */
	public String code() {
		return code;
	}
	
	/**
	 * @return Returns the flowCode.
	 */
	public String flowCode() {
		return flowCode;
	}
	
	/**
	 * @return Returns the processServiceName.
	 */
	public String processServiceName() {
		return processServiceName;
	}
	
	/**
	 * @return Returns the message.
	 */
	public String message() {
		return message;
	}
	
	/**
	 * @return Returns the viewUrl.
	 */
	public String viewUrl() {
		return viewUrl;
	}
	
	/**
	 * @return Returns the editUrl.
	 */
	public String editUrl() {
		return editUrl;
	}
	
	/**
	 * @return Returns the printUrl.
	 */
	public String printUrl() {
		return printUrl;
	}
	
	/**
	 * @return Returns the defaultCheckStatus.
	 */
	public String defaultCheckStatus() {
		return this.defaultCheckStatus;
	}
	
	/**
	 * @return Returns the defaultCheckStatus.
	 */
	public String getDefaultCheckStatus() {
		return this.defaultCheckStatus;
	}
	
	/**
	 * 通过枚举<code>code</code>获得枚举
	 * 
	 * @param code
	 * @return FormCodeEnum
	 */
	public static FormCodeEnum getByCode(String code) {
		for (FormCodeEnum _enum : values()) {
			if (_enum.getCode().equals(code)) {
				return _enum;
			}
		}
		return null;
	}
	
	/**
	 * 获取全部枚举
	 * 
	 * @return List<FormCodeEnum>
	 */
	public static List<FormCodeEnum> getAllEnum() {
		List<FormCodeEnum> list = new ArrayList<FormCodeEnum>();
		for (FormCodeEnum _enum : values()) {
			list.add(_enum);
		}
		return list;
	}
	
	/**
	 * 获取全部枚举值
	 * 
	 * @return List<String>
	 */
	public static List<String> getAllEnumCode() {
		List<String> list = new ArrayList<String>();
		for (FormCodeEnum _enum : values()) {
			if (_enum == RISK_REVIEW_REPORT)
				continue;
			list.add(_enum.code());
		}
		return list;
	}
}
