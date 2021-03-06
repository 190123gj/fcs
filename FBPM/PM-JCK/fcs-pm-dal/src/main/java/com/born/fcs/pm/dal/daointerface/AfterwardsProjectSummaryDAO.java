/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.dal.daointerface;

// auto generated imports
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.born.fcs.pm.dal.dataobject.AfterwardsProjectSummaryDO;

/**
 * A dao interface provides methods to access database table
 * <tt>afterwards_project_summary</tt>.
 *
 * This file is generated by <tt>specialmer-dalgen</tt>, a DAL (Data Access
 * Layer) code generation utility specially developed for <tt>paygw</tt>
 * project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may be
 * OVERWRITTEN by someone else. To modify the file, you should go to directory
 * <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and find the corresponding
 * configuration file (<tt>tables/afterwards_project_summary.xml</tt>). Modify
 * the configuration file according to your needs, then run
 * <tt>specialmer-dalgen</tt> to generate this file.
 *
 * @author peigen
 */
@SuppressWarnings("rawtypes")
public interface AfterwardsProjectSummaryDAO {
	/**
	 * Insert one <tt>AfterwardsProjectSummaryDO</tt> object to DB table
	 * <tt>afterwards_project_summary</tt>, return primary key
	 *
	 * <p>
	 * The sql statement for this operation is <br>
	 * <tt>insert into afterwards_project_summary(dept_id,dept_code,dept_name,report_period,submit_man_id,submit_man_name,submit_man_account,guarantee_households,guarantee_releasing_amount,loan_households,loan_releasing_amount,credit_risk,credit_after_check,credit_after_check_prob,raw_add_time) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)</tt>
	 *
	 * @param afterwardsProjectSummary
	 * @return long
	 * @throws DataAccessException
	 */
	public long insert(AfterwardsProjectSummaryDO afterwardsProjectSummary)
																			throws DataAccessException;
	
	/**
	 * Update DB table <tt>afterwards_project_summary</tt>.
	 *
	 * <p>
	 * The sql statement for this operation is <br>
	 * <tt>update afterwards_project_summary set dept_id=?, dept_code=?, dept_name=?, report_period=?, submit_man_id=?, submit_man_name=?, submit_man_account=?, guarantee_households=?, guarantee_releasing_amount=?, loan_households=?, loan_releasing_amount=?, credit_risk=?, credit_after_check=?, credit_after_check_prob=? where (summary_id = ?)</tt>
	 *
	 * @param afterwardsProjectSummary
	 * @return int
	 * @throws DataAccessException
	 */
	public int update(AfterwardsProjectSummaryDO afterwardsProjectSummary)
																			throws DataAccessException;
	
	/**
	 * Query DB table <tt>afterwards_project_summary</tt> for records.
	 *
	 * <p>
	 * The sql statement for this operation is <br>
	 * <tt>select * from afterwards_project_summary where (summary_id = ?)</tt>
	 *
	 * @param summaryId
	 * @return AfterwardsProjectSummaryDO
	 * @throws DataAccessException
	 */
	public AfterwardsProjectSummaryDO findById(long summaryId) throws DataAccessException;
	
	/**
	 * Query DB table <tt>afterwards_project_summary</tt> for records.
	 *
	 * <p>
	 * The sql statement for this operation is <br>
	 * <tt>select * from afterwards_project_summary where (dept_name = ?)</tt>
	 *
	 * @param deptName
	 * @return List<AfterwardsProjectSummaryDO>
	 * @throws DataAccessException
	 */
	public List<AfterwardsProjectSummaryDO> findByDeptName(String deptName)
																			throws DataAccessException;
	
	/**
	 * Query DB table <tt>afterwards_project_summary</tt> for records.
	 *
	 * <p>
	 * The sql statement for this operation is <br>
	 * <tt>select * from afterwards_project_summary where ((dept_code = ?) AND (report_period = ?))</tt>
	 *
	 * @param deptCode
	 * @param reportPeriod
	 * @return AfterwardsProjectSummaryDO
	 * @throws DataAccessException
	 */
	public AfterwardsProjectSummaryDO findByDeptCodeAndReportPeriod(String deptCode,
																	String reportPeriod)
																						throws DataAccessException;
	
	/**
	 * Delete records from DB table <tt>afterwards_project_summary</tt>.
	 *
	 * <p>
	 * The sql statement for this operation is <br>
	 * <tt>delete from afterwards_project_summary where (summary_id = ?)</tt>
	 *
	 * @param summaryId
	 * @return int
	 * @throws DataAccessException
	 */
	public int deleteById(long summaryId) throws DataAccessException;
	
	/**
	 * Query DB table <tt>afterwards_project_summary</tt> for records.
	 *
	 * <p>
	 * The sql statement for this operation is <br>
	 * <tt>select * from afterwards_project_summary where (summary_id = ?)</tt>
	 *
	 * @param afterwardsProjectSummary
	 * @param limitStart
	 * @param pageSize
	 * @return List<AfterwardsProjectSummaryDO>
	 * @throws DataAccessException
	 */
	public List<AfterwardsProjectSummaryDO> findByCondition(AfterwardsProjectSummaryDO afterwardsProjectSummary,
															long limitStart, long pageSize,
															String sortOrder, String sortCol)
																								throws DataAccessException;
	
	/**
	 * Query DB table <tt>afterwards_project_summary</tt> for records.
	 *
	 * <p>
	 * The sql statement for this operation is <br>
	 * <tt>select * from afterwards_project_summary where (summary_id = ?)</tt>
	 *
	 * @param afterwardsProjectSummary
	 * @return long
	 * @throws DataAccessException
	 */
	public long findByConditionCount(AfterwardsProjectSummaryDO afterwardsProjectSummary)
																							throws DataAccessException;
	
}