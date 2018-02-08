package com.born.fcs.pm.biz.service.fund;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.born.fcs.pm.dal.dataobject.ProjectDO;
import com.born.fcs.pm.ws.enums.*;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.service.common.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseFormAutowiredDomainService;
import com.born.fcs.pm.dal.dataobject.FChargeNotificationDO;
import com.born.fcs.pm.dal.dataobject.FChargeNotificationFeeDO;
import com.born.fcs.pm.dataobject.ChargeNotificationFormDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.FormInfo;
import com.born.fcs.pm.ws.info.fund.FChargeNotificationFeeInfo;
import com.born.fcs.pm.ws.info.fund.FChargeNotificationInfo;
import com.born.fcs.pm.ws.info.fund.FChargeNotificationResultInfo;
import com.born.fcs.pm.ws.order.fund.FChargeNotificationFeeOrder;
import com.born.fcs.pm.ws.order.fund.FChargeNotificationFeeQueryOrder;
import com.born.fcs.pm.ws.order.fund.FChargeNotificationOrder;
import com.born.fcs.pm.ws.order.fund.FChargeNotificationQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.fund.RefundApplicationResult;
import com.born.fcs.pm.ws.service.fund.ChargeNotificationFeeService;
import com.born.fcs.pm.ws.service.fund.ChargeNotificationService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("chargeNotificationService")
public class ChargeNotificationServiceImpl extends BaseFormAutowiredDomainService implements
																					ChargeNotificationService {
	
	@Autowired
	ChargeNotificationFeeService chargeNotificationFeeService;
	@Autowired
	ProjectService projectService;
	
	@Override
	public FormBaseResult saveChargeNotification(final FChargeNotificationOrder order) {
		return commonFormSaveProcess(order, "保存收费通知", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				//校验feeType是存出保证划回 时不能大于project表self_deposit_amount
				//委贷本金收回，委贷本金收回金额综合不能大于在保余额
				if (order.getProjectCode() != null&&order.getChargeBasis()==ChargeBasisEnum.PROJECT) {
					 checkBackAmount(order);
				}
				if (order.getNotificationId() <= 0) {
					FormInfo formInfo = (FormInfo) FcsPmDomainHolder.get().getAttribute("form");
					final Date now = FcsPmDomainHolder.get().getSysDate();
					//保存
					FChargeNotificationDO fChargeNotificationDO = new FChargeNotificationDO();
					BeanCopier.staticCopy(order, fChargeNotificationDO);
					fChargeNotificationDO.setFormId(formInfo.getFormId());
					fChargeNotificationDO.setRawAddTime(now);
					fChargeNotificationDO.setChargeNo(genChargeNo());
					fChargeNotificationDO.setStatus(FormStatusEnum.DRAFT.code());
					fChargeNotificationDO.setChargeBasis(order.getChargeBasis().code());
					long notificationId = fChargeNotificationDAO.insert(fChargeNotificationDO);
					
					List<FChargeNotificationFeeOrder> feeList = order.getFeeList();
					if(ListUtil.isNotEmpty(feeList)){
					for (FChargeNotificationFeeOrder feeOrder : feeList) {
						FChargeNotificationFeeDO chargeNotificationFee = new FChargeNotificationFeeDO();
						BeanCopier.staticCopy(feeOrder, chargeNotificationFee);
						if (StringUtil.isNotBlank(feeOrder.getStartTimeStr())) {
							chargeNotificationFee.setStartTime(DateUtil.parse(feeOrder
								.getStartTimeStr()));
						}
						if (StringUtil.isNotBlank(feeOrder.getEndTimeStr())) {
							chargeNotificationFee.setEndTime(DateUtil.parse(feeOrder
								.getEndTimeStr()));
						}
						if (feeOrder.getFeeType() != null) {
							chargeNotificationFee.setFeeType(feeOrder.getFeeType().code());
							chargeNotificationFee.setFeeTypeDesc(feeOrder.getFeeType().message());
						}
						Money chargeBase = feeOrder.getChargeBase();
						chargeNotificationFee.setChargeBase(chargeBase);
						chargeNotificationFee.setNotificationId(notificationId);
						chargeNotificationFee.setRawAddTime(now);
						fChargeNotificationFeeDAO.insert(chargeNotificationFee);
					}
					}
					FormBaseResult result = (FormBaseResult) FcsPmDomainHolder.get().getAttribute(
						"result");
					result.setKeyId(notificationId);
				} else {
					//更新
					FChargeNotificationDO fChargeNotificationDO = fChargeNotificationDAO
						.findById(order.getNotificationId());
					if (null == fChargeNotificationDO) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"未找到收费通知");
					}
					
					BeanCopier.staticCopy(order, fChargeNotificationDO);
					fChargeNotificationDAO.update(fChargeNotificationDO);
					updateFees(order, fChargeNotificationDO);
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public List<FChargeNotificationInfo> queryAuditingByProjectCode(String projectCode) {
		
		List<FChargeNotificationInfo> result = Lists.newArrayList();
		List<FChargeNotificationDO> notificationDOs = fChargeNotificationDAO
			.findAuditingByProjectCode(projectCode);
		for (FChargeNotificationDO item : notificationDOs) {
			FChargeNotificationInfo info = new FChargeNotificationInfo();
			BeanCopier.staticCopy(item, info);
			info.setChargeBasis(ChargeBasisEnum.getByCode(item.getChargeBasis()));
			List<FChargeNotificationFeeInfo> feeList = chargeNotificationFeeService
				.queryByNotificationId(info.getNotificationId());
			info.setFeeList(feeList);
			result.add(info);
		}
		return result;
	}

	public List<FChargeNotificationInfo> queryApprovalByProjectCode(String projectCode) {
		List<FChargeNotificationInfo> result = Lists.newArrayList();
		List<FChargeNotificationDO> notificationDOs = fChargeNotificationDAO
				.findByProjectCode(projectCode);
		for (FChargeNotificationDO item : notificationDOs) {
			FChargeNotificationInfo info = new FChargeNotificationInfo();
			BeanCopier.staticCopy(item, info);
			info.setChargeBasis(ChargeBasisEnum.getByCode(item.getChargeBasis()));
			List<FChargeNotificationFeeInfo> feeList = chargeNotificationFeeService
					.queryByNotificationId(info.getNotificationId());
			info.setFeeList(feeList);
			result.add(info);
		}
		return result;
	}
	
	private synchronized String genChargeNo() {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String seqName = SysDateSeqNameEnum.CHARGE_NOTIFICATION_CODE_SEQ.code() + year;
		long seq = dateSeqService.getNextSeqNumberWithoutCache(seqName, false);
		String code = StringUtil.leftPad(String.valueOf(seq), 6, "0");
		return code;
	}
	
	private void updateFees(final FChargeNotificationOrder order,
							FChargeNotificationDO fChargeNotificationDO) {
		List<FChargeNotificationFeeOrder> fees = order.getFeeList();
		List<FChargeNotificationFeeDO> delList = fChargeNotificationFeeDAO
			.findByNotificationId(fChargeNotificationDO.getNotificationId());
		
		for (FChargeNotificationFeeOrder feeOrder : fees) {
			FChargeNotificationFeeDO feeDO = new FChargeNotificationFeeDO();
			BeanCopier.staticCopy(feeOrder, feeDO);
			feeDO.setFeeType(feeOrder.getFeeType().code());
			feeDO.setFeeTypeDesc(feeOrder.getFeeType().message());
			feeDO.setNotificationId(fChargeNotificationDO.getNotificationId());
			if (StringUtil.isNotBlank(feeOrder.getStartTimeStr())) {
				feeDO.setStartTime(DateUtil.parse(feeOrder.getStartTimeStr()));
			}
			if (StringUtil.isNotBlank(feeOrder.getEndTimeStr())) {
				feeDO.setEndTime(DateUtil.parse(feeOrder.getEndTimeStr()));
			}
			//收费基数 万元
			Money chargeBase = feeOrder.getChargeBase();
			feeDO.setChargeBase(chargeBase);
			if (feeDO.getId() != 0) {
				fChargeNotificationFeeDAO.update(feeDO);
				Iterator<FChargeNotificationFeeDO> iter = delList.iterator();
				while (iter.hasNext()) {
					FChargeNotificationFeeDO curDO = iter.next();
					if (curDO.getId() == feeOrder.getId()) {
						iter.remove();
					}
				}
			} else {
				feeDO.setNotificationId(fChargeNotificationDO.getNotificationId());
				if (StringUtil.isNotBlank(feeOrder.getStartTimeStr())) {
					feeDO.setStartTime(DateUtil.parse(feeOrder.getStartTimeStr()));
				}
				if (StringUtil.isNotBlank(feeOrder.getEndTimeStr())) {
					feeDO.setEndTime(DateUtil.parse(feeOrder.getEndTimeStr()));
				}
				fChargeNotificationFeeDAO.insert(feeDO);
			}
		}
		
		for (FChargeNotificationFeeDO feeDO : delList) {
			fChargeNotificationFeeDAO.deleteById(feeDO.getId());
		}
	}
	
	@Override
	public FChargeNotificationInfo queryChargeNotificationById(long notificationId) {
		FChargeNotificationDO fChargeNotificationDO = fChargeNotificationDAO
			.findById(notificationId);
		
		FChargeNotificationInfo info = new FChargeNotificationInfo();
		BeanCopier.staticCopy(fChargeNotificationDO, info);
		info.setChargeBasis(ChargeBasisEnum.getByCode(fChargeNotificationDO.getChargeBasis()));
		List<FChargeNotificationFeeInfo> feeList = chargeNotificationFeeService
			.queryByNotificationId(info.getNotificationId());
		info.setFeeList(feeList);
		
		return info;
	}
	
	@Override
	public FcsBaseResult destroyChargeNotificationById(final FChargeNotificationOrder order) {
		return commonProcess(order, "删除收费通知", new BeforeProcessInvokeService() {
			@Override
			public Domain before() {
				if (order.getNotificationId() <= 0) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "未找到收费通知");
				} else {
					fChargeNotificationFeeDAO.deleteByNotificationId(order.getNotificationId());
					fChargeNotificationDAO.deleteById(order.getNotificationId());
				}
				return null;
			}
		}, null, null);
	}
	
	@Override
	public FChargeNotificationInfo queryChargeNotificationByFormId(long formId) {
		FChargeNotificationDO fChargeNotificationDO = fChargeNotificationDAO.findByFormId(formId);
		
		FChargeNotificationInfo info = new FChargeNotificationInfo();
		BeanCopier.staticCopy(fChargeNotificationDO, info);
		info.setChargeBasis(ChargeBasisEnum.getByCode(fChargeNotificationDO.getChargeBasis()));
		List<FChargeNotificationFeeInfo> feeList = chargeNotificationFeeService
			.queryByNotificationId(info.getNotificationId());
		info.setFeeList(feeList);
		
		return info;
	}
	
	@Override
	public List<FChargeNotificationInfo> queryChargeNotificationByProjectCode(String projectCode) {
		
		List<FChargeNotificationInfo> result = Lists.newArrayList();
		List<FChargeNotificationDO> list = fChargeNotificationDAO.findByProjectCode(projectCode);
		for (FChargeNotificationDO item : list) {
			FChargeNotificationInfo info = new FChargeNotificationInfo();
			BeanCopier.staticCopy(item, info);
			info.setChargeBasis(ChargeBasisEnum.getByCode(item.getChargeBasis()));
			List<FChargeNotificationFeeInfo> feeList = chargeNotificationFeeService
				.queryByNotificationId(info.getNotificationId());
			info.setFeeList(feeList);
			result.add(info);
		}
		return result;
	}
	
	@Override
	public QueryBaseBatchResult<FChargeNotificationInfo> queryChargeNotification(	FChargeNotificationQueryOrder chargeNotificationQueryOrder) {
		QueryBaseBatchResult<FChargeNotificationInfo> batchResult = new QueryBaseBatchResult<FChargeNotificationInfo>();
		try {
			FChargeNotificationDO fChargeNotificationDO = new FChargeNotificationDO();
			BeanCopier.staticCopy(chargeNotificationQueryOrder, fChargeNotificationDO);
			
			long totalCount = fChargeNotificationDAO.findByConditionCount(fChargeNotificationDO);
			PageComponent component = new PageComponent(chargeNotificationQueryOrder, totalCount);
			
			List<FChargeNotificationDO> list = fChargeNotificationDAO.findByCondition(
				fChargeNotificationDO, component.getFirstRecord(), component.getPageSize());
			
			List<FChargeNotificationInfo> pageList = new ArrayList<FChargeNotificationInfo>(
				list.size());
			for (FChargeNotificationDO item : list) {
				FChargeNotificationInfo info = new FChargeNotificationInfo();
				BeanCopier.staticCopy(item, info);
				info.setChargeBasis(ChargeBasisEnum.getByCode(item.getChargeBasis()));
				if (chargeNotificationQueryOrder.isQueryFeeList()) {
					info.setFeeList(chargeNotificationFeeService.queryByNotificationId(item
						.getNotificationId()));
				}
				pageList.add(info);
			}
			batchResult.setSuccess(true);
			batchResult.setPageList(pageList);
			batchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询收费通知失败" + e.getMessage(), e);
			batchResult.setSuccess(false);
			batchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return batchResult;
	}
	
	@Override
	public QueryBaseBatchResult<FChargeNotificationResultInfo> queryChargeNotificationList(	FChargeNotificationQueryOrder chargeNotificationQueryOrder) {
		QueryBaseBatchResult<FChargeNotificationResultInfo> batchResult = new QueryBaseBatchResult<FChargeNotificationResultInfo>();
		try {
			ChargeNotificationFormDO chargeNotificationFormDO = new ChargeNotificationFormDO();
			BeanCopier.staticCopy(chargeNotificationQueryOrder, chargeNotificationFormDO);
			if(chargeNotificationQueryOrder.getChargeBasis()!=null){
				chargeNotificationFormDO.setChargeBasis(chargeNotificationQueryOrder.getChargeBasis().code());
			}
			chargeNotificationFormDO.setDeptIdList(chargeNotificationQueryOrder.getDeptIdList());
			long totalCount = extraDAO.searchChargeNotificationCount(chargeNotificationFormDO);
			PageComponent component = new PageComponent(chargeNotificationQueryOrder, totalCount);
			
			List<ChargeNotificationFormDO> list = extraDAO.searchChargeNotificationList(
				chargeNotificationFormDO, component.getFirstRecord(), component.getPageSize(),
				chargeNotificationQueryOrder.getSortCol(),
				chargeNotificationQueryOrder.getSortOrder());
			
			List<FChargeNotificationResultInfo> pageList = new ArrayList<FChargeNotificationResultInfo>(
				list.size());
			for (ChargeNotificationFormDO item : list) {
				FChargeNotificationResultInfo info = new FChargeNotificationResultInfo();
				BeanCopier.staticCopy(item, info);
				info.setChargeBasis(ChargeBasisEnum.getByCode(item.getChargeBasis()));
				info.setFormStatus(FormStatusEnum.getByCode(item.getFormStatus()));
				info.setProjectStatus(ProjectStatusEnum.getByCode(item.getProjectStatus()));
				if (chargeNotificationQueryOrder.isQueryFeeList()) {
					info.setFeeList(chargeNotificationFeeService.queryByNotificationId(item
						.getNotificationId()));
				}
				pageList.add(info);
			}
			batchResult.setSuccess(true);
			batchResult.setPageList(pageList);
			batchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询收费通知失败 : {}", e);
			batchResult.setSuccess(false);
			batchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return batchResult;
	}
	
	@Override
	public RefundApplicationResult queryChargeTotalAmount(String projectCode, FeeTypeEnum feeType) {
		RefundApplicationResult result = new RefundApplicationResult();
		try {
			Money total = new Money(0L);
			FChargeNotificationQueryOrder queryOrder = new FChargeNotificationQueryOrder();
			queryOrder.setProjectCode(projectCode);
			queryOrder.setFormStatus(FormStatusEnum.APPROVAL.code());
			QueryBaseBatchResult<FChargeNotificationResultInfo> batchResult = this.queryChargeNotificationList(queryOrder);
			if (null != batchResult && ListUtil.isNotEmpty(batchResult.getPageList())) {
				for (FChargeNotificationResultInfo info : batchResult.getPageList()) {
					FChargeNotificationFeeQueryOrder feeQueryOrder = new FChargeNotificationFeeQueryOrder();
					feeQueryOrder.setFeeType(feeType.code());
					feeQueryOrder.setNotificationId(info.getNotificationId());
					QueryBaseBatchResult<FChargeNotificationFeeInfo> feeBatchResult = chargeNotificationFeeService
						.queryChargeNotification(feeQueryOrder);
					if (null != feeBatchResult && ListUtil.isNotEmpty(feeBatchResult.getPageList())) {
						for (FChargeNotificationFeeInfo fee : feeBatchResult.getPageList()) {
							total.addTo(fee.getChargeAmount());
						}
					}
				}
			}
			result.setOther(total);
		} catch (Exception e) {
			logger.error("查询收费失败 : {}", e);
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
			result.setMessage("查询收费失败：" + e.getMessage());
		}
		
		return result;
	}

	public void checkBackAmount(FChargeNotificationOrder order) {
		List<FChargeNotificationInfo> noticeAuditingInfos = queryAuditingByProjectCode(order.getProjectCode());//审核中
		List<FChargeNotificationInfo> noticeApprovalInfos = queryApprovalByProjectCode(order.getProjectCode());//审核通过
		Money totalBackMoney = new Money(0, 0);//存出保证金划回
		Money entrustedLoan=ZERO_MONEY; //委贷本金
		if (noticeAuditingInfos != null) {
			for (FChargeNotificationInfo noticeInfo : noticeAuditingInfos) {//审核中的单子 存出保证金总计
				List<FChargeNotificationFeeInfo> feeInfos = noticeInfo.getFeeList();
				if (feeInfos != null) {
					for (FChargeNotificationFeeInfo feeInfo : feeInfos) {
						if (feeInfo.getFeeType().equals(FeeTypeEnum.REFUNDABLE_DEPOSITS_DRAW_BACK)) {
							totalBackMoney = totalBackMoney.add(feeInfo.getChargeAmount());
						}
						if (feeInfo.getFeeType().equals(FeeTypeEnum.ENTRUSTED_LOAN_PRINCIPAL_WITHDRAWAL)) {
							entrustedLoan = entrustedLoan.add(feeInfo.getChargeAmount());
						}
					}
				}
			}
		}
		if (noticeApprovalInfos != null) {
			for (FChargeNotificationInfo noticeInfo : noticeApprovalInfos) {
				List<FChargeNotificationFeeInfo> feeInfos = noticeInfo.getFeeList();
				if (feeInfos != null) {
					for (FChargeNotificationFeeInfo feeInfo : feeInfos) {
						if (feeInfo.getFeeType().equals(FeeTypeEnum.ENTRUSTED_LOAN_PRINCIPAL_WITHDRAWAL)) {
							entrustedLoan = entrustedLoan.add(feeInfo.getChargeAmount());
						}
					}
				}
			}
		}
		List<FChargeNotificationFeeOrder> feeOrders = order.getFeeList();
		if (feeOrders != null) {
			for (FChargeNotificationFeeOrder feeOrder : feeOrders) {//加上本张单子的金额
				if (feeOrder.getFeeType() != null) {
					if (feeOrder.getFeeType().equals(FeeTypeEnum.REFUNDABLE_DEPOSITS_DRAW_BACK)) {
						totalBackMoney = totalBackMoney.add(feeOrder.getChargeAmount());
					}
					if (feeOrder.getFeeType().equals(FeeTypeEnum.ENTRUSTED_LOAN_PRINCIPAL_WITHDRAWAL)) {
						entrustedLoan = entrustedLoan.add(feeOrder.getChargeAmount());
					}
				}
			}
		}
		ProjectInfo projectInfo = projectService.queryByCode(order.getProjectCode(), false);
		//校验
		if (projectInfo.getSelfDepositAmount().compareTo(totalBackMoney) == -1) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.EXECUTE_FAILURE, "存出保证金划回金额总和不能超过该项目已划付的存出保证金金额");
		}
		if (projectInfo.getLoanedAmount().compareTo(entrustedLoan) == -1) {
			throw ExceptionFactory.newFcsException(FcsResultEnum.EXECUTE_FAILURE, "委贷本金收回金额总和不能大于已放款金额");
		}
	}
	
}
