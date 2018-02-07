package com.born.fcs.face.integration.pm.service.formchange;

import java.util.List;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.enums.FormChangeApplyEnum;
import com.born.fcs.pm.ws.info.formchange.FormChangeApplyInfo;
import com.born.fcs.pm.ws.info.formchange.FormChangeApplySearchInfo;
import com.born.fcs.pm.ws.info.formchange.FormChangeRecordDetailInfo;
import com.born.fcs.pm.ws.info.formchange.FormChangeRecordInfo;
import com.born.fcs.pm.ws.order.formchange.FormChangeApplyOrder;
import com.born.fcs.pm.ws.order.formchange.FormChangeApplyQueryOrder;
import com.born.fcs.pm.ws.order.formchange.FormChangeRecordOrder;
import com.born.fcs.pm.ws.order.formchange.FormChangeRecordQueryOrder;
import com.born.fcs.pm.ws.order.formchange.FormCheckCanChangeOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.formchange.FormChangeApplyService;

public class FormChangeApplyServiceClient extends ClientAutowiredBaseService implements
																			FormChangeApplyService {
	
	@Override
	public QueryBaseBatchResult<FormChangeApplySearchInfo> searchForm(	final FormChangeApplyQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<FormChangeApplySearchInfo>>() {
			
			@Override
			public QueryBaseBatchResult<FormChangeApplySearchInfo> call() {
				return formChangeApplyWebService.searchForm(order);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<FormChangeApplyInfo> searchApply(	final FormChangeApplyQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<FormChangeApplyInfo>>() {
			
			@Override
			public QueryBaseBatchResult<FormChangeApplyInfo> call() {
				return formChangeApplyWebService.searchApply(order);
			}
		});
	}
	
	@Override
	public FormChangeApplyInfo queryByFormId(final long formId) {
		return callInterface(new CallExternalInterface<FormChangeApplyInfo>() {
			
			@Override
			public FormChangeApplyInfo call() {
				return formChangeApplyWebService.queryByFormId(formId);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<FormChangeRecordInfo> searchRecord(	final FormChangeRecordQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<FormChangeRecordInfo>>() {
			
			@Override
			public QueryBaseBatchResult<FormChangeRecordInfo> call() {
				return formChangeApplyWebService.searchRecord(order);
			}
		});
	}
	
	@Override
	public FormChangeRecordInfo queryRecord(final long recordId, final boolean queryDetail) {
		return callInterface(new CallExternalInterface<FormChangeRecordInfo>() {
			
			@Override
			public FormChangeRecordInfo call() {
				return formChangeApplyWebService.queryRecord(recordId, queryDetail);
			}
		});
	}
	
	@Override
	public List<FormChangeRecordDetailInfo> queryRecordDetail(final long recordId) {
		return callInterface(new CallExternalInterface<List<FormChangeRecordDetailInfo>>() {
			
			@Override
			public List<FormChangeRecordDetailInfo> call() {
				return formChangeApplyWebService.queryRecordDetail(recordId);
			}
		});
	}
	
	@Override
	public FormBaseResult saveApply(final FormChangeApplyOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			
			@Override
			public FormBaseResult call() {
				return formChangeApplyWebService.saveApply(order);
			}
		});
	}
	
	@Override
	public FormBaseResult saveRecord(final FormChangeRecordOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			
			@Override
			public FormBaseResult call() {
				return formChangeApplyWebService.saveRecord(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult executeChange(final long formId) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return formChangeApplyWebService.executeChange(formId);
			}
		});
	}
	
	@Override
	public FcsBaseResult reExeChange(final long recordId) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return formChangeApplyWebService.reExeChange(recordId);
			}
		});
	}
	
	@Override
	public FcsBaseResult checkCanChange(final FormCheckCanChangeOrder checkOrder) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return formChangeApplyWebService.checkCanChange(checkOrder);
			}
		});
	}
}
