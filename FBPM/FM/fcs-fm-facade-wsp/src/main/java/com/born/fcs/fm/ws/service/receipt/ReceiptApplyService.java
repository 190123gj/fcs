package com.born.fcs.fm.ws.service.receipt;

import java.util.List;

import javax.jws.WebService;

import com.born.fcs.fm.ws.info.receipt.FormReceiptFeeInfo;
import com.born.fcs.fm.ws.info.receipt.FormReceiptFormInfo;
import com.born.fcs.fm.ws.info.receipt.FormReceiptInfo;
import com.born.fcs.fm.ws.order.common.UpdateVoucherOrder;
import com.born.fcs.fm.ws.order.receipt.FormReceiptOrder;
import com.born.fcs.fm.ws.order.receipt.FormReceiptQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 收款申请单
 * @author wuzj
 */
@WebService
public interface ReceiptApplyService {
	
	/**
	 * 保存收款申请单
	 * @param order
	 * @return
	 */
	FormBaseResult saveApply(FormReceiptOrder order);
	
	/***
	 * 查询表单列表
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<FormReceiptFormInfo> searchForm(FormReceiptQueryOrder order);
	
	/**
	 * 根据表单ID查询收款单
	 * @param formId
	 * @return
	 */
	FormReceiptInfo findReceiptByFormId(long formId, boolean queryFeeDetail);
	
	/**
	 * 根据单据号查询收款单
	 * @param formId
	 * @return
	 */
	FormReceiptInfo findReceiptByBillNo(String billNo, boolean queryFeeDetail);
	
	/**
	 * 根据表单ID查询收款单明细
	 * @param formId
	 * @return
	 */
	List<FormReceiptFeeInfo> findReceiptFeeByFormId(long formId);
	
	/**
	 * 同步至金碟（财务系统）
	 * @param formId
	 * @return
	 */
	FcsBaseResult sync2FinancialSys(FormReceiptInfo receiptInfo);
	
	/**
	 * 更新凭证号同步状态
	 * @param order
	 * @return
	 */
	FcsBaseResult updateVoucher(UpdateVoucherOrder order);
}
