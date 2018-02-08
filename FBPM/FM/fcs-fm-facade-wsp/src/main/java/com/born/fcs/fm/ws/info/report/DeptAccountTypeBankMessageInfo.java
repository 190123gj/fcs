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

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;

/**
 * 
 * 
 * @author hjiajie
 * 
 */
public class DeptAccountTypeBankMessageInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = 1L;
	
	private List<AccountTypeBankMessageInfo> accountBanks;
	
	/** 公司名，各个部门换算为母公司 **/
	private String company;
	
	private int count;
	
	public int getCount() {
		return this.count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	public List<AccountTypeBankMessageInfo> getAccountBanks() {
		return this.accountBanks;
	}
	
	public void setAccountBanks(List<AccountTypeBankMessageInfo> accountBanks) {
		this.accountBanks = accountBanks;
	}
	
	public String getCompany() {
		return this.company;
	}
	
	public void setCompany(String company) {
		this.company = company;
	}
	
}
