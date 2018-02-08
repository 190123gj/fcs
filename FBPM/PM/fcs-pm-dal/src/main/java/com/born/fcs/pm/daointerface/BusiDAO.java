/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.daointerface;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.born.fcs.pm.dal.dataobject.FCounterGuaranteeRepayDO;
import com.born.fcs.pm.dal.dataobject.FInvestigationAssetReviewDO;
import com.born.fcs.pm.dal.dataobject.FProjectFinancialDO;
import com.born.fcs.pm.dal.dataobject.ProjectDO;
import com.born.fcs.pm.dal.dataobject.ProjectExtendInfoDO;
import com.born.fcs.pm.dal.dataobject.ProjectFinancialDO;
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

/**
 * @Filename BusiDAO.java
 * @Description 手动写的业务sql
 * @Version 1.0
 * @Author wuzj
 * @Email yuanying@yiji.com
 * @History <li>Author: wuzj</li> <li>Date: 2016-7-6</li> <li>Version: 1.0</li>
 * <li>Content: create</li>
 */
public interface BusiDAO {
	
	/**
	 * 查询已经通过的风险处置方案
	 * @param projectCode
	 * @return
	 * @throws DataAccessException
	 */
	public List<CouncilSummaryRiskHandleDO> findApprovalRiskHandleSummary(	CouncilSummaryRiskHandleDO querDO)
																												throws DataAccessException;
	
	/**
	 * 统计已经通过的风险处置方案
	 * @param projectCode
	 * @return
	 * @throws DataAccessException
	 */
	public long findApprovalRiskHandleSummaryCount(CouncilSummaryRiskHandleDO querDO)
																						throws DataAccessException;
	
	/**
	 * 查询内审意见交换表单列表
	 * @param exForm
	 * @return
	 */
	public List<InternalOpinionExchangeFormDO> searchInternalOpinionForm(	InternalOpinionExchangeFormDO exForm);
	
	/**
	 * 查询内审意见交换表单列表统计
	 * @param exForm
	 * @return
	 */
	public long searchInternalOpinionFormCount(InternalOpinionExchangeFormDO exForm);
	
	/**
	 * 查询已经通过审核的项目扩展属性
	 * @param queryDO
	 * @return
	 */
	public List<ProjectExtendInfoDO> findApprovalProjectExtendInfo(ProjectExtendInfoDO queryDO);
	
	/**
	 * 查询经纪业务列表
	 * @param exForm
	 * @return
	 */
	public List<BrokerBusinessFormDO> searchBrokerBusinessForm(BrokerBusinessFormDO queryDO);
	
	/**
	 * 查询经纪业务列表统计
	 * @param exForm
	 * @return
	 */
	public long searchBrokerBusinessFormCount(BrokerBusinessFormDO queryDO);
	
	/**
	 * 融资担保业务 在保余额合计(已放款金额-已解保金额)
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public String getReleasingAmountToReport();
	
	/**
	 * 预计解保情况汇总表、预计收入汇总表（根据还款计划的数据，取查询月需要还款的金额 统计范围为：在保的担保、委贷类项目）
	 * @return
	 */
	public List<String> getRepayOrChargeAmount(String deptCode, String type, Date startTime,
												Date endTime);
	
	/**
	 * 统计批复中的授信金额 统计范围为：已批未放的担保、委贷类项目
	 * @return
	 */
	public List<ToReportExpectEventDO> getExpectEvent(Date signedTime);
	
	/**
	 * 项目推动情况汇总表 统计范围为：在保的担保、委贷类项目）
	 * @return
	 */
	public List<ToReportDO> getReportProjectProcess(Date startTime, Date endTime);
	
	/**
	 * 担保业务结构汇总表
	 * @return
	 */
	public List<ToReportGuaranteeStructreDO> getReportGuaranteeStructre(Date startTime, Date endTime);
	
	/**
	 * 担保类别年末在保余额(担保业务结构汇总表)
	 * @return
	 */
	public List<ToReportExpectEventDO> getReportGuaranteeStructreBalanceAmount();
	
	/**
	 * 担保类别合同余额(担保业务结构汇总表)
	 * @return
	 */
	public List<ToReportExpectEventDO> getReportGuaranteeStructreContractAmount(Date startTime,
																				Date endTime);
	
	/**
	 * 资产复评
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	long searchAssetReviewCount(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 资产复评列表
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	List<FInvestigationAssetReviewDO> searchAssetReview(Map<String, Object> paramMap)
																						throws DataAccessException;
	
	/**
	 * 驳回的任务列表
	 * @param userId
	 * @return
	 * @throws DataAccessException
	 */
	List<BackTaskDO> backTaskList(long userId) throws DataAccessException;
	
	/**
	 * B角更换统计
	 * @param formDO
	 * @return
	 * @throws DataAccessException
	 */
	long searchManagerbChangeApplyFormCount(ManagerbChangeApplyFormDO searchDO)
																				throws DataAccessException;
	
	/**
	 * B角更换列表
	 * @param formDO
	 * @return
	 * @throws DataAccessException
	 */
	List<ManagerbChangeApplyFormDO> searchManagerbChangeApplyForm(ManagerbChangeApplyFormDO searchDO)
																										throws DataAccessException;
	
	/**
	 * 查询划付理财项目统计
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	long queryPurchasingFinancialProjectCount(Map<String, Object> paramMap)
																			throws DataAccessException;
	
	/**
	 * 查询划付理财项目列表
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	List<FProjectFinancialDO> queryPurchasingFinancialProject(Map<String, Object> paramMap)
																							throws DataAccessException;
	
	/***
	 * 等待生成计提数据的理财项目
	 * @param withdrawMonth 生成月份yyyy-MM
	 * @param withdrawDay 查询日期 yyyy-MM-dd
	 * @param limit 每次查询条数
	 * @return
	 */
	List<ProjectFinancialDO> waitGenWithdrawFinancialProject(String withdrawMonth,
																String withdrawDay, long limit);
	
	/***
	 * 等待生成计提数据的理财转让
	 * @param withdrawMonth 生成月份yyyy-MM
	 * @param withdrawDay 查询日期 yyyy-MM-dd
	 * @param limit 每次查询条数
	 * @return
	 */
	List<ProjectGenWithdrawFinancialTransferDO> waitGenWithdrawFinancialTransfer(	String withdrawMonth,
																					String withdrawDay,
																					long limit);
	
	/**
	 * 查询项目收费、划付明细
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	List<ProjectChargePayDO> queryProjectChargePayDetail(Map<String, Object> paramMap)
																						throws DataAccessException;
	
	/**
	 * 查询项目收费、划付明细统计
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	long queryProjectChargePayDetailCount(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 查询项目收费、划付明细选择
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	List<ProjectChargePayDO> queryProjectChargePayDetailChoose(Map<String, Object> paramMap)
																							throws DataAccessException;
	
	/**
	 * 查询项目收费、划付明细统计选择
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	long queryProjectChargePayDetailChooseCount(Map<String, Object> paramMap)
																				throws DataAccessException;
	
	/**
	 * 解保审核通过的还款
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	long queryReleasedRepayCount(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 解保审核通过的还款
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	List<FCounterGuaranteeRepayDO> queryReleasedRepayList(Map<String, Object> paramMap)
																						throws DataAccessException;
	
	/**
	 * 资金划付导出列表
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	List<CapitalExportDO> capitalExportList(Map<String, Object> paramMap)
																			throws DataAccessException;
	
	/**
	 * 资金划付导出统计
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	long capitalExportCount(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 尽职调查报告是否使用某个资产统计
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	
	long investigationUseAssetCount(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 项目移交明细
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	List<ProjectTransferDetailFormDO> projectTransferDetailForm(Map<String, Object> paramMap)
																								throws DataAccessException;
	
	/**
	 * 项目移交明细统计
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	long projectTransferDetailFormCount(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 选择可移交到法务部的项目
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	List<ProjectDO> canTransferProject(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 选择可移交到法务部的项目统计
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	long canTransferProjectCount(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 选择追偿项目
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	List<ProjectDO> recoveryProject(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 选择追偿项目统计
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	long recoveryProjectCount(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 理财项目送审列表
	 * @param queryCondition
	 * @return
	 * @throws DataAccessException
	 */
	List<FinancialProjectReviewFormDO> searchFinancialProjectReviewForm(FinancialProjectReviewFormDO queryCondition)
																													throws DataAccessException;
	
	/**
	 * 理财项目送审列表统计
	 * @param queryCondition
	 * @throws DataAccessException
	 */
	long searchFinancialProjectReviewFormCount(FinancialProjectReviewFormDO queryCondition)
																							throws DataAccessException;
	
	/**
	 * 理财项目合同列表
	 * @param queryCondition
	 * @return
	 * @throws DataAccessException
	 */
	List<FinancialProjectContractFormDO> searchFinancialProjectContractForm(FinancialProjectContractFormDO queryCondition)
																															throws DataAccessException;
	
	/**
	 * 理财项目合同列表统计
	 * @param queryCondition
	 * @throws DataAccessException
	 */
	long searchFinancialProjectContractFormCount(FinancialProjectContractFormDO queryCondition)
																								throws DataAccessException;
	
	/**
	 * 查询项目表单 <br>
	 * formStatus : ('DRAFT', 'SUBMIT', 'CANCEL', 'AUDITING', 'BACK','APPROVAL')
	 * @param projectCode
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	HashMap queryProjectForms(String projectCode);
}
