package com.born.fcs.face.integration.pm.service.managerbchange;

import java.net.SocketTimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.info.managerbchange.FManagerbChangeApplyInfo;
import com.born.fcs.pm.ws.info.managerbchange.ManagerbChangeApplyFormInfo;
import com.born.fcs.pm.ws.order.managerbchange.FManagerbChangeApplyOrder;
import com.born.fcs.pm.ws.order.managerbchange.ManagerbChangeApplyQueryOrder;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.managerbchange.ManagerbChangeService;

@Service("managerbChangeServiceClient")
public class ManagerbChangeServiceClient extends ClientAutowiredBaseService implements
																			ManagerbChangeService {
	@Autowired
	ManagerbChangeService managerbChangeWebService;
	
	@Override
	public FormBaseResult saveApply(final FManagerbChangeApplyOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			
			@Override
			public FormBaseResult call() throws SocketTimeoutException {
				return managerbChangeWebService.saveApply(order);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<ManagerbChangeApplyFormInfo> searchForm(final ManagerbChangeApplyQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<ManagerbChangeApplyFormInfo>>() {
			
			@Override
			public QueryBaseBatchResult<ManagerbChangeApplyFormInfo> call()
																			throws SocketTimeoutException {
				return managerbChangeWebService.searchForm(order);
			}
		});
	}
	
	@Override
	public FManagerbChangeApplyInfo queryApplyByFormId(final long formId) {
		return callInterface(new CallExternalInterface<FManagerbChangeApplyInfo>() {
			
			@Override
			public FManagerbChangeApplyInfo call() throws SocketTimeoutException {
				return managerbChangeWebService.queryApplyByFormId(formId);
			}
		});
	}
	
}
