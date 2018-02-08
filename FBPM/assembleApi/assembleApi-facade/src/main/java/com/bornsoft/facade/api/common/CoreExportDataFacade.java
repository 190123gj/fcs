package com.bornsoft.facade.api.common;

import com.bornsoft.pub.order.common.CoreExportDataOrder;
import com.bornsoft.pub.result.common.CoreExportDataResult;

/**
 * 导出充值或提现数据
 * @Title: EzExportDataFacade.java 
 * @Package com.bornsoft.bornfinance.bankroll.api 
 * @author xiaohui@yiji.com   
 * @date 2014-12-17 下午5:11:53 
 * @version V1.0
 */
public interface CoreExportDataFacade {

	/**
	 * 数据导出
	 * @param dataOrder
	 * @return
	 */
	public CoreExportDataResult exportData(CoreExportDataOrder dataOrder);
}
