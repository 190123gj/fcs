package com.born.fcs.pm.ws.service.financialproject;

import javax.jws.WebService;

import com.born.fcs.pm.ws.info.financialproject.FProjectFinancialInfo;
import com.born.fcs.pm.ws.info.financialproject.FinancialProjectSetupFormInfo;
import com.born.fcs.pm.ws.order.financialproject.FProjectFinancialOrder;
import com.born.fcs.pm.ws.order.financialproject.FinancialProjectQueryOrder;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.financialproject.FinancialProjectSetupResult;

@WebService
public interface FinancialProjectSetupService {
	
	/**
	 * 查询理财项目立项申请单
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<FinancialProjectSetupFormInfo> query(FinancialProjectQueryOrder order);
	
	/**
	 * 保存理财项目信息
	 * @param order
	 * @return
	 */
	FinancialProjectSetupResult save(FProjectFinancialOrder order);
	
	/**
	 * 查询理财类项目信息
	 * @param formId
	 * @return
	 */
	FProjectFinancialInfo queryByFormId(long formId);
	
	/**
	 * 查询理财类项目信息
	 * @param formId
	 * @return
	 */
	FProjectFinancialInfo queryByProductId(long productId);
	
}
