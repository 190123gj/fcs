/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.fm.ws.info.bank;

import java.util.Date;

import com.born.fcs.fm.ws.enums.SubjectAccountTypeEnum;
import com.born.fcs.fm.ws.enums.SubjectStatusEnum;
import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;

/**
 * 银行账户信息
 * @author hjiajie
 * 
 */
public class BankMessageInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = -6931971923247341606L;
	
	/** 主键 */
	private long bankId;
	/** 银行简称 */
	private String bankCode;
	/** 开户银行 */
	private String bankName;
	/** 资金类型 */
	private SubjectAccountTypeEnum accountType;
	/** 账户号码 */
	private String accountNo;
	/** 是否默认对公支付账户 */
	private BooleanEnum defaultCompanyAccount;
	/** 是否默认对私支付账户 */
	private BooleanEnum defaultPersonalAccount;
	/** 是否保证金账户账户 */
	private BooleanEnum depositAccount;
	/** 户名 */
	private String accountName;
	/** 会计科目代码 */
	private String atCode;
	/** 会计科目名称 */
	private String atName;
	/** 账户余额 */
	private Money amount = new Money(0, 0);
	/** 保证金账号代码 */
	private String cashDepositCode;
	/** 账户状态 */
	private SubjectStatusEnum status;
	/** 部门/公司ID */
	private long deptId;
	/** 公司/部门名称 */
	private String deptName;
	/** 新增时间 */
	private Date rawAddTime;
	/** 修改时间 */
	private Date rawUpdateTime;
	
	//// 为报表添加属性
	private String showAmount;
	
	public String getShowAmount() {
		if (StringUtil.isNotBlank(this.showAmount)) {
			return this.showAmount;
		} else {
			return this.amount.toStandardString();
		}
	}
	
	public void setShowAmount(String showAmount) {
		this.showAmount = showAmount;
	}
	
	public long getBankId() {
		return bankId;
	}
	
	public void setBankId(long bankId) {
		this.bankId = bankId;
	}
	
	public String getBankCode() {
		return bankCode;
	}
	
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
	public String getBankName() {
		return bankName;
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
	
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	public SubjectAccountTypeEnum getAccountType() {
		return accountType;
	}
	
	public void setAccountType(SubjectAccountTypeEnum accountType) {
		this.accountType = accountType;
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
	
	public String getAtCode() {
		return atCode;
	}
	
	public void setAtCode(String atCode) {
		this.atCode = atCode;
	}
	
	public String getAtName() {
		return atName;
	}
	
	public void setAtName(String atName) {
		this.atName = atName;
	}
	
	public Money getAmount() {
		return amount;
	}
	
	public void setAmount(Money amount) {
		this.amount = amount;
	}
	
	public String getCashDepositCode() {
		return cashDepositCode;
	}
	
	public void setCashDepositCode(String cashDepositCode) {
		this.cashDepositCode = cashDepositCode;
	}
	
	public SubjectStatusEnum getStatus() {
		return status;
	}
	
	public void setStatus(SubjectStatusEnum status) {
		this.status = status;
	}
	
	public long getDeptId() {
		return deptId;
	}
	
	public void setDeptId(long deptId) {
		this.deptId = deptId;
	}
	
	public String getDeptName() {
		return deptName;
	}
	
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	public Date getRawAddTime() {
		return rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	public Date getRawUpdateTime() {
		return rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
	
}
