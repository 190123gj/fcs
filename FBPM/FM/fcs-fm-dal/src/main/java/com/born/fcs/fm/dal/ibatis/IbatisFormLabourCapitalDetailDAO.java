/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.fm.dal.ibatis;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import com.born.fcs.fm.dal.daointerface.FormLabourCapitalDetailDAO;


// auto generated imports
import com.born.fcs.fm.dal.dataobject.FormLabourCapitalDetailDO;
import org.springframework.dao.DataAccessException;
import java.util.List;
import java.util.Map;
	import java.util.HashMap;
	
/**
 * An ibatis based implementation of dao interface <tt>com.born.fcs.fm.dal.daointerface.FormLabourCapitalDetailDAO</tt>.
 *
 * This file is generated by <tt>specialmer-dalgen</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>paygw</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/form_labour_capital_detail.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>specialmer-dalgen</tt> 
 * to generate this file.
 *
 * @author peigen
 */ 
@SuppressWarnings({ "unchecked", "rawtypes" })

public class IbatisFormLabourCapitalDetailDAO extends SqlMapClientDaoSupport implements FormLabourCapitalDetailDAO {
	/**
	 *  Insert one <tt>FormLabourCapitalDetailDO</tt> object to DB table <tt>form_labour_capital_detail</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into form_labour_capital_detail(detail_id,labour_capital_id,expense_type,amount,reverse,tax_amount,fp_amount,xj_amount,dept_id,dept_name,raw_add_time) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)</tt>
	 *
	 *	@param formLabourCapitalDetail
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long insert(FormLabourCapitalDetailDO formLabourCapitalDetail) throws DataAccessException {
    	if (formLabourCapitalDetail == null) {
    		throw new IllegalArgumentException("Can't insert a null data object into db.");
    	}
    	
        getSqlMapClientTemplate().insert("MS-FORM-LABOUR-CAPITAL-DETAIL-INSERT", formLabourCapitalDetail);

        return formLabourCapitalDetail.getDetailId();
    }

	/**
	 *  Update DB table <tt>form_labour_capital_detail</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update form_labour_capital_detail set labour_capital_id=?, expense_type=?, amount=?, reverse=?, tax_amount=?, fp_amount=?, xj_amount=?, dept_id=?, dept_name=? where (detail_id = ?)</tt>
	 *
	 *	@param formLabourCapitalDetail
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int update(FormLabourCapitalDetailDO formLabourCapitalDetail) throws DataAccessException {
    	if (formLabourCapitalDetail == null) {
    		throw new IllegalArgumentException("Can't update by a null data object.");
    	}


        return getSqlMapClientTemplate().update("MS-FORM-LABOUR-CAPITAL-DETAIL-UPDATE", formLabourCapitalDetail);
    }

	/**
	 *  Query DB table <tt>form_labour_capital_detail</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from form_labour_capital_detail where (detail_id = ?)</tt>
	 *
	 *	@param detailId
	 *	@return FormLabourCapitalDetailDO
	 *	@throws DataAccessException
	 */	 
    public FormLabourCapitalDetailDO findById(long detailId) throws DataAccessException {
        Long param = new Long(detailId);

        return (FormLabourCapitalDetailDO) getSqlMapClientTemplate().queryForObject("MS-FORM-LABOUR-CAPITAL-DETAIL-FIND-BY-ID", param);

    }

	/**
	 *  Delete records from DB table <tt>form_labour_capital_detail</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from form_labour_capital_detail where (detail_id = ?)</tt>
	 *
	 *	@param detailId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteById(long detailId) throws DataAccessException {
        Long param = new Long(detailId);

        return getSqlMapClientTemplate().delete("MS-FORM-LABOUR-CAPITAL-DETAIL-DELETE-BY-ID", param);
    }

	/**
	 *  Query DB table <tt>form_labour_capital_detail</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from form_labour_capital_detail where (labour_capital_id = ?)</tt>
	 *
	 *	@param labourCapitalId
	 *	@return List<FormLabourCapitalDetailDO>
	 *	@throws DataAccessException
	 */	 
    public List<FormLabourCapitalDetailDO> findByLabourCapitalId(long labourCapitalId) throws DataAccessException {
        Long param = new Long(labourCapitalId);

        return getSqlMapClientTemplate().queryForList("MS-FORM-LABOUR-CAPITAL-DETAIL-FIND-BY-LABOUR-CAPITAL-ID", param);

    }

	/**
	 *  Query DB table <tt>form_labour_capital_detail</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select COUNT(*) from form_labour_capital_detail where (expense_type = ?)</tt>
	 *
	 *	@param expenseType
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long findCountByExpenseType(String expenseType) throws DataAccessException {

	    Long retObj = (Long) getSqlMapClientTemplate().queryForObject("MS-FORM-LABOUR-CAPITAL-DETAIL-FIND-COUNT-BY-EXPENSE-TYPE", expenseType);

		if (retObj == null) {
		    return 0;
		} else {
		    return retObj.longValue();
		}

    }

	/**
	 *  Query DB table <tt>form_labour_capital_detail</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select d.* from form_labour_capital e, form_labour_capital_detail d, form f where ((d.labour_capital_id = e.labour_capital_id) AND (e.form_id = f.form_id) AND (f.status IN ('APPROVAL', 'SUBMIT', 'AUDITING')))</tt>
	 *
	 *	@param deptId
	 *	@param categoryId
	 *	@param applyTimeStart
	 *	@param applyTimeEnd
	 *	@return List<FormLabourCapitalDetailDO>
	 *	@throws DataAccessException
	 */	 
    public List<FormLabourCapitalDetailDO> findSublInfoByDeptCategory(long deptId, long categoryId, String applyTimeStart, String applyTimeEnd) throws DataAccessException {
        Map param = new HashMap();

        param.put("deptId", new Long(deptId));
        param.put("categoryId", new Long(categoryId));
        param.put("applyTimeStart", applyTimeStart);
        param.put("applyTimeEnd", applyTimeEnd);

        return getSqlMapClientTemplate().queryForList("MS-FORM-LABOUR-CAPITAL-DETAIL-FIND-SUBL-INFO-BY-DEPT-CATEGORY", param);

    }

}