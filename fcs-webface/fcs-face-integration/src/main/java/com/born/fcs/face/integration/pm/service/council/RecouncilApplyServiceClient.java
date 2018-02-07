package com.born.fcs.face.integration.pm.service.council;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.info.council.FReCouncilApplyInfo;
import com.born.fcs.pm.ws.info.council.ReCouncilApplyFormInfo;
import com.born.fcs.pm.ws.order.council.FReCouncilApplyOrder;
import com.born.fcs.pm.ws.order.council.ReCouncilApplyQueryOrder;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.council.RecouncilApplyService;

public class RecouncilApplyServiceClient extends ClientAutowiredBaseService implements
																			RecouncilApplyService {
	
	@Override
	public QueryBaseBatchResult<ReCouncilApplyFormInfo> queryForm(	final ReCouncilApplyQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<ReCouncilApplyFormInfo>>() {
			
			@Override
			public QueryBaseBatchResult<ReCouncilApplyFormInfo> call() {
				return recouncilApplyWebService.queryForm(order);
			}
		});
	}
	
	@Override
	public FormBaseResult saveApply(final FReCouncilApplyOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			
			@Override
			public FormBaseResult call() {
				return recouncilApplyWebService.saveApply(order);
			}
		});
	}
	
	@Override
	public FReCouncilApplyInfo queryApplyByFormId(final long formId) {
		return callInterface(new CallExternalInterface<FReCouncilApplyInfo>() {
			
			@Override
			public FReCouncilApplyInfo call() {
				return recouncilApplyWebService.queryApplyByFormId(formId);
			}
		});
	}
	
}
