package com.born.fcs.pm.ws.service.report;

import javax.jws.WebService;

import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.report.info.ChannelReportInfo;
import com.born.fcs.pm.ws.service.report.order.ChannelReportQueryOrder;

/**
 * 渠道报表
 * */
@WebService
public interface ChannelReportService {
	/** face 客户管理报表查询 */
	QueryBaseBatchResult<ChannelReportInfo> list(ChannelReportQueryOrder order);
	
}
