/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.dal.ibatis;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import com.born.fcs.pm.dal.daointerface.FRiskWarningCreditDAO;


// auto generated imports
import com.born.fcs.pm.dal.dataobject.FRiskWarningCreditDO;
import org.springframework.dao.DataAccessException;
import java.util.List;

/**
 * An ibatis based implementation of dao interface <tt>com.born.fcs.pm.dal.daointerface.FRiskWarningCreditDAO</tt>.
 *
 * This file is generated by <tt>specialmer-dalgen</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>paygw</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/f_risk_warning_credit.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>specialmer-dalgen</tt> 
 * to generate this file.
 *
 * @author peigen
 */ 
@SuppressWarnings("unchecked")

public class IbatisFRiskWarningCreditDAO extends SqlMapClientDaoSupport implements FRiskWarningCreditDAO {
	/**
	 *  Insert one <tt>FRiskWarningCreditDO</tt> object to DB table <tt>f_risk_warning_credit</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into f_risk_warning_credit(id,project_code,dept_name,warning_id,issue_date,expire_date,loan_amount,debit_interest,has_repay_plan,sort_order,josn_data,raw_add_time) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)</tt>
	 *
	 *	@param FRiskWarningCredit
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long insert(FRiskWarningCreditDO FRiskWarningCredit) throws DataAccessException {
    	if (FRiskWarningCredit == null) {
    		throw new IllegalArgumentException("Can't insert a null data object into db.");
    	}
    	
        getSqlMapClientTemplate().insert("MS-F-RISK-WARNING-CREDIT-INSERT", FRiskWarningCredit);

        return FRiskWarningCredit.getId();
    }

	/**
	 *  Update DB table <tt>f_risk_warning_credit</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update f_risk_warning_credit set project_code=?, dept_name=?, warning_id=?, issue_date=?, expire_date=?, loan_amount=?, debit_interest=?, has_repay_plan=?, sort_order=?, josn_data=? where (id = ?)</tt>
	 *
	 *	@param FRiskWarningCredit
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int update(FRiskWarningCreditDO FRiskWarningCredit) throws DataAccessException {
    	if (FRiskWarningCredit == null) {
    		throw new IllegalArgumentException("Can't update by a null data object.");
    	}


        return getSqlMapClientTemplate().update("MS-F-RISK-WARNING-CREDIT-UPDATE", FRiskWarningCredit);
    }

	/**
	 *  Query DB table <tt>f_risk_warning_credit</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from f_risk_warning_credit where (id = ?)</tt>
	 *
	 *	@param id
	 *	@return FRiskWarningCreditDO
	 *	@throws DataAccessException
	 */	 
    public FRiskWarningCreditDO findById(long id) throws DataAccessException {
        Long param = new Long(id);

        return (FRiskWarningCreditDO) getSqlMapClientTemplate().queryForObject("MS-F-RISK-WARNING-CREDIT-FIND-BY-ID", param);

    }

	/**
	 *  Query DB table <tt>f_risk_warning_credit</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from f_risk_warning_credit where (warning_id = ?)</tt>
	 *
	 *	@param warningId
	 *	@return List<FRiskWarningCreditDO>
	 *	@throws DataAccessException
	 */	 
    public List<FRiskWarningCreditDO> findByWarningId(long warningId) throws DataAccessException {
        Long param = new Long(warningId);

        return getSqlMapClientTemplate().queryForList("MS-F-RISK-WARNING-CREDIT-FIND-BY-WARNING-ID", param);

    }

	/**
	 *  Delete records from DB table <tt>f_risk_warning_credit</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from f_risk_warning_credit where (id = ?)</tt>
	 *
	 *	@param id
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteById(long id) throws DataAccessException {
        Long param = new Long(id);

        return getSqlMapClientTemplate().delete("MS-F-RISK-WARNING-CREDIT-DELETE-BY-ID", param);
    }

	/**
	 *  Delete records from DB table <tt>f_risk_warning_credit</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from f_risk_warning_credit where (warning_id = ?)</tt>
	 *
	 *	@param warningId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteByWarningId(long warningId) throws DataAccessException {
        Long param = new Long(warningId);

        return getSqlMapClientTemplate().delete("MS-F-RISK-WARNING-CREDIT-DELETE-BY-WARNING-ID", param);
    }

}