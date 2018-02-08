package com.born.fcs.fm.ws.order.payment;

import java.util.Date;
import java.util.List;

import com.born.fcs.fm.ws.enums.VoucherStatusEnum;
import com.born.fcs.pm.ws.order.base.FormOrderBase;
import com.yjf.common.lang.util.money.Money;

public class FormTransferOrder extends FormOrderBase {
	/** Comment for <code>serialVersionUID</code> */
	private static final long serialVersionUID = -4282603875229233564L;
	
	//========== properties ==========
	
	private long transferId;
	
	private String billNo;
	
	private String voucherNo;
	
	private VoucherStatusEnum voucherStatus;
	
	private Date voucherSyncSendTime;
	
	private Date voucherSyncFinishTime;
	
	private String voucherSyncMessage;
	
	private long transferDeptId;
	
	private String transferDeptName;
	
	private String deptHead;
	
	private Date applicationTime;
	
	private String reasons;
	
	private long agentId;
	
	private String agent;
	
	private long payeeId;
	
	private String payee;
	
	private long bankId;
	
	private String bankName;
	
	private String bankAccount;
	
	private Money amount = new Money(0, 0);
	
	private int attachmentsNum;
	
	private Date rawAddTime;
	
	private Date rawUpdateTime;
	
	/** 转账明细 */
	private List<FormTransferDetailOrder> detailList;
	
	//========== getters and setters ==========
	
	@Override
	public void check() {
		validateNotNull(transferDeptId, "申请部门");
		validateNotNull(applicationTime, "申请日期");
		validateNotNull(reasons, "用款事由");
		validateNotNull(agent, "经办人");
		validateNotNull(bankName, "转入账户");
		validateNotNull(detailList, "转账明细");
		//		Assert.isTrue(detailList.size() > 0, "转账明细");
		//		for (FormTransferDetailOrder detailOrder : detailList) {
		//			detailOrder.check();
		//		}
	}
	
	public long getTransferId() {
		return transferId;
	}
	
	public long getPayeeId() {
		return this.payeeId;
	}
	
	public void setPayeeId(long payeeId) {
		this.payeeId = payeeId;
	}
	
	public String getPayee() {
		return this.payee;
	}
	
	public void setPayee(String payee) {
		this.payee = payee;
	}
	
	public void setTransferId(long transferId) {
		this.transferId = transferId;
	}
	
	public int getAttachmentsNum() {
		return attachmentsNum;
	}
	
	public void setAttachmentsNum(int attachmentsNum) {
		this.attachmentsNum = attachmentsNum;
	}
	
	public List<FormTransferDetailOrder> getDetailList() {
		return detailList;
	}
	
	public void setDetailList(List<FormTransferDetailOrder> detailList) {
		this.detailList = detailList;
	}
	
	public String getBillNo() {
		return billNo;
	}
	
	public void setBillNo(String billNo) {
		this.billNo = billNo;
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
	
	public long getTransferDeptId() {
		return transferDeptId;
	}
	
	public void setTransferDeptId(long transferDeptId) {
		this.transferDeptId = transferDeptId;
	}
	
	public String getTransferDeptName() {
		return transferDeptName;
	}
	
	public void setTransferDeptName(String transferDeptName) {
		this.transferDeptName = transferDeptName;
	}
	
	public String getDeptHead() {
		return deptHead;
	}
	
	public void setDeptHead(String deptHead) {
		this.deptHead = deptHead;
	}
	
	public Date getApplicationTime() {
		return applicationTime;
	}
	
	public void setApplicationTime(Date applicationTime) {
		this.applicationTime = applicationTime;
	}
	
	public String getReasons() {
		return reasons;
	}
	
	public void setReasons(String reasons) {
		this.reasons = reasons;
	}
	
	public long getAgentId() {
		return agentId;
	}
	
	public void setAgentId(long agentId) {
		this.agentId = agentId;
	}
	
	public String getAgent() {
		return agent;
	}
	
	public void setAgent(String agent) {
		this.agent = agent;
	}
	
	public long getBankId() {
		return bankId;
	}
	
	public void setBankId(long bankId) {
		this.bankId = bankId;
	}
	
	public String getBankName() {
		return bankName;
	}
	
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	public String getBankAccount() {
		return bankAccount;
	}
	
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	
	public Money getAmount() {
		return amount;
	}
	
	public void setAmount(Money amount) {
		if (amount == null) {
			this.amount = new Money(0, 0);
		} else {
			this.amount = amount;
		}
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
	
}
