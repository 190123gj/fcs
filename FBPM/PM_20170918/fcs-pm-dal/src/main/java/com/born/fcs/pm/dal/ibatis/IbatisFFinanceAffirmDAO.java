/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.dal.ibatis;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import com.born.fcs.pm.dal.daointerface.FFinanceAffirmDAO;


// auto generated imports
import com.born.fcs.pm.dal.dataobject.FFinanceAffirmDO;
import org.springframework.dao.DataAccessException;
import java.util.List;
import java.util.Map;
	import java.util.HashMap;
	
/**
 * An ibatis based implementation of dao interface <tt>com.born.fcs.pm.dal.daointerface.FFinanceAffirmDAO</tt>.
 *
 * This file is generated by <tt>specialmer-dalgen</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>paygw</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/f_finance_affirm.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>specialmer-dalgen</tt> 
 * to generate this file.
 *
 * @author peigen
 */ 
@SuppressWarnings({ "unchecked", "rawtypes" })

public class IbatisFFinanceAffirmDAO extends SqlMapClientDaoSupport implements FFinanceAffirmDAO {
	/**
	 *  Insert one <tt>FFinanceAffirmDO</tt> object to DB table <tt>f_finance_affirm</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into f_finance_affirm(form_id,project_code,affirm_form_type,amount,remark,attach,raw_add_time) values (?, ?, ?, ?, ?, ?, ?)</tt>
	 *
	 *	@param FFinanceAffirm
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long insert(FFinanceAffirmDO FFinanceAffirm) throws DataAccessException {
    	if (FFinanceAffirm == null) {
    		throw new IllegalArgumentException("Can't insert a null data object into db.");
    	}
    	
        getSqlMapClientTemplate().insert("MS-F-FINANCE-AFFIRM-INSERT", FFinanceAffirm);

        return FFinanceAffirm.getAffirmId();
    }

	/**
	 *  Update DB table <tt>f_finance_affirm</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update f_finance_affirm set form_id=?, project_code=?, affirm_form_type=?, amount=?, remark=?, attach=? where (affirm_id = ?)</tt>
	 *
	 *	@param FFinanceAffirm
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int update(FFinanceAffirmDO FFinanceAffirm) throws DataAccessException {
    	if (FFinanceAffirm == null) {
    		throw new IllegalArgumentException("Can't update by a null data object.");
    	}


        return getSqlMapClientTemplate().update("MS-F-FINANCE-AFFIRM-UPDATE", FFinanceAffirm);
    }

	/**
	 *  Query DB table <tt>f_finance_affirm</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from f_finance_affirm where (affirm_id = ?)</tt>
	 *
	 *	@param affirmId
	 *	@return FFinanceAffirmDO
	 *	@throws DataAccessException
	 */	 
    public FFinanceAffirmDO findById(long affirmId) throws DataAccessException {
        Long param = new Long(affirmId);

        return (FFinanceAffirmDO) getSqlMapClientTemplate().queryForObject("MS-F-FINANCE-AFFIRM-FIND-BY-ID", param);

    }

	/**
	 *  Query DB table <tt>f_finance_affirm</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from f_finance_affirm where (form_id = ?)</tt>
	 *
	 *	@param formId
	 *	@return FFinanceAffirmDO
	 *	@throws DataAccessException
	 */	 
    public FFinanceAffirmDO findByFormId(long formId) throws DataAccessException {
        Long param = new Long(formId);

        return (FFinanceAffirmDO) getSqlMapClientTemplate().queryForObject("MS-F-FINANCE-AFFIRM-FIND-BY-FORM-ID", param);

    }

	/**
	 *  Delete records from DB table <tt>f_finance_affirm</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from f_finance_affirm where (affirm_id = ?)</tt>
	 *
	 *	@param affirmId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteById(long affirmId) throws DataAccessException {
        Long param = new Long(affirmId);

        return getSqlMapClientTemplate().delete("MS-F-FINANCE-AFFIRM-DELETE-BY-ID", param);
    }

	/**
	 *  Query DB table <tt>f_finance_affirm</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from f_finance_affirm where (1 = 1)</tt>
	 *
	 *	@param FFinanceAffirm
	 *	@param limitStart
	 *	@param pageSize
	 *	@return List<FFinanceAffirmDO>
	 *	@throws DataAccessException
	 */	 
    public List<FFinanceAffirmDO> findByCondition(FFinanceAffirmDO FFinanceAffirm, long limitStart, long pageSize) throws DataAccessException {
    	if (FFinanceAffirm == null) {
    		throw new IllegalArgumentException("Can't select by a null data object.");
    	}

        Map param = new HashMap();

        param.put("FFinanceAffirm", FFinanceAffirm);
        param.put("limitStart", new Long(limitStart));
        param.put("pageSize", new Long(pageSize));

        return getSqlMapClientTemplate().queryForList("MS-F-FINANCE-AFFIRM-FIND-BY-CONDITION", param);

    }

	/**
	 *  Query DB table <tt>f_finance_affirm</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select COUNT(*) from f_finance_affirm where (1 = 1)</tt>
	 *
	 *	@param FFinanceAffirm
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long findByConditionCount(FFinanceAffirmDO FFinanceAffirm) throws DataAccessException {
    	if (FFinanceAffirm == null) {
    		throw new IllegalArgumentException("Can't select by a null data object.");
    	}


	    Long retObj = (Long) getSqlMapClientTemplate().queryForObject("MS-F-FINANCE-AFFIRM-FIND-BY-CONDITION-COUNT", FFinanceAffirm);

		if (retObj == null) {
		    return 0;
		} else {
		    return retObj.longValue();
		}

    }

}