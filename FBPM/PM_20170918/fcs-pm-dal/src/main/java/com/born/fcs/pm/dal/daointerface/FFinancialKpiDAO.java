/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.dal.daointerface;

// auto generated imports
import com.born.fcs.pm.dal.dataobject.FFinancialKpiDO;
import org.springframework.dao.DataAccessException;
import java.util.List;

/**
 * A dao interface provides methods to access database table <tt>f_financial_kpi</tt>.
 *
 * This file is generated by <tt>specialmer-dalgen</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>paygw</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/f_financial_kpi.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>specialmer-dalgen</tt> 
 * to generate this file.
 *
 * @author peigen
 */
 @SuppressWarnings("rawtypes") 
public interface FFinancialKpiDAO {
	/**
	 *  Insert one <tt>FFinancialKpiDO</tt> object to DB table <tt>f_financial_kpi</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into f_financial_kpi(form_id,parent_id,kpi_type,kpi_code,kpi_name,kpi_value1,kpi_value2,kpi_value3,kpi_value4,kpi_value5,kpi_value6,kpi_value7,remark,sort_order,raw_add_time) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)</tt>
	 *
	 *	@param FFinancialKpi
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long insert(FFinancialKpiDO FFinancialKpi) throws DataAccessException;

	/**
	 *  Update DB table <tt>f_financial_kpi</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update f_financial_kpi set form_id=?, parent_id=?, kpi_type=?, kpi_code=?, kpi_name=?, kpi_value1=?, kpi_value2=?, kpi_value3=?, kpi_value4=?, kpi_value5=?, kpi_value6=?, kpi_value7=?, remark=?, sort_order=? where (kpi_id = ?)</tt>
	 *
	 *	@param FFinancialKpi
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int update(FFinancialKpiDO FFinancialKpi) throws DataAccessException;

	/**
	 *  Query DB table <tt>f_financial_kpi</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from f_financial_kpi where (kpi_id = ?)</tt>
	 *
	 *	@param kpiId
	 *	@return FFinancialKpiDO
	 *	@throws DataAccessException
	 */	 
    public FFinancialKpiDO findById(long kpiId) throws DataAccessException;

	/**
	 *  Query DB table <tt>f_financial_kpi</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from f_financial_kpi where (form_id = ?) order by sort_order ASC</tt>
	 *
	 *	@param formId
	 *	@return List<FFinancialKpiDO>
	 *	@throws DataAccessException
	 */	 
    public List<FFinancialKpiDO> findByFormId(long formId) throws DataAccessException;

	/**
	 *  Query DB table <tt>f_financial_kpi</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from f_financial_kpi where ((form_id = ?) AND (kpi_type = ?)) order by sort_order ASC</tt>
	 *
	 *	@param formId
	 *	@param kpiType
	 *	@return List<FFinancialKpiDO>
	 *	@throws DataAccessException
	 */	 
    public List<FFinancialKpiDO> findByFormIdAndKpitype(long formId, String kpiType) throws DataAccessException;

	/**
	 *  Query DB table <tt>f_financial_kpi</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from f_financial_kpi where (parent_id = ?) order by sort_order ASC</tt>
	 *
	 *	@param parentId
	 *	@return List<FFinancialKpiDO>
	 *	@throws DataAccessException
	 */	 
    public List<FFinancialKpiDO> findByParentId(long parentId) throws DataAccessException;

	/**
	 *  Delete records from DB table <tt>f_financial_kpi</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from f_financial_kpi where (kpi_id = ?)</tt>
	 *
	 *	@param kpiId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteById(long kpiId) throws DataAccessException;

	/**
	 *  Delete records from DB table <tt>f_financial_kpi</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from f_financial_kpi where (form_id = ?)</tt>
	 *
	 *	@param formId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteByFormId(long formId) throws DataAccessException;

	/**
	 *  Delete records from DB table <tt>f_financial_kpi</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from f_financial_kpi where (parent_id = ?)</tt>
	 *
	 *	@param parentId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteByParentId(long parentId) throws DataAccessException;

	/**
	 *  Delete records from DB table <tt>f_financial_kpi</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from f_financial_kpi where ((form_id = ?) AND (kpi_type = ?))</tt>
	 *
	 *	@param formId
	 *	@param kpiType
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteByFormIdAndKpitype(long formId, String kpiType) throws DataAccessException;

	/**
	 *  Query DB table <tt>f_financial_kpi</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from f_financial_kpi where (1 = 1)</tt>
	 *
	 *	@param FFinancialKpi
	 *	@param limitStart
	 *	@param pageSize
	 *	@return List<FFinancialKpiDO>
	 *	@throws DataAccessException
	 */	 
    public List<FFinancialKpiDO> findByCondition(FFinancialKpiDO FFinancialKpi, long limitStart, long pageSize) throws DataAccessException;

	/**
	 *  Query DB table <tt>f_financial_kpi</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select COUNT(*) from f_financial_kpi where (1 = 1)</tt>
	 *
	 *	@param FFinancialKpi
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long findByConditionCount(FFinancialKpiDO FFinancialKpi) throws DataAccessException;

}