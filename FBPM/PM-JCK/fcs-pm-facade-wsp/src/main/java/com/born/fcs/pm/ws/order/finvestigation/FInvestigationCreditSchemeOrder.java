package com.born.fcs.pm.ws.order.finvestigation;

import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.enums.ChargePhaseEnum;
import com.born.fcs.pm.ws.enums.ChargeWayEnum;
import com.born.fcs.pm.ws.enums.ProcessControlEnum;
import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.born.fcs.pm.ws.order.base.ProjectFormOrderBase;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * 尽职调查报告-授信方案
 * 
 * @author lirz
 * 
 * 2016-3-8 下午3:38:50
 */
public class FInvestigationCreditSchemeOrder extends ProjectFormOrderBase {
	
	private static final long serialVersionUID = -3941781386902296286L;
	
	//（一）授信概况：（续授信的说明上次授信情况及（反）担保措施）
	//1、授信内容简介
	private long schemeId; //主键
	private String industryCode; //行业
	private String industryName; //行业名称
	private String creditAmountStr; //授信金额
	private Integer timeLimit; //授信期限
	private TimeUnitEnum timeUnit; //期限单位
	private String busiType; //业务品种
	private Long projectChannelId; //项目渠道ID
	private String projectChannelName; //项目渠道名称
	private Long projectSubChannelId; //二级项目渠道ID
	private String projectSubChannelName; //二级项目渠道名称
	private Long capitalChannelId; //资金渠道ID
	private String capitalChannelName; //资金渠道名称
	private Long capitalSubChannelId; //二级资金渠道ID
	private String capitalSubChannelName; //二级资金渠道名称
	private String loanPurpose; //用途
	private String repayWay; //还款方式
	private ChargePhaseEnum chargePhase; //收费(先收/后扣)
	private ChargeWayEnum chargeWay; //收费(一次性/分次)
	private Double chargeRate; //费率
	private String chargeType;
	private Double deposit; //保证金
	private String depositType; //保证金类型
	private String depositAccount; //保证金存入账户名
	//2、需重点说明的授信事项：
	private String statement; //需重点说明的授信事项
	private String other; //其它
	//（二）（反）担保措施（简要列明，按《尽职调查操作办法》第十九条规定的标准表述）
	//1、抵押
	private List<FInvestigationCreditSchemePledgeAssetOrder> pledgeOrders;
	//2、质押
	private List<FInvestigationCreditSchemePledgeAssetOrder> mortgageOrders;
	//3、保证
	private List<FInvestigationCreditSchemeGuarantorOrder> guarantorOrders;
	private String processFlag; //过程控制(YNY，如果有项则为Y)
	//4、过程控制  客户主体评价
	private String creditLevel; //客户主体评级
	private List<FInvestigationCreditSchemeProcessControlOrder> customerGrades;
	//4、过程控制  资产负债率
	private String assetLiabilityRatio; //资产负责率
	private List<FInvestigationCreditSchemeProcessControlOrder> debtRatios;
	//4、过程控制  其他
	private List<FInvestigationCreditSchemeProcessControlOrder> others;
	
	@Override
	public void check() {
		super.check();
	}
	
	public long getSchemeId() {
		return schemeId;
	}
	
	public void setSchemeId(long schemeId) {
		this.schemeId = schemeId;
	}
	
	public String getIndustryCode() {
		return industryCode;
	}
	
	public void setIndustryCode(String industryCode) {
		this.industryCode = industryCode;
	}
	
	public String getIndustryName() {
		return industryName;
	}
	
	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}
	
	public Money getCreditAmount() {
		if (isNull(this.creditAmountStr)) {
			return new Money(0L);
		}
		return Money.amout(this.creditAmountStr);
	}
	
	public String getCreditAmountStr() {
		return creditAmountStr;
	}
	
	public void setCreditAmountStr(String creditAmountStr) {
		this.creditAmountStr = creditAmountStr;
	}
	
	public Integer getTimeLimit() {
		return timeLimit;
	}
	
	public void setTimeLimit(Integer timeLimit) {
		this.timeLimit = timeLimit;
	}
	
	public TimeUnitEnum getTimeUnit() {
		return timeUnit;
	}
	
	public void setTimeUnit(TimeUnitEnum timeUnit) {
		this.timeUnit = timeUnit;
	}
	
	public String getBusiType() {
		return busiType;
	}
	
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}
	
	public String getTimeUnitStr() {
		return (null == this.timeUnit) ? "" : this.timeUnit.code();
	}
	
	public void setTimeUnitStr(String code) {
		this.timeUnit = TimeUnitEnum.getByCode(code);
	}
	
	public String getLoanPurpose() {
		return loanPurpose;
	}
	
	public void setLoanPurpose(String loanPurpose) {
		this.loanPurpose = loanPurpose;
	}
	
	public String getRepayWay() {
		return repayWay;
	}
	
	public void setRepayWay(String repayWay) {
		this.repayWay = repayWay;
	}
	
	public ChargePhaseEnum getChargePhase() {
		return chargePhase;
	}
	
	public void setChargePhase(ChargePhaseEnum chargePhase) {
		this.chargePhase = chargePhase;
	}
	
	public String getChargePhaseStr() {
		return (null == this.chargePhase) ? "" : this.chargePhase.code();
	}
	
	public void setChargePhaseStr(String code) {
		this.chargePhase = ChargePhaseEnum.getByCode(code);
	}
	
	public ChargeWayEnum getChargeWay() {
		return chargeWay;
	}
	
	public void setChargeWay(ChargeWayEnum chargeWay) {
		this.chargeWay = chargeWay;
	}
	
	public String getChargeWayStr() {
		return (null == this.getChargeWay()) ? "" : this.getChargeWay().code();
	}
	
	public void setChargeWayStr(String code) {
		this.chargeWay = ChargeWayEnum.getByCode(code);
	}
	
	public String getStatement() {
		return statement;
	}
	
	public void setStatement(String statement) {
		this.statement = statement;
	}
	
	public List<FInvestigationCreditSchemePledgeAssetOrder> getPledgeOrders() {
		return pledgeOrders;
	}
	
	public void setPledgeOrders(List<FInvestigationCreditSchemePledgeAssetOrder> pledgeOrders) {
		this.pledgeOrders = pledgeOrders;
	}
	
	public List<FInvestigationCreditSchemePledgeAssetOrder> getMortgageOrders() {
		return mortgageOrders;
	}
	
	public void setMortgageOrders(List<FInvestigationCreditSchemePledgeAssetOrder> mortgageOrders) {
		this.mortgageOrders = mortgageOrders;
	}
	
	public List<FInvestigationCreditSchemeGuarantorOrder> getGuarantorOrders() {
		return guarantorOrders;
	}
	
	public void setGuarantorOrders(List<FInvestigationCreditSchemeGuarantorOrder> guarantorOrders) {
		this.guarantorOrders = guarantorOrders;
	}
	
	public Long getProjectChannelId() {
		return projectChannelId;
	}
	
	public void setProjectChannelId(Long projectChannelId) {
		this.projectChannelId = projectChannelId;
	}
	
	public String getProjectChannelName() {
		return projectChannelName;
	}
	
	public void setProjectChannelName(String projectChannelName) {
		this.projectChannelName = projectChannelName;
	}
	
	public Long getCapitalChannelId() {
		return capitalChannelId;
	}
	
	public void setCapitalChannelId(Long capitalChannelId) {
		this.capitalChannelId = capitalChannelId;
	}
	
	public String getCapitalChannelName() {
		return capitalChannelName;
	}
	
	public void setCapitalChannelName(String capitalChannelName) {
		this.capitalChannelName = capitalChannelName;
	}
	
	public Double getChargeRate() {
		return chargeRate;
	}
	
	public void setChargeRate(Double chargeRate) {
		this.chargeRate = chargeRate;
	}
	
	public List<FInvestigationCreditSchemeProcessControlOrder> getCustomerGrades() {
		if (null != customerGrades && customerGrades.size() > 0) {
			customerGrades.get(0).setContent(creditLevel);
		}
		return customerGrades;
	}
	
	public void setCustomerGrades(List<FInvestigationCreditSchemeProcessControlOrder> customerGrades) {
		this.customerGrades = customerGrades;
	}
	
	public List<FInvestigationCreditSchemeProcessControlOrder> getDebtRatios() {
		if (null != debtRatios && debtRatios.size() > 0) {
			debtRatios.get(0).setContent(assetLiabilityRatio);
		}
		return debtRatios;
	}
	
	public void setDebtRatios(List<FInvestigationCreditSchemeProcessControlOrder> debtRatios) {
		this.debtRatios = debtRatios;
	}
	
	public List<FInvestigationCreditSchemeProcessControlOrder> getOthers() {
		return others;
	}
	
	public void setOthers(List<FInvestigationCreditSchemeProcessControlOrder> others) {
		this.others = others;
	}
	
	public String getCreditLevel() {
		return creditLevel;
	}
	
	public void setCreditLevel(String creditLevel) {
		this.creditLevel = creditLevel;
	}
	
	public String getAssetLiabilityRatio() {
		return assetLiabilityRatio;
	}
	
	public void setAssetLiabilityRatio(String assetLiabilityRatio) {
		this.assetLiabilityRatio = assetLiabilityRatio;
	}
	
	public String getProcessFlag() {
		return processFlag;
	}
	
	public void setProcessFlag(String processFlag) {
		if (!isNull(processFlag)) {
			this.processFlag = processFlag.replaceAll(",", "");
		}
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
	
	public Long getProjectSubChannelId() {
		return projectSubChannelId;
	}
	
	public void setProjectSubChannelId(Long projectSubChannelId) {
		this.projectSubChannelId = projectSubChannelId;
	}
	
	public String getProjectSubChannelName() {
		return projectSubChannelName;
	}
	
	public void setProjectSubChannelName(String projectSubChannelName) {
		this.projectSubChannelName = projectSubChannelName;
	}
	
	public Long getCapitalSubChannelId() {
		return capitalSubChannelId;
	}
	
	public void setCapitalSubChannelId(Long capitalSubChannelId) {
		this.capitalSubChannelId = capitalSubChannelId;
	}
	
	public String getCapitalSubChannelName() {
		return capitalSubChannelName;
	}
	
	public void setCapitalSubChannelName(String capitalSubChannelName) {
		this.capitalSubChannelName = capitalSubChannelName;
	}
	
	public String getChargeType() {
		return this.chargeType;
	}
	
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}
	
	public Double getDeposit() {
		return this.deposit;
	}

	public void setDeposit(Double deposit) {
		this.deposit = deposit;
	}

	public String getDepositType() {
		return this.depositType;
	}

	public void setDepositType(String depositType) {
		this.depositType = depositType;
	}

	public String getDepositAccount() {
		return this.depositAccount;
	}

	public void setDepositAccount(String depositAccount) {
		this.depositAccount = depositAccount;
	}

	public String getOther() {
		return this.other;
	}
	
	public void setOther(String other) {
		this.other = other;
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
