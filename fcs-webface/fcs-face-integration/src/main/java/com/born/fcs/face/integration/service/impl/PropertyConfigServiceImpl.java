package com.born.fcs.face.integration.service.impl;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.PropertyConfigService;
import com.born.fcs.pm.biz.service.common.SysParameterService;
import com.born.fcs.pm.ws.enums.SysParamEnum;
import com.yjf.common.env.Env;

@Service("propertyConfigService")
public class PropertyConfigServiceImpl implements PropertyConfigService, InitializingBean {
	
	@Autowired
	private SysParameterService sysParameterServiceClient;
	
	@Value("${born.bpm.service.endpoint}")
	private String bornBpmServiceEndpoint;
	
	@Value("${born.bpm.contextpath}")
	private String bornBpmContextpath;

	@Value("${pm.database.title}")
	private String pmDbTitle;

	@Value("${fm.database.title}")
	private String fmDbTitle;

	@Override
	public String getBmpServiceEndpoint() {
		return this.bornBpmServiceEndpoint;
	}
	
	@Override
	public String getBmpServiceUserDetailsService() {
		return this.bornBpmServiceEndpoint + "/UserDetailsService";
	}
	
	@Override
	public String getBmpServiceProcessService() {
		return this.bornBpmServiceEndpoint + "/ProcessService";
	}
	
	@Override
	public String getBmpServiceSystemResourcesService() {
		return this.bornBpmServiceEndpoint + "/SystemResourcesService";
	}
	
	@Override
	public String getBmpServiceUserOrgService() {
		return this.bornBpmServiceEndpoint + "/UserOrgService";
	}
	
	@Override
	public String getBmpRootUrl() {
		if (Env.isDev()) {
			return sysParameterServiceClient
				.getSysParameterValue(SysParamEnum.SYS_PARAM_FACE_WEB_URL.code())
					+ "/"
					+ bornBpmContextpath;
		} else {
			return "/" + bornBpmContextpath;
		}
	}

	@Override
	public String getBmpServiceFlowProcessService() {
		return this.bornBpmServiceEndpoint + "/FlowService";
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if (bornBpmServiceEndpoint != null)
			bornBpmServiceEndpoint = bornBpmServiceEndpoint.trim();
		if (bornBpmContextpath != null)
			bornBpmContextpath = bornBpmContextpath.trim();
	}

	@Override
	public String getPmDataBaseTitile() {
		return this.pmDbTitle;
	}

	@Override
	public String getFmDataBaseTitile() {
		return this.fmDbTitle;
	}
}
