package com.born.fcs.face.integration.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.BasicDataCacheService;
import com.born.fcs.pm.biz.service.common.SysClearCacheService;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

@Service("sysClearCacheServiceClient")
public class SysClearCacheServiceClientImpl
											implements
											com.born.fcs.face.integration.service.SysClearWebCacheService {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	SysClearCacheService sysClearCacheService;
	
	@Autowired
	BasicDataCacheService basicDataCacheService;
	
	@Override
	public void clearCache() {
		try {
			//本地清理两次缓存
			sysClearCacheService.clearCache();
			
			//基础数据缓存清除
			basicDataCacheService.clearCache();
			//系统参数
			//sysParameterServiceClient.clearCache();
			//模板缓存
			//formMessageTempleteServiceClient.clearCache();
		} catch (Exception ce) {
			logger.error("未知异常:e={}", ce.getMessage(), ce);
			
		}
	}
}
