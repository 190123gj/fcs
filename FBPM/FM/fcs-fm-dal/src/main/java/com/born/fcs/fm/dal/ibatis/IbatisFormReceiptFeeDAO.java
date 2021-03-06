/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.fm.dal.ibatis;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import com.born.fcs.fm.dal.daointerface.FormReceiptFeeDAO;


// auto generated imports
import com.born.fcs.fm.dal.dataobject.FormReceiptFeeDO;
import org.springframework.dao.DataAccessException;
import java.util.List;

/**
 * An ibatis based implementation of dao interface <tt>com.born.fcs.fm.dal.daointerface.FormReceiptFeeDAO</tt>.
 *
 * This file is generated by <tt>specialmer-dalgen</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>paygw</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/form_receipt_fee.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>specialmer-dalgen</tt> 
 * to generate this file.
 *
 * @author peigen
 */ 
@SuppressWarnings("unchecked")

public class IbatisFormReceiptFeeDAO extends SqlMapClientDaoSupport implements FormReceiptFeeDAO {
	/**
	 *  Insert one <tt>FormReceiptFeeDO</tt> object to DB table <tt>form_receipt_fee</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into form_receipt_fee(id,form_id,fee_type,amount,account,at_code,at_name,receipt_date,remark,raw_add_time) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)</tt>
	 *
	 *	@param formReceiptFee
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long insert(FormReceiptFeeDO formReceiptFee) throws DataAccessException {
    	if (formReceiptFee == null) {
    		throw new IllegalArgumentException("Can't insert a null data object into db.");
    	}
    	
        getSqlMapClientTemplate().insert("MS-FORM-RECEIPT-FEE-INSERT", formReceiptFee);

        return formReceiptFee.getId();
    }

	/**
	 *  Update DB table <tt>form_receipt_fee</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update form_receipt_fee set form_id=?, fee_type=?, amount=?, account=?, at_code=?, at_name=?, receipt_date=?, remark=? where (id = ?)</tt>
	 *
	 *	@param formReceiptFee
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int update(FormReceiptFeeDO formReceiptFee) throws DataAccessException {
    	if (formReceiptFee == null) {
    		throw new IllegalArgumentException("Can't update by a null data object.");
    	}


        return getSqlMapClientTemplate().update("MS-FORM-RECEIPT-FEE-UPDATE", formReceiptFee);
    }

	/**
	 *  Query DB table <tt>form_receipt_fee</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from form_receipt_fee t where (id = ?)</tt>
	 *
	 *	@param id
	 *	@return FormReceiptFeeDO
	 *	@throws DataAccessException
	 */	 
    public FormReceiptFeeDO findById(long id) throws DataAccessException {
        Long param = new Long(id);

        return (FormReceiptFeeDO) getSqlMapClientTemplate().queryForObject("MS-FORM-RECEIPT-FEE-FIND-BY-ID", param);

    }

	/**
	 *  Query DB table <tt>form_receipt_fee</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from form_receipt_fee t where (form_id = ?)</tt>
	 *
	 *	@param formId
	 *	@return List<FormReceiptFeeDO>
	 *	@throws DataAccessException
	 */	 
    public List<FormReceiptFeeDO> findByFormId(long formId) throws DataAccessException {
        Long param = new Long(formId);

        return getSqlMapClientTemplate().queryForList("MS-FORM-RECEIPT-FEE-FIND-BY-FORM-ID", param);

    }

	/**
	 *  Query DB table <tt>form_receipt_fee</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from form_receipt_fee t where (account = ?)</tt>
	 *
	 *	@param account
	 *	@return List<FormReceiptFeeDO>
	 *	@throws DataAccessException
	 */	 
    public List<FormReceiptFeeDO> findByAccount(String account) throws DataAccessException {

        return getSqlMapClientTemplate().queryForList("MS-FORM-RECEIPT-FEE-FIND-BY-ACCOUNT", account);

    }

	/**
	 *  Delete records from DB table <tt>form_receipt_fee</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from form_receipt_fee where (form_id = ?)</tt>
	 *
	 *	@param formId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteByFormId(long formId) throws DataAccessException {
        Long param = new Long(formId);

        return getSqlMapClientTemplate().delete("MS-FORM-RECEIPT-FEE-DELETE-BY-FORM-ID", param);
    }

	/**
	 *  Delete records from DB table <tt>form_receipt_fee</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from form_receipt_fee where (id = ?)</tt>
	 *
	 *	@param id
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteById(long id) throws DataAccessException {
        Long param = new Long(id);

        return getSqlMapClientTemplate().delete("MS-FORM-RECEIPT-FEE-DELETE-BY-ID", param);
    }

}