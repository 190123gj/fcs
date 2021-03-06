/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.rm.dal.daointerface;

// auto generated imports
import com.born.fcs.rm.dal.dataobject.RegularProjectChannelInfoDO;
import org.springframework.dao.DataAccessException;
import java.util.Date;

/**
 * A dao interface provides methods to access database table <tt>regular_project_channel_info</tt>.
 *
 * This file is generated by <tt>specialmer-dalgen</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>paygw</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/regular_project_channel_info.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>specialmer-dalgen</tt> 
 * to generate this file.
 *
 * @author peigen
 */

public interface RegularProjectChannelInfoDAO {
	/**
	 *  Insert one <tt>RegularProjectChannelInfoDO</tt> object to DB table <tt>regular_project_channel_info</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into regular_project_channel_info(id,report_date,customer_id,customer_name,project_code,project_name,channel_relation,channel_code,channel_name,channel_type,is_new,is_insurance,count_rate,customer_count_rate,first_occur_date,contract_amount,occur_amount,occur_amount_this_month,occur_amount_this_year,release_amount,release_amount_this_month,release_amount_this_year,balance,balance_beginning,raw_add_time) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)</tt>
	 *
	 *	@param regularProjectChannelInfo
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long insert(RegularProjectChannelInfoDO regularProjectChannelInfo) throws DataAccessException;

	/**
	 *  Update DB table <tt>regular_project_channel_info</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update regular_project_channel_info set report_date=?, customer_id=?, customer_name=?, project_code=?, project_name=?, channel_relation=?, channel_code=?, channel_name=?, channel_type=?, is_new=?, is_insurance=?, count_rate=?, customer_count_rate=?, first_occur_date=?, contract_amount=?, occur_amount=?, occur_amount_this_month=?, occur_amount_this_year=?, release_amount=?, release_amount_this_month=?, release_amount_this_year=?, balance=?, balance_beginning=? where (id = ?)</tt>
	 *
	 *	@param regularProjectChannelInfo
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int update(RegularProjectChannelInfoDO regularProjectChannelInfo) throws DataAccessException;

	/**
	 *  Delete records from DB table <tt>regular_project_channel_info</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from regular_project_channel_info where (report_date = ?)</tt>
	 *
	 *	@param reportDate
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteByReportDate(Date reportDate) throws DataAccessException;

	/**
	 *  Delete records from DB table <tt>regular_project_channel_info</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from regular_project_channel_info where ((report_date = ?) AND (channel_relation = ?))</tt>
	 *
	 *	@param reportDate
	 *	@param channelRelation
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteByReportDateAndRelation(Date reportDate, String channelRelation) throws DataAccessException;

}