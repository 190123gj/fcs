package com.born.fcs.pm.ws.service.riskHandleTeam;

import com.born.fcs.pm.ws.info.riskHandleTeam.RiskHandleTeamInfo;
import com.born.fcs.pm.ws.order.riskHandleTeam.RiskHandleTeamProcessOrder;
import com.born.fcs.pm.ws.order.riskHandleTeam.RiskHandlerTeamQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

import javax.jws.WebService;

/**
 * Created by Administrator on 2016/9/8.
 */
@WebService
public interface RiskHandleTeamService {
    RiskHandleTeamInfo findByTeamId(long teamId);

    QueryBaseBatchResult<RiskHandleTeamInfo> queryRiskHandleTeam(RiskHandlerTeamQueryOrder riskHandlerTeamQueryOrder);

    FcsBaseResult save(RiskHandleTeamProcessOrder processOrder);
}
