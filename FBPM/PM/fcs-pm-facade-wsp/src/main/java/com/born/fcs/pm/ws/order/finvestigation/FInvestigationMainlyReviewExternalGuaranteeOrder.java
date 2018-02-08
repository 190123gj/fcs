package com.born.fcs.pm.ws.order.finvestigation;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.enums.CreditTypeEnum;
import com.born.fcs.pm.ws.order.base.ValidateOrderBase;
import com.yjf.common.lang.util.money.Money;

/**
 * 对外担保情况
 * 
 * @author lirz
 *
 * 2016-3-10 下午3:17:09
 */
public class FInvestigationMainlyReviewExternalGuaranteeOrder extends ValidateOrderBase{

	private static final long serialVersionUID = 5462408749769136642L;

	private long id;
	private long MReviewId;
	//类型（客户/个人-企业实际控制人、主要股东、管理人员、担保人等）
	private CreditTypeEnum type;
	//企业实际控制人、主要股东、管理人员、担保人等（类型为个人时填写）
	private String persionalName;
	private String persionalDesc; //个人描述（类型为个人时填写）
	private String warrantee; //被担保人
	private String amountStr; //担保金额
	private String guarranteeWay; //担保方式
	private String timeLimit; //担保期限
	private String consideration; //提供的对价
	private int sortOrder;
	
	public boolean isNull() {
		return isNull(persionalName)
				&& isNull(persionalDesc)
				&& isNull(warrantee)
				&& isNull(amountStr)
				&& isNull(guarranteeWay)
				&& isNull(timeLimit)
				&& isNull(consideration)
				;
	}

    //========== getters and setters ==========

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public long getMReviewId() {
		return MReviewId;
	}
	
	public void setMReviewId(long MReviewId) {
		this.MReviewId = MReviewId;
	}

	public CreditTypeEnum getType() {
		return type;
	}
	
	public void setType(CreditTypeEnum type) {
		this.type = type;
	}
	
	public String getTypeStr() {
		return (null == this.type) ? "" : this.type.code();
	}
	
	public void setTypeStr(String code) {
		this.type = CreditTypeEnum.getByCode(code);
	}

	public String getPersionalName() {
		return persionalName;
	}
	
	public void setPersionalName(String persionalName) {
		this.persionalName = persionalName;
	}

	public String getPersionalDesc() {
		return persionalDesc;
	}
	
	public void setPersionalDesc(String persionalDesc) {
		this.persionalDesc = persionalDesc;
	}

	public String getWarrantee() {
		return warrantee;
	}
	
	public void setWarrantee(String warrantee) {
		this.warrantee = warrantee;
	}

	public Money getAmount() {
		if(isNull(this.amountStr)) {
			return new Money(0L);
		}
		return Money.amout(this.amountStr);
	}
	
	public String getAmountStr() {
		return amountStr;
	}
	
	public void setAmountStr(String amountStr) {
		this.amountStr = amountStr;
	}

	public String getGuarranteeWay() {
		return guarranteeWay;
	}
	
	public void setGuarranteeWay(String guarranteeWay) {
		this.guarranteeWay = guarranteeWay;
	}

	public String getTimeLimit() {
		return timeLimit;
	}
	
	public void setTimeLimit(String timeLimit) {
		this.timeLimit = timeLimit;
	}

	public String getConsideration() {
		return consideration;
	}
	
	public void setConsideration(String consideration) {
		this.consideration = consideration;
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
