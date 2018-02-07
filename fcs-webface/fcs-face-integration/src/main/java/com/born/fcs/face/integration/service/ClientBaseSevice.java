package com.born.fcs.face.integration.service;

import java.net.SocketTimeoutException;

import org.springframework.beans.factory.annotation.Autowired;

import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

public class ClientBaseSevice {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	protected boolean isInit = false;
	
	@Autowired
	protected PropertyConfigService propertyConfigService;
	
	protected <T> T callInterface(CallExternalInterface<T> externalInterface) {
		T t = null;
		try {
			if (!isInit)
				init();
			t = externalInterface.call();
			logger.info("远程调用结果{}" + t);
			return t;
		} catch (SocketTimeoutException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return t;
	}
	
	public void init() {
		
	}
}
