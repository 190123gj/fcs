package com.born.fcs.pm.ws.service.financialproject;

import javax.jws.WebService;

import com.born.fcs.pm.ws.info.financialproject.FProjectFinancialTansferApplyInfo;
import com.born.fcs.pm.ws.info.financialproject.FinancialProjectTransferFormInfo;
import com.born.fcs.pm.ws.info.financialproject.ProjectFinancialTradeTansferInfo;
import com.born.fcs.pm.ws.order.financialproject.FProjectFinancialTansferApplyOrder;
import com.born.fcs.pm.ws.order.financialproject.FinancialProjectTransferFormQueryOrder;
import com.born.fcs.pm.ws.order.financialproject.ProjectFinancialTradeTansferOrder;
import com.born.fcs.pm.ws.order.financialproject.ProjectFinancialTradeTansferQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 理财项目转让service
 *
 *
 * @author wuzj
 *
 */
@WebService
public interface FinancialProjectTransferService {
	
	/**
	 * 查询转让表单列表
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<FinancialProjectTransferFormInfo> queryPage(	FinancialProjectTransferFormQueryOrder order);
	
	/**
	 * 查询转让交易列表
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<ProjectFinancialTradeTansferInfo> queryTrade(	ProjectFinancialTradeTansferQueryOrder order);
	
	/**
	 * 根据表单ID查询转让申请单
	 * @param formId
	 * @return
	 */
	FProjectFinancialTansferApplyInfo queryApplyByFormId(long formId);
	
	/**
	 * 根据申请单ID查询转让交易
	 * @param applyId
	 * @return
	 */
	ProjectFinancialTradeTansferInfo queryTradeByApplyId(long applyId);
	
	/**
	 * 保存转让申请单
	 * @param order
	 * @return
	 */
	FormBaseResult save(FProjectFinancialTansferApplyOrder order);
	
	/**
	 * 转让信息维护
	 * @param order
	 * @return
	 */
	FcsBaseResult saveTrade(ProjectFinancialTradeTansferOrder order);
	
	/***
	 * 同步到资金系统预测
	 * @param tradeId
	 * @return
	 */
	FcsBaseResult syncForecast(long applyId);
}
