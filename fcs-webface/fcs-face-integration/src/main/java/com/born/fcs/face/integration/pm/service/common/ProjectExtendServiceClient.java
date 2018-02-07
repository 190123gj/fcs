package com.born.fcs.face.integration.pm.service.common;

import java.util.List;

import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.enums.ProjectExtendPropertyEnum;
import com.born.fcs.pm.ws.info.common.ProjectExtendInfo;
import com.born.fcs.pm.ws.order.common.ProjectExtendFormOrder;
import com.born.fcs.pm.ws.order.common.ProjectExtendOrder;
import com.born.fcs.pm.ws.order.common.ProjectExtendQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.common.ProjectExtendService;

@Service("projectExtendServiceClient")
public class ProjectExtendServiceClient extends ClientAutowiredBaseService implements
																			ProjectExtendService {
	
	@Override
	public FcsBaseResult saveExtendInfo(final ProjectExtendOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return projectExtendWebService.saveExtendInfo(order);
			}
		});
	}
	
	@Override
	public FormBaseResult saveExtendInfoWithForm(final ProjectExtendFormOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			
			@Override
			public FormBaseResult call() {
				return projectExtendWebService.saveExtendInfoWithForm(order);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<ProjectExtendInfo> query(final ProjectExtendQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<ProjectExtendInfo>>() {
			
			@Override
			public QueryBaseBatchResult<ProjectExtendInfo> call() {
				return projectExtendWebService.query(order);
			}
		});
	}
	
	@Override
	public List<ProjectExtendInfo> findApprovalProjectExtendInfo(final ProjectExtendQueryOrder order) {
		return callInterface(new CallExternalInterface<List<ProjectExtendInfo>>() {
			
			@Override
			public List<ProjectExtendInfo> call() {
				return projectExtendWebService.findApprovalProjectExtendInfo(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult deleteByProjectCode(final String projectCode) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return projectExtendWebService.deleteByProjectCode(projectCode);
			}
		});
	}
	
	@Override
	public FcsBaseResult deleteByProjectCodeAndProperty(final String projectCode,
														final ProjectExtendPropertyEnum property) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return projectExtendWebService
					.deleteByProjectCodeAndProperty(projectCode, property);
			}
		});
	}
	
}
