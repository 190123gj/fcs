package com.born.fcs.pm.ws.info.financialproject;

import java.util.Date;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

public class FProjectFinancialRedeemApplyInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = -6747912041382149462L;
	
	private long applyId;
	
	private long formId;
	
	private String projectCode;
	
	private Money redeemPrice = new Money(0, 0);
	
	private long redeemNum;
	
	private long holdNum;
	
	private long transferingNum;
	
	private long redeemingNum;
	
	private Money redeemPrincipal = new Money(0, 0);
	
	private Money redeemInterest = new Money(0, 0);
	
	private String redeemReason;
	
	private String attach;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	public long getApplyId() {
		return this.applyId;
	}
	
	public void setApplyId(long applyId) {
		this.applyId = applyId;
	}
	
	public long getFormId() {
		return this.formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public Money getRedeemPrice() {
		return this.redeemPrice;
	}
	
	public void setRedeemPrice(Money redeemPrice) {
		this.redeemPrice = redeemPrice;
	}
	
	public long getRedeemNum() {
		return this.redeemNum;
	}
	
	public void setRedeemNum(long redeemNum) {
		this.redeemNum = redeemNum;
	}
	
	public Money getRedeemPrincipal() {
		return this.redeemPrincipal;
	}
	
	public void setRedeemPrincipal(Money redeemPrincipal) {
		this.redeemPrincipal = redeemPrincipal;
	}
	
	public Money getRedeemInterest() {
		return this.redeemInterest;
	}
	
	public void setRedeemInterest(Money redeemInterest) {
		this.redeemInterest = redeemInterest;
	}
	
	public String getRedeemReason() {
		return this.redeemReason;
	}
	
	public void setRedeemReason(String redeemReason) {
		this.redeemReason = redeemReason;
	}
	
	public String getAttach() {
		return this.attach;
	}
	
	public void setAttach(String attach) {
		this.attach = attach;
	}
	
	public Date getRawAddTime() {
		return this.rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	public Date getRawUpdateTime() {
		return this.rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
	
	public long getHoldNum() {
		return this.holdNum;
	}
	
	public void setHoldNum(long holdNum) {
		this.holdNum = holdNum;
	}
	
	public long getTransferingNum() {
		return this.transferingNum;
	}
	
	public void setTransferingNum(long transferingNum) {
		this.transferingNum = transferingNum;
	}
	
	public long getRedeemingNum() {
		return this.redeemingNum;
	}
	
	public void setRedeemingNum(long redeemingNum) {
		this.redeemingNum = redeemingNum;
	}
	
}
