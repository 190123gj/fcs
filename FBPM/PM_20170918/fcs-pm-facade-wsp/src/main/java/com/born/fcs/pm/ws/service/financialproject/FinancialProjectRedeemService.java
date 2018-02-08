package com.born.fcs.pm.ws.service.financialproject;

import javax.jws.WebService;

import com.born.fcs.pm.ws.info.financialproject.FProjectFinancialRedeemApplyInfo;
import com.born.fcs.pm.ws.info.financialproject.FinancialProjectRedeemFormInfo;
import com.born.fcs.pm.ws.info.financialproject.ProjectFinancialTradeRedeemInfo;
import com.born.fcs.pm.ws.order.financialproject.FProjectFinancialRedeemApplyOrder;
import com.born.fcs.pm.ws.order.financialproject.FinancialProjectRedeemFormQueryOrder;
import com.born.fcs.pm.ws.order.financialproject.ProjectFinancialTradeRedeemOrder;
import com.born.fcs.pm.ws.order.financialproject.ProjectFinancialTradeRedeemQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 理财项目赎回Service
 *
 *
 * @author wuzj
 *
 */
@WebService
public interface FinancialProjectRedeemService {
	
	/**
	 * 查询赎回表单列表
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<FinancialProjectRedeemFormInfo> queryPage(	FinancialProjectRedeemFormQueryOrder order);
	
	/**
	 * 查询赎回交易列表
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<ProjectFinancialTradeRedeemInfo> queryTrade(	ProjectFinancialTradeRedeemQueryOrder order);
	
	/**
	 * 根据表单ID查询赎回申请单
	 * @param formId
	 * @return
	 */
	FProjectFinancialRedeemApplyInfo queryApplyByFormId(long formId);
	
	/**
	 * 根据申请单ID查询赎回交易
	 * @param applyId
	 * @return
	 */
	ProjectFinancialTradeRedeemInfo queryTradeByApplyId(long applyId);
	
	/**
	 * 保存赎回申请单
	 * @param order
	 * @return
	 */
	FormBaseResult save(FProjectFinancialRedeemApplyOrder order);
	
	/**
	 * 赎回信息维护
	 * @param order
	 * @return
	 */
	FcsBaseResult saveTrade(ProjectFinancialTradeRedeemOrder order);
}
