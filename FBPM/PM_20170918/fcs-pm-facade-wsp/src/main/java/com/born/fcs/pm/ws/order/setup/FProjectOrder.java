package com.born.fcs.pm.ws.order.setup;

import java.util.Date;

import com.yjf.common.lang.util.StringUtil;
import com.yjf.common.lang.util.money.Money;

/**
 * 项目立项 - 申请贷款/担保情况Order
 *
 * @author wuzj
 *
 */
public class FProjectOrder extends SetupFormSaveOrder {
	
	private static final long serialVersionUID = 8368115404736401175L;
	
	private Long projectId;
	
	private Integer timeLimit;
	
	private String timeUnit;
	
	private String amountStr;
	
	private Money amount = new Money(0, 0);
	
	private String foreignAmount;
	
	private String foreignCurrencyCode;
	
	private String foreignCurrencyName;
	
	private String exchangeRate;
	
	private Date exchangeUpdateTime;
	
	private Long busiManagerId;
	
	private String busiManagerAccount;
	
	private String busiManagerName;
	
	private String industryCode;
	
	private Long busiManagerbId;
	
	private String busiManagerbAccount;
	
	private String busiManagerbName;
	
	private Long deptId;
	
	private String deptName;
	
	private String otherCounterGuarntee;
	
	private String remark;
	
	/** 是否属于南充分公司项目 YES/NO */
	private String belongToNc;
	
	@Override
	public void check() {
		validateHasText(busiType, "业务类型");
	}
	
	public Long getProjectId() {
		return this.projectId;
	}
	
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
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
	
	public String getCustomerType() {
		return this.customerType;
	}
	
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	
	public Integer getTimeLimit() {
		return this.timeLimit;
	}
	
	public void setTimeLimit(Integer timeLimit) {
		this.timeLimit = timeLimit;
	}
	
	public String getTimeUnit() {
		return this.timeUnit;
	}
	
	public void setTimeUnit(String timeUnit) {
		this.timeUnit = timeUnit;
	}
	
	public String getAmountStr() {
		return this.amountStr;
	}
	
	public void setAmountStr(String amountStr) {
		this.amountStr = amountStr;
	}
	
	public Money getAmount() {
		if (StringUtil.isNotBlank(amountStr))
			return Money.amout(amountStr);
		return this.amount;
	}
	
	public void setAmount(Money amount) {
		this.amount = amount;
	}
	
	public Long getBusiManagerId() {
		return this.busiManagerId;
	}
	
	public void setBusiManagerId(Long busiManagerId) {
		this.busiManagerId = busiManagerId;
	}
	
	public String getBusiManagerAccount() {
		return this.busiManagerAccount;
	}
	
	public void setBusiManagerAccount(String busiManagerAccount) {
		this.busiManagerAccount = busiManagerAccount;
	}
	
	public String getBusiManagerName() {
		return this.busiManagerName;
	}
	
	public void setBusiManagerName(String busiManagerName) {
		this.busiManagerName = busiManagerName;
	}
	
	public Long getBusiManagerbId() {
		return this.busiManagerbId;
	}
	
	public void setBusiManagerbId(Long busiManagerbId) {
		this.busiManagerbId = busiManagerbId;
	}
	
	public String getBusiManagerbAccount() {
		return this.busiManagerbAccount;
	}
	
	public void setBusiManagerbAccount(String busiManagerbAccount) {
		this.busiManagerbAccount = busiManagerbAccount;
	}
	
	public String getBusiManagerbName() {
		return this.busiManagerbName;
	}
	
	public void setBusiManagerbName(String busiManagerbName) {
		this.busiManagerbName = busiManagerbName;
	}
	
	public Long getDeptId() {
		return this.deptId;
	}
	
	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	
	public String getDeptName() {
		return this.deptName;
	}
	
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	
	public String getIndustryCode() {
		return this.industryCode;
	}
	
	public void setIndustryCode(String industryCode) {
		this.industryCode = industryCode;
	}
	
	public String getOtherCounterGuarntee() {
		return this.otherCounterGuarntee;
	}
	
	public void setOtherCounterGuarntee(String otherCounterGuarntee) {
		this.otherCounterGuarntee = otherCounterGuarntee;
	}
	
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getBelongToNc() {
		return this.belongToNc;
	}
	
	public void setBelongToNc(String belongToNc) {
		this.belongToNc = belongToNc;
	}
	
	public String getForeignAmount() {
		return this.foreignAmount;
	}
	
	public void setForeignAmount(String foreignAmount) {
		this.foreignAmount = foreignAmount;
	}
	
	public String getForeignCurrencyCode() {
		return this.foreignCurrencyCode;
	}
	
	public void setForeignCurrencyCode(String foreignCurrencyCode) {
		this.foreignCurrencyCode = foreignCurrencyCode;
	}
	
	public String getForeignCurrencyName() {
		return this.foreignCurrencyName;
	}
	
	public void setForeignCurrencyName(String foreignCurrencyName) {
		this.foreignCurrencyName = foreignCurrencyName;
	}
	
	public String getExchangeRate() {
		return this.exchangeRate;
	}
	
	public void setExchangeRate(String exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	
	public Date getExchangeUpdateTime() {
		return this.exchangeUpdateTime;
	}
	
	public void setExchangeUpdateTime(Date exchangeUpdateTime) {
		this.exchangeUpdateTime = exchangeUpdateTime;
	}
	
}
