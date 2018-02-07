package com.born.fcs.face.integration.pm.service.projectRiskReport;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.info.projectRiskReport.ProjectRiskReportInfo;
import com.born.fcs.pm.ws.order.projectRiskReport.ProjectRiskReportDeleteOrder;
import com.born.fcs.pm.ws.order.projectRiskReport.ProjectRiskReportProcessOrder;
import com.born.fcs.pm.ws.order.projectRiskReport.ProjectRiskReportQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.projectRiskReport.ProjectRiskReportService;
import org.springframework.stereotype.Service;

import java.net.SocketTimeoutException;

/**
 * Created by Administrator on 2016/9/20.
 */
@Service("projectRiskReportClient")
public class ProjectRiskReportClient extends ClientAutowiredBaseService implements ProjectRiskReportService {
    @Override
    public ProjectRiskReportInfo findByReportId(final long l) {
        return callInterface(new CallExternalInterface<ProjectRiskReportInfo>() {
            @Override
            public ProjectRiskReportInfo call() throws SocketTimeoutException {
                return projectRiskReportService.findByReportId(l);
            }
        });
    }

    @Override
    public FcsBaseResult deleteByReportId(final ProjectRiskReportDeleteOrder projectRiskReportDeleteOrder) {
        return callInterface(new CallExternalInterface<FcsBaseResult>() {
            @Override
            public FcsBaseResult call() throws SocketTimeoutException {
                return projectRiskReportService.deleteByReportId(projectRiskReportDeleteOrder);
            }
        });
    }

    @Override
    public QueryBaseBatchResult<ProjectRiskReportInfo> queryProjectRiskReportInfo(final ProjectRiskReportQueryOrder projectRiskReportQueryOrder) {
        return callInterface(new CallExternalInterface<QueryBaseBatchResult<ProjectRiskReportInfo>>() {
            @Override
            public QueryBaseBatchResult<ProjectRiskReportInfo> call() throws SocketTimeoutException {
                return projectRiskReportService.queryProjectRiskReportInfo(projectRiskReportQueryOrder);
            }
        });
    }

    @Override
    public FcsBaseResult save(final ProjectRiskReportProcessOrder projectRiskReportProcessOrder) {
        return callInterface(new CallExternalInterface<FcsBaseResult>() {
            @Override
            public FcsBaseResult call() throws SocketTimeoutException {
                return projectRiskReportService.save(projectRiskReportProcessOrder);
            }
        });
    }
}
