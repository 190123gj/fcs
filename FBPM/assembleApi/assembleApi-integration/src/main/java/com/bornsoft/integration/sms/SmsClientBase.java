package com.bornsoft.integration.sms;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.bornsoft.integration.jck.config.SystemParamCacheHolder;
import com.bornsoft.utils.constants.ApiSystemParamEnum;

public class SmsClientBase {
	
	@Autowired
	private SystemParamCacheHolder systemParamCacheHolder;
	
	
	protected String getSignatrue() {
		String sign = StringUtils.defaultIfBlank(
				getConfig(ApiSystemParamEnum.Sms_Sign),
				getConfig(ApiSystemParamEnum.Huaruan_Sms_Sign));
		return "【" + sign + "】";
	}
	
	
	protected String getConfigNotNull(ApiSystemParamEnum conf){
		return systemParamCacheHolder.getConfigNotNull(conf);
	}

	protected String getConfig(ApiSystemParamEnum conf){
		return systemParamCacheHolder.getConfig(conf);
	}
	
	

}
