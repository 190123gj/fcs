package com.born.fcs.crm.integration.common.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.born.fcs.crm.integration.common.PropertyConfigService;
import com.born.fcs.pm.biz.service.common.SysParameterService;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.yjf.common.env.Env;

@Service("propertyConfigService")
public class PropertyConfigServiceImpl implements PropertyConfigService {
	
	@Autowired
	private SysParameterService sysParameterServiceClient;
	
	@Value("${born.bpm.service.endpoint}")
	private String bornBmpServiceEndpoint;
	
	@Value("${born.bpm.contextpath}")
	private String bornBmpContextpath;
	
	@Override
	public String getBmpServiceEndpoint() {
		return this.bornBmpServiceEndpoint;
	}
	
	@Override
	public String getBmpServiceUserDetailsService() {
		return this.bornBmpServiceEndpoint + "/UserDetailsService";
	}
	
	@Override
	public String getBmpServiceProcessService() {
		return this.bornBmpServiceEndpoint + "/ProcessService";
	}
	
	@Override
	public String getBmpServiceSystemResourcesService() {
		return this.bornBmpServiceEndpoint + "/SystemResourcesService";
	}
	
	@Override
	public String getBmpServiceUserOrgService() {
		return this.bornBmpServiceEndpoint + "/UserOrgService";
	}
	
	@Override
	public String getBmpRootUrl() {
		if (Env.isDev()) {
			return sysParameterServiceClient
				.getSysParameterValue(SysParamEnum.SYS_PARAM_FACE_WEB_URL.code())
					+ "/"
					+ bornBmpContextpath;
		} else {
			return "/" + bornBmpContextpath;
		}
	}
	
	@Override
	public String getBmpServiceFlowService() {
		return this.bornBmpServiceEndpoint + "/FlowService";
	}
	
}
