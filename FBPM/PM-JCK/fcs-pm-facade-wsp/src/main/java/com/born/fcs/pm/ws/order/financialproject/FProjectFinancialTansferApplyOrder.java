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
	
	private Long applyId;
	
	private String projectCode;
	
	private Long holdNum;
	
	private Long transferPrice;
	
	private Long transferNum;
	
	private Date transferTimeStart;
	
	private Date transferTimeEnd;
	
	private String transferReason;
	
	private String attach;
	
	private String isTransferOwnership;
	
	private String isBuyBack;
	
	private Money buyBackPrice = new Money(0, 0);
	
	private Date buyBackTime;
	
	@Override
	public void check() {
		validateHasText(projectCode, "理财项目");
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
	
	public Long getHoldNum() {
		return this.holdNum;
	}
	
	public void setHoldNum(Long holdNum) {
		this.holdNum = holdNum;
	}
	
	public Long getTransferPrice() {
		return this.transferPrice;
	}
	
	public void setTransferPrice(Long transferPrice) {
		this.transferPrice = transferPrice;
	}
	
	public Long getTransferNum() {
		return this.transferNum;
	}
	
	public void setTransferNum(Long transferNum) {
		this.transferNum = transferNum;
	}
	
	public Date getTransferTimeStart() {
		return this.transferTimeStart;
	}
	
	public void setTransferTimeStart(Date transferTimeStart) {
		this.transferTimeStart = transferTimeStart;
	}
	
	public Date getTransferTimeEnd() {
		return this.transferTimeEnd;
	}
	
	public void setTransferTimeEnd(Date transferTimeEnd) {
		this.transferTimeEnd = transferTimeEnd;
	}
	
	public String getTransferReason() {
		return this.transferReason;
	}
	
	public void setTransferReason(String transferReason) {
		this.transferReason = transferReason;
	}
	
	public String getAttach() {
		return this.attach;
	}
	
	public void setAttach(String attach) {
		this.attach = attach;
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
