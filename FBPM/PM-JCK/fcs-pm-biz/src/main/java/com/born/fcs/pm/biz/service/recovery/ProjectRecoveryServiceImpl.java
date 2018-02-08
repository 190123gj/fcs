/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午5:30:34 创建
 */
package com.born.fcs.pm.biz.service.recovery;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.am.ws.enums.AssetStatusEnum;
import com.born.fcs.am.ws.order.pledgeasset.AssetRelationProjectBindOrder;
import com.born.fcs.am.ws.order.pledgeasset.AssetStatusOrder;
import com.born.fcs.am.ws.service.pledgeasset.PledgeAssetService;
import com.born.fcs.pm.biz.exception.ExceptionFactory;
import com.born.fcs.pm.biz.service.base.BaseAutowiredMessageService;
import com.born.fcs.pm.dal.daointerface.CommonAttachmentDAO;
import com.born.fcs.pm.dal.dataobject.CommonAttachmentDO;
import com.born.fcs.pm.dal.dataobject.ProjectDO;
import com.born.fcs.pm.dal.dataobject.ProjectRecoveryDO;
import com.born.fcs.pm.dal.dataobject.ProjectRecoveryDebtorReorganizationAmountDetailDO;
import com.born.fcs.pm.dal.dataobject.ProjectRecoveryDebtorReorganizationDO;
import com.born.fcs.pm.dal.dataobject.ProjectRecoveryDebtorReorganizationDebtsCouncilDO;
import com.born.fcs.pm.dal.dataobject.ProjectRecoveryDebtorReorganizationPledgeDO;
import com.born.fcs.pm.dal.dataobject.ProjectRecoveryLitigationAdjournedProcedureDO;
import com.born.fcs.pm.dal.dataobject.ProjectRecoveryLitigationAdjournedSecondDO;
import com.born.fcs.pm.dal.dataobject.ProjectRecoveryLitigationBeforePreservationDO;
import com.born.fcs.pm.dal.dataobject.ProjectRecoveryLitigationBeforePreservationPrecautionDO;
import com.born.fcs.pm.dal.dataobject.ProjectRecoveryLitigationBeforeTrailDO;
import com.born.fcs.pm.dal.dataobject.ProjectRecoveryLitigationCertificateDO;
import com.born.fcs.pm.dal.dataobject.ProjectRecoveryLitigationExecuteDO;
import com.born.fcs.pm.dal.dataobject.ProjectRecoveryLitigationExecuteGyrationDO;
import com.born.fcs.pm.dal.dataobject.ProjectRecoveryLitigationExecuteStuffDO;
import com.born.fcs.pm.dal.dataobject.ProjectRecoveryLitigationOpeningDO;
import com.born.fcs.pm.dal.dataobject.ProjectRecoveryLitigationPlaceOnFileDO;
import com.born.fcs.pm.dal.dataobject.ProjectRecoveryLitigationPreservationDO;
import com.born.fcs.pm.dal.dataobject.ProjectRecoveryLitigationRefereeDO;
import com.born.fcs.pm.dal.dataobject.ProjectRecoveryLitigationSecondAppealDO;
import com.born.fcs.pm.dal.dataobject.ProjectRecoveryLitigationSpecialProcedureDO;
import com.born.fcs.pm.dal.dataobject.ProjectRecoveryNoticeLetterDO;
import com.born.fcs.pm.dal.dataobject.ProjectRecoveryQueryDO;
import com.born.fcs.pm.dal.dataobject.ProjectRiskHandleTeamDO;
import com.born.fcs.pm.domain.context.FcsPmDomainHolder;
import com.born.fcs.pm.integration.bpm.service.client.user.SysUser;
import com.born.fcs.pm.util.MiscUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.enums.CommonAttachmentTypeEnum;
import com.born.fcs.pm.ws.enums.PledgeAssetManagementModeEnum;
import com.born.fcs.pm.ws.enums.ProjectPhasesEnum;
import com.born.fcs.pm.ws.enums.ProjectRecoveryDescribeTypeEnum;
import com.born.fcs.pm.ws.enums.ProjectRecoveryLetterStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectRecoveryLetterTypeEnum;
import com.born.fcs.pm.ws.enums.ProjectRecoveryLitigationTypeEnum;
import com.born.fcs.pm.ws.enums.ProjectRecoveryPreservationTypeEnum;
import com.born.fcs.pm.ws.enums.ProjectRecoveryProcedureTypeEnum;
import com.born.fcs.pm.ws.enums.ProjectRecoveryRefereeTypeEnum;
import com.born.fcs.pm.ws.enums.ProjectRecoveryStatusEnum;
import com.born.fcs.pm.ws.enums.ProjectRecoveryTypeEnum;
import com.born.fcs.pm.ws.enums.ProjectRecoveryUploadTypeEnum;
import com.born.fcs.pm.ws.enums.ProjectStatusEnum;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.info.common.CommonAttachmentInfo;
import com.born.fcs.pm.ws.info.common.ProjectInfo;
import com.born.fcs.pm.ws.info.common.ProjectRelatedUserInfo;
import com.born.fcs.pm.ws.info.revovery.ProjectRecoveryDebtorReorganizationAmountDetailInfo;
import com.born.fcs.pm.ws.info.revovery.ProjectRecoveryDebtorReorganizationDebtsCouncilInfo;
import com.born.fcs.pm.ws.info.revovery.ProjectRecoveryDebtorReorganizationInfo;
import com.born.fcs.pm.ws.info.revovery.ProjectRecoveryDebtorReorganizationPledgeInfo;
import com.born.fcs.pm.ws.info.revovery.ProjectRecoveryInfo;
import com.born.fcs.pm.ws.info.revovery.ProjectRecoveryListInfo;
import com.born.fcs.pm.ws.info.revovery.ProjectRecoveryLitigationAdjournedProcedureInfo;
import com.born.fcs.pm.ws.info.revovery.ProjectRecoveryLitigationAdjournedSecondInfo;
import com.born.fcs.pm.ws.info.revovery.ProjectRecoveryLitigationBeforePreservationInfo;
import com.born.fcs.pm.ws.info.revovery.ProjectRecoveryLitigationBeforePreservationPrecautionInfo;
import com.born.fcs.pm.ws.info.revovery.ProjectRecoveryLitigationBeforeTrailInfo;
import com.born.fcs.pm.ws.info.revovery.ProjectRecoveryLitigationCertificateInfo;
import com.born.fcs.pm.ws.info.revovery.ProjectRecoveryLitigationExecuteGyrationInfo;
import com.born.fcs.pm.ws.info.revovery.ProjectRecoveryLitigationExecuteInfo;
import com.born.fcs.pm.ws.info.revovery.ProjectRecoveryLitigationExecuteStuffInfo;
import com.born.fcs.pm.ws.info.revovery.ProjectRecoveryLitigationOpeningInfo;
import com.born.fcs.pm.ws.info.revovery.ProjectRecoveryLitigationPlaceOnFileInfo;
import com.born.fcs.pm.ws.info.revovery.ProjectRecoveryLitigationPreservationInfo;
import com.born.fcs.pm.ws.info.revovery.ProjectRecoveryLitigationRefereeInfo;
import com.born.fcs.pm.ws.info.revovery.ProjectRecoveryLitigationSecondAppealInfo;
import com.born.fcs.pm.ws.info.revovery.ProjectRecoveryLitigationSpecialProcedureInfo;
import com.born.fcs.pm.ws.info.revovery.ProjectRecoveryNoticeLetterInfo;
import com.born.fcs.pm.ws.order.common.MessageOrder;
import com.born.fcs.pm.ws.order.common.SendMailOrder;
import com.born.fcs.pm.ws.order.common.SimpleUserInfo;
import com.born.fcs.pm.ws.order.recovery.ProjectRecoveryDebtorReorganizationAmountDetailOrder;
import com.born.fcs.pm.ws.order.recovery.ProjectRecoveryDebtorReorganizationDebtsCouncilOrder;
import com.born.fcs.pm.ws.order.recovery.ProjectRecoveryDebtorReorganizationOrder;
import com.born.fcs.pm.ws.order.recovery.ProjectRecoveryDebtorReorganizationPledgeOrder;
import com.born.fcs.pm.ws.order.recovery.ProjectRecoveryLitigationAdjournedProcedureOrder;
import com.born.fcs.pm.ws.order.recovery.ProjectRecoveryLitigationAdjournedSecondOrder;
import com.born.fcs.pm.ws.order.recovery.ProjectRecoveryLitigationBeforePreservationOrder;
import com.born.fcs.pm.ws.order.recovery.ProjectRecoveryLitigationBeforePreservationPrecautionOrder;
import com.born.fcs.pm.ws.order.recovery.ProjectRecoveryLitigationBeforeTrailOrder;
import com.born.fcs.pm.ws.order.recovery.ProjectRecoveryLitigationCertificateOrder;
import com.born.fcs.pm.ws.order.recovery.ProjectRecoveryLitigationExecuteGyrationOrder;
import com.born.fcs.pm.ws.order.recovery.ProjectRecoveryLitigationExecuteOrder;
import com.born.fcs.pm.ws.order.recovery.ProjectRecoveryLitigationExecuteStuffOrder;
import com.born.fcs.pm.ws.order.recovery.ProjectRecoveryLitigationOpeningOrder;
import com.born.fcs.pm.ws.order.recovery.ProjectRecoveryLitigationPlaceOnFileOrder;
import com.born.fcs.pm.ws.order.recovery.ProjectRecoveryLitigationPreservationOrder;
import com.born.fcs.pm.ws.order.recovery.ProjectRecoveryLitigationRefereeOrder;
import com.born.fcs.pm.ws.order.recovery.ProjectRecoveryLitigationSecondAppealOrder;
import com.born.fcs.pm.ws.order.recovery.ProjectRecoveryLitigationSpecialProcedureOrder;
import com.born.fcs.pm.ws.order.recovery.ProjectRecoveryOrder;
import com.born.fcs.pm.ws.order.recovery.ProjectRecoveryQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.recovery.ProjectRecoveryResult;
import com.born.fcs.pm.ws.service.common.ProjectService;
import com.born.fcs.pm.ws.service.recovery.ProjectRecoveryService;
import com.yjf.common.domain.api.Domain;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.DateUtil;
import com.yjf.common.lang.util.ListUtil;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;
import com.yjf.common.service.base.AfterProcessInvokeService;
import com.yjf.common.service.base.BeforeProcessInvokeService;

/**
 * 
 * 
 * @author hjiajie
 * 
 */
@Service("projectRecoveryService")
public class ProjectRecoveryServiceImpl extends BaseAutowiredMessageService implements
																			ProjectRecoveryService {
	@Autowired
	CommonAttachmentDAO commonAttachmentDAO;
	
	@Autowired
	private ProjectService projectService;
	
	private Long userId;
	
	private String userAccount;
	
	private String userName;
	
	@Autowired
	private PledgeAssetService pledgeAssetServiceClient;
	
	/**
	 * @param order
	 * @return
	 * @see com.born.fcs.pm.ws.service.recovery.ProjectRecoveryService#save(com.born.fcs.pm.ws.order.recovery.ProjectRecoveryOrder)
	 */
	@Override
	public FcsBaseResult save(final ProjectRecoveryOrder order) {
		return commonProcess(order, "追偿信息保存", new BeforeProcessInvokeService() {
			
			@Override
			public Domain before() {
				if (order == null || StringUtil.isBlank(order.getProjectCode())) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "入参不能为空！");
				}
				long recoveryId = 0;
				if (order.getId() != null) {
					recoveryId = order.getId();
				}
				Date now = FcsPmDomainHolder.get().getSysDate();
				// 抓取法务经理
				ProjectRelatedUserInfo rData = projectRelatedUserService.getLegalManager(order
					.getProjectCode());
				if (rData == null) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.WRONG_REQ_PARAM,
						"未查询到项目法务经理！");
				}
				if (rData.getUserId() != order.getUserId()) {
					throw ExceptionFactory.newFcsException(FcsResultEnum.WRONG_REQ_PARAM,
						"申请人必须是法务经理！");
				}
				// 设置用户
				userId = order.getUserId();
				userAccount = order.getUserAccount();
				userName = order.getUserName();
				
				/// 判定新增或修改
				if (recoveryId <= 0) {
					// 进入新增
					// 插入主表
					ProjectRecoveryDO projectRecoveryDO = new ProjectRecoveryDO();
					MiscUtil.copyPoObject(projectRecoveryDO, order);
					if (order.getRecoveryStatus() != null) {
						projectRecoveryDO.setRecoveryStatus(order.getRecoveryStatus().code());
					} else {
						projectRecoveryDO.setRecoveryStatus(ProjectRecoveryStatusEnum.RECOVERYING
							.code());
					}
					if (order.getLitigationOn() != null) {
						projectRecoveryDO.setLitigationOn(order.getLitigationOn().code());
					}
					if (order.getDebtorReorganizationOn() != null) {
						projectRecoveryDO.setDebtorReorganizationOn(order
							.getDebtorReorganizationOn().code());
					}
					ProjectRecoveryDO findByProjectDO = projectRecoveryDAO.findByProjectCode(order
						.getProjectCode());
					if (findByProjectDO != null) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.EXECUTE_FAILURE,
							"已存在数目，不能新增！");
					}
					// 设置法务经理
					projectRecoveryDO.setLegalManagerId(rData.getUserId());
					projectRecoveryDO.setLegalManagerName(rData.getUserName());
					projectRecoveryDO.setRawAddTime(now);
					projectRecoveryDO.setStatusUpdateTime(now);
					recoveryId = projectRecoveryDAO.insert(projectRecoveryDO);
					order.setId(recoveryId);
				} else {
					ProjectRecoveryDO projectRecoveryDO = projectRecoveryDAO.findById(recoveryId);
					// 保留状态更新时间
					Date statusUpdateTime = projectRecoveryDO.getStatusUpdateTime();
					MiscUtil.copyPoObject(projectRecoveryDO, order);
					projectRecoveryDO.setStatusUpdateTime(statusUpdateTime);
					if (order.getRecoveryStatus() != null) {
						projectRecoveryDO.setRecoveryStatus(order.getRecoveryStatus().code());
					} else {
						projectRecoveryDO.setRecoveryStatus(ProjectRecoveryStatusEnum.RECOVERYING
							.code());
					}
					if (order.getLitigationOn() != null) {
						projectRecoveryDO.setLitigationOn(order.getLitigationOn().code());
					}
					if (order.getDebtorReorganizationOn() != null) {
						projectRecoveryDO.setDebtorReorganizationOn(order
							.getDebtorReorganizationOn().code());
					}
					// 设置法务经理
					projectRecoveryDO.setLegalManagerId(rData.getUserId());
					projectRecoveryDO.setLegalManagerName(rData.getUserName());
					projectRecoveryDAO.update(projectRecoveryDO);
				}
				// 若插入失败
				if (recoveryId <= 0) {
					throw ExceptionFactory
						.newFcsException(FcsResultEnum.HAVE_NOT_DATA, "未获取到主表id！");
				}
				
				// 维护项目状态为追偿中
				
				ProjectDO projectDO = projectDAO.findByProjectCode(order.getProjectCode());
				if (projectDO.getCompPrincipalAmount().addTo(projectDO.getCompInterestAmount())
					.greaterThan(Money.zero())) {
					// 维护为追偿中
					projectDO.setStatus(ProjectStatusEnum.RECOVERY.code());
					projectDO.setPhases(ProjectPhasesEnum.RECOVERY_PHASES.code());
					projectDAO.update(projectDO);
				} else {
					//当项目风险处置会决议为代偿的项目，资金划付申请单中划付事由为代偿本金、代偿利息，并且审核通过的
					// 不满足条件的不允许追偿
					throw ExceptionFactory.newFcsException(FcsResultEnum.INCOMPLETE_REQ_PARAM,
						"此项目暂不能进行追偿！");
				}
				// 添加主表附件[暂无附件]
				
				// 第一大部分，添加【债务人重整或破产清算】信息
				ProjectRecoveryDebtorReorganizationOrder reorganizationOrder = order
					.getProjectRecoveryDebtorReorganizationOrder();
				if (BooleanEnum.YES == order.getDebtorReorganizationOn()
					&& reorganizationOrder != null) {
					long reorganizationId = 0;
					if (reorganizationOrder.getId() != null) {
						reorganizationId = reorganizationOrder.getId();
					}
					// 1.债务人重整或破产清算表 主表维护
					debtorReorganizationSave(recoveryId, reorganizationOrder, reorganizationId);
					
				} else {
					// 删除 债务人重整或破产清算表 所有信息
					// 1.债务人重整或破产清算表 主表
					projectRecoveryDebtorReorganizationDAO.deleteByRecoveryId(recoveryId);
					// 1.1 追偿跟踪表 - 债务人重整或破产清算表-债权人会议表
					projectRecoveryDebtorReorganizationDebtsCouncilDAO
						.deleteByRecoveryId(recoveryId);
					// 1.2  追偿跟踪表 - 债务人重整或破产清算表-回收金额明细
					projectRecoveryDebtorReorganizationAmountDetailDAO.deleteByRecoveryIdAndType(
						recoveryId, ProjectRecoveryTypeEnum.DEBTOR_REORGANIZATION.code());
					// 1.3  追偿跟踪表 - 债务人重整或破产清算表-抵质押资产抵债明细 
					projectRecoveryDebtorReorganizationPledgeDAO.deleteByRecoveryIdAndType(
						recoveryId, ProjectRecoveryTypeEnum.DEBTOR_REORGANIZATION.code());
				}
				// 第二大部分，添加【诉讼】信息
				if (BooleanEnum.YES == order.getLitigationOn()) {
					// 1、 追偿跟踪表 - 诉讼-诉前保全
					litigationBeforePreservationSave(order, recoveryId);
					
					// 2. 追偿跟踪表 - 诉讼-立案
					litigationPlaceOnFileSave(order, recoveryId);
					// 3 .追偿跟踪表 - 诉讼-诉讼保全
					litigationPreservationSave(order, recoveryId);
					
					// 4. 追偿跟踪表 - 诉讼-庭前准备 
					litigationBeforeTrailSave(order, recoveryId);
					
					// 5. 追偿跟踪表 - 诉讼-开庭
					litigationOpeningSave(order, recoveryId);
					// 6. 追偿跟踪表 - 诉讼-裁判 
					litigationRefereeSave(order, recoveryId);
					// 7. 追偿跟踪表 - 诉讼-二审上述
					litigationSecondAppealSave(order, recoveryId);
					// 8 . 追偿跟踪表 - 诉讼-实现担保物权特别程序 
					litigationSpecialProcedureSave(order, recoveryId);
					// 9. 追偿跟踪表 - 诉讼-强制执行公证执行证书
					litigationCertificateSave(order, recoveryId);
					
					// 10 . 追偿跟踪表 - 诉讼-执行   
					litigationExecuteSave(order, recoveryId);
					
					// 11 . 追偿跟踪表 - 诉讼-再审程序
					litigationAdjournedProcedureSave(order, recoveryId);
					// 12   再审程序二审
					
					litigationAdjournedSecondSave(order, recoveryId);
					// 13 . 追偿跟踪表 - 诉讼-执行回转
					litigationExecuteGyrationSave(order, recoveryId);
				} else {
					// 执行删除操作 
					// 1、 追偿跟踪表 - 诉讼-诉前保全
					projectRecoveryLitigationBeforePreservationDAO.deleteByRecoveryId(recoveryId);
					// 1.1 追偿跟踪表 - 诉讼-诉前保全-保全措施 
					projectRecoveryLitigationBeforePreservationPrecautionDAO
						.deleteByRecoveryIdAndType(recoveryId,
							ProjectRecoveryLitigationTypeEnum.LITIGATION_BEFORE.code());
					// 2. 追偿跟踪表 - 诉讼-立案
					projectRecoveryLitigationPlaceOnFileDAO.deleteByRecoveryId(recoveryId);
					// 3 .追偿跟踪表 - 诉讼-诉讼保全
					projectRecoveryLitigationPreservationDAO.deleteByRecoveryId(recoveryId);
					// 3.1 追偿跟踪表 - 诉讼-诉讼保全-保全措施 
					projectRecoveryLitigationBeforePreservationPrecautionDAO
						.deleteByRecoveryIdAndType(recoveryId,
							ProjectRecoveryLitigationTypeEnum.LITIGATION_IN.code());
					// 4. 追偿跟踪表 - 诉讼-庭前准备 
					projectRecoveryLitigationBeforeTrailDAO.deleteByRecoveryId(recoveryId);
					// 5. 追偿跟踪表 - 诉讼-开庭
					projectRecoveryLitigationOpeningDAO.deleteByRecoveryId(recoveryId);
					// 6. 追偿跟踪表 - 诉讼-裁判 
					projectRecoveryLitigationRefereeDAO.deleteByRecoveryId(recoveryId);
					// 7. 追偿跟踪表 - 诉讼-二审上述
					projectRecoveryLitigationSecondAppealDAO.deleteByRecoveryId(recoveryId);
					// 8 . 追偿跟踪表 - 诉讼-实现担保物权特别程序 
					projectRecoveryLitigationSpecialProcedureDAO.deleteByRecoveryId(recoveryId);
					// 9. 追偿跟踪表 - 诉讼-强制执行公证执行证书
					projectRecoveryLitigationCertificateDAO.deleteByRecoveryId(recoveryId);
					// 10 . 追偿跟踪表 - 诉讼-执行   
					projectRecoveryLitigationExecuteDAO.deleteByRecoveryId(recoveryId);
					// 10.1  追偿跟踪表 - 诉讼-执行-执行内容 
					projectRecoveryLitigationExecuteStuffDAO.deleteByRecoveryId(recoveryId);
					// 10.2 追偿跟踪表 - 诉讼-执行-回收金额明细
					projectRecoveryDebtorReorganizationAmountDetailDAO.deleteByRecoveryIdAndType(
						recoveryId, ProjectRecoveryTypeEnum.LITIGATION_EXECUTE.code());
					// 10.3 追偿跟踪表 - 诉讼-执行-抵质押资产抵债明细 
					projectRecoveryDebtorReorganizationPledgeDAO.deleteByRecoveryIdAndType(
						recoveryId, ProjectRecoveryTypeEnum.LITIGATION_EXECUTE.code());
					// 11 . 追偿跟踪表 - 诉讼-再审程序
					projectRecoveryLitigationAdjournedProcedureDAO.deleteByRecoveryId(recoveryId);
					// 12   再审程序二审
					projectRecoveryLitigationAdjournedSecondDAO.deleteByRecoveryId(recoveryId);
					// 13 . 追偿跟踪表 - 诉讼-执行回转
					projectRecoveryLitigationExecuteGyrationDAO.deleteByRecoveryId(recoveryId);
				}
				
				return null;
			}
			
			private void litigationAdjournedSecondSave(final ProjectRecoveryOrder order,
														long recoveryId) {
				ProjectRecoveryLitigationAdjournedSecondOrder secondOrder = order
					.getProjectRecoveryLitigationAdjournedSecondOrder();
				if (secondOrder != null && StringUtil.isNotBlank(secondOrder.getAppealCenter())) {
					// 代表有保存任务
					long secondId = secondOrder.getId() == null ? 0 : secondOrder.getId();
					if (secondId > 0) {
						// 修改
						ProjectRecoveryLitigationAdjournedSecondDO executeDO = projectRecoveryLitigationAdjournedSecondDAO
							.findById(secondId);
						convertLitigationAdjournedSecondOrder2DO(recoveryId, secondOrder, executeDO);
						projectRecoveryLitigationAdjournedSecondDAO.update(executeDO);
					} else {
						// 添加
						ProjectRecoveryLitigationAdjournedSecondDO executeDO = new ProjectRecoveryLitigationAdjournedSecondDO();
						convertLitigationAdjournedSecondOrder2DO(recoveryId, secondOrder, executeDO);
						
						executeDO.setRawAddTime(FcsPmDomainHolder.get().getSysDate());
						secondId = projectRecoveryLitigationAdjournedSecondDAO.insert(executeDO);
					}
					// 附件
					saveLitigationAdjournedSecondAttachment(recoveryId, secondOrder, secondId);
					
				}
			}
			
			private void convertLitigationAdjournedSecondOrder2DO(	long recoveryId,
																	ProjectRecoveryLitigationAdjournedSecondOrder secondOrder,
																	ProjectRecoveryLitigationAdjournedSecondDO executeDO) {
				MiscUtil.copyPoObject(executeDO, secondOrder);
				executeDO.setProjectRecoveryId(recoveryId);
				
				try {
					executeDO.setNoticeTime(DateUtil.string2Date(secondOrder.getNoticeTime()));
					executeDO.setOpeningTime(DateUtil.string2Date(secondOrder.getOpeningTime()));
				} catch (ParseException e) {
				}
			}
			
			private void saveLitigationAdjournedSecondAttachment(	long recoveryId,
																	ProjectRecoveryLitigationAdjournedSecondOrder secondOrder,
																	long secondId) {
				/*** 诉讼-再审程序 二审-上诉请求-附件 **/
				setAttatment(
					recoveryId,
					secondId,
					secondOrder.getRecoveryLitigationAdjournedSecondAppealDemandUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_ADJOURNED_SECOND_APPEAL_DEMAND);
				/*** 诉讼-再审程序 二审-公告时间-附件 **/
				setAttatment(recoveryId, secondId,
					secondOrder.getRecoveryLitigationAdjournedSecondNoticeTimeUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_ADJOURNED_SECOND_NOTICE_TIME);
				/*** 诉讼-再审程序 二审-开庭时间-附件 **/
				setAttatment(recoveryId, secondId,
					secondOrder.getRecoveryLitigationAdjournedSecondOpeningTimeUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_ADJOURNED_SECOND_OPENING_TIME);
				/*** 诉讼-再审程序 二审-新证据-附件 **/
				setAttatment(recoveryId, secondId,
					secondOrder.getRecoveryLitigationAdjournedSecondNewEvidenceUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_ADJOURNED_SECOND_NEW_EVIDENCE);
				/*** 诉讼-再审程序 二审-争议焦点-附件 **/
				setAttatment(
					recoveryId,
					secondId,
					secondOrder.getRecoveryLitigationAdjournedSecondControversyFocusUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_ADJOURNED_SECOND_CONTROVERSY_FOCUS);
				/*** 诉讼-再审程序 二审-备注-附件 **/
				setAttatment(recoveryId, secondId,
					secondOrder.getRecoveryLitigationAdjournedSecondMemoUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_ADJOURNED_SECOND_MEMO);
			}
			
			private void litigationExecuteGyrationSave(final ProjectRecoveryOrder order,
														long recoveryId) {
				ProjectRecoveryLitigationExecuteGyrationOrder gyrationOrder = order
					.getProjectRecoveryLitigationExecuteGyrationOrder();
				
				if (gyrationOrder != null
					&& (StringUtil.isNotBlank(gyrationOrder.getMemo()) || StringUtil
						.isNotBlank(gyrationOrder.getRecoveryLitigationExecuteGyrationMemoUrl()))) {
					// 代表有保存任务
					long gyrationId = gyrationOrder.getId() == null ? 0 : gyrationOrder.getId();
					if (gyrationId > 0) {
						// 修改
						ProjectRecoveryLitigationExecuteGyrationDO gyrationDO = projectRecoveryLitigationExecuteGyrationDAO
							.findById(gyrationId);
						MiscUtil.copyPoObject(gyrationDO, gyrationOrder);
						gyrationDO.setProjectRecoveryId(recoveryId);
						projectRecoveryLitigationExecuteGyrationDAO.update(gyrationDO);
					} else {
						// 添加
						ProjectRecoveryLitigationExecuteGyrationDO gyrationDO = new ProjectRecoveryLitigationExecuteGyrationDO();
						MiscUtil.copyPoObject(gyrationDO, gyrationOrder);
						gyrationDO.setProjectRecoveryId(recoveryId);
						
						gyrationDO.setRawAddTime(FcsPmDomainHolder.get().getSysDate());
						gyrationId = projectRecoveryLitigationExecuteGyrationDAO.insert(gyrationDO);
					}
					// 附件
					
					saveLitigationExecuteGyrationAttachment(recoveryId, gyrationOrder, gyrationId);
					
				} else {
					// 执行删除任务
					projectRecoveryLitigationExecuteGyrationDAO.deleteByRecoveryId(recoveryId);
				}
			}
			
			private void saveLitigationExecuteGyrationAttachment(	long recoveryId,
																	ProjectRecoveryLitigationExecuteGyrationOrder gyrationOrder,
																	long gyrationId) {
				/*** 诉讼-执行回转-默认附件-附件 **/
				setAttatment(recoveryId, gyrationId,
					gyrationOrder.getRecoveryLitigationExecuteGyrationMemoUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_EXECUTE_GYRATION_MEMO);
			}
			
			private void litigationAdjournedProcedureSave(final ProjectRecoveryOrder order,
															long recoveryId) {
				List<ProjectRecoveryLitigationAdjournedProcedureOrder> procedureOrders = order
					.getProjectRecoveryLitigationAdjournedProcedureOrder();
				
				if (procedureOrders != null && ListUtil.isNotEmpty(procedureOrders)) {
					// 查出原本的数据
					List<ProjectRecoveryLitigationAdjournedProcedureDO> oldProcedureDOs = projectRecoveryLitigationAdjournedProcedureDAO
						.findByRecoveryId(recoveryId);
					// 循环保存任务
					for (ProjectRecoveryLitigationAdjournedProcedureOrder procedureOrder : procedureOrders) {
						long openingId = procedureOrder.getId() == null ? 0 : procedureOrder
							.getId();
						if (openingId > 0) {
							// 修改任务
							// 先剔除删除任务数据
							Iterator<ProjectRecoveryLitigationAdjournedProcedureDO> iter = oldProcedureDOs
								.iterator();
							while (iter.hasNext()) {
								ProjectRecoveryLitigationAdjournedProcedureDO curDO = iter.next();
								if (curDO.getId() == procedureOrder.getId()) {
									iter.remove();
								}
							}
							// 执行修改任务
							ProjectRecoveryLitigationAdjournedProcedureDO procedureDO = projectRecoveryLitigationAdjournedProcedureDAO
								.findById(openingId);
							convertLitigationAdjournedProcedureOrder2DO(recoveryId, procedureOrder,
								procedureDO);
							projectRecoveryLitigationAdjournedProcedureDAO.update(procedureDO);
						} else {
							// 添加任务 
							ProjectRecoveryLitigationAdjournedProcedureDO procedureDO = new ProjectRecoveryLitigationAdjournedProcedureDO();
							convertLitigationAdjournedProcedureOrder2DO(recoveryId, procedureOrder,
								procedureDO);
							
							procedureDO.setRawAddTime(FcsPmDomainHolder.get().getSysDate());
							openingId = projectRecoveryLitigationAdjournedProcedureDAO
								.insert(procedureDO);
						}
						// 附件
						
						saveLitigationAdjournedProcedureAttachment(recoveryId, procedureOrder,
							openingId);
					}
					// 不需要的数据执行删除任务
					for (ProjectRecoveryLitigationAdjournedProcedureDO procedureDO : oldProcedureDOs) {
						projectRecoveryLitigationAdjournedProcedureDAO.deleteById(procedureDO
							.getId());
					}
					
				} else {
					// 删除任务 
					projectRecoveryLitigationAdjournedProcedureDAO.deleteByRecoveryId(recoveryId);
				}
			}
			
			private void convertLitigationAdjournedProcedureOrder2DO(	long recoveryId,
																		ProjectRecoveryLitigationAdjournedProcedureOrder procedureOrder,
																		ProjectRecoveryLitigationAdjournedProcedureDO procedureDO) {
				MiscUtil.copyPoObject(procedureDO, procedureOrder);
				if (procedureOrder.getProcedureType() != null) {
					procedureDO.setProcedureType(procedureOrder.getProcedureType().code());
				}
				procedureDO.setProjectRecoveryId(recoveryId);
				try {
					procedureDO.setOpeningTime(DateUtil.string2Date(procedureOrder.getOpeningTime()));
				} catch (ParseException e) {
				}
			}
			
			private void saveLitigationAdjournedProcedureAttachment(long recoveryId,
																	ProjectRecoveryLitigationAdjournedProcedureOrder procedureOrder,
																	long openingId) {
				/*** 诉讼-再审程序-我方主要诉讼请求或答辩意见-附件 **/
				setAttatment(
					recoveryId,
					openingId,
					procedureOrder.getRecoveryLitigationAdjournedProcedureWeLitigationDemandUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_ADJOURNED_PROCEDURE_WE_LITIGATION_DEMAND);
				/*** 诉讼-再审程序-补充证据-附件 **/
				setAttatment(
					recoveryId,
					openingId,
					procedureOrder.getRecoveryLitigationAdjournedProcedureAdditionalEvidenceUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_ADJOURNED_PROCEDURE_ADDITIONAL_EVIDENCE);
				/*** 诉讼-再审程序-备注-附件 **/
				setAttatment(recoveryId, openingId,
					procedureOrder.getRecoveryLitigationAdjournedProcedureMemoUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_ADJOURNED_PROCEDURE_MEMO);
			}
			
			private void litigationExecuteSave(final ProjectRecoveryOrder order, long recoveryId) {
				ProjectRecoveryLitigationExecuteOrder executeOrder = order
					.getProjectRecoveryLitigationExecuteOrder();
				if (executeOrder != null && StringUtil.isNotBlank(executeOrder.getAcceptingCourt())) {
					// 代表有保存任务
					long executeId = executeOrder.getId() == null ? 0 : executeOrder.getId();
					if (executeId > 0) {
						// 修改
						ProjectRecoveryLitigationExecuteDO executeDO = projectRecoveryLitigationExecuteDAO
							.findById(executeId);
						convertLitigationExecuteOrder2DO(recoveryId, executeOrder, executeDO);
						projectRecoveryLitigationExecuteDAO.update(executeDO);
					} else {
						// 添加
						ProjectRecoveryLitigationExecuteDO executeDO = new ProjectRecoveryLitigationExecuteDO();
						convertLitigationExecuteOrder2DO(recoveryId, executeOrder, executeDO);
						
						executeDO.setRawAddTime(FcsPmDomainHolder.get().getSysDate());
						executeId = projectRecoveryLitigationExecuteDAO.insert(executeDO);
					}
					// 附件
					
					saveLitigationExecuteAttachment(recoveryId, executeOrder, executeId);
					
					// 10.1  追偿跟踪表 - 诉讼-执行-执行内容  先删除再插入
					litigationExecuteStuffSave(recoveryId, executeOrder, executeId);
					
					// 10.2 追偿跟踪表 - 诉讼-执行-回收金额明细 先删除再插入
					debtorReorganizationAmountDetailSave(recoveryId, executeOrder, executeId);
					// 10.3 追偿跟踪表 - 诉讼-执行-抵质押资产抵债明细  先删除再插入
					debtorReorganizationPledgeSave(recoveryId, executeOrder, executeId);
				} else {
					// 执行删除任务
					//  追偿跟踪表 - 诉讼-执行   
					projectRecoveryLitigationExecuteDAO.deleteByRecoveryId(recoveryId);
					// 10.1  追偿跟踪表 - 诉讼-执行-执行内容 
					projectRecoveryLitigationExecuteStuffDAO.deleteByRecoveryId(recoveryId);
					// 10.2 追偿跟踪表 - 诉讼-执行-回收金额明细
					projectRecoveryDebtorReorganizationAmountDetailDAO.deleteByRecoveryIdAndType(
						recoveryId, ProjectRecoveryTypeEnum.LITIGATION_EXECUTE.code());
					// 10.3 追偿跟踪表 - 诉讼-执行-抵质押资产抵债明细 
					projectRecoveryDebtorReorganizationPledgeDAO.deleteByRecoveryIdAndType(
						recoveryId, ProjectRecoveryTypeEnum.LITIGATION_EXECUTE.code());
				}
			}
			
			private void convertLitigationExecuteOrder2DO(	long recoveryId,
															ProjectRecoveryLitigationExecuteOrder executeOrder,
															ProjectRecoveryLitigationExecuteDO executeDO) {
				MiscUtil.copyPoObject(executeDO, executeOrder);
				executeDO.setProjectRecoveryId(recoveryId);
				if (executeOrder.getRecoveryTotalAmount() != null) {
					executeDO.setRecoveryTotalAmount(new Money(executeOrder
						.getRecoveryTotalAmount()));
				}
			}
			
			private void debtorReorganizationPledgeSave(long recoveryId,
														ProjectRecoveryLitigationExecuteOrder executeOrder,
														long executeId) {
				//				projectRecoveryDebtorReorganizationPledgeDAO.deleteByRecoveryIdAndType(recoveryId,
				//					ProjectRecoveryTypeEnum.LITIGATION_EXECUTE.code());
				//				if (executeOrder.getProjectRecoveryDebtorReorganizationPledgeOrder() != null) {
				//					// 循环插入
				//					for (ProjectRecoveryDebtorReorganizationPledgeOrder pledgeOrder : executeOrder
				//						.getProjectRecoveryDebtorReorganizationPledgeOrder()) {
				//						ProjectRecoveryDebtorReorganizationPledgeDO pledgeDO = new ProjectRecoveryDebtorReorganizationPledgeDO();
				//						MiscUtil.copyPoObject(pledgeDO, pledgeOrder);
				//						pledgeDO.setProjectRecoveryType(ProjectRecoveryTypeEnum.LITIGATION_EXECUTE
				//							.code());
				//						pledgeDO.setProjectRecoveryLitigationExecuteId(executeId);
				//						pledgeDO.setProjectRecoveryId(recoveryId);
				//						if (pledgeOrder.getPledgeAssetManagementMode() != null) {
				//							pledgeDO.setPledgeAssetManagementMode(pledgeOrder
				//								.getPledgeAssetManagementMode().code());
				//						}
				//						if (pledgeOrder.getPledgeAmount() != null) {
				//							pledgeDO.setPledgeAmount(new Money(pledgeOrder.getPledgeAmount()));
				//						}
				//						
				//						pledgeDO.setRawAddTime(FcsPmDomainHolder.get().getSysDate());
				//						long pledgeId = projectRecoveryDebtorReorganizationPledgeDAO
				//							.insert(pledgeDO);
				//						// 代表需要向业务经理发送【是否新增收款通知单】信息
				//						FcsPmDomainHolder.get().addAttribute("sendBusiManagerMessage", "send");
				//						
				//						// 添加附件   
				//						
				//						saveDebtorReorganizationPledgeAttachment(recoveryId, pledgeOrder, pledgeId);
				//					}
				//				}
				
				List<ProjectRecoveryDebtorReorganizationPledgeOrder> pledgeOrders = executeOrder
					.getProjectRecoveryDebtorReorganizationPledgeOrder();
				if (pledgeOrders != null && ListUtil.isNotEmpty(pledgeOrders)) {
					// 查出原本的数据
					List<ProjectRecoveryDebtorReorganizationPledgeDO> oldPledgeDOs = projectRecoveryDebtorReorganizationPledgeDAO
						.findByRecoveryIdAndType(recoveryId,
							ProjectRecoveryTypeEnum.LITIGATION_EXECUTE.code());
					// 循环保存任务
					for (ProjectRecoveryDebtorReorganizationPledgeOrder pledgeOrder : pledgeOrders) {
						long pledgeId = pledgeOrder.getId() == null ? 0 : pledgeOrder.getId();
						if (pledgeId > 0) {
							// 修改任务
							// 先剔除删除任务数据
							Iterator<ProjectRecoveryDebtorReorganizationPledgeDO> iter = oldPledgeDOs
								.iterator();
							while (iter.hasNext()) {
								ProjectRecoveryDebtorReorganizationPledgeDO curDO = iter.next();
								if (curDO.getId() == pledgeOrder.getId()) {
									iter.remove();
								}
							}
							// 执行修改任务
							ProjectRecoveryDebtorReorganizationPledgeDO pledgeDO = projectRecoveryDebtorReorganizationPledgeDAO
								.findById(pledgeId);
							convertExecuteReorganizationPledge2DO(recoveryId, executeId,
								pledgeOrder, pledgeDO);
							projectRecoveryDebtorReorganizationPledgeDAO.update(pledgeDO);
						} else {
							// 添加任务 
							ProjectRecoveryDebtorReorganizationPledgeDO pledgeDO = new ProjectRecoveryDebtorReorganizationPledgeDO();
							convertExecuteReorganizationPledge2DO(recoveryId, executeId,
								pledgeOrder, pledgeDO);
							
							pledgeDO.setRawAddTime(FcsPmDomainHolder.get().getSysDate());
							pledgeId = projectRecoveryDebtorReorganizationPledgeDAO
								.insert(pledgeDO);
							// 代表需要向业务经理发送【是否新增收款通知单】信息
							FcsPmDomainHolder.get().addAttribute("sendBusiManagerMessage", "send");
						}
						// 附件
						
						// 添加附件  
						saveDebtorReorganizationPledgeAttachment(recoveryId, pledgeOrder, pledgeId);
					}
					// 不需要的数据执行删除任务
					for (ProjectRecoveryDebtorReorganizationPledgeDO refereeDO : oldPledgeDOs) {
						projectRecoveryDebtorReorganizationPledgeDAO.deleteById(refereeDO.getId());
					}
				} else {
					// 删除任务
					projectRecoveryDebtorReorganizationPledgeDAO.deleteByRecoveryIdAndType(
						recoveryId, ProjectRecoveryTypeEnum.LITIGATION_EXECUTE.code());
					// 同时删除所有图片数据
					commonAttachmentDAO.deleteByBizNoModuleType(
						String.valueOf(recoveryId),
						ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_EXECUTE_PLEDGE_SOLD_OUT_MEMO
							.code());
				}
				
			}
			
			private void saveDebtorReorganizationPledgeAttachment(	long recoveryId,
																	ProjectRecoveryDebtorReorganizationPledgeOrder pledgeOrder,
																	long pledgeId) {
				/*** 诉讼-抵质押资产抵债-默认附件-附件 **/
				setAttatment(recoveryId, pledgeId,
					pledgeOrder.getRecoveryLitigationExecutePledgeSoldOutMemoUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_EXECUTE_PLEDGE_SOLD_OUT_MEMO);
			}
			
			private void debtorReorganizationAmountDetailSave(	long recoveryId,
																ProjectRecoveryLitigationExecuteOrder executeOrder,
																long executeId) {
				projectRecoveryDebtorReorganizationAmountDetailDAO.deleteByRecoveryIdAndType(
					recoveryId, ProjectRecoveryTypeEnum.LITIGATION_EXECUTE.code());
				if (executeOrder.getProjectRecoveryDebtorReorganizationAmountDetailOrder() != null) {
					// 循环插入
					for (ProjectRecoveryDebtorReorganizationAmountDetailOrder detailOrder : executeOrder
						.getProjectRecoveryDebtorReorganizationAmountDetailOrder()) {
						ProjectRecoveryDebtorReorganizationAmountDetailDO detailDO = new ProjectRecoveryDebtorReorganizationAmountDetailDO();
						MiscUtil.copyPoObject(detailDO, detailOrder);
						detailDO.setProjectRecoveryType(ProjectRecoveryTypeEnum.LITIGATION_EXECUTE
							.code());
						detailDO.setProjectRecoveryLitigationExecuteId(executeId);
						detailDO.setProjectRecoveryId(recoveryId);
						try {
							detailDO.setRecoveryTime(DateUtil.string2Date(detailOrder
								.getRecoveryTime()));
						} catch (ParseException e) {
						}
						if (detailOrder.getRecoveryAmount() != null) {
							detailDO.setRecoveryAmount(new Money(detailOrder.getRecoveryAmount()));
						}
						
						detailDO.setRawAddTime(FcsPmDomainHolder.get().getSysDate());
						projectRecoveryDebtorReorganizationAmountDetailDAO.insert(detailDO);
						
						// 添加附件   [暂无]
					}
				}
			}
			
			private void litigationExecuteStuffSave(long recoveryId,
													ProjectRecoveryLitigationExecuteOrder executeOrder,
													long executeId) {
				projectRecoveryLitigationExecuteStuffDAO.deleteByRecoveryId(recoveryId);
				// 同时删除所有图片数据
				commonAttachmentDAO.deleteByBizNoModuleType(String.valueOf(recoveryId),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_EXECUTE_STUFF_MEMO.code());
				if (executeOrder.getProjectRecoveryLitigationExecuteStuffOrder() != null) {
					// 循环插入
					for (ProjectRecoveryLitigationExecuteStuffOrder stuffOrder : executeOrder
						.getProjectRecoveryLitigationExecuteStuffOrder()) {
						ProjectRecoveryLitigationExecuteStuffDO stuffDO = new ProjectRecoveryLitigationExecuteStuffDO();
						MiscUtil.copyPoObject(stuffDO, stuffOrder);
						stuffDO.setDescribeType(stuffOrder.getDescribeType().code());
						stuffDO.setProjectRecoveryLitigationExecuteId(executeId);
						stuffDO.setProjectRecoveryId(recoveryId);
						stuffDO.setRawAddTime(FcsPmDomainHolder.get().getSysDate());
						long stuffId = projectRecoveryLitigationExecuteStuffDAO.insert(stuffDO);
						
						// 添加附件  
						
						saveLitigationExecuteStuffAttachment(recoveryId, stuffOrder, stuffId);
					}
				}
			}
			
			private void saveLitigationExecuteStuffAttachment(	long recoveryId,
																ProjectRecoveryLitigationExecuteStuffOrder stuffOrder,
																long stuffId) {
				/*** 诉讼-抵质押资产抵债-默认附件-附件 **/
				setAttatment(recoveryId, stuffId,
					stuffOrder.getRecoveryLitigationExecuteStuffMemoUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_EXECUTE_STUFF_MEMO);
			}
			
			private void saveLitigationExecuteAttachment(	long recoveryId,
															ProjectRecoveryLitigationExecuteOrder executeOrder,
															long executeId) {
				/*** 诉讼-执行-强制执行申请-附件 **/
				setAttatment(recoveryId, executeId,
					executeOrder.getRecoveryLitigationExecuteExecuteApplyUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_EXECUTE_EXECUTE_APPLY);
				/*** 诉讼-执行-立案-附件 **/
				setAttatment(recoveryId, executeId,
					executeOrder.getRecoveryLitigationExecutePlaceOnFileUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_EXECUTE_PLACE_ON_FILE);
				/*** 诉讼-执行-受理法院-附件 **/
				setAttatment(recoveryId, executeId,
					executeOrder.getRecoveryLitigationExecuteAcceptingCourtUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_EXECUTE_ACCEPTING_COURT);
				/*** 诉讼-执行-执行和解-附件 **/
				setAttatment(recoveryId, executeId,
					executeOrder.getRecoveryLitigationExecuteCompromiseUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_EXECUTE_COMPROMISE);
				/*** 诉讼-执行-调解-附件 **/
				setAttatment(recoveryId, executeId,
					executeOrder.getRecoveryLitigationExecuteConciliationUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_EXECUTE_CONCILIATION);
				/*** 诉讼-执行-评估-附件 **/
				setAttatment(recoveryId, executeId,
					executeOrder.getRecoveryLitigationExecuteEstimateUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_EXECUTE_ESTIMATE);
				
				/*** 诉讼-执行-备注-附件 **/
				setAttatment(recoveryId, executeId,
					executeOrder.getRecoveryLitigationExecuteMemoUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_EXECUTE_MEMO);
			}
			
			private void litigationCertificateSave(final ProjectRecoveryOrder order, long recoveryId) {
				ProjectRecoveryLitigationCertificateOrder certificateOrder = order
					.getProjectRecoveryLitigationCertificateOrder();
				if (certificateOrder != null
					&& StringUtil.isNotBlank(certificateOrder.getNotaryOrgan())) {
					// 代表有保存任务
					long certificateId = certificateOrder.getId() == null ? 0 : certificateOrder
						.getId();
					if (certificateId > 0) {
						// 修改
						ProjectRecoveryLitigationCertificateDO certificateDO = projectRecoveryLitigationCertificateDAO
							.findById(certificateId);
						convertLitigationCertificateOrder2DO(recoveryId, certificateOrder,
							certificateDO);
						projectRecoveryLitigationCertificateDAO.update(certificateDO);
					} else {
						// 添加
						ProjectRecoveryLitigationCertificateDO certificateDO = new ProjectRecoveryLitigationCertificateDO();
						convertLitigationCertificateOrder2DO(recoveryId, certificateOrder,
							certificateDO);
						
						certificateDO.setRawAddTime(FcsPmDomainHolder.get().getSysDate());
						certificateId = projectRecoveryLitigationCertificateDAO
							.insert(certificateDO);
					}
					// 附件
					
					saveLitigationCertificateAttachment(recoveryId, certificateOrder, certificateId);
				} else {
					// 执行删除任务
					projectRecoveryLitigationCertificateDAO.deleteByRecoveryId(recoveryId);
				}
			}
			
			private void convertLitigationCertificateOrder2DO(	long recoveryId,
																ProjectRecoveryLitigationCertificateOrder certificateOrder,
																ProjectRecoveryLitigationCertificateDO certificateDO) {
				MiscUtil.copyPoObject(certificateDO, certificateOrder);
				certificateDO.setProjectRecoveryId(recoveryId);
				try {
					certificateDO.setApplyTime(DateUtil.string2Date(certificateOrder.getApplyTime()));
					certificateDO.setPayTime(DateUtil.string2Date(certificateOrder.getPayTime()));
				} catch (ParseException e) {
				}
			}
			
			private void saveLitigationCertificateAttachment(	long recoveryId,
																ProjectRecoveryLitigationCertificateOrder certificateOrder,
																long certificateId) {
				/*** 诉讼-强制执行公证执行证书-执行证书-附件 **/
				setAttatment(recoveryId, certificateId,
					certificateOrder.getRecoveryLitigationCertificateNoUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_CERTIFICATE_NO);
				/*** 诉讼-强制执行公证执行证书-申请书-附件 **/
				setAttatment(recoveryId, certificateId,
					certificateOrder.getRecoveryLitigationCertificateDeclarationUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_CERTIFICATE_DECLARATION);
				/*** 诉讼-强制执行公证执行证书-证据清单及证据-附件 **/
				setAttatment(recoveryId, certificateId,
					certificateOrder.getRecoveryLitigationCertificateEvidenceUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_CERTIFICATE_EVIDENCE);
				/*** 诉讼-强制执行公证执行证书-缴费通知书-附件 **/
				setAttatment(recoveryId, certificateId,
					certificateOrder.getRecoveryLitigationCertificatePayNoticeUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_CERTIFICATE_PAY_NOTICE);
				/*** 诉讼-强制执行公证执行证书-其他-附件 **/
				setAttatment(recoveryId, certificateId,
					certificateOrder.getRecoveryLitigationCertificateOtherUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_CERTIFICATE_OTHER);
				/*** 诉讼-强制执行公证执行证书-备注-附件 **/
				setAttatment(recoveryId, certificateId,
					certificateOrder.getRecoveryLitigationCertificateMemoUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_CERTIFICATE_MEMO);
			}
			
			private void litigationSpecialProcedureSave(final ProjectRecoveryOrder order,
														long recoveryId) {
				ProjectRecoveryLitigationSpecialProcedureOrder procedureOrder = order
					.getProjectRecoveryLitigationSpecialProcedureOrder();
				if (procedureOrder != null
					&& StringUtil.isNotBlank(procedureOrder.getAcceptingCourt())) {
					// 代表有保存任务
					long procedureId = procedureOrder.getId() == null ? 0 : procedureOrder.getId();
					if (procedureId > 0) {
						// 修改
						ProjectRecoveryLitigationSpecialProcedureDO procedureDO = projectRecoveryLitigationSpecialProcedureDAO
							.findById(procedureId);
						convertLitigationSpecialProcedureOrder2DO(recoveryId, procedureOrder,
							procedureDO);
						projectRecoveryLitigationSpecialProcedureDAO.update(procedureDO);
					} else {
						// 添加
						ProjectRecoveryLitigationSpecialProcedureDO procedureDO = new ProjectRecoveryLitigationSpecialProcedureDO();
						convertLitigationSpecialProcedureOrder2DO(recoveryId, procedureOrder,
							procedureDO);
						
						procedureDO.setRawAddTime(FcsPmDomainHolder.get().getSysDate());
						procedureId = projectRecoveryLitigationSpecialProcedureDAO
							.insert(procedureDO);
					}
					// 附件
					
					saveLitigationSpecialProcedureAttachment(recoveryId, procedureOrder,
						procedureId);
				} else {
					// 执行删除任务
					projectRecoveryLitigationSpecialProcedureDAO.deleteByRecoveryId(recoveryId);
				}
			}
			
			private void convertLitigationSpecialProcedureOrder2DO(	long recoveryId,
																	ProjectRecoveryLitigationSpecialProcedureOrder procedureOrder,
																	ProjectRecoveryLitigationSpecialProcedureDO procedureDO) {
				MiscUtil.copyPoObject(procedureDO, procedureOrder);
				procedureDO.setProjectRecoveryId(recoveryId);
				try {
					procedureDO.setPlaceOnFileTime(DateUtil.string2Date(procedureOrder
						.getPlaceOnFileTime()));
					procedureDO.setPayTime(DateUtil.string2Date(procedureOrder.getPayTime()));
					procedureDO.setRefereeTime(DateUtil.string2Date(procedureOrder.getRefereeTime()));
				} catch (ParseException e) {
				}
			}
			
			private void saveLitigationSpecialProcedureAttachment(	long recoveryId,
																	ProjectRecoveryLitigationSpecialProcedureOrder procedureOrder,
																	long procedureId) {
				/*** 诉讼-实现担保物权特别程序-裁判文书-附件 **/
				setAttatment(
					recoveryId,
					procedureId,
					procedureOrder.getRecoveryLitigationSpecialProcedureRefereeClerkUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_SPECIAL_PROCEDURE_REFEREE_CLERK);
				/*** 诉讼-实现担保物权特别程序-诉状-附件 **/
				setAttatment(recoveryId, procedureId,
					procedureOrder.getRecoveryLitigationSpecialProcedurePetitionUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_SPECIAL_PROCEDURE_PETITION);
				/*** 诉讼-实现担保物权特别程序-证据清单及证据-附件 **/
				setAttatment(recoveryId, procedureId,
					procedureOrder.getRecoveryLitigationSpecialProcedureEvidenceUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_SPECIAL_PROCEDURE_EVIDENCE);
				/*** 诉讼-实现担保物权特别程序-案件受理通知书-附件 **/
				setAttatment(
					recoveryId,
					procedureId,
					procedureOrder.getRecoveryLitigationSpecialProcedureAcceptNoticeUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_SPECIAL_PROCEDURE_ACCEPT_NOTICE);
				/*** 诉讼-实现担保物权特别程序-缴费通知书-附件 **/
				setAttatment(recoveryId, procedureId,
					procedureOrder.getRecoveryLitigationSpecialProcedurePayNoticeUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_SPECIAL_PROCEDURE_PAY_NOTICE);
				/*** 诉讼-实现担保物权特别程序-备注-附件 **/
				setAttatment(recoveryId, procedureId,
					procedureOrder.getRecoveryLitigationSpecialProcedureMemoUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_SPECIAL_PROCEDURE_MEMO);
			}
			
			private void litigationSecondAppealSave(final ProjectRecoveryOrder order,
													long recoveryId) {
				List<ProjectRecoveryLitigationSecondAppealOrder> appealOrders = order
					.getProjectRecoveryLitigationSecondAppealOrder();
				if (appealOrders != null && ListUtil.isNotEmpty(appealOrders)) {
					// 查出原本的数据
					List<ProjectRecoveryLitigationSecondAppealDO> oldAppealDOs = projectRecoveryLitigationSecondAppealDAO
						.findByRecoveryId(recoveryId);
					// 循环保存任务
					for (ProjectRecoveryLitigationSecondAppealOrder appealOrder : appealOrders) {
						long appealId = appealOrder.getId() == null ? 0 : appealOrder.getId();
						if (appealId > 0) {
							// 修改任务
							// 先剔除删除任务数据
							Iterator<ProjectRecoveryLitigationSecondAppealDO> iter = oldAppealDOs
								.iterator();
							while (iter.hasNext()) {
								ProjectRecoveryLitigationSecondAppealDO curDO = iter.next();
								if (curDO.getId() == appealOrder.getId()) {
									iter.remove();
								}
							}
							// 执行修改任务
							ProjectRecoveryLitigationSecondAppealDO appealDO = projectRecoveryLitigationSecondAppealDAO
								.findById(appealId);
							convertLitigationSecondAppealOrder2DO(recoveryId, appealOrder, appealDO);
							
							projectRecoveryLitigationSecondAppealDAO.update(appealDO);
						} else {
							// 添加任务 
							ProjectRecoveryLitigationSecondAppealDO appealDO = new ProjectRecoveryLitigationSecondAppealDO();
							convertLitigationSecondAppealOrder2DO(recoveryId, appealOrder, appealDO);
							
							appealDO.setRawAddTime(FcsPmDomainHolder.get().getSysDate());
							appealId = projectRecoveryLitigationSecondAppealDAO.insert(appealDO);
						}
						// 附件
						
						saveLitigationSecondAppealAttachment(recoveryId, appealOrder, appealId);
					}
					// 不需要的数据执行删除任务
					for (ProjectRecoveryLitigationSecondAppealDO appealDO : oldAppealDOs) {
						projectRecoveryLitigationSecondAppealDAO.deleteById(appealDO.getId());
					}
				} else {
					// 删除任务
					projectRecoveryLitigationSecondAppealDAO.deleteByRecoveryId(recoveryId);
				}
			}
			
			private void convertLitigationSecondAppealOrder2DO(	long recoveryId,
																ProjectRecoveryLitigationSecondAppealOrder appealOrder,
																ProjectRecoveryLitigationSecondAppealDO appealDO) {
				MiscUtil.copyPoObject(appealDO, appealOrder);
				appealDO.setProjectRecoveryId(recoveryId);
				try {
					appealDO.setNoticeTime(DateUtil.string2Date(appealOrder.getNoticeTime()));
					appealDO.setOpeningTime(DateUtil.string2Date(appealOrder.getOpeningTime()));
				} catch (ParseException e) {
				}
			}
			
			private void saveLitigationSecondAppealAttachment(	long recoveryId,
																ProjectRecoveryLitigationSecondAppealOrder appealOrder,
																long appealId) {
				/*** 诉讼-二审上述-上诉请求-附件 **/
				setAttatment(recoveryId, appealId,
					appealOrder.getRecoveryLitigationSecondAppealAppealDemandUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_SECOND_APPEAL_APPEAL_DEMAND);
				/*** 诉讼-二审上述-公告时间-附件 **/
				setAttatment(recoveryId, appealId,
					appealOrder.getRecoveryLitigationSecondAppealNoticeTimeUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_SECOND_APPEAL_NOTICE_TIME);
				/*** 诉讼-二审上述-开庭时间-附件 **/
				setAttatment(recoveryId, appealId,
					appealOrder.getRecoveryLitigationSecondAppealOpeningTimeUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_SECOND_APPEAL_OPENING_TIME);
				/*** 诉讼-二审上述-新证据-附件 **/
				setAttatment(recoveryId, appealId,
					appealOrder.getRecoveryLitigationSecondAppealNewEvidenceUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_SECOND_APPEAL_NEW_EVIDENCE);
				/*** 诉讼-二审上述-争议焦点-附件 **/
				setAttatment(
					recoveryId,
					appealId,
					appealOrder.getRecoveryLitigationSecondAppealControversyFocusUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_SECOND_APPEAL_CONTROVERSY_FOCUS);
				/*** 诉讼-二审上述-备注-附件 **/
				setAttatment(recoveryId, appealId,
					appealOrder.getRecoveryLitigationSecondAppealMemoUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_SECOND_APPEAL_MEMO);
			}
			
			private void litigationRefereeSave(final ProjectRecoveryOrder order, long recoveryId) {
				List<ProjectRecoveryLitigationRefereeOrder> refereeOrders = order
					.getProjectRecoveryLitigationRefereeOrder();
				if (refereeOrders != null && ListUtil.isNotEmpty(refereeOrders)) {
					// 查出原本的数据
					List<ProjectRecoveryLitigationRefereeDO> oldRefereeDOs = projectRecoveryLitigationRefereeDAO
						.findByRecoveryId(recoveryId);
					// 循环保存任务
					for (ProjectRecoveryLitigationRefereeOrder refereeOrder : refereeOrders) {
						long refereeId = refereeOrder.getId() == null ? 0 : refereeOrder.getId();
						if (refereeId > 0) {
							// 修改任务
							// 先剔除删除任务数据
							Iterator<ProjectRecoveryLitigationRefereeDO> iter = oldRefereeDOs
								.iterator();
							while (iter.hasNext()) {
								ProjectRecoveryLitigationRefereeDO curDO = iter.next();
								if (curDO.getId() == refereeOrder.getId()) {
									iter.remove();
								}
							}
							// 执行修改任务
							ProjectRecoveryLitigationRefereeDO refereeDO = projectRecoveryLitigationRefereeDAO
								.findById(refereeId);
							convertLitigationRefereeOrder2DO(recoveryId, refereeOrder, refereeDO);
							
							projectRecoveryLitigationRefereeDAO.update(refereeDO);
						} else {
							// 添加任务 
							ProjectRecoveryLitigationRefereeDO refereeDO = new ProjectRecoveryLitigationRefereeDO();
							convertLitigationRefereeOrder2DO(recoveryId, refereeOrder, refereeDO);
							
							refereeDO.setRawAddTime(FcsPmDomainHolder.get().getSysDate());
							refereeId = projectRecoveryLitigationRefereeDAO.insert(refereeDO);
						}
						// 附件
						
						saveLitigationRefereeAttachment(recoveryId, refereeOrder, refereeId);
					}
					// 不需要的数据执行删除任务
					for (ProjectRecoveryLitigationRefereeDO refereeDO : oldRefereeDOs) {
						projectRecoveryLitigationRefereeDAO.deleteById(refereeDO.getId());
					}
				} else {
					// 删除任务
					projectRecoveryLitigationRefereeDAO.deleteByRecoveryId(recoveryId);
				}
			}
			
			private void convertLitigationRefereeOrder2DO(	long recoveryId,
															ProjectRecoveryLitigationRefereeOrder refereeOrder,
															ProjectRecoveryLitigationRefereeDO refereeDO) {
				MiscUtil.copyPoObject(refereeDO, refereeOrder);
				refereeDO.setProjectRecoveryId(recoveryId);
				if (refereeOrder.getProjectRecoveryRefereeType() != null) {
					refereeDO.setProjectRecoveryRefereeType(refereeOrder
						.getProjectRecoveryRefereeType().code());
				}
				try {
					refereeDO.setArrivedTime(DateUtil.string2Date(refereeOrder.getArrivedTime()));
					refereeDO.setNoticeTime(DateUtil.string2Date(refereeOrder.getNoticeTime()));
					refereeDO.setEffectiveTime(DateUtil.string2Date(refereeOrder.getEffectiveTime()));
				} catch (ParseException e) {
				}
			}
			
			private void saveLitigationRefereeAttachment(	long recoveryId,
															ProjectRecoveryLitigationRefereeOrder refereeOrder,
															long refereeId) {
				/*** 诉讼-裁判-裁判文书-附件 **/
				setAttatment(recoveryId, refereeId,
					refereeOrder.getRecoveryLitigationRefereeClerkUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_REFEREE_CLERK);
				/*** 诉讼-裁判-送达时间-附件 **/
				setAttatment(recoveryId, refereeId,
					refereeOrder.getRecoveryLitigationRefereeArrivedTimeUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_REFEREE_ARRIVED_TIME);
				/*** 诉讼-裁判-公告时间-附件 **/
				setAttatment(recoveryId, refereeId,
					refereeOrder.getRecoveryLitigationRefereeNoticeTimeUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_REFEREE_NOTICE_TIME);
				/*** 诉讼-裁判-生效时间-附件 **/
				setAttatment(recoveryId, refereeId,
					refereeOrder.getRecoveryLitigationRefereeEffectiveTimeUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_REFEREE_EFFECTIVE_TIME);
				/*** 诉讼-裁判-备注-附件 **/
				setAttatment(recoveryId, refereeId,
					refereeOrder.getRecoveryLitigationRefereeMemoUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_REFEREE_MEMO);
			}
			
			private void litigationOpeningSave(final ProjectRecoveryOrder order, long recoveryId) {
				List<ProjectRecoveryLitigationOpeningOrder> openingOrders = order
					.getProjectRecoveryLitigationOpeningOrder();
				if (openingOrders != null && ListUtil.isNotEmpty(openingOrders)) {
					// 查出原本的数据
					List<ProjectRecoveryLitigationOpeningDO> oldOpeningDOs = projectRecoveryLitigationOpeningDAO
						.findByRecoveryId(recoveryId);
					// 循环保存任务
					for (ProjectRecoveryLitigationOpeningOrder openingOrder : openingOrders) {
						long openingId = openingOrder.getId() == null ? 0 : openingOrder.getId();
						if (openingId > 0) {
							// 修改任务
							// 先剔除删除任务数据
							Iterator<ProjectRecoveryLitigationOpeningDO> iter = oldOpeningDOs
								.iterator();
							while (iter.hasNext()) {
								ProjectRecoveryLitigationOpeningDO curDO = iter.next();
								if (curDO.getId() == openingOrder.getId()) {
									iter.remove();
								}
							}
							// 执行修改任务
							ProjectRecoveryLitigationOpeningDO openingDO = projectRecoveryLitigationOpeningDAO
								.findById(openingId);
							convertLitigationOpeningOrder2DO(recoveryId, openingOrder, openingDO);
							
							projectRecoveryLitigationOpeningDAO.update(openingDO);
						} else {
							// 添加任务 
							ProjectRecoveryLitigationOpeningDO openingDO = new ProjectRecoveryLitigationOpeningDO();
							convertLitigationOpeningOrder2DO(recoveryId, openingOrder, openingDO);
							
							openingDO.setRawAddTime(FcsPmDomainHolder.get().getSysDate());
							openingId = projectRecoveryLitigationOpeningDAO.insert(openingDO);
						}
						// 附件
						
						saveLitigationOpeningAttachment(recoveryId, openingOrder, openingId);
					}
					// 不需要的数据执行删除任务
					for (ProjectRecoveryLitigationOpeningDO openingDO : oldOpeningDOs) {
						projectRecoveryLitigationOpeningDAO.deleteById(openingDO.getId());
					}
					
				} else {
					// 删除任务 
					projectRecoveryLitigationOpeningDAO.deleteByRecoveryId(recoveryId);
				}
			}
			
			private void convertLitigationOpeningOrder2DO(	long recoveryId,
															ProjectRecoveryLitigationOpeningOrder openingOrder,
															ProjectRecoveryLitigationOpeningDO openingDO) {
				MiscUtil.copyPoObject(openingDO, openingOrder);
				openingDO.setProjectRecoveryId(recoveryId);
				try {
					openingDO.setOpeningTime(DateUtil.string2Date(openingOrder.getOpeningTime()));
				} catch (ParseException e) {
				}
			}
			
			private void saveLitigationOpeningAttachment(	long recoveryId,
															ProjectRecoveryLitigationOpeningOrder openingOrder,
															long openingId) {
				/*** 诉讼-开庭-我方主要诉讼请求或答辩意见-附件 **/
				setAttatment(recoveryId, openingId,
					openingOrder.getRecoveryLitigationOpeningWeLitigationDemandUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_OPENING_WE_LITIGATION_DEMAND);
				/*** 诉讼-开庭-对方主要诉讼请求或答辩意见-附件 **/
				setAttatment(
					recoveryId,
					openingId,
					openingOrder.getRecoveryLitigationOpeningOtherSideLitigationDemandUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_OPENING_OTHER_SIDE_LITIGATION_DEMAND);
				/*** 诉讼-开庭-补充证据-附件 **/
				setAttatment(recoveryId, openingId,
					openingOrder.getRecoveryLitigationOpeningAdditionalEvidenceUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_OPENING_ADDITIONAL_EVIDENCE);
				/*** 诉讼-开庭-备注-附件 **/
				setAttatment(recoveryId, openingId,
					openingOrder.getRecoveryLitigationOpeningMemoUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_OPENING_MEMO);
			}
			
			private void litigationBeforeTrailSave(final ProjectRecoveryOrder order, long recoveryId) {
				ProjectRecoveryLitigationBeforeTrailOrder trailOrder = order
					.getProjectRecoveryLitigationBeforeTrailOrder();
				if (trailOrder != null && trailOrder.getOpeningTime() != null) {
					// 代表有保存任务
					long trailId = trailOrder.getId() == null ? 0 : trailOrder.getId();
					if (trailId > 0) {
						// 修改
						ProjectRecoveryLitigationBeforeTrailDO trailDO = projectRecoveryLitigationBeforeTrailDAO
							.findById(trailId);
						convertLitigationBeforeTrailOrder2DO(recoveryId, trailOrder, trailDO);
						
						projectRecoveryLitigationBeforeTrailDAO.update(trailDO);
					} else {
						// 添加
						ProjectRecoveryLitigationBeforeTrailDO trailDO = new ProjectRecoveryLitigationBeforeTrailDO();
						convertLitigationBeforeTrailOrder2DO(recoveryId, trailOrder, trailDO);
						
						trailDO.setRawAddTime(FcsPmDomainHolder.get().getSysDate());
						trailId = projectRecoveryLitigationBeforeTrailDAO.insert(trailDO);
					}
					// 附件
					
					saveLitigationBeforeTrailAttachment(recoveryId, trailOrder, trailId);
				} else {
					// 执行删除任务
					projectRecoveryLitigationBeforeTrailDAO.deleteByRecoveryId(recoveryId);
				}
			}
			
			private void convertLitigationBeforeTrailOrder2DO(	long recoveryId,
																ProjectRecoveryLitigationBeforeTrailOrder trailOrder,
																ProjectRecoveryLitigationBeforeTrailDO trailDO) {
				MiscUtil.copyPoObject(trailDO, trailOrder);
				trailDO.setProjectRecoveryId(recoveryId);
				if (trailOrder.getEndNotice() != null) {
					trailDO.setEndNotice(trailOrder.getEndNotice().code());
				}
				try {
					trailDO.setOpeningTime(DateUtil.string2Date(trailOrder.getOpeningTime()));
					trailDO.setNoticeTime(DateUtil.string2Date(trailOrder.getNoticeTime()));
					trailDO.setClerkArrivedTime(DateUtil.string2Date(trailOrder
						.getClerkArrivedTime()));
				} catch (ParseException e) {
				}
			}
			
			private void saveLitigationBeforeTrailAttachment(	long recoveryId,
																ProjectRecoveryLitigationBeforeTrailOrder trailOrder,
																long trailId) {
				/*** 诉讼-庭前准备-开庭时间-附件 **/
				setAttatment(recoveryId, trailId,
					trailOrder.getRecoveryLitigationBeforeTrailOpeningTimeUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_BEFORE_TRAIL_OPENING_TIME);
				/*** 诉讼-庭前准备-公告时间-附件 **/
				setAttatment(recoveryId, trailId,
					trailOrder.getRecoveryLitigationBeforeTrailNoticeTimeUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_BEFORE_TRAIL_NOTICE_TIME);
				/*** 诉讼-庭前准备-文书送达时间-附件 **/
				setAttatment(
					recoveryId,
					trailId,
					trailOrder.getRecoveryLitigationBeforeTrailClerkArrivedTimeUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_BEFORE_TRAIL_CLERK_ARRIVED_TIME);
				/*** 诉讼-庭前准备-管辖异议-附件 **/
				setAttatment(
					recoveryId,
					trailId,
					trailOrder.getRecoveryLitigationBeforeTrailJurisdictionObjectionUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_BEFORE_TRAIL_JURISDICTION_OBJECTION);
				/*** 诉讼-庭前准备-管辖异议裁定-附件 **/
				setAttatment(
					recoveryId,
					trailId,
					trailOrder.getRecoveryLitigationBeforeTrailJurisdictionObjectionJudgmentUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_BEFORE_TRAIL_JURISDICTION_OBJECTION_JUDGMENT);
				/*** 诉讼-庭前准备-管辖异议上诉-附件 **/
				setAttatment(
					recoveryId,
					trailId,
					trailOrder.getRecoveryLitigationBeforeTrailJurisdictionObjectionAppealUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_BEFORE_TRAIL_JURISDICTION_OBJECTION_APPEAL);
				/*** 诉讼-庭前准备-管辖异议二审裁定-附件 **/
				setAttatment(
					recoveryId,
					trailId,
					trailOrder
						.getRecoveryLitigationBeforeTrailJurisdictionObjectionJudgmentSecondUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_BEFORE_TRAIL_JURISDICTION_OBJECTION_JUDGMENT_SECOND);
				/*** 诉讼-庭前准备-证据交换-附件 **/
				setAttatment(
					recoveryId,
					trailId,
					trailOrder.getRecoveryLitigationBeforeTrailEvidenceExchangeUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_BEFORE_TRAIL_EVIDENCE_EXCHANGE);
				/*** 诉讼-庭前准备-鉴定申请-附件 **/
				setAttatment(recoveryId, trailId,
					trailOrder.getRecoveryLitigationBeforeTrailAppraisalApplyUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_BEFORE_TRAIL_APPRAISAL_APPLY);
				/*** 诉讼-庭前准备-鉴定材料-附件 **/
				setAttatment(
					recoveryId,
					trailId,
					trailOrder.getRecoveryLitigationBeforeTrailAppraisalMaterialUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_BEFORE_TRAIL_APPRAISAL_MATERIAL);
				/*** 诉讼-庭前准备-鉴定费用-附件 **/
				setAttatment(recoveryId, trailId,
					trailOrder.getRecoveryLitigationBeforeTrailAppraisalAmountUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_BEFORE_TRAIL_APPRAISAL_AMOUNT);
				/*** 诉讼-庭前准备-申请调查取证-附件 **/
				setAttatment(
					recoveryId,
					trailId,
					trailOrder.getRecoveryLitigationBeforeTrailInvestigatingApplyUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_BEFORE_TRAIL_INVESTIGATING_APPLY);
				/*** 诉讼-庭前准备-申请证人出庭-附件 **/
				setAttatment(recoveryId, trailId,
					trailOrder.getRecoveryLitigationBeforeTrailWitnessesApplyUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_BEFORE_TRAIL_WITNESSES_APPLY);
				/*** 诉讼-庭前准备-增加诉讼请求申请-附件 **/
				setAttatment(
					recoveryId,
					trailId,
					trailOrder.getRecoveryLitigationBeforeTrailIncreaseLitigationApplyUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_BEFORE_TRAIL_INCREASE_LITIGATION_APPLY);
				/*** 诉讼-庭前准备-memo附件-附件 **/
				setAttatment(recoveryId, trailId,
					trailOrder.getRecoveryLitigationBeforeTrailMemoUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_BEFORE_TRAIL_MEMO);
			}
			
			private void litigationPreservationSave(final ProjectRecoveryOrder order,
													long recoveryId) {
				ProjectRecoveryLitigationPreservationOrder preservationOrder = order
					.getProjectRecoveryLitigationPreservationOrder();
				if (preservationOrder != null
					&& StringUtil.isNotBlank(preservationOrder.getAcceptingCourt())) {
					// 代表有保存任务
					long preservationId = preservationOrder.getId() == null ? 0 : preservationOrder
						.getId();
					if (preservationId > 0) {
						// 修改任务
						ProjectRecoveryLitigationPreservationDO preservationDO = projectRecoveryLitigationPreservationDAO
							.findById(preservationId);
						convertLitigationPreservationOrder2DO(recoveryId, preservationOrder,
							preservationDO);
						
						projectRecoveryLitigationPreservationDAO.update(preservationDO);
					} else {
						// 新增任务
						ProjectRecoveryLitigationPreservationDO preservationDO = new ProjectRecoveryLitigationPreservationDO();
						convertLitigationPreservationOrder2DO(recoveryId, preservationOrder,
							preservationDO);
						
						preservationDO.setRawAddTime(FcsPmDomainHolder.get().getSysDate());
						preservationId = projectRecoveryLitigationPreservationDAO
							.insert(preservationDO);
					}
					
					// 附件 
					saveLitigationPreservationAttachment(recoveryId, preservationOrder,
						preservationId);
					
					// 3.1 追偿跟踪表 - 诉讼-诉讼保全-保全措施 
					litigationBeforePreservationPrecautionSave(recoveryId, preservationOrder,
						preservationId);
					
				} else {
					// 清空任务
					projectRecoveryLitigationPreservationDAO.deleteByRecoveryId(recoveryId);
					//  追偿跟踪表 - 诉讼-诉讼保全-保全措施 
					projectRecoveryLitigationBeforePreservationPrecautionDAO
						.deleteByRecoveryIdAndType(recoveryId,
							ProjectRecoveryLitigationTypeEnum.LITIGATION_IN.code());
					
				}
			}
			
			private void convertLitigationPreservationOrder2DO(	long recoveryId,
																ProjectRecoveryLitigationPreservationOrder preservationOrder,
																ProjectRecoveryLitigationPreservationDO preservationDO) {
				MiscUtil.copyPoObject(preservationDO, preservationOrder);
				preservationDO.setProjectRecoveryId(recoveryId);
				try {
					preservationDO.setApplyTime(DateUtil.string2Date(preservationOrder
						.getApplyTime()));
					preservationDO.setPayTime(DateUtil.string2Date(preservationOrder.getPayTime()));
					preservationDO.setPreservationTime(DateUtil.string2Date(preservationOrder
						.getPreservationTime()));
				} catch (ParseException e) {
				}
			}
			
			private void litigationBeforePreservationPrecautionSave(long recoveryId,
																	ProjectRecoveryLitigationPreservationOrder preservationOrder,
																	long preservationId) {
				//				// 先删除再插入
				//				projectRecoveryLitigationBeforePreservationPrecautionDAO.deleteByRecoveryIdAndType(
				//					recoveryId, ProjectRecoveryLitigationTypeEnum.LITIGATION_IN.code());
				//				if (preservationOrder
				//					.getProjectRecoveryLitigationBeforePreservationPrecautionOrder() != null) {
				//					for (ProjectRecoveryLitigationBeforePreservationPrecautionOrder precautionOrder : preservationOrder
				//						.getProjectRecoveryLitigationBeforePreservationPrecautionOrder()) {
				//						ProjectRecoveryLitigationBeforePreservationPrecautionDO precautionDO = new ProjectRecoveryLitigationBeforePreservationPrecautionDO();
				//						convertLitigationPreservationPrecautionOrder2DO(recoveryId, preservationId,
				//							precautionOrder, precautionDO);
				//						
				//						precautionDO.setRawAddTime(FcsPmDomainHolder.get().getSysDate());
				//						projectRecoveryLitigationBeforePreservationPrecautionDAO
				//							.insert(precautionDO);
				//						// 附件 【暂无】
				//					}
				//				}
				
				List<ProjectRecoveryLitigationBeforePreservationPrecautionOrder> precautionOrder2 = preservationOrder
					.getProjectRecoveryLitigationBeforePreservationPrecautionOrder();
				if (precautionOrder2 != null && ListUtil.isNotEmpty(precautionOrder2)) {
					// 查出原本的数据
					List<ProjectRecoveryLitigationBeforePreservationPrecautionDO> oldPrecautionDOs = projectRecoveryLitigationBeforePreservationPrecautionDAO
						.findByRecoveryIdAndType(recoveryId,
							ProjectRecoveryLitigationTypeEnum.LITIGATION_IN.code());
					// 循环保存任务
					for (ProjectRecoveryLitigationBeforePreservationPrecautionOrder precautionOrder : precautionOrder2) {
						long pledgeId = precautionOrder.getId() == null ? 0 : precautionOrder
							.getId();
						if (pledgeId > 0) {
							// 修改任务
							// 先剔除删除任务数据
							Iterator<ProjectRecoveryLitigationBeforePreservationPrecautionDO> iter = oldPrecautionDOs
								.iterator();
							while (iter.hasNext()) {
								ProjectRecoveryLitigationBeforePreservationPrecautionDO curDO = iter
									.next();
								if (curDO.getId() == precautionOrder.getId()) {
									iter.remove();
								}
							}
							// 执行修改任务
							ProjectRecoveryLitigationBeforePreservationPrecautionDO precautionDO = projectRecoveryLitigationBeforePreservationPrecautionDAO
								.findById(pledgeId);
							convertLitigationPreservationPrecautionOrder2DO(recoveryId,
								preservationId, precautionOrder, precautionDO);
							
							projectRecoveryLitigationBeforePreservationPrecautionDAO
								.update(precautionDO);
						} else {
							// 添加任务 
							ProjectRecoveryLitigationBeforePreservationPrecautionDO precautionDO = new ProjectRecoveryLitigationBeforePreservationPrecautionDO();
							convertLitigationPreservationPrecautionOrder2DO(recoveryId,
								preservationId, precautionOrder, precautionDO);
							
							precautionDO.setRawAddTime(FcsPmDomainHolder.get().getSysDate());
							
							pledgeId = projectRecoveryLitigationBeforePreservationPrecautionDAO
								.insert(precautionDO);
						}
						// 附件
						
						// 添加附件  
					}
					// 不需要的数据执行删除任务
					for (ProjectRecoveryLitigationBeforePreservationPrecautionDO precautionDO : oldPrecautionDOs) {
						projectRecoveryLitigationBeforePreservationPrecautionDAO
							.deleteById(precautionDO.getId());
					}
				} else {
					// 删除任务
					projectRecoveryLitigationBeforePreservationPrecautionDAO
						.deleteByRecoveryIdAndType(recoveryId,
							ProjectRecoveryLitigationTypeEnum.LITIGATION_IN.code());
					// 同时删除所有图片数据
				}
				
			}
			
			private void convertLitigationPreservationPrecautionOrder2DO(	long recoveryId,
																			long preservationId,
																			ProjectRecoveryLitigationBeforePreservationPrecautionOrder precautionOrder,
																			ProjectRecoveryLitigationBeforePreservationPrecautionDO precautionDO) {
				MiscUtil.copyPoObject(precautionDO, precautionOrder);
				precautionDO.setProjectRecoveryLitigationPreservationId(preservationId);
				precautionDO
					.setProjectRecoveryLitigationType(ProjectRecoveryLitigationTypeEnum.LITIGATION_IN
						.code());
				if (precautionOrder.getProjectRecoveryPreservationType() != null) {
					precautionDO.setProjectRecoveryPreservationType(precautionOrder
						.getProjectRecoveryPreservationType().code());
				}
				precautionDO.setProjectRecoveryId(recoveryId);
				
				try {
					precautionDO.setPreservationTimeStart(DateUtil.string2Date(precautionOrder
						.getPreservationTimeStart()));
					precautionDO.setPreservationTimeEnd(DateUtil.string2Date(precautionOrder
						.getPreservationTimeEnd()));
				} catch (ParseException e) {
				}
			}
			
			private void saveLitigationPreservationAttachment(	long recoveryId,
																ProjectRecoveryLitigationPreservationOrder preservationOrder,
																long preservationId) {
				/*** 诉讼-诉讼保全-保全裁定书-附件 **/
				setAttatment(recoveryId, preservationId,
					preservationOrder.getRecoveryLitigationPreservationWrittenVerdictUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_PRESERVATION_WRITTEN_VERDICT);
				/*** 诉讼-诉讼保全-协助执行通知书-附件 **/
				setAttatment(recoveryId, preservationId,
					preservationOrder.getRecoveryLitigationPreservationExecutionNoticeUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_PRESERVATION_EXECUTION_NOTICE);
				/*** 诉讼-诉讼保全-送达回执-附件 **/
				setAttatment(recoveryId, preservationId,
					preservationOrder.getRecoveryLitigationPreservationDeliveryReceiptUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_PRESERVATION_DELIVERY_RECEIPT);
				/*** 诉讼-诉讼保全-其他-附件 **/
				setAttatment(recoveryId, preservationId,
					preservationOrder.getRecoveryLitigationPreservationOtherUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_PRESERVATION_OTHER);
			}
			
			private void litigationPlaceOnFileSave(final ProjectRecoveryOrder order, long recoveryId) {
				ProjectRecoveryLitigationPlaceOnFileOrder placeOnFileOrder = order
					.getProjectRecoveryLitigationPlaceOnFileOrder();
				if (placeOnFileOrder != null
					&& StringUtil.isNotBlank(placeOnFileOrder.getAcceptingCourt())) {
					// 代表有保存任务
					long placeOnFileId = placeOnFileOrder.getId() == null ? 0 : placeOnFileOrder
						.getId();
					if (placeOnFileId > 0) {
						// 修改
						ProjectRecoveryLitigationPlaceOnFileDO placeOnFileDO = projectRecoveryLitigationPlaceOnFileDAO
							.findById(placeOnFileOrder.getId());
						convertPlaceOnFileOrder2DO(recoveryId, placeOnFileOrder, placeOnFileDO);
						
						projectRecoveryLitigationPlaceOnFileDAO.update(placeOnFileDO);
					} else {
						// 新增
						ProjectRecoveryLitigationPlaceOnFileDO placeOnFileDO = new ProjectRecoveryLitigationPlaceOnFileDO();
						convertPlaceOnFileOrder2DO(recoveryId, placeOnFileOrder, placeOnFileDO);
						
						placeOnFileDO.setRawAddTime(FcsPmDomainHolder.get().getSysDate());
						placeOnFileId = projectRecoveryLitigationPlaceOnFileDAO
							.insert(placeOnFileDO);
					}
					// 附件 
					
					saveLitigationPlaceOnFileAttachment(recoveryId, placeOnFileOrder, placeOnFileId);
				} else {
					// 删除历史任务
					projectRecoveryLitigationPlaceOnFileDAO.deleteByRecoveryId(recoveryId);
				}
			}
			
			private void convertPlaceOnFileOrder2DO(long recoveryId,
													ProjectRecoveryLitigationPlaceOnFileOrder placeOnFileOrder,
													ProjectRecoveryLitigationPlaceOnFileDO placeOnFileDO) {
				MiscUtil.copyPoObject(placeOnFileDO, placeOnFileOrder);
				placeOnFileDO.setProjectRecoveryId(recoveryId);
				try {
					placeOnFileDO.setPlaceOnFileTime(DateUtil.string2Date(placeOnFileOrder
						.getPlaceOnFileTime()));
					placeOnFileDO.setPayTime(DateUtil.string2Date(placeOnFileOrder.getPayTime()));
				} catch (ParseException e) {
				}
			}
			
			private void saveLitigationPlaceOnFileAttachment(	long recoveryId,
																ProjectRecoveryLitigationPlaceOnFileOrder placeOnFileOrder,
																long placeOnFileId) {
				/*** 诉讼-立案-诉状-附件 **/
				setAttatment(recoveryId, placeOnFileId,
					placeOnFileOrder.getRecoveryLitigationPlaceOnFilePetitionUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_PLACE_ON_FILE_PETITION);
				/*** 诉讼-立案-证据清单及证据-附件 **/
				setAttatment(recoveryId, placeOnFileId,
					placeOnFileOrder.getRecoveryLitigationPlaceOnFileEvidenceUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_PLACE_ON_FILE_EVIDENCE);
				/*** 诉讼-立案-案件受理通知书-附件 **/
				setAttatment(recoveryId, placeOnFileId,
					placeOnFileOrder.getRecoveryLitigationPlaceOnFileAcceptNoticeUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_PLACE_ON_FILE_ACCEPT_NOTICE);
				/*** 诉讼-立案-缴费通知书-附件 **/
				setAttatment(recoveryId, placeOnFileId,
					placeOnFileOrder.getRecoveryLitigationPlaceOnFilePayNoticeUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_PLACE_ON_FILE_PAY_NOTICE);
				/*** 诉讼-立案-其他-附件 **/
				setAttatment(recoveryId, placeOnFileId,
					placeOnFileOrder.getRecoveryLitigationPlaceOnFileOtherUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_PLACE_ON_FILE_OTHER);
			}
			
			private void litigationBeforePreservationSave(final ProjectRecoveryOrder order,
															long recoveryId) {
				ProjectRecoveryLitigationBeforePreservationOrder beforePreservationOrder = order
					.getProjectRecoveryLitigationBeforePreservationOrder();
				// 用必填项 受理法院 来判定是否有保存任务
				if (beforePreservationOrder != null
					&& StringUtil.isNotBlank(beforePreservationOrder.getAcceptingCourt())) {
					long beforePreservationId = beforePreservationOrder.getId() == null ? 0
						: beforePreservationOrder.getId();
					// 执行保存任务
					if (beforePreservationId > 0) {
						// 修改
						ProjectRecoveryLitigationBeforePreservationDO beforePreservationDO = projectRecoveryLitigationBeforePreservationDAO
							.findById(beforePreservationOrder.getId());
						convertBeforePreservationOrder2DO(recoveryId, beforePreservationOrder,
							beforePreservationDO);
						
						projectRecoveryLitigationBeforePreservationDAO.update(beforePreservationDO);
					} else {
						// 插入
						ProjectRecoveryLitigationBeforePreservationDO beforePreservationDO = new ProjectRecoveryLitigationBeforePreservationDO();
						convertBeforePreservationOrder2DO(recoveryId, beforePreservationOrder,
							beforePreservationDO);
						
						beforePreservationDO.setRawAddTime(FcsPmDomainHolder.get().getSysDate());
						beforePreservationId = projectRecoveryLitigationBeforePreservationDAO
							.insert(beforePreservationDO);
					}
					// 附件
					
					saveLitigationBeforePreservationAttachment(recoveryId, beforePreservationOrder,
						beforePreservationId);
					
					// 1.1 追偿跟踪表 - 诉讼-诉前保全-保全措施 
					litigationBeforePreservationPrecautionSave(recoveryId, beforePreservationOrder,
						beforePreservationId);
				} else {
					// 清空 追偿跟踪表 - 诉讼-诉前保全 以及辖下所有表
					// 1. 追偿跟踪表 - 诉讼-诉前保全 清空
					projectRecoveryLitigationBeforePreservationDAO.deleteByRecoveryId(recoveryId);
					// 2. 追偿跟踪表 - 诉讼-诉讼保全-保全措施  清空
					projectRecoveryLitigationBeforePreservationPrecautionDAO
						.deleteByRecoveryIdAndType(recoveryId,
							ProjectRecoveryLitigationTypeEnum.LITIGATION_BEFORE.code());
				}
			}
			
			private void convertBeforePreservationOrder2DO(	long recoveryId,
															ProjectRecoveryLitigationBeforePreservationOrder beforePreservationOrder,
															ProjectRecoveryLitigationBeforePreservationDO beforePreservationDO) {
				MiscUtil.copyPoObject(beforePreservationDO, beforePreservationOrder);
				beforePreservationDO.setProjectRecoveryId(recoveryId);
				try {
					beforePreservationDO.setApplyTime(DateUtil.string2Date(beforePreservationOrder
						.getApplyTime()));
					beforePreservationDO.setPayTime(DateUtil.string2Date(beforePreservationOrder
						.getPayTime()));
					beforePreservationDO.setPreservationTime(DateUtil
						.string2Date(beforePreservationOrder.getPreservationTime()));
				} catch (ParseException e) {
				}
			}
			
			private void litigationBeforePreservationPrecautionSave(long recoveryId,
																	ProjectRecoveryLitigationBeforePreservationOrder beforePreservationOrder,
																	long beforePreservationId) {
				//				// 先删除再插入
				//				projectRecoveryLitigationBeforePreservationPrecautionDAO.deleteByRecoveryIdAndType(
				//					recoveryId, ProjectRecoveryLitigationTypeEnum.LITIGATION_BEFORE.code());
				//				if (beforePreservationOrder
				//					.getProjectRecoveryLitigationBeforePreservationPrecautionOrder() != null) {
				//					for (ProjectRecoveryLitigationBeforePreservationPrecautionOrder precautionOrder : beforePreservationOrder
				//						.getProjectRecoveryLitigationBeforePreservationPrecautionOrder()) {
				//						ProjectRecoveryLitigationBeforePreservationPrecautionDO precautionDO = new ProjectRecoveryLitigationBeforePreservationPrecautionDO();
				//						convertLitigationBeforePreservationPrecautionOrder2DO(recoveryId,
				//							beforePreservationId, precautionOrder, precautionDO);
				//						
				//						precautionDO.setRawAddTime(FcsPmDomainHolder.get().getSysDate());
				//						projectRecoveryLitigationBeforePreservationPrecautionDAO
				//							.insert(precautionDO);
				//						
				//						// 附件 【暂无】
				//					}
				//				}
				
				List<ProjectRecoveryLitigationBeforePreservationPrecautionOrder> precautionOrder2 = beforePreservationOrder
					.getProjectRecoveryLitigationBeforePreservationPrecautionOrder();
				if (precautionOrder2 != null && ListUtil.isNotEmpty(precautionOrder2)) {
					// 查出原本的数据
					List<ProjectRecoveryLitigationBeforePreservationPrecautionDO> oldPrecautionDOs = projectRecoveryLitigationBeforePreservationPrecautionDAO
						.findByRecoveryIdAndType(recoveryId,
							ProjectRecoveryLitigationTypeEnum.LITIGATION_BEFORE.code());
					// 循环保存任务
					for (ProjectRecoveryLitigationBeforePreservationPrecautionOrder precautionOrder : precautionOrder2) {
						long pledgeId = precautionOrder.getId() == null ? 0 : precautionOrder
							.getId();
						if (pledgeId > 0) {
							// 修改任务
							// 先剔除删除任务数据
							Iterator<ProjectRecoveryLitigationBeforePreservationPrecautionDO> iter = oldPrecautionDOs
								.iterator();
							while (iter.hasNext()) {
								ProjectRecoveryLitigationBeforePreservationPrecautionDO curDO = iter
									.next();
								if (curDO.getId() == precautionOrder.getId()) {
									iter.remove();
								}
							}
							// 执行修改任务
							ProjectRecoveryLitigationBeforePreservationPrecautionDO precautionDO = projectRecoveryLitigationBeforePreservationPrecautionDAO
								.findById(pledgeId);
							convertLitigationBeforePreservationPrecautionOrder2DO(recoveryId,
								beforePreservationId, precautionOrder, precautionDO);
							
							projectRecoveryLitigationBeforePreservationPrecautionDAO
								.update(precautionDO);
						} else {
							// 添加任务 
							ProjectRecoveryLitigationBeforePreservationPrecautionDO precautionDO = new ProjectRecoveryLitigationBeforePreservationPrecautionDO();
							convertLitigationBeforePreservationPrecautionOrder2DO(recoveryId,
								beforePreservationId, precautionOrder, precautionDO);
							
							precautionDO.setRawAddTime(FcsPmDomainHolder.get().getSysDate());
							
							pledgeId = projectRecoveryLitigationBeforePreservationPrecautionDAO
								.insert(precautionDO);
						}
						// 附件
						
						// 添加附件  
					}
					// 不需要的数据执行删除任务
					for (ProjectRecoveryLitigationBeforePreservationPrecautionDO precautionDO : oldPrecautionDOs) {
						projectRecoveryLitigationBeforePreservationPrecautionDAO
							.deleteById(precautionDO.getId());
					}
				} else {
					// 删除任务
					projectRecoveryLitigationBeforePreservationPrecautionDAO
						.deleteByRecoveryIdAndType(recoveryId,
							ProjectRecoveryLitigationTypeEnum.LITIGATION_BEFORE.code());
					// 同时删除所有图片数据
				}
				
			}
			
			private void convertLitigationBeforePreservationPrecautionOrder2DO(	long recoveryId,
																				long beforePreservationId,
																				ProjectRecoveryLitigationBeforePreservationPrecautionOrder precautionOrder,
																				ProjectRecoveryLitigationBeforePreservationPrecautionDO precautionDO) {
				MiscUtil.copyPoObject(precautionDO, precautionOrder);
				precautionDO
					.setProjectRecoveryLitigationType(ProjectRecoveryLitigationTypeEnum.LITIGATION_BEFORE
						.code());
				if (precautionOrder.getProjectRecoveryPreservationType() != null) {
					precautionDO.setProjectRecoveryPreservationType(precautionOrder
						.getProjectRecoveryPreservationType().code());
				}
				precautionDO.setProjectRecoveryLitigationBeforePreservationId(beforePreservationId);
				precautionDO.setProjectRecoveryId(recoveryId);
				try {
					precautionDO.setPreservationTimeStart(DateUtil.string2Date(precautionOrder
						.getPreservationTimeStart()));
					precautionDO.setPreservationTimeEnd(DateUtil.string2Date(precautionOrder
						.getPreservationTimeEnd()));
				} catch (ParseException e) {
				}
			}
			
			private void saveLitigationBeforePreservationAttachment(long recoveryId,
																	ProjectRecoveryLitigationBeforePreservationOrder beforePreservationOrder,
																	long beforePreservationId) {
				/*** 诉讼-诉前保全-保全裁定书-附件 **/
				setAttatment(
					recoveryId,
					beforePreservationId,
					beforePreservationOrder
						.getRecoveryLitigationBeforePreservationWrittenVerdictUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_BEFORE_PRESERVATION_WRITTEN_VERDICT);
				
				/*** 诉讼-诉前保全-协助执行通知书-附件 **/
				setAttatment(
					recoveryId,
					beforePreservationId,
					beforePreservationOrder
						.getRecoveryLitigationBeforePreservationExecutionNoticeUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_BEFORE_PRESERVATION_EXECUTION_NOTICE);
				/*** 诉讼-诉前保全-送达回执-附件 **/
				setAttatment(
					recoveryId,
					beforePreservationId,
					beforePreservationOrder
						.getRecoveryLitigationBeforePreservationDeliveryReceiptUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_BEFORE_PRESERVATION_DELIVERY_RECEIPT);
				/*** 诉讼-诉前保全-其他-附件 **/
				setAttatment(recoveryId, beforePreservationId,
					beforePreservationOrder.getRecoveryLitigationBeforePreservationOtherUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_LITIGATION_BEFORE_PRESERVATION_OTHER);
			}
			
			private void debtorReorganizationPledgeSave(long recoveryId,
														ProjectRecoveryDebtorReorganizationOrder reorganizationOrder,
														long reorganizationId) {
				//				projectRecoveryDebtorReorganizationPledgeDAO.deleteByRecoveryIdAndType(recoveryId,
				//					ProjectRecoveryTypeEnum.DEBTOR_REORGANIZATION.code());
				//				// 同时删除所有图片数据
				//				commonAttachmentDAO.deleteByBizNoModuleType(String.valueOf(recoveryId),
				//					ProjectRecoveryUploadTypeEnum.RECOVERY_REORGANIZATION_PLEDGE_SOLD_OUT.code());
				//				if (reorganizationOrder.getProjectRecoveryDebtorReorganizationPledgeOrder() != null) {
				//					for (ProjectRecoveryDebtorReorganizationPledgeOrder pledgeOrder : reorganizationOrder
				//						.getProjectRecoveryDebtorReorganizationPledgeOrder()) {
				//						ProjectRecoveryDebtorReorganizationPledgeDO pledgeDO = new ProjectRecoveryDebtorReorganizationPledgeDO();
				//						convertDebtorReorganizationPledge2DO(recoveryId, reorganizationId,
				//							pledgeOrder, pledgeDO);
				//						pledgeDO.setRawAddTime(FcsPmDomainHolder.get().getSysDate());
				//						
				//						long pledgeId = projectRecoveryDebtorReorganizationPledgeDAO
				//							.insert(pledgeDO);
				//						// 代表需要向业务经理发送【是否新增收款通知单】信息
				//						FcsPmDomainHolder.get().addAttribute("sendBusiManagerMessage", "send");
				//						// 添加附件  
				//						saveRecoveryDebtorReorganizationPledgeAttachment(recoveryId, pledgeId,
				//							pledgeOrder);
				//					}
				//				}
				
				List<ProjectRecoveryDebtorReorganizationPledgeOrder> pledgeOrders = reorganizationOrder
					.getProjectRecoveryDebtorReorganizationPledgeOrder();
				if (pledgeOrders != null && ListUtil.isNotEmpty(pledgeOrders)) {
					// 查出原本的数据
					List<ProjectRecoveryDebtorReorganizationPledgeDO> oldPledgeDOs = projectRecoveryDebtorReorganizationPledgeDAO
						.findByRecoveryIdAndType(recoveryId,
							ProjectRecoveryTypeEnum.DEBTOR_REORGANIZATION.code());
					// 循环保存任务
					for (ProjectRecoveryDebtorReorganizationPledgeOrder pledgeOrder : pledgeOrders) {
						long pledgeId = pledgeOrder.getId() == null ? 0 : pledgeOrder.getId();
						if (pledgeId > 0) {
							// 修改任务
							// 先剔除删除任务数据
							Iterator<ProjectRecoveryDebtorReorganizationPledgeDO> iter = oldPledgeDOs
								.iterator();
							while (iter.hasNext()) {
								ProjectRecoveryDebtorReorganizationPledgeDO curDO = iter.next();
								if (curDO.getId() == pledgeOrder.getId()) {
									iter.remove();
								}
							}
							// 执行修改任务
							ProjectRecoveryDebtorReorganizationPledgeDO pledgeDO = projectRecoveryDebtorReorganizationPledgeDAO
								.findById(pledgeId);
							convertDebtorReorganizationPledge2DO(recoveryId, reorganizationId,
								pledgeOrder, pledgeDO);
							
							projectRecoveryDebtorReorganizationPledgeDAO.update(pledgeDO);
						} else {
							// 添加任务 
							ProjectRecoveryDebtorReorganizationPledgeDO pledgeDO = new ProjectRecoveryDebtorReorganizationPledgeDO();
							convertDebtorReorganizationPledge2DO(recoveryId, reorganizationId,
								pledgeOrder, pledgeDO);
							
							pledgeDO.setRawAddTime(FcsPmDomainHolder.get().getSysDate());
							
							pledgeId = projectRecoveryDebtorReorganizationPledgeDAO
								.insert(pledgeDO);
							// 代表需要向业务经理发送【是否新增收款通知单】信息
							FcsPmDomainHolder.get().addAttribute("sendBusiManagerMessage", "send");
						}
						// 附件
						
						// 添加附件  
						saveRecoveryDebtorReorganizationPledgeAttachment(recoveryId, pledgeId,
							pledgeOrder);
					}
					// 不需要的数据执行删除任务
					for (ProjectRecoveryDebtorReorganizationPledgeDO refereeDO : oldPledgeDOs) {
						projectRecoveryDebtorReorganizationPledgeDAO.deleteById(refereeDO.getId());
					}
				} else {
					// 删除任务
					projectRecoveryDebtorReorganizationPledgeDAO.deleteByRecoveryIdAndType(
						recoveryId, ProjectRecoveryTypeEnum.DEBTOR_REORGANIZATION.code());
					// 同时删除所有图片数据
					commonAttachmentDAO.deleteByBizNoModuleType(String.valueOf(recoveryId),
						ProjectRecoveryUploadTypeEnum.RECOVERY_REORGANIZATION_PLEDGE_SOLD_OUT
							.code());
				}
				
			}
			
			private void convertDebtorReorganizationPledge2DO(	long recoveryId,
																long reorganizationId,
																ProjectRecoveryDebtorReorganizationPledgeOrder pledgeOrder,
																ProjectRecoveryDebtorReorganizationPledgeDO pledgeDO) {
				MiscUtil.copyPoObject(pledgeDO, pledgeOrder);
				pledgeDO.setProjectRecoveryType(ProjectRecoveryTypeEnum.DEBTOR_REORGANIZATION
					.code());
				pledgeDO.setProjectRecoveryDebtorReorganizationId(reorganizationId);
				pledgeDO.setProjectRecoveryId(recoveryId);
				if (pledgeOrder.getPledgeAssetManagementMode() != null) {
					pledgeDO.setPledgeAssetManagementMode(pledgeOrder
						.getPledgeAssetManagementMode().code());
				}
				
				if (pledgeOrder.getPledgeAmount() != null) {
					pledgeDO.setPledgeAmount(new Money(pledgeOrder.getPledgeAmount()));
				}
				
			}
			
			private void convertExecuteReorganizationPledge2DO(	long recoveryId,
																long executeId,
																ProjectRecoveryDebtorReorganizationPledgeOrder pledgeOrder,
																ProjectRecoveryDebtorReorganizationPledgeDO pledgeDO) {
				MiscUtil.copyPoObject(pledgeDO, pledgeOrder);
				pledgeDO.setProjectRecoveryType(ProjectRecoveryTypeEnum.LITIGATION_EXECUTE.code());
				pledgeDO.setProjectRecoveryLitigationExecuteId(executeId);
				pledgeDO.setProjectRecoveryId(recoveryId);
				if (pledgeOrder.getPledgeAssetManagementMode() != null) {
					pledgeDO.setPledgeAssetManagementMode(pledgeOrder
						.getPledgeAssetManagementMode().code());
				}
				if (pledgeOrder.getPledgeAmount() != null) {
					pledgeDO.setPledgeAmount(new Money(pledgeOrder.getPledgeAmount()));
				}
				
			}
			
			private void saveRecoveryDebtorReorganizationPledgeAttachment(	long recoveryId,
																			long pledgeId,
																			ProjectRecoveryDebtorReorganizationPledgeOrder pledgeOrder) {
				/*** 债务人重整或破产清算-抵质押资产抵债-默认附件-附件 **/
				setAttatment(recoveryId, pledgeId,
					pledgeOrder.getRecoveryReorganizationPledgeSoldOutUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_REORGANIZATION_PLEDGE_SOLD_OUT);
			}
			
			private void debtorReorganizationAmountDetailSave(	long recoveryId,
																ProjectRecoveryDebtorReorganizationOrder reorganizationOrder,
																long reorganizationId) {
				// 先删除再插入
				projectRecoveryDebtorReorganizationAmountDetailDAO.deleteByRecoveryIdAndType(
					recoveryId, ProjectRecoveryTypeEnum.DEBTOR_REORGANIZATION.code());
				if (reorganizationOrder.getProjectRecoveryDebtorReorganizationAmountDetailOrder() != null) {
					for (ProjectRecoveryDebtorReorganizationAmountDetailOrder detailOrder : reorganizationOrder
						.getProjectRecoveryDebtorReorganizationAmountDetailOrder()) {
						//						long detailId = detailOrder.getId() == null ? 0 : detailOrder.getId();
						//						if (detailId > 0) {
						ProjectRecoveryDebtorReorganizationAmountDetailDO detailDO = new ProjectRecoveryDebtorReorganizationAmountDetailDO();
						MiscUtil.copyPoObject(detailDO, detailOrder);
						detailDO
							.setProjectRecoveryType(ProjectRecoveryTypeEnum.DEBTOR_REORGANIZATION
								.code());
						detailDO.setProjectRecoveryDebtorReorganizationId(reorganizationId);
						detailDO.setProjectRecoveryId(recoveryId);
						
						try {
							detailDO.setRecoveryTime(DateUtil.string2Date(detailOrder
								.getRecoveryTime()));
						} catch (ParseException e) {
						}
						if (detailOrder.getRecoveryAmount() != null) {
							detailDO.setRecoveryAmount(new Money(detailOrder.getRecoveryAmount()));
						}
						
						detailDO.setRawAddTime(FcsPmDomainHolder.get().getSysDate());
						projectRecoveryDebtorReorganizationAmountDetailDAO.insert(detailDO);
						
						// 附件 【暂无】
						//						}
					}
				}
			}
			
			private void debtorReorganizationDebtsCouncilSave(	long recoveryId,
																ProjectRecoveryDebtorReorganizationOrder reorganizationOrder,
																long reorganizationId) {
				// 使用简单式操作：所有此recoveryid数据删除，重新插入
				projectRecoveryDebtorReorganizationDebtsCouncilDAO.deleteByRecoveryId(recoveryId);
				if (reorganizationOrder.getProjectRecoveryDebtorReorganizationDebtsCouncilOrder() != null) {
					// 循环插入
					for (ProjectRecoveryDebtorReorganizationDebtsCouncilOrder councilOrder : reorganizationOrder
						.getProjectRecoveryDebtorReorganizationDebtsCouncilOrder()) {
						//						long councilId = councilOrder.getId() == null ? 0 : councilOrder.getId();
						//						if (councilId > 0) {
						ProjectRecoveryDebtorReorganizationDebtsCouncilDO debtorReorganizationDebtsCouncilDO = new ProjectRecoveryDebtorReorganizationDebtsCouncilDO();
						MiscUtil.copyPoObject(debtorReorganizationDebtsCouncilDO, councilOrder);
						debtorReorganizationDebtsCouncilDO
							.setProjectRecoveryDebtorReorganizationId(reorganizationId);
						debtorReorganizationDebtsCouncilDO.setProjectRecoveryId(recoveryId);
						try {
							debtorReorganizationDebtsCouncilDO.setDebtsCouncilTime(DateUtil
								.string2Date(councilOrder.getDebtsCouncilTime()));
						} catch (ParseException e) {
						}
						
						debtorReorganizationDebtsCouncilDO.setRawAddTime(FcsPmDomainHolder.get()
							.getSysDate());
						projectRecoveryDebtorReorganizationDebtsCouncilDAO
							.insert(debtorReorganizationDebtsCouncilDO);
						
						// 添加附件  【暂无】
						//						}
					}
				}
				
			}
			
			private void debtorReorganizationSave(	long recoveryId,
													ProjectRecoveryDebtorReorganizationOrder reorganizationOrder,
													long reorganizationId) {
				if (reorganizationId <= 0) {
					// 代表新增
					ProjectRecoveryDebtorReorganizationDO projectRecoveryDebtorReorganizationDO = new ProjectRecoveryDebtorReorganizationDO();
					convertReorganizationOrder2DO(recoveryId, reorganizationOrder,
						projectRecoveryDebtorReorganizationDO);
					
					projectRecoveryDebtorReorganizationDO.setRawAddTime(FcsPmDomainHolder.get()
						.getSysDate());
					reorganizationId = projectRecoveryDebtorReorganizationDAO
						.insert(projectRecoveryDebtorReorganizationDO);
					// 若插入失败
					if (reorganizationId <= 0) {
						throw ExceptionFactory.newFcsException(FcsResultEnum.HAVE_NOT_DATA,
							"未获取到债务人重整或破产清算表id！");
					}
				} else {
					// 代表修改
					ProjectRecoveryDebtorReorganizationDO oldReorganizationDO = projectRecoveryDebtorReorganizationDAO
						.findById(reorganizationId);
					convertReorganizationOrder2DO(recoveryId, reorganizationOrder,
						oldReorganizationDO);
					projectRecoveryDebtorReorganizationDAO.update(oldReorganizationDO);
				}
				
				// 插入债务人重整或破产清算表附件
				// 附件开始 
				saveRecoveryReorganizationAttachment(recoveryId, reorganizationOrder,
					reorganizationId);
				
				// 添加子表信息
				// 1.1  追偿跟踪表 - 债务人重整或破产清算表-债权人会议表
				
				debtorReorganizationDebtsCouncilSave(recoveryId, reorganizationOrder,
					reorganizationId);
				
				// 1.2  追偿跟踪表 - 债务人重整或破产清算表-回收金额明细
				
				debtorReorganizationAmountDetailSave(recoveryId, reorganizationOrder,
					reorganizationId);
				
				// 1.3  追偿跟踪表 - 债务人重整或破产清算表-抵质押资产抵债明细 
				
				// 先删除再插入
				debtorReorganizationPledgeSave(recoveryId, reorganizationOrder, reorganizationId);
			}
			
			private void convertReorganizationOrder2DO(	long recoveryId,
														ProjectRecoveryDebtorReorganizationOrder reorganizationOrder,
														ProjectRecoveryDebtorReorganizationDO projectRecoveryDebtorReorganizationDO) {
				MiscUtil.copyPoObject(projectRecoveryDebtorReorganizationDO, reorganizationOrder);
				projectRecoveryDebtorReorganizationDO.setProjectRecoveryId(recoveryId);
				
				try {
					projectRecoveryDebtorReorganizationDO.setDebtsDeclareEndTime(DateUtil
						.string2Date(reorganizationOrder.getDebtsDeclareEndTime()));
					projectRecoveryDebtorReorganizationDO.setDivisionWeDeclareTime(DateUtil
						.string2Date(reorganizationOrder.getDivisionWeDeclareTime()));
					if (reorganizationOrder.getRecoveryTotalAmount() != null) {
						projectRecoveryDebtorReorganizationDO.setRecoveryTotalAmount(new Money(
							reorganizationOrder.getRecoveryTotalAmount()));
					}
				} catch (ParseException e) {
				}
			}
			
			/**
			 * 债务人重整或破产清算 附件插入
			 * @param recoveryId
			 * @param reorganizationOrder
			 * @param reorganizationId
			 */
			private void saveRecoveryReorganizationAttachment(	long recoveryId,
																ProjectRecoveryDebtorReorganizationOrder reorganizationOrder,
																long reorganizationId) {
				/*** 债务人重整或破产清算-我司申报时间-附件 **/
				setAttatment(recoveryId, reorganizationId,
					reorganizationOrder.getRecoveryReorganizationDivisionWeDeclareTimeUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_REORGANIZATION_DIVISION_WE_DECLARE_TIME);
				
				/*** 债务人重整或破产清算-债权确认-附件 **/
				setAttatment(recoveryId, reorganizationId,
					reorganizationOrder.getRecoveryReorganizationDebtsConfirmUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_REORGANIZATION_DEBTS_CONFIRM);
				/*** 债务人重整或破产清算-会议情况-附件 **/
				setAttatment(recoveryId, reorganizationId,
					reorganizationOrder.getRecoveryReorganizationCouncilCircumstancesUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_REORGANIZATION_COUNCIL_CIRCUMSTANCES);
				/*** 债务人重整或破产清算-我司意见-附件 **/
				setAttatment(recoveryId, reorganizationId,
					reorganizationOrder.getRecoveryReorganizationWeSuggestionUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_REORGANIZATION_WE_SUGGESTION);
				/*** 债务人重整或破产清算-批准的重整方案及执行情况-附件 **/
				setAttatment(recoveryId, reorganizationId,
					reorganizationOrder.getRecoveryReorganizationReExecutionPlanUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_REORGANIZATION_RE_EXECUTION_PLAN);
				/*** 债务人重整或破产清算-和解方案-附件 **/
				setAttatment(recoveryId, reorganizationId,
					reorganizationOrder.getRecoveryReorganizationSettlementSchemeContentUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_REORGANIZATION_SETTLEMENT_SCHEME_CONTENT);
				/*** 债务人重整或破产清算-清算方案-附件 **/
				setAttatment(recoveryId, reorganizationId,
					reorganizationOrder.getRecoveryReorganizationLiquidationSchemeUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_REORGANIZATION_LIQUIDATION_SCHEME);
				/*** 债务人重整或破产清算-清偿情况-附件 **/
				setAttatment(recoveryId, reorganizationId,
					reorganizationOrder.getRecoveryReorganizationLiquidationSituationUrl(),
					ProjectRecoveryUploadTypeEnum.RECOVERY_REORGANIZATION_LIQUIDATION_SITUATION);
			}
		}, null, new AfterProcessInvokeService() {
			
			@Override
			public Domain after(Domain domain) {
				//  当法务经理填写此表后，向该项目的风险处置小组成员发送系统消息，
				// 消息内容：项目编号XXX，客户名称XXX，该项目追偿跟踪表由法务经理XXX更新，点击查看详情；
				String projectCode = order.getProjectCode();
				ProjectRiskHandleTeamDO teamDO = projectRiskHandleTeamDAO
					.findByProjectCode(projectCode);
				List<Long> userIds = new ArrayList<Long>();
				userIds.add(teamDO.getChiefLeaderId());
				userIds.add(teamDO.getViceLeaderId());
				if (StringUtil.isNotBlank(teamDO.getMemberIds())) {
					for (String str : teamDO.getMemberIds().split(",")) {
						userIds.add(new Long(str));
					}
				}
				//				List<SysUser> users = new ArrayList<SysUser>();
				//				for (SysUser user : users) {
				//					saveRecoveryNotice(order, user);
				//				}
				for (Long userId : userIds) {
					saveRecoveryNotice(order, userId);
				}
				
				// 新增任务2.向业务经理发送【是否新增收款通知单】信息
				
				String sendBusiManagerMessage = (String) FcsPmDomainHolder.get().getAttribute(
					"sendBusiManagerMessage");
				if (StringUtil.isNotBlank(sendBusiManagerMessage)
					&& "send".equals(sendBusiManagerMessage)) {
					ProjectDO projectDO = projectDAO.findByProjectCode(projectCode);
					sendBusiManagerMessage(order, projectDO.getBusiManagerId());
				}
				return null;
			}
			
			private void sendBusiManagerMessage(ProjectRecoveryOrder order, Long userId) {
				//表单站内地址
				ProjectInfo project = projectService.queryByCode(order.getProjectCode(), false);
				String messageUrl = "/projectMg/index.htm?systemNameDefautUrl=/projectMg/chargeNotification/addChargeNotification.htm?projectCode="
									+ order.getProjectCode() + "&recovery=IS";
				String forMessage = "<a href='" + messageUrl + "'>新增收款通知单</a>";
				String subject = "追偿时新增收款通知单提醒";
				List<SimpleUserInfo> notifyUserList = Lists.newArrayList();
				SysUser sendUser = null;
				sendUser = bpmUserQueryService.findUserByUserId(userId);
				if (sendUser != null) {
					SimpleUserInfo userInfo = new SimpleUserInfo();
					userInfo.setUserId(sendUser.getUserId());
					userInfo.setUserName(sendUser.getFullname());
					userInfo.setUserAccount(sendUser.getAccount());
					userInfo.setEmail(sendUser.getEmail());
					userInfo.setMobile(sendUser.getMobile());
					notifyUserList.add(userInfo);
				}
				if (ListUtil.isNotEmpty(notifyUserList)) {
					
					SimpleUserInfo[] sendUsers = notifyUserList
						.toArray(new SimpleUserInfo[notifyUserList.size()]);
					//替换审核地址
					// 项目编号XXX，客户名称XXX，该项目追偿跟踪表由法务经理XXX更新，点击查看详情
					StringBuilder sb = new StringBuilder();
					sb.append("项目编号 ");
					sb.append(order.getProjectCode());
					sb.append("，客户名称 ");
					sb.append(project.getCustomerName());
					sb.append(" ，该项目追偿跟踪表由法务经理");
					sb.append(order.getUserName());
					sb.append(" 更新，点击新增收款通知单！");
					String messageContent = sb.toString();
					messageContent += forMessage;
					if (StringUtil.isNotBlank(messageContent)) {
						MessageOrder ormessageOrderder = MessageOrder.newSystemMessageOrder();
						ormessageOrderder.setMessageSubject(subject);
						ormessageOrderder.setMessageTitle("新增收款通知单提醒");
						ormessageOrderder.setMessageContent(messageContent);
						ormessageOrderder.setSendUsers(sendUsers);
						siteMessageService.addMessageInfo(ormessageOrderder);
					}
					
					for (SimpleUserInfo userInfo : notifyUserList) {
						
						//发送邮件
						
						String accessToken = getAccessToken(userInfo);
						
						//站外审核地址
						String outSiteUrl = "<a href=\"" + getFaceWebUrl() + messageUrl
											+ "&accessToken=" + accessToken
											+ "\" target=\"_blank\">点击处理</a>";
						
						String mailContent = sb.toString();
						mailContent += outSiteUrl;
						if (StringUtil.isNotBlank(mailContent)) {
							//邮件地址
							List<String> mailAddress = Lists.newArrayList(userInfo.getEmail());
							SendMailOrder mailOrder = new SendMailOrder();
							mailOrder.setSendTo(mailAddress);
							mailOrder.setSubject(subject);
							mailOrder.setContent(mailContent);
							mailService.sendHtmlEmail(mailOrder);
						}
						if (StringUtil.isNotEmpty(userInfo.getMobile())) {
							
							String contentTxt = sb.toString();
							//发送短信
							sMSService.sendSMS(userInfo.getMobile(), contentTxt, null);
						}
					}
					
				}
			}
			
			private void saveRecoveryNotice(ProjectRecoveryOrder order, Long userId) {
				//表单站内地址
				ProjectInfo project = projectService.queryByCode(order.getProjectCode(), false);
				String messageUrl = "/projectMg/index.htm?systemNameDefautUrl=/projectMg/recovery/projectRecoveryMessage.htm?recoveryId="
									+ order.getId();
				String forMessage = "<a href='" + messageUrl + "'>查看详情</a>";
				String subject = "追偿更新提醒";
				List<SimpleUserInfo> notifyUserList = Lists.newArrayList();
				SysUser sendUser = null;
				sendUser = bpmUserQueryService.findUserByUserId(userId);
				if (sendUser != null) {
					SimpleUserInfo userInfo = new SimpleUserInfo();
					userInfo.setUserId(sendUser.getUserId());
					userInfo.setUserName(sendUser.getFullname());
					userInfo.setUserAccount(sendUser.getAccount());
					userInfo.setEmail(sendUser.getEmail());
					userInfo.setMobile(sendUser.getMobile());
					notifyUserList.add(userInfo);
				}
				if (ListUtil.isNotEmpty(notifyUserList)) {
					
					SimpleUserInfo[] sendUsers = notifyUserList
						.toArray(new SimpleUserInfo[notifyUserList.size()]);
					//替换审核地址
					// 项目编号XXX，客户名称XXX，该项目追偿跟踪表由法务经理XXX更新，点击查看详情
					StringBuilder sb = new StringBuilder();
					sb.append("项目编号 ");
					sb.append(order.getProjectCode());
					sb.append("，客户名称 ");
					sb.append(project.getCustomerName());
					sb.append(" ，该项目追偿跟踪表由法务经理");
					sb.append(order.getUserName());
					sb.append(" 更新，点击查看详情");
					String messageContent = sb.toString();
					messageContent += forMessage;
					if (StringUtil.isNotBlank(messageContent)) {
						MessageOrder ormessageOrderder = MessageOrder.newSystemMessageOrder();
						ormessageOrderder.setMessageSubject(subject);
						ormessageOrderder.setMessageTitle("追偿更新提醒");
						ormessageOrderder.setMessageContent(messageContent);
						ormessageOrderder.setSendUsers(sendUsers);
						siteMessageService.addMessageInfo(ormessageOrderder);
					}
					
					for (SimpleUserInfo userInfo : notifyUserList) {
						
						//发送邮件
						
						String accessToken = getAccessToken(userInfo);
						
						//站外审核地址
						String outSiteUrl = "<a href=\"" + getFaceWebUrl() + messageUrl
											+ "&accessToken=" + accessToken
											+ "\" target=\"_blank\">点击处理</a>";
						
						String mailContent = sb.toString();
						mailContent += outSiteUrl;
						if (StringUtil.isNotBlank(mailContent)) {
							//邮件地址
							List<String> mailAddress = Lists.newArrayList(userInfo.getEmail());
							SendMailOrder mailOrder = new SendMailOrder();
							mailOrder.setSendTo(mailAddress);
							mailOrder.setSubject(subject);
							mailOrder.setContent(mailContent);
							mailService.sendHtmlEmail(mailOrder);
						}
						if (StringUtil.isNotEmpty(userInfo.getMobile())) {
							
							String contentTxt = sb.toString();
							//发送短信
							sMSService.sendSMS(userInfo.getMobile(), contentTxt, null);
						}
					}
					
				}
			}
			
		});
	}
	
	private void setAttatment(Long id, Long childId, String pathValues,
								ProjectRecoveryUploadTypeEnum type) {
		//  删除的时候判断child，用于兼容列表插入的时候，以免后入的把前面插入的删除了
		// 先删除原本的
		if (childId > 0) {
			commonAttachmentDAO.deleteByBizNoAndChildIdModuleType(String.valueOf(id), type.code(),
				String.valueOf(childId));
		} else {
			commonAttachmentDAO.deleteByBizNoModuleType(String.valueOf(id), type.code());
		}
		if (StringUtil.isNotBlank(pathValues)) {
			Date now = FcsPmDomainHolder.get().getSysDate();
			String[] attachPaths = pathValues.split(";");
			int j = 1;
			for (String path : attachPaths) {
				String paths[] = path.split(",");
				if (null != paths && paths.length >= 3) {
					CommonAttachmentDO attachmentDO = new CommonAttachmentDO();
					attachmentDO.setBizNo(String.valueOf(id));
					//  插入子id，用于判断是哪一张同类型表的数据
					attachmentDO.setChildId(String.valueOf(childId));
					attachmentDO.setModuleType(type.code());
					attachmentDO.setIsort(j++);
					attachmentDO.setFileName(paths[0]);
					attachmentDO.setFilePhysicalPath(paths[1]);
					attachmentDO.setRequestPath(paths[2]);
					attachmentDO.setRawAddTime(now);
					
					// 设置用户id和名
					attachmentDO.setUploaderId(userId);
					attachmentDO.setUploaderAccount(userAccount);
					attachmentDO.setUploaderName(userName);
					commonAttachmentDAO.insert(attachmentDO);
				}
			}
		}
	}
	
	/**
	 * @param recoveryId
	 * @return
	 * @see com.born.fcs.pm.ws.service.recovery.ProjectRecoveryService#findById(java.lang.Long)
	 */
	@Override
	public ProjectRecoveryResult findById(Long recoveryId) {
		ProjectRecoveryResult result = new ProjectRecoveryResult();
		logger.info("进入ProjectRecoveryServiceImpl的findById方法，入参：recoveryId=" + recoveryId);
		if (recoveryId <= 0) {
			result.setFcsResultEnum(FcsResultEnum.INCOMPLETE_REQ_PARAM);
			result.setMessage("入参不能小于0！");
			logger.error("入参不能小于0！");
			return result;
		}
		try {
			// 先抓取主表 
			ProjectRecoveryDO recoveryDO = projectRecoveryDAO.findById(recoveryId);
			ProjectRecoveryInfo recoveryInfo = new ProjectRecoveryInfo();
			MiscUtil.copyPoObject(recoveryInfo, recoveryDO);
			recoveryInfo.setRecoveryStatus(ProjectRecoveryStatusEnum.getByCode(recoveryDO
				.getRecoveryStatus()));
			// 再抓取图片信息
			// 将图片信息设置为 map<string[枚举code],list<info>>的格式 
			Map<String, List<CommonAttachmentInfo>> maps = getAttachMap(recoveryId);
			
			// 填充主表
			recoveryInfo.setDebtorReorganizationOn(BooleanEnum.getByCode(recoveryDO
				.getDebtorReorganizationOn()));
			if (BooleanEnum.YES == recoveryInfo.getDebtorReorganizationOn()) {
				/** 第一大类 债务人重整或破产清算 */
				// 1.债务人重整或破产清算表 主表
				ProjectRecoveryDebtorReorganizationDO reorganizationDO = projectRecoveryDebtorReorganizationDAO
					.findByRecoveryId(recoveryId);
				ProjectRecoveryDebtorReorganizationInfo reorganizationInfo = new ProjectRecoveryDebtorReorganizationInfo();
				MiscUtil.copyPoObject(reorganizationInfo, reorganizationDO);
				// 附件
				
				setDebtorReorganizationUrl(maps, reorganizationInfo, reorganizationDO.getId());
				
				// 添加子表
				// 1.1 追偿跟踪表 - 债务人重整或破产清算表-债权人会议表
				getDebtorReorganizationDebtsCouncil(recoveryId, reorganizationInfo);
				// 1.2  追偿跟踪表 - 债务人重整或破产清算表-回收金额明细
				getDebtorReorganizationAmountDetail(recoveryId, reorganizationInfo);
				// 1.3  追偿跟踪表 - 债务人重整或破产清算表-抵质押资产抵债明细 
				getDebtorReorganizationPledge(recoveryId, maps, reorganizationInfo);
				recoveryInfo.setProjectRecoveryDebtorReorganizationInfo(reorganizationInfo);
			}
			recoveryInfo.setLitigationOn(BooleanEnum.getByCode(recoveryDO.getLitigationOn()));
			if (BooleanEnum.YES == recoveryInfo.getLitigationOn()) {
				
				/** 第二大类 诉讼 */
				// 1、 追偿跟踪表 - 诉讼-诉前保全
				getLitigationBeforePreservation(recoveryId, maps, recoveryInfo);
				// 2. 追偿跟踪表 - 诉讼-立案
				getLitigationPlaceOnFile(recoveryId, maps, recoveryInfo);
				// 3 .追偿跟踪表 - 诉讼-诉讼保全
				getLitigationPreservation(recoveryId, maps, recoveryInfo);
				// 4. 追偿跟踪表 - 诉讼-庭前准备 
				getLitigationBeforeTrail(recoveryId, maps, recoveryInfo);
				// 5. 追偿跟踪表 - 诉讼-开庭
				getLitigationOpening(recoveryId, maps, recoveryInfo);
				// 6. 追偿跟踪表 - 诉讼-裁判 
				getLitigationReferee(recoveryId, maps, recoveryInfo);
				// 7. 追偿跟踪表 - 诉讼-二审上述
				getLitigationSecondAppeal(recoveryId, maps, recoveryInfo);
				// 8 . 追偿跟踪表 - 诉讼-实现担保物权特别程序 
				getLitigationSpecialProcedure(recoveryId, maps, recoveryInfo);
				// 9. 追偿跟踪表 - 诉讼-强制执行公证执行证书
				getLitigationCertificate(recoveryId, maps, recoveryInfo);
				// 10 . 追偿跟踪表 - 诉讼-执行   
				getLitigationExecute(recoveryId, maps, recoveryInfo);
				// 11 . 追偿跟踪表 - 诉讼-再审程序
				getLitigationAdjournedProcedure(recoveryId, maps, recoveryInfo);
				
				// 12   再审程序二审
				getLitigationAdjournedSecond(recoveryId, maps, recoveryInfo);
				// 13 . 追偿跟踪表 - 诉讼-执行回转
				getLitigationExecuteGyration(recoveryId, maps, recoveryInfo);
			}
			// 设入result
			result.setProjectRecoveryInfo(recoveryInfo);
			result.setSuccess(true);
		} catch (DataAccessException e) {
			logger.error("查询追偿信息失败" + e.getMessage(), e);
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
			
		} catch (Exception e) {
			logger.error("查询追偿信息失败" + e.getMessage(), e);
			result.setSuccess(false);
			
		}
		return result;
	}
	
	@Override
	public ProjectRecoveryResult findByProjectCode(String projectCode) {
		ProjectRecoveryResult result = new ProjectRecoveryResult();
		try {
			if (StringUtil.isBlank(projectCode)) {
				result.setFcsResultEnum(FcsResultEnum.INCOMPLETE_REQ_PARAM);
				result.setMessage("请求参数不能为空！");
				return result;
			}
			ProjectRecoveryDO recoveryDO = projectRecoveryDAO.findByProjectCode(projectCode);
			if (recoveryDO == null) {
				result.setFcsResultEnum(FcsResultEnum.HAVE_NOT_DATA);
				result.setMessage("未查询到该项目的数据！");
				return result;
			}
			Long recoveryId = recoveryDO.getId();
			result = findById(recoveryId);
		} catch (Exception e) {
			logger.error("查询追偿信息失败" + e.getMessage(), e);
			result.setSuccess(false);
		}
		return result;
	}
	
	private void getLitigationAdjournedSecond(Long recoveryId,
												Map<String, List<CommonAttachmentInfo>> maps,
												ProjectRecoveryInfo recoveryInfo) {
		ProjectRecoveryLitigationAdjournedSecondDO secondDO = projectRecoveryLitigationAdjournedSecondDAO
			.findByRecoveryId(recoveryId);
		ProjectRecoveryLitigationAdjournedSecondInfo secondInfo = new ProjectRecoveryLitigationAdjournedSecondInfo();
		MiscUtil.copyPoObject(secondInfo, secondDO);
		// 附件
		
		getLitigationAdjournedSecondUrl(maps, secondInfo);
		
		recoveryInfo.setProjectRecoveryLitigationAdjournedSecondInfo(secondInfo);
	}
	
	private void getLitigationAdjournedSecondUrl(	Map<String, List<CommonAttachmentInfo>> maps,
													ProjectRecoveryLitigationAdjournedSecondInfo secondInfo) {
		/*** 诉讼-再审程序 二审-上诉请求-附件 **/
		secondInfo.setRecoveryLitigationAdjournedSecondAppealDemand(getByTypeCode(maps,
			"RECOVERY_LITIGATION_ADJOURNED_SECOND_APPEAL_DEMAND", secondInfo.getId()));
		secondInfo.setRecoveryLitigationAdjournedSecondAppealDemandUrl(attachsToUrl(secondInfo
			.getRecoveryLitigationAdjournedSecondAppealDemand()));
		/*** 诉讼-再审程序 二审-公告时间-附件 **/
		secondInfo.setRecoveryLitigationAdjournedSecondNoticeTime(getByTypeCode(maps,
			"RECOVERY_LITIGATION_ADJOURNED_SECOND_NOTICE_TIME", secondInfo.getId()));
		secondInfo.setRecoveryLitigationAdjournedSecondNoticeTimeUrl(attachsToUrl(secondInfo
			.getRecoveryLitigationAdjournedSecondNoticeTime()));
		/*** 诉讼-再审程序 二审-开庭时间-附件 **/
		secondInfo.setRecoveryLitigationAdjournedSecondOpeningTime(getByTypeCode(maps,
			"RECOVERY_LITIGATION_ADJOURNED_SECOND_OPENING_TIME", secondInfo.getId()));
		secondInfo.setRecoveryLitigationAdjournedSecondOpeningTimeUrl(attachsToUrl(secondInfo
			.getRecoveryLitigationAdjournedSecondOpeningTime()));
		/*** 诉讼-再审程序 二审-新证据-附件 **/
		secondInfo.setRecoveryLitigationAdjournedSecondNewEvidence(getByTypeCode(maps,
			"RECOVERY_LITIGATION_ADJOURNED_SECOND_NEW_EVIDENCE", secondInfo.getId()));
		secondInfo.setRecoveryLitigationAdjournedSecondNewEvidenceUrl(attachsToUrl(secondInfo
			.getRecoveryLitigationAdjournedSecondNewEvidence()));
		/*** 诉讼-再审程序 二审-争议焦点-附件 **/
		secondInfo.setRecoveryLitigationAdjournedSecondControversyFocus(getByTypeCode(maps,
			"RECOVERY_LITIGATION_ADJOURNED_SECOND_CONTROVERSY_FOCUS", secondInfo.getId()));
		secondInfo.setRecoveryLitigationAdjournedSecondControversyFocusUrl(attachsToUrl(secondInfo
			.getRecoveryLitigationAdjournedSecondControversyFocus()));
		/*** 诉讼-再审程序 二审-备注-附件 **/
		secondInfo.setRecoveryLitigationAdjournedSecondMemo(getByTypeCode(maps,
			"RECOVERY_LITIGATION_ADJOURNED_SECOND_MEMO", secondInfo.getId()));
		secondInfo.setRecoveryLitigationAdjournedSecondMemoUrl(attachsToUrl(secondInfo
			.getRecoveryLitigationAdjournedSecondMemo()));
	}
	
	private void getLitigationExecuteGyration(Long recoveryId,
												Map<String, List<CommonAttachmentInfo>> maps,
												ProjectRecoveryInfo recoveryInfo) {
		ProjectRecoveryLitigationExecuteGyrationDO gyrationDO = projectRecoveryLitigationExecuteGyrationDAO
			.findByRecoveryId(recoveryId);
		ProjectRecoveryLitigationExecuteGyrationInfo info = new ProjectRecoveryLitigationExecuteGyrationInfo();
		MiscUtil.copyPoObject(info, gyrationDO);
		// 附件 
		setLitigationExecuteGyrationUrl(maps, info);
		recoveryInfo.setProjectRecoveryLitigationExecuteGyrationInfo(info);
	}
	
	private void setLitigationExecuteGyrationUrl(	Map<String, List<CommonAttachmentInfo>> maps,
													ProjectRecoveryLitigationExecuteGyrationInfo info) {
		/*** 诉讼-执行回转-默认附件-附件 **/
		info.setRecoveryLitigationExecuteGyrationMemo(getByTypeCode(maps,
			"RECOVERY_LITIGATION_EXECUTE_GYRATION_MEMO", info.getId()));
		info.setRecoveryLitigationExecuteGyrationMemoUrl(attachsToUrl(info
			.getRecoveryLitigationExecuteGyrationMemo()));
	}
	
	private void getLitigationAdjournedProcedure(Long recoveryId,
													Map<String, List<CommonAttachmentInfo>> maps,
													ProjectRecoveryInfo recoveryInfo) {
		List<ProjectRecoveryLitigationAdjournedProcedureDO> procedureDOs = projectRecoveryLitigationAdjournedProcedureDAO
			.findByRecoveryId(recoveryId);
		List<ProjectRecoveryLitigationAdjournedProcedureInfo> infos = new ArrayList<ProjectRecoveryLitigationAdjournedProcedureInfo>();
		for (ProjectRecoveryLitigationAdjournedProcedureDO procedureDO : procedureDOs) {
			ProjectRecoveryLitigationAdjournedProcedureInfo info = new ProjectRecoveryLitigationAdjournedProcedureInfo();
			MiscUtil.copyPoObject(info, procedureDO);
			info.setProcedureType(ProjectRecoveryProcedureTypeEnum.getByCode(procedureDO
				.getProcedureType()));
			// 附件
			setLitigationAdjournedProcedureUrl(maps, info);
			infos.add(info);
		}
		recoveryInfo.setProjectRecoveryLitigationAdjournedProcedureInfos(infos);
	}
	
	private void setLitigationAdjournedProcedureUrl(Map<String, List<CommonAttachmentInfo>> maps,
													ProjectRecoveryLitigationAdjournedProcedureInfo info) {
		/*** 诉讼-再审程序-我方主要诉讼请求或答辩意见-附件 **/
		info.setRecoveryLitigationAdjournedProcedureWeLitigationDemand(getByTypeCode(maps,
			"RECOVERY_LITIGATION_ADJOURNED_PROCEDURE_WE_LITIGATION_DEMAND", info.getId()));
		info.setRecoveryLitigationAdjournedProcedureWeLitigationDemandUrl(attachsToUrl(info
			.getRecoveryLitigationAdjournedProcedureWeLitigationDemand()));
		/*** 诉讼-再审程序-补充证据-附件 **/
		info.setRecoveryLitigationAdjournedProcedureAdditionalEvidence(getByTypeCode(maps,
			"RECOVERY_LITIGATION_ADJOURNED_PROCEDURE_ADDITIONAL_EVIDENCE", info.getId()));
		info.setRecoveryLitigationAdjournedProcedureAdditionalEvidenceUrl(attachsToUrl(info
			.getRecoveryLitigationAdjournedProcedureAdditionalEvidence()));
		/*** 诉讼-再审程序-备注-附件 **/
		info.setRecoveryLitigationAdjournedProcedureMemo(getByTypeCode(maps,
			"RECOVERY_LITIGATION_ADJOURNED_PROCEDURE_MEMO", info.getId()));
		info.setRecoveryLitigationAdjournedProcedureMemoUrl(attachsToUrl(info
			.getRecoveryLitigationAdjournedProcedureMemo()));
	}
	
	private void getLitigationExecute(Long recoveryId,
										Map<String, List<CommonAttachmentInfo>> maps,
										ProjectRecoveryInfo recoveryInfo) {
		ProjectRecoveryLitigationExecuteDO executeDO = projectRecoveryLitigationExecuteDAO
			.findByRecoveryId(recoveryId);
		ProjectRecoveryLitigationExecuteInfo executeInfo = new ProjectRecoveryLitigationExecuteInfo();
		MiscUtil.copyPoObject(executeInfo, executeDO);
		// 附件
		getLitigationExecuteUrl(maps, executeInfo);
		
		// 10.1  追偿跟踪表 - 诉讼-执行-执行内容 
		getLitigationExecuteStuff(recoveryId, maps, executeInfo);
		// 10.2 追偿跟踪表 - 诉讼-执行-回收金额明细
		getLitigationAmountDetail(recoveryId, executeInfo);
		// 10.3 追偿跟踪表 - 诉讼-执行-抵质押资产抵债明细 
		getLitigationPledge(recoveryId, maps, executeInfo);
		recoveryInfo.setProjectRecoveryLitigationExecuteInfo(executeInfo);
	}
	
	private void getLitigationPledge(Long recoveryId, Map<String, List<CommonAttachmentInfo>> maps,
										ProjectRecoveryLitigationExecuteInfo executeInfo) {
		List<ProjectRecoveryDebtorReorganizationPledgeDO> pledgeDOs = projectRecoveryDebtorReorganizationPledgeDAO
			.findByRecoveryIdAndType(recoveryId, ProjectRecoveryTypeEnum.LITIGATION_EXECUTE.code());
		List<ProjectRecoveryDebtorReorganizationPledgeInfo> infos = new ArrayList<ProjectRecoveryDebtorReorganizationPledgeInfo>();
		for (ProjectRecoveryDebtorReorganizationPledgeDO pledgeDO : pledgeDOs) {
			ProjectRecoveryDebtorReorganizationPledgeInfo info = new ProjectRecoveryDebtorReorganizationPledgeInfo();
			MiscUtil.copyPoObject(info, pledgeDO);
			info.setProjectRecoveryType(ProjectRecoveryTypeEnum.getByCode(pledgeDO
				.getProjectRecoveryType()));
			info.setPledgeAssetManagementMode(PledgeAssetManagementModeEnum.getByCode(pledgeDO
				.getPledgeAssetManagementMode()));
			// 附件 
			setLitigationPledgeUrl(maps, info);
			infos.add(info);
		}
		executeInfo.setProjectRecoveryDebtorReorganizationPledgeInfos(infos);
	}
	
	private void setLitigationPledgeUrl(Map<String, List<CommonAttachmentInfo>> maps,
										ProjectRecoveryDebtorReorganizationPledgeInfo info) {
		/*** 诉讼-抵质押资产抵债-默认附件-附件 **/
		info.setRecoveryLitigationExecutePledgeSoldOutMemo(getByTypeCode(maps,
			"RECOVERY_LITIGATION_EXECUTE_PLEDGE_SOLD_OUT_MEMO", info.getId()));
		info.setRecoveryLitigationExecutePledgeSoldOutMemoUrl(attachsToUrl(info
			.getRecoveryLitigationExecutePledgeSoldOutMemo()));
	}
	
	private void getLitigationAmountDetail(Long recoveryId,
											ProjectRecoveryLitigationExecuteInfo executeInfo) {
		List<ProjectRecoveryDebtorReorganizationAmountDetailDO> detailDOs = projectRecoveryDebtorReorganizationAmountDetailDAO
			.findByRecoveryIdAndType(recoveryId, ProjectRecoveryTypeEnum.LITIGATION_EXECUTE.code());
		List<ProjectRecoveryDebtorReorganizationAmountDetailInfo> infos = new ArrayList<ProjectRecoveryDebtorReorganizationAmountDetailInfo>();
		for (ProjectRecoveryDebtorReorganizationAmountDetailDO detailDO : detailDOs) {
			ProjectRecoveryDebtorReorganizationAmountDetailInfo info = new ProjectRecoveryDebtorReorganizationAmountDetailInfo();
			MiscUtil.copyPoObject(info, detailDO);
			info.setProjectRecoveryType(ProjectRecoveryTypeEnum.getByCode(detailDO
				.getProjectRecoveryType()));
			// 附件  [暂无]
			infos.add(info);
		}
		executeInfo.setProjectRecoveryDebtorReorganizationAmountDetailInfos(infos);
	}
	
	private void getLitigationExecuteStuff(Long recoveryId,
											Map<String, List<CommonAttachmentInfo>> maps,
											ProjectRecoveryLitigationExecuteInfo executeInfo) {
		List<ProjectRecoveryLitigationExecuteStuffDO> stuffDOs = projectRecoveryLitigationExecuteStuffDAO
			.findByRecoveryId(recoveryId);
		List<ProjectRecoveryLitigationExecuteStuffInfo> infos = new ArrayList<ProjectRecoveryLitigationExecuteStuffInfo>();
		for (ProjectRecoveryLitigationExecuteStuffDO stuffDO : stuffDOs) {
			ProjectRecoveryLitigationExecuteStuffInfo info = new ProjectRecoveryLitigationExecuteStuffInfo();
			MiscUtil.copyPoObject(info, stuffDO);
			info.setDescribeType(ProjectRecoveryDescribeTypeEnum.getByCode(stuffDO
				.getDescribeType()));
			// 附件
			setLitigationExecuteStuffUrl(maps, info);
			infos.add(info);
		}
		executeInfo.setProjectRecoveryLitigationExecuteStuffInfos(infos);
	}
	
	private void setLitigationExecuteStuffUrl(Map<String, List<CommonAttachmentInfo>> maps,
												ProjectRecoveryLitigationExecuteStuffInfo info) {
		/*** 诉讼-抵质押资产抵债-默认附件-附件 **/
		info.setRecoveryLitigationExecuteStuffMemo(getByTypeCode(maps,
			"RECOVERY_LITIGATION_EXECUTE_STUFF_MEMO", info.getId()));
		info.setRecoveryLitigationExecuteStuffMemoUrl(attachsToUrl(info
			.getRecoveryLitigationExecuteStuffMemo()));
	}
	
	private void getLitigationExecuteUrl(Map<String, List<CommonAttachmentInfo>> maps,
											ProjectRecoveryLitigationExecuteInfo executeInfo) {
		
		/*** 诉讼-执行-强制执行申请-附件 **/
		executeInfo.setRecoveryLitigationExecuteExecuteApply(getByTypeCode(maps,
			"RECOVERY_LITIGATION_EXECUTE_EXECUTE_APPLY", executeInfo.getId()));
		executeInfo.setRecoveryLitigationExecuteExecuteApplyUrl(attachsToUrl(executeInfo
			.getRecoveryLitigationExecuteExecuteApply()));
		/*** 诉讼-执行-立案-附件 **/
		executeInfo.setRecoveryLitigationExecutePlaceOnFile(getByTypeCode(maps,
			"RECOVERY_LITIGATION_EXECUTE_PLACE_ON_FILE", executeInfo.getId()));
		executeInfo.setRecoveryLitigationExecutePlaceOnFileUrl(attachsToUrl(executeInfo
			.getRecoveryLitigationExecutePlaceOnFile()));
		/*** 诉讼-执行-受理法院-附件 **/
		executeInfo.setRecoveryLitigationExecuteAcceptingCourt(getByTypeCode(maps,
			"RECOVERY_LITIGATION_EXECUTE_ACCEPTING_COURT", executeInfo.getId()));
		executeInfo.setRecoveryLitigationExecuteAcceptingCourtUrl(attachsToUrl(executeInfo
			.getRecoveryLitigationExecuteAcceptingCourt()));
		/*** 诉讼-执行-执行和解-附件 **/
		executeInfo.setRecoveryLitigationExecuteCompromise(getByTypeCode(maps,
			"RECOVERY_LITIGATION_EXECUTE_COMPROMISE", executeInfo.getId()));
		executeInfo.setRecoveryLitigationExecuteCompromiseUrl(attachsToUrl(executeInfo
			.getRecoveryLitigationExecuteCompromise()));
		/*** 诉讼-执行-调解-附件 **/
		executeInfo.setRecoveryLitigationExecuteConciliation(getByTypeCode(maps,
			"RECOVERY_LITIGATION_EXECUTE_CONCILIATION", executeInfo.getId()));
		executeInfo.setRecoveryLitigationExecuteConciliationUrl(attachsToUrl(executeInfo
			.getRecoveryLitigationExecuteConciliation()));
		/*** 诉讼-执行-评估-附件 **/
		executeInfo.setRecoveryLitigationExecuteEstimate(getByTypeCode(maps,
			"RECOVERY_LITIGATION_EXECUTE_ESTIMATE", executeInfo.getId()));
		executeInfo.setRecoveryLitigationExecuteEstimateUrl(attachsToUrl(executeInfo
			.getRecoveryLitigationExecuteEstimate()));
		
		/*** 诉讼-执行-备注-附件 **/
		executeInfo.setRecoveryLitigationExecuteMemo(getByTypeCode(maps,
			"RECOVERY_LITIGATION_EXECUTE_MEMO", executeInfo.getId()));
		executeInfo.setRecoveryLitigationExecuteMemoUrl(attachsToUrl(executeInfo
			.getRecoveryLitigationExecuteMemo()));
	}
	
	private void getLitigationCertificate(Long recoveryId,
											Map<String, List<CommonAttachmentInfo>> maps,
											ProjectRecoveryInfo recoveryInfo) {
		ProjectRecoveryLitigationCertificateDO certificateDO = projectRecoveryLitigationCertificateDAO
			.findByRecoveryId(recoveryId);
		ProjectRecoveryLitigationCertificateInfo info = new ProjectRecoveryLitigationCertificateInfo();
		MiscUtil.copyPoObject(info, certificateDO);
		// 附件
		
		setLitigationCertificateUrl(maps, info);
		recoveryInfo.setProjectRecoveryLitigationCertificateInfo(info);
	}
	
	private void setLitigationCertificateUrl(Map<String, List<CommonAttachmentInfo>> maps,
												ProjectRecoveryLitigationCertificateInfo info) {
		/*** 诉讼-强制执行公证执行证书-执行证书-附件 **/
		info.setRecoveryLitigationCertificateNo(getByTypeCode(maps,
			"RECOVERY_LITIGATION_CERTIFICATE_NO", info.getId()));
		info.setRecoveryLitigationCertificateNoUrl(attachsToUrl(info
			.getRecoveryLitigationCertificateNo()));
		/*** 诉讼-强制执行公证执行证书-申请书-附件 **/
		info.setRecoveryLitigationCertificateDeclaration(getByTypeCode(maps,
			"RECOVERY_LITIGATION_CERTIFICATE_DECLARATION", info.getId()));
		info.setRecoveryLitigationCertificateDeclarationUrl(attachsToUrl(info
			.getRecoveryLitigationCertificateDeclaration()));
		/*** 诉讼-强制执行公证执行证书-证据清单及证据-附件 **/
		info.setRecoveryLitigationCertificateEvidence(getByTypeCode(maps,
			"RECOVERY_LITIGATION_CERTIFICATE_EVIDENCE", info.getId()));
		info.setRecoveryLitigationCertificateEvidenceUrl(attachsToUrl(info
			.getRecoveryLitigationCertificateEvidence()));
		/*** 诉讼-强制执行公证执行证书-缴费通知书-附件 **/
		info.setRecoveryLitigationCertificatePayNotice(getByTypeCode(maps,
			"RECOVERY_LITIGATION_CERTIFICATE_PAY_NOTICE", info.getId()));
		info.setRecoveryLitigationCertificatePayNoticeUrl(attachsToUrl(info
			.getRecoveryLitigationCertificatePayNotice()));
		/*** 诉讼-强制执行公证执行证书-其他-附件 **/
		info.setRecoveryLitigationCertificateOther(getByTypeCode(maps,
			"RECOVERY_LITIGATION_CERTIFICATE_OTHER", info.getId()));
		info.setRecoveryLitigationCertificateOtherUrl(attachsToUrl(info
			.getRecoveryLitigationCertificateOther()));
		/*** 诉讼-强制执行公证执行证书-备注-附件 **/
		info.setRecoveryLitigationCertificateMemo(getByTypeCode(maps,
			"RECOVERY_LITIGATION_CERTIFICATE_MEMO", info.getId()));
		info.setRecoveryLitigationCertificateMemoUrl(attachsToUrl(info
			.getRecoveryLitigationCertificateMemo()));
	}
	
	private void getLitigationSpecialProcedure(Long recoveryId,
												Map<String, List<CommonAttachmentInfo>> maps,
												ProjectRecoveryInfo recoveryInfo) {
		ProjectRecoveryLitigationSpecialProcedureDO procedureDO = projectRecoveryLitigationSpecialProcedureDAO
			.findByRecoveryId(recoveryId);
		ProjectRecoveryLitigationSpecialProcedureInfo info = new ProjectRecoveryLitigationSpecialProcedureInfo();
		MiscUtil.copyPoObject(info, procedureDO);
		// 附件
		
		setLitigationSpecialProcedureUrl(maps, info);
		recoveryInfo.setProjectRecoveryLitigationSpecialProcedureInfo(info);
	}
	
	private void setLitigationSpecialProcedureUrl(	Map<String, List<CommonAttachmentInfo>> maps,
													ProjectRecoveryLitigationSpecialProcedureInfo info) {
		/*** 诉讼-实现担保物权特别程序-裁判文书-附件 **/
		info.setRecoveryLitigationSpecialProcedureRefereeClerk(getByTypeCode(maps,
			"RECOVERY_LITIGATION_SPECIAL_PROCEDURE_REFEREE_CLERK", info.getId()));
		info.setRecoveryLitigationSpecialProcedureRefereeClerkUrl(attachsToUrl(info
			.getRecoveryLitigationSpecialProcedureRefereeClerk()));
		/*** 诉讼-实现担保物权特别程序-诉状-附件 **/
		info.setRecoveryLitigationSpecialProcedurePetition(getByTypeCode(maps,
			"RECOVERY_LITIGATION_SPECIAL_PROCEDURE_PETITION", info.getId()));
		info.setRecoveryLitigationSpecialProcedurePetitionUrl(attachsToUrl(info
			.getRecoveryLitigationSpecialProcedurePetition()));
		/*** 诉讼-实现担保物权特别程序-证据清单及证据-附件 **/
		info.setRecoveryLitigationSpecialProcedureEvidence(getByTypeCode(maps,
			"RECOVERY_LITIGATION_SPECIAL_PROCEDURE_EVIDENCE", info.getId()));
		info.setRecoveryLitigationSpecialProcedureEvidenceUrl(attachsToUrl(info
			.getRecoveryLitigationSpecialProcedureEvidence()));
		/*** 诉讼-实现担保物权特别程序-案件受理通知书-附件 **/
		info.setRecoveryLitigationSpecialProcedureAcceptNotice(getByTypeCode(maps,
			"RECOVERY_LITIGATION_SPECIAL_PROCEDURE_ACCEPT_NOTICE", info.getId()));
		info.setRecoveryLitigationSpecialProcedureAcceptNoticeUrl(attachsToUrl(info
			.getRecoveryLitigationSpecialProcedureAcceptNotice()));
		/*** 诉讼-实现担保物权特别程序-缴费通知书-附件 **/
		info.setRecoveryLitigationSpecialProcedurePayNotice(getByTypeCode(maps,
			"RECOVERY_LITIGATION_SPECIAL_PROCEDURE_PAY_NOTICE", info.getId()));
		info.setRecoveryLitigationSpecialProcedurePayNoticeUrl(attachsToUrl(info
			.getRecoveryLitigationSpecialProcedurePayNotice()));
		/*** 诉讼-实现担保物权特别程序-备注-附件 **/
		info.setRecoveryLitigationSpecialProcedureMemo(getByTypeCode(maps,
			"RECOVERY_LITIGATION_SPECIAL_PROCEDURE_MEMO", info.getId()));
		info.setRecoveryLitigationSpecialProcedureMemoUrl(attachsToUrl(info
			.getRecoveryLitigationSpecialProcedureMemo()));
	}
	
	private void getLitigationSecondAppeal(Long recoveryId,
											Map<String, List<CommonAttachmentInfo>> maps,
											ProjectRecoveryInfo recoveryInfo) {
		List<ProjectRecoveryLitigationSecondAppealDO> appealDOs = projectRecoveryLitigationSecondAppealDAO
			.findByRecoveryId(recoveryId);
		List<ProjectRecoveryLitigationSecondAppealInfo> infos = new ArrayList<ProjectRecoveryLitigationSecondAppealInfo>();
		for (ProjectRecoveryLitigationSecondAppealDO appealDO : appealDOs) {
			ProjectRecoveryLitigationSecondAppealInfo info = new ProjectRecoveryLitigationSecondAppealInfo();
			MiscUtil.copyPoObject(info, appealDO);
			// 附件
			
			setLitigationSecondAppealUrl(maps, info);
			infos.add(info);
		}
		recoveryInfo.setProjectRecoveryLitigationSecondAppealInfos(infos);
	}
	
	private void setLitigationSecondAppealUrl(Map<String, List<CommonAttachmentInfo>> maps,
												ProjectRecoveryLitigationSecondAppealInfo info) {
		/*** 诉讼-二审上述-上诉请求-附件 **/
		info.setRecoveryLitigationSecondAppealAppealDemand(getByTypeCode(maps,
			"RECOVERY_LITIGATION_SECOND_APPEAL_APPEAL_DEMAND", info.getId()));
		info.setRecoveryLitigationSecondAppealAppealDemandUrl(attachsToUrl(info
			.getRecoveryLitigationSecondAppealAppealDemand()));
		/*** 诉讼-二审上述-公告时间-附件 **/
		info.setRecoveryLitigationSecondAppealNoticeTime(getByTypeCode(maps,
			"RECOVERY_LITIGATION_SECOND_APPEAL_NOTICE_TIME", info.getId()));
		info.setRecoveryLitigationSecondAppealNoticeTimeUrl(attachsToUrl(info
			.getRecoveryLitigationSecondAppealNoticeTime()));
		/*** 诉讼-二审上述-开庭时间-附件 **/
		info.setRecoveryLitigationSecondAppealOpeningTime(getByTypeCode(maps,
			"RECOVERY_LITIGATION_SECOND_APPEAL_OPENING_TIME", info.getId()));
		info.setRecoveryLitigationSecondAppealOpeningTimeUrl(attachsToUrl(info
			.getRecoveryLitigationSecondAppealOpeningTime()));
		/*** 诉讼-二审上述-新证据-附件 **/
		info.setRecoveryLitigationSecondAppealNewEvidence(getByTypeCode(maps,
			"RECOVERY_LITIGATION_SECOND_APPEAL_NEW_EVIDENCE", info.getId()));
		info.setRecoveryLitigationSecondAppealNewEvidenceUrl(attachsToUrl(info
			.getRecoveryLitigationSecondAppealNewEvidence()));
		/*** 诉讼-二审上述-争议焦点-附件 **/
		info.setRecoveryLitigationSecondAppealControversyFocus(getByTypeCode(maps,
			"RECOVERY_LITIGATION_SECOND_APPEAL_CONTROVERSY_FOCUS", info.getId()));
		info.setRecoveryLitigationSecondAppealControversyFocusUrl(attachsToUrl(info
			.getRecoveryLitigationSecondAppealControversyFocus()));
		/*** 诉讼-二审上述-备注-附件 **/
		info.setRecoveryLitigationSecondAppealMemo(getByTypeCode(maps,
			"RECOVERY_LITIGATION_SECOND_APPEAL_MEMO", info.getId()));
		info.setRecoveryLitigationSecondAppealMemoUrl(attachsToUrl(info
			.getRecoveryLitigationSecondAppealMemo()));
	}
	
	private void getLitigationReferee(Long recoveryId,
										Map<String, List<CommonAttachmentInfo>> maps,
										ProjectRecoveryInfo recoveryInfo) {
		List<ProjectRecoveryLitigationRefereeDO> refereeDOs = projectRecoveryLitigationRefereeDAO
			.findByRecoveryId(recoveryId);
		List<ProjectRecoveryLitigationRefereeInfo> infos = new ArrayList<ProjectRecoveryLitigationRefereeInfo>();
		for (ProjectRecoveryLitigationRefereeDO refereeDO : refereeDOs) {
			ProjectRecoveryLitigationRefereeInfo info = new ProjectRecoveryLitigationRefereeInfo();
			MiscUtil.copyPoObject(info, refereeDO);
			info.setProjectRecoveryRefereeType(ProjectRecoveryRefereeTypeEnum.getByCode(refereeDO
				.getProjectRecoveryRefereeType()));
			
			// 附件
			
			setLitigationRefereeUrl(maps, info);
			
			infos.add(info);
		}
		recoveryInfo.setProjectRecoveryLitigationRefereeInfos(infos);
	}
	
	private void setLitigationRefereeUrl(Map<String, List<CommonAttachmentInfo>> maps,
											ProjectRecoveryLitigationRefereeInfo info) {
		/*** 诉讼-裁判-裁判文书-附件 **/
		info.setRecoveryLitigationRefereeClerk(getByTypeCode(maps,
			"RECOVERY_LITIGATION_REFEREE_CLERK", info.getId()));
		info.setRecoveryLitigationRefereeClerkUrl(attachsToUrl(info
			.getRecoveryLitigationRefereeClerk()));
		/*** 诉讼-裁判-送达时间-附件 **/
		info.setRecoveryLitigationRefereeArrivedTime(getByTypeCode(maps,
			"RECOVERY_LITIGATION_REFEREE_ARRIVED_TIME", info.getId()));
		info.setRecoveryLitigationRefereeArrivedTimeUrl(attachsToUrl(info
			.getRecoveryLitigationRefereeArrivedTime()));
		/*** 诉讼-裁判-公告时间-附件 **/
		info.setRecoveryLitigationRefereeNoticeTime(getByTypeCode(maps,
			"RECOVERY_LITIGATION_REFEREE_NOTICE_TIME", info.getId()));
		info.setRecoveryLitigationRefereeNoticeTimeUrl(attachsToUrl(info
			.getRecoveryLitigationRefereeNoticeTime()));
		/*** 诉讼-裁判-生效时间-附件 **/
		info.setRecoveryLitigationRefereeEffectiveTime(getByTypeCode(maps,
			"RECOVERY_LITIGATION_REFEREE_EFFECTIVE_TIME", info.getId()));
		info.setRecoveryLitigationRefereeEffectiveTimeUrl(attachsToUrl(info
			.getRecoveryLitigationRefereeEffectiveTime()));
		/*** 诉讼-裁判-备注-附件 **/
		info.setRecoveryLitigationRefereeMemo(getByTypeCode(maps,
			"RECOVERY_LITIGATION_REFEREE_MEMO", info.getId()));
		info.setRecoveryLitigationRefereeMemoUrl(attachsToUrl(info
			.getRecoveryLitigationRefereeMemo()));
	}
	
	private void getLitigationOpening(Long recoveryId,
										Map<String, List<CommonAttachmentInfo>> maps,
										ProjectRecoveryInfo recoveryInfo) {
		List<ProjectRecoveryLitigationOpeningDO> openingDOs = projectRecoveryLitigationOpeningDAO
			.findByRecoveryId(recoveryId);
		List<ProjectRecoveryLitigationOpeningInfo> infos = new ArrayList<ProjectRecoveryLitigationOpeningInfo>();
		for (ProjectRecoveryLitigationOpeningDO openingDO : openingDOs) {
			ProjectRecoveryLitigationOpeningInfo info = new ProjectRecoveryLitigationOpeningInfo();
			MiscUtil.copyPoObject(info, openingDO);
			
			// 附件
			setLitigationOpeningUrl(maps, info);
			
			infos.add(info);
		}
		recoveryInfo.setProjectRecoveryLitigationOpeningInfos(infos);
	}
	
	private void setLitigationOpeningUrl(Map<String, List<CommonAttachmentInfo>> maps,
											ProjectRecoveryLitigationOpeningInfo info) {
		/*** 诉讼-开庭-我方主要诉讼请求或答辩意见-附件 **/
		info.setRecoveryLitigationOpeningWeLitigationDemand(getByTypeCode(maps,
			"RECOVERY_LITIGATION_OPENING_WE_LITIGATION_DEMAND", info.getId()));
		info.setRecoveryLitigationOpeningWeLitigationDemandUrl(attachsToUrl(info
			.getRecoveryLitigationOpeningWeLitigationDemand()));
		/*** 诉讼-开庭-对方主要诉讼请求或答辩意见-附件 **/
		info.setRecoveryLitigationOpeningOtherSideLitigationDemand(getByTypeCode(maps,
			"RECOVERY_LITIGATION_OPENING_OTHER_SIDE_LITIGATION_DEMAND", info.getId()));
		info.setRecoveryLitigationOpeningOtherSideLitigationDemandUrl(attachsToUrl(info
			.getRecoveryLitigationOpeningOtherSideLitigationDemand()));
		/*** 诉讼-开庭-补充证据-附件 **/
		info.setRecoveryLitigationOpeningAdditionalEvidence(getByTypeCode(maps,
			"RECOVERY_LITIGATION_OPENING_ADDITIONAL_EVIDENCE", info.getId()));
		info.setRecoveryLitigationOpeningAdditionalEvidenceUrl(attachsToUrl(info
			.getRecoveryLitigationOpeningAdditionalEvidence()));
		/*** 诉讼-开庭-备注-附件 **/
		info.setRecoveryLitigationOpeningMemo(getByTypeCode(maps,
			"RECOVERY_LITIGATION_OPENING_MEMO", info.getId()));
		info.setRecoveryLitigationOpeningMemoUrl(attachsToUrl(info
			.getRecoveryLitigationOpeningMemo()));
	}
	
	private void getLitigationBeforeTrail(Long recoveryId,
											Map<String, List<CommonAttachmentInfo>> maps,
											ProjectRecoveryInfo recoveryInfo) {
		ProjectRecoveryLitigationBeforeTrailDO trailDO = projectRecoveryLitigationBeforeTrailDAO
			.findByRecoveryId(recoveryId);
		if (trailDO == null) {
			return;
		}
		ProjectRecoveryLitigationBeforeTrailInfo trailInfo = new ProjectRecoveryLitigationBeforeTrailInfo();
		MiscUtil.copyPoObject(trailInfo, trailDO);
		trailInfo.setEndNotice(BooleanEnum.getByCode(trailDO.getEndNotice()));
		// 附件
		
		setLitigationBeforeTrailUrl(maps, trailInfo);
		recoveryInfo.setProjectRecoveryLitigationBeforeTrailInfo(trailInfo);
	}
	
	private void setLitigationBeforeTrailUrl(Map<String, List<CommonAttachmentInfo>> maps,
												ProjectRecoveryLitigationBeforeTrailInfo trailInfo) {
		/*** 诉讼-庭前准备-开庭时间-附件 **/
		trailInfo.setRecoveryLitigationBeforeTrailOpeningTime(getByTypeCode(maps,
			"RECOVERY_LITIGATION_BEFORE_TRAIL_OPENING_TIME", trailInfo.getId()));
		trailInfo.setRecoveryLitigationBeforeTrailOpeningTimeUrl(attachsToUrl(trailInfo
			.getRecoveryLitigationBeforeTrailOpeningTime()));
		/*** 诉讼-庭前准备-公告时间-附件 **/
		trailInfo.setRecoveryLitigationBeforeTrailNoticeTime(getByTypeCode(maps,
			"RECOVERY_LITIGATION_BEFORE_TRAIL_NOTICE_TIME", trailInfo.getId()));
		trailInfo.setRecoveryLitigationBeforeTrailNoticeTimeUrl(attachsToUrl(trailInfo
			.getRecoveryLitigationBeforeTrailNoticeTime()));
		/*** 诉讼-庭前准备-文书送达时间-附件 **/
		trailInfo.setRecoveryLitigationBeforeTrailClerkArrivedTime(getByTypeCode(maps,
			"RECOVERY_LITIGATION_BEFORE_TRAIL_CLERK_ARRIVED_TIME", trailInfo.getId()));
		trailInfo.setRecoveryLitigationBeforeTrailClerkArrivedTimeUrl(attachsToUrl(trailInfo
			.getRecoveryLitigationBeforeTrailClerkArrivedTime()));
		/*** 诉讼-庭前准备-管辖异议-附件 **/
		trailInfo.setRecoveryLitigationBeforeTrailJurisdictionObjection(getByTypeCode(maps,
			"RECOVERY_LITIGATION_BEFORE_TRAIL_JURISDICTION_OBJECTION", trailInfo.getId()));
		trailInfo.setRecoveryLitigationBeforeTrailJurisdictionObjectionUrl(attachsToUrl(trailInfo
			.getRecoveryLitigationBeforeTrailJurisdictionObjection()));
		/*** 诉讼-庭前准备-管辖异议裁定-附件 **/
		trailInfo.setRecoveryLitigationBeforeTrailJurisdictionObjectionJudgment(getByTypeCode(maps,
			"RECOVERY_LITIGATION_BEFORE_TRAIL_JURISDICTION_OBJECTION_JUDGMENT", trailInfo.getId()));
		trailInfo
			.setRecoveryLitigationBeforeTrailJurisdictionObjectionJudgmentUrl(attachsToUrl(trailInfo
				.getRecoveryLitigationBeforeTrailJurisdictionObjectionJudgment()));
		/*** 诉讼-庭前准备-管辖异议上诉-附件 **/
		trailInfo.setRecoveryLitigationBeforeTrailJurisdictionObjectionAppeal(getByTypeCode(maps,
			"RECOVERY_LITIGATION_BEFORE_TRAIL_JURISDICTION_OBJECTION_APPEAL", trailInfo.getId()));
		trailInfo
			.setRecoveryLitigationBeforeTrailJurisdictionObjectionAppealUrl(attachsToUrl(trailInfo
				.getRecoveryLitigationBeforeTrailJurisdictionObjectionAppeal()));
		/*** 诉讼-庭前准备-管辖异议二审裁定-附件 **/
		trailInfo
			.setRecoveryLitigationBeforeTrailJurisdictionObjectionJudgmentSecond(getByTypeCode(
				maps, "RECOVERY_LITIGATION_BEFORE_TRAIL_JURISDICTION_OBJECTION_JUDGMENT_SECOND",
				trailInfo.getId()));
		trailInfo
			.setRecoveryLitigationBeforeTrailJurisdictionObjectionJudgmentSecondUrl(attachsToUrl(trailInfo
				.getRecoveryLitigationBeforeTrailJurisdictionObjectionJudgmentSecond()));
		/*** 诉讼-庭前准备-证据交换-附件 **/
		trailInfo.setRecoveryLitigationBeforeTrailEvidenceExchange(getByTypeCode(maps,
			"RECOVERY_LITIGATION_BEFORE_TRAIL_EVIDENCE_EXCHANGE", trailInfo.getId()));
		trailInfo.setRecoveryLitigationBeforeTrailEvidenceExchangeUrl(attachsToUrl(trailInfo
			.getRecoveryLitigationBeforeTrailEvidenceExchange()));
		/*** 诉讼-庭前准备-鉴定申请-附件 **/
		trailInfo.setRecoveryLitigationBeforeTrailAppraisalApply(getByTypeCode(maps,
			"RECOVERY_LITIGATION_BEFORE_TRAIL_APPRAISAL_APPLY", trailInfo.getId()));
		trailInfo.setRecoveryLitigationBeforeTrailAppraisalApplyUrl(attachsToUrl(trailInfo
			.getRecoveryLitigationBeforeTrailAppraisalApply()));
		/*** 诉讼-庭前准备-鉴定材料-附件 **/
		trailInfo.setRecoveryLitigationBeforeTrailAppraisalMaterial(getByTypeCode(maps,
			"RECOVERY_LITIGATION_BEFORE_TRAIL_APPRAISAL_MATERIAL", trailInfo.getId()));
		trailInfo.setRecoveryLitigationBeforeTrailAppraisalMaterialUrl(attachsToUrl(trailInfo
			.getRecoveryLitigationBeforeTrailAppraisalMaterial()));
		/*** 诉讼-庭前准备-鉴定费用-附件 **/
		trailInfo.setRecoveryLitigationBeforeTrailAppraisalAmount(getByTypeCode(maps,
			"RECOVERY_LITIGATION_BEFORE_TRAIL_APPRAISAL_AMOUNT", trailInfo.getId()));
		trailInfo.setRecoveryLitigationBeforeTrailAppraisalAmountUrl(attachsToUrl(trailInfo
			.getRecoveryLitigationBeforeTrailAppraisalAmount()));
		/*** 诉讼-庭前准备-申请调查取证-附件 **/
		trailInfo.setRecoveryLitigationBeforeTrailInvestigatingApply(getByTypeCode(maps,
			"RECOVERY_LITIGATION_BEFORE_TRAIL_INVESTIGATING_APPLY", trailInfo.getId()));
		trailInfo.setRecoveryLitigationBeforeTrailInvestigatingApplyUrl(attachsToUrl(trailInfo
			.getRecoveryLitigationBeforeTrailInvestigatingApply()));
		/*** 诉讼-庭前准备-申请证人出庭-附件 **/
		trailInfo.setRecoveryLitigationBeforeTrailWitnessesApply(getByTypeCode(maps,
			"RECOVERY_LITIGATION_BEFORE_TRAIL_WITNESSES_APPLY", trailInfo.getId()));
		trailInfo.setRecoveryLitigationBeforeTrailWitnessesApplyUrl(attachsToUrl(trailInfo
			.getRecoveryLitigationBeforeTrailWitnessesApply()));
		/*** 诉讼-庭前准备-增加诉讼请求申请-附件 **/
		trailInfo.setRecoveryLitigationBeforeTrailIncreaseLitigationApply(getByTypeCode(maps,
			"RECOVERY_LITIGATION_BEFORE_TRAIL_INCREASE_LITIGATION_APPLY", trailInfo.getId()));
		trailInfo.setRecoveryLitigationBeforeTrailIncreaseLitigationApplyUrl(attachsToUrl(trailInfo
			.getRecoveryLitigationBeforeTrailIncreaseLitigationApply()));
		
		/*** 诉讼-庭前准备-memo附件-附件 **/
		trailInfo.setRecoveryLitigationBeforeTrailMemo(getByTypeCode(maps,
			"RECOVERY_LITIGATION_BEFORE_TRAIL_MEMO", trailInfo.getId()));
		trailInfo.setRecoveryLitigationBeforeTrailMemoUrl(attachsToUrl(trailInfo
			.getRecoveryLitigationBeforeTrailMemo()));
	}
	
	private void getLitigationPreservation(Long recoveryId,
											Map<String, List<CommonAttachmentInfo>> maps,
											ProjectRecoveryInfo recoveryInfo) {
		ProjectRecoveryLitigationPreservationDO preservationDO = projectRecoveryLitigationPreservationDAO
			.findByRecoveryId(recoveryId);
		ProjectRecoveryLitigationPreservationInfo preservationInfo = new ProjectRecoveryLitigationPreservationInfo();
		MiscUtil.copyPoObject(preservationInfo, preservationDO);
		
		// 附件 
		setLitigationPreservationUrl(maps, preservationInfo);
		
		// 3.1 追偿跟踪表 - 诉讼-诉讼保全-保全措施 
		getLitigationBeforePreservationPrecaution(recoveryId, preservationInfo);
		recoveryInfo.setProjectRecoveryLitigationPreservationInfo(preservationInfo);
	}
	
	private void getLitigationBeforePreservationPrecaution(	Long recoveryId,
															ProjectRecoveryLitigationPreservationInfo preservationInfo) {
		List<ProjectRecoveryLitigationBeforePreservationPrecautionDO> precautionDOs = projectRecoveryLitigationBeforePreservationPrecautionDAO
			.findByRecoveryIdAndType(recoveryId,
				ProjectRecoveryLitigationTypeEnum.LITIGATION_IN.code());
		List<ProjectRecoveryLitigationBeforePreservationPrecautionInfo> infos = new ArrayList<ProjectRecoveryLitigationBeforePreservationPrecautionInfo>();
		for (ProjectRecoveryLitigationBeforePreservationPrecautionDO precautionDO : precautionDOs) {
			ProjectRecoveryLitigationBeforePreservationPrecautionInfo info = new ProjectRecoveryLitigationBeforePreservationPrecautionInfo();
			MiscUtil.copyPoObject(info, precautionDO);
			info.setProjectRecoveryLitigationType(ProjectRecoveryLitigationTypeEnum
				.getByCode(precautionDO.getProjectRecoveryLitigationType()));
			info.setProjectRecoveryPreservationType(ProjectRecoveryPreservationTypeEnum
				.getByCode(precautionDO.getProjectRecoveryPreservationType()));
			// 附件  [暂无]
			infos.add(info);
		}
		preservationInfo.setProjectRecoveryLitigationBeforePreservationPrecautionInfos(infos);
	}
	
	private void setLitigationPreservationUrl(	Map<String, List<CommonAttachmentInfo>> maps,
												ProjectRecoveryLitigationPreservationInfo preservationInfo) {
		/*** 诉讼-诉讼保全-保全裁定书-附件 **/
		preservationInfo.setRecoveryLitigationPreservationWrittenVerdict(getByTypeCode(maps,
			"RECOVERY_LITIGATION_PRESERVATION_WRITTEN_VERDICT", preservationInfo.getId()));
		preservationInfo
			.setRecoveryLitigationPreservationWrittenVerdictUrl(attachsToUrl(preservationInfo
				.getRecoveryLitigationPreservationWrittenVerdict()));
		/*** 诉讼-诉讼保全-协助执行通知书-附件 **/
		preservationInfo.setRecoveryLitigationPreservationExecutionNotice(getByTypeCode(maps,
			"RECOVERY_LITIGATION_PRESERVATION_EXECUTION_NOTICE", preservationInfo.getId()));
		preservationInfo
			.setRecoveryLitigationPreservationExecutionNoticeUrl(attachsToUrl(preservationInfo
				.getRecoveryLitigationPreservationExecutionNotice()));
		/*** 诉讼-诉讼保全-送达回执-附件 **/
		preservationInfo.setRecoveryLitigationPreservationDeliveryReceipt(getByTypeCode(maps,
			"RECOVERY_LITIGATION_PRESERVATION_DELIVERY_RECEIPT", preservationInfo.getId()));
		preservationInfo
			.setRecoveryLitigationPreservationDeliveryReceiptUrl(attachsToUrl(preservationInfo
				.getRecoveryLitigationPreservationDeliveryReceipt()));
		/*** 诉讼-诉讼保全-其他-附件 **/
		preservationInfo.setRecoveryLitigationPreservationOther(getByTypeCode(maps,
			"RECOVERY_LITIGATION_PRESERVATION_OTHER", preservationInfo.getId()));
		preservationInfo.setRecoveryLitigationPreservationOtherUrl(attachsToUrl(preservationInfo
			.getRecoveryLitigationPreservationOther()));
	}
	
	private void getLitigationPlaceOnFile(Long recoveryId,
											Map<String, List<CommonAttachmentInfo>> maps,
											ProjectRecoveryInfo recoveryInfo) {
		ProjectRecoveryLitigationPlaceOnFileDO placeOnFileDO = projectRecoveryLitigationPlaceOnFileDAO
			.findByRecoveryId(recoveryId);
		ProjectRecoveryLitigationPlaceOnFileInfo placeOnFileInfo = new ProjectRecoveryLitigationPlaceOnFileInfo();
		MiscUtil.copyPoObject(placeOnFileInfo, placeOnFileDO);
		
		// 附件
		
		setLitigationPlaceOnFileUrl(maps, placeOnFileInfo);
		recoveryInfo.setProjectRecoveryLitigationPlaceOnFileInfo(placeOnFileInfo);
	}
	
	private void setLitigationPlaceOnFileUrl(	Map<String, List<CommonAttachmentInfo>> maps,
												ProjectRecoveryLitigationPlaceOnFileInfo placeOnFileInfo) {
		/*** 诉讼-立案-诉状-附件 **/
		placeOnFileInfo.setRecoveryLitigationPlaceOnFilePetition(getByTypeCode(maps,
			"RECOVERY_LITIGATION_PLACE_ON_FILE_PETITION", placeOnFileInfo.getId()));
		placeOnFileInfo.setRecoveryLitigationPlaceOnFilePetitionUrl(attachsToUrl(placeOnFileInfo
			.getRecoveryLitigationPlaceOnFilePetition()));
		/*** 诉讼-立案-证据清单及证据-附件 **/
		placeOnFileInfo.setRecoveryLitigationPlaceOnFileEvidence(getByTypeCode(maps,
			"RECOVERY_LITIGATION_PLACE_ON_FILE_EVIDENCE", placeOnFileInfo.getId()));
		placeOnFileInfo.setRecoveryLitigationPlaceOnFileEvidenceUrl(attachsToUrl(placeOnFileInfo
			.getRecoveryLitigationPlaceOnFileEvidence()));
		/*** 诉讼-立案-案件受理通知书-附件 **/
		placeOnFileInfo.setRecoveryLitigationPlaceOnFileAcceptNotice(getByTypeCode(maps,
			"RECOVERY_LITIGATION_PLACE_ON_FILE_ACCEPT_NOTICE", placeOnFileInfo.getId()));
		placeOnFileInfo
			.setRecoveryLitigationPlaceOnFileAcceptNoticeUrl(attachsToUrl(placeOnFileInfo
				.getRecoveryLitigationPlaceOnFileAcceptNotice()));
		/*** 诉讼-立案-缴费通知书-附件 **/
		placeOnFileInfo.setRecoveryLitigationPlaceOnFilePayNotice(getByTypeCode(maps,
			"RECOVERY_LITIGATION_PLACE_ON_FILE_PAY_NOTICE", placeOnFileInfo.getId()));
		placeOnFileInfo.setRecoveryLitigationPlaceOnFilePayNoticeUrl(attachsToUrl(placeOnFileInfo
			.getRecoveryLitigationPlaceOnFilePayNotice()));
		/*** 诉讼-立案-其他-附件 **/
		placeOnFileInfo.setRecoveryLitigationPlaceOnFileOther(getByTypeCode(maps,
			"RECOVERY_LITIGATION_PLACE_ON_FILE_OTHER", placeOnFileInfo.getId()));
		placeOnFileInfo.setRecoveryLitigationPlaceOnFileOtherUrl(attachsToUrl(placeOnFileInfo
			.getRecoveryLitigationPlaceOnFileOther()));
	}
	
	private void getLitigationBeforePreservation(Long recoveryId,
													Map<String, List<CommonAttachmentInfo>> maps,
													ProjectRecoveryInfo recoveryInfo) {
		ProjectRecoveryLitigationBeforePreservationDO preservationDO = projectRecoveryLitigationBeforePreservationDAO
			.findByRecoveryId(recoveryId);
		ProjectRecoveryLitigationBeforePreservationInfo preservationInfo = new ProjectRecoveryLitigationBeforePreservationInfo();
		MiscUtil.copyPoObject(preservationInfo, preservationDO);
		
		// 附件
		
		setLitigationBeforePreservationUrl(maps, preservationInfo);
		// 1.1 追偿跟踪表 - 诉讼-诉前保全-保全措施 
		getLitigationBeforePreservationPrecaution(recoveryId, preservationInfo);
		recoveryInfo.setProjectRecoveryLitigationBeforePreservationInfo(preservationInfo);
	}
	
	private void getLitigationBeforePreservationPrecaution(	Long recoveryId,
															ProjectRecoveryLitigationBeforePreservationInfo preservationInfo) {
		List<ProjectRecoveryLitigationBeforePreservationPrecautionDO> precautionDOs = projectRecoveryLitigationBeforePreservationPrecautionDAO
			.findByRecoveryIdAndType(recoveryId,
				ProjectRecoveryLitigationTypeEnum.LITIGATION_BEFORE.code());
		List<ProjectRecoveryLitigationBeforePreservationPrecautionInfo> infos = new ArrayList<ProjectRecoveryLitigationBeforePreservationPrecautionInfo>();
		// 循环加载
		for (ProjectRecoveryLitigationBeforePreservationPrecautionDO precautionDO : precautionDOs) {
			ProjectRecoveryLitigationBeforePreservationPrecautionInfo info = new ProjectRecoveryLitigationBeforePreservationPrecautionInfo();
			MiscUtil.copyPoObject(info, precautionDO);
			info.setProjectRecoveryLitigationType(ProjectRecoveryLitigationTypeEnum
				.getByCode(precautionDO.getProjectRecoveryLitigationType()));
			info.setProjectRecoveryPreservationType(ProjectRecoveryPreservationTypeEnum
				.getByCode(precautionDO.getProjectRecoveryPreservationType()));
			//   附件 [暂无]
			infos.add(info);
		}
		preservationInfo.setProjectRecoveryLitigationBeforePreservationPrecautionInfos(infos);
	}
	
	private void setLitigationBeforePreservationUrl(Map<String, List<CommonAttachmentInfo>> maps,
													ProjectRecoveryLitigationBeforePreservationInfo info) {
		/*** 诉讼-诉前保全-保全裁定书-附件 **/
		info.setRecoveryLitigationBeforePreservationWrittenVerdict(getByTypeCode(maps,
			"RECOVERY_LITIGATION_BEFORE_PRESERVATION_WRITTEN_VERDICT", info.getId()));
		info.setRecoveryLitigationBeforePreservationWrittenVerdictUrl(attachsToUrl(info
			.getRecoveryLitigationBeforePreservationWrittenVerdict()));
		/*** 诉讼-诉前保全-协助执行通知书-附件 **/
		info.setRecoveryLitigationBeforePreservationExecutionNotice(getByTypeCode(maps,
			"RECOVERY_LITIGATION_BEFORE_PRESERVATION_EXECUTION_NOTICE", info.getId()));
		info.setRecoveryLitigationBeforePreservationExecutionNoticeUrl(attachsToUrl(info
			.getRecoveryLitigationBeforePreservationExecutionNotice()));
		/*** 诉讼-诉前保全-送达回执-附件 **/
		info.setRecoveryLitigationBeforePreservationDeliveryReceipt(getByTypeCode(maps,
			"RECOVERY_LITIGATION_BEFORE_PRESERVATION_DELIVERY_RECEIPT", info.getId()));
		info.setRecoveryLitigationBeforePreservationDeliveryReceiptUrl(attachsToUrl(info
			.getRecoveryLitigationBeforePreservationDeliveryReceipt()));
		/*** 诉讼-诉前保全-其他-附件 **/
		info.setRecoveryLitigationBeforePreservationOther(getByTypeCode(maps,
			"RECOVERY_LITIGATION_BEFORE_PRESERVATION_OTHER", info.getId()));
		info.setRecoveryLitigationBeforePreservationOtherUrl(attachsToUrl(info
			.getRecoveryLitigationBeforePreservationOther()));
	}
	
	private void getDebtorReorganizationPledge(	Long recoveryId,
												Map<String, List<CommonAttachmentInfo>> maps,
												ProjectRecoveryDebtorReorganizationInfo reorganizationInfo) {
		List<ProjectRecoveryDebtorReorganizationPledgeDO> pledgeDOs = projectRecoveryDebtorReorganizationPledgeDAO
			.findByRecoveryIdAndType(recoveryId,
				ProjectRecoveryTypeEnum.DEBTOR_REORGANIZATION.code());
		List<ProjectRecoveryDebtorReorganizationPledgeInfo> infos = new ArrayList<ProjectRecoveryDebtorReorganizationPledgeInfo>();
		for (ProjectRecoveryDebtorReorganizationPledgeDO pledgeDO : pledgeDOs) {
			ProjectRecoveryDebtorReorganizationPledgeInfo info = new ProjectRecoveryDebtorReorganizationPledgeInfo();
			MiscUtil.copyPoObject(info, pledgeDO);
			info.setProjectRecoveryType(ProjectRecoveryTypeEnum.getByCode(pledgeDO
				.getProjectRecoveryType()));
			info.setPledgeAssetManagementMode(PledgeAssetManagementModeEnum.getByCode(pledgeDO
				.getPledgeAssetManagementMode()));
			
			// 附件
			setDebtorReorganizationPledgeUrl(maps, info);
			infos.add(info);
		}
		reorganizationInfo.setProjectRecoveryDebtorReorganizationPledgeInfos(infos);
	}
	
	private void setDebtorReorganizationPledgeUrl(	Map<String, List<CommonAttachmentInfo>> maps,
													ProjectRecoveryDebtorReorganizationPledgeInfo info) {
		/*** 债务人重整或破产清算-抵质押资产抵债-默认附件-附件 **/
		info.setRecoveryReorganizationPledgeSoldOut(getByTypeCode(maps,
			"RECOVERY_REORGANIZATION_PLEDGE_SOLD_OUT", info.getId()));
		info.setRecoveryReorganizationPledgeSoldOutUrl(attachsToUrl(info
			.getRecoveryReorganizationPledgeSoldOut()));
	}
	
	private void getDebtorReorganizationAmountDetail(	Long recoveryId,
														ProjectRecoveryDebtorReorganizationInfo reorganizationInfo) {
		List<ProjectRecoveryDebtorReorganizationAmountDetailDO> detailDOs = projectRecoveryDebtorReorganizationAmountDetailDAO
			.findByRecoveryIdAndType(recoveryId,
				ProjectRecoveryTypeEnum.DEBTOR_REORGANIZATION.code());
		List<ProjectRecoveryDebtorReorganizationAmountDetailInfo> infos = new ArrayList<ProjectRecoveryDebtorReorganizationAmountDetailInfo>();
		// 循环获取 
		for (ProjectRecoveryDebtorReorganizationAmountDetailDO detailDO : detailDOs) {
			ProjectRecoveryDebtorReorganizationAmountDetailInfo info = new ProjectRecoveryDebtorReorganizationAmountDetailInfo();
			MiscUtil.copyPoObject(info, detailDO);
			info.setProjectRecoveryType(ProjectRecoveryTypeEnum.getByCode(detailDO
				.getProjectRecoveryType()));
			// 附件  暂无
			
			infos.add(info);
		}
		reorganizationInfo.setProjectRecoveryDebtorReorganizationAmountDetailInfos(infos);
	}
	
	private void getDebtorReorganizationDebtsCouncil(	Long recoveryId,
														ProjectRecoveryDebtorReorganizationInfo reorganizationInfo) {
		List<ProjectRecoveryDebtorReorganizationDebtsCouncilDO> councilDOs = projectRecoveryDebtorReorganizationDebtsCouncilDAO
			.findByRecoveryId(recoveryId);
		List<ProjectRecoveryDebtorReorganizationDebtsCouncilInfo> infos = new ArrayList<ProjectRecoveryDebtorReorganizationDebtsCouncilInfo>();
		// 循环获取 
		for (ProjectRecoveryDebtorReorganizationDebtsCouncilDO councilDO : councilDOs) {
			ProjectRecoveryDebtorReorganizationDebtsCouncilInfo info = new ProjectRecoveryDebtorReorganizationDebtsCouncilInfo();
			MiscUtil.copyPoObject(info, councilDO);
			// 附件 【暂无】
			infos.add(info);
		}
		reorganizationInfo.setProjectRecoveryDebtorReorganizationDebtsCouncilInfos(infos);
	}
	
	private void setDebtorReorganizationUrl(Map<String, List<CommonAttachmentInfo>> maps,
											ProjectRecoveryDebtorReorganizationInfo reorganizationInfo,
											Long childId) {
		/*** 债务人重整或破产清算-我司申报时间-附件 **/
		reorganizationInfo.setRecoveryReorganizationDivisionWeDeclareTime(getByTypeCode(maps,
			"RECOVERY_REORGANIZATION_DIVISION_WE_DECLARE_TIME", childId));
		reorganizationInfo
			.setRecoveryReorganizationDivisionWeDeclareTimeUrl(attachsToUrl(reorganizationInfo
				.getRecoveryReorganizationDivisionWeDeclareTime()));
		/*** 债务人重整或破产清算-债权确认-附件 **/
		reorganizationInfo.setRecoveryReorganizationDebtsConfirm(getByTypeCode(maps,
			"RECOVERY_REORGANIZATION_DEBTS_CONFIRM", childId));
		reorganizationInfo.setRecoveryReorganizationDebtsConfirmUrl(attachsToUrl(reorganizationInfo
			.getRecoveryReorganizationDebtsConfirm()));
		/*** 债务人重整或破产清算-会议情况-附件 **/
		reorganizationInfo.setRecoveryReorganizationCouncilCircumstances(getByTypeCode(maps,
			"RECOVERY_REORGANIZATION_COUNCIL_CIRCUMSTANCES", childId));
		reorganizationInfo
			.setRecoveryReorganizationCouncilCircumstancesUrl(attachsToUrl(reorganizationInfo
				.getRecoveryReorganizationCouncilCircumstances()));
		/*** 债务人重整或破产清算-我司意见-附件 **/
		reorganizationInfo.setRecoveryReorganizationWeSuggestion(getByTypeCode(maps,
			"RECOVERY_REORGANIZATION_WE_SUGGESTION", childId));
		reorganizationInfo.setRecoveryReorganizationWeSuggestionUrl(attachsToUrl(reorganizationInfo
			.getRecoveryReorganizationWeSuggestion()));
		/*** 债务人重整或破产清算-批准的重整方案及执行情况-附件 **/
		reorganizationInfo.setRecoveryReorganizationReExecutionPlan(getByTypeCode(maps,
			"RECOVERY_REORGANIZATION_RE_EXECUTION_PLAN", childId));
		reorganizationInfo
			.setRecoveryReorganizationReExecutionPlanUrl(attachsToUrl(reorganizationInfo
				.getRecoveryReorganizationReExecutionPlan()));
		/*** 债务人重整或破产清算-和解方案-附件 **/
		reorganizationInfo.setRecoveryReorganizationSettlementSchemeContent(getByTypeCode(maps,
			"RECOVERY_REORGANIZATION_SETTLEMENT_SCHEME_CONTENT", childId));
		reorganizationInfo
			.setRecoveryReorganizationSettlementSchemeContentUrl(attachsToUrl(reorganizationInfo
				.getRecoveryReorganizationSettlementSchemeContent()));
		/*** 债务人重整或破产清算-清算方案-附件 **/
		reorganizationInfo.setRecoveryReorganizationLiquidationScheme(getByTypeCode(maps,
			"RECOVERY_REORGANIZATION_LIQUIDATION_SCHEME", childId));
		reorganizationInfo
			.setRecoveryReorganizationLiquidationSchemeUrl(attachsToUrl(reorganizationInfo
				.getRecoveryReorganizationLiquidationScheme()));
		/*** 债务人重整或破产清算-清偿情况-附件 **/
		reorganizationInfo.setRecoveryReorganizationLiquidationSituation(getByTypeCode(maps,
			"RECOVERY_REORGANIZATION_LIQUIDATION_SITUATION", childId));
		reorganizationInfo
			.setRecoveryReorganizationLiquidationSituationUrl(attachsToUrl(reorganizationInfo
				.getRecoveryReorganizationLiquidationSituation()));
	}
	
	private List<CommonAttachmentInfo> getByTypeCode(Map<String, List<CommonAttachmentInfo>> maps,
														String code, Long childId) {
		List<CommonAttachmentInfo> allAttachs = maps.get(code);
		List<CommonAttachmentInfo> attachs = new ArrayList<CommonAttachmentInfo>();
		// 获取子id相同的，代表是自己的，因为会有code相同主id相同的list数据，所以要判定子id相同
		if (allAttachs != null) {
			for (CommonAttachmentInfo info : allAttachs) {
				if (StringUtil.equals(String.valueOf(childId), info.getChildId())) {
					attachs.add(info);
				}
			}
		}
		return attachs;
	}
	
	private String attachsToUrl(List<CommonAttachmentInfo> attachs) {
		
		StringBuilder urls = new StringBuilder("");
		if (null != attachs && ListUtil.isNotEmpty(attachs)) {
			for (CommonAttachmentInfo attach : attachs) {
				urls.append(attach.getFileName()).append(",").append(attach.getFilePhysicalPath())
					.append(",").append(attach.getRequestPath()).append(";");
			}
			urls.deleteCharAt(urls.length() - 1);
		} else {
			return "";
		}
		return urls.toString();
	}
	
	/**
	 * 抓去所有本追偿信息的附件，并按照类型分类
	 * @param recoveryId
	 * @return
	 */
	private Map<String, List<CommonAttachmentInfo>> getAttachMap(Long recoveryId) {
		Map<String, List<CommonAttachmentInfo>> maps = new HashMap<String, List<CommonAttachmentInfo>>();
		List<CommonAttachmentDO> attachs = commonAttachmentDAO.findByManyModuleType(
			ProjectRecoveryUploadTypeEnum.getAllEnumCode(), String.valueOf(recoveryId), null);
		for (CommonAttachmentDO attach : attachs) {
			String typeCode = attach.getModuleType();
			List<CommonAttachmentInfo> infos = maps.get(typeCode);
			if (infos == null) {
				infos = new ArrayList<CommonAttachmentInfo>();
			}
			CommonAttachmentInfo info = new CommonAttachmentInfo();
			MiscUtil.copyPoObject(info, attach);
			info.setModuleType(CommonAttachmentTypeEnum.getByCode(typeCode));
			infos.add(info);
			maps.put(typeCode, infos);
		}
		return maps;
	}
	
	/**
	 * @param projectRecoveryQueryOrder
	 * @return
	 * @see com.born.fcs.pm.ws.service.recovery.ProjectRecoveryService#queryRecovery(com.born.fcs.pm.ws.order.recovery.ProjectRecoveryQueryOrder)
	 */
	@Override
	public QueryBaseBatchResult<ProjectRecoveryListInfo> queryRecovery(	ProjectRecoveryQueryOrder projectRecoveryQueryOrder) {
		logger.info("进入ProjectRecoveryServiceImpl的queryRecovery方法，入参：" + projectRecoveryQueryOrder);
		QueryBaseBatchResult<ProjectRecoveryListInfo> result = new QueryBaseBatchResult<ProjectRecoveryListInfo>();
		
		try {
			ProjectRecoveryQueryDO queryDO = new ProjectRecoveryQueryDO();
			BeanCopier.staticCopy(projectRecoveryQueryOrder, queryDO);
			// 非风险处置会小组时，剔除小组用户id
			if (BooleanEnum.YES == projectRecoveryQueryOrder.getIsAllFind()) {
				queryDO.setUserId(null);
			}
			long totalCount = 0;
			// 查询totalcount
			totalCount = extraDAO.projectRecoveryQueryCount(queryDO);
			
			PageComponent component = new PageComponent(projectRecoveryQueryOrder, totalCount);
			// 查询list
			List<ProjectRecoveryQueryDO> list = Lists.newArrayList();
			list = extraDAO.projectRecoveryQuery(queryDO);
			
			List<ProjectRecoveryListInfo> pageList = new ArrayList<ProjectRecoveryListInfo>();
			for (ProjectRecoveryQueryDO item : list) {
				ProjectRecoveryListInfo info = new ProjectRecoveryListInfo();
				MiscUtil.copyPoObject(info, item);
				info.setRecoveryStatus(ProjectRecoveryStatusEnum.getByCode(item.getRecoveryStatus()));
				
				pageList.add(info);
			}
			result.setSuccess(true);
			result.setPageList(pageList);
			result.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询追偿列表信息失败" + e.getMessage(), e);
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return result;
	}
	
	@Override
	public FcsBaseResult closeProjectRecovery(ProjectRecoveryOrder order) {
		logger.info("进入ProjectRecoveryServiceImpl的closeProjectRecovery方法，入参：" + order);
		FcsBaseResult result = new FcsBaseResult();
		if (order == null || order.getId() == null || order.getId() <= 0) {
			result.setFcsResultEnum(FcsResultEnum.INCOMPLETE_REQ_PARAM);
			result.setMessage("入参不能小于0！");
			logger.error("入参不能小于0！");
			return result;
		}
		try {
			ProjectRecoveryDO recoveryDO = projectRecoveryDAO.findById(order.getId());
			// 添加任务 项目关闭判定
			ProjectDO projectDO = projectDAO.findByProjectCode(recoveryDO.getProjectCode());
			// 维护项目阶段和项目状态为完成
			if (!ProjectStatusEnum.RECOVERY.code().equals(projectDO.getStatus())
				|| !ProjectPhasesEnum.RECOVERY_PHASES.code().equals(projectDO.getPhases())) {
				// 不在追偿状态的不允许关闭追偿
				result.setSuccess(false);
				result.setFcsResultEnum(FcsResultEnum.NO_ACCESS);
				result.setMessage("该项目不允许关闭追偿！");
				return result;
			}
			if (BooleanEnum.YES == order.getReClose()) {
				recoveryDO.setRecoveryStatus(ProjectRecoveryStatusEnum.RECOVERYING.code());
			} else {
				recoveryDO.setRecoveryStatus(ProjectRecoveryStatusEnum.CLOSEING.code());
			}
			projectRecoveryDAO.update(recoveryDO);
			result.setSuccess(true);
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
			result.setMessage(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setMessage(e.getMessage());
		}
		return result;
	}
	
	@Override
	public FcsBaseResult closeProjectRecoverySure(final ProjectRecoveryOrder order) {
		logger.info("进入ProjectRecoveryServiceImpl的closeProjectRecoverySure方法，入参：" + order);
		FcsBaseResult result = new FcsBaseResult();
		if (order == null || order.getId() == null || order.getId() <= 0) {
			result.setFcsResultEnum(FcsResultEnum.INCOMPLETE_REQ_PARAM);
			result.setMessage("入参不能小于0！");
			logger.error("入参不能小于0！");
			return result;
		}
		try {
			result = transactionTemplate.execute(new TransactionCallback<FcsBaseResult>() {
				
				@Override
				public FcsBaseResult doInTransaction(TransactionStatus status) {
					FcsBaseResult result = new FcsBaseResult();
					try {
						ProjectRecoveryDO recoveryDO = projectRecoveryDAO.findById(order.getId());
						if (ProjectRecoveryStatusEnum.CLOSEING.code().equals(
							recoveryDO.getRecoveryStatus())) {
							
							recoveryDO.setRecoveryStatus(ProjectRecoveryStatusEnum.CLOSED.code());
							recoveryDO.setEstimateLoseAmount(order.getEstimateLoseAmount());
							recoveryDO.setApportionLoseAmount(order.getApportionLoseAmount());
							recoveryDO.setLoseCognizanceAmount(order.getLoseCognizanceAmount());
							recoveryDO.setStatusUpdateTime(getSysdate());
							projectRecoveryDAO.update(recoveryDO);
							// 添加任务 项目关闭
							ProjectDO projectDO = projectDAO.findByProjectCode(recoveryDO
								.getProjectCode());
							// 维护项目阶段和项目状态为完成
							if (!ProjectStatusEnum.RECOVERY.code().equals(projectDO.getStatus())
								|| !ProjectPhasesEnum.RECOVERY_PHASES.code().equals(
									projectDO.getPhases())) {
								// 不在追偿状态的不允许关闭追偿
								throw ExceptionFactory.newFcsException(
									FcsResultEnum.WRONG_REQ_PARAM, "该项目不允许关闭追偿！");
								
							}
							projectDO.setStatus(ProjectStatusEnum.FINISH.code());
							projectDO.setPhases(ProjectPhasesEnum.FINISH_PHASES.code());
							projectDAO.update(projectDO);
							
							//  添加任务，资产标记为已抵债资产
							//							PledgeAssetService
							//							PledgeAssetService接口下的 assetRelationProject方法  用来关联资产项目信息
							//							assetsStatus 资产状态 用assetId进行状态更定操作
							List<ProjectRecoveryDebtorReorganizationPledgeDO> pledges = projectRecoveryDebtorReorganizationPledgeDAO
								.findByRecoveryId(order.getId());
							if (ListUtil.isNotEmpty(pledges)) {
								AssetRelationProjectBindOrder bindOrder = new AssetRelationProjectBindOrder();
								bindOrder.setProjectCode(projectDO.getProjectCode());
								bindOrder.setProjectName(projectDO.getProjectName());
								bindOrder.setCustomerId(projectDO.getCustomerId());
								bindOrder.setCustomerName(projectDO.getCustomerName());
								bindOrder.setBusiType(projectDO.getBusiType());
								bindOrder.setBusiTypeName(projectDO.getBusiTypeName());
								bindOrder.setDelOld(false);
								List<AssetStatusOrder> assetList = new ArrayList<AssetStatusOrder>();
								List<String> assetIds = new ArrayList<String>();
								for (ProjectRecoveryDebtorReorganizationPledgeDO pledgeDO : pledges) {
									AssetStatusOrder assetStatusOrder = new AssetStatusOrder();
									if (StringUtil.isNotBlank(pledgeDO.getAssetId())) {
										// 去重复
										if (assetIds.contains(pledgeDO.getAssetId())) {
											continue;
										}
										assetIds.add(pledgeDO.getAssetId());
										assetStatusOrder.setAssetId(new Long(pledgeDO.getAssetId()));
										
										assetStatusOrder.setStatus(AssetStatusEnum.DEBT_ASSET);
										assetList.add(assetStatusOrder);
									}
								}
								
								if (ListUtil.isNotEmpty(assetList)) {
									bindOrder.setAssetList(assetList);
									logger.info("入参order:" + bindOrder);
									FcsBaseResult assetResult = pledgeAssetServiceClient
										.assetRelationProject(bindOrder);
									if (!assetResult.isSuccess()) {
										// 更新资产失败报错
										throw ExceptionFactory.newFcsException(
											FcsResultEnum.CALL_REMOTE_SERVICE_ERROR,
											"更新资产信息报错！" + assetResult.getMessage());
									}
								}
							}
							
							result.setSuccess(true);
						} else {
							result.setSuccess(false);
							result.setFcsResultEnum(FcsResultEnum.NO_ACCESS);
							result.setMessage("该追偿尚未处于可关闭的状态！");
						}
					} catch (Exception e) {
						setDbException(status, result, e);
					}
					return result;
				}
			});
			
		} catch (DataAccessException e) {
			
			logger.error(e.getMessage(), e);
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
			result.setMessage(e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setMessage(e.getMessage());
		}
		return result;
	}
	
	@Override
	public ProjectRecoveryResult queryNoticeLetter(Long recoveryId) {
		logger.info("进入ProjectRecoveryServiceImpl的queryNoticeLetter方法，入参：" + recoveryId);
		ProjectRecoveryResult result = new ProjectRecoveryResult();
		ProjectRecoveryInfo recoveryInfo = new ProjectRecoveryInfo();
		if (recoveryId == null || recoveryId <= 0) {
			result.setFcsResultEnum(FcsResultEnum.ILLEGAL_SIGN);
			result.setMessage("请求参数不能为空！");
			logger.error("请求参数不能为空！");
			return result;
		}
		try {
			ProjectRecoveryDO recoveryDO = projectRecoveryDAO.findById(recoveryId);
			MiscUtil.copyPoObject(recoveryInfo, recoveryDO);
			recoveryInfo.setRecoveryStatus(ProjectRecoveryStatusEnum.getByCode(recoveryDO
				.getRecoveryStatus()));
			recoveryInfo.setLitigationOn(BooleanEnum.getByCode(recoveryDO.getLitigationOn()));
			// 再抓取图片信息
			// 将图片信息设置为 map<string[枚举code],list<info>>的格式 
			//Map<String, List<CommonAttachmentInfo>> maps = getAttachMap(recoveryId);
			
			// 填充主表
			recoveryInfo.setDebtorReorganizationOn(BooleanEnum.getByCode(recoveryDO
				.getDebtorReorganizationOn()));
			List<ProjectRecoveryNoticeLetterDO> letterDOs = projectRecoveryNoticeLetterDAO
				.findByRecoveryId(recoveryId);
			List<ProjectRecoveryNoticeLetterInfo> infos = new ArrayList<ProjectRecoveryNoticeLetterInfo>();
			if (letterDOs != null) {
				for (ProjectRecoveryNoticeLetterDO letterDO : letterDOs) {
					ProjectRecoveryNoticeLetterInfo info = new ProjectRecoveryNoticeLetterInfo();
					convertLetterDO2Info(letterDO, info);
					infos.add(info);
				}
			}
			recoveryInfo.setNoticeLetters(infos);
			recoveryInfo.setId(recoveryId);
			result.setProjectRecoveryInfo(recoveryInfo);
			result.setSuccess(true);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
			result.setMessage(e.getMessage());
		}
		
		return result;
	}
	
	private void convertLetterDO2Info(ProjectRecoveryNoticeLetterDO letterDO,
										ProjectRecoveryNoticeLetterInfo info) {
		MiscUtil.copyPoObject(info, letterDO);
		info.setLetterType(ProjectRecoveryLetterTypeEnum.getByCode(letterDO.getLetterType()));
		info.setLetterStatus(ProjectRecoveryLetterStatusEnum.getByCode(letterDO.getLetterStatus()));
	}
	
	@Override
	public ProjectRecoveryResult saveNoticeLetter(ProjectRecoveryOrder order) {
		
		ProjectRecoveryResult result = new ProjectRecoveryResult();
		List<ProjectRecoveryNoticeLetterInfo> infos = order.getNoticeLetters();
		ProjectRecoveryInfo projectRecoveryInfo = new ProjectRecoveryInfo();
		
		logger.info("进入ProjectRecoveryServiceImpl的saveNoticeLetter方法，入参：" + order);
		try {
			if (order == null || order.getId() == null || order.getId() <= 0) {
				result.setFcsResultEnum(FcsResultEnum.INCOMPLETE_REQ_PARAM);
				result.setMessage("入参不能小于0！");
				logger.error("入参不能小于0！");
				return result;
			}
			Long recoveryId = order.getId();
			
			for (ProjectRecoveryNoticeLetterInfo info : infos) {
				// 添加判断，修改或删除，判断2，已用印的不允许修改
				Long letterId = info.getId();
				if (letterId > 0) {
					//  修改
					ProjectRecoveryNoticeLetterDO letterDO = projectRecoveryNoticeLetterDAO
						.findById(letterId);
					// 追偿id不相同的，不允许修改
					Long oldRevoveryId = letterDO.getProjectRecoveryId();
					if (recoveryId != oldRevoveryId) {
						result.setFcsResultEnum(FcsResultEnum.NO_ACCESS);
						result.setMessage("入参不正确，数据不正确！");
						logger.error("入参不正确，数据不正确！");
						return result;
					}
					
					// 已申请用印的，不保存修改内容
					if (!ProjectRecoveryLetterStatusEnum.USE_SIGNET.code().equals(
						letterDO.getLetterStatus())) {
						MiscUtil.copyPoObject(letterDO, info);
						if (info.getLetterStatus() != null) {
							letterDO.setLetterStatus(info.getLetterStatus().code());
						}
						if (info.getLetterType() != null) {
							letterDO.setLetterType(info.getLetterType().code());
						}
						letterDO.setProjectRecoveryId(recoveryId);
						projectRecoveryNoticeLetterDAO.update(letterDO);
						
					}
				} else {
					// 新增
					ProjectRecoveryNoticeLetterDO letterDO = new ProjectRecoveryNoticeLetterDO();
					MiscUtil.copyPoObject(letterDO, info);
					if (info.getLetterStatus() != null) {
						letterDO.setLetterStatus(info.getLetterStatus().code());
					}
					if (info.getLetterType() != null) {
						letterDO.setLetterType(info.getLetterType().code());
					} else {
						letterDO.setLetterType(ProjectRecoveryLetterStatusEnum.NOTMAL.code());
					}
					letterDO.setProjectRecoveryId(recoveryId);
					letterId = projectRecoveryNoticeLetterDAO.insert(letterDO);
					info.setId(letterId);
				}
				
			}
			projectRecoveryInfo.setNoticeLetters(infos);
			result.setProjectRecoveryInfo(projectRecoveryInfo);
			result.setSuccess(true);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
			result.setMessage(e.getMessage());
		}
		return result;
	}
	
	@Override
	public FcsBaseResult changeBeforeTrailNotice(ProjectRecoveryLitigationBeforeTrailOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		logger.info("进入ProjectRecoveryServiceImpl的changeBeforeTrailNotice方法，入参：" + order);
		try {
			if (order == null || order.getId() == null || order.getId() <= 0) {
				result.setFcsResultEnum(FcsResultEnum.INCOMPLETE_REQ_PARAM);
				result.setMessage("入参不能小于0！");
				logger.error("入参不能小于0！");
				return result;
			}
			ProjectRecoveryLitigationBeforeTrailDO infoDO = new ProjectRecoveryLitigationBeforeTrailDO();
			infoDO.setId(order.getId());
			infoDO.setEndNotice(order.getEndNotice().code());
			projectRecoveryLitigationBeforeTrailDAO.updateEndNotice(infoDO);
			result.setSuccess(true);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
			result.setMessage(e.getMessage());
		}
		return result;
	}
	
	@Override
	public FcsBaseResult changePreservationPrecautionNotice(ProjectRecoveryLitigationBeforePreservationPrecautionOrder order) {
		FcsBaseResult result = new FcsBaseResult();
		logger
			.info("进入ProjectRecoveryServiceImpl的changePreservationPrecautionNotice方法，入参：" + order);
		try {
			if (order == null || order.getId() == null || order.getId() <= 0) {
				result.setFcsResultEnum(FcsResultEnum.INCOMPLETE_REQ_PARAM);
				result.setMessage("入参不能小于0！");
				logger.error("入参不能小于0！");
				return result;
			}
			ProjectRecoveryLitigationBeforePreservationPrecautionDO infoDO = new ProjectRecoveryLitigationBeforePreservationPrecautionDO();
			infoDO.setId(order.getId());
			// 判定是停止通知还是 30天的时候发起通知
			if (BooleanEnum.NO == order.getEndNotice()) {
				// 代表30天时重新发起
				// 设置时间，开始结束时间分别是今天往前推30天的0点和24点
				Calendar cal = Calendar.getInstance();
				cal.setTime(getSysdate());
				cal.add(Calendar.DAY_OF_YEAR, 30);
				Date thirdtyDaysBrfore = cal.getTime();
				Date startDate = DateUtil.getStartTimeOfTheDate(thirdtyDaysBrfore);
				Date endDate = DateUtil.getEndTimeOfTheDate(thirdtyDaysBrfore);
				projectRecoveryLitigationBeforePreservationPrecautionDAO.updateEndNoticeLimit(
					startDate, endDate);
				result.setSuccess(true);
			} else if (BooleanEnum.YES == order.getEndNotice()) {
				// 代表停止通知
				infoDO.setEndNotice(BooleanEnum.YES.code());
				projectRecoveryLitigationBeforePreservationPrecautionDAO.updateEndNotice(infoDO);
				result.setSuccess(true);
			} else {
				result.setFcsResultEnum(FcsResultEnum.INCOMPLETE_REQ_PARAM);
				result.setMessage("停止通知条件不能为空！");
				logger.error("停止通知条件不能为空！");
				return result;
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
			result.setMessage(e.getMessage());
		}
		return result;
	}
}
