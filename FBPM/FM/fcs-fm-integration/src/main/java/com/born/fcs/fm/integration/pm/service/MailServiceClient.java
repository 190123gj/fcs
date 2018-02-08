package com.born.fcs.fm.integration.pm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.fm.integration.bpm.service.CallExternalInterface;
import com.born.fcs.fm.integration.bpm.service.ClientAutowiredBaseService;
import com.born.fcs.pm.biz.service.common.MailService;
import com.born.fcs.pm.ws.order.common.SendMailOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;

@Service("mailServiceClient")
public class MailServiceClient extends ClientAutowiredBaseService implements MailService {
	
	@Autowired
	MailService mailWebService;
	
	@Override
	public FcsBaseResult sendHtmlEmail(final SendMailOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return mailWebService.sendHtmlEmail(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult sendTextEmail(final SendMailOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return mailWebService.sendTextEmail(order);
			}
		});
	}
	
}
