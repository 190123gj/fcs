package com.born.fcs.face.integration.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.pm.biz.service.common.SysClearCacheService;
import com.born.fcs.pm.biz.service.common.SysParameterService;
import com.born.fcs.pm.ws.service.common.FormMessageTempleteService;
import com.yjf.common.log.Logger;
import com.yjf.common.log.LoggerFactory;

@Service("sysClearCacheService")
public class SysClearCacheServiceImpl implements SysClearCacheService {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	SysParameterService sysParameterWebService1;
	@Autowired
	SysParameterService sysParameterWebService2;
	@Autowired
	FormMessageTempleteService formMessageTempleteWebService1;
	@Autowired
	FormMessageTempleteService formMessageTempleteWebService2;
	
	@Override
	public void clearCache() {
		try {
			//本地清理两次缓存
			sysParameterWebService1.clearCache();
			sysParameterWebService2.clearCache();
			formMessageTempleteWebService1.clearCache();
			formMessageTempleteWebService2.clearCache();
		} catch (Exception ce) {
			logger.error("未知异常:e={}", ce.getMessage(), ce);
			
		}
	}
}
