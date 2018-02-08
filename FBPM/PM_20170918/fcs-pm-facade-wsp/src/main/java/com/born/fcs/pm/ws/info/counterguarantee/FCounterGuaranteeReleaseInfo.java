package com.born.fcs.pm.ws.info.counterguarantee;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.born.fcs.pm.ws.info.common.SimpleFormProjectInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 解保申请
 * 
 * @author lirz
 *
 * 2016-5-12 下午6:27:39
 */
public class FCounterGuaranteeReleaseInfo extends SimpleFormProjectInfo{

	private static final long serialVersionUID = -6422570571387279212L;

	private long id;
	private long formId;
	private String projectCode;
	private String projectName;
	private long customerId;
	private String customerName;
	private String contractNumber;
	private Money creditAmount = new Money(0, 0);
	private int timeLimit;
	private TimeUnitEnum timeUnit;
	private String busiType;
	private String busiTypeName;
	private String remark;
	private Money releasedAmount = new Money(0, 0);
	private Money releasingAmount = new Money(0, 0);
	private Money applyAmount = new Money(0, 0);
	private Money releaseBalance = new Money(0, 0);
	private String formChange;
	private String riskCouncilSummary;
	private String projectApproval;
	private Date rawAddTime;
	private Date rawUpdateTime;
	
	private List<FCounterGuaranteeRepayInfo> repayes; //还款
	private List<GuaranteeApplyCounterInfo> counters; //反担保措施
	
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

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public Money getCreditAmount() {
		return creditAmount;
	}

	public void setCreditAmount(Money creditAmount) {
		this.creditAmount = creditAmount;
	}

	public int getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}

	public TimeUnitEnum getTimeUnit() {
		return timeUnit;
	}

	public void setTimeUnit(TimeUnitEnum timeUnit) {
		this.timeUnit = timeUnit;
	}

	public String getBusiType() {
		return this.busiType;
	}

	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}

	public String getBusiTypeName() {
		return this.busiTypeName;
	}

	public void setBusiTypeName(String busiTypeName) {
		this.busiTypeName = busiTypeName;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Money getReleasedAmount() {
		return releasedAmount;
	}

	public void setReleasedAmount(Money releasedAmount) {
		this.releasedAmount = releasedAmount;
	}

	public Money getReleasingAmount() {
		return releasingAmount;
	}

	public void setReleasingAmount(Money releasingAmount) {
		this.releasingAmount = releasingAmount;
	}

	public Money getApplyAmount() {
		return applyAmount;
	}

	public void setApplyAmount(Money applyAmount) {
		this.applyAmount = applyAmount;
	}

	public Money getReleaseBalance() {
		return this.releaseBalance;
	}

	public void setReleaseBalance(Money releaseBalance) {
		this.releaseBalance = releaseBalance;
	}

	public String getFormChange() {
		return this.formChange;
	}

	public void setFormChange(String formChange) {
		this.formChange = formChange;
	}

	public String getRiskCouncilSummary() {
		return this.riskCouncilSummary;
	}

	public void setRiskCouncilSummary(String riskCouncilSummary) {
		this.riskCouncilSummary = riskCouncilSummary;
	}

	public String getProjectApproval() {
		return this.projectApproval;
	}

	public void setProjectApproval(String projectApproval) {
		this.projectApproval = projectApproval;
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

	public List<FCounterGuaranteeRepayInfo> getRepayes() {
		return this.repayes;
	}

	public void setRepayes(List<FCounterGuaranteeRepayInfo> repayes) {
		this.repayes = repayes;
	}

	public List<GuaranteeApplyCounterInfo> getCounters() {
		return this.counters;
	}

	public void setCounters(List<GuaranteeApplyCounterInfo> counters) {
		this.counters = counters;
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
