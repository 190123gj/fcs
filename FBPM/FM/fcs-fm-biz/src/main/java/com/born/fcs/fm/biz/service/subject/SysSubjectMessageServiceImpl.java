/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午5:24:45 创建
 */
package com.born.fcs.fm.biz.service.subject;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import rop.thirdparty.com.google.common.collect.Lists;

import com.born.fcs.fm.biz.exception.ExceptionFactory;
import com.born.fcs.fm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.fm.dal.dataobject.SysSubjectMessageDO;
import com.born.fcs.fm.ws.enums.SubjectCostTypeEnum;
import com.born.fcs.fm.ws.enums.SubjectTypeEnum;
import com.born.fcs.fm.ws.info.subject.SysSubjectMessageInfo;
import com.born.fcs.fm.ws.order.subject.SysSubjectMessageBatchOrder;
import com.born.fcs.fm.ws.order.subject.SysSubjectMessageOrder;
import com.born.fcs.fm.ws.result.subject.SysSubjectMessageResult;
import com.born.fcs.fm.ws.service.subject.SysSubjectMessageService;
import com.born.fcs.pm.util.MiscUtil;
import com.born.fcs.pm.ws.base.PageComponent;
import com.born.fcs.pm.ws.enums.base.FcsResultEnum;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.beans.cglib.BeanCopier;
import com.yjf.common.lang.util.ListUtil;

/**
 * 
 * 
 * @author hjiajie
 * 
 */
@Service("sysSubjectMessageService")
public class SysSubjectMessageServiceImpl extends BaseAutowiredDomainService implements
																			SysSubjectMessageService {
	
	/**
	 * @param id
	 * @return
	 * @see com.born.fcs.fm.ws.service.subject.SysSubjectMessageService#findById(java.lang.Long)
	 */
	@Override
	public SysSubjectMessageResult findById(Long id) {
		logger.info("进入SysSubjectMessageServiceImpl的findById方法，入参：" + id);
		SysSubjectMessageResult result = new SysSubjectMessageResult();
		if (id <= 0) {
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.INCOMPLETE_REQ_PARAM);
			logger.error("主键不能为空！");
			return result;
		}
		try {
			SysSubjectMessageDO subjectMDO = sysSubjectMessageDAO.findById(id);
			SysSubjectMessageInfo info = new SysSubjectMessageInfo();
			convertDO2Info(subjectMDO, info);
			result.setSubjectInfo(info);
			result.setSuccess(true);
		} catch (Exception e) {
			logger.error("查询默认科目信息失败，原因:" + e.getMessage(), e);
			result.setSuccess(false);
		}
		return result;
	}
	
	private void convertDO2Info(SysSubjectMessageDO subjectMDO, SysSubjectMessageInfo info) {
		MiscUtil.copyPoObject(info, subjectMDO);
		info.setSubjectType(SubjectTypeEnum.getByCode(subjectMDO.getSubjectType()));
		info.setSubjectCostType(SubjectCostTypeEnum.getByCode(subjectMDO.getSubjectCostType()));
	}
	
	/**
	 * @param order
	 * @return
	 * @see com.born.fcs.fm.ws.service.subject.SysSubjectMessageService#update(com.born.fcs.fm.ws.order.subject.SysSubjectMessageOrder)
	 */
	@Override
	public FcsBaseResult update(SysSubjectMessageOrder order) {
		logger.info("进入SysSubjectMessageServiceImpl的update方法，入参：" + order);
		FcsBaseResult result = new FcsBaseResult();
		try {
			if (order == null || order.getId() <= 0) {
				result.setSuccess(false);
				result.setFcsResultEnum(FcsResultEnum.INCOMPLETE_REQ_PARAM);
				logger.error("修改时主键不能为空！");
				return result;
			}
			SysSubjectMessageDO subjectDO = new SysSubjectMessageDO();
			convertOrder2DO(order, subjectDO);
			sysSubjectMessageDAO.update(subjectDO);
			
			result.setSuccess(true);
		} catch (Exception e) {
			logger.error("更新默认科目信息失败，原因:" + e.getMessage(), e);
			result.setSuccess(false);
		}
		return result;
	}
	
	private void convertOrder2DO(SysSubjectMessageOrder order, SysSubjectMessageDO subjectDO) {
		MiscUtil.copyPoObject(subjectDO, order);
		if (order.getSubjectType() != null) {
			subjectDO.setSubjectType(order.getSubjectType().code());
		}
		if (order.getSubjectCostType() != null) {
			subjectDO.setSubjectCostType(order.getSubjectCostType().code());
		}
	}
	
	/**
	 * @param order
	 * @return
	 * @see com.born.fcs.fm.ws.service.subject.SysSubjectMessageService#querySubject(com.born.fcs.fm.ws.order.subject.SysSubjectMessageOrder)
	 */
	@Override
	public QueryBaseBatchResult<SysSubjectMessageInfo> querySubject(SysSubjectMessageOrder order) {
		logger.info("进入SysSubjectMessageServiceImpl的querySubject方法，入参：" + order);
		QueryBaseBatchResult<SysSubjectMessageInfo> result = new QueryBaseBatchResult<SysSubjectMessageInfo>();
		
		try {
			SysSubjectMessageDO queryDO = new SysSubjectMessageDO();
			BeanCopier.staticCopy(order, queryDO);
			if (order.getSubjectType() != null) {
				queryDO.setSubjectType(order.getSubjectType().code());
			}
			if (order.getSubjectCostType() != null) {
				queryDO.setSubjectCostType(order.getSubjectCostType().code());
			}
			
			long totalCount = 0;
			// 查询totalcount
			totalCount = sysSubjectMessageDAO.findByConditionCount(queryDO);
			
			PageComponent component = new PageComponent(order, totalCount);
			// 查询list
			List<SysSubjectMessageDO> list = Lists.newArrayList();
			list = sysSubjectMessageDAO.findByCondition(queryDO, component.getFirstRecord(),
				component.getPageSize());
			
			List<SysSubjectMessageInfo> pageList = new ArrayList<SysSubjectMessageInfo>();
			for (SysSubjectMessageDO item : list) {
				SysSubjectMessageInfo info = new SysSubjectMessageInfo();
				convertDO2Info(item, info);
				
				pageList.add(info);
			}
			result.setSuccess(true);
			result.setPageList(pageList);
			result.initPageParam(component);
		} catch (Exception e) {
			logger.error("查询默认科目信息失败" + e.getMessage(), e);
			result.setSuccess(false);
			result.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
		}
		return result;
	}
	
	/**
	 * @param order
	 * @return
	 * @see com.born.fcs.fm.ws.service.subject.SysSubjectMessageService#update(com.born.fcs.fm.ws.order.subject.SysSubjectMessageBatchOrder)
	 */
	@Override
	public FcsBaseResult updateAll(final SysSubjectMessageBatchOrder order) {
		logger.info("进入SysSubjectMessageServiceImpl的update方法，入参：" + order);
		FcsBaseResult result = new FcsBaseResult();
		try {
			if (order == null || ListUtil.isEmpty(order.getSubjectOrders())) {
				result.setSuccess(false);
				result.setFcsResultEnum(FcsResultEnum.INCOMPLETE_REQ_PARAM);
				logger.error("修改时主键不能为空！");
				return result;
			}
			result = transactionTemplate.execute(new TransactionCallback<FcsBaseResult>() {
				
				@Override
				public FcsBaseResult doInTransaction(TransactionStatus status) {
					FcsBaseResult result = new FcsBaseResult();
					try {
						for (SysSubjectMessageOrder sysOrder : order.getSubjectOrders()) {
							FcsBaseResult sysResult = update(sysOrder);
							if (!sysResult.isExecuted() || !sysResult.isSuccess()) {
								throw ExceptionFactory.newFcsException(
									FcsResultEnum.EXECUTE_FAILURE,
									"更新失败，原因：" + sysResult.getMessage());
							}
						}
						result.setSuccess(true);
					} catch (Exception e) {
						setDbException(status, result, e);
						logger.error("更新默认科目信息失败，原因:" + e.getMessage(), e);
					}
					return result;
				}
			});
		} catch (Exception e) {
			logger.error("更新默认科目信息失败，原因:" + e.getMessage(), e);
			result.setSuccess(false);
		}
		return result;
	}
	
}
