package com.born.fcs.fm.integration.pm.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.born.fcs.fm.integration.bpm.service.CallExternalInterface;
import com.born.fcs.fm.integration.bpm.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.service.report.PmReportService;
import com.born.fcs.pm.ws.service.report.field.FcsField;
import com.born.fcs.pm.ws.service.report.order.LoadReportProjectOrder;
import com.born.fcs.pm.ws.service.report.order.PmReportDOQueryOrder;
import com.born.fcs.pm.ws.service.report.result.DataListItem;
import com.born.fcs.pm.ws.service.report.result.ReportDataResult;

/**
 * 
 * 报表生成
 * 
 * @author lirz
 * 
 * 2016-8-11 下午2:41:37
 */
@Service("pmReportServiceClient")
public class PmReportServiceClient extends ClientAutowiredBaseService implements PmReportService {
	
	@Override
	public ReportDataResult getReporData(final LoadReportProjectOrder arg0) {
		return callInterface(new CallExternalInterface<ReportDataResult>() {
			
			@Override
			public ReportDataResult call() {
				return pmReportWebService.getReporData(arg0);
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
	
	public List<DataListItem> doQuery(final String sql, final long limitStart, final long pageSize,
										final HashMap<String, FcsField> fieldMap) {
		PmReportDOQueryOrder monthOrder = new PmReportDOQueryOrder();
		monthOrder.setSql(sql);
		monthOrder.setLimitStart(limitStart);
		monthOrder.setPageSize(pageSize);
		monthOrder.setFieldMap(fieldMap);
		return doQuery(monthOrder);
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
