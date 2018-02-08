package com.born.fcs.rm.ws.service.report;

import java.util.Map;

import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.rm.ws.order.report.ReportOrder;
import com.born.fcs.rm.ws.order.report.ReportQueryOrder;

/**
 * 
 * 报表通用处理接口
 * 
 * @author lirz
 *
 * 2016-8-5 上午10:35:42
 */
public interface ReportProcessService {
	
	/**
	 * 
	 * 报表：数据保存
	 * 
	 * @param order
	 * @return
	 */
	FcsBaseResult save(ReportOrder order);
	
	/**
	 * 按会计期间查询
	 * 
	 * @param queryOrder
	 * @return
	 */
	Object findByAccountPeriod(ReportQueryOrder queryOrder);
	
}
