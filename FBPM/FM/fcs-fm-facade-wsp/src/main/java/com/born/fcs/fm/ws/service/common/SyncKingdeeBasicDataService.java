package com.born.fcs.fm.ws.service.common;

import com.born.fcs.fm.ws.order.common.SyncKingdeeBasicDataOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;

/***
 * 同步基础数据到金蝶
 * @author wuzj
 */
public interface SyncKingdeeBasicDataService {
	
	/***
	 * 同步基础数据到金蝶
	 * @param order
	 * @return
	 */
	FcsBaseResult syncBasicData(SyncKingdeeBasicDataOrder order);
}
