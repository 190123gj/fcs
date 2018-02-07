package com.born.fcs.face.integration.pm.service.report;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.service.report.PmReportService;
import com.born.fcs.pm.ws.service.report.order.LoadReportProjectOrder;
import com.born.fcs.pm.ws.service.report.order.PmReportDOQueryOrder;
import com.born.fcs.pm.ws.service.report.result.DataListItem;
import com.born.fcs.pm.ws.service.report.result.ReportDataResult;

@Service("pmReportServiceClient")
public class PmReportServiceClient extends ClientAutowiredBaseService implements PmReportService {
	
	@Autowired
	PmReportService pmReportWebService;
	
	@Override
	public ReportDataResult getReporData(final LoadReportProjectOrder loadReportProjectOrder) {
		return callInterface(new CallExternalInterface<ReportDataResult>() {
			
			@Override
			public ReportDataResult call() {
				return pmReportWebService.getReporData(loadReportProjectOrder);
			}
		});
	}
	
	@Override
	public List<DataListItem> doQuery(final PmReportDOQueryOrder order) {
		return callInterface(new CallExternalInterface<List<DataListItem>>() {
			
			@Override
			public List<DataListItem> call() {
				return pmReportWebService.doQuery(order);
			}
		});
	}
	
	@Override
	public long doQueryCount(final String sql) {
		return callInterface(new CallExternalInterface<Long>() {
			
			@Override
			public Long call() {
				return pmReportWebService.doQueryCount(sql);
			}
		});
	}
	
}
