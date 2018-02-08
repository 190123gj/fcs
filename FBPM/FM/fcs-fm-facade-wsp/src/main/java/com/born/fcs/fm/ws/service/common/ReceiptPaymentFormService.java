package com.born.fcs.fm.ws.service.common;

import javax.jws.WebService;

import com.born.fcs.fm.ws.enums.SourceFormEnum;
import com.born.fcs.fm.ws.info.common.ReceiptPaymentFormInfo;
import com.born.fcs.fm.ws.order.common.ReceiptPaymentFormOrder;
import com.born.fcs.fm.ws.order.common.ReceiptPaymentFormQueryOrder;
import com.born.fcs.fm.ws.order.common.UpdateReceiptPaymentFormStatusOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 收款单/付款单 单据来源Service
 * @author wuzj
 */
@WebService
public interface ReceiptPaymentFormService {
	
	/**
	 * 新增收款/付款单数据
	 * @param order
	 * @return
	 */
	FcsBaseResult add(ReceiptPaymentFormOrder order);
	
	/**
	 * 查询收款单/付款单数据源
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<ReceiptPaymentFormInfo> query(ReceiptPaymentFormQueryOrder order);
	
	/***
	 * 根据来源单据ID查询
	 * @param sourceForm
	 * @param sourceFormId
	 * @return
	 */
	ReceiptPaymentFormInfo queryBySourceFormId(SourceFormEnum sourceForm, String sourceFormId,
												boolean queryDetail);
	
	/**
	 * 根据主键查询
	 * @param sourceId
	 * @param queryDetail
	 * @return
	 */
	ReceiptPaymentFormInfo queryBySourceId(long sourceId, boolean queryDetail);
	
	/**
	 * 更新处理状态
	 * @param order
	 * @return
	 */
	FcsBaseResult updateStatus(UpdateReceiptPaymentFormStatusOrder order);
}
