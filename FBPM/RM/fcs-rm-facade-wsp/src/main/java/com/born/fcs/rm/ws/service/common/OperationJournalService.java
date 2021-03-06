/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.rm.ws.service.common;

import javax.jws.WebService;

import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.rm.ws.info.common.OperationJournalInfo;
import com.born.fcs.rm.ws.order.common.OperationJournalAddOrder;
import com.born.fcs.rm.ws.order.common.OperationJournalQueryOrder;
import com.born.fcs.rm.ws.result.common.OperationJournalServiceResult;

/**
 * 
 * @Filename OperationJournalService.java
 * 
 * @Description
 * 
 * @Version 1.0
 * 
 * @Author jiajie
 * 
 * @Email hjiajie@yiji.com
 * 
 * @History <li>Author: jiajie</li> <li>Date: 2013-1-8</li> <li>Version: 1.0</li>
 * <li>Content: create</li>
 * 
 */
@WebService
public interface OperationJournalService {
	
	/**
	 * 添加一条操作日志
	 * @param operationJournalAddOrder
	 * @return
	 */
	public OperationJournalServiceResult addOperationJournalInfo(OperationJournalAddOrder operationJournalAddOrder);

    QueryBaseBatchResult<OperationJournalInfo> queryOperationJournalInfo(OperationJournalQueryOrder queryOrder);
	
}
