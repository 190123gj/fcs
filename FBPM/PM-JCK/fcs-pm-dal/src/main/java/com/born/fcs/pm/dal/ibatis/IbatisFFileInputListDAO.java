/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.dal.ibatis;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import com.born.fcs.pm.dal.daointerface.FFileInputListDAO;


// auto generated imports
import com.born.fcs.pm.dal.dataobject.FFileInputListDO;
import org.springframework.dao.DataAccessException;
import java.util.List;
import java.util.Map;
	import java.util.HashMap;
	
/**
 * An ibatis based implementation of dao interface <tt>com.born.fcs.pm.dal.daointerface.FFileInputListDAO</tt>.
 *
 * This file is generated by <tt>specialmer-dalgen</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>paygw</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/f_file_input_list.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>specialmer-dalgen</tt> 
 * to generate this file.
 *
 * @author peigen
 */ 
@SuppressWarnings({ "unchecked", "rawtypes" })

public class IbatisFFileInputListDAO extends SqlMapClientDaoSupport implements FFileInputListDAO {
	/**
	 *  Insert one <tt>FFileInputListDO</tt> object to DB table <tt>f_file_input_list</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into f_file_input_list(file_id,id,file_type,file_name,archive_file_name,file_page,file_path,input_remark,sort_order,status,no,attach_type,curr_borrow_man_id,raw_add_time) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)</tt>
	 *
	 *	@param FFileInputList
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long insert(FFileInputListDO FFileInputList) throws DataAccessException {
    	if (FFileInputList == null) {
    		throw new IllegalArgumentException("Can't insert a null data object into db.");
    	}
    	
        getSqlMapClientTemplate().insert("MS-F-FILE-INPUT-LIST-INSERT", FFileInputList);

        return FFileInputList.getInputListId();
    }

	/**
	 *  Update DB table <tt>f_file_input_list</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update f_file_input_list set file_id=?, id=?, file_type=?, file_name=?, archive_file_name=?, file_page=?, file_path=?, input_remark=?, sort_order=?, status=?, no=?, attach_type=?, curr_borrow_man_id=? where (input_list_id = ?)</tt>
	 *
	 *	@param FFileInputList
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int update(FFileInputListDO FFileInputList) throws DataAccessException {
    	if (FFileInputList == null) {
    		throw new IllegalArgumentException("Can't update by a null data object.");
    	}


        return getSqlMapClientTemplate().update("MS-F-FILE-INPUT-LIST-UPDATE", FFileInputList);
    }

	/**
	 *  Query DB table <tt>f_file_input_list</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from f_file_input_list where (input_list_id = ?)</tt>
	 *
	 *	@param inputListId
	 *	@return FFileInputListDO
	 *	@throws DataAccessException
	 */	 
    public FFileInputListDO findById(long inputListId) throws DataAccessException {
        Long param = new Long(inputListId);

        return (FFileInputListDO) getSqlMapClientTemplate().queryForObject("MS-F-FILE-INPUT-LIST-FIND-BY-ID", param);

    }

	/**
	 *  Query DB table <tt>f_file_input_list</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from f_file_input_list where (file_id = ?)</tt>
	 *
	 *	@param fileId
	 *	@return List<FFileInputListDO>
	 *	@throws DataAccessException
	 */	 
    public List<FFileInputListDO> findByFileId(long fileId) throws DataAccessException {
        Long param = new Long(fileId);

        return getSqlMapClientTemplate().queryForList("MS-F-FILE-INPUT-LIST-FIND-BY-FILE-ID", param);

    }

	/**
	 *  Delete records from DB table <tt>f_file_input_list</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from f_file_input_list where (input_list_id = ?)</tt>
	 *
	 *	@param inputListId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteById(long inputListId) throws DataAccessException {
        Long param = new Long(inputListId);

        return getSqlMapClientTemplate().delete("MS-F-FILE-INPUT-LIST-DELETE-BY-ID", param);
    }

	/**
	 *  Query DB table <tt>f_file_input_list</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from f_file_input_list where (1 = 1)</tt>
	 *
	 *	@param FFileInputList
	 *	@param limitStart
	 *	@param pageSize
	 *	@return List<FFileInputListDO>
	 *	@throws DataAccessException
	 */	 
    public List<FFileInputListDO> findByCondition(FFileInputListDO FFileInputList, long limitStart, long pageSize) throws DataAccessException {
    	if (FFileInputList == null) {
    		throw new IllegalArgumentException("Can't select by a null data object.");
    	}

        Map param = new HashMap();

        param.put("FFileInputList", FFileInputList);
        param.put("limitStart", new Long(limitStart));
        param.put("pageSize", new Long(pageSize));

        return getSqlMapClientTemplate().queryForList("MS-F-FILE-INPUT-LIST-FIND-BY-CONDITION", param);

    }

	/**
	 *  Query DB table <tt>f_file_input_list</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select COUNT(*) from f_file_input_list where (1 = 1)</tt>
	 *
	 *	@param FFileInputList
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long findByConditionCount(FFileInputListDO FFileInputList) throws DataAccessException {
    	if (FFileInputList == null) {
    		throw new IllegalArgumentException("Can't select by a null data object.");
    	}


	    Long retObj = (Long) getSqlMapClientTemplate().queryForObject("MS-F-FILE-INPUT-LIST-FIND-BY-CONDITION-COUNT", FFileInputList);

		if (retObj == null) {
		    return 0;
		} else {
		    return retObj.longValue();
		}

    }

}