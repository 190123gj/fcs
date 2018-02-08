package com.born.fcs.pm.ws.service.financialproject;

import java.util.Date;

import javax.jws.WebService;

import com.born.fcs.pm.ws.enums.FinancialProjectStatusEnum;
import com.born.fcs.pm.ws.info.financialproject.FinancialProjectWithdrawingInfo;
import com.born.fcs.pm.ws.info.financialproject.ProjectFinancialInfo;
import com.born.fcs.pm.ws.order.financialproject.FinancialProjectOrder;
import com.born.fcs.pm.ws.order.financialproject.FinancialProjectQueryOrder;
import com.born.fcs.pm.ws.order.financialproject.FinancialProjectWithdrawingQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryAmountBatchResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.born.fcs.pm.ws.result.financialproject.FinancialProjectInterestResult;
import com.yjf.common.lang.util.money.Money;

/**
 * 理财项目
 *
 * @author wuzj
 */
@WebService
public interface FinancialProjectService {
	
	/**
	 * 查询理财项目
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<ProjectFinancialInfo> query(FinancialProjectQueryOrder order);
	
	/**
	 * 计提台帐查询
	 * @param order
	 * @return
	 */
	QueryAmountBatchResult<FinancialProjectWithdrawingInfo> queryWithdraw(	FinancialProjectWithdrawingQueryOrder order);
	
	/**
	 * 根据项目编号查询
	 * @param projectCode
	 * @return
	 */
	ProjectFinancialInfo queryByCode(String projectCode);
	
	/**
	 * 项目信息维护
	 * @param order
	 * @return
	 */
	FcsBaseResult confirm(FinancialProjectOrder order);
	
	/**
	 * 修改项目状态
	 * @param projectCode
	 * @param status
	 * @return
	 */
	FcsBaseResult changeStatus(String projectCode, FinancialProjectStatusEnum status);
	
	/**
	 * 计算持有期收益和计提收益
	 * @param project 项目信息
	 * @param calculateDate 计算日期（yyyy-MM-dd）
	 * @return
	 */
	FinancialProjectInterestResult caculateProjectInterest(ProjectFinancialInfo project,
															Date calculateDate);
	
	/**
	 * 计算持有期收益和计提收益
	 * @param projectCode 项目编号
	 * @param calculateDate 计算日期（yyyy-MM-dd）
	 * @return
	 */
	FinancialProjectInterestResult caculateInterest(String projectCode, Date calculateDate);
	
	/**
	 * 计算项目转让收益
	 * @param projectCode 项目编号
	 * @param transferPrice 转让单价
	 * @param transferNum 转让数量
	 * @param transferDate 转让日期（yyyy-MM-dd）
	 * @return
	 */
	Money caculateTransferInterest(String projectCode, Money transferPrice, long transferNum,
									Date transferDate);
	
	/**
	 * 
	 * @param projectCode 项目编号
	 * @param redeemPrice 赎回价格（默认为购买单价 传 null）
	 * @param redeemNum 赎回数量
	 * @param redeemDate 数据时间（yyyy-MM-dd 默认为当前时间）
	 * @return
	 */
	Money caculateRedeemInterest(String projectCode, Money redeemPrice, long redeemNum,
									Date redeemDate);
	
	/**
	 * 计算年华收益率
	 * @param projectCode 项目编号
	 * @param interest 收益
	 * @param principal 本金
	 * @return
	 */
	double caculateInterestRate(String projectCode, Money interest, Money principal);
	
	/**
	 * 转让中的份额
	 * @param applyId 排除的申请单
	 * @return
	 */
	long transferingNum(String projectCode, long applyId);
	
	/**
	 * 赎回中的份额
	 * @param applyId 排除的申请单
	 * @return
	 */
	long redeemingNum(String projectCode, long applyId);
}
