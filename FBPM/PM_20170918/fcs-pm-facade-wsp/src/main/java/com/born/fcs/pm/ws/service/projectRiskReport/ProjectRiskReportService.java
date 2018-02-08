package com.born.fcs.pm.ws.service.projectRiskReport;

import com.born.fcs.pm.ws.info.projectRiskReport.ProjectRiskReportInfo;
import com.born.fcs.pm.ws.order.projectRiskReport.ProjectRiskReportDeleteOrder;
import com.born.fcs.pm.ws.order.projectRiskReport.ProjectRiskReportProcessOrder;
import com.born.fcs.pm.ws.order.projectRiskReport.ProjectRiskReportQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

import javax.jws.WebService;

/**
 * Created by wqh on 2016/9/20.
 */
@WebService
public interface ProjectRiskReportService {
    ProjectRiskReportInfo findByReportId(long reportId);

    FcsBaseResult deleteByReportId(ProjectRiskReportDeleteOrder deleteOrder);

    QueryBaseBatchResult<ProjectRiskReportInfo> queryProjectRiskReportInfo(ProjectRiskReportQueryOrder queryOrder);

    FcsBaseResult save(ProjectRiskReportProcessOrder processOrder);
}
