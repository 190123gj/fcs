/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.dal.ibatis;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import com.born.fcs.pm.dal.daointerface.FStampApplyFileDAO;


// auto generated imports
import com.born.fcs.pm.dal.dataobject.FStampApplyFileDO;
import org.springframework.dao.DataAccessException;
import java.util.List;

/**
 * An ibatis based implementation of dao interface <tt>com.born.fcs.pm.dal.daointerface.FStampApplyFileDAO</tt>.
 *
 * This file is generated by <tt>specialmer-dalgen</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>paygw</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/f_stamp_apply_file.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>specialmer-dalgen</tt> 
 * to generate this file.
 *
 * @author peigen
 */ 
@SuppressWarnings("unchecked")

public class IbatisFStampApplyFileDAO extends SqlMapClientDaoSupport implements FStampApplyFileDAO {
	/**
	 *  Insert one <tt>FStampApplyFileDO</tt> object to DB table <tt>f_stamp_apply_file</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into f_stamp_apply_file(apply_id,replace_apply_id,contract_code,file_name,file_conent,legal_chapter_num,cachet_num,is_replace_old,old_file_num,sort_order,remark,old_legal_chapter_num,old_cachet_num,old_file_name,old_file_content,source,invalid,raw_add_time) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)</tt>
	 *
	 *	@param FStampApplyFile
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long insert(FStampApplyFileDO FStampApplyFile) throws DataAccessException {
    	if (FStampApplyFile == null) {
    		throw new IllegalArgumentException("Can't insert a null data object into db.");
    	}
    	
        getSqlMapClientTemplate().insert("MS-F-STAMP-APPLY-FILE-INSERT", FStampApplyFile);

        return FStampApplyFile.getId();
    }

	/**
	 *  Update DB table <tt>f_stamp_apply_file</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update f_stamp_apply_file set apply_id=?, contract_code=?, replace_apply_id=?, file_name=?, file_conent=?, legal_chapter_num=?, cachet_num=?, is_replace_old=?, old_file_num=?, sort_order=?, remark=?, old_legal_chapter_num=?, old_cachet_num=?, old_file_name=?, old_file_content=?, source=?, invalid=? where (id = ?)</tt>
	 *
	 *	@param FStampApplyFile
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int update(FStampApplyFileDO FStampApplyFile) throws DataAccessException {
    	if (FStampApplyFile == null) {
    		throw new IllegalArgumentException("Can't update by a null data object.");
    	}


        return getSqlMapClientTemplate().update("MS-F-STAMP-APPLY-FILE-UPDATE", FStampApplyFile);
    }

	/**
	 *  Query DB table <tt>f_stamp_apply_file</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from f_stamp_apply_file where (id = ?)</tt>
	 *
	 *	@param id
	 *	@return FStampApplyFileDO
	 *	@throws DataAccessException
	 */	 
    public FStampApplyFileDO findById(long id) throws DataAccessException {
        Long param = new Long(id);

        return (FStampApplyFileDO) getSqlMapClientTemplate().queryForObject("MS-F-STAMP-APPLY-FILE-FIND-BY-ID", param);

    }

	/**
	 *  Query DB table <tt>f_stamp_apply_file</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from f_stamp_apply_file where (apply_id = ?)</tt>
	 *
	 *	@param applyId
	 *	@return List<FStampApplyFileDO>
	 *	@throws DataAccessException
	 */	 
    public List<FStampApplyFileDO> findByApplyId(long applyId) throws DataAccessException {
        Long param = new Long(applyId);

        return getSqlMapClientTemplate().queryForList("MS-F-STAMP-APPLY-FILE-FIND-BY-APPLY-ID", param);

    }

	/**
	 *  Query DB table <tt>f_stamp_apply_file</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from f_stamp_apply_file where ((contract_code = ?) AND ((invalid IS NULL) OR (invalid = '')))</tt>
	 *
	 *	@param contractCode
	 *	@return List<FStampApplyFileDO>
	 *	@throws DataAccessException
	 */	 
    public List<FStampApplyFileDO> findByContractCode(String contractCode) throws DataAccessException {

        return getSqlMapClientTemplate().queryForList("MS-F-STAMP-APPLY-FILE-FIND-BY-CONTRACT-CODE", contractCode);

    }

	/**
	 *  Query DB table <tt>f_stamp_apply_file</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from f_stamp_apply_file where (replace_apply_id = ?)</tt>
	 *
	 *	@param replaceApplyId
	 *	@return List<FStampApplyFileDO>
	 *	@throws DataAccessException
	 */	 
    public List<FStampApplyFileDO> findByReplaceApplyId(long replaceApplyId) throws DataAccessException {
        Long param = new Long(replaceApplyId);

        return getSqlMapClientTemplate().queryForList("MS-F-STAMP-APPLY-FILE-FIND-BY-REPLACE-APPLY-ID", param);

    }

	/**
	 *  Delete records from DB table <tt>f_stamp_apply_file</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from f_stamp_apply_file where (id = ?)</tt>
	 *
	 *	@param id
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteById(long id) throws DataAccessException {
        Long param = new Long(id);

        return getSqlMapClientTemplate().delete("MS-F-STAMP-APPLY-FILE-DELETE-BY-ID", param);
    }

	/**
	 *  Delete records from DB table <tt>f_stamp_apply_file</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from f_stamp_apply_file where (apply_id = ?)</tt>
	 *
	 *	@param applyId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteByApplyId(long applyId) throws DataAccessException {
        Long param = new Long(applyId);

        return getSqlMapClientTemplate().delete("MS-F-STAMP-APPLY-FILE-DELETE-BY-APPLY-ID", param);
    }

}