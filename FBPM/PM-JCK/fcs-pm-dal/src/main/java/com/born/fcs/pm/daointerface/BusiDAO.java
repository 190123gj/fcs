/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.daointerface;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.born.fcs.pm.dal.dataobject.FCouncilSummaryRiskHandleDO;
import com.born.fcs.pm.dal.dataobject.FInvestigationAssetReviewDO;
import com.born.fcs.pm.dal.dataobject.ProjectExtendInfoDO;
import com.born.fcs.pm.dataobject.BackTaskDO;
import com.born.fcs.pm.dataobject.BrokerBusinessFormDO;
import com.born.fcs.pm.dataobject.InternalOpinionExchangeFormDO;
import com.born.fcs.pm.dataobject.ManagerbChangeApplyFormDO;
import com.born.fcs.pm.dataobject.ToReportDO;
import com.born.fcs.pm.dataobject.ToReportExpectEventDO;

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
	public List<FCouncilSummaryRiskHandleDO> findApprovalRiskHandleSummary(String projectCode)
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
	public List<ToReportExpectEventDO> getExpectEvent();
	
	/**
	 * 项目推动情况汇总表 统计范围为：在保的担保、委贷类项目）
	 * @return
	 */
	public List<ToReportDO> getReportProjectProcess(Date startTime, Date endTime);
	
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
}
