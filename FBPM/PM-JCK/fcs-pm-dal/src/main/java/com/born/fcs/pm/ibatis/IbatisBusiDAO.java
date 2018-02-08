/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ibatis;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.born.fcs.pm.dal.dataobject.FCouncilSummaryRiskHandleDO;
import com.born.fcs.pm.dal.dataobject.FInvestigationAssetReviewDO;
import com.born.fcs.pm.dal.dataobject.ProjectExtendInfoDO;
import com.born.fcs.pm.daointerface.BusiDAO;
import com.born.fcs.pm.dataobject.BackTaskDO;
import com.born.fcs.pm.dataobject.BrokerBusinessFormDO;
import com.born.fcs.pm.dataobject.InternalOpinionExchangeFormDO;
import com.born.fcs.pm.dataobject.ManagerbChangeApplyFormDO;
import com.born.fcs.pm.dataobject.ToReportDO;
import com.born.fcs.pm.dataobject.ToReportExpectEventDO;
import com.born.fcs.pm.util.StringUtil;

/**
 * @Filename IbatisBusiDAO.java
 * @Description 手动写的业务sql
 * @Version 1.0
 * @Author wuzj
 * @Email yuanying@yiji.com
 * @History <li>Author: wuzj</li> <li>Date: 2016-7-6</li> <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
@SuppressWarnings("deprecation")
public class IbatisBusiDAO extends SqlMapClientDaoSupport implements BusiDAO {
	
	@SuppressWarnings("unchecked")
	@Override
	public List<FCouncilSummaryRiskHandleDO> findApprovalRiskHandleSummary(String projectCode)
																								throws DataAccessException {
		if (projectCode == null) {
			throw new IllegalArgumentException("projectCode can`t by null");
		}
		
		List<FCouncilSummaryRiskHandleDO> list = (List<FCouncilSummaryRiskHandleDO>) getSqlMapClientTemplate()
			.queryForList("MS-BUSI-APPROVAL-RISK-HANDLE-COUNCIL-SUMMARY", projectCode);
		return list;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<InternalOpinionExchangeFormDO> searchInternalOpinionForm(	InternalOpinionExchangeFormDO exForm) {
		if (exForm == null) {
			throw new IllegalArgumentException("exForm can`t by null");
		}
		
		Map paramMap = new HashMap<String, Object>();
		
		paramMap.put("limitStart", exForm.getLimitStart());
		paramMap.put("pageSize", exForm.getPageSize());
		paramMap.put("sortOrder", exForm.getSortOrder());
		paramMap.put("sortCol", exForm.getSortCol());
		
		paramMap.put("formId", exForm.getFormId());
		paramMap.put("formStatus", exForm.getFormStatus());
		paramMap.put("exType", exForm.getExType());
		paramMap.put("deptNames", exForm.getDeptNames());
		paramMap.put("users", exForm.getUsers());
		paramMap.put("deptIdList", exForm.getDeptIdList());
		paramMap.put("loginUserId", exForm.getLoginUserId());
		List<InternalOpinionExchangeFormDO> list = getSqlMapClientTemplate().queryForList(
			"MS-BUSI-INTERNAL-OPINION-EXCHANGE-FORM", paramMap);
		
		return list;
	}
	
	@Override
	public long searchInternalOpinionFormCount(InternalOpinionExchangeFormDO exForm) {
		if (exForm == null) {
			throw new IllegalArgumentException("exForm can`t by null");
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("formId", exForm.getFormId());
		paramMap.put("formStatus", exForm.getFormStatus());
		paramMap.put("exType", exForm.getExType());
		paramMap.put("deptNames", exForm.getDeptNames());
		paramMap.put("users", exForm.getUsers());
		paramMap.put("deptIdList", exForm.getDeptIdList());
		paramMap.put("loginUserId", exForm.getLoginUserId());
		
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-BUSI-INTERNAL-OPINION-EXCHANGE-FORM-COUNT", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectExtendInfoDO> findApprovalProjectExtendInfo(ProjectExtendInfoDO queryDO) {
		if (queryDO == null) {
			throw new IllegalArgumentException("ProjectExtendInfoDO can`t by null");
		}
		List<ProjectExtendInfoDO> list = (List<ProjectExtendInfoDO>) getSqlMapClientTemplate()
			.queryForList("MS-BUSI-PROJECT-PASSED-EXTEND-INFO", queryDO);
		return list;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<BrokerBusinessFormDO> searchBrokerBusinessForm(BrokerBusinessFormDO exForm) {
		if (exForm == null) {
			throw new IllegalArgumentException("BrokerBusinessFormDO can`t by null");
		}
		
		Map paramMap = new HashMap<String, Object>();
		
		paramMap.put("limitStart", exForm.getLimitStart());
		paramMap.put("pageSize", exForm.getPageSize());
		paramMap.put("sortOrder", exForm.getSortOrder());
		paramMap.put("sortCol", exForm.getSortCol());
		
		paramMap.put("id", exForm.getId());
		paramMap.put("formId", exForm.getFormId());
		paramMap.put("formStatus", exForm.getFormStatus());
		paramMap.put("projectCode", exForm.getProjectCode());
		paramMap.put("customerName", exForm.getCustomerName());
		paramMap.put("isNeedCouncil", exForm.getIsNeedCouncil());
		paramMap.put("isSelForCharge", exForm.getIsSelForCharge());
		paramMap.put("deptIdList", exForm.getDeptIdList());
		paramMap.put("loginUserId", exForm.getLoginUserId());
		List<BrokerBusinessFormDO> list = getSqlMapClientTemplate().queryForList(
			"MS-BUSI-BROKER-BUSINESS-FORM", paramMap);
		
		return list;
	}
	
	@Override
	public long searchBrokerBusinessFormCount(BrokerBusinessFormDO exForm) {
		if (exForm == null) {
			throw new IllegalArgumentException("BrokerBusinessFormDO can`t by null");
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("id", exForm.getId());
		paramMap.put("formId", exForm.getFormId());
		paramMap.put("formStatus", exForm.getFormStatus());
		paramMap.put("projectCode", exForm.getProjectCode());
		paramMap.put("customerName", exForm.getCustomerName());
		paramMap.put("isNeedCouncil", exForm.getIsNeedCouncil());
		paramMap.put("isSelForCharge", exForm.getIsSelForCharge());
		paramMap.put("deptIdList", exForm.getDeptIdList());
		paramMap.put("loginUserId", exForm.getLoginUserId());
		
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-BUSI-BROKER-BUSINESS-FORM-COUNT", paramMap);
	}
	
	@Override
	public String getReleasingAmountToReport() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		//		paramMap.put("startTime", startTime);
		//		paramMap.put("endTime", endTime);
		
		return (String) getSqlMapClientTemplate().queryForObject(
			"MS-BUSI-RELEASING-AMOUNT-TO-REPORT", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getRepayOrChargeAmount(String deptCode, String type, Date startTime,
												Date endTime) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("deptCode", deptCode);
		paramMap.put("type", type);
		paramMap.put("startTime", startTime);
		paramMap.put("endTime", endTime);
		List<String> list = new ArrayList<String>();
		List<Map<String, Object>> resultMapList = getSqlMapClientTemplate().queryForList(
			"MS-BUSI-REPAY-OR-CHARGE-AMOUNT-TO-REPORT", paramMap);
		for (Map<String, Object> map : resultMapList) {
			if (map.get("amount") == null || StringUtil.isEmpty(map.get("amount").toString())) {
				list.add("0");
			} else {
				list.add(map.get("amount").toString());
			}
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ToReportExpectEventDO> getExpectEvent() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		List<ToReportExpectEventDO> list = getSqlMapClientTemplate().queryForList(
			"MS-BUSI-EXPECT-EVENT-TO-REPORT", paramMap);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ToReportDO> getReportProjectProcess(Date startTime, Date endTime) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("startTime", startTime);
		paramMap.put("endTime", endTime);
		List<ToReportDO> list = getSqlMapClientTemplate().queryForList(
			"MS-BUSI-PROJECT-PROCESS-TO-REPORT", paramMap);
		return list;
	}
	
	@Override
	public long searchAssetReviewCount(Map<String, Object> paramMap) throws DataAccessException {
		return (Long) getSqlMapClientTemplate().queryForObject("MS-EXTRA-ASSET-REVIEW-COUNT",
			paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<FInvestigationAssetReviewDO> searchAssetReview(Map<String, Object> paramMap)
																							throws DataAccessException {
		List<FInvestigationAssetReviewDO> list = getSqlMapClientTemplate().queryForList(
			"MS-EXTRA-ASSET-REVIEW-SEARCH", paramMap);
		
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BackTaskDO> backTaskList(long userId) throws DataAccessException {
		List<BackTaskDO> list = getSqlMapClientTemplate().queryForList("MS-BUSI-BACK-TASK", userId);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ManagerbChangeApplyFormDO> searchManagerbChangeApplyForm(	ManagerbChangeApplyFormDO searchDO)
	
	throws DataAccessException {
		if (searchDO == null) {
			throw new IllegalArgumentException("ManagerbChangeApplyFormDO can`t by null");
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("limitStart", searchDO.getLimitStart());
		paramMap.put("pageSize", searchDO.getPageSize());
		paramMap.put("sortOrder", searchDO.getSortOrder());
		paramMap.put("sortCol", searchDO.getSortCol());
		
		paramMap.put("applyId", searchDO.getApplyId());
		paramMap.put("status", searchDO.getStatus());
		paramMap.put("formStatus", searchDO.getProjectCode());
		paramMap.put("projectCode", searchDO.getProjectCode());
		paramMap.put("customerName", searchDO.getCustomerName());
		paramMap.put("busiManagerId", searchDO.getManagerId());
		paramMap.put("busiManagerName", searchDO.getManagerName());
		
		paramMap.put("deptIdList", searchDO.getDeptIdList());
		paramMap.put("loginUserId", searchDO.getLoginUserId());
		
		return getSqlMapClientTemplate().queryForList("MS-BUSI-MANAGERB-CHANGE-FORM", paramMap);
	}
	
	@Override
	public long searchManagerbChangeApplyFormCount(ManagerbChangeApplyFormDO searchDO)
																						throws DataAccessException {
		if (searchDO == null) {
			throw new IllegalArgumentException("ManagerbChangeApplyFormDO can`t by null");
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("applyId", searchDO.getApplyId());
		paramMap.put("status", searchDO.getStatus());
		paramMap.put("formStatus", searchDO.getProjectCode());
		paramMap.put("projectCode", searchDO.getProjectCode());
		paramMap.put("customerName", searchDO.getCustomerName());
		paramMap.put("busiManagerId", searchDO.getManagerId());
		paramMap.put("busiManagerName", searchDO.getManagerName());
		
		paramMap.put("deptIdList", searchDO.getDeptIdList());
		paramMap.put("loginUserId", searchDO.getLoginUserId());
		
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-BUSI-MANAGERB-CHANGE-FORM-COUNT", paramMap);
	}
}
