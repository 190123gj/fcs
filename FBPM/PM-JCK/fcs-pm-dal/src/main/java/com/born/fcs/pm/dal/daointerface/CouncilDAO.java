/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.dal.daointerface;

// auto generated imports
import com.born.fcs.pm.dal.dataobject.CouncilDO;
import org.springframework.dao.DataAccessException;
import java.util.Date;
import java.util.List;

/**
 * A dao interface provides methods to access database table <tt>council</tt>.
 *
 * This file is generated by <tt>specialmer-dalgen</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>paygw</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/council.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>specialmer-dalgen</tt> 
 * to generate this file.
 *
 * @author peigen
 */
 @SuppressWarnings("rawtypes") 
public interface CouncilDAO {
	/**
	 *  Insert one <tt>CouncilDO</tt> object to DB table <tt>council</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into council(council_code,council_subject,start_time,end_time,council_type,council_type_code,council_type_name,council_place,council_online,status,decision_institution_id,major_num,less_num,if_vote,vote_rule_type,pass_num,indeterminate_num,pass_rate,indeterminate_rate,summary_url,create_man_id,create_man_account,create_man_name,company_name,raw_add_time) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)</tt>
	 *
	 *	@param council
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long insert(CouncilDO council) throws DataAccessException;

	/**
	 *  Update DB table <tt>council</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update council set council_code=?, council_subject=?, start_time=?, end_time=?, council_type=?, council_type_code=?, council_type_name=?, council_place=?, council_online=?, status=?, decision_institution_id=?, major_num=?, less_num=?, if_vote=?, vote_rule_type=?, pass_num=?, indeterminate_num=?, pass_rate=?, indeterminate_rate=?, summary_url=?, create_man_id=?, create_man_account=?, create_man_name=?, company_name=? where (council_id = ?)</tt>
	 *
	 *	@param council
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int update(CouncilDO council) throws DataAccessException;

	/**
	 *  Query DB table <tt>council</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from council where (council_id = ?)</tt>
	 *
	 *	@param councilId
	 *	@return CouncilDO
	 *	@throws DataAccessException
	 */	 
    public CouncilDO findById(long councilId) throws DataAccessException;

	/**
	 *  Query DB table <tt>council</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from council where ((start_time = ?) AND (council_place = ?))</tt>
	 *
	 *	@param startTime
	 *	@param councilPlace
	 *	@return List<CouncilDO>
	 *	@throws DataAccessException
	 */	 
    public List<CouncilDO> findByStartTimeAndPlace(Date startTime, String councilPlace) throws DataAccessException;

	/**
	 *  Delete records from DB table <tt>council</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from council where (council_id = ?)</tt>
	 *
	 *	@param councilId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteById(long councilId) throws DataAccessException;

	/**
	 *  Query DB table <tt>council</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from council where (1 = 1)</tt>
	 *
	 *	@param council
	 *	@param limitStart
	 *	@param pageSize
	 *	@param startTimeBegin
	 *	@param startTimeEnd
	 *	@param councilTypeCodeList
	 *	@param companyNames
	 *	@param statusList
	 *	@return List<CouncilDO>
	 *	@throws DataAccessException
	 */	 
    public List<CouncilDO> findByCondition(CouncilDO council, long limitStart, long pageSize, Date startTimeBegin, Date startTimeEnd, List councilTypeCodeList, List companyNames, List statusList) throws DataAccessException;

	/**
	 *  Query DB table <tt>council</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select COUNT(*) from council where (1 = 1)</tt>
	 *
	 *	@param council
	 *	@param startTimeBegin
	 *	@param startTimeEnd
	 *	@param councilTypeCodeList
	 *	@param companyNames
	 *	@param statusList
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long findByConditionCount(CouncilDO council, Date startTimeBegin, Date startTimeEnd, List councilTypeCodeList, List companyNames, List statusList) throws DataAccessException;

	/**
	 *  Query DB table <tt>council</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from council where (1 = 1)</tt>
	 *
	 *	@param council
	 *	@param relatveId
	 *	@param limitStart
	 *	@param pageSize
	 *	@param startTimeBegin
	 *	@param startTimeEnd
	 *	@param companyNames
	 *	@param statusList
	 *	@return List<CouncilDO>
	 *	@throws DataAccessException
	 */	 
    public List<CouncilDO> findByConditionWithRelatveId(CouncilDO council, long relatveId, long limitStart, long pageSize, Date startTimeBegin, Date startTimeEnd, List companyNames, List statusList) throws DataAccessException;

	/**
	 *  Query DB table <tt>council</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select COUNT(*) from council where (1 = 1)</tt>
	 *
	 *	@param council
	 *	@param relatveId
	 *	@param startTimeBegin
	 *	@param startTimeEnd
	 *	@param companyNames
	 *	@param statusList
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long findByConditionWithRelatveIdCount(CouncilDO council, long relatveId, Date startTimeBegin, Date startTimeEnd, List companyNames, List statusList) throws DataAccessException;

	/**
	 *  Query DB table <tt>council</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from council where (council_type = ?)</tt>
	 *
	 *	@param councilType
	 *	@return List<CouncilDO>
	 *	@throws DataAccessException
	 */	 
    public List<CouncilDO> findByCouncilType(long councilType) throws DataAccessException;

	/**
	 *  Query DB table <tt>council</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from council where (1 = 1)</tt>
	 *
	 *	@return List<CouncilDO>
	 *	@throws DataAccessException
	 */	 
    public List<CouncilDO> queryCouncilByCouncilSummary() throws DataAccessException;

}