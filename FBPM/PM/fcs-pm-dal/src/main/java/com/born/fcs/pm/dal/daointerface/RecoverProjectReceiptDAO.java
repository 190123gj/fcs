/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.dal.daointerface;

// auto generated imports
import com.born.fcs.pm.dal.dataobject.RecoverProjectReceiptDO;
import org.springframework.dao.DataAccessException;
import java.util.List;

/**
 * A dao interface provides methods to access database table <tt>recover_project_receipt</tt>.
 *
 * This file is generated by <tt>specialmer-dalgen</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>paygw</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/recover_project_receipt.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>specialmer-dalgen</tt> 
 * to generate this file.
 *
 * @author peigen
 */
 @SuppressWarnings("rawtypes") 
public interface RecoverProjectReceiptDAO {
	/**
	 *  Insert one <tt>RecoverProjectReceiptDO</tt> object to DB table <tt>recover_project_receipt</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into recover_project_receipt(recover_id,receipt_name,receipt_url,raw_add_time) values (?, ?, ?, ?)</tt>
	 *
	 *	@param recoverProjectReceipt
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long insert(RecoverProjectReceiptDO recoverProjectReceipt) throws DataAccessException;

	/**
	 *  Update DB table <tt>recover_project_receipt</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update recover_project_receipt set recover_id=?, receipt_name=?, receipt_url=? where (id = ?)</tt>
	 *
	 *	@param recoverProjectReceipt
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int update(RecoverProjectReceiptDO recoverProjectReceipt) throws DataAccessException;

	/**
	 *  Query DB table <tt>recover_project_receipt</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from recover_project_receipt where (id = ?)</tt>
	 *
	 *	@param id
	 *	@return RecoverProjectReceiptDO
	 *	@throws DataAccessException
	 */	 
    public RecoverProjectReceiptDO findById(long id) throws DataAccessException;

	/**
	 *  Query DB table <tt>recover_project_receipt</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from recover_project_receipt where (recover_id = ?)</tt>
	 *
	 *	@param recoverId
	 *	@return List<RecoverProjectReceiptDO>
	 *	@throws DataAccessException
	 */	 
    public List<RecoverProjectReceiptDO> findByRecoverId(long recoverId) throws DataAccessException;

	/**
	 *  Delete records from DB table <tt>recover_project_receipt</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from recover_project_receipt where (id = ?)</tt>
	 *
	 *	@param id
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteById(long id) throws DataAccessException;

	/**
	 *  Delete records from DB table <tt>recover_project_receipt</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from recover_project_receipt where (recover_id = ?)</tt>
	 *
	 *	@param recoverId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteByRecoverId(long recoverId) throws DataAccessException;

}