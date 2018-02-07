package com.born.fcs.face.integration.pm.service.virtual;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.info.virtualproject.VirtualProjectDetailInfo;
import com.born.fcs.pm.ws.info.virtualproject.VirtualProjectInfo;
import com.born.fcs.pm.ws.order.virtualproject.VirtualProjectDeleteOrder;
import com.born.fcs.pm.ws.order.virtualproject.VirtualProjectOrder;
import com.born.fcs.pm.ws.order.virtualproject.VirtualProjectQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.virtualproject.VirtualProjectService;

@Service("virtualProjectServiceClient")
public class VirtualprojectServiceClient extends ClientAutowiredBaseService implements
																			VirtualProjectService {
	
	@Autowired
	VirtualProjectService virtualProjectWebService;
	
	@Override
	public FcsBaseResult save(final VirtualProjectOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return virtualProjectWebService.save(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult delete(final VirtualProjectDeleteOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return virtualProjectWebService.delete(order);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<VirtualProjectInfo> query(final VirtualProjectQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<VirtualProjectInfo>>() {
			
			@Override
			public QueryBaseBatchResult<VirtualProjectInfo> call() {
				return virtualProjectWebService.query(order);
			}
		});
	}
	
	@Override
	public VirtualProjectInfo findById(final long virtualId) {
		return callInterface(new CallExternalInterface<VirtualProjectInfo>() {
			
			@Override
			public VirtualProjectInfo call() {
				return virtualProjectWebService.findById(virtualId);
			}
		});
	}
	
	@Override
	public VirtualProjectInfo findByProjectCode(final String projectCode) {
		return callInterface(new CallExternalInterface<VirtualProjectInfo>() {
			
			@Override
			public VirtualProjectInfo call() {
				return virtualProjectWebService.findByProjectCode(projectCode);
			}
		});
	}
	
	@Override
	public List<VirtualProjectDetailInfo> findDetailByVirtualId(final long virtualId) {
		return callInterface(new CallExternalInterface<List<VirtualProjectDetailInfo>>() {
			
			@Override
			public List<VirtualProjectDetailInfo> call() {
				return virtualProjectWebService.findDetailByVirtualId(virtualId);
			}
		});
	}
	
	@Override
	public List<VirtualProjectDetailInfo> findDetailByVirtualProjectCode(	final String virtualProjectCode) {
		return callInterface(new CallExternalInterface<List<VirtualProjectDetailInfo>>() {
			
			@Override
			public List<VirtualProjectDetailInfo> call() {
				return virtualProjectWebService.findDetailByVirtualProjectCode(virtualProjectCode);
			}
		});
	}
}
