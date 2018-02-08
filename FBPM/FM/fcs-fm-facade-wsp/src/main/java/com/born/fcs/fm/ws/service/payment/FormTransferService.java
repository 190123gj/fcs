/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午5:14:40 创建
 */
package com.born.fcs.fm.ws.service.payment;

import javax.jws.WebService;

import com.born.fcs.fm.ws.info.payment.FormTransferInfo;
import com.born.fcs.fm.ws.order.common.UpdateVoucherOrder;
import com.born.fcs.fm.ws.order.payment.FormTransferOrder;
import com.born.fcs.fm.ws.order.payment.FormTransferQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
/**
 * 转账申请单Service
 * 
 * @author lzb
 * 
 */
@WebService
public interface FormTransferService {
	
	/**
	 * 查询转账申请单列表
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<FormTransferInfo> queryPage(FormTransferQueryOrder order);
	
	
	/**
	 * 根据表单ID查询转账申请单
	 * @param formId
	 * @return
	 */
	FormTransferInfo queryByFormId(long formId);
	
	/**
	 * 根据转账申请单ID查询
	 * @param applyId
	 * @return
	 */
	FormTransferInfo queryById(long transferId);
	
	/**
	 * 保存转账申请单
	 * @param order
	 * @return
	 */
	FormBaseResult save(FormTransferOrder order);
	
	/**
	 * 同步至金碟（财务系统）
	 * @param formId
	 * @return
	 */
	FcsBaseResult sync2FinancialSys(FormTransferInfo transferInfo);
	
	/**
	 * 更新凭证号同步状态
	 * @param order
	 * @return
	 */
	FcsBaseResult updateVoucher(UpdateVoucherOrder order);
}
