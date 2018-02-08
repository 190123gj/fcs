package com.born.fcs.pm.ws.order.council;

import com.born.fcs.pm.ws.enums.GuaranteeTypeEnum;
import com.born.fcs.pm.ws.enums.PledgePropertyEnum;
import com.born.fcs.pm.ws.enums.PledgeTypeEnum;
import com.born.fcs.pm.ws.order.base.ValidateOrderBase;

/**
 * 会议纪要 - 项目评审会 - 授信条件 - 抵（质）押
 *
 * @author wuzj 2016-08-27 废弃 新使用 FCouncilSummaryProjectPledgeAssetOrder
 *
 */
@Deprecated
public class FCouncilSummaryProjectPledgeOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = 7592809409682664936L;
	
	private Long id;
	
	private Long summaryId;
	
	private GuaranteeTypeEnum type;
	
	private PledgePropertyEnum pledgeProperty;
	
	private PledgeTypeEnum pledgeType;
	
	private String pledgeTypeDesc;
	
	private String ownership;
	
	private String address;
	
	private String warrantNo;
	
	private String num;
	
	private String unit;
	
	private String amountStr;
	
	private Double ratio;
	
	private Integer sortOrder;
	
	public boolean isNull() {
		return null == pledgeType && null == pledgeProperty && isNull(ownership) && isNull(address)
				&& isNull(warrantNo) && isNull(num) && isNull(amountStr) && isNull(ratio);
	}
	
	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getSummaryId() {
		return this.summaryId;
	}
	
	public void setSummaryId(Long summaryId) {
		this.summaryId = summaryId;
	}
	
	public GuaranteeTypeEnum getType() {
		return this.type;
	}
	
	public void setType(GuaranteeTypeEnum type) {
		this.type = type;
	}
	
	public PledgePropertyEnum getPledgeProperty() {
		return this.pledgeProperty;
	}
	
	public void setPledgeProperty(PledgePropertyEnum pledgeProperty) {
		this.pledgeProperty = pledgeProperty;
	}
	
	public PledgeTypeEnum getPledgeType() {
		return this.pledgeType;
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
	
	public String getAmountStr() {
		return this.amountStr;
	}
	
	public void setAmountStr(String amountStr) {
		this.amountStr = amountStr;
	}
	
	public Double getRatio() {
		return this.ratio;
	}
	
	public void setRatio(Double ratio) {
		this.ratio = ratio;
	}
	
	public Integer getSortOrder() {
		return this.sortOrder;
	}
	
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}
	
}
