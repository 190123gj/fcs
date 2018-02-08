package com.born.fcs.pm.ws.order.financialproject;

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
	
	private Long applyId;
	
	private String projectCode;
	
	private Money redeemPrice = new Money(0, 0);
	
	private Long redeemNum;
	
	private Money redeemPrincipal = new Money(0, 0);
	
	private Money redeemInterest = new Money(0, 0);
	
	private String redeemReason;
	
	private String attach;
	
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
	
	public Long getRedeemNum() {
		return this.redeemNum;
	}
	
	public void setRedeemNum(Long redeemNum) {
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
	
}
