package com.born.fcs.pm.ws.order.council;

import java.util.List;

import com.born.fcs.pm.ws.enums.ProcessControlEnum;

/**
 * 会议纪要 - 授信条件通用Order
 *
 * @author wuzj
 *
 */
public class FCouncilSummaryProjectCreditConditionOrder extends FCouncilSummaryProjectOrder {
	
	private static final long serialVersionUID = 2539584381923219656L;
	
	//收费方式
	private List<FCouncilSummaryProjectChargeWayOrder> chargeWayOrder;
	
	//还款方式
	private List<FCouncilSummaryProjectRepayWayOrder> repayWayOrder;
	
	//保证人 
	private List<FCouncilSummaryProjectGuarantorOrder> guarantorOrders;
	
	//抵押物
	private List<FCouncilSummaryProjectPledgeAssetOrder> pledgeOrders;
	
	//质押物
	private List<FCouncilSummaryProjectPledgeAssetOrder> mortgageOrders;
	
	//文字授信条件
	private List<FCouncilSummaryProjectTextCreditConditionOrder> textCreditConditionOrder;
	
	//过程控制  客户主体评价
	private List<FCouncilSummaryProjectProcessControlOrder> customerGrades;
	private String creditLevel; //客户主体评级
	
	//过程控制  资产负债率
	private List<FCouncilSummaryProjectProcessControlOrder> debtRatios;
	private String assetLiabilityRatio; //资产负责率
	//过程控制  其他
	private List<FCouncilSummaryProjectProcessControlOrder> others;
	//过程控制(YNY，如果有项则为Y)
	private String processFlag;
	
	public List<FCouncilSummaryProjectChargeWayOrder> getChargeWayOrder() {
		return this.chargeWayOrder;
	}
	
	public void setChargeWayOrder(List<FCouncilSummaryProjectChargeWayOrder> chargeWayOrder) {
		this.chargeWayOrder = chargeWayOrder;
	}
	
	public List<FCouncilSummaryProjectRepayWayOrder> getRepayWayOrder() {
		return this.repayWayOrder;
	}
	
	public void setRepayWayOrder(List<FCouncilSummaryProjectRepayWayOrder> repayWayOrder) {
		this.repayWayOrder = repayWayOrder;
	}
	
	public List<FCouncilSummaryProjectGuarantorOrder> getGuarantorOrders() {
		return this.guarantorOrders;
	}
	
	public void setGuarantorOrders(List<FCouncilSummaryProjectGuarantorOrder> guarantorOrders) {
		this.guarantorOrders = guarantorOrders;
	}
	
	public List<FCouncilSummaryProjectPledgeAssetOrder> getPledgeOrders() {
		return this.pledgeOrders;
	}
	
	public void setPledgeOrders(List<FCouncilSummaryProjectPledgeAssetOrder> pledgeOrders) {
		this.pledgeOrders = pledgeOrders;
	}
	
	public List<FCouncilSummaryProjectPledgeAssetOrder> getMortgageOrders() {
		return this.mortgageOrders;
	}
	
	public void setMortgageOrders(List<FCouncilSummaryProjectPledgeAssetOrder> mortgageOrders) {
		this.mortgageOrders = mortgageOrders;
	}
	
	public List<FCouncilSummaryProjectTextCreditConditionOrder> getTextCreditConditionOrder() {
		return this.textCreditConditionOrder;
	}
	
	public void setTextCreditConditionOrder(List<FCouncilSummaryProjectTextCreditConditionOrder> textCreditConditionOrder) {
		this.textCreditConditionOrder = textCreditConditionOrder;
	}
	
	public String getCreditLevel() {
		return this.creditLevel;
	}
	
	public void setCreditLevel(String creditLevel) {
		this.creditLevel = creditLevel;
	}
	
	public String getAssetLiabilityRatio() {
		return this.assetLiabilityRatio;
	}
	
	public void setAssetLiabilityRatio(String assetLiabilityRatio) {
		this.assetLiabilityRatio = assetLiabilityRatio;
	}
	
	public List<FCouncilSummaryProjectProcessControlOrder> getOthers() {
		return this.others;
	}
	
	public void setOthers(List<FCouncilSummaryProjectProcessControlOrder> others) {
		this.others = others;
	}
	
	public void setCustomerGrades(List<FCouncilSummaryProjectProcessControlOrder> customerGrades) {
		this.customerGrades = customerGrades;
	}
	
	public void setDebtRatios(List<FCouncilSummaryProjectProcessControlOrder> debtRatios) {
		this.debtRatios = debtRatios;
	}
	
	public String getProcessFlag() {
		return processFlag;
	}
	
	public void setProcessFlag(String processFlag) {
		if (!isNull(processFlag)) {
			this.processFlag = processFlag.replaceAll(",", "");
		}
	}
	
	public List<FCouncilSummaryProjectProcessControlOrder> getCustomerGrades() {
		if (null != customerGrades && customerGrades.size() > 0) {
			customerGrades.get(0).setContent(creditLevel);
		}
		return customerGrades;
	}
	
	public List<FCouncilSummaryProjectProcessControlOrder> getDebtRatios() {
		if (null != debtRatios && debtRatios.size() > 0) {
			debtRatios.get(0).setContent(assetLiabilityRatio);
		}
		return debtRatios;
	}
	
	public boolean isSave(ProcessControlEnum type) {
		if (isNull(this.processFlag) || null == type) {
			return false;
		}
		
		if (type == ProcessControlEnum.CUSTOMER_GRADE) {
			return this.processFlag.contains("C");
		} else if (type == ProcessControlEnum.DEBT_RATIO) {
			return this.processFlag.contains("D");
		} else if (type == ProcessControlEnum.OTHER) {
			return this.processFlag.contains("O");
		} else {
			return false;
		}
	}
	
}
