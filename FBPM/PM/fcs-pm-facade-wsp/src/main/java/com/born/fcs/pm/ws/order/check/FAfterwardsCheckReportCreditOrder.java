package com.born.fcs.pm.ws.order.check;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.order.base.ValidateOrderBase;
/**
 * 
 * 信用状况
 *
 * @author lirz
 * 
 * 2017-1-12 下午3:32:40
 *
 */
public class FAfterwardsCheckReportCreditOrder extends ValidateOrderBase{

    private static final long serialVersionUID = -5838117911790147474L;

	private long id;

	private long formId;

	private String loanInstitution;

	private String loanBalance;

	private String loanStartDate;

	private String loanEndDate;

	private String loanCost;

	private String guaranteePledge;

	private String creditRemark;

	private int sortOrder;

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public long getFormId() {
		return formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}

	public String getLoanInstitution() {
		return loanInstitution;
	}
	
	public void setLoanInstitution(String loanInstitution) {
		this.loanInstitution = loanInstitution;
	}

	public String getLoanBalance() {
		return loanBalance;
	}
	
	public void setLoanBalance(String loanBalance) {
		this.loanBalance = loanBalance;
	}

	public String getLoanStartDate() {
		return loanStartDate;
	}
	
	public void setLoanStartDate(String loanStartDate) {
		this.loanStartDate = loanStartDate;
	}

	public String getLoanEndDate() {
		return loanEndDate;
	}
	
	public void setLoanEndDate(String loanEndDate) {
		this.loanEndDate = loanEndDate;
	}

	public String getLoanCost() {
		return loanCost;
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

	public String getCreditRemark() {
		return creditRemark;
	}
	
	public void setCreditRemark(String creditRemark) {
		this.creditRemark = creditRemark;
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
