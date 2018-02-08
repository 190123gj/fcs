/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.dal.ibatis;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import com.born.fcs.pm.dal.daointerface.AppAboutConfDAO;


// auto generated imports
import com.born.fcs.pm.dal.dataobject.AppAboutConfDO;
import org.springframework.dao.DataAccessException;

/**
 * An ibatis based implementation of dao interface <tt>com.born.fcs.pm.dal.daointerface.AppAboutConfDAO</tt>.
 *
 * This file is generated by <tt>specialmer-dalgen</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>paygw</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/app_about_conf.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>specialmer-dalgen</tt> 
 * to generate this file.
 *
 * @author peigen
 */ 

public class IbatisAppAboutConfDAO extends SqlMapClientDaoSupport implements AppAboutConfDAO {
	/**
	 *  Insert one <tt>AppAboutConfDO</tt> object to DB table <tt>app_about_conf</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into app_about_conf(conf_id,online,content,logo,ios_path,ios_version,ios_change_log,ios_force_update,ios_option_update,ios_two_dimension,android_path,android_version,android_change_log,android_force_update,android_option_update,android_two_dimension,raw_add_time) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)</tt>
	 *
	 *	@param appAboutConf
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long insert(AppAboutConfDO appAboutConf) throws DataAccessException {
    	if (appAboutConf == null) {
    		throw new IllegalArgumentException("Can't insert a null data object into db.");
    	}
    	
        getSqlMapClientTemplate().insert("MS-APP-ABOUT-CONF-INSERT", appAboutConf);

        return appAboutConf.getConfId();
    }

	/**
	 *  Update DB table <tt>app_about_conf</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update app_about_conf set online=?, content=?, logo=?, ios_path=?, ios_version=?, ios_change_log=?, ios_force_update=?, ios_option_update=?, ios_two_dimension=?, android_path=?, android_version=?, android_change_log=?, android_force_update=?, android_option_update=?, android_two_dimension=? where (conf_id = ?)</tt>
	 *
	 *	@param appAboutConf
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int update(AppAboutConfDO appAboutConf) throws DataAccessException {
    	if (appAboutConf == null) {
    		throw new IllegalArgumentException("Can't update by a null data object.");
    	}


        return getSqlMapClientTemplate().update("MS-APP-ABOUT-CONF-UPDATE", appAboutConf);
    }

	/**
	 *  Query DB table <tt>app_about_conf</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from app_about_conf where (conf_id = ?)</tt>
	 *
	 *	@param confId
	 *	@return AppAboutConfDO
	 *	@throws DataAccessException
	 */	 
    public AppAboutConfDO findById(long confId) throws DataAccessException {
        Long param = new Long(confId);

        return (AppAboutConfDO) getSqlMapClientTemplate().queryForObject("MS-APP-ABOUT-CONF-FIND-BY-ID", param);

    }

	/**
	 *  Query DB table <tt>app_about_conf</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from app_about_conf order by conf_id DESC</tt>
	 *
	 *	@return AppAboutConfDO
	 *	@throws DataAccessException
	 */	 
    public AppAboutConfDO findOne() throws DataAccessException {

        return (AppAboutConfDO) getSqlMapClientTemplate().queryForObject("MS-APP-ABOUT-CONF-FIND-ONE", null);

    }

	/**
	 *  Delete records from DB table <tt>app_about_conf</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from app_about_conf where (conf_id = ?)</tt>
	 *
	 *	@param confId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteById(long confId) throws DataAccessException {
        Long param = new Long(confId);

        return getSqlMapClientTemplate().delete("MS-APP-ABOUT-CONF-DELETE-BY-ID", param);
    }

}