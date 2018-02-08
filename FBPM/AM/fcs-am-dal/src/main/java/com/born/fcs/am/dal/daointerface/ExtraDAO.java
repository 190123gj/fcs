/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.am.dal.daointerface;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.born.fcs.am.dataobject.AssessCompanyApplyFormDO;
import com.born.fcs.am.dataobject.AssetSimpleDO;
import com.born.fcs.am.dataobject.AssetsTransferApplicationFormDO;
import com.born.fcs.am.dataobject.AssetsTransfereeApplicationFormDO;
import com.born.fcs.am.dataobject.BackTaskDO;
import com.born.fcs.am.dataobject.PledgeTypeAttributeImageDO;
import com.born.fcs.am.dataobject.PledgeTypeAttributeNetworkDO;
import com.born.fcs.am.dataobject.PledgeTypeAttributeTextDO;

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
	 * 资产属性 文字信息属性查询
	 * 
	 * @param chargeNotificationFormDO
	 * @param limitStart
	 * @param pageSize
	 * @return
	 * @throws DataAccessException
	 */
	List<PledgeTypeAttributeTextDO> searchPledgeTypeAttributeTextList(	PledgeTypeAttributeTextDO attributeTextDO)
																													throws DataAccessException;
	
	/**
	 * 资产属性 文字信息属性统计
	 * 
	 * @param chargeNotificationFormDO
	 * @return
	 * @throws DataAccessException
	 */
	long searchPledgeTypeAttributeTextDOCount(PledgeTypeAttributeTextDO attributeTextDO)
																						throws DataAccessException;
	
	/**
	 * 资产属性 图像信息属性查询
	 * 
	 * @param chargeNotificationFormDO
	 * @param limitStart
	 * @param pageSize
	 * @return
	 * @throws DataAccessException
	 */
	List<PledgeTypeAttributeImageDO> searchPledgeTypeAttributeImageList(PledgeTypeAttributeImageDO attributeImageDO)
																													throws DataAccessException;
	
	/**
	 * 资产属性 图像信息属性统计
	 * 
	 * @param chargeNotificationFormDO
	 * @return
	 * @throws DataAccessException
	 */
	long searchPledgeTypeAttributeImageCount(PledgeTypeAttributeImageDO attributeImageDO)
																							throws DataAccessException;
	
	/**
	 * 资产属性 网络信息属性查询
	 * 
	 * @param chargeNotificationFormDO
	 * @param limitStart
	 * @param pageSize
	 * @return
	 * @throws DataAccessException
	 */
	List<PledgeTypeAttributeNetworkDO> searchPledgeTypeAttributeNetworkList(PledgeTypeAttributeNetworkDO attributeNetworkDO)
																															throws DataAccessException;
	
	/**
	 * 资产属性 网络信息属性统计
	 * 
	 * @param chargeNotificationFormDO
	 * @return
	 * @throws DataAccessException
	 */
	long searchPledgeTypeAttributeNetworkCount(PledgeTypeAttributeNetworkDO attributeNetworkDO)
																								throws DataAccessException;
	
	/**
	 * 资产转让申请项目列表
	 * 
	 * @param creditCondition
	 * @param limitStart
	 * @param pageSize
	 * @param sortCol
	 * @param sortOrder
	 * @return
	 * @throws DataAccessException
	 */
	List<AssetsTransferApplicationFormDO> searchAssetsTransferApplicationList(	AssetsTransferApplicationFormDO DO,
																				long limitStart,
																				long pageSize,
																				Date submitTimeStart,
																				Date submitTimeEnd,
																				List<Long> deptIdList,
																				String sortCol,
																				String sortOrder)
																									throws DataAccessException;
	
	/**
	 * 资产转让申请项目列表统计
	 * 
	 * @param creditCondition
	 * @return
	 * @throws DataAccessException
	 */
	long searchAssetsTransferApplicationCount(AssetsTransferApplicationFormDO DO,
												Date submitTimeStart, Date submitTimeEnd,
												List<Long> deptIdList) throws DataAccessException;
	
	/**
	 * 资产受让申请项目列表
	 * 
	 * @param creditCondition
	 * @param limitStart
	 * @param pageSize
	 * @param sortCol
	 * @param sortOrder
	 * @return
	 * @throws DataAccessException
	 */
	List<AssetsTransfereeApplicationFormDO> searchAssetsTransfereeApplicationList(	AssetsTransfereeApplicationFormDO DO,
																					long limitStart,
																					long pageSize,
																					Date submitTimeStart,
																					Date submitTimeEnd,
																					List<Long> deptIdList,
																					String sortCol,
																					String sortOrder)
																										throws DataAccessException;
	
	/**
	 * 资产受让申请项目列表统计
	 * 
	 * @param creditCondition
	 * @return
	 * @throws DataAccessException
	 */
	long searchAssetsTransfereeApplicationCount(AssetsTransfereeApplicationFormDO DO,
												Date submitTimeStart, Date submitTimeEnd,
												List<Long> deptIdList) throws DataAccessException;
	
	/**
	 * 资产受让申请单 选择项目列表（资产转让项目）
	 * 
	 * @param creditCondition
	 * @param limitStart
	 * @param pageSize
	 * @param sortCol
	 * @param sortOrder
	 * @return
	 * @throws DataAccessException
	 */
	List<AssetsTransferApplicationFormDO> searchAssetsTransfereeProjectSelectList(	AssetsTransferApplicationFormDO DO,
																					long limitStart,
																					long pageSize,
																					Date submitTimeStart,
																					Date submitTimeEnd,
																					List<Long> deptIdList,
																					String sortCol,
																					String sortOrder)
																										throws DataAccessException;
	
	/**
	 * 资产受让申请单 选择项目列表统计(资产转让项目)
	 * 
	 * @param creditCondition
	 * @return
	 * @throws DataAccessException
	 */
	long searchAssetsTransfereeProjectSelectCount(AssetsTransferApplicationFormDO DO,
													Date submitTimeStart, Date submitTimeEnd,
													List<Long> deptIdList)
																			throws DataAccessException;
	
	/**
	 * 评估公司申请单列表
	 * 
	 * @param creditCondition
	 * @param limitStart
	 * @param pageSize
	 * @param sortCol
	 * @param sortOrder
	 * @return
	 * @throws DataAccessException
	 */
	List<AssessCompanyApplyFormDO> searchAssessCompanyApplyList(AssessCompanyApplyFormDO DO,
																long limitStart, long pageSize,
																Date submitTimeStart,
																Date submitTimeEnd,
																List<Long> deptIdList,
																String sortCol, String sortOrder)
																									throws DataAccessException;
	
	/**
	 * 评估公司申请单列表统计
	 * 
	 * @param creditCondition
	 * @return
	 * @throws DataAccessException
	 */
	long searchAssessCompanyApplyCount(AssessCompanyApplyFormDO DO, Date submitTimeStart,
										Date submitTimeEnd, List<Long> deptIdList)
																					throws DataAccessException;
	
	/**
	 * 尽调查询列表
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	List<AssetSimpleDO> searchAssessSimpleList(Map<String, Object> paramMap)
																			throws DataAccessException;
	
	/**
	 * 尽调查询列表统计
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	long searchAssessSimple(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 驳回的任务列表
	 * @param userId
	 * @return
	 * @throws DataAccessException
	 */
	List<BackTaskDO> backTaskList(long userId) throws DataAccessException;
}
