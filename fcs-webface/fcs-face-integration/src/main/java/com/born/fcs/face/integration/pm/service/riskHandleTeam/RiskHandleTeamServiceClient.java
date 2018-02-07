package com.born.fcs.face.integration.pm.service.riskHandleTeam;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.info.riskHandleTeam.RiskHandleTeamInfo;
import com.born.fcs.pm.ws.order.riskHandleTeam.RiskHandleTeamProcessOrder;
import com.born.fcs.pm.ws.order.riskHandleTeam.RiskHandlerTeamQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.riskHandleTeam.RiskHandleTeamService;
import org.springframework.stereotype.Service;

import java.net.SocketTimeoutException;

/**
 * Created by Administrator on 2016/9/8.
 */
@Service("riskHandleTeamServiceClient")
public class RiskHandleTeamServiceClient extends ClientAutowiredBaseService implements
        RiskHandleTeamService {

    @Override
    public RiskHandleTeamInfo findByTeamId(final long teamId) {
        return callInterface(new CallExternalInterface<RiskHandleTeamInfo>() {
            @Override
            public RiskHandleTeamInfo call() throws SocketTimeoutException {
                return riskHandleTeamService.findByTeamId(teamId);
            }
        });
    }

    @Override
    public QueryBaseBatchResult<RiskHandleTeamInfo> queryRiskHandleTeam(final RiskHandlerTeamQueryOrder riskHandlerTeamQueryOrder) {
        return callInterface(new CallExternalInterface<QueryBaseBatchResult<RiskHandleTeamInfo>>() {
            @Override
            public QueryBaseBatchResult<RiskHandleTeamInfo> call() throws SocketTimeoutException {
                return riskHandleTeamService.queryRiskHandleTeam(riskHandlerTeamQueryOrder) ;
            }
        });
    }

    @Override
    public FcsBaseResult save(final RiskHandleTeamProcessOrder riskHandleTeamProcessOrder) {
        return callInterface(new CallExternalInterface<FcsBaseResult>() {
            @Override
            public FcsBaseResult call() throws SocketTimeoutException {
                return riskHandleTeamService.save(riskHandleTeamProcessOrder);
            }
        });
    }
}
