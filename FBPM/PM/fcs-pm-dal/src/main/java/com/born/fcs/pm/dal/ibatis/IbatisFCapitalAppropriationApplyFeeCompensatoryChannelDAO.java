/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.dal.ibatis;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import com.born.fcs.pm.dal.daointerface.FCapitalAppropriationApplyFeeCompensatoryChannelDAO;


// auto generated imports
import com.born.fcs.pm.dal.dataobject.FCapitalAppropriationApplyFeeCompensatoryChannelDO;
import org.springframework.dao.DataAccessException;
import java.util.List;
import java.util.Map;
	import java.util.HashMap;
	
/**
 * An ibatis based implementation of dao interface <tt>com.born.fcs.pm.dal.daointerface.FCapitalAppropriationApplyFeeCompensatoryChannelDAO</tt>.
 *
 * This file is generated by <tt>specialmer-dalgen</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>paygw</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/f_capital_appropriation_apply_fee_compensatory_channel.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>specialmer-dalgen</tt> 
 * to generate this file.
 *
 * @author peigen
 */ 
@SuppressWarnings({ "unchecked", "rawtypes" })

public class IbatisFCapitalAppropriationApplyFeeCompensatoryChannelDAO extends SqlMapClientDaoSupport implements FCapitalAppropriationApplyFeeCompensatoryChannelDAO {
	/**
	 *  Insert one <tt>FCapitalAppropriationApplyFeeCompensatoryChannelDO</tt> object to DB table <tt>f_capital_appropriation_apply_fee_compensatory_channel</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into f_capital_appropriation_apply_fee_compensatory_channel(form_id,fee_id,capital_channel_id,capital_channel_code,capital_channel_type,capital_channel_name,capital_sub_channel_id,capital_sub_channel_code,capital_sub_channel_type,capital_sub_channel_name,actual_deposit_amount,liquidity_loan_amount,fixed_assets_financing_amount,acceptance_bill_amount,credit_letter_amount,raw_add_time) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)</tt>
	 *
	 *	@param FCapitalAppropriationApplyFeeCompensatoryChannel
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long insert(FCapitalAppropriationApplyFeeCompensatoryChannelDO FCapitalAppropriationApplyFeeCompensatoryChannel) throws DataAccessException {
    	if (FCapitalAppropriationApplyFeeCompensatoryChannel == null) {
    		throw new IllegalArgumentException("Can't insert a null data object into db.");
    	}
    	
        getSqlMapClientTemplate().insert("MS-F-CAPITAL-APPROPRIATION-APPLY-FEE-COMPENSATORY-CHANNEL-INSERT", FCapitalAppropriationApplyFeeCompensatoryChannel);

        return FCapitalAppropriationApplyFeeCompensatoryChannel.getId();
    }

	/**
	 *  Update DB table <tt>f_capital_appropriation_apply_fee_compensatory_channel</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update f_capital_appropriation_apply_fee_compensatory_channel set form_id=?, fee_id=?, capital_channel_id=?, capital_channel_code=?, capital_channel_type=?, capital_channel_name=?, capital_sub_channel_id=?, capital_sub_channel_code=?, capital_sub_channel_type=?, capital_sub_channel_name=?, actual_deposit_amount=?, liquidity_loan_amount=?, fixed_assets_financing_amount=?, acceptance_bill_amount=?, credit_letter_amount=? where (id = ?)</tt>
	 *
	 *	@param FCapitalAppropriationApplyFeeCompensatoryChannel
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int update(FCapitalAppropriationApplyFeeCompensatoryChannelDO FCapitalAppropriationApplyFeeCompensatoryChannel) throws DataAccessException {
    	if (FCapitalAppropriationApplyFeeCompensatoryChannel == null) {
    		throw new IllegalArgumentException("Can't update by a null data object.");
    	}


        return getSqlMapClientTemplate().update("MS-F-CAPITAL-APPROPRIATION-APPLY-FEE-COMPENSATORY-CHANNEL-UPDATE", FCapitalAppropriationApplyFeeCompensatoryChannel);
    }

	/**
	 *  Query DB table <tt>f_capital_appropriation_apply_fee_compensatory_channel</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from f_capital_appropriation_apply_fee_compensatory_channel where (id = ?)</tt>
	 *
	 *	@param id
	 *	@return FCapitalAppropriationApplyFeeCompensatoryChannelDO
	 *	@throws DataAccessException
	 */	 
    public FCapitalAppropriationApplyFeeCompensatoryChannelDO findById(long id) throws DataAccessException {
        Long param = new Long(id);

        return (FCapitalAppropriationApplyFeeCompensatoryChannelDO) getSqlMapClientTemplate().queryForObject("MS-F-CAPITAL-APPROPRIATION-APPLY-FEE-COMPENSATORY-CHANNEL-FIND-BY-ID", param);

    }

	/**
	 *  Query DB table <tt>f_capital_appropriation_apply_fee_compensatory_channel</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from f_capital_appropriation_apply_fee_compensatory_channel where (form_id = ?)</tt>
	 *
	 *	@param formId
	 *	@return List<FCapitalAppropriationApplyFeeCompensatoryChannelDO>
	 *	@throws DataAccessException
	 */	 
    public List<FCapitalAppropriationApplyFeeCompensatoryChannelDO> findByFormId(long formId) throws DataAccessException {
        Long param = new Long(formId);

        return getSqlMapClientTemplate().queryForList("MS-F-CAPITAL-APPROPRIATION-APPLY-FEE-COMPENSATORY-CHANNEL-FIND-BY-FORM-ID", param);

    }

	/**
	 *  Query DB table <tt>f_capital_appropriation_apply_fee_compensatory_channel</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from f_capital_appropriation_apply_fee_compensatory_channel where (fee_id = ?)</tt>
	 *
	 *	@param feeId
	 *	@return List<FCapitalAppropriationApplyFeeCompensatoryChannelDO>
	 *	@throws DataAccessException
	 */	 
    public List<FCapitalAppropriationApplyFeeCompensatoryChannelDO> findByFeeId(long feeId) throws DataAccessException {
        Long param = new Long(feeId);

        return getSqlMapClientTemplate().queryForList("MS-F-CAPITAL-APPROPRIATION-APPLY-FEE-COMPENSATORY-CHANNEL-FIND-BY-FEE-ID", param);

    }

	/**
	 *  Delete records from DB table <tt>f_capital_appropriation_apply_fee_compensatory_channel</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from f_capital_appropriation_apply_fee_compensatory_channel where (id = ?)</tt>
	 *
	 *	@param id
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteById(long id) throws DataAccessException {
        Long param = new Long(id);

        return getSqlMapClientTemplate().delete("MS-F-CAPITAL-APPROPRIATION-APPLY-FEE-COMPENSATORY-CHANNEL-DELETE-BY-ID", param);
    }

	/**
	 *  Delete records from DB table <tt>f_capital_appropriation_apply_fee_compensatory_channel</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from f_capital_appropriation_apply_fee_compensatory_channel where (fee_id = ?)</tt>
	 *
	 *	@param feeId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteByFeeId(long feeId) throws DataAccessException {
        Long param = new Long(feeId);

        return getSqlMapClientTemplate().delete("MS-F-CAPITAL-APPROPRIATION-APPLY-FEE-COMPENSATORY-CHANNEL-DELETE-BY-FEE-ID", param);
    }

	/**
	 *  Delete records from DB table <tt>f_capital_appropriation_apply_fee_compensatory_channel</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from f_capital_appropriation_apply_fee_compensatory_channel where (form_id = ?)</tt>
	 *
	 *	@param formId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteByFormId(long formId) throws DataAccessException {
        Long param = new Long(formId);

        return getSqlMapClientTemplate().delete("MS-F-CAPITAL-APPROPRIATION-APPLY-FEE-COMPENSATORY-CHANNEL-DELETE-BY-FORM-ID", param);
    }

	/**
	 *  Query DB table <tt>f_capital_appropriation_apply_fee_compensatory_channel</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from f_capital_appropriation_apply_fee_compensatory_channel where (1 = 1)</tt>
	 *
	 *	@param FCapitalAppropriationApplyFeeCompensatoryChannel
	 *	@param limitStart
	 *	@param pageSize
	 *	@return List<FCapitalAppropriationApplyFeeCompensatoryChannelDO>
	 *	@throws DataAccessException
	 */	 
    public List<FCapitalAppropriationApplyFeeCompensatoryChannelDO> findByCondition(FCapitalAppropriationApplyFeeCompensatoryChannelDO FCapitalAppropriationApplyFeeCompensatoryChannel, long limitStart, long pageSize) throws DataAccessException {
    	if (FCapitalAppropriationApplyFeeCompensatoryChannel == null) {
    		throw new IllegalArgumentException("Can't select by a null data object.");
    	}

        Map param = new HashMap();

        param.put("FCapitalAppropriationApplyFeeCompensatoryChannel", FCapitalAppropriationApplyFeeCompensatoryChannel);
        param.put("limitStart", new Long(limitStart));
        param.put("pageSize", new Long(pageSize));

        return getSqlMapClientTemplate().queryForList("MS-F-CAPITAL-APPROPRIATION-APPLY-FEE-COMPENSATORY-CHANNEL-FIND-BY-CONDITION", param);

    }

	/**
	 *  Query DB table <tt>f_capital_appropriation_apply_fee_compensatory_channel</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select COUNT(*) from f_capital_appropriation_apply_fee_compensatory_channel where (1 = 1)</tt>
	 *
	 *	@param FCapitalAppropriationApplyFeeCompensatoryChannel
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long findByConditionCount(FCapitalAppropriationApplyFeeCompensatoryChannelDO FCapitalAppropriationApplyFeeCompensatoryChannel) throws DataAccessException {
    	if (FCapitalAppropriationApplyFeeCompensatoryChannel == null) {
    		throw new IllegalArgumentException("Can't select by a null data object.");
    	}


	    Long retObj = (Long) getSqlMapClientTemplate().queryForObject("MS-F-CAPITAL-APPROPRIATION-APPLY-FEE-COMPENSATORY-CHANNEL-FIND-BY-CONDITION-COUNT", FCapitalAppropriationApplyFeeCompensatoryChannel);

		if (retObj == null) {
		    return 0;
		} else {
		    return retObj.longValue();
		}

    }

}