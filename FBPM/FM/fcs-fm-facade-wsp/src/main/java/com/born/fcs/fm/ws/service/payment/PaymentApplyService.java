package com.born.fcs.fm.ws.service.payment;

import java.util.List;

import javax.jws.WebService;

import com.born.fcs.fm.ws.info.payment.FormPaymentFeeInfo;
import com.born.fcs.fm.ws.info.payment.FormPaymentFormInfo;
import com.born.fcs.fm.ws.info.payment.FormPaymentInfo;
import com.born.fcs.fm.ws.order.common.UpdateVoucherOrder;
import com.born.fcs.fm.ws.order.payment.FormPaymentOrder;
import com.born.fcs.fm.ws.order.payment.FormPaymentQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 付款申请单
 * @author wuzj
 */
@WebService
public interface PaymentApplyService {
	
	/**
	 * 保存付款申请单
	 * @param order
	 * @return
	 */
	FormBaseResult saveApply(FormPaymentOrder order);
	
	/***
	 * 查询表单列表
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<FormPaymentFormInfo> searchForm(FormPaymentQueryOrder order);
	
	/**
	 * 根据表单ID查询收款单
	 * @param formId
	 * @return
	 */
	FormPaymentInfo findPaymentByFormId(long formId, boolean queryFeeDetail);
	
	/**
	 * 根据单据号查询收款单
	 * @param formId
	 * @return
	 */
	FormPaymentInfo findPaymentByBillNo(String billNo, boolean queryFeeDetail);
	
	/**
	 * 根据表单ID查询收款单明细
	 * @param formId
	 * @return
	 */
	List<FormPaymentFeeInfo> findPaymentFeeByFormId(long formId);
	
	/**
	 * 同步至金碟（财务系统）
	 * @param formId
	 * @return
	 */
	FcsBaseResult sync2FinancialSys(FormPaymentInfo paymentInfo);
	
	/**
	 * 更新凭证号同步状态
	 * @param order
	 * @return
	 */
	FcsBaseResult updateVoucher(UpdateVoucherOrder order);
}
