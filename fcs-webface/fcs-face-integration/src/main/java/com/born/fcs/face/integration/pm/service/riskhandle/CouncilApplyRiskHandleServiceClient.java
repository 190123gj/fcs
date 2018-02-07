package com.born.fcs.face.integration.pm.service.riskhandle;

import java.net.SocketTimeoutException;

import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.base.QueryProjectBase;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.council.CouncilApplyRiskHandleInfo;
import com.born.fcs.pm.ws.info.council.FCouncilApplyRiskHandleInfo;
import com.born.fcs.pm.ws.order.common.ProjectQueryOrder;
import com.born.fcs.pm.ws.order.council.CouncilApplyRiskHandleQueryOrder;
import com.born.fcs.pm.ws.order.council.FCouncilApplyRiskHandleOrder;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.council.CouncilApplyRiskHandleService;

/**
 * 
 * 风险处置上会申报
 * 
 * 
 * @author lirz
 * 
 * 2016-5-9 下午5:50:29
 */
@Service("councilApplyRiskHandleServiceClient")
public class CouncilApplyRiskHandleServiceClient extends ClientAutowiredBaseService implements
																					CouncilApplyRiskHandleService {
	
	@Override
	public FormBaseResult saveCouncilApplyRiskHandle(final FCouncilApplyRiskHandleOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			
			@Override
			public FormBaseResult call() throws SocketTimeoutException {
				return councilApplyRiskHandleWebService.saveCouncilApplyRiskHandle(order);
			}
		});
	}
	
	@Override
	public FCouncilApplyRiskHandleInfo findById(final long id) {
		return callInterface(new CallExternalInterface<FCouncilApplyRiskHandleInfo>() {
			
			@Override
			public FCouncilApplyRiskHandleInfo call() throws SocketTimeoutException {
				return councilApplyRiskHandleWebService.findById(id);
			}
		});
	}
	
	@Override
	public FCouncilApplyRiskHandleInfo findByFormId(final long formId) {
		return callInterface(new CallExternalInterface<FCouncilApplyRiskHandleInfo>() {
			
			@Override
			public FCouncilApplyRiskHandleInfo call() throws SocketTimeoutException {
				return councilApplyRiskHandleWebService.findByFormId(formId);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<CouncilApplyRiskHandleInfo> queryList(	final CouncilApplyRiskHandleQueryOrder queryOrder) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<CouncilApplyRiskHandleInfo>>() {
			
			@Override
			public QueryBaseBatchResult<CouncilApplyRiskHandleInfo> call()
																			throws SocketTimeoutException {
				return councilApplyRiskHandleWebService.queryList(queryOrder);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<ProjectInfo> queryProject(final QueryProjectBase queryOrder) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<ProjectInfo>>() {
			
			@Override
			public QueryBaseBatchResult<ProjectInfo> call() throws SocketTimeoutException {
				return councilApplyRiskHandleWebService.queryProject(queryOrder);
			}
		});
	}

	@Override
	public QueryBaseBatchResult<ProjectInfo> queryNoRepayProject(final ProjectQueryOrder queryOrder) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<ProjectInfo>>() {
			
			@Override
			public QueryBaseBatchResult<ProjectInfo> call() throws SocketTimeoutException {
				return councilApplyRiskHandleWebService.queryNoRepayProject(queryOrder);
			}
		});
	}
	
}
