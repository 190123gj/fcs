/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 上午10:53:46 创建
 */
package com.born.fcs.fm.biz.service.risk;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.born.fcs.fm.biz.service.base.BaseAutowiredDomainService;
import com.born.fcs.fm.ws.enums.VoucherStatusEnum;
import com.born.fcs.fm.ws.order.common.UpdateVoucherOrder;
import com.born.fcs.fm.ws.service.payment.ExpenseApplicationService;
import com.born.fcs.fm.ws.service.payment.FormTransferService;
import com.born.fcs.fm.ws.service.payment.PaymentApplyService;
import com.born.fcs.fm.ws.service.payment.TravelExpenseService;
import com.born.fcs.fm.ws.service.receipt.ReceiptApplyService;
import com.born.fcs.pm.util.StringUtil;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.bornsoft.pub.interfaces.IKingdeeSystemService;
import com.bornsoft.pub.order.kingdee.KingdeeVoucherNoRecevieOrder;
import com.bornsoft.utils.base.BornSynResultBase;
import com.bornsoft.utils.enums.CommonResultEnum;
import com.yjf.common.lang.util.DateUtil;

/**
 * 
 * 
 * @author hjiajie
 * 
 */
@Service("iKingdeeSystemService")
public class IKingdeeSystemServiceImpl extends BaseAutowiredDomainService implements
																			IKingdeeSystemService {
	
	@Autowired
	private ReceiptApplyService receiptApplyService;
	@Autowired
	private PaymentApplyService paymentApplyService;
	@Autowired
	private TravelExpenseService travelExpenseService;
	@Autowired
	private FormTransferService formTransferService;
	@Autowired
	private ExpenseApplicationService expenseApplicationService;
	
	/**
	 * @param riskInfo
	 * @return
	 * @see com.bornsoft.pub.interfaces.IKingdeeSystemService#recieveRiskInfo(com.bornsoft.pub.order.kingdee.KingdeeVoucherNoRecevieOrder)
	 */
	@Override
	public BornSynResultBase recieveRiskInfo(KingdeeVoucherNoRecevieOrder riskInfo) {
		logger.info("进入金碟单据号回推：order=" + riskInfo);
		BornSynResultBase result = new BornSynResultBase();
		//		/****单据号******/
		//		private String bizNo;	
		//		/****凭证号******/
		//		private String voucherNo;	
		//		/****是否删除******/
		//		private Boolean isDelete;
		//	voucherNo	FK 付款 SK收款
		String billNo = riskInfo.getBizNo();
		if (riskInfo == null || StringUtil.isBlank(billNo)) {
			result.setResultCode(CommonResultEnum.EXECUTE_FAILURE);
			result.setResultMessage("入参不能为空！" + riskInfo);
			return result;
		}
		if ("SK".equals(billNo.substring(0, 2))) {
			// 收款单
			UpdateVoucherOrder updateOrder = new UpdateVoucherOrder();
			updateOrder.setBillNo(riskInfo.getBizNo());
			updateOrder.setVoucherNo(riskInfo.getVoucherNo());
			try {
				if (StringUtil.isNotBlank(riskInfo.getVoucherSyncFinishTime())) {
					updateOrder.setVoucherSyncFinishTime(DateUtil.string2Date(riskInfo
						.getVoucherSyncFinishTime()));
				} else {
					logger.info("未传入过账时间，以当前时间替换！");
					updateOrder.setVoucherSyncFinishTime(getSysdate());
				}
			} catch (ParseException e) {
				logger.error("转换过账时间失败：" + e.getMessage(), e);
			}
			if (riskInfo.isDelete()) {
				updateOrder.setVoucherStatus(VoucherStatusEnum.SYNC_DELETE);
			} else {
				updateOrder.setVoucherStatus(VoucherStatusEnum.SYNC_FINISH);
			}
			FcsBaseResult fcsResult = receiptApplyService.updateVoucher(updateOrder);
			if (fcsResult.isSuccess() && fcsResult.isExecuted()) {
				result.setResultCode(CommonResultEnum.EXECUTE_SUCCESS);
				result.setResultMessage("接收完成！");
				logger.info("接收 收款单凭证完成！");
			} else {
				result.setResultCode(CommonResultEnum.EXECUTE_FAILURE);
				result.setResultMessage("接收失败！原因：" + result.getResultMessage());
				logger.error("接收失败！原因：" + result.getResultMessage());
			}
		} else if ("FK".equals(billNo.substring(0, 2))) {
			// 付款单
			
			// 收款单
			UpdateVoucherOrder updateOrder = new UpdateVoucherOrder();
			updateOrder.setBillNo(riskInfo.getBizNo());
			updateOrder.setVoucherNo(riskInfo.getVoucherNo());
			try {
				if (StringUtil.isNotBlank(riskInfo.getVoucherSyncFinishTime())) {
					updateOrder.setVoucherSyncFinishTime(DateUtil.string2Date(riskInfo
						.getVoucherSyncFinishTime()));
				} else {
					logger.info("未传入过账时间，以当前时间替换！");
					updateOrder.setVoucherSyncFinishTime(getSysdate());
				}
			} catch (ParseException e) {
				logger.error("转换过账时间失败：" + e.getMessage(), e);
			}
			if (riskInfo.isDelete()) {
				updateOrder.setVoucherStatus(VoucherStatusEnum.SYNC_DELETE);
			} else {
				updateOrder.setVoucherStatus(VoucherStatusEnum.SYNC_FINISH);
			}
			FcsBaseResult fcsResult = paymentApplyService.updateVoucher(updateOrder);
			
			if (fcsResult.isSuccess() && fcsResult.isExecuted()) {
				result.setResultCode(CommonResultEnum.EXECUTE_SUCCESS);
				result.setResultMessage("接收完成！");
				logger.info("接收 收款单凭证完成！");
			} else {
				result.setResultCode(CommonResultEnum.EXECUTE_FAILURE);
				result.setResultMessage("接收失败！原因：" + result.getResultMessage());
				logger.error("接收失败！原因：" + result.getResultMessage());
			}
			
		} else if ("CLF".equals(billNo.substring(0, 3))) {
			// 差旅费
			UpdateVoucherOrder updateOrder = new UpdateVoucherOrder();
			updateOrder.setBillNo(riskInfo.getBizNo());
			updateOrder.setVoucherNo(riskInfo.getVoucherNo());
			try {
				if (StringUtil.isNotBlank(riskInfo.getVoucherSyncFinishTime())) {
					updateOrder.setVoucherSyncFinishTime(DateUtil.string2Date(riskInfo
						.getVoucherSyncFinishTime()));
				} else {
					logger.info("未传入过账时间，以当前时间替换！");
					updateOrder.setVoucherSyncFinishTime(getSysdate());
				}
			} catch (ParseException e) {
				logger.error("转换过账时间失败：" + e.getMessage(), e);
			}
			if (riskInfo.isDelete()) {
				updateOrder.setVoucherStatus(VoucherStatusEnum.SYNC_DELETE);
			} else {
				updateOrder.setVoucherStatus(VoucherStatusEnum.SYNC_FINISH);
			}
			FcsBaseResult fcsResult = travelExpenseService.updateVoucher(updateOrder);
			
			if (fcsResult.isSuccess() && fcsResult.isExecuted()) {
				result.setResultCode(CommonResultEnum.EXECUTE_SUCCESS);
				result.setResultMessage("接收完成！");
				logger.info("接收差旅费报销单凭证完成！");
			} else {
				result.setResultCode(CommonResultEnum.EXECUTE_FAILURE);
				result.setResultMessage("接收失败！原因：" + result.getResultMessage());
				logger.error("接收失败！原因：" + result.getResultMessage());
			}
			
		} else if ("ZZ".equals(billNo.substring(0, 2))) {
			// 转账申请单
			UpdateVoucherOrder updateOrder = new UpdateVoucherOrder();
			updateOrder.setBillNo(riskInfo.getBizNo());
			updateOrder.setVoucherNo(riskInfo.getVoucherNo());
			try {
				if (StringUtil.isNotBlank(riskInfo.getVoucherSyncFinishTime())) {
					updateOrder.setVoucherSyncFinishTime(DateUtil.string2Date(riskInfo
						.getVoucherSyncFinishTime()));
				} else {
					logger.info("未传入过账时间，以当前时间替换！");
					updateOrder.setVoucherSyncFinishTime(getSysdate());
				}
			} catch (ParseException e) {
				logger.error("转换过账时间失败：" + e.getMessage(), e);
			}
			
			if (riskInfo.isDelete()) {
				updateOrder.setVoucherStatus(VoucherStatusEnum.SYNC_DELETE);
			} else {
				updateOrder.setVoucherStatus(VoucherStatusEnum.SYNC_FINISH);
			}
			FcsBaseResult fcsResult = formTransferService.updateVoucher(updateOrder);
			
			if (fcsResult.isSuccess() && fcsResult.isExecuted()) {
				result.setResultCode(CommonResultEnum.EXECUTE_SUCCESS);
				result.setResultMessage("接收完成！");
				logger.info("接收转账申请单凭证完成！");
			} else {
				result.setResultCode(CommonResultEnum.EXECUTE_FAILURE);
				result.setResultMessage("接收失败！原因：" + result.getResultMessage());
				logger.error("接收失败！原因：" + result.getResultMessage());
			}
			
		} else if ("ZF".equals(billNo.substring(0, 2))) {
			// 费用报销单
			UpdateVoucherOrder updateOrder = new UpdateVoucherOrder();
			updateOrder.setBillNo(riskInfo.getBizNo());
			updateOrder.setVoucherNo(riskInfo.getVoucherNo());
			try {
				if (StringUtil.isNotBlank(riskInfo.getVoucherSyncFinishTime())) {
					updateOrder.setVoucherSyncFinishTime(DateUtil.string2Date(riskInfo
						.getVoucherSyncFinishTime()));
				} else {
					logger.info("未传入过账时间，以当前时间替换！");
					updateOrder.setVoucherSyncFinishTime(getSysdate());
				}
			} catch (ParseException e) {
				logger.error("转换过账时间失败：" + e.getMessage(), e);
			}
			if (riskInfo.isDelete()) {
				updateOrder.setVoucherStatus(VoucherStatusEnum.SYNC_DELETE);
			} else {
				updateOrder.setVoucherStatus(VoucherStatusEnum.SYNC_FINISH);
			}
			FcsBaseResult fcsResult = expenseApplicationService.updateVoucher(updateOrder);
			
			if (fcsResult.isSuccess() && fcsResult.isExecuted()) {
				result.setResultCode(CommonResultEnum.EXECUTE_SUCCESS);
				result.setResultMessage("接收完成！");
				logger.info("接收费用报销单凭证完成！");
			} else {
				result.setResultCode(CommonResultEnum.EXECUTE_FAILURE);
				result.setResultMessage("接收失败！原因：" + result.getResultMessage());
				logger.error("接收失败！原因：" + result.getResultMessage());
			}
			
		} else {
			result.setResultCode(CommonResultEnum.EXECUTE_FAILURE);
			result.setResultMessage("非法的单据号！" + riskInfo.getBizNo());
			logger.error("非法的单据号!" + riskInfo.getBizNo());
		}
		result.setOrderNo(riskInfo.getOrderNo());
		return result;
	}
}
