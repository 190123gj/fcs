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

import com.born.fcs.pm.dal.dataobject.ConsentIssueNoticeDO;
import com.born.fcs.pm.dal.dataobject.FAfterwardsCheckDO;
import com.born.fcs.pm.dal.dataobject.FCreditConditionConfirmDO;
import com.born.fcs.pm.dal.dataobject.FFileDO;
import com.born.fcs.pm.dal.dataobject.FRefundApplicationDO;
import com.born.fcs.pm.dal.dataobject.ProjectDO;
import com.born.fcs.pm.dal.dataobject.ProjectIssueInformationDO;
import com.born.fcs.pm.dal.dataobject.ProjectRecoveryQueryDO;
import com.born.fcs.pm.daointerface.ExtraDAO;
import com.born.fcs.pm.dataobject.AfterwardsCheckDO;
import com.born.fcs.pm.dataobject.AfterwardsCheckProjectDO;
import com.born.fcs.pm.dataobject.AfterwardsProjectSummaryFormDO;
import com.born.fcs.pm.dataobject.AfterwardsSummaryReleasingAmountDO;
import com.born.fcs.pm.dataobject.CapitalAppropriationApplyFormDO;
import com.born.fcs.pm.dataobject.ChargeNotificationExportDO;
import com.born.fcs.pm.dataobject.ChargeNotificationFormDO;
import com.born.fcs.pm.dataobject.CouncilApplyRiskHandleDO;
import com.born.fcs.pm.dataobject.CouncilVoteProjectDO;
import com.born.fcs.pm.dataobject.CreditRefrerenceApplyFormDO;
import com.born.fcs.pm.dataobject.DbFieldProjectDO;
import com.born.fcs.pm.dataobject.ExpireFormProjectDO;
import com.born.fcs.pm.dataobject.FFileInputListDO;
import com.born.fcs.pm.dataobject.FileExtensionFormDO;
import com.born.fcs.pm.dataobject.FileFormDO;
import com.born.fcs.pm.dataobject.FileInOutFormDO;
import com.born.fcs.pm.dataobject.FileNeedSendMessageProjectDO;
import com.born.fcs.pm.dataobject.FileNotArchiveDO;
import com.born.fcs.pm.dataobject.FileViewDO;
import com.born.fcs.pm.dataobject.FinancialProjectRedeemFormDO;
import com.born.fcs.pm.dataobject.FinancialProjectSetupFormDO;
import com.born.fcs.pm.dataobject.FinancialProjectTransferFormDO;
import com.born.fcs.pm.dataobject.FormChangeApplySearchDO;
import com.born.fcs.pm.dataobject.FormProjectDO;
import com.born.fcs.pm.dataobject.InvestigationDO;
import com.born.fcs.pm.dataobject.LoanUseApplyFormDO;
import com.born.fcs.pm.dataobject.ProjectContractFormDO;
import com.born.fcs.pm.dataobject.ProjectCreditConditionFormDO;
import com.born.fcs.pm.dataobject.ReCouncilApplyFormDO;
import com.born.fcs.pm.dataobject.ReleaseApplyDO;
import com.born.fcs.pm.dataobject.RiskLevelDO;
import com.born.fcs.pm.dataobject.RiskWarningDO;
import com.born.fcs.pm.dataobject.SetupFormProjectDO;
import com.born.fcs.pm.dataobject.SpecialPaperResultDO;
import com.born.fcs.pm.dataobject.StampApplyFormProjeactDO;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.MiscUtil;
import com.yjf.common.lang.util.StringUtil;

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
	
	/**
	 * @param name
	 * @return
	 * @throws DataAccessException
	 * @see com.yjf.paycore.dal.daointerface.ExtraDAO#getNextSeq(java.lang.String)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public long insertDateSeq(String name, Date date) throws DataAccessException {
		
		if (StringUtil.isBlank(name)) {
			throw new IllegalArgumentException("Can't insert a null data object into db.");
		}
		
		Map paramMap = new HashMap<String, Object>();
		paramMap.put("name", name);
		paramMap.put("seqDate", DateUtil.formatDay(date));
		paramMap.put("rawAddTime", date);
		return (Long) getSqlMapClientTemplate().insert("MS-EXTRA-SYS-DATE-SEQ-INSERT", paramMap);
	}
	
	/**
	 * @param name
	 * @return
	 * @throws DataAccessException
	 * @see com.yjf.paycore.dal.daointerface.ExtraDAO#getNextSeq(java.lang.String)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public long insertYearSeq(String name, Date date) throws DataAccessException {
		
		if (StringUtil.isBlank(name)) {
			throw new IllegalArgumentException("Can't insert a null data object into db.");
		}
		
		Map paramMap = new HashMap<String, Object>();
		paramMap.put("name", name);
		paramMap.put("seqDate", DateUtil.formatYear(date));
		paramMap.put("rawAddTime", date);
		return (Long) getSqlMapClientTemplate().insert("MS-EXTRA-SYS-DATE-SEQ-INSERT", paramMap);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public long getNextDateSeq(String name, Date date, long cacheNumber) throws DataAccessException {
		
		if (StringUtil.isBlank(name)) {
			throw new IllegalArgumentException("Can't update a null data object into db.");
		}
		if (cacheNumber <= 0)
			cacheNumber = 1;
		Map paramMap = new HashMap<String, Object>();
		paramMap.put("name", name);
		paramMap.put("seqDate", DateUtil.formatDay(date));
		paramMap.put("cacheNumber", cacheNumber);
		return (Long) getSqlMapClientTemplate().insert("MS-EXTRA-SYS-DATE-SEQ-UPDATE", paramMap);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public long getNextYearSeq(String name, Date date, long cacheNumber) throws DataAccessException {
		
		if (StringUtil.isBlank(name)) {
			throw new IllegalArgumentException("Can't update a null data object into db.");
		}
		if (cacheNumber <= 0)
			cacheNumber = 1;
		Map paramMap = new HashMap<String, Object>();
		paramMap.put("name", name);
		paramMap.put("seqDate", DateUtil.formatYear(date));
		paramMap.put("cacheNumber", cacheNumber);
		return (Long) getSqlMapClientTemplate().insert("MS-EXTRA-SYS-DATE-SEQ-UPDATE", paramMap);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public boolean selectDateSeq(String name, Date date) throws DataAccessException {
		
		if (StringUtil.isBlank(name)) {
			throw new IllegalArgumentException("Can't select a null data object into db.");
		}
		
		Map paramMap = new HashMap<String, Object>();
		paramMap.put("name", name);
		paramMap.put("seqDate", DateUtil.formatDay(date));
		Long maxNumber = (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-SYS-DATE-SEQ-SELECT", paramMap);
		if (maxNumber == null || maxNumber <= 0) {
			return false;
		}
		return true;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public boolean selectYearSeq(String name, Date date) throws DataAccessException {
		
		if (StringUtil.isBlank(name)) {
			throw new IllegalArgumentException("Can't select a null data object into db.");
		}
		
		Map paramMap = new HashMap<String, Object>();
		paramMap.put("name", name);
		paramMap.put("seqDate", DateUtil.formatYear(date));
		Long maxNumber = (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-SYS-DATE-SEQ-SELECT", paramMap);
		if (maxNumber == null || maxNumber <= 0) {
			return false;
		}
		return true;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Long selectYearSeqNum(String name, Date date) throws DataAccessException {
		
		if (StringUtil.isBlank(name)) {
			throw new IllegalArgumentException("Can't select a null data object into db.");
		}
		
		Map paramMap = new HashMap<String, Object>();
		paramMap.put("name", name);
		paramMap.put("seqDate", DateUtil.formatYear(date));
		Long maxNumber = (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-SYS-DATE-SEQ-SELECT", paramMap);
		if (maxNumber == null || maxNumber <= 0) {
			return 0L;
		}
		return maxNumber;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public boolean selectYearMonthSeq(String name, Date date) throws DataAccessException {
		
		if (StringUtil.isBlank(name)) {
			throw new IllegalArgumentException("Can't select a null data object into db.");
		}
		
		Map paramMap = new HashMap<String, Object>();
		paramMap.put("name", name);
		paramMap.put("seqDate", DateUtil.simpleFormatYM(date));
		Long maxNumber = (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-SYS-DATE-SEQ-SELECT", paramMap);
		if (maxNumber == null || maxNumber <= 0) {
			return false;
		}
		return true;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Long selectYearMonthSeqNum(String name, Date date) throws DataAccessException {
		
		if (StringUtil.isBlank(name)) {
			throw new IllegalArgumentException("Can't select a null data object into db.");
		}
		
		Map paramMap = new HashMap<String, Object>();
		paramMap.put("name", name);
		paramMap.put("seqDate", DateUtil.simpleFormatYM(date));
		Long maxNumber = (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-SYS-DATE-SEQ-SELECT", paramMap);
		if (maxNumber == null || maxNumber <= 0) {
			return 0L;
		}
		return maxNumber;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<FormProjectDO> searchSetupForm(SetupFormProjectDO setupForm, long limitStart,
												long pageSize, Date submitTimeStart,
												Date submitTimeEnd, List<Long> deptIdList,
												List<String> busiTypeList, String sortCol,
												String sortOrder) throws DataAccessException {
		
		if (setupForm == null) {
			throw new IllegalArgumentException("setupForm can`t by null");
		}
		
		Map paramMap = new HashMap<String, Object>();
		
		paramMap.put("limitStart", limitStart);
		paramMap.put("pageSize", pageSize);
		paramMap.put("sortOrder", sortOrder);
		paramMap.put("sortCol", sortCol);
		
		paramMap.put("formId", setupForm.getFormId());
		paramMap.put("formStatus", setupForm.getFormStatus());
		paramMap.put("projectId", setupForm.getProjectId());
		paramMap.put("customerId", setupForm.getCustomerId());
		paramMap.put("busiManagerId", setupForm.getBusiManagerId());
		paramMap.put("busiManagerbId", setupForm.getBusiManagerbId());
		paramMap.put("projectCode", setupForm.getProjectCode());
		paramMap.put("likeProjectCode", setupForm.getLikeProjectCode());
		paramMap.put("busiType", setupForm.getBusiType());
		paramMap.put("busiTypeName", setupForm.getBusiTypeName());
		paramMap.put("projectName", setupForm.getProjectName());
		paramMap.put("customerName", setupForm.getCustomerName());
		paramMap.put("busiManagerName", setupForm.getBusiManagerName());
		paramMap.put("busiManagerbName", setupForm.getBusiManagerbName());
		paramMap.put("submitTimeStart", submitTimeStart);
		paramMap.put("submitTimeEnd", submitTimeEnd);
		
		paramMap.put("deptIdList", deptIdList);
		paramMap.put("busiTypeList", busiTypeList);
		paramMap.put("loginUserId", setupForm.getLoginUserId());
		paramMap.put("draftUserId", setupForm.getDraftUserId());
		List<FormProjectDO> list = getSqlMapClientTemplate().queryForList(
			"MS-EXTRA-FORM-F-PROJECT-SEARCH", paramMap);
		
		return list;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public long searchSetupFormCount(SetupFormProjectDO setupForm, Date submitTimeStart,
										Date submitTimeEnd, List<Long> deptIdList,
										List<String> busiTypeList) throws DataAccessException {
		
		if (setupForm == null) {
			throw new IllegalArgumentException("setupForm can`t by null");
		}
		
		Map paramMap = new HashMap<String, Object>();
		paramMap.put("formId", setupForm.getFormId());
		paramMap.put("formStatus", setupForm.getFormStatus());
		paramMap.put("projectId", setupForm.getProjectId());
		paramMap.put("customerId", setupForm.getCustomerId());
		paramMap.put("busiManagerId", setupForm.getBusiManagerId());
		paramMap.put("busiManagerbId", setupForm.getBusiManagerbId());
		paramMap.put("projectCode", setupForm.getProjectCode());
		paramMap.put("likeProjectCode", setupForm.getLikeProjectCode());
		paramMap.put("busiType", setupForm.getBusiType());
		paramMap.put("busiTypeName", setupForm.getBusiTypeName());
		paramMap.put("projectName", setupForm.getProjectName());
		paramMap.put("customerName", setupForm.getCustomerName());
		paramMap.put("busiManagerName", setupForm.getBusiManagerName());
		paramMap.put("busiManagerbName", setupForm.getBusiManagerbName());
		paramMap.put("submitTimeStart", submitTimeStart);
		paramMap.put("submitTimeEnd", submitTimeEnd);
		paramMap.put("deptIdList", deptIdList);
		paramMap.put("busiTypeList", busiTypeList);
		paramMap.put("loginUserId", setupForm.getLoginUserId());
		paramMap.put("draftUserId", setupForm.getDraftUserId());
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-FORM-F-PROJECT-SEARCH-COUNT", paramMap);
	}
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public List<FormProjectDO> searchRiskReviewProject(Map<String, Object> paramMap)
																					throws DataAccessException {
		
		List<FormProjectDO> list = getSqlMapClientTemplate().queryForList(
			"MS-EXTRA-FORM-PROJECT-RISK-REPORT-SEARCH", paramMap);
		return list;
	}
	
	@Override
	public long searchRiskReviewProjectCount(Map<String, Object> paramMap)
																			throws DataAccessException {
		
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-FORM-PROJECT-RISK-REPORT-COUNT", paramMap);
	}
	
	@Override
	public long searchInvestigationCount(Map<String, Object> paramMap) throws DataAccessException {
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-FORM-PROJECT-INVESTIGATION-COUNT", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<InvestigationDO> searchInvestigation(Map<String, Object> paramMap)
																					throws DataAccessException {
		List<InvestigationDO> list = getSqlMapClientTemplate().queryForList(
			"MS-EXTRA-FORM-PROJECT-INVESTIGATION-SEARCH", paramMap);
		
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CouncilVoteProjectDO> searchCouncilVoteProject(	CouncilVoteProjectDO councilVoteProjectDO,
																long limitStart, long pageSize,
																String sortCol, String sortOrder)
																									throws DataAccessException {
		if (councilVoteProjectDO == null) {
			throw new IllegalArgumentException("councilVoteProjectDO can`t by null");
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("limitStart", limitStart);
		paramMap.put("pageSize", pageSize);
		paramMap.put("sortOrder", sortOrder);
		paramMap.put("sortCol", sortCol);
		paramMap.put("customerId", councilVoteProjectDO.getCustomerId());
		paramMap.put("busiManagerId", councilVoteProjectDO.getBusiManagerId());
		paramMap.put("busiManagerbId", councilVoteProjectDO.getBusiManagerbId());
		paramMap.put("projectCode", councilVoteProjectDO.getProjectCode());
		paramMap.put("busiType", councilVoteProjectDO.getBusiType());
		paramMap.put("projectName", councilVoteProjectDO.getProjectName());
		paramMap.put("customerName", councilVoteProjectDO.getCustomerName());
		paramMap.put("busiManagerName", councilVoteProjectDO.getBusiManagerName());
		paramMap.put("busiManagerbName", councilVoteProjectDO.getBusiManagerbName());
		paramMap.put("id", councilVoteProjectDO.getId());
		paramMap.put("councilId", councilVoteProjectDO.getCouncilId());
		paramMap.put("councilCode", councilVoteProjectDO.getCouncilCode());
		paramMap.put("judgeId", councilVoteProjectDO.getJudgeId());
		List<CouncilVoteProjectDO> list = getSqlMapClientTemplate().queryForList(
			"MS-EXTRA-COUNCIL-VOTE-PROJECT-SEARCH", paramMap);
		
		return list;
	}
	
	@Override
	public long searchCouncilVoteProjectCount(CouncilVoteProjectDO councilVoteProjectDO)
																						throws DataAccessException {
		if (councilVoteProjectDO == null) {
			throw new IllegalArgumentException("councilVoteProjectDO can`t by null");
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("customerId", councilVoteProjectDO.getCustomerId());
		paramMap.put("busiManagerId", councilVoteProjectDO.getBusiManagerId());
		paramMap.put("busiManagerbId", councilVoteProjectDO.getBusiManagerbId());
		paramMap.put("projectCode", councilVoteProjectDO.getProjectCode());
		paramMap.put("busiType", councilVoteProjectDO.getBusiType());
		paramMap.put("projectName", councilVoteProjectDO.getProjectName());
		paramMap.put("customerName", councilVoteProjectDO.getCustomerName());
		paramMap.put("busiManagerName", councilVoteProjectDO.getBusiManagerName());
		paramMap.put("busiManagerbName", councilVoteProjectDO.getBusiManagerbName());
		paramMap.put("id", councilVoteProjectDO.getId());
		paramMap.put("councilId", councilVoteProjectDO.getCouncilId());
		paramMap.put("councilCode", councilVoteProjectDO.getCouncilCode());
		paramMap.put("judgeId", councilVoteProjectDO.getJudgeId());
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-COUNCIL-VOTE-PROJECT-SEARCH-COUNT", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StampApplyFormProjeactDO> searchStampApplyFormProjeact(	StampApplyFormProjeactDO stampApplyFormProjeactDO,
																		long limitStart,
																		long pageSize,
																		Date applyTimeStart,
																		Date applyTimeEnd,
																		String sortCol,
																		String sortOrder)
																							throws DataAccessException {
		if (stampApplyFormProjeactDO == null) {
			throw new IllegalArgumentException("stampApplyFormProjeactDO can`t by null");
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("limitStart", limitStart);
		paramMap.put("pageSize", pageSize);
		paramMap.put("sortOrder", sortOrder);
		paramMap.put("sortCol", sortCol);
		paramMap.put("applyCode", stampApplyFormProjeactDO.getApplyCode());
		paramMap.put("customerName", stampApplyFormProjeactDO.getCustomerName());
		paramMap.put("fileCode", stampApplyFormProjeactDO.getFileCode());
		paramMap.put("projectCode", stampApplyFormProjeactDO.getProjectCode());
		paramMap.put("fileName", stampApplyFormProjeactDO.getFileName());
		paramMap.put("status", stampApplyFormProjeactDO.getFormStatus());
		paramMap.put("formCode", stampApplyFormProjeactDO.getFormCode());
		paramMap.put("submitStatus", stampApplyFormProjeactDO.getSubmitStatus());
		paramMap.put("busiType", stampApplyFormProjeactDO.getBusiType());
		paramMap.put("applyTimeStart", applyTimeStart);
		paramMap.put("applyTimeEnd", applyTimeEnd);
		paramMap.put("loginUserId", stampApplyFormProjeactDO.getLoginUserId());
		paramMap.put("deptIdList", stampApplyFormProjeactDO.getDeptIdList());
		paramMap.put("draftUserId", stampApplyFormProjeactDO.getDraftUserId());
		
		paramMap.put("isNeedChannelContract", stampApplyFormProjeactDO.getIsNeedChannelContract());
		List<StampApplyFormProjeactDO> list = getSqlMapClientTemplate().queryForList(
			"MS-EXTRA-STAMP-APPLY-SEARCH", paramMap);
		
		return list;
	}
	
	@Override
	public long searchStampApplyFormProjeactCount(	StampApplyFormProjeactDO stampApplyFormProjeactDO,
													Date applyTimeStart, Date applyTimeEnd)
																							throws DataAccessException {
		if (stampApplyFormProjeactDO == null) {
			throw new IllegalArgumentException("stampApplyFormProjeactDO can`t by null");
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("customerName", stampApplyFormProjeactDO.getCustomerName());
		paramMap.put("fileCode", stampApplyFormProjeactDO.getFileCode());
		paramMap.put("applyCode", stampApplyFormProjeactDO.getApplyCode());
		paramMap.put("fileName", stampApplyFormProjeactDO.getFileName());
		paramMap.put("status", stampApplyFormProjeactDO.getFormStatus());
		paramMap.put("formCode", stampApplyFormProjeactDO.getFormCode());
		paramMap.put("submitStatus", stampApplyFormProjeactDO.getSubmitStatus());
		paramMap.put("busiType", stampApplyFormProjeactDO.getBusiType());
		paramMap.put("projectCode", stampApplyFormProjeactDO.getProjectCode());
		paramMap.put("applyTimeStart", applyTimeStart);
		paramMap.put("applyTimeEnd", applyTimeEnd);
		paramMap.put("loginUserId", stampApplyFormProjeactDO.getLoginUserId());
		paramMap.put("draftUserId", stampApplyFormProjeactDO.getDraftUserId());
		paramMap.put("deptIdList", stampApplyFormProjeactDO.getDeptIdList());
		paramMap.put("isNeedChannelContract", stampApplyFormProjeactDO.getIsNeedChannelContract());
		return (Long) getSqlMapClientTemplate().queryForObject("MS-EXTRA-STAMP-APPLY-SEARCH-COUNT",
			paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StampApplyFormProjeactDO> searchStampApplyList(	StampApplyFormProjeactDO stampApplyFormProjeactDO,
																long limitStart, long pageSize,
																Date applyTimeStart,
																Date applyTimeEnd, String sortCol,
																String sortOrder)
																					throws DataAccessException {
		if (stampApplyFormProjeactDO == null) {
			throw new IllegalArgumentException("stampApplyFormProjeactDO can`t by null");
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("limitStart", limitStart);
		paramMap.put("pageSize", pageSize);
		paramMap.put("sortOrder", sortOrder);
		paramMap.put("sortCol", sortCol);
		paramMap.put("customerName", stampApplyFormProjeactDO.getCustomerName());
		paramMap.put("applyCode", stampApplyFormProjeactDO.getApplyCode());
		paramMap.put("fileCode", stampApplyFormProjeactDO.getFileCode());
		paramMap.put("projectCode", stampApplyFormProjeactDO.getProjectCode());
		paramMap.put("status", stampApplyFormProjeactDO.getFormStatus());
		paramMap.put("formCode", stampApplyFormProjeactDO.getFormCode());
		paramMap.put("submitStatus", stampApplyFormProjeactDO.getSubmitStatus());
		paramMap.put("busiType", stampApplyFormProjeactDO.getBusiType());
		paramMap.put("applyTimeStart", applyTimeStart);
		paramMap.put("applyTimeEnd", applyTimeEnd);
		paramMap.put("loginUserId", stampApplyFormProjeactDO.getLoginUserId());
		paramMap.put("draftUserId", stampApplyFormProjeactDO.getDraftUserId());
		paramMap.put("deptIdList", stampApplyFormProjeactDO.getDeptIdList());
		paramMap.put("isNeedChannelContract", stampApplyFormProjeactDO.getIsNeedChannelContract());
		paramMap.put("fileName", stampApplyFormProjeactDO.getFileName());
		paramMap.put("contractCode", stampApplyFormProjeactDO.getContractCode());
		List<StampApplyFormProjeactDO> list = getSqlMapClientTemplate().queryForList(
			"MS-EXTRA-STAMP-APPLY-FORM-SEARCH", paramMap);
		
		return list;
	}
	
	@Override
	public long searchStampApplyListCount(StampApplyFormProjeactDO stampApplyFormProjeactDO,
											Date applyTimeStart, Date applyTimeEnd)
																					throws DataAccessException {
		if (stampApplyFormProjeactDO == null) {
			throw new IllegalArgumentException("stampApplyFormProjeactDO can`t by null");
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("customerName", stampApplyFormProjeactDO.getCustomerName());
		paramMap.put("applyCode", stampApplyFormProjeactDO.getApplyCode());
		paramMap.put("fileCode", stampApplyFormProjeactDO.getFileCode());
		paramMap.put("status", stampApplyFormProjeactDO.getFormStatus());
		paramMap.put("formCode", stampApplyFormProjeactDO.getFormCode());
		paramMap.put("submitStatus", stampApplyFormProjeactDO.getSubmitStatus());
		paramMap.put("projectCode", stampApplyFormProjeactDO.getProjectCode());
		paramMap.put("busiType", stampApplyFormProjeactDO.getBusiType());
		paramMap.put("applyTimeStart", applyTimeStart);
		paramMap.put("applyTimeEnd", applyTimeEnd);
		paramMap.put("loginUserId", stampApplyFormProjeactDO.getLoginUserId());
		paramMap.put("draftUserId", stampApplyFormProjeactDO.getDraftUserId());
		paramMap.put("deptIdList", stampApplyFormProjeactDO.getDeptIdList());
		paramMap.put("isNeedChannelContract", stampApplyFormProjeactDO.getIsNeedChannelContract());
		paramMap.put("fileName", stampApplyFormProjeactDO.getFileName());
		paramMap.put("contractCode", stampApplyFormProjeactDO.getContractCode());
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-STAMP-APPLY-FORM-SEARCH-COUNT", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StampApplyFormProjeactDO> searchStampLedger(StampApplyFormProjeactDO stampApplyFormProjeactDO,
															long limitStart, long pageSize,
															Date applyTimeStart, Date applyTimeEnd,
															String sortCol, String sortOrder)
																								throws DataAccessException {
		if (stampApplyFormProjeactDO == null) {
			throw new IllegalArgumentException("stampApplyFormProjeactDO can`t by null");
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("limitStart", limitStart);
		paramMap.put("pageSize", pageSize);
		paramMap.put("sortOrder", sortOrder);
		paramMap.put("sortCol", sortCol);
		paramMap.put("fileName", stampApplyFormProjeactDO.getFileName());
		paramMap.put("customerName", stampApplyFormProjeactDO.getCustomerName());
		paramMap.put("applyCode", stampApplyFormProjeactDO.getApplyCode());
		paramMap.put("fileCode", stampApplyFormProjeactDO.getFileCode());
		paramMap.put("projectCode", stampApplyFormProjeactDO.getProjectCode());
		paramMap.put("status", stampApplyFormProjeactDO.getFormStatus());
		paramMap.put("formCode", stampApplyFormProjeactDO.getFormCode());
		paramMap.put("submitStatus", stampApplyFormProjeactDO.getSubmitStatus());
		paramMap.put("busiType", stampApplyFormProjeactDO.getBusiType());
		paramMap.put("applyTimeStart", applyTimeStart);
		paramMap.put("applyTimeEnd", applyTimeEnd);
		paramMap.put("loginUserId", stampApplyFormProjeactDO.getLoginUserId());
		paramMap.put("draftUserId", stampApplyFormProjeactDO.getDraftUserId());
		paramMap.put("deptIdList", stampApplyFormProjeactDO.getDeptIdList());
		paramMap.put("isNeedChannelContract", stampApplyFormProjeactDO.getIsNeedChannelContract());
		List<StampApplyFormProjeactDO> list = getSqlMapClientTemplate().queryForList(
			"MS-EXTRA-STAMP-LEDGER-SEARCH", paramMap);
		
		return list;
	}
	
	@Override
	public long searchStampLedgerCount(StampApplyFormProjeactDO stampApplyFormProjeactDO,
										Date applyTimeStart, Date applyTimeEnd)
																				throws DataAccessException {
		if (stampApplyFormProjeactDO == null) {
			throw new IllegalArgumentException("stampApplyFormProjeactDO can`t by null");
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("customerName", stampApplyFormProjeactDO.getCustomerName());
		paramMap.put("fileCode", stampApplyFormProjeactDO.getFileCode());
		paramMap.put("applyCode", stampApplyFormProjeactDO.getApplyCode());
		paramMap.put("fileName", stampApplyFormProjeactDO.getFileName());
		paramMap.put("status", stampApplyFormProjeactDO.getFormStatus());
		paramMap.put("formCode", stampApplyFormProjeactDO.getFormCode());
		paramMap.put("submitStatus", stampApplyFormProjeactDO.getSubmitStatus());
		paramMap.put("busiType", stampApplyFormProjeactDO.getBusiType());
		paramMap.put("applyTimeStart", applyTimeStart);
		paramMap.put("applyTimeEnd", applyTimeEnd);
		paramMap.put("loginUserId", stampApplyFormProjeactDO.getLoginUserId());
		paramMap.put("draftUserId", stampApplyFormProjeactDO.getDraftUserId());
		paramMap.put("deptIdList", stampApplyFormProjeactDO.getDeptIdList());
		paramMap.put("isNeedChannelContract", stampApplyFormProjeactDO.getIsNeedChannelContract());
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-STAMP-LEDGER-SEARCH-COUNT", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StampApplyFormProjeactDO> searchStampBasicDataApplyForm(StampApplyFormProjeactDO stampApplyFormProjeactDO,
																		long limitStart,
																		long pageSize,
																		Date applyTimeStart,
																		Date applyTimeEnd,
																		String sortCol,
																		String sortOrder)
																							throws DataAccessException {
		if (stampApplyFormProjeactDO == null) {
			throw new IllegalArgumentException("stampApplyFormProjeactDO can`t by null");
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("limitStart", limitStart);
		paramMap.put("pageSize", pageSize);
		paramMap.put("sortOrder", sortOrder);
		paramMap.put("sortCol", sortCol);
		paramMap.put("applyCode", stampApplyFormProjeactDO.getApplyCode());
		paramMap.put("status", stampApplyFormProjeactDO.getFormStatus());
		paramMap.put("applyTimeStart", applyTimeStart);
		paramMap.put("applyTimeEnd", applyTimeEnd);
		paramMap.put("loginUserId", stampApplyFormProjeactDO.getLoginUserId());
		paramMap.put("draftUserId", stampApplyFormProjeactDO.getDraftUserId());
		paramMap.put("deptIdList", stampApplyFormProjeactDO.getDeptIdList());
		paramMap.put("fileName", stampApplyFormProjeactDO.getFileName());
		paramMap.put("formUserName", stampApplyFormProjeactDO.getFormUserName());
		List<StampApplyFormProjeactDO> list = getSqlMapClientTemplate().queryForList(
			"MS-EXTRA-STAMP-BASIC-DATA-APPLY-FORM-SEARCH", paramMap);
		
		return list;
	}
	
	@Override
	public long searchStampBasicDataApplyFormCount(	StampApplyFormProjeactDO stampApplyFormProjeactDO,
													Date applyTimeStart, Date applyTimeEnd)
																							throws DataAccessException {
		if (stampApplyFormProjeactDO == null) {
			throw new IllegalArgumentException("stampApplyFormProjeactDO can`t by null");
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("applyCode", stampApplyFormProjeactDO.getApplyCode());
		paramMap.put("status", stampApplyFormProjeactDO.getFormStatus());
		paramMap.put("applyTimeStart", applyTimeStart);
		paramMap.put("applyTimeEnd", applyTimeEnd);
		paramMap.put("loginUserId", stampApplyFormProjeactDO.getLoginUserId());
		paramMap.put("draftUserId", stampApplyFormProjeactDO.getDraftUserId());
		paramMap.put("deptIdList", stampApplyFormProjeactDO.getDeptIdList());
		paramMap.put("fileName", stampApplyFormProjeactDO.getFileName());
		paramMap.put("formUserName", stampApplyFormProjeactDO.getFormUserName());
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-STAMP-BASIC-DATA-APPLY-FORM-SEARCH-COUNT", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<LoanUseApplyFormDO> searchLoanUseApplyForm(LoanUseApplyFormDO queryCondition)
																								throws DataAccessException {
		if (queryCondition == null) {
			throw new IllegalArgumentException("LoanUseApplyFormProjectDO can`t by null");
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("limitStart", queryCondition.getLimitStart());
		paramMap.put("pageSize", queryCondition.getPageSize());
		paramMap.put("sortOrder", queryCondition.getSortOrder());
		paramMap.put("sortCol", queryCondition.getSortCol());
		paramMap.put("formId", queryCondition.getFormId());
		paramMap.put("applyId", queryCondition.getApplyId());
		paramMap.put("customerId", queryCondition.getCustomerId());
		paramMap.put("customerName", queryCondition.getCustomerName());
		paramMap.put("projectCode", queryCondition.getProjectCode());
		paramMap.put("projectName", queryCondition.getProjectName());
		paramMap.put("formStatus", queryCondition.getFormStatus());
		paramMap.put("busiType", queryCondition.getBusiType());
		paramMap.put("loginUserId", queryCondition.getLoginUserId());
		paramMap.put("draftUserId", queryCondition.getDraftUserId());
		paramMap.put("deptIdList", queryCondition.getDeptIdList());
		paramMap.put("statusList", queryCondition.getStatusList());
		paramMap.put("hasReceipt", queryCondition.getHasReceipt());
		List<LoanUseApplyFormDO> list = getSqlMapClientTemplate().queryForList(
			"MS-EXTRA-LOAN-USE-APPLY-FORM-SEARCH", paramMap);
		return list;
	}
	
	@Override
	public long searchLoanUseApplyFormCount(LoanUseApplyFormDO queryCondition)
																				throws DataAccessException {
		
		if (queryCondition == null) {
			throw new IllegalArgumentException("LoanUseApplyFormProjectDO can`t by null");
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("formId", queryCondition.getFormId());
		paramMap.put("applyId", queryCondition.getApplyId());
		paramMap.put("customerId", queryCondition.getCustomerId());
		paramMap.put("customerName", queryCondition.getCustomerName());
		paramMap.put("projectCode", queryCondition.getProjectCode());
		paramMap.put("projectName", queryCondition.getProjectName());
		paramMap.put("formStatus", queryCondition.getFormStatus());
		paramMap.put("busiType", queryCondition.getBusiType());
		paramMap.put("loginUserId", queryCondition.getLoginUserId());
		paramMap.put("draftUserId", queryCondition.getDraftUserId());
		
		paramMap.put("deptIdList", queryCondition.getDeptIdList());
		paramMap.put("statusList", queryCondition.getStatusList());
		paramMap.put("hasReceipt", queryCondition.getHasReceipt());
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-LOAN-USE-APPLY-FORM-SEARCH-COUNT", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ReCouncilApplyFormDO> searchRecouncilApplyForm(ReCouncilApplyFormDO queryCondition)
																									throws DataAccessException {
		if (queryCondition == null) {
			throw new IllegalArgumentException("ReCouncilApplyFormDO can`t by null");
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("limitStart", queryCondition.getLimitStart());
		paramMap.put("pageSize", queryCondition.getPageSize());
		paramMap.put("sortOrder", queryCondition.getSortOrder());
		paramMap.put("sortCol", queryCondition.getSortCol());
		paramMap.put("formId", queryCondition.getFormId());
		paramMap.put("applyId", queryCondition.getApplyId());
		paramMap.put("customerId", queryCondition.getCustomerId());
		paramMap.put("customerName", queryCondition.getCustomerName());
		paramMap.put("projectCode", queryCondition.getProjectCode());
		paramMap.put("likeProjectCode", queryCondition.getLikeProjectCode());
		paramMap.put("projectName", queryCondition.getProjectName());
		paramMap.put("formStatus", queryCondition.getFormStatus());
		paramMap.put("busiType", queryCondition.getBusiType());
		paramMap.put("loginUserId", queryCondition.getLoginUserId());
		paramMap.put("draftUserId", queryCondition.getDraftUserId());
		
		paramMap.put("deptIdList", queryCondition.getDeptIdList());
		paramMap.put("statusList", queryCondition.getStatusList());
		List<ReCouncilApplyFormDO> list = getSqlMapClientTemplate().queryForList(
			"MS-EXTRA-RECOUNCIL-APPLY-FORM-SEARCH", paramMap);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectDO> selectLoanUseApplyProject(ProjectDO queryCondition,
														String projectCodeOrName, long loginUserId,
														long draftUserId, List<Long> detpList,
														long limitStart, long pageSize,
														String sortCol, String sortOrder)
																							throws DataAccessException {
		
		if (queryCondition == null) {
			throw new IllegalArgumentException("ProjectDO can`t by null");
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("limitStart", limitStart);
		paramMap.put("pageSize", pageSize);
		paramMap.put("sortOrder", sortOrder);
		paramMap.put("sortCol", sortCol);
		paramMap.put("customerName", queryCondition.getCustomerName());
		paramMap.put("projectCode", queryCondition.getProjectCode());
		paramMap.put("projectName", queryCondition.getProjectName());
		paramMap.put("projectCodeOrName", projectCodeOrName);
		paramMap.put("loginUserId", loginUserId);
		paramMap.put("draftUserId", draftUserId);
		paramMap.put("deptIdList", detpList);
		paramMap.put("busiManagerId", queryCondition.getBusiManagerId());
		paramMap.put("busiManagerbId", queryCondition.getBusiManagerbId());
		List<ProjectDO> list = getSqlMapClientTemplate().queryForList(
			"MS-EXTRA-LOAN-USE-APPLY-SELECT-PROJECT", paramMap);
		return list;
	}
	
	@Override
	public long selectLoanUseApplyProjectCount(ProjectDO queryCondition, String projectCodeOrName,
												long loginUserId, long draftUserId,
												List<Long> detpList) throws DataAccessException {
		if (queryCondition == null) {
			throw new IllegalArgumentException("ProjectDO can`t by null");
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("customerName", queryCondition.getCustomerName());
		paramMap.put("projectCode", queryCondition.getProjectCode());
		paramMap.put("projectName", queryCondition.getProjectName());
		paramMap.put("projectCodeOrName", projectCodeOrName);
		paramMap.put("draftUserId", draftUserId);
		paramMap.put("loginUserId", loginUserId);
		paramMap.put("deptIdList", detpList);
		paramMap.put("busiManagerId", queryCondition.getBusiManagerId());
		paramMap.put("busiManagerbId", queryCondition.getBusiManagerbId());
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-LOAN-USE-APPLY-SELECT-PROJECT-COUNT", paramMap);
	}
	
	@Override
	public long searchRecouncilApplyFormCount(ReCouncilApplyFormDO queryCondition)
																					throws DataAccessException {
		if (queryCondition == null) {
			throw new IllegalArgumentException("ReCouncilApplyFormDO can`t by null");
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("formId", queryCondition.getFormId());
		paramMap.put("applyId", queryCondition.getApplyId());
		paramMap.put("customerId", queryCondition.getCustomerId());
		paramMap.put("customerName", queryCondition.getCustomerName());
		paramMap.put("projectCode", queryCondition.getProjectCode());
		paramMap.put("likeProjectCode", queryCondition.getLikeProjectCode());
		paramMap.put("projectName", queryCondition.getProjectName());
		paramMap.put("formStatus", queryCondition.getFormStatus());
		paramMap.put("busiType", queryCondition.getBusiType());
		paramMap.put("loginUserId", queryCondition.getLoginUserId());
		paramMap.put("draftUserId", queryCondition.getDraftUserId());
		paramMap.put("deptIdList", queryCondition.getDeptIdList());
		paramMap.put("statusList", queryCondition.getStatusList());
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-RECOUNCIL-APPLY-FORM-SEARCH-COUNT", paramMap);
	}
	
	@Override
	public long searchRiskWarningCount(Map<String, Object> paramMap) throws DataAccessException {
		return (Long) getSqlMapClientTemplate().queryForObject("MS-EXTRA-FORM-RISK-WARNING-COUNT",
			paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RiskWarningDO> searchRiskWarning(Map<String, Object> paramMap)
																				throws DataAccessException {
		return getSqlMapClientTemplate()
			.queryForList("MS-EXTRA-FORM-RISK-WARNING-SEARCH", paramMap);
	}
	
	@Override
	public long searchCouncilApplyRiskHandleCount(Map<String, Object> paramMap)
																				throws DataAccessException {
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-RISK-HANDLE-PROJECT-COUNT", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CouncilApplyRiskHandleDO> searchCouncilApplyRiskHandle(Map<String, Object> paramMap)
																									throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("MS-EXTRA-RISK-HANDLE-PROJECT-SEARCH",
			paramMap);
	}
	
	@Override
	public long searchInvestigationSelectProjectCount(Map<String, Object> paramMap)
																					throws DataAccessException {
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-INVESTIGATION-SELECT-PROJECT-COUNT", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectDO> searchInvestigationSelectProject(Map<String, Object> paramMap)
																							throws DataAccessException {
		return getSqlMapClientTemplate().queryForList(
			"MS-EXTRA-INVESTIGATION-SELECT-PROJECT-SEARCH", paramMap);
	}
	
	@Override
	public long searchExpireProjectCount(Map<String, Object> paramMap) throws DataAccessException {
		return (Long) getSqlMapClientTemplate().queryForObject("MS-EXTRA-EXPIRE-PROJECT-COUNT",
			paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ExpireFormProjectDO> searchExpireProject(Map<String, Object> paramMap)
																						throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("MS-EXTRA-EXPIRE-PROJECT-SEARCH", paramMap);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<FinancialProjectSetupFormDO> searchFinancialProjectSetupForm(	FinancialProjectSetupFormDO queryCondition)
																															throws DataAccessException {
		if (queryCondition == null) {
			throw new IllegalArgumentException("FinancialProjectSetupFormDO can`t by null");
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("draftUserId", queryCondition.getDraftUserId());
		paramMap.put("loginUserId", queryCondition.getLoginUserId());
		paramMap.put("deptIdList", queryCondition.getDeptIdList());
		paramMap.put("limitStart", queryCondition.getLimitStart());
		paramMap.put("pageSize", queryCondition.getPageSize());
		paramMap.put("sortCol", queryCondition.getSortCol());
		paramMap.put("sortOrder", queryCondition.getSortOrder());
		paramMap.put("busiManagerId", queryCondition.getCreateUserId());
		paramMap.put("deptId", queryCondition.getDeptId());
		paramMap.put("projectCode", queryCondition.getProjectCode());
		paramMap.put("deptName", queryCondition.getDeptName());
		paramMap.put("issueInstitution", queryCondition.getIssueInstitution());
		paramMap.put("productName", queryCondition.getProductName());
		paramMap.put("productId", queryCondition.getProductId());
		paramMap.put("deptCode", queryCondition.getDeptCode());
		paramMap.put("deptPath", queryCondition.getDeptPath());
		paramMap.put("deptPathName", queryCondition.getDeptPathName());
		paramMap.put("formStatus", queryCondition.getFormStatus());
		paramMap.put("chooseForContract", queryCondition.getChooseForContract());
		paramMap.put("formStatusList", queryCondition.getFormStatusList());
		List<FinancialProjectSetupFormDO> list = getSqlMapClientTemplate().queryForList(
			"MS-EXTRA-FINANCIAL-PROJECT-SETUP-SEARCH", paramMap);
		return list;
	}
	
	@Override
	public long searchFinancialProjectSetupFormCount(FinancialProjectSetupFormDO queryCondition)
																								throws DataAccessException {
		
		if (queryCondition == null) {
			throw new IllegalArgumentException("FinancialProjectSetupFormDO can`t by null");
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("loginUserId", queryCondition.getLoginUserId());
		paramMap.put("draftUserId", queryCondition.getDraftUserId());
		paramMap.put("deptIdList", queryCondition.getDeptIdList());
		paramMap.put("busiManagerId", queryCondition.getCreateUserId());
		paramMap.put("projectCode", queryCondition.getProjectCode());
		paramMap.put("issueInstitution", queryCondition.getIssueInstitution());
		paramMap.put("productName", queryCondition.getProductName());
		paramMap.put("productId", queryCondition.getProductId());
		paramMap.put("deptId", queryCondition.getDeptId());
		paramMap.put("deptName", queryCondition.getDeptName());
		paramMap.put("deptCode", queryCondition.getDeptCode());
		paramMap.put("deptPath", queryCondition.getDeptPath());
		paramMap.put("deptPathName", queryCondition.getDeptPathName());
		paramMap.put("formStatus", queryCondition.getFormStatus());
		paramMap.put("chooseForContract", queryCondition.getChooseForContract());
		paramMap.put("formStatusList", queryCondition.getFormStatusList());
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-FINANCIAL-PROJECT-SETUP-SEARCH-COUNT", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<FinancialProjectTransferFormDO> searchFinancialProjectTransferForm(	FinancialProjectTransferFormDO queryCondition)
																																	throws DataAccessException {
		if (queryCondition == null) {
			throw new IllegalArgumentException("FinancialProjectTransferFormDO can`t by null");
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("loginUserId", queryCondition.getLoginUserId());
		paramMap.put("draftUserId", queryCondition.getDraftUserId());
		paramMap.put("deptIdList", queryCondition.getDeptIdList());
		paramMap.put("limitStart", queryCondition.getLimitStart());
		paramMap.put("pageSize", queryCondition.getPageSize());
		paramMap.put("sortOrder", queryCondition.getSortOrder());
		paramMap.put("sortCol", queryCondition.getSortCol());
		paramMap.put("busiManagerId", queryCondition.getCreateUserId());
		paramMap.put("applyId", queryCondition.getApplyId());
		paramMap.put("projectCode", queryCondition.getProjectCode());
		paramMap.put("productName", queryCondition.getProductName());
		paramMap.put("formStatus", queryCondition.getFormStatus());
		paramMap.put("transferTimeStart", queryCondition.getTransferTimeStart());
		paramMap.put("transferTimeEnd", queryCondition.getTransferTimeEnd());
		paramMap.put("formStatusList", queryCondition.getFormStatusList());
		List<FinancialProjectTransferFormDO> list = getSqlMapClientTemplate().queryForList(
			"MS-EXTRA-FINANCIAL-PROJECT-TRANSFER-SEARCH", paramMap);
		
		return list;
	}
	
	@Override
	public long searchFinancialProjectTransferFormCount(FinancialProjectTransferFormDO queryCondition)
																										throws DataAccessException {
		if (queryCondition == null) {
			throw new IllegalArgumentException("FinancialProjectTransferFormDO can`t by null");
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("loginUserId", queryCondition.getLoginUserId());
		paramMap.put("draftUserId", queryCondition.getDraftUserId());
		paramMap.put("deptIdList", queryCondition.getDeptIdList());
		paramMap.put("busiManagerId", queryCondition.getCreateUserId());
		paramMap.put("applyId", queryCondition.getApplyId());
		paramMap.put("projectCode", queryCondition.getProjectCode());
		paramMap.put("productName", queryCondition.getProductName());
		paramMap.put("formStatus", queryCondition.getFormStatus());
		paramMap.put("transferTimeStart", queryCondition.getTransferTimeStart());
		paramMap.put("transferTimeEnd", queryCondition.getTransferTimeEnd());
		paramMap.put("formStatusList", queryCondition.getFormStatusList());
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-FINANCIAL-PROJECT-TRANSFER-SEARCH-COUNT", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<FinancialProjectRedeemFormDO> searchFinancialProjectRedeemForm(	FinancialProjectRedeemFormDO queryCondition)
																															throws DataAccessException {
		if (queryCondition == null) {
			throw new IllegalArgumentException("FinancialProjectRedeemFormDO can`t by null");
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("loginUserId", queryCondition.getLoginUserId());
		paramMap.put("draftUserId", queryCondition.getDraftUserId());
		paramMap.put("deptIdList", queryCondition.getDeptIdList());
		paramMap.put("limitStart", queryCondition.getLimitStart());
		paramMap.put("pageSize", queryCondition.getPageSize());
		paramMap.put("sortOrder", queryCondition.getSortOrder());
		paramMap.put("sortCol", queryCondition.getSortCol());
		paramMap.put("busiManagerId", queryCondition.getCreateUserId());
		paramMap.put("applyId", queryCondition.getApplyId());
		paramMap.put("projectCode", queryCondition.getProjectCode());
		paramMap.put("productName", queryCondition.getProductName());
		paramMap.put("formStatus", queryCondition.getFormStatus());
		paramMap.put("applyTimeStart", queryCondition.getApplyTimeStart());
		paramMap.put("applyTimeEnd", queryCondition.getApplyTimeEnd());
		paramMap.put("redeemTimeStart", queryCondition.getRedeemTimeStart());
		paramMap.put("redeemTimeEnd", queryCondition.getRedeemTimeEnd());
		paramMap.put("formStatusList", queryCondition.getFormStatusList());
		List<FinancialProjectRedeemFormDO> list = getSqlMapClientTemplate().queryForList(
			"MS-EXTRA-FINANCIAL-PROJECT-REDEEM-SEARCH", paramMap);
		
		return list;
	}
	
	@Override
	public long searchFinancialProjectRedeemFormCount(FinancialProjectRedeemFormDO queryCondition)
																									throws DataAccessException {
		if (queryCondition == null) {
			throw new IllegalArgumentException("FinancialProjectRedeemFormDO can`t by null");
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("loginUserId", queryCondition.getLoginUserId());
		paramMap.put("draftUserId", queryCondition.getDraftUserId());
		paramMap.put("deptIdList", queryCondition.getDeptIdList());
		paramMap.put("busiManagerId", queryCondition.getCreateUserId());
		paramMap.put("projectCode", queryCondition.getProjectCode());
		paramMap.put("productName", queryCondition.getProductName());
		paramMap.put("applyId", queryCondition.getApplyId());
		paramMap.put("formStatus", queryCondition.getFormStatus());
		paramMap.put("applyTimeStart", queryCondition.getApplyTimeStart());
		paramMap.put("applyTimeEnd", queryCondition.getApplyTimeEnd());
		paramMap.put("redeemTimeStart", queryCondition.getRedeemTimeStart());
		paramMap.put("redeemTimeEnd", queryCondition.getRedeemTimeEnd());
		paramMap.put("formStatusList", queryCondition.getFormStatusList());
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-FINANCIAL-PROJECT-REDEEM-SEARCH-COUNT", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectContractFormDO> searchProjectContractList(	ProjectContractFormDO projectContractFormDO,
																	long limitStart, long pageSize,
																	Date operateTimeStart,
																	Date operateTimeEnd,
																	String sortCol, String sortOrder)
																										throws DataAccessException {
		if (projectContractFormDO == null) {
			throw new IllegalArgumentException("projectContractFormDO can`t by null");
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("limitStart", limitStart);
		paramMap.put("pageSize", pageSize);
		paramMap.put("sortOrder", sortOrder);
		paramMap.put("sortCol", sortCol);
		
		paramMap.put("contractCode", projectContractFormDO.getContractCode());
		paramMap.put("contractCode2", projectContractFormDO.getContractCode2());
		paramMap.put("contractName", projectContractFormDO.getContractName());
		paramMap.put("projectCode", projectContractFormDO.getProjectCode());
		paramMap.put("projectName", projectContractFormDO.getProjectName());
		paramMap.put("busiType", projectContractFormDO.getBusiType());
		paramMap.put("customerName", projectContractFormDO.getCustomerName());
		paramMap.put("contractStatus", projectContractFormDO.getContractStatus());
		paramMap.put("exceptContractStatus", projectContractFormDO.getExceptContractStatus());
		paramMap.put("isChargeNotification", projectContractFormDO.getIsChargeNotification());
		paramMap.put("likeContractCodeOrNmae", projectContractFormDO.getLikeContractCodeOrName());
		paramMap.put("operateTimeStart", operateTimeStart);
		paramMap.put("operateTimeEnd", operateTimeEnd);
		paramMap.put("chooseProject", projectContractFormDO.getChooseProject());
		paramMap.put("isMain", projectContractFormDO.getIsMain());
		paramMap.put("applyType", projectContractFormDO.getApplyType());
		paramMap.put("letterType", projectContractFormDO.getLetterType());
		
		paramMap.put("loginUserId", projectContractFormDO.getLoginUserId());
		paramMap.put("deptIdList", projectContractFormDO.getDeptIdList());
		paramMap.put("draftUserId", projectContractFormDO.getDraftUserId());
		paramMap.put("remark", projectContractFormDO.getRemark());
		List<ProjectContractFormDO> list = getSqlMapClientTemplate().queryForList(
			"MS-EXTRA-PROJECT-CONTRACT-SEARCH", paramMap);
		
		return list;
	}
	
	@Override
	public long searchProjectContractListCount(ProjectContractFormDO projectContractFormDO,
												Date operateTimeStart, Date operateTimeEnd)
																							throws DataAccessException {
		if (projectContractFormDO == null) {
			throw new IllegalArgumentException("projectContractFormDO can`t by null");
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("contractCode", projectContractFormDO.getContractCode());
		paramMap.put("contractCode2", projectContractFormDO.getContractCode2());
		paramMap.put("contractName", projectContractFormDO.getContractName());
		paramMap.put("projectCode", projectContractFormDO.getProjectCode());
		paramMap.put("projectName", projectContractFormDO.getProjectName());
		paramMap.put("busiType", projectContractFormDO.getBusiType());
		paramMap.put("customerName", projectContractFormDO.getCustomerName());
		paramMap.put("contractStatus", projectContractFormDO.getContractStatus());
		paramMap.put("exceptContractStatus", projectContractFormDO.getExceptContractStatus());
		paramMap.put("isChargeNotification", projectContractFormDO.getIsChargeNotification());
		paramMap.put("operateTimeStart", operateTimeStart);
		paramMap.put("operateTimeEnd", operateTimeEnd);
		paramMap.put("chooseProject", projectContractFormDO.getChooseProject());
		paramMap.put("likeContractCodeOrNmae", projectContractFormDO.getLikeContractCodeOrName());
		paramMap.put("isMain", projectContractFormDO.getIsMain());
		paramMap.put("applyType", projectContractFormDO.getApplyType());
		paramMap.put("letterType", projectContractFormDO.getLetterType());
		
		paramMap.put("loginUserId", projectContractFormDO.getLoginUserId());
		paramMap.put("draftUserId", projectContractFormDO.getDraftUserId());
		paramMap.put("deptIdList", projectContractFormDO.getDeptIdList());
		paramMap.put("remark", projectContractFormDO.getRemark());
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-PROJECT-CONTRACT-SEARCH-COUNT", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectContractFormDO> searchProjectContractInvalidList(ProjectContractFormDO projectContractFormDO,
																		long limitStart,
																		long pageSize,
																		Date operateTimeStart,
																		Date operateTimeEnd,
																		String sortCol,
																		String sortOrder)
																							throws DataAccessException {
		if (projectContractFormDO == null) {
			throw new IllegalArgumentException("projectContractFormDO can`t by null");
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("limitStart", limitStart);
		paramMap.put("pageSize", pageSize);
		paramMap.put("sortOrder", sortOrder);
		paramMap.put("sortCol", sortCol);
		
		paramMap.put("contractCode", projectContractFormDO.getContractCode());
		paramMap.put("contractName", projectContractFormDO.getContractName());
		paramMap.put("contractStatus", projectContractFormDO.getContractStatus());
		paramMap.put("projectCode", projectContractFormDO.getProjectCode());
		paramMap.put("projectName", projectContractFormDO.getProjectName());
		paramMap.put("busiType", projectContractFormDO.getBusiType());
		paramMap.put("customerName", projectContractFormDO.getCustomerName());
		paramMap.put("likeContractCodeOrNmae", projectContractFormDO.getLikeContractCodeOrName());
		paramMap.put("operateTimeStart", operateTimeStart);
		paramMap.put("operateTimeEnd", operateTimeEnd);
		
		paramMap.put("loginUserId", projectContractFormDO.getLoginUserId());
		paramMap.put("deptIdList", projectContractFormDO.getDeptIdList());
		paramMap.put("draftUserId", projectContractFormDO.getDraftUserId());
		paramMap.put("remark", projectContractFormDO.getRemark());
		List<ProjectContractFormDO> list = getSqlMapClientTemplate().queryForList(
			"MS-EXTRA-PROJECT-CONTRACT-INVALID-SEARCH", paramMap);
		
		return list;
	}
	
	@Override
	public long searchProjectContractInvalidListCount(ProjectContractFormDO projectContractFormDO,
														Date operateTimeStart, Date operateTimeEnd)
																									throws DataAccessException {
		if (projectContractFormDO == null) {
			throw new IllegalArgumentException("projectContractFormDO can`t by null");
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("contractCode", projectContractFormDO.getContractCode());
		paramMap.put("contractName", projectContractFormDO.getContractName());
		paramMap.put("contractStatus", projectContractFormDO.getContractStatus());
		paramMap.put("projectCode", projectContractFormDO.getProjectCode());
		paramMap.put("projectName", projectContractFormDO.getProjectName());
		paramMap.put("busiType", projectContractFormDO.getBusiType());
		paramMap.put("customerName", projectContractFormDO.getCustomerName());
		paramMap.put("operateTimeStart", operateTimeStart);
		paramMap.put("operateTimeEnd", operateTimeEnd);
		paramMap.put("likeContractCodeOrNmae", projectContractFormDO.getLikeContractCodeOrName());
		
		paramMap.put("loginUserId", projectContractFormDO.getLoginUserId());
		paramMap.put("draftUserId", projectContractFormDO.getDraftUserId());
		paramMap.put("deptIdList", projectContractFormDO.getDeptIdList());
		paramMap.put("remark", projectContractFormDO.getRemark());
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-PROJECT-CONTRACT-INVALID-SEARCH-COUNT", paramMap);
	}
	
	@Override
	public long getNextYearMonthSeq(String name, Date date, long cacheNumber)
																				throws DataAccessException {
		
		if (StringUtil.isBlank(name)) {
			throw new IllegalArgumentException("Can't update a null data object into db.");
		}
		if (cacheNumber <= 0)
			cacheNumber = 1;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("name", name);
		paramMap.put("seqDate", DateUtil.simpleFormatYM(date));
		paramMap.put("cacheNumber", cacheNumber);
		return (Long) getSqlMapClientTemplate().insert("MS-EXTRA-SYS-DATE-SEQ-UPDATE", paramMap);
	}
	
	@Override
	public long insertYearMonthSeq(String name, Date date) throws DataAccessException {
		if (StringUtil.isBlank(name)) {
			throw new IllegalArgumentException("Can't insert a null data object into db.");
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("name", name);
		paramMap.put("seqDate", DateUtil.simpleFormatYM(date));
		paramMap.put("rawAddTime", date);
		return (Long) getSqlMapClientTemplate().insert("MS-EXTRA-SYS-DATE-SEQ-INSERT", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AfterwardsCheckProjectDO> searchAfterwardsCheckProject(FormProjectDO formProjectDO,
																		long limitStart,
																		long pageSize,
																		Date submitTimeStart,
																		Date submitTimeEnd,
																		String sortCol,
																		String sortOrder)
																							throws DataAccessException {
		if (formProjectDO == null) {
			throw new IllegalArgumentException("setupForm can`t by null");
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("limitStart", limitStart);
		paramMap.put("pageSize", pageSize);
		paramMap.put("sortOrder", sortOrder);
		paramMap.put("sortCol", sortCol);
		
		paramMap.put("formId", formProjectDO.getFormId());
		paramMap.put("projectId", formProjectDO.getProjectId());
		paramMap.put("customerId", formProjectDO.getCustomerId());
		paramMap.put("busiManagerId", formProjectDO.getBusiManagerId());
		paramMap.put("busiManagerbId", formProjectDO.getBusiManagerbId());
		paramMap.put("projectCode", formProjectDO.getProjectCode());
		paramMap.put("busiType", formProjectDO.getBusiType());
		paramMap.put("busiTypeName", formProjectDO.getBusiTypeName());
		paramMap.put("projectName", formProjectDO.getProjectName());
		paramMap.put("customerName", formProjectDO.getCustomerName());
		paramMap.put("busiManagerName", formProjectDO.getBusiManagerName());
		paramMap.put("busiManagerbName", formProjectDO.getBusiManagerbName());
		paramMap.put("submitTimeStart", submitTimeStart);
		paramMap.put("submitTimeEnd", submitTimeEnd);
		paramMap.put("deptId", formProjectDO.getDeptId());
		paramMap.put("deptName", formProjectDO.getDeptName());
		paramMap.put("deptCode", formProjectDO.getDeptCode());
		paramMap.put("deptPath", formProjectDO.getDeptPath());
		paramMap.put("deptPathName", formProjectDO.getDeptPathName());
		List<AfterwardsCheckProjectDO> list = getSqlMapClientTemplate().queryForList(
			"MS-EXTRA-AFTERWARDS-CHECK-F-PROJECT-SEARCH", paramMap);
		
		return list;
	}
	
	@Override
	public long searchAfterwardsCheckProjectCount(FormProjectDO formProjectDO,
													Date submitTimeStart, Date submitTimeEnd)
																								throws DataAccessException {
		if (formProjectDO == null) {
			throw new IllegalArgumentException("setupForm can`t by null");
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("formId", formProjectDO.getFormId());
		paramMap.put("projectId", formProjectDO.getProjectId());
		paramMap.put("customerId", formProjectDO.getCustomerId());
		paramMap.put("busiManagerId", formProjectDO.getBusiManagerId());
		paramMap.put("busiManagerbId", formProjectDO.getBusiManagerbId());
		paramMap.put("projectCode", formProjectDO.getProjectCode());
		paramMap.put("busiType", formProjectDO.getBusiType());
		paramMap.put("busiTypeName", formProjectDO.getBusiTypeName());
		paramMap.put("projectName", formProjectDO.getProjectName());
		paramMap.put("customerName", formProjectDO.getCustomerName());
		paramMap.put("busiManagerName", formProjectDO.getBusiManagerName());
		paramMap.put("busiManagerbName", formProjectDO.getBusiManagerbName());
		paramMap.put("submitTimeStart", submitTimeStart);
		paramMap.put("submitTimeEnd", submitTimeEnd);
		paramMap.put("deptId", formProjectDO.getDeptId());
		paramMap.put("deptName", formProjectDO.getDeptName());
		paramMap.put("deptCode", formProjectDO.getDeptCode());
		paramMap.put("deptPath", formProjectDO.getDeptPath());
		paramMap.put("deptPathName", formProjectDO.getDeptPathName());
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-AFTERWARDS-CHECK-F-PROJECT-SEARCH-COUNT", paramMap);
	}
	
	//授信落实 列表
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<FCreditConditionConfirmDO> searchCreditConditionList(	FCreditConditionConfirmDO creditCondition,
																		long limitStart,
																		long pageSize,
																		Date submitTimeStart,
																		Date submitTimeEnd,
																		List<Long> deptIdList,
																		String sortCol,
																		String sortOrder)
																							throws DataAccessException {
		
		if (creditCondition == null) {
			throw new IllegalArgumentException("setupForm can`t by null");
		}
		
		Map paramMap = new HashMap<String, Object>();
		
		paramMap.put("limitStart", limitStart);
		paramMap.put("pageSize", pageSize);
		paramMap.put("sortOrder", sortOrder);
		paramMap.put("sortCol", sortCol);
		
		paramMap.put("formId", creditCondition.getFormId());
		paramMap.put("customerId", creditCondition.getCustomerId());
		paramMap.put("busiManagerbId", creditCondition.getBusiManagerbId());
		paramMap.put("busiManagerbAccount", creditCondition.getBusiManagerbAccount());
		paramMap.put("projectCode", creditCondition.getProjectCode());
		paramMap.put("busiType", creditCondition.getBusiType());
		paramMap.put("busiTypeName", creditCondition.getBusiTypeName());
		paramMap.put("projectName", creditCondition.getProjectName());
		paramMap.put("customerName", creditCondition.getCustomerName());
		paramMap.put("busiManagerbName", creditCondition.getBusiManagerbName());
		paramMap.put("busiManagerbName", creditCondition.getBusiManagerbName());
		paramMap.put("submitTimeStart", submitTimeStart);
		paramMap.put("submitTimeEnd", submitTimeEnd);
		paramMap.put("isMargin", creditCondition.getIsMargin());
		paramMap.put("statusList", creditCondition.getStatusList());
		paramMap.put("formStatus", creditCondition.getFormStatus());
		
		paramMap.put("loginUserId", creditCondition.getLoginUserId());
		paramMap.put("deptIdList", creditCondition.getDeptIdList());
		paramMap.put("draftUserId", creditCondition.getDraftUserId());
		paramMap.put("deptIdList", deptIdList);
		List<FCreditConditionConfirmDO> list = getSqlMapClientTemplate().queryForList(
			"MS-EXTRA-FORM-PROJECT-CREDIT-SEARCH", paramMap);
		
		return list;
	}
	
	//授信列表 统计
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public long searchCreditConditionCount(FCreditConditionConfirmDO creditCondition,
											Date submitTimeStart, Date submitTimeEnd,
											List<Long> deptIdList) throws DataAccessException {
		
		if (creditCondition == null) {
			throw new IllegalArgumentException("setupForm can`t by null");
		}
		
		Map paramMap = new HashMap<String, Object>();
		paramMap.put("formId", creditCondition.getFormId());
		paramMap.put("customerId", creditCondition.getCustomerId());
		paramMap.put("busiManagerbId", creditCondition.getBusiManagerbId());
		paramMap.put("busiManagerbAccount", creditCondition.getBusiManagerbAccount());
		paramMap.put("projectCode", creditCondition.getProjectCode());
		paramMap.put("busiType", creditCondition.getBusiType());
		paramMap.put("busiTypeName", creditCondition.getBusiTypeName());
		paramMap.put("projectName", creditCondition.getProjectName());
		paramMap.put("customerName", creditCondition.getCustomerName());
		paramMap.put("busiManagerbName", creditCondition.getBusiManagerbName());
		paramMap.put("busiManagerbName", creditCondition.getBusiManagerbName());
		paramMap.put("submitTimeStart", submitTimeStart);
		paramMap.put("submitTimeEnd", submitTimeEnd);
		paramMap.put("isMargin", creditCondition.getIsMargin());
		paramMap.put("statusList", creditCondition.getStatusList());
		paramMap.put("formStatus", creditCondition.getFormStatus());
		
		paramMap.put("loginUserId", creditCondition.getLoginUserId());
		paramMap.put("draftUserId", creditCondition.getDraftUserId());
		paramMap.put("deptIdList", creditCondition.getDeptIdList());
		paramMap.put("deptIdList", deptIdList);
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-FORM-PROJECT-CREDIT-SEARCH-COUNT", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DbFieldProjectDO> searchDbFieldList(DbFieldProjectDO dbFieldProjectDO,
													long limitStart, long pageSize, String sortCol,
													String sortOrder) throws DataAccessException {
		if (dbFieldProjectDO == null) {
			throw new IllegalArgumentException("dbFieldProjectDO can`t by null");
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("limitStart", limitStart);
		paramMap.put("pageSize", pageSize);
		paramMap.put("sortCol", sortCol);
		paramMap.put("sortOrder", sortOrder);
		paramMap.put("tableName", dbFieldProjectDO.getTableName());
		paramMap.put("fieldName", dbFieldProjectDO.getFieldName());
		paramMap.put("projectPhase", dbFieldProjectDO.getProjectPhase());
		List<DbFieldProjectDO> list = getSqlMapClientTemplate().queryForList(
			"MS-EXTRA-DB-FIELD-SEARCH", paramMap);
		
		return list;
	}
	
	@Override
	public long searchDbFieldListCount(DbFieldProjectDO dbFieldProjectDO)
																			throws DataAccessException {
		if (dbFieldProjectDO == null) {
			throw new IllegalArgumentException("dbFieldProjectDO can`t by null");
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("tableName", dbFieldProjectDO.getTableName());
		paramMap.put("fieldName", dbFieldProjectDO.getFieldName());
		paramMap.put("projectPhase", dbFieldProjectDO.getProjectPhase());
		return (Long) getSqlMapClientTemplate().queryForObject("MS-EXTRA-DB-FIELD-SEARCH-COUNT",
			paramMap);
	}
	
	//承销/发债已发售项目信息列表
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<ProjectDO> searchIssueList(ProjectIssueInformationDO issueDO, long limitStart,
											long pageSize, List<Long> deptIdList, String sortCol,
											String sortOrder) throws DataAccessException {
		
		if (issueDO == null) {
			throw new IllegalArgumentException("setupForm can`t by null");
		}
		
		Map paramMap = new HashMap<String, Object>();
		
		paramMap.put("limitStart", limitStart);
		paramMap.put("pageSize", pageSize);
		paramMap.put("sortOrder", sortOrder);
		paramMap.put("sortCol", sortCol);
		
		paramMap.put("projectId", issueDO.getProjectId());
		paramMap.put("customerId", issueDO.getCustomerId());
		paramMap.put("busiManagerId", issueDO.getBusiManagerId());
		paramMap.put("busiManagerbId", issueDO.getBusiManagerbId());
		paramMap.put("projectCode", issueDO.getProjectCode());
		paramMap.put("busiType", issueDO.getBusiType());
		paramMap.put("busiTypeName", issueDO.getBusiTypeName());
		paramMap.put("projectName", issueDO.getProjectName());
		paramMap.put("customerName", issueDO.getCustomerName());
		paramMap.put("busiManagerName", issueDO.getBusiManagerName());
		paramMap.put("busiManagerbName", issueDO.getBusiManagerbName());
		paramMap.put("isContinue", issueDO.getIsContinue());
		paramMap.put("deptId", issueDO.getDeptId());
		paramMap.put("deptName", issueDO.getDeptName());
		paramMap.put("deptCode", issueDO.getDeptCode());
		paramMap.put("deptPath", issueDO.getDeptPath());
		paramMap.put("deptPathName", issueDO.getDeptPathName());
		paramMap.put("loginUserId", issueDO.getLoginUserId());
		paramMap.put("draftUserId", issueDO.getDraftUserId());
		
		paramMap.put("deptIdList", issueDO.getDeptIdList());
		paramMap.put("statusList", issueDO.getStatusList());
		paramMap.put("deptIdList", deptIdList);
		List<ProjectDO> list = getSqlMapClientTemplate().queryForList(
			"MS-EXTRA-FORM-PROJECT-ISSUE-SEARCH", paramMap);
		
		return list;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public long searchIssueCount(ProjectIssueInformationDO issueDO, List<Long> deptIdList)
																							throws DataAccessException {
		
		if (issueDO == null) {
			throw new IllegalArgumentException("setupForm can`t by null");
		}
		
		Map paramMap = new HashMap<String, Object>();
		paramMap.put("projectId", issueDO.getProjectId());
		paramMap.put("customerId", issueDO.getCustomerId());
		paramMap.put("busiManagerId", issueDO.getBusiManagerId());
		paramMap.put("busiManagerbId", issueDO.getBusiManagerbId());
		paramMap.put("projectCode", issueDO.getProjectCode());
		paramMap.put("busiType", issueDO.getBusiType());
		paramMap.put("busiTypeName", issueDO.getBusiTypeName());
		paramMap.put("projectName", issueDO.getProjectName());
		paramMap.put("customerName", issueDO.getCustomerName());
		paramMap.put("busiManagerName", issueDO.getBusiManagerName());
		paramMap.put("busiManagerbName", issueDO.getBusiManagerbName());
		
		paramMap.put("deptId", issueDO.getDeptId());
		paramMap.put("deptName", issueDO.getDeptName());
		paramMap.put("deptCode", issueDO.getDeptCode());
		paramMap.put("deptPath", issueDO.getDeptPath());
		paramMap.put("deptPathName", issueDO.getDeptPathName());
		paramMap.put("loginUserId", issueDO.getLoginUserId());
		paramMap.put("draftUserId", issueDO.getDraftUserId());
		
		paramMap.put("deptIdList", issueDO.getDeptIdList());
		paramMap.put("statusList", issueDO.getStatusList());
		paramMap.put("deptIdList", deptIdList);
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-FORM-PROJECT-ISSUE-SEARCH-COUNT", paramMap);
	}
	
	@Override
	public long searchFileSelectProjectCount(Map<String, Object> paramMap)
																			throws DataAccessException {
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-FILE-SELECT-PROJECT-SEARCH-COUNT", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectDO> searchFileSelectProject(Map<String, Object> paramMap)
																				throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("MS-EXTRA-FILE-SELECT-PROJECT-SEARCH",
			paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<FileFormDO> searchFileList(FileFormDO fileFormDO, long limitStart, long pageSize,
											String sortCol, String sortOrder)
																				throws DataAccessException {
		if (fileFormDO == null) {
			throw new IllegalArgumentException("fileFormDO can`t by null");
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("limitStart", limitStart);
		paramMap.put("pageSize", pageSize);
		paramMap.put("sortCol", sortCol);
		paramMap.put("sortOrder", sortOrder);
		paramMap.put("projectCode", fileFormDO.getProjectCode());
		paramMap.put("customerName", fileFormDO.getCustomerName());
		paramMap.put("fileCode", fileFormDO.getFileCode());
		paramMap.put("oldFileCode", fileFormDO.getOldFileCode());
		paramMap.put("formStatus", fileFormDO.getFormStatus());
		paramMap.put("status", fileFormDO.getStatus());
		paramMap.put("fileName", fileFormDO.getFileName());
		paramMap.put("applyType", fileFormDO.getApplyType());
		paramMap.put("fileType", fileFormDO.getFileType());
		paramMap.put("startTime", fileFormDO.getStartTime());
		paramMap.put("endTime", fileFormDO.getEndTime());
		paramMap.put("applyManId", fileFormDO.getApplyManId());
		paramMap.put("detailId", fileFormDO.getDetailId());
		
		paramMap.put("loginUserId", fileFormDO.getLoginUserId());
		paramMap.put("draftUserId", fileFormDO.getDraftUserId());
		paramMap.put("deptIdList", fileFormDO.getDeptIdList());
		paramMap.put("isFileAdmin", fileFormDO.getIsFileAdmin());
		List<FileFormDO> list = getSqlMapClientTemplate().queryForList("MS-EXTRA-FILE-FORM-SEARCH",
			paramMap);
		return list;
	}
	
	@Override
	public long searchFileListCount(FileFormDO fileFormDO) throws DataAccessException {
		if (fileFormDO == null) {
			throw new IllegalArgumentException("fileFormDO can`t by null");
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("projectCode", fileFormDO.getProjectCode());
		paramMap.put("customerName", fileFormDO.getCustomerName());
		paramMap.put("fileCode", fileFormDO.getFileCode());
		paramMap.put("oldFileCode", fileFormDO.getOldFileCode());
		paramMap.put("formStatus", fileFormDO.getFormStatus());
		paramMap.put("status", fileFormDO.getStatus());
		paramMap.put("fileName", fileFormDO.getFileName());
		paramMap.put("applyType", fileFormDO.getApplyType());
		paramMap.put("fileType", fileFormDO.getFileType());
		paramMap.put("startTime", fileFormDO.getStartTime());
		paramMap.put("endTime", fileFormDO.getEndTime());
		
		paramMap.put("loginUserId", fileFormDO.getLoginUserId());
		paramMap.put("draftUserId", fileFormDO.getDraftUserId());
		paramMap.put("deptIdList", fileFormDO.getDeptIdList());
		paramMap.put("isFileAdmin", fileFormDO.getIsFileAdmin());
		return (Long) getSqlMapClientTemplate().queryForObject("MS-EXTRA-FILE-FORM-SEARCH-COUNT",
			paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<FileFormDO> searchFileDetailList(FileFormDO fileFormDO, long limitStart,
													long pageSize, String sortCol, String sortOrder)
																									throws DataAccessException {
		if (fileFormDO == null) {
			throw new IllegalArgumentException("fileFormDO can`t by null");
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("limitStart", limitStart);
		paramMap.put("pageSize", pageSize);
		paramMap.put("sortCol", sortCol);
		paramMap.put("sortOrder", sortOrder);
		paramMap.put("projectCode", fileFormDO.getProjectCode());
		paramMap.put("customerName", fileFormDO.getCustomerName());
		paramMap.put("fileCode", fileFormDO.getFileCode());
		paramMap.put("oldFileCode", fileFormDO.getOldFileCode());
		paramMap.put("formStatus", fileFormDO.getFormStatus());
		paramMap.put("status", fileFormDO.getStatus());
		paramMap.put("fileName", fileFormDO.getFileName());
		paramMap.put("archiveFileName", fileFormDO.getArchiveFileName());
		paramMap.put("applyType", fileFormDO.getApplyType());
		paramMap.put("fileType", fileFormDO.getFileType());
		paramMap.put("fileStartTime", fileFormDO.getFileStartTime());
		paramMap.put("fileEndTime", fileFormDO.getFileEndTime());
		paramMap.put("startTime", fileFormDO.getStartTime());
		paramMap.put("endTime", fileFormDO.getEndTime());
		
		paramMap.put("loginUserId", fileFormDO.getLoginUserId());
		paramMap.put("draftUserId", fileFormDO.getDraftUserId());
		paramMap.put("deptIdList", fileFormDO.getDeptIdList());
		paramMap.put("isFileAdmin", fileFormDO.getIsFileAdmin());
		paramMap.put("xinhuiDeptCode", fileFormDO.getXinhuiDeptCode());
		paramMap.put("isXinhui", fileFormDO.getIsXinhui());
		List<FileFormDO> list = getSqlMapClientTemplate().queryForList(
			"MS-EXTRA-FILE-DETAIL-FORM-SEARCH", paramMap);
		return list;
	}
	
	@Override
	public long searchFileDetailListCount(FileFormDO fileFormDO) throws DataAccessException {
		if (fileFormDO == null) {
			throw new IllegalArgumentException("fileFormDO can`t by null");
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("projectCode", fileFormDO.getProjectCode());
		paramMap.put("customerName", fileFormDO.getCustomerName());
		paramMap.put("fileCode", fileFormDO.getFileCode());
		paramMap.put("formStatus", fileFormDO.getFormStatus());
		paramMap.put("status", fileFormDO.getStatus());
		paramMap.put("type", fileFormDO.getFileType());
		paramMap.put("fileStartTime", fileFormDO.getFileStartTime());
		paramMap.put("fileEndTime", fileFormDO.getFileEndTime());
		paramMap.put("fileName", fileFormDO.getFileName());
		paramMap.put("archiveFileName", fileFormDO.getArchiveFileName());
		paramMap.put("applyType", fileFormDO.getApplyType());
		
		paramMap.put("deptIdList", fileFormDO.getDeptIdList());
		paramMap.put("loginUserId", fileFormDO.getLoginUserId());
		paramMap.put("draftUserId", fileFormDO.getDraftUserId());
		paramMap.put("isFileAdmin", fileFormDO.getIsFileAdmin());
		paramMap.put("xinhuiDeptCode", fileFormDO.getXinhuiDeptCode());
		paramMap.put("isXinhui", fileFormDO.getIsXinhui());
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-FILE-DETAIL-FORM-SEARCH-COUNT", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<FileExtensionFormDO> searchExtensionList(FileFormDO fileFormDO, long limitStart,
															long pageSize, String sortCol,
															String sortOrder)
																				throws DataAccessException {
		if (fileFormDO == null) {
			throw new IllegalArgumentException("fileFormDO can`t by null");
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("limitStart", limitStart);
		paramMap.put("pageSize", pageSize);
		paramMap.put("sortCol", sortCol);
		paramMap.put("sortOrder", sortOrder);
		paramMap.put("projectCode", fileFormDO.getProjectCode());
		paramMap.put("customerName", fileFormDO.getCustomerName());
		paramMap.put("formStatus", fileFormDO.getFormStatus());
		paramMap.put("loginUserId", fileFormDO.getLoginUserId());
		paramMap.put("draftUserId", fileFormDO.getDraftUserId());
		paramMap.put("deptIdList", fileFormDO.getDeptIdList());
		List<FileExtensionFormDO> list = getSqlMapClientTemplate().queryForList(
			"MS-EXTRA-FILE-EXTENSION-SEARCH", paramMap);
		return list;
	}
	
	@Override
	public long searchExtensionListCount(FileFormDO fileFormDO) throws DataAccessException {
		if (fileFormDO == null) {
			throw new IllegalArgumentException("fileFormDO can`t by null");
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("projectCode", fileFormDO.getProjectCode());
		paramMap.put("customerName", fileFormDO.getCustomerName());
		paramMap.put("formStatus", fileFormDO.getFormStatus());
		paramMap.put("loginUserId", fileFormDO.getLoginUserId());
		paramMap.put("draftUserId", fileFormDO.getDraftUserId());
		paramMap.put("deptIdList", fileFormDO.getDeptIdList());
		paramMap.put("isFileAdmin", fileFormDO.getIsFileAdmin());
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-FILE-EXTENSION-SEARCH-COUNT", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<FileViewDO> searchFileViewList(FileViewDO fileViewDO, long limitStart,
												long pageSize, String sortCol, String sortOrder)
																								throws DataAccessException {
		if (fileViewDO == null) {
			throw new IllegalArgumentException("fileViewDO can`t by null");
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("limitStart", limitStart);
		paramMap.put("pageSize", pageSize);
		paramMap.put("sortCol", sortCol);
		paramMap.put("sortOrder", sortOrder);
		paramMap.put("projectCode", fileViewDO.getProjectCode());
		paramMap.put("customerName", fileViewDO.getCustomerName());
		paramMap.put("projectName", fileViewDO.getProjectName());
		paramMap.put("busiManagerName", fileViewDO.getBusiManagerName());
		List<FileViewDO> list = getSqlMapClientTemplate().queryForList("MS-EXTRA-FILE-VIEW-SEARCH",
			paramMap);
		return list;
	}
	
	@Override
	public long searchFileViewListCount(FileViewDO fileViewDO) throws DataAccessException {
		if (fileViewDO == null) {
			throw new IllegalArgumentException("fileViewDO can`t by null");
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("projectCode", fileViewDO.getProjectCode());
		paramMap.put("customerName", fileViewDO.getCustomerName());
		paramMap.put("projectName", fileViewDO.getProjectName());
		paramMap.put("busiManagerName", fileViewDO.getBusiManagerName());
		return (Long) getSqlMapClientTemplate().queryForObject("MS-EXTRA-FILE-VIEW-SEARCH-COUNT",
			paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<FileFormDO> needMessageFile() {
		List<FileFormDO> list = getSqlMapClientTemplate().queryForList(
			"MS-EXTRA-NEED-MESSAGE-FILE-SEARCH");
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<FileInOutFormDO> getInOutHistory(Long inputListId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("inputListId", inputListId);
		List<FileInOutFormDO> list = getSqlMapClientTemplate().queryForList(
			"MS-EXTRA-FILE-IN-OUT-FORM-SEARCH", paramMap);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<FileNeedSendMessageProjectDO> FileNeedMessageProject(String type,
																		List<String> statusList) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("type", type);
		paramMap.put("statusList", statusList);
		List<FileNeedSendMessageProjectDO> list = getSqlMapClientTemplate().queryForList(
			"MS-EXTRA-NEED-MESSAGE-PROJECT-SEARCH", paramMap);
		return list;
	}
	
	@Override
	public Long searchMaxNoWithFileCode(String fileCode) throws DataAccessException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("fileCode", fileCode);
		return (Long) getSqlMapClientTemplate().queryForObject("MS-EXTRA-FILE-MAX-NO-SERACH",
			paramMap);
	}
	
	@Override
	public List<FileFormDO> getFileNOs(String fileCode, long formId) throws DataAccessException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("fileCode", fileCode);
		paramMap.put("formId", formId);
		List<FileFormDO> list = getSqlMapClientTemplate().queryForList("MS-EXTRA-FILE-NOS-SEARCH",
			paramMap);
		return list;
	}
	
	@Override
	public Long checkFileCode(String fileCode) throws DataAccessException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("fileCode", fileCode);
		return (Long) getSqlMapClientTemplate()
			.queryForObject("MS-EXTRA-CHECK-FILE-CODE", paramMap);
	}
	
	@Override
	public String searchInputStatusFileIds(String type, String projectCode, Date startTime,
											Date endTime) throws DataAccessException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("type", type);
		paramMap.put("projectCode", projectCode);
		paramMap.put("startTime", startTime);
		paramMap.put("endTime", endTime);
		return (String) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-INPUT-STATUS-FILE-IDS-SERACH", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<FileNotArchiveDO> searchNotArchiveByProjectCode(long formId, String type,
																String projectCode)
																					throws DataAccessException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("formId", formId);
		paramMap.put("type", type);
		paramMap.put("projectCode", projectCode);
		List<FileNotArchiveDO> list = getSqlMapClientTemplate().queryForList(
			"MS-EXTRA-FILE-NOT-ARCHIVE-SEARCH", paramMap);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<FFileInputListDO> searchArchivedByProjectCode(long formId, String type,
																String projectCode,
																String notNeedDraft, String no)
																								throws DataAccessException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("formId", formId);
		paramMap.put("type", type);
		paramMap.put("projectCode", projectCode);
		paramMap.put("notNeedDraft", notNeedDraft);
		paramMap.put("no", no);
		List<FFileInputListDO> list = getSqlMapClientTemplate().queryForList(
			"MS-EXTRA-FILE-ARCHIVED-SEARCH", paramMap);
		return list;
	}
	
	@Override
	public long searchReleaseAmount(Map<String, Object> paramMap) throws DataAccessException {
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-COUNTER-GUARANTEE-APPLY-SUM", paramMap);
	}
	
	@Override
	public long searchReleaseApplyCount(Map<String, Object> paramMap) throws DataAccessException {
		return (Long) getSqlMapClientTemplate().queryForObject("MS-EXTRA-RELEASE-APPLY-COUNT",
			paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ReleaseApplyDO> searchReleaseApply(Map<String, Object> paramMap)
																				throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("MS-EXTRA-RELEASE-APPLY-SEARCH", paramMap);
	}
	
	@Override
	public long isConfirmAllCount(String projectCode) throws DataAccessException {
		return (Long) getSqlMapClientTemplate().queryForObject("MS-EXTRA-IS-ALL-CONFIRM-COUNT",
			projectCode);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectContractFormDO> searchIsConfirmAll(String projectCode)
																				throws DataAccessException {
		List<ProjectContractFormDO> list = getSqlMapClientTemplate().queryForList(
			"MS-EXTRA-IS-ALL-CONFIRM-SEARCH", projectCode);
		return list;
	}
	
	@Override
	public long searchRiskLevelCount(Map<String, Object> paramMap) throws DataAccessException {
		return (Long) getSqlMapClientTemplate().queryForObject("MS-EXTRA-RISK-LEVEL-COUNT",
			paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RiskLevelDO> searchRiskLevel(Map<String, Object> paramMap)
																			throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("MS-EXTRA-RISK-LEVEL-SEARCH", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CreditRefrerenceApplyFormDO> searchCreditApplyList(	CreditRefrerenceApplyFormDO creditRefrerenceApplyFromDO,
																	long limitStart, long pageSize,
																	String sortCol, String sortOrder)
																										throws DataAccessException {
		if (creditRefrerenceApplyFromDO == null) {
			throw new IllegalArgumentException("creditRefrerenceApplyFromDO can`t by null");
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("limitStart", limitStart);
		paramMap.put("pageSize", pageSize);
		paramMap.put("companyName", creditRefrerenceApplyFromDO.getCompanyName());
		paramMap.put("projectCode", creditRefrerenceApplyFromDO.getProjectCode());
		paramMap.put("projectName", creditRefrerenceApplyFromDO.getProjectName());
		paramMap.put("busiLicenseNo", creditRefrerenceApplyFromDO.getBusiLicenseNo());
		paramMap.put("applyManName", creditRefrerenceApplyFromDO.getApplyManName());
		paramMap.put("formStatus", creditRefrerenceApplyFromDO.getFormStatus());
		paramMap.put("formId", creditRefrerenceApplyFromDO.getFormId());
		paramMap.put("sortCol", sortCol);
		paramMap.put("sortOrder", sortOrder);
		
		paramMap.put("deptIdList", creditRefrerenceApplyFromDO.getDeptIdList());
		paramMap.put("loginUserId", creditRefrerenceApplyFromDO.getLoginUserId());
		paramMap.put("draftUserId", creditRefrerenceApplyFromDO.getDraftUserId());
		List<CreditRefrerenceApplyFormDO> list = getSqlMapClientTemplate().queryForList(
			"RM-CREDIT-REFRERENCE-APPLY-PROJECT-SEARCH", paramMap);
		return list;
	}
	
	@Override
	public long searchCreditApplyCount(CreditRefrerenceApplyFormDO creditRefrerenceApplyFromDO)
																								throws DataAccessException {
		if (creditRefrerenceApplyFromDO == null) {
			throw new IllegalArgumentException("creditRefrerenceApplyFromDO can`t by null");
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("companyName", creditRefrerenceApplyFromDO.getCompanyName());
		paramMap.put("projectCode", creditRefrerenceApplyFromDO.getProjectCode());
		paramMap.put("busiLicenseNo", creditRefrerenceApplyFromDO.getBusiLicenseNo());
		paramMap.put("applyManName", creditRefrerenceApplyFromDO.getApplyManName());
		paramMap.put("formStatus", creditRefrerenceApplyFromDO.getFormStatus());
		paramMap.put("projectName", creditRefrerenceApplyFromDO.getProjectName());
		paramMap.put("formId", creditRefrerenceApplyFromDO.getFormId());
		
		paramMap.put("deptIdList", creditRefrerenceApplyFromDO.getDeptIdList());
		paramMap.put("loginUserId", creditRefrerenceApplyFromDO.getLoginUserId());
		paramMap.put("draftUserId", creditRefrerenceApplyFromDO.getDraftUserId());
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-CREDIT-REFRERENCE-APPLY-PROJECT-SEARCH-COUNT", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectDO> selectChargeNotificationProject(ProjectDO queryCondition,
															String projectCodeOrName,
															long loginUserId, long draftUserId,
															List<Long> detpList,
															List<String> statusList,
															List<String> phases,
															List<String> phasesStatus,
															String hasContract, long limitStart,
															long pageSize, String sortCol,
															String sortOrder)
																				throws DataAccessException {
		
		if (queryCondition == null) {
			throw new IllegalArgumentException("ProjectDO can`t by null");
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("limitStart", limitStart);
		paramMap.put("pageSize", pageSize);
		paramMap.put("sortOrder", sortOrder);
		paramMap.put("sortCol", sortCol);
		paramMap.put("customerName", queryCondition.getCustomerName());
		paramMap.put("projectCode", queryCondition.getProjectCode());
		paramMap.put("projectName", queryCondition.getProjectName());
		paramMap.put("projectCodeOrName", projectCodeOrName);
		paramMap.put("loginUserId", loginUserId);
		paramMap.put("draftUserId", draftUserId);
		paramMap.put("deptIdList", detpList);
		paramMap.put("busiManagerId", queryCondition.getBusiManagerId());
		paramMap.put("statusList", statusList);
		paramMap.put("phasesStatus", phasesStatus);
		paramMap.put("hasContract", hasContract);
		paramMap.put("phases", phases);
		List<ProjectDO> list = getSqlMapClientTemplate().queryForList(
			"MS-EXTRA-CHARGE-NOTIFICATION-SELECT-PROJECT", paramMap);
		return list;
	}
	
	@Override
	public long selectChargeNotificationProjectCount(ProjectDO queryCondition,
														String projectCodeOrName, long loginUserId,
														long draftUserId, List<Long> detpList,
														List<String> statusList,
														List<String> phases,
														List<String> phasesStatus,
														String hasContract)
																			throws DataAccessException {
		if (queryCondition == null) {
			throw new IllegalArgumentException("ProjectDO can`t by null");
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("customerName", queryCondition.getCustomerName());
		paramMap.put("projectCode", queryCondition.getProjectCode());
		paramMap.put("projectName", queryCondition.getProjectName());
		paramMap.put("projectCodeOrName", projectCodeOrName);
		paramMap.put("draftUserId", draftUserId);
		paramMap.put("loginUserId", loginUserId);
		paramMap.put("deptIdList", detpList);
		paramMap.put("busiManagerId", queryCondition.getBusiManagerId());
		paramMap.put("busiManagerbId", queryCondition.getBusiManagerbId());
		paramMap.put("statusList", statusList);
		paramMap.put("phasesStatus", phasesStatus);
		paramMap.put("hasContract", hasContract);
		paramMap.put("phases", phases);
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-CHARGE-NOTIFICATION-SELECT-PROJECT-COUNT", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ChargeNotificationFormDO> searchChargeNotificationList(	ChargeNotificationFormDO chargeNotificationFormDO,
																		long limitStart,
																		long pageSize,
																		String sortCol,
																		String sortOrder)
																							throws DataAccessException {
		if (chargeNotificationFormDO == null) {
			throw new IllegalArgumentException("chargeNotificationFormDO can`t by null");
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("limitStart", limitStart);
		paramMap.put("pageSize", pageSize);
		paramMap.put("sortCol", sortCol);
		paramMap.put("sortOrder", sortOrder);
		paramMap.put("projectCode", chargeNotificationFormDO.getProjectCode());
		paramMap.put("projectName", chargeNotificationFormDO.getProjectName());
		paramMap.put("customerName", chargeNotificationFormDO.getCustomerName());
		paramMap.put("formStatus", chargeNotificationFormDO.getFormStatus());
		paramMap.put("chargeBasis", chargeNotificationFormDO.getChargeBasis());
		
		paramMap.put("deptIdList", chargeNotificationFormDO.getDeptIdList());
		paramMap.put("loginUserId", chargeNotificationFormDO.getLoginUserId());
		paramMap.put("draftUserId", chargeNotificationFormDO.getDraftUserId());
		paramMap.put("busiType", chargeNotificationFormDO.getBusiType());
		List<ChargeNotificationFormDO> list = getSqlMapClientTemplate().queryForList(
			"RM-CHARGE-NOTIFICATION-SEARCH", paramMap);
		return list;
	}
	
	@Override
	public long searchChargeNotificationCount(ChargeNotificationFormDO chargeNotificationFormDO)
																								throws DataAccessException {
		if (chargeNotificationFormDO == null) {
			throw new IllegalArgumentException("chargeNotificationFormDO can`t by null");
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("projectCode", chargeNotificationFormDO.getProjectCode());
		paramMap.put("projectName", chargeNotificationFormDO.getProjectName());
		paramMap.put("customerName", chargeNotificationFormDO.getCustomerName());
		paramMap.put("formStatus", chargeNotificationFormDO.getFormStatus());
		paramMap.put("chargeBasis", chargeNotificationFormDO.getChargeBasis());
		
		paramMap.put("deptIdList", chargeNotificationFormDO.getDeptIdList());
		paramMap.put("loginUserId", chargeNotificationFormDO.getLoginUserId());
		paramMap.put("draftUserId", chargeNotificationFormDO.getDraftUserId());
		paramMap.put("busiType", chargeNotificationFormDO.getBusiType());
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-CHARGE-NOTIFICATION-SEARCH-COUNT", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ChargeNotificationExportDO> searchChargeNotificationExport(	ChargeNotificationFormDO chargeNotificationFormDO,
																			long limitStart,
																			long pageSize,
																			String sortCol,
																			String sortOrder)
																								throws DataAccessException {
		if (chargeNotificationFormDO == null) {
			throw new IllegalArgumentException("chargeNotificationFormDO can`t by null");
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("limitStart", limitStart);
		paramMap.put("pageSize", pageSize);
		paramMap.put("sortCol", sortCol);
		paramMap.put("sortOrder", sortOrder);
		paramMap.put("projectCode", chargeNotificationFormDO.getProjectCode());
		paramMap.put("projectName", chargeNotificationFormDO.getProjectName());
		paramMap.put("customerName", chargeNotificationFormDO.getCustomerName());
		paramMap.put("formStatus", chargeNotificationFormDO.getFormStatus());
		paramMap.put("chargeBasis", chargeNotificationFormDO.getChargeBasis());
		
		paramMap.put("deptIdList", chargeNotificationFormDO.getDeptIdList());
		paramMap.put("loginUserId", chargeNotificationFormDO.getLoginUserId());
		paramMap.put("draftUserId", chargeNotificationFormDO.getDraftUserId());
		paramMap.put("busiType", chargeNotificationFormDO.getBusiType());
		List<ChargeNotificationExportDO> list = getSqlMapClientTemplate().queryForList(
			"RM-CHARGE-NOTIFICATION-EXPORT", paramMap);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SpecialPaperResultDO> searchSpecialPaperInvalidList(SpecialPaperResultDO specialPaperResultDO,
																	long limitStart, long pageSize,
																	Date applyTimeStart,
																	Date applyTimeEnd,
																	String sortCol,
																	String sortOrder,
																	Date invalidReceiveDateStart,
																	Date invalidReceiveDateEnd)
																								throws DataAccessException {
		if (specialPaperResultDO == null) {
			throw new IllegalArgumentException("specialPaperResultDO can`t by null");
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("limitStart", limitStart);
		paramMap.put("pageSize", pageSize);
		paramMap.put("sortCol", sortCol);
		paramMap.put("sortOrder", sortOrder);
		paramMap.put("applyTimeStart", applyTimeStart);
		paramMap.put("applyTimeEnd", applyTimeEnd);
		paramMap.put("invalidReceiveDateStart", invalidReceiveDateStart);
		paramMap.put("invalidReceiveDateEnd", invalidReceiveDateEnd);
		paramMap.put("projectName", specialPaperResultDO.getProjectName());
		paramMap.put("startNo", specialPaperResultDO.getStartNo());
		paramMap.put("endNo", specialPaperResultDO.getEndNo());
		paramMap.put("keepingManName", specialPaperResultDO.getKeepingManName());
		paramMap.put("receiveManName", specialPaperResultDO.getReceiveManName());
		paramMap.put("receiptMan", specialPaperResultDO.getReceiptMan());
		paramMap.put("receiptPlace", specialPaperResultDO.getReceiptPlace());
		paramMap.put("provideManName", specialPaperResultDO.getProvideManName());
		paramMap.put("isSaveInvail", specialPaperResultDO.getIsSaveInvail());
		List<SpecialPaperResultDO> list = getSqlMapClientTemplate().queryForList(
			"RM-SPECIAL-PAPER-INVALID-SEARCH", paramMap);
		return list;
	}
	
	@Override
	public long searchSpecialPaperInvalidListCount(SpecialPaperResultDO specialPaperResultDO,
													Date applyTimeStart, Date applyTimeEnd,
													Date invalidReceiveDateStart,
													Date invalidReceiveDateEnd)
																				throws DataAccessException {
		if (specialPaperResultDO == null) {
			throw new IllegalArgumentException("specialPaperResultDO can`t by null");
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("applyTimeStart", applyTimeStart);
		paramMap.put("applyTimeEnd", applyTimeEnd);
		paramMap.put("invalidReceiveDateStart", invalidReceiveDateStart);
		paramMap.put("invalidReceiveDateEnd", invalidReceiveDateEnd);
		paramMap.put("projectName", specialPaperResultDO.getProjectName());
		paramMap.put("startNo", specialPaperResultDO.getStartNo());
		paramMap.put("endNo", specialPaperResultDO.getEndNo());
		paramMap.put("keepingManName", specialPaperResultDO.getKeepingManName());
		paramMap.put("receiveManName", specialPaperResultDO.getReceiveManName());
		paramMap.put("receiptMan", specialPaperResultDO.getReceiptMan());
		paramMap.put("receiptPlace", specialPaperResultDO.getReceiptPlace());
		paramMap.put("provideManName", specialPaperResultDO.getProvideManName());
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-SPECIAL-PAPER-INVALID-SEARCH-COUNT", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SpecialPaperResultDO> searchSpecialPaperDeptList(	SpecialPaperResultDO specialPaperResultDO,
																	long limitStart, long pageSize,
																	Date applyTimeStart,
																	Date applyTimeEnd,
																	String sortCol, String sortOrder)
																										throws DataAccessException {
		if (specialPaperResultDO == null) {
			throw new IllegalArgumentException("specialPaperResultDO can`t by null");
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("limitStart", limitStart);
		paramMap.put("pageSize", pageSize);
		paramMap.put("sortCol", sortCol);
		paramMap.put("sortOrder", sortOrder);
		paramMap.put("applyTimeStart", applyTimeStart);
		paramMap.put("applyTimeEnd", applyTimeEnd);
		paramMap.put("projectName", specialPaperResultDO.getProjectName());
		paramMap.put("startNo", specialPaperResultDO.getStartNo());
		paramMap.put("endNo", specialPaperResultDO.getEndNo());
		paramMap.put("keepingManName", specialPaperResultDO.getKeepingManName());
		paramMap.put("receiveManName", specialPaperResultDO.getReceiveManName());
		paramMap.put("receiptMan", specialPaperResultDO.getReceiptMan());
		paramMap.put("receiptPlace", specialPaperResultDO.getReceiptPlace());
		paramMap.put("provideManName", specialPaperResultDO.getProvideManName());
		paramMap.put("deptName", specialPaperResultDO.getDeptName());
		List<SpecialPaperResultDO> list = getSqlMapClientTemplate().queryForList(
			"RM-SPECIAL-PAPER-DEPT-SEARCH", paramMap);
		return list;
	}
	
	@Override
	public long searchSpecialPaperDeptListCount(SpecialPaperResultDO specialPaperResultDO,
												Date applyTimeStart, Date applyTimeEnd)
																						throws DataAccessException {
		if (specialPaperResultDO == null) {
			throw new IllegalArgumentException("specialPaperResultDO can`t by null");
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("applyTimeStart", applyTimeStart);
		paramMap.put("applyTimeEnd", applyTimeEnd);
		paramMap.put("projectName", specialPaperResultDO.getProjectName());
		paramMap.put("startNo", specialPaperResultDO.getStartNo());
		paramMap.put("endNo", specialPaperResultDO.getEndNo());
		paramMap.put("keepingManName", specialPaperResultDO.getKeepingManName());
		paramMap.put("receiveManName", specialPaperResultDO.getReceiveManName());
		paramMap.put("receiptMan", specialPaperResultDO.getReceiptMan());
		paramMap.put("receiptPlace", specialPaperResultDO.getReceiptPlace());
		paramMap.put("provideManName", specialPaperResultDO.getProvideManName());
		paramMap.put("deptName", specialPaperResultDO.getDeptName());
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-SPECIAL-PAPER-DEPT-SEARCH-COUNT", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SpecialPaperResultDO> searchSpecialPaperProjectList(SpecialPaperResultDO specialPaperResultDO,
																	long limitStart, long pageSize,
																	Date applyTimeStart,
																	Date applyTimeEnd,
																	String sortCol, String sortOrder)
																										throws DataAccessException {
		if (specialPaperResultDO == null) {
			throw new IllegalArgumentException("specialPaperResultDO can`t by null");
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("limitStart", limitStart);
		paramMap.put("pageSize", pageSize);
		paramMap.put("sortCol", sortCol);
		paramMap.put("sortOrder", sortOrder);
		paramMap.put("applyTimeStart", applyTimeStart);
		paramMap.put("applyTimeEnd", applyTimeEnd);
		paramMap.put("projectName", specialPaperResultDO.getProjectName());
		paramMap.put("startNo", specialPaperResultDO.getStartNo());
		paramMap.put("endNo", specialPaperResultDO.getEndNo());
		paramMap.put("keepingManName", specialPaperResultDO.getKeepingManName());
		paramMap.put("receiveManName", specialPaperResultDO.getReceiveManName());
		paramMap.put("receiptMan", specialPaperResultDO.getReceiptMan());
		paramMap.put("receiptPlace", specialPaperResultDO.getReceiptPlace());
		paramMap.put("provideManName", specialPaperResultDO.getProvideManName());
		List<SpecialPaperResultDO> list = getSqlMapClientTemplate().queryForList(
			"RM-SPECIAL-PAPER-PROJECT-SEARCH", paramMap);
		return list;
	}
	
	@Override
	public long searchSpecialPaperProjectListCount(SpecialPaperResultDO specialPaperResultDO,
													Date applyTimeStart, Date applyTimeEnd)
																							throws DataAccessException {
		if (specialPaperResultDO == null) {
			throw new IllegalArgumentException("specialPaperResultDO can`t by null");
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("applyTimeStart", applyTimeStart);
		paramMap.put("applyTimeEnd", applyTimeEnd);
		paramMap.put("projectName", specialPaperResultDO.getProjectName());
		paramMap.put("startNo", specialPaperResultDO.getStartNo());
		paramMap.put("endNo", specialPaperResultDO.getEndNo());
		paramMap.put("keepingManName", specialPaperResultDO.getKeepingManName());
		paramMap.put("receiveManName", specialPaperResultDO.getReceiveManName());
		paramMap.put("receiptMan", specialPaperResultDO.getReceiptMan());
		paramMap.put("receiptPlace", specialPaperResultDO.getReceiptPlace());
		paramMap.put("provideManName", specialPaperResultDO.getProvideManName());
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-SPECIAL-PAPER-PROJECT-SEARCH-COUNT", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SpecialPaperResultDO> checkInvalidNo(SpecialPaperResultDO specialPaperResultDO)
																								throws DataAccessException {
		if (specialPaperResultDO == null) {
			throw new IllegalArgumentException("specialPaperResultDO can`t by null");
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("startNo", specialPaperResultDO.getStartNo());
		paramMap.put("endNo", specialPaperResultDO.getEndNo());
		List<SpecialPaperResultDO> list = getSqlMapClientTemplate().queryForList(
			"RM-SPECIAL-PAPER-INVALID-CHECK-SEARCH", paramMap);
		return list;
	}
	
	//授信条件 选择项目
	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectDO> searchCreditAndProjectList(ProjectCreditConditionFormDO projectDO,
														long limitStart, long pageSize,
														List<Long> deptIdList, String sortCol,
														String sortOrder)
																			throws DataAccessException {
		if (projectDO == null) {
			throw new IllegalArgumentException("setupForm can`t by null");
		}
		
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("limitStart", limitStart);
		paramMap.put("pageSize", pageSize);
		paramMap.put("sortOrder", sortOrder);
		paramMap.put("sortCol", sortCol);
		
		paramMap.put("projectId", projectDO.getProjectId());
		paramMap.put("customerId", projectDO.getCustomerId());
		paramMap.put("busiManagerId", projectDO.getBusiManagerId());
		paramMap.put("busiManagerbId", projectDO.getBusiManagerbId());
		paramMap.put("projectCode", projectDO.getProjectCode());
		paramMap.put("projectCodeOrName", projectDO.getProjectCodeOrName());
		
		paramMap.put("busiType", projectDO.getBusiType());
		paramMap.put("busiTypeName", projectDO.getBusiTypeName());
		paramMap.put("projectName", projectDO.getProjectName());
		paramMap.put("customerName", projectDO.getCustomerName());
		paramMap.put("busiManagerName", projectDO.getBusiManagerName());
		paramMap.put("busiManagerbName", projectDO.getBusiManagerbName());
		paramMap.put("isContinue", projectDO.getIsContinue());
		paramMap.put("deptId", projectDO.getDeptId());
		paramMap.put("deptName", projectDO.getDeptName());
		paramMap.put("deptCode", projectDO.getDeptCode());
		paramMap.put("deptPath", projectDO.getDeptPath());
		paramMap.put("deptPathName", projectDO.getDeptPathName());
		paramMap.put("phasesStatus", projectDO.getPhasesStatus());
		paramMap.put("deptIdList", deptIdList);
		List<ProjectDO> list = getSqlMapClientTemplate().queryForList(
			"MS-EXTRA-FORM-CREDIT-AND-PROJECT-SEARCH", paramMap);
		
		return list;
	}
	
	@Override
	public long searchCreditAndProjectCount(ProjectCreditConditionFormDO projectDO,
											List<Long> deptIdList) throws DataAccessException {
		if (projectDO == null) {
			throw new IllegalArgumentException("setupForm can`t by null");
		}
		
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("projectId", projectDO.getProjectId());
		paramMap.put("customerId", projectDO.getCustomerId());
		paramMap.put("busiManagerId", projectDO.getBusiManagerId());
		paramMap.put("busiManagerbId", projectDO.getBusiManagerbId());
		paramMap.put("projectCode", projectDO.getProjectCode());
		paramMap.put("projectCodeOrName", projectDO.getProjectCodeOrName());
		paramMap.put("busiType", projectDO.getBusiType());
		paramMap.put("busiTypeName", projectDO.getBusiTypeName());
		paramMap.put("projectName", projectDO.getProjectName());
		paramMap.put("customerName", projectDO.getCustomerName());
		paramMap.put("busiManagerName", projectDO.getBusiManagerName());
		paramMap.put("busiManagerbName", projectDO.getBusiManagerbName());
		
		paramMap.put("deptId", projectDO.getDeptId());
		paramMap.put("deptName", projectDO.getDeptName());
		paramMap.put("deptCode", projectDO.getDeptCode());
		paramMap.put("deptPath", projectDO.getDeptPath());
		paramMap.put("deptPathName", projectDO.getDeptPathName());
		paramMap.put("phasesStatus", projectDO.getPhasesStatus());
		paramMap.put("deptIdList", deptIdList);
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-FORM-CREDIT-AND-PROJECT-COUNT", paramMap);
	}
	
	//需求变更，为使用
	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectDO> searchConsignmentSalesList(ProjectDO projectDO, long limitStart,
														long pageSize, List<Long> deptIdList,
														String sortCol, String sortOrder)
																							throws DataAccessException {
		if (projectDO == null) {
			throw new IllegalArgumentException("setupForm can`t by null");
		}
		
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("limitStart", limitStart);
		paramMap.put("pageSize", pageSize);
		paramMap.put("sortOrder", sortOrder);
		paramMap.put("sortCol", sortCol);
		
		paramMap.put("projectId", projectDO.getProjectId());
		paramMap.put("customerId", projectDO.getCustomerId());
		paramMap.put("busiManagerId", projectDO.getBusiManagerId());
		paramMap.put("busiManagerbId", projectDO.getBusiManagerbId());
		paramMap.put("projectCode", projectDO.getProjectCode());
		paramMap.put("busiType", projectDO.getBusiType());
		paramMap.put("busiTypeName", projectDO.getBusiTypeName());
		paramMap.put("projectName", projectDO.getProjectName());
		paramMap.put("customerName", projectDO.getCustomerName());
		paramMap.put("busiManagerName", projectDO.getBusiManagerName());
		paramMap.put("busiManagerbName", projectDO.getBusiManagerbName());
		paramMap.put("isContinue", projectDO.getIsContinue());
		paramMap.put("deptId", projectDO.getDeptId());
		paramMap.put("deptName", projectDO.getDeptName());
		paramMap.put("deptCode", projectDO.getDeptCode());
		paramMap.put("deptPath", projectDO.getDeptPath());
		paramMap.put("deptPathName", projectDO.getDeptPathName());
		paramMap.put("deptIdList", deptIdList);
		List<ProjectDO> list = getSqlMapClientTemplate().queryForList(
			"MS-EXTRA-FORM-PROJECT-CONSIGNMENT-SALES-SEARCH", paramMap);
		
		return list;
	}
	
	//需求变更，未使用
	@Override
	public long searchConsignmentSalesCount(ProjectDO projectDO, List<Long> deptIdList)
																						throws DataAccessException {
		if (projectDO == null) {
			throw new IllegalArgumentException("setupForm can`t by null");
		}
		
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("projectId", projectDO.getProjectId());
		paramMap.put("customerId", projectDO.getCustomerId());
		paramMap.put("busiManagerId", projectDO.getBusiManagerId());
		paramMap.put("busiManagerbId", projectDO.getBusiManagerbId());
		paramMap.put("projectCode", projectDO.getProjectCode());
		paramMap.put("busiType", projectDO.getBusiType());
		paramMap.put("busiTypeName", projectDO.getBusiTypeName());
		paramMap.put("projectName", projectDO.getProjectName());
		paramMap.put("customerName", projectDO.getCustomerName());
		paramMap.put("busiManagerName", projectDO.getBusiManagerName());
		paramMap.put("busiManagerbName", projectDO.getBusiManagerbName());
		
		paramMap.put("deptId", projectDO.getDeptId());
		paramMap.put("deptName", projectDO.getDeptName());
		paramMap.put("deptCode", projectDO.getDeptCode());
		paramMap.put("deptPath", projectDO.getDeptPath());
		paramMap.put("deptPathName", projectDO.getDeptPathName());
		paramMap.put("deptIdList", deptIdList);
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-FORM-PROJECT-CONSIGNMENT-SALES-SEARCH-COUNT", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CapitalAppropriationApplyFormDO> searchCapitalAppropriationList(CapitalAppropriationApplyFormDO DO,
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
		//		paramMap.put("customerId", DO.getCustomerId());
		
		paramMap.put("projectType", DO.getProjectType());
		paramMap.put("applyId", DO.getApplyId());
		paramMap.put("isSimple", DO.getIsSimple());
		
		paramMap.put("projectCode", DO.getProjectCode());
		paramMap.put("busiType", DO.getBusiType());
		paramMap.put("busiTypeName", DO.getBusiTypeName());
		paramMap.put("projectName", DO.getProjectName());
		//		paramMap.put("customerName", DO.getCustomerName());
		
		paramMap.put("loginUserId", DO.getLoginUserId());
		paramMap.put("deptIdList", DO.getDeptIdList());
		paramMap.put("draftUserId", DO.getDraftUserId());
		paramMap.put("statusList", DO.getStatusList());
		
		//		paramMap.put("busiManagerbName", DO.getBusiManagerbName());
		//		paramMap.put("busiManagerbName", DO.getBusiManagerbName());
		paramMap.put("submitTimeStart", submitTimeStart);
		paramMap.put("submitTimeEnd", submitTimeEnd);
		paramMap.put("deptIdList", deptIdList);
		paramMap.put("appropriateReasonList", DO.getAppropriateReasonList());
		List<CapitalAppropriationApplyFormDO> list = getSqlMapClientTemplate().queryForList(
			"MS-EXTRA-FORM-PROJECT-CAPITAL-APPROPRIATION-SEARCH", paramMap);
		
		return list;
		
	}
	
	@Override
	public long searchCapitalAppropriationCount(CapitalAppropriationApplyFormDO DO,
												Date submitTimeStart, Date submitTimeEnd,
												List<Long> deptIdList) throws DataAccessException {
		if (DO == null) {
			throw new IllegalArgumentException("setupForm can`t by null");
		}
		
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("formStatus", DO.getFormStatus());
		paramMap.put("formUserName", DO.getFormUserName());
		paramMap.put("formId", DO.getFormId());
		//		paramMap.put("customerId", DO.getCustomerId());
		paramMap.put("projectType", DO.getProjectType());
		paramMap.put("applyId", DO.getApplyId());
		paramMap.put("isSimple", DO.getIsSimple());
		
		paramMap.put("projectCode", DO.getProjectCode());
		paramMap.put("busiType", DO.getBusiType());
		paramMap.put("busiTypeName", DO.getBusiTypeName());
		paramMap.put("projectName", DO.getProjectName());
		//		paramMap.put("customerName", DO.getCustomerName());
		
		paramMap.put("loginUserId", DO.getLoginUserId());
		paramMap.put("deptIdList", DO.getDeptIdList());
		paramMap.put("draftUserId", DO.getDraftUserId());
		paramMap.put("statusList", DO.getStatusList());
		//		paramMap.put("busiManagerbName", DO.getBusiManagerbName());
		//		paramMap.put("busiManagerbName", DO.getBusiManagerbName());
		paramMap.put("submitTimeStart", submitTimeStart);
		paramMap.put("submitTimeEnd", submitTimeEnd);
		paramMap.put("deptIdList", deptIdList);
		
		paramMap.put("appropriateReasonList", DO.getAppropriateReasonList());
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-FORM-PROJECT-CAPITAL-APPROPRIATION-SEARCH-COUNT", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectDO> searchBondList(ProjectIssueInformationDO issueDO, long limitStart,
											long pageSize, List<Long> deptIdList, String sortCol,
											String sortOrder) throws DataAccessException {
		if (issueDO == null) {
			throw new IllegalArgumentException("setupForm can`t by null");
		}
		
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("limitStart", limitStart);
		paramMap.put("pageSize", pageSize);
		paramMap.put("sortOrder", sortOrder);
		paramMap.put("sortCol", sortCol);
		
		paramMap.put("projectId", issueDO.getProjectId());
		paramMap.put("customerId", issueDO.getCustomerId());
		paramMap.put("busiManagerId", issueDO.getBusiManagerId());
		paramMap.put("busiManagerbId", issueDO.getBusiManagerbId());
		paramMap.put("projectCode", issueDO.getProjectCode());
		paramMap.put("busiType", issueDO.getBusiType());
		paramMap.put("busiTypeName", issueDO.getBusiTypeName());
		paramMap.put("projectName", issueDO.getProjectName());
		paramMap.put("customerName", issueDO.getCustomerName());
		paramMap.put("busiManagerName", issueDO.getBusiManagerName());
		paramMap.put("busiManagerbName", issueDO.getBusiManagerbName());
		paramMap.put("isContinue", issueDO.getIsContinue());
		
		paramMap.put("deptId", issueDO.getDeptId());
		paramMap.put("deptName", issueDO.getDeptName());
		paramMap.put("deptCode", issueDO.getDeptCode());
		paramMap.put("deptPath", issueDO.getDeptPath());
		paramMap.put("deptPathName", issueDO.getDeptPathName());
		
		paramMap.put("statusList", issueDO.getStatusList());
		paramMap.put("phasesStatus", issueDO.getPhasesStatus());
		
		paramMap.put("loginUserId", issueDO.getLoginUserId());
		paramMap.put("deptIdList", issueDO.getDeptIdList());
		paramMap.put("draftUserId", issueDO.getDraftUserId());
		paramMap.put("deptIdList", deptIdList);
		List<ProjectDO> list = getSqlMapClientTemplate().queryForList(
			"MS-EXTRA-FORM-PROJECT-BOND-SEARCH", paramMap);
		
		return list;
	}
	
	@Override
	public long searchBondCount(ProjectIssueInformationDO issueDO, List<Long> deptIdList)
																							throws DataAccessException {
		if (issueDO == null) {
			throw new IllegalArgumentException("setupForm can`t by null");
		}
		
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("projectId", issueDO.getProjectId());
		paramMap.put("customerId", issueDO.getCustomerId());
		paramMap.put("busiManagerId", issueDO.getBusiManagerId());
		paramMap.put("busiManagerbId", issueDO.getBusiManagerbId());
		paramMap.put("projectCode", issueDO.getProjectCode());
		paramMap.put("busiType", issueDO.getBusiType());
		paramMap.put("busiTypeName", issueDO.getBusiTypeName());
		paramMap.put("projectName", issueDO.getProjectName());
		paramMap.put("customerName", issueDO.getCustomerName());
		paramMap.put("busiManagerName", issueDO.getBusiManagerName());
		paramMap.put("busiManagerbName", issueDO.getBusiManagerbName());
		
		paramMap.put("deptId", issueDO.getDeptId());
		paramMap.put("deptName", issueDO.getDeptName());
		paramMap.put("deptCode", issueDO.getDeptCode());
		paramMap.put("deptPath", issueDO.getDeptPath());
		paramMap.put("deptPathName", issueDO.getDeptPathName());
		paramMap.put("statusList", issueDO.getStatusList());
		paramMap.put("phasesStatus", issueDO.getPhasesStatus());
		paramMap.put("draftUserId", issueDO.getDraftUserId());
		paramMap.put("loginUserId", issueDO.getLoginUserId());
		paramMap.put("deptIdList", issueDO.getDeptIdList());
		paramMap.put("deptIdList", deptIdList);
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-FORM-PROJECT-BOND-SEARCH-COUNT", paramMap);
	}
	
	@Override
	public long searchAvailableReleaseSelectProjectCount(Map<String, Object> paramMap)
																						throws DataAccessException {
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-AVAILABLE-RELEASE-PROJECT-COUNT", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectDO> searchAvailableReleaseSelectProject(Map<String, Object> paramMap)
																							throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("MS-EXTRA-AVAILABLE-RELEASE-PROJECT-SEARCH",
			paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AfterwardsSummaryReleasingAmountDO> searchSummaryLoanedAmount(	AfterwardsSummaryReleasingAmountDO summaryDO)
																																throws DataAccessException {
		if (summaryDO == null) {
			throw new IllegalArgumentException("setupForm can`t by null");
		}
		
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("busiType", summaryDO.getBusiType());
		paramMap.put("busiTypeList", summaryDO.getBusiTypeList());
		paramMap.put("finishTime", DateUtil.dtSimpleFormat(summaryDO.getFinishTime()));
		paramMap.put("deptCode", summaryDO.getDeptCode());
		List<AfterwardsSummaryReleasingAmountDO> list = getSqlMapClientTemplate().queryForList(
			"MS-EXTRA-AFTERWARDS-PROJECT-SUMMARY-LOANED-AMOUNT-SEARCH", paramMap);
		
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AfterwardsSummaryReleasingAmountDO> searchSummaryApplyAmount(	AfterwardsSummaryReleasingAmountDO summaryDO)
																																throws DataAccessException {
		if (summaryDO == null) {
			throw new IllegalArgumentException("setupForm can`t by null");
		}
		
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("busiType", summaryDO.getBusiType());
		paramMap.put("busiTypeList", summaryDO.getBusiTypeList());
		paramMap.put("finishTime", DateUtil.dtSimpleFormat(summaryDO.getFinishTime()));
		paramMap.put("deptCode", summaryDO.getDeptCode());
		List<AfterwardsSummaryReleasingAmountDO> list = getSqlMapClientTemplate().queryForList(
			"MS-EXTRA-AFTERWARDS-PROJECT-SUMMARY-APPLY-AMOUNT-SEARCH", paramMap);
		
		return list;
	}
	
	@Override
	public long searchSummaryCount(AfterwardsSummaryReleasingAmountDO summaryDO)
																				throws DataAccessException {
		if (summaryDO == null) {
			throw new IllegalArgumentException("setupForm can`t by null");
		}
		
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("busiType", summaryDO.getBusiType());
		paramMap.put("busiTypeList", summaryDO.getBusiTypeList());
		paramMap.put("finishTime", DateUtil.dtSimpleFormat(summaryDO.getFinishTime()));
		paramMap.put("deptCode", summaryDO.getDeptCode());
		Long formId = (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-AFTERWARDS-PROJECT-SUMMARY-SEARCH-COUNT", paramMap);
		return null == formId ? 0L : formId.longValue();
	}
	
	@Override
	public long queryCheckReportIncomeFormId(Map<String, Object> param) throws DataAccessException {
		Long formId = (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-CHECK-REPORT-INCOME-FORMID", param);
		return null == formId ? 0L : formId.longValue();
	}
	
	@Override
	public long queryNewestFinancialKpiReviewId(String projectCode) throws DataAccessException {
		Long reviewId = (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-NEWEST-FINANCIAL-KPI-REVIEW-ID", projectCode);
		return null == reviewId ? 0L : reviewId.longValue();
	}
	
	@Override
	public long searchAfterwardsCheckCount(Map<String, Object> paramMap) throws DataAccessException {
		return (Long) getSqlMapClientTemplate().queryForObject("MS-EXTRA-AFTER-CHECK-COUNT",
			paramMap);
	}
	
	@Override
	public long searchAfterwardsCheckCountEdition(Map<String, Object> paramMap)
																				throws DataAccessException {
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-AFTER-CHECK-COUNT-EDITION", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AfterwardsCheckDO> searchAfterwardsCheck(Map<String, Object> paramMap)
																						throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("MS-EXTRA-AFTER-CHECK-SEARCH", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectDO> searchConsentIssueNoticeList(ConsentIssueNoticeDO noticeDO,
														long limitStart, long pageSize,
														List<Long> deptIdList, String sortCol,
														String sortOrder)
																			throws DataAccessException {
		if (noticeDO == null) {
			throw new IllegalArgumentException("setupForm can`t by null");
		}
		
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("limitStart", limitStart);
		paramMap.put("pageSize", pageSize);
		paramMap.put("sortOrder", sortOrder);
		paramMap.put("sortCol", sortCol);
		
		paramMap.put("projectId", noticeDO.getProjectId());
		paramMap.put("customerId", noticeDO.getCustomerId());
		paramMap.put("busiManagerId", noticeDO.getBusiManagerId());
		paramMap.put("busiManagerbId", noticeDO.getBusiManagerbId());
		paramMap.put("projectCode", noticeDO.getProjectCode());
		paramMap.put("busiType", noticeDO.getBusiType());
		paramMap.put("busiTypeName", noticeDO.getBusiTypeName());
		paramMap.put("projectName", noticeDO.getProjectName());
		paramMap.put("customerName", noticeDO.getCustomerName());
		paramMap.put("busiManagerName", noticeDO.getBusiManagerName());
		paramMap.put("busiManagerbName", noticeDO.getBusiManagerbName());
		paramMap.put("isContinue", noticeDO.getIsContinue());
		
		paramMap.put("deptId", noticeDO.getDeptId());
		paramMap.put("deptName", noticeDO.getDeptName());
		paramMap.put("deptCode", noticeDO.getDeptCode());
		paramMap.put("deptPath", noticeDO.getDeptPath());
		paramMap.put("deptPathName", noticeDO.getDeptPathName());
		paramMap.put("loginUserId", noticeDO.getLoginUserId());
		paramMap.put("draftUserId", noticeDO.getDraftUserId());
		
		paramMap.put("deptIdList", noticeDO.getDeptIdList());
		paramMap.put("statusList", noticeDO.getStatusList());
		paramMap.put("phasesStatus", noticeDO.getPhasesStatus());
		paramMap.put("deptIdList", deptIdList);
		List<ProjectDO> list = getSqlMapClientTemplate().queryForList(
			"MS-EXTRA-FORM-PROJECT-CONSENT-ISSUE-NOTICE-SEARCH", paramMap);
		
		return list;
	}
	
	@Override
	public long searchConsentIssueNoticeCount(ConsentIssueNoticeDO noticeDO, List<Long> deptIdList)
																									throws DataAccessException {
		if (noticeDO == null) {
			throw new IllegalArgumentException("setupForm can`t by null");
		}
		
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("projectId", noticeDO.getProjectId());
		paramMap.put("customerId", noticeDO.getCustomerId());
		paramMap.put("busiManagerId", noticeDO.getBusiManagerId());
		paramMap.put("busiManagerbId", noticeDO.getBusiManagerbId());
		paramMap.put("projectCode", noticeDO.getProjectCode());
		paramMap.put("busiType", noticeDO.getBusiType());
		paramMap.put("busiTypeName", noticeDO.getBusiTypeName());
		paramMap.put("projectName", noticeDO.getProjectName());
		paramMap.put("customerName", noticeDO.getCustomerName());
		paramMap.put("busiManagerName", noticeDO.getBusiManagerName());
		paramMap.put("busiManagerbName", noticeDO.getBusiManagerbName());
		
		paramMap.put("deptId", noticeDO.getDeptId());
		paramMap.put("deptName", noticeDO.getDeptName());
		paramMap.put("deptCode", noticeDO.getDeptCode());
		paramMap.put("deptPath", noticeDO.getDeptPath());
		paramMap.put("deptPathName", noticeDO.getDeptPathName());
		
		paramMap.put("loginUserId", noticeDO.getLoginUserId());
		paramMap.put("draftUserId", noticeDO.getDraftUserId());
		paramMap.put("deptIdList", noticeDO.getDeptIdList());
		paramMap.put("statusList", noticeDO.getStatusList());
		
		paramMap.put("phasesStatus", noticeDO.getPhasesStatus());
		paramMap.put("deptIdList", deptIdList);
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-FORM-PROJECT-CONSENT-ISSUE-NOTICE-SEARCH-COUNT", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ConsentIssueNoticeDO> searchConsentIssueNoticeListList(	ConsentIssueNoticeDO noticeDO,
																		long limitStart,
																		long pageSize,
																		List<Long> deptIdList,
																		String sortCol,
																		String sortOrder)
																							throws DataAccessException {
		if (noticeDO == null) {
			throw new IllegalArgumentException("setupForm can`t by null");
		}
		
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("limitStart", limitStart);
		paramMap.put("pageSize", pageSize);
		paramMap.put("sortOrder", sortOrder);
		paramMap.put("sortCol", sortCol);
		
		paramMap.put("projectId", noticeDO.getProjectId());
		paramMap.put("customerId", noticeDO.getCustomerId());
		paramMap.put("busiManagerId", noticeDO.getBusiManagerId());
		paramMap.put("busiManagerbId", noticeDO.getBusiManagerbId());
		paramMap.put("projectCode", noticeDO.getProjectCode());
		paramMap.put("busiType", noticeDO.getBusiType());
		paramMap.put("busiTypeName", noticeDO.getBusiTypeName());
		paramMap.put("projectName", noticeDO.getProjectName());
		paramMap.put("customerName", noticeDO.getCustomerName());
		paramMap.put("busiManagerName", noticeDO.getBusiManagerName());
		paramMap.put("busiManagerbName", noticeDO.getBusiManagerbName());
		paramMap.put("isContinue", noticeDO.getIsContinue());
		
		paramMap.put("isUploadReceipt", noticeDO.getIsUploadReceipt()); //是否上传回执
		
		paramMap.put("deptId", noticeDO.getDeptId());
		paramMap.put("deptName", noticeDO.getDeptName());
		paramMap.put("deptCode", noticeDO.getDeptCode());
		paramMap.put("deptPath", noticeDO.getDeptPath());
		paramMap.put("deptPathName", noticeDO.getDeptPathName());
		
		paramMap.put("loginUserId", noticeDO.getLoginUserId());
		paramMap.put("draftUserId", noticeDO.getDraftUserId());
		paramMap.put("deptIdList", noticeDO.getDeptIdList());
		
		paramMap.put("statusList", noticeDO.getStatusList());
		paramMap.put("deptIdList", deptIdList);
		List<ConsentIssueNoticeDO> list = getSqlMapClientTemplate().queryForList(
			"MS-EXTRA-FORM-PROJECT-CONSENT-ISSUE-NOTICE-LIST-SEARCH", paramMap);
		
		return list;
	}
	
	@Override
	public long searchConsentIssueNoticelistCount(ConsentIssueNoticeDO noticeDO,
													List<Long> deptIdList)
																			throws DataAccessException {
		if (noticeDO == null) {
			throw new IllegalArgumentException("setupForm can`t by null");
		}
		
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("projectId", noticeDO.getProjectId());
		paramMap.put("customerId", noticeDO.getCustomerId());
		paramMap.put("busiManagerId", noticeDO.getBusiManagerId());
		paramMap.put("busiManagerbId", noticeDO.getBusiManagerbId());
		paramMap.put("projectCode", noticeDO.getProjectCode());
		paramMap.put("busiType", noticeDO.getBusiType());
		paramMap.put("busiTypeName", noticeDO.getBusiTypeName());
		paramMap.put("projectName", noticeDO.getProjectName());
		paramMap.put("customerName", noticeDO.getCustomerName());
		paramMap.put("busiManagerName", noticeDO.getBusiManagerName());
		paramMap.put("busiManagerbName", noticeDO.getBusiManagerbName());
		
		paramMap.put("isUploadReceipt", noticeDO.getIsUploadReceipt()); //是否上传回执
		
		paramMap.put("deptId", noticeDO.getDeptId());
		paramMap.put("deptName", noticeDO.getDeptName());
		paramMap.put("deptCode", noticeDO.getDeptCode());
		paramMap.put("deptPath", noticeDO.getDeptPath());
		paramMap.put("deptPathName", noticeDO.getDeptPathName());
		paramMap.put("loginUserId", noticeDO.getLoginUserId());
		paramMap.put("draftUserId", noticeDO.getDraftUserId());
		paramMap.put("deptIdList", noticeDO.getDeptIdList());
		
		paramMap.put("statusList", noticeDO.getStatusList());
		paramMap.put("deptIdList", deptIdList);
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-FORM-PROJECT-CONSENT-ISSUE-NOTICE-LIST-COUNT", paramMap);
	}
	
	//退费申请
	@SuppressWarnings("unchecked")
	@Override
	public List<FRefundApplicationDO> searchRefundApplicationList(FRefundApplicationDO DO,
																	long limitStart, long pageSize,
																	Date submitTimeStart,
																	Date submitTimeEnd,
																	List<Long> deptIdList,
																	String sortCol, String sortOrder)
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
		
		paramMap.put("applyId", DO.getRefundId());
		
		paramMap.put("projectCode", DO.getProjectCode());
		paramMap.put("busiType", DO.getBusiType());
		paramMap.put("busiTypeName", DO.getBusiTypeName());
		paramMap.put("projectName", DO.getProjectName());
		
		paramMap.put("loginUserId", DO.getLoginUserId());
		paramMap.put("draftUserId", DO.getDraftUserId());
		
		paramMap.put("deptIdList", DO.getDeptIdList());
		paramMap.put("statusList", DO.getStatusList());
		
		//		paramMap.put("busiManagerbName", DO.getBusiManagerbName());
		//		paramMap.put("busiManagerbName", DO.getBusiManagerbName());
		paramMap.put("submitTimeStart", submitTimeStart);
		paramMap.put("submitTimeEnd", submitTimeEnd);
		paramMap.put("deptIdList", deptIdList);
		List<FRefundApplicationDO> list = getSqlMapClientTemplate().queryForList(
			"MS-EXTRA-FORM-PROJECT-REFUND-APPLICATION-SEARCH", paramMap);
		
		return list;
	}
	
	@Override
	public long searchRefundApplicationCount(FRefundApplicationDO DO, Date submitTimeStart,
												Date submitTimeEnd, List<Long> deptIdList)
																							throws DataAccessException {
		if (DO == null) {
			throw new IllegalArgumentException("setupForm can`t by null");
		}
		
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("formStatus", DO.getFormStatus());
		paramMap.put("formUserName", DO.getFormUserName());
		paramMap.put("formId", DO.getFormId());
		paramMap.put("applyId", DO.getRefundId());
		paramMap.put("projectCode", DO.getProjectCode());
		paramMap.put("busiType", DO.getBusiType());
		paramMap.put("busiTypeName", DO.getBusiTypeName());
		paramMap.put("projectName", DO.getProjectName());
		//		paramMap.put("customerName", DO.getCustomerName());
		
		paramMap.put("loginUserId", DO.getLoginUserId());
		paramMap.put("draftUserId", DO.getDraftUserId());
		
		paramMap.put("deptIdList", DO.getDeptIdList());
		paramMap.put("statusList", DO.getStatusList());
		//		paramMap.put("busiManagerbName", DO.getBusiManagerbName());
		//		paramMap.put("busiManagerbName", DO.getBusiManagerbName());
		paramMap.put("submitTimeStart", submitTimeStart);
		paramMap.put("submitTimeEnd", submitTimeEnd);
		paramMap.put("deptIdList", deptIdList);
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-FORM-PROJECT-REFUND-APPLICATION-SEARCH-COUNT", paramMap);
	}
	
	//退费申请 选择列表
	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectDO> searchRefundApplicationSelectList(ProjectDO formProjectDO,
																long limitStart, long pageSize,
																String sortCol, String sortOrder)
																									throws DataAccessException {
		if (formProjectDO == null) {
			throw new IllegalArgumentException("setupForm can`t by null");
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("limitStart", limitStart);
		paramMap.put("pageSize", pageSize);
		paramMap.put("sortOrder", sortOrder);
		paramMap.put("sortCol", sortCol);
		
		paramMap.put("projectId", formProjectDO.getProjectId());
		paramMap.put("customerId", formProjectDO.getCustomerId());
		paramMap.put("busiManagerId", formProjectDO.getBusiManagerId());
		paramMap.put("busiManagerbId", formProjectDO.getBusiManagerbId());
		paramMap.put("projectCode", formProjectDO.getProjectCode());
		paramMap.put("busiType", formProjectDO.getBusiType());
		paramMap.put("busiTypeName", formProjectDO.getBusiTypeName());
		paramMap.put("projectName", formProjectDO.getProjectName());
		paramMap.put("customerName", formProjectDO.getCustomerName());
		paramMap.put("busiManagerName", formProjectDO.getBusiManagerName());
		paramMap.put("busiManagerbName", formProjectDO.getBusiManagerbName());
		//		paramMap.put("submitTimeStart", submitTimeStart);
		//		paramMap.put("submitTimeEnd", submitTimeEnd);
		paramMap.put("deptId", formProjectDO.getDeptId());
		paramMap.put("deptName", formProjectDO.getDeptName());
		paramMap.put("deptCode", formProjectDO.getDeptCode());
		paramMap.put("deptPath", formProjectDO.getDeptPath());
		paramMap.put("deptPathName", formProjectDO.getDeptPathName());
		List<ProjectDO> list = getSqlMapClientTemplate().queryForList(
			"RM-REFUND-APPLICATION-SEARCH", paramMap);
		return list;
	}
	
	@Override
	public long searchRefundApplicationSelectCount(ProjectDO formProjectDO)
																			throws DataAccessException {
		if (formProjectDO == null) {
			throw new IllegalArgumentException("setupForm can`t by null");
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("projectId", formProjectDO.getProjectId());
		paramMap.put("customerId", formProjectDO.getCustomerId());
		paramMap.put("busiManagerId", formProjectDO.getBusiManagerId());
		paramMap.put("busiManagerbId", formProjectDO.getBusiManagerbId());
		paramMap.put("projectCode", formProjectDO.getProjectCode());
		paramMap.put("busiType", formProjectDO.getBusiType());
		paramMap.put("busiTypeName", formProjectDO.getBusiTypeName());
		paramMap.put("projectName", formProjectDO.getProjectName());
		paramMap.put("customerName", formProjectDO.getCustomerName());
		paramMap.put("busiManagerName", formProjectDO.getBusiManagerName());
		paramMap.put("busiManagerbName", formProjectDO.getBusiManagerbName());
		//		paramMap.put("submitTimeStart", submitTimeStart);
		//		paramMap.put("submitTimeEnd", submitTimeEnd);
		paramMap.put("deptId", formProjectDO.getDeptId());
		paramMap.put("deptName", formProjectDO.getDeptName());
		paramMap.put("deptCode", formProjectDO.getDeptCode());
		paramMap.put("deptPath", formProjectDO.getDeptPath());
		paramMap.put("deptPathName", formProjectDO.getDeptPathName());
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-REFUND-APPLICATION-SEARCH-COUNT", paramMap);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<ProjectIssueInformationDO> queryReleasableProject(Date today) {
		List<ProjectIssueInformationDO> list = getSqlMapClientTemplate().queryForList(
			"MS-BOND-EXPIRE-PROJECT-SEARCH", today);
		return list;
	}
	
	@Override
	public long searchRepayProjectCount(Map<String, Object> paramMap) throws DataAccessException {
		Long count = (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-REPAY-PROJECT-COUNT", paramMap);
		return null == count ? 0L : count.longValue();
	}
	
	//授信条件 选择项目
	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectDO> searchRepayProjectList(Map<String, Object> paramMap)
																				throws DataAccessException {
		List<ProjectDO> list = getSqlMapClientTemplate().queryForList(
			"MS-EXTRA-REPAY-PROJECT-SEARCH", paramMap);
		
		return list;
	}
	
	@Override
	public long searchNoRepayProjectCount(Map<String, Object> paramMap) throws DataAccessException {
		Long count = (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-NO-REPAY-PROJECT-COUNT", paramMap);
		return null == count ? 0L : count.longValue();
	}
	
	//授信条件 选择项目
	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectDO> searchNoRepayProjectList(Map<String, Object> paramMap)
																					throws DataAccessException {
		List<ProjectDO> list = getSqlMapClientTemplate().queryForList(
			"MS-EXTRA-NO-REPAY-PROJECT-SEARCH", paramMap);
		
		return list;
	}
	
	@Override
	public long searchAfterCheckProjectCount(Map<String, Object> paramMap)
																			throws DataAccessException {
		Long count = (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-AFTER-CHECK-PROJECT-COUNT", paramMap);
		return null == count ? 0L : count.longValue();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectDO> searchAfterCheckProjectList(Map<String, Object> paramMap)
																					throws DataAccessException {
		List<ProjectDO> list = getSqlMapClientTemplate().queryForList(
			"MS-EXTRA-AFTER-CHECK-PROJECT-SEARCH", paramMap);
		
		return list;
	}
	
	@Override
	public long searchAfterCheckLitigationProjectCount(Map<String, Object> paramMap)
																					throws DataAccessException {
		Long count = (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-AFTER-CHECK-LITIGATION-PROJECT-COUNT", paramMap);
		return null == count ? 0L : count.longValue();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectDO> searchAfterCheckLitigationProjectList(Map<String, Object> paramMap)
																								throws DataAccessException {
		List<ProjectDO> list = getSqlMapClientTemplate().queryForList(
			"MS-EXTRA-AFTER-CHECK-LITIGATION-PROJECT-SEARCH", paramMap);
		
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<FormChangeApplySearchDO> searchFormChangeApply(	FormChangeApplySearchDO queryCondition)
																										throws DataAccessException {
		if (queryCondition == null) {
			throw new IllegalArgumentException("FormChangeApplySearchDO can`t by null");
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("limitStart", queryCondition.getLimitStart());
		paramMap.put("pageSize", queryCondition.getPageSize());
		paramMap.put("sortOrder", queryCondition.getSortOrder());
		paramMap.put("sortCol", queryCondition.getSortCol());
		
		paramMap.put("formId", queryCondition.getFormId());
		paramMap.put("formUserId", queryCondition.getFormUserId());
		paramMap.put("applyId", queryCondition.getApplyId());
		paramMap.put("customerId", queryCondition.getCustomerId());
		paramMap.put("customerName", queryCondition.getCustomerName());
		paramMap.put("projectCode", queryCondition.getProjectCode());
		paramMap.put("likeProjectCode", queryCondition.getLikeProjectCode());
		paramMap.put("projectName", queryCondition.getProjectName());
		paramMap.put("formStatus", queryCondition.getFormStatus());
		paramMap.put("busiType", queryCondition.getBusiType());
		paramMap.put("changeForm", queryCondition.getChangeForm());
		paramMap.put("statusList", queryCondition.getStatusList());
		paramMap.put("applyTitle", queryCondition.getApplyTitle());
		paramMap.put("applyCode", queryCondition.getApplyCode());
		paramMap.put("likeApplyCode", queryCondition.getLikeApplyCode());
		paramMap.put("isNeedCouncil", queryCondition.getIsNeedCouncil());
		paramMap.put("isSelForCharge", queryCondition.getIsSelForCharge());
		paramMap.put("isSelXinHui", queryCondition.getIsSelXinHui());
		
		paramMap.put("loginUserId", queryCondition.getLoginUserId());
		paramMap.put("deptIdList", queryCondition.getDeptIdList());
		paramMap.put("draftUserId", queryCondition.getDraftUserId());
		
		List<FormChangeApplySearchDO> list = getSqlMapClientTemplate().queryForList(
			"MS-EXTRA-FORM-CHANGE-APPLY-SEARCH", paramMap);
		return list;
	}
	
	@Override
	public long searchFormChangeApplyCount(FormChangeApplySearchDO queryCondition)
																					throws DataAccessException {
		if (queryCondition == null) {
			throw new IllegalArgumentException("ReCouncilApplyFormDO can`t by null");
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("formId", queryCondition.getFormId());
		paramMap.put("formUserId", queryCondition.getFormUserId());
		paramMap.put("applyId", queryCondition.getApplyId());
		paramMap.put("customerId", queryCondition.getCustomerId());
		paramMap.put("customerName", queryCondition.getCustomerName());
		paramMap.put("projectCode", queryCondition.getProjectCode());
		paramMap.put("likeProjectCode", queryCondition.getLikeProjectCode());
		paramMap.put("projectName", queryCondition.getProjectName());
		paramMap.put("formStatus", queryCondition.getFormStatus());
		paramMap.put("busiType", queryCondition.getBusiType());
		paramMap.put("changeForm", queryCondition.getChangeForm());
		paramMap.put("statusList", queryCondition.getStatusList());
		paramMap.put("applyTitle", queryCondition.getApplyTitle());
		paramMap.put("applyCode", queryCondition.getApplyCode());
		paramMap.put("likeApplyCode", queryCondition.getLikeApplyCode());
		paramMap.put("isNeedCouncil", queryCondition.getIsNeedCouncil());
		paramMap.put("isSelForCharge", queryCondition.getIsSelForCharge());
		paramMap.put("isSelXinHui", queryCondition.getIsSelXinHui());
		
		paramMap.put("loginUserId", queryCondition.getLoginUserId());
		paramMap.put("deptIdList", queryCondition.getDeptIdList());
		paramMap.put("draftUserId", queryCondition.getDraftUserId());
		
		return (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-FORM-CHANGE-APPLY-SEARCH-COUNT", paramMap);
	}
	
	@Override
	public FAfterwardsCheckDO searchAfterCheckEdition(Map<String, Object> paramMap)
																					throws DataAccessException {
		Object obj = getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-AFTER-CHECK-LATEST-EDITION-SEARCH", paramMap);
		if (null != obj) {
			return (FAfterwardsCheckDO) obj;
		}
		return null;
	}
	
	@Override
	public long searchAfterCheckReportCount(Map<String, Object> paramMap)
																			throws DataAccessException {
		Long count = (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-AFTER-CHECK-EDITION-COUNT", paramMap);
		return null == count ? 0L : count.longValue();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<FAfterwardsCheckDO> searchAfterCheckReportList(Map<String, Object> paramMap)
																							throws DataAccessException {
		List<FAfterwardsCheckDO> list = getSqlMapClientTemplate().queryForList(
			"MS-EXTRA-AFTER-CHECK-EDITION-SEARCH", paramMap);
		
		return list;
	}
	
	@Override
	public long searchRiskLevelProjectCount(Map<String, Object> paramMap)
																			throws DataAccessException {
		Long count = (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-RISK-LEVEL-PROJECT-COUNT", paramMap);
		return null == count ? 0L : count.longValue();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectDO> searchRiskLevelProjectList(Map<String, Object> paramMap)
																					throws DataAccessException {
		List<ProjectDO> list = getSqlMapClientTemplate().queryForList(
			"MS-EXTRA-RISK-LEVEL-PROJECT-SEARCH", paramMap);
		
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectDO> searchRiskWarningCustomerProjectList(String customerName)
																					throws DataAccessException {
		List<ProjectDO> list = getSqlMapClientTemplate().queryForList(
			"MS-EXTRA-RISK-WARNING-CUSTOMER-PROJECT-SEARCH", customerName);
		
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectRecoveryQueryDO> projectRecoveryQuery(	ProjectRecoveryQueryDO projectRecoveryQueryDO)
																												throws DataAccessException {
		Map<String, Object> paramMap = MiscUtil.covertPoToMap(projectRecoveryQueryDO);
		paramMap.put("limitStart", projectRecoveryQueryDO.getLimitStart());
		paramMap.put("pageSize", projectRecoveryQueryDO.getPageSize());
		paramMap.put("sortOrder", projectRecoveryQueryDO.getSortOrder());
		paramMap.put("sortCol", projectRecoveryQueryDO.getSortCol());
		List<ProjectRecoveryQueryDO> projectRecoveryDOList = new ArrayList<ProjectRecoveryQueryDO>();
		List<Map<String, Object>> resultMapList = getSqlMapClientTemplate().queryForList(
			"MS-PROJECT-RECOVERY-LIST-QUERY", paramMap);
		for (Map<String, Object> map : resultMapList) {
			ProjectRecoveryQueryDO projectRecoveryNewQueryDO = new ProjectRecoveryQueryDO();
			MiscUtil.setDOPropertyByDbMap(map, projectRecoveryNewQueryDO);
			projectRecoveryDOList.add(projectRecoveryNewQueryDO);
			
		}
		return projectRecoveryDOList;
	}
	
	@Override
	public long projectRecoveryQueryCount(ProjectRecoveryQueryDO projectRecoveryQueryDO)
																						throws DataAccessException {
		Map<String, Object> paramMap = MiscUtil.covertPoToMap(projectRecoveryQueryDO);
		Long retObj = (Long) getSqlMapClientTemplate().queryForObject(
			"MS-PROJECT-RECOVERY-LIST-QUERY-COUNT", paramMap);
		if (retObj == null) {
			return 0;
		} else {
			return retObj.longValue();
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AfterwardsProjectSummaryFormDO> searchSummary(	AfterwardsProjectSummaryFormDO summaryDO)
																											throws DataAccessException {
		if (summaryDO == null) {
			throw new IllegalArgumentException("setupForm can`t by null");
		}
		
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("formId", summaryDO.getFormId());
		paramMap.put("summaryId", summaryDO.getSummaryId());
		paramMap.put("deptId", summaryDO.getDeptId());
		paramMap.put("submitManId", summaryDO.getSubmitManId());
		paramMap.put("deptCode", summaryDO.getDeptCode());
		paramMap.put("deptCode", summaryDO.getDeptCode());
		paramMap.put("deptName", summaryDO.getDeptName());
		paramMap.put("reportPeriod", summaryDO.getReportPeriod());
		paramMap.put("submitManName", summaryDO.getSubmitManName());
		paramMap.put("formStatus", summaryDO.getFormStatus());
		
		paramMap.put("draftUserId", summaryDO.getDraftUserId());
		paramMap.put("loginUserId", summaryDO.getLoginUserId());
		paramMap.put("deptIdList", summaryDO.getDeptIdList());
		paramMap.put("sortCol", summaryDO.getSortCol());
		paramMap.put("sortOrder", summaryDO.getSortOrder());
		paramMap.put("limitStart", summaryDO.getLimitStart());
		paramMap.put("pageSize", summaryDO.getPageSize());
		
		List<AfterwardsProjectSummaryFormDO> list = getSqlMapClientTemplate().queryForList(
			"MS-EXTRA-FORM-AFTERWARDS-PROJECT-SUMMARY-SEARCH", paramMap);
		
		return list;
	}
	
	@Override
	public long searchSummaryCount(AfterwardsProjectSummaryFormDO summaryDO)
																			throws DataAccessException {
		if (summaryDO == null) {
			throw new IllegalArgumentException("setupForm can`t by null");
		}
		
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("formId", summaryDO.getFormId());
		paramMap.put("summaryId", summaryDO.getSummaryId());
		paramMap.put("deptId", summaryDO.getDeptId());
		paramMap.put("submitManId", summaryDO.getSubmitManId());
		paramMap.put("deptCode", summaryDO.getDeptCode());
		paramMap.put("deptCode", summaryDO.getDeptCode());
		paramMap.put("deptName", summaryDO.getDeptName());
		paramMap.put("reportPeriod", summaryDO.getReportPeriod());
		paramMap.put("submitManName", summaryDO.getSubmitManName());
		paramMap.put("formStatus", summaryDO.getFormStatus());
		
		paramMap.put("draftUserId", summaryDO.getDraftUserId());
		paramMap.put("loginUserId", summaryDO.getLoginUserId());
		paramMap.put("deptIdList", summaryDO.getDeptIdList());
		
		Long count = (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-FORM-AFTERWARDS-PROJECT-SUMMARY-SEARCH-COUNT", paramMap);
		return null == count ? 0L : count.longValue();
	}
	
	@Override
	public long searchFileCodeCount(Map<String, Object> param) throws DataAccessException {
		Long count = (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-FILE-CODE-SEARCH-COUNT", param);
		return null == count ? 0L : count.longValue();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<FFileDO> searchFileCode(Map<String, Object> paramMap) throws DataAccessException {
		List<FFileDO> list = getSqlMapClientTemplate().queryForList("MS-EXTRA-FILE-CODE-SEARCH",
			paramMap);
		
		return list;
	}
	
	@Override
	public long searchFileStatusCount(Map<String, Object> param) throws DataAccessException {
		Long count = (Long) getSqlMapClientTemplate().queryForObject(
			"MS-EXTRA-FILE-STATUS-SEARCH-COUNT", param);
		return null == count ? 0L : count.longValue();
	}
	
}
