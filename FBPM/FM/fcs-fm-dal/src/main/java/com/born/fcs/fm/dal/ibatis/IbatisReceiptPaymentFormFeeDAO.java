/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.fm.dal.ibatis;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import com.born.fcs.fm.dal.daointerface.ReceiptPaymentFormFeeDAO;


// auto generated imports
import com.born.fcs.fm.dal.dataobject.ReceiptPaymentFormFeeDO;
import org.springframework.dao.DataAccessException;
import java.util.List;

/**
 * An ibatis based implementation of dao interface <tt>com.born.fcs.fm.dal.daointerface.ReceiptPaymentFormFeeDAO</tt>.
 *
 * This file is generated by <tt>specialmer-dalgen</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>paygw</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/receipt_payment_form_fee.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>specialmer-dalgen</tt> 
 * to generate this file.
 *
 * @author peigen
 */ 
@SuppressWarnings("unchecked")

public class IbatisReceiptPaymentFormFeeDAO extends SqlMapClientDaoSupport implements ReceiptPaymentFormFeeDAO {
	/**
	 *  Insert one <tt>ReceiptPaymentFormFeeDO</tt> object to DB table <tt>receipt_payment_form_fee</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into receipt_payment_form_fee(id,source_id,fee_type,amount,account,occur_time,deposit_account,deposit_rate,deposit_time,deposit_period,period_unit,attach,remark,raw_add_time) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)</tt>
	 *
	 *	@param receiptPaymentFormFee
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long insert(ReceiptPaymentFormFeeDO receiptPaymentFormFee) throws DataAccessException {
    	if (receiptPaymentFormFee == null) {
    		throw new IllegalArgumentException("Can't insert a null data object into db.");
    	}
    	
        getSqlMapClientTemplate().insert("MS-RECEIPT-PAYMENT-FORM-FEE-INSERT", receiptPaymentFormFee);

        return receiptPaymentFormFee.getId();
    }

	/**
	 *  Update DB table <tt>receipt_payment_form_fee</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update receipt_payment_form_fee set source_id=?, fee_type=?, amount=?, account=?, occur_time=?, deposit_account=?, deposit_rate=?, deposit_time=?, deposit_period=?, period_unit=?, attach=?, remark=? where (id = ?)</tt>
	 *
	 *	@param receiptPaymentFormFee
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int update(ReceiptPaymentFormFeeDO receiptPaymentFormFee) throws DataAccessException {
    	if (receiptPaymentFormFee == null) {
    		throw new IllegalArgumentException("Can't update by a null data object.");
    	}


        return getSqlMapClientTemplate().update("MS-RECEIPT-PAYMENT-FORM-FEE-UPDATE", receiptPaymentFormFee);
    }

	/**
	 *  Query DB table <tt>receipt_payment_form_fee</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from receipt_payment_form_fee t where (id = ?)</tt>
	 *
	 *	@param id
	 *	@return ReceiptPaymentFormFeeDO
	 *	@throws DataAccessException
	 */	 
    public ReceiptPaymentFormFeeDO findById(long id) throws DataAccessException {
        Long param = new Long(id);

        return (ReceiptPaymentFormFeeDO) getSqlMapClientTemplate().queryForObject("MS-RECEIPT-PAYMENT-FORM-FEE-FIND-BY-ID", param);

    }

	/**
	 *  Query DB table <tt>receipt_payment_form_fee</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from receipt_payment_form_fee t where (source_id = ?)</tt>
	 *
	 *	@param sourceId
	 *	@return List<ReceiptPaymentFormFeeDO>
	 *	@throws DataAccessException
	 */	 
    public List<ReceiptPaymentFormFeeDO> findBySourceId(long sourceId) throws DataAccessException {
        Long param = new Long(sourceId);

        return getSqlMapClientTemplate().queryForList("MS-RECEIPT-PAYMENT-FORM-FEE-FIND-BY-SOURCE-ID", param);

    }

	/**
	 *  Delete records from DB table <tt>receipt_payment_form_fee</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from receipt_payment_form_fee where (id = ?)</tt>
	 *
	 *	@param id
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteById(long id) throws DataAccessException {
        Long param = new Long(id);

        return getSqlMapClientTemplate().delete("MS-RECEIPT-PAYMENT-FORM-FEE-DELETE-BY-ID", param);
    }

	/**
	 *  Delete records from DB table <tt>receipt_payment_form_fee</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from receipt_payment_form_fee where (source_id = ?)</tt>
	 *
	 *	@param sourceId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteBySourceId(long sourceId) throws DataAccessException {
        Long param = new Long(sourceId);

        return getSqlMapClientTemplate().delete("MS-RECEIPT-PAYMENT-FORM-FEE-DELETE-BY-SOURCE-ID", param);
    }

}