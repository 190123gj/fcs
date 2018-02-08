package com.bornsoft.integration;

import org.springframework.beans.factory.annotation.Autowired;

import com.bornsoft.integration.aspect.IntegrationLog;
import com.bornsoft.integration.jck.config.SystemParamCacheHolder;
import com.bornsoft.utils.base.MerchantInfo;

/**
 * @Description: serviceClient工厂 
 * @author taibai@yiji.com
 * @date 2016-11-8 下午6:47:55
 * @version V1.0
 */
public class ServiceClientFactory {
	
	@Autowired
	protected SystemParamCacheHolder systemParamCacheHolder;
	
	@IntegrationLog
	public MerchantInfo getMerchantInfo(String merchantNo) {
		MerchantInfo info = systemParamCacheHolder.getMerchantInfo(merchantNo);
		/*if(info == null || StringUtil.isBlank(info.getMerchantKey())){
			info.setMerchantNo(merchantNo);
			info.setMerchantKey("be7c713d08194fac983036924586be09");
		}*/
		return info;
	}
	
	
}
