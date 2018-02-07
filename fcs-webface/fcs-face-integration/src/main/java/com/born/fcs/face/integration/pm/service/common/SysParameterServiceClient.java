package com.born.fcs.face.integration.pm.service.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.face.integration.service.SysClearWebCacheService;
import com.born.fcs.pm.biz.service.common.SysParameterService;
import com.born.fcs.pm.biz.service.common.info.SysParamInfo;
import com.born.fcs.pm.util.AppConstantsUtil;
import com.born.fcs.pm.util.SystemConfig;
import com.born.fcs.pm.ws.order.sysParam.SysParamOrder;
import com.born.fcs.pm.ws.order.sysParam.SysParamQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

@Service("sysParameterServiceClient")
public class SysParameterServiceClient extends ClientAutowiredBaseService implements
																			SysParameterService,
																			InitializingBean {
	@Autowired
	SysClearWebCacheService sysClearCacheServiceClient;
	
	@Override
	public String getSysParameterValue(final String paramName) {
		return callInterface(new CallExternalInterface<String>() {
			
			@Override
			public String call() {
				return sysParameterService.getSysParameterValue(paramName);
			}
		});
	}
	
	@Override
	public SysParamInfo getSysParameterValueDO(final String paramName) {
		return callInterface(new CallExternalInterface<SysParamInfo>() {
			@Override
			public SysParamInfo call() {
				return sysParameterService.getSysParameterValueDO(paramName);
			}
		});
	}
	
	@Override
	public FcsBaseResult updateSysParameterValueDO(final SysParamOrder sysParamOrder) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			@Override
			public FcsBaseResult call() {
				FcsBaseResult baseResult = new FcsBaseResult();
				baseResult = sysParameterService.updateSysParameterValueDO(sysParamOrder);
				sysClearCacheServiceClient.clearCache();
				return baseResult;
			}
		});
	}
	
	@Override
	public FcsBaseResult insertSysParameterValueDO(final SysParamOrder sysParamOrder) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			@Override
			public FcsBaseResult call() {
				FcsBaseResult baseResult = new FcsBaseResult();
				baseResult = sysParameterService.insertSysParameterValueDO(sysParamOrder);
				sysClearCacheServiceClient.clearCache();
				return baseResult;
			}
		});
	}
	
	@Override
	public void deleteSysParameterValue(final String paramName) {
		callInterface(new CallExternalInterface<Object>() {
			@Override
			public Object call() {
				sysParameterService.deleteSysParameterValue(paramName);
				sysClearCacheServiceClient.clearCache();
				return null;
			}
		});
	}
	
	@Override
	public List<SysParamInfo> getSysParameterValueList(final String paramName) {
		
		return callInterface(new CallExternalInterface<List<SysParamInfo>>() {
			@Override
			public List<SysParamInfo> call() {
				return sysParameterService.getSysParameterValueList(paramName);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<SysParamInfo> querySysPram(	final SysParamQueryOrder sysParamQueryOrder) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<SysParamInfo>>() {
			@Override
			public QueryBaseBatchResult<SysParamInfo> call() {
				return sysParameterService.querySysPram(sysParamQueryOrder);
			}
		});
	}
	
	@Override
	public void clearCache() {
		this.isInit = false;
		AppConstantsUtil.clear();
		try {
			afterPropertiesSet();
			sysParameterService.clearCache();
		} catch (Exception e) {
			logger.error("afterPropertiesSet is error", e);
		}
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		try {
			SystemConfig config = new SystemConfig();
			this.isInit = true;
			List<SysParamInfo> paramInfos = this.getSysParameterValueList("");
			if (paramInfos != null) {
				Map<String, String> map = new HashMap<String, String>();
				for (SysParamInfo info : paramInfos) {
					map.put(info.getParamName(), info.getParamValue());
				}
				AppConstantsUtil.init(config);
				this.isInit = true;
			}
			
		} catch (Exception e) {
			logger.info("init complete Exception", e);
			this.isInit = false;
		}
		
	}
	
	/**
	 * @param config
	 */
	protected void initConfigValue(SystemConfig config, Map<String, String> map) {
		
	}
}
