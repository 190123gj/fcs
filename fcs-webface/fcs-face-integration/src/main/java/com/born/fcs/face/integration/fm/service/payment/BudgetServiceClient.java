package com.born.fcs.face.integration.fm.service.payment;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.fm.ws.info.payment.BudgetInfo;
import com.born.fcs.fm.ws.order.payment.BudgetOrder;
import com.born.fcs.fm.ws.order.payment.BudgetQueryOrder;
import com.born.fcs.fm.ws.service.payment.BudgetService;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.util.money.Money;

/**
 * 预算管理客户端
 * @author lzb
 * 
 */
@Service("budgetServiceClient")
public class BudgetServiceClient extends ClientAutowiredBaseService implements BudgetService {
	
	@Override
	public QueryBaseBatchResult<BudgetInfo> queryPage(final BudgetQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<BudgetInfo>>() {
			@Override
			public QueryBaseBatchResult<BudgetInfo> call() {
				return budgetService.queryPage(order);
			}
		});
	}
	
	@Override
	public BudgetInfo queryById(final long budgetId) {
		return callInterface(new CallExternalInterface<BudgetInfo>() {
			@Override
			public BudgetInfo call() {
				return budgetService.queryById(budgetId);
			}
		});
	}
	
	@Override
	public FcsBaseResult save(final BudgetOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			@Override
			public FcsBaseResult call() {
				return budgetService.save(order);
			}
		});
	}
	
	@Override
	public Money queryBalanceByDeptCategoryId(final long deptId, final long categoryId,
												final Date applyTime,final boolean isNull) {
		return callInterface(new CallExternalInterface<Money>() {
			@Override
			public Money call() {
				return budgetService.queryBalanceByDeptCategoryId(deptId, categoryId, applyTime, isNull);
			}
		});
	}
	
}
