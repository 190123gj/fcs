/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.fm.ws.order.bank;

import java.util.List;

import com.born.fcs.fm.ws.enums.SubjectAccountTypeEnum;
import com.born.fcs.fm.ws.enums.SubjectStatusEnum;
import com.born.fcs.pm.ws.base.QueryPageBase;
import com.born.fcs.pm.ws.enums.BooleanEnum;

/**
 * 
 * 银行账户信息
 * 
 * @author hjiajie
 * 
 */
public class BankMessageQueryOrder extends QueryPageBase {
	
	private static final long serialVersionUID = -2736137435396993156L;
	
	/** 账户ID */
	private long bankId;
	/** 开户银行 */
	private String bankName;
	/** 资金类型 */
	private SubjectAccountTypeEnum accountType;
	/** 账户状态 */
	private SubjectStatusEnum status;
	/** 账户号码 */
	private String accountNo;
	/** 户名 */
	private String accountName;
	/** 是否默认对公支付账户 */
	private BooleanEnum defaultCompanyAccount;
	/** 是否默认对私支付账户 */
	private BooleanEnum defaultPersonalAccount;
	/** 是否保证金账户账户 */
	private BooleanEnum depositAccount;
	/** 部门id */
	private long deptId;
	
	/** 部门/公司ID */
	private List<Long> deptIdList;
	
	public long getBankId() {
		return bankId;
	}
	
	public void setBankId(long bankId) {
		this.bankId = bankId;
	}
	
	public String getBankName() {
		return bankName;
	}
	
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	public long getDeptId() {
		return this.deptId;
	}
	
	public void setDeptId(long deptId) {
		this.deptId = deptId;
	}
	
	public BooleanEnum getDefaultCompanyAccount() {
		return this.defaultCompanyAccount;
	}
	
	public void setDefaultCompanyAccount(BooleanEnum defaultCompanyAccount) {
		this.defaultCompanyAccount = defaultCompanyAccount;
	}
	
	public BooleanEnum getDefaultPersonalAccount() {
		return this.defaultPersonalAccount;
	}
	
	public void setDefaultPersonalAccount(BooleanEnum defaultPersonalAccount) {
		this.defaultPersonalAccount = defaultPersonalAccount;
	}
	
	public BooleanEnum getDepositAccount() {
		return this.depositAccount;
	}
	
	public void setDepositAccount(BooleanEnum depositAccount) {
		this.depositAccount = depositAccount;
	}
	
	public SubjectAccountTypeEnum getAccountType() {
		return accountType;
	}
	
	public void setAccountType(SubjectAccountTypeEnum accountType) {
		this.accountType = accountType;
	}
	
	public SubjectStatusEnum getStatus() {
		return status;
	}
	
	public void setStatus(SubjectStatusEnum status) {
		this.status = status;
	}
	
	public String getAccountNo() {
		return accountNo;
	}
	
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	
	public String getAccountName() {
		return accountName;
	}
	
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	public List<Long> getDeptIdList() {
		return deptIdList;
	}
	
	public void setDeptIdList(List<Long> deptIdList) {
		this.deptIdList = deptIdList;
	}
	
}
