/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午3:37:00 创建
 */
package com.born.fcs.face.integration.pm.service.recovery;

import java.util.List;

import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.revovery.ProjectRecoveryInfo;
import com.born.fcs.pm.ws.info.revovery.ProjectRecoveryListInfo;
import com.born.fcs.pm.ws.order.common.ProjectQueryOrder;
import com.born.fcs.pm.ws.order.recovery.ProjectRecoveryLitigationBeforePreservationPrecautionOrder;
import com.born.fcs.pm.ws.order.recovery.ProjectRecoveryLitigationBeforeTrailOrder;
import com.born.fcs.pm.ws.order.recovery.ProjectRecoveryOrder;
import com.born.fcs.pm.ws.order.recovery.ProjectRecoveryQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.recovery.ProjectRecoveryResult;
import com.born.fcs.pm.ws.service.recovery.ProjectRecoveryService;

/**
 * 
 * 
 * @author hjiajie
 * 
 */
@Service("projectRecoveryServiceClient")
public class ProjectRecoveryServiceClient extends ClientAutowiredBaseService implements
																			ProjectRecoveryService {
	
	/**
	 * @param order
	 * @return
	 * @see com.born.fcs.pm.ws.service.recovery.ProjectRecoveryService#save(com.born.fcs.pm.ws.order.recovery.ProjectRecoveryOrder)
	 */
	@Override
	public FcsBaseResult save(final ProjectRecoveryOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return projectRecoveryWebService.save(order);
			}
		});
	}
	
	/**
	 * @param recoveryId
	 * @return
	 * @see com.born.fcs.pm.ws.service.recovery.ProjectRecoveryService#findById(java.lang.Long)
	 */
	@Override
	public ProjectRecoveryResult findById(final Long recoveryId, final boolean queryContent) {
		return callInterface(new CallExternalInterface<ProjectRecoveryResult>() {
			
			@Override
			public ProjectRecoveryResult call() {
				return projectRecoveryWebService.findById(recoveryId, queryContent);
			}
		});
	}
	
	@Override
	public List<ProjectRecoveryInfo> findByAppendRecoveryId(final long appendRecoveryId,
															final boolean queryContent) {
		return callInterface(new CallExternalInterface<List<ProjectRecoveryInfo>>() {
			
			@Override
			public List<ProjectRecoveryInfo> call() {
				return projectRecoveryWebService.findByAppendRecoveryId(appendRecoveryId,
					queryContent);
			}
		});
	}
	
	@Override
	public List<ProjectRecoveryInfo> findByProjectCode(final String projectCode,
														final boolean queryContent) {
		return callInterface(new CallExternalInterface<List<ProjectRecoveryInfo>>() {
			
			@Override
			public List<ProjectRecoveryInfo> call() {
				return projectRecoveryWebService.findByProjectCode(projectCode, queryContent);
			}
		});
	}
	
	/**
	 * @param projectRecoveryQueryOrder
	 * @return
	 * @see com.born.fcs.pm.ws.service.recovery.ProjectRecoveryService#queryRecovery(com.born.fcs.pm.ws.order.recovery.ProjectRecoveryQueryOrder)
	 */
	@Override
	public QueryBaseBatchResult<ProjectRecoveryListInfo> queryRecovery(	final ProjectRecoveryQueryOrder projectRecoveryQueryOrder) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<ProjectRecoveryListInfo>>() {
			
			@Override
			public QueryBaseBatchResult<ProjectRecoveryListInfo> call() {
				return projectRecoveryWebService.queryRecovery(projectRecoveryQueryOrder);
			}
		});
	}
	
	/**
	 * @param order
	 * @return
	 * @see com.born.fcs.pm.ws.service.recovery.ProjectRecoveryService#closeProjectRecovery(com.born.fcs.pm.ws.order.recovery.ProjectRecoveryOrder)
	 */
	@Override
	public FcsBaseResult closeProjectRecovery(final ProjectRecoveryOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return projectRecoveryWebService.closeProjectRecovery(order);
			}
		});
	}
	
	/**
	 * @param order
	 * @return
	 * @see com.born.fcs.pm.ws.service.recovery.ProjectRecoveryService#closeProjectRecoverySure(com.born.fcs.pm.ws.order.recovery.ProjectRecoveryOrder)
	 */
	@Override
	public FcsBaseResult closeProjectRecoverySure(final ProjectRecoveryOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return projectRecoveryWebService.closeProjectRecoverySure(order);
			}
		});
	}
	
	/**
	 * @param recoveryId
	 * @return
	 * @see com.born.fcs.pm.ws.service.recovery.ProjectRecoveryService#queryNoticeLetter(java.lang.Long)
	 */
	@Override
	public ProjectRecoveryResult queryNoticeLetter(final Long recoveryId) {
		return callInterface(new CallExternalInterface<ProjectRecoveryResult>() {
			
			@Override
			public ProjectRecoveryResult call() {
				return projectRecoveryWebService.queryNoticeLetter(recoveryId);
			}
		});
	}
	
	/**
	 * @param order
	 * @return
	 * @see com.born.fcs.pm.ws.service.recovery.ProjectRecoveryService#saveNoticeLetter(com.born.fcs.pm.ws.order.recovery.ProjectRecoveryOrder)
	 */
	@Override
	public ProjectRecoveryResult saveNoticeLetter(final ProjectRecoveryOrder order) {
		return callInterface(new CallExternalInterface<ProjectRecoveryResult>() {
			
			@Override
			public ProjectRecoveryResult call() {
				return projectRecoveryWebService.saveNoticeLetter(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult changeBeforeTrailNotice(	final ProjectRecoveryLitigationBeforeTrailOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return projectRecoveryWebService.changeBeforeTrailNotice(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult changePreservationPrecautionNotice(final ProjectRecoveryLitigationBeforePreservationPrecautionOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return projectRecoveryWebService.changePreservationPrecautionNotice(order);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<ProjectInfo> recoveryProject(final ProjectQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<ProjectInfo>>() {
			
			@Override
			public QueryBaseBatchResult<ProjectInfo> call() {
				return projectRecoveryWebService.recoveryProject(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult endProjectRecovery(final ProjectRecoveryOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return projectRecoveryWebService.endProjectRecovery(order);
			}
		});
	}
}
