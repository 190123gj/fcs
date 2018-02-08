package com.bornsoft.facade.api.report;

import javax.jws.WebService;

import com.bornsoft.facade.order.OutApiRequestLogQueryOrder;
import com.bornsoft.facade.order.SmsSendQueryOrder;
import com.bornsoft.facade.result.OutApiRequestLogQueryResult;
import com.bornsoft.facade.result.OutApiRequestLogReportResult;
import com.bornsoft.facade.result.SmsSendQueryResult;

@WebService
public interface OutApiRequestLogFacade {
	
	/**
	 * 分页查询外部接口调用记录
	 * @param order
	 * @return
	 */
	public OutApiRequestLogQueryResult queryOutApiInvokeLogList(OutApiRequestLogQueryOrder order);
	
	/**
	 * 外部接口调用统计
	 * @param order
	 * @return
	 */
	public OutApiRequestLogReportResult outApiInvokeLogReport(
			OutApiRequestLogQueryOrder order);
	
	public SmsSendQueryResult smsSendQueryPage(SmsSendQueryOrder order);

}
