package com.born.fcs.pm.ws.service.report;

import java.util.List;

import javax.jws.WebService;

import com.born.fcs.pm.ws.service.report.order.LoadReportProjectOrder;
import com.born.fcs.pm.ws.service.report.order.PmReportDOQueryOrder;
import com.born.fcs.pm.ws.service.report.result.DataListItem;
import com.born.fcs.pm.ws.service.report.result.ReportDataResult;

@WebService
public interface PmReportService {
	/**
	 * 得到于项目相关的
	 * @param loadReportProjectOrder
	 * @return
	 */
	ReportDataResult getReporData(LoadReportProjectOrder loadReportProjectOrder);
	
	List<DataListItem> doQuery(PmReportDOQueryOrder order);
	
	long doQueryCount(String sql);
	
}
