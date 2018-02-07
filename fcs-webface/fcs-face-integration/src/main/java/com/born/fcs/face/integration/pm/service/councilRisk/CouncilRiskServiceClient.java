package com.born.fcs.face.integration.pm.service.councilRisk;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.info.councilRisk.CouncilRiskInfo;
import com.born.fcs.pm.ws.order.councilRisk.*;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.councilRisk.CouncilRiskService;
import org.springframework.stereotype.Service;

import java.net.SocketTimeoutException;

/**
 * Created by wqh on 2016/9/9.
 */
@Service("councilRiskServiceClient")
public class CouncilRiskServiceClient extends ClientAutowiredBaseService implements
																		CouncilRiskService {
	@Override
	public CouncilRiskInfo findBySummaryQueryOrder(final CouncilRiskSummaryQueryOrder councilRiskSummaryQueryOrder) {
		return callInterface(new CallExternalInterface<CouncilRiskInfo>() {
			@Override
			public CouncilRiskInfo call() throws SocketTimeoutException {
				return councilRiskService.findBySummaryQueryOrder(councilRiskSummaryQueryOrder);
			}
		});
	}

	@Override
	public CouncilRiskInfo findByCouncilId(final long l) {
		return callInterface(new CallExternalInterface<CouncilRiskInfo>() {
			@Override
			public CouncilRiskInfo call() throws SocketTimeoutException {
				return councilRiskService.findByCouncilId(l);
			}
		});
	}
	
	@Override
	public CouncilRiskInfo findByCouncilIdAndUserId(final long councilId, final long userId) {
		return callInterface(new CallExternalInterface<CouncilRiskInfo>() {
			@Override
			public CouncilRiskInfo call() throws SocketTimeoutException {
				return councilRiskService.findByCouncilIdAndUserId(councilId, userId);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<CouncilRiskInfo> queryCouncilRiskInfo(	final CouncilRiskQueryOrder councilRiskQueryOrder) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<CouncilRiskInfo>>() {
			@Override
			public QueryBaseBatchResult<CouncilRiskInfo> call() throws SocketTimeoutException {
				return councilRiskService.queryCouncilRiskInfo(councilRiskQueryOrder);
			}
		});
	}
	
	@Override
	public FcsBaseResult save(final CouncilRiskProcessOrder councilRiskProcessOrder) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			@Override
			public FcsBaseResult call() throws SocketTimeoutException {
				return councilRiskService.save(councilRiskProcessOrder);
			}
		});
	}
	
	@Override
	public void startCouncilRisk() {
		
	}
	
	@Override
	public FcsBaseResult saveSummary(final AddCouncilRiskSummaryOrder addCouncilRiskSummaryOrder) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			@Override
			public FcsBaseResult call() throws SocketTimeoutException {
				return councilRiskService.saveSummary(addCouncilRiskSummaryOrder);
			}
		});
	}
	
	@Override
	public FcsBaseResult endCouncilRisk(final EndCouncilRiskProcessOrder riskProcessOrder) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			@Override
			public FcsBaseResult call() throws SocketTimeoutException {
				return councilRiskService.endCouncilRisk(riskProcessOrder);
			}
		});
	}
	
	@Override
	public void noticeMonth() {
		
	}
	
	@Override
	public void addNoticeMonth() {
		
	}
}
