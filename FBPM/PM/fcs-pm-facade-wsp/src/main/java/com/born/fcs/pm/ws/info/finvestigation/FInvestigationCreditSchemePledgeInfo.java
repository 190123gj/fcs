/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.pm.ws.info.finvestigation;

import java.io.Serializable;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.enums.GuaranteeTypeEnum;
import com.born.fcs.pm.ws.enums.PledgePropertyEnum;
import com.born.fcs.pm.ws.enums.PledgeTypeEnum;
import com.yjf.common.lang.util.money.Money;

// auto generated imports
import java.util.Date;

/**
 * 授信方案 - 抵押/质押
 * 
 * @author lirz
 * 
 * 2016-3-9 下午4:09:52
 */
public class FInvestigationCreditSchemePledgeInfo implements Serializable {
	
	private static final long serialVersionUID = 1923377932349854693L;
	private long id; //主键
	private long schemeId; //授信方案ID
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
	private Money amount; //评估价格
	private double ratio; //抵押率
	private int sortOrder;
	private Date rawAddTime;
	private Date rawUpdateTime;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getSchemeId() {
		return schemeId;
	}
	
	public void setSchemeId(long schemeId) {
		this.schemeId = schemeId;
	}
	
	public GuaranteeTypeEnum getType() {
		return type;
	}
	
	public void setType(GuaranteeTypeEnum type) {
		this.type = type;
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
	
	public Money getAmount() {
		return amount;
	}
	
	public void setAmount(Money amount) {
		this.amount = amount;
	}
	
	public double getRatio() {
		return ratio;
	}
	
	public void setRatio(double ratio) {
		this.ratio = ratio;
	}
	
	public int getSortOrder() {
		return sortOrder;
	}
	
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	
	public Date getRawAddTime() {
		return rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	public Date getRawUpdateTime() {
		return rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
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
