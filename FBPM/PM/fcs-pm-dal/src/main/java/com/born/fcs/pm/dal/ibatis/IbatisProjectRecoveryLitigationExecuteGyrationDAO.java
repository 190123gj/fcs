/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.dal.ibatis;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import com.born.fcs.pm.dal.daointerface.ProjectRecoveryLitigationExecuteGyrationDAO;


// auto generated imports
import com.born.fcs.pm.dal.dataobject.ProjectRecoveryLitigationExecuteGyrationDO;
import org.springframework.dao.DataAccessException;
import java.util.List;

/**
 * An ibatis based implementation of dao interface <tt>com.born.fcs.pm.dal.daointerface.ProjectRecoveryLitigationExecuteGyrationDAO</tt>.
 *
 * This file is generated by <tt>specialmer-dalgen</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>paygw</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/project_recovery_litigation_execute_gyration.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>specialmer-dalgen</tt> 
 * to generate this file.
 *
 * @author peigen
 */ 
@SuppressWarnings("unchecked")

public class IbatisProjectRecoveryLitigationExecuteGyrationDAO extends SqlMapClientDaoSupport implements ProjectRecoveryLitigationExecuteGyrationDAO {
	/**
	 *  Insert one <tt>ProjectRecoveryLitigationExecuteGyrationDO</tt> object to DB table <tt>project_recovery_litigation_execute_gyration</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into project_recovery_litigation_execute_gyration(project_recovery_id,memo,litigation_index,raw_add_time) values (?, ?, ?, ?)</tt>
	 *
	 *	@param projectRecoveryLitigationExecuteGyration
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long insert(ProjectRecoveryLitigationExecuteGyrationDO projectRecoveryLitigationExecuteGyration) throws DataAccessException {
    	if (projectRecoveryLitigationExecuteGyration == null) {
    		throw new IllegalArgumentException("Can't insert a null data object into db.");
    	}
    	
        getSqlMapClientTemplate().insert("MS-PROJECT-RECOVERY-LITIGATION-EXECUTE-GYRATION-INSERT", projectRecoveryLitigationExecuteGyration);

        return projectRecoveryLitigationExecuteGyration.getId();
    }

	/**
	 *  Update DB table <tt>project_recovery_litigation_execute_gyration</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update project_recovery_litigation_execute_gyration set project_recovery_id=?, memo=? where (id = ?)</tt>
	 *
	 *	@param projectRecoveryLitigationExecuteGyration
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int update(ProjectRecoveryLitigationExecuteGyrationDO projectRecoveryLitigationExecuteGyration) throws DataAccessException {
    	if (projectRecoveryLitigationExecuteGyration == null) {
    		throw new IllegalArgumentException("Can't update by a null data object.");
    	}


        return getSqlMapClientTemplate().update("MS-PROJECT-RECOVERY-LITIGATION-EXECUTE-GYRATION-UPDATE", projectRecoveryLitigationExecuteGyration);
    }

	/**
	 *  Query DB table <tt>project_recovery_litigation_execute_gyration</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from project_recovery_litigation_execute_gyration where (id = ?)</tt>
	 *
	 *	@param id
	 *	@return ProjectRecoveryLitigationExecuteGyrationDO
	 *	@throws DataAccessException
	 */	 
    public ProjectRecoveryLitigationExecuteGyrationDO findById(long id) throws DataAccessException {
        Long param = new Long(id);

        return (ProjectRecoveryLitigationExecuteGyrationDO) getSqlMapClientTemplate().queryForObject("MS-PROJECT-RECOVERY-LITIGATION-EXECUTE-GYRATION-FIND-BY-ID", param);

    }

	/**
	 *  Query DB table <tt>project_recovery_litigation_execute_gyration</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from project_recovery_litigation_execute_gyration where (project_recovery_id = ?)</tt>
	 *
	 *	@param projectRecoveryId
	 *	@return List<ProjectRecoveryLitigationExecuteGyrationDO>
	 *	@throws DataAccessException
	 */	 
    public List<ProjectRecoveryLitigationExecuteGyrationDO> findByRecoveryId(long projectRecoveryId) throws DataAccessException {
        Long param = new Long(projectRecoveryId);

        return getSqlMapClientTemplate().queryForList("MS-PROJECT-RECOVERY-LITIGATION-EXECUTE-GYRATION-FIND-BY-RECOVERY-ID", param);

    }

	/**
	 *  Delete records from DB table <tt>project_recovery_litigation_execute_gyration</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from project_recovery_litigation_execute_gyration where (id = ?)</tt>
	 *
	 *	@param id
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteById(long id) throws DataAccessException {
        Long param = new Long(id);

        return getSqlMapClientTemplate().delete("MS-PROJECT-RECOVERY-LITIGATION-EXECUTE-GYRATION-DELETE-BY-ID", param);
    }

	/**
	 *  Delete records from DB table <tt>project_recovery_litigation_execute_gyration</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from project_recovery_litigation_execute_gyration where (project_recovery_id = ?)</tt>
	 *
	 *	@param projectRecoveryId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteByRecoveryId(long projectRecoveryId) throws DataAccessException {
        Long param = new Long(projectRecoveryId);

        return getSqlMapClientTemplate().delete("MS-PROJECT-RECOVERY-LITIGATION-EXECUTE-GYRATION-DELETE-BY-RECOVERY-ID", param);
    }

}