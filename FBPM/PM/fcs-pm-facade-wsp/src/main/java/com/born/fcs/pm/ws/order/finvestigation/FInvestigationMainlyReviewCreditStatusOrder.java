package com.born.fcs.pm.ws.order.finvestigation;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.enums.CreditTypeEnum;
import com.born.fcs.pm.ws.order.base.ValidateOrderBase;
import com.yjf.common.lang.util.DateUtil;
import com.yjf.common.lang.util.money.Money;

/**
 * 客户信用状况
 * 
 * @author lirz
 * 
 * 2016-3-10 下午3:09:24
 */
public class FInvestigationMainlyReviewCreditStatusOrder extends ValidateOrderBase {
	
	private static final long serialVersionUID = -2120169774391769799L;
	private long id;
	private long MReviewId;
	//类型（客户/个人-企业实际控制人、主要股东、管理人员、担保人等）
	private CreditTypeEnum type;
	//企业实际控制人、主要股东、管理人员、担保人等（类型为个人时填写）
	private String persionalName;
	private String persionalDesc; //个人描述（类型为个人时填写）
	private String loanInstitution; //融资机构
	private String loanBalanceStr; //融资余额
	private Date loanStartDate; //融资期限起
	private Date loanEndDate; //融资期限止
	private String loanCost; //融资成本
	private String guaranteePledge; //担保方式及扣保物
	private String consideration; //提供的对价
	private int sortOrder;
	
	public boolean isNull() {
		return isNull(persionalName) && isNull(persionalDesc) && isNull(loanInstitution)
				&& isNull(loanBalanceStr) && isNull(loanStartDate) && isNull(loanEndDate)
				&& isNull(loanCost) && isNull(guaranteePledge) && isNull(consideration);
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
	
	public String getLoanInstitution() {
		return loanInstitution;
	}
	
	public void setLoanInstitution(String loanInstitution) {
		this.loanInstitution = loanInstitution;
	}
	
	public Money getLoanBalance() {
		if (isNull(this.loanBalanceStr)) {
			return new Money(0L);
		}
		return Money.amout(this.loanBalanceStr);
	}
	
	public String getLoanBalanceStr() {
		return loanBalanceStr;
	}
	
	public void setLoanBalanceStr(String loanBalanceStr) {
		this.loanBalanceStr = loanBalanceStr;
	}
	
	public Date getLoanStartDate() {
		return loanStartDate;
	}
	
	public void setLoanStartDate(Date loanStartDate) {
		this.loanStartDate = loanStartDate;
	}
	
	public void setLoanStartDateStr(String loanStartDateStr) {
		this.loanStartDate = DateUtil.strToDtSimpleFormat(loanStartDateStr);
	}
	
	public Date getLoanEndDate() {
		return loanEndDate;
	}
	
	public void setLoanEndDate(Date loanEndDate) {
		this.loanEndDate = loanEndDate;
	}
	
	public void setLoanEndDateStr(String loanEndDateStr) {
		this.loanEndDate = DateUtil.strToDtSimpleFormat(loanEndDateStr);
	}
	
	public String getLoanCost() {
		return this.loanCost;
	}
	
	public void setLoanCost(String loanCost) {
		this.loanCost = loanCost;
	}
	
	public String getGuaranteePledge() {
		return guaranteePledge;
	}
	
	public void setGuaranteePledge(String guaranteePledge) {
		this.guaranteePledge = guaranteePledge;
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
