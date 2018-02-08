package com.born.fcs.pm.integration.risk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.service.common.AsynchronousService;
import com.born.fcs.pm.integration.common.CallExternalInterface;
import com.born.fcs.pm.integration.common.impl.ClientAutowiredBaseService;
import com.bornsoft.facade.api.umeng.UmenMessageSendFacade;
import com.bornsoft.facade.order.UMengSendOrder;
import com.bornsoft.pub.result.umeng.UmengSendResult;

/**
 * 信息推送
 * **/
@Service("umenMessageSendFacadeClient")
public class UmenMessageSendFacadeClient extends ClientAutowiredBaseService implements
																			UmenMessageSendFacade,
																			AsynchronousService {
	
	@Autowired
	protected UmenMessageSendFacade umenMessageSendFacade;
	
	@Override
	public UmengSendResult send(final UMengSendOrder order) {
		return callInterface(new CallExternalInterface<UmengSendResult>() {
			
			@Override
			public UmengSendResult call() {
				return umenMessageSendFacade.send(order);
			}
		});
	}
	
	@Override
	public void execute(Object[] objects) {
		try {
			if (objects != null && objects.length > 0) {
				UMengSendOrder order = (UMengSendOrder) objects[0];
				if (order != null)
					send(order);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
