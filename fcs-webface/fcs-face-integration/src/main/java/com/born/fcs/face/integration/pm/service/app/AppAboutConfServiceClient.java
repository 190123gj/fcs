package com.born.fcs.face.integration.pm.service.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.app.AppAboutConfInfo;
import com.born.fcs.pm.ws.app.AppAboutConfOrder;
import com.born.fcs.pm.ws.app.AppAboutConfService;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;

@Service("appAboutConfServiceClient")
public class AppAboutConfServiceClient extends ClientAutowiredBaseService implements
																			AppAboutConfService {
	
	@Autowired
	AppAboutConfService appAboutConfWebService;
	
	@Override
	public FcsBaseResult save(final AppAboutConfOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return appAboutConfWebService.save(order);
			}
		});
	}
	
	@Override
	public AppAboutConfInfo get() {
		return callInterface(new CallExternalInterface<AppAboutConfInfo>() {
			
			@Override
			public AppAboutConfInfo call() {
				return appAboutConfWebService.get();
			}
		});
	}
	
}
