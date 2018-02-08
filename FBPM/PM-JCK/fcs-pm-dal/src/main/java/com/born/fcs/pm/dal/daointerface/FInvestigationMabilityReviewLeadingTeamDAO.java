/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.dal.daointerface;

// auto generated imports
import com.born.fcs.pm.dal.dataobject.FInvestigationMabilityReviewLeadingTeamDO;
import org.springframework.dao.DataAccessException;
import java.util.List;

/**
 * A dao interface provides methods to access database table <tt>f_investigation_mability_review_leading_team</tt>.
 *
 * This file is generated by <tt>specialmer-dalgen</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>paygw</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/f_investigation_mability_review_leading_team.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>specialmer-dalgen</tt> 
 * to generate this file.
 *
 * @author peigen
 */
 @SuppressWarnings("rawtypes") 
public interface FInvestigationMabilityReviewLeadingTeamDAO {
	/**
	 *  Insert one <tt>FInvestigationMabilityReviewLeadingTeamDO</tt> object to DB table <tt>f_investigation_mability_review_leading_team</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into f_investigation_mability_review_leading_team(ma_review_id,owner,name,sex,age,degree,title,resume,sort_order,raw_add_time) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)</tt>
	 *
	 *	@param FInvestigationMabilityReviewLeadingTeam
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long insert(FInvestigationMabilityReviewLeadingTeamDO FInvestigationMabilityReviewLeadingTeam) throws DataAccessException;

	/**
	 *  Update DB table <tt>f_investigation_mability_review_leading_team</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update f_investigation_mability_review_leading_team set ma_review_id=?, owner=?, name=?, sex=?, age=?, degree=?, title=?, resume=?, sort_order=? where (id = ?)</tt>
	 *
	 *	@param FInvestigationMabilityReviewLeadingTeam
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int update(FInvestigationMabilityReviewLeadingTeamDO FInvestigationMabilityReviewLeadingTeam) throws DataAccessException;

	/**
	 *  Query DB table <tt>f_investigation_mability_review_leading_team</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from f_investigation_mability_review_leading_team where (id = ?)</tt>
	 *
	 *	@param id
	 *	@return FInvestigationMabilityReviewLeadingTeamDO
	 *	@throws DataAccessException
	 */	 
    public FInvestigationMabilityReviewLeadingTeamDO findById(long id) throws DataAccessException;

	/**
	 *  Query DB table <tt>f_investigation_mability_review_leading_team</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from f_investigation_mability_review_leading_team where ((owner = ?) AND (ma_review_id = ?)) order by sort_order ASC</tt>
	 *
	 *	@param owner
	 *	@param maReviewId
	 *	@return List<FInvestigationMabilityReviewLeadingTeamDO>
	 *	@throws DataAccessException
	 */	 
    public List<FInvestigationMabilityReviewLeadingTeamDO> findByOwnerAndReviewId(String owner, long maReviewId) throws DataAccessException;

	/**
	 *  Delete records from DB table <tt>f_investigation_mability_review_leading_team</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from f_investigation_mability_review_leading_team where (id = ?)</tt>
	 *
	 *	@param id
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteById(long id) throws DataAccessException;

	/**
	 *  Delete records from DB table <tt>f_investigation_mability_review_leading_team</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from f_investigation_mability_review_leading_team where ((owner = ?) AND (ma_review_id = ?))</tt>
	 *
	 *	@param owner
	 *	@param maReviewId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteByOwnerAndReviewId(String owner, long maReviewId) throws DataAccessException;

}