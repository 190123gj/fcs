///**
// * www.yiji.com Inc.
// * Copyright (c) 2011 All Rights Reserved.
// */
//package com.born.fcs.rm.biz.service.common;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.born.fcs.rm.biz.service.convert.OperationJournalConvertor;
//import com.born.fcs.rm.ws.service.common.OperationJournalService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataAccessException;
//import org.springframework.stereotype.Service;
//
//
//import com.born.fcs.rm.biz.service.base.BaseAutowiredDAOService;
//import com.born.fcs.pm.daointerface.OperationJournalDAO;
//import com.born.fcs.pm.dataobject.OperationJournalDO;
//import com.born.fcs.rm.ws.base.PageComponent;
//import com.born.fcs.rm.ws.enums.base.FcsResultEnum;
//import com.born.fcs.rm.ws.info.common.OperationJournalInfo;
//import com.born.fcs.rm.ws.order.common.OperationJournalAddOrder;
//import com.born.fcs.rm.ws.order.common.OperationJournalQueryOrder;
//import com.born.fcs.rm.ws.result.base.QueryBaseBatchResult;
//import com.born.fcs.rm.ws.result.common.OperationJournalServiceResult;
//import com.yjf.common.lang.util.ListUtil;
//
///**
// *
// * @Filename OperationJournalServiceImpl.java
// *
// * @Description
// *
// * @Version 1.0
// *
// * @Author jiajie
// *
// * @Email hjiajie@yiji.com
// *
// * @History <li>Author: jiajie</li> <li>Date: 2013-1-8</li> <li>Version: 1.0</li>
// * <li>Content: create</li>
// *
// */
//@Service("operationJournalService")
//public class OperationJournalServiceImpl extends BaseAutowiredDAOService implements
//		OperationJournalService {
//	private static final Logger logger = LoggerFactory.getLogger(OperationJournalServiceImpl.class);
//	private static int MAXLENTH = 1000;
//
//	@Autowired
//	OperationJournalDAO operationJournalDAO;
//
//	/**
//	 * @param operationJournalAddOrder
//	 * @return
//	 * @see com.yjf.bops.facade.operationJournal.service.api.OperationJournalService#addOperationJournalInfo(com.yjf.bops.facade.operationJournal.service.order.OperationJournalAddOrder)
//	 */
//	@Override
//	public OperationJournalServiceResult addOperationJournalInfo(	OperationJournalAddOrder operationJournalAddOrder) {
//		logger.info("进入OperationJournalServiceImpl的addOperationJournalInfo方法!添加一条日志信息,入参: "
//					+ operationJournalAddOrder);
//		OperationJournalServiceResult operationJournalServiceResult = new OperationJournalServiceResult();
//		try {
//			//参数校验
//			operationJournalAddOrder.check();
//			// 参数设置
//			OperationJournalDO operationJournalDO = new OperationJournalDO();
//			OperationJournalConvertor.convert(operationJournalAddOrder, operationJournalDO);
//			operationJournalDO.setRawAddTime(getSysdate());
//			// 插入
//
//			if(operationJournalDO.getMemo()!=null){
//				String memo = operationJournalDO.getMemo();
//				if(memo.length()>MAXLENTH){
//					memo=memo.substring(0, MAXLENTH);
//				}
//				operationJournalDO.setMemo(memo);
//			}
//
//			long identity = operationJournalDAO.insert(operationJournalDO);
//			// 设置返回结果
//			operationJournalDO.setIdentity(identity);
//			OperationJournalInfo operationJournalInfo = new OperationJournalInfo();
//			OperationJournalConvertor.convert(operationJournalDO, operationJournalInfo);
//			operationJournalServiceResult.setOperationJournalInfo(operationJournalInfo);
//			setResultInfo(operationJournalServiceResult, "添加一条操作日志信息成功!",
//				FcsResultEnum.EXECUTE_SUCCESS, true);
//			logger.info("添加一条操作日志信息成功!resultCode=[ "
//						+ operationJournalServiceResult.getFcsResultEnum() + " ]");
//		} catch (IllegalArgumentException e) {
//			setResultInfo(operationJournalServiceResult, e.getMessage(),
//				FcsResultEnum.INCOMPLETE_REQ_PARAM, false);
//			logger.error("添加一条操作日志信息失败!返回结果:[" + operationJournalServiceResult + "]原因：{}",
//				e.getMessage());
//		} catch (DataAccessException e) {
//			setResultInfo(operationJournalServiceResult,
//				FcsResultEnum.DATABASE_EXCEPTION.getMessage(), FcsResultEnum.DATABASE_EXCEPTION,
//				false);
//			logger.error("添加一条操作日志信息失败!返回结果:[" + operationJournalServiceResult + "]原因：", e);
//		} catch (Exception e) {
//			setResultInfo(operationJournalServiceResult,
//				FcsResultEnum.UN_KNOWN_EXCEPTION.getMessage(), FcsResultEnum.UN_KNOWN_EXCEPTION,
//				false);
//			logger.error("添加一条操作日志信息失败!返回结果:[" + operationJournalServiceResult + "]原因：", e);
//		}
//		return operationJournalServiceResult;
//	}
//
//	protected void setResultInfo(OperationJournalServiceResult operationJournalResultBase,
//									String message, FcsResultEnum resultCode, boolean success) {
//		if (operationJournalResultBase != null) {
//			operationJournalResultBase.setMessage(message);
//			operationJournalResultBase.setFcsResultEnum(resultCode);
//			operationJournalResultBase.setSuccess(success);
//		}
//	}
//
//	@Override
//	public QueryBaseBatchResult<OperationJournalInfo> queryOperationJournalInfo(OperationJournalQueryOrder operationJournalQueryOrder) {
//		QueryBaseBatchResult<OperationJournalInfo> batchResult = new QueryBaseBatchResult<OperationJournalInfo>();
//		try {
//			operationJournalQueryOrder.check();
//			List<OperationJournalInfo> pageList = new ArrayList<OperationJournalInfo>(
//				(int) operationJournalQueryOrder.getPageSize());
//			OperationJournalDO operationJournalDO = new OperationJournalDO();
//			OperationJournalConvertor.convert(operationJournalQueryOrder, operationJournalDO);
//			long totalCount = operationJournalDAO.queryOperationJournalPageListCount(
//				operationJournalDO, operationJournalQueryOrder.getOperatorTimeStart(),
//				operationJournalQueryOrder.getOperatorTimeEnd());
//			PageComponent component = new PageComponent(operationJournalQueryOrder, totalCount);
//			List<OperationJournalDO> recordList = operationJournalDAO
//				.queryOperationJournalPageList(operationJournalDO,
//					operationJournalQueryOrder.getOperatorTimeStart(),
//					operationJournalQueryOrder.getOperatorTimeEnd(), component.getFirstRecord(),
//					operationJournalQueryOrder.getPageSize());
//			if (ListUtil.isNotEmpty(recordList)) {
//				for (OperationJournalDO item : recordList) {
//					OperationJournalInfo info = new OperationJournalInfo();
//					OperationJournalConvertor.convert(item, info);
//					pageList.add(info);
//				}
//			}
//			batchResult.initPageParam(component);
//			batchResult.setSuccess(true);
//			batchResult.setPageList(pageList);
//		} catch (IllegalArgumentException e) {
//			batchResult.setSuccess(false);
//			batchResult.setFcsResultEnum(FcsResultEnum.INCOMPLETE_REQ_PARAM);
//			batchResult.setMessage(e.getMessage());
//		} catch (Exception e) {
//			batchResult.setSuccess(false);
//			batchResult.setFcsResultEnum(FcsResultEnum.DATABASE_EXCEPTION);
//			logger.error(e.getLocalizedMessage(), e);
//		}
//
//		return batchResult;
//	}
//
//}
