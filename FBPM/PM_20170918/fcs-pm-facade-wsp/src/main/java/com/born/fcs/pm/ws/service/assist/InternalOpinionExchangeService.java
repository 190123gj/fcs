package com.born.fcs.pm.ws.service.assist;

import java.util.List;

import javax.jws.WebService;

import com.born.fcs.pm.ws.info.assist.FInternalOpinionExchangeInfo;
import com.born.fcs.pm.ws.info.assist.FInternalOpinionExchangeUserInfo;
import com.born.fcs.pm.ws.info.assist.InternalOpinionExchangeFormInfo;
import com.born.fcs.pm.ws.order.assist.FInternalOpinionExchangeOrder;
import com.born.fcs.pm.ws.order.assist.InternalOpinionExchangeQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 内审意见交换Service
 *
 *
 * @author wuzj
 *
 */
@WebService
public interface InternalOpinionExchangeService {
	
	/**
	 * 保存内审意见
	 * @param order
	 * @return
	 */
	FormBaseResult save(FInternalOpinionExchangeOrder order);
	
	/**
	 * 检查部门人员
	 * @param order
	 * @return
	 */
	FcsBaseResult checkDeptUser(FInternalOpinionExchangeOrder order);
	
	/**
	 * 查询内审意见表单
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<InternalOpinionExchangeFormInfo> searchForm(	InternalOpinionExchangeQueryOrder order);
	
	/**
	 * 根据表单ID查询内审意见表单
	 * @param formId
	 * @return
	 */
	FInternalOpinionExchangeInfo findByFormId(long formId);
	
	/***
	 * 根据表单ID查询审计人员
	 * @param formId
	 * @return
	 */
	List<FInternalOpinionExchangeUserInfo> findUserByFormId(long formId);
}
