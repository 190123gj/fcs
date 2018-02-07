package com.born.fcs.face.integration.pm.service.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.order.projectcreditcondition.FCreditConditionConfirmQueryOrder;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.report.ProjectReportService;
import com.born.fcs.pm.ws.service.report.info.AmountRecordInfo;
import com.born.fcs.pm.ws.service.report.info.ProjectChargeDetailInfo;
import com.born.fcs.pm.ws.service.report.info.ProjectPayDetailInfo;
import com.born.fcs.pm.ws.service.report.info.ProjectRepayDetailInfo;
import com.born.fcs.pm.ws.service.report.order.ProjectChargeDetailQueryOrder;
import com.born.fcs.pm.ws.service.report.order.ProjectPayDetailQueryOrder;
import com.born.fcs.pm.ws.service.report.order.ProjectRepayDetailQueryOrder;

@Service("projectReportServiceClient")
public class ProjectReportServiceClient extends ClientAutowiredBaseService implements
																			ProjectReportService {
	
	@Autowired
	ProjectReportService projectReportWebService;
	
	@Override
	public QueryBaseBatchResult<ProjectRepayDetailInfo> projectRepayDetail(	final ProjectRepayDetailQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<ProjectRepayDetailInfo>>() {
			
			@Override
			public QueryBaseBatchResult<ProjectRepayDetailInfo> call() {
				return projectReportWebService.projectRepayDetail(order);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<ProjectPayDetailInfo> projectPayDetail(	final ProjectPayDetailQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<ProjectPayDetailInfo>>() {
			
			@Override
			public QueryBaseBatchResult<ProjectPayDetailInfo> call() {
				return projectReportWebService.projectPayDetail(order);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<ProjectChargeDetailInfo> projectChargeDetail(	final ProjectChargeDetailQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<ProjectChargeDetailInfo>>() {
			
			@Override
			public QueryBaseBatchResult<ProjectChargeDetailInfo> call() {
				return projectReportWebService.projectChargeDetail(order);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<AmountRecordInfo> queryRepayRecord(	final FCreditConditionConfirmQueryOrder queryOrder) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<AmountRecordInfo>>() {
			
			@Override
			public QueryBaseBatchResult<AmountRecordInfo> call() {
				return projectReportWebService.queryRepayRecord(queryOrder);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<AmountRecordInfo> queryLoanRecord(	final FCreditConditionConfirmQueryOrder queryOrder) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<AmountRecordInfo>>() {
			
			@Override
			public QueryBaseBatchResult<AmountRecordInfo> call() {
				return projectReportWebService.queryLoanRecord(queryOrder);
			}
		});
	}
	
}
