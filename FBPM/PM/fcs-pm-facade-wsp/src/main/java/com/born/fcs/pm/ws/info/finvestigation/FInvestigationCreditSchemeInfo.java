package com.born.fcs.pm.ws.info.finvestigation;

import java.util.Date;
import java.util.List;

import com.born.fcs.pm.ws.enums.ChargePhaseEnum;
import com.born.fcs.pm.ws.enums.ChargeTypeEnum;
import com.born.fcs.pm.ws.enums.ChargeWayEnum;
import com.born.fcs.pm.ws.enums.CreditLevelEnum;
import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.born.fcs.pm.ws.info.common.ProjectChannelRelationInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 
 * @author lirz
 * 
 * 2016-3-8 下午3:38:50
 */
public class FInvestigationCreditSchemeInfo extends InvestigationBaseInfo {
	
	private static final long serialVersionUID = -2426066760350536936L;
	//（一）授信概况：（续授信的说明上次授信情况及（反）担保措施）
	//1、授信内容简介
	private long schemeId; //主键
	private String industryCode; //行业
	private String industryName; //行业名称
	private Money creditAmount = new Money(0, 0); //授信金额
	private int timeLimit; //授信期限
	private TimeUnitEnum timeUnit; //期限单位
	private String busiType; //业务品种
	private long projectChannelId; //项目渠道ID
	private String projectChannelCode; //项目渠道编码
	private String projectChannelName; //项目渠道名称
	private String projectChannelType; //项目渠道类型
	private long projectSubChannelId; //二级项目渠道ID
	private String projectSubChannelCode; //二级项目渠道编码
	private String projectSubChannelName; //二级项目渠道名称
	private String projectSubChannelType; //二级项目渠道类型
	private long capitalChannelId; //资金渠道ID
	private String capitalChannelCode; //资金渠道编码
	private String capitalChannelName; //资金渠道名称
	private String capitalChannelType; //资金渠道类型
	private long capitalSubChannelId; //二级资金渠道ID
	private String capitalSubChannelCode; //二级资金渠道编码
	private String capitalSubChannelName; //二级资金渠道名称
	private String capitalSubChannelType; //二级资金渠道类型
	
	private String loanPurpose; //用途
	private String repayWay; //还款方式
	private ChargePhaseEnum chargePhase; //收费(先收/后扣)
	private ChargeWayEnum chargeWay; //收费(一次性/分次)
	private double chargeRate; //费率
	//收费类型
	private ChargeTypeEnum chargeType;
	private double deposit; //保证金
	private ChargeTypeEnum depositType; //保证金类型
	private String depositAccount; //保证金存入账户名
	//2、需重点说明的授信事项：
	private String statement; //需重点说明的授信事项
	private String other; //其它
	//抵押
	private List<FInvestigationCreditSchemePledgeAssetInfo> pledges;
	private Money pledgeAssessPrice = new Money(0L); //抵押评估价格
	private Money pledgePrice = new Money(0L); //抵押价格
	//质押
	private List<FInvestigationCreditSchemePledgeAssetInfo> mortgages;
	private Money mortgageAssessPrice = new Money(0L); //质押评估价格
	private Money mortgagePrice = new Money(0L); //质押价格
	//保证人
	private List<FInvestigationCreditSchemeGuarantorInfo> guarantors;
	//合计保证额度
	private Money guarantorAmount = new Money(0L); 
	private String processFlag; //过程控制(YNY，如果有项则为Y
	//4、过程控制  客户主体评价
	private List<FInvestigationCreditSchemeProcessControlInfo> customerGrades;
	//4、过程控制  资产负债率
	private List<FInvestigationCreditSchemeProcessControlInfo> debtRatios;
	//4、过程控制  其他
	private List<FInvestigationCreditSchemeProcessControlInfo> others;
	private Date rawAddTime;
	private Date rawUpdateTime;
	
	private List<ProjectChannelRelationInfo> capitalChannel; //资金渠道
//	private List<ProjectChannelRelationInfo> projectChannel; //项目渠道
	
	public String getPledgeIds() {
		if (null != pledges && pledges.size() > 0) {
			StringBuilder sb = new StringBuilder();
			for (FInvestigationCreditSchemePledgeAssetInfo pledge : pledges) {
				sb.append(pledge.getAssetsId()).append(",");
			}
			sb.deleteCharAt(sb.length()-1);
			return sb.toString();
		}
		
		return "";
	}
	
	public String getMortgageIds() {
		if (null != mortgages && mortgages.size() > 0) {
			StringBuilder sb = new StringBuilder();
			for (FInvestigationCreditSchemePledgeAssetInfo pledge : mortgages) {
				sb.append(pledge.getAssetsId()).append(",");
			}
			sb.deleteCharAt(sb.length()-1);
			return sb.toString();
		}
		
		return "";
	}
	
	/**
	 * 获取保证额度(合计)
	 * 
	 * @return
	 */
	public Money getTotaoGuaranteeAmount() {
		if (null != guarantors && guarantors.size() > 0) {
			Money total = new Money(0L);
			for (FInvestigationCreditSchemeGuarantorInfo guarantor : guarantors) {
				total.addTo(guarantor.getGuaranteeAmount());
			}
			
			return total;
		} else {
			return null;
		}
	}
	
	public CreditLevelEnum getCreditLevel() {
		if (null != customerGrades && customerGrades.size() > 0) {
			return CreditLevelEnum.getByCode(customerGrades.get(0).getContent());
		}
		
		return null;
	}
	
	public String getAssetLiabilityRatio() {
		if (null != debtRatios && debtRatios.size() > 0) {
			return debtRatios.get(0).getContent();
		}
		
		return null;
	}
	
	public boolean hasCustomerGrades() {
		return null != processFlag && processFlag.contains("C");
	}
	
	public boolean hasDebtRatios() {
		return null != processFlag && processFlag.contains("D");
	}
	
	public boolean hasOthers() {
		return null != processFlag && processFlag.contains("O");
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
		return busiType;
	}
	
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}
	
	public long getProjectChannelId() {
		return projectChannelId;
	}
	
	public void setProjectChannelId(long projectChannelId) {
		this.projectChannelId = projectChannelId;
	}
	
	public String getProjectChannelName() {
		return projectChannelName;
	}
	
	public void setProjectChannelName(String projectChannelName) {
		this.projectChannelName = projectChannelName;
	}
	
	public long getCapitalChannelId() {
		return capitalChannelId;
	}
	
	public void setCapitalChannelId(long capitalChannelId) {
		this.capitalChannelId = capitalChannelId;
	}
	
	public String getCapitalChannelName() {
		return capitalChannelName;
	}
	
	public void setCapitalChannelName(String capitalChannelName) {
		this.capitalChannelName = capitalChannelName;
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
	
	public ChargeWayEnum getChargeWay() {
		return chargeWay;
	}
	
	public void setChargeWay(ChargeWayEnum chargeWay) {
		this.chargeWay = chargeWay;
	}
	
	public double getChargeRate() {
		return chargeRate;
	}
	
	public void setChargeRate(double chargeRate) {
		this.chargeRate = chargeRate;
	}
	
	public String getStatement() {
		return statement;
	}
	
	public void setStatement(String statement) {
		this.statement = statement;
	}
	
	public List<FInvestigationCreditSchemePledgeAssetInfo> getPledges() {
		return pledges;
	}
	
	public void setPledges(List<FInvestigationCreditSchemePledgeAssetInfo> pledges) {
		this.pledges = pledges;
	}
	
	public List<FInvestigationCreditSchemePledgeAssetInfo> getMortgages() {
		return mortgages;
	}
	
	public void setMortgages(List<FInvestigationCreditSchemePledgeAssetInfo> mortgages) {
		this.mortgages = mortgages;
	}
	
	public List<FInvestigationCreditSchemeGuarantorInfo> getGuarantors() {
		return guarantors;
	}
	
	public void setGuarantors(List<FInvestigationCreditSchemeGuarantorInfo> guarantors) {
		this.guarantors = guarantors;
	}
	
	public List<FInvestigationCreditSchemeProcessControlInfo> getCustomerGrades() {
		return customerGrades;
	}
	
	public void setCustomerGrades(List<FInvestigationCreditSchemeProcessControlInfo> customerGrades) {
		this.customerGrades = customerGrades;
	}
	
	public List<FInvestigationCreditSchemeProcessControlInfo> getDebtRatios() {
		return debtRatios;
	}
	
	public void setDebtRatios(List<FInvestigationCreditSchemeProcessControlInfo> debtRatios) {
		this.debtRatios = debtRatios;
	}
	
	public List<FInvestigationCreditSchemeProcessControlInfo> getOthers() {
		return others;
	}
	
	public void setOthers(List<FInvestigationCreditSchemeProcessControlInfo> others) {
		this.others = others;
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

	public Money getPledgeAssessPrice() {
		return pledgeAssessPrice;
	}

	public void setPledgeAssessPrice(Money pledgeAssessPrice) {
		this.pledgeAssessPrice = pledgeAssessPrice;
	}

	public Money getPledgePrice() {
		return pledgePrice;
	}

	public void setPledgePrice(Money pledgePrice) {
		this.pledgePrice = pledgePrice;
	}

	public Money getMortgageAssessPrice() {
		return mortgageAssessPrice;
	}

	public void setMortgageAssessPrice(Money mortgageAssessPrice) {
		this.mortgageAssessPrice = mortgageAssessPrice;
	}

	public Money getMortgagePrice() {
		return mortgagePrice;
	}

	public void setMortgagePrice(Money mortgagePrice) {
		this.mortgagePrice = mortgagePrice;
	}

	public Money getGuarantorAmount() {
		return guarantorAmount;
	}

	public void setGuarantorAmount(Money guarantorAmount) {
		this.guarantorAmount = guarantorAmount;
	}

	public String getProcessFlag() {
		return processFlag;
	}

	public void setProcessFlag(String processFlag) {
		this.processFlag = processFlag;
	}

	public long getProjectSubChannelId() {
		return projectSubChannelId;
	}

	public void setProjectSubChannelId(long projectSubChannelId) {
		this.projectSubChannelId = projectSubChannelId;
	}

	public String getProjectSubChannelName() {
		return projectSubChannelName;
	}

	public void setProjectSubChannelName(String projectSubChannelName) {
		this.projectSubChannelName = projectSubChannelName;
	}

	public long getCapitalSubChannelId() {
		return capitalSubChannelId;
	}

	public void setCapitalSubChannelId(long capitalSubChannelId) {
		this.capitalSubChannelId = capitalSubChannelId;
	}

	public String getCapitalSubChannelName() {
		return capitalSubChannelName;
	}

	public void setCapitalSubChannelName(String capitalSubChannelName) {
		this.capitalSubChannelName = capitalSubChannelName;
	}

	public ChargeTypeEnum getChargeType() {
		return this.chargeType;
	}

	public void setChargeType(ChargeTypeEnum chargeType) {
		this.chargeType = chargeType;
	}

	public double getDeposit() {
		return this.deposit;
	}

	public void setDeposit(double deposit) {
		this.deposit = deposit;
	}

	public ChargeTypeEnum getDepositType() {
		return this.depositType;
	}

	public void setDepositType(ChargeTypeEnum depositType) {
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

//	public List<ProjectChannelRelationInfo> getCapitalChannel() {
//		return this.capitalChannel;
//	}
//
//	public void setCapitalChannel(List<ProjectChannelRelationInfo> capitalChannel) {
//		this.capitalChannel = capitalChannel;
//	}

	public String getProjectChannelCode() {
		return this.projectChannelCode;
	}

	public void setProjectChannelCode(String projectChannelCode) {
		this.projectChannelCode = projectChannelCode;
	}

	public String getProjectSubChannelCode() {
		return this.projectSubChannelCode;
	}

	public void setProjectSubChannelCode(String projectSubChannelCode) {
		this.projectSubChannelCode = projectSubChannelCode;
	}

	public String getCapitalChannelCode() {
		return this.capitalChannelCode;
	}

	public void setCapitalChannelCode(String capitalChannelCode) {
		this.capitalChannelCode = capitalChannelCode;
	}

	public String getCapitalSubChannelCode() {
		return this.capitalSubChannelCode;
	}

	public void setCapitalSubChannelCode(String capitalSubChannelCode) {
		this.capitalSubChannelCode = capitalSubChannelCode;
	}

	public String getProjectChannelType() {
		return this.projectChannelType;
	}

	public void setProjectChannelType(String projectChannelType) {
		this.projectChannelType = projectChannelType;
	}

	public String getProjectSubChannelType() {
		return this.projectSubChannelType;
	}

	public void setProjectSubChannelType(String projectSubChannelType) {
		this.projectSubChannelType = projectSubChannelType;
	}

	public String getCapitalChannelType() {
		return this.capitalChannelType;
	}

	public void setCapitalChannelType(String capitalChannelType) {
		this.capitalChannelType = capitalChannelType;
	}

	public String getCapitalSubChannelType() {
		return this.capitalSubChannelType;
	}

	public void setCapitalSubChannelType(String capitalSubChannelType) {
		this.capitalSubChannelType = capitalSubChannelType;
	}

	public List<ProjectChannelRelationInfo> getCapitalChannel() {
		return this.capitalChannel;
	}

	public void setCapitalChannel(List<ProjectChannelRelationInfo> capitalChannel) {
		this.capitalChannel = capitalChannel;
	}

}
