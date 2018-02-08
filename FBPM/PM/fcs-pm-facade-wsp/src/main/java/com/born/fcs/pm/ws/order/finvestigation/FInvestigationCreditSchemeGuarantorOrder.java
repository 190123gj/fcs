package com.born.fcs.pm.ws.order.finvestigation;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;
import com.yjf.common.lang.util.money.Money;

/**
 * 保证人
 * 
 * @author lirz
 *
 * 2016-3-9 下午3:04:05
 */
public class FInvestigationCreditSchemeGuarantorOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = -1860536257561887742L;
	
	private long id; //主键
	private long schemeId; //授信方案ID
	private String guarantorType; //保证类型
	private String guarantor; //保证人
	private String guaranteeAmountStr; //保证额度
	private String guaranteeWay; //保证方式
	private int sortOrder;
	
	public boolean isNull() {
		return isNull(guarantor)
				&& isNull(guarantorType)
				&& isNull(guaranteeAmountStr)
				&& isNull(guaranteeWay)
				;
	}
	
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
	
	public String getGuarantorType() {
		return this.guarantorType;
	}

	public void setGuarantorType(String guarantorType) {
		this.guarantorType = guarantorType;
	}

	public String getGuarantor() {
		return guarantor;
	}
	
	public void setGuarantor(String guarantor) {
		this.guarantor = guarantor;
	}
	
	public String getGuaranteeAmountStr() {
		return guaranteeAmountStr;
	}
	
	public void setGuaranteeAmountStr(String guaranteeAmountStr) {
		this.guaranteeAmountStr = guaranteeAmountStr;
	}

	public Money getGuaranteeAmount() {
		if (isNull(this.guaranteeAmountStr)) {
			return new Money(0L);
		}
		return Money.amout(this.guaranteeAmountStr);
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
