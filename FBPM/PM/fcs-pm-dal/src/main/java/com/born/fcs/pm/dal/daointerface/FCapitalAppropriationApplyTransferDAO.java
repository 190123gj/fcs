/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.dal.daointerface;

// auto generated imports
import com.born.fcs.pm.dal.dataobject.FCapitalAppropriationApplyTransferDO;
import org.springframework.dao.DataAccessException;
import java.util.List;

/**
 * A dao interface provides methods to access database table <tt>f_capital_appropriation_apply_transfer</tt>.
 *
 * This file is generated by <tt>specialmer-dalgen</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>paygw</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/f_capital_appropriation_apply_transfer.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>specialmer-dalgen</tt> 
 * to generate this file.
 *
 * @author peigen
 */
 @SuppressWarnings("rawtypes") 
public interface FCapitalAppropriationApplyTransferDAO {
	/**
	 *  Insert one <tt>FCapitalAppropriationApplyTransferDO</tt> object to DB table <tt>f_capital_appropriation_apply_transfer</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into f_capital_appropriation_apply_transfer(id,form_id,in_account,out_account,out_amount,remark,raw_add_time) values (?, ?, ?, ?, ?, ?, ?)</tt>
	 *
	 *	@param FCapitalAppropriationApplyTransfer
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long insert(FCapitalAppropriationApplyTransferDO FCapitalAppropriationApplyTransfer) throws DataAccessException;

	/**
	 *  Update DB table <tt>f_capital_appropriation_apply_transfer</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update f_capital_appropriation_apply_transfer set form_id=?, in_account=?, out_account=?, out_amount=?, remark=? where (id = ?)</tt>
	 *
	 *	@param FCapitalAppropriationApplyTransfer
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int update(FCapitalAppropriationApplyTransferDO FCapitalAppropriationApplyTransfer) throws DataAccessException;

	/**
	 *  Query DB table <tt>f_capital_appropriation_apply_transfer</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from f_capital_appropriation_apply_transfer where (id = ?)</tt>
	 *
	 *	@param id
	 *	@return FCapitalAppropriationApplyTransferDO
	 *	@throws DataAccessException
	 */	 
    public FCapitalAppropriationApplyTransferDO findById(long id) throws DataAccessException;

	/**
	 *  Query DB table <tt>f_capital_appropriation_apply_transfer</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from f_capital_appropriation_apply_transfer where (form_id = ?)</tt>
	 *
	 *	@param formId
	 *	@return List<FCapitalAppropriationApplyTransferDO>
	 *	@throws DataAccessException
	 */	 
    public List<FCapitalAppropriationApplyTransferDO> findByFormId(long formId) throws DataAccessException;

	/**
	 *  Delete records from DB table <tt>f_capital_appropriation_apply_transfer</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from f_capital_appropriation_apply_transfer where (id = ?)</tt>
	 *
	 *	@param id
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteById(long id) throws DataAccessException;

	/**
	 *  Delete records from DB table <tt>f_capital_appropriation_apply_transfer</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from f_capital_appropriation_apply_transfer where (form_id = ?)</tt>
	 *
	 *	@param formId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteByFormId(long formId) throws DataAccessException;

}