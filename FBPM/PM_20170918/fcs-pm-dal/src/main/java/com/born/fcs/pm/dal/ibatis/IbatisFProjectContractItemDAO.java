/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.dal.ibatis;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import com.born.fcs.pm.dal.daointerface.FProjectContractItemDAO;


// auto generated imports
import com.born.fcs.pm.dal.dataobject.FProjectContractItemDO;
import org.springframework.dao.DataAccessException;
import java.util.List;
import java.util.Map;
	import java.util.HashMap;
	
/**
 * An ibatis based implementation of dao interface <tt>com.born.fcs.pm.dal.daointerface.FProjectContractItemDAO</tt>.
 *
 * This file is generated by <tt>specialmer-dalgen</tt>, a DAL (Data Access Layer)
 * code generation utility specially developed for <tt>paygw</tt> project.
 * 
 * PLEASE DO NOT MODIFY THIS FILE MANUALLY, or else your modification may
 * be OVERWRITTEN by someone else. To modify the file, you should go to 
 * directory <tt>(project-home)/biz/dal/src/conf/dalgen</tt>, and 
 * find the corresponding configuration file (<tt>tables/f_project_contract_item.xml</tt>). 
 * Modify the configuration file according to your needs, then run <tt>specialmer-dalgen</tt> 
 * to generate this file.
 *
 * @author peigen
 */ 
@SuppressWarnings({ "unchecked", "rawtypes" })

public class IbatisFProjectContractItemDAO extends SqlMapClientDaoSupport implements FProjectContractItemDAO {
	/**
	 *  Insert one <tt>FProjectContractItemDO</tt> object to DB table <tt>f_project_contract_item</tt>, return primary key
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>insert into f_project_contract_item(id,contract_id,contract_code,contract_code2,contract_name,contract_status,pledge_id,template_id,contract_type,is_main,stamp_phase,cnt,file_url,credit_measure,content,content_message,remark,audit_info,signed_time,sign_person_a,sign_person_b,sign_person_a_id,sign_person_b_id,contract_scan_url,invalid_reason,approved_amount,approved_time,approved_url,basis_of_decision,basis_of_decision_type,letter_type,credit_condition_type,pledge_type,court_ruling_url,court_ruling_time,court_ruling_add_time,contract_amount,form_change,risk_council_summary,project_approval,project_set_up,refer_attachment,return_remark,return_add_time,is_need_stamp,sort_order,raw_add_time) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)</tt>
	 *
	 *	@param FProjectContractItem
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long insert(FProjectContractItemDO FProjectContractItem) throws DataAccessException {
    	if (FProjectContractItem == null) {
    		throw new IllegalArgumentException("Can't insert a null data object into db.");
    	}
    	
        getSqlMapClientTemplate().insert("MS-F-PROJECT-CONTRACT-ITEM-INSERT", FProjectContractItem);

        return FProjectContractItem.getId();
    }

	/**
	 *  Update DB table <tt>f_project_contract_item</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>update f_project_contract_item set contract_id=?, contract_code=?, contract_code2=?, contract_name=?, contract_status=?, pledge_id=?, template_id=?, contract_type=?, is_main=?, stamp_phase=?, cnt=?, file_url=?, credit_measure=?, content=?, content_message=?, remark=?, audit_info=?, signed_time=?, sign_person_a=?, sign_person_b=?, sign_person_a_id=?, sign_person_b_id=?, contract_scan_url=?, invalid_reason=?, approved_amount=?, approved_time=?, approved_url=?, basis_of_decision=?, basis_of_decision_type=?, letter_type=?, credit_condition_type=?, pledge_type=?, court_ruling_url=?, court_ruling_time=?, contract_amount=?, form_change=?, risk_council_summary=?, project_approval=?, project_set_up=?, refer_attachment=?, return_remark=?, sort_order=?, return_add_time=?, is_need_stamp=?, court_ruling_add_time=? where (id = ?)</tt>
	 *
	 *	@param FProjectContractItem
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int update(FProjectContractItemDO FProjectContractItem) throws DataAccessException {
    	if (FProjectContractItem == null) {
    		throw new IllegalArgumentException("Can't update by a null data object.");
    	}


        return getSqlMapClientTemplate().update("MS-F-PROJECT-CONTRACT-ITEM-UPDATE", FProjectContractItem);
    }

	/**
	 *  Query DB table <tt>f_project_contract_item</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select id, contract_id, contract_code, contract_code2, contract_name, contract_status, pledge_id, template_id, contract_type, is_main, stamp_phase, cnt, file_url, credit_measure, content, content_message, remark, audit_info, signed_time, sign_person_a, sign_person_b, sign_person_a_id, sign_person_b_id, contract_scan_url, invalid_reason, approved_amount, approved_time, approved_url, basis_of_decision, basis_of_decision_type, letter_type, credit_condition_type, pledge_type, court_ruling_url, court_ruling_time, contract_amount, form_change, risk_council_summary, project_approval, project_set_up, refer_attachment, return_remark, sort_order, raw_add_time, raw_update_time, return_add_time, court_ruling_add_time, is_need_stamp from f_project_contract_item where (id = ?)</tt>
	 *
	 *	@param id
	 *	@return FProjectContractItemDO
	 *	@throws DataAccessException
	 */	 
    public FProjectContractItemDO findById(long id) throws DataAccessException {
        Long param = new Long(id);

        return (FProjectContractItemDO) getSqlMapClientTemplate().queryForObject("MS-F-PROJECT-CONTRACT-ITEM-FIND-BY-ID", param);

    }

	/**
	 *  Query DB table <tt>f_project_contract_item</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select id, contract_id, contract_code, contract_code2, contract_name, contract_status, pledge_id, template_id, contract_type, is_main, stamp_phase, cnt, file_url, credit_measure, content, content_message, remark, audit_info, signed_time, sign_person_a, sign_person_b, sign_person_a_id, sign_person_b_id, contract_scan_url, invalid_reason, approved_amount, approved_time, approved_url, basis_of_decision, basis_of_decision_type, letter_type, credit_condition_type, pledge_type, court_ruling_url, court_ruling_time, contract_amount, form_change, risk_council_summary, project_approval, project_set_up, refer_attachment, return_remark, sort_order, raw_add_time, raw_update_time, return_add_time, court_ruling_add_time, is_need_stamp from f_project_contract_item where (contract_id = ?)</tt>
	 *
	 *	@param contractId
	 *	@return List<FProjectContractItemDO>
	 *	@throws DataAccessException
	 */	 
    public List<FProjectContractItemDO> findByFormContractId(long contractId) throws DataAccessException {
        Long param = new Long(contractId);

        return getSqlMapClientTemplate().queryForList("MS-F-PROJECT-CONTRACT-ITEM-FIND-BY-FORM-CONTRACT-ID", param);

    }

	/**
	 *  Query DB table <tt>f_project_contract_item</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select id, contract_id, contract_code, contract_code2, contract_name, contract_status, pledge_id, template_id, contract_type, is_main, stamp_phase, cnt, file_url, credit_measure, content, content_message, remark, audit_info, signed_time, sign_person_a, sign_person_b, sign_person_a_id, sign_person_b_id, contract_scan_url, invalid_reason, approved_amount, approved_time, approved_url, basis_of_decision, basis_of_decision_type, letter_type, credit_condition_type, pledge_type, court_ruling_url, court_ruling_time, contract_amount, form_change, risk_council_summary, project_approval, project_set_up, refer_attachment, return_remark, sort_order, raw_add_time, raw_update_time, return_add_time, court_ruling_add_time, is_need_stamp from f_project_contract_item where (contract_code = ?)</tt>
	 *
	 *	@param contractCode
	 *	@return FProjectContractItemDO
	 *	@throws DataAccessException
	 */	 
    public FProjectContractItemDO findByContractCode(String contractCode) throws DataAccessException {

        return (FProjectContractItemDO) getSqlMapClientTemplate().queryForObject("MS-F-PROJECT-CONTRACT-ITEM-FIND-BY-CONTRACT-CODE", contractCode);

    }

	/**
	 *  Query DB table <tt>f_project_contract_item</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select id, contract_id, contract_code, contract_code2, contract_name, contract_status, pledge_id, template_id, contract_type, is_main, stamp_phase, cnt, file_url, credit_measure, content, content_message, remark, audit_info, signed_time, sign_person_a, sign_person_b, sign_person_a_id, sign_person_b_id, contract_scan_url, invalid_reason, approved_amount, approved_time, approved_url, basis_of_decision, basis_of_decision_type, letter_type, credit_condition_type, pledge_type, court_ruling_url, court_ruling_time, contract_amount, form_change, risk_council_summary, project_approval, project_set_up, refer_attachment, return_remark, sort_order, raw_add_time, raw_update_time, return_add_time, court_ruling_add_time, is_need_stamp from f_project_contract_item where ((contract_code2 = ?) AND (contract_status NOT IN ('DELETED', 'END')))</tt>
	 *
	 *	@param contractCode2
	 *	@return FProjectContractItemDO
	 *	@throws DataAccessException
	 */	 
    public FProjectContractItemDO findByContractCode2(String contractCode2) throws DataAccessException {

        return (FProjectContractItemDO) getSqlMapClientTemplate().queryForObject("MS-F-PROJECT-CONTRACT-ITEM-FIND-BY-CONTRACT-CODE-2", contractCode2);

    }

	/**
	 *  Delete records from DB table <tt>f_project_contract_item</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from f_project_contract_item where (id = ?)</tt>
	 *
	 *	@param id
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteById(long id) throws DataAccessException {
        Long param = new Long(id);

        return getSqlMapClientTemplate().delete("MS-F-PROJECT-CONTRACT-ITEM-DELETE-BY-ID", param);
    }

	/**
	 *  Delete records from DB table <tt>f_project_contract_item</tt>.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>delete from f_project_contract_item where (contract_id = ?)</tt>
	 *
	 *	@param contractId
	 *	@return int
	 *	@throws DataAccessException
	 */	 
    public int deleteByContractId(long contractId) throws DataAccessException {
        Long param = new Long(contractId);

        return getSqlMapClientTemplate().delete("MS-F-PROJECT-CONTRACT-ITEM-DELETE-BY-CONTRACT-ID", param);
    }

	/**
	 *  Query DB table <tt>f_project_contract_item</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select COUNT(*) from f_project_contract_item</tt>
	 *
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long findAllCount() throws DataAccessException {

	    Long retObj = (Long) getSqlMapClientTemplate().queryForObject("MS-F-PROJECT-CONTRACT-ITEM-FIND-ALL-COUNT", null);

		if (retObj == null) {
		    return 0;
		} else {
		    return retObj.longValue();
		}

    }

	/**
	 *  Query DB table <tt>f_project_contract_item</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from f_project_contract_item where (id = ?)</tt>
	 *
	 *	@param FProjectContractItem
	 *	@param limitStart
	 *	@param pageSize
	 *	@return List<FProjectContractItemDO>
	 *	@throws DataAccessException
	 */	 
    public List<FProjectContractItemDO> findByCondition(FProjectContractItemDO FProjectContractItem, long limitStart, long pageSize) throws DataAccessException {
    	if (FProjectContractItem == null) {
    		throw new IllegalArgumentException("Can't select by a null data object.");
    	}

        Map param = new HashMap();

        param.put("FProjectContractItem", FProjectContractItem);
        param.put("limitStart", new Long(limitStart));
        param.put("pageSize", new Long(pageSize));

        return getSqlMapClientTemplate().queryForList("MS-F-PROJECT-CONTRACT-ITEM-FIND-BY-CONDITION", param);

    }

	/**
	 *  Query DB table <tt>f_project_contract_item</tt> for records.
	 *
	 *  <p>
	 *  The sql statement for this operation is <br>
	 *  <tt>select * from f_project_contract_item where (id = ?)</tt>
	 *
	 *	@param FProjectContractItem
	 *	@return long
	 *	@throws DataAccessException
	 */	 
    public long findByConditionCount(FProjectContractItemDO FProjectContractItem) throws DataAccessException {
    	if (FProjectContractItem == null) {
    		throw new IllegalArgumentException("Can't select by a null data object.");
    	}


	    Long retObj = (Long) getSqlMapClientTemplate().queryForObject("MS-F-PROJECT-CONTRACT-ITEM-FIND-BY-CONDITION-COUNT", FProjectContractItem);

		if (retObj == null) {
		    return 0;
		} else {
		    return retObj.longValue();
		}

    }

}