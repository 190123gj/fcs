package com.born.fcs.face.integration.fm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.fm.ws.enums.SourceFormEnum;
import com.born.fcs.fm.ws.info.common.ReceiptPaymentFormInfo;
import com.born.fcs.fm.ws.order.common.ReceiptPaymentFormOrder;
import com.born.fcs.fm.ws.order.common.ReceiptPaymentFormQueryOrder;
import com.born.fcs.fm.ws.order.common.UpdateReceiptPaymentFormStatusOrder;
import com.born.fcs.fm.ws.service.common.ReceiptPaymentFormService;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

@Service("receiptPaymentFormServiceClient")
public class ReceiptPaymentFormServiceClient extends ClientAutowiredBaseService implements
																				ReceiptPaymentFormService {
	@Autowired
	ReceiptPaymentFormService receiptPaymentFormWebService;
	
	@Override
	public FcsBaseResult add(final ReceiptPaymentFormOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return receiptPaymentFormWebService.add(order);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<ReceiptPaymentFormInfo> query(	final ReceiptPaymentFormQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<ReceiptPaymentFormInfo>>() {
			
			@Override
			public QueryBaseBatchResult<ReceiptPaymentFormInfo> call() {
				return receiptPaymentFormWebService.query(order);
			}
		});
	}
	
	@Override
	public ReceiptPaymentFormInfo queryBySourceFormId(final SourceFormEnum sourceForm,
														final String sourceFormId,
														final boolean queryDetail) {
		return callInterface(new CallExternalInterface<ReceiptPaymentFormInfo>() {
			
			@Override
			public ReceiptPaymentFormInfo call() {
				return receiptPaymentFormWebService.queryBySourceFormId(sourceForm, sourceFormId,
					queryDetail);
			}
		});
	}
	
	@Override
	public ReceiptPaymentFormInfo queryBySourceId(final long sourceId, final boolean queryDetail) {
		return callInterface(new CallExternalInterface<ReceiptPaymentFormInfo>() {
			
			@Override
			public ReceiptPaymentFormInfo call() {
				return receiptPaymentFormWebService.queryBySourceId(sourceId, queryDetail);
			}
		});
	}
	
	@Override
	public FcsBaseResult updateStatus(final UpdateReceiptPaymentFormStatusOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return receiptPaymentFormWebService.updateStatus(order);
			}
		});
	}
	
}
