package com.born.fcs.pm.ws.order.financialproject;

import java.util.Date;

import com.born.fcs.pm.ws.order.base.FormOrderBase;
import com.yjf.common.lang.util.money.Money;

/**
 * 理财产品转让申请Order
 * @author wuzj
 */
public class FProjectFinancialTansferApplyOrder extends FormOrderBase {
	
	private static final long serialVersionUID = 8399557096338323366L;
	
	/** 申请ID */
	private Long applyId;
	/** 项目编号 */
	private String projectCode;
	/** 申请时持有数 */
	private Double holdNum;
	/** 转让单价 */
	private Money transferPrice = new Money(0, 0);
	/** 转让数量 */
	private Double transferNum;
	/** 预计转让时间 */
	private Date transferTime;
	/** 预计转让收益 */
	private Money transferInterest = new Money(0, 0);
	/** 转让对象 */
	private String transferTo;
	/** 融资利率 */
	private Double interestRate;
	/** 转让原因 */
	private String transferReason;
	/** 是否过户 */
	private String isTransferOwnership;
	/** 是否回购 */
	private String isBuyBack;
	/** 回购单价 */
	private Money buyBackPrice = new Money(0, 0);
	/** 回购时间 */
	private Date buyBackTime;
	
	@Override
	public void check() {
		validateHasText(projectCode, "理财项目编号");
	}
	
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
	
	public Double getHoldNum() {
		return this.holdNum;
	}
	
	public void setHoldNum(Double holdNum) {
		this.holdNum = holdNum;
	}
	
	public Money getTransferPrice() {
		return this.transferPrice;
	}
	
	public void setTransferPrice(Money transferPrice) {
		this.transferPrice = transferPrice;
	}
	
	public Double getTransferNum() {
		return this.transferNum;
	}
	
	public void setTransferNum(Double transferNum) {
		this.transferNum = transferNum;
	}
	
	public Date getTransferTime() {
		return this.transferTime;
	}
	
	public void setTransferTime(Date transferTime) {
		this.transferTime = transferTime;
	}
	
	public Money getTransferInterest() {
		return this.transferInterest;
	}
	
	public void setTransferInterest(Money transferInterest) {
		this.transferInterest = transferInterest;
	}
	
	public String getTransferTo() {
		return this.transferTo;
	}
	
	public void setTransferTo(String transferTo) {
		this.transferTo = transferTo;
	}
	
	public Double getInterestRate() {
		return this.interestRate;
	}
	
	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}
	
	public String getTransferReason() {
		return this.transferReason;
	}
	
	public void setTransferReason(String transferReason) {
		this.transferReason = transferReason;
	}
	
	public String getIsTransferOwnership() {
		return this.isTransferOwnership;
	}
	
	public void setIsTransferOwnership(String isTransferOwnership) {
		this.isTransferOwnership = isTransferOwnership;
	}
	
	public String getIsBuyBack() {
		return this.isBuyBack;
	}
	
	public void setIsBuyBack(String isBuyBack) {
		this.isBuyBack = isBuyBack;
	}
	
	public Money getBuyBackPrice() {
		return this.buyBackPrice;
	}
	
	public void setBuyBackPrice(Money buyBackPrice) {
		this.buyBackPrice = buyBackPrice;
	}
	
	public Date getBuyBackTime() {
		return this.buyBackTime;
	}
	
	public void setBuyBackTime(Date buyBackTime) {
		this.buyBackTime = buyBackTime;
	}
}
