/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.dal.ibatis;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import com.born.fcs.pm.dal.daointerface.ProjectRiskReportCompDetailDAO;


// auto generated imports
import com.born.fcs.pm.dal.dataobject.ProjectRiskReportCompDetailDO;
import org.springframework.dao.DataAccessException;
import java.util.Date;
import java.util.List;
import java.util.Map;
	import java.util.HashMap;
	
/**
 * An ibatis based implementation of dao interface <tt>com.born.fcs.pm.dal.daointerface.ProjectRiskReportCompDetailDAO</tt>.
 *
 * This file is generated by <tt>specialmer-dalgen</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>paygw</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/project_risk_report_comp_detail.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>specialmer-dalgen</tt> 
 * to generate this file.
 *
 * @author peigen
 */ 
@SuppressWarnings({ "unchecked", "rawtypes" })

public class IbatisProjectRiskReportCompDetailDAO extends SqlMapClientDaoSupport implements ProjectRiskReportCompDetailDAO {
	/**
	 *  Insert one <tt>ProjectRiskReportCompDetailDO</tt> object to DB table <tt>project_risk_report_comp_detail</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into project_risk_report_comp_detail(detail_id,report_id,project_code,comp_amount,comp_date,raw_add_time) values (?, ?, ?, ?, ?, ?)</tt>
	 *
	 *	@param projectRiskReportCompDetail
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long insert(ProjectRiskReportCompDetailDO projectRiskReportCompDetail) throws DataAccessException {
    	if (projectRiskReportCompDetail == null) {
    		throw new IllegalArgumentException("Can't insert a null data object into db.");
    	}
    	
        getSqlMapClientTemplate().insert("MS-PROJECT-RISK-REPORT-COMP-DETAIL-INSERT", projectRiskReportCompDetail);

        return projectRiskReportCompDetail.getDetailId();
    }

	/**
	 *  Update DB table <tt>project_risk_report_comp_detail</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update project_risk_report_comp_detail set report_id=?, project_code=?, comp_amount=?, comp_date=? where (detail_id = ?)</tt>
	 *
	 *	@param projectRiskReportCompDetail
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int update(ProjectRiskReportCompDetailDO projectRiskReportCompDetail) throws DataAccessException {
    	if (projectRiskReportCompDetail == null) {
    		throw new IllegalArgumentException("Can't update by a null data object.");
    	}


        return getSqlMapClientTemplate().update("MS-PROJECT-RISK-REPORT-COMP-DETAIL-UPDATE", projectRiskReportCompDetail);
    }

	/**
	 *  Query DB table <tt>project_risk_report_comp_detail</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select detail_id, report_id, project_code, comp_amount, comp_date, raw_add_time, raw_update_time from project_risk_report_comp_detail where (detail_id = ?)</tt>
	 *
	 *	@param detailId
	 *	@return ProjectRiskReportCompDetailDO
	 *	@throws DataAccessException
	 */	 
    public ProjectRiskReportCompDetailDO findById(long detailId) throws DataAccessException {
        Long param = new Long(detailId);

        return (ProjectRiskReportCompDetailDO) getSqlMapClientTemplate().queryForObject("MS-PROJECT-RISK-REPORT-COMP-DETAIL-FIND-BY-ID", param);

    }

	/**
	 *  Delete records from DB table <tt>project_risk_report_comp_detail</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from project_risk_report_comp_detail where (detail_id = ?)</tt>
	 *
	 *	@param detailId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteById(long detailId) throws DataAccessException {
        Long param = new Long(detailId);

        return getSqlMapClientTemplate().delete("MS-PROJECT-RISK-REPORT-COMP-DETAIL-DELETE-BY-ID", param);
    }

	/**
	 *  Delete records from DB table <tt>project_risk_report_comp_detail</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from project_risk_report_comp_detail where (report_id = ?)</tt>
	 *
	 *	@param reportId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteByReportId(long reportId) throws DataAccessException {
        Long param = new Long(reportId);

        return getSqlMapClientTemplate().delete("MS-PROJECT-RISK-REPORT-COMP-DETAIL-DELETE-BY-REPORT-ID", param);
    }

	/**
	 *  Query DB table <tt>project_risk_report_comp_detail</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select detail_id, report_id, project_code, comp_amount, comp_date, raw_add_time, raw_update_time from project_risk_report_comp_detail where (1 = 1)</tt>
	 *
	 *	@param projectRiskReportCompDetail
	 *	@param limitStart
	 *	@param pageSize
	 *	@param startTimeBegin
	 *	@param startTimeEnd
	 *	@return List<ProjectRiskReportCompDetailDO>
	 *	@throws DataAccessException
	 */	 
    public List<ProjectRiskReportCompDetailDO> findByCondition(ProjectRiskReportCompDetailDO projectRiskReportCompDetail, long limitStart, long pageSize, Date startTimeBegin, Date startTimeEnd) throws DataAccessException {
    	if (projectRiskReportCompDetail == null) {
    		throw new IllegalArgumentException("Can't select by a null data object.");
    	}

        Map param = new HashMap();

        param.put("projectRiskReportCompDetail", projectRiskReportCompDetail);
        param.put("limitStart", new Long(limitStart));
        param.put("pageSize", new Long(pageSize));
        param.put("startTimeBegin", startTimeBegin);
        param.put("startTimeEnd", startTimeEnd);

        return getSqlMapClientTemplate().queryForList("MS-PROJECT-RISK-REPORT-COMP-DETAIL-FIND-BY-CONDITION", param);

    }

	/**
	 *  Query DB table <tt>project_risk_report_comp_detail</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select COUNT(*) from project_risk_report_comp_detail where (1 = 1)</tt>
	 *
	 *	@param projectRiskReportCompDetail
	 *	@param startTimeBegin
	 *	@param startTimeEnd
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long findByConditionCount(ProjectRiskReportCompDetailDO projectRiskReportCompDetail, Date startTimeBegin, Date startTimeEnd) throws DataAccessException {
    	if (projectRiskReportCompDetail == null) {
    		throw new IllegalArgumentException("Can't select by a null data object.");
    	}

        Map param = new HashMap();

        param.put("projectRiskReportCompDetail", projectRiskReportCompDetail);
        param.put("startTimeBegin", startTimeBegin);
        param.put("startTimeEnd", startTimeEnd);

	    Long retObj = (Long) getSqlMapClientTemplate().queryForObject("MS-PROJECT-RISK-REPORT-COMP-DETAIL-FIND-BY-CONDITION-COUNT", param);

		if (retObj == null) {
		    return 0;
		} else {
		    return retObj.longValue();
		}

    }

}