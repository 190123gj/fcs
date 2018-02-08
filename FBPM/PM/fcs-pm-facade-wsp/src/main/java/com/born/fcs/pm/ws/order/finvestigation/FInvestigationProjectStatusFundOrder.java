package com.born.fcs.pm.ws.order.finvestigation;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;
import com.yjf.common.lang.util.money.Money;

/**
 * 项目投资与资金筹措
 * 
 * @author lirz
 * 
 * 2016-3-10 下午5:35:04
 */
public class FInvestigationProjectStatusFundOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = 1086190001928964907L;
	private long id;
	private long statusId;
	private String item; //项目
	private String itemCode; //项目标识
	private String itemAmountStr; //项目金额
	private Double itemPercent; //项目百分比
	private String fundSource; //资金来源
	private String fundCode; //资金来源标识
	private String fundAmountStr; //资金来源金额
	private Double fundPercent; //资金来源百分比
	private int sortOrder;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getStatusId() {
		return statusId;
	}
	
	public void setStatusId(long statusId) {
		this.statusId = statusId;
	}
	
	public String getItem() {
		return item;
	}
	
	public void setItem(String item) {
		this.item = item;
	}
	
	public String getItemCode() {
		return itemCode;
	}
	
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	
	public Money getItemAmount() {
		if (isNull(this.itemAmountStr)) {
			return new Money(0L);
		}
		return Money.amout(this.itemAmountStr);
	}
	
	public String getItemAmountStr() {
		return itemAmountStr;
	}
	
	public void setItemAmountStr(String itemAmountStr) {
		this.itemAmountStr = itemAmountStr;
	}
	
	public Double getItemPercent() {
		return itemPercent;
	}
	
	public void setItemPercent(Double itemPercent) {
		this.itemPercent = itemPercent;
	}
	
	public String getFundSource() {
		return fundSource;
	}
	
	public void setFundSource(String fundSource) {
		this.fundSource = fundSource;
	}
	
	public String getFundCode() {
		return fundCode;
	}
	
	public void setFundCode(String fundCode) {
		this.fundCode = fundCode;
	}
	
	public Money getFundAmount() {
		if (isNull(this.fundAmountStr)) {
			return new Money(0L);
		}
		return Money.amout(this.fundAmountStr);
	}
	
	public String getFundAmountStr() {
		return fundAmountStr;
	}
	
	public void setFundAmountStr(String fundAmountStr) {
		this.fundAmountStr = fundAmountStr;
	}
	
	public Double getFundPercent() {
		return fundPercent;
	}
	
	public void setFundPercent(Double fundPercent) {
		this.fundPercent = fundPercent;
	}
	
	public int getSortOrder() {
		return sortOrder;
	}
	
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	/**
	 * @return
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
