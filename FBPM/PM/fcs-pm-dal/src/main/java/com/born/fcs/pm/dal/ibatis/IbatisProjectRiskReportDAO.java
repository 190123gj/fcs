/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.dal.ibatis;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import com.born.fcs.pm.dal.daointerface.ProjectRiskReportDAO;


// auto generated imports
import com.born.fcs.pm.dal.dataobject.ProjectRiskReportDO;
import org.springframework.dao.DataAccessException;
import java.util.Date;
import java.util.List;
import java.util.Map;
	import java.util.HashMap;
	
/**
 * An ibatis based implementation of dao interface <tt>com.born.fcs.pm.dal.daointerface.ProjectRiskReportDAO</tt>.
 *
 * This file is generated by <tt>specialmer-dalgen</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>paygw</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/project_risk_report.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>specialmer-dalgen</tt> 
 * to generate this file.
 *
 * @author peigen
 */ 
@SuppressWarnings({ "unchecked", "rawtypes" })

public class IbatisProjectRiskReportDAO extends SqlMapClientDaoSupport implements ProjectRiskReportDAO {
	/**
	 *  Insert one <tt>ProjectRiskReportDO</tt> object to DB table <tt>project_risk_report</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into project_risk_report(report_id,customer_id,customer_name,enterprise_type,project_code,project_name,time_limit,time_unit,amount,guarantee_fee,guarantee_fee_type,loan_bank,busi_manager_id,busi_manager_name,risk_manager_id,risk_manager_name,reprot1,reprot2,reprot3,reprot4,reprot5,report_type,report_time,report_man_id,report_man_name,report_status,raw_add_time) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)</tt>
	 *
	 *	@param projectRiskReport
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long insert(ProjectRiskReportDO projectRiskReport) throws DataAccessException {
    	if (projectRiskReport == null) {
    		throw new IllegalArgumentException("Can't insert a null data object into db.");
    	}
    	
        getSqlMapClientTemplate().insert("MS-PROJECT-RISK-REPORT-INSERT", projectRiskReport);

        return projectRiskReport.getReportId();
    }

	/**
	 *  Update DB table <tt>project_risk_report</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update project_risk_report set customer_id=?, customer_name=?, enterprise_type=?, project_code=?, project_name=?, time_limit=?, time_unit=?, amount=?, guarantee_fee=?, guarantee_fee_type=?, loan_bank=?, busi_manager_id=?, busi_manager_name=?, risk_manager_id=?, risk_manager_name=?, reprot1=?, reprot2=?, reprot3=?, reprot4=?, reprot5=?, report_type=?, report_time=?, report_man_id=?, report_man_name=?, report_status=? where (report_id = ?)</tt>
	 *
	 *	@param projectRiskReport
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int update(ProjectRiskReportDO projectRiskReport) throws DataAccessException {
    	if (projectRiskReport == null) {
    		throw new IllegalArgumentException("Can't update by a null data object.");
    	}


        return getSqlMapClientTemplate().update("MS-PROJECT-RISK-REPORT-UPDATE", projectRiskReport);
    }

	/**
	 *  Query DB table <tt>project_risk_report</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select report_id, customer_id, customer_name, enterprise_type, project_code, project_name, time_limit, time_unit, amount, guarantee_fee, guarantee_fee_type, loan_bank, busi_manager_id, busi_manager_name, risk_manager_id, risk_manager_name, reprot1, reprot2, reprot3, reprot4, reprot5, report_type, report_time, report_man_id, report_man_name, report_status, raw_add_time, raw_update_time from project_risk_report where (report_id = ?)</tt>
	 *
	 *	@param reportId
	 *	@return ProjectRiskReportDO
	 *	@throws DataAccessException
	 */	 
    public ProjectRiskReportDO findById(long reportId) throws DataAccessException {
        Long param = new Long(reportId);

        return (ProjectRiskReportDO) getSqlMapClientTemplate().queryForObject("MS-PROJECT-RISK-REPORT-FIND-BY-ID", param);

    }

	/**
	 *  Delete records from DB table <tt>project_risk_report</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from project_risk_report where (report_id = ?)</tt>
	 *
	 *	@param reportId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteById(long reportId) throws DataAccessException {
        Long param = new Long(reportId);

        return getSqlMapClientTemplate().delete("MS-PROJECT-RISK-REPORT-DELETE-BY-ID", param);
    }

	/**
	 *  Query DB table <tt>project_risk_report</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select report.* from project_risk_report report where (1 = 1)</tt>
	 *
	 *	@param projectRiskReport
	 *	@param limitStart
	 *	@param pageSize
	 *	@param startTimeBegin
	 *	@param startTimeEnd
	 *	@param loginUserId
	 *	@param deptIdList
	 *	@param relatedRoleList
	 *	@param sortCol
	 *	@param sortOrder
	 *	@return List<ProjectRiskReportDO>
	 *	@throws DataAccessException
	 */	 
    public List<ProjectRiskReportDO> findByCondition(ProjectRiskReportDO projectRiskReport, long limitStart, long pageSize, Date startTimeBegin, Date startTimeEnd, long loginUserId, List deptIdList, List relatedRoleList, String sortCol, String sortOrder) throws DataAccessException {
    	if (projectRiskReport == null) {
    		throw new IllegalArgumentException("Can't select by a null data object.");
    	}

        Map param = new HashMap();

        param.put("projectRiskReport", projectRiskReport);
        param.put("limitStart", new Long(limitStart));
        param.put("pageSize", new Long(pageSize));
        param.put("startTimeBegin", startTimeBegin);
        param.put("startTimeEnd", startTimeEnd);
        param.put("loginUserId", new Long(loginUserId));
        param.put("deptIdList", deptIdList);
        param.put("relatedRoleList", relatedRoleList);
        param.put("sortCol", sortCol);
        param.put("sortOrder", sortOrder);

        return getSqlMapClientTemplate().queryForList("MS-PROJECT-RISK-REPORT-FIND-BY-CONDITION", param);

    }

	/**
	 *  Query DB table <tt>project_risk_report</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select COUNT(*) from project_risk_report report where (1 = 1)</tt>
	 *
	 *	@param projectRiskReport
	 *	@param startTimeBegin
	 *	@param startTimeEnd
	 *	@param loginUserId
	 *	@param deptIdList
	 *	@param relatedRoleList
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long findByConditionCount(ProjectRiskReportDO projectRiskReport, Date startTimeBegin, Date startTimeEnd, long loginUserId, List deptIdList, List relatedRoleList) throws DataAccessException {
    	if (projectRiskReport == null) {
    		throw new IllegalArgumentException("Can't select by a null data object.");
    	}

        Map param = new HashMap();

        param.put("projectRiskReport", projectRiskReport);
        param.put("startTimeBegin", startTimeBegin);
        param.put("startTimeEnd", startTimeEnd);
        param.put("loginUserId", new Long(loginUserId));
        param.put("deptIdList", deptIdList);
        param.put("relatedRoleList", relatedRoleList);

	    Long retObj = (Long) getSqlMapClientTemplate().queryForObject("MS-PROJECT-RISK-REPORT-FIND-BY-CONDITION-COUNT", param);

		if (retObj == null) {
		    return 0;
		} else {
		    return retObj.longValue();
		}

    }

}