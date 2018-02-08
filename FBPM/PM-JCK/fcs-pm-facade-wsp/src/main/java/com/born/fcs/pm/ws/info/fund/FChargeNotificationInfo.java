package com.born.fcs.pm.ws.info.fund;

import java.util.Date;
import java.util.List;

import com.born.fcs.pm.ws.enums.ChargeBasisEnum;
import com.born.fcs.pm.ws.info.common.BaseToStringInfo;
import com.yjf.common.lang.util.money.Money;

public class FChargeNotificationInfo extends BaseToStringInfo {
	
	private static final long serialVersionUID = -4015002282846611871L;
	
	private long notificationId;
	
	private long formId;
	
	private String projectCode;
	
	private String projectName;
	
	private long customerId;
	
	private String customerName;
	
	private String isAgentPay;
	
	private Money payAmount = new Money(0, 0);
	
	private String payName;
	
	private String payAccount;
	
	private String payBank;
	
	private Date payTime;

	private long submitManId;

	private String submitManName;
	
	private String remark;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	private List<FChargeNotificationFeeInfo> feeList;

	private String chargeNo;

	private Date chargeTime;

	private String status;

	private String contractCode;

	private ChargeBasisEnum chargeBasis;

	private String applyCode;

	private String applyTitle;

	private String payer;

	private String selfPay;

	private String payForAnother;

	private Money anotherPayAmount = new Money(0, 0);

	private String anotherPayName;

	private String anotherPayAccount;

	private String anotherPayBank;

	private String attachment;

	public ChargeBasisEnum getChargeBasis() {
		return chargeBasis;
	}

	public void setChargeBasis(ChargeBasisEnum chargeBasis) {
		this.chargeBasis = chargeBasis;
	}

	public String getApplyCode() {
		return applyCode;
	}

	public void setApplyCode(String applyCode) {
		this.applyCode = applyCode;
	}

	public String getApplyTitle() {
		return applyTitle;
	}

	public void setApplyTitle(String applyTitle) {
		this.applyTitle = applyTitle;
	}

	public String getPayer() {
		return payer;
	}

	public void setPayer(String payer) {
		this.payer = payer;
	}

	public long getNotificationId() {
		return this.notificationId;
	}
	
	public void setNotificationId(long notificationId) {
		this.notificationId = notificationId;
	}
	
	public long getFormId() {
		return this.formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
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
	
	public String getIsAgentPay() {
		return this.isAgentPay;
	}
	
	public void setIsAgentPay(String isAgentPay) {
		this.isAgentPay = isAgentPay;
	}
	
	public Money getPayAmount() {
		return this.payAmount;
	}
	
	public void setPayAmount(Money payAmount) {
		this.payAmount = payAmount;
	}
	
	public String getPayName() {
		return this.payName;
	}
	
	public void setPayName(String payName) {
		this.payName = payName;
	}
	
	public String getPayAccount() {
		return this.payAccount;
	}
	
	public void setPayAccount(String payAccount) {
		this.payAccount = payAccount;
	}
	
	public String getPayBank() {
		return this.payBank;
	}
	
	public void setPayBank(String payBank) {
		this.payBank = payBank;
	}
	
	public Date getPayTime() {
		return this.payTime;
	}
	
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	
	public String getRemark() {
		return this.remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public Date getRawAddTime() {
		return this.rawAddTime;
	}
	
	public void setRawAddTime(Date rawAddTime) {
		this.rawAddTime = rawAddTime;
	}
	
	public Date getRawUpdateTime() {
		return this.rawUpdateTime;
	}
	
	public void setRawUpdateTime(Date rawUpdateTime) {
		this.rawUpdateTime = rawUpdateTime;
	}
	
	public List<FChargeNotificationFeeInfo> getFeeList() {
		return this.feeList;
	}
	
	public void setFeeList(List<FChargeNotificationFeeInfo> feeList) {
		this.feeList = feeList;
	}

	public long getSubmitManId() {
		return submitManId;
	}

	public void setSubmitManId(long submitManId) {
		this.submitManId = submitManId;
	}

	public String getSubmitManName() {
		return submitManName;
	}

	public void setSubmitManName(String submitManName) {
		this.submitManName = submitManName;
	}

	public String getChargeNo() {
		return chargeNo;
	}

	public void setChargeNo(String chargeNo) {
		this.chargeNo = chargeNo;
	}

	public Date getChargeTime() {
		return chargeTime;
	}

	public void setChargeTime(Date chargeTime) {
		this.chargeTime = chargeTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getSelfPay() {
		return selfPay;
	}

	public void setSelfPay(String selfPay) {
		this.selfPay = selfPay;
	}

	public String getPayForAnother() {
		return payForAnother;
	}

	public void setPayForAnother(String payForAnother) {
		this.payForAnother = payForAnother;
	}

	public Money getAnotherPayAmount() {
		return anotherPayAmount;
	}

	public void setAnotherPayAmount(Money anotherPayAmount) {
		this.anotherPayAmount = anotherPayAmount;
	}

	public String getAnotherPayName() {
		return anotherPayName;
	}

	public void setAnotherPayName(String anotherPayName) {
		this.anotherPayName = anotherPayName;
	}

	public String getAnotherPayAccount() {
		return anotherPayAccount;
	}

	public void setAnotherPayAccount(String anotherPayAccount) {
		this.anotherPayAccount = anotherPayAccount;
	}

	public String getAnotherPayBank() {
		return anotherPayBank;
	}

	public void setAnotherPayBank(String anotherPayBank) {
		this.anotherPayBank = anotherPayBank;
	}

	public String getAttachment() {
		return attachment;
	}

	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
}
