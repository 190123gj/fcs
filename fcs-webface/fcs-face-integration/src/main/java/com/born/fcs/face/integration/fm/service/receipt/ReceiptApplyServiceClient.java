package com.born.fcs.face.integration.fm.service.receipt;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.fm.ws.info.receipt.FormReceiptFeeInfo;
import com.born.fcs.fm.ws.info.receipt.FormReceiptFormInfo;
import com.born.fcs.fm.ws.info.receipt.FormReceiptInfo;
import com.born.fcs.fm.ws.order.common.UpdateVoucherOrder;
import com.born.fcs.fm.ws.order.receipt.FormReceiptOrder;
import com.born.fcs.fm.ws.order.receipt.FormReceiptQueryOrder;
import com.born.fcs.fm.ws.service.receipt.ReceiptApplyService;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

@Service("receiptApplyServiceClient")
public class ReceiptApplyServiceClient extends ClientAutowiredBaseService implements
																			ReceiptApplyService {
	@Autowired
	ReceiptApplyService receiptApplyWebService;
	
	@Override
	public FormBaseResult saveApply(final FormReceiptOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			
			@Override
			public FormBaseResult call() {
				return receiptApplyWebService.saveApply(order);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<FormReceiptFormInfo> searchForm(final FormReceiptQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<FormReceiptFormInfo>>() {
			
			@Override
			public QueryBaseBatchResult<FormReceiptFormInfo> call() {
				return receiptApplyWebService.searchForm(order);
			}
		});
	}
	
	@Override
	public FormReceiptInfo findReceiptByFormId(final long formId, final boolean queryFeeDetail) {
		return callInterface(new CallExternalInterface<FormReceiptInfo>() {
			
			@Override
			public FormReceiptInfo call() {
				return receiptApplyWebService.findReceiptByFormId(formId, queryFeeDetail);
			}
		});
	}
	
	@Override
	public FormReceiptInfo findReceiptByBillNo(final String billNo, final boolean queryFeeDetail) {
		return callInterface(new CallExternalInterface<FormReceiptInfo>() {
			
			@Override
			public FormReceiptInfo call() {
				return receiptApplyWebService.findReceiptByBillNo(billNo, queryFeeDetail);
			}
		});
	}
	
	@Override
	public List<FormReceiptFeeInfo> findReceiptFeeByFormId(final long formId) {
		return callInterface(new CallExternalInterface<List<FormReceiptFeeInfo>>() {
			
			@Override
			public List<FormReceiptFeeInfo> call() {
				return receiptApplyWebService.findReceiptFeeByFormId(formId);
			}
		});
	}
	
	@Override
	public FcsBaseResult sync2FinancialSys(final FormReceiptInfo receiptInfo) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return receiptApplyWebService.sync2FinancialSys(receiptInfo);
			}
		});
	}
	
	@Override
	public FcsBaseResult updateVoucher(final UpdateVoucherOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return receiptApplyWebService.updateVoucher(order);
			}
		});
	}
	
}
