package com.born.fcs.crm.dal.ibatis;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.born.fcs.crm.dal.daointerface.CustomerRelationListDAO;
import com.born.fcs.crm.dal.dataobject.CustomerRelationListDO;

@SuppressWarnings("unchecked")
public class IbatisCustomerRelationListDAO extends SqlMapClientDaoSupport implements
																			CustomerRelationListDAO {
	
	@Override
	public List<CustomerRelationListDO> findWithCondition(	CustomerRelationListDO customerRelationListDO,
															long limitStart, long pageSize,
															List customerLevelList,
															List industryCodeList,
															List cityCodeList, Date beginDate,
															Date endDate, String likeCustomerName,
															long loginUserId, List deptIdList,
															String likeNameOrId,
															String mobileQuery, String likeCertNo,
															String likeBusiLicenseNo,String includePublic)
																						throws DataAccessException {
		if (customerRelationListDO == null) {
			throw new IllegalArgumentException("Can't select by a null data object.");
		}
		
		@SuppressWarnings("rawtypes")
		Map param = new HashMap();
		
		param.put("customerBaseInfo", customerRelationListDO);
		param.put("limitStart", new Long(limitStart));
		param.put("pageSize", new Long(pageSize));
		param.put("customerLevelList", customerLevelList);
		param.put("industryCodeList", industryCodeList);
		param.put("cityCodeList", cityCodeList);
		param.put("beginDate", beginDate);
		param.put("endDate", endDate);
		param.put("likeCustomerName", likeCustomerName);
		param.put("loginUserId", new Long(loginUserId));
		param.put("deptIdList", deptIdList);
		param.put("likeNameOrId", likeNameOrId);
		param.put("mobileQuery", mobileQuery);
		param.put("includePublic", includePublic);
		param.put("likeCertNo", likeCertNo);
		param.put("likeBusiLicenseNo", likeBusiLicenseNo);
		return getSqlMapClientTemplate().queryForList("MS-CUSTOMER-RELATION_LIST-WITH-CONDITION",
			param);
		
	}
	
	@Override
	public long countWithCondition(CustomerRelationListDO customerRelationListDO,
									List customerLevelList, List industryCodeList,
									List cityCodeList, Date beginDate, Date endDate,
									String likeCustomerName, long loginUserId, List deptIdList,
									String likeNameOrId, String mobileQuery,String likeCertNo,
									String likeBusiLicenseNo,String includePublic) throws DataAccessException {
		if (customerRelationListDO == null) {
			throw new IllegalArgumentException("Can't select by a null data object.");
		}
		
		Map param = new HashMap();
		
		param.put("customerBaseInfo", customerRelationListDO);
		param.put("customerLevelList", customerLevelList);
		param.put("industryCodeList", industryCodeList);
		param.put("cityCodeList", cityCodeList);
		param.put("beginDate", beginDate);
		param.put("endDate", endDate);
		param.put("likeCustomerName", likeCustomerName);
		param.put("loginUserId", new Long(loginUserId));
		param.put("deptIdList", deptIdList);
		param.put("likeNameOrId", likeNameOrId);
		param.put("mobileQuery", mobileQuery);
		param.put("includePublic", includePublic);
		param.put("likeCertNo", likeCertNo);
		param.put("likeBusiLicenseNo", likeBusiLicenseNo);
		@SuppressWarnings("deprecation")
		Long retObj = (Long) getSqlMapClientTemplate().queryForObject(
			"MS-RELATION_LIST-COUNT-WITH-CONDITION", param);
		
		if (retObj == null) {
			return 0;
		} else {
			return retObj.longValue();
		}
	}
	
}
