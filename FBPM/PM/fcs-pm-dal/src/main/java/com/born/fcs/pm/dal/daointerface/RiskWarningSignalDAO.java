/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.dal.daointerface;

// auto generated imports
import com.born.fcs.pm.dal.dataobject.RiskWarningSignalDO;
import org.springframework.dao.DataAccessException;
import java.util.List;

/**
 * A dao interface provides methods to access database table <tt>risk_warning_signal</tt>.
 *
 * This file is generated by <tt>specialmer-dalgen</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>paygw</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/risk_warning_signal.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>specialmer-dalgen</tt> 
 * to generate this file.
 *
 * @author peigen
 */
 @SuppressWarnings("rawtypes") 
public interface RiskWarningSignalDAO {
	/**
	 *  Insert one <tt>RiskWarningSignalDO</tt> object to DB table <tt>risk_warning_signal</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into risk_warning_signal(signal_type,signal_type,signal_type_name,raw_add_time) values (?, ?, ?, ?)</tt>
	 *
	 *	@param riskWarningSignal
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long insert(RiskWarningSignalDO riskWarningSignal) throws DataAccessException;

	/**
	 *  Update DB table <tt>risk_warning_signal</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update risk_warning_signal set signal_type=?, signal_type=?, signal_type_name=? where (id = ?)</tt>
	 *
	 *	@param riskWarningSignal
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int update(RiskWarningSignalDO riskWarningSignal) throws DataAccessException;

	/**
	 *  Query DB table <tt>risk_warning_signal</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from risk_warning_signal where (id = ?)</tt>
	 *
	 *	@param id
	 *	@return RiskWarningSignalDO
	 *	@throws DataAccessException
	 */	 
    public RiskWarningSignalDO findById(long id) throws DataAccessException;

	/**
	 *  Delete records from DB table <tt>risk_warning_signal</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from risk_warning_signal where (id = ?)</tt>
	 *
	 *	@param id
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteById(long id) throws DataAccessException;

	/**
	 *  Query DB table <tt>risk_warning_signal</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from risk_warning_signal where (1 = 1)</tt>
	 *
	 *	@param riskWarningSignal
	 *	@param limitStart
	 *	@param pageSize
	 *	@return List<RiskWarningSignalDO>
	 *	@throws DataAccessException
	 */	 
    public List<RiskWarningSignalDO> findByCondition(RiskWarningSignalDO riskWarningSignal, long limitStart, long pageSize) throws DataAccessException;

	/**
	 *  Query DB table <tt>risk_warning_signal</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select COUNT(*) from risk_warning_signal where (1 = 1)</tt>
	 *
	 *	@param riskWarningSignal
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long findByConditionCount(RiskWarningSignalDO riskWarningSignal) throws DataAccessException;

}