package com.born.fcs.pm.ws.info.council;

import java.util.List;

import com.born.fcs.pm.ws.enums.CreditLevelEnum;
import com.yjf.common.lang.util.money.Money;

/**
 * 会议纪要 - 项目评审会 - 授信方案通用
 *
 * @author wuzj
 *
 */
public class FCouncilSummaryProjectCreditConditionInfo extends FCouncilSummaryProjectInfo {
	
	private static final long serialVersionUID = 2804469482608863618L;
	
	//收费方式
	private List<FCouncilSummaryProjectChargeWayInfo> chargeWayList;
	
	//还款方式
	private List<FCouncilSummaryProjectRepayWayInfo> repayWayList;
	
	//抵押物
	private List<FCouncilSummaryProjectPledgeAssetInfo> pledges;
	private Money pledgeAssessPrice = new Money(0L); //抵押评估价格
	private Money pledgePrice = new Money(0L); //抵押价格
	
	//质押物
	private List<FCouncilSummaryProjectPledgeAssetInfo> mortgages;
	private Money mortgageAssessPrice = new Money(0L); //质押评估价格
	private Money mortgagePrice = new Money(0L); //质押价格	
	
	//保证人
	private List<FCouncilSummaryProjectGuarantorInfo> guarantors;
	//合计保证额度
	private Money guarantorAmount = new Money(0L);
	
	//文字授信条件
	private List<FCouncilSummaryProjectTextCreditConditionInfo> textCreditCondition;
	
	//过程控制(YNY，如果有项则为Y
	private String processFlag;
	
	//过程控制  客户主体评价
	private List<FCouncilSummaryProjectProcessControlInfo> customerGrades;
	
	//过程控制  资产负债率
	private List<FCouncilSummaryProjectProcessControlInfo> debtRatios;
	
	//过程控制  其他
	private List<FCouncilSummaryProjectProcessControlInfo> others;
	
	/**
	 * 客户信用等级
	 * @return
	 */
	public CreditLevelEnum getCreditLevel() {
		if (null != customerGrades && customerGrades.size() > 0) {
			return CreditLevelEnum.getByCode(customerGrades.get(0).getContent());
		}
		return null;
	}
	
	/**
	 * 资产负债率 警戒
	 * @return
	 */
	public String getAssetLiabilityRatio() {
		if (null != debtRatios && debtRatios.size() > 0) {
			return debtRatios.get(0).getContent();
		}
		return null;
	}
	
	public List<FCouncilSummaryProjectChargeWayInfo> getChargeWayList() {
		return this.chargeWayList;
	}
	
	public void setChargeWayList(List<FCouncilSummaryProjectChargeWayInfo> chargeWayList) {
		this.chargeWayList = chargeWayList;
	}
	
	public List<FCouncilSummaryProjectRepayWayInfo> getRepayWayList() {
		return this.repayWayList;
	}
	
	public void setRepayWayList(List<FCouncilSummaryProjectRepayWayInfo> repayWayList) {
		this.repayWayList = repayWayList;
	}
	
	public List<FCouncilSummaryProjectPledgeAssetInfo> getPledges() {
		return this.pledges;
	}
	
	public void setPledges(List<FCouncilSummaryProjectPledgeAssetInfo> pledges) {
		this.pledges = pledges;
	}
	
	public Money getPledgeAssessPrice() {
		return this.pledgeAssessPrice;
	}
	
	public void setPledgeAssessPrice(Money pledgeAssessPrice) {
		this.pledgeAssessPrice = pledgeAssessPrice;
	}
	
	public Money getPledgePrice() {
		return this.pledgePrice;
	}
	
	public void setPledgePrice(Money pledgePrice) {
		this.pledgePrice = pledgePrice;
	}
	
	public List<FCouncilSummaryProjectPledgeAssetInfo> getMortgages() {
		return this.mortgages;
	}
	
	public void setMortgages(List<FCouncilSummaryProjectPledgeAssetInfo> mortgages) {
		this.mortgages = mortgages;
	}
	
	public Money getMortgageAssessPrice() {
		return this.mortgageAssessPrice;
	}
	
	public void setMortgageAssessPrice(Money mortgageAssessPrice) {
		this.mortgageAssessPrice = mortgageAssessPrice;
	}
	
	public Money getMortgagePrice() {
		return this.mortgagePrice;
	}
	
	public void setMortgagePrice(Money mortgagePrice) {
		this.mortgagePrice = mortgagePrice;
	}
	
	public List<FCouncilSummaryProjectGuarantorInfo> getGuarantors() {
		return this.guarantors;
	}
	
	public void setGuarantors(List<FCouncilSummaryProjectGuarantorInfo> guarantors) {
		this.guarantors = guarantors;
	}
	
	public Money getGuarantorAmount() {
		return this.guarantorAmount;
	}
	
	public void setGuarantorAmount(Money guarantorAmount) {
		this.guarantorAmount = guarantorAmount;
	}
	
	public List<FCouncilSummaryProjectTextCreditConditionInfo> getTextCreditCondition() {
		return this.textCreditCondition;
	}
	
	public void setTextCreditCondition(	List<FCouncilSummaryProjectTextCreditConditionInfo> textCreditCondition) {
		this.textCreditCondition = textCreditCondition;
	}
	
	public String getProcessFlag() {
		return this.processFlag;
	}
	
	public void setProcessFlag(String processFlag) {
		this.processFlag = processFlag;
	}
	
	public List<FCouncilSummaryProjectProcessControlInfo> getCustomerGrades() {
		return this.customerGrades;
	}
	
	public void setCustomerGrades(List<FCouncilSummaryProjectProcessControlInfo> customerGrades) {
		this.customerGrades = customerGrades;
	}
	
	public List<FCouncilSummaryProjectProcessControlInfo> getDebtRatios() {
		return this.debtRatios;
	}
	
	public void setDebtRatios(List<FCouncilSummaryProjectProcessControlInfo> debtRatios) {
		this.debtRatios = debtRatios;
	}
	
	public List<FCouncilSummaryProjectProcessControlInfo> getOthers() {
		return this.others;
	}
	
	public void setOthers(List<FCouncilSummaryProjectProcessControlInfo> others) {
		this.others = others;
	}
	
}
