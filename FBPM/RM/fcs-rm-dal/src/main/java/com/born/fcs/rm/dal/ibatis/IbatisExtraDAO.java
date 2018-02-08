/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.rm.dal.ibatis;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.rm.dal.daointerface.ExtraDAO;
import com.born.fcs.rm.dal.dataobject.AccountBalanceDO;
import com.yjf.common.lang.util.StringUtil;

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

	/**
	 * @param name
	 * @return
	 * @throws DataAccessException
	 * @see com.yjf.paycore.dal.daointerface.ExtraDAO#getNextSeq(java.lang.String)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public long insertDateSeq(String name, Date date) throws DataAccessException {

		if (StringUtil.isBlank(name)) {
			throw new IllegalArgumentException("Can't insert a null data object into db.");
		}

		Map paramMap = new HashMap<String, Object>();
		paramMap.put("name", name);
		paramMap.put("seqDate", DateUtil.formatDay(date));
		paramMap.put("rawAddTime", date);
		return (Long) getSqlMapClientTemplate().insert("MS-EXTRA-SYS-DATE-SEQ-INSERT", paramMap);
	}

	/**
	 * @param name
	 * @return
	 * @throws DataAccessException
	 * @see com.yjf.paycore.dal.daointerface.ExtraDAO#getNextSeq(java.lang.String)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public long insertYearSeq(String name, Date date) throws DataAccessException {

		if (StringUtil.isBlank(name)) {
			throw new IllegalArgumentException("Can't insert a null data object into db.");
		}

		Map paramMap = new HashMap<String, Object>();
		paramMap.put("name", name);
		paramMap.put("seqDate", DateUtil.formatYear(date));
		paramMap.put("rawAddTime", date);
		return (Long) getSqlMapClientTemplate().insert("MS-EXTRA-SYS-DATE-SEQ-INSERT", paramMap);
	}

	@Override
	public long insertYearMonthSeq(String name, Date date) throws DataAccessException {
		if (StringUtil.isBlank(name)) {
			throw new IllegalArgumentException("Can't insert a null data object into db.");
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("name", name);
		paramMap.put("seqDate", DateUtil.simpleFormatYM(date));
		paramMap.put("rawAddTime", date);
		return (Long) getSqlMapClientTemplate().insert("MS-EXTRA-SYS-DATE-SEQ-INSERT", paramMap);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public long getNextDateSeq(String name, Date date, long cacheNumber) throws DataAccessException {

		if (StringUtil.isBlank(name)) {
			throw new IllegalArgumentException("Can't update a null data object into db.");
		}
		if (cacheNumber <= 0)
			cacheNumber = 1;
		Map paramMap = new HashMap<String, Object>();
		paramMap.put("name", name);
		paramMap.put("seqDate", DateUtil.formatDay(date));
		paramMap.put("cacheNumber", cacheNumber);
		return (Long) getSqlMapClientTemplate().insert("MS-EXTRA-SYS-DATE-SEQ-UPDATE", paramMap);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public long getNextYearSeq(String name, Date date, long cacheNumber) throws DataAccessException {

		if (StringUtil.isBlank(name)) {
			throw new IllegalArgumentException("Can't update a null data object into db.");
		}
		if (cacheNumber <= 0)
			cacheNumber = 1;
		Map paramMap = new HashMap<String, Object>();
		paramMap.put("name", name);
		paramMap.put("seqDate", DateUtil.formatYear(date));
		paramMap.put("cacheNumber", cacheNumber);
		return (Long) getSqlMapClientTemplate().insert("MS-EXTRA-SYS-DATE-SEQ-UPDATE", paramMap);
	}

	@Override
	public long getNextYearMonthSeq(String name, Date date, long cacheNumber)
			throws DataAccessException {

		if (StringUtil.isBlank(name)) {
			throw new IllegalArgumentException("Can't update a null data object into db.");
		}
		if (cacheNumber <= 0)
			cacheNumber = 1;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("name", name);
		paramMap.put("seqDate", DateUtil.simpleFormatYM(date));
		paramMap.put("cacheNumber", cacheNumber);
		return (Long) getSqlMapClientTemplate().insert("MS-EXTRA-SYS-DATE-SEQ-UPDATE", paramMap);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public boolean selectDateSeq(String name, Date date) throws DataAccessException {

		if (StringUtil.isBlank(name)) {
			throw new IllegalArgumentException("Can't select a null data object into db.");
		}

		Map paramMap = new HashMap<String, Object>();
		paramMap.put("name", name);
		paramMap.put("seqDate", DateUtil.formatDay(date));
		Long maxNumber = (Long) getSqlMapClientTemplate().queryForObject(
				"MS-EXTRA-SYS-DATE-SEQ-SELECT", paramMap);
		if (maxNumber == null || maxNumber <= 0) {
			return false;
		}
		return true;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public boolean selectYearSeq(String name, Date date) throws DataAccessException {

		if (StringUtil.isBlank(name)) {
			throw new IllegalArgumentException("Can't select a null data object into db.");
		}

		Map paramMap = new HashMap<String, Object>();
		paramMap.put("name", name);
		paramMap.put("seqDate", DateUtil.formatYear(date));
		Long maxNumber = (Long) getSqlMapClientTemplate().queryForObject(
				"MS-EXTRA-SYS-DATE-SEQ-SELECT", paramMap);
		if (maxNumber == null || maxNumber <= 0) {
			return false;
		}
		return true;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Long selectYearSeqNum(String name, Date date) throws DataAccessException {

		if (StringUtil.isBlank(name)) {
			throw new IllegalArgumentException("Can't select a null data object into db.");
		}

		Map paramMap = new HashMap<String, Object>();
		paramMap.put("name", name);
		paramMap.put("seqDate", DateUtil.formatYear(date));
		Long maxNumber = (Long) getSqlMapClientTemplate().queryForObject(
				"MS-EXTRA-SYS-DATE-SEQ-SELECT", paramMap);
		if (maxNumber == null || maxNumber <= 0) {
			return 0L;
		}
		return maxNumber;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public boolean selectYearMonthSeq(String name, Date date) throws DataAccessException {

		if (StringUtil.isBlank(name)) {
			throw new IllegalArgumentException("Can't select a null data object into db.");
		}

		Map paramMap = new HashMap<String, Object>();
		paramMap.put("name", name);
		paramMap.put("seqDate", DateUtil.simpleFormatYM(date));
		Long maxNumber = (Long) getSqlMapClientTemplate().queryForObject(
				"MS-EXTRA-SYS-DATE-SEQ-SELECT", paramMap);
		if (maxNumber == null || maxNumber <= 0) {
			return false;
		}
		return true;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Long selectYearMonthSeqNum(String name, Date date) throws DataAccessException {

		if (StringUtil.isBlank(name)) {
			throw new IllegalArgumentException("Can't select a null data object into db.");
		}

		Map paramMap = new HashMap<String, Object>();
		paramMap.put("name", name);
		paramMap.put("seqDate", DateUtil.simpleFormatYM(date));
		Long maxNumber = (Long) getSqlMapClientTemplate().queryForObject(
				"MS-EXTRA-SYS-DATE-SEQ-SELECT", paramMap);
		if (maxNumber == null || maxNumber <= 0) {
			return 0L;
		}
		return maxNumber;
	}

	@Override
	public AccountBalanceDO queryLatestAccountBalance() {
		Object obj = getSqlMapClientTemplate().queryForObject("MS-EXTRA-LASET-ACCOUNT-BALANCE");
		if (null  != obj) {
			return (AccountBalanceDO)obj;
		}
		return null;
	}
	
}
