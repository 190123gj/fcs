package com.born.fcs.pm.ws.service.financialproject;

import javax.jws.WebService;

import com.born.fcs.pm.ws.info.financialproject.FProjectFinancialContractInfo;
import com.born.fcs.pm.ws.info.financialproject.FinancialProjectContractFormInfo;
import com.born.fcs.pm.ws.order.financialproject.ProjectFinancialContractFormQueryOrder;
import com.born.fcs.pm.ws.order.financialproject.ProjectFinancialContractOrder;
import com.born.fcs.pm.ws.order.financialproject.ProjectFinancialContractStatusOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 理财项目合同审service
 *
 *
 * @author wuzj
 *
 */
@WebService
public interface FinancialProjectContractService {
	
	/**
	 * 查询合同表单列表
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<FinancialProjectContractFormInfo> queryPage(	ProjectFinancialContractFormQueryOrder order);
	
	/**
	 * 根据表单ID查询合同申请单
	 * @param formId
	 * @return
	 */
	FProjectFinancialContractInfo queryByFormId(long formId);
	
	/**
	 * 保存合同申请单
	 * @param order
	 * @return
	 */
	FormBaseResult save(ProjectFinancialContractOrder order);
	
	/**
	 * 修改合同状态
	 * @param order
	 * @return
	 */
	FcsBaseResult changeStatus(ProjectFinancialContractStatusOrder order);
	
}
