package com.born.fcs.face.integration.pm.service.common;

import java.util.List;

import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.enums.ChannelRelationEnum;
import com.born.fcs.pm.ws.info.common.ProjectChannelRelationInfo;
import com.born.fcs.pm.ws.order.common.ProjectChannelRelationAmountOrder;
import com.born.fcs.pm.ws.order.common.ProjectChannelRelationBatchOrder;
import com.born.fcs.pm.ws.order.common.ProjectChannelRelationQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.common.ProjectChannelRelationService;

@Service("projectChannelRelationServiceClient")
public class ProjectChannelRelationServiceClient extends ClientAutowiredBaseService implements
																					ProjectChannelRelationService {
	
	@Override
	public FcsBaseResult saveByBizNoAndType(final ProjectChannelRelationBatchOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return projectChannelRelationWebService.saveByBizNoAndType(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult updateRelatedAmount(final ProjectChannelRelationAmountOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return projectChannelRelationWebService.updateRelatedAmount(order);
			}
		});
	}
	
	@Override
	public List<ProjectChannelRelationInfo> queryByBizNoAndType(final String bizNo,
																final ChannelRelationEnum type) {
		return callInterface(new CallExternalInterface<List<ProjectChannelRelationInfo>>() {
			
			@Override
			public List<ProjectChannelRelationInfo> call() {
				return projectChannelRelationWebService.queryByBizNoAndType(bizNo, type);
			}
		});
	}
	
	@Override
	public List<ProjectChannelRelationInfo> queryCapitalChannel(final String projectCode) {
		return callInterface(new CallExternalInterface<List<ProjectChannelRelationInfo>>() {
			
			@Override
			public List<ProjectChannelRelationInfo> call() {
				return projectChannelRelationWebService.queryCapitalChannel(projectCode);
			}
		});
	}
	
	@Override
	public ProjectChannelRelationInfo queryProjectChannel(final String projectCode) {
		return callInterface(new CallExternalInterface<ProjectChannelRelationInfo>() {
			
			@Override
			public ProjectChannelRelationInfo call() {
				return projectChannelRelationWebService.queryProjectChannel(projectCode);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<ProjectChannelRelationInfo> query(	final ProjectChannelRelationQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<ProjectChannelRelationInfo>>() {
			
			@Override
			public QueryBaseBatchResult<ProjectChannelRelationInfo> call() {
				return projectChannelRelationWebService.query(order);
			}
		});
	}
	
}
