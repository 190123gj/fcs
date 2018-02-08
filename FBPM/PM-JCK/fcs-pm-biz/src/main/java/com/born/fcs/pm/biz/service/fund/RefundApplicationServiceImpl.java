package com.born.fcs.pm.biz.service.fund;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.convert.DoConvert;
import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.pm.dal.dataobject.FRefundApplicationDO;
import com.born.fcs.pm.dal.dataobject.FRefundApplicationFeeDO;
import com.born.fcs.pm.dal.dataobject.ProjectDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.FeeTypeEnum;
import com.born.fcs.pm.ws.enums.FormCodeEnum;
import com.born.fcs.pm.ws.enums.FormStatusEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.fund.FChargeNotificationFeeInfo;
import com.born.fcs.pm.ws.info.fund.FChargeNotificationResultInfo;
import com.born.fcs.pm.ws.info.fund.FRefundApplicationFeeInfo;
import com.born.fcs.pm.ws.info.fund.FRefundApplicationInfo;
import com.born.fcs.pm.ws.order.common.ProjectQueryOrder;
import com.born.fcs.pm.ws.order.fund.FChargeNotificationQueryOrder;
import com.born.fcs.pm.ws.order.fund.FRefundApplicationFeeOrder;
import com.born.fcs.pm.ws.order.fund.FRefundApplicationOrder;
import com.born.fcs.pm.ws.order.fund.FRefundApplicationQueryOrder;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.fund.RefundApplicationResult;
import com.born.fcs.pm.ws.service.common.FormService;
import com.born.fcs.pm.ws.service.fund.ChargeNotificationFeeService;
import com.born.fcs.pm.ws.service.fund.ChargeNotificationService;
import com.born.fcs.pm.ws.service.fund.RefundApplicationService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.PageUtil;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("refundApplicationService")
public class RefundApplicationServiceImpl extends BaseFormAutowiredDomainService implements
																				RefundApplicationService {
	@Autowired
	ChargeNotificationService chargeNotificationService;
	@Autowired
	ChargeNotificationFeeService chargeNotificationFeeService;
	@Autowired
	FormService formService;
	
	private FRefundApplicationInfo convertDO2Info(FRefundApplicationDO DO) {
		if (DO == null)
			return null;
		FRefundApplicationInfo info = new FRefundApplicationInfo();
		BeanCopier.staticCopy(DO, info);
		return info;
	}
	
	private FRefundApplicationFeeInfo convertFRefundApplicationFeeDO2Info(FRefundApplicationFeeDO DO) {
		if (DO == null)
			return null;
		FRefundApplicationFeeInfo info = new FRefundApplicationFeeInfo();
		BeanCopier.staticCopy(DO, info);
		info.setRefundReason(FeeTypeEnum.getByCode(DO.getRefundReason()));
		return info;
	}
	
	@Override
	public FRefundApplicationInfo findById(long id) {
		FRefundApplicationDO DO = FRefundApplicationDAO.findById(id);
		FRefundApplicationInfo info = new FRefundApplicationInfo();
		BeanCopier.staticCopy(DO, info);
		return info;
	}
	
	@Override
	public FRefundApplicationInfo findRefundApplicationByFormId(long formId) {
		FRefundApplicationDO DO = FRefundApplicationDAO.findByFormId(formId);
		FRefundApplicationInfo info = convertDO2Info(DO);
		return info;
	}
	
	@Override
	public FormBaseResult saveRefundApplication(final FRefundApplicationOrder order) {
		return commonFormSaveProcess(order, "退费申请单", new BeforeProcessInvokeService() {
			@Override
			public Domain before() {
				if (order.getRefundId() == null || order.getRefundId() <= 0) {
					FormInfo formInfo = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
					final Date now = FcsPmDomainHolder.get().getSysDate();
					//保存
					FRefundApplicationDO DO = new FRefundApplicationDO();
					BeanCopier.staticCopy(order, DO);
					DO.setFormId(formInfo.getFormId());
					DO.setRawAddTime(now);
					//					fCapitalAppropriationApplyDO.setAmount(new Money(order.getAmount()));
					long id = FRefundApplicationDAO.insert(DO);//主表id
					List<FRefundApplicationFeeOrder> feeOrders = order.getFeeOrders();
					for (FRefundApplicationFeeOrder feeOrder : feeOrders) {
						FRefundApplicationFeeDO feeDO = new FRefundApplicationFeeDO();
						BeanCopier.staticCopy(feeOrder, feeDO);
						feeDO.setRefundId(id);//设置副表依赖主表id
						feeDO.setRawAddTime(now);
						feeDO.setRefundAmount(new Money(feeOrder.getRefundAmount() == null ? 0.00
							: feeOrder.getRefundAmount()));
						FRefundApplicationFeeDAO.insert(feeDO);
					}
					FormBaseResult result = (FormBaseResult) FcsPmDomainHolder.get().getAttribute(
						"result");
					result.setKeyId(id);
				} else {
					final Date now = FcsPmDomainHolder.get().getSysDate();
					//更新
					Long id = order.getRefundId();
					FRefundApplicationDO refundDO = FRefundApplicationDAO.findById(id);
					if (null == refundDO) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"未找到退费记录");
					}
					BeanCopier.staticCopy(order, refundDO);
					FRefundApplicationDAO.update(refundDO);//更新主表信息
					if (order.getFeeOrders() != null) {
						//删除先
						FRefundApplicationFeeDAO.deleteByRefundId(refundDO.getRefundId());
					}
					//更新副表(直接插入新数据)  退费申请-退费信息
					for (FRefundApplicationFeeOrder feeOrder : order.getFeeOrders()) {
						FRefundApplicationFeeDO feeDO = new FRefundApplicationFeeDO();
						feeDO.setRefundId(id);
						feeDO.setRawAddTime(now);
						feeDO.setRefundAmount(new Money(feeOrder.getRefundAmount() == null ? 0.00
							: feeOrder.getRefundAmount()));
						feeDO.setRefundReason(feeOrder.getRefundReason());
						feeDO.setRemark(feeOrder.getRemark());
						FRefundApplicationFeeDAO.insert(feeDO);
						
					}
					
				}
				FormBaseResult result = (FormBaseResult) FcsPmDomainHolder.get().getAttribute(
					"result");
				
				return null;
			}
		}, null, null);
	}
	
	@Override
	public QueryBaseBatchResult<FRefundApplicationInfo> query(FRefundApplicationQueryOrder order) {
		QueryBaseBatchResult<FRefundApplicationInfo> baseBatchResult = new QueryBaseBatchResult<FRefundApplicationInfo>();
		try {
			FRefundApplicationDO DO = new FRefundApplicationDO();
			BeanCopier.staticCopy(order, DO);
			
			long totalCount = extraDAO.searchRefundApplicationCount(DO, order.getSubmitTimeStart(),
				order.getSubmitTimeEnd(), order.getDeptIdList());
			PageComponent component = new PageComponent(order, totalCount);
			
			List<FRefundApplicationDO> pageList = extraDAO.searchRefundApplicationList(DO,
				component.getFirstRecord(), component.getPageSize(), order.getSubmitTimeStart(),
				order.getSubmitTimeEnd(), order.getDeptIdList(), order.getSortCol(),
				order.getSortOrder());
			
			List<FRefundApplicationInfo> list = baseBatchResult.getPageList();
			if (totalCount > 0) {
				for (FRefundApplicationDO sf : pageList) {
					FRefundApplicationInfo info = new FRefundApplicationInfo();
					BeanCopier.staticCopy(sf, info);
					info.setFormCode(FormCodeEnum.getByCode(sf.getFormCode()));
					info.setFormStatus(FormStatusEnum.getByCode(sf.getFormStatus()));
					list.add(info);
				}
			}
			baseBatchResult.setSuccess(true);
			baseBatchResult.setPageList(list);
			baseBatchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询退费列表失败" + e.getMessage(), e);
			baseBatchResult.setSuccess(false);
			baseBatchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return baseBatchResult;
	}
	
	@Override
	public List<FRefundApplicationInfo> findByProjectCode(String projectCode) {
		List<FRefundApplicationInfo> listRefundInfo = new ArrayList<FRefundApplicationInfo>();
		List<FRefundApplicationDO> listRefundDO = FRefundApplicationDAO
			.findByProjectCode(projectCode);
		for (FRefundApplicationDO DO : listRefundDO) {
			FRefundApplicationInfo info = new FRefundApplicationInfo();
			info = convertDO2Info(DO);
			listRefundInfo.add(info);
		}
		return listRefundInfo;
	}
	
	@Override
	public List<FRefundApplicationFeeInfo> findByRefundId(long refundId) {
		List<FRefundApplicationFeeInfo> listFeeInfo = new ArrayList<FRefundApplicationFeeInfo>();
		List<FRefundApplicationFeeDO> listFeeDO = FRefundApplicationFeeDAO.findByRefundId(refundId);
		for (FRefundApplicationFeeDO FeeDO : listFeeDO) {
			FRefundApplicationFeeInfo FeeInfo = convertFRefundApplicationFeeDO2Info(FeeDO);
			listFeeInfo.add(FeeInfo);
		}
		return listFeeInfo;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public RefundApplicationResult findAmountByChargeNotification(String projectCode,
																	long refundId, Boolean isEdit) {
		RefundApplicationResult result = createResult();
		FChargeNotificationQueryOrder order = new FChargeNotificationQueryOrder();
		order.setProjectCode(projectCode);
		order.setFormStatus(FormStatusEnum.APPROVAL.code());
		QueryBaseBatchResult<FChargeNotificationResultInfo> chargeResult = chargeNotificationService
			.queryChargeNotificationList(order);
		List<FChargeNotificationResultInfo> listInfo = null;
		if (chargeResult != null) {
			listInfo = chargeResult.getPageList();
		}
		if (listInfo != null) {
			//其它费用
			Money other = Money.zero();
			//担保费
			Money guaranteeFee = Money.zero();
			//项目评审费
			Money projectReviewFee = Money.zero();
			//追偿收入
			Money recoveryIncome = Money.zero();
			//代偿款本金收回
			Money compensatoryPrincipalWithdrawal = Money.zero();
			//代偿款利息收回
			Money compensatoryInterestWithdrawal = Money.zero();
			//委贷本金收回
			Money entrustedLoanPrincipalWithdrawal = Money.zero();
			//委贷利息收回
			Money entrustedLoanInterestWithdrawal = Money.zero();
			//顾问费
			Money consultFee = Money.zero();
			//承销收入
			Money consignmentInwardIncome = Money.zero();
			//存出保证金划回
			Money refundableDepositsDrawBack = Money.zero();
			//存入保证金
			Money guaranteeDeposit = Money.zero();
			//资产转让
			Money assetsTransfer = Money.zero();
			//代收费(合作机构费用、律所费用、会所费用)
			Money proxyCharging = Money.zero();
			
			if (listInfo != null) {
				for (FChargeNotificationResultInfo info : listInfo) {
					List<FChargeNotificationFeeInfo> listFeeInfo = chargeNotificationFeeService
						.queryByNotificationId(info.getNotificationId());
					for (FChargeNotificationFeeInfo feeInfo : listFeeInfo) {
						//其它收费
						if (feeInfo.getFeeType() == FeeTypeEnum.OTHER) {
							other = other.add(feeInfo.getChargeAmount());
						}
						//担保费
						if (feeInfo.getFeeType() == FeeTypeEnum.GUARANTEE_FEE) {
							guaranteeFee = guaranteeFee.add(feeInfo.getChargeAmount());
						}
						//项目评审费
						if (feeInfo.getFeeType() == FeeTypeEnum.PROJECT_REVIEW_FEE) {
							projectReviewFee = projectReviewFee.add(feeInfo.getChargeAmount());
						}
						//追偿收入
						if (feeInfo.getFeeType() == FeeTypeEnum.RECOVERY_INCOME) {
							recoveryIncome = recoveryIncome.add(feeInfo.getChargeAmount());
						}
						//代偿款本金收回
						if (feeInfo.getFeeType() == FeeTypeEnum.COMPENSATORY_PRINCIPAL_WITHDRAWAL) {
							compensatoryPrincipalWithdrawal = compensatoryPrincipalWithdrawal
								.add(feeInfo.getChargeAmount());
						}
						//代偿款利息收回
						if (feeInfo.getFeeType() == FeeTypeEnum.COMPENSATORY_INTEREST_WITHDRAWAL) {
							compensatoryInterestWithdrawal = compensatoryInterestWithdrawal
								.add(feeInfo.getChargeAmount());
						}
						//委贷本金收回
						if (feeInfo.getFeeType() == FeeTypeEnum.ENTRUSTED_LOAN_PRINCIPAL_WITHDRAWAL) {
							entrustedLoanPrincipalWithdrawal = entrustedLoanPrincipalWithdrawal
								.add(feeInfo.getChargeAmount());
						}
						//委贷利息收回
						if (feeInfo.getFeeType() == FeeTypeEnum.ENTRUSTED_LOAN_INTEREST_WITHDRAWAL) {
							entrustedLoanInterestWithdrawal = entrustedLoanInterestWithdrawal
								.add(feeInfo.getChargeAmount());
						}
						//顾问费
						if (feeInfo.getFeeType() == FeeTypeEnum.CONSULT_FEE) {
							consultFee = consultFee.add(feeInfo.getChargeAmount());
						}
						//承销收入
						if (feeInfo.getFeeType() == FeeTypeEnum.CONSIGNMENT_INWARD_INCOME) {
							consignmentInwardIncome = consignmentInwardIncome.add(feeInfo
								.getChargeAmount());
						}
						//存出保证金划回
						if (feeInfo.getFeeType() == FeeTypeEnum.REFUNDABLE_DEPOSITS_DRAW_BACK) {
							refundableDepositsDrawBack = refundableDepositsDrawBack.add(feeInfo
								.getChargeAmount());
						}
						//存入保证金
						if (feeInfo.getFeeType() == FeeTypeEnum.GUARANTEE_DEPOSIT) {
							guaranteeDeposit = guaranteeDeposit.add(feeInfo.getChargeAmount());
						}
						//资产转让
						if (feeInfo.getFeeType() == FeeTypeEnum.ASSETS_TRANSFER) {
							assetsTransfer = assetsTransfer.add(feeInfo.getChargeAmount());
						}
						//代收费(合作机构费用、律所费用、会所费用)
						if (feeInfo.getFeeType() == FeeTypeEnum.PROXY_CHARGING) {
							proxyCharging = proxyCharging.add(feeInfo.getChargeAmount());
						}
					}
				}
			}
			FRefundApplicationQueryOrder refundOrder = new FRefundApplicationQueryOrder();
			refundOrder.setProjectCode(projectCode);
			//			refundOrder.setFormStatus(FormStatusEnum.APPROVAL.code());
			QueryBaseBatchResult<FRefundApplicationInfo> refundResult = query(refundOrder);
			List<FRefundApplicationInfo> listRefundInfo = null;
			if (refundResult != null) {
				listRefundInfo = refundResult.getPageList();
			}
			//			List<FRefundApplicationInfo> listRefundInfo = findByProjectCode(projectCode);
			if (isEdit) {//编辑时 移除正在编辑的对象
				for (Iterator iterator = listRefundInfo.iterator(); iterator.hasNext();) {
					FRefundApplicationInfo info = (FRefundApplicationInfo) iterator.next();
					if (info.getRefundId() == refundId) {
						iterator.remove();
					}
					FormInfo form1 = formService.findByFormId(info.getFormId());
					if (form1.getStatus().code().equals("DENY")) {//移除已经审核不通过的申请单
						iterator.remove();
					}
				}
			}
			if (listRefundInfo != null) {
				for (FRefundApplicationInfo info : listRefundInfo) {
					List<FRefundApplicationFeeInfo> listFeeInfo = findByRefundId(info.getRefundId());
					for (FRefundApplicationFeeInfo feeInfo : listFeeInfo) {
						
						//其它收费
						if (feeInfo.getRefundReason() == FeeTypeEnum.OTHER) {
							other = other.subtract(feeInfo.getRefundAmount());
						}
						//担保费
						if (feeInfo.getRefundReason() == FeeTypeEnum.GUARANTEE_FEE) {
							guaranteeFee = guaranteeFee.subtract(feeInfo.getRefundAmount());
						}
						//项目评审费
						if (feeInfo.getRefundReason() == FeeTypeEnum.PROJECT_REVIEW_FEE) {
							projectReviewFee = projectReviewFee.subtract(feeInfo.getRefundAmount());
						}
						//追偿收入
						if (feeInfo.getRefundReason() == FeeTypeEnum.RECOVERY_INCOME) {
							recoveryIncome = recoveryIncome.subtract(feeInfo.getRefundAmount());
						}
						//代偿款本金收回
						if (feeInfo.getRefundReason() == FeeTypeEnum.COMPENSATORY_PRINCIPAL_WITHDRAWAL) {
							compensatoryPrincipalWithdrawal = compensatoryPrincipalWithdrawal
								.subtract(feeInfo.getRefundAmount());
						}
						//代偿款利息收回
						if (feeInfo.getRefundReason() == FeeTypeEnum.COMPENSATORY_INTEREST_WITHDRAWAL) {
							compensatoryInterestWithdrawal = compensatoryInterestWithdrawal
								.subtract(feeInfo.getRefundAmount());
						}
						//委贷本金收回
						if (feeInfo.getRefundReason() == FeeTypeEnum.ENTRUSTED_LOAN_PRINCIPAL_WITHDRAWAL) {
							entrustedLoanPrincipalWithdrawal = entrustedLoanPrincipalWithdrawal
								.subtract(feeInfo.getRefundAmount());
						}
						//委贷利息收回
						if (feeInfo.getRefundReason() == FeeTypeEnum.ENTRUSTED_LOAN_INTEREST_WITHDRAWAL) {
							entrustedLoanInterestWithdrawal = entrustedLoanInterestWithdrawal
								.subtract(feeInfo.getRefundAmount());
						}
						//顾问费
						if (feeInfo.getRefundReason() == FeeTypeEnum.CONSULT_FEE) {
							consultFee = consultFee.subtract(feeInfo.getRefundAmount());
						}
						//承销收入
						if (feeInfo.getRefundReason() == FeeTypeEnum.CONSIGNMENT_INWARD_INCOME) {
							consignmentInwardIncome = consignmentInwardIncome.subtract(feeInfo
								.getRefundAmount());
						}
						//存出保证金划回
						if (feeInfo.getRefundReason() == FeeTypeEnum.REFUNDABLE_DEPOSITS_DRAW_BACK) {
							refundableDepositsDrawBack = refundableDepositsDrawBack
								.subtract(feeInfo.getRefundAmount());
						}
						//存入保证金
						if (feeInfo.getRefundReason() == FeeTypeEnum.GUARANTEE_DEPOSIT) {
							guaranteeDeposit = guaranteeDeposit.subtract(feeInfo.getRefundAmount());
						}
						//资产转让
						if (feeInfo.getRefundReason() == FeeTypeEnum.ASSETS_TRANSFER) {
							assetsTransfer = assetsTransfer.subtract(feeInfo.getRefundAmount());
						}
						//代收费(合作机构费用、律所费用、会所费用)
						if (feeInfo.getRefundReason() == FeeTypeEnum.PROXY_CHARGING) {
							proxyCharging = proxyCharging.subtract(feeInfo.getRefundAmount());
						}
					}
				}
			}
			
			result.setOther(other);
			result.setAssetsTransfer(assetsTransfer);
			result.setCompensatoryInterestWithdrawal(compensatoryInterestWithdrawal);
			result.setCompensatoryPrincipalWithdrawal(compensatoryPrincipalWithdrawal);
			result.setConsignmentInwardIncome(consignmentInwardIncome);
			result.setConsultFee(consultFee);
			result.setEntrustedLoanInterestWithdrawal(entrustedLoanInterestWithdrawal);
			result.setEntrustedLoanPrincipalWithdrawal(entrustedLoanPrincipalWithdrawal);
			result.setGuaranteeDeposit(guaranteeDeposit);
			result.setGuaranteeFee(guaranteeFee);
			result.setProjectReviewFee(projectReviewFee);
			result.setProxyCharging(proxyCharging);
			result.setRecoveryIncome(recoveryIncome);
			result.setRefundableDepositsDrawBack(refundableDepositsDrawBack);
		}
		
		result.setSuccess(true);
		result.setMessage("查询成功");
		return result;
	}
	
	@Override
	protected RefundApplicationResult createResult() {
		return new RefundApplicationResult();
	}
	
	@Override
	public QueryBaseBatchResult<ProjectInfo> queryRefundApplicationByCharge(ProjectQueryOrder order) {
		QueryBaseBatchResult<ProjectInfo> baseBatchResult = new QueryBaseBatchResult<ProjectInfo>();
		try {
			ProjectDO projectDO = new ProjectDO();
			
			if (order != null)
				BeanCopier.staticCopy(order, projectDO);
			
			if (new Long(order.getProjectId()) != null)
				projectDO.setProjectId(order.getProjectId());
			
			BeanCopier.staticCopy(order, projectDO);
			long totalCount = extraDAO.searchRefundApplicationSelectCount(projectDO);
			PageComponent component = new PageComponent(order, totalCount);
			
			List<ProjectDO> pageList = extraDAO.searchRefundApplicationSelectList(projectDO,
				component.getFirstRecord(), component.getPageSize(), order.getSortCol(),
				order.getSortOrder());
			
			List<ProjectInfo> list = baseBatchResult.getPageList();
			if (totalCount > 0) {
				for (ProjectDO sf : pageList) {
					ProjectInfo project = DoConvert.convertProjectDO2Info(sf);
					list.add(project);
				}
			}
			baseBatchResult.setSuccess(true);
			baseBatchResult.setPageList(list);
			baseBatchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询可退费的项目失败" + e.getMessage(), e);
			baseBatchResult.setSuccess(false);
			baseBatchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return baseBatchResult;
	}

	@Override
	public Money findRefundApplicationByProjectCode(String projectCode) {
		Money refundAmount = Money.zero();
		QueryBaseBatchResult<FRefundApplicationInfo> baseBatchResult = new QueryBaseBatchResult<FRefundApplicationInfo>();
		FRefundApplicationQueryOrder queryOrder = new FRefundApplicationQueryOrder();
		queryOrder.setProjectCode(projectCode);
		queryOrder.setFormStatus(FormStatusEnum.APPROVAL.code());
		baseBatchResult = query(queryOrder);
		List<FRefundApplicationInfo> infoList = baseBatchResult.getPageList();
		for (FRefundApplicationInfo fRefundApplicationInfo : infoList) {
			refundAmount = refundAmount.add(fRefundApplicationInfo.getRefundAmount());
		}
		return refundAmount;
	}
}
