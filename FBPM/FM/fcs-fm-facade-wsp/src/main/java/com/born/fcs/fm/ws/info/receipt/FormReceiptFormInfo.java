package com.born.fcs.fm.ws.info.receipt;

import java.util.Date;
import java.util.List;

import com.born.fcs.fm.ws.enums.FormSourceEnum;
import com.born.fcs.fm.ws.enums.SourceFormEnum;
import com.born.fcs.fm.ws.enums.VoucherStatusEnum;
import com.born.fcs.pm.ws.enums.SystemEnum;
import com.born.fcs.pm.ws.info.common.FormVOInfo;
import com.yjf.common.lang.util.money.Money;

/**
 * 收款单表单信息
 * @author wuzj
 * 
 */
public class FormReceiptFormInfo extends FormVOInfo {
	
	private static final long serialVersionUID = 6341933291600230790L;
	
	/** 收款单ID */
	private long receiptId;
	/** 收款单编号 */
	private String billNo;
	/** 表单来源 */
	private FormSourceEnum formSource;
	/** 来源表单 */
	private SourceFormEnum sourceForm;
	/** 来源表单ID */
	private String sourceFormId;
	/** 来源表单系统 */
	private SystemEnum sourceFormSys;
	/** 项目编号 */
	private String projectCode;
	/** 项目名称 */
	private String projectName;
	/** 客户ID */
	private long customerId;
	/** 客户名称 */
	private String customerName;
	/** 客户类型 */
	private String contractNo;
	/** 收款日期 */
	private Date receiptDate;
	/** 收款总金额 */
	private Money amount = new Money(0, 0);
	/** 凭证号 */
	private String voucherNo;
	/** 凭证号发送状态 */
	private VoucherStatusEnum voucherStatus;
	/** 凭证同步发送时间 */
	private Date voucherSyncSendTime;
	/** 凭证同步完成时间 */
	private Date voucherSyncFinishTime;
	/** 凭证同步结果 */
	private String voucherSyncMessage;
	/** 客户经理、经办人ID */
	private long applyUserId;
	/** 客户经理、经办人账号 */
	private String applyUserAccount;
	/** 客户经理、经办人名字 */
	private String applyUserName;
	/** 客户经理、经办人部门ID */
	private long applyDeptId;
	/** 客户经理、经办人部门编码 */
	private String applyDeptCode;
	/** 客户经理、经办人部门名字 */
	private String applyDeptName;
	/** 收款事由、备注 */
	private String remark;
	/** 新增时间 */
	private Date rawAddTime;
	/** 修改时间 */
	private Date rawUpdateTime;
	/** 收款明细 */
	List<FormReceiptFeeInfo> feeList;
	
	public long getReceiptId() {
		return receiptId;
	}
	
	public void setReceiptId(long receiptId) {
		this.receiptId = receiptId;
	}
	
	public String getBillNo() {
		return billNo;
	}
	
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	
	public FormSourceEnum getFormSource() {
		return formSource;
	}
	
	public void setFormSource(FormSourceEnum formSource) {
		this.formSource = formSource;
	}
	
	public SourceFormEnum getSourceForm() {
		return sourceForm;
	}
	
	public void setSourceForm(SourceFormEnum sourceForm) {
		this.sourceForm = sourceForm;
	}
	
	public String getSourceFormId() {
		return this.sourceFormId;
	}
	
	public void setSourceFormId(String sourceFormId) {
		this.sourceFormId = sourceFormId;
	}
	
	public SystemEnum getSourceFormSys() {
		return sourceFormSys;
	}
	
	public void setSourceFormSys(SystemEnum sourceFormSys) {
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
	
	public String getContractNo() {
		return contractNo;
	}
	
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	
	public Date getReceiptDate() {
		return receiptDate;
	}
	
	public void setReceiptDate(Date receiptDate) {
		this.receiptDate = receiptDate;
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
	
	public VoucherStatusEnum getVoucherStatus() {
		return voucherStatus;
	}
	
	public void setVoucherStatus(VoucherStatusEnum voucherStatus) {
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
	
	public List<FormReceiptFeeInfo> getFeeList() {
		return feeList;
	}
	
	public void setFeeList(List<FormReceiptFeeInfo> feeList) {
		this.feeList = feeList;
	}
}
