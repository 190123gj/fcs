package com.born.fcs.face.integration.risk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.crm.ws.service.VerifyMessageSaveService;
import com.born.fcs.crm.ws.service.info.VerifyMessageInfo;
import com.born.fcs.crm.ws.service.order.VerifyMessageOrder;
import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;

@Service("verifyMessageSaveClient")
public class VerifyMessageSaveClient extends ClientAutowiredBaseService implements
																		VerifyMessageSaveService {
	@Autowired
	VerifyMessageSaveService verifyMessageSaveService;
	
	@Override
	public FcsBaseResult save(final VerifyMessageOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return verifyMessageSaveService.save(order);
			}
		});
	}
	
	@Override
	public VerifyMessageInfo queryById(final String errorKey) {
		return callInterface(new CallExternalInterface<VerifyMessageInfo>() {
			
			@Override
			public VerifyMessageInfo call() {
				return verifyMessageSaveService.queryById(errorKey);
			}
		});
	}
	
}
