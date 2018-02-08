package com.born.fcs.am.intergration.bpm.pm;

import org.springframework.stereotype.Service;

import com.born.fcs.am.intergration.service.CallExternalInterface;
import com.born.fcs.am.intergration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.order.finvestigation.UpdateInvestigationCreditSchemePledgeAssetOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.service.forcall.ForAmService;

@Service("pmWebServiceClient")
public class PmWebServiceClient extends ClientAutowiredBaseService implements ForAmService {
	
	@Override
	public FcsBaseResult updatePledgeAsset(	final UpdateInvestigationCreditSchemePledgeAssetOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return pmWebService.updatePledgeAsset(order);
			}
		});
	}
	
}
