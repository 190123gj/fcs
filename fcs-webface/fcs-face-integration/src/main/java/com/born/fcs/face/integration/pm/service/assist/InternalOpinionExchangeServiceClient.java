package com.born.fcs.face.integration.pm.service.assist;

import java.util.List;

import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.info.assist.FInternalOpinionExchangeInfo;
import com.born.fcs.pm.ws.info.assist.FInternalOpinionExchangeUserInfo;
import com.born.fcs.pm.ws.info.assist.InternalOpinionExchangeFormInfo;
import com.born.fcs.pm.ws.order.assist.FInternalOpinionExchangeOrder;
import com.born.fcs.pm.ws.order.assist.InternalOpinionExchangeQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.assist.InternalOpinionExchangeService;

@Service("internalOpinionExchangeServiceClient")
public class InternalOpinionExchangeServiceClient extends ClientAutowiredBaseService implements
																					InternalOpinionExchangeService {
	
	@Override
	public FormBaseResult save(final FInternalOpinionExchangeOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			
			@Override
			public FormBaseResult call() {
				return internalOpinionExchangeWebService.save(order);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<InternalOpinionExchangeFormInfo> searchForm(final InternalOpinionExchangeQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<InternalOpinionExchangeFormInfo>>() {
			
			@Override
			public QueryBaseBatchResult<InternalOpinionExchangeFormInfo> call() {
				return internalOpinionExchangeWebService.searchForm(order);
			}
		});
	}
	
	@Override
	public FInternalOpinionExchangeInfo findByFormId(final long formId) {
		return callInterface(new CallExternalInterface<FInternalOpinionExchangeInfo>() {
			
			@Override
			public FInternalOpinionExchangeInfo call() {
				return internalOpinionExchangeWebService.findByFormId(formId);
			}
		});
	}
	
	@Override
	public List<FInternalOpinionExchangeUserInfo> findUserByFormId(final long formId) {
		return callInterface(new CallExternalInterface<List<FInternalOpinionExchangeUserInfo>>() {
			
			@Override
			public List<FInternalOpinionExchangeUserInfo> call() {
				return internalOpinionExchangeWebService.findUserByFormId(formId);
			}
		});
	}
	
	@Override
	public FcsBaseResult checkDeptUser(final FInternalOpinionExchangeOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return internalOpinionExchangeWebService.checkDeptUser(order);
			}
		});
	}
	
}
