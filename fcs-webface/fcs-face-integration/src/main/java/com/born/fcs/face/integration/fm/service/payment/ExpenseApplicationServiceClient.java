package com.born.fcs.face.integration.fm.service.payment;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.born.fcs.face.integration.service.CallExternalInterface;
import com.born.fcs.face.integration.service.ClientAutowiredBaseService;
import com.born.fcs.fm.ws.info.payment.FormExpenseApplicationDetailAllInfo;
import com.born.fcs.fm.ws.info.payment.FormExpenseApplicationDetailInfo;
import com.born.fcs.fm.ws.info.payment.FormExpenseApplicationInfo;
import com.born.fcs.fm.ws.order.common.UpdateVoucherOrder;
import com.born.fcs.fm.ws.order.payment.ConfirmBranchPayOrder;
import com.born.fcs.fm.ws.order.payment.ExpenseApplicationOrder;
import com.born.fcs.fm.ws.order.payment.ExpenseApplicationQueryOrder;
import com.born.fcs.fm.ws.service.payment.ExpenseApplicationService;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 费用支付申请单客户端
 * @author lzb
 * 
 */
@Service("expenseApplicationServiceClient")
public class ExpenseApplicationServiceClient extends ClientAutowiredBaseService implements
																				ExpenseApplicationService {
	
	@Override
	public QueryBaseBatchResult<FormExpenseApplicationInfo> queryPage(	final ExpenseApplicationQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<FormExpenseApplicationInfo>>() {
			@Override
			public QueryBaseBatchResult<FormExpenseApplicationInfo> call() {
				return expenseApplicationService.queryPage(order);
			}
		});
	}
	
	@Override
	public FormExpenseApplicationInfo queryByFormId(final long formId) {
		return callInterface(new CallExternalInterface<FormExpenseApplicationInfo>() {
			@Override
			public FormExpenseApplicationInfo call() {
				return expenseApplicationService.queryByFormId(formId);
			}
		});
	}
	
	@Override
	public FormExpenseApplicationInfo queryById(final long expenseApplicationId) {
		return callInterface(new CallExternalInterface<FormExpenseApplicationInfo>() {
			@Override
			public FormExpenseApplicationInfo call() {
				return expenseApplicationService.queryById(expenseApplicationId);
			}
		});
	}
	
	@Override
	public FormBaseResult save(final ExpenseApplicationOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			@Override
			public FormBaseResult call() {
				return expenseApplicationService.save(order);
			}
		});
	}
	
	@Override
	public List<FormExpenseApplicationDetailInfo> findSublInfoByDeptCategory(	final long deptId,
																				final long categoryId,
																				final Date applyTimeStart,
																				final Date applyTimeEnd) {
		return callInterface(new CallExternalInterface<List<FormExpenseApplicationDetailInfo>>() {
			@Override
			public List<FormExpenseApplicationDetailInfo> call() {
				return expenseApplicationService.findSublInfoByDeptCategory(deptId, categoryId,
					applyTimeStart, applyTimeEnd);
			}
		});
	}
	
	@Override
	public FormBaseResult updatePayBank(final ExpenseApplicationOrder order) {
		return callInterface(new CallExternalInterface<FormBaseResult>() {
			@Override
			public FormBaseResult call() {
				return expenseApplicationService.updatePayBank(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult syncForecast(final long formId, final boolean afterAudit) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			@Override
			public FcsBaseResult call() {
				return expenseApplicationService.syncForecast(formId, afterAudit);
			}
		});
	}
	
	@Override
	public FcsBaseResult sync2FinancialSys(final FormExpenseApplicationInfo expenseInfo) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			@Override
			public FcsBaseResult call() {
				return expenseApplicationService.sync2FinancialSys(expenseInfo);
			}
		});
	}
	
	@Override
	public FcsBaseResult updateVoucher(final UpdateVoucherOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			@Override
			public FcsBaseResult call() {
				return expenseApplicationService.updateVoucher(order);
			}
		});
	}
	
	@Override
	public FcsBaseResult confirmBranchPay(final ConfirmBranchPayOrder order) {
		return callInterface(new CallExternalInterface<FcsBaseResult>() {
			@Override
			public FcsBaseResult call() {
				return expenseApplicationService.confirmBranchPay(order);
			}
		});
	}
	
	@Override
	public QueryBaseBatchResult<FormExpenseApplicationDetailAllInfo> queryPageAll(	final ExpenseApplicationQueryOrder order) {
		return callInterface(new CallExternalInterface<QueryBaseBatchResult<FormExpenseApplicationDetailAllInfo>>() {
			@Override
			public QueryBaseBatchResult<FormExpenseApplicationDetailAllInfo> call() {
				return expenseApplicationService.queryPageAll(order);
			}
		});
	}
}
