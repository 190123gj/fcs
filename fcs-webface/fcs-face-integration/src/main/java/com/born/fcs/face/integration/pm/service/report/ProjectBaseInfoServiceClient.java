package com.born.fcs.face.integration.pm.service.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.info.report.ProjectInfoBaseInfo;
import com.born.fcs.pm.ws.service.report.ProjectBaseInfoService;
import com.born.fcs.pm.ws.service.report.order.ProjectBaseInfoQueryOrder;

@Service("projectBaseInfoServiceClient")
public class ProjectBaseInfoServiceClient extends ClientAutowiredBaseService implements
																			ProjectBaseInfoService {
	
	@Autowired
	ProjectBaseInfoService projectBaseInfoWebService;
	
	@Override
	public ProjectInfoBaseInfo queryProjectBaseInfo(final ProjectBaseInfoQueryOrder queryOrder) {
		return callInterface(new CallExternalInterface<ProjectInfoBaseInfo>() {
			
			@Override
			public ProjectInfoBaseInfo call() {
				return projectBaseInfoWebService.queryProjectBaseInfo(queryOrder);
			}
		});
	}
	
}
