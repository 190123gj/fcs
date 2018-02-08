package com.born.fcs.pm.ws.order.financialproject;

import java.util.Date;

import com.born.fcs.pm.ws.enums.BooleanEnum;
import com.born.fcs.pm.ws.order.base.ProcessOrder;
import com.yjf.common.lang.util.money.Money;

/**
 * 转让信息维护
 * @author wuzj
 */
public class ProjectFinancialTradeTansferOrder extends ProcessOrder {
	
	private static final long serialVersionUID = 8548989076820899437L;
	//交易ID
	private long tradeId;
	//流水号
	private String flowNo;
	//项目编号
	private String projectCode;
	//申请单表单ID
	private long applyId;
	//转让单价
	private Money transferPrice = new Money(0, 0);
	//转让数量
	private double transferNum;
	//转让收益
	private Money transferInterest = new Money(0, 0);
	//转让时间
	private Date transferTime;
	/** 转让对象 */
	private String transferTo;
	/** 融资利率 */
	private double interestRate;
	//是否过户
	private BooleanEnum isTransferOwnership;
	//是否回购
	private BooleanEnum isBuyBack;
	//回购单价
	private Money buyBackPrice = new Money(0, 0);
	//回购时间
	private Date buyBackTime;
	//是否确认回购
	private BooleanEnum isConfirm;
	//转让原因
	private String transferReason;
	
	@Override
	public void check() {
		validateHasZore(applyId, "申请单ID");
	}
	
	public long getTradeId() {
		return this.tradeId;
	}
	
	public void setTradeId(long tradeId) {
		this.tradeId = tradeId;
	}
	
	public String getFlowNo() {
		return this.flowNo;
	}
	
	public void setFlowNo(String flowNo) {
		this.flowNo = flowNo;
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public long getApplyId() {
		return this.applyId;
	}
	
	public void setApplyId(long applyId) {
		this.applyId = applyId;
	}
	
	public Money getTransferPrice() {
		return this.transferPrice;
	}
	
	public void setTransferPrice(Money transferPrice) {
		this.transferPrice = transferPrice;
	}
	
	public double getTransferNum() {
		return this.transferNum;
	}
	
	public void setTransferNum(double transferNum) {
		this.transferNum = transferNum;
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
	
	public double getInterestRate() {
		return this.interestRate;
	}
	
	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}
	
	public Date getTransferTime() {
		return this.transferTime;
	}
	
	public void setTransferTime(Date transferTime) {
		this.transferTime = transferTime;
	}
	
	public BooleanEnum getIsTransferOwnership() {
		return this.isTransferOwnership;
	}
	
	public void setIsTransferOwnership(BooleanEnum isTransferOwnership) {
		this.isTransferOwnership = isTransferOwnership;
	}
	
	public BooleanEnum getIsBuyBack() {
		return this.isBuyBack;
	}
	
	public void setIsBuyBack(BooleanEnum isBuyBack) {
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
	
	public String getTransferReason() {
		return this.transferReason;
	}
	
	public void setTransferReason(String transferReason) {
		this.transferReason = transferReason;
	}
	
	public BooleanEnum getIsConfirm() {
		return this.isConfirm;
	}
	
	public void setIsConfirm(BooleanEnum isConfirm) {
		this.isConfirm = isConfirm;
	}
}
