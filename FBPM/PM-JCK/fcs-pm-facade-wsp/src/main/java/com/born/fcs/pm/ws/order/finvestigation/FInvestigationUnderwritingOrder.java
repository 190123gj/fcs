package com.born.fcs.pm.ws.order.finvestigation;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.born.fcs.pm.ws.enums.ChargeUnitEnum;
import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.born.fcs.pm.ws.enums.UnderwritingChargeWaytEnum;
import com.born.fcs.pm.ws.order.base.ProjectFormOrderBase;
import com.yjf.common.lang.util.DateUtil;
import com.yjf.common.lang.util.money.Money;

/**
 * 项目调查 - 承销项目情况
 * 
 * @author lirz
 *
 * 2016-3-23 下午4:32:35
 */
public class FInvestigationUnderwritingOrder extends ProjectFormOrderBase {
	
	private static final long serialVersionUID = -8568947123974719879L;
	
	private long underwritingId; //主键
	private String projectSource; //项目来源
	private String projectGist; //项目依据
	private String financingAmountStr; //本次申请融资金额
	private Date demandDate; //发行人资金需求日期
	private Date setupDate; //立项日期
	private Date issueDate; //预计发行日期
	private String collectScaleStr; //募集规模
	private Integer timeLimit; //融资期限
	private String timeUnit; //融资单位(年,月,日)
	private Double totalCost; //总成本(百分比)
	private Double issueRate; //发行利率
	private String hasCredit; //是否增信
	private Long exchangeId; //交易所ID
	private String exchangeName; //交易所名称
	private Double chargeRate; //收费费率
	private String chargeUnit; //收费费率单位(%/年,单)
	private Double lawOfficeRate; //律所费率
	private String lawOfficeUnit; //收费费率单位(%/年,单)
	private Double clubRate; //会所费率
	private String clubUnit; //会所费率单位(%/年,单)
	private Double otherRate; //其他费用
	private String otherUnit; //其他费率单位(%/年,单)
	private Double underwritingRate; //承销费率
	private String underwritingUnit; //承销费率单位(%/年,单)
	private String chargeWay; //收费方式
	private String balanceStr; //发行人扣除费用后实际到账金额
	private String intro; //发行主体简要介绍
	
	//========== getters and setters ==========
	
	public long getUnderwritingId() {
		return underwritingId;
	}
	
	public void setUnderwritingId(long underwritingId) {
		this.underwritingId = underwritingId;
	}
	
	public String getProjectSource() {
		return projectSource;
	}
	
	public void setProjectSource(String projectSource) {
		this.projectSource = projectSource;
	}
	
	public String getProjectGist() {
		return projectGist;
	}
	
	public void setProjectGist(String projectGist) {
		this.projectGist = projectGist;
	}
	
	public Money getFinancingAmount() {
		if (isNull(this.financingAmountStr)) {
			return new Money(0L);
		}
		return Money.amout(this.financingAmountStr);
	}
	
	public String getFinancingAmountStr() {
		return financingAmountStr;
	}
	
	public void setFinancingAmountStr(String financingAmountStr) {
		this.financingAmountStr = financingAmountStr;
	}
	
	public Date getDemandDate() {
		return demandDate;
	}
	
	public void setDemandDate(Date demandDate) {
		this.demandDate = demandDate;
	}
	
	public void setDemandDateStr(String demandDateStr) {
		this.demandDate = DateUtil.strToDtSimpleFormat(demandDateStr);
	}
	
	public Date getSetupDate() {
		return setupDate;
	}
	
	public void setSetupDate(Date setupDate) {
		this.setupDate = setupDate;
	}
	
	public void setSetupDateStr(String setupDateStr) {
		this.setupDate = DateUtil.strToDtSimpleFormat(setupDateStr);
	}
	
	public Date getIssueDate() {
		return issueDate;
	}
	
	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}
	
	public void setIssueDateStr(String issueDateStr) {
		this.issueDate = DateUtil.strToDtSimpleFormat(issueDateStr);
	}
	
	public Money getCollectScale() {
		if (isNull(this.collectScaleStr)) {
			return new Money(0L);
		}
		return Money.amout(this.collectScaleStr);
	}
	
	public String getCollectScaleStr() {
		return collectScaleStr;
	}
	
	public void setCollectScaleStr(String collectScaleStr) {
		this.collectScaleStr = collectScaleStr;
	}
	
	public Integer getTimeLimit() {
		return timeLimit;
	}
	
	public void setTimeLimit(Integer timeLimit) {
		this.timeLimit = timeLimit;
	}
	
	public String getTimeUnit() {
		return timeUnit;
	}
	
	public void setTimeUnit(String timeUnit) {
		TimeUnitEnum unit = TimeUnitEnum.getByCode(timeUnit);
		this.timeUnit = (null == unit) ? "" : unit.code();
	}
	
	public Double getTotalCost() {
		return totalCost;
	}
	
	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
	}
	
	public Double getIssueRate() {
		return issueRate;
	}
	
	public void setIssueRate(Double issueRate) {
		this.issueRate = issueRate;
	}
	
	public String getHasCredit() {
		return hasCredit;
	}
	
	public void setHasCredit(String hasCredit) {
		this.hasCredit = hasCredit;
	}
	
	public Long getExchangeId() {
		return exchangeId;
	}
	
	public void setExchangeId(Long exchangeId) {
		this.exchangeId = exchangeId;
	}
	
	public String getExchangeName() {
		return exchangeName;
	}
	
	public void setExchangeName(String exchangeName) {
		this.exchangeName = exchangeName;
	}
	
	public Double getChargeRate() {
		return chargeRate;
	}
	
	public void setChargeRate(Double chargeRate) {
		this.chargeRate = chargeRate;
	}
	
	public String getChargeUnit() {
		return chargeUnit;
	}
	
	public void setChargeUnit(String chargeUnit) {
		ChargeUnitEnum unit = ChargeUnitEnum.getByCode(chargeUnit);
		this.chargeUnit = (null == unit) ? "" : unit.code();
	}
	
	public Double getLawOfficeRate() {
		return lawOfficeRate;
	}
	
	public void setLawOfficeRate(Double lawOfficeRate) {
		this.lawOfficeRate = lawOfficeRate;
	}
	
	public Double getClubRate() {
		return clubRate;
	}
	
	public void setClubRate(Double clubRate) {
		this.clubRate = clubRate;
	}
	
	public Double getOtherRate() {
		return otherRate;
	}
	
	public void setOtherRate(Double otherRate) {
		this.otherRate = otherRate;
	}
	
	public Double getUnderwritingRate() {
		return underwritingRate;
	}
	
	public void setUnderwritingRate(Double underwritingRate) {
		this.underwritingRate = underwritingRate;
	}
	
	public String getChargeWay() {
		return chargeWay;
	}
	
	public void setChargeWay(String chargeWay) {
		UnderwritingChargeWaytEnum way = UnderwritingChargeWaytEnum.getByCode(chargeWay);
		this.chargeWay = (null == way) ? "" : way.code();
	}
	
	public Money getBalance() {
		if (isNull(this.balanceStr)) {
			return new Money(0L);
		}
		return Money.amout(this.balanceStr);
	}
	
	public String getBalanceStr() {
		return balanceStr;
	}
	
	public void setBalanceStr(String balanceStr) {
		this.balanceStr = balanceStr;
	}
	
	public String getIntro() {
		return intro;
	}
	
	public void setIntro(String intro) {
		this.intro = intro;
	}
	
	public String getLawOfficeUnit() {
		return lawOfficeUnit;
	}
	
	public void setLawOfficeUnit(String lawOfficeUnit) {
		this.lawOfficeUnit = lawOfficeUnit;
	}
	
	public String getClubUnit() {
		return clubUnit;
	}
	
	public void setClubUnit(String clubUnit) {
		this.clubUnit = clubUnit;
	}
	
	public String getOtherUnit() {
		return otherUnit;
	}
	
	public void setOtherUnit(String otherUnit) {
		this.otherUnit = otherUnit;
	}
	
	public String getUnderwritingUnit() {
		return underwritingUnit;
	}
	
	public void setUnderwritingUnit(String underwritingUnit) {
		this.underwritingUnit = underwritingUnit;
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
