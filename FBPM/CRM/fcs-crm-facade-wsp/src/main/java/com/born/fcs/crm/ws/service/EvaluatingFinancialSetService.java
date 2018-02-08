package com.born.fcs.crm.ws.service;

import javax.jws.WebService;

import com.born.fcs.crm.ws.service.order.ListOrder;
import com.born.fcs.crm.ws.service.order.query.FinancialSetQueryOrder;
import com.born.fcs.crm.ws.service.result.FinancialSetResult;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;

/**
 * 一般企业财务指标配置
 * */
@WebService
public interface EvaluatingFinancialSetService {
	
	/** 修改 */
	FcsBaseResult update(ListOrder order);
	
	/**
	 * 查询
	 * @param type 指标类型
	 * @param year 指标年限(暂未使用)
	 * @Param typeChild 指标分类
	 * @Return Map<typeChild,FinancialSetInfo>
	 * */
	FinancialSetResult list(FinancialSetQueryOrder queryOrder);
	
}
