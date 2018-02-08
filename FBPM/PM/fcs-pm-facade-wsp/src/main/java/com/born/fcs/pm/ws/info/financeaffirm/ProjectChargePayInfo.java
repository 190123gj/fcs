package com.born.fcs.pm.ws.info.financeaffirm;

import java.util.Date;

import com.born.fcs.pm.ws.enums.CustomerTypeEnum;
import com.born.fcs.pm.ws.enums.FeeTypeEnum;
import com.born.fcs.pm.ws.enums.PaymentMenthodEnum;
import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 项目收费、划付明细
 *
 *
 * @author wuzj
 *
 */
public class ProjectChargePayInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = -9069288925848393957L;
	
	/** 项目编号 */
	private String projectCode;
	/** 项目名称 */
	private String projectName;
	/** 客户ID */
	private long customerId;
	/** 客户名称 */
	private String customerName;
	/** 客户类型 */
	private CustomerTypeEnum customerType;
	/** 业务类型 */
	private String busiType;
	/** 业务类型名称 */
	private String busiTypeName;
	/**
	 * 收款通知单/资金划付 CHARGE_NOTIFICATION CAPITAL_APPROPROATION_APPLY
	 **/
	private String affirmFormType;
	/** 费用类型 */
	private String feeType;
	/** 收费类型 */
	private FeeTypeEnum chargeType;
	/** 划付类型 */
	private PaymentMenthodEnum payType;
	/** 收费/划付金额 */
	private Money payAmount = new Money(0, 0);
	/** 剩余退还金额 */
	private Money returnCustomerAmount = new Money(0, 0);
	/** 收费/划付时间 */
	private Date payTime;
	/** 收费/划付账号 */
	private String payeeAccountName;
	/** 保证金账号 */
	private String depositAccount;
	/** 保证金比例 */
	private double marginRate;
	/** 保证金存出/入时间 */
	private Date depositTime;
	/** 保证金存出/入期限 */
	private int period;
	/** 保证金存出/入期限单位 */
	private TimeUnitEnum periodUnit;
	
	/** 财务确认-明细id */
	private long payId;
	
	/** 财务确认-资金划付和收费通知-明细id */
	private long detailId;
	
	public long getPayId() {
		return payId;
	}
	
	public void setPayId(long payId) {
		this.payId = payId;
	}
	
	public String getProjectCode() {
		return this.projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getProjectName() {
		return this.projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public long getCustomerId() {
		return this.customerId;
	}
	
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	
	public String getCustomerName() {
		return this.customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public CustomerTypeEnum getCustomerType() {
		return this.customerType;
	}
	
	public void setCustomerType(CustomerTypeEnum customerType) {
		this.customerType = customerType;
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
	
	public String getAffirmFormType() {
		return this.affirmFormType;
	}
	
	public void setAffirmFormType(String affirmFormType) {
		this.affirmFormType = affirmFormType;
	}
	
	public String getFeeType() {
		return this.feeType;
	}
	
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}
	
	public FeeTypeEnum getChargeType() {
		return this.chargeType;
	}
	
	public void setChargeType(FeeTypeEnum chargeType) {
		this.chargeType = chargeType;
	}
	
	public PaymentMenthodEnum getPayType() {
		return this.payType;
	}
	
	public void setPayType(PaymentMenthodEnum payType) {
		this.payType = payType;
	}
	
	public Money getPayAmount() {
		return this.payAmount;
	}
	
	public void setPayAmount(Money payAmount) {
		this.payAmount = payAmount;
	}
	
	public Date getPayTime() {
		return this.payTime;
	}
	
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	
	public String getPayeeAccountName() {
		return this.payeeAccountName;
	}
	
	public void setPayeeAccountName(String payeeAccountName) {
		this.payeeAccountName = payeeAccountName;
	}
	
	public String getDepositAccount() {
		return this.depositAccount;
	}
	
	public void setDepositAccount(String depositAccount) {
		this.depositAccount = depositAccount;
	}
	
	public double getMarginRate() {
		return this.marginRate;
	}
	
	public void setMarginRate(double marginRate) {
		this.marginRate = marginRate;
	}
	
	public Date getDepositTime() {
		return this.depositTime;
	}
	
	public void setDepositTime(Date depositTime) {
		this.depositTime = depositTime;
	}
	
	public int getPeriod() {
		return this.period;
	}
	
	public void setPeriod(int period) {
		this.period = period;
	}
	
	public TimeUnitEnum getPeriodUnit() {
		return this.periodUnit;
	}
	
	public void setPeriodUnit(TimeUnitEnum periodUnit) {
		this.periodUnit = periodUnit;
	}
	
	public Money getReturnCustomerAmount() {
		return returnCustomerAmount;
	}
	
	public void setReturnCustomerAmount(Money returnCustomerAmount) {
		this.returnCustomerAmount = returnCustomerAmount;
	}
	
	public long getDetailId() {
		return this.detailId;
	}
	
	public void setDetailId(long detailId) {
		this.detailId = detailId;
	}
	
}
