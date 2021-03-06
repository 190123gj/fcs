/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.dal.ibatis;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import com.born.fcs.pm.dal.daointerface.FormChangeRecordDetailDAO;


// auto generated imports
import com.born.fcs.pm.dal.dataobject.FormChangeRecordDetailDO;
import org.springframework.dao.DataAccessException;
import java.util.List;

/**
 * An ibatis based implementation of dao interface <tt>com.born.fcs.pm.dal.daointerface.FormChangeRecordDetailDAO</tt>.
 *
 * This file is generated by <tt>specialmer-dalgen</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>paygw</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/form_change_record_detail.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>specialmer-dalgen</tt> 
 * to generate this file.
 *
 * @author peigen
 */ 
@SuppressWarnings("unchecked")

public class IbatisFormChangeRecordDetailDAO extends SqlMapClientDaoSupport implements FormChangeRecordDetailDAO {
	/**
	 *  Insert one <tt>FormChangeRecordDetailDO</tt> object to DB table <tt>form_change_record_detail</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into form_change_record_detail(detail_id,record_id,label,name,old_text,old_value,new_value,new_text,raw_add_time) values (?, ?, ?, ?, ?, ?, ?, ?, ?)</tt>
	 *
	 *	@param formChangeRecordDetail
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long insert(FormChangeRecordDetailDO formChangeRecordDetail) throws DataAccessException {
    	if (formChangeRecordDetail == null) {
    		throw new IllegalArgumentException("Can't insert a null data object into db.");
    	}
    	
        getSqlMapClientTemplate().insert("MS-FORM-CHANGE-RECORD-DETAIL-INSERT", formChangeRecordDetail);

        return formChangeRecordDetail.getDetailId();
    }

	/**
	 *  Update DB table <tt>form_change_record_detail</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update form_change_record_detail set record_id=?, label=?, name=?, old_text=?, old_value=?, new_value=?, new_text=? where (detail_id = ?)</tt>
	 *
	 *	@param formChangeRecordDetail
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int update(FormChangeRecordDetailDO formChangeRecordDetail) throws DataAccessException {
    	if (formChangeRecordDetail == null) {
    		throw new IllegalArgumentException("Can't update by a null data object.");
    	}


        return getSqlMapClientTemplate().update("MS-FORM-CHANGE-RECORD-DETAIL-UPDATE", formChangeRecordDetail);
    }

	/**
	 *  Query DB table <tt>form_change_record_detail</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from form_change_record_detail where (detail_id = ?)</tt>
	 *
	 *	@param detailId
	 *	@return FormChangeRecordDetailDO
	 *	@throws DataAccessException
	 */	 
    public FormChangeRecordDetailDO findById(long detailId) throws DataAccessException {
        Long param = new Long(detailId);

        return (FormChangeRecordDetailDO) getSqlMapClientTemplate().queryForObject("MS-FORM-CHANGE-RECORD-DETAIL-FIND-BY-ID", param);

    }

	/**
	 *  Query DB table <tt>form_change_record_detail</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from form_change_record_detail where (record_id = ?)</tt>
	 *
	 *	@param recordId
	 *	@return List<FormChangeRecordDetailDO>
	 *	@throws DataAccessException
	 */	 
    public List<FormChangeRecordDetailDO> findByRecordId(long recordId) throws DataAccessException {
        Long param = new Long(recordId);

        return getSqlMapClientTemplate().queryForList("MS-FORM-CHANGE-RECORD-DETAIL-FIND-BY-RECORD-ID", param);

    }

	/**
	 *  Delete records from DB table <tt>form_change_record_detail</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from form_change_record_detail where (detail_id = ?)</tt>
	 *
	 *	@param detailId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteById(long detailId) throws DataAccessException {
        Long param = new Long(detailId);

        return getSqlMapClientTemplate().delete("MS-FORM-CHANGE-RECORD-DETAIL-DELETE-BY-ID", param);
    }

	/**
	 *  Delete records from DB table <tt>form_change_record_detail</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from form_change_record_detail where (record_id = ?)</tt>
	 *
	 *	@param recordId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteByRecordId(long recordId) throws DataAccessException {
        Long param = new Long(recordId);

        return getSqlMapClientTemplate().delete("MS-FORM-CHANGE-RECORD-DETAIL-DELETE-BY-RECORD-ID", param);
    }

}