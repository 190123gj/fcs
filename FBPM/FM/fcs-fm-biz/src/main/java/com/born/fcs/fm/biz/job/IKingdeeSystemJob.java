/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午6:24:52 创建
 */
package com.born.fcs.fm.biz.job;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.born.fcs.fm.biz.job.inter.ProcessJobService;
import com.born.fcs.fm.ws.enums.VoucherStatusEnum;
import com.born.fcs.fm.ws.info.payment.FormExpenseApplicationInfo;
import com.born.fcs.fm.ws.info.payment.FormPaymentFormInfo;
import com.born.fcs.fm.ws.info.payment.FormPaymentInfo;
import com.born.fcs.fm.ws.info.payment.FormTransferInfo;
import com.born.fcs.fm.ws.info.payment.FormTravelExpenseInfo;
import com.born.fcs.fm.ws.info.receipt.FormReceiptFormInfo;
import com.born.fcs.fm.ws.info.receipt.FormReceiptInfo;
import com.born.fcs.fm.ws.order.payment.ExpenseApplicationQueryOrder;
import com.born.fcs.fm.ws.order.payment.FormPaymentQueryOrder;
import com.born.fcs.fm.ws.order.payment.FormTransferQueryOrder;
import com.born.fcs.fm.ws.order.payment.TravelExpenseQueryOrder;
import com.born.fcs.fm.ws.order.receipt.FormReceiptQueryOrder;
import com.born.fcs.fm.ws.service.payment.ExpenseApplicationService;
import com.born.fcs.fm.ws.service.payment.FormTransferService;
import com.born.fcs.fm.ws.service.payment.PaymentApplyService;
import com.born.fcs.fm.ws.service.payment.TravelExpenseService;
import com.born.fcs.fm.ws.service.receipt.ReceiptApplyService;
import com.born.fcs.pm.util.DateUtil;
import com.born.fcs.pm.util.MiscUtil;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;
import com.yjf.common.lang.util.ListUtil;

/**
 * 
 * 金蝶同步接口信息维护[针对已失败的同步]
 * @author hjiajie
 * 
 */
@Service("IKingdeeSystemJob")
public class IKingdeeSystemJob extends JobBase implements ProcessJobService {
	
	@Autowired
	private ReceiptApplyService receiptApplyService;
	
	@Autowired
	private PaymentApplyService paymentApplyService;
	
	@Autowired
	private ExpenseApplicationService expenseApplicationService;
	
	@Autowired
	private FormTransferService formTransferService;
	
	@Autowired
	private TravelExpenseService travelExpenseService;
	
	@Override
	// 一天两次次1点 十三点触发,每次处理250条各项数据
	@Scheduled(cron = "0 0 1,13 * * ? ")
	//	@Scheduled(cron = "0 0/30 * * * ? ")
	//	@Scheduled(cron = "0 0/2 * * * ? ")
	public void doJob() throws Exception {
		
		logger.info("处理同步金蝶信息开始在 " + DateUtil.simpleFormat(new Date()));
		try {
			// 1. 收款单
			FormReceiptQueryOrder formReceiptQueryOrder = new FormReceiptQueryOrder();
			formReceiptQueryOrder.setVoucherStatus(VoucherStatusEnum.SEND_FAILED.code());
			formReceiptQueryOrder.setPageSize(250);
			formReceiptQueryOrder.setQueryFeeDetail(true);
			QueryBaseBatchResult<FormReceiptFormInfo> receipts = receiptApplyService
				.searchForm(formReceiptQueryOrder);
			//同步至财务系统
			if (receipts != null && ListUtil.isNotEmpty(receipts.getPageList())) {
				for (FormReceiptFormInfo info : receipts.getPageList()) {
					FormReceiptInfo receipt = new FormReceiptInfo();
					MiscUtil.copyPoObject(receipt, info);
					receiptApplyService.sync2FinancialSys(receipt);
				}
			}
			
			// 2. 付款单
			FormPaymentQueryOrder formPaymentQueryOrder = new FormPaymentQueryOrder();
			formPaymentQueryOrder.setVoucherStatus(VoucherStatusEnum.SEND_FAILED.code());
			formPaymentQueryOrder.setPageSize(250);
			formPaymentQueryOrder.setQueryFeeDetail(true);
			QueryBaseBatchResult<FormPaymentFormInfo> payments = paymentApplyService
				.searchForm(formPaymentQueryOrder);
			if (payments != null && ListUtil.isNotEmpty(payments.getPageList())) {
				for (FormPaymentFormInfo info : payments.getPageList()) {
					FormPaymentInfo payment = new FormPaymentInfo();
					MiscUtil.copyPoObject(payment, info);
					paymentApplyService.sync2FinancialSys(payment);
				}
			}
			
			// 3.费用支付申请单
			ExpenseApplicationQueryOrder expenseApplicationQueryOrder = new ExpenseApplicationQueryOrder();
			expenseApplicationQueryOrder.setVoucherStatus(VoucherStatusEnum.SEND_FAILED.code());
			expenseApplicationQueryOrder.setPageSize(250);
			expenseApplicationQueryOrder.setDetail(true);
			QueryBaseBatchResult<FormExpenseApplicationInfo> expenses = expenseApplicationService
				.queryPage(expenseApplicationQueryOrder);
			if (expenses != null && ListUtil.isNotEmpty(expenses.getPageList())) {
				for (FormExpenseApplicationInfo info : expenses.getPageList()) {
					expenseApplicationService.sync2FinancialSys(info);
				}
			}
			
			// 4.转账申请单
			FormTransferQueryOrder formTransferQueryOrder = new FormTransferQueryOrder();
			formTransferQueryOrder.setVoucherStatus(VoucherStatusEnum.SEND_FAILED.code());
			formTransferQueryOrder.setPageSize(250);
			QueryBaseBatchResult<FormTransferInfo> transfers = formTransferService
				.queryPage(formTransferQueryOrder);
			if (transfers != null && ListUtil.isNotEmpty(transfers.getPageList())) {
				for (FormTransferInfo info : transfers.getPageList()) {
					formTransferService.sync2FinancialSys(info);
				}
			}
			
			// 5.差旅费报销单
			TravelExpenseQueryOrder travelExpenseQueryOrder = new TravelExpenseQueryOrder();
			travelExpenseQueryOrder.setVoucherStatus(VoucherStatusEnum.SEND_FAILED.code());
			travelExpenseQueryOrder.setPageSize(250);
			travelExpenseQueryOrder.setDetail(true);
			QueryBaseBatchResult<FormTravelExpenseInfo> travels = travelExpenseService
				.queryPage(travelExpenseQueryOrder);
			if (travels != null && ListUtil.isNotEmpty(travels.getPageList())) {
				for (FormTravelExpenseInfo info : travels.getPageList()) {
					
					travelExpenseService.sync2FinancialSys(info);
				}
			}
			
		} catch (Exception e) {
			logger.error("处理同步金蝶信息任务异常---异常原因：" + e.getMessage(), e);
		}
		logger.info("处理同步金蝶信息任务结束在 " + DateUtil.simpleFormat(new Date()));
	}
}
