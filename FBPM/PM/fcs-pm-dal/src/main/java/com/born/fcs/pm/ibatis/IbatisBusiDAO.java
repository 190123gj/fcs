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

import org.apache.poi.ss.formula.functions.T;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.born.fcs.pm.dal.dataobject.FCounterGuaranteeRepayDO;
import com.born.fcs.pm.dal.dataobject.FInvestigationAssetReviewDO;
import com.born.fcs.pm.dal.dataobject.FProjectFinancialDO;
import com.born.fcs.pm.dal.dataobject.ProjectDO;
import com.born.fcs.pm.dal.dataobject.ProjectExtendInfoDO;
import com.born.fcs.pm.dal.dataobject.ProjectFinancialDO;
import com.born.fcs.pm.daointerface.BusiDAO;
import com.born.fcs.pm.dataobject.BackTaskDO;
import com.born.fcs.pm.dataobject.BrokerBusinessFormDO;
import com.born.fcs.pm.dataobject.CapitalExportDO;
import com.born.fcs.pm.dataobject.CouncilSummaryRiskHandleDO;
import com.born.fcs.pm.dataobject.FinancialProjectContractFormDO;
import com.born.fcs.pm.dataobject.FinancialProjectReviewFormDO;
import com.born.fcs.pm.dataobject.InternalOpinionExchangeFormDO;
import com.born.fcs.pm.dataobject.ManagerbChangeApplyFormDO;
import com.born.fcs.pm.dataobject.ProjectChargePayDO;
import com.born.fcs.pm.dataobject.ProjectGenWithdrawFinancialTransferDO;
import com.born.fcs.pm.dataobject.ProjectTransferDetailFormDO;
import com.born.fcs.pm.dataobject.ToReportDO;
import com.born.fcs.pm.dataobject.ToReportExpectEventDO;
import com.born.fcs.pm.dataobject.ToReportGuaranteeStructreDO;
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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<CouncilSummaryRiskHandleDO> findApprovalRiskHandleSummary(	CouncilSummaryRiskHandleDO queryDO)
																												throws DataAccessException {
		if (queryDO == null) {
			throw new IllegalArgumentException("CouncilSummaryRiskHandleDO can`t by null");
		}
		
		Map paramMap = new HashMap<String, Object>();
		paramMap.put("limitStart", queryDO.getLimitStart());
		paramMap.put("pageSize", queryDO.getPageSize());
		paramMap.put("sortOrder", queryDO.getSortOrder());
		paramMap.put("sortCol", queryDO.getSortCol());
		
		paramMap.put("summaryId", queryDO.getSummaryId());
		paramMap.put("councilId", queryDO.getCouncilId());
		paramMap.put("customerId", queryDO.getCustomerId());
		paramMap.put("customerName", queryDO.getCustomerName());
		paramMap.put("projectCode", queryDO.getProjectCode());
		paramMap.put("projectName", queryDO.getProjectName());
		paramMap.put("summaryCode", queryDO.getSummaryCode());
		paramMap.put("busiManagerId", queryDO.getBusiManagerId());
		
		paramMap.put("deptIdList", queryDO.getDeptIdList());
		paramMap.put("loginUserId", queryDO.getLoginUserId());
		
		List<CouncilSummaryRiskHandleDO> list = (List<CouncilSummaryRiskHandleDO>) getSqlMapClientTemplate()
			.queryForList("MS-BUSI-APPROVAL-RISK-HANDLE-COUNCIL-SUMMARY", paramMap);
		return list;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public long findApprovalRiskHandleSummaryCount(CouncilSummaryRiskHandleDO queryDO)
																						throws DataAccessException {
		if (queryDO == null) {
			throw new IllegalArgumentException("CouncilSummaryRiskHandleDO can`t by null");
		}
		
		Map paramMap = new HashMap<String, Object>();
		paramMap.put("summaryId", queryDO.getSummaryId());
		paramMap.put("councilId", queryDO.getCouncilId());
		paramMap.put("customerId", queryDO.getCustomerId());
		paramMap.put("customerName", queryDO.getCustomerName());
		paramMap.put("projectCode", queryDO.getProjectCode());
		paramMap.put("projectName", queryDO.getProjectName());
		paramMap.put("summaryCode", queryDO.getSummaryCode());
		paramMap.put("busiManagerId", queryDO.getBusiManagerId());
		
		paramMap.put("deptIdList", queryDO.getDeptIdList());
		paramMap.put("loginUserId", queryDO.getLoginUserId());
		
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-BUSI-APPROVAL-RISK-HANDLE-COUNCIL-SUMMARY-COUNT", paramMap);
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
		paramMap.put("draftUserId", exForm.getDraftUserId());
		
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
		paramMap.put("draftUserId", exForm.getDraftUserId());
		
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
		paramMap.put("likeProjectCode", exForm.getLikeProjectCode());
		paramMap.put("customerName", exForm.getCustomerName());
		paramMap.put("isNeedCouncil", exForm.getIsNeedCouncil());
		paramMap.put("isSelForCharge", exForm.getIsSelForCharge());
		paramMap.put("deptIdList", exForm.getDeptIdList());
		paramMap.put("loginUserId", exForm.getLoginUserId());
		paramMap.put("draftUserId", exForm.getDraftUserId());
		
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
		paramMap.put("likeProjectCode", exForm.getLikeProjectCode());
		paramMap.put("customerName", exForm.getCustomerName());
		paramMap.put("isNeedCouncil", exForm.getIsNeedCouncil());
		paramMap.put("isSelForCharge", exForm.getIsSelForCharge());
		paramMap.put("deptIdList", exForm.getDeptIdList());
		paramMap.put("loginUserId", exForm.getLoginUserId());
		paramMap.put("draftUserId", exForm.getDraftUserId());
		
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
	public List<ToReportExpectEventDO> getExpectEvent(Date signedTime) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("signedTime", signedTime);
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
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ToReportGuaranteeStructreDO> getReportGuaranteeStructre(Date startTime, Date endTime) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("startTime", startTime);
		paramMap.put("endTime", endTime);
		List<ToReportGuaranteeStructreDO> list = getSqlMapClientTemplate().queryForList(
			"MS-BUSI-GUARANTEE-STRUCTURE-TO-REPORT", paramMap);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ToReportExpectEventDO> getReportGuaranteeStructreBalanceAmount() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		List<ToReportExpectEventDO> list = getSqlMapClientTemplate().queryForList(
			"MS-BUSI-GUARANTEE-STRUCTURE-BALANCE-AMOUNT", paramMap);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ToReportExpectEventDO> getReportGuaranteeStructreContractAmount(Date startTime,
																				Date endTime) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("startTime", startTime);
		paramMap.put("endTime", endTime);
		List<ToReportExpectEventDO> list = getSqlMapClientTemplate().queryForList(
			"MS-BUSI-GUARANTEE-STRUCTURE-CONTRACT-AMOUNT-TO-REPORT", paramMap);
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
		paramMap.put("formStatus", searchDO.getFormStatus());
		paramMap.put("projectCode", searchDO.getProjectCode());
		paramMap.put("customerName", searchDO.getCustomerName());
		paramMap.put("busiManagerId", searchDO.getManagerId());
		paramMap.put("busiManagerName", searchDO.getManagerName());
		
		paramMap.put("deptIdList", searchDO.getDeptIdList());
		paramMap.put("loginUserId", searchDO.getLoginUserId());
		paramMap.put("draftUserId", searchDO.getDraftUserId());
		
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
		paramMap.put("formStatus", searchDO.getFormStatus());
		paramMap.put("projectCode", searchDO.getProjectCode());
		paramMap.put("customerName", searchDO.getCustomerName());
		paramMap.put("busiManagerId", searchDO.getManagerId());
		paramMap.put("busiManagerName", searchDO.getManagerName());
		
		paramMap.put("deptIdList", searchDO.getDeptIdList());
		paramMap.put("loginUserId", searchDO.getLoginUserId());
		paramMap.put("draftUserId", searchDO.getDraftUserId());
		
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-BUSI-MANAGERB-CHANGE-FORM-COUNT", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<FProjectFinancialDO> queryPurchasingFinancialProject(Map<String, Object> paramMap)
																									throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("MS-BUSI-SEARCH-CAN-BUY-FINANCIAL-PROJECT",
			paramMap);
	}
	
	@Override
	public long queryPurchasingFinancialProjectCount(Map<String, Object> paramMap)
																					throws DataAccessException {
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-BUSI-SEARCH-CAN-BUY-FINANCIAL-PROJECT-COUNT", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectFinancialDO> waitGenWithdrawFinancialProject(String withdrawMonth,
																	String withdrawDay, long limit) {
		if (StringUtil.isBlank(withdrawMonth) || StringUtil.isBlank(withdrawDay)) {
			throw new IllegalArgumentException("withdrawMonth & withdrawDay can`t by null");
		}
		if (limit <= 0)
			limit = 20;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("withdrawMonth", withdrawMonth);
		paramMap.put("withdrawDay", withdrawDay);
		paramMap.put("limit", new Long(limit));
		return getSqlMapClientTemplate().queryForList("MS-BUSI-SEARCH-GEN-WITHDRAW-PROJECT-RECORD",
			paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectGenWithdrawFinancialTransferDO> waitGenWithdrawFinancialTransfer(String withdrawMonth,
																						String withdrawDay,
																						long limit) {
		if (StringUtil.isBlank(withdrawMonth) || StringUtil.isBlank(withdrawDay)) {
			throw new IllegalArgumentException("withdrawMonth & withdrawDay can`t by null");
		}
		if (limit <= 0)
			limit = 20;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("withdrawMonth", withdrawMonth);
		paramMap.put("withdrawDay", withdrawDay);
		paramMap.put("limit", new Long(limit));
		return getSqlMapClientTemplate().queryForList(
			"MS-BUSI-SEARCH-GEN-WITHDRAW-TRANSFER-RECORD", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectChargePayDO> queryProjectChargePayDetail(Map<String, Object> paramMap)
																								throws DataAccessException {
		return getSqlMapClientTemplate()
			.queryForList("MS-BUSI-PROJECT-CHARGE-PAY-DETAIL", paramMap);
	}
	
	@Override
	public long queryProjectChargePayDetailCount(Map<String, Object> paramMap)
																				throws DataAccessException {
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-BUSI-PROJECT-CHARGE-PAY-DETAIL-COUNT", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectChargePayDO> queryProjectChargePayDetailChoose(Map<String, Object> paramMap)
																									throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("MS-BUSI-PROJECT-CHARGE-PAY-DETAIL-CHOOSE",
			paramMap);
	}
	
	@Override
	public long queryProjectChargePayDetailChooseCount(Map<String, Object> paramMap)
																					throws DataAccessException {
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-BUSI-PROJECT-CHARGE-PAY-DETAIL-CHOOSE-COUNT", paramMap);
	}
	
	@Override
	public long queryReleasedRepayCount(Map<String, Object> paramMap) throws DataAccessException {
		return (Long) getSqlMapClientTemplate().queryForObject("MS-BUSI-RELEASED-REPAY-COUNT",
			paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<FCounterGuaranteeRepayDO> queryReleasedRepayList(Map<String, Object> paramMap)
																								throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("MS-BUSI-RELEASED-REPAY-LIST", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CapitalExportDO> capitalExportList(Map<String, Object> paramMap)
																				throws DataAccessException {
		return getSqlMapClientTemplate().queryForList(
			"MS-CAPITAL-APPROPRIATION-PROJECT-EXPORT-LIST", paramMap);
	}
	
	@Override
	public long capitalExportCount(Map<String, Object> paramMap) throws DataAccessException {
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-CAPITAL-APPROPRIATION-PROJECT-EXPORT-COUNT", paramMap);
	}
	
	@Override
	public long investigationUseAssetCount(Map<String, Object> paramMap) throws DataAccessException {
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-BUSI-IS-INVESTIGATION-USE-ASSET-COUNT", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectTransferDetailFormDO> projectTransferDetailForm(Map<String, Object> paramMap)
																									throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("MS-BUSI-PROJECT-TRANSFER-FORM-SEARCH",
			paramMap);
	}
	
	@Override
	public long projectTransferDetailFormCount(Map<String, Object> paramMap)
																			throws DataAccessException {
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-BUSI-PROJECT-TRANSFER-FORM-SEARCH-COUNT", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectDO> canTransferProject(Map<String, Object> paramMap)
																			throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("MS-BUSI-CAN-TRANSFER-PROJECT", paramMap);
	}
	
	@Override
	public long canTransferProjectCount(Map<String, Object> paramMap) throws DataAccessException {
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-BUSI-CAN-TRANSFER-PROJECT-COUNT", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectDO> recoveryProject(Map<String, Object> paramMap) throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("MS-BUSI-SELECT-RECOVERY-PROJECT", paramMap);
	}
	
	@Override
	public long recoveryProjectCount(Map<String, Object> paramMap) throws DataAccessException {
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-BUSI-SELECT-RECOVERY-PROJECT-COUNT", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<FinancialProjectContractFormDO> searchFinancialProjectContractForm(	FinancialProjectContractFormDO queryCondition)
																																	throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("MS-BUSI-FINANCIAL-PROJECT-CONTRACT-SEARCH",
			queryCondition);
	}
	
	@Override
	public long searchFinancialProjectContractFormCount(FinancialProjectContractFormDO queryCondition)
																										throws DataAccessException {
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-BUSI-FINANCIAL-PROJECT-CONTRACT-COUNT", queryCondition);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<FinancialProjectReviewFormDO> searchFinancialProjectReviewForm(	FinancialProjectReviewFormDO queryCondition)
																															throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("MS-BUSI-FINANCIAL-PROJECT-REVIEW-SEARCH",
			queryCondition);
	}
	
	@Override
	public long searchFinancialProjectReviewFormCount(FinancialProjectReviewFormDO queryCondition)
																									throws DataAccessException {
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-BUSI-FINANCIAL-PROJECT-REVIEW-COUNT", queryCondition);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public HashMap<String, T> queryProjectForms(String projectCode) {
		return (HashMap) getSqlMapClientTemplate().queryForObject("MS-BUSI-PROJECT-FORMS",
			projectCode);
	}
}
