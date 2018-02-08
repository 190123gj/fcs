package com.born.fcs.fm.ws.service.payment;

import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import com.born.fcs.fm.ws.info.payment.FormExpenseApplicationDetailAllInfo;
import com.born.fcs.fm.ws.info.payment.FormExpenseApplicationDetailInfo;
import com.born.fcs.fm.ws.info.payment.FormExpenseApplicationInfo;
import com.born.fcs.fm.ws.order.common.UpdateVoucherOrder;
import com.born.fcs.fm.ws.order.payment.ConfirmBranchPayOrder;
import com.born.fcs.fm.ws.order.payment.ExpenseApplicationOrder;
import com.born.fcs.fm.ws.order.payment.ExpenseApplicationQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 费用支付申请单Service
 * 
 * @author lzb
 * 
 */
@WebService
public interface ExpenseApplicationService {
	
	/**
	 * 查询 费用支付申请单列表
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<FormExpenseApplicationInfo> queryPage(ExpenseApplicationQueryOrder order);
	
	/**
	 * 根据表单ID查询 费用支付申请单
	 * @param formId
	 * @return
	 */
	FormExpenseApplicationInfo queryByFormId(long formId);
	
	/**
	 * 根据 费用支付申请单ID查询
	 * @param expenseApplicationId
	 * @return
	 */
	FormExpenseApplicationInfo queryById(long expenseApplicationId);
	
	/**
	 * 保存 费用支付申请单
	 * @param order
	 * @return
	 */
	FormBaseResult save(ExpenseApplicationOrder order);
	
	/**
	 * 查询提交信息
	 * @param deptId
	 * @param categoryId
	 * @param applyTimeStart
	 * @param applyTimeEnd
	 * @return
	 */
	List<FormExpenseApplicationDetailInfo> findSublInfoByDeptCategory(long deptId, long categoryId,
																		Date applyTimeStart,
																		Date applyTimeEnd);
	
	FormBaseResult updatePayBank(ExpenseApplicationOrder order);
	
	/**
	 * 同步至金碟（财务系统）
	 * @param formId
	 * @return
	 */
	FcsBaseResult sync2FinancialSys(FormExpenseApplicationInfo expenseInfo);
	
	/**
	 * 更新凭证号同步状态
	 * @param order
	 * @return
	 */
	FcsBaseResult updateVoucher(UpdateVoucherOrder order);
	
	/**
	 * 分支机构确认付款
	 * @param order
	 * @return
	 */
	FcsBaseResult confirmBranchPay(ConfirmBranchPayOrder order);
	
	/**
	 * 同步费用支付资金预测数据(提交后和审核通过后都预测)
	 * @param formId 表单ID
	 * @param afterAudit 是否审核通过后
	 * @return
	 */
	FcsBaseResult syncForecast(long formId, boolean afterAudit);
	
	/**
	 * 查询 费用支付申请单列表[报表用]
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<FormExpenseApplicationDetailAllInfo> queryPageAll(	ExpenseApplicationQueryOrder order);
}
