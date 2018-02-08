package com.born.fcs.pm.ws.service.projectRiskLog;

import javax.jws.WebService;

import com.born.fcs.pm.ws.info.projectRiskLog.ProjectRiskLogInfo;
import com.born.fcs.pm.ws.info.riskHandleTeam.RiskHandleTeamInfo;
import com.born.fcs.pm.ws.order.projectRiskLog.ProjectRiskLogProcessOrder;
import com.born.fcs.pm.ws.order.projectRiskLog.ProjectRiskLogQueryOrder;
import com.born.fcs.pm.ws.order.riskHandleTeam.RiskHandleTeamProcessOrder;
import com.born.fcs.pm.ws.order.riskHandleTeam.RiskHandlerTeamQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * Created by Administrator on 2016/9/8.
 */
@WebService
public interface ProjectRiskLogService {
    ProjectRiskLogInfo findByLogId(long logId);

    FcsBaseResult deleteByLogId(long logId);

    QueryBaseBatchResult<ProjectRiskLogInfo> queryProjectRiskLogInfo(ProjectRiskLogQueryOrder riskLogQueryOrder);

    FcsBaseResult save(ProjectRiskLogProcessOrder processOrder);
}
