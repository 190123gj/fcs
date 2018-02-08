package com.born.fcs.crm.dal.ibatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.born.fcs.crm.dal.daointerface.EvaluetingListForAuditDAO;
import com.born.fcs.crm.dal.dataobject.EvaluetingListForAuditDO;

public class IbatisEvaluetingListForAuditDAO extends SqlMapClientDaoSupport implements
																			EvaluetingListForAuditDAO {
	
	@Override
	public EvaluetingListForAuditDO findByFormId(long formId) throws DataAccessException {
		Long param = new Long(formId);
		
		return (EvaluetingListForAuditDO) getSqlMapClientTemplate().queryForObject(
			"evalueting_form_info_byId", param);
	}
	
	@Override
	public List<EvaluetingListForAuditDO> findWithCondition(EvaluetingListForAuditDO evaluetingList,
															long loginUserId,
															List<Long> deptIdList, long limitStart,
															long pageSize, String likeCustomerName,
															String likeBusiLicenseNo)
																						throws DataAccessException {
		if (evaluetingList == null) {
			throw new IllegalArgumentException("Can't select by a null data object.");
		}
		
		Map param = new HashMap();
		
		param.put("evaluetingList", evaluetingList);
		param.put("limitStart", new Long(limitStart));
		param.put("pageSize", new Long(pageSize));
		param.put("loginUserId", new Long(loginUserId));
		param.put("deptIdList", deptIdList);
		param.put("likeCustomerName", likeCustomerName);
		param.put("likeBusiLicenseNo", likeBusiLicenseNo);
		return getSqlMapClientTemplate().queryForList("evalueting_form_info", param);
		
	}
	
	@Override
	public long countWithCondition(EvaluetingListForAuditDO evaluetingList, long loginUserId,
									List<Long> deptIdList, String likeCustomerName,
									String likeBusiLicenseNo) throws DataAccessException {
		if (evaluetingList == null) {
			throw new IllegalArgumentException("Can't select by a null data object.");
		}
		
		Map param = new HashMap();
		param.put("loginUserId", new Long(loginUserId));
		param.put("deptIdList", deptIdList);
		param.put("evaluetingList", evaluetingList);
		param.put("likeCustomerName", likeCustomerName);
		param.put("likeBusiLicenseNo", likeBusiLicenseNo);
		Long retObj = (Long) getSqlMapClientTemplate().queryForObject("evalueting_form_count",
			param);
		if (retObj == null) {
			return 0;
		} else {
			return retObj.longValue();
		}
		
	}
	
}
