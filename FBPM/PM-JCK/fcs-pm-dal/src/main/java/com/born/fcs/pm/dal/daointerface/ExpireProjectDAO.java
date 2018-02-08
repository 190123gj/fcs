/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.dal.daointerface;

// auto generated imports
import com.born.fcs.pm.dal.dataobject.ExpireProjectDO;
import org.springframework.dao.DataAccessException;
import java.util.List;

/**
 * A dao interface provides methods to access database table <tt>expire_project</tt>.
 *
 * This file is generated by <tt>specialmer-dalgen</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>paygw</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/expire_project.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>specialmer-dalgen</tt> 
 * to generate this file.
 *
 * @author peigen
 */
 @SuppressWarnings("rawtypes") 
public interface ExpireProjectDAO {
	/**
	 *  Insert one <tt>ExpireProjectDO</tt> object to DB table <tt>expire_project</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into expire_project(project_code,project_name,expire_date,status,receipt,repay_certificate,raw_add_time) values (?, ?, ?, ?, ?, ?, ?)</tt>
	 *
	 *	@param expireProject
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long insert(ExpireProjectDO expireProject) throws DataAccessException;

	/**
	 *  Update DB table <tt>expire_project</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update expire_project set project_code=?, project_name=?, expire_date=?, status=?, receipt=?, repay_certificate=? where (id = ?)</tt>
	 *
	 *	@param expireProject
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int update(ExpireProjectDO expireProject) throws DataAccessException;

	/**
	 *  Update DB table <tt>expire_project</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update expire_project set project_name=?, expire_date=?, status=?, receipt=?, repay_certificate=? where (project_code = ?)</tt>
	 *
	 *	@param expireProject
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int updateProjectCode(ExpireProjectDO expireProject) throws DataAccessException;

	/**
	 *  Query DB table <tt>expire_project</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from expire_project where (id = ?)</tt>
	 *
	 *	@param id
	 *	@return ExpireProjectDO
	 *	@throws DataAccessException
	 */	 
    public ExpireProjectDO findById(long id) throws DataAccessException;

	/**
	 *  Query DB table <tt>expire_project</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from expire_project where (project_code = ?)</tt>
	 *
	 *	@param projectCode
	 *	@return ExpireProjectDO
	 *	@throws DataAccessException
	 */	 
    public ExpireProjectDO findByProjectCode(String projectCode) throws DataAccessException;

	/**
	 *  Delete records from DB table <tt>expire_project</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from expire_project where (id = ?)</tt>
	 *
	 *	@param id
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteById(long id) throws DataAccessException;

	/**
	 *  Delete records from DB table <tt>expire_project</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from expire_project where (project_code = ?)</tt>
	 *
	 *	@param projectCode
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteByProjectCode(String projectCode) throws DataAccessException;

	/**
	 *  Query DB table <tt>expire_project</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from expire_project where (1 = 1)</tt>
	 *
	 *	@param expireProject
	 *	@param expireDateBegin
	 *	@param expireDateEnd
	 *	@param limitStart
	 *	@param pageSize
	 *	@return List<ExpireProjectDO>
	 *	@throws DataAccessException
	 */	 
    public List<ExpireProjectDO> findByCondition(ExpireProjectDO expireProject, String expireDateBegin, String expireDateEnd, long limitStart, long pageSize) throws DataAccessException;

	/**
	 *  Query DB table <tt>expire_project</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select COUNT(*) from expire_project where (1 = 1)</tt>
	 *
	 *	@param expireProject
	 *	@param expireDateBegin
	 *	@param expireDateEnd
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long findByConditionCount(ExpireProjectDO expireProject, String expireDateBegin, String expireDateEnd) throws DataAccessException;

}