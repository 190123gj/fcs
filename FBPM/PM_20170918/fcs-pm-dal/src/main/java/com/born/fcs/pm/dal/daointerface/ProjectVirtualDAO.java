/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.dal.daointerface;

// auto generated imports
import com.born.fcs.pm.dal.dataobject.ProjectVirtualDO;
import org.springframework.dao.DataAccessException;
import java.util.List;

/**
 * A dao interface provides methods to access database table <tt>project_virtual</tt>.
 *
 * This file is generated by <tt>specialmer-dalgen</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>paygw</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/project_virtual.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>specialmer-dalgen</tt> 
 * to generate this file.
 *
 * @author peigen
 */
 @SuppressWarnings("rawtypes") 
public interface ProjectVirtualDAO {
	/**
	 *  Insert one <tt>ProjectVirtualDO</tt> object to DB table <tt>project_virtual</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into project_virtual(virtual_id,project_code,project_name,customer_id,customer_name,amount,busi_type,busi_type_name,apply_user_id,apply_user_account,apply_user_name,apply_dept_id,apply_dept_name,scheme,remark,attach,status,submit_time,form_names,raw_add_time) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)</tt>
	 *
	 *	@param projectVirtual
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long insert(ProjectVirtualDO projectVirtual) throws DataAccessException;

	/**
	 *  Update DB table <tt>project_virtual</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update project_virtual set project_code=?, project_name=?, customer_id=?, customer_name=?, amount=?, busi_type=?, busi_type_name=?, apply_user_id=?, apply_user_account=?, apply_user_name=?, apply_dept_id=?, apply_dept_name=?, status=?, submit_time=?, remark=?, attach=? where (virtual_id = ?)</tt>
	 *
	 *	@param projectVirtual
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int update(ProjectVirtualDO projectVirtual) throws DataAccessException;

	/**
	 *  Update DB table <tt>project_virtual</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update project_virtual set project_code=?, status=? where (virtual_id = ?)</tt>
	 *
	 *	@param projectCode
	 *	@param status
	 *	@param virtualId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int updateProjectCodeAndStatus(String projectCode, String status, long virtualId) throws DataAccessException;

	/**
	 *  Query DB table <tt>project_virtual</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from project_virtual where (virtual_id = ?)</tt>
	 *
	 *	@param virtualId
	 *	@return ProjectVirtualDO
	 *	@throws DataAccessException
	 */	 
    public ProjectVirtualDO findById(long virtualId) throws DataAccessException;

	/**
	 *  Query DB table <tt>project_virtual</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from project_virtual where (project_code = ?)</tt>
	 *
	 *	@param projectCode
	 *	@return ProjectVirtualDO
	 *	@throws DataAccessException
	 */	 
    public ProjectVirtualDO findByProjectCode(String projectCode) throws DataAccessException;

	/**
	 *  Delete records from DB table <tt>project_virtual</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from project_virtual where (virtual_id = ?)</tt>
	 *
	 *	@param virtualId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteById(long virtualId) throws DataAccessException;

	/**
	 *  Delete records from DB table <tt>project_virtual</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from project_virtual where (project_code = ?)</tt>
	 *
	 *	@param projectCode
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteByProjectCode(String projectCode) throws DataAccessException;

	/**
	 *  Query DB table <tt>project_virtual</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from project_virtual where (1 = 1)</tt>
	 *
	 *	@param projectVirtual
	 *	@param limitStart
	 *	@param pageSize
	 *	@param sortCol
	 *	@param sortOrder
	 *	@param draftUserId
	 *	@param loginUserId
	 *	@param deptIdList
	 *	@param relatedRoleList
	 *	@return List<ProjectVirtualDO>
	 *	@throws DataAccessException
	 */	 
    public List<ProjectVirtualDO> findByCondition(ProjectVirtualDO projectVirtual, long limitStart, long pageSize, String sortCol, String sortOrder, long draftUserId, long loginUserId, List deptIdList, List relatedRoleList) throws DataAccessException;

	/**
	 *  Query DB table <tt>project_virtual</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select COUNT(*) from project_virtual where (1 = 1)</tt>
	 *
	 *	@param projectVirtual
	 *	@param draftUserId
	 *	@param loginUserId
	 *	@param deptIdList
	 *	@param relatedRoleList
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long findByConditionCount(ProjectVirtualDO projectVirtual, long draftUserId, long loginUserId, List deptIdList, List relatedRoleList) throws DataAccessException;

}