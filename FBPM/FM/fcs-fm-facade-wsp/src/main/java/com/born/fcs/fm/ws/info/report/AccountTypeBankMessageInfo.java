/*
 * www.yiji.com Inc.
 * Copyright (c) 2014 All Rights Reserved.
 */

/*
 * 修订记录：
 * hjiajie 上午9:44:01 创建
 */
package com.born.fcs.fm.ws.info.report;

import java.util.List;

import com.born.fcs.fm.ws.info.bank.BankMessageInfo;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

/**
 * 
 * 
 * @author hjiajie
 * 
 */
public class AccountTypeBankMessageInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 统计列
	 */
	private List<BankMessageInfo> bankMessages;
	
	/** 资金类型 */
	private String accountType;
	
	private int count;
	
	public int getCount() {
		return this.count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	public List<BankMessageInfo> getBankMessages() {
		return this.bankMessages;
	}
	
	public void setBankMessages(List<BankMessageInfo> bankMessages) {
		this.bankMessages = bankMessages;
	}
	
	public String getAccountType() {
		return this.accountType;
	}
	
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	
}
