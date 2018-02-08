/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 下午5:15:24 创建
 */
package com.born.fcs.fm.ws.result.bank;

import com.born.fcs.fm.ws.info.bank.BankMessageInfo;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.result.base.FcsBaseResult;

/**
 * 
 * 
 * @author hjiajie
 * 
 */
public class BankMessageResult extends FcsBaseResult {
	
	private static final long serialVersionUID = 1L;
	
	private BankMessageInfo bankMessageInfo;
	
	private BooleanEnum used;
	
	public BooleanEnum getUsed() {
		return this.used;
	}
	
	public void setUsed(BooleanEnum used) {
		this.used = used;
	}
	
	public BankMessageInfo getBankMessageInfo() {
		return this.bankMessageInfo;
	}
	
	public void setBankMessageInfo(BankMessageInfo bankMessageInfo) {
		this.bankMessageInfo = bankMessageInfo;
	}
	
}
