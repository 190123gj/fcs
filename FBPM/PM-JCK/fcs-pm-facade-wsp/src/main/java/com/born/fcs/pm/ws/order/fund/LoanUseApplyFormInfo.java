package com.born.fcs.pm.ws.order.fund;

import java.util.Date;

import com.born.fcs.pm.ws.enums.CustomerTypeEnum;
import com.born.fcs.pm.ws.enums.LoanUseApplyTypeEnum;
import com.born.fcs.pm.ws.enums.TimeUnitEnum;
import com.born.fcs.pm.ws.info.common.FormVOInfo;
import com.yjf.common.lang.util.money.Money;

public class LoanUseApplyFormInfo extends FormVOInfo {
	
	private static final long serialVersionUID = -3572629098976662126L;
	
	/**
	 * 项目部分信息
	 */
	private String projectCode;
	
	private String projectName;
	
	private long customerId;
	
	private String customerName;
	
	private CustomerTypeEnum customerType;
	
	private String busiType;
	
	private String busiTypeName;
	
	private Money amount = new Money(0, 0);
	
	//监管机构批复金额
	private Money approvalAmount = new Money(0, 0);
	
	private int timeLimit;
	
	private TimeUnitEnum timeUnit;
	
	private long applyId;
	
	private long notificationId;
	
	private LoanUseApplyTypeEnum applyType;
	
	private Date applyAddTime;
	
	private Date applyUpdateTime;
	
	private Money applyAmount = Money.zero();
	
	/** 回执部分 */
	private long receiptId;
	
	private Money actualAmount;
	
	private Money actualDepositAmount;
	
	private Date receiptAddTime;
	
	private Date receiptUpdateTime;
	
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
	
	public Money getAmount() {
		return this.amount;
	}
	
	public void setAmount(Money amount) {
		this.amount = amount;
	}
	
	public Money getApprovalAmount() {
		return this.approvalAmount;
	}
	
	public void setApprovalAmount(Money approvalAmount) {
		this.approvalAmount = approvalAmount;
	}
	
	public int getTimeLimit() {
		return this.timeLimit;
	}
	
	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}
	
	public TimeUnitEnum getTimeUnit() {
		return this.timeUnit;
	}
	
	public void setTimeUnit(TimeUnitEnum timeUnit) {
		this.timeUnit = timeUnit;
	}
	
	public long getApplyId() {
		return this.applyId;
	}
	
	public void setApplyId(long applyId) {
		this.applyId = applyId;
	}
	
	public long getNotificationId() {
		return this.notificationId;
	}
	
	public void setNotificationId(long notificationId) {
		this.notificationId = notificationId;
	}
	
	public LoanUseApplyTypeEnum getApplyType() {
		return this.applyType;
	}
	
	public void setApplyType(LoanUseApplyTypeEnum applyType) {
		this.applyType = applyType;
	}
	
	public Date getApplyAddTime() {
		return this.applyAddTime;
	}
	
	public void setApplyAddTime(Date applyAddTime) {
		this.applyAddTime = applyAddTime;
	}
	
	public Date getApplyUpdateTime() {
		return this.applyUpdateTime;
	}
	
	public void setApplyUpdateTime(Date applyUpdateTime) {
		this.applyUpdateTime = applyUpdateTime;
	}
	
	public long getReceiptId() {
		return this.receiptId;
	}
	
	public void setReceiptId(long receiptId) {
		this.receiptId = receiptId;
	}
	
	public Date getReceiptAddTime() {
		return this.receiptAddTime;
	}
	
	public void setReceiptAddTime(Date receiptAddTime) {
		this.receiptAddTime = receiptAddTime;
	}
	
	public Date getReceiptUpdateTime() {
		return this.receiptUpdateTime;
	}
	
	public void setReceiptUpdateTime(Date receiptUpdateTime) {
		this.receiptUpdateTime = receiptUpdateTime;
	}
	
	public Money getApplyAmount() {
		return this.applyAmount;
	}
	
	public void setApplyAmount(Money applyAmount) {
		this.applyAmount = applyAmount;
	}
	
	public Money getActualAmount() {
		return this.actualAmount;
	}
	
	public void setActualAmount(Money actualAmount) {
		this.actualAmount = actualAmount;
	}
	
	public Money getActualDepositAmount() {
		return this.actualDepositAmount;
	}
	
	public void setActualDepositAmount(Money actualDepositAmount) {
		this.actualDepositAmount = actualDepositAmount;
	}
}
