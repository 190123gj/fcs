/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.daointerface;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.born.fcs.pm.dataobject.*;
import org.springframework.dao.DataAccessException;

import com.born.fcs.pm.dal.dataobject.ConsentIssueNoticeDO;
import com.born.fcs.pm.dal.dataobject.FAfterwardsCheckDO;
import com.born.fcs.pm.dal.dataobject.FCreditConditionConfirmDO;
import com.born.fcs.pm.dal.dataobject.FFileDO;
import com.born.fcs.pm.dal.dataobject.FRefundApplicationDO;
import com.born.fcs.pm.dal.dataobject.ProjectDO;
import com.born.fcs.pm.dal.dataobject.ProjectIssueInformationDO;
import com.born.fcs.pm.dal.dataobject.ProjectRecoveryQueryDO;

/**
 * @Filename ExtraDAO.java
 * @Description 手动写的sql
 * @Version 1.0
 * @Author peigen
 * @Email peigen@yiji.com
 * @History <li>Author: peigen</li> <li>Date: 2011-9-6</li> <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
public interface ExtraDAO {
	
	/**
	 * 获取系统时间
	 * 
	 * @return
	 */
	public Date getSysdate();
	
	/**
	 * @param name
	 * @param date
	 * @param cacheNumber
	 * @return
	 * @throws DataAccessException
	 */
	long getNextDateSeq(String name, Date date, long cacheNumber) throws DataAccessException;
	
	/**
	 * @param name
	 * @param date
	 * @param cacheNumber
	 * @return
	 * @throws DataAccessException
	 */
	long getNextYearSeq(String name, Date date, long cacheNumber) throws DataAccessException;
	
	/**
	 * @param name
	 * @param date
	 * @param cacheNumber
	 * @return
	 * @throws DataAccessException
	 */
	long getNextYearMonthSeq(String name, Date date, long cacheNumber) throws DataAccessException;
	
	/**
	 * @param name
	 * @param date
	 * @return
	 * @throws DataAccessException
	 */
	long insertDateSeq(String name, Date date) throws DataAccessException;
	
	/**
	 * @param name
	 * @param date
	 * @return
	 * @throws DataAccessException
	 */
	long insertYearSeq(String name, Date date) throws DataAccessException;
	
	/**
	 * @param name
	 * @param date
	 * @return
	 * @throws DataAccessException
	 */
	long insertYearMonthSeq(String name, Date date) throws DataAccessException;
	
	/**
	 * 
	 * @param name
	 * @param date
	 * @return
	 * @throws DataAccessException
	 */
	boolean selectDateSeq(String name, Date date) throws DataAccessException;
	
	/**
	 * 项目立项表单列表
	 * 
	 * @param setupForm
	 * @param limitStart
	 * @param pageSize
	 * @param sortCol
	 * @param sortOrder
	 * @return
	 * @throws DataAccessException
	 */
	List<FormProjectDO> searchSetupForm(SetupFormProjectDO setupForm, long limitStart,
										long pageSize, Date submitTimeStart, Date submitTimeEnd,
										List<Long> deptIdList, List<String> busiTypeList,
										String sortCol, String sortOrder)
																			throws DataAccessException;
	
	/**
	 * 项目立项表单列表统计
	 * @param setupForm
	 * @return
	 * @throws DataAccessException
	 */
	long searchSetupFormCount(SetupFormProjectDO setupForm, Date submitTimeStart,
								Date submitTimeEnd, List<Long> deptIdList, List<String> busiTypeList)
																										throws DataAccessException;
	
	/**
	 * 风险审查报告列表
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	List<FormProjectDO> searchRiskReviewProject(Map<String, Object> paramMap)
																				throws DataAccessException;
	
	/**
	 * 风险审查报告列表
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	long searchRiskReviewProjectCount(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 保后检查报告列表
	 * @param formProjectDO
	 * @param limitStart
	 * @param pageSize
	 * @param submitTimeStart
	 * @param submitTimeEnd
	 * @param sortCol
	 * @param sortOrder
	 * @return
	 * @throws DataAccessException
	 */
	List<AfterwardsCheckProjectDO> searchAfterwardsCheckProject(FormProjectDO formProjectDO,
																long limitStart, long pageSize,
																Date submitTimeStart,
																Date submitTimeEnd, String sortCol,
																String sortOrder)
																					throws DataAccessException;
	
	/**
	 * 保后检查报告列表
	 * @param formProjectDO
	 * @param submitTimeStart
	 * @param submitTimeEnd
	 * @return
	 * @throws DataAccessException
	 */
	long searchAfterwardsCheckProjectCount(FormProjectDO formProjectDO, Date submitTimeStart,
											Date submitTimeEnd) throws DataAccessException;
	
	/**
	 * 尽职调查报告统计
	 * 
	 * @param setupForm
	 * @param deptIdList
	 * @return
	 * @throws DataAccessException
	 */
	long searchInvestigationCount(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 尽职调查报告列表
	 * 
	 * @param setupForm
	 * @param deptIdList
	 * @param limitStart
	 * @param pageSize
	 * @param sortCol
	 * @param sortOrder
	 * @return
	 * @throws DataAccessException
	 */
	List<InvestigationDO> searchInvestigation(Map<String, Object> paramMap)
																			throws DataAccessException;
	
	/**
	 * 上会项目列表
	 * @param formProjectDO
	 * @param limitStart
	 * @param pageSize
	 * @param submitTimeStart
	 * @param submitTimeEnd
	 * @param sortCol
	 * @param sortOrder
	 * @return
	 * @throws DataAccessException
	 */
	List<CouncilVoteProjectDO> searchCouncilVoteProject(CouncilVoteProjectDO councilVoteProjectDO,
														long limitStart, long pageSize,
														String sortCol, String sortOrder)
																							throws DataAccessException;
	
	/**
	 * 上会项目列表
	 * @param formProjectDO
	 * @param submitTimeStart
	 * @param submitTimeEnd
	 * @return
	 * @throws DataAccessException
	 */
	long searchCouncilVoteProjectCount(CouncilVoteProjectDO councilVoteProjectDO)
																					throws DataAccessException;
	
	boolean selectYearSeq(String name, Date date) throws DataAccessException;
	
	/**
	 * 用印台账、明细列表
	 * @param stampApplyFormProjeactDO
	 * @param limitStart
	 * @param pageSize
	 * @param sortCol
	 * @param sortOrder
	 * @return
	 * @throws DataAccessException
	 */
	List<StampApplyFormProjeactDO> searchStampApplyFormProjeact(StampApplyFormProjeactDO stampApplyFormProjeactDO,
																long limitStart, long pageSize,
																Date applyTimeStart,
																Date applyTimeEnd, String sortCol,
																String sortOrder)
																					throws DataAccessException;
	
	/**
	 * 用印台账、明细列表
	 * @param stampApplyFormProjeactDO
	 * @return
	 * @throws DataAccessException
	 */
	long searchStampApplyFormProjeactCount(StampApplyFormProjeactDO stampApplyFormProjeactDO,
											Date applyTimeStart, Date applyTimeEnd)
																					throws DataAccessException;
	
	/**
	 * 用印台账列表
	 * @param stampApplyFormProjeactDO
	 * @param limitStart
	 * @param pageSize
	 * @param sortCol
	 * @param sortOrder
	 * @return
	 * @throws DataAccessException
	 */
	List<StampApplyFormProjeactDO> searchStampLedger(	StampApplyFormProjeactDO stampApplyFormProjeactDO,
														long limitStart, long pageSize,
														Date applyTimeStart, Date applyTimeEnd,
														String sortCol, String sortOrder)
																							throws DataAccessException;
	
	/**
	 * 用印台账列表
	 * @param stampApplyFormProjeactDO
	 * @return
	 * @throws DataAccessException
	 */
	long searchStampLedgerCount(StampApplyFormProjeactDO stampApplyFormProjeactDO,
								Date applyTimeStart, Date applyTimeEnd) throws DataAccessException;
	
	/**
	 * 用印申请列表
	 * @param stampApplyFormProjeactDO
	 * @param limitStart
	 * @param pageSize
	 * @param sortCol
	 * @param sortOrder
	 * @return
	 * @throws DataAccessException
	 */
	List<StampApplyFormProjeactDO> searchStampApplyList(StampApplyFormProjeactDO stampApplyFormProjeactDO,
														long limitStart, long pageSize,
														Date applyTimeStart, Date applyTimeEnd,
														String sortCol, String sortOrder)
																							throws DataAccessException;
	
	/**
	 * 用印申请列表
	 * @param stampApplyFormProjeactDO
	 * @return
	 * @throws DataAccessException
	 */
	long searchStampApplyListCount(StampApplyFormProjeactDO stampApplyFormProjeactDO,
									Date applyTimeStart, Date applyTimeEnd)
																			throws DataAccessException;
	
	/**
	 * 基础资料用印申请列表
	 * @param stampApplyFormProjeactDO
	 * @param limitStart
	 * @param pageSize
	 * @param sortCol
	 * @param sortOrder
	 * @return
	 * @throws DataAccessException
	 */
	List<StampApplyFormProjeactDO> searchStampBasicDataApplyForm(	StampApplyFormProjeactDO stampApplyFormProjeactDO,
																	long limitStart, long pageSize,
																	Date applyTimeStart,
																	Date applyTimeEnd,
																	String sortCol, String sortOrder)
																										throws DataAccessException;
	
	/**
	 * 基础资料用印申请列表
	 * @param stampApplyFormProjeactDO
	 * @return
	 * @throws DataAccessException
	 */
	long searchStampBasicDataApplyFormCount(StampApplyFormProjeactDO stampApplyFormProjeactDO,
											Date applyTimeStart, Date applyTimeEnd)
																					throws DataAccessException;
	
	/**
	 * 查询放用款申请列表
	 * @param queryCondition
	 * @return
	 * @throws DataAccessException
	 */
	List<LoanUseApplyFormDO> searchLoanUseApplyForm(LoanUseApplyFormDO queryCondition)
																						throws DataAccessException;
	
	/**
	 * 放用款申请列表统计
	 * @param queryCondition
	 * @return
	 * @throws DataAccessException
	 */
	long searchLoanUseApplyFormCount(LoanUseApplyFormDO queryCondition) throws DataAccessException;
	
	/**
	 * 选择放用款项目
	 * @param queryCondition
	 * @return
	 * @throws DataAccessException
	 */
	List<ProjectDO> selectLoanUseApplyProject(ProjectDO queryCondition, String projectCodeOrName,
												long loginUserId, long draftUserId,
												List<Long> detpList, long limitStart,
												long pageSize, String sortCol, String sortOrder)
																								throws DataAccessException;
	
	/**
	 * 选择放用款项目统计
	 * @param queryCondition
	 * @return
	 * @throws DataAccessException
	 */
	long selectLoanUseApplyProjectCount(ProjectDO queryCondition, String projectCodeOrName,
										long loginUserId, long draftUserId, List<Long> detpList)
																								throws DataAccessException;
	
	/**
	 * 查询风险预警
	 * 
	 * @param paramMap 条件map
	 * @return
	 * @throws DataAccessException
	 */
	long searchRiskWarningCount(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 查询风险预警列表
	 * 
	 * @param paramMap 条件map
	 * @return
	 * @throws DataAccessException
	 */
	List<RiskWarningDO> searchRiskWarning(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 查询上会申报
	 * 
	 * @param paramMap 条件map
	 * @return
	 * @throws DataAccessException
	 */
	long searchCouncilApplyRiskHandleCount(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 查询上会申报列表
	 * 
	 * @param paramMap 条件map
	 * @return
	 * @throws DataAccessException
	 */
	List<CouncilApplyRiskHandleDO> searchCouncilApplyRiskHandle(Map<String, Object> paramMap)
																								throws DataAccessException;
	
	/**
	 * 查询尽职调查申明可选择的项目个数
	 * 
	 * @param paramMap 条件map
	 * @return
	 * @throws DataAccessException
	 */
	long searchInvestigationSelectProjectCount(Map<String, Object> paramMap)
																			throws DataAccessException;
	
	/**
	 * 查询尽职调查申明可选择的项目列表
	 * 
	 * @param paramMap 条件map
	 * @return
	 * @throws DataAccessException
	 */
	List<ProjectDO> searchInvestigationSelectProject(Map<String, Object> paramMap)
																					throws DataAccessException;
	
	/**
	 * 查询到期项目个数
	 * 
	 * @param paramMap 条件map
	 * @return
	 * @throws DataAccessException
	 */
	long searchExpireProjectCount(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 查询到期项目列表
	 * 
	 * @param paramMap 条件map
	 * @return
	 * @throws DataAccessException
	 */
	List<ExpireFormProjectDO> searchExpireProject(Map<String, Object> paramMap)
																				throws DataAccessException;
	
	/**
	 * 理财项目立项列表
	 * @param queryCondition
	 * @param limitStart
	 * @param pageSize
	 * @param sortCol
	 * @param sortOrder
	 * @return
	 * @throws DataAccessException
	 */
	List<FinancialProjectSetupFormDO> searchFinancialProjectSetupForm(	FinancialProjectSetupFormDO queryCondition)
																													throws DataAccessException;
	
	/**
	 * 理财项目立项列表统计
	 * @param queryCondition
	 * @throws DataAccessException
	 */
	long searchFinancialProjectSetupFormCount(FinancialProjectSetupFormDO queryCondition)
																							throws DataAccessException;
	
	/**
	 * 查询理财项目转让申请单
	 * @param queryCondition
	 * @return
	 * @throws DataAccessException
	 */
	List<FinancialProjectTransferFormDO> searchFinancialProjectTransferForm(FinancialProjectTransferFormDO queryCondition)
																															throws DataAccessException;
	
	/**
	 * 理财项目转让申请统计
	 * @param queryCondition
	 * @return
	 * @throws DataAccessException
	 */
	long searchFinancialProjectTransferFormCount(FinancialProjectTransferFormDO queryCondition)
																								throws DataAccessException;
	
	/**
	 * 查询理财项目赎回申请单
	 * @param queryCondition
	 * @return
	 * @throws DataAccessException
	 */
	List<FinancialProjectRedeemFormDO> searchFinancialProjectRedeemForm(FinancialProjectRedeemFormDO queryCondition)
																													throws DataAccessException;
	
	/**
	 * 理财项目赎回申请统计
	 * @param queryCondition
	 * @return
	 * @throws DataAccessException
	 */
	long searchFinancialProjectRedeemFormCount(FinancialProjectRedeemFormDO queryCondition)
																							throws DataAccessException;
	
	/**
	 * 合同列表
	 * @param projectContractFormDO
	 * @param limitStart
	 * @param pageSize
	 * @param sortCol
	 * @param sortOrder
	 * @return
	 * @throws DataAccessException
	 */
	List<ProjectContractFormDO> searchProjectContractList(	ProjectContractFormDO projectContractFormDO,
															long limitStart, long pageSize,
															Date operateTimeStart,
															Date operateTimeEnd, String sortCol,
															String sortOrder)
																				throws DataAccessException;
	
	/**
	 * 合同列表
	 * @param projectContractFormDO
	 * @return
	 * @throws DataAccessException
	 */
	long searchProjectContractListCount(ProjectContractFormDO projectContractFormDO,
										Date operateTimeStart, Date operateTimeEnd)
																					throws DataAccessException;
	
	/**
	 * 合同作废列表
	 * @param projectContractFormDO
	 * @param limitStart
	 * @param pageSize
	 * @param sortCol
	 * @param sortOrder
	 * @return
	 * @throws DataAccessException
	 */
	List<ProjectContractFormDO> searchProjectContractInvalidList(	ProjectContractFormDO projectContractFormDO,
																	long limitStart, long pageSize,
																	Date operateTimeStart,
																	Date operateTimeEnd,
																	String sortCol, String sortOrder)
																										throws DataAccessException;
	
	/**
	 * 合同作废列表
	 * @param projectContractFormDO
	 * @return
	 * @throws DataAccessException
	 */
	long searchProjectContractInvalidListCount(ProjectContractFormDO projectContractFormDO,
												Date operateTimeStart, Date operateTimeEnd)
																							throws DataAccessException;
	
	/**
	 * 授信条件落实项目列表
	 * 
	 * @param creditCondition
	 * @param limitStart
	 * @param pageSize
	 * @param sortCol
	 * @param sortOrder
	 * @return
	 * @throws DataAccessException
	 */
	List<FCreditConditionConfirmDO> searchCreditConditionList(	FCreditConditionConfirmDO creditCondition,
																long limitStart, long pageSize,
																Date submitTimeStart,
																Date submitTimeEnd,
																List<Long> deptIdList,
																String sortCol, String sortOrder)
																									throws DataAccessException;
	
	/**
	 * 授信条件落实项目列表统计
	 * @param creditCondition
	 * @return
	 * @throws DataAccessException
	 */
	long searchCreditConditionCount(FCreditConditionConfirmDO creditCondition,
									Date submitTimeStart, Date submitTimeEnd, List<Long> deptIdList)
																									throws DataAccessException;
	
	/**
	 * 授信条件落实 选择项目列表
	 * 
	 * @param creditCondition
	 * @param limitStart
	 * @param pageSize
	 * @param sortCol
	 * @param sortOrder
	 * @return
	 * @throws DataAccessException
	 */
	List<ProjectDO> searchCreditAndProjectList(ProjectCreditConditionFormDO projectDO,
												long limitStart, long pageSize,
												List<Long> deptIdList, String sortCol,
												String sortOrder) throws DataAccessException;
	
	/**
	 * 授信条件落实 选择项目列表统计
	 * @param creditCondition
	 * @return
	 * @throws DataAccessException
	 */
	long searchCreditAndProjectCount(ProjectCreditConditionFormDO projectDO, List<Long> deptIdList)
																									throws DataAccessException;
	
	Long selectYearSeqNum(String name, Date date) throws DataAccessException;
	
	boolean selectYearMonthSeq(String name, Date date) throws DataAccessException;
	
	Long selectYearMonthSeqNum(String name, Date date) throws DataAccessException;
	
	/**
	 * 常用数据库自段列表
	 * @param dbFieldProjectDO
	 * @param limitStart
	 * @param pageSize
	 * @return
	 * @throws DataAccessException
	 */
	List<DbFieldProjectDO> searchDbFieldList(DbFieldProjectDO dbFieldProjectDO, long limitStart,
												long pageSize, String sortCol, String sortOrder)
																								throws DataAccessException;
	
	/**
	 * 常用数据库自段列表
	 * @param dbFieldProjectDO
	 * @return
	 * @throws DataAccessException
	 */
	long searchDbFieldListCount(DbFieldProjectDO dbFieldProjectDO) throws DataAccessException;
	
	/**
	 * 承销/发债已发售项目列表
	 * 
	 * @param creditCondition
	 * @param limitStart
	 * @param pageSize
	 * @param sortCol
	 * @param sortOrder
	 * @return
	 * @throws DataAccessException
	 */
	List<ProjectDO> searchIssueList(ProjectIssueInformationDO issueDO, long limitStart,
									long pageSize, List<Long> deptIdList, String sortCol,
									String sortOrder) throws DataAccessException;
	
	/**
	 * 承销/发债已发售项目列表统计
	 * @param creditCondition
	 * @return
	 * @throws DataAccessException
	 */
	long searchIssueCount(ProjectIssueInformationDO issueDO, List<Long> deptIdList)
																					throws DataAccessException;
	
	/**
	 * 承销发售项目 选择列表(排除已发售过的项目) 需求变更未使用
	 * 
	 * @param projectDO
	 * @param limitStart
	 * @param pageSize
	 * @param sortCol
	 * @param sortOrder
	 * @return
	 * @throws DataAccessException
	 */
	List<ProjectDO> searchConsignmentSalesList(ProjectDO projectDO, long limitStart, long pageSize,
												List<Long> deptIdList, String sortCol,
												String sortOrder) throws DataAccessException;
	
	/**
	 * 承销 发售项目 选择列表统计 需求变更未使用
	 * @param projectDO
	 * @return
	 * @throws DataAccessException
	 */
	long searchConsignmentSalesCount(ProjectDO projectDO, List<Long> deptIdList)
																				throws DataAccessException;
	
	/**
	 * 承销/发债发售项目 选择列表(排除已发售过的项目 和未签订合同的项目)
	 * 
	 * @param projectDO
	 * @param limitStart
	 * @param pageSize
	 * @param sortCol
	 * @param sortOrder
	 * @return
	 * @throws DataAccessException
	 */
	List<ProjectDO> searchBondList(ProjectIssueInformationDO issueDO, long limitStart,
									long pageSize, List<Long> deptIdList, String sortCol,
									String sortOrder) throws DataAccessException;
	
	/**
	 * 承销/发债 发售项目 选择列表统计
	 * @param creditCondition
	 * @return
	 * @throws DataAccessException
	 */
	long searchBondCount(ProjectIssueInformationDO issueDO, List<Long> deptIdList)
																					throws DataAccessException;
	
	/**
	 * 同意发行通知书 项目列表
	 * 
	 * @param noticeDO
	 * @param limitStart
	 * @param pageSize
	 * @param sortCol
	 * @param sortOrder
	 * @return
	 * @throws DataAccessException
	 */
	List<ConsentIssueNoticeDO> searchConsentIssueNoticeListList(ConsentIssueNoticeDO noticeDO,
																long limitStart, long pageSize,
																List<Long> deptIdList,
																String sortCol, String sortOrder)
																									throws DataAccessException;
	
	/**
	 * 同意发行通知书 项目列表统计
	 * @param noticeDO
	 * @return
	 * @throws DataAccessException
	 */
	long searchConsentIssueNoticelistCount(ConsentIssueNoticeDO noticeDO, List<Long> deptIdList)
																								throws DataAccessException;
	
	/**
	 * 同意发行通知书 项目选择列表(排除已发行过的项目、合同未签订的项目和已发售完成的项目)
	 * 
	 * @param noticeDO
	 * @param limitStart
	 * @param pageSize
	 * @param sortCol
	 * @param sortOrder
	 * @return
	 * @throws DataAccessException
	 */
	List<ProjectDO> searchConsentIssueNoticeList(ConsentIssueNoticeDO noticeDO, long limitStart,
													long pageSize, List<Long> deptIdList,
													String sortCol, String sortOrder)
																						throws DataAccessException;
	
	/**
	 * 同意发行通知书 项目选择列表统计(排除已发行过的项目、合同未签订的项目和已发售完成的项目)
	 * @param noticeDO
	 * @return
	 * @throws DataAccessException
	 */
	long searchConsentIssueNoticeCount(ConsentIssueNoticeDO noticeDO, List<Long> deptIdList)
																							throws DataAccessException;
	
	/**
	 * 档案项目选择统计
	 *
	 * @param paramMap 条件map
	 * @return
	 * @throws DataAccessException
	 */
	long searchFileSelectProjectCount(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 档案项目选择
	 *
	 * @param paramMap 条件map
	 * @return
	 * @throws DataAccessException
	 */
	List<ProjectDO> searchFileSelectProject(Map<String, Object> paramMap)
																			throws DataAccessException;
	
	/**
	 * 档案列表
	 * @param fileFormDO
	 * @param limitStart
	 * @param pageSize
	 * @return
	 * @throws DataAccessException
	 */
	List<FileFormDO> searchFileList(FileFormDO fileFormDO, long limitStart, long pageSize,
									String sortCol, String sortOrder) throws DataAccessException;
	
	/**
	 * 档案列表统计
	 * @param fileFormDO
	 * @return
	 * @throws DataAccessException
	 */
	long searchFileListCount(FileFormDO fileFormDO) throws DataAccessException;
	
	/**
	 * 档案明细列表
	 * @param fileFormDO
	 * @param limitStart
	 * @param pageSize
	 * @return
	 * @throws DataAccessException
	 */
	List<FileFormDO> searchFileDetailList(FileFormDO fileFormDO, long limitStart, long pageSize,
											String sortCol, String sortOrder)
																				throws DataAccessException;
	
	/**
	 * 档案明细列表统计
	 * @param fileFormDO
	 * @return
	 * @throws DataAccessException
	 */
	long searchFileDetailListCount(FileFormDO fileFormDO) throws DataAccessException;

	/**
	 * 展期列表
	 * @param fileFormDO
	 * @param limitStart
	 * @param pageSize
	 * @return
	 * @throws DataAccessException
	 */
	List<FileExtensionFormDO> searchExtensionList(FileFormDO fileFormDO, long limitStart, long pageSize,
												  String sortCol, String sortOrder) throws DataAccessException;

	/**
	 * 展期列表统计
	 * @param fileFormDO
	 * @return
	 * @throws DataAccessException
	 */
	long searchExtensionListCount(FileFormDO fileFormDO) throws DataAccessException;
	
	/**
	 * 档案一览表
	 * @param fileViewDO
	 * @param limitStart
	 * @param pageSize
	 * @return
	 * @throws DataAccessException
	 */
	List<FileViewDO> searchFileViewList(FileViewDO fileViewDO, long limitStart, long pageSize,
										String sortCol, String sortOrder)
																			throws DataAccessException;
	
	/**
	 * 档案一览表统计
	 * @param fileViewDO
	 * @return
	 * @throws DataAccessException
	 */
	long searchFileViewListCount(FileViewDO fileViewDO) throws DataAccessException;
	
	/**
	 * 档案明细
	 * @param formId
	 * @param type
	 * @return
	 * @throws DataAccessException
	 */
	List<FileNotArchiveDO> searchNotArchiveByProjectCode(long formId, String type,
															String projectCode)
																				throws DataAccessException;
	
	/**
	 * 档案明细
	 * @param projectCode
	 * @param type
	 * @return
	 * @throws DataAccessException
	 */
	List<FFileInputListDO> searchArchivedByProjectCode(long formId, String type,
														String projectCode, String notNeedDraft,
														String no) throws DataAccessException;
	
	/**
	 * 查询要发送消息的档案
	 * @return
	 * @throws DataAccessException
	 */
	List<FileFormDO> needMessageFile() throws DataAccessException;
	
	/**
	 * 出入库历史
	 * @return
	 * @throws DataAccessException
	 */
	List<FileInOutFormDO> getInOutHistory(Long inputListId) throws DataAccessException;
	
	/**
	 * 查询需要提醒归档的项目
	 * @return
	 * @throws DataAccessException
	 */
	List<FileNeedSendMessageProjectDO> FileNeedMessageProject(String type, List<String> statusList)
																									throws DataAccessException;
	
	/**
	 * 查询当前档案编号的最大件号
	 *
	 * @param fileCode 档案编号
	 * @return
	 * @throws DataAccessException
	 */
	Long searchMaxNoWithFileCode(String fileCode) throws DataAccessException;

	/**
	 * 查询要发送消息的档案
	 * @return
	 * @throws DataAccessException
	 */
	List<FileFormDO> getFileNOs(String fileCode,long formId) throws DataAccessException;
	
	/**
	 * 通过档案编号查询是否已经存在
	 *
	 * @param fileCode 档案编号
	 * @return
	 * @throws DataAccessException
	 */
	Long checkFileCode(String fileCode) throws DataAccessException;
	
	/**
	 * 查询项目归档时间内处理已入库状态的档案id集
	 *
	 * @return
	 * @throws DataAccessException
	 */
	String searchInputStatusFileIds(String type, String projectCode, Date startTime, Date endTime)
																									throws DataAccessException;
	
	/**
	 * 查询解保金额
	 * 
	 * @param projectCode 项目编号
	 * @return
	 * @throws DataAccessException
	 */
	long searchReleaseAmount(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 查询解保申请
	 * 
	 * @param paramMap 条件map
	 * @return
	 * @throws DataAccessException
	 */
	long searchReleaseApplyCount(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 查询解保申请列表
	 * 
	 * @param paramMap 条件map
	 * @return
	 * @throws DataAccessException
	 */
	List<ReleaseApplyDO> searchReleaseApply(Map<String, Object> paramMap)
																			throws DataAccessException;
	
	/**
	 * 统计项目是否确认完
	 * @param projectCode
	 * @return
	 * @throws DataAccessException
	 */
	long isConfirmAllCount(String projectCode) throws DataAccessException;
	
	/**
	 * 查询没有确认的合同
	 * @param projectCode
	 * @return
	 * @throws DataAccessException
	 */
	List<ProjectContractFormDO> searchIsConfirmAll(String projectCode) throws DataAccessException;
	
	/**
	 * 查询风险评级
	 * 
	 * @param paramMap 条件map
	 * @return
	 * @throws DataAccessException
	 */
	long searchRiskLevelCount(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 查询风险评级列表
	 * 
	 * @param paramMap 条件map
	 * @return
	 * @throws DataAccessException
	 */
	List<RiskLevelDO> searchRiskLevel(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 征信查询列表
	 * @param creditRefrerenceApplyFromDO
	 * @param limitStart
	 * @param pageSize
	 * @return
	 * @throws DataAccessException
	 */
	List<CreditRefrerenceApplyFormDO> searchCreditApplyList(CreditRefrerenceApplyFormDO creditRefrerenceApplyFromDO,
															long limitStart, long pageSize,
															String sortCol, String sortOrder)
																								throws DataAccessException;
	
	/**
	 * 征信查询列表统计
	 * @param creditRefrerenceApplyFromDO
	 * @return
	 * @throws DataAccessException
	 */
	long searchCreditApplyCount(CreditRefrerenceApplyFormDO creditRefrerenceApplyFromDO)
																						throws DataAccessException;
	
	/**
	 * 选择收费项目
	 * @param queryCondition
	 * @return
	 * @throws DataAccessException
	 */
	List<ProjectDO> selectChargeNotificationProject(ProjectDO queryCondition,
													String projectCodeOrName, long loginUserId,
													long draftUserId, List<Long> detpList,
													List<String> statusList, List<String> phases,
													List<String> phasesStatus, String hasContract,
													long limitStart, long pageSize, String sortCol,
													String sortOrder) throws DataAccessException;
	
	/**
	 * 选择收费项目统计
	 * @param queryCondition
	 * @return
	 * @throws DataAccessException
	 */
	long selectChargeNotificationProjectCount(ProjectDO queryCondition, String projectCodeOrName,
												long loginUserId, long draftUserId,
												List<Long> detpList, List<String> statusList,
												List<String> phases, List<String> phasesStatus,
												String hasContract) throws DataAccessException;
	
	/**
	 * 收费查询列表
	 * @param chargeNotificationFormDO
	 * @param limitStart
	 * @param pageSize
	 * @return
	 * @throws DataAccessException
	 */
	List<ChargeNotificationFormDO> searchChargeNotificationList(ChargeNotificationFormDO chargeNotificationFormDO,
																long limitStart, long pageSize,
																String sortCol, String sortOrder)
																									throws DataAccessException;
	
	/**
	 * 收费查询列表统计
	 * @param chargeNotificationFormDO
	 * @return
	 * @throws DataAccessException
	 */
	long searchChargeNotificationCount(ChargeNotificationFormDO chargeNotificationFormDO)
																							throws DataAccessException;
	
	/**
	 * 收费导出
	 * @param chargeNotificationFormDO
	 * @param limitStart
	 * @param pageSize
	 * @return
	 * @throws DataAccessException
	 */
	List<ChargeNotificationExportDO> searchChargeNotificationExport(ChargeNotificationFormDO chargeNotificationFormDO,
																	long limitStart, long pageSize,
																	String sortCol, String sortOrder)
																										throws DataAccessException;
	
	/**
	 * 特殊纸作废列表
	 * @param specialPaperResultDO
	 * @param limitStart
	 * @param pageSize
	 * @return
	 * @throws DataAccessException
	 */
	List<SpecialPaperResultDO> searchSpecialPaperInvalidList(	SpecialPaperResultDO specialPaperResultDO,
																long limitStart, long pageSize,
																Date applyTimeStart,
																Date applyTimeEnd, String sortCol,
																String sortOrder,
																Date invalidReceiveDateStart,
																Date invalidReceiveDateEnd)
																							throws DataAccessException;
	
	/**
	 * 特殊纸作废列表统计
	 * @param specialPaperResultDO
	 * @return
	 * @throws DataAccessException
	 */
	long searchSpecialPaperInvalidListCount(SpecialPaperResultDO specialPaperResultDO,
											Date applyTimeStart, Date applyTimeEnd,
											Date invalidReceiveDateStart, Date invalidReceiveDateEnd)
																										throws DataAccessException;
	
	/**
	 * 特殊纸作废校验
	 * @param specialPaperResultDO
	 * @return
	 * @throws DataAccessException
	 */
	List<SpecialPaperResultDO> checkInvalidNo(SpecialPaperResultDO specialPaperResultDO)
																						throws DataAccessException;
	
	/**
	 * 特殊纸--部门领取列表
	 * @param specialPaperResultDO
	 * @param limitStart
	 * @param pageSize
	 * @return
	 * @throws DataAccessException
	 */
	List<SpecialPaperResultDO> searchSpecialPaperDeptList(	SpecialPaperResultDO specialPaperResultDO,
															long limitStart, long pageSize,
															Date applyTimeStart, Date applyTimeEnd,
															String sortCol, String sortOrder)
																								throws DataAccessException;
	
	/**
	 * 特殊纸--部门领取列表统计
	 * @param specialPaperResultDO
	 * @return
	 * @throws DataAccessException
	 */
	long searchSpecialPaperDeptListCount(SpecialPaperResultDO specialPaperResultDO,
											Date applyTimeStart, Date applyTimeEnd)
																					throws DataAccessException;
	
	/**
	 * 特殊纸--使用明细列表
	 * @param specialPaperResultDO
	 * @param limitStart
	 * @param pageSize
	 * @return
	 * @throws DataAccessException
	 */
	List<SpecialPaperResultDO> searchSpecialPaperProjectList(	SpecialPaperResultDO specialPaperResultDO,
																long limitStart, long pageSize,
																Date applyTimeStart,
																Date applyTimeEnd, String sortCol,
																String sortOrder)
																					throws DataAccessException;
	
	/**
	 * 特殊纸--使用明细列表统计
	 * @param specialPaperResultDO
	 * @return
	 * @throws DataAccessException
	 */
	long searchSpecialPaperProjectListCount(SpecialPaperResultDO specialPaperResultDO,
											Date applyTimeStart, Date applyTimeEnd)
																					throws DataAccessException;
	
	/**
	 * 资金划付申请项目列表
	 * 
	 * @param creditCondition
	 * @param limitStart
	 * @param pageSize
	 * @param sortCol
	 * @param sortOrder
	 * @return
	 * @throws DataAccessException
	 */
	List<CapitalAppropriationApplyFormDO> searchCapitalAppropriationList(	CapitalAppropriationApplyFormDO DO,
																			long limitStart,
																			long pageSize,
																			Date submitTimeStart,
																			Date submitTimeEnd,
																			List<Long> deptIdList,
																			String sortCol,
																			String sortOrder)
																								throws DataAccessException;
	
	/**
	 * 资金划付申请项目列表统计
	 * @param creditCondition
	 * @return
	 * @throws DataAccessException
	 */
	long searchCapitalAppropriationCount(CapitalAppropriationApplyFormDO DO, Date submitTimeStart,
											Date submitTimeEnd, List<Long> deptIdList)
																						throws DataAccessException;
	
	/**
	 * 退费申请项目列表
	 * 
	 * @param creditCondition
	 * @param limitStart
	 * @param pageSize
	 * @param sortCol
	 * @param sortOrder
	 * @return
	 * @throws DataAccessException
	 */
	List<FRefundApplicationDO> searchRefundApplicationList(FRefundApplicationDO DO,
															long limitStart, long pageSize,
															Date submitTimeStart,
															Date submitTimeEnd,
															List<Long> deptIdList, String sortCol,
															String sortOrder)
																				throws DataAccessException;
	
	/**
	 * 退费申请项目列表统计
	 * @param creditCondition
	 * @return
	 * @throws DataAccessException
	 */
	long searchRefundApplicationCount(FRefundApplicationDO DO, Date submitTimeStart,
										Date submitTimeEnd, List<Long> deptIdList)
																					throws DataAccessException;
	
	/**
	 * 退费申请 选择列表
	 * @param chargeNotificationFormDO
	 * @param limitStart
	 * @param pageSize
	 * @return
	 * @throws DataAccessException
	 */
	List<ProjectDO> searchRefundApplicationSelectList(ProjectDO projectDO, long limitStart,
														long pageSize, String sortCol,
														String sortOrder)
																			throws DataAccessException;
	
	/**
	 * 退费申请 选择列表统计
	 * @param chargeNotificationFormDO
	 * @return
	 * @throws DataAccessException
	 */
	long searchRefundApplicationSelectCount(ProjectDO projectDO) throws DataAccessException;
	
	/**
	 * 查询复议申请列表
	 * @param queryCondition
	 * @return
	 * @throws DataAccessException
	 */
	List<ReCouncilApplyFormDO> searchRecouncilApplyForm(ReCouncilApplyFormDO queryCondition)
																							throws DataAccessException;
	
	/**
	 * 复议申请列表统计
	 * @param queryCondition
	 * @return
	 * @throws DataAccessException
	 */
	long searchRecouncilApplyFormCount(ReCouncilApplyFormDO queryCondition)
																			throws DataAccessException;
	
	/**
	 * 查询可申请解保的项目个数
	 * 
	 * @param paramMap 条件map
	 * @return
	 * @throws DataAccessException
	 */
	long searchAvailableReleaseSelectProjectCount(Map<String, Object> paramMap)
																				throws DataAccessException;
	
	/**
	 * 查询可申请解保的项目列表
	 * 
	 * @param paramMap 条件map
	 * @return
	 * @throws DataAccessException
	 */
	List<ProjectDO> searchAvailableReleaseSelectProject(Map<String, Object> paramMap)
																						throws DataAccessException;
	
	/**
	 * 项目汇总 上季度 节点 放用款金额（用于计算在保余额）
	 * @param summaryDO
	 * @return
	 * @throws DataAccessException
	 */
	List<AfterwardsSummaryReleasingAmountDO> searchSummaryLoanedAmount(	AfterwardsSummaryReleasingAmountDO summaryDO)
																														throws DataAccessException;
	
	/**
	 * 项目汇总 上季度 节点 解保金额（用于计算在保余额）
	 * @param summaryDO
	 * @return
	 * @throws DataAccessException
	 */
	List<AfterwardsSummaryReleasingAmountDO> searchSummaryApplyAmount(	AfterwardsSummaryReleasingAmountDO summaryDO)
																														throws DataAccessException;
	
	/**
	 * 项目汇总 户数 统计
	 * @param summaryDO
	 * @return
	 * @throws DataAccessException
	 */
	long searchSummaryCount(AfterwardsSummaryReleasingAmountDO summaryDO)
																			throws DataAccessException;
	
	/**
	 * 保后项目列表
	 * @param summaryDO
	 * @return
	 * @throws DataAccessException
	 */
	List<AfterwardsProjectSummaryFormDO> searchSummary(AfterwardsProjectSummaryFormDO summaryDO)
																								throws DataAccessException;
	
	/**
	 * 保后项目列表 统计
	 * @param summaryDO
	 * @return
	 * @throws DataAccessException
	 */
	long searchSummaryCount(AfterwardsProjectSummaryFormDO summaryDO) throws DataAccessException;
	
	/**
	 * 
	 * 企业收入核实情况工作底稿上年或本年数据
	 * 
	 * @param projectCode
	 * @return
	 * @throws DataAccessException
	 */
	long queryCheckReportIncomeFormId(Map<String, Object> param) throws DataAccessException;
	
	/**
	 * 
	 * 最新的财务报表id
	 * 
	 * @param projectCode
	 * @return
	 * @throws DataAccessException
	 */
	long queryNewestFinancialKpiReviewId(String projectCode) throws DataAccessException;
	
	/**
	 * 查询保后检查报告
	 * 
	 * @param paramMap 条件map
	 * @return
	 * @throws DataAccessException
	 */
	long searchAfterwardsCheckCount(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 查询保后检查报告(相同版本)
	 * 
	 * @param paramMap 条件map
	 * @return
	 * @throws DataAccessException
	 */
	long searchAfterwardsCheckCountEdition(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 查询保后检查报告列表
	 * 
	 * @param paramMap 条件map
	 * @return
	 * @throws DataAccessException
	 */
	List<AfterwardsCheckDO> searchAfterwardsCheck(Map<String, Object> paramMap)
																				throws DataAccessException;
	
	/**
	 * 
	 * 查询发债类到期的项目
	 * 
	 * @return
	 */
	List<ProjectIssueInformationDO> queryReleasableProject(Date today);
	
	/**
	 * 
	 * 查询代偿上会项目个数
	 * 
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	long searchRepayProjectCount(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 查询代偿上会项目列表
	 * 
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	List<ProjectDO> searchRepayProjectList(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 
	 * 查询非代偿上会项目个数
	 * 
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	long searchNoRepayProjectCount(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 查询非代偿上会项目列表
	 * 
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	List<ProjectDO> searchNoRepayProjectList(Map<String, Object> paramMap)
																			throws DataAccessException;
	
	/**
	 * 
	 * 查询保后检查报告项目个数
	 * 
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	long searchAfterCheckProjectCount(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 查询保后检查报告项目列表
	 * 
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	List<ProjectDO> searchAfterCheckProjectList(Map<String, Object> paramMap)
																				throws DataAccessException;
	
	/**
	 * 
	 * 查询保后检查报告项目个数(诉讼保函)
	 * 
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	long searchAfterCheckLitigationProjectCount(Map<String, Object> paramMap)
																				throws DataAccessException;
	
	/**
	 * 查询保后检查报告项目列表(诉讼保函)
	 * 
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	List<ProjectDO> searchAfterCheckLitigationProjectList(Map<String, Object> paramMap)
																						throws DataAccessException;
	
	/**
	 * 查询签报申请列表
	 * @param queryCondition
	 * @return
	 * @throws DataAccessException
	 */
	List<FormChangeApplySearchDO> searchFormChangeApply(FormChangeApplySearchDO queryCondition)
																								throws DataAccessException;
	
	/**
	 * 签报申请列表统计
	 * @param queryCondition
	 * @return
	 * @throws DataAccessException
	 */
	long searchFormChangeApplyCount(FormChangeApplySearchDO queryCondition)
																			throws DataAccessException;
	
	/**
	 * 
	 * 查询最新一版的保后检查报告
	 * 
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	FAfterwardsCheckDO searchAfterCheckEdition(Map<String, Object> paramMap)
																			throws DataAccessException;
	
	/**
	 * 
	 * 查询审核通过的保后检查报告个数
	 * 
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	long searchAfterCheckReportCount(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 查询审核通过的保后检查报告列表
	 * 
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	List<FAfterwardsCheckDO> searchAfterCheckReportList(Map<String, Object> paramMap)
																						throws DataAccessException;
	
	/**
	 * 
	 * 查询保后检查报告项目个数
	 * 
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	long searchRiskLevelProjectCount(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 查询保后检查报告项目列表
	 * 
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	List<ProjectDO> searchRiskLevelProjectList(Map<String, Object> paramMap)
																			throws DataAccessException;
	
	/**
	 * 根据客户查询相关项目
	 * @param customerName
	 * @return
	 * @throws DataAccessException
	 */
	List<ProjectDO> searchRiskWarningCustomerProjectList(String customerName)
																				throws DataAccessException;
	
	/**
	 * 追偿列表
	 * @param projectRecoveryQueryDO
	 * @return
	 * @throws DataAccessException
	 */
	public List<ProjectRecoveryQueryDO> projectRecoveryQuery(	ProjectRecoveryQueryDO projectRecoveryQueryDO)
																												throws DataAccessException;
	
	/**
	 * 查询追偿列表总数
	 * @param tradeCommonQuery
	 * @return
	 * @throws DataAccessException
	 */
	public long projectRecoveryQueryCount(ProjectRecoveryQueryDO projectRecoveryQueryDO)
																						throws DataAccessException;
	
	/**
	 * 按档案编辑统计
	 * @param param
	 * @return
	 * @throws DataAccessException
	 */
	long searchFileCodeCount(Map<String, Object> param) throws DataAccessException;
	
	/**
	 * 按档案编号查询列表
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	List<FFileDO> searchFileCode(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 按档案状态查询总数
	 * @param param
	 * @return
	 * @throws DataAccessException
	 */
	long searchFileStatusCount(Map<String, Object> param) throws DataAccessException;
}
