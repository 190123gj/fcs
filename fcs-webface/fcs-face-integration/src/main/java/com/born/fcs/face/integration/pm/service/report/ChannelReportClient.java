package com.born.fcs.face.integration.pm.service.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.service.report.ChannelReportService;
import com.born.fcs.pm.ws.service.report.info.ChannelReportInfo;
import com.born.fcs.pm.ws.service.report.order.ChannelReportQueryOrder;

@Service("channelReportClient")
public class ChannelReportClient extends ClientAutowiredBaseService implements ChannelReportService {
	
	@Autowired
	ChannelReportService channelReportService;
	
	@Override
	public QueryBaseBatchResult<ChannelReportInfo> list(final ChannelReportQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<ChannelReportInfo>>() {
			
			@Override
			public QueryBaseBatchResult<ChannelReportInfo> call() {
				return channelReportService.list(order);
			}
		});
	}
	
}
