package com.born.fcs.pm.ws.order.setup;

import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;

public class FProjectCounterGuaranteePledgeOrder extends SetupFormSaveOrder {
	
	private static final long serialVersionUID = -6000747443111713271L;
	
	private Long id;
	
	private String type;
	
	private String pledgeType;
	
	private String pledger;
	
	private String pledgeName;
	
	private String netValueStr;
	
	private Money netValue = Money.zero();
	
	private String synPosition;
	
	private String postposition;
	
	private Integer sortOrder;
	
	public boolean isNull() {
		return isNull(type) && isNull(pledgeType) && isNull(pledger) && isNull(pledgeName)
				&& isNull(netValueStr) && isNull(synPosition) && isNull(postposition);
	}
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getType() {
		return this.type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getPledgeType() {
		return this.pledgeType;
	}
	
	public void setPledgeType(String pledgeType) {
		this.pledgeType = pledgeType;
	}
	
	public String getPledger() {
		return this.pledger;
	}
	
	public void setPledger(String pledger) {
		this.pledger = pledger;
	}
	
	public String getPledgeName() {
		return this.pledgeName;
	}
	
	public void setPledgeName(String pledgeName) {
		this.pledgeName = pledgeName;
	}
	
	public String getNetValueStr() {
		return this.netValueStr;
	}
	
	public void setNetValueStr(String netValueStr) {
		this.netValueStr = netValueStr;
	}
	
	public Money getNetValue() {
		if (StringUtil.isNotBlank(netValueStr))
			return Money.amout(netValueStr);
		return this.netValue;
	}
	
	public void setNetValue(Money netValue) {
		this.netValue = netValue;
	}
	
	public String getSynPosition() {
		return this.synPosition;
	}
	
	public void setSynPosition(String synPosition) {
		this.synPosition = synPosition;
	}
	
	public String getPostposition() {
		return this.postposition;
	}
	
	public void setPostposition(String postposition) {
		this.postposition = postposition;
	}
	
	public Integer getSortOrder() {
		return this.sortOrder;
	}
	
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}
}
