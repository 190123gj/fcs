/**
 * www.yiji.com Inc.
 * Copyright (c) 2013 All Rights Reserved.
 */
package com.bornsoft.jck.dal.daointerface;

import java.util.List;

import com.bornsoft.jck.dal.dataobject.ApixVeryLogDO;
import com.bornsoft.jck.dal.dataobject.ApixVeryLogReport;

public interface ApixVeryLogDAO extends ApixVeryLogDAOAbstract {
	public List<ApixVeryLogDO> queryApiLogForPage(String serviceName,
			String startTime, String endTime, int pageNum, int pageSize);

	public int countApiLogForPage(String serviceName, String startTime,
			String endTime);

	public List<ApixVeryLogReport> queryApiLogReport(String serviceName,
			String startTime, String endTime);
}