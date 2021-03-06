/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.dal.daointerface;

// auto generated imports
import com.born.fcs.pm.dal.dataobject.FReCouncilApplyDO;
import org.springframework.dao.DataAccessException;

/**
 * A dao interface provides methods to access database table <tt>f_re_council_apply</tt>.
 *
 * This file is generated by <tt>specialmer-dalgen</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>paygw</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/f_re_council_apply.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>specialmer-dalgen</tt> 
 * to generate this file.
 *
 * @author peigen
 */

public interface FReCouncilApplyDAO {
	/**
	 *  Insert one <tt>FReCouncilApplyDO</tt> object to DB table <tt>f_re_council_apply</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into f_re_council_apply(id,form_id,project_code,old_sp_id,old_sp_code,content_reason,overview,council_apply_id,council_back,raw_add_time) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)</tt>
	 *
	 *	@param FReCouncilApply
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long insert(FReCouncilApplyDO FReCouncilApply) throws DataAccessException;

	/**
	 *  Update DB table <tt>f_re_council_apply</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update f_re_council_apply set form_id=?, project_code=?, old_sp_id=?, old_sp_code=?, content_reason=?, overview=?, council_apply_id=?, council_back=? where (id = ?)</tt>
	 *
	 *	@param FReCouncilApply
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int update(FReCouncilApplyDO FReCouncilApply) throws DataAccessException;

	/**
	 *  Update DB table <tt>f_re_council_apply</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update f_re_council_apply set project_code=?, old_sp_id=?, old_sp_code=?, content_reason=?, overview=?, council_apply_id=?, council_back=? where (form_id = ?)</tt>
	 *
	 *	@param FReCouncilApply
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int updateByFormId(FReCouncilApplyDO FReCouncilApply) throws DataAccessException;

	/**
	 *  Query DB table <tt>f_re_council_apply</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from f_re_council_apply where (id = ?)</tt>
	 *
	 *	@param id
	 *	@return FReCouncilApplyDO
	 *	@throws DataAccessException
	 */	 
    public FReCouncilApplyDO findById(long id) throws DataAccessException;

	/**
	 *  Query DB table <tt>f_re_council_apply</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from f_re_council_apply where (form_id = ?)</tt>
	 *
	 *	@param formId
	 *	@return FReCouncilApplyDO
	 *	@throws DataAccessException
	 */	 
    public FReCouncilApplyDO findByFormId(long formId) throws DataAccessException;

	/**
	 *  Query DB table <tt>f_re_council_apply</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from f_re_council_apply where (council_apply_id = ?)</tt>
	 *
	 *	@param councilApplyId
	 *	@return FReCouncilApplyDO
	 *	@throws DataAccessException
	 */	 
    public FReCouncilApplyDO findByCouncilApplyId(long councilApplyId) throws DataAccessException;

	/**
	 *  Delete records from DB table <tt>f_re_council_apply</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from f_re_council_apply where (id = ?)</tt>
	 *
	 *	@param id
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteById(long id) throws DataAccessException;

	/**
	 *  Delete records from DB table <tt>f_re_council_apply</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from f_re_council_apply where (form_id = ?)</tt>
	 *
	 *	@param formId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteByFormId(long formId) throws DataAccessException;

}