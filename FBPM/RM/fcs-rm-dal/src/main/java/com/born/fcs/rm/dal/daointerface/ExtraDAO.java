/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.rm.dal.daointerface;

import java.util.Date;

import org.springframework.dao.DataAccessException;

import com.born.fcs.rm.dal.dataobject.AccountBalanceDO;

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
	 * @param name
	 * @param date
	 * @param cacheNumber
	 * @return
	 * @throws DataAccessException
	 */
	long getNextDateSeq(String name, Date date, long cacheNumber) throws DataAccessException;

	/**
	 * @param name
	 * @param date
	 * @param cacheNumber
	 * @return
	 * @throws DataAccessException
	 */
	long getNextYearSeq(String name, Date date, long cacheNumber) throws DataAccessException;

	/**
	 * @param name
	 * @param date
	 * @param cacheNumber
	 * @return
	 * @throws DataAccessException
	 */
	long getNextYearMonthSeq(String name, Date date, long cacheNumber) throws DataAccessException;

	/**
	 * @param name
	 * @param date
	 * @return
	 * @throws DataAccessException
	 */
	long insertDateSeq(String name, Date date) throws DataAccessException;

	/**
	 * @param name
	 * @param date
	 * @return
	 * @throws DataAccessException
	 */
	long insertYearSeq(String name, Date date) throws DataAccessException;

	/**
	 * @param name
	 * @param date
	 * @return
	 * @throws DataAccessException
	 */
	long insertYearMonthSeq(String name, Date date) throws DataAccessException;

	/**
	 *
	 * @param name
	 * @param date
	 * @return
	 * @throws DataAccessException
	 */
	boolean selectDateSeq(String name, Date date) throws DataAccessException;

	boolean selectYearSeq(String name, Date date) throws DataAccessException;

	Long selectYearSeqNum(String name, Date date) throws DataAccessException;

	boolean selectYearMonthSeq(String name, Date date) throws DataAccessException;

	Long selectYearMonthSeqNum(String name, Date date) throws DataAccessException;
	
	/**
	 * 查询最近一期的科目余额表
	 * @return
	 */
	AccountBalanceDO queryLatestAccountBalance();
}
