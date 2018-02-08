package com.born.fcs.pm.biz.service.fund;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

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
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.ProjectUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.base.QueryProjectBase;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.ChargePhaseEnum;
import com.born.fcs.pm.ws.enums.CustomerTypeEnum;
import com.born.fcs.pm.ws.enums.ExpireProjectStatusEnum;
import com.born.fcs.pm.ws.enums.FeeTypeEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.LoanUseApplyTypeEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.common.ProjectSimpleDetailInfo;
import com.born.fcs.pm.ws.info.council.FCouncilSummaryProjectInfo;
import com.born.fcs.pm.ws.info.fund.FChargeNotificationFeeInfo;
import com.born.fcs.pm.ws.info.fund.FChargeNotificationResultInfo;
import com.born.fcs.pm.ws.info.fund.FLoanUseApplyFeeInfo;
import com.born.fcs.pm.ws.info.fund.FLoanUseApplyInfo;
import com.born.fcs.pm.ws.info.fund.FLoanUseApplyReceiptInfo;
import com.born.fcs.pm.ws.info.projectissueinformation.ProjectIssueInformationInfo;
import com.born.fcs.pm.ws.order.expireproject.ExpireProjectOrder;
import com.born.fcs.pm.ws.order.fund.FChargeNotificationQueryOrder;
import com.born.fcs.pm.ws.order.fund.FLoanUseApplyFeeOrder;
import com.born.fcs.pm.ws.order.fund.FLoanUseApplyOrder;
import com.born.fcs.pm.ws.order.fund.FLoanUseApplyReceiptOrder;
import com.born.fcs.pm.ws.order.fund.LoanUseApplyFormInfo;
import com.born.fcs.pm.ws.order.fund.LoanUseApplyQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.fund.LoanUseApplyResult;
import com.born.fcs.pm.ws.service.base.CheckBeforeProcessService;
import com.born.fcs.pm.ws.service.common.ProjectService;
import com.born.fcs.pm.ws.service.council.CouncilSummaryService;
import com.born.fcs.pm.ws.service.expireproject.ExpireProjectService;
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
					
					if (project.getStatus() != ProjectStatusEnum.NORMAL) {
						throw ExceptionFactory.newFcsException(
							FcsResultEnum.DO_ACTION_STATUS_ERROR, "当前状态不能放用款");
					}
					
					//承销和诉保没有放用款
					if (ProjectUtil.isUnderwriting(project.getBusiType())
						|| ProjectUtil.isLitigation(project.getBusiType())) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"项目类型不存在放用款");
					}
					
					//先收
					if (project.getChargePhase() == ChargePhaseEnum.BEFORE) {
						
						//查看收费情况
						FChargeNotificationQueryOrder qOrder = new FChargeNotificationQueryOrder();
						qOrder.setFormStatus(FormStatusEnum.APPROVAL.code());
						qOrder.setProjectCode(project.getProjectCode());
						qOrder.setQueryFeeList(true);
						QueryBaseBatchResult<FChargeNotificationResultInfo> chargeList = chargeNotificationService
							.queryChargeNotificationList(qOrder);
						if (chargeList.getTotalCount() == 0) {
							throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
								"该项目需要先收费");
						}
						
						//保费收取情况
						Money guaranteeFee = Money.zero();
						for (FChargeNotificationResultInfo feeInfo : chargeList.getPageList()) {
							if (ListUtil.isNotEmpty(feeInfo.getFeeList())) {
								for (FChargeNotificationFeeInfo fee : feeInfo.getFeeList()) {
									if (fee.getFeeType() == FeeTypeEnum.GUARANTEE_FEE) {
										guaranteeFee.addTo(fee.getChargeAmount());
									}
								}
							}
						}
						
						if (!ProjectUtil.isEntrusted(project.getBusiType())
							&& !guaranteeFee.greaterThan(ZERO_MONEY)) {
							throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
								"该项目需要先收担保费才能放用款");
						}
					}
					
					if (StringUtil.isBlank(project.getContractNo())) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"合同尚未回传，不能放用款");
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
					
					Money amount = order.getAmount();
					
					if (applyType == LoanUseApplyTypeEnum.LOAN
						|| applyType == LoanUseApplyTypeEnum.BOTH) { //放款/放用款
						if (ZERO_MONEY.greaterThan(balanceLoanAmount.subtract(amount).subtract(
							applyingLoanAmount))) {
							throw ExceptionFactory.newFcsException(FcsResultEnum.NO_BALANCE,
								"放款金额不足");
						}
					} else if (applyType == LoanUseApplyTypeEnum.USE) {//用款
						if (ZERO_MONEY.greaterThan(balanceUseAmount.subtract(amount).subtract(
							applyingUseAmount))) {
							throw ExceptionFactory.newFcsException(FcsResultEnum.NO_BALANCE,
								"用款金额不足");
						}
					} else { //放用款
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"未知申请类型");
					}
					
					apply.setAmount(amount);
					apply.setCashDepositAmount(order.getCashDepositAmount());
					apply.setOriginalAmount(originalAmount);
					apply.setApplyingLoanAmount(applyingLoanAmount);
					apply.setApplyingUseAmount(applyingUseAmount);
					apply.setLoanedAmount(amountResult.getLoanedAmount());
					apply.setUsedAmount(amountResult.getUsedAmount());
					apply.setReleasedAmount(amountResult.getReleasedAmount());
					apply.setIsMaximumAmount(amountResult.getIsMaximumAmount().code());
					
					if (isUpdate) {
						FLoanUseApplyDAO.update(apply);
					} else {
						apply.setApplyId(FLoanUseApplyDAO.insert(apply));
					}
					
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
	public FcsBaseResult saveApplyReceipt(final FLoanUseApplyReceiptOrder order) {
		return commonProcess(order, "保存放用款申请单回执", new BeforeProcessInvokeService() {
			
			@SuppressWarnings("deprecation")
			@Override
			public Domain before() {
				
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
				
				FLoanUseApplyReceiptDO receipt = FLoanUseApplyReceiptDAO.findByApplyId(apply
					.getApplyId());
				
				boolean isUpdate = false;
				if (receipt == null) { //新增
					receipt = new FLoanUseApplyReceiptDO();
					BeanCopier.staticCopy(order, receipt, UnBoxingConverter.getInstance());
					receipt.setRawAddTime(getSysdate());
				} else {//修改
					isUpdate = true;
					BeanCopier.staticCopy(order, receipt, UnBoxingConverter.getInstance());
				}
				
				receipt.setApplyId(apply.getApplyId());
				
				//实际保证金如果没填就是申请中的金额
				if (!receipt.getActualDepositAmount().greaterThan(ZERO_MONEY)) {
					receipt.setActualDepositAmount(apply.getCashDepositAmount());
				}
				
				receipt.setProjectCode(apply.getProjectCode());
				//银行融资担保金额处理
				if (project.getBusiType().startsWith("11")) {
					Money actualAmount = Money.zero();
					actualAmount.addTo(order.getLiquidityLoanAmount());
					actualAmount.addTo(order.getCreditLetterAmount());
					actualAmount.addTo(order.getAcceptanceBillAmount());
					actualAmount.addTo(order.getFixedAssetsFinancingAmount());
					if (!actualAmount.greaterThan(ZERO_MONEY)) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.NO_BALANCE,
							LoanUseApplyTypeEnum.getByCode(apply.getApplyType()).message()
									+ "总金额需大于0");
					}
					receipt.setActualAmount(actualAmount);
				}
				
				if (receipt.getActualAmount().greaterThan(apply.getAmount())) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.NO_BALANCE,
						LoanUseApplyTypeEnum.getByCode(apply.getApplyType()).message()
								+ "总金额不能大于申请金额");
				}
				
				if (isUpdate) {
					FLoanUseApplyReceiptDAO.updateByApplyId(receipt);
				} else {
					FLoanUseApplyReceiptDAO.insert(receipt);
				}
				
				FcsPmDomainHolder.get().addAttribute("receipt", receipt);
				
				return null;
			}
		}, null, new AfterProcessInvokeService() {
			
			@Override
			public Domain after(Domain domain) {
				
				//申请单
				FLoanUseApplyDO apply = (FLoanUseApplyDO) FcsPmDomainHolder.get().getAttribute(
					"apply");
				//回执
				FLoanUseApplyReceiptDO receipt = (FLoanUseApplyReceiptDO) FcsPmDomainHolder.get()
					.getAttribute("receipt");
				
				ProjectDO projectDO = projectDAO.findByProjectCodeForUpdate(apply.getProjectCode());
				
				//查询所有放用款单子
				LoanUseApplyQueryOrder order = new LoanUseApplyQueryOrder();
				order.setProjectCode(apply.getProjectCode());
				order.setFormStatus(FormStatusEnum.APPROVAL.code());
				order.setHasReceipt(BooleanEnum.IS);
				order.setPageSize(999);
				QueryBaseBatchResult<LoanUseApplyFormInfo> allForm = searchApplyForm(order);
				//是否存在  已经上传了的回执的单子
				int receiptCount = 0;
				long receiptApplyId = 0;
				if (ListUtil.isNotEmpty(allForm.getPageList())) {
					for (LoanUseApplyFormInfo applyForm : allForm.getPageList()) {
						if (applyForm.getReceiptId() > 0
							&& applyForm.getActualAmount().greaterThan(ZERO_MONEY)) {
							receiptCount++;
							receiptApplyId = applyForm.getApplyId();
						}
					}
				}
				
				//加锁
				synchronized (this) {
					
					//修改项目放用款金额
					if (LoanUseApplyTypeEnum.LOAN.code().equals(apply.getApplyType())) { //放款
						projectDO.getLoanedAmount().addTo(receipt.getActualAmount());
						releaseProjectService.add(projectDO.getProjectCode());
						//放款添加到总的解保金额中
						if (ProjectUtil.isGuarantee(projectDO.getBusiType())) {
							projectDO.setReleasableAmount(projectDO.getReleasableAmount().add(
								receipt.getActualAmount()));
						}
					} else if (LoanUseApplyTypeEnum.USE.code().equals(apply.getApplyType())) { //用款
						projectDO.getUsedAmount().addTo(receipt.getActualAmount());
					} else {
						projectDO.getLoanedAmount().addTo(receipt.getActualAmount());
						projectDO.getUsedAmount().addTo(receipt.getActualAmount());
						releaseProjectService.add(projectDO.getProjectCode());
						//放款添加到总的解保金额中
						if (ProjectUtil.isGuarantee(projectDO.getBusiType())) {
							projectDO.setReleasableAmount(projectDO.getReleasableAmount().add(
								receipt.getActualAmount()));
						}
					}
					
					//担保公司存入银行的保证金
					projectDO.setSelfDepositAmount(projectDO.getSelfDepositAmount().addTo(
						receipt.getActualDepositAmount()));
				}
				
				//第一次放用款上传回执后
				if (receiptCount == 1 && receiptApplyId == apply.getApplyId()) {
					
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
						Date expireDate = calculateExpireDate(receipt.getActualLoanTime(),
							TimeUnitEnum.getByCode(projectDO.getTimeUnit()),
							projectDO.getTimeLimit());
						expireOrder.setExpireDate(expireDate);
						expireOrder.setStatus(ExpireProjectStatusEnum.UNDUE);
						expireProjectService.add(expireOrder);
						//更新项目开始结束时间
						//starttime endtime 为NULL才更新project表时间
						if (projectDO.getStartTime() == null) {
							projectDO.setStartTime(receipt.getActualLoanTime());
						}
						if (projectDO.getEndTime() == null) {
							projectDO.setEndTime(expireDate);
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
	public FLoanUseApplyReceiptInfo queryReceiptByApplyId(long applyId) {
		return convertApplyReceiptDO2Info(FLoanUseApplyReceiptDAO.findByApplyId(applyId));
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
				//发债类项目查询监管机构批复金额
				if (ProjectUtil.isBond(apply.getBusiType())) {
					List<ProjectIssueInformationInfo> iif = projectIssueInformationService
						.findProjectIssueInformationByProjectCode(info.getProjectCode());
					if (ListUtil.isNotEmpty(iif)) {
						info.setApprovalAmount(iif.get(0).getAmount());
					}
				}
				list.add(info);
			}
		}
		
		baseBatchResult.initPageParam(component);
		baseBatchResult.setPageList(list);
		baseBatchResult.setSuccess(true);
		return baseBatchResult;
	}
	
	public QueryBaseBatchResult<ProjectSimpleDetailInfo> selectLoanUseApplyProject(	QueryProjectBase order) {
		QueryBaseBatchResult<ProjectSimpleDetailInfo> baseBatchResult = new QueryBaseBatchResult<ProjectSimpleDetailInfo>();
		ProjectDO queryCondition = new ProjectDO();
		BeanCopier.staticCopy(order, queryCondition);
		long totalSize = extraDAO.selectLoanUseApplyProjectCount(queryCondition,
			order.getLoginUserId(), order.getDeptIdList());
		PageComponent component = new PageComponent(order, totalSize);
		List<ProjectDO> pageList = extraDAO.selectLoanUseApplyProject(queryCondition,
			order.getLoginUserId(), order.getDeptIdList(), component.getFirstRecord(),
			component.getPageSize(), order.getSortCol(), order.getSortOrder());
		
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
						applyingLoanAmount.addTo(applyingForm.getApplyAmount());
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
		Money originalAmount = project.getAmount();//原始金额
		if (ProjectUtil.isBond(project.getBusiType())) { //发债金额以累计发行金额为准
			originalAmount = project.getAccumulatedIssueAmount();
		}
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
		boolean isMaximumAmount = false;
		if (project.getSpId() > 0) {
			FCouncilSummaryProjectInfo projectCs = councilSummaryService
				.queryProjectCsBySpId(project.getSpId());
			isMaximumAmount = (BooleanEnum.IS == projectCs.getIsMaximumAmount());
		}
		
		//可放款金额  = 总金额 - 已放款金额
		Money balanceLoanAmount = originalAmount.subtract(loanedAmount);
		//如果是最高授信额 可放款金额 = 总金额 - 已放款金额  + 已还款金额
		if (isMaximumAmount) {
			balanceLoanAmount.addTo(releasedAmount);
		}
		
		//可用款金额 = 已放款金额 - 已用款金额
		Money balanceUseAmount = loanedAmount.subtract(usedAmount);
		
		result.setApplyingLoanAmount(applyingLoanAmount);
		result.setApplyingUseAmount(applyingUseAmount);
		result.setUsedAmount(usedAmount);
		result.setLoanedAmount(loanedAmount);
		result.setBalanceLoanAmount(balanceLoanAmount);
		result.setBalanceUseAmount(balanceUseAmount);
		result.setOrignalAmount(originalAmount);
		result.setReleasedAmount(releasedAmount);
		result.setIsMaximumAmount(isMaximumAmount ? BooleanEnum.IS : BooleanEnum.NO);
		
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
		
		//查询会议项目批复中 该项目是否为最高额授信
		BooleanEnum isMaximumAmount = apply.getIsMaximumAmount();
		
		//可放款金额  = 总金额 - 已放款金额
		Money balanceLoanAmount = originalAmount.subtract(loanedAmount);
		//如果是最高授信额 可放款金额 = 总金额 - 已放款金额  + 已还款金额
		if (isMaximumAmount == BooleanEnum.IS) {
			balanceLoanAmount.addTo(releasedAmount);
		}
		
		//可用款金额 = 已放款金额 - 已用款金额
		Money balanceUseAmount = loanedAmount.subtract(usedAmount);
		
		result.setApplyingLoanAmount(applyingLoanAmount);
		result.setApplyingUseAmount(applyingUseAmount);
		result.setUsedAmount(usedAmount);
		result.setLoanedAmount(loanedAmount);
		result.setBalanceLoanAmount(balanceLoanAmount);
		result.setBalanceUseAmount(balanceUseAmount);
		result.setOrignalAmount(originalAmount);
		result.setReleasedAmount(releasedAmount);
		result.setIsMaximumAmount(isMaximumAmount);
		
		result.setSuccess(true);
		result.setMessage("查询成功");
		
		return result;
	}
	
	@Override
	protected LoanUseApplyResult createResult() {
		return new LoanUseApplyResult();
	}
}
