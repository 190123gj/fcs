/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.crm.dal.daointerface;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.born.fcs.crm.dal.dataobject.BackTaskDO;
import com.born.fcs.crm.dal.dataobject.BusyRegionDO;

/**
 * @Filename ExtraDAO.java
 * @Description 手动写的sql
 * @Version 1.0
 * @Author peigen
 * @Email peigen@yiji.com
 * @History <li>Author: peigen</li> <li>Date: 2011-9-6</li> <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
public interface ExtraDAO {
	
	/**
	 * 获取系统时间
	 * 
	 * @return
	 */
	public Date getSysdate();
	
	/**
	 * 驳回的任务列表
	 * @param userId
	 * @return
	 * @throws DataAccessException
	 */
	List<BackTaskDO> backTaskList(long userId) throws DataAccessException;
	
	/***
	 * 按地域统计客户
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings("rawtypes")
	HashMap statisticsCustomerRegion() throws DataAccessException;
	
	/***
	 * 按部门统计客户
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings("rawtypes")
	HashMap statisticsCustomerDept() throws DataAccessException;
	
	/***
	 * 统计客户所有部门
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings("rawtypes")
	HashMap allCustomerDept() throws DataAccessException;
	
	/***
	 * 按部门查询业务区域
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings("rawtypes")
	List<BusyRegionDO> queryByDepPath(String depPath) throws DataAccessException;
}
