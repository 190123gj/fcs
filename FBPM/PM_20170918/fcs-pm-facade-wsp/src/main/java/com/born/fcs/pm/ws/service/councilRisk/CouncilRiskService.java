package com.born.fcs.pm.ws.service.councilRisk;

import javax.jws.WebService;

import com.born.fcs.pm.ws.info.councilRisk.CouncilRiskInfo;
import com.born.fcs.pm.ws.order.councilRisk.*;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * Created by wqh on 2016/9/9.
 */
@WebService
public interface CouncilRiskService {
	CouncilRiskInfo findByCouncilId(long councilId);
	
	CouncilRiskInfo findByCouncilIdAndUserId(long councilId, long userId);

	CouncilRiskInfo findBySummaryQueryOrder(CouncilRiskSummaryQueryOrder queryOrder);

	
	QueryBaseBatchResult<CouncilRiskInfo> queryCouncilRiskInfo(CouncilRiskQueryOrder riskQueryOrder);
	
	FcsBaseResult save(CouncilRiskProcessOrder processOrder);
	
	FcsBaseResult saveSummary(AddCouncilRiskSummaryOrder riskSummaryOrder);
	
	FcsBaseResult endCouncilRisk(EndCouncilRiskProcessOrder riskProcessOrder);
	
	void startCouncilRisk();
	
	void noticeMonth();
	
	void addNoticeMonth();
}
