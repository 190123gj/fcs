package com.born.fcs.face.integration.pm.service.capitalappropriationapply;

import java.util.List;

import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.enums.FCapitalAppropriationApplyTypeEnum;
import com.born.fcs.pm.ws.enums.PaymentMenthodEnum;
import com.born.fcs.pm.ws.info.capitalappropriationapply.CapitalExportInfo;
import com.born.fcs.pm.ws.info.capitalappropriationapply.FCapitalAppropriationApplyFeeInfo;
import com.born.fcs.pm.ws.info.capitalappropriationapply.FCapitalAppropriationApplyInfo;
import com.born.fcs.pm.ws.info.capitalappropriationapply.FCapitalAppropriationApplyReceiptInfo;
import com.born.fcs.pm.ws.order.capitalappropriationapply.FCapitalAppropriationApplyOrder;
import com.born.fcs.pm.ws.order.capitalappropriationapply.FCapitalAppropriationApplyQueryOrder;
import com.born.fcs.pm.ws.order.capitalappropriationapply.FCapitalAppropriationApplyReceiptOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.capitalappropriationapply.FCapitalAppropriationApplyService;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * @author jil
 * 
 */
@Service("fCapitalAppropriationApplyServiceClient")
public class FCapitalAppropriationApplyServiceClient extends ClientAutowiredBaseService implements
																						FCapitalAppropriationApplyService {
	
	@Override
	public FCapitalAppropriationApplyInfo findById(final long id) {
		return callInterface(new CallExternalInterface<FCapitalAppropriationApplyInfo>() {
			
			@Override
			public FCapitalAppropriationApplyInfo call() {
				return fCapitalAppropriationApplyWebService.findById(id);
			}
		});
	}
	
	@Override
	public FCapitalAppropriationApplyInfo findCapitalAppropriationApplyByFormId(final long formId) {
		return callInterface(new CallExternalInterface<FCapitalAppropriationApplyInfo>() {
			
			@Override
			public FCapitalAppropriationApplyInfo call() {
				return fCapitalAppropriationApplyWebService
					.findCapitalAppropriationApplyByFormId(formId);
			}
		});
	}
	
	@Override
	public FormBaseResult save(final FCapitalAppropriationApplyOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			
			@Override
			public FormBaseResult call() {
				return fCapitalAppropriationApplyWebService.save(order);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<FCapitalAppropriationApplyInfo> query(	final FCapitalAppropriationApplyQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<FCapitalAppropriationApplyInfo>>() {
			
			@Override
			public QueryBaseBatchResult<FCapitalAppropriationApplyInfo> call() {
				return fCapitalAppropriationApplyWebService.query(order);
			}
		});
	}
	
	@Override
	public List<FCapitalAppropriationApplyInfo> findCapitalAppropriationApplyByProjectCode(	final String projectCode) {
		return callInterface(new CallExternalInterface<List<FCapitalAppropriationApplyInfo>>() {
			
			@Override
			public List<FCapitalAppropriationApplyInfo> call() {
				return fCapitalAppropriationApplyWebService
					.findCapitalAppropriationApplyByProjectCode(projectCode);
			}
		});
	}
	
	@Override
	public FcsBaseResult saveCapitalAppropriationApply(final FCapitalAppropriationApplyOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return fCapitalAppropriationApplyWebService.save(order);
			}
		});
	}
	
	@Override
	public List<FCapitalAppropriationApplyFeeInfo> findByApplyId(final long applyId) {
		return callInterface(new CallExternalInterface<List<FCapitalAppropriationApplyFeeInfo>>() {
			
			@Override
			public List<FCapitalAppropriationApplyFeeInfo> call() {
				return fCapitalAppropriationApplyWebService.findByApplyId(applyId);
			}
		});
	}
	
	@Override
	public List<PaymentMenthodEnum> getPaymentMenthodEnum(final String projectCode) {
		return callInterface(new CallExternalInterface<List<PaymentMenthodEnum>>() {
			
			@Override
			public List<PaymentMenthodEnum> call() {
				return fCapitalAppropriationApplyWebService.getPaymentMenthodEnum(projectCode);
			}
		});
	}
	
	@Override
	public List<Money> getLimitByProject(final String projectCode,
											final FCapitalAppropriationApplyTypeEnum type,
											final long applyId, final Boolean isEdit) {
		return callInterface(new CallExternalInterface<List<Money>>() {
			
			@Override
			public List<Money> call() {
				return fCapitalAppropriationApplyWebService.getLimitByProject(projectCode, type,
					applyId, isEdit);
			}
		});
	}
	
	@Override
	public FcsBaseResult saveReceipt(final FCapitalAppropriationApplyReceiptOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return fCapitalAppropriationApplyWebService.saveReceipt(order);
			}
		});
	}
	
	@Override
	public FCapitalAppropriationApplyReceiptInfo findByReceiptFormId(final long formId) {
		return callInterface(new CallExternalInterface<FCapitalAppropriationApplyReceiptInfo>() {
			
			@Override
			public FCapitalAppropriationApplyReceiptInfo call() {
				return fCapitalAppropriationApplyWebService.findByReceiptFormId(formId);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<CapitalExportInfo> capitalExport(	final FCapitalAppropriationApplyQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<CapitalExportInfo>>() {
			
			@Override
			public QueryBaseBatchResult<CapitalExportInfo> call() {
				return fCapitalAppropriationApplyWebService.capitalExport(order);
			}
		});
	}
	
}
