package com.born.fcs.am.intergration.common;

import org.springframework.stereotype.Service;

import com.born.fcs.am.intergration.service.CallExternalInterface;
import com.born.fcs.am.intergration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.biz.service.common.MailService;
import com.born.fcs.pm.ws.order.common.SendMailOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;

@Service("mailServiceClient")
public class MailServiceClient extends ClientAutowiredBaseService implements
		MailService {

	@Override
	public FcsBaseResult sendTextEmail(final SendMailOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {

			@Override
			public FcsBaseResult call() {
				return mailService.sendTextEmail(order);
			}
		});
	}

	@Override
	public FcsBaseResult sendHtmlEmail(final SendMailOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {

			@Override
			public FcsBaseResult call() {
				return mailService.sendHtmlEmail(order);
			}
		});
	}
}
