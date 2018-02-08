package com.born.fcs.pm.ws.order.financialproject;

import java.util.Date;

import com.born.fcs.pm.ws.order.base.FormOrderBase;
import com.yjf.common.lang.util.money.Money;

/**
 * 理财产品赎回申请Order
 *
 *
 * @author wuzj
 *
 */
public class FProjectFinancialRedeemApplyOrder extends FormOrderBase {
	
	private static final long serialVersionUID = 1934021461159949396L;
	
	/** 申请ID */
	private Long applyId;
	/** 项目编号 */
	private String projectCode;
	/** 赎回单价 */
	private Money redeemPrice = new Money(0, 0);
	/** 赎回份额 */
	private Double redeemNum;
	/** 赎回时间 */
	private Date redeemTime;
	/** 赎回本金 */
	private Money redeemPrincipal = new Money(0, 0);
	/** 赎回利息 */
	private Money redeemInterest = new Money(0, 0);
	/** 赎回原因 */
	private String redeemReason;
	
	public Long getApplyId() {
		return this.applyId;
	}
	
	public void setApplyId(Long applyId) {
		this.applyId = applyId;
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
	
	public Double getRedeemNum() {
		return this.redeemNum;
	}
	
	public void setRedeemNum(Double redeemNum) {
		this.redeemNum = redeemNum;
	}
	
	public Date getRedeemTime() {
		return this.redeemTime;
	}
	
	public void setRedeemTime(Date redeemTime) {
		this.redeemTime = redeemTime;
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
	
}
