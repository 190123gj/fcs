/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.dal.daointerface;

// auto generated imports
import com.born.fcs.pm.dal.dataobject.FAfterwardsCheckReportCapitalDO;
import org.springframework.dao.DataAccessException;
import java.util.List;

/**
 * A dao interface provides methods to access database table <tt>f_afterwards_check_report_capital</tt>.
 *
 * This file is generated by <tt>specialmer-dalgen</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>paygw</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/f_afterwards_check_report_capital.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>specialmer-dalgen</tt> 
 * to generate this file.
 *
 * @author peigen
 */
 @SuppressWarnings("rawtypes") 
public interface FAfterwardsCheckReportCapitalDAO {
	/**
	 *  Insert one <tt>FAfterwardsCheckReportCapitalDO</tt> object to DB table <tt>f_afterwards_check_report_capital</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into f_afterwards_check_report_capital(form_id,capital_type,capital_item,capital_value1,capital_value2,capital_value3,capital_value4,capital_value5,capital_value6,capital_value7,capital_value8,capital_value9,capital_value10,del_able,sort_order,raw_add_time) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)</tt>
	 *
	 *	@param FAfterwardsCheckReportCapital
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long insert(FAfterwardsCheckReportCapitalDO FAfterwardsCheckReportCapital) throws DataAccessException;

	/**
	 *  Update DB table <tt>f_afterwards_check_report_capital</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update f_afterwards_check_report_capital set form_id=?, capital_type=?, capital_item=?, capital_value1=?, capital_value2=?, capital_value3=?, capital_value4=?, capital_value5=?, capital_value6=?, capital_value7=?, capital_value8=?, capital_value9=?, capital_value10=?, del_able=?, sort_order=? where (capital_id = ?)</tt>
	 *
	 *	@param FAfterwardsCheckReportCapital
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int update(FAfterwardsCheckReportCapitalDO FAfterwardsCheckReportCapital) throws DataAccessException;

	/**
	 *  Query DB table <tt>f_afterwards_check_report_capital</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from f_afterwards_check_report_capital where (capital_id = ?)</tt>
	 *
	 *	@param capitalId
	 *	@return FAfterwardsCheckReportCapitalDO
	 *	@throws DataAccessException
	 */	 
    public FAfterwardsCheckReportCapitalDO findById(long capitalId) throws DataAccessException;

	/**
	 *  Query DB table <tt>f_afterwards_check_report_capital</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from f_afterwards_check_report_capital where (form_id = ?) order by sort_order ASC</tt>
	 *
	 *	@param formId
	 *	@return List<FAfterwardsCheckReportCapitalDO>
	 *	@throws DataAccessException
	 */	 
    public List<FAfterwardsCheckReportCapitalDO> findByFormId(long formId) throws DataAccessException;

	/**
	 *  Query DB table <tt>f_afterwards_check_report_capital</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from f_afterwards_check_report_capital where ((form_id = ?) AND (capital_type = ?)) order by sort_order ASC</tt>
	 *
	 *	@param formId
	 *	@param capitalType
	 *	@return List<FAfterwardsCheckReportCapitalDO>
	 *	@throws DataAccessException
	 */	 
    public List<FAfterwardsCheckReportCapitalDO> findByFormIdAndCapitalType(long formId, String capitalType) throws DataAccessException;

	/**
	 *  Delete records from DB table <tt>f_afterwards_check_report_capital</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from f_afterwards_check_report_capital where (capital_id = ?)</tt>
	 *
	 *	@param capitalId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteById(long capitalId) throws DataAccessException;

	/**
	 *  Delete records from DB table <tt>f_afterwards_check_report_capital</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from f_afterwards_check_report_capital where ((form_id = ?) AND (capital_type = ?))</tt>
	 *
	 *	@param formId
	 *	@param capitalType
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteByFormIdAndCapitalType(long formId, String capitalType) throws DataAccessException;

}