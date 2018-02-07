package com.born.fcs.face.integration.risk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.bornsoft.pub.interfaces.IRiskSystemService;
import com.bornsoft.pub.order.risk.RiskInfoRecOrder;
import com.bornsoft.utils.base.BornSynResultBase;

@Service("riskMessage")
public class RiskMessage extends ClientAutowiredBaseService implements IRiskSystemService {
	
	@Autowired
	IRiskSystemService iRiskSystemService;
	
	@Override
	public BornSynResultBase recieveRiskInfo(final RiskInfoRecOrder riskInfo) {
		return callInterface(new CallExternalInterface<BornSynResultBase>() {
			
			@Override
			public BornSynResultBase call() {
				return iRiskSystemService.recieveRiskInfo(riskInfo);
			}
		});
	}
	
}
