package com.born.fcs.pm.ws.info.financeaffirm;

/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */

import java.util.Date;
import java.util.List;

import com.born.fcs.pm.ws.enums.FinanceAffirmTypeEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 财务确认-资金划付和收费通知info
 *
 * @author Ji
 *
 */
public class FFinanceAffirmInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = 2121913751350745028L;
	
	private long affirmId;
	
	private long formId;
	
	private String projectCode;
	
	private FinanceAffirmTypeEnum affirmFormType;
	
	private Money amount = new Money(0, 0);
	
	private String remark;
	
	private String attach;
	
	private Date rawAddTime;
	
	private List<FFinanceAffirmDetailInfo> feeList;
	
	public long getAffirmId() {
		return affirmId;
	}
	
	public void setAffirmId(long affirmId) {
		this.affirmId = affirmId;
	}
	
	public long getFormId() {
		return formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}
	
	public FinanceAffirmTypeEnum getAffirmFormType() {
		return affirmFormType;
	}
	
	public void setAffirmFormType(FinanceAffirmTypeEnum affirmFormType) {
		this.affirmFormType = affirmFormType;
	}
	
	public Money getAmount() {
		return amount;
	}
	
	public void setAmount(Money amount) {
		this.amount = amount;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getAttach() {
		return attach;
	}
	
	public void setAttach(String attach) {
		this.attach = attach;
	}
	
	public Date getRawAddTime() {
		return rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	public List<FFinanceAffirmDetailInfo> getFeeList() {
		return feeList;
	}
	
	public void setFeeList(List<FFinanceAffirmDetailInfo> feeList) {
		this.feeList = feeList;
	}
	
	public String getProjectCode() {
		return projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
}
