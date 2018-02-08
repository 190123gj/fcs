package com.born.fcs.pm.biz.service.fund;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.fm.ws.enums.ForecastFeeTypeEnum;
import com.born.fcs.fm.ws.order.forecast.ForecastAccountChangeOrder;
import com.born.fcs.fm.ws.order.incomeconfirm.IncomeConfirmOrder;
import com.born.fcs.fm.ws.service.forecast.ForecastService;
import com.born.fcs.pm.biz.convert.DoConvert;
import com.born.fcs.pm.biz.convert.UnBoxingConverter;
import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.pm.dal.dataobject.FLoanUseApplyDO;
import com.born.fcs.pm.dal.dataobject.FLoanUseApplyFeeDO;
import com.born.fcs.pm.dal.dataobject.FLoanUseApplyReceiptDO;
import com.born.fcs.pm.dal.dataobject.ProjectDO;
import com.born.fcs.pm.dataobject.LoanUseApplyFormDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.integration.fm.service.IncomeConfirmServiceClient;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.base.QueryProjectBase;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.ChargePhaseEnum;
import com.born.fcs.pm.ws.enums.CheckStatusEnum;
import com.born.fcs.pm.ws.enums.CommonAttachmentTypeEnum;
import com.born.fcs.pm.ws.enums.CustomerTypeEnum;
import com.born.fcs.pm.ws.enums.ExpireProjectStatusEnum;
import com.born.fcs.pm.ws.enums.FeeTypeEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.LoanUseApplyTypeEnum;
import com.born.fcs.pm.ws.enums.PaymentMenthodEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;
import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectChannelRelationInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.common.ProjectSimpleDetailInfo;
import com.born.fcs.pm.ws.info.financeaffirm.FFinanceAffirmDetailInfo;
import com.born.fcs.pm.ws.info.financeaffirm.FFinanceAffirmInfo;
import com.born.fcs.pm.ws.info.financeaffirm.ProjectChargePayInfo;
import com.born.fcs.pm.ws.info.fund.FChargeNotificationResultInfo;
import com.born.fcs.pm.ws.info.fund.FLoanUseApplyFeeInfo;
import com.born.fcs.pm.ws.info.fund.FLoanUseApplyInfo;
import com.born.fcs.pm.ws.info.fund.FLoanUseApplyReceiptInfo;
import com.born.fcs.pm.ws.order.common.CommonAttachmentBatchOrder;
import com.born.fcs.pm.ws.order.common.ProjectChannelRelationAmountOrder;
import com.born.fcs.pm.ws.order.expireproject.ExpireProjectOrder;
import com.born.fcs.pm.ws.order.financeaffirm.ProjectChargePayQueryOrder;
import com.born.fcs.pm.ws.order.fund.FChargeNotificationQueryOrder;
import com.born.fcs.pm.ws.order.fund.FLoanUseApplyFeeOrder;
import com.born.fcs.pm.ws.order.fund.FLoanUseApplyOrder;
import com.born.fcs.pm.ws.order.fund.FLoanUseApplyReceiptBatchOrder;
import com.born.fcs.pm.ws.order.fund.FLoanUseApplyReceiptOrder;
import com.born.fcs.pm.ws.order.fund.LoanUseApplyFormInfo;
import com.born.fcs.pm.ws.order.fund.LoanUseApplyQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.fund.LoanUseApplyResult;
import com.born.fcs.pm.ws.service.base.CheckBeforeProcessService;
import com.born.fcs.pm.ws.service.common.CommonAttachmentService;
import com.born.fcs.pm.ws.service.common.ProjectChannelRelationService;
import com.born.fcs.pm.ws.service.common.ProjectService;
import com.born.fcs.pm.ws.service.council.CouncilSummaryService;
import com.born.fcs.pm.ws.service.expireproject.ExpireProjectService;
import com.born.fcs.pm.ws.service.financeaffirm.FinanceAffirmService;
import com.born.fcs.pm.ws.service.fund.ChargeNotificationService;
import com.born.fcs.pm.ws.service.fund.LoanUseApplyService;
import com.born.fcs.pm.ws.service.projectissueinformation.ProjectIssueInformationService;
import com.born.fcs.pm.ws.service.releaseproject.ReleaseProjectService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.service.base.AfterProcessInvokeService;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("loanUseApplyService")
public class LoanUseApplyServiceImpl extends BaseFormAutowiredDomainService implements
																			LoanUseApplyService {
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private CouncilSummaryService councilSummaryService;
	
	@Autowired
	protected ExpireProjectService expireProjectService;
	
	@Autowired
	ChargeNotificationService chargeNotificationService;
	
	@Autowired
	protected ReleaseProjectService releaseProjectService;
	
	@Autowired
	ProjectIssueInformationService projectIssueInformationService;
	
	@Autowired
	CommonAttachmentService commonAttachmentService;
	
	@Autowired
	ProjectChannelRelationService projectChannelRelationService;
	
	@Autowired
	FinanceAffirmService financeAffirmService;
	
	@Autowired
	ForecastService forecastServiceClient;
	@Autowired
	IncomeConfirmServiceClient incomeConfirmServiceClient;
	
	@Override
	public LoanUseApplyResult saveApply(final FLoanUseApplyOrder order) {
		order.setRelatedProjectCode(order.getProjectCode());
		return (LoanUseApplyResult) commonFormSaveProcess(order, "保存放用款申请",
			new CheckBeforeProcessService() {
				
				@Override
				public void check() {
					ProjectSimpleDetailInfo project = projectService.querySimpleDetailInfo(order
						.getProjectCode());
					if (project == null) {
						throw ExceptionFactory
							.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "项目不存在");
					}
					
					//承销和诉保没有放用款
					if (ProjectUtil.isUnderwriting(project.getBusiType())
						|| ProjectUtil.isLitigation(project.getBusiType())) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"项目类型不存在放用款");
					}
					
					//重新授信的项目不限制
					if (project.getIsRedoProject() != BooleanEnum.IS) {
						if (StringUtil.isBlank(project.getContractNo())) {
							throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
								"合同尚未回传，不能放用款");
						}
						
						//先收 
						//用款先收的情况
						if ((StringUtil.equals("USE", order.getApplyType()) || StringUtil.equals(
							"BOTH", order.getApplyType()))
							&& project.getChargePhase() == ChargePhaseEnum.BEFORE) {
							
							//查看收费情况
							FChargeNotificationQueryOrder qOrder = new FChargeNotificationQueryOrder();
							qOrder.setFormStatus(FormStatusEnum.APPROVAL.code());
							qOrder.setProjectCode(project.getProjectCode());
							qOrder.setQueryFeeList(true);
							QueryBaseBatchResult<FChargeNotificationResultInfo> chargeList = chargeNotificationService
								.queryChargeNotificationList(qOrder);
							if (chargeList.getTotalCount() == 0) {
								throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
									"该项目用款需要先收费");
							}
							
							//保费收取情况
							Money guaranteeFee = Money.zero();
							for (FChargeNotificationResultInfo feeInfo : chargeList.getPageList()) {
								//							if (ListUtil.isNotEmpty(feeInfo.getFeeList())) {
								//								for (FChargeNotificationFeeInfo fee : feeInfo.getFeeList()) {
								//									if (fee.getFeeType() == FeeTypeEnum.GUARANTEE_FEE) {
								//										guaranteeFee.addTo(fee.getChargeAmount());
								//									}
								//								}
								//							}
								//取财务确认信息
								FFinanceAffirmInfo firmInfo = feeInfo.getFirmInfo();
								if (firmInfo != null) {
									if (ListUtil.isNotEmpty(firmInfo.getFeeList())) {
										for (FFinanceAffirmDetailInfo fee : firmInfo.getFeeList()) {
											if (fee.getChargeFeeType() == FeeTypeEnum.GUARANTEE_FEE) {
												guaranteeFee.addTo(fee.getPayAmount());
											}
										}
									}
								}
							}
							
							if (!ProjectUtil.isEntrusted(project.getBusiType())
								&& !guaranteeFee.greaterThan(ZERO_MONEY)) {
								throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
									"该项目用款需要先收取担保费");
							}
							
						}
					}
					FcsPmDomainHolder.get().addAttribute("project", project);
				}
			}, new BeforeProcessInvokeService() {
				
				@SuppressWarnings("deprecation")
				@Override
				public Domain before() {
					
					FormInfo form = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
					
					if (form == null) {
						throw ExceptionFactory
							.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "表单不存在");
					}
					
					ProjectInfo project = (ProjectInfo) FcsPmDomainHolder.get().getAttribute(
						"project");
					
					FLoanUseApplyDO apply = FLoanUseApplyDAO.findByFormId(form.getFormId());
					
					boolean isUpdate = false;
					if (apply == null) {//新增
						apply = new FLoanUseApplyDO();
						BeanCopier.staticCopy(order, apply, UnBoxingConverter.getInstance());
						apply.setFormId(form.getFormId());
					} else {//修改
						isUpdate = true;
						BeanCopier.staticCopy(order, apply, UnBoxingConverter.getInstance());
					}
					
					apply.setProjectCode(project.getProjectCode());
					LoanUseApplyTypeEnum applyType = LoanUseApplyTypeEnum.getByCode(apply
						.getApplyType());
					if (applyType == null) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"申请类型错误");
					}
					
					LoanUseApplyResult amountResult = queryProjectAmount(project,
						apply.getApplyId());
					
					Money originalAmount = amountResult.getOrignalAmount();//原始金额
					Money applyingLoanAmount = amountResult.getApplyingLoanAmount(); //申请中放款金额
					Money applyingUseAmount = amountResult.getApplyingUseAmount(); //申请中用款金额
					Money balanceLoanAmount = amountResult.getBalanceLoanAmount(); //可放款余额
					Money balanceUseAmount = amountResult.getBalanceUseAmount(); //可用款余额
					
					Money amount = (order.getAmount() == null ? Money.zero() : order.getAmount());
					
					if (project.getIsRedoProject() == BooleanEnum.IS) {
						//重新授信的项目只限制用款金额
						if (applyType == LoanUseApplyTypeEnum.USE) {
							if (ZERO_MONEY.greaterThan(balanceUseAmount.subtract(amount).subtract(
								applyingUseAmount))) {
								throw ExceptionFactory.newFcsException(FcsResultEnum.NO_BALANCE,
									"用款金额不足");
							}
						}
					} else {
						if (applyType == LoanUseApplyTypeEnum.LOAN) { //放款
						
							if (ZERO_MONEY.greaterThan(balanceLoanAmount.subtract(amount).subtract(
								applyingLoanAmount))) {
								throw ExceptionFactory.newFcsException(FcsResultEnum.NO_BALANCE,
									"放款金额不足");
							}
						} else if (applyType == LoanUseApplyTypeEnum.USE) {//用款
						
							if (project.isBond()) {
								if (ZERO_MONEY.greaterThan(project.getAccumulatedIssueAmount()
									.subtract(amount).subtract(applyingUseAmount)
									.subtract(project.getUsedAmount()))) {
									throw ExceptionFactory.newFcsException(
										FcsResultEnum.NO_BALANCE, "用款金额不足");
								}
							} else if (ZERO_MONEY.greaterThan(balanceUseAmount.subtract(amount)
								.subtract(applyingUseAmount))) {
								throw ExceptionFactory.newFcsException(FcsResultEnum.NO_BALANCE,
									"用款金额不足");
							}
							
						} else if (applyType == LoanUseApplyTypeEnum.BOTH) {///放用款
							if (ZERO_MONEY.greaterThan(balanceLoanAmount.subtract(amount).subtract(
								applyingLoanAmount))) {
								throw ExceptionFactory.newFcsException(FcsResultEnum.NO_BALANCE,
									"放款金额不足");
							}
							//非发债类的放了就可以用
							if (project.isBond()) {
								if (ZERO_MONEY.greaterThan(project.getAccumulatedIssueAmount()
									.subtract(amount).subtract(applyingUseAmount)
									.subtract(project.getUsedAmount()))) {
									throw ExceptionFactory.newFcsException(
										FcsResultEnum.NO_BALANCE, "用款金额不足");
								}
								
								//加上已经放款未用掉的部分不能超过总的发行金额
								if (amount
									.add(applyingUseAmount)
									.add(project.getUsedAmount())
									.add(
										project.getLoanedAmount().subtract(project.getUsedAmount()))
									.greaterThan(project.getAccumulatedIssueAmount())) {
									throw ExceptionFactory.newFcsException(
										FcsResultEnum.NO_BALANCE, "发售金额不足");
								}
							}
						} else { //放用款
							throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
								"未知申请类型");
						}
					}
					
					apply.setAmount(amount);
					apply.setCashDepositAmount(order.getCashDepositAmount());
					apply.setOriginalAmount(originalAmount);
					apply.setApplyingLoanAmount(applyingLoanAmount);
					if (project.isBond()) {
						apply.setApplyingUseAmount(applyingUseAmount.subtract(amountResult
							.getApplyingLoanUseAmount()));
					} else {
						apply.setApplyingUseAmount(applyingUseAmount);
					}
					apply.setLoanedAmount(amountResult.getLoanedAmount());
					apply.setUsedAmount(amountResult.getUsedAmount());
					apply.setReleasedAmount(amountResult.getReleasedAmount());
					apply.setIssueAmount(amountResult.getIssueAmount());
					apply.setIsMaximumAmount(amountResult.getIsMaximumAmount().code());
					
					//保存保证金收取情况
					ProjectChargePayQueryOrder queryOrder = new ProjectChargePayQueryOrder();
					queryOrder.setProjectCode(apply.getProjectCode());
					List<String> feeTypeList = Lists.newArrayList();
					feeTypeList.add(FeeTypeEnum.GUARANTEE_DEPOSIT.code()); //存入保证金
					feeTypeList.add(PaymentMenthodEnum.CUSTOMER_DEPOSIT_REFUND.code()); //退换客户保证金
					queryOrder.setFeeTypeList(feeTypeList);
					queryOrder.setPageSize(999);
					QueryBaseBatchResult<ProjectChargePayInfo> depositList = financeAffirmService
						.queryProjectChargePayDetail(queryOrder);
					if (depositList.isSuccess() && ListUtil.isNotEmpty(depositList.getPageList())) {
						Money customerDepositCharge = Money.zero();
						Money customerDepositRefund = Money.zero();
						for (ProjectChargePayInfo fee : depositList.getPageList()) {
							if (StringUtil.equals(fee.getFeeType(),
								FeeTypeEnum.GUARANTEE_DEPOSIT.code())) {
								customerDepositCharge.addTo(fee.getPayAmount());
							} else {
								customerDepositRefund.addTo(fee.getPayAmount());
							}
						}
						apply.setCustomerDepositCharge(customerDepositCharge);
						apply.setCustomerDepositRefund(customerDepositRefund);
					}
					
					if (isUpdate) {
						FLoanUseApplyDAO.update(apply);
					} else {
						apply.setApplyId(FLoanUseApplyDAO.insert(apply));
					}
					
					//保存收费情况
					if (order.getFeeOrder() != null) {
						//删除先
						FLoanUseApplyFeeDAO.deleteApplyId(apply.getApplyId());
						//最后新增
						for (FLoanUseApplyFeeOrder feeOrder : order.getFeeOrder()) {
							if (StringUtil.isNotBlank(feeOrder.getStartTimeStr())) {
								feeOrder.setStartTime(DateUtil.parse(feeOrder.getStartTimeStr()));
							}
							if (StringUtil.isNotBlank(feeOrder.getEndTimeStr())) {
								feeOrder.setEndTime(DateUtil.parse(feeOrder.getEndTimeStr()));
							}
							if (feeOrder.isNull())
								continue;
							
							FLoanUseApplyFeeDO fee = new FLoanUseApplyFeeDO();
							BeanCopier.staticCopy(feeOrder, fee, UnBoxingConverter.getInstance());
							
							if (StringUtil.isNotBlank(feeOrder.getStartTimeStr())) {
								fee.setStartTime(DateUtil.parse(feeOrder.getStartTimeStr()));
							}
							
							if (StringUtil.isNotBlank(feeOrder.getEndTimeStr())) {
								fee.setEndTime(DateUtil.parse(feeOrder.getEndTimeStr()));
							}
							
							if (StringUtil.isNotBlank(fee.getFeeType())) {
								FeeTypeEnum feeType = FeeTypeEnum.getByCode(fee.getFeeType());
								if (feeType != null)
									fee.setFeeTypeDesc(feeType.getMessage());
							}
							
							fee.setApplyId(apply.getApplyId());
							FLoanUseApplyFeeDAO.insert(fee);
						}
					}
					
					LoanUseApplyResult result = (LoanUseApplyResult) FcsPmDomainHolder.get()
						.getAttribute("result");
					
					BeanCopier.staticCopy(amountResult, result);
					
					result.setFormInfo(form);
					
					return null;
					
				}
			}, null, null);
	}
	
	@Override
	public FcsBaseResult saveApplyReceipt(final FLoanUseApplyReceiptBatchOrder order) {
		return commonProcess(order, "保存放用款申请单回执", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				
				Date now = FcsPmDomainHolder.get().getSysDate();
				
				FLoanUseApplyDO apply = FLoanUseApplyDAO.findById(order.getApplyId());
				if (apply == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "申请单不存在");
				}
				
				//项目
				ProjectInfo project = projectService.queryByCode(apply.getProjectCode(), false);
				if (project == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "项目不存在");
				}
				
				FcsPmDomainHolder.get().addAttribute("apply", apply);
				
				List<FLoanUseApplyReceiptDO> exits = FLoanUseApplyReceiptDAO.findByApplyId(apply
					.getApplyId());
				Money receiptAmount = Money.zero();
				for (FLoanUseApplyReceiptDO receipt : exits) {
					receiptAmount.addTo(receipt.getActualAmount());
				}
				
				List<FLoanUseApplyReceiptOrder> receiptOrder = order.getReceiptOrder();
				if (ListUtil.isEmpty(receiptOrder)) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.INCOMPLETE_REQ_PARAM,
						"回执信息不完整");
				}
				
				//查询项目资金渠道
				ProjectChannelRelationInfo singleChannel = null;
				List<ProjectChannelRelationInfo> capitalChannels = projectChannelRelationService
					.queryCapitalChannel(project.getProjectCode());
				if (capitalChannels != null && capitalChannels.size() == 1) { //默认一个资金渠道的时候
					singleChannel = capitalChannels.get(0);
				}
				
				//放用款总金额
				Money totalAmount = Money.zero();
				
				//最小放用款时间
				Date minLoanDate = null;
				
				List<FLoanUseApplyReceiptDO> receipts = Lists.newArrayList();
				
				for (FLoanUseApplyReceiptOrder rOrder : receiptOrder) {
					
					FLoanUseApplyReceiptDO receipt = new FLoanUseApplyReceiptDO();
					BeanCopier.staticCopy(rOrder, receipt, UnBoxingConverter.getInstance());
					receipt.setApplyId(apply.getApplyId());
					receipt.setApplyAmount(apply.getAmount());
					receipt.setApplyType(apply.getApplyType());
					receipt.setProjectCode(apply.getProjectCode());
					//					receipt.setVoucherUrl(order.getVoucherUrl());
					//					receipt.setOtherUrl(order.getOtherUrl());
					//					receipt.setRemark(order.getRemark());
					if (singleChannel != null) {
						receipt.setCapitalChannelId(singleChannel.getChannelId());
						receipt.setCapitalChannelCode(singleChannel.getChannelCode());
						receipt.setCapitalChannelType(singleChannel.getChannelType());
						receipt.setCapitalChannelName(singleChannel.getChannelName());
						receipt.setCapitalSubChannelId(singleChannel.getSubChannelId());
						receipt.setCapitalSubChannelCode(singleChannel.getSubChannelCode());
						receipt.setCapitalSubChannelType(singleChannel.getSubChannelType());
						receipt.setCapitalSubChannelName(singleChannel.getSubChannelName());
					}
					
					//实际保证金如果没填就是申请中的金额
					if (!order.getActualDepositAmount().greaterThan(ZERO_MONEY)) {
						receipt.setActualDepositAmount(apply.getCashDepositAmount());
					}
					
					//银行融资担保金额处理
					if (project.isBankFinancing()) {
						Money actualAmount = Money.zero();
						actualAmount.addTo(rOrder.getLiquidityLoanAmount());
						actualAmount.addTo(rOrder.getCreditLetterAmount());
						actualAmount.addTo(rOrder.getAcceptanceBillAmount());
						actualAmount.addTo(rOrder.getFixedAssetsFinancingAmount());
						receipt.setActualAmount(actualAmount);
					}
					
					if (!receipt.getActualAmount().greaterThan(ZERO_MONEY)) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.NO_BALANCE,
							LoanUseApplyTypeEnum.getByCode(apply.getApplyType()).message()
									+ "金额需大于0");
					}
					receipt.setRawAddTime(now);
					receipts.add(receipt);
					totalAmount.addTo(receipt.getActualAmount());
					
					if (minLoanDate == null || minLoanDate.after(receipt.getActualLoanTime())) {
						minLoanDate = receipt.getActualLoanTime();
					}
				}
				
				if (totalAmount.greaterThan(apply.getAmount().subtract(receiptAmount))) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.NO_BALANCE,
						LoanUseApplyTypeEnum.getByCode(apply.getApplyType()).message()
								+ "回执总金额不能大于申请金额");
				}
				
				//插入回执
				for (FLoanUseApplyReceiptDO receipt : receipts) {
					
					receipt.setId(FLoanUseApplyReceiptDAO.insert(receipt));
					
					//更新渠道相关金额
					ProjectChannelRelationAmountOrder channelAmountOrder = new ProjectChannelRelationAmountOrder();
					channelAmountOrder.setProjectCode(receipt.getProjectCode());
					channelAmountOrder.setChannelCode(receipt.getCapitalChannelCode());
					if ("LOAN".equals(receipt.getApplyType())
						|| "BOTH".equals(receipt.getApplyType())) {
						channelAmountOrder.setLoanedAmount(receipt.getActualAmount());
						channelAmountOrder.setLoanLiquidityLoanAmount(receipt
							.getLiquidityLoanAmount());
						channelAmountOrder.setLoanCreditLetterAmount(receipt
							.getCreditLetterAmount());
						channelAmountOrder.setLoanAcceptanceBillAmount(receipt
							.getAcceptanceBillAmount());
						channelAmountOrder.setLoanFixedAssetsFinancingAmount(receipt
							.getFixedAssetsFinancingAmount());
					}
					//放款添加到总的解保金额中
					if (project.isGuarantee()) {
						channelAmountOrder.setReleasableAmount(receipt.getActualAmount());
					}
					projectChannelRelationService.updateRelatedAmount(channelAmountOrder);
					
					//保存附件
					CommonAttachmentBatchOrder attachOrder = new CommonAttachmentBatchOrder();
					attachOrder.setProjectCode(apply.getProjectCode());
					attachOrder.setBizNo("PM_" + apply.getFormId() + "_" + receipt.getId());
					attachOrder.setCheckStatus(CheckStatusEnum.CHECK_PASS.code());
					attachOrder.setModuleType(CommonAttachmentTypeEnum.LOAN_USE_RECEIPT_VOUCHER);
					attachOrder.setUploaderId(order.getUserId());
					attachOrder.setUploaderName(order.getUserName());
					attachOrder.setUploaderAccount(order.getUserAccount());
					attachOrder.setPath(receipt.getVoucherUrl());
					commonAttachmentService.addNewDelOldByMoudleAndBizNo(attachOrder);
					if (StringUtil.isNotEmpty(receipt.getOtherUrl())) {
						attachOrder.setPath(receipt.getOtherUrl());
						attachOrder.setModuleType(CommonAttachmentTypeEnum.LOAN_USE_RECEIPT_OTHER);
						commonAttachmentService.addNewDelOldByMoudleAndBizNo(attachOrder);
					}
				}
				
				FcsPmDomainHolder.get().addAttribute("totalAmount", totalAmount);
				FcsPmDomainHolder.get().addAttribute("minLoanDate", minLoanDate);
				
				if (ProjectUtil.isEntrusted(project.getBusiType())) {
					//更新资金预测（委贷放款）
					try {
						for (FLoanUseApplyReceiptDO receipt : receipts) {
							if ("USE".equals(receipt.getApplyType())
								|| ZERO_MONEY.greaterThan(receipt.getActualAmount()))
								continue;
							ForecastAccountChangeOrder changeForecastOrder = new ForecastAccountChangeOrder();
							changeForecastOrder.setFeeType(ForecastFeeTypeEnum.WD_LOAN);
							changeForecastOrder.setProjectCode(project.getProjectCode());
							changeForecastOrder.setAmount(receipt.getActualAmount());
							changeForecastOrder.setUserId(order.getUserId());
							changeForecastOrder.setUserAccount(order.getUserAccount());
							changeForecastOrder.setUserName(order.getUserName());
							changeForecastOrder.setForecastMemo("放款 "
																+ receipt.getActualAmount()
																	.toStandardString() + "元");
							forecastServiceClient.change(changeForecastOrder);
						}
					} catch (Exception e) {
						logger.error("委贷放款更新资金预测数据出错：{}", e);
					}
				}
				try {
					//将收费信息同步到收入确认表中
					if (apply.getProjectCode() != null) {
						ProjectDO projectDO1 = projectDAO.findByProjectCode(apply.getProjectCode());
						IncomeConfirmOrder order = new IncomeConfirmOrder();
						order.setProjectCode(apply.getProjectCode());
						order.setCustomerId(projectDO1.getCustomerId());
						order.setCustomerName(projectDO1.getCustomerName());
						order.setBusiType(projectDO1.getBusiType());
						order.setBusiTypeName(projectDO1.getBusiTypeName());
						order.setProjectName(projectDO1.getProjectName());
						order.setChargedAmount(projectDO1.getChargedAmount());
						order.setRelatedProjectCode(apply.getProjectCode());
						order.setNotConfirmedIncomeAmount(projectDO1.getChargedAmount());
						order.setCheckStatus(0);
						order.setCheckIndex(0);
						incomeConfirmServiceClient.save(order);
					}
				} catch (Exception e) {
					logger.error("收费信息同步到收入确认表出错：{}", e);
				}
				return null;
			}
		}, null, new AfterProcessInvokeService() {
			
			@Override
			public Domain after(Domain domain) {
				
				//申请单
				FLoanUseApplyDO apply = (FLoanUseApplyDO) FcsPmDomainHolder.get().getAttribute(
					"apply");
				Money totalAmount = (Money) FcsPmDomainHolder.get().getAttribute("totalAmount");
				Date minLoanDate = (Date) FcsPmDomainHolder.get().getAttribute("minLoanDate");
				
				ProjectDO projectDO = projectDAO.findByProjectCodeForUpdate(apply.getProjectCode());
				//所有回执
				List<FLoanUseApplyReceiptInfo> receipts = queryReceipt(apply.getProjectCode());
				int otherReceiptCount = 0;
				for (FLoanUseApplyReceiptInfo receipt : receipts) {
					//不算当前回执
					if (receipt.getApplyId() == apply.getApplyId())
						continue;
					otherReceiptCount++;
				}
				
				//加锁
				synchronized (this) {
					
					//修改项目放用款金额
					if (LoanUseApplyTypeEnum.LOAN.code().equals(apply.getApplyType())
						|| LoanUseApplyTypeEnum.BOTH.code().equals(apply.getApplyType())) { //放款
						projectDO.getLoanedAmount().addTo(totalAmount);
						releaseProjectService.add(projectDO.getProjectCode());
						if (null != projectDO.getRightVoucherExtenDate()) {
							projectDO.setRightVoucherExtenDate(null);
						}
						
						//放款添加到总的解保金额中
						if (ProjectUtil.isGuarantee(projectDO.getBusiType())) {
							projectDO.setReleasableAmount(projectDO.getReleasableAmount().add(
								totalAmount));
							
						}
					}
					//担保公司存入银行的保证金 统一从资金划付走
					//					projectDO.setSelfDepositAmount(projectDO.getSelfDepositAmount().addTo(
					//						receipt.getActualDepositAmount()));
				}
				
				//第一次放用款上传回执后
				if (otherReceiptCount == 0) {
					
					//					projectDO.setBusiSubType(receipt.getBusiSubType());
					//					projectDO.setBusiSubTypeName(receipt.getBusiSubType());
					//首次放用款完成进入保后阶段
					projectDO.setPhases(ProjectPhasesEnum.AFTERWARDS_PHASES.code());
					
					//停止发售的项目添加到到期项目列表中
					try {
						// 添加到期项目列表中
						ExpireProjectOrder expireOrder = new ExpireProjectOrder();
						expireOrder.setProjectCode(projectDO.getProjectCode());
						expireOrder.setProjectName(projectDO.getProjectName());
						Date expireDate = calculateExpireDate(minLoanDate,
							TimeUnitEnum.getByCode(projectDO.getTimeUnit()),
							projectDO.getTimeLimit());
						expireOrder.setExpireDate(expireDate);
						expireOrder.setStatus(ExpireProjectStatusEnum.UNDUE);
						expireProjectService.add(expireOrder);
						//更新项目开始结束时间
						//starttime endtime 为NULL才更新project表时间
						if (projectDO.getStartTime() == null) {
							projectDO.setStartTime(minLoanDate);
						}
						if (projectDO.getEndTime() == null) {
							projectDO.setEndTime(expireDate);
							//项目有到期时间预测退换客户保证金
							if (expireDate != null) {
								projectService.syncForecastDeposit(projectDO.getProjectCode());
							}
						}
					} catch (Exception e) {
						logger.error("添加到期项目(第一次放用款上传回执后)异常: {}", e);
					}
				}
				
				//更新项目信息
				projectDAO.update(projectDO);
				
				return null;
			}
		});
	}
	
	@Override
	public FLoanUseApplyInfo queryByFormId(long formId) {
		FLoanUseApplyDO DO = FLoanUseApplyDAO.findByFormId(formId);
		if (DO == null)
			return null;
		FLoanUseApplyInfo info = convertLoanUseApplyDO2Info(DO);
		info.setFeeList(queryFeeByApplyId(info.getApplyId()));
		info.setReceipt(queryReceiptByApplyId(info.getApplyId()));
		info.setIsMaximumAmount(BooleanEnum.getByCode(DO.getIsMaximumAmount()));
		return info;
	}
	
	@Override
	public FLoanUseApplyInfo queryByApplyId(long applyId) {
		FLoanUseApplyDO DO = FLoanUseApplyDAO.findById(applyId);
		if (DO == null)
			return null;
		FLoanUseApplyInfo info = convertLoanUseApplyDO2Info(DO);
		info.setFeeList(queryFeeByApplyId(info.getApplyId()));
		info.setReceipt(queryReceiptByApplyId(info.getApplyId()));
		info.setIsMaximumAmount(BooleanEnum.getByCode(DO.getIsMaximumAmount()));
		return info;
	}
	
	@Override
	public List<FLoanUseApplyInfo> queryByProjectCode(String projectCode) {
		List<FLoanUseApplyDO> data = FLoanUseApplyDAO.findByProjectCode(projectCode);
		if (data == null)
			return null;
		
		List<FLoanUseApplyInfo> list = Lists.newArrayList();
		for (FLoanUseApplyDO DO : data) {
			FLoanUseApplyInfo info = convertLoanUseApplyDO2Info(DO);
			info.setFeeList(queryFeeByApplyId(info.getApplyId()));
			info.setReceipt(queryReceiptByApplyId(info.getApplyId()));
			list.add(info);
		}
		return list;
	}
	
	@Override
	public List<FLoanUseApplyFeeInfo> queryFeeByApplyId(long applyId) {
		
		List<FLoanUseApplyFeeDO> data = FLoanUseApplyFeeDAO.findByApplyId(applyId);
		if (ListUtil.isEmpty(data))
			return null;
		
		List<FLoanUseApplyFeeInfo> list = Lists.newArrayList();
		for (FLoanUseApplyFeeDO DO : data) {
			list.add(convertFLoanUseApplyFeeDO2Info(DO));
		}
		return list;
	}
	
	@Override
	public List<FLoanUseApplyReceiptInfo> queryReceiptByApplyId(long applyId) {
		List<FLoanUseApplyReceiptDO> dos = FLoanUseApplyReceiptDAO.findByApplyId(applyId);
		if (ListUtil.isNotEmpty(dos)) {
			List<FLoanUseApplyReceiptInfo> receipts = Lists.newArrayList();
			for (FLoanUseApplyReceiptDO DO : dos) {
				receipts.add(convertApplyReceiptDO2Info(DO));
			}
			return receipts;
		}
		return null;
	}
	
	@Override
	public List<FLoanUseApplyReceiptInfo> queryReceipt(String projectCode) {
		List<FLoanUseApplyReceiptDO> data = FLoanUseApplyReceiptDAO.findByProjectCode(projectCode);
		List<FLoanUseApplyReceiptInfo> list = Lists.newArrayList();
		if (ListUtil.isNotEmpty(data)) {
			for (FLoanUseApplyReceiptDO DO : data) {
				list.add(convertApplyReceiptDO2Info(DO));
			}
		}
		return list;
	}
	
	private FLoanUseApplyInfo convertLoanUseApplyDO2Info(FLoanUseApplyDO DO) {
		if (DO == null)
			return null;
		FLoanUseApplyInfo info = new FLoanUseApplyInfo();
		BeanCopier.staticCopy(DO, info);
		info.setApplyType(LoanUseApplyTypeEnum.getByCode(DO.getApplyType()));
		info.setCashDepositTimeUnit(TimeUnitEnum.getByCode(DO.getCashDepositTimeUnit()));
		return info;
	}
	
	private FLoanUseApplyFeeInfo convertFLoanUseApplyFeeDO2Info(FLoanUseApplyFeeDO DO) {
		if (DO == null)
			return null;
		FLoanUseApplyFeeInfo info = new FLoanUseApplyFeeInfo();
		BeanCopier.staticCopy(DO, info);
		info.setFeeType(FeeTypeEnum.getByCode(DO.getFeeType()));
		return info;
	}
	
	private FLoanUseApplyReceiptInfo convertApplyReceiptDO2Info(FLoanUseApplyReceiptDO DO) {
		if (DO == null)
			return null;
		FLoanUseApplyReceiptInfo info = new FLoanUseApplyReceiptInfo();
		BeanCopier.staticCopy(DO, info);
		info.setApplyType(LoanUseApplyTypeEnum.getByCode(DO.getApplyType()));
		return info;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public QueryBaseBatchResult<LoanUseApplyFormInfo> searchApplyForm(LoanUseApplyQueryOrder order) {
		
		QueryBaseBatchResult<LoanUseApplyFormInfo> baseBatchResult = new QueryBaseBatchResult<LoanUseApplyFormInfo>();
		LoanUseApplyFormDO queryCondition = new LoanUseApplyFormDO();
		BeanCopier.staticCopy(order, queryCondition, UnBoxingConverter.getInstance());
		queryCondition.setDeptIdList(order.getDeptIdList());
		queryCondition.setStatusList(order.getStatusList());
		if (order.getHasReceipt() != null) {
			queryCondition.setHasReceipt(order.getHasReceipt().code());
		}
		long totalSize = extraDAO.searchLoanUseApplyFormCount(queryCondition);
		PageComponent component = new PageComponent(order, totalSize);
		List<LoanUseApplyFormDO> pageList = extraDAO.searchLoanUseApplyForm(queryCondition);
		
		List<LoanUseApplyFormInfo> list = baseBatchResult.getPageList();
		if (totalSize > 0) {
			for (LoanUseApplyFormDO apply : pageList) {
				LoanUseApplyFormInfo info = new LoanUseApplyFormInfo();
				BeanCopier.staticCopy(apply, info);
				info.setFormCode(FormCodeEnum.getByCode(apply.getFormCode()));
				info.setFormStatus(FormStatusEnum.getByCode(apply.getFormStatus()));
				info.setCustomerType(CustomerTypeEnum.getByCode(apply.getCustomerType()));
				info.setApplyType(LoanUseApplyTypeEnum.getByCode(apply.getApplyType()));
				info.setTimeUnit(TimeUnitEnum.getByCode(apply.getTimeUnit()));
				list.add(info);
			}
		}
		
		baseBatchResult.initPageParam(component);
		baseBatchResult.setPageList(list);
		baseBatchResult.setSuccess(true);
		return baseBatchResult;
	}
	
	@Override
	public QueryBaseBatchResult<ProjectSimpleDetailInfo> selectLoanUseApplyProject(	QueryProjectBase order) {
		QueryBaseBatchResult<ProjectSimpleDetailInfo> baseBatchResult = new QueryBaseBatchResult<ProjectSimpleDetailInfo>();
		ProjectDO queryCondition = new ProjectDO();
		BeanCopier.staticCopy(order, queryCondition);
		long totalSize = extraDAO.selectLoanUseApplyProjectCount(queryCondition,
			order.getProjectCodeOrName(), order.getLoginUserId(), order.getDraftUserId(),
			order.getDeptIdList());
		PageComponent component = new PageComponent(order, totalSize);
		List<ProjectDO> pageList = extraDAO.selectLoanUseApplyProject(queryCondition,
			order.getProjectCodeOrName(), order.getLoginUserId(), order.getDraftUserId(),
			order.getDeptIdList(), component.getFirstRecord(), component.getPageSize(),
			order.getSortCol(), order.getSortOrder());
		
		List<ProjectSimpleDetailInfo> list = baseBatchResult.getPageList();
		if (totalSize > 0) {
			for (ProjectDO project : pageList) {
				list.add(projectService.getSimpleDetailInfo(DoConvert
					.convertProjectDO2Info(project)));
			}
		}
		
		baseBatchResult.initPageParam(component);
		baseBatchResult.setPageList(list);
		baseBatchResult.setSuccess(true);
		return baseBatchResult;
	}
	
	@Override
	public LoanUseApplyResult getApplyingAmountByProjectCode(String projectCode, long currentApplyId) {
		ProjectInfo project = projectService.queryByCode(projectCode, false);
		if (project == null) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "项目不存在");
		}
		return getApplyingAmount(project, currentApplyId);
	}
	
	@Override
	public LoanUseApplyResult getApplyingAmount(ProjectInfo project, long currentApplyId) {
		
		LoanUseApplyResult result = new LoanUseApplyResult();
		try {
			
			Money applyingLoanAmount = Money.zero();
			Money applyingUseAmount = Money.zero();
			Money applyingLoanUseAmount = Money.zero();
			
			LoanUseApplyQueryOrder order = new LoanUseApplyQueryOrder();
			order.setProjectCode(project.getProjectCode());
			order.setPageSize(999);
			
			List<String> statusList = Lists.newArrayList();
			statusList.add(FormStatusEnum.SUBMIT.code());
			statusList.add(FormStatusEnum.AUDITING.code());
			statusList.add(FormStatusEnum.BACK.code());
			order.setStatusList(statusList);
			
			//申请中表单
			QueryBaseBatchResult<LoanUseApplyFormInfo> submitedForm = searchApplyForm(order);
			for (LoanUseApplyFormInfo applyingForm : submitedForm.getPageList()) {
				if (currentApplyId != applyingForm.getApplyId()) { //不包括当前申请
					if (applyingForm.getApplyType() == LoanUseApplyTypeEnum.LOAN) {//放款
						applyingLoanAmount.addTo(applyingForm.getApplyAmount());
					} else if (applyingForm.getApplyType() == LoanUseApplyTypeEnum.USE) {//用款
						applyingUseAmount.addTo(applyingForm.getApplyAmount());
					} else { //放用款
						applyingLoanUseAmount.addTo(applyingForm.getApplyAmount());
						applyingLoanAmount.addTo(applyingForm.getApplyAmount());
						applyingUseAmount.addTo(applyingForm.getApplyAmount());
					}
				}
			}
			//审核通过但未回执的表单
			order.setStatusList(null);
			order.setFormStatus(FormStatusEnum.APPROVAL.code());
			order.setHasReceipt(BooleanEnum.NO);
			submitedForm = searchApplyForm(order);
			for (LoanUseApplyFormInfo applyingForm : submitedForm.getPageList()) {
				if (currentApplyId != applyingForm.getApplyId()) { //不包括当前申请
					if (applyingForm.getApplyType() == LoanUseApplyTypeEnum.LOAN) {//放款
						applyingLoanAmount.addTo(applyingForm.getApplyAmount());
					} else if (applyingForm.getApplyType() == LoanUseApplyTypeEnum.USE) {//用款
						applyingUseAmount.addTo(applyingForm.getApplyAmount());
					} else { //放用款
						applyingLoanAmount.addTo(applyingForm.getApplyAmount());
					}
				}
			}
			result.setApplyingLoanAmount(applyingLoanAmount);
			result.setApplyingLoanUseAmount(applyingLoanUseAmount);
			result.setApplyingUseAmount(applyingUseAmount);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage("获取申请中放用款金额出错");
			logger.error("获取申请中放用款金额出错 {}", e);
		}
		return result;
	}
	
	@Override
	public LoanUseApplyResult queryProjectAmount(ProjectInfo project, long currentApplyId) {
		
		LoanUseApplyResult result = createResult();
		Money originalAmount = project.getContractAmount();//原始金额
		//重新授信的项目就是授信金额
		if (project.getIsRedoProject() == BooleanEnum.IS) {
			originalAmount = project.getAmount();
		}
		//2017-1-19 规则变更 放款不限制金额 不限制先收费、用款才限制、发债类最高能放发行金额
		//		if (ProjectUtil.isBond(project.getBusiType())) { //发债金额以累计发行金额为准
		//			if (project.getReinsuranceRatio() > 0) { //分保的情况
		//				originalAmount = project.getReinsuranceAmount();
		//			} else {//不分保直接取发行金额
		//				originalAmount = project.getAccumulatedIssueAmount();
		//			}
		//		}
		Money loanedAmount = project.getLoanedAmount();//已放款金额
		LoanUseApplyResult applyingResult = getApplyingAmount(project, currentApplyId);
		Money usedAmount = project.getUsedAmount(); //已用金额
		Money releasedAmount = project.getReleasedAmount(); //已解保金额
		Money applyingLoanAmount = Money.zero(); //申请中放款金额
		Money applyingUseAmount = Money.zero(); //申请中用款金额
		if (applyingResult != null && applyingResult.isSuccess()) {
			applyingLoanAmount = applyingResult.getApplyingLoanAmount();
			applyingUseAmount = applyingResult.getApplyingUseAmount();
		}
		
		//查询会议项目批复中 该项目是否为最高额授信
		boolean isMaximumAmount = (BooleanEnum.IS == project.getIsMaximumAmount());
		
		//可放款金额  = 总金额 - 已放款金额
		Money balanceLoanAmount = originalAmount.subtract(loanedAmount);
		//如果是最高授信额 可放款金额 = 总金额 - 已放款金额  + 已还款金额
		if (isMaximumAmount) {
			balanceLoanAmount.addTo(releasedAmount);
		}
		
		//可用款金额 = 已放款金额 - 已用款金额
		Money balanceUseAmount = Money.zero();
		if (project.getIsRedoProject() == BooleanEnum.IS) {
			balanceUseAmount = loanedAmount.subtract(usedAmount);
		} else {
			boolean isBond = ProjectUtil.isBond(project.getBusiType());
			if (isBond && loanedAmount.greaterThan(project.getAccumulatedIssueAmount())) {
				balanceUseAmount = project.getAccumulatedIssueAmount().subtract(usedAmount);
			} else if (!isBond || project.getAccumulatedIssueAmount().getCent() > 0) {
				balanceUseAmount = loanedAmount.subtract(usedAmount);
			}
		}
		
		result.setApplyingLoanAmount(applyingLoanAmount);
		result.setApplyingLoanUseAmount(applyingResult.getApplyingLoanUseAmount());
		result.setApplyingUseAmount(applyingUseAmount);
		result.setUsedAmount(usedAmount);
		result.setLoanedAmount(loanedAmount);
		result.setBalanceLoanAmount(balanceLoanAmount);
		if (ZERO_MONEY.greaterThan(balanceUseAmount))
			balanceUseAmount = ZERO_MONEY;
		result.setBalanceUseAmount(balanceUseAmount);
		result.setOrignalAmount(originalAmount);
		result.setReleasedAmount(releasedAmount);
		result.setIsMaximumAmount(isMaximumAmount ? BooleanEnum.IS : BooleanEnum.NO);
		result.setIssueAmount(project.getAccumulatedIssueAmount());
		
		result.setSuccess(true);
		result.setMessage("查询成功");
		return result;
	}
	
	@Override
	public LoanUseApplyResult queryFormAmount(FLoanUseApplyInfo apply) {
		
		LoanUseApplyResult result = createResult();
		Money originalAmount = apply.getOriginalAmount();//原始金额
		Money loanedAmount = apply.getLoanedAmount();//已放款金额
		Money usedAmount = apply.getUsedAmount(); //已用金额
		Money applyingLoanAmount = apply.getApplyingLoanAmount(); //申请中放款金额
		Money applyingUseAmount = apply.getApplyingUseAmount(); //申请中用款金额
		Money releasedAmount = apply.getReleasedAmount(); //已解保金额
		Money issueAmount = apply.getIssueAmount(); //已发售金额
		
		//查询会议项目批复中 该项目是否为最高额授信
		BooleanEnum isMaximumAmount = apply.getIsMaximumAmount();
		
		//可放款金额  = 总金额 - 已放款金额
		Money balanceLoanAmount = originalAmount.subtract(loanedAmount);
		
		//如果是最高授信额 可放款金额 = 总金额 - 已放款金额  + 已还款金额
		if (isMaximumAmount == BooleanEnum.IS) {
			balanceLoanAmount.addTo(releasedAmount);
		}
		
		ProjectInfo project = projectService.queryByCode(apply.getProjectCode(), false);
		//可用款金额 = 已放款金额 - 已用款金额
		Money balanceUseAmount = Money.zero();
		if (project.getIsRedoProject() == BooleanEnum.IS) {
			balanceUseAmount = loanedAmount.subtract(usedAmount);
		} else {
			boolean isBond = ProjectUtil.isBond(project.getBusiType());
			if (isBond && loanedAmount.greaterThan(issueAmount)) {
				balanceUseAmount = issueAmount.subtract(usedAmount);
			} else if (!isBond || issueAmount.getCent() > 0) {
				balanceUseAmount = loanedAmount.subtract(usedAmount);
			}
		}
		result.setApplyingLoanAmount(applyingLoanAmount);
		result.setApplyingUseAmount(applyingUseAmount);
		result.setUsedAmount(usedAmount);
		result.setLoanedAmount(loanedAmount);
		result.setBalanceLoanAmount(balanceLoanAmount);
		if (ZERO_MONEY.greaterThan(balanceUseAmount))
			balanceUseAmount = ZERO_MONEY;
		result.setBalanceUseAmount(balanceUseAmount);
		result.setOrignalAmount(originalAmount);
		result.setReleasedAmount(releasedAmount);
		result.setIsMaximumAmount(isMaximumAmount);
		result.setIssueAmount(apply.getIssueAmount());
		
		result.setSuccess(true);
		result.setMessage("查询成功");
		
		return result;
	}
	
	@Override
	protected LoanUseApplyResult createResult() {
		return new LoanUseApplyResult();
	}
}
