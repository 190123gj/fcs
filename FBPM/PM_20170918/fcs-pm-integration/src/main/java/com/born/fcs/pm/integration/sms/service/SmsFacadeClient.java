package com.born.fcs.pm.integration.sms.service;

import com.born.fcs.pm.integration.common.CallExternalInterface;
import com.bornsoft.facade.api.sms.SmsFacade;
import com.bornsoft.facade.order.SmsSendOrder;
import com.bornsoft.facade.result.SmsSendResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.integration.common.impl.ClientAutowiredBaseService;

/**
 * 短信接口
 * **/
@Service("smsFacadeClient")
public class SmsFacadeClient extends ClientAutowiredBaseService implements SmsFacade {
	
	@Autowired
	protected SmsFacade smsFacade;
	@Override
	public SmsSendResult sendSms(final SmsSendOrder order) {
		return callInterface(new CallExternalInterface<SmsSendResult>() {

			@Override
			public SmsSendResult call() {
				return smsFacade.sendSms(order);
			}
		});
	}
}
