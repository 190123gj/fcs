/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.crm.dal.daointerface;

// auto generated imports
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.born.fcs.crm.dal.dataobject.EvaluetingListForAuditDO;

@SuppressWarnings("rawtypes")
public interface EvaluetingListForAuditDAO {
	
	public EvaluetingListForAuditDO findByFormId(long formId) throws DataAccessException;
	
	public List<EvaluetingListForAuditDO> findWithCondition(EvaluetingListForAuditDO evaluetingList,
															long loginUserId,
															List<Long> deptIdList, long limitStart,
															long pageSize, String likeCustomerName,
															String likeBusiLicenseNo)
																						throws DataAccessException;
	
	public long countWithCondition(EvaluetingListForAuditDO evaluetingList, long loginUserId,
									List<Long> deptIdList, String likeCustomerName,
									String likeBusiLicenseNo) throws DataAccessException;
}