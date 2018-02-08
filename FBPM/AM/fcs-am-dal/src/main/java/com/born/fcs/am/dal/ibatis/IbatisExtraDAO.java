/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.am.dal.ibatis;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.born.fcs.am.dal.daointerface.ExtraDAO;
import com.born.fcs.am.dataobject.AssessCompanyApplyFormDO;
import com.born.fcs.am.dataobject.AssetSimpleDO;
import com.born.fcs.am.dataobject.AssetsTransferApplicationFormDO;
import com.born.fcs.am.dataobject.AssetsTransfereeApplicationFormDO;
import com.born.fcs.am.dataobject.BackTaskDO;
import com.born.fcs.am.dataobject.PledgeTypeAttributeImageDO;
import com.born.fcs.am.dataobject.PledgeTypeAttributeNetworkDO;
import com.born.fcs.am.dataobject.PledgeTypeAttributeTextDO;

/**
 * 
 * @Filename IbatisExtraDAO.java
 * 
 * @Description
 * 
 * @Version 1.0
 * 
 * @Author peigen
 * 
 * @Email peigen@yiji.com
 * 
 * @History <li>Author: peigen</li> <li>Date: 2011-9-6</li> <li>Version: 1.0</li>
 * <li>Content: create</li>
 * 
 */
@SuppressWarnings("deprecation")
public class IbatisExtraDAO extends SqlMapClientDaoSupport implements ExtraDAO {
	
	/**
	 * @return
	 * @see com.yjf.paycore.dal.daointerface.ExtraDAO#getSysdate()
	 */
	@Override
	public Date getSysdate() {
		return (Date) getSqlMapClientTemplate().queryForObject("MS-COMMON-GET-SYSDATE");
	}
	
	@Override
	public List<PledgeTypeAttributeTextDO> searchPledgeTypeAttributeTextList(	PledgeTypeAttributeTextDO attributeTextDO)
																															throws DataAccessException {
		
		if (attributeTextDO == null) {
			throw new IllegalArgumentException("setupForm can`t by null");
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("assetsId", attributeTextDO.getAssetsId());
		paramMap.put("typeId", attributeTextDO.getTypeId());
		paramMap.put("attributeKey", attributeTextDO.getAttributeKey());
		paramMap.put("customType", attributeTextDO.getCustomType());
		paramMap.put("fieldName", attributeTextDO.getFieldName());
		paramMap.put("isByRelation", attributeTextDO.getIsByRelation());
		paramMap.put("relationConditionItem", attributeTextDO.getRelationConditionItem());
		paramMap.put("relationFieldName", attributeTextDO.getRelationFieldName());
		paramMap.put("fieldType", attributeTextDO.getFieldType());
		
		List<PledgeTypeAttributeTextDO> list = getSqlMapClientTemplate().queryForList(
			"MS-EXTRA-FORM-PLEDGE-TYPE-ATTRIBUTE-TEXT-SEARCH", paramMap);
		
		return list;
	}
	
	@Override
	public long searchPledgeTypeAttributeTextDOCount(PledgeTypeAttributeTextDO attributeTextDO)
																								throws DataAccessException {
		if (attributeTextDO == null) {
			throw new IllegalArgumentException("setupForm can`t by null");
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("assetsId", attributeTextDO.getAssetsId());
		paramMap.put("typeId", attributeTextDO.getTypeId());
		paramMap.put("attributeKey", attributeTextDO.getAttributeKey());
		paramMap.put("customType", attributeTextDO.getCustomType());
		paramMap.put("fieldName", attributeTextDO.getFieldName());
		paramMap.put("isByRelation", attributeTextDO.getIsByRelation());
		paramMap.put("relationConditionItem", attributeTextDO.getRelationConditionItem());
		paramMap.put("relationFieldName", attributeTextDO.getRelationFieldName());
		paramMap.put("fieldType", attributeTextDO.getFieldType());
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-FORM-PLEDGE-TYPE-ATTRIBUTE-TEXT-COUNT", paramMap);
	}
	
	@Override
	public List<PledgeTypeAttributeImageDO> searchPledgeTypeAttributeImageList(	PledgeTypeAttributeImageDO attributeImageDO)
																															throws DataAccessException {
		if (attributeImageDO == null) {
			throw new IllegalArgumentException("setupForm can`t by null");
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("assetsId", attributeImageDO.getAssetsId());
		paramMap.put("typeId", attributeImageDO.getTypeId());
		paramMap.put("attributeKey", attributeImageDO.getAttributeKey());
		paramMap.put("customType", attributeImageDO.getCustomType());
		paramMap.put("fieldName", attributeImageDO.getFieldName());
		paramMap.put("latestEntryForm", attributeImageDO.getLatestEntryForm());
		List<PledgeTypeAttributeImageDO> list = getSqlMapClientTemplate().queryForList(
			"MS-EXTRA-FORM-PLEDGE-TYPE-ATTRIBUTE-IMAGE-SEARCH", paramMap);
		
		return list;
	}
	
	@Override
	public long searchPledgeTypeAttributeImageCount(PledgeTypeAttributeImageDO attributeImageDO)
																								throws DataAccessException {
		if (attributeImageDO == null) {
			throw new IllegalArgumentException("setupForm can`t by null");
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("assetsId", attributeImageDO.getAssetsId());
		paramMap.put("typeId", attributeImageDO.getTypeId());
		paramMap.put("attributeKey", attributeImageDO.getAttributeKey());
		paramMap.put("customType", attributeImageDO.getCustomType());
		paramMap.put("fieldName", attributeImageDO.getFieldName());
		paramMap.put("latestEntryForm", attributeImageDO.getLatestEntryForm());
		
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-FORM-PLEDGE-TYPE-ATTRIBUTE-IMAGE-COUNT", paramMap);
	}
	
	@Override
	public List<PledgeTypeAttributeNetworkDO> searchPledgeTypeAttributeNetworkList(	PledgeTypeAttributeNetworkDO attributeNetworkDO)
																																	throws DataAccessException {
		if (attributeNetworkDO == null) {
			throw new IllegalArgumentException("setupForm can`t by null");
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("assetsId", attributeNetworkDO.getAssetsId());
		paramMap.put("typeId", attributeNetworkDO.getTypeId());
		paramMap.put("attributeKey", attributeNetworkDO.getAttributeKey());
		paramMap.put("customType", attributeNetworkDO.getCustomType());
		paramMap.put("websiteName", attributeNetworkDO.getWebsiteName());
		List<PledgeTypeAttributeNetworkDO> list = getSqlMapClientTemplate().queryForList(
			"MS-EXTRA-FORM-PLEDGE-TYPE-ATTRIBUTE-NETWORK-SEARCH", paramMap);
		
		return list;
	}
	
	@Override
	public long searchPledgeTypeAttributeNetworkCount(	PledgeTypeAttributeNetworkDO attributeNetworkDO)
																										throws DataAccessException {
		if (attributeNetworkDO == null) {
			throw new IllegalArgumentException("setupForm can`t by null");
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("assetsId", attributeNetworkDO.getAssetsId());
		paramMap.put("typeId", attributeNetworkDO.getTypeId());
		paramMap.put("attributeKey", attributeNetworkDO.getAttributeKey());
		paramMap.put("customType", attributeNetworkDO.getCustomType());
		paramMap.put("websiteName", attributeNetworkDO.getWebsiteName());
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-FORM-PLEDGE-TYPE-ATTRIBUTE-NETWORK-COUNT", paramMap);
	}
	
	@Override
	public List<AssetsTransferApplicationFormDO> searchAssetsTransferApplicationList(	AssetsTransferApplicationFormDO DO,
																						long limitStart,
																						long pageSize,
																						Date submitTimeStart,
																						Date submitTimeEnd,
																						List<Long> deptIdList,
																						String sortCol,
																						String sortOrder)
																											throws DataAccessException {
		if (DO == null) {
			throw new IllegalArgumentException("setupForm can`t by null");
		}
		
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("formStatus", DO.getFormStatus());
		paramMap.put("formUserName", DO.getFormUserName());
		
		paramMap.put("limitStart", limitStart);
		paramMap.put("pageSize", pageSize);
		paramMap.put("sortOrder", sortOrder);
		paramMap.put("sortCol", sortCol);
		
		paramMap.put("formId", DO.getFormId());
		
		paramMap.put("applyId", DO.getId());
		
		paramMap.put("projectCode", DO.getProjectCode());
		// paramMap.put("busiType", DO.getBusiType());
		// paramMap.put("busiTypeName", DO.getBusiTypeName());
		paramMap.put("projectName", DO.getProjectName());
		paramMap.put("isToMeet", DO.getIsToMeet());
		paramMap.put("loginUserId", DO.getLoginUserId());
		paramMap.put("draftUserId", DO.getDraftUserId());
		paramMap.put("deptIdList", DO.getDeptIdList());
		paramMap.put("statusList", DO.getStatusList());
		
		// paramMap.put("busiManagerbName", DO.getBusiManagerbName());
		// paramMap.put("busiManagerbName", DO.getBusiManagerbName());
		
		paramMap.put("isTrusteeLiquidate", DO.getIsTrusteeLiquidate());
		paramMap.put("liquidateTimeStart", DO.getLiquidateTimeStart());
		paramMap.put("liquidateTimeEnd", DO.getLiquidateTimeEnd());
		paramMap.put("councilStatus", DO.getCouncilStatus());

		paramMap.put("deptIdList", deptIdList);
		List<AssetsTransferApplicationFormDO> list = getSqlMapClientTemplate().queryForList(
			"MS-EXTRA-FORM-ASSETS-TRANSFER-APPLICATION-SEARCH", paramMap);
		
		return list;
	}
	
	@Override
	public long searchAssetsTransferApplicationCount(AssetsTransferApplicationFormDO DO,
														Date submitTimeStart, Date submitTimeEnd,
														List<Long> deptIdList)
																				throws DataAccessException {
		if (DO == null) {
			throw new IllegalArgumentException("setupForm can`t by null");
		}
		
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("formStatus", DO.getFormStatus());
		paramMap.put("formUserName", DO.getFormUserName());
		paramMap.put("formId", DO.getFormId());
		// paramMap.put("applyId", DO.getId());
		paramMap.put("projectCode", DO.getProjectCode());
		// paramMap.put("busiType", DO.getBusiType());
		// paramMap.put("busiTypeName", DO.getBusiTypeName());
		paramMap.put("projectName", DO.getProjectName());
		// paramMap.put("customerName", DO.getCustomerName());
		paramMap.put("isToMeet", DO.getIsToMeet());
		paramMap.put("loginUserId", DO.getLoginUserId());
		paramMap.put("draftUserId", DO.getDraftUserId());
		paramMap.put("deptIdList", DO.getDeptIdList());
		paramMap.put("statusList", DO.getStatusList());
		// paramMap.put("busiManagerbName", DO.getBusiManagerbName());
		// paramMap.put("busiManagerbName", DO.getBusiManagerbName());
		paramMap.put("isTrusteeLiquidate", DO.getIsTrusteeLiquidate());
		paramMap.put("liquidateTimeStart", DO.getLiquidateTimeStart());
		paramMap.put("liquidateTimeEnd", DO.getLiquidateTimeEnd());
		paramMap.put("councilStatus", DO.getCouncilStatus());
		
		paramMap.put("deptIdList", deptIdList);
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-FORM-ASSETS-TRANSFER-APPLICATION-SEARCH-COUNT", paramMap);
	}
	
	@Override
	public List<AssetsTransfereeApplicationFormDO> searchAssetsTransfereeApplicationList(	AssetsTransfereeApplicationFormDO DO,
																							long limitStart,
																							long pageSize,
																							Date submitTimeStart,
																							Date submitTimeEnd,
																							List<Long> deptIdList,
																							String sortCol,
																							String sortOrder)
																												throws DataAccessException {
		if (DO == null) {
			throw new IllegalArgumentException("setupForm can`t by null");
		}
		
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("formStatus", DO.getFormStatus());
		paramMap.put("formUserName", DO.getFormUserName());
		
		paramMap.put("limitStart", limitStart);
		paramMap.put("pageSize", pageSize);
		paramMap.put("sortOrder", sortOrder);
		paramMap.put("sortCol", sortCol);
		
		paramMap.put("formId", DO.getFormId());
		
		paramMap.put("applyId", DO.getId());
		
		paramMap.put("projectCode", DO.getProjectCode());
		// paramMap.put("busiType", DO.getBusiType());
		// paramMap.put("busiTypeName", DO.getBusiTypeName());
		paramMap.put("projectName", DO.getProjectName());
		
		paramMap.put("loginUserId", DO.getLoginUserId());
		paramMap.put("draftUserId", DO.getDraftUserId());
		paramMap.put("deptIdList", DO.getDeptIdList());
		paramMap.put("statusList", DO.getStatusList());
		
		// paramMap.put("busiManagerbName", DO.getBusiManagerbName());
		// paramMap.put("busiManagerbName", DO.getBusiManagerbName());
		
		paramMap.put("isTrusteeLiquidate", DO.getIsTrusteeLiquidate());
		paramMap.put("liquidateTimeStart", DO.getLiquidateTimeStart());
		paramMap.put("liquidateTimeEnd", DO.getLiquidateTimeEnd());
		
		
		paramMap.put("deptIdList", deptIdList);
		List<AssetsTransfereeApplicationFormDO> list = getSqlMapClientTemplate().queryForList(
			"MS-EXTRA-FORM-ASSETS-TRANSFEREE-APPLICATION-SEARCH", paramMap);
		
		return list;
	}
	
	@Override
	public long searchAssetsTransfereeApplicationCount(AssetsTransfereeApplicationFormDO DO,
														Date submitTimeStart, Date submitTimeEnd,
														List<Long> deptIdList)
																				throws DataAccessException {
		if (DO == null) {
			throw new IllegalArgumentException("setupForm can`t by null");
		}
		
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("formStatus", DO.getFormStatus());
		paramMap.put("formUserName", DO.getFormUserName());
		paramMap.put("formId", DO.getFormId());
		// paramMap.put("applyId", DO.getId());
		paramMap.put("projectCode", DO.getProjectCode());
		// paramMap.put("busiType", DO.getBusiType());
		// paramMap.put("busiTypeName", DO.getBusiTypeName());
		paramMap.put("projectName", DO.getProjectName());
		// paramMap.put("customerName", DO.getCustomerName());
		
		paramMap.put("loginUserId", DO.getLoginUserId());
		paramMap.put("draftUserId", DO.getDraftUserId());
		paramMap.put("deptIdList", DO.getDeptIdList());
		paramMap.put("statusList", DO.getStatusList());
		// paramMap.put("busiManagerbName", DO.getBusiManagerbName());
		// paramMap.put("busiManagerbName", DO.getBusiManagerbName());
		
		paramMap.put("isTrusteeLiquidate", DO.getIsTrusteeLiquidate());
		paramMap.put("liquidateTimeStart", DO.getLiquidateTimeStart());
		paramMap.put("liquidateTimeEnd", DO.getLiquidateTimeEnd());
		paramMap.put("deptIdList", deptIdList);
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-FORM-ASSETS-TRANSFEREE-APPLICATION-SEARCH-COUNT", paramMap);
	}
	
	@Override
	public List<AssessCompanyApplyFormDO> searchAssessCompanyApplyList(AssessCompanyApplyFormDO DO,
																		long limitStart,
																		long pageSize,
																		Date submitTimeStart,
																		Date submitTimeEnd,
																		List<Long> deptIdList,
																		String sortCol,
																		String sortOrder)
																							throws DataAccessException {
		if (DO == null) {
			throw new IllegalArgumentException("setupForm can`t by null");
		}
		
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("formStatus", DO.getFormStatus());
		paramMap.put("formUserName", DO.getFormUserName());
		
		paramMap.put("limitStart", limitStart);
		paramMap.put("pageSize", pageSize);
		paramMap.put("sortOrder", sortOrder);
		paramMap.put("sortCol", sortCol);
		
		paramMap.put("formId", DO.getFormId());
		
		paramMap.put("id", DO.getId());
		
		paramMap.put("projectCode", DO.getProjectCode());
		paramMap.put("projectName", DO.getProjectName());
		paramMap.put("companyName", DO.getCompanyName());
		paramMap.put("customerName", DO.getCustomerName());
		paramMap.put("loginUserId", DO.getLoginUserId());
		paramMap.put("draftUserId", DO.getDraftUserId());
		paramMap.put("deptIdList", DO.getDeptIdList());
		paramMap.put("statusList", DO.getStatusList());
		
		// paramMap.put("busiManagerbName", DO.getBusiManagerbName());
		// paramMap.put("busiManagerbName", DO.getBusiManagerbName());
		
		paramMap.put("deptIdList", deptIdList);
		List<AssessCompanyApplyFormDO> list = getSqlMapClientTemplate().queryForList(
			"MS-EXTRA-FORM-ASSETS-COMPANY-APPLY-SEARCH", paramMap);
		
		return list;
	}
	
	@Override
	public long searchAssessCompanyApplyCount(AssessCompanyApplyFormDO DO, Date submitTimeStart,
												Date submitTimeEnd, List<Long> deptIdList)
																							throws DataAccessException {
		if (DO == null) {
			throw new IllegalArgumentException("setupForm can`t by null");
		}
		
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("formStatus", DO.getFormStatus());
		paramMap.put("formUserName", DO.getFormUserName());
		paramMap.put("formId", DO.getFormId());
		
		paramMap.put("projectCode", DO.getProjectCode());
		paramMap.put("companyName", DO.getCompanyName());
		paramMap.put("projectName", DO.getProjectName());
		paramMap.put("customerName", DO.getCustomerName());
		
		paramMap.put("loginUserId", DO.getLoginUserId());
		paramMap.put("draftUserId", DO.getDraftUserId());
		paramMap.put("deptIdList", DO.getDeptIdList());
		paramMap.put("statusList", DO.getStatusList());
		// paramMap.put("busiManagerbName", DO.getBusiManagerbName());
		// paramMap.put("busiManagerbName", DO.getBusiManagerbName());
		
		paramMap.put("deptIdList", deptIdList);
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-FORM-ASSETS-COMPANY-APPLY-SEARCH-COUNT", paramMap);
	}
	
	@Override
	public List<AssetsTransferApplicationFormDO> searchAssetsTransfereeProjectSelectList(	AssetsTransferApplicationFormDO DO,
																							long limitStart,
																							long pageSize,
																							Date submitTimeStart,
																							Date submitTimeEnd,
																							List<Long> deptIdList,
																							String sortCol,
																							String sortOrder)
																												throws DataAccessException {
		if (DO == null) {
			throw new IllegalArgumentException("setupForm can`t by null");
		}
		
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("formStatus", DO.getFormStatus());
		paramMap.put("formUserName", DO.getFormUserName());
		
		paramMap.put("limitStart", limitStart);
		paramMap.put("pageSize", pageSize);
		paramMap.put("sortOrder", sortOrder);
		paramMap.put("sortCol", sortCol);
		
		paramMap.put("formId", DO.getFormId());
		
		paramMap.put("applyId", DO.getId());
		
		paramMap.put("projectCode", DO.getProjectCode());
		paramMap.put("projectName", DO.getProjectName());
		
		paramMap.put("loginUserId", DO.getLoginUserId());
		paramMap.put("draftUserId", DO.getDraftUserId());
		paramMap.put("deptIdList", DO.getDeptIdList());
		paramMap.put("statusList", DO.getStatusList());
		
		// paramMap.put("busiManagerbName", DO.getBusiManagerbName());
		// paramMap.put("busiManagerbName", DO.getBusiManagerbName());
		
		paramMap.put("isTrusteeLiquidate", DO.getIsTrusteeLiquidate());
		paramMap.put("liquidateTimeStart", DO.getLiquidateTimeStart());
		paramMap.put("liquidateTimeEnd", DO.getLiquidateTimeEnd());
		paramMap.put("liquidaterStatus", DO.getLiquidaterStatus());
		paramMap.put("deptIdList", deptIdList);
		List<AssetsTransferApplicationFormDO> list = getSqlMapClientTemplate().queryForList(
			"MS-EXTRA-FORM-ASSETS-TRANSFEREE-APPLICATION-PROJECT-SEARCH", paramMap);
		
		return list;
	}
	
	@Override
	public long searchAssetsTransfereeProjectSelectCount(AssetsTransferApplicationFormDO DO,
															Date submitTimeStart,
															Date submitTimeEnd,
															List<Long> deptIdList)
																					throws DataAccessException {
		if (DO == null) {
			throw new IllegalArgumentException("setupForm can`t by null");
		}
		
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("formStatus", DO.getFormStatus());
		paramMap.put("formUserName", DO.getFormUserName());
		paramMap.put("formId", DO.getFormId());
		// paramMap.put("applyId", DO.getId());
		paramMap.put("projectCode", DO.getProjectCode());
		
		paramMap.put("projectName", DO.getProjectName());
		// paramMap.put("customerName", DO.getCustomerName());
		
		paramMap.put("loginUserId", DO.getLoginUserId());
		paramMap.put("draftUserId", DO.getDraftUserId());
		paramMap.put("deptIdList", DO.getDeptIdList());
		paramMap.put("statusList", DO.getStatusList());
		// paramMap.put("busiManagerbName", DO.getBusiManagerbName());
		// paramMap.put("busiManagerbName", DO.getBusiManagerbName());
		paramMap.put("isTrusteeLiquidate", DO.getIsTrusteeLiquidate());
		paramMap.put("liquidateTimeStart", DO.getLiquidateTimeStart());
		paramMap.put("liquidateTimeEnd", DO.getLiquidateTimeEnd());
		paramMap.put("liquidaterStatus", DO.getLiquidaterStatus());
		paramMap.put("deptIdList", deptIdList);
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-FORM-ASSETS-TRANSFEREE-APPLICATION-PROJECT-SEARCH-COUNT", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AssetSimpleDO> searchAssessSimpleList(Map<String, Object> paramMap)
																					throws DataAccessException {
		List<AssetSimpleDO> list = getSqlMapClientTemplate().queryForList(
			"MS-EXTRA-SIMPLE-ASSETS-SEARCH", paramMap);
		
		return list;
	}
	
	@Override
	public long searchAssessSimple(Map<String, Object> paramMap) throws DataAccessException {
		return (Long) getSqlMapClientTemplate().queryForObject("MS-EXTRA-SIMPLE-ASSETS-COUNT",
			paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BackTaskDO> backTaskList(long userId) throws DataAccessException {
		List<BackTaskDO> list = getSqlMapClientTemplate().queryForList("MS-BUSI-BACK-TASK", userId);
		return list;
	}
}
