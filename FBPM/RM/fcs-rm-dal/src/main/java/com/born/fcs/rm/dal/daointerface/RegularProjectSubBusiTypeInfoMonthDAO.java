/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.rm.dal.daointerface;

// auto generated imports
import com.born.fcs.rm.dal.dataobject.RegularProjectSubBusiTypeInfoMonthDO;
import org.springframework.dao.DataAccessException;

/**
 * A dao interface provides methods to access database table <tt>regular_project_sub_busi_type_info_month</tt>.
 *
 * This file is generated by <tt>specialmer-dalgen</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>paygw</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/regular_project_sub_busi_type_info_month.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>specialmer-dalgen</tt> 
 * to generate this file.
 *
 * @author peigen
 */

public interface RegularProjectSubBusiTypeInfoMonthDAO {
	/**
	 *  Insert one <tt>RegularProjectSubBusiTypeInfoMonthDO</tt> object to DB table <tt>regular_project_sub_busi_type_info_month</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into regular_project_sub_busi_type_info_month(id,report_date,customer_id,customer_name,project_code,project_name,busi_type,busi_type_name,sub_busi_type,sub_busi_type_name,is_new,is_insurance,count_rate,customer_count_rate,first_occur_date,contract_amount,occur_amount,occur_amount_this_month,occur_amount_this_year,release_amount,release_amount_this_month,release_amount_this_year,balance,balance_beginning,raw_add_time) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)</tt>
	 *
	 *	@param regularProjectSubBusiTypeInfoMonth
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long insert(RegularProjectSubBusiTypeInfoMonthDO regularProjectSubBusiTypeInfoMonth) throws DataAccessException;

	/**
	 *  Update DB table <tt>regular_project_sub_busi_type_info_month</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update regular_project_sub_busi_type_info_month set report_date=?, customer_id=?, customer_name=?, project_code=?, project_name=?, busi_type=?, busi_type_name=?, sub_busi_type=?, sub_busi_type_name=?, is_new=?, is_insurance=?, count_rate=?, customer_count_rate=?, first_occur_date=?, contract_amount=?, occur_amount=?, occur_amount_this_month=?, occur_amount_this_year=?, release_amount=?, release_amount_this_month=?, release_amount_this_year=?, balance=?, balance_beginning=? where (id = ?)</tt>
	 *
	 *	@param regularProjectSubBusiTypeInfoMonth
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int update(RegularProjectSubBusiTypeInfoMonthDO regularProjectSubBusiTypeInfoMonth) throws DataAccessException;

	/**
	 *  Delete records from DB table <tt>regular_project_sub_busi_type_info_month</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from regular_project_sub_busi_type_info_month where (report_date = ?)</tt>
	 *
	 *	@param reportDate
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteByReportDate(String reportDate) throws DataAccessException;

}