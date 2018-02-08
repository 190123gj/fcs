package com.born.fcs.pm.ws.order.counterguarantee;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.order.base.ProjectFormOrderBase;
import com.yjf.common.lang.util.money.Money;

/**
 * 解保申请
 * 
 * @author lirz
 * 
 * 2016-5-12 下午6:26:19
 */
public class FCounterGuaranteeReleaseOrder extends ProjectFormOrderBase {
	
	private static final long serialVersionUID = 3702528805868323775L;
	
	private long id;
	private String contractNumber;
	private Money creditAmount = new Money(0, 0);
	private int timeLimit;
	private String timeUnit;
	private String busiType;
	private String busiTypeName;
	private String remark;
	private Money releasedAmount = new Money(0, 0);
	private Money releasingAmount = new Money(0, 0);
	private String applyAmountStr;
	private Money releaseBalance = new Money(0, 0);
	private String formChange;
	private String riskCouncilSummary;
	private String projectApproval;
	private List<CreditConditionReleaseOrder> releases;
	private List<FCounterGuaranteeRepayOrder> repayes; //还款列表
	private List<GuaranteeApplyCounterOrder> counter; //授信条件落实
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
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
	
	public String getTimeUnit() {
		return timeUnit;
	}
	
	public void setTimeUnit(String timeUnit) {
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
		if (null != this.repayes && this.repayes.size() > 0) {
			Money m = new Money(0L);
			for (FCounterGuaranteeRepayOrder repay : repayes) {
				m.addTo(repay.getRepayAmount());
			}
			return m;
		}
		if (isNull(this.applyAmountStr)) {
			return new Money(0L);
		}
		return Money.amout(this.applyAmountStr);
	}
	
	public String getApplyAmountStr() {
		return applyAmountStr;
	}
	
	public void setApplyAmountStr(String applyAmountStr) {
		this.applyAmountStr = applyAmountStr;
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

	public List<CreditConditionReleaseOrder> getReleases() {
		return releases;
	}
	
	public void setReleases(List<CreditConditionReleaseOrder> releases) {
		this.releases = releases;
	}
	
	public List<FCounterGuaranteeRepayOrder> getRepayes() {
		return this.repayes;
	}

	public void setRepayes(List<FCounterGuaranteeRepayOrder> repayes) {
		this.repayes = repayes;
	}

	public List<GuaranteeApplyCounterOrder> getCounter() {
		return this.counter;
	}

	public void setCounter(List<GuaranteeApplyCounterOrder> counter) {
		this.counter = counter;
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
