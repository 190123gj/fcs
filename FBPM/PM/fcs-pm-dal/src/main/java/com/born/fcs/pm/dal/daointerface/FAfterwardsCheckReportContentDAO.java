/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.dal.daointerface;

// auto generated imports
import com.born.fcs.pm.dal.dataobject.FAfterwardsCheckReportContentDO;
import org.springframework.dao.DataAccessException;

/**
 * A dao interface provides methods to access database table <tt>f_afterwards_check_report_content</tt>.
 *
 * This file is generated by <tt>specialmer-dalgen</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>paygw</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/f_afterwards_check_report_content.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>specialmer-dalgen</tt> 
 * to generate this file.
 *
 * @author peigen
 */

public interface FAfterwardsCheckReportContentDAO {
	/**
	 *  Insert one <tt>FAfterwardsCheckReportContentDO</tt> object to DB table <tt>f_afterwards_check_report_content</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into f_afterwards_check_report_content(form_id,import_excel,use_way_conditions,project_finish_desc,income_check_desc,management_matters,decision_way,counter_check,related_enterprise,other_explain,analysis_conclusion,content_remark1,content_remark2,content_remark3,date1,date2,date3,amount_unit1,amount_unit2,amount_unit3,raw_add_time) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)</tt>
	 *
	 *	@param FAfterwardsCheckReportContent
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long insert(FAfterwardsCheckReportContentDO FAfterwardsCheckReportContent) throws DataAccessException;

	/**
	 *  Update DB table <tt>f_afterwards_check_report_content</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update f_afterwards_check_report_content set form_id=?, import_excel=?, use_way_conditions=?, project_finish_desc=?, income_check_desc=?, management_matters=?, decision_way=?, counter_check=?, related_enterprise=?, other_explain=?, analysis_conclusion=?, content_remark1=?, content_remark2=?, content_remark3=?, date1=?, date2=?, date3=?, amount_unit1=?, amount_unit2=?, amount_unit3=? where (content_id = ?)</tt>
	 *
	 *	@param FAfterwardsCheckReportContent
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int update(FAfterwardsCheckReportContentDO FAfterwardsCheckReportContent) throws DataAccessException;

	/**
	 *  Update DB table <tt>f_afterwards_check_report_content</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update f_afterwards_check_report_content set import_excel=?, use_way_conditions=?, project_finish_desc=?, income_check_desc=?, management_matters=?, decision_way=?, counter_check=?, related_enterprise=?, other_explain=?, analysis_conclusion=?, content_remark1=?, content_remark2=?, content_remark3=?, date1=?, date2=?, date3=?, amount_unit1=?, amount_unit2=?, amount_unit3=? where (form_id = ?)</tt>
	 *
	 *	@param FAfterwardsCheckReportContent
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int updateByFormId(FAfterwardsCheckReportContentDO FAfterwardsCheckReportContent) throws DataAccessException;

	/**
	 *  Query DB table <tt>f_afterwards_check_report_content</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from f_afterwards_check_report_content where (content_id = ?)</tt>
	 *
	 *	@param contentId
	 *	@return FAfterwardsCheckReportContentDO
	 *	@throws DataAccessException
	 */	 
    public FAfterwardsCheckReportContentDO findById(long contentId) throws DataAccessException;

	/**
	 *  Query DB table <tt>f_afterwards_check_report_content</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from f_afterwards_check_report_content where (form_id = ?)</tt>
	 *
	 *	@param formId
	 *	@return FAfterwardsCheckReportContentDO
	 *	@throws DataAccessException
	 */	 
    public FAfterwardsCheckReportContentDO findByFormId(long formId) throws DataAccessException;

	/**
	 *  Delete records from DB table <tt>f_afterwards_check_report_content</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from f_afterwards_check_report_content where (content_id = ?)</tt>
	 *
	 *	@param contentId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteById(long contentId) throws DataAccessException;

	/**
	 *  Delete records from DB table <tt>f_afterwards_check_report_content</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from f_afterwards_check_report_content where (form_id = ?)</tt>
	 *
	 *	@param formId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteByFormId(long formId) throws DataAccessException;

}