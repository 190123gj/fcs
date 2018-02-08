package com.born.fcs.crm.dal.daointerface;

import java.util.Date;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.born.fcs.crm.dal.dataobject.CustomerRelationListDO;

@SuppressWarnings("rawtypes")
public interface CustomerRelationListDAO {
	
	public List<CustomerRelationListDO> findWithCondition(	CustomerRelationListDO customerRelationListDO,
															long limitStart, long pageSize,
															List customerLevelList,
															List industryCodeList,
															List cityCodeList, Date beginDate,
															Date endDate, String likeCustomerName,
															long loginUserId, List deptIdList,
															String likeNameOrId,
															String mobileQuery, String likeCertNo,
															String likeBusiLicenseNo,
															String includePublic)
																					throws DataAccessException;
	
	public long countWithCondition(CustomerRelationListDO customerRelationListDO,
									List customerLevelList, List industryCodeList,
									List cityCodeList, Date beginDate, Date endDate,
									String likeCustomerName, long loginUserId, List deptIdList,
									String likeNameOrId, String mobileQuery, String likeCertNo,
									String likeBusiLicenseNo, String includePublic)
																					throws DataAccessException;
	
}
