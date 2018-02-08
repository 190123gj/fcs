/**
 * www.yiji.com Inc.
 * Copyright (c) 2013 All Rights Reserved.
 */
package com.bornsoft.jck.dal.daointerface;

import java.util.List;

import com.bornsoft.jck.dal.dataobject.SmsSendRecordDO;


public interface SmsSendRecordDAO extends SmsSendRecordDAOAbstract{
	public List<SmsSendRecordDO> smsSendRecordQuery(String mobile, String startTime,
			String endTime,int pageSize, int pageNum);
	
	public int smsSendRecordCount(String mobile, String startTime,
			String endTime);
}