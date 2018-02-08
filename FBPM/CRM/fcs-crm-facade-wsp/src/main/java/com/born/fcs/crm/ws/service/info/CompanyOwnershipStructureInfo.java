package com.born.fcs.crm.ws.service.info;

import com.yjf.common.lang.util.money.Money;

/**
 * 公司客户：股权结构
 * */
public class CompanyOwnershipStructureInfo {
	
	/** 主键 */
	private Long id;
	/** 客户ID */
	private String customerId;
	/** 所属公司 */
	private String perents;
	/** 主要股东名称 */
	private String shareholdersName;
	/** 出资额 单 */
	private Money amount;
	/** 货币 默认元 */
	private String amountType;
	/** 出资额 单位：万 */
	private String amountString;
	/** 出资方式 */
	private String methord;
	/** 股权比例 % */
	private String equity;
	/** 备注 */
	private String memo;
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCustomerId() {
		return this.customerId;
	}
	
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
	public String getPerents() {
		return this.perents;
	}
	
	public void setPerents(String perents) {
		this.perents = perents;
	}
	
	public String getShareholdersName() {
		return this.shareholdersName;
	}
	
	public void setShareholdersName(String shareholdersName) {
		this.shareholdersName = shareholdersName;
	}
	
	public String getMethord() {
		return this.methord;
	}
	
	public void setMethord(String methord) {
		this.methord = methord;
	}
	
	public String getEquity() {
		return this.equity;
	}
	
	public void setEquity(String equity) {
		this.equity = equity;
	}
	
	public String getMemo() {
		return this.memo;
	}
	
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	public Money getAmount() {
		if (this.amount != null) {
			return this.amount;
		} else if (getAmountString() != null) {
			return Money.amout(getAmountString());
		}
		return this.amount;
	}
	
	public void setAmount(Money amount) {
		this.amount = amount;
	}
	
	public String getAmountType() {
		if (amountType == null || "".equals(amountType))
			return "元";
		return amountType;
	}
	
	public void setAmountType(String amountType) {
		this.amountType = amountType;
	}
	
	public String getAmountString() {
		return this.amountString;
	}
	
	public void setAmountString(String amountString) {
		this.amountString = amountString;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CompanyOwnershipStructureInfo [id=");
		builder.append(id);
		builder.append(", customerId=");
		builder.append(customerId);
		builder.append(", perents=");
		builder.append(perents);
		builder.append(", shareholdersName=");
		builder.append(shareholdersName);
		builder.append(", amount=");
		builder.append(amount);
		builder.append(", amountString=");
		builder.append(amountString);
		builder.append(", methord=");
		builder.append(methord);
		builder.append(", equity=");
		builder.append(equity);
		builder.append(", memo=");
		builder.append(memo);
		builder.append("]");
		return builder.toString();
	}
	
}
