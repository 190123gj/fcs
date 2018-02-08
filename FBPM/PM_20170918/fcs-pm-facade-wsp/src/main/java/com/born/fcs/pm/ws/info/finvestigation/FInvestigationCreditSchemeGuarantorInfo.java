package com.born.fcs.pm.ws.info.finvestigation;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.enums.GuarantorTypeEnum;
import com.yjf.common.lang.util.money.Money;

/**
 * 保证人
 * 
 * @author lirz
 *
 * 2016-3-9 下午3:04:15
 */
public class FInvestigationCreditSchemeGuarantorInfo implements Serializable {
	
	private static final long serialVersionUID = 3308544663881604090L;
	private long id; //主键
	private long schemeId; //授信方案ID
	private GuarantorTypeEnum guarantorType; //保证类型
	private String guarantor; //保证人
	private Money guaranteeAmount = new Money(0, 0); //保证额度
	private String guaranteeWay; //保证方式
	private int sortOrder;
	private Date rawAddTime;
	private Date rawUpdateTime;
	
	//========== getters and setters ==========
	
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
	
	public GuarantorTypeEnum getGuarantorType() {
		return this.guarantorType;
	}

	public void setGuarantorType(GuarantorTypeEnum guarantorType) {
		this.guarantorType = guarantorType;
	}

	public String getGuarantor() {
		return guarantor;
	}
	
	public void setGuarantor(String guarantor) {
		this.guarantor = guarantor;
	}
	
	public Money getGuaranteeAmount() {
		return guaranteeAmount;
	}
	
	public void setGuaranteeAmount(Money guaranteeAmount) {
		if (guaranteeAmount == null) {
			this.guaranteeAmount = new Money(0, 0);
		} else {
			this.guaranteeAmount = guaranteeAmount;
		}
	}
	
	public String getGuaranteeWay() {
		return guaranteeWay;
	}
	
	public void setGuaranteeWay(String guaranteeWay) {
		this.guaranteeWay = guaranteeWay;
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
