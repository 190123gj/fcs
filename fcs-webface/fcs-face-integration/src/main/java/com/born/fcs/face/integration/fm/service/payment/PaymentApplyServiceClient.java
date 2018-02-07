package com.born.fcs.face.integration.fm.service.payment;

import java.util.List;

import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.fm.ws.info.payment.FormPaymentFeeInfo;
import com.born.fcs.fm.ws.info.payment.FormPaymentFormInfo;
import com.born.fcs.fm.ws.info.payment.FormPaymentInfo;
import com.born.fcs.fm.ws.order.common.UpdateVoucherOrder;
import com.born.fcs.fm.ws.order.payment.FormPaymentOrder;
import com.born.fcs.fm.ws.order.payment.FormPaymentQueryOrder;
import com.born.fcs.fm.ws.service.payment.PaymentApplyService;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 
 * 付款单
 * 
 * @author hjiajie
 * 
 */
@Service("paymentApplyServiceClient")
public class PaymentApplyServiceClient extends ClientAutowiredBaseService implements
																			PaymentApplyService {
	
	@Override
	public FormBaseResult saveApply(final FormPaymentOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			
			@Override
			public FormBaseResult call() {
				return paymentApplyWebService.saveApply(order);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<FormPaymentFormInfo> searchForm(final FormPaymentQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<FormPaymentFormInfo>>() {
			
			@Override
			public QueryBaseBatchResult<FormPaymentFormInfo> call() {
				return paymentApplyWebService.searchForm(order);
			}
		});
	}
	
	@Override
	public FormPaymentInfo findPaymentByFormId(final long formId, final boolean queryFeeDetail) {
		return callInterface(new CallExternalInterface<FormPaymentInfo>() {
			
			@Override
			public FormPaymentInfo call() {
				return paymentApplyWebService.findPaymentByFormId(formId, queryFeeDetail);
			}
		});
	}
	
	@Override
	public FormPaymentInfo findPaymentByBillNo(final String billNo, final boolean queryFeeDetail) {
		return callInterface(new CallExternalInterface<FormPaymentInfo>() {
			
			@Override
			public FormPaymentInfo call() {
				return paymentApplyWebService.findPaymentByBillNo(billNo, queryFeeDetail);
			}
		});
	}
	
	@Override
	public List<FormPaymentFeeInfo> findPaymentFeeByFormId(final long formId) {
		return callInterface(new CallExternalInterface<List<FormPaymentFeeInfo>>() {
			
			@Override
			public List<FormPaymentFeeInfo> call() {
				return paymentApplyWebService.findPaymentFeeByFormId(formId);
			}
		});
	}
	
	@Override
	public FcsBaseResult sync2FinancialSys(final FormPaymentInfo paymentInfo) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return paymentApplyWebService.sync2FinancialSys(paymentInfo);
			}
		});
	}
	
	@Override
	public FcsBaseResult updateVoucher(final UpdateVoucherOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return paymentApplyWebService.updateVoucher(order);
			}
		});
	}
	
}
