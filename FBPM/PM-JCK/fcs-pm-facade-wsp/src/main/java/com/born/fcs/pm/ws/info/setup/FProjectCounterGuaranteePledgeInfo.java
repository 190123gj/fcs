package com.born.fcs.pm.ws.info.setup;

import java.util.Date;

import com.born.fcs.pm.ws.enums.GuaranteeTypeEnum;
import com.born.fcs.pm.ws.enums.PledgeTypeEnum;
import com.born.fcs.pm.ws.info.common.SimpleFormProjectInfo;
import com.yjf.common.lang.util.money.Money;

public class FProjectCounterGuaranteePledgeInfo extends SimpleFormProjectInfo {
	
	private static final long serialVersionUID = -3204685647795239354L;
	
	private long id;
	
	private GuaranteeTypeEnum type;
	
	private String pledger;
	
	private PledgeTypeEnum pledgeType;
	
	private String pledgeName;
	
	private Money netValue = new Money(0, 0);
	
	private String synPosition;
	
	private String postposition;
	
	private int sortOrder;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public GuaranteeTypeEnum getType() {
		return this.type;
	}
	
	public void setType(GuaranteeTypeEnum type) {
		this.type = type;
	}
	
	public String getPledger() {
		return this.pledger;
	}
	
	public void setPledger(String pledger) {
		this.pledger = pledger;
	}
	
	public PledgeTypeEnum getPledgeType() {
		return this.pledgeType;
	}
	
	public void setPledgeType(PledgeTypeEnum pledgeType) {
		this.pledgeType = pledgeType;
	}
	
	public String getPledgeName() {
		return this.pledgeName;
	}
	
	public void setPledgeName(String pledgeName) {
		this.pledgeName = pledgeName;
	}
	
	public Money getNetValue() {
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
	
	public int getSortOrder() {
		return this.sortOrder;
	}
	
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
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
