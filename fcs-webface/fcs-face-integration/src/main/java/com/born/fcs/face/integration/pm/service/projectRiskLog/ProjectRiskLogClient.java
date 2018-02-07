package com.born.fcs.face.integration.pm.service.projectRiskLog;

import com.born.fcs.face.integration.service.CallExternalInterface;
import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.info.projectRiskLog.ProjectRiskLogInfo;
import com.born.fcs.pm.ws.order.projectRiskLog.ProjectRiskLogProcessOrder;
import com.born.fcs.pm.ws.order.projectRiskLog.ProjectRiskLogQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.projectRiskLog.ProjectRiskLogService;

import java.net.SocketTimeoutException;

/**
 * Created by wqh on 2016/9/8.
 */
@Service("projectRiskLogClient")
public class ProjectRiskLogClient extends ClientAutowiredBaseService implements
        ProjectRiskLogService {

    @Override
    public ProjectRiskLogInfo findByLogId(final long l) {
        return callInterface(new CallExternalInterface<ProjectRiskLogInfo>() {
            @Override
            public ProjectRiskLogInfo call() throws SocketTimeoutException {
                return projectRiskLogService.findByLogId(l);
            }
        });
    }

    @Override
    public QueryBaseBatchResult<ProjectRiskLogInfo> queryProjectRiskLogInfo(final ProjectRiskLogQueryOrder projectRiskLogQueryOrder) {
        return callInterface(new CallExternalInterface<QueryBaseBatchResult<ProjectRiskLogInfo>>() {
            @Override
            public QueryBaseBatchResult<ProjectRiskLogInfo> call() throws SocketTimeoutException {
                return projectRiskLogService.queryProjectRiskLogInfo(projectRiskLogQueryOrder);
            }
        });
    }

    @Override
    public FcsBaseResult save(final ProjectRiskLogProcessOrder projectRiskLogProcessOrder) {
        return callInterface(new CallExternalInterface<FcsBaseResult>() {
            @Override
            public FcsBaseResult call() throws SocketTimeoutException {
                return projectRiskLogService.save(projectRiskLogProcessOrder);
            }
        });
    }

    @Override
    public FcsBaseResult deleteByLogId(final long l) {
        return callInterface(new CallExternalInterface<FcsBaseResult>() {
            @Override
            public FcsBaseResult call() throws SocketTimeoutException {
                return projectRiskLogService.deleteByLogId(l);
            }
        });
    }
}
