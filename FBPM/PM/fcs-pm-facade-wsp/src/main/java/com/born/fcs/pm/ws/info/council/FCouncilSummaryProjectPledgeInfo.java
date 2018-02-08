package com.born.fcs.pm.ws.info.council;

import java.util.Date;

import com.born.fcs.pm.ws.enums.GuaranteeTypeEnum;
import com.born.fcs.pm.ws.enums.PledgePropertyEnum;
import com.born.fcs.pm.ws.enums.PledgeTypeEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 会议纪要 - 项目评审会 - 授信条件 - 抵（质）押
 * 
 * @author wuzj 2016-08-27 废弃 新使用 FCouncilSummaryProjectPledgeAssetInfo
 */
@Deprecated
public class FCouncilSummaryProjectPledgeInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = -3212613093273221456L;
	
	private long id;
	
	private long summaryId;
	
	private String projectCode;
	
	private String projectName;
	
	private GuaranteeTypeEnum type;
	
	private String typeDesc;
	
	private PledgePropertyEnum pledgeProperty;
	
	private String pledgePropertyDesc;
	
	private PledgeTypeEnum pledgeType;
	
	private String pledgeTypeDesc;
	
	private String ownership;
	
	private String address;
	
	private String warrantNo;
	
	private String num;
	
	private String unit;
	
	private Money amount = new Money(0, 0);
	
	private double ratio;
	
	private int sortOrder;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	public long getId() {
		return this.id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getSummaryId() {
		return this.summaryId;
	}
	
	public void setSummaryId(long summaryId) {
		this.summaryId = summaryId;
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getProjectName() {
		return this.projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public GuaranteeTypeEnum getType() {
		return this.type;
	}
	
	public String getTypeMessage() {
		return this.type == null ? "" : type.message();
	}
	
	public void setType(GuaranteeTypeEnum type) {
		this.type = type;
	}
	
	public String getTypeDesc() {
		return this.typeDesc;
	}
	
	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}
	
	public PledgePropertyEnum getPledgeProperty() {
		return this.pledgeProperty;
	}
	
	public String getPledgePropertyMessage() {
		return this.pledgeProperty == null ? "" : pledgeProperty.message();
	}
	
	public void setPledgeProperty(PledgePropertyEnum pledgeProperty) {
		this.pledgeProperty = pledgeProperty;
	}
	
	public String getPledgePropertyDesc() {
		return this.pledgePropertyDesc;
	}
	
	public void setPledgePropertyDesc(String pledgePropertyDesc) {
		this.pledgePropertyDesc = pledgePropertyDesc;
	}
	
	public PledgeTypeEnum getPledgeType() {
		return this.pledgeType;
	}
	
	public String getPledgeTypeMessage() {
		return this.pledgeType == null ? "" : pledgeType.message();
	}
	
	public void setPledgeType(PledgeTypeEnum pledgeType) {
		this.pledgeType = pledgeType;
	}
	
	public String getPledgeTypeDesc() {
		return this.pledgeTypeDesc;
	}
	
	public void setPledgeTypeDesc(String pledgeTypeDesc) {
		this.pledgeTypeDesc = pledgeTypeDesc;
	}
	
	public String getOwnership() {
		return this.ownership;
	}
	
	public void setOwnership(String ownership) {
		this.ownership = ownership;
	}
	
	public String getAddress() {
		return this.address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getWarrantNo() {
		return this.warrantNo;
	}
	
	public void setWarrantNo(String warrantNo) {
		this.warrantNo = warrantNo;
	}
	
	public String getNum() {
		return this.num;
	}
	
	public void setNum(String num) {
		this.num = num;
	}
	
	public String getUnit() {
		return this.unit;
	}
	
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public Money getAmount() {
		return this.amount;
	}
	
	public String getAmountCNString() {
		Money money = this.amount == null ? Money.zero() : amount;
		return money.toCNString();
	}
	
	public String getAmountStandardString() {
		Money money = this.amount == null ? Money.zero() : amount;
		return money.toStandardString();
	}
	
	public String getAmountTenThousandString() {
		Money money = this.amount == null ? Money.zero() : amount;
		return getMoneyByw2(money);
	}
	
	public void setAmount(Money amount) {
		this.amount = amount;
	}
	
	public double getRatio() {
		return this.ratio;
	}
	
	public void setRatio(double ratio) {
		this.ratio = ratio;
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
