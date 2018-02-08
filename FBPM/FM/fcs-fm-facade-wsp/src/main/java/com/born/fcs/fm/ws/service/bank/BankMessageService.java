/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午5:14:40 创建
 */
package com.born.fcs.fm.ws.service.bank;

import javax.jws.WebService;

import com.born.fcs.fm.ws.enums.SubjectStatusEnum;
import com.born.fcs.fm.ws.info.bank.BankMessageInfo;
import com.born.fcs.fm.ws.info.bank.BankTradeInfo;
import com.born.fcs.fm.ws.order.bank.BankMessageBatchOrder;
import com.born.fcs.fm.ws.order.bank.BankMessageOrder;
import com.born.fcs.fm.ws.order.bank.BankMessageQueryOrder;
import com.born.fcs.fm.ws.order.bank.BankTradeOrder;
import com.born.fcs.fm.ws.order.bank.BankTradeQueryOrder;
import com.born.fcs.fm.ws.result.bank.BankMessageResult;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;
import com.born.fcs.pm.ws.result.base.QueryBaseBatchResult;

/**
 * 
 * 
 * @author hjiajie
 * 
 */
@WebService
public interface BankMessageService {
	
	/** 保存 */
	FcsBaseResult save(BankMessageOrder order);
	
	/** 批量保存 */
	FcsBaseResult saveAll(BankMessageBatchOrder order);
	
	/** 插入 */
	FcsBaseResult insert(BankMessageOrder order);
	
	/** 单查 */
	BankMessageResult findById(long bankId);
	
	/** 单查 */
	BankMessageResult findByAccount(String accountNo);
	
	/** 单改 */
	FcsBaseResult update(BankMessageOrder order);
	
	/** 列查 */
	QueryBaseBatchResult<BankMessageInfo> queryBankMessageInfo(BankMessageQueryOrder order);
	
	/** 修改状态 */
	FcsBaseResult updateStatus(long bankId, SubjectStatusEnum status);
	
	/**
	 * 交易
	 * @param order
	 * @return
	 */
	FcsBaseResult trade(BankTradeOrder order);
	
	/**
	 * 查询交易
	 * @param order
	 * @return
	 */
	QueryBaseBatchResult<BankTradeInfo> quryTrade(BankTradeQueryOrder order);
	
}
