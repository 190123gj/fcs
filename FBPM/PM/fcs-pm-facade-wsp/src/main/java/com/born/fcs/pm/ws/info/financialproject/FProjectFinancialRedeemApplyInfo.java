package com.born.fcs.pm.ws.info.financialproject;

import java.util.Date;

import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

public class FProjectFinancialRedeemApplyInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = -6747912041382149462L;
	
	/** 申请ID */
	private long applyId;
	/** 表单ID */
	private long formId;
	/** 项目编号 */
	private String projectCode;
	/** 买入份额 */
	private double buyNum;
	/** 持有份额 */
	private double holdNum;
	/** 申请时可转让数 */
	private double actualHoldNum;
	/** 当前转让中份额 */
	private double transferingNum;
	/** 当前赎回中份额 */
	private double redeemingNum;
	/** 赎回时间 */
	private Date redeemTime;
	/** 赎回单价 */
	private Money redeemPrice = new Money(0, 0);
	/** 赎回份额 */
	private double redeemNum;
	/** 赎回本金 */
	private Money redeemPrincipal = new Money(0, 0);
	/** 赎回利息 */
	private Money redeemInterest = new Money(0, 0);
	/** 赎回原因 */
	private String redeemReason;
	/** 新增时间 */
	private Date rawAddTime;
	/** 修改时间 */
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
	
	public double getBuyNum() {
		return this.buyNum;
	}
	
	public void setBuyNum(double buyNum) {
		this.buyNum = buyNum;
	}
	
	public double getHoldNum() {
		return this.holdNum;
	}
	
	public void setHoldNum(double holdNum) {
		this.holdNum = holdNum;
	}
	
	public double getActualHoldNum() {
		return this.actualHoldNum;
	}
	
	public void setActualHoldNum(double actualHoldNum) {
		this.actualHoldNum = actualHoldNum;
	}
	
	public double getTransferingNum() {
		return this.transferingNum;
	}
	
	public void setTransferingNum(double transferingNum) {
		this.transferingNum = transferingNum;
	}
	
	public double getRedeemingNum() {
		return this.redeemingNum;
	}
	
	public void setRedeemingNum(double redeemingNum) {
		this.redeemingNum = redeemingNum;
	}
	
	public Date getRedeemTime() {
		return this.redeemTime;
	}
	
	public void setRedeemTime(Date redeemTime) {
		this.redeemTime = redeemTime;
	}
	
	public Money getRedeemPrice() {
		return this.redeemPrice;
	}
	
	public void setRedeemPrice(Money redeemPrice) {
		this.redeemPrice = redeemPrice;
	}
	
	public double getRedeemNum() {
		return this.redeemNum;
	}
	
	public void setRedeemNum(double redeemNum) {
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
	
}
