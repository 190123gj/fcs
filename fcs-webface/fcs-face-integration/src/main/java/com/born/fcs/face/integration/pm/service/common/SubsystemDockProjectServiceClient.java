package com.born.fcs.face.integration.pm.service.common;

import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.info.common.SubsystemDockProjectInfo;
import com.born.fcs.pm.ws.order.common.SubsystemDockProjectOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.service.common.SubsystemDockProjectService;

@Service("subsystemDockProjectClient")
public class SubsystemDockProjectServiceClient extends ClientAutowiredBaseService implements
																					SubsystemDockProjectService {
	
	@Override
	public SubsystemDockProjectInfo findByProjectCodeAndDockFormType(final String projectCode,
																		final String dockFormType) {
		return callInterface(new CallExternalInterface<SubsystemDockProjectInfo>() {
			
			@Override
			public SubsystemDockProjectInfo call() {
				return subsystemDockProjectWebService.findByProjectCodeAndDockFormType(projectCode,
					dockFormType);
			}
		});
	}
	
	@Override
	public int deleteByProjectCodeAndDockFormType(final String projectCode,
													final String dockFormType) {
		return callInterface(new CallExternalInterface<Integer>() {
			
			@Override
			public Integer call() {
				return subsystemDockProjectWebService.deleteByProjectCodeAndDockFormType(
					projectCode, dockFormType);
			}
		});
	}
	
	@Override
	public FcsBaseResult save(final SubsystemDockProjectOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return subsystemDockProjectWebService.save(order);
			}
		});
	}
	
}
