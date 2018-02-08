/**
 * www.yiji.com Inc.
 * Copyright (c) 2011 All Rights Reserved.
 */
package com.born.fcs.fm.dataobject;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.yjf.common.lang.util.money.Money;

/**
 * 手写sql 的付款单表单数据
 * @author wuzj
 * 
 */
public class FormPaymentFormDO extends SimpleFormDO {
	
	private static final long serialVersionUID = -8192481068747873656L;
	
	private long paymentId;
	
	private String billNo;
	
	private long formId;
	
	private String formSource;
	
	private String sourceForm;
	
	private long sourceFormId;
	
	private String sourceFormSys;
	
	private String projectCode;
	
	private String projectName;
	
	private long customerId;
	
	private String customerName;
	
	private Date paymentDate;
	
	private Money amount = new Money(0, 0);
	
	private String voucherNo;
	
	private String voucherStatus;
	
	private Date voucherSyncSendTime;
	
	private Date voucherSyncFinishTime;
	
	private String voucherSyncMessage;
	
	private long applyUserId;
	
	private String applyUserAccount;
	
	private String applyUserName;
	
	private long applyDeptId;
	
	private String applyDeptCode;
	
	private String applyDeptName;
	
	private String remark;
	
	private String isSimple;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	public String getBillNo() {
		return billNo;
	}
	
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	
	public long getFormId() {
		return formId;
	}
	
	public void setFormId(long formId) {
		this.formId = formId;
	}
	
	public String getFormSource() {
		return formSource;
	}
	
	public void setFormSource(String formSource) {
		this.formSource = formSource;
	}
	
	public String getSourceForm() {
		return sourceForm;
	}
	
	public void setSourceForm(String sourceForm) {
		this.sourceForm = sourceForm;
	}
	
	public long getSourceFormId() {
		return sourceFormId;
	}
	
	public void setSourceFormId(long sourceFormId) {
		this.sourceFormId = sourceFormId;
	}
	
	public String getSourceFormSys() {
		return sourceFormSys;
	}
	
	public void setSourceFormSys(String sourceFormSys) {
		this.sourceFormSys = sourceFormSys;
	}
	
	public String getProjectCode() {
		return projectCode;
	}
	
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	public String getProjectName() {
		return projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public long getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public long getPaymentId() {
		return this.paymentId;
	}
	
	public void setPaymentId(long paymentId) {
		this.paymentId = paymentId;
	}
	
	public Date getPaymentDate() {
		return this.paymentDate;
	}
	
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}
	
	public Money getAmount() {
		return amount;
	}
	
	public void setAmount(Money amount) {
		this.amount = amount;
	}
	
	public String getVoucherNo() {
		return voucherNo;
	}
	
	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
	}
	
	public String getVoucherStatus() {
		return voucherStatus;
	}
	
	public void setVoucherStatus(String voucherStatus) {
		this.voucherStatus = voucherStatus;
	}
	
	public Date getVoucherSyncSendTime() {
		return voucherSyncSendTime;
	}
	
	public void setVoucherSyncSendTime(Date voucherSyncSendTime) {
		this.voucherSyncSendTime = voucherSyncSendTime;
	}
	
	public Date getVoucherSyncFinishTime() {
		return voucherSyncFinishTime;
	}
	
	public void setVoucherSyncFinishTime(Date voucherSyncFinishTime) {
		this.voucherSyncFinishTime = voucherSyncFinishTime;
	}
	
	public String getVoucherSyncMessage() {
		return voucherSyncMessage;
	}
	
	public void setVoucherSyncMessage(String voucherSyncMessage) {
		this.voucherSyncMessage = voucherSyncMessage;
	}
	
	public long getApplyUserId() {
		return applyUserId;
	}
	
	public void setApplyUserId(long applyUserId) {
		this.applyUserId = applyUserId;
	}
	
	public String getApplyUserAccount() {
		return applyUserAccount;
	}
	
	public void setApplyUserAccount(String applyUserAccount) {
		this.applyUserAccount = applyUserAccount;
	}
	
	public String getApplyUserName() {
		return applyUserName;
	}
	
	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
	}
	
	public long getApplyDeptId() {
		return applyDeptId;
	}
	
	public void setApplyDeptId(long applyDeptId) {
		this.applyDeptId = applyDeptId;
	}
	
	public String getApplyDeptCode() {
		return applyDeptCode;
	}
	
	public void setApplyDeptCode(String applyDeptCode) {
		this.applyDeptCode = applyDeptCode;
	}
	
	public String getApplyDeptName() {
		return applyDeptName;
	}
	
	public void setApplyDeptName(String applyDeptName) {
		this.applyDeptName = applyDeptName;
	}
	
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getIsSimple() {
		return isSimple;
	}
	
	public void setIsSimple(String isSimple) {
		this.isSimple = isSimple;
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
