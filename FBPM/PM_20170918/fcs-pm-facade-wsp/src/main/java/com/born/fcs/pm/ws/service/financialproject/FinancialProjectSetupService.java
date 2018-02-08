package com.born.fcs.pm.ws.service.financialproject;

import javax.jws.WebService;

import com.born.fcs.pm.ws.info.financialproject.FProjectFinancialInfo;
import com.born.fcs.pm.ws.info.financialproject.FinancialProjectSetupFormInfo;
import com.born.fcs.pm.ws.order.financialproject.FProjectFinancialOrder;
import com.born.fcs.pm.ws.order.financialproject.FinancialProjectQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

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
	FormBaseResult save(FProjectFinancialOrder order);
	
	/**
	 * 查询理财类项目信息
	 * @param formId
	 * @return
	 */
	FProjectFinancialInfo queryByFormId(long formId);
	
	/**
	 * 根据编号查询理财类项目信息
	 * @param formId
	 * @return
	 */
	FProjectFinancialInfo queryByProjectCode(String projectCode);
	
	/**
	 * 同步预测数据到资金系统 拟购买时间”取立项申请单中的“预计申购日”，预测金额计算公式：购买份数*购买单价；资金方向：流出
	 */
	FcsBaseResult syncForecast(String projectCode);
}
