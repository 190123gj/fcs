package com.born.fcs.face.integration.rm.service.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.page.PageParam;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.rm.ws.info.report.ReportRuleData;
import com.born.fcs.rm.ws.order.report.ReportQueryParam;
import com.born.fcs.rm.ws.order.report.ReportSqlQueryOrder;
import com.born.fcs.rm.ws.service.report.ReportBySqlService;

@Service("reportBySqlClient")
public class ReportBySqlClient extends ClientAutowiredBaseService implements ReportBySqlService {
	
	@Autowired
	ReportBySqlService reportBySqlService;
	
	@Override
	public ReportRuleData getQueryRule(final long sqlId) {
		return callInterface(new CallExternalInterface<ReportRuleData>() {
			
			@Override
			public ReportRuleData call() {
				return reportBySqlService.getQueryRule(sqlId);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<ReportRuleData> getQueryRules(final ReportSqlQueryOrder queryOrder) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<ReportRuleData>>() {
			
			@Override
			public QueryBaseBatchResult<ReportRuleData> call() {
				return reportBySqlService.getQueryRules(queryOrder);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<ReportRuleData> getMyReport(ReportSqlQueryOrder queryOrder) {
		return null;
	}
	
	@Override
	public FcsBaseResult queryReportData(final ReportQueryParam queryParam,
											final ReportRuleData rule) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return reportBySqlService.queryReportData(queryParam, rule);
			}
		});
	}
	
	@Override
	public String getQueryParam(ReportQueryParam queryParam, ReportRuleData rule) {
		return null;
	}
	
	@Override
	public long addQueryRule(final ReportRuleData ruleData) throws Exception {
		return callInterface(new CallExternalInterface<Long>() {
			
			@Override
			public Long call() {
				try {
					return reportBySqlService.addQueryRule(ruleData);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
				return 0L;
			}
		});
	}
	
	@Override
	public long udpateQueryRule(final ReportRuleData ruleData) throws Exception {
		return callInterface(new CallExternalInterface<Long>() {
			
			@Override
			public Long call() {
				try {
					return reportBySqlService.udpateQueryRule(ruleData);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
				return 0L;
			}
		});
	}
	
	@Override
	public long grant2Roles(long reportId, long[] roleIds) throws Exception {
		return 0;
	}
	
	@Override
	public int deleteQueryRule(final long reportId) {
		return callInterface(new CallExternalInterface<Integer>() {
			
			@Override
			public Integer call() {
				return reportBySqlService.deleteQueryRule(reportId);
			}
		});
	}
	
	@Override
	public FcsBaseResult queryReportPage(final ReportQueryParam queryParam,
											final PageParam pageParam, final ReportRuleData rule) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			
			@Override
			public FcsBaseResult call() {
				return reportBySqlService.queryReportPage(queryParam, pageParam, rule);
			}
		});
	}
	
	@Override
	public int refreshData() {
		return 0;
	}
	
}
