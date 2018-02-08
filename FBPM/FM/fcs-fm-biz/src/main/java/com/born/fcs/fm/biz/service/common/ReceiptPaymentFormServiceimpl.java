package com.born.fcs.fm.biz.service.common;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.fm.biz.exception.ExceptionFactory;
import com.born.fcs.fm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.fm.dal.dataobject.ReceiptPaymentFormDO;
import com.born.fcs.fm.dal.dataobject.ReceiptPaymentFormFeeDO;
import com.born.fcs.fm.domain.context.FcsFmDomainHolder;
import com.born.fcs.fm.ws.enums.FundDirectionEnum;
import com.born.fcs.fm.ws.enums.ReceiptPaymentFormStatusEnum;
import com.born.fcs.fm.ws.enums.SourceFormEnum;
import com.born.fcs.fm.ws.enums.SubjectCostTypeEnum;
import com.born.fcs.fm.ws.info.common.ReceiptPaymentFormFeeInfo;
import com.born.fcs.fm.ws.info.common.ReceiptPaymentFormInfo;
import com.born.fcs.fm.ws.order.common.ReceiptPaymentFormFeeOrder;
import com.born.fcs.fm.ws.order.common.ReceiptPaymentFormOrder;
import com.born.fcs.fm.ws.order.common.ReceiptPaymentFormQueryOrder;
import com.born.fcs.fm.ws.order.common.UpdateReceiptPaymentFormStatusOrder;
import com.born.fcs.fm.ws.service.common.ReceiptPaymentFormService;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.SystemEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.service.base.BeforeProcessInvokeService;

@Service("receiptPaymentFormService")
public class ReceiptPaymentFormServiceimpl extends BaseAutowiredDomainService implements
																				ReceiptPaymentFormService {
	
	@Override
	public FcsBaseResult add(final ReceiptPaymentFormOrder order) {
		return commonProcess(order, "新增收款单/付款单数据源", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				
				if (!StringUtil.equals(order.getIsSimple(), "IS")) {
					order.setIsSimple("NO");
				}
				
				Date now = FcsFmDomainHolder.get().getSysDate();
				
				ReceiptPaymentFormDO rpForm = receiptPaymentFormDAO.findBySourceFormId(order
					.getSourceForm().code(), order.getSourceFormId());
				if (rpForm != null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.DO_ACTION_STATUS_ERROR,
						"数据已存在");
				}
				//新增数据
				rpForm = new ReceiptPaymentFormDO();
				BeanCopier.staticCopy(order, rpForm);
				rpForm.setSourceForm(order.getSourceForm().code());
				rpForm.setFundDirection("RECEIPT".equals(order.getSourceForm().getType()) ? FundDirectionEnum.IN
					.code() : FundDirectionEnum.OUT.code());
				rpForm.setRawAddTime(now);
				rpForm.setSourceFormSys(order.getSourceFormSys().code());
				rpForm.setStatus(ReceiptPaymentFormStatusEnum.NOT_PROCESS.code());
				//总金额
				Money totalAmount = Money.zero();
				for (ReceiptPaymentFormFeeOrder feeOrder : order.getFeeOrderList()) {
					totalAmount.addTo(feeOrder.getAmount());
				}
				rpForm.setAmount(totalAmount);
				long sourceId = receiptPaymentFormDAO.insert(rpForm);
				
				//新增费用明细
				for (ReceiptPaymentFormFeeOrder feeOrder : order.getFeeOrderList()) {
					ReceiptPaymentFormFeeDO fee = new ReceiptPaymentFormFeeDO();
					BeanCopier.staticCopy(feeOrder, fee);
					fee.setFeeType(feeOrder.getFeeType().code());
					fee.setRawAddTime(now);
					fee.setSourceId(sourceId);
					receiptPaymentFormFeeDAO.insert(fee);
				}
				
				return null;
			}
		}, null, null);
	}
	
	@Override
	public QueryBaseBatchResult<ReceiptPaymentFormInfo> query(ReceiptPaymentFormQueryOrder order) {
		QueryBaseBatchResult<ReceiptPaymentFormInfo> batchResult = new QueryBaseBatchResult<ReceiptPaymentFormInfo>();
		try {
			ReceiptPaymentFormDO receiptPaymentForm = new ReceiptPaymentFormDO();
			
			List<String> statusList = Lists.newArrayList();
			if (ListUtil.isNotEmpty(order.getStatusList())) {
				for (ReceiptPaymentFormStatusEnum sta : order.getStatusList()) {
					statusList.add(sta.code());
				}
			}
			BeanCopier.staticCopy(order, receiptPaymentForm);
			
			if (order.getSourceForm() != null)
				receiptPaymentForm.setSourceForm(order.getSourceForm().code());
			
			if (order.getStatus() != null)
				receiptPaymentForm.setStatus(order.getStatus().code());
			
			if (order.getSourceFormSys() != null)
				receiptPaymentForm.setSourceFormSys(order.getSourceFormSys().code());
			
			if (order.getFundDirection() != null)
				receiptPaymentForm.setFundDirection(order.getFundDirection().code());
			
			long totalCount = receiptPaymentFormDAO.findByConditionCount(receiptPaymentForm,
				statusList);
			PageComponent component = new PageComponent(order, totalCount);
			List<ReceiptPaymentFormDO> dataList = receiptPaymentFormDAO.findByCondition(
				receiptPaymentForm, statusList, order.getSortCol(), order.getSortOrder(),
				component.getFirstRecord(), component.getPageSize());
			
			List<ReceiptPaymentFormInfo> list = Lists.newArrayList();
			for (ReceiptPaymentFormDO DO : dataList) {
				ReceiptPaymentFormInfo info = convertDO2Info(DO);
				if (order.isQueryDetail()) {
					info.setFeeList(queryFeeList(info.getSourceId()));
				}
				list.add(info);
			}
			batchResult.setSuccess(true);
			batchResult.setPageList(list);
			batchResult.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询收款单/付款单数据源失败 {}", e);
			batchResult.setSuccess(false);
			batchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return batchResult;
	}
	
	@Override
	public ReceiptPaymentFormInfo queryBySourceFormId(SourceFormEnum sourceForm,
														String sourceFormId, boolean queryDetail) {
		ReceiptPaymentFormDO rpDO = receiptPaymentFormDAO.findBySourceFormId(sourceForm.code(),
			sourceFormId);
		ReceiptPaymentFormInfo info = convertDO2Info(rpDO);
		if (info != null && queryDetail) {
			info.setFeeList(queryFeeList(info.getSourceId()));
		}
		return info;
	}
	
	@Override
	public ReceiptPaymentFormInfo queryBySourceId(long sourceId, boolean queryDetail) {
		ReceiptPaymentFormDO rpDO = receiptPaymentFormDAO.findById(sourceId);
		ReceiptPaymentFormInfo info = convertDO2Info(rpDO);
		if (info != null && queryDetail) {
			info.setFeeList(queryFeeList(info.getSourceId()));
		}
		return info;
	}
	
	@Override
	public FcsBaseResult updateStatus(final UpdateReceiptPaymentFormStatusOrder order) {
		return commonProcess(order, "更新付款单/收款单处理状态", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				
				ReceiptPaymentFormDO rpForm = receiptPaymentFormDAO.findBySourceFormId(order
					.getSourceForm().code(), order.getSourceFormId());
				if (rpForm == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "数据不存在");
				}
				//状态未改变不做更新
				if (!StringUtil.equals(rpForm.getStatus(), order.getStatus().code())) {
					rpForm.setStatus(order.getStatus().code());
					receiptPaymentFormDAO.update(rpForm);
				}
				
				return null;
			}
		}, null, null);
	}
	
	/**
	 * 查询费用明细
	 * @param sourceId
	 * @return
	 */
	private List<ReceiptPaymentFormFeeInfo> queryFeeList(long sourceId) {
		List<ReceiptPaymentFormFeeInfo> list = null;
		List<ReceiptPaymentFormFeeDO> feeList = receiptPaymentFormFeeDAO.findBySourceId(sourceId);
		if (ListUtil.isNotEmpty(feeList)) {
			list = Lists.newArrayList();
			for (ReceiptPaymentFormFeeDO fee : feeList) {
				ReceiptPaymentFormFeeInfo feeInfo = new ReceiptPaymentFormFeeInfo();
				BeanCopier.staticCopy(fee, feeInfo);
				feeInfo.setFeeType(SubjectCostTypeEnum.getByCode(fee.getFeeType()));
				list.add(feeInfo);
			}
		}
		return list;
	}
	
	/**
	 * DO转info
	 * @param DO
	 * @return
	 */
	private ReceiptPaymentFormInfo convertDO2Info(ReceiptPaymentFormDO DO) {
		if (DO == null)
			return null;
		ReceiptPaymentFormInfo info = new ReceiptPaymentFormInfo();
		BeanCopier.staticCopy(DO, info);
		info.setSourceForm(SourceFormEnum.getByCode(DO.getSourceForm()));
		info.setSourceFormSys(SystemEnum.getByCode(DO.getSourceFormSys()));
		info.setStatus(ReceiptPaymentFormStatusEnum.getByCode(DO.getStatus()));
		info.setFundDirection(FundDirectionEnum.getByCode(DO.getFundDirection()));
		return info;
	}
}
