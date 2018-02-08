package com.born.fcs.fm.ws.service.payment;

import java.util.Date;

import javax.jws.WebService;

import com.born.fcs.fm.ws.info.payment.BudgetInfo;
import com.born.fcs.fm.ws.order.payment.BudgetOrder;
import com.born.fcs.fm.ws.order.payment.BudgetQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.util.money.Money;

/**
 * 预算Service
 * 
 * @author lzb
 * 
 */
@WebService
public interface BudgetService {
	
	/**
	 * 查询预算列表
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<BudgetInfo> queryPage(BudgetQueryOrder order);
	
	
	/**
	 * 根据预算ID查询
	 * @param budgetId
	 * @return
	 */
	BudgetInfo queryById(long budgetId);
	
	/**
	 * 保存预算
	 * @param order
	 * @return
	 */
	FcsBaseResult save(BudgetOrder order);
	
	/**
	 * 查询部门费用预算余额
	 * @param deptId
	 * @param categoryId
	 * @param applyTime
	 * @return 未设置预算或预算余额处该部门为不控制且isNull=ture,返回null
	 */
	Money queryBalanceByDeptCategoryId(long deptId, long categoryId, Date applyTime, boolean isNull);
}
