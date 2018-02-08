package com.born.fcs.pm.ws.order.finvestigation;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.enums.GuaranteeTypeEnum;
import com.born.fcs.pm.ws.enums.PledgePropertyEnum;
import com.born.fcs.pm.ws.enums.PledgeTypeEnum;
import com.born.fcs.pm.ws.order.base.ValidateOrderBase;
import com.yjf.common.lang.util.money.Money;

public class FInvestigationCreditSchemePledgeOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = -1412179252259387770L;
	private Long id; //主键
	private Long schemeId; //授信方案ID
	private GuaranteeTypeEnum type; //抵/质押类型
	private String typeDesc; //抵押 / 质押
	private PledgeTypeEnum pledgeType; //押品类型
	private String pledgeTypeDesc; //押品类型描述
	private PledgePropertyEnum pledgeProperty; //押品性质
	private String pledgePropertyDesc; //押品性质描述
	private String ownership; //权利人
	private String address; //住所
	private String warrantNo; //权证号
	private String num; //数量
	private String unit; //单位
	private String amountStr; //评估价格
	private Double ratio; //抵押率
	
	public boolean isNull() {
		return null == pledgeType
				&& null == pledgeProperty
				&& isNull(ownership)
				&& isNull(address)
				&& isNull(warrantNo)
				&& isNull(num)
				&& isNull(amountStr)
				&& isNull(ratio)
				;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getSchemeId() {
		return schemeId;
	}
	
	public void setSchemeId(Long schemeId) {
		this.schemeId = schemeId;
	}
	
	public GuaranteeTypeEnum getType() {
		return type;
	}
	
	public void setType(GuaranteeTypeEnum type) {
		this.type = type;
	}
	
	public String getTypeStr() {
		return (null == this.type) ? "" : this.type.code();
	}
	
	public void setTypeStr(String code) {
		this.type = GuaranteeTypeEnum.getByCode(code);
	}
	
	public String getTypeDesc() {
		return typeDesc;
	}
	
	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}
	
	public PledgeTypeEnum getPledgeType() {
		return pledgeType;
	}
	
	public void setPledgeType(PledgeTypeEnum pledgeType) {
		this.pledgeType = pledgeType;
	}
	
	public String getPledgeTypeStr() {
		return (null == this.pledgeType) ? "" : this.pledgeType.code();
	}
	
	public void setPledgeTypeStr(String code) {
		this.pledgeType = PledgeTypeEnum.getByCode(code);
	}
	
	public String getPledgeTypeDesc() {
		return pledgeTypeDesc;
	}
	
	public void setPledgeTypeDesc(String pledgeTypeDesc) {
		this.pledgeTypeDesc = pledgeTypeDesc;
	}
	
	public PledgePropertyEnum getPledgeProperty() {
		return pledgeProperty;
	}
	
	public void setPledgeProperty(PledgePropertyEnum pledgeProperty) {
		this.pledgeProperty = pledgeProperty;
	}
	
	public String getPledgePropertyStr() {
		return (null == this.pledgeProperty) ? "" : this.pledgeProperty.code();
	}
	
	public void setPledgePropertyStr(String code) {
		this.pledgeProperty = PledgePropertyEnum.getByCode(code);
	}
	
	public String getPledgePropertyDesc() {
		return pledgePropertyDesc;
	}
	
	public void setPledgePropertyDesc(String pledgePropertyDesc) {
		this.pledgePropertyDesc = pledgePropertyDesc;
	}
	
	public String getOwnership() {
		return ownership;
	}
	
	public void setOwnership(String ownership) {
		this.ownership = ownership;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getWarrantNo() {
		return warrantNo;
	}
	
	public void setWarrantNo(String warrantNo) {
		this.warrantNo = warrantNo;
	}
	
	public String getNum() {
		return num;
	}
	
	public void setNum(String num) {
		this.num = num;
	}
	
	public String getUnit() {
		return unit;
	}
	
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public String getAmountStr() {
		return amountStr;
	}
	
	public void setAmountStr(String amountStr) {
		this.amountStr = amountStr;
	}
	
//	public void setAmountStr(String amountStr) {
//		this.amount = Money.amout(amountStr);
//	}
	
	public Money getAmount() {
		if (isNull(this.amountStr)) {
			return new Money(0L);
		}
		return Money.amout(this.amountStr);
	}
	
	public Double getRatio() {
		return ratio;
	}
	
	public void setRatio(Double ratio) {
		this.ratio = ratio;
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
