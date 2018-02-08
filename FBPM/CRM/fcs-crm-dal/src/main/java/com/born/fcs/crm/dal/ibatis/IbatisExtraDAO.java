/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.crm.dal.ibatis;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.born.fcs.crm.dal.daointerface.ExtraDAO;
import com.born.fcs.crm.dal.dataobject.BackTaskDO;
import com.born.fcs.crm.dal.dataobject.BusyRegionDO;

/**
 * 
 * @Filename IbatisExtraDAO.java
 * 
 * @Description
 * 
 * @Version 1.0
 * 
 * @Author peigen
 * 
 * @Email peigen@yiji.com
 * 
 * @History <li>Author: peigen</li> <li>Date: 2011-9-6</li> <li>Version: 1.0</li>
 * <li>Content: create</li>
 * 
 */
@SuppressWarnings("deprecation")
public class IbatisExtraDAO extends SqlMapClientDaoSupport implements ExtraDAO {
	
	/**
	 * @return
	 * @see com.yjf.paycore.dal.daointerface.ExtraDAO#getSysdate()
	 */
	@Override
	public Date getSysdate() {
		return (Date) getSqlMapClientTemplate().queryForObject("MS-COMMON-GET-SYSDATE");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BackTaskDO> backTaskList(long userId) throws DataAccessException {
		List<BackTaskDO> list = getSqlMapClientTemplate().queryForList("MS-BUSI-BACK-TASK", userId);
		return list;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public HashMap statisticsCustomerDept() throws DataAccessException {
		return (HashMap) getSqlMapClientTemplate().queryForMap("MS-EXTRA-STATISTICS-CUSTOMER-DEPT",
			null, "dept", "count");
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public HashMap statisticsCustomerRegion() throws DataAccessException {
		return (HashMap) getSqlMapClientTemplate().queryForMap(
			"MS-EXTRA-STATISTICS-CUSTOMER-REGION", null, "region", "count");
	}
	
	@Override
	public List<BusyRegionDO> queryByDepPath(String depPath) throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("MS-BUSY-REGION-FIND-ALL-BY-DEP-PATH",
			depPath);
	}
	
	@Override
	public HashMap allCustomerDept() throws DataAccessException {
		return (HashMap) getSqlMapClientTemplate().queryForMap("MS-EXTRA-STATISTICS-ALL-DEPT",
			null, "dept", "orgId");
	}
}
