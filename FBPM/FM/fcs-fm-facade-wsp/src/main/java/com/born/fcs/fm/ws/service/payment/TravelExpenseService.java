/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午5:14:40 创建
 */
package com.born.fcs.fm.ws.service.payment;

import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import com.born.fcs.fm.ws.info.payment.FormPaymentInfo;
import com.born.fcs.fm.ws.info.payment.FormTravelExpenseDetailInfo;
import com.born.fcs.fm.ws.info.payment.FormTravelExpenseInfo;
import com.born.fcs.fm.ws.order.common.UpdateVoucherOrder;
import com.born.fcs.fm.ws.order.payment.ConfirmBranchPayOrder;
import com.born.fcs.fm.ws.order.payment.TravelExpenseOrder;
import com.born.fcs.fm.ws.order.payment.TravelExpenseQueryOrder;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.FormBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
/**
 * 差旅费报销单Service
 * 
 * @author lzb
 * 
 */
@WebService
public interface TravelExpenseService {
	
	/**
	 * 查询报销单列表
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<FormTravelExpenseInfo> queryPage(TravelExpenseQueryOrder order);
	
	
	/**
	 * 根据表单ID查询报销单
	 * @param formId
	 * @return
	 */
	FormTravelExpenseInfo queryByFormId(long formId);
	
	/**
	 * 根据报销单ID查询
	 * @param applyId
	 * @return
	 */
	FormTravelExpenseInfo queryById(long travelId);
	
	/**
	 * 保存差旅费报销单
	 * @param order
	 * @return
	 */
	FormBaseResult save(TravelExpenseOrder order);
	
	FormBaseResult updatePayBank(TravelExpenseOrder order);
	
	/**
	 * 同步至金碟（财务系统）
	 * @param formId
	 * @return
	 */
	FcsBaseResult sync2FinancialSys(FormTravelExpenseInfo travelInfo);
	
	/**
	 * 更新凭证号同步状态
	 * @param order
	 * @return
	 */
	FcsBaseResult updateVoucher(UpdateVoucherOrder order);
	
	/**
	 * 根据部门ID查询已审核通过的报销金额信息
	 * @param formId
	 * @return
	 */
	List<FormTravelExpenseDetailInfo> findApprlInfoByDeptId(String deptId, Date applyTimeStart, Date applyTimeEnd);
	
	/**
	 * 同步费用支付资金预测数据(提交后和审核通过后都预测)
	 * @param formId 表单ID
	 * @param afterAudit 是否审核通过后
	 * @return
	 */
	FcsBaseResult syncForecast(long formId, boolean afterAudit);
	
	/**
	 * 分支机构确认付款
	 * @param order
	 * @return
	 */
	FcsBaseResult confirmBranchPay(ConfirmBranchPayOrder order);
}
