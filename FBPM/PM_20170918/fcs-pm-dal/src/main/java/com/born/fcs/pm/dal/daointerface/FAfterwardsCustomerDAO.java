/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.dal.daointerface;

// auto generated imports
import com.born.fcs.pm.dal.dataobject.FAfterwardsCustomerDO;
import org.springframework.dao.DataAccessException;

/**
 * A dao interface provides methods to access database table <tt>f_afterwards_customer</tt>.
 *
 * This file is generated by <tt>specialmer-dalgen</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>paygw</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/f_afterwards_customer.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>specialmer-dalgen</tt> 
 * to generate this file.
 *
 * @author peigen
 */

public interface FAfterwardsCustomerDAO {
	/**
	 *  Insert one <tt>FAfterwardsCustomerDO</tt> object to DB table <tt>f_afterwards_customer</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into f_afterwards_customer(form_id,customer_id,customer_name,form_data,edit_html,view_html,status,user_id,user_account,user_name,user_ip,dept_id,dept_name,session_id,access_token,exe_result,raw_add_time) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)</tt>
	 *
	 *	@param FAfterwardsCustomer
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long insert(FAfterwardsCustomerDO FAfterwardsCustomer) throws DataAccessException;

	/**
	 *  Update DB table <tt>f_afterwards_customer</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update f_afterwards_customer set form_id=?, customer_id=?, customer_name=?, form_data=?, edit_html=?, view_html=?, status=?, user_id=?, user_account=?, user_name=?, user_ip=?, dept_id=?, dept_name=?, session_id=?, access_token=?, exe_result=? where (id = ?)</tt>
	 *
	 *	@param FAfterwardsCustomer
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int update(FAfterwardsCustomerDO FAfterwardsCustomer) throws DataAccessException;

	/**
	 *  Query DB table <tt>f_afterwards_customer</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from f_afterwards_customer where (id = ?)</tt>
	 *
	 *	@param id
	 *	@return FAfterwardsCustomerDO
	 *	@throws DataAccessException
	 */	 
    public FAfterwardsCustomerDO findById(long id) throws DataAccessException;

	/**
	 *  Query DB table <tt>f_afterwards_customer</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from f_afterwards_customer where (form_id = ?)</tt>
	 *
	 *	@param formId
	 *	@return FAfterwardsCustomerDO
	 *	@throws DataAccessException
	 */	 
    public FAfterwardsCustomerDO findByFormId(long formId) throws DataAccessException;

	/**
	 *  Delete records from DB table <tt>f_afterwards_customer</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from f_afterwards_customer where (id = ?)</tt>
	 *
	 *	@param id
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteById(long id) throws DataAccessException;

	/**
	 *  Delete records from DB table <tt>f_afterwards_customer</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from f_afterwards_customer where (form_id = ?)</tt>
	 *
	 *	@param formId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteByFormId(long formId) throws DataAccessException;

}